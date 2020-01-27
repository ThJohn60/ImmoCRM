package net.immocrm.gui;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import net.immocrm.domain.Constants;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Order;
import net.immocrm.domain.Person;
import net.immocrm.gui.refresh.RefreshedImmobilien;
import net.immocrm.gui.refresh.RefreshedOrders;
import net.immocrm.gui.refresh.RefreshedPersons;

public abstract class BaseTab implements ImmoCrmTab {

	protected final TabPane tabPane;
	private Tab myTab;
	private ScrollPane scrollPane;


	public BaseTab(TabPane tabPane) {
		this.tabPane = tabPane;
	}


	@Override
	public Tab getTab() {
		return myTab;
	}

	@Override
	public boolean isSelected() {
		return myTab != null && myTab.isSelected();
	}


	protected FXMLLoader getLoader(String fxmlFileName) {
		URL location = getClass().getResource(fxmlFileName);
		FXMLLoader fxmlLoader = new FXMLLoader(location);
		return fxmlLoader;
	}

	protected Tab loadTab(FXMLLoader fxmlLoader) {
		try {
			Tab root = (Tab)fxmlLoader.load();
			return root;
		} catch (IOException e) {
			// ignore
			if (Constants.DEBUG_MODE) {
				e.printStackTrace();
			}
		}
		return null;
	}

	protected Tab getTabById(String id) {
		Optional<Tab> tab = tabPane.getTabs().stream()
				.filter(t -> t.getId().equals(id))
				.findFirst();
		return (tab.isPresent() ? tab.get() : null);
	}

	protected void select(Tab tab) {
		this.myTab = tab;
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
		selectionModel.select(tab);
	}

	public ScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(ScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}


	@Override
	public void refreshPersons(RefreshedPersons persons) {

	}

	@Override
	public void refreshImmobilie(RefreshedImmobilien immobilien) {

	}

	@Override
	public void refreshOrders(RefreshedOrders orders) {

	}

	@Override
	public Order getSelectedOrder() {
		return null;
	}

	@Override
	public Immobilie getSelectedImmobilie() {
		return null;
	}

	@Override
	public Person getSelectedPerson() {
		return null;
	}

}
