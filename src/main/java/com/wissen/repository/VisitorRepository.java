package com.wissen.repository;

import com.wissen.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository class for visitor entity.
 *
 * @author Vishal Tomar
 */
@Repository
public interface VisitorRepository extends JpaRepository<Visitor, String>, JpaSpecificationExecutor<Visitor> {

    /**
     * Method will fetch the visitor details whose missed updating out time
     * Method is invoked via scheduler
     *
     * @return List of visitor details without time
     */
    List<Visitor> findByOutTime(LocalDateTime outTime);
}
