package net.immocrm.domain.termin;

import java.util.List;

import net.immocrm.domain.Address;
import net.immocrm.domain.Order;
import net.immocrm.domain.Person;
import net.immocrm.domain.PersonName;
import net.immocrm.domain.Request;
import net.immocrm.domain.vc.ImmoDateTime;

public interface Termin extends Comparable<Termin> {

	String getTitle();
	String getCalendarLabel();

	ImmoDateTime getDate();
	void setDate(ImmoDateTime dateTime);

	Address getAddress();

	List<PersonName> getParticipants();
	String getParticipantsCommaSeperated();

	TerminCategoryEnum getCategory();
	String getCategoryName();

	String getDetails();
	String getDetailsWithLineBreaks();

	Person getPerson();
	Order getOrder();
	Request getRequest();

	boolean isOrder();
	boolean isPerson();
	boolean isRequest();
	boolean isIndependentTermin();

}