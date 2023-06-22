package com.wissen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
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

}
