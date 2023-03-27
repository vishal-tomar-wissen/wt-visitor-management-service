package com.wissen.constants.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enum for Visitor Id proof type.
 *
 * @author Vishal Tomar
 */
public enum VisitorIDType {

    AADHAR("Aadhar"),
    VOTER_ID("Voter Id"),
    PAN("Pan"),
    DRIVING_LICENCE("Driving Licence"),
    PASSPORT("Passport");

    private String label;

    VisitorIDType(String label) {
        this.label = label;
    }

    public static List<String> getValues() {
        return Arrays.stream(VisitorIDType.values())
                .map(visitorIDType -> visitorIDType.label)
                .collect(Collectors.toList());
    }

}
