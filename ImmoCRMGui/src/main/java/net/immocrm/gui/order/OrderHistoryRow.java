package net.immocrm.gui.order;

import java.time.LocalDate;

import net.immocrm.domain.OrderHistory;
import net.immocrm.domain.vc.ImmoDateTime;
import net.immocrm.gui.AbstractRow;

public class OrderHistoryRow extends AbstractRow<OrderHistory> {


	public OrderHistoryRow(OrderHistory order) {
    	super(order);
	}


	public String getDate() {
		ImmoDateTime createTimestamp = domain.getCreateTimestamp();
		if (createTimestamp.isAfter(LocalDate.now().minusDays(5))) {
			return createTimestamp.getDateAsText();
		}
		return createTimestamp.toImmoDate().toString();
	}

	public String getDescription() {
		return domain.getDescription();
	}

	public String getOldValue() {
		return domain.getOldValue();
	}

	public String getNewValue() {
		return domain.getNewValue();
	}

}
