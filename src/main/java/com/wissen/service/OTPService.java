package com.wissen.service;

import com.wissen.dto.OTPDTO;

/**
 * Service for OTP details related operation.
 *
 * @author Ankit Garg
 */
public interface OTPService {

	/**
	 * Method to generate the OTP and send back to the visitor based n the mobile
	 * number or emailId
	 * 
	 * @param phEmail
	 * @return
	 */
	public String sendOTP(String phEmail);

	/**
	 * Method to verify the OTP for login.
	 * 
	 * @param emailId
	 * @param otp
	 * @return
	 */
	public String verifyOTP(OTPDTO data);

}
