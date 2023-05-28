package com.wissen.config;

import com.wissen.constants.Constants;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class to get application properties.
 *
 * @author Vishal Tomar
 */
@Component
@Data
public class ApplicationPropertiesConfig {

    @Value(Constants.EMPLOYEE_MANAGEMENT_BASE_URL_PROPERTIES)
    private String employeeManagementBaseUrl;

    @Value(Constants.EMPLOYEE_MANAGEMENT_GET_POINT_OF_CONTACT_URL_PROPERTIES)
    private String getPointOfContactUrl;

    @Value(Constants.EMPLOYEE_MANAGEMENT_SEARCG_POINT_OF_CONTACT_URL_PROPERTIES)
    private String searchPointOfContactUrl;

    @Value(Constants.IS_EMPLOYEE_MANAGEMENT_ENABLE_PROPERTIES)
    private boolean isEmployeeManagementEnable;
}
