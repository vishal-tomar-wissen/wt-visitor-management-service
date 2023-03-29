package com.wissen.service;

import com.wissen.dto.VisitorDto;
import com.wissen.entity.Visitor;

import java.io.UnsupportedEncodingException;

/**
 * Service for visitor details related operation.
 *
 * @author Vishal Tomar
 */
public interface VisitorService {

    /**
     * Save visitor details.
     *
     * @param visitorDto
     * @return savedVisitor
     */
    public Visitor saveVisitorDetails(VisitorDto visitorDto) throws UnsupportedEncodingException;

    /**
     * Method to update out time.
     *
     * @param id
     * @return visitor
     */
    public Visitor logOut(String id);
}
