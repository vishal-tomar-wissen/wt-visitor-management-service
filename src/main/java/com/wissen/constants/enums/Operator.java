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
public enum OperatorOperator {
    GREATER_THAN,
    GREATER_THAN_EQUALS,
    LESS_THAN,
    LESS_THAN_EQUALS,
    EQUALS,
    LIKE,
    NOT_EQ,
    BETWEEN,
    IN;

    public boolean isValueIn(List<String> requiredValues, String value) {
        return requiredValues.contains(value);
    }

    public boolean isValueIn(List<LocalDateTime> requiredValues, LocalDateTime value) {
        return requiredValues.contains(value);
    }

    public boolean isValueEqual(String requiredValue, String value) {
        return StringUtils.equalsIgnoreCase(requiredValue, value);
    }

    public boolean isValueEqual(LocalDateTime requiredValue, LocalDateTime value) {
        return requiredValue.equals(value);
    }

    public boolean isValueNotEqual(String requiredValue, String value) {
        return !StringUtils.equalsIgnoreCase(requiredValue, value);
    }

    public boolean isValueNotEqual(LocalDateTime requiredValue, LocalDateTime value) {
        return !requiredValue.equals(value);
    }

    public boolean isGreaterThan(LocalDateTime requiredValue, LocalDateTime value) {
        return value.isAfter(requiredValue);
    }

    public boolean isGreaterThanOrEqual(LocalDateTime requiredValue, LocalDateTime value) {
        return value.isAfter(requiredValue) || isValueEqual(requiredValue, value);
    }

    public boolean isLesserThan(LocalDateTime requiredValue, LocalDateTime value) {
        return value.isBefore(requiredValue);
    }

    public boolean isLesserOrEqual(LocalDateTime requiredValue, LocalDateTime value) {
        return value.isBefore(requiredValue) || isValueEqual(requiredValue, value);
    }

    public boolean isValueBetween(LocalDateTime requiredValue, LocalDateTime value) {
        return isGreaterThanOrEqual(requiredValue, value) && isLesserOrEqual(requiredValue, value);
    }

}