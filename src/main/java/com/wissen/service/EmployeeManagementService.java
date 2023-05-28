package com.wissen.service;

import com.wissen.model.VisitorPointOfContactDetail;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.util.List;

/**
 * Interface class handle operation for employee management tool.
 *
 * @author Vishal Tomar
 */
public interface EmployeeManagementService {

    /**
     * Method to get point of contact info by wissen id.
     *
     * @param wissenId
     * @return visitorPointOfContactDetail
     */
    public VisitorPointOfContactDetail getPointOfContactById(String wissenId) throws JSONException;

    /**
     * Method to search point of contact info by wissen id.
     *
     * @param search
     * @return visitorPointOfContactDetails
     */
    public List<VisitorPointOfContactDetail> searchPointOfContactById(String search) throws JSONException;
}
