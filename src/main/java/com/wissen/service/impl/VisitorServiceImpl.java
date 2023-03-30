package com.wissen.service.impl;

import com.wissen.constants.Constants;
import com.wissen.dto.VisitorFilterDto;
import com.wissen.entity.Visitor;
import com.wissen.exceptions.VisitorManagementException;
import com.wissen.repository.VisitorEntityManagerRepository;
import com.wissen.repository.VisitorRepository;
import com.wissen.service.VisitorService;
import com.wissen.util.VisitorManagementUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * Save visitor details.
     *
     * @param visitor
     * @return savedVisitor
     */
    @Override
    public Visitor saveVisitorDetails(Visitor visitor) {

        if(StringUtils.isBlank(visitor.getVisitorImageBase64())) {
            throw new VisitorManagementException("Visitor image can not be empty.");
        }

        //decorating visitor details before saving
        visitor.setId(UuidUtil.getTimeBasedUuid().toString());
        visitor.setInTime(LocalDateTime.now());
        visitor.setOutTime(null);
        visitor.setVisitorImage(VisitorManagementUtils.convertBase64ToByte(visitor.getVisitorImageBase64()));
        visitor.setIdProofImage(StringUtils.isNotBlank(visitor.getIdProofImageBase64()) ?
                VisitorManagementUtils.convertBase64ToByte(visitor.getIdProofImageBase64()) : null);

        //decorating saved visitor details returning
        Visitor savedVisitor = this.visitorRepository.save(visitor);

        savedVisitor.setIdProofImageBase64(Objects.nonNull(visitor.getIdProofImage()) ?
                VisitorManagementUtils.convertByteToBase64(visitor.getIdProofImage()) : null);
        savedVisitor.setVisitorImageBase64(VisitorManagementUtils.convertByteToBase64(visitor.getVisitorImage()));

        savedVisitor.setVisitorImage(null);
        savedVisitor.setIdProofImage(null);

        return savedVisitor;
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
            List<Visitor> visitors =  this.visitorEntityManagerRepository.findVisitorDetailsByFilter(visitorFilterDto);

            // Decorating images for UI.
            visitors.forEach( visitor -> {
                visitor.setIdProofImageBase64(Objects.nonNull(visitor.getIdProofImage()) ?
                        VisitorManagementUtils.convertByteToBase64(visitor.getIdProofImage()) : null);
                visitor.setVisitorImageBase64(VisitorManagementUtils.convertByteToBase64(visitor.getVisitorImage()));

                visitor.setVisitorImage(null);
                visitor.setIdProofImage(null);
            });

            return visitors;
        }
    }
}
