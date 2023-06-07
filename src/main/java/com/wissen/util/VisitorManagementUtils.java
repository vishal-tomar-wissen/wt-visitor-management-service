package com.wissen.util;

import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Set;

/**
 * Utility class.
 *
 * @author Vishal Tomar
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VisitorManagementUtils {

    /**
     * Convert string to byte code for image.
     *
     * @param base64Image
     * @return byteCode
     */
    public static byte[] convertBase64ToByte(String base64Image) {
        return Base64.getDecoder().decode(base64Image.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Covert byte code to string.
     *
     * @param image
     * @return convertedImage
     */
    public static String convertByteToBase64(byte[] image) {
        return Base64.getEncoder().encodeToString(image);
    }

    /**
     * Method return fields which is allowed in get filter.
     *
     * @return fields
     */
    public static Set<String> getAllowedVisitorFilterField() {
        return Sets.newHashSet("visitorId", "fullName", "email", "phoneNumber", "idProofType",
                "idProofNumber", "tempCardNo");
    }

    /**
     * Method return fields which is allowed in get filter.
     *
     * @return fields
     */
    public static Set<String> getAllowedTimingFilterField() {
        return Sets.newHashSet("id", "inTime", "outTime", "employeeId", "visitorType");
    }

}
