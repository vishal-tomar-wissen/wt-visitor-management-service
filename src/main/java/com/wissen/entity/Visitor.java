package com.wissen.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

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
    private String visitorId;

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
    @Column(nullable = true, length = 100)
    private String employeeId; // wissen id of point of contact

    @NotBlank(message = "Location can not be blank.")
    @Column(nullable = true, length = 255)
    private String location;

    @Column(nullable = false, length = 50)
    private String visitorPurpose;

    @NotBlank(message = "Please Specify Type of proof")
    @Column(nullable = true, length = 50)
    private String idProofType;

    @NotBlank(message = "Id proof number can not be blank.")
    @Column(nullable = true, length = 100)
    private String idProofNumber;

    /**
     * Field to set temporary card number to the visitor
     */
    @Column(nullable = true, length = 100)
    private String tempCardNo;

    @JsonIgnore
    @Lob
    @Column(nullable = false, length = 700000)
    private byte[] visitorImage;


    /***************** Transients  object **********************/

    @Transient
    private String visitorImageBase64;

    @Transient
    public List<Timing> timings;

}
