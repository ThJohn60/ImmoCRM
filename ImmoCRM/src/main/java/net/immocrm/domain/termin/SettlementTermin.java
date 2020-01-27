package net.immocrm.domain.termin;

import java.util.ArrayList;
import java.util.List;

import net.immocrm.domain.Address;
import net.immocrm.domain.Order;
import net.immocrm.domain.PersonName;
import net.immocrm.domain.vc.ImmoDateTime;

public class SettlementTermin extends OrderTermin {

	private ImmoDateTime date;


	public SettlementTermin(Order order, TerminCategoryEnum typeEnum) {
		super(order, typeEnum);
		date = order.getSettlementDateTime();
	}


	@Override
	public String getTitle() {
		return "Notar " + order.getNotar().getName();
	}

	@Override
	public String getCalendarLabel() {
		return "Notar\n" + order.getNotar().getName() + "\n" + order.getNotar().getCity();
	}


	@Override
	public ImmoDateTime getDate() {
		return date;
	}

	@Override
	public void setDate(ImmoDateTime dateTime) {
		date = dateTime;
		order.setSettlementDateTime(dateTime);
	}


	@Override
	public Address getAddress() {
		return order.getNotar();
	}


	@Override
	public List<PersonName> getParticipants() {
		List<PersonName> result = new ArrayList<>();
		result.add(order.getCustomer());
		result.add(order.getBuyer());
		result.add(order.getNotar());
		return result;
	}

}
