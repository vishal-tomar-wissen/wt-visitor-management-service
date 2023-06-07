package com.wissen.scheduler;

import com.wissen.entity.Timing;
import com.wissen.entity.Visitor;
import com.wissen.service.TimingService;
import com.wissen.service.VisitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@Configuration
@EnableScheduling
public class VisitorScheduler {

    @Autowired
    TimingService timingService;

    /**
     * Scheduler to update all the visitor details who missed providing the out details.
     * scheduler will run and update every day 12 am and close the out details
     */
    @Async
    @Scheduled(cron = "0 0 0 * * *", zone = "Indian/Maldives")
    public void scheduleClosingOutTime(){
        log.info("cron expression ran at {}", new Date());
        List<Timing> outDetails = timingService.updateOutTimeWhereNull();
        if(!CollectionUtils.isEmpty(outDetails)){
            outDetails.stream().forEach(timing -> timing.setOutTime(LocalDateTime.now()));
            List<Timing> updateVisitors = timingService.saveOrUpdateTimings(outDetails);
            log.info("Number of visitor timing details are updated are {} ",updateVisitors.size());
        }
        log.info("updated out time who missed to provide the out time");

    }
}
