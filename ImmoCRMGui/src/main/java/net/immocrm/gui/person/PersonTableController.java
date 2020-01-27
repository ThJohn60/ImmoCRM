package net.immocrm.gui.person;

import javafx.scene.control.TableView;
import net.immocrm.domain.Person;
import net.immocrm.gui.AbstractTableController;

public class PersonTableController extends AbstractTableController<Person, PersonRow> {


	public PersonTableController(TableView<PersonRow> table) {
		super(table);
	}


	@Override
	protected PersonRow newTableRow(Person person) {
		return new PersonRow(person);
	}

}
