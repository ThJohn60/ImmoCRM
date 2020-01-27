package net.immocrm.gui.person;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import net.immocrm.domain.Person;
import net.immocrm.gui.BaseTab;
import net.immocrm.gui.refresh.RefreshDispatcher;
import net.immocrm.gui.refresh.RefreshedPersons;

public class PersonTableTab extends BaseTab {

	private final RefreshDispatcher refreshDispatcher;

	private PersonController myCtrl;
	private Tab myTab;


	public PersonTableTab(TabPane tabPane, RefreshDispatcher refreshDispatcher) {
		super(tabPane);
		this.refreshDispatcher = refreshDispatcher;
	}


	@Override
	public Person getSelectedPerson() {
		if (myTab != null && myCtrl != null && myTab.isSelected()) {
			return myCtrl.getSelectedItem();
		}
		return null;
	}

	public void showTab() {
		if (myCtrl == null) {
			buildtab();
			refresh();
		}
		select(myTab);
		myCtrl.requestFocus();
	}

	private void buildtab() {
		FXMLLoader loader = getLoader("PersonTableView.fxml");
		myTab = loadTab(loader);
		myCtrl = loader.getController();
		tabPane.getTabs().add(myTab);

//		myTab.setOnClosed(new EventHandler<Event>() {
//
//			@Override
//			public void handle(Event event) {
//				myCtrl = null;
//				myTab = null;
//			}
//		});

	}

	@Override
	public void refresh() {
		refreshPersons(refreshDispatcher.getRefreshedPersons());
	}

	@Override
	public void refreshPersons(RefreshedPersons persons) {
		myCtrl.refresh(persons);
	}

}
