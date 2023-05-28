package com.wissen.controller;

import com.wissen.constants.Constants;
import com.wissen.entity.Visitor;
import com.wissen.model.VisitorPointOfContactDetail;
import com.wissen.model.response.VisitorManagementResponse;
import com.wissen.service.EmployeeManagementService;
import com.wissen.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class to get details from employee management tool.
 *
 * @author Vishal Tomar
 */
@RestController
@Slf4j
@RequestMapping("/api/visitor/employee")
public class EmployeeManagementController {

    @Autowired
    private EmployeeManagementService employeeManagementService;

    @GetMapping("/getPointOfContact")
    public VisitorManagementResponse getVisitorPointOfContact(@RequestParam String wissenId) {
        try {
            VisitorPointOfContactDetail visitorPointOfContactDetail = employeeManagementService.getPointOfContactById(wissenId);
            return  ResponseUtil.getResponse(visitorPointOfContactDetail);
        }catch (Exception e) {
            log.error(Constants.EXCEPTION_LOG_PREFIX, e.getMessage());
            return ResponseUtil.getResponse(e.getMessage(), "Point of Contact", e);
        }
    }

    @GetMapping("/searchPointOfContact")
    public VisitorManagementResponse searchVisitorPointOfContact(@RequestParam String search) {
        try {
            List<VisitorPointOfContactDetail> visitorPointOfContactDetails = employeeManagementService.searchPointOfContactById(search);
            return  ResponseUtil.getResponse(visitorPointOfContactDetails);
        }catch (Exception e) {
            log.error(Constants.EXCEPTION_LOG_PREFIX, e.getMessage());
            return ResponseUtil.getResponse(e.getMessage(), "Point of Contact", e);
        }
    }

}
