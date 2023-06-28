package com.wissen.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * Used to transform the input form details to save in tables
 * @author User - Sreenath Sampangi
 * @created 07/06/2023 - 16:20
 * @project wt-visitor-management-service
 */

@Data
public class VisitorDto {

    private String visitorId;

    @NotBlank(message = "Name can not be blank.")
    private String fullName;

    @NotBlank(message = "Email can not be blank.")
    private String email;

    @NotBlank(message = "Phone number can not be blank.")
    private String phoneNumber;

    private String location;

    @NotBlank(message = "Visitor Proof Type can not be blank.")
    private String proofType;

    @NotBlank(message = "Visitor Proof Type Id can not be blank.")
    private String idProofNumber;

    /**
     * Field to set temporary card number to the visitor
     */
    private String tempCardNo;

    @NotBlank(message = "Visitor image can not be empty.")
    private String visitorImageBase64;

    private LocalDateTime inTime;

    private LocalDateTime outTime;

    private String employeeId; // wissen id of point of contact

    @NotBlank(message = "Visitor type can not be null.")
    private String visitorType;

    private String assetList;
}
