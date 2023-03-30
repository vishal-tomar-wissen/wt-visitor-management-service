package com.wissen.enrich;

import com.wissen.dto.FilterRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
        String value = input.getValues().get(0);
        switch (input.getOperator()){
            case EQUALS:
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(fieldName), value)));
                return;
            case NOT_EQ:
                predicates.add(criteriaBuilder.and(criteriaBuilder.notEqual(root.get(fieldName), value)));
                return;
            case GREATER_THAN:
                predicates.add(criteriaBuilder.and(criteriaBuilder.gt(root.get(fieldName), Integer.valueOf(value))));
                return;
            case GREATER_THAN_EQUALS:
                predicates.add(criteriaBuilder.and(criteriaBuilder.ge(root.get(fieldName), Integer.valueOf(value))));
                return;
            case LESS_THAN:
                predicates.add(criteriaBuilder.and(criteriaBuilder.lt(root.get(fieldName), Integer.valueOf(value))));
                return;
            case LESS_THAN_EQUALS:
                predicates.add(criteriaBuilder.and(criteriaBuilder.le(root.get(fieldName), Integer.valueOf(value))));
                return;
            case LIKE:
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get(fieldName), PERCENTAGE.concat(value).concat(PERCENTAGE))));
                return;
            case IN:
                Expression<String> parentExpression = root.get(fieldName);
                Predicate inPredicate = parentExpression.in(input.getValues());
                predicates.add(inPredicate);
                return;
            case BETWEEN:
                predicates.add(criteriaBuilder.and(criteriaBuilder.between(root.get(fieldName), Integer.valueOf(value), Integer.valueOf(input.getValues().get(1)))));
                return;
            default:
                throw new RuntimeException("Operation not supported yet");
        }
    }


}
