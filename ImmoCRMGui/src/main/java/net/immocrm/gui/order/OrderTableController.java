package net.immocrm.gui.order;

import javafx.scene.control.TableView;
import net.immocrm.domain.Order;
import net.immocrm.gui.AbstractTableController;

public class OrderTableController extends AbstractTableController<Order, OrderRow> {

	public OrderTableController(TableView<OrderRow> table) {
		super(table);
	}


	@Override
	protected OrderRow newTableRow(Order order) {
		return new OrderRow(order);
	}

}
