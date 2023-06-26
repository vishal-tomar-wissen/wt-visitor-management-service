package com.wissen.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constant class.
 *
 * @author Vishal Tomar
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static String REF_DATA = "RefData";
    public static String EXCEPTION_LOG_PREFIX = "Exception : {}";


    /**************** ERROR MESSAGE ************************/
    public static final String FILTER_ERROR = "Please provide appropriate filter or provide no filter to get all data.";

    /**************** Application Properties **************/
    public static final String EMPLOYEE_MANAGEMENT_BASE_URL_PROPERTIES = "${employee.management.base.url}";
    public static final String EMPLOYEE_MANAGEMENT_GET_POINT_OF_CONTACT_URL_PROPERTIES = "${employee.management.get.visitor.point.of.contact}";
    public static final String EMPLOYEE_MANAGEMENT_SEARCG_POINT_OF_CONTACT_URL_PROPERTIES = "${employee.management.search.visitor.point.of.contact}";
    public static final String IS_EMPLOYEE_MANAGEMENT_ENABLE_PROPERTIES = "${employee.management.enable}";

    /******************* JSON KEY FOR EMPLOYEE MANAGEMENT RESPONSE **********/
    public static final String RESPONSE_DATA_KEY = "responseData";
    public static final String DATA_KEY = "data";
    public static final String WISSEN_ID_KEY = "wissenId";
    public static final String FIRST_NAME_KEY = "firstName";
    public static final String LAST_NAME_KEY = "lastName";
    public static final String EMAIL_NAME_KEY = "email";

    /***************** Tables Name *****************/
    public static final String EMPLOYEE_TABLE = "employee";

    /********************** Time type column name ******************/
    public static final String IN_TIME_COLUMN = "inTime";
    public static final String OUT_TIME_COLUMN = "outTime";
    public static final String VISITOR_TYPE_COLUMN = "visitorType";
    public static final String WISSEN_ID = "wissenId";
}
