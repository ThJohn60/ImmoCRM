package net.immocrm.gui;

import net.immocrm.domain.valid.Issue;

public class FieldValidationException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private final Issue issue;
	

	public FieldValidationException(String errorText) {
		this(Issue.ERROR_TEXT(errorText));
	}

	public FieldValidationException(String fieldId, String errorText) {
		this(Issue.ERROR_TEXT(fieldId, errorText));
	}

	public FieldValidationException(String fieldId, String fieldName, String errorText) {
		this(Issue.ERROR_TEXT(fieldId, fieldName, errorText));
	}

	public FieldValidationException(Issue issue) {
		super(issue.getMsg());
		this.issue = issue;
	}

	public Issue getIssue() {
		return issue;
	}

}
