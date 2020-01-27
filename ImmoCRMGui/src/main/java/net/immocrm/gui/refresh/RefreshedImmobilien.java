package net.immocrm.gui.refresh;

import java.util.List;

import net.immocrm.domain.Immobilie;

public class RefreshedImmobilien extends RefreshedDomain<Immobilie> {

	private final ImmobilieFilter filter;


	public RefreshedImmobilien(Immobilie immobilie, List<Immobilie> immobilieList, ImmobilieFilter filter, RefreshType type) {
		super(immobilie, immobilieList, type);
		this.filter = filter;
	}


	public String getFilterPattern() {
		return filter == null ? "" : filter.toString();
	}

}
