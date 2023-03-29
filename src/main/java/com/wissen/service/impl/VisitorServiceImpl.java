package com.wissen.service.impl;

import com.wissen.entity.Visitor;
import com.wissen.repository.VisitorRepository;
import com.wissen.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Implementation class for visitor service.
 *
 * @author Vishal Tomar
 */
@Service
public class VisitorServiceImpl implements VisitorService {

    @Autowired
    private VisitorRepository visitorRepository;

    /**
     * Save visitor details.
     *
     * @param visitor
     * @return savedVisitor
     */
    @Override
    public Visitor saveVisitorDetails(Visitor visitor) {
        return this.visitorRepository.save(visitor);
    }

    /**
     * Method to update out time.
     *
     * @param id
     * @return visitor
     */
    @Override
    public Visitor logOut(String id) {
        Visitor visitor = this.visitorRepository.findById(id).get();
        visitor.setOutTime(LocalDateTime.now());
        return visitorRepository.save(visitor);
    }
}
