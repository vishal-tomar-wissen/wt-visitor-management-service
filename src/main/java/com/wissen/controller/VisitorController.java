package com.wissen.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.wissen.constants.Constants;
import com.wissen.dto.FilterRequest;
import com.wissen.dto.VisitorDto;
import com.wissen.entity.Visitor;
import com.wissen.exceptions.VisitorManagementException;
import com.wissen.model.response.VisitorManagementResponse;
import com.wissen.service.VisitorService;
import com.wissen.util.ResponseUtil;
import com.wissen.validation.VisitorValidation;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller Class for visitor.
 *
 * @author Vishal Tomar
 */
@RestController
@Slf4j
@RequestMapping("/api/visitor")
public class VisitorController {

	@Autowired
	private VisitorService visitorService;

	@Autowired
	private VisitorValidation visitorValidation;

	/**
	 * Saving visitor details.
	 *
	 * @param visitorDto
	 * @return
	 */
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "API to save visitor details", nickname = "Save Visitor")
	public VisitorManagementResponse saveVisitorDetails(@RequestBody @Valid VisitorDto visitorDto) {
		try {
			log.info("Saving visitors details");
			// validation
			List<Visitor> matchedVisitors = this.visitorService.getVisitorsByPhoneNoOrEmail(visitorDto.getPhoneNumber(),
					visitorDto.getEmail());
			this.visitorValidation.validateVisitor(matchedVisitors, visitorDto);

			Visitor savedData = this.visitorService.saveVisitorDetails(visitorDto);
			return ResponseUtil.getResponse(savedData);
		} catch (VisitorManagementException e) {
			log.error(Constants.EXCEPTION_LOG_PREFIX, e.getMessage());
			return ResponseUtil.getResponse(e.getMessage(), "Visitor Details", e);
		} catch (Exception e) {
			log.error(Constants.EXCEPTION_LOG_PREFIX, e.getMessage());
			return ResponseUtil.getResponse("Not able to save the visitor details.", "Visitor Details", e);
		}
	}

	/**
	 * Method to fetch values from the Visitor table Dynamic Query will be formed
	 * based on the request filter If the list is empty then all visitor data will
	 * be fetched Else will return only specific results
	 * 
	 * @param requestFilters
	 * @return
	 */
	@PostMapping("/genericFetch")
	@ApiOperation(value = "API to get visitors details", nickname = "getVisitorsDetails")
	public VisitorManagementResponse fetchVisitorsDetails(
			@RequestBody(required = false) List<FilterRequest> requestFilters) {
		try {
			log.info("Getting visitors details");
			List<Visitor> visitors = this.visitorService.fetchVisitorsDetails(requestFilters);
			return ResponseUtil.getResponse(visitors);
		} catch (Exception e) {
			log.error(Constants.EXCEPTION_LOG_PREFIX, e.getMessage());
			return ResponseUtil.getResponse(e.getMessage(), "Visitors details", e);
		}
	}

	@PostMapping("/fetch")
	@ApiOperation(value = "API to get visitors details", nickname = "getVisitorsDetails")
	public VisitorManagementResponse getVisitorsDetails(
			@RequestBody(required = false) List<FilterRequest> requestFilters) {
		try {
			// TODO try using generic way- add join if in/out time is passed as part of
			// filter
			log.info("Getting visitors details");

			// validating
			this.visitorValidation.validateFetchRequest(requestFilters);

			List<Visitor> visitors = this.visitorService.getVisitorByTypeNameOrTiming(requestFilters);
			return ResponseUtil.getResponse(visitors);
		} catch (Exception e) {
			log.error(Constants.EXCEPTION_LOG_PREFIX, e.getMessage());
			return ResponseUtil.getResponse(e.getMessage(), "Visitors details", e);
		}
	}

	@GetMapping("/getOTP")
	@ApiOperation(value = "API to get OTP", nickname = "getOTP")
	public VisitorManagementResponse sendOTP(
			@Valid @NotBlank(message = "Email id or Mobile Number should not be blank") @RequestParam(required = false) String phEmail) {

		try {

			log.info("Generating OTP for login");

			return ResponseUtil.getResponse(visitorService.getOTP(phEmail));
		} catch (Exception e) {
			log.error(Constants.EXCEPTION_LOG_PREFIX, e.getMessage());
			return ResponseUtil.getResponse(e.getMessage(), "OTP details", e);
		}
	}

	@PostMapping("/verifyOTP")
	public VisitorManagementResponse verifyOTP(@RequestBody(required = false) String data) {

		try {

			log.info("Verifying OTP");

			@SuppressWarnings("unchecked")
			Map<String, String> jsonJavaRootObject = new Gson().fromJson(data, Map.class);

			String emailId = jsonJavaRootObject.get("phEmail");
			String otp = jsonJavaRootObject.get("otp");

			return ResponseUtil.getResponse(visitorService.verifyOTP(emailId, otp));
		} catch (Exception e) {
			log.error(Constants.EXCEPTION_LOG_PREFIX, e.getMessage());
			return ResponseUtil.getResponse(e.getMessage(), "Verify OTP details", e);
		}
	}

}
