package net.immocrm.gui.tree;

import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Order;
import net.immocrm.domain.Person;

@SuppressWarnings("rawtypes")
public class StringNodeValue extends StdNodeValue implements BaseNodeValue {

	private final String nodeText;


	@SuppressWarnings("unchecked")
	public StringNodeValue(String t) {
		super(null);
		nodeText = t;
	}

	@Override
	public boolean isDomainNode() {
		return false;
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

	@Override
	public String toString() {
		return nodeText;
	}

}
