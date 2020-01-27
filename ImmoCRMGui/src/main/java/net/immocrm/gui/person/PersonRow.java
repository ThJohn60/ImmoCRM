package net.immocrm.gui.person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import net.immocrm.domain.Order;
import net.immocrm.domain.Person;
import net.immocrm.domain.vc.ImmoDateTime;
import net.immocrm.gui.AbstractRow;

public class PersonRow extends AbstractRow<Person> {

	private static final ImmoDateTime START_DATE = new ImmoDateTime(LocalDate.of(1900, 1, 1));

	public PersonRow(Person person) {
		super(person);
	}


	public String getFirstName() {
		return domain.getFirstName();
	}

	public String getLastName() {
		return domain.getLastName();
	}

	public String getStreet() {
		return domain.getStreet();
	}

	public String getPostalCode() {
		return domain.getPostalCode();
	}

	public String getCity() {
		return domain.getCity();
	}

	public String getTelefon() {
		return domain.getHomeContact().getTelNumber();
	}

	public String getEmail() {
		return domain.getHomeContact().getEmailAddress();
	}

	public String getOrderCount() {
		List<Order> orders = domain.getOrders();
		if (orders.isEmpty()) {
			return "";
		}
		return String.valueOf(orders.size());
	}

	public String getLastOrderDate() {
		ImmoDateTime lastDate = START_DATE;
		for (Order order : domain.getOrders()) {
			ImmoDateTime orderDate = order.getSettlementDateTime().isEmpty() ? order.getCreateTimestamp() : order.getSettlementDateTime();
			if (orderDate.isAfter(lastDate)) {
				lastDate = orderDate;
			}
		}
		if (lastDate.equals(START_DATE)) {
			return "";
		}
		return DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(lastDate.getDateTime());
	}

}
