package net.immocrm.domain.termin;

import net.immocrm.domain.Order;
import net.immocrm.domain.Person;
import net.immocrm.domain.Request;
import net.immocrm.domain.vc.ImmoDateTime;

public abstract class OrderTermin extends BaseTermin {

	protected final Order order;


	public OrderTermin(Order order, TerminCategoryEnum typeEnum) {
		super(typeEnum);
		this.order = order;
	}


	@Override
	public abstract ImmoDateTime getDate();


	@Override
	public String getDetails() {
		boolean withLineBreaks = false;
		return getDetails(withLineBreaks);
	}

	@Override
	public String getDetailsWithLineBreaks() {
		boolean withLineBreaks = true;
		return getDetails(withLineBreaks);
	}

	public String getDetails(boolean withLineBreaks) {
		StringBuilder sb = new StringBuilder();
		sb.append(order.getCustomer().getName())
			.append(" aus ")
			.append(order.getCustomer().getCity());

		if (order.isSaleOrder()) {
			sb.append(" verkauft ");
		} else {
			sb.append(" vermietet ");
		}
		if (withLineBreaks) {
			sb.append("\n");
		}
		sb.append(order.getImmobilie().getImmobilieTypeName());
		sb.append(" in ").append(order.getImmobilie().getAddress().getCity());
		return sb.toString();
	}


	@Override
	public Person getPerson() {
		return null;
	}

	@Override
	public Order getOrder() {
		return order;
	}

	@Override
	public Request getRequest() {
		return null;
	}

	@Override
	public boolean isOrder() {
		return true;
	}

}
