package net.immocrm.domain;

import net.immocrm.domain.vc.Price;

public interface BaseUmsatz {

	String getDateInfo();
	Price getUmsatz();
	String getDetails();

	Order getOrder();

}