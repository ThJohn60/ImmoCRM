package net.immocrm.domain.valid;

import static net.immocrm.domain.valid.ValidationTool.checkEmpty;

import net.immocrm.domain.Notar;

public class NotarValidator implements Validator<Notar> {

	@Override
	public ValidationIssues validate(Notar notar) {
		ValidationIssues issues = new ValidationIssues();
		checkEmpty(notar.getName(), "Name", issues);
		checkEmpty(notar.getCity(), "Ort", issues);
		return issues;
	}

}
