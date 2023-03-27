package com.wissen.service.impl;

import com.wissen.constants.enums.VisitorIDType;
import com.wissen.constants.enums.VisitorPurpose;
import com.wissen.constants.enums.VisitorType;
import com.wissen.model.RefData;
import com.wissen.service.RefDataService;
import org.springframework.stereotype.Service;

/**
 * Implementation class for ref data service.
 *
 * @author Vishal Tomar
 */
@Service
public class RefDataServiceImpl implements RefDataService {

    /**
     * @{inheritDoc}
     */
    @Override
    public RefData getRefData() {
        return RefData.builder()
                .visitorsIdTypes(VisitorIDType.getValues())
                .visitorsPurposes(VisitorPurpose.getValues())
                .visitorsTypes(VisitorType.getValues())
                .build();
    }
}
