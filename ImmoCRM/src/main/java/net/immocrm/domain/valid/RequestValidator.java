package net.immocrm.domain.valid;

import net.immocrm.domain.Request;

public class RequestValidator implements Validator<Request> {

	@Override
	public ValidationIssues validate(Request request) {
		ValidationIssues issues = new ValidationIssues();
		if (request.getPurchaser() == null || request.getPurchaser().isEmpty()) {
			issues.addMissingField("Interessent");
		} else {
			issues.addAllIssues(request.getPurchaser().validate());
		}
		return issues;
	}

}
