package net.immocrm.gui.refresh;

import java.util.List;

import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Notar;
import net.immocrm.domain.Order;
import net.immocrm.domain.Person;
import net.immocrm.gui.ImmoCrmTab;
import net.immocrm.gui.notar.NotarTableTab;
import net.immocrm.gui.overview.OverviewTreeController;

public class RefreshDispatcher {

	private DomainViewFilter filter;
	private OverviewTreeController overviewTreeCtrl;

	private final RefreshedDomainFactory factory;
	private final List<ImmoCrmTab> tabList;


	public RefreshDispatcher(List<ImmoCrmTab> tabList) {
		this.tabList = tabList;
		this.factory = new RefreshedDomainFactory();
	}


	public DomainViewFilter getFilter() {
		return filter;
	}

	public void setFilter(DomainViewFilter filter) {
		this.filter = filter;
	}

	public void setOverviewTreeCtrl(OverviewTreeController ctrl) {
		this.overviewTreeCtrl = ctrl;
	}


	public RefreshedPersons getRefreshedPersons() {
		return factory.refreshedPersons(filter.getFilterPattern());
	}

	public RefreshedImmobilien getRefreshedImmobilien() {
		return factory.refreshedImmobilien(filter.getImmobilieFilter());
	}

	public RefreshedOrders getRefreshedOrders() {
		return factory.refreshedOrders(filter);
	}


	public void refreshAll() {
		for (ImmoCrmTab immoCrmTab : tabList) {
			immoCrmTab.refresh();
		}
		overviewTreeCtrl.refreshAll();
	}

	public void refreshInitial() {
		overviewTreeCtrl.refreshInitial();
		DomainFilterController.requestFocus();
	}


	public void refreshImmobilieViews(Immobilie immobilie, RefreshType type) {
		if (immobilie == null) {
			return;
		}
		RefreshedImmobilien immobilien = factory.refreshedImmobilien(immobilie, filter.getImmobilieFilter(), type);
		for (ImmoCrmTab immoCrmTab : tabList) {
			immoCrmTab.refreshImmobilie(immobilien);
		}
		overviewTreeCtrl.refresh(immobilien);
	}


	public void refreshPersonViews(Person person, RefreshType type) {
		if (person == null) {
			return;
		}
		RefreshedPersons persons = factory.refreshedPersons(person, filter.getFilterPattern(), type);
		for (ImmoCrmTab immoCrmTab : tabList) {
			immoCrmTab.refreshPersons(persons);
		}
		overviewTreeCtrl.refresh(persons);
	}


	public void refreshOrderViews(Order order, RefreshType type) {
		if (order == null) {
			return;
		}
		if (order.getImmobilie().getOwner().isNew()) {
			RefreshedPersons persons = factory.refreshedPersons(order.getImmobilie().getOwner(), filter.getFilterPattern(), RefreshType.insertItem);
			for (ImmoCrmTab immoCrmTab : tabList) {
				immoCrmTab.refreshPersons(persons);
			}
		}
		RefreshType immoRefreshType = order.getImmobilie().isNew() ? RefreshType.insertItem : RefreshType.updateItem;
		RefreshedImmobilien immobilien = factory.refreshedImmobilien(order.getImmobilie(), filter.getImmobilieFilter(), immoRefreshType);
		for (ImmoCrmTab immoCrmTab : tabList) {
			immoCrmTab.refreshImmobilie(immobilien);
		}
		RefreshedOrders orders = factory.refreshedOrders(order, filter, type);
		for (ImmoCrmTab immoCrmTab : tabList) {
			immoCrmTab.refreshOrders(orders);
		}
		overviewTreeCtrl.refreshAll();
	}


	public RefreshedNotars getRefreshedNotars() {
		return factory.refreshedNotars(filter.getFilterPattern());
	}

	public void refreshNotarViews(Notar notar, RefreshType type) {
		RefreshedNotars notare = factory.refreshedNotars(notar, filter.getFilterPattern(), type);
		for (ImmoCrmTab immoCrmTab : tabList) {
			if (immoCrmTab instanceof NotarTableTab) {
				((NotarTableTab)immoCrmTab).refresh(notare);
			}
		}
	}

}
