package net.immocrm.gui.refresh;

import java.util.List;

import net.immocrm.domain.Order;

public class RefreshedOrders extends RefreshedDomain<Order> {

	private final DomainViewFilter filter;


	public RefreshedOrders(List<Order> orderList, DomainViewFilter filter) {
		this(null, orderList, filter, RefreshType.all);
	}

	public RefreshedOrders(Order order, List<Order> orderList, DomainViewFilter filter, RefreshType type) {
		super(order, orderList, type);
		this.filter = filter;
	}


	public String getFilterPattern() {
		return filter == null ? "" : filter.toString();
	}

}
