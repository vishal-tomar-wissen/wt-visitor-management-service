package com.wissen.scheduler;


import com.wissen.repository.OTPRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

/**
 * Class to delete visitor otp post the expire of the time interval
 */
@Slf4j
@Configuration
public class DeleteOtpScheduler {

    @Autowired
    OTPRepository otpRepository;

    /**
     * <pre>
     * Method will be executed every 15 minutes based on the cron scheduler
     *
     * Cron takes 5 arguments namely
     *  ┌───────────── second (0-59)
     *  │ ┌───────────── minute (0 - 59)
     *  │ │ ┌───────────── hour (0 - 23)
     *  │ │ │ ┌───────────── day of the month (1 - 31)
     *  │ │ │ │ ┌───────────── month (1 - 12) (or JAN-DEC)
     *  │ │ │ │ │ ┌───────────── day of the week (0 - 7)
     *  │ │ │ │ │ │          (0 or 7 is Sunday, or MON-SUN)
     *  │ │ │ │ │ │
     *  * * * * * *
     * </pre>
     */
    @Async
    @Scheduled(cron = "0 */15 * ? * *")
    @Transactional
    public void scheduleClosingOutTime(){
        log.info("cron expression to delete visitor otp ran at {}", LocalDateTime.now());
        Integer deleteRecordsCount = otpRepository.deleteByExpiryLessThan(LocalDateTime.now());
        log.info("Number of records deleted after crossing time interval is {}",deleteRecordsCount);
    }
}
