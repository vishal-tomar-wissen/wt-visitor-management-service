package com.wissen.repository;

import com.wissen.entity.Timing;
import com.wissen.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Timing Repository.
 */
@Repository
public interface TimingRepository extends JpaRepository<Timing, Long> {

    /**
     * Find by visitor(vistiorId);
     *
     * @param visitor
     * @return timings
     */
    List<Timing> findByVisitorAndOutTime(Visitor visitor, LocalDateTime localDateTime);

    /**
     * Method to update out time where it is null.
     * @param outTime
     * @return
     */
    @Query("UPDATE Timing t set t.outTime = :outTime WHERE t.outTime = null")
    List<Timing> updateOutTimeWhereNull(LocalDateTime outTime);
}
