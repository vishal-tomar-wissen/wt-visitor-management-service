package com.wissen.repository;

import com.wissen.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class for visitor entity.
 *
 * @author Vishal Tomar
 */
@Repository
public interface VisitorRepository extends JpaRepository<Visitor, String> {

}
