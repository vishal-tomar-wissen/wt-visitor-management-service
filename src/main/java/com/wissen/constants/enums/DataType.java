package com.wissen.constants.enums;

import com.google.common.collect.Lists;
import com.wissen.exceptions.VisitorManagementException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enum class for datatype.
 *
 * @author Vishal Tomar
 */
public enum DataType {
    INTEGER,
    LONG,
    DOUBLE,
    STRING,
    DATE,
    FLOAT;

    /**
     * Get the value on the basis of datatype.
     *
     * @param values
     * @param dataType
     * @return values
     */
    public static List<Object> getValue(List<String> values, DataType dataType) {

        switch (dataType) {
            case INTEGER :
                return values.stream()
                        .map(value -> Integer.valueOf(value)).collect(Collectors.toList());
            case LONG :
                return values.stream()
                        .map(value -> Long.valueOf(value)).collect(Collectors.toList());
            case DOUBLE :
                return values.stream()
                        .map(value -> Double.valueOf(value)).collect(Collectors.toList());
            case FLOAT :
                return values.stream()
                        .map(value -> Float.valueOf(value)).collect(Collectors.toList());
            case STRING :
                return values.stream()
                        .map(value -> String.valueOf(value)).collect(Collectors.toList());
            case DATE :
                return values.stream()
                        .map(value -> LocalDateTime.parse(String.valueOf(value), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .collect(Collectors.toList());
            default:
                throw new VisitorManagementException("Data type not supported yet");
        }
    }
}
