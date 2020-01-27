package net.immocrm.domain.termin;

import java.util.ArrayList;
import java.util.List;

import net.immocrm.domain.Address;
import net.immocrm.domain.Order;
import net.immocrm.domain.PersonName;
import net.immocrm.domain.vc.ImmoDateTime;

public class HandoverTermin extends OrderTermin {

	private ImmoDateTime date;


	public HandoverTermin(Order order, TerminCategoryEnum typeEnum) {
		super(order, typeEnum);
		date = order.getHandoverDateTime();
	}


	@Override
	public String getTitle() {
		return "Übergabetermin " + order.getImmobilie().getImmobilieTypeName();
	}

	@Override
	public String getCalendarLabel() {
		return "Übergabetermin\n" + order.getImmobilie().getAddress().getAddressText();
	}


	@Override
	public ImmoDateTime getDate() {
		return date;
	}

	@Override
	public void setDate(ImmoDateTime dateTime) {
		date = dateTime;
		order.setHandoverDateTime(dateTime);
	}


	@Override
	public Address getAddress() {
		return order.getImmobilieAddress();
	}


	@Override
	public List<PersonName> getParticipants() {
		List<PersonName> result = new ArrayList<>();
		result.add(order.getCustomer());
		if (!order.getBuyer().isEmpty()) {
			result.add(order.getBuyer());
		}
		return result;
	}

}
