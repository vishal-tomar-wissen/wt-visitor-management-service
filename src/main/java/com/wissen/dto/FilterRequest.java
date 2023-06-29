package com.wissen.dto;

import com.wissen.constants.enums.DataType;
import com.wissen.constants.enums.Operator;
import lombok.Builder;
import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Used to create dynamic request from the input user
 * @author User - Sreenath Sampangi
 * @created 30/03/2023 - 20:47
 * @project wt-visitor-management-service
 */
@Data
@Builder
public class FilterRequest {

    @Required
    String fieldName;

    @Required
    Operator operator;

    @Required
    @NotEmpty
    List<String> values;

    @Required
    @NotNull
    DataType dataType;
}
