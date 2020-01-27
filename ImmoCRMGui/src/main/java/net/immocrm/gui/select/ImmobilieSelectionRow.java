package net.immocrm.gui.select;

import net.immocrm.domain.Address;
import net.immocrm.domain.Immobilie;

public class ImmobilieSelectionRow extends SelectionRow<Immobilie> {

	private final Immobilie immobilie;


	public ImmobilieSelectionRow(Immobilie immobilie) {
		super(immobilie);
		this.immobilie = immobilie;
	}


	public String getImmobilieType() {
		return immobilie.getImmobilieTypeName();
	}


	public Address getAddress() {
		return immobilie.getAddress();
	}

}
