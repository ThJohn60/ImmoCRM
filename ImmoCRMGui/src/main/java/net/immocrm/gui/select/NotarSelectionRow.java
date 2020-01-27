package net.immocrm.gui.select;

import net.immocrm.domain.Notar;

public class NotarSelectionRow extends SelectionRow<Notar> {

	private final Notar notar;


	public NotarSelectionRow(Notar notar) {
		super(notar);
		this.notar = notar;
	}

	
	public String getName() {
		return notar.getName();
	}

	
	public String getPostalCode() {
		return notar.getPostalCode();
	}

	public String getCity() {
		return notar.getCity();
	}

}
