package net.immocrm.gui.property;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.ImmobilieReader;
import net.immocrm.gui.BaseTab;
import net.immocrm.gui.IconProvider;
import net.immocrm.gui.MainController;
import net.immocrm.gui.refresh.RefreshedImmobilien;
import net.immocrm.gui.refresh.RefreshedOrders;
import net.immocrm.gui.refresh.RefreshedPersons;

public class ImmobilieDetailTab extends BaseTab {

	private ImmobilieDetailController myCtrl;
	private Tab myTab;
	private Immobilie immobilie;
	private ImmobilieDetailTab instance;


	public ImmobilieDetailTab(TabPane tabPane) {
		super(tabPane);
		instance = this;
	}


	public Immobilie getImmobilie() {
		return immobilie;
	}


	public void showTab(Immobilie im) {
		buildtab(im);
		select(myTab);
//		myCtrl.requestFocus();
	}

	private void buildtab(Immobilie im) {
		this.immobilie = im;
		FXMLLoader loader = getLoader("ImmobilieDetailView.fxml");
		myTab = loadTab(loader);
		myTab.setText(im.getAddress().getStreet());
		myTab.setGraphic(IconProvider.immobilieIcon(im));
		myTab.setUserData(this);
		tabPane.getTabs().add(myTab);

		myCtrl = loader.getController();
		myCtrl.showImmobilie(im);
		super.setScrollPane(myCtrl.getScrollPane());
	}

	@Override
	public Immobilie getSelectedImmobilie() {
		if (!myTab.isSelected()) {
			return null;
		}
		return immobilie;
	}

	@Override
	public void refresh() {
		immobilie = ImmobilieReader.INSTANCE.fetchById(immobilie.getId());
		myTab.setText(immobilie.getAddress().getStreet());
		myCtrl.showImmobilie(immobilie);
	}

	@Override
	public void refreshImmobilie(RefreshedImmobilien immobilien) {
		if (immobilie.equals(immobilien.getDomain())) {
			if (!immobilien.isDelete()) {
				immobilie = immobilien.getDomain();
				myTab.setText(immobilie.getAddress().getStreet());
				myCtrl.showImmobilie(immobilie);
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
	public void refreshPersons(RefreshedPersons persons) {
		if (immobilie.getOwner().equals(persons.getDomain())) {
			immobilie.setOwner(persons.getDomain());
			myCtrl.showImmobilie(immobilie);
		}
	}

	@Override
	public void refreshOrders(RefreshedOrders orders) {
		if (immobilie.equals(orders.getDomain().getImmobilie())) {
			refresh();
		}
	}

}
