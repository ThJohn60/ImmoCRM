package net.immocrm.gui.refresh;

import java.util.List;

import net.immocrm.domain.Notar;

public class RefreshedNotars extends RefreshedDomain<Notar> {

	private final String filterPattern;


	public RefreshedNotars(Notar notar, List<Notar> notarList, String filterPattern, RefreshType type) {
		super(notar, notarList, type);
		this.filterPattern = filterPattern;
	}


	public String getFilterPattern() {
		return filterPattern;
	}

}
