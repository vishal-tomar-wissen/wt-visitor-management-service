package com.wissen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wissen.entity.Visitor;

/**
 * Repository class for visitor entity.
 *
 * @author Vishal Tomar
 */
@Repository
public interface VisitorRepository extends JpaRepository<Visitor, String>, JpaSpecificationExecutor<Visitor> {

	/**
	 * Method to fetch visitors by phoneNumber and email.
	 * 
	 * @param phoneNumber
	 * @param email
	 * @return
	 */
	List<Visitor> findByPhoneNumberOrEmail(String phoneNumber, String email);

	@Query("SELECT COUNT(v) > 0 FROM Visitor v WHERE v.email = :identifier OR v.phoneNumber = :identifier")
	boolean existsByEmailIdOrPhoneNumber(@Param("identifier") String identifier);

	/**
	 * Method to fetch the visitor by email
	 * 
	 * @param emailId
	 * @return
	 */
	Visitor findByEmail(String emailId);

}
