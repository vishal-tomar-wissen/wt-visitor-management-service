package com.wissen.service;

import com.wissen.dto.VisitorFilterDto;
import com.wissen.entity.Visitor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for visitor details related operation.
 *
 * @author Vishal Tomar
 */
public interface VisitorService {

    /**
     * Save visitor details.
     *
     * @param visitor
     * @return savedVisitor
     */
    public Visitor saveVisitorDetails(Visitor visitor);

    /**
     * Method to update out time.
     *
     * @param id
     * @return visitor
     */
    public Visitor logOut(String id);

    /**
     * Method to get visitors details..
     *
     * @param visitorFilterDto
     * @return visitors
     */
    public List<Visitor> getVisitorsDetails(VisitorFilterDto visitorFilterDto);
}
