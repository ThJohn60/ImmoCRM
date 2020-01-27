package net.immocrm.gui.geo;

import javafx.fxml.FXML;
import net.immocrm.gui.MainController;

public class MapMenuItemController {

	@FXML
	public void showInMap() {
		MainController.instance().openMap();
	}

}
