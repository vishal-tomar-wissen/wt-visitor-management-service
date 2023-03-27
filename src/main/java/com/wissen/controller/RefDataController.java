package com.wissen.controller;

import com.wissen.constants.Constants;
import com.wissen.model.RefData;
import com.wissen.model.response.VisitorManagementResponse;
import com.wissen.service.RefDataService;
import com.wissen.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller Class for refdata.
 *
 * @author Vishal Tomar
 */
@RestController
@Slf4j
@RequestMapping("/api/refdata")
public class RefDataController {

    @Autowired
    private RefDataService refDataService;

    /**
     * API to get ref data.
     *
     * @return visitorManagementResponse
     */
    @GetMapping
    @ApiOperation(value = "Getting ref data", nickname = "getRefData")
    public VisitorManagementResponse getRefData() {
        log.info("START: Getting Ref data");
        try {
            RefData refData = this.refDataService.getRefData();
            log.info("END: Getting Ref data");
            return ResponseUtil.getResponse(refData);
        } catch (Exception e) {
            log.error(Constants.EXCEPTION_LOG_PREFIX, e.getMessage());
            return ResponseUtil.getResponse("Unable to get Ref data.", Constants.REF_DATA, e);
        }
    }

}
