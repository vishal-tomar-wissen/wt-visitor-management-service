package com.wissen.decorator;

import com.wissen.entity.Visitor;
import com.wissen.util.VisitorManagementUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

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
            String visitorId = UuidUtil.getTimeBasedUuid().toString();
            visitor.setVisitorId(visitorId);
        }

        //decorating visitor details before saving
        visitor.getTimings().stream().forEach(timing -> {
            timing.setInTime(LocalDateTime.now());
            timing.setOutTime(null);
            timing.setVisitor(visitor);
        });

        // setting image to save
        visitor.setVisitorImage(VisitorManagementUtils.convertBase64ToByte(visitor.getVisitorImageBase64()));
        visitor.setIdProofImage(StringUtils.isNotBlank(visitor.getIdProofImageBase64()) ?
                VisitorManagementUtils.convertBase64ToByte(visitor.getIdProofImageBase64()) : null);

    }

    /**
     * Decorate saved data after saving.
     *
     * @param visitor
     */
    public void decorateAfterSaving(Visitor visitor) {
        visitor.setIdProofImageBase64(Objects.nonNull(visitor.getIdProofImage()) ?
                VisitorManagementUtils.convertByteToBase64(visitor.getIdProofImage()) : null);
        visitor.setVisitorImageBase64(VisitorManagementUtils.convertByteToBase64(visitor.getVisitorImage()));
    }

}
