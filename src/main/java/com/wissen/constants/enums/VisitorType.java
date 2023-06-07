package com.wissen.constants.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enum for visitors type.
 *
 * @author Vishal Tomar
 */
public enum VisitorType {

    MEETING("Meeting"),
    INTERVIEW("Interview"),
    VENDOR("Vendor"),
    OTHERS("Others");

    private String label;

    VisitorType(String label) {
        this.label = label;
    }

    public static List<String> getValues() {
        return Arrays.stream(VisitorType.values())
                .map(visitorPurpose -> visitorPurpose.label)
                .collect(Collectors.toList());
    }

}
