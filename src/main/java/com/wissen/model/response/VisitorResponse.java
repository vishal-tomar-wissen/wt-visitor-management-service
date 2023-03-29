package com.wissen.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Response class for visitor.
 *
 * @author Vishal Tomar
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitorResponse {

    private String id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String pointOfContact;
    private String pointOfContactEmail;
    private String location;
    private String purposeOfVisit;
    private String visitorType;
    private String idProofType;
    private String idProofNumber;
    private String visitorImage;
    private String idProofImage;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime inTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime outTime;
}
