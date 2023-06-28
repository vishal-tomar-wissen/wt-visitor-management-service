package com.wissen.service;

import com.wissen.dto.OtpDTO;

/**
 * Service for OTP details related operation.
 *
 * @author Ankit Garg
 */
public interface OTPService {

	/**
	 * Method to generate the OTP and send back to the visitor based on the mobile
	 * number or emailId
	 * 
	 * @param phEmail
	 * @return : validation or OTP generation success message
	 */
	public String sendOTP(String phEmail);

	/**
	 * Method to verify the OTP for login.
	 * 
	 * @param emailId
	 * @param otp
	 * @return : Success or Failure Message
	 */
	public String verifyOTP(OtpDTO data);

}
