package net.immocrm.gui.money;

import net.immocrm.domain.BaseUmsatz;

public class UmsatzRow  {

	private final BaseUmsatz umsatz;

	public UmsatzRow(BaseUmsatz umsatz) {
		this.umsatz = umsatz;
	}


	public BaseUmsatz getUmsatz() {
		return umsatz;
	}


	public String getDate() {
		return umsatz.getDateInfo();
	}

	public String getUmsatzValue() {
		return umsatz.getUmsatz().toString();
	}

	public String getDetails() {
		return umsatz.getDetails();
	}

}
