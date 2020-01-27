package net.immocrm.gui.select;

import java.util.ArrayList;
import java.util.List;

import net.immocrm.domain.Order;
import net.immocrm.domain.Request;
import net.immocrm.domain.RequestReader;

public class RequestItemProvider implements TableItemProvider<Request, RequestSelectionRow>{

	private final Order order;


	public RequestItemProvider(Order order) {
		this.order = order;
	}


	@Override
	public List<RequestSelectionRow> search(String pattern) {
		List<RequestSelectionRow> result = new ArrayList<>();
		for (Request re : RequestReader.INSTANCE.searchInOrder(order.getId(), pattern)) {
			result.add(new RequestSelectionRow(re));
		}
		return result;
	}

	@Override
	public List<RequestSelectionRow> fetchAll() {
		List<RequestSelectionRow> result = new ArrayList<>();
		for (Request re : RequestReader.INSTANCE.fetchByOrderId(order.getId())) {
			result.add(new RequestSelectionRow(re));
		}
		return result;
	}

}
