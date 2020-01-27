package net.immocrm.gui.alert;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.text.Text;
import net.immocrm.gui.IconProvider;

public class ErrorDialog {

	private final AlertDialogBuilder builder;


	public ErrorDialog(Text... textList) {
		this.builder
			= new AlertDialogBuilder(false)
				.withOkButton()
				.withMessage(textList)
				.withHeader("Fehler")
				.withIcon(IconProvider.errorAlertIcon())
				.withTitle("Fehler");
	}

	public ErrorDialog(String msg) {
		this.builder
			= new AlertDialogBuilder(true)
				.withOkButton()
				.withMessage(msg)
				.withHeader("Fehler")
				.withIcon(IconProvider.errorAlertIcon())
				.withTitle("Fehler");
	}


	public void showAndWait() {
		Dialog<ButtonType> dialog = builder.build();
		dialog.showAndWait();
	}

	public ErrorDialog withHeader(String header) {
		builder.withHeader(header);
		return this;
	}

	public ErrorDialog withDetails(String msg) {
		builder.withDetailMessage(msg);
		return this;
	}

}
