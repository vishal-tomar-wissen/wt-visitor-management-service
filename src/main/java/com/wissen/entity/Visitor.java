package com.wissen.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity for visitor.
 *
 * @author Vishal Tomar
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "visitor")
public class Visitor {

    @Id
    @Column(nullable = false, length = 75)
    private String id;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @Column(nullable = false, length = 100)
    private String pointOfContact;

    @Column(nullable = false, length = 150)
    private String pointOfContactEmail;

    @Column(nullable = false, length = 255)
    private String location;

    @Column(nullable = true, length = 100)
    private String purposeOfVisit;

    @Column(nullable = true, length = 50)
    private String visitorType;

    @Column(nullable = true, length = 50)
    private String idProofType;

    @Column(nullable = false, length = 100)
    private String idProofNumber;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime inTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = true)
    private LocalDateTime outTime;

    @Lob
    @Column(nullable = false, length = 700000)
    private byte[] visitorImage;

    @Lob
    @Column(nullable = true, length = 700000)
    private byte[] idProofImage;
}
