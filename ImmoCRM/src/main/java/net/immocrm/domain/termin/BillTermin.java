package net.immocrm.domain.termin;

import java.util.Collections;
import java.util.List;

import net.immocrm.domain.Address;
import net.immocrm.domain.Order;
import net.immocrm.domain.PersonName;
import net.immocrm.domain.vc.ImmoDateTime;

public class BillTermin extends OrderTermin {

	private ImmoDateTime date;


	public BillTermin(Order order, TerminCategoryEnum typeEnum) {
		super(order, typeEnum);
		date = order.getBillDate().toImmoDateTime();
	}


	@Override
	public String getTitle() {
		return "Rechnung gestellt für " + order.getImmobilie().getImmobilieTypeName();
	}

	@Override
	public String getCalendarLabel() {
		return "Rechnung gestellt\n" + order.getImmobilie().getAddress().getAddressText();
	}


	@Override
	public ImmoDateTime getDate() {
		return date;
	}

	@Override
	public void setDate(ImmoDateTime dateTime) {
		date = dateTime;
		order.setBillDate(dateTime.toImmoDate());
	}


	@Override
	public Address getAddress() {
		return null;
	}


	@Override
	public List<PersonName> getParticipants() {
		return Collections.emptyList();
	}

}
