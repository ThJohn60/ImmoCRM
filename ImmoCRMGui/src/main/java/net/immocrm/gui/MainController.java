package net.immocrm.gui;

import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import net.immocrm.domain.Address;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Notar;
import net.immocrm.domain.Order;
import net.immocrm.domain.Person;
import net.immocrm.domain.PreferenceManager;
import net.immocrm.gui.geo.MapTab;
import net.immocrm.gui.money.UmsatzTab;
import net.immocrm.gui.notar.NotarTableTab;
import net.immocrm.gui.order.OrderDetailTab;
import net.immocrm.gui.order.OrderTableTab;
import net.immocrm.gui.overview.OverviewTreeSelectionListener;
import net.immocrm.gui.person.PersonDetailTab;
import net.immocrm.gui.person.PersonTableTab;
import net.immocrm.gui.property.ImmobilieDetailTab;
import net.immocrm.gui.property.ImmobilieTableTab;
import net.immocrm.gui.refresh.RefreshDispatcher;
import net.immocrm.gui.termin.CalendarTab;
import net.immocrm.gui.termin.TerminListViewTab;
import net.immocrm.gui.transfer.AddressImportDialog;
import net.immocrm.gui.transfer.PersonImportDialog;

public class MainController implements Initializable, OverviewTreeSelectionListener, AppCloseListener {

	private static MainController me;

	public static MainController instance() {
		return me;
	}

	public static RefreshDispatcher getRefreshDispatcher() {
		return me.refreshDispatcher;
	}

	@FXML TabPane mainTabPane;
	@FXML SplitPane splitPane;
	@FXML ImageView backgroundImageView;

	private final RefreshDispatcher refreshDispatcher;
	private final List<ImmoCrmTab> tabList;


	public MainController() {
		MainController.me = this;
		tabList = new LinkedList<>();
		refreshDispatcher = new RefreshDispatcher(tabList);
	}


	@Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		mainTabPane.getTabs().addListener((ListChangeListener<Tab>)c ->	backgroundImageView.setVisible(mainTabPane.getTabs().isEmpty()));
		Main.addCloseListener(this);
    }

	public Order getOrder() {
		for (ImmoCrmTab t : tabList) {
			Order o = t.getSelectedOrder();
			if (o != null) {
				return o;
			}
		}
		return null;
	}

	public Immobilie getImmobilie() {
		for (ImmoCrmTab t : tabList) {
			Immobilie i = t.getSelectedImmobilie();
			if (i != null) {
				return i;
			}
		}
		return null;
	}

	public Person getPerson() {
		for (ImmoCrmTab t : tabList) {
			Person p = t.getSelectedPerson();
			if (p != null) {
				return p;
			}
		}
		return null;
	}

	public Notar getNotar() {
		for (ImmoCrmTab t : tabList) {
			if (t instanceof NotarTableTab) {
				return ((NotarTableTab)t).getSelectedNotar();
			}
		}
		return null;
	}

	@FXML
	public void openPersonTableTab() {
		PersonTableTab personTab = new PersonTableTab(mainTabPane, refreshDispatcher);
		personTab.showTab();
		tabList.add(personTab);
	}

	@FXML
	public void openImmobilieTableTab() {
		ImmobilieTableTab immoTab = new ImmobilieTableTab(mainTabPane, refreshDispatcher);
		immoTab.showTab();
		tabList.add(immoTab);
	}

	@FXML
	public void openOrderTableTab() {
		OrderTableTab orderTab = new OrderTableTab(mainTabPane, refreshDispatcher);
		orderTab.showTab();
		tabList.add(orderTab);
	}

	@FXML
	public void openNotare() {
		NotarTableTab notarTab = new NotarTableTab(mainTabPane, refreshDispatcher);
		notarTab.showTab();
		tabList.add(notarTab);
	}

	@FXML
	public void openTerminList() {
		TerminListViewTab terminTab = new TerminListViewTab(mainTabPane);
		terminTab.showTab();
		tabList.add(terminTab);
	}

	@FXML
	public void openCalendar() {
		CalendarTab calendarTab = new CalendarTab(mainTabPane);
		calendarTab.showTab();
		tabList.add(calendarTab);
	}


	@FXML
	public void openUmsaetze() {
		UmsatzTab umsatzTab = new UmsatzTab(mainTabPane);
		umsatzTab.showTab();
		tabList.add(umsatzTab);
	}

	public void openMap() {
		if (getOrder() != null) {
			openMapTab(getOrder().getImmobilieAddress());
		} else if (getImmobilie() != null) {
			openMapTab(getImmobilie().getAddress());
		} else if (getPerson() != null) {
			openMapTab(getPerson().getHomeAddress());
		} else if (getNotar() != null) {
			openMapTab(getNotar().getAddress());
		}
	}

	public void openMapTab(Address address) {
		MapTab mapTab = new MapTab(mainTabPane);
		mapTab.showTab(address);
	}



	@FXML
	public void closeSelectedTab() {
		ImmoCrmTab foundTab = null;
		for (ImmoCrmTab tab : tabList) {
			if (tab.isSelected()) {
				foundTab = tab;
				break;
			}
		}
		if (foundTab != null) {
			closeTab(foundTab);
		}
	}

	@FXML
	public void closeAllTabs() {
		for (ImmoCrmTab tab : tabList) {
			deactivateHandler(tab);
		}
		tabList.clear();
		mainTabPane.getTabs().clear();
	}

	@FXML
	public void closeOtherTabs() {
		Iterator<ImmoCrmTab> iterator = tabList.iterator();
		while (iterator.hasNext()) {
			ImmoCrmTab tab = iterator.next();
			if (!tab.isSelected()) {
				deactivateHandler(tab);
				mainTabPane.getTabs().remove(tab.getTab());
				iterator.remove();
			}
		}
	}

	public void closeTab(ImmoCrmTab t) {
		deactivateHandler(t);
		mainTabPane.getTabs().remove(t.getTab());
		tabList.remove(t);
	}

	private void deactivateHandler(ImmoCrmTab tab) {
		EventHandler<Event> handler = tab.getTab().getOnClosed();
		if (handler != null) {
			handler.handle(null);
		}
	}



	public void restoreDividerPos() {
		PreferenceManager prefMan = new PreferenceManager();
		String pos = prefMan.fetchString(PreferenceManager.OVERVIEW_VIEW_DIVIDER_POS);
		if (pos != null) {
			splitPane.setDividerPositions(Float.parseFloat(pos));
		} else {
			splitPane.getDividers().get(0).setPosition(0.7);
		}
	}

	public void gotoDetails() {
		if (getOrder() != null) {
			showOrderDetails(getOrder());
		} else if (getImmobilie() != null) {
			showImmobileDetails(getImmobilie());
		} else if (getPerson() != null) {
			showPersonDetails(getPerson());
		}
	}

	@Override
	public void showPersonDetails(Person person) {
		PersonDetailTab tab = new PersonDetailTab(mainTabPane);
		tab.showTab(person);
		addDetailtab(tab);
	}

	@Override
	public void showImmobileDetails(Immobilie immobilie) {
		ImmobilieDetailTab tab = new ImmobilieDetailTab(mainTabPane);
		tab.showTab(immobilie);
		addDetailtab(tab);
	}

	@Override
	public void showOrderDetails(Order order) {
 		OrderDetailTab tab = new OrderDetailTab(mainTabPane);
		tab.showTab(order);
		addDetailtab(tab);
	}

	private void addDetailtab(ImmoCrmTab tab) {
		tabList.add(tab);
	}


	@Override
	public void closeAction() {
		PreferenceManager prefMan = new PreferenceManager();
		prefMan.save(PreferenceManager.OVERVIEW_VIEW_DIVIDER_POS, Double.toString(splitPane.getDividerPositions()[0]));
	}


	@FXML
	public void pageDown() {
		ScrollPane sp = getScrollPane();
		if (sp != null) {
			sp.setVvalue(sp.getVvalue() + 0.8);
		}
	}

	@FXML
	public void pageUp() {
		ScrollPane sp = getScrollPane();
		if (sp != null) {
			sp.setVvalue(sp.getVvalue() - 0.8);
		}
	}

	private ScrollPane getScrollPane() {
		for (Tab tab : mainTabPane.getTabs()) {
			if (tab.isSelected()) {
				BaseTab baseTab = (BaseTab)tab.getUserData();
				if (baseTab != null) {
					return baseTab.getScrollPane();
				}
				break;
			}
		}
		return null;
	}

	@FXML
	public void importPersons() {
		new PersonImportDialog().showDialog();
	}

	@FXML
	public void importAddresses() {
		new AddressImportDialog().showDialog();
	}

}
