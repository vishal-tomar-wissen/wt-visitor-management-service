package com.wissen.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @NotBlank(message = "Name can not be blank.")
    @Column(nullable = false, length = 100)
    private String fullName;

    @NotBlank(message = "Email can not be blank.")
    @Column(nullable = false, length = 150)
    private String email;

    @NotBlank(message = "Phone number can not be blank.")
    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @NotBlank(message = "Point of contact can not be blank.")
    @Column(nullable = false, length = 100)
    private String pointOfContact;

    //TODO make it nullable
    @NotBlank(message = "Point of contact email can not be blank.")
    @Column(nullable = false, length = 150)
    private String pointOfContactEmail;

    @NotBlank(message = "Location can not be blank.")
    @Column(nullable = false, length = 255)
    private String location;

    //TODO make it mandatory
    @Column(nullable = true, length = 100)
    private String purposeOfVisit;

    //TODO mke it allow null
    @Column(nullable = true, length = 50)
    private String visitorType;

    @Column(nullable = true, length = 50)
    private String idProofType;

    //TODO make it mandatory
    @NotBlank(message = "Id proof number can not be blank.")
    @Column(nullable = false, length = 100)
    private String idProofNumber;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime inTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = true)
    private LocalDateTime outTime;

    @JsonIgnore
    @Lob
    @Column(nullable = false, length = 700000)
    private byte[] visitorImage;

    @JsonIgnore
    @Lob
    @Column(nullable = true, length = 700000)
    private byte[] idProofImage;

    //TODO make it mandatory so that UI sends always
    @Transient
    private String visitorImageBase64;

    @Transient
    private String idProofImageBase64;

}
