package net.immocrm.gui.tree;

import net.immocrm.domain.BaseDomain;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Order;
import net.immocrm.domain.Person;

public class StdNodeValue<D extends BaseDomain> implements BaseNodeValue {

	private D domain;


	public StdNodeValue(D domain) {
		this.domain = domain;
	}


	public D getDomain() {
		return domain;
	}

	public void setDomain(D domain) {
		this.domain = domain;
	}

	@Override
	public boolean isDomainNode() {
		return domain != null;
	}


	@Override
	public boolean isPersonNode() {
		return false;
	}

	@Override
	public boolean isImmobilieNode() {
		return false;
	}

	@Override
	public boolean isOrderNode() {
		return false;
	}


	@Override
	public Person getPerson() {
		return null;
	}

	@Override
	public Immobilie getImmobilie() {
		return null;
	}

	@Override
	public Order getOrder() {
		return null;
	}

}
