package com.wissen.constants.enums;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Used for dynamic search filter criteria for fetching the details
 * @author User - Sreenath Sampangi
 * @created 30/03/2023 - 20:56
 * @project wt-visitor-management-service
 */
public enum Operator {
    GREATER_THAN,
    GREATER_THAN_EQUALS,
    LESS_THAN,
    LESS_THAN_EQUALS,
    EQUALS,
    LIKE,
    NOT_EQ,
    BETWEEN,
    IN;

    public static boolean isValueIn(List<String> requiredValues, String value) {
        return requiredValues.contains(value);
    }

    public static boolean isValueIn(List<LocalDateTime> requiredValues, LocalDateTime value) {
        return requiredValues.contains(value);
    }

    public static boolean isValueEqual(String requiredValue, String value) {
        return StringUtils.equalsIgnoreCase(requiredValue, value);
    }

    public static boolean isValueEqual(LocalDateTime requiredValue, LocalDateTime value) {
        return requiredValue.equals(value);
    }

    public static boolean isValueNotEqual(String requiredValue, String value) {
        return !StringUtils.equalsIgnoreCase(requiredValue, value);
    }

    public static boolean isValueNotEqual(LocalDateTime requiredValue, LocalDateTime value) {
        return !requiredValue.equals(value);
    }

    public static boolean isGreaterThan(LocalDateTime requiredValue, LocalDateTime value) {
        return value.isAfter(requiredValue);
    }

    public static boolean isGreaterThanOrEqual(LocalDateTime requiredValue, LocalDateTime value) {
        return value.isAfter(requiredValue) || isValueEqual(requiredValue, value);
    }

    public static boolean isLesserThan(LocalDateTime requiredValue, LocalDateTime value) {
        return value.isBefore(requiredValue);
    }

    public static boolean isLesserOrEqual(LocalDateTime requiredValue, LocalDateTime value) {
        return value.isBefore(requiredValue) || isValueEqual(requiredValue, value);
    }

    public static boolean isValueBetween(LocalDateTime fromRequiredValue, LocalDateTime toRequiredValue, LocalDateTime value) {
        return isGreaterThanOrEqual(fromRequiredValue, value) && isLesserOrEqual(toRequiredValue, value);
    }

    public static boolean isValueLike(String requiredValue, String value) {

        if(StringUtils.startsWith(requiredValue, "%"))
            return StringUtils.startsWith(value, StringUtils.substring(requiredValue, 1));
        else if(StringUtils.endsWith(requiredValue, "%"))
            return StringUtils.startsWith(value, StringUtils.removeEnd(requiredValue, "%"));
        else {
            // example %abc%abc%abc OR abc
            String[] seperatedValues = StringUtils.split(requiredValue, value);

            for(String subStr : seperatedValues) {
                int startIndex = StringUtils.indexOf(value, subStr);
                if(startIndex == -1)
                    return false;

                int lastIndex = startIndex + subStr.length();
                value = StringUtils.substring(value, lastIndex);
            }
            return true;
        }
    }

}