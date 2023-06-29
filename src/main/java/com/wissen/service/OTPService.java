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
	String sendOTP(String phEmail);

	/**
	 * Method to verify the OTP for login.
	 * 
	 * @param data
	 * @return : Success or Failure Message
	 */
	String verifyOTP(OtpDTO data);

}
