package net.immocrm.gui.property;

import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Person;
import net.immocrm.domain.vc.ImmoDate;

public interface ImmoActionRow extends Comparable<ImmoActionRow> {

	String getName();
	String getRole();

	String getType();
	String getAddress();
	String getPrice();

	String getStartDate();
	ImmoDate getStartImmoDate();
	String getEndDate();
	ImmoDate getEndImmoDate();

	String getRoomCnt();
	String getZustand();
	String getHausgeld();
	String getMiete();

	Immobilie getImmobilie();
	Person getPerson();

}