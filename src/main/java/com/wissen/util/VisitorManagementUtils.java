package com.wissen.util;

import com.google.common.collect.Sets;
import com.wissen.constants.Constants;
import com.wissen.dto.FilterRequest;
import com.wissen.entity.Timing;
import com.wissen.exceptions.VisitorManagementException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class.
 *
 * @author Vishal Tomar
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VisitorManagementUtils {

    /**
     * Convert string to byte code for image.
     *
     * @param base64Image
     * @return byteCode
     */
    public static byte[] convertBase64ToByte(String base64Image) {
        return Base64.getDecoder().decode(base64Image.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Covert byte code to string.
     *
     * @param image
     * @return convertedImage
     */
    public static String convertByteToBase64(byte[] image) {
        return Base64.getEncoder().encodeToString(image);
    }

    /**
     * Method return fields which is allowed in get filter.
     *
     * @return fields
     */
    public static Set<String> getAllowedVisitorFilterField() {
        return Sets.newHashSet("visitorId", "fullName", "email", "phoneNumber", "idProofType",
                "idProofNumber", "tempCardNo");
    }

    /**
     * Method to check time type field.
     *
     * @return isTimeTypeField
     */
    public static boolean isTimeTypeField(String fieldName) {
        return Sets.newHashSet(Constants.IN_TIME_COLUMN, Constants.OUT_TIME_COLUMN).contains(fieldName);
    }

    /**
     * Method to check time type field.
     *
     * @return isTimeTypeField
     */
    public static LocalDateTime getTimeTypeTimingFieldValue(Timing timing, String fieldName) {
        switch (fieldName) {
            case Constants.IN_TIME_COLUMN:
                return timing.getInTime();
            case Constants.OUT_TIME_COLUMN:
                return timing.getOutTime();
            default:
                throw new VisitorManagementException(fieldName + "is not dateTime type.");
        }
    }

    /**
     * Method to check time type field.
     *
     * @return isTimeTypeField
     */
    public static String getStringTypeTimingFieldValue(Timing timing, String fieldName) {
        switch (fieldName) {
            case Constants.WISSEN_ID_KEY:
                return timing.getEmployee().getWissenId();
            case Constants.VISITOR_TYPE_COLUMN:
                return timing.getVisitorType();
            default:
                throw new VisitorManagementException(fieldName + " is not valid field or String type.");
        }
    }

    /**
     * Method return fields which is allowed in get filter.
     *
     * @return fields
     */
    public static Set<String> getAllowedTimingFilterField() {
        return Sets.newHashSet(Constants.IN_TIME_COLUMN, Constants.OUT_TIME_COLUMN,
                Constants.WISSEN_ID_KEY, Constants.VISITOR_TYPE_COLUMN);
    }

    /**
     * Method to convert the list of string to localDateTime list.
     *
     * @param values
     * @return
     */
    public static List<LocalDateTime> getLocalDateTimeValues(List<String> values) {
        return values.stream().map(value -> getLocalDateTimeValue(value)).collect(Collectors.toList());
    }

    /**
     * Method to convert the list of string to localDateTime list.
     *
     * @param value
     * @return
     */
    public static LocalDateTime getLocalDateTimeValue(String value) {
        return LocalDateTime.parse(value);
    }

    /**
     * Method filter out only timing table field filter request.
     * @param filterRequests
     * @return
     */
    public static List<FilterRequest> getTimingfilterRequests(List<FilterRequest> filterRequests) {
        return filterRequests.stream().filter(filterRequest ->
                VisitorManagementUtils.getAllowedTimingFilterField().contains(filterRequest.getFieldName()))
                .collect(Collectors.toList());
    }
}
