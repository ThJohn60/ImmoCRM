package net.immocrm.gui.geo;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import net.immocrm.domain.Address;
import net.immocrm.gui.BaseTab;

public class MapTab extends BaseTab {

	private Tab myTab;
	private MapViewController myCtrl;


	public MapTab(TabPane tabPane) {
		super(tabPane);
	}


	public void showTab(Address address) {
		FXMLLoader loader = getLoader("MapView.fxml");
		myTab = loadTab(loader);
		tabPane.getTabs().add(myTab);
		myCtrl = loader.getController();
		myCtrl.showAddressInMap(address);
		select(myTab);
	}


	@Override
	public void refresh() {
		// ignore
	}

}
