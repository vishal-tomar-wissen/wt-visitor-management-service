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
import com.wissen.dto.OTPDTO;
import com.wissen.model.response.VisitorManagementResponse;
import com.wissen.repository.VisitorRepository;
import com.wissen.service.OTPService;
import com.wissen.util.ResponseUtil;
import com.wissen.util.VisitorManagementUtils;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller Class for OTP.
 *
 * @author Ankit Garg
 */
@RestController
@Slf4j
@RequestMapping("/api/visitor")
public class OTPController {

	@Autowired
	private OTPService otpService;

	@Autowired
	private VisitorRepository visitorRepository;

	/**
	 * This API generates the OTP based on input parameter either email id or mobile
	 * number it fetches the record from DB and then generate the OTP for login
	 * 
	 * @param phEmail : Mobile number or Email Id
	 * 
	 * @return
	 */
	@GetMapping("/sendOTP")
	@ApiOperation(value = "API to send OTP", nickname = "sendOTP")
	public VisitorManagementResponse validateAndSendOtp(
			@Valid @NotBlank(message = "Email id or Mobile Number should not be blank") @RequestParam(required = true) String phEmail) {

		try {

			log.info("Generating OTP for login");

			String response = "";

			if (!VisitorManagementUtils.validateEmailOrMobile(phEmail))
				response = "Please enter valid Email id or Mobile Number";

			else if (!visitorRepository.existsByEmailIdOrPhoneNumber(phEmail))
				response = "Visitor doesn't exists please register";
			else
				response = otpService.sendOTP(phEmail);

			return ResponseUtil.getResponse(response);
		} catch (Exception e) {
			log.error(Constants.EXCEPTION_LOG_PREFIX, e.getMessage());
			return ResponseUtil.getResponse(e.getMessage(), "OTP details", e);
		}
	}

	/**
	 * This API verifies the generated OTP corresponds to email id or mobile number
	 * 
	 * @param data : input json format with emailId or Mobile number & OTP
	 * @return
	 */
	@PostMapping("/verifyOTP")
	public VisitorManagementResponse verifyOTP(@RequestBody(required = false) OTPDTO data) {
		try {
			log.info("Verifying OTP");

			return ResponseUtil.getResponse(otpService.verifyOTP(data));
		} catch (Exception e) {
			log.error(Constants.EXCEPTION_LOG_PREFIX, e.getMessage());
			return ResponseUtil.getResponse(e.getMessage(), "Verify OTP details", e);
		}
	}

}
