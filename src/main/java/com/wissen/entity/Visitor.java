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
@EqualsAndHashCode
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

//    @Basic(fetch=FetchType.EAGER)
//    @Column(nullable = false, length = 900000)
    @JsonIgnore
    @Lob
    private byte[] visitorImage;


    private String location;

    private String proofType;

    private String idProofNumber;

    /**
     * Field to set temporary card number to the visitor
     */
    private String tempCardNo;


    @OneToMany(mappedBy = "visitor", cascade = CascadeType.ALL)
    public List<Timing> timings;

    /***************** Transients  object **********************/

    @Transient
    private String visitorImageBase64;

}
