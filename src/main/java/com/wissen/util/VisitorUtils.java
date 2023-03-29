package com.wissen.util;

import com.wissen.dto.VisitorDto;
import com.wissen.entity.Visitor;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.util.UuidUtil;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Base64;

/**
 * Utility class for visitor.
 *
 * @author Vishal Tomar
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VisitorUtils {

    /**
     * Method to create entity from dto.
     *
     * @param visitorDto
     * @return visitor
     */
    public static Visitor getVisitorEntity(VisitorDto visitorDto) throws UnsupportedEncodingException {

        return Visitor.builder()
                .id(UuidUtil.getTimeBasedUuid().toString())
                .fullName(visitorDto.getFullName())
                .email(visitorDto.getEmail())
                .phoneNumber(visitorDto.getPhoneNumber())
                .pointOfContact(visitorDto.getPointOfContact())
                .pointOfContactEmail(visitorDto.getPointOfContactEmail())
                .location(visitorDto.getLocation())
                .purposeOfVisit(visitorDto.getPurposeOfVisit())
                .visitorType(visitorDto.getVisitorType())
                .idProofType(visitorDto.getIdProofType())
                .idProofNumber(visitorDto.getIdProofNumber())
                .inTime(LocalDateTime.now())
                .visitorImage(getImageByte(visitorDto.getVisitorImage()))
                .idProofImage(StringUtils.isNotBlank(visitorDto.getIdProofImage()) ? getImageByte(visitorDto.getIdProofImage()): null)
                .build();

    }

    /**
     * Method to get byte.
     * @param imageBase64
     * @return byte
     * @throws UnsupportedEncodingException
     */
    public static byte[] getImageByte(String imageBase64) throws UnsupportedEncodingException {
        byte[] name = Base64.getEncoder().encode(imageBase64.getBytes());
        byte[] decodedString = Base64.getDecoder().decode(new String(name).getBytes("UTF-8"));
        return decodedString;
    }

    /**
     * Convert byte to base64
     * @param image
     * @return
     */
    public static String byteToBase64(byte[] image) {
        return new String(image);
    }
}
