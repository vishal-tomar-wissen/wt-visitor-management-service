package com.wissen.repository;

import com.wissen.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository class for visitor entity.
 *
 * @author Vishal Tomar
 */
@Repository
public interface VisitorRepository extends JpaRepository<Visitor, String> {

    @Query("SELECT v FROM Visitor v WHERE v.inTime >= :fromInTime and v.inTime <= :toInTime")
    List<Visitor> findVisitorDetailsByFilter(LocalDateTime fromInTime, LocalDateTime toInTime);
}
