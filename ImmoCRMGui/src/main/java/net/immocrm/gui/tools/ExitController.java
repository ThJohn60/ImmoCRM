package net.immocrm.gui.tools;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class ExitController {

	@FXML
	public void exitProgram() {
		Platform.exit();
	}

}
