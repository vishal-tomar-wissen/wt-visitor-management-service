package com.wissen.controller;

import com.wissen.constants.Constants;
import com.wissen.dto.VisitorDto;
import com.wissen.entity.Visitor;
import com.wissen.model.response.VisitorManagementResponse;
import com.wissen.service.VisitorService;
import com.wissen.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller Class for visitor.
 *
 * @author Vishal Tomar
 */
@RestController
@Slf4j
@RequestMapping("/api/visitor")
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    /**
     * Saving visitor details.
     *
     * @param visitorDto
     * @return
     */
    @PostMapping(consumes ={MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "API to save visitor details", nickname = "getRefData")
    public VisitorManagementResponse saveVisitorDetails(@RequestBody @Valid VisitorDto visitorDto) {
        try {
            Visitor visitor = this.visitorService.saveVisitorDetails(visitorDto);
            return ResponseUtil.getResponse(ResponseUtil.getSavedVisitorSavedDetails(visitor));
        }catch (Exception e) {
            log.error(Constants.EXCEPTION_LOG_PREFIX, e.getMessage());
            return ResponseUtil.getResponse("Not able to save visitor details", "Visitor Details", e);
        }
    }

    /**
     * Method to update out time.
     *
     * @param id
     * @return visitor
     */
    @PutMapping("/logout")
    @ApiOperation(value = "API to Update logout time", nickname = "logout")
    public VisitorManagementResponse logout(@RequestParam(required = true) String id) {
        try {
            Visitor visitor = this.visitorService.logOut(id);
            return ResponseUtil.getResponse(ResponseUtil.getSavedVisitorSavedDetails(visitor));
        }catch (Exception e) {
            log.error(Constants.EXCEPTION_LOG_PREFIX, e.getMessage());
            return ResponseUtil.getResponse("Not able to update log out time.", "Logout time", e);
        }
    }
}
