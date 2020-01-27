package net.immocrm.gui;

import net.immocrm.domain.BaseDomain;

public class AbstractRow<D extends BaseDomain> {

	protected D domain;

	public AbstractRow(D domain) {
		this.domain = domain;
	}

	public Integer getId() {
		return domain.getId();
	}

	public D getDomain() {
		return domain;
	}

	public void setDomain(D domain) {
		this.domain = domain;
	}

}
