package net.immocrm.gui.tools;

import javafx.fxml.FXML;
import net.immocrm.gui.MainController;

public class GotoItemController {

	@FXML
	public void gotoItem() {
		MainController.instance().gotoDetails();
	}

}
