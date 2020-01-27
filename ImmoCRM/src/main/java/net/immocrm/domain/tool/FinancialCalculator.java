package net.immocrm.domain.tool;

import net.immocrm.domain.Order;
import net.immocrm.domain.vc.Percent;
import net.immocrm.domain.vc.Price;

public class FinancialCalculator {

	public static Price calcSaleProvision(Price price, Order order) {
		return calcSaleProvision(price, order.getCustomerProvision(), order.getBuyerProvision());
	}

	public static Price calcSaleProvision(Price price, Percent customerProvision, Percent purchaserProvision) {
		return price.multiply(customerProvision).plus(price.multiply(purchaserProvision));
	}

	public static Price calcRentProvision(Price rent, Number monthCount) {
		return rent.multiply(monthCount);
	}

}
