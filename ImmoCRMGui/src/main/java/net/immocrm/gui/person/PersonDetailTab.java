package net.immocrm.gui.person;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import net.immocrm.domain.Person;
import net.immocrm.domain.PersonReader;
import net.immocrm.gui.BaseTab;
import net.immocrm.gui.MainController;
import net.immocrm.gui.refresh.RefreshedImmobilien;
import net.immocrm.gui.refresh.RefreshedOrders;
import net.immocrm.gui.refresh.RefreshedPersons;

public class PersonDetailTab extends BaseTab {

	private PersonDetailController myCtrl;
	private Tab myTab;
	private Person person;
	private PersonDetailTab instance;


	public PersonDetailTab(TabPane tabPane) {
		super(tabPane);
		instance = this;
	}


	public void showTab(Person p) {
		buildtab(p);
		select(myTab);
//		myCtrl.requestFocus();
	}

	private void buildtab(Person p) {
		this.person = p;
		FXMLLoader loader = getLoader("PersonDetailView.fxml");
		myTab = loadTab(loader);
		myTab.setText(p.getName());
		myTab.setUserData(this);
		tabPane.getTabs().add(myTab);

		myCtrl = loader.getController();
		myCtrl.showPerson(p);
		super.setScrollPane(myCtrl.getScrollPane());
	}

	@Override
	public Person getSelectedPerson() {
		if (!myTab.isSelected()) {
			return null;
		}
		return person;
	}

	@Override
	public void refresh() {
		person = PersonReader.INSTANCE.fetchById(person.getId());
		myCtrl.showPerson(person);
	}


	@Override
	public void refreshPersons(RefreshedPersons persons) {
		if (person.equals(persons.getDomain())) {
			if (!persons.isDelete()) {
				person = persons.getDomain();
				myCtrl.showPerson(person);
			} else {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						MainController.instance().closeTab(instance);
					}

				});
			}
		}
	}

	@Override
	public void refreshImmobilie(RefreshedImmobilien immobilien) {
		if (person.equals(immobilien.getDomain().getOwner())) {
			refresh();
		}
	}

	@Override
	public void refreshOrders(RefreshedOrders orders) {
		if (person.equals(orders.getDomain().getCustomer())) {
			refresh();
		}
	}

}
