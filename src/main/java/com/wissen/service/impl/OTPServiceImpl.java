package com.wissen.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wissen.constants.Constants;
import com.wissen.dto.OtpDTO;
import com.wissen.entity.Employee;
import com.wissen.entity.OTP;
import com.wissen.entity.Timing;
import com.wissen.entity.Visitor;
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

	@Override
	public String sendOTP(String phEmail) {
		String response = "";
		Visitor visitor = visitorRepository.findByEmailOrPhoneNumber(phEmail);
		if (visitor == null)
			return Constants.VISITOR_DOESNOT_EXISTS;

		List<Timing> timingList = visitor.getTimings();
		if (timingList.stream().filter(time -> Objects.isNull(time.getOutTime())).count() > 0)
			return Constants.VISITOR_IN_FIRM;
		OTP visitorOTPRecord = otpRepository.findByVisitorAndExpiryAfter(visitor, LocalDateTime.now());
		if (Objects.isNull(visitorOTPRecord)) {
			String otp = generateOTP();
			storeOTP(visitor, otp);
			emailService.sendOTPEmail(visitor.getFullName(), visitor.getEmail(), otp);
		} else
			emailService.sendOTPEmail(visitor.getFullName(), visitor.getEmail(), visitorOTPRecord.getOtp());

		response = Constants.OTP_GENERATION_MESSAGE;
		return response;
	}

	private void storeOTP(Visitor visitor, String otp) {
		OTP visitorOtp = new OTP();
		visitorOtp.setVisitor(visitor);
		visitorOtp.setOtp(otp);
		visitorOtp.setExpiry(LocalDateTime.now().plusMinutes(15));
		otpRepository.save(visitorOtp);
	}

	@Override
	public String verifyOTP(OtpDTO data) {
		String response = "";
		OTP visitorOTPRecord = otpRepository.findByVisitorEmailOrPhoneNumber(data.getPhEmail());
		if (visitorOTPRecord != null) {
			if (data.getOtp().equals(visitorOTPRecord.getOtp())) {
				Visitor visitor = visitorRepository.findByEmailOrPhoneNumber(data.getPhEmail());

				if (visitor != null) {
					setTimings(visitor);
					otpRepository.delete(visitorOTPRecord);
					response = Constants.VALID_OTP_MESSAGE + ". " + sendEmailToHost(visitor);
				} else
					response = Constants.VISITOR_NOT_FOUND_MESSAGE;

			} else
				response = Constants.INVALID_OTP_MESSAGE;
		} else
			response = Constants.NEW_OTP_MESSAGE;

		return response;
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
			emailService.sendHostEmail(visitor.getFullName(), employee.getFirstName(), employee.getEmail());
			return Constants.HOST_EMAIL_SENT_MESSAGE;
		} else
			return Constants.HOST_NOT_FOUND_MESSAGE;
	}

}
