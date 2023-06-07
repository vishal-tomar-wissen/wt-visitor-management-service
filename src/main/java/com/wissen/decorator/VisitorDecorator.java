package com.wissen.decorator;

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
     * @param visitor
     */
    public void decorateBeforeSaving(Visitor visitor) {

        //check the payload data is insert/update
        if (StringUtils.isEmpty(visitor.getVisitorId())) {
            //insert, since visitor id is empty
            visitor.setVisitorId(UuidUtil.getTimeBasedUuid().toString());
        }

        LocalDateTime now = LocalDateTime.now();
        //decorating visitor details before saving
        visitor.getTimings().stream().forEach(timing -> {
            timing.setId(null); //always insert
            timing.setInTime(now);
            timing.setOutTime(null);
            timing.setVisitor(visitor);
        });

        // setting image to save
        visitor.setVisitorImage(VisitorManagementUtils.convertBase64ToByte(visitor.getVisitorImageBase64()));

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
