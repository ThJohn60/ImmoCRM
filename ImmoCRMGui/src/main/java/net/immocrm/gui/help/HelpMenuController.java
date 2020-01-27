package net.immocrm.gui.help;

import javafx.fxml.FXML;
import net.immocrm.domain.Constants;
import net.immocrm.gui.BaseDialogBuilder;
import net.immocrm.gui.Main;
import net.immocrm.gui.dialog.BaseDialog;

public class HelpMenuController {


	@FXML
	public void help() {
		Main.getApplicationHostServices().showDocument("http://immocrm.net/help.html");
	}

	@FXML
	public void about() {
		BaseDialog dialog = new BaseDialogBuilder("help/AboutDialog.fxml")
				.withOkButton()
				.withTitle("Über " + Constants.APPLICATION_NAME + "...")
				.build();
		dialog.showAndWait();
	}

}
