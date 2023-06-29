package com.wissen.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.wissen.constants.Constants;
import com.wissen.constants.enums.DataType;
import com.wissen.constants.enums.Operator;
import com.wissen.decorator.VisitorDecorator;
import com.wissen.dto.FilterRequest;
import com.wissen.dto.VisitorDto;
import com.wissen.enrich.FilterResult;
import com.wissen.enrich.FilterSpecification;
import com.wissen.entity.Timing;
import com.wissen.entity.Visitor;
import com.wissen.exceptions.VisitorManagementException;
import com.wissen.repository.VisitorRepository;
import com.wissen.service.TimingService;
import com.wissen.service.VisitorService;
import com.wissen.util.VisitorManagementUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
     * @param visitorDto
     * @return savedVisitor
     */
    @Override
    @Transactional
    public Visitor saveVisitorDetails(VisitorDto visitorDto) {

        // decorating before saving
        Visitor visitor = visitorDecorator.decorateBeforeSaving(visitorDto);

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
    public List<Visitor> getVisitorsByPhoneNoOrEmail(String phNo, String email) {
        return this.visitorRepository.findByPhoneNumberOrEmail(phNo, email);
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public List<Visitor> getVisitorByFilter(List<FilterRequest> requestFilters) {

        List<Visitor> visitors = new ArrayList<>();

        if (CollectionUtils.isEmpty(requestFilters)) {
            // Will fetch last week data.
            requestFilters.add(FilterRequest.builder()
                    .dataType(DataType.DATE)
                    .fieldName(Constants.IN_TIME_COLUMN)
                    .operator(Operator.BETWEEN)
                    .values(Lists.newArrayList(VisitorManagementUtils.getLastWeekDate().toString(),
                            LocalDateTime.now().toString()))
                    .build());
        }

        Specification<Visitor> specificationRequest = filterSpecification.getSpecificationFromFilters(requestFilters);
        visitors.addAll(Sets.newHashSet(visitorRepository.findAll(specificationRequest)));
        visitors = FilterResult.filterExtraByFilterRequest(requestFilters, visitors);


        if(CollectionUtils.isNotEmpty(visitors)) {
            // Decorating images for UI.
            this.visitorDecorator.decorateImageForUi(visitors);
        }

        return Lists.newArrayList(visitors);
    }

}
