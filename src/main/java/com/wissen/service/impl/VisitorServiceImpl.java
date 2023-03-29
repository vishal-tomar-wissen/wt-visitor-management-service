package com.wissen.service.impl;

import com.wissen.dto.VisitorDto;
import com.wissen.entity.Visitor;
import com.wissen.repository.VisitorRepository;
import com.wissen.service.VisitorService;
import com.wissen.util.VisitorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Objects;

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
     * @param visitorDto
     * @return savedVisitor
     */
    @Override
    public Visitor saveVisitorDetails(VisitorDto visitorDto) throws UnsupportedEncodingException {
        Visitor visitor = VisitorUtils.getVisitorEntity(visitorDto);
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
