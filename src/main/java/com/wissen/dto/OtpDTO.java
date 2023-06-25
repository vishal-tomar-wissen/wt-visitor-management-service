package com.wissen.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Class to contain the data for OTP verification and performs the validation
 * via annotations
 * 
 * @author Ankit Garg
 *
 */
public class OtpDTO {

	@NotBlank(message = "Email Id or Mobile number can not be blank.")
	private String phEmail;

	@NotBlank(message = "OTP can not be blank.")
	@Size(min = 4, max = 4, message = "OTP should have 4 characters")
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