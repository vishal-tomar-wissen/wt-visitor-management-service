package com.wissen.enrich;

import com.wissen.constants.enums.DataType;
import com.wissen.dto.FilterRequest;
import com.wissen.entity.Timing;
import com.wissen.entity.Visitor;
import com.wissen.exceptions.VisitorManagementException;
import com.wissen.util.VisitorManagementUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to enrich the input request
 * Will form dynamic queries based on the filter request
 * @author User - Sreenath Sampangi
 * @created 30/03/2023 - 22:46
 * @project wt-visitor-management-service
 */
@Component
public class FilterSpecification<T> {

    final String PERCENTAGE = "%";

    /**
     * Method will create query using criteria builder and adds to predicate list
     * the predicate list is converted to Specification for supporting jpa repo
     * @param filter
     * @return  Specification of dynamic query formed
     */
    public Specification<T> getSpecificationFromFilters(List<FilterRequest> filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (FilterRequest input : filter) {
                updatePredicate(predicates, criteriaBuilder, root, input);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    /**
     * Based on the operation in the filter request the queries are formed
     * By default combing multiple filter request will be and clause
     * @param predicates
     * @param criteriaBuilder
     * @param root
     * @param input
     */
    private void updatePredicate(List<Predicate> predicates, CriteriaBuilder criteriaBuilder, Root<T> root, FilterRequest input) {
        String fieldName = input.getFieldName();
        List<Object> values = DataType.getValue(input.getValues(), input.getDataType());
        boolean isDateDataType = input.getDataType() == DataType.DATE;
        Object value = values.get(0);
        switch (input.getOperator()){
            case EQUALS:
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(fieldName), value)));
                return;
            case NOT_EQ:
                predicates.add(criteriaBuilder.and(criteriaBuilder.notEqual(root.get(fieldName), value)));
                return;
            case GREATER_THAN:
                 predicates.add(criteriaBuilder.and(criteriaBuilder.gt(root.get(fieldName), (Number) value)));
                return;
            case GREATER_THAN_EQUALS:
                predicates.add(criteriaBuilder.and(criteriaBuilder.ge(root.get(fieldName), (Number) value)));
                return;
            case LESS_THAN:
                predicates.add(criteriaBuilder.and(criteriaBuilder.lt(root.get(fieldName), (Number) value)));
                return;
            case LESS_THAN_EQUALS:
                if(isDateDataType)
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(fieldName), getDateTime(value))));
                else
                    predicates.add(criteriaBuilder.and(criteriaBuilder.le(root.get(fieldName), (Number) value)));
                return;
            case LIKE:
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get(fieldName), PERCENTAGE.concat((String) value).concat(PERCENTAGE))));
                return;
            case IN:
                Expression<String> parentExpression = root.get(fieldName);
                Predicate inPredicate = parentExpression.in(input.getValues());
                predicates.add(inPredicate);
                return;
            case BETWEEN:
                 handleBetweenOperator(predicates, values, input, criteriaBuilder, root, fieldName);
                return;
            default:
                throw new VisitorManagementException("Operation not supported yet");
        }
    }

    /**
     * Method to handle betweenOperator for different datatype.
     *
     * @param predicates
     * @param values
     * @param inputRequest
     * @param criteriaBuilder
     * @param root
     * @param fieldName
     */
    private void handleBetweenOperator(List<Predicate> predicates, List<Object> values, FilterRequest inputRequest,
                                       CriteriaBuilder criteriaBuilder, Root<T> root, String fieldName) {

        switch (inputRequest.getDataType()) {
            case DATE :
                predicates.add(criteriaBuilder.and(criteriaBuilder.between(root.get(fieldName),
                        getDateTime(values.get(0)), getDateTime(values.get(1)))));
                break;
            case INTEGER:
                predicates.add(criteriaBuilder.and(criteriaBuilder.between(root.get(fieldName), (Integer) values.get(0),
                        (Integer) values.get(1))));
                break;
            case FLOAT:
                predicates.add(criteriaBuilder.and(criteriaBuilder.between(root.get(fieldName), (Float) values.get(0),
                        (Float) values.get(1))));
                break;
            case DOUBLE:
                predicates.add(criteriaBuilder.and(criteriaBuilder.between(root.get(fieldName), (Double) values.get(0),
                        (Double) values.get(1))));
                break;
        }

    }

    public Specification<T> getSpecificationByTypeNameOrTiming(List<FilterRequest> filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<Visitor, Timing> join = root.join("timings");

            for (FilterRequest input : filter) {
                if(VisitorManagementUtils.getAllowedTimingFilterField().contains(input.getFieldName())) {
                    if(StringUtils.equals(input.getFieldName(), "inTime") || StringUtils.equals(input.getFieldName(), "outTime"))
                        setDateCriteria(input, join, criteriaBuilder, predicates);
                    else
                        predicates.add(criteriaBuilder.and(criteriaBuilder.equal(join.get(input.getFieldName()), input.getValues().get(0))));
                } else {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(input.getFieldName()), input.getValues().get(0))));
                }

            }

            Predicate criteriaBuilder1 = criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    /**
     * If only inTime and outTime both are given the we have to perform and operation between them.
     *
     * @param input
     * @param join
     * @param criteriaBuilder
     * @param predicates
     * @return isAnyDateCriteria
     */
    public boolean setDateCriteria(FilterRequest input, Join<Visitor, Timing> join,
                                   CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        predicates.add(criteriaBuilder.and(criteriaBuilder.between(join.get(input.getFieldName()),
                    getDateTime(input.getValues().get(0)), getDateTime(input.getValues().get(1)))));
        return true;
    }


    private LocalDateTime getDateTime(Object value) {
        return LocalDateTime.parse(value.toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
