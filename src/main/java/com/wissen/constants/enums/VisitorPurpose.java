package com.wissen.constants.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enum for visitors purpose.
 *
 * @author Vishal Tomar
 */
public enum VisitorPurpose {

    WALKIN("Meeting"),
    INVITED("Invited");

    private String label;

    VisitorPurpose(String label) {
        this.label = label;
    }

    public static List<String> getValues() {
        return Arrays.stream(VisitorPurpose.values())
                .map(visitorPurpose -> visitorPurpose.label)
                .collect(Collectors.toList());
    }

}
