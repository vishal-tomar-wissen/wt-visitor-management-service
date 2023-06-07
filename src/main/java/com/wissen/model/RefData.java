package com.wissen.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Model class for refData.
 *
 * @author Vishal Tomar
 */
@Data
@Builder
public class RefData {

    private List<String> proofTypes;
    private List<String> visitorsTypes;
}
