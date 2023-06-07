package com.wissen.repository;

import com.wissen.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
public interface VisitorRepository extends JpaRepository<Visitor, String>, JpaSpecificationExecutor<Visitor> {


    /**
     * Method to fetch visitors by phoneNumber and email.
     * @param phoneNumber
     * @param email
     * @return
     */
    List<Visitor> findByPhoneNumberOrEmail(String phoneNumber, String email);

}
