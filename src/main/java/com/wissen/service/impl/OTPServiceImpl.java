package com.wissen.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wissen.constants.Constants;
import com.wissen.constants.enums.EmailTemplates;
import com.wissen.dto.EmailDTO;
import com.wissen.dto.OtpDTO;
import com.wissen.entity.Employee;
import com.wissen.entity.OTP;
import com.wissen.entity.Timing;
import com.wissen.entity.Visitor;
import com.wissen.exceptions.VisitorManagementException;
import com.wissen.repository.OTPRepository;
import com.wissen.repository.TimingRepository;
import com.wissen.repository.VisitorRepository;
import com.wissen.service.OTPService;

/**
 * 
 * Implementation class for OTP service. This class contains methods to generate
 * OTP based on visitors emailId or Mobile number and send the OTP to registered
 * email id and the method to verify the OTP
 *
 * @author Ankit Garg
 */
@Service
public class OTPServiceImpl implements OTPService {

	@Autowired
	private VisitorRepository visitorRepository;

	@Autowired
	private OTPRepository otpRepository;

	@Autowired
	private TimingRepository timingRepository;

	@Autowired
	private EmailService emailService;

	@Value("${email.subject}")
	private String otpEmailSubject;

	@Value("${host.email.subject}")
	private String hostEmailSubject;

	/**
	 * This method fetches the Visitor details based on the given input either email
	 * id or phone number. It generates a 4 digit OTP and store it in visitor_otp
	 * table and then send an email to the visitor with OTP
	 * 
	 * @param phEmail : input email id or phone number to fetch visitor details
	 * 
	 * @return : validation message
	 * 
	 */
	@Override
	@Transactional
	public String sendOTP(String phEmail) {
		Visitor visitor = visitorRepository.findByEmailOrPhoneNumber(phEmail);

		if (visitor == null)
			throw new VisitorManagementException(Constants.VISITOR_DOESNOT_EXISTS);

		List<Timing> timingList = visitor.getTimings();
		if (timingList.stream().filter(time -> Objects.isNull(time.getOutTime())).count() > 0)
			throw new VisitorManagementException(Constants.VISITOR_IN_FIRM);
		OTP visitorOTPRecord = otpRepository.findByVisitorAndExpiryAfter(visitor, LocalDateTime.now());
		if (Objects.isNull(visitorOTPRecord)) {
			String otp = generateOTP();
			storeOTP(visitor, otp);
			prepareAndSendEmailForOTP(visitor, otp);
		} else
			prepareAndSendEmailForOTP(visitor, visitorOTPRecord.getOtp());
		return Constants.OTP_GENERATION_MESSAGE;
	}

	/**
	 * This method stores an OTP in visitor_otp table with expiry time as current
	 * time + 15 mins
	 * 
	 * @param visitor
	 * @param otp
	 */
	private void storeOTP(Visitor visitor, String otp) {
		OTP visitorOtp = new OTP();
		visitorOtp.setVisitor(visitor);
		visitorOtp.setOtp(otp);
		visitorOtp.setExpiry(LocalDateTime.now().plusMinutes(15));
		otpRepository.save(visitorOtp);
	}

	/**
	 * This method prepares to send an email to visitor's email id with generated
	 * OTP
	 * 
	 * @param visitor : Visitor details
	 * @param otp     : generated OTP
	 */
	private void prepareAndSendEmailForOTP(Visitor visitor, String otp) {
		try {
			EmailDTO emailDTO = new EmailDTO();

			Map<String, Object> model = new HashMap<String, Object>();
			model.put(Constants.VISITOR_NAME, visitor.getFullName());
			model.put(Constants.VISITOR_EMAIL, visitor.getEmail());
			model.put(Constants.OTP, otp);
			emailDTO.setProperties(model);

			List<String> recipients = new ArrayList<>();
			recipients.add(visitor.getEmail());
			emailDTO.setRecipients(recipients);
			emailDTO.setSubject(otpEmailSubject);
			emailDTO.setTemplateName(EmailTemplates.VISITOR_OTP.getTemplateName());

			emailService.sendEmail(emailDTO);
		} catch (Exception e) {
			throw new VisitorManagementException(
					"Exception Occurred while preparing the temple to send an email for OTP");
		}

	}

	/**
	 * This method validates the generated OTP and returns the success message else
	 * throw an exception if validation fails
	 * 
	 * @param data : DTO object contains OTP and phone/email
	 * 
	 * @return : validation message
	 */
	@Override
	@Transactional
	public String verifyOTP(OtpDTO data) {
		OTP visitorOTPRecord = otpRepository.findByVisitorEmailOrPhoneNumber(data.getPhEmail());
		if (visitorOTPRecord != null) {
			if (data.getOtp().equalsIgnoreCase(visitorOTPRecord.getOtp())) {

				Visitor visitor = visitorRepository.findByEmailOrPhoneNumber(data.getPhEmail());
				if (visitor != null) {
					setTimings(visitor);
					otpRepository.delete(visitorOTPRecord);
					return Constants.VALID_OTP_MESSAGE + ". " + sendEmailToHost(visitor);
				} else
					throw new VisitorManagementException(Constants.VISITOR_NOT_FOUND_MESSAGE);
			} else
				throw new VisitorManagementException(Constants.INVALID_OTP_MESSAGE);
		} else
			throw new VisitorManagementException(Constants.NEW_OTP_MESSAGE);

	}

	/**
	 * method to generate random 4 digit OTP will be sent to registered visitor's
	 * email id also stores in DB
	 * 
	 * @return : 4 digit random number
	 */
	private String generateOTP() {
		Random random = new Random();
		int otp = random.nextInt(9999);
		return String.format("%04d", otp);
	}

	/**
	 * This method sets the timings for the visitor in timing table with inTime as
	 * current time and outTime as null
	 * 
	 * @param visitor : Visitor record
	 */
	private void setTimings(Visitor visitor) {

		if (visitor != null) {

			// Sort the timings list based on the outTime in descending order
			Collections.sort(visitor.getTimings(), Comparator.comparing(Timing::getOutTime).reversed());

			Timing timing = new Timing();
			timing.setInTime(LocalDateTime.now());
			timing.setOutTime(null);
			timing.setVisitor(visitor);
			timing.setEmployee(visitor.getTimings().get(0).getEmployee());
			timing.setVisitorType(visitor.getTimings().get(0).getVisitorType());
			timingRepository.save(timing);

		}
	}

	/**
	 * This method sort the visitor details based on the out time and get the latest
	 * entry details to fetch the employee details based on the employee id and
	 * retrieve the host details to send an intimation email for visitor
	 * 
	 * @param visitor : input visitor details
	 * @return : validation message
	 */
	private String sendEmailToHost(Visitor visitor) {
		Collections.sort(visitor.getTimings(), Comparator.comparing(Timing::getOutTime).reversed());
		Employee employee = visitor.getTimings().get(0).getEmployee();
		if (!Objects.isNull(employee)) {
			prepareAndSendEmailForHost(visitor, employee);
			return Constants.HOST_EMAIL_SENT_MESSAGE;
		} else
			throw new VisitorManagementException(Constants.HOST_NOT_FOUND_MESSAGE);
	}

	/**
	 * This method fetches the employee details from employee table based on the
	 * employee id and retrieve the host details to send an intimation email for
	 * visitor
	 * 
	 * @param visitor  : input visitor details
	 * @param employee : Employee object to fetch host details
	 * @return : validation message
	 */
	private void prepareAndSendEmailForHost(Visitor visitor, Employee employee) {
		try {
			EmailDTO emailDTO = new EmailDTO();

			Map<String, Object> model = new HashMap<String, Object>();
			model.put(Constants.VISITOR_NAME, visitor.getFullName());
			model.put(Constants.EMPLOYEE_NAME, employee.getFirstName());

			emailDTO.setProperties(model);

			List<String> recipients = new ArrayList<>();
			recipients.add(employee.getEmail());
			emailDTO.setRecipients(recipients);
			emailDTO.setSubject(hostEmailSubject);
			emailDTO.setTemplateName(EmailTemplates.HOST_INTIMATION.getTemplateName());

			emailService.sendEmail(emailDTO);
		} catch (Exception e) {
			throw new VisitorManagementException(
					"Exception Occurred while preparing the temple to send an email for Host");
		}
	}

}