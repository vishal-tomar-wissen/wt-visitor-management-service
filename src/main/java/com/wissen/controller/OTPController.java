package com.wissen.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wissen.constants.Constants;
import com.wissen.dto.OtpDTO;
import com.wissen.model.response.VisitorManagementResponse;
import com.wissen.service.OTPService;
import com.wissen.util.ResponseUtil;
import com.wissen.util.VisitorManagementUtils;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller is used to send and verify OTP for existing visitors.
 *
 * @author Ankit Garg
 */
@RestController
@Slf4j
@RequestMapping("/api/visitor/exists")
public class OTPController {

	@Autowired
	private OTPService otpService;

	/**
	 * This API generates the OTP based on input parameter either email id or mobile
	 * number. it fetches the record from DB and then generate the OTP for visitor
	 * login
	 * 
	 * @param phEmail : Mobile number or Email Id
	 * 
	 * @return : Validation or OTP generation success message
	 */
	@GetMapping("/sendOTP")
	@ApiOperation(value = "API to send OTP", nickname = "sendOTP")
	public VisitorManagementResponse validateAndSendOtp(
			@Valid @NotBlank(message = Constants.BLANK_EMAIL_OR_MOBILE) @RequestParam(required = true) String phEmail) {
		try {
			log.info("Checks if visitor is valid or not");
			String response = "";
			if (!VisitorManagementUtils.validateEmailOrMobile(phEmail))
				response = Constants.VALID_EMAIL_OR_MOBILE;
			else
				response = otpService.sendOTP(phEmail);
			return ResponseUtil.getResponse(response);
		} catch (Exception e) {
			log.error(Constants.EXCEPTION_LOG_PREFIX, e.getMessage());
			return ResponseUtil.getResponse(e.getMessage(), "Generate OTP details", e);
		}
	}

	/**
	 * This API verifies the generated OTP corresponds to email id or mobile number
	 * 
	 * @param data : DTO class that contains emailId/Mobile number & OTP
	 * 
	 * @return : Success or failure Messages
	 */
	@PostMapping("/verifyOTP")
	public VisitorManagementResponse verifyOTP(@Valid @RequestBody(required = false) OtpDTO data) {
		try {
			log.info("Verifying generated OTP");

			return ResponseUtil.getResponse(otpService.verifyOTP(data));
		} catch (Exception e) {
			log.error(Constants.EXCEPTION_LOG_PREFIX, e.getMessage());
			return ResponseUtil.getResponse(e.getMessage(), "Verify OTP details", e);
		}
	}

}
