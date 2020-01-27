package net.immocrm.domain.valid;

public class Issue {

	private final String fieldId;
	private final String fieldname;
	private final String errorText;
	private final Severity severity;
	

	public static final Issue ERROR_TEXT(String errorText) {
		return new Issue(errorText, Severity.ERROR);
	}
			
	public static final Issue ERROR_TEXT(String fieldId, String errorText) {
		return new Issue(fieldId, "", errorText, Severity.ERROR);
	}
			
	public static Issue ERROR_TEXT(String fieldId, String fieldName, String errorText) {
		return new Issue(fieldId, fieldName, errorText, Severity.ERROR);
	}
	
	public static Issue PERSISTENCE_ERROR(Exception e) {
		String exMsg = (e.getMessage() == null) ? e.getClass().getName() : e.getMessage(); 
		if (exMsg.indexOf("wurde ein Truncation-Fehler festgestellt") > 0) {
			return new TruncationError(exMsg);
		}
		return new Issue(exMsg, Severity.ERROR);
	}
	
	Issue(String errorText, Severity severity) {
		this("", "", errorText, severity);
	}

	Issue(String fieldId, String fieldname, String errorText, Severity severity) {
		this.fieldId = fieldId;
		this.fieldname = fieldname;
		this.errorText = errorText;
		this.severity = severity;
	}


	public String getFieldId() {
		return fieldId;
	}

	public String getFieldname() {
		return fieldname;
	}

	public String getMsg() {
		return errorText;
	}
	
	public Severity getSeverity() {
		return severity;
	}

	public boolean isError() {
		return severity == Severity.ERROR;
	}

	public enum Severity {
		WARNING,
		ERROR;
	}

}
