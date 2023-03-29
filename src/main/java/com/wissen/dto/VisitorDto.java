package com.wissen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitorDto {

    @NotBlank(message = "Name can not be blank.")
    private String fullName;

    @NotBlank(message = "Email can not be blank.")
    private String email;

    @NotBlank(message = "Phone number can not be blank.")
    private String phoneNumber;

    @NotBlank(message = "Point of contact can not be blank.")
    private String pointOfContact;

    @NotBlank(message = "Point of contact email can not be blank.")
    private String pointOfContactEmail;

    @NotBlank(message = "Location can not be blank.")
    private String location;

    private String purposeOfVisit;

    private String visitorType;

    private String idProofType;

    @NotBlank(message = "Id proof number can not be blank.")
    private String idProofNumber;

    @NotBlank(message = "Visitor image can not be blank.")
    private String visitorImage;

    private String idProofImage;
}
