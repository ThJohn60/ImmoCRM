package net.immocrm.domain.valid;

import static net.immocrm.domain.valid.ValidationTool.checkEmpty;
import static net.immocrm.domain.valid.ValidationTool.checkNull;

import net.immocrm.domain.quality.MerkmalType;

public class MerkmalTypeValidator implements Validator<MerkmalType> {

	@Override
	public ValidationIssues validate(MerkmalType type) {
		ValidationIssues issues = new ValidationIssues();
		checkEmpty(type.getName(), "Bezeichnung", issues);
		checkNull(type.getCategory(), "Kategorie", issues);
		checkNull(type.getDataType(), "Datentyp", issues);
		checkNull(type.getImmobileTypes(), "Immobilientyp", issues);
		return issues;
	}

}
