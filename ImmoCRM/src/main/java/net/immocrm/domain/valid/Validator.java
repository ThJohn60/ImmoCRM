package net.immocrm.domain.valid;

import net.immocrm.domain.BaseDomain;

public interface Validator<D extends BaseDomain> {

	ValidationIssues validate(D domain);

}