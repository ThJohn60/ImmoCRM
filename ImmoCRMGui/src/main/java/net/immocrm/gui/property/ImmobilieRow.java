package net.immocrm.gui.property;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Order;
import net.immocrm.domain.TextMaker;
import net.immocrm.domain.vc.ImmoDateTime;
import net.immocrm.gui.AbstractRow;

public class ImmobilieRow extends AbstractRow<Immobilie> {

	private static final ImmoDateTime START_DATE = new ImmoDateTime(LocalDate.of(1900, 1, 1));

	public ImmobilieRow(Immobilie property) {
		super(property);
	}


	public String getRole() {
		return "Eigentümer";
	}

	public String getType() {
		return domain.getImmobilieTypeName();
	}

	public String getAddress() {
		return TextMaker.INSTANCE.getStreetAndCity(domain.getAddress());
	}

	public String getStreet() {
		return domain.getAddress().getStreet();
	}

	public String getZustand() {
		return domain.getZustandName();
	}

	public String getArea() {
		return domain.getArea().toString();
	}

	public String getRoomCnt() {
		return domain.getRoomCnt().toString();
	}

	public String getPostalCode() {
		return domain.getAddress().getPostalCode();
	}

	public String getCity() {
		return domain.getAddress().getCity();
	}

	public String getOwnerName() {
		return domain.getOwner().getName();
	}

	public String getOwnerAddress() {
		return domain.getOwner().getHomeAddress().toString();
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
