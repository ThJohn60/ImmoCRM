package net.immocrm.gui.notar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import net.immocrm.domain.Notar;
import net.immocrm.domain.Order;
import net.immocrm.domain.vc.ImmoDateTime;
import net.immocrm.gui.AbstractRow;

public class NotarRow extends AbstractRow<Notar> {

	private static final ImmoDateTime START_DATE = new ImmoDateTime(LocalDate.of(1900, 1, 1));

	public NotarRow(Notar entity) {
		super(entity);
	}

	public String getName() {
		return domain.getName();
	}

	public String getAddress() {
		return domain.getStreet() + ", " + domain.getPostalCode() + " " + domain.getCity();
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
		return domain.getTelNumber();
	}

	public String getEmail() {
		return domain.getEmailAddress();
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
