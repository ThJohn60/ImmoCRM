package net.immocrm.gui.order;

import net.immocrm.domain.Order;
import net.immocrm.domain.OrderHistory;
import net.immocrm.domain.Person;
import net.immocrm.domain.vc.DomainTool;

public class SelectedOrderItem {

	private final Order order;
	private final OrderHistory orderHistory;
	private final Person person;
	
	
	public SelectedOrderItem() {
		this(null);
	}
	
	public SelectedOrderItem(Order order) {
		this(order, null, null);
	}
	
	public SelectedOrderItem(Order order, OrderHistory orderHistory, Person person) {
		this.order = order;
		this.orderHistory = orderHistory;
		this.person = person;
	}


	public boolean isEmpty() {
		return DomainTool.isNull(order, orderHistory, person);
	}
	
	public Order getOrder() {
		return order;
	}

	public OrderHistory getOrderHistory() {
		return orderHistory;
	}

	public Person getPerson() {
		return person;
	}
	
}
