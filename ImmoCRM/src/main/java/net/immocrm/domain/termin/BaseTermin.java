package net.immocrm.domain.termin;

import java.util.List;

import net.immocrm.domain.Order;
import net.immocrm.domain.Person;
import net.immocrm.domain.PersonName;
import net.immocrm.domain.Request;

public abstract class BaseTermin implements Termin {

	private final TerminCategoryEnum category;


	public BaseTermin(TerminCategoryEnum typeEnum) {
		this.category = typeEnum;
	}


	@Override
	public TerminCategoryEnum getCategory() {
		return category;
	}

	@Override
	public String getCategoryName() {
		return category.getName();
	}

	@Override
	public Person getPerson() {
		return null;
	}

	@Override
	public Order getOrder() {
		return null;
	}

	@Override
	public Request getRequest() {
		return null;
	}

	@Override
	public boolean isOrder() {
		return false;
	}

	@Override
	public boolean isPerson() {
		return false;
	}

	@Override
	public boolean isRequest() {
		return false;
	}

	@Override
	public boolean isIndependentTermin() {
		return false;
	}

	@Override
	public String getParticipantsCommaSeperated() {
		List<PersonName> participants = getParticipants();
		if (participants.isEmpty()) {
			return "";
		}
		StringBuilder result = new StringBuilder();
		for (PersonName person : participants) {
			result.append(person.getName()).append(", ");
		}
		result.delete(result.length()-2, result.length());
		return result.toString();
	}

	@Override
	public String getDetailsWithLineBreaks() {
		return getDetails();
	}


	@Override
	public int compareTo(Termin o) {
		return getDate().compareTo(o.getDate());
	}

}
