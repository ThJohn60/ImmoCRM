package net.immocrm.domain;

import net.immocrm.domain.valid.ValidationIssues;
import net.immocrm.domain.vc.ImmoDateTime;

public interface BaseDomain {

	Integer getId();
	boolean isSameId(BaseDomain d);

	boolean isNew();
	boolean isEmpty();

	String getDomainName();

	ImmoDateTime getCreateTimestamp();

	ValidationIssues validate();

}