package com.wissen.service.impl;

import com.wissen.entity.Timing;
import com.wissen.entity.Visitor;
import com.wissen.repository.TimingRepository;
import com.wissen.service.TimingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation class for timings.
 *
 * @author Vishal Tomar
 */
@Service
public class TimingServiceImpl implements TimingService {

    @Autowired
    private TimingRepository timingRepository;

    @Override
    public List<Timing> fetchTimingWhereOutIsNull() {
        return this.timingRepository.findByOutTime(null);
    }

    @Override
    public List<Timing> saveOrUpdateTimings(List<Timing> outDetails) {
        return this.timingRepository.saveAll(outDetails);
    }

    @Override
    public Timing logOut(Long id) {
        Timing timing = this.timingRepository.findById(id).get();
        timing.setOutTime(LocalDateTime.now());
        return timingRepository.save(timing);
    }
}
