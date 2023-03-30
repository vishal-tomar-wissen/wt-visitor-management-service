package com.wissen.repository;

import com.wissen.dto.VisitorFilterDto;
import com.wissen.entity.Visitor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Visitor repo to perform custom/dynamic operation.
 *
 * @author Vishal Tomar
 */
@Repository
public class VisitorEntityManagerRepository {

    @PersistenceContext
    EntityManager entityManager;

    public List<Visitor> findVisitorDetailsByFilter(VisitorFilterDto visitorFilterDto) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Visitor> cq = cb.createQuery(Visitor.class);

        Root<Visitor> visitor = cq.from(Visitor.class);
        List<Predicate> predicates = new ArrayList<>();

        if(StringUtils.isNotBlank(visitorFilterDto.getFullName())) {
            predicates.add(cb.equal(visitor.get("fullName"), visitorFilterDto.getFullName()));
        }

        if(StringUtils.isNotBlank(visitorFilterDto.getVisitorType())) {
            predicates.add(cb.equal(visitor.get("visitorType"), visitorFilterDto.getVisitorType()));
        }

        if(StringUtils.isNotBlank(visitorFilterDto.getPurposeOfVisit())) {
            predicates.add(cb.equal(visitor.get("purposeOfVisit"), visitorFilterDto.getPurposeOfVisit()));
        }

        if(Objects.nonNull(visitorFilterDto.getFromInTime()) && Objects.nonNull(visitorFilterDto.getToInTime())) {
            predicates.add(cb.between(visitor.get("inTime"), visitorFilterDto.getFromInTime(), visitorFilterDto.getToInTime()));
        }

        cq.where(predicates.toArray(Predicate[]::new));

        TypedQuery<Visitor> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
}
