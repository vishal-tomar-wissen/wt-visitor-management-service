package com.wissen.dao;

import com.wissen.model.VisitorPointOfContactDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Dao interface class for employee table.
 *
 * @author Vishal Tomar
 */
public interface EmployeeDao {

    public List<VisitorPointOfContactDetail> searchVisitorPointOfContact(String search);

}
