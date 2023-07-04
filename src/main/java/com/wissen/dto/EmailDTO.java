package com.wissen.dto;

import java.util.List;
import java.util.Map;

/**
 * Class to contain the data for Email
 * 
 * @author Ankit Garg
 *
 */
public class EmailDTO {

	private List<String> recipients;
	private List<String> ccList;
	private List<String> bccList;
	private String subject;
	private String body;
	private Boolean isHtml;
	private String attachmentPath;
	private Map<String, Object> properties;
	private String templateName;

	public List<String> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}

	public List<String> getCcList() {
		return ccList;
	}

	public void setCcList(List<String> ccList) {
		this.ccList = ccList;
	}

	public List<String> getBccList() {
		return bccList;
	}

	public void setBccList(List<String> bccList) {
		this.bccList = bccList;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Boolean getHtml() {
		return isHtml;
	}

	public void setHtml(Boolean html) {
		isHtml = html;
	}

	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

}