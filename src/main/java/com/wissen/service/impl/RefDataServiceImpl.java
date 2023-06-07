package com.wissen.service.impl;

import com.wissen.constants.enums.ProofType;
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
                .visitorsIdTypes(ProofType.getValues())
                .visitorsTypes(VisitorType.getValues())
                .build();
    }
}
