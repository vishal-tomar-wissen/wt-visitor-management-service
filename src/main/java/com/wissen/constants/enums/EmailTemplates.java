package com.wissen.constants.enums;

/**
 * Enums for email Templates
 * 
 * @author Ankit Garg
 *
 */
public enum EmailTemplates {

	VISITOR_OTP("visitorOtpEmail"), HOST_INTIMATION("hostIntimationEmail");

	private String templateName;

	EmailTemplates(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateName() {
		return templateName;
	}

}
