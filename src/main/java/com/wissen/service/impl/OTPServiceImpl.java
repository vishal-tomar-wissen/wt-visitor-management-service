package com.wissen.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wissen.dto.OTPDTO;
import com.wissen.entity.OTP;
import com.wissen.entity.Timing;
import com.wissen.entity.Visitor;
import com.wissen.repository.OTPRepository;
import com.wissen.repository.VisitorRepository;
import com.wissen.service.OTPService;
import com.wissen.util.VisitorManagementUtils;

/**
 * Implementation class for visitor service.
 *
 * @author Vishal Tomar
 */
@Service
public class OTPServiceImpl implements OTPService {

	@Autowired
	private VisitorRepository visitorRepository;

	@Autowired
	private OTPRepository otpRepository;

	@Autowired
	private EmailService emailService;

	@Value("${email.subject}")
	String emailSubject;

	@Value("${email.body}")
	String emailBody;

	@Override
	public String sendOTP(String phEmail) {

		String response = "";

		Visitor visitor = visitorRepository.findByEmailOrPhoneNumber(phEmail);

		List<Timing> timingList = visitor.getTimings();

		LocalDateTime checkin = CollectionUtils.isEmpty(timingList) ? null : timingList.get(0).getInTime();
		LocalDateTime checkout = CollectionUtils.isEmpty(timingList) ? null : timingList.get(0).getOutTime();

		if (checkin != null && checkout == null)
			response = "Guest is already in the firm";
		else {

			OTP visitorOTPRecord = otpRepository.findByVisitor(visitor);

			if (visitorOTPRecord == null
					|| (visitorOTPRecord != null && !validateOTPExpiry(visitorOTPRecord.getExpiry()))) {

				String otp = generateOTP();

				storeOTP(visitor, otp);

				emailService.sendEmail(visitor.getFullName(), visitor.getEmail(), otp, emailSubject, emailBody);

			} else {
				emailService.sendEmail(visitor.getFullName(), visitor.getEmail(), visitorOTPRecord.getOtp(),
						emailSubject, emailBody);
			}

			response = "OTP sent to your registered Emial Id";
		}

		return response;
	}

	private void storeOTP(Visitor visitor, String otp) {

		OTP existingVisitorOtp = otpRepository.findByVisitor(visitor);

		if (existingVisitorOtp == null) {
			existingVisitorOtp = new OTP();
			existingVisitorOtp.setVisitor(visitor);
		}

		existingVisitorOtp.setOtp(otp);
		existingVisitorOtp.setExpiry(LocalDateTime.now().plusMinutes(15));
		otpRepository.save(existingVisitorOtp);
	}

	private boolean validateOTPExpiry(LocalDateTime time) {
		if (time.isAfter(LocalDateTime.now()) || time.isEqual(LocalDateTime.now()))
			return true;
		else
			return false;

	}

	@Override
	public String verifyOTP(OTPDTO data) {

		String response = "";

		String phEmail = data.getPhEmail();
		String otp = data.getOtp();

		if (StringUtils.isBlank(phEmail) && !VisitorManagementUtils.validateEmailId(phEmail))
			response = "Email id is not valid";

		Visitor visitor = visitorRepository.findByEmailOrPhoneNumber(phEmail);

		if (visitor != null) {

			OTP visitorOTPRecord = otpRepository.findByVisitor(visitor);

			if (visitorOTPRecord != null) {

				if (validateOTPExpiry(visitorOTPRecord.getExpiry())) {

					if (otp.equals(visitorOTPRecord.getOtp()))
						response = "OTP is valid";
					else
						response = "Invalid OTP";
				} else {
					response = "OTP Expired. Please generate new one";
				}
			}

		}

		return response;
	}

	/**
	 * method to generate random 6 digit OTP
	 * 
	 * @return
	 */
	private String generateOTP() {

		Random random = new Random();

		int otp = random.nextInt(999999);

		// this will convert any number sequence into 6 character.
		return String.format("%06d", otp);
	}

}
