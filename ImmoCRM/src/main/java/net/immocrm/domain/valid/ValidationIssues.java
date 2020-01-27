package net.immocrm.domain.valid;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert.AlertType;
import net.immocrm.domain.valid.Issue.Severity;
import net.immocrm.domain.vc.MissingFields;

public class ValidationIssues {
	
	public static final ValidationIssues emptyIssues = new ValidationIssues();

	private final List<Issue> issues;

	private final MissingFields missingFields;


	public ValidationIssues(String id, String fieldname, String errorText) {
		this();
		addIssue(id, fieldname, errorText, Severity.ERROR);
	}

	public ValidationIssues(Exception ex) {
		this();
		addIssue(Issue.PERSISTENCE_ERROR(ex));
	}

	public ValidationIssues() {
		this.issues = new ArrayList<>();
		this.missingFields = new MissingFields();
	}


	public void addError(String errorText) {
		addIssue("", "Immobilie", errorText, Severity.ERROR);
	}

	public void addError(String fieldId, String fieldname, String errorText) {
		addIssue(fieldId, fieldname, errorText, Severity.ERROR);
	}

	public void addWarning(String fieldId, String fieldname, String errorText) {
		addIssue(fieldId, fieldname, errorText, Severity.WARNING);
	}

	private void addIssue(String fieldId, String fieldname, String errorText, Severity severity) {
		addIssue(new Issue(fieldId, fieldname, errorText, severity));
	}

	public boolean addIssue(Issue e) {
		return issues.add(e);
	}

	public void addAllIssues(ValidationIssues vi) {
		issues.addAll(vi.getIssueList());
		missingFields.addAll(vi.missingFields);
	}


	public boolean isEmpty() {
		return issues.isEmpty() && missingFields.isEmpty();
	}

	public boolean hasIssues() {
		return !isEmpty();
	}

	public AlertType getAlertType() {
		if (hasErrors()) {
			return AlertType.ERROR;
		}
		return AlertType.WARNING;
	}

	public boolean hasErrors() {
		if (!missingFields.isEmpty()) {
			return true;
		}
		for (Issue issue : issues) {
			if (issue.isError()) {
				return true;
			}
		}
		return false;
	}

	public String getErrorMsg() {
		StringBuilder result = new StringBuilder();
		if (!missingFields.isEmpty()) {
			result.append(missingFields.getErrorMsg());
		}
		for (Issue issue : issues) {
			result.append(issue.getMsg()).append("\n");
		}
		return result.toString();
	}

	private List<Issue> getIssueList() {
		return issues;
	}

	public void addMissingField(String fieldName) {
		missingFields.addMissingField(fieldName);
	}

	public int getIssueCount() {
		return issues.size();
	}

	public String getMsgHeader() {
		if (!missingFields.isEmpty()) {
			return "Unvollständige Eingabe";
		}
		return "Fehler";
	}

}
