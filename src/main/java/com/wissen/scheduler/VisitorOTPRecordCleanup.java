package com.wissen.scheduler;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wissen.repository.OTPRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * This class deletes the record from visitor_otp table if expiry time is
 * exceeds current time. It is scheduled at every second.
 * 
 * @author Ankit Garg
 *
 */
@Component
@Slf4j
public class VisitorOTPRecordCleanup {

	@Autowired
	OTPRepository otpRepository;

	/**
	 * This method deletes the record from visitor_otp table if expiry time is
	 * exceeds current time. Scheduled at every second.
	 */
	@Transactional
	@Scheduled(fixedDelay = 60000) // Run every minute
	public void deleteExpiredRecords() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		int val = otpRepository.deleteByExpiryBefore(currentDateTime);
		if (val == 1)
			log.info("Record delete successfully");
	}
}
