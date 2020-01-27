package net.immocrm.gui.alert;

import java.util.Optional;

import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.text.Text;
import net.immocrm.gui.IconProvider;

public class ConfirmationDialog {

	private AlertDialogBuilder builder;

	public ConfirmationDialog(Text... textList) {
		this.builder
			= new AlertDialogBuilder(false)
				.withYesNoButton()
				.withTitle("Bestätigung")
				.withHeader("Bestätigung")
				.withIcon(IconProvider.confirmationAlertIcon())
				.withMessage(textList);
	}

	public ConfirmationDialog(String msg) {
		this.builder
			= new AlertDialogBuilder(true)
				.withYesNoButton()
				.withTitle("Bestätigung")
				.withHeader("Bestätigung")
				.withIcon(IconProvider.confirmationAlertIcon())
				.withMessage(msg);
	}


	public boolean showAndWait() {
		Dialog<ButtonType> dialog = builder.build();
		Optional<ButtonType> response = dialog.showAndWait();
		return response.isPresent() && response.get().getButtonData() == ButtonData.YES;
	}


	public ConfirmationDialog withHeader(String header) {
		builder.withHeader(header);
		return this;
	}

	public ConfirmationDialog withDetailMessage(String msg) {
		builder.withDetailMessage(msg);
		return this;
	}

}
