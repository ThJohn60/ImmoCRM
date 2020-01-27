package net.immocrm.domain.valid;

import net.immocrm.domain.quality.Merkmal;

public class MerkmalValidator implements Validator<Merkmal> {

	@Override
	public ValidationIssues validate(Merkmal quality) {
		return ValidationIssues.emptyIssues;
	}

}
