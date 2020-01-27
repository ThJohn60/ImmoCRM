package net.immocrm.domain;

import java.util.List;

import net.immocrm.domain.ref.OrderStateEnum;

public class OrderSettlementmanager {

	private final OrderManager orderMan;
	private final ImmobilieTransactionManager immoTransMan;


	public OrderSettlementmanager() {
		orderMan = new OrderManager();
		immoTransMan = new ImmobilieTransactionManager();
	}


	public void changeOwner(Order o) {
		OrderDomain order = (OrderDomain) o;
		try {
			immoTransMan.startTransaction();

			ImmoTransactionDomain beforeSale = beforeSaleTransaction(order);
			if (beforeSale != null) {
				immoTransMan.persist(beforeSale);
			}
			ImmoTransactionDomain afterSale = newAfterSaleTransaction(order);
			immoTransMan.persist(afterSale);

			order.setOrderState(OrderStateEnum.HANDOVER);
			order.getImmobilie().setOwner(order.getBuyer());
			order.getImmobilie().setPurchaseDate(order.getHandoverDateTime().toImmoDate());
			order.getImmobilie().setPurchasePrice(order.getSettlementPrice());
			orderMan.persist(order);

			immoTransMan.commit();
		} catch (RuntimeException e) {
			immoTransMan.rollbackSave(e);
			throw e;
		}
	}


	private ImmoTransactionDomain newAfterSaleTransaction(OrderDomain order) {
		ImmoTransactionDomain result = new ImmoTransactionDomain();
		result.setImmobilie(order.getImmobilie());
		result.setIsSaleEvent(order.isSaleOrder());
		result.setNotice(order.getImmobilie().getInternalNotice());
		result.setOrder(order);
		result.setPerson(order.getBuyer());
		result.setPrice(order.getSettlementPrice());
		result.setStartDate(order.getSettlementDateTime().toImmoDate());
		return result;
	}

	private ImmoTransactionDomain beforeSaleTransaction(OrderDomain order) {
		List<ImmoTransaction> list = immoTransMan.fetchTransactions(order.getImmobilie());
		if (list.isEmpty()) {
			return newBeforeSaleTransaction(order, order.getImmobilie());
		}
		ImmoTransactionDomain aftersale = findbeforeSaleTransaction(order, list);
		if (aftersale != null) {
			aftersale.setEndDate(order.getSettlementDateTime().toImmoDate());
			return aftersale;
		}
		return null;
	}

	private ImmoTransactionDomain newBeforeSaleTransaction(OrderDomain order, Immobilie immobilie) {
		ImmoTransactionDomain result = new ImmoTransactionDomain();
		result.setImmobilie(immobilie);
		result.setIsSaleEvent(order.isSaleOrder());
		result.setNotice(immobilie.getInternalNotice());
		result.setPerson(order.getCustomer());
		result.setEndDate(order.getSettlementDateTime().toImmoDate());
		// TODO copy qualities from immo to transaction
		return result;
	}

	private ImmoTransactionDomain findbeforeSaleTransaction(OrderDomain order, List<ImmoTransaction> list) {
		boolean saleEvent = order.isSaleOrder();
		if (saleEvent) {
			for (ImmoTransaction e : list) {
				if (e.isSaleEvent() && e.getPerson().equals(order.getCustomer())) {
					return (ImmoTransactionDomain)e;
				}
			}
		} else {
			for (ImmoTransaction e : list) {
				if (e.getEndDate() == null  &&  !e.isSaleEvent()) {
					return (ImmoTransactionDomain)e;
				}
			}
		}
		return null;
	}

}
