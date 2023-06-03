package com.wissen.service;

import com.wissen.entity.Timing;
import com.wissen.entity.Visitor;

import java.util.List;

/**
 * Interface class for timing.
 *
 * @author Vishal Tomar
 */
public interface TimingService {

    /**
     * Fetch timing details where logout time is null.
     *
     * @return timings.
     */
    public List<Timing> fetchTimingWhereOutIsNull();

    /**
     * Method to save timing details.
     *
     * @param timings
     * @return timings
     */
    public List<Timing> saveOrUpdateTimings(List<Timing> timings);

    /**
     * Method to update out time.
     *
     * @param id
     * @return visitor
     */
    public List<Timing> logOut(Long id, String visitorId);
}

