package com.wissen.model;

import lombok.Builder;
import lombok.Data;

/**
 * Model class for error.
 *
 * @author Vishal Tomar
 */
@Data
@Builder
public class Error {

    private String errorMessage;
    private String field;
    private StackTraceElement[] stackTraces;
}
