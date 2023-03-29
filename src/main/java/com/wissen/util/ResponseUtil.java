package com.wissen.util;

import com.google.common.collect.Lists;
import com.wissen.constants.enums.ResponseStatus;
import com.wissen.entity.Visitor;
import com.wissen.model.Error;
import com.wissen.model.response.VisitorManagementResponse;
import com.wissen.model.response.VisitorResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;

/**
 * Utility class for response.
 *
 * @author Vishal Tomar
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseUtil {

    /**
     * Method to create success response.
     *
     * @param data
     * @return response
     */
    public static VisitorManagementResponse getResponse(Object data) {
        return VisitorManagementResponse.builder()
                .responseStatus(ResponseStatus.SUCCESS)
                .responseData(data)
                .build();
    }

    /**
     * Method to create error response.
     *
     * @param errorMessage
     * @param field
     * @return response
     */
    public static VisitorManagementResponse getResponse(String errorMessage, String field, Exception e) {
        return VisitorManagementResponse.builder()
                .responseStatus(ResponseStatus.FAILURE)
                .errors(Lists.newArrayList(Error.builder()
                        .errorMessage(errorMessage)
                        .field(field)
                        .stackTraces(e.getStackTrace())
                        .build()))
                .build();
    }

    /**
     * Method to create error response.
     *
     * @param errors
     * @return response
     */
    public static VisitorManagementResponse getResponse(List<Error> errors) {
        return VisitorManagementResponse.builder()
                .responseStatus(ResponseStatus.FAILURE)
                .errors(errors)
                .build();
    }

    /**
     * Method to get saved response of visitor.
     *
     * @param visitor
     * @return savedResponse
     */
    public static VisitorResponse getSavedVisitorSavedDetails(Visitor visitor) throws UnsupportedEncodingException {
        return VisitorResponse.builder()
                .id(visitor.getId())
                .fullName(visitor.getFullName())
                .email(visitor.getEmail())
                .phoneNumber(visitor.getPhoneNumber())
                .pointOfContact(visitor.getPointOfContact())
                .pointOfContactEmail(visitor.getPointOfContactEmail())
                .location(visitor.getLocation())
                .purposeOfVisit(visitor.getPurposeOfVisit())
                .visitorType(visitor.getVisitorType())
                .idProofType(visitor.getIdProofType())
                .idProofNumber(visitor.getIdProofNumber())
                .inTime(visitor.getInTime())
                .outTime(visitor.getOutTime())
                .visitorImage(VisitorUtils.byteToBase64(visitor.getVisitorImage()))
                .idProofImage(Objects.nonNull(visitor.getIdProofImage()) ?
                        VisitorUtils.byteToBase64(visitor.getIdProofImage()) : null)
                .build();
    }
}
