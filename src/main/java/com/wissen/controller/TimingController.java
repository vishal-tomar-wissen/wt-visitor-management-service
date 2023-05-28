package com.wissen.controller;

import com.wissen.constants.Constants;
import com.wissen.entity.Timing;
import com.wissen.entity.Visitor;
import com.wissen.model.response.VisitorManagementResponse;
import com.wissen.service.TimingService;
import com.wissen.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
* Controller Class for timing.
*
* @author Vishal Tomar
*/
@RestController
@Slf4j
@RequestMapping("/api/visitor")
public class TimingController {

    @Autowired
    private TimingService timingService;

    /**
     * Method to update out time.
     *
     * @param id
     * @return timing
     */
    @PutMapping("/logout")
    @ApiOperation(value = "API to Update logout time", nickname = "logout")
    public VisitorManagementResponse logout(@RequestParam(required = true) Long id) {
        try {
            log.info("Updating logout time for id : {}", id);
            Timing timing = this.timingService.logOut(id);
            return ResponseUtil.getResponse(timing);
        }catch (Exception e) {
            log.error(Constants.EXCEPTION_LOG_PREFIX, e.getMessage());
            return ResponseUtil.getResponse("Not able to update log out time.", "Logout time", e);
        }
    }
}
