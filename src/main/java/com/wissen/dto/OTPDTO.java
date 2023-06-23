package com.wissen.dto;

/**
 * Class to contain the data for OTP verification
 * 
 * @author Ankit Garg
 *
 */
public class OTPDTO {

	private String phEmail;
	private String otp;

	public String getPhEmail() {
		return phEmail;
	}

	public void setPhEmail(String phEmail) {
		this.phEmail = phEmail;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	@Override
	public String toString() {
		return "OTPDTO [phEmail=" + phEmail + ", otp=" + otp + "]";
	}

}