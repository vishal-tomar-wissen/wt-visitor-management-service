package com.wissen.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Model class contains details of point of contact of visitors.
*
* @author Vishal Tomar
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitorPointOfContactDetail {

    private String wissenId;
    private String firstName;
    private String lastName;
    private String email;
}