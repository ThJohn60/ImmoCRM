package net.immocrm.gui.select;

import net.immocrm.domain.BaseDomain;

public class SelectionRow<T extends BaseDomain> {

	private final T domain;

	
	public SelectionRow(T entity) {
		this.domain = entity;
	}


	public String getText() {
		return domain.toString();
	}
	
	public T getDomainObject() {
		return domain;
	}

	@Override
	public String toString() {
		return domain.toString();
	}

}
