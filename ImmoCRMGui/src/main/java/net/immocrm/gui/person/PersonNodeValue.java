package net.immocrm.gui.person;

import net.immocrm.domain.Person;
import net.immocrm.domain.TextMaker;
import net.immocrm.gui.tree.StdNodeValue;

public class PersonNodeValue extends StdNodeValue<Person> {

	private final Person person;
	private final String role;


	public PersonNodeValue(Person person, String role) {
		super(person);
		this.person = person;
		this.role = role;
	}


	@Override
	public boolean isPersonNode() {
		return true;
	}

	@Override
	public Person getPerson() {
		return super.getDomain();
	}

	@Override
	public String toString() {
		return role + TextMaker.INSTANCE.getTreeNodeText(person);
	}

}
