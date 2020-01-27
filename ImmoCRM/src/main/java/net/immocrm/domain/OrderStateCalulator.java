package net.immocrm.domain;

import java.time.LocalDate;

import net.immocrm.domain.ref.OrderStateEnum;
import net.immocrm.domain.vc.ImmoDate;
import net.immocrm.domain.vc.ImmoDateTime;

public class OrderStateCalulator {

	private OrderStateCalulator() {
		super();
	}


	public static OrderStateEnum stateOf(Order order) {
		LocalDate now = LocalDate.now();
		ImmoDateTime notarDate = order.getSettlementDateTime();
		ImmoDateTime handoverDate = order.getHandoverDateTime();
		ImmoDate billDate = order.getBillDate();
		ImmoDate payedDate = order.getPayedDate();

		if (!payedDate.isEmpty() && payedDate.isBefore(now)) {
			return OrderStateEnum.FINISHED;
		}
		if (!billDate.isEmpty() && billDate.isBefore(now)) {
			return OrderStateEnum.BILLED;
		}
		if (!handoverDate.isEmpty() && handoverDate.isBefore(now)) {
			return OrderStateEnum.HANDOVER;
		}
		if (!notarDate.isEmpty() && notarDate.isBefore(now)) {
			return OrderStateEnum.AFTER_NOTAR;
		}
		if (!notarDate.isEmpty() && notarDate.isAfter(now)) {
			return OrderStateEnum.BEFORE_NOTAR;
		}
		return OrderStateEnum.OPEN;
	}

}
