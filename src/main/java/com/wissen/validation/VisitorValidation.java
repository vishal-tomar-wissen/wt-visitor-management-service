package com.wissen.validation;

import com.google.common.collect.Sets;
import com.wissen.dto.FilterRequest;
import com.wissen.dto.VisitorDto;
import com.wissen.entity.Visitor;
import com.wissen.exceptions.VisitorManagementException;
import com.wissen.util.VisitorManagementUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * Class to validate visitor.
 *
 * @author Vishal Tomar
 */
@Component
public class VisitorValidation {

    /**
     * If asked visitor detail is present in db and visitor id is null or visitorId doesn't match with id present in db,
     * then throw error.
     *
     * @param matchedVisitor
     * @param visitorDto
     */
    public void validateVisitor(List<Visitor> matchedVisitor, VisitorDto visitorDto) {

        // visitor details already present in DB.
        if(!CollectionUtils.isEmpty(matchedVisitor)) {

            // if visitor details already exist but UI asking to insert the record.
            if(StringUtils.isBlank(visitorDto.getVisitorId())) {
                throw new VisitorManagementException("Visitor details already exist, please use check-In");
            }
            else{
                if(!visitorDto.getVisitorId().equalsIgnoreCase(matchedVisitor.get(0).getVisitorId())){
                    throw new VisitorManagementException("Not a valid visitor, please check the details");
                }
            }

        }
    }

    public void validateFetchRequest(List<FilterRequest> requestFilters) {
        Set<String> validField = Sets.newHashSet("fullName", "visitorType", "inTime", "outTime");
        for (FilterRequest input : requestFilters) {
            if (!validField.contains(input.getFieldName()))
                throw new VisitorManagementException("Please provide correct filters.");
            else if((StringUtils.equals(input.getFieldName(), "inTime") || StringUtils.equals(input.getFieldName(), "outTime"))
                && input.getValues().size() != 2)
                throw new VisitorManagementException("Please provide correct filters.");

        }
    }

    public void validateGenericFetchRequest(List<FilterRequest> requestFilters) {
        for (FilterRequest input : requestFilters) {
            if (!VisitorManagementUtils.getAllowedVisitorFilterField().contains(input.getFieldName())
                    || !VisitorManagementUtils.getAllowedTimingFilterField().contains(input.getFieldName()))
                throw new VisitorManagementException("Please provide correct filters.");
            else if(StringUtils.equals(input.getFieldName(), "inTime") || StringUtils.equals(input.getFieldName(), "outTime")
                    && input.getValues().size() != 2)
                throw new VisitorManagementException("Please provide correct filters.");

        }
    }
}
