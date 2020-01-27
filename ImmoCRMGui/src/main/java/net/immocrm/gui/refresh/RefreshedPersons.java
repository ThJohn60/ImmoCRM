package net.immocrm.gui.refresh;

import java.util.List;

import net.immocrm.domain.Person;

public class RefreshedPersons extends RefreshedDomain<Person> {

	private final String filterPattern;


	public RefreshedPersons(Person person, List<Person> personList, String filterPattern, RefreshType type) {
		super(person, personList, type);
		this.filterPattern = filterPattern;
	}


	public String getFilterPattern() {
		return filterPattern;
	}

}
