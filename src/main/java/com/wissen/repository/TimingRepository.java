package com.wissen.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wissen.entity.Timing;
import com.wissen.entity.Visitor;

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
	 * 
	 * @param outTime
	 * @return
	 */
	@Query("UPDATE Timing t set t.outTime = :outTime WHERE t.outTime = null")
	List<Timing> updateOutTimeWhereNull(LocalDateTime outTime);

	/**
	 * Find the visitor record based on visitorId
	 * 
	 * @param visitor
	 * @return
	 */
	Timing findByVisitor(Visitor visitor);

}
