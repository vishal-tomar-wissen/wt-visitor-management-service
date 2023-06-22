package com.wissen.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.wissen.decorator.VisitorDecorator;
import com.wissen.dto.EmailDTO;
import com.wissen.dto.FilterRequest;
import com.wissen.dto.VisitorDto;
import com.wissen.enrich.FilterResult;
import com.wissen.enrich.FilterSpecification;
import com.wissen.entity.OTP;
import com.wissen.entity.Timing;
import com.wissen.entity.Visitor;
import com.wissen.exceptions.VisitorManagementException;
import com.wissen.repository.OTPRepository;
import com.wissen.repository.TimingRepository;
import com.wissen.repository.VisitorRepository;
import com.wissen.service.TimingService;
import com.wissen.service.VisitorService;
import com.wissen.util.VisitorManagementUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation class for visitor service.
 *
 * @author Vishal Tomar
 */
@Service
@Slf4j
public class VisitorServiceImpl implements VisitorService {

	@Autowired
	private VisitorRepository visitorRepository;

	@Autowired
	private VisitorDecorator visitorDecorator;

	@Autowired
	private TimingService timingService;

	@Autowired
	private OTPRepository otpRepository;

	@Autowired
	private TimingRepository timingRepository;

	@Autowired
	private EmailService emailService;

	@Value("${email.subject}")
	String emailSubject;

	@Value("${email.body}")
	String emailBody;

	/**
	 * Filter Specification is used to enrich the input request This class will form
	 * dynamic queries No dao layer is been called in this class, just a transformer
	 */
	@Autowired
	FilterSpecification<Visitor> filterSpecification;

	/**
	 * Save visitor details.
	 *
	 * @param visitorDto
	 * @return savedVisitor
	 */
	@Override
	@Transactional
	public Visitor saveVisitorDetails(VisitorDto visitorDto) {

		// decorating before saving
		Visitor visitor = visitorDecorator.decorateBeforeSaving(visitorDto);

		// if user already checked in
		List<Timing> timings = this.timingService.findByVisitorAndOutTime(visitor, null);
		if (!CollectionUtils.isEmpty(timings))
			throw new VisitorManagementException("Visitor already checked in.");

		// save/update the details
		Visitor savedVisitor = this.visitorRepository.save(visitor);

		// decorating after saving
		visitorDecorator.decorateAfterSaving(savedVisitor, null);

		return savedVisitor;
	}

	/**
	 * Method to fetch values from the Visitor table Dynamic Query will be formed
	 * based on the request filter If the list is empty then all visitor will be
	 * fetched Else will return only specific results *
	 *
	 * @param requestFilters
	 * @return List of visitor response based on the filter request
	 */
	@Override
	public List<Visitor> fetchVisitorsDetails(List<FilterRequest> requestFilters) {
		List<Visitor> visitors = new ArrayList<>();
		if (CollectionUtils.isEmpty(requestFilters)) {
			// TODO if no filter are passed, then fetch only 30 days visitor details
			visitors.addAll(visitorRepository.findAll());
		} else {
			Specification<Visitor> specificationRequest = filterSpecification
					.getSpecificationFromFilters(requestFilters);
			visitors.addAll(visitorRepository.findAll(specificationRequest));
		}

		// Decorating images for UI.
		this.visitorDecorator.decorateImageForUi(visitors);

		return visitors;
	}

	/**
	 * Method will save or update the details based on the details provided
	 *
	 * @param outDetails
	 * @return List of saved or updated details
	 */
	@Override
	public List<Visitor> saveOrUpdateVisitors(List<Visitor> outDetails) {
		return visitorRepository.saveAll(outDetails);
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public List<Visitor> getVisitorsByPhoneNoOrEmail(String phNo, String email) {
		return this.visitorRepository.findByPhoneNumberOrEmail(phNo, email);
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public List<Visitor> getVisitorByTypeNameOrTiming(List<FilterRequest> requestFilters) {

		List<Visitor> visitors = new ArrayList<>();
		if (CollectionUtils.isEmpty(requestFilters)) {
			// TODO fetch only 30days records pass in time in the request
			visitors.addAll(visitorRepository.findAll());
		} else {
			Specification<Visitor> specificationRequest = filterSpecification
					.getSpecificationByTypeNameOrTiming(requestFilters);
			visitors.addAll(Sets.newHashSet(visitorRepository.findAll(specificationRequest)));
			visitors = FilterResult.filterExtraByFilterRequest(requestFilters, visitors);
		}

		if (CollectionUtils.isNotEmpty(visitors)) {
			// Decorating images for UI.
			this.visitorDecorator.decorateImageForUi(visitors);
		}

		return Lists.newArrayList(visitors);
	}

	@Override
	public String getOTP(String phEmail) {

		String response = "";

		if (!VisitorManagementUtils.validateEmailOrMobile(phEmail))
			response = "Please enter valid Email id or Mobile Number";

		else if (!visitorRepository.existsByEmailIdOrPhoneNumber(phEmail))
			response = "Visitor doesn't exists please register";
		else {
			Visitor visitor = visitorRepository.findByEmail(phEmail);

			List<Timing> timingList = visitor.getTimings();

			LocalDateTime checkin = CollectionUtils.isEmpty(timingList) ? null : timingList.get(0).getInTime();
			LocalDateTime checkout = CollectionUtils.isEmpty(timingList) ? null : timingList.get(0).getOutTime();

			if (checkin != null && checkout == null)
				response = "Guest is already in the firm";
			else {

				OTP visitorOTPRecord = otpRepository.findByVisitor(visitor);

				boolean isOtpRecordExists = validateOTPRecord(visitorOTPRecord);

				if (!isOtpRecordExists || (isOtpRecordExists && !validateOTPExpiry(visitorOTPRecord.getExpiry()))) {

					int otp = generateOTP();

					storeOTP(visitor, otp);

					sendEmail(phEmail, otp);

					updateGuestWithCheckinCheckoutTime(visitor);

				} else {
					sendEmail(phEmail, Integer.valueOf(visitorOTPRecord.getOtp()));
				}

				response = "OTP sent to your registered Emial Id";
			}
		}

		return response;
	}

	private void sendEmail(String emailId, int otp) {

		List<String> recipients = new ArrayList<>();
		recipients.add(emailId);

		// generate emailDTO object
		EmailDTO emailDTO = new EmailDTO();
		emailDTO.setSubject(emailSubject);
		emailDTO.setBody(emailBody + otp);
		emailDTO.setRecipients(recipients);

		// send generated e-mail
		emailService.sendSimpleMessage(emailDTO);
	}

	private void storeOTP(Visitor visitor, int otp) {

		OTP existingVisitorOtp = otpRepository.findByVisitor(visitor);

		if (existingVisitorOtp != null) {
			existingVisitorOtp.setOtp(String.valueOf(otp));
			existingVisitorOtp.setExpiry(LocalDateTime.now().plusMinutes(15));
			otpRepository.save(existingVisitorOtp);
		} else {
			OTP otpRecord = new OTP();
			otpRecord.setVisitor(visitor);
			otpRecord.setOtp(String.valueOf(otp));
			otpRecord.setExpiry(LocalDateTime.now().plusMinutes(15));
			otpRepository.save(otpRecord);
		}
	}

	private void updateGuestWithCheckinCheckoutTime(Visitor visitor) {

		Timing existingTiming = timingRepository.findByVisitor(visitor);

		if (existingTiming != null) {
			existingTiming.setInTime(LocalDateTime.now());
			existingTiming.setOutTime(null);

			timingRepository.save(existingTiming);
		} else {

			Timing newTiming = new Timing();
			newTiming.setVisitor(visitor);
			newTiming.setInTime(LocalDateTime.now());
			timingRepository.save(newTiming);
		}

	}

	private boolean validateOTPRecord(OTP otpRecord) {

		if (otpRecord != null)
			return true;
		else
			return false;
	}

	private boolean validateOTPExpiry(LocalDateTime time) {
		if (time.isAfter(LocalDateTime.now()) || time.isEqual(LocalDateTime.now()))
			return true;
		else
			return false;

	}

	@Override
	public String verifyOTP(String emailId, String otp) {

		String response = "";

		if (StringUtils.isBlank(emailId) && !VisitorManagementUtils.validateEmailId(emailId))
			response = "Email id is not valid";

		Visitor visitor = visitorRepository.findByEmail(emailId);

		if (visitor != null) {

			OTP visitorOTPRecord = otpRepository.findByVisitor(visitor);

			if (visitorOTPRecord != null) {

				if (validateOTPExpiry(visitorOTPRecord.getExpiry())) {

					if (otp.equals(visitorOTPRecord.getOtp()) && visitor.getEmail().equals(emailId))
						response = "OTP is valid";
					else
						response = "Invalid OTP";
				} else {
					response = "OTP Expired. Please generate new one";
				}
			}

		} else
			response = "Invalid Emial Id";

		return response;
	}

	/**
	 * method to generate random 6 digit OTP
	 * 
	 * @return
	 */
	private int generateOTP() {

		Random random = new Random();

		int otp = 100000 + random.nextInt(900000);

		return otp;
	}

}
