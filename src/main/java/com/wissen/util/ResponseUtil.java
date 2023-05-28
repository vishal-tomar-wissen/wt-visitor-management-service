package com.wissen.util;

import com.google.common.collect.Lists;
import com.wissen.constants.Constants;
import com.wissen.constants.enums.ResponseStatus;
import com.wissen.model.Error;
import com.wissen.model.VisitorPointOfContactDetail;
import com.wissen.model.response.VisitorManagementResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.ArrayList;
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

    /**
     * Method to fetch data from employeeManagement response.
     */
    public static List<VisitorPointOfContactDetail> getEmployeeManagementDataArray(String response) throws JSONException {
        JSONObject root = new JSONObject(response);
        JSONObject responseData = root.optJSONObject(Constants.RESPONSE_DATA_KEY);
        JSONArray dataArray =  responseData.optJSONArray(Constants.DATA_KEY);
        List<VisitorPointOfContactDetail> lst = new ArrayList<>();
        for (int i=0; i< dataArray.length(); i++) {
            JSONObject data = dataArray.optJSONObject(i);
            lst.add(VisitorPointOfContactDetail.builder()
                    .wissenId(data.optString(Constants.WISSEN_ID_KEY))
                    .firstName(data.optString(Constants.FIRST_NAME_KEY))
                    .lastName(data.optString(Constants.LAST_NAME_KEY))
                    .email(data.optString(Constants.EMAIL_NAME_KEY))
                    .build());
        }
        return lst;
    }
}
