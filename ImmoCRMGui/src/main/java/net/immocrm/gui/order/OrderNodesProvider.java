package net.immocrm.gui.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.immocrm.domain.Order;
import net.immocrm.domain.vc.ImmoDateTime;

public class OrderNodesProvider {

	private Map<String, List<Order>> nodeInfo;
	private final ImmoDateTime today;


	public OrderNodesProvider(List<Order> orderList) {
		today = new ImmoDateTime(LocalDateTime.now());
		nodeInfo = new HashMap<>();
		for (Order order : orderList) {
			String year = getOrderYear(order);
			List<Order> yearList = getYearList(year);
			yearList.add(order);
		}
	}

	private List<Order> getYearList(String year) {
		List<Order> yearList = nodeInfo.get(year);
		if (yearList == null) {
			yearList = new ArrayList<Order>();
			nodeInfo.put(year, yearList);
		}
		return yearList;
	}

	public String getOrderYear(Order order) {
		ImmoDateTime orderDate = order.getSettlementDateTime();
		if (orderDate == null || orderDate.isEmpty()) {
			orderDate = today;
		}
		return orderDate.getYear().toString();
	}


	public List<String> getYears() {
		List<String> result = new ArrayList<>(nodeInfo.keySet());
		Collections.sort(result);
		return result;
	}

	public List<Order> getOrderListByYear(String year) {
		return nodeInfo.get(year);
	}

}
