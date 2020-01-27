package net.immocrm.domain;

import java.util.ArrayList;
import java.util.List;

import net.immocrm.db.OrderHistoryEntity;
import net.immocrm.domain.ref.OrderStateEnum;
import net.immocrm.domain.vc.ImmoDate;
import net.immocrm.domain.vc.ImmoDateTime;
import net.immocrm.domain.vc.Price;

public class OrderHistoryProvider {

	private final OrderStateEnum orderState;
	private final Price customerPrice;
	private final Price estimatedPrice;
	private final Price settlementPrice;
	private final ImmoDateTime settlementDateTime;
	private final ImmoDateTime handoverDateTime;
	private final ImmoDate billDate;


	public OrderHistoryProvider(Order order) {
		orderState = order.getOrderState();
		customerPrice = order.getCustomerPrice();
		estimatedPrice = order.getEstimatedPrice();
		settlementPrice = order.getSettlementPrice();

		settlementDateTime = order.getSettlementDateTime();
		handoverDateTime = order.getHandoverDateTime();
		billDate = order.getBillDate();
	}


	public List<OrderHistoryEntity> historyEntries(OrderDomain order) {
		List<OrderHistoryEntity> result = new ArrayList<>();
		if (order.isNew()) {
			OrderHistoryEntity e = new OrderHistoryEntity();
			e.setDescription("Auftrag angelegt");
			e.setOrder(order.getEntity());
			result.add(e);
		} else if (orderState != order.getOrderState()) {
			OrderHistoryEntity e = new OrderHistoryEntity();
			e.setDescription("Statusänderung");
			e.setOldValue(orderState.getLabel());
			e.setNewValue(order.getOrderState().getLabel());
			e.setOrder(order.getEntity());
			result.add(e);
		}

		if (settlementDateTime.isEmpty()  &&  !order.getSettlementDateTime().isEmpty()) {
			OrderHistoryEntity e = new OrderHistoryEntity();
			e.setDescription("Notar eingetragen");
			e.setOrder(order.getEntity());
			result.add(e);
		} else if (!settlementDateTime.toString().equals(order.getSettlementDateTime().toString())) {
			OrderHistoryEntity e = new OrderHistoryEntity();
			e.setDescription("Notar geändert" );
			e.setOldValue(settlementDateTime.toString());
			e.setNewValue(order.getSettlementDateTime().toString());
			e.setOrder(order.getEntity());
			result.add(e);
		}

		if (handoverDateTime.isEmpty()  &&  !order.getHandoverDateTime().isEmpty()) {
			OrderHistoryEntity e = new OrderHistoryEntity();
			e.setDescription("Übergabetermin eingetragen");
			e.setOrder(order.getEntity());
			result.add(e);
		} else if (!handoverDateTime.toString().equals(order.getHandoverDateTime().toString())) {
			OrderHistoryEntity e = new OrderHistoryEntity();
			e.setDescription("Übergabetermin geändert" );
			e.setOldValue(handoverDateTime.toString());
			e.setNewValue(order.getHandoverDateTime().toString());
			e.setOrder(order.getEntity());
			result.add(e);
		}

		if (billDate.isEmpty()  &&  !order.getBillDate().isEmpty()) {
			OrderHistoryEntity e = new OrderHistoryEntity();
			e.setDescription("Rechnungsdatum eingetragen");
			e.setOldValue(billDate.toString());
			e.setNewValue(order.getBillDate().toString());
			e.setOrder(order.getEntity());
			result.add(e);
		}

		if (!customerPrice.equals(order.getCustomerPrice())) {
			OrderHistoryEntity e = new OrderHistoryEntity();
			if (customerPrice.isEmpty()) {
				e.setDescription(order.isSaleOrder() ? "Wunschpreis eingetragen" : "Wunschmiete eingetragen" );
			} else {
				e.setDescription(order.isSaleOrder() ? "Wunschpreis geändert" : "Wunschmiete geändert" );
			}
			e.setOldValue(customerPrice.toString());
			e.setNewValue(order.getCustomerPrice().toString());
			e.setOrder(order.getEntity());
			result.add(e);
		}
		if (!estimatedPrice.equals(order.getEstimatedPrice())) {
			OrderHistoryEntity e = new OrderHistoryEntity();
			if (estimatedPrice.isEmpty()) {
				e.setDescription(order.isSaleOrder() ? "Realistischer Preis eingetragen" : "Realistische Miete eingetragen" );
			} else {
				e.setDescription(order.isSaleOrder() ? "Realistischer Preis geändert" : "Realistische Miete geändert" );
			}
			e.setOldValue(estimatedPrice.toString());
			e.setNewValue(order.getEstimatedPrice().toString());
			e.setOrder(order.getEntity());
			result.add(e);
		}
		if (!settlementPrice.equals(order.getSettlementPrice())) {
			OrderHistoryEntity e = new OrderHistoryEntity();
			if (settlementPrice.isEmpty()) {
				e.setDescription(order.isSaleOrder() ? "Tatsächlicher Preis eingetragen" : "Tatsächliche Miete eingetragen" );
			} else {
				e.setDescription(order.isSaleOrder() ? "Wunschpreis geändert" : "Tatsächliche Miete geändert" );
			}
			e.setOldValue(settlementPrice.toString());
			e.setNewValue(order.getSettlementPrice().toString());
			e.setOrder(order.getEntity());
			result.add(e);
		}
		return result;
	}

}
