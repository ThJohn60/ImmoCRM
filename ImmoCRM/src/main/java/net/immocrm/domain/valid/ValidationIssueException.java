package net.immocrm.domain.valid;

public class ValidationIssueException extends Exception {

	private static final long serialVersionUID = 1L;
	private final Issue issue;


	public ValidationIssueException(Issue issue) {
		super(issue.getMsg());
		this.issue = issue;
		
	}


	public Issue getIssue() {
		return issue;
	}

}
