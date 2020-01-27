package net.immocrm.gui.order;

import net.immocrm.domain.Order;
import net.immocrm.gui.tree.StdNodeValue;

public class OrderNodeValue extends StdNodeValue<Order> {

	private final String nodeLabel;


	public OrderNodeValue(Order order, String nodeLabel) {
		super(order);
		this.nodeLabel = nodeLabel;
	}


	@Override
	public boolean isOrderNode() {
		return true;
	}

	@Override
	public Order getOrder() {
		return super.getDomain();
	}

	@Override
	public String toString() {
		return nodeLabel;
	}

}
