package net.immocrm.gui.select;

import java.util.ArrayList;
import java.util.List;

import net.immocrm.domain.Person;
import net.immocrm.domain.PersonReader;

public class PersonPersonSelectionRowProvider implements TableItemProvider<Person, PersonSelectionRow> {

	@Override
	public List<PersonSelectionRow> search(String pattern) {
		List<PersonSelectionRow> result = new ArrayList<>();
		for (Person person : PersonReader.INSTANCE.find(pattern)) {
			result.add(new PersonSelectionRow(person));
		}
		return result;
	}

	@Override
	public List<PersonSelectionRow> fetchAll() {
		List<PersonSelectionRow> result = new ArrayList<>();
		for (Person person : PersonReader.INSTANCE.find(null)) {
			result.add(new PersonSelectionRow(person));
		}
		return result;
	}

}
