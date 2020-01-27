package net.immocrm.domain;

public interface OrderHistory extends BaseDomain, Comparable<OrderHistory> {

	Order getOrder();
	String getDescription();

	String getOldValue();
	String getNewValue();

}