package net.immocrm.gui.order;

import java.util.List;

import net.immocrm.domain.Order;
import net.immocrm.domain.Request;
import net.immocrm.domain.tool.FinancialCalculator;
import net.immocrm.domain.vc.Price;
import net.immocrm.gui.AbstractRow;

public class OrderRow extends AbstractRow<Order> {


	public OrderRow(Order order) {
    	super(order);
	}


	public String getOwner() {
		return domain.getCustomer().getName();
	}

	public String getName() {
		return domain.getCustomer().getName();
	}

	public String getAddress() {
		return domain.getImmobilieAddressText();
	}

	public String getHonorar() {
		Price provision = null;
		if (domain.isSaleOrder()) {
			provision = FinancialCalculator.calcSaleProvision(domain.getSettlementPrice(), domain.getCustomerProvision(), domain.getBuyerProvision());
		} else {
			provision = FinancialCalculator.calcRentProvision(domain.getSettlementPrice(), domain.getCustomerProvision().toNumber());
		}
		return provision.toString();
	}

	public String getOrderType() {
		return domain.getOrderType().name();
	}

	public String getPurchaser() {
		if (domain.getBuyer() == null) {
			return "";
		}
		return domain.getBuyer().getName();
	}

	public String getPropertyType() {
		return domain.getImmobilie().getImmobilieTypeName();
	}

	public String getDate() {
		return domain.getCreateTimestamp().getDateAsText();
	}

	public String getState() {
		return domain.getOrderState().getLabel();
	}

	public String getPrice() {
		return domain.getSettlementPrice().toString();
	}

	public String getRequestCount() {
		List<Request> requests = domain.getRequests();
		if (requests.isEmpty()) {
			return "";
		}
		return String.valueOf(requests.size());
	}

}
