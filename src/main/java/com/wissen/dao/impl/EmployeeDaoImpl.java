package com.wissen.dao.impl;

import com.wissen.dao.EmployeeDao;
import com.wissen.dao.rowmapper.VisitorPointOfContactDetailMapper;
import com.wissen.model.VisitorPointOfContactDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Dao interface class for employee table.
 *
 * @author Vishal Tomar
 */
@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<VisitorPointOfContactDetail> searchVisitorPointOfContact(String search) {
        String likeClauseValue = "'%" + search + "%'";
        String sql = "SELECT * FROM employee WHERE first_name LIKE " + likeClauseValue + " OR last_name LIKE " + likeClauseValue + " OR " +
                "wissen_id LIKE " + likeClauseValue + " OR email LIKE " + likeClauseValue;
        List<VisitorPointOfContactDetail> visitorPointOfContactDetails = jdbcTemplate.query(sql,
                new VisitorPointOfContactDetailMapper());
        if(CollectionUtils.isEmpty(visitorPointOfContactDetails)) {
            return null;
        }
        return visitorPointOfContactDetails;
    }
}
