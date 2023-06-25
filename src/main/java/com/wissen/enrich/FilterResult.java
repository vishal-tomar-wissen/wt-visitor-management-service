package com.wissen.enrich;

import com.wissen.constants.enums.Operator;
import com.wissen.dto.FilterRequest;
import com.wissen.entity.Timing;
import com.wissen.entity.Visitor;
import com.wissen.exceptions.VisitorManagementException;
import com.wissen.util.VisitorManagementUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FilterResult {

    public static List<Visitor> filterExtraByFilterRequest(List<FilterRequest> filterRequests, List<Visitor> visitors) {

        List<FilterRequest> timingFilterRequests = VisitorManagementUtils.getTimingfilterRequests(filterRequests);

        for(Visitor visitor : visitors) {

            List<Timing> validValue = visitor.getTimings().stream()
                    .filter(timing -> isValidByFilterRequest(timingFilterRequests, timing)).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(validValue))
                visitor.setTimings(validValue);
        }
        return visitors;
    }

    public static boolean isValidByFilterRequest(List<FilterRequest> filterRequests, Timing timing) {

        boolean isInclude = true;
        for(FilterRequest filterRequest : filterRequests) {
            if(VisitorManagementUtils.isTimeTypeField(filterRequest.getFieldName()))
                isInclude = isIncludeByOperator(null, VisitorManagementUtils.getTimeTypeTimingFieldValue(timing,
                        filterRequest.getFieldName()), filterRequest);
            else
                isInclude = isIncludeByOperator(VisitorManagementUtils.getStringTypeTimingFieldValue(timing,
                        filterRequest.getFieldName()), null, filterRequest);
            if (!isInclude)
                return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }


    public static boolean isIncludeByOperator(String stringValue, LocalDateTime timeValue,
                                              FilterRequest filterRequest) {
        String fistValue = filterRequest.getValues().get(0);
        switch (filterRequest.getOperator()) {
            case IN:
                if(StringUtils.isNotBlank(stringValue))
                    Operator.isValueIn(filterRequest.getValues(), stringValue);
                else
                    Operator.isValueIn(VisitorManagementUtils.getLocalDateTimeValues(filterRequest.getValues()),
                            timeValue);
            case EQUALS:
                if(StringUtils.isNotBlank(stringValue))
                    return Operator.isValueEqual(fistValue, stringValue);
                else
                    return Operator.isValueEqual(VisitorManagementUtils.getLocalDateTimeValue(fistValue), timeValue);
            case NOT_EQ:
                if(StringUtils.isNotBlank(stringValue))
                    return Operator.isValueNotEqual(fistValue, stringValue);
                else
                    return Operator.isValueNotEqual(VisitorManagementUtils.getLocalDateTimeValue(fistValue), timeValue);
            case GREATER_THAN:
                return Operator.isGreaterThan(VisitorManagementUtils.getLocalDateTimeValue(fistValue), timeValue);
            case GREATER_THAN_EQUALS:
                return Operator.isGreaterThanOrEqual(VisitorManagementUtils.getLocalDateTimeValue(fistValue), timeValue);
            case LESS_THAN:
                return Operator.isLesserThan(VisitorManagementUtils.getLocalDateTimeValue(fistValue), timeValue);
            case LESS_THAN_EQUALS:
                return Operator.isLesserOrEqual(VisitorManagementUtils.getLocalDateTimeValue(fistValue), timeValue);
            case LIKE:
                return Operator.isValueLike(fistValue, stringValue);
            case BETWEEN:
                return Operator.isValueBetween(VisitorManagementUtils.getLocalDateTimeValue(fistValue),
                        VisitorManagementUtils.getLocalDateTimeValue(filterRequest.getValues().get(1)), timeValue);
            default:
                throw new VisitorManagementException("Please provide valid filter operator.");
        }
    }


}
