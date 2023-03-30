package com.wissen.service.impl;

import com.wissen.constants.Constants;
import com.wissen.dto.FilterRequest;
import com.wissen.dto.VisitorFilterDto;
import com.wissen.enrich.FilterSpecification;
import com.wissen.entity.Visitor;
import com.wissen.exceptions.VisitorManagementException;
import com.wissen.repository.VisitorEntityManagerRepository;
import com.wissen.repository.VisitorRepository;
import com.wissen.service.VisitorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


/**
 * Implementation class for visitor service.
 *
 * @author Vishal Tomar
 */
@Service
@Slf4j
public class VisitorServiceImpl implements VisitorService {

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private VisitorEntityManagerRepository visitorEntityManagerRepository;

    /**
     * Filter Specification is used to enrich the input request
     * This class will form dynamic queries
     * No dao layer is been called in this class, just a transformer
     */
    @Autowired
    FilterSpecification<Visitor> filterSpecification;

    /**
     * Save visitor details.
     *
     * @param visitor
     * @return savedVisitor
     */
    @Override
    public Visitor saveVisitorDetails(Visitor visitor) {
        visitor.setId(UuidUtil.getTimeBasedUuid().toString());
        visitor.setInTime(LocalDateTime.now());
        return this.visitorRepository.save(visitor);
    }

    /**
     * Method to update out time.
     *
     * @param id
     * @return visitor
     */
    @Override
    public Visitor logOut(String id) {
        Visitor visitor = this.visitorRepository.findById(id).get();
        visitor.setOutTime(LocalDateTime.now());
        return visitorRepository.save(visitor);
    }

    /**
     * Method to get visitors details.
     *
     * @param visitorFilterDto
     * @return visitors
     */
    @Override
    public List<Visitor> getVisitorsDetails(VisitorFilterDto visitorFilterDto) {
        if(Objects.isNull(visitorFilterDto.getFromInTime()) && Objects.nonNull(visitorFilterDto.getToInTime())) {
            throw new VisitorManagementException(Constants.FILTER_ERROR);
        } else if (Objects.nonNull(visitorFilterDto.getFromInTime()) && Objects.isNull(visitorFilterDto.getToInTime())) {
            throw new VisitorManagementException(Constants.FILTER_ERROR);
        }  else {
            return this.visitorEntityManagerRepository.findVisitorDetailsByFilter(visitorFilterDto);
        }
    }

    /**
     * Method to fetch values from the Visitor table
     * Dynamic Query will be formed based on the request filter
     * If the list is empty then all vistor will be fetched
     * Else will return only specific results     *
     * @param requestFilters
     * @return List of visitor response based on the filter request
     */
    @Override
    public List<Visitor> fetchVisitorsDetails(List<FilterRequest> requestFilters) {
        if (CollectionUtils.isEmpty(requestFilters)){
            return visitorRepository.findAll();
        }else {
            Specification<Visitor> specificationRequest = filterSpecification.getSpecificationFromFilters(requestFilters);
            return visitorRepository.findAll(specificationRequest);
        }
    }


}
