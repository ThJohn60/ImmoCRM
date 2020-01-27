package net.immocrm.domain.valid;

import static net.immocrm.domain.valid.ValidationTool.checkEmpty;

import net.immocrm.domain.Person;

public class PersonValidator implements Validator<Person> {

	@Override
	public ValidationIssues validate(Person person) {
		ValidationIssues issues = new ValidationIssues();
		checkEmpty(person.getFirstName(), "Vorname", issues);
		checkEmpty(person.getLastName(), "Nachname", issues);
		issues.addAllIssues(person.getHomeContact().validate());
		issues.addAllIssues(person.getHomeAddress().validate());
		return issues;
	}

}
