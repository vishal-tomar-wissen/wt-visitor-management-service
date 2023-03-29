package com.wissen.util;

import com.google.common.collect.Lists;
import com.wissen.constants.enums.ResponseStatus;
import com.wissen.model.Error;
import com.wissen.model.response.VisitorManagementResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

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
}
