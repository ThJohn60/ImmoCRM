package net.immocrm.gui.termin;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import net.immocrm.gui.BaseTab;
import net.immocrm.gui.refresh.RefreshedOrders;
import net.immocrm.gui.refresh.RefreshedPersons;

public class TerminListViewTab extends BaseTab {

	private TerminListViewController myCtrl;
	private Tab myTab;


	public TerminListViewTab(TabPane tabPane) {
		super(tabPane);
	}


	public void showTab() {
		if (myCtrl == null) {
			buildtab();
		}
		select(myTab);
		refresh();
		myCtrl.requestFocus();
	}


	private void buildtab() {
		FXMLLoader loader = getLoader("TerminListView.fxml");
		myTab = loadTab(loader);
		myCtrl = loader.getController();
		tabPane.getTabs().add(myTab);
		myTab.setOnClosed(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				myCtrl = null;
				myTab = null;
			}
		});
	}

	@Override
	public void refresh() {
		myCtrl.refreshByTerminAction();
	}

	@Override
	public void refreshOrders(RefreshedOrders orders) {
		refresh();
	}

	@Override
	public void refreshPersons(RefreshedPersons persons) {
		refresh();
	}

}
