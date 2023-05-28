package com.wissen.service.impl;

import com.wissen.config.ApplicationPropertiesConfig;
import com.wissen.dao.EmployeeDao;
import com.wissen.model.VisitorPointOfContactDetail;
import com.wissen.service.EmployeeManagementService;
import com.wissen.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

/**
 * Implement class for employeeManagementService.
 *
 * @author Vishal Tomar
 */
@Service
public class EmployeeManagementServiceImpl implements EmployeeManagementService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ApplicationPropertiesConfig applicationPropertiesConfig;

    @Autowired
    EmployeeDao employeeDao;

    @Override
    public VisitorPointOfContactDetail getPointOfContactById(String wissenId) throws JSONException {

        if(applicationPropertiesConfig.isEmployeeManagementEnable()) {
            String url = applicationPropertiesConfig.getEmployeeManagementBaseUrl()
                    + applicationPropertiesConfig.getGetPointOfContactUrl() + wissenId;
            List<VisitorPointOfContactDetail> result = getVisitorPointOfContactDetailsByUrl(url);
            if(Objects.nonNull(result) && result.size() > 0)
                return result.get(0);
            return null;
        } else {
            return employeeDao.getVisitorPointOfContact(wissenId);
        }
    }

    @Override
    public List<VisitorPointOfContactDetail> searchPointOfContactById(String search) throws JSONException {

        if(applicationPropertiesConfig.isEmployeeManagementEnable()) {
            String url = applicationPropertiesConfig.getEmployeeManagementBaseUrl()
                    + applicationPropertiesConfig.getSearchPointOfContactUrl() + search;
            List<VisitorPointOfContactDetail> result = getVisitorPointOfContactDetailsByUrl(url);
            if(Objects.nonNull(result) && result.size() > 0)
                return result;
            return null;
        } else {
            return employeeDao.searchVisitorPointOfContact(search);
        }
    }

    private List<VisitorPointOfContactDetail> getVisitorPointOfContactDetailsByUrl(String url) throws JSONException {
        ResponseEntity<String> response = restTemplate.getForEntity(url,
                String.class);
        return mapVisitorPointOfContact(response.getBody());
    }

    private List<VisitorPointOfContactDetail> mapVisitorPointOfContact(String response) throws JSONException {
        try {
            return ResponseUtil.getEmployeeManagementDataArray(response);
        } catch (Exception e) {
            return null;
        }
    }
}
