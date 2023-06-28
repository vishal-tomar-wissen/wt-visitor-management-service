package com.wissen.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wissen.entity.OTP;
import com.wissen.entity.Visitor;

/**
 * Repository class for visitor_otp table.
 * 
 * @author Ankit Garg
 *
 */
@Repository
public interface OTPRepository extends JpaRepository<OTP, Integer> {

	OTP findByVisitor(Visitor visitor);

	OTP findByVisitorAndExpiryAfter(Visitor visitor, LocalDateTime currentTime);

	@Query("SELECT v FROM OTP v WHERE v.visitor.email = :identifier OR v.visitor.phoneNumber = :identifier")
	OTP findByVisitorEmailOrPhoneNumber(@Param("identifier") String identifier);
}
