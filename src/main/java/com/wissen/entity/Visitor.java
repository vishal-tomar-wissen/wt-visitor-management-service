package com.wissen.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    @Column(nullable = false, length = 100)
    private String pointOfContact; // wissen id of point of contact

    @Column(nullable = true, length = 150)
    private String pointOfContactEmail;

    @NotBlank(message = "Location can not be blank.")
    @Column(nullable = false, length = 255)
    private String location;

    @NotBlank(message = "Please Specify Purpose of Visit")
    @Column(nullable = false, length = 100)
    private String purposeOfVisit;

    @Column(nullable = true, length = 50)
    private String visitorType;

    @NotBlank(message = "Please Specify Type of proof")
    @Column(nullable = false, length = 50)
    private String idProofType;

    @NotBlank(message = "Id proof number can not be blank.")
    @Column(nullable = false, length = 100)
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

    @JsonIgnore
    @Lob
    @Column(nullable = true, length = 700000)
    private byte[] idProofImage;
    
    @Transient
    private String visitorImageBase64;

    @Transient
    private String idProofImageBase64;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "visitor_id")
    public List<Timing> timings;

}
