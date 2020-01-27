package net.immocrm.gui.select;

import net.immocrm.domain.Address;
import net.immocrm.domain.Person;

public class PersonSelectionRow extends SelectionRow<Person> {

	private final Person person;


	public PersonSelectionRow(Person person) {
		super(person);
		this.person = person;
	}

	
	public String getLastName() {
		return person.getLastName();
	}

	public String getFirstName() {
		return person.getFirstName();
	}

	public Address getHomeAddress() {
		return person.getHomeAddress();
	}

}
