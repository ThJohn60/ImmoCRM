package net.immocrm.gui.money;

import net.immocrm.domain.BaseUmsatz;
import net.immocrm.domain.Order;
import net.immocrm.domain.vc.Price;
import net.immocrm.domain.vc.Year;

public class YearUmsatz implements BaseUmsatz {

	private final Year year;
	private Price umsatz;


	public YearUmsatz(Year year, Price umsatz) {
		this.year = year;
		this.umsatz = umsatz;
	}


	public void addUmsatz(Price value) {
		umsatz = umsatz.plus(value);
	}


	@Override
	public String getDateInfo() {
		return year.toString();
	}

	@Override
	public Price getUmsatz() {
		return umsatz;
	}


	@Override
	public String getDetails() {
		return "";
	}


	@Override
	public Order getOrder() {
		return null;
	}

}
