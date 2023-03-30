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
}
