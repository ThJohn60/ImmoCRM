package net.immocrm.gui.refresh;

import java.util.List;

import net.immocrm.domain.Immobilie;
import net.immocrm.domain.ImmobilieReader;
import net.immocrm.domain.Notar;
import net.immocrm.domain.NotarReader;
import net.immocrm.domain.Order;
import net.immocrm.domain.OrderReader;
import net.immocrm.domain.Person;
import net.immocrm.domain.PersonReader;

class RefreshedDomainFactory {

	public RefreshedImmobilien refreshedImmobilien(ImmobilieFilter filter) {
		List<Immobilie> immoList = ImmobilieReader.INSTANCE.find(filter.getFilterPattern(), filter.categorySet());
		RefreshType type = filter.isFilterActive() ? RefreshType.filtered : RefreshType.all;
		RefreshedImmobilien result = new RefreshedImmobilien(null, immoList, filter, type);
		return result;
	}

	public RefreshedImmobilien refreshedImmobilien(Immobilie immobilie, ImmobilieFilter filter, RefreshType type) {
		List<Immobilie> immoList = ImmobilieReader.INSTANCE.find(filter.getFilterPattern(), filter.categorySet());
		if (filter.isFilterActive()  &&  type == RefreshType.all) {
			type = RefreshType.filtered;
		}
		RefreshedImmobilien result = new RefreshedImmobilien(immobilie, immoList, filter, type);
		return result;
	}


	public RefreshedPersons refreshedPersons(String filterPattern) {
		List<Person> personList = PersonReader.INSTANCE.find(filterPattern);
		RefreshType type = filterPattern != null && !filterPattern.isEmpty() ? RefreshType.filtered : RefreshType.all;
		RefreshedPersons result = new RefreshedPersons(null, personList, filterPattern, type);
		return result;
	}

	public RefreshedPersons refreshedPersons(Person person, String filterPattern, RefreshType type) {
		List<Person> personList = PersonReader.INSTANCE.find(filterPattern);
		if (filterPattern != null && !filterPattern.isEmpty()  &&  type == RefreshType.all) {
			type = RefreshType.filtered;
		}
		RefreshedPersons result = new RefreshedPersons(person, personList, filterPattern, type);
		return result;
	}


	public RefreshedOrders refreshedOrders(DomainViewFilter filter) {
		List<Order> orders = fetchOrders(filter);
		RefreshType type = filter.isFilterActive() ? RefreshType.filtered : RefreshType.all;
		RefreshedOrders result = new RefreshedOrders(null, orders, filter, type);
		return result;
	}

	public RefreshedOrders refreshedOrders(Order order, DomainViewFilter filter, RefreshType type) {
		List<Order> orders = fetchOrders(filter);
		if (filter.isFilterActive()  &&  type == RefreshType.all) {
			type = RefreshType.filtered;
		}
		RefreshedOrders result = new RefreshedOrders(order, orders, filter, type);
		return result;
	}

	private List<Order> fetchOrders(DomainViewFilter filter) {
		if (filter.isFilterActive()) {
			return OrderReader.INSTANCE.find(filter.getFilterPattern(), filter.orderTypeSet(), filter.isActiveFilterSelected(), filter.categorySet());
		}
		return OrderReader.INSTANCE.readOrders();
	}


	public RefreshedNotars refreshedNotars(String filterPattern) {
		List<Notar> personList = NotarReader.INSTANCE.find(filterPattern);
		RefreshType type = filterPattern != null && !filterPattern.isEmpty() ? RefreshType.filtered : RefreshType.all;
		RefreshedNotars result = new RefreshedNotars(null, personList, filterPattern, type);
		return result;
	}

	public RefreshedNotars refreshedNotars(Notar notar, String filterPattern, RefreshType type) {
		List<Notar> personList = NotarReader.INSTANCE.find(filterPattern);
		RefreshedNotars result = new RefreshedNotars(notar, personList, filterPattern, type);
		return result;
	}

}
