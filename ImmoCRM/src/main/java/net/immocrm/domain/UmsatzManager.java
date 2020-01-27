package net.immocrm.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UmsatzManager {


	public List<Umsatz> fetchAll() {
		List<Umsatz> result = new ArrayList<>(50);
		for (Order order : OrderReader.INSTANCE.fetchAll()) {
//			if (!order.getSettlementDateTime().isEmpty()  ||  !order.getHandoverDateTime().isEmpty()  ||  order.getOrderState().ordinal() >= OrderStateEnum.AFTER_NOTAR.ordinal()) {
			if (!order.getSettlementDateTime().isEmpty()  ||  !order.getHandoverDateTime().isEmpty()  ||  !order.getBillDate().isEmpty()) {
				result.add(new Umsatz(order));
			}
		}
		Collections.sort(result);
		return result;
	}

}
