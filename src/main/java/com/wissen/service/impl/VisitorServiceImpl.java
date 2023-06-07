package com.wissen.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.wissen.decorator.VisitorDecorator;
import com.wissen.dto.FilterRequest;
import com.wissen.enrich.FilterSpecification;
import com.wissen.entity.Timing;
import com.wissen.entity.Visitor;
import com.wissen.exceptions.VisitorManagementException;
import com.wissen.repository.VisitorRepository;
import com.wissen.service.TimingService;
import com.wissen.service.VisitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

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
    private VisitorDecorator visitorDecorator;

    @Autowired
    private TimingService timingService;

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
    @Transactional
    public Visitor saveVisitorDetails(Visitor visitor) {

        // decorating before saving
        visitorDecorator.decorateBeforeSaving(visitor);

        // if user already checked in
        List<Timing> timings = this.timingService.findByVisitorAndOutTime(visitor, null);
        if(!CollectionUtils.isEmpty(timings))
            throw new VisitorManagementException("Visitor already checked in.");

        //save/update the details
        Visitor savedVisitor = this.visitorRepository.save(visitor);

        // decorating after saving
        visitorDecorator.decorateAfterSaving(savedVisitor, null);

        return savedVisitor;
    }

    /**
     * Method to fetch values from the Visitor table
     * Dynamic Query will be formed based on the request filter
     * If the list is empty then all visitor will be fetched
     * Else will return only specific results     *
     *
     * @param requestFilters
     * @return List of visitor response based on the filter request
     */
    @Override
    public List<Visitor> fetchVisitorsDetails(List<FilterRequest> requestFilters) {
        List<Visitor> visitors = new ArrayList<>();
        if (CollectionUtils.isEmpty(requestFilters)) {
            visitors.addAll(visitorRepository.findAll());
        } else {
            Specification<Visitor> specificationRequest = filterSpecification.getSpecificationFromFilters(requestFilters);
            visitors.addAll(visitorRepository.findAll(specificationRequest));
        }

        // Decorating images for UI.
        this.visitorDecorator.decorateImageForUi(visitors);

        return visitors;
    }


    /**
     * Method will save or update the details based on the details provided
     *
     * @param outDetails
     * @return List of saved or updated details
     */
    @Override
    public List<Visitor> saveOrUpdateVisitors(List<Visitor> outDetails) {
        return visitorRepository.saveAll(outDetails);
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public List<Visitor> getVisitorsByPhoneNoOrEmail(Visitor visitor) {
        return this.visitorRepository.findByPhoneNumberOrEmail(visitor.getPhoneNumber(), visitor.getEmail());
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public List<Visitor> getVisitorByTypeNameOrTiming(List<FilterRequest> requestFilters) {

        List<Visitor> visitors = new ArrayList<>();
        if (CollectionUtils.isEmpty(requestFilters)) {
            //fetch only 30days records pass in time in the request
            visitors.addAll(visitorRepository.findAll());
        } else {
            Specification<Visitor> specificationRequest = filterSpecification.getSpecificationByTypeNameOrTiming(requestFilters);
            visitors.addAll(Sets.newHashSet(visitorRepository.findAll(specificationRequest)));
        }

        // Decorating images for UI.
        this.visitorDecorator.decorateImageForUi(visitors);

        return Lists.newArrayList(visitors);
    }

}
