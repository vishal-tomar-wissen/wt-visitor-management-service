package com.wissen.dto;

import com.wissen.constants.enums.OperatorOperator;
import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Used to create dynamic request from the input user
 * @author User - Sreenath Sampangi
 * @created 30/03/2023 - 20:47
 * @project wt-visitor-management-service
 */
@Data
public class FilterRequest {

    @Required
    String fieldName;

    @Required
    OperatorOperator operator;

    @Required
    @NotEmpty
    List<String> values;
}
