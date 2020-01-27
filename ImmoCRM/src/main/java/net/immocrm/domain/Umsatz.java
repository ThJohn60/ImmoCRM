package net.immocrm.domain;

import net.immocrm.domain.tool.FinancialCalculator;
import net.immocrm.domain.vc.ImmoDate;
import net.immocrm.domain.vc.Price;
import net.immocrm.domain.vc.Year;

public class Umsatz implements Comparable<Umsatz>, BaseUmsatz {

	private final Order order;
	private final ImmoDate date;


	public Umsatz(Order order) {
		this.order = order;
		date = findDate(order);
	}

	private static ImmoDate findDate(Order order) {
		if (!order.getSettlementDateTime().isEmpty()) {
			return order.getSettlementDateTime().toImmoDate();
		}
		if (!order.getHandoverDateTime().isEmpty()) {
			return order.getHandoverDateTime().toImmoDate();
		}
		return order.getBillDate();
	}


	public Year getYear() {
		return getDate().getYear();
	}

	public ImmoDate getDate() {
		return date;
	}


	@Override
	public Order getOrder() {
		return order;
	}

	@Override
	public Price getUmsatz() {
		if (order.isSaleOrder()) {
			return FinancialCalculator.calcSaleProvision(order.getSettlementPrice(), order);
		}
		return FinancialCalculator.calcRentProvision(order.getSettlementPrice(), order.getCustomerProvision().toNumber());
	}

	@Override
	public String getDateInfo() {
		return getDate().toString();
	}

	@Override
	public String getDetails() {
		return TextMaker.INSTANCE.getDetailsHeader(order);
	}


	@Override
	public int compareTo(Umsatz o) {
		if  (getDate().isAfter(o.getDate())) {
			return 1;
		}
		if  (getDate().isBefore(o.getDate())) {
			return -1;
		}
		return 0;
	}

}
