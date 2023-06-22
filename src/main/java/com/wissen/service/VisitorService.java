package com.wissen.service;

import java.util.List;

import com.wissen.dto.FilterRequest;
import com.wissen.dto.VisitorDto;
import com.wissen.entity.Visitor;

/**
 * Service for visitor details related operation.
 *
 * @author Vishal Tomar
 */
public interface VisitorService {

	/**
	 * Save visitor details.
	 *
	 * @param visitorDto
	 * @return savedVisitor
	 */
	public Visitor saveVisitorDetails(VisitorDto visitorDto);

	/**
	 * Method to fetch values from the Visitor table Dynamic Query will be formed
	 * based on the request filter If the list is empty then all vistor will be
	 * fetched Else will return only specific results
	 * 
	 * @param requestFilters
	 * @return List of visitor response based on the filter request
	 */
	List<Visitor> fetchVisitorsDetails(List<FilterRequest> requestFilters);

	/**
	 * Method will save or update the details based on the details provided
	 * 
	 * @param outDetails
	 * @return List of saved or updated details
	 */
	List<Visitor> saveOrUpdateVisitors(List<Visitor> outDetails);

	/**
	 * Method to fetch visitors by phoneNumber and email.
	 * 
	 * @param phNo
	 * @param email
	 * @return
	 */
	List<Visitor> getVisitorsByPhoneNoOrEmail(String phNo, String email);

	/**
	 * Get result by visit of purpose, name, inTime and outTime.
	 *
	 * @param requestFilters
	 * @return result
	 */
	public List<Visitor> getVisitorByTypeNameOrTiming(List<FilterRequest> requestFilters);


	/**
	 * Method to generate the OTP and send back to the visitor based n the mobile
	 * number or emailId
	 * 
	 * @param phEmail
	 * @return
	 */
	public String getOTP(String phEmail);

	/**
	 * Method to verify the OTP for login.
	 * 
	 * @param emailId
	 * @param otp
	 * @return
	 */
	public String verifyOTP(String emailId, String otp);

}
