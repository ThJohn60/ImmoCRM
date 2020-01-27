package net.immocrm.gui.tree;

import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Order;
import net.immocrm.domain.Person;

public interface BaseNodeValue {

	boolean isDomainNode();

	boolean isPersonNode();
	boolean isImmobilieNode();
	boolean isOrderNode();

	Person getPerson();
	Immobilie getImmobilie();
	Order getOrder();

}