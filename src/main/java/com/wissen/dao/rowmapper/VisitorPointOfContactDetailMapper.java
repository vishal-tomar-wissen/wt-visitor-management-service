package com.wissen.dao.rowmapper;

import com.wissen.model.VisitorPointOfContactDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * VisitorPointOfContactDetail mapper.
 *
 * @author Vishal Tomar
 */
public class VisitorPointOfContactDetailMapper implements RowMapper<VisitorPointOfContactDetail> {

    @Override
    public VisitorPointOfContactDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        return VisitorPointOfContactDetail.builder()
                .wissenId(rs.getString("wissen_id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .email(rs.getString("email"))
                .build();
    }
}
