package com.wissen.repository;

import com.wissen.entity.Timing;
import com.wissen.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Timing Repository.
 */
@Repository
public interface TimingRepository extends JpaRepository<Timing, Long> {

    /**
     * Method will fetch the visitor details whose missed updating out time
     * Method is invoked via scheduler
     *
     * @return List of timing details without time
     */
    List<Timing> findByOutTime(LocalDateTime outTime);

    /**
     * Find by visitor(vistiorId);
     *
     * @param visitor
     * @return timings
     */
    List<Timing> findByVisitorAndOutTime(Visitor visitor, LocalDateTime localDateTime);
}
