package net.immocrm.domain.valid;

import net.immocrm.domain.Immobilie;

public class ImmobilieValidator implements Validator<Immobilie> {

	@Override
	public ValidationIssues validate(Immobilie immobilie) {
		ValidationIssues issues = new ValidationIssues();
		if (immobilie.getOwner() == null || immobilie.getOwner().isEmpty()) {
			issues.addMissingField("Eigentümer");
		} else {
			issues.addAllIssues(immobilie.getOwner().validate());
		}
		ValidationTool.checkEmpty(immobilie.getAddress(), "Adresse", issues);
		return issues;
	}

}
