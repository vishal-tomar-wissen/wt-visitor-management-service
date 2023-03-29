package com.wissen.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.wissen.model.Error;
import com.wissen.model.response.VisitorManagementResponse;
import com.wissen.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ParamValidationExceptionHandler {

    @ExceptionHandler({InvalidDefinitionException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public VisitorManagementResponse handleInvalidArgumentException(MethodArgumentNotValidException ex) {
        final List<Error> errors= ex.getBindingResult().getFieldErrors().stream().map(e -> Error
                .builder()
                .errorMessage(e.getDefaultMessage())
                .field(e.getField())
                .build()).collect(Collectors.toList());
        return ResponseUtil.getResponse(errors);
    }
}
