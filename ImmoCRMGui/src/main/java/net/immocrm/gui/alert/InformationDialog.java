package net.immocrm.gui.alert;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import net.immocrm.gui.IconProvider;

public class InformationDialog {

	private AlertDialogBuilder builder;


	public InformationDialog(String msg) {
		this.builder
			= new AlertDialogBuilder(true)
				.withOkButton()
				.withTitle("Hinweis")
				.withHeader("Hinweis")
				.withIcon(IconProvider.infoAlertIcon())
				.withMessage(msg);
	}


	public void showAndWait() {
		Dialog<ButtonType> dialog = builder.build();
		dialog.showAndWait();
	}


	public InformationDialog withHeader(String header) {
		builder.withHeader(header);
		return this;
	}

	public InformationDialog withDetailMessage(String msg) {
		builder.withDetailMessage(msg);
		return this;
	}

}
