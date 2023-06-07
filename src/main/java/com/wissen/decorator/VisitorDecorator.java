package com.wissen.decorator;

import com.wissen.dto.VisitorDto;
import com.wissen.entity.Timing;
import com.wissen.entity.Visitor;
import com.wissen.util.VisitorManagementUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Decorator class for visitor.
 *
 * @author Vishal Tomar
 */
@Component
public class VisitorDecorator {

    /**
     * Method to decorate visitor before saving to db.
     *
     * @param visitorDto
     */
    public Visitor decorateBeforeSaving(VisitorDto visitorDto) {

        Visitor visitor = new Visitor();
        //check the payload data is insert/update
        if (StringUtils.isEmpty(visitorDto.getVisitorId())) {
            //insert, since visitor id is empty
            visitor.setVisitorId(UuidUtil.getTimeBasedUuid().toString());
        }else{
            visitor.setVisitorId(visitorDto.getVisitorId());
        }
        visitor.setFullName(visitorDto.getFullName());
        visitor.setEmail(visitorDto.getEmail());
        visitor.setPhoneNumber(visitorDto.getPhoneNumber());
        visitor.setLocation(visitorDto.getLocation());
        visitor.setIdProofType(visitorDto.getIdProofType().name());
        visitor.setIdProofNumber(visitorDto.getIdProofNumber());
        visitor.setTempCardNo(visitorDto.getTempCardNo());
        // setting image to save
        visitor.setVisitorImage(VisitorManagementUtils.convertBase64ToByte(visitor.getVisitorImageBase64()));


        LocalDateTime now = LocalDateTime.now();
        //decorating visitor details before saving
        visitor.getTimings().stream().forEach(timing -> {
            timing.setId(null); //always insert
            timing.setInTime(now);
            timing.setOutTime(null);
            timing.setVisitor(visitor);
            timing.setEmployeeId(visitorDto.getEmployeeId());
            timing.setVisitorType(visitorDto.getVisitorType().name());
        });
        return visitor;

    }

    /**
     * Decorate saved data after saving.
     *
     * @param visitor
     */
    public void decorateAfterSaving(Visitor visitor, List<Timing> timings) {
        visitor.setVisitorImageBase64(VisitorManagementUtils.convertByteToBase64(visitor.getVisitorImage()));
    }

    /**
     * Decorate image for UI response.
     *
     * @param visitors
     */
    public void decorateImageForUi(List<Visitor> visitors) {
        visitors.forEach(visitor -> {
            visitor.setVisitorImageBase64(VisitorManagementUtils.convertByteToBase64(visitor.getVisitorImage()));
            visitor.setVisitorImage(null);
        });
    }

}
