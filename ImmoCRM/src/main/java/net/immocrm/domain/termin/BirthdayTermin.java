package net.immocrm.domain.termin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import net.immocrm.domain.Address;
import net.immocrm.domain.Person;
import net.immocrm.domain.PersonName;
import net.immocrm.domain.vc.ImmoDateTime;

public class BirthdayTermin extends BaseTermin {

	private Person person;
	private ImmoDateTime birthDay;


	public BirthdayTermin(Person person, LocalDate currentBirthDay) {
		super(TerminCategoryEnum.birthday);
		this.person = person;
		this.birthDay = new ImmoDateTime(currentBirthDay);
	}


	@Override
	public String getTitle() {
		return "birthday " + person.getName();
	}

	@Override
	public String getCalendarLabel() {
		return getTitle();
	}


	@Override
	public ImmoDateTime getDate() {
		return birthDay;
	}

	@Override
	public String getDetails() {
		StringBuilder sb = new StringBuilder();
		sb.append(person.getName());
		sb.append(" hat Geburtstag ");
		return sb.toString();
	}


	@Override
	public void setDate(ImmoDateTime dateTime) {
		person.setBirthday(dateTime.toImmoDate());
	}


	@Override
	public Address getAddress() {
		return null;
	}


	@Override
	public List<PersonName> getParticipants() {
		List<PersonName> result = new ArrayList<>();
		result.add(person);
		return result;
	}


	@Override
	public boolean isPerson() {
		return true;
	}

}
