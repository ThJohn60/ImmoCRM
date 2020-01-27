package net.immocrm.domain;

import java.util.List;

public interface Notar extends BaseDomain, PersonName, Contact, Address {

	void setName(String name);
	Address getAddress();
	String getNameAndAddress();

	List<Order> getOrders();

}