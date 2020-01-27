package net.immocrm.gui.alert;

import java.util.Optional;

import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.text.Text;
import net.immocrm.gui.IconProvider;

public class WarningDialog {

	private AlertDialogBuilder builder;


	public WarningDialog(Text... textList) {
		this.builder
			= new AlertDialogBuilder(false)
				.withOkButton()
				.withTitle("Hinweis")
				.withHeader("Hinweis")
				.withIcon(IconProvider.warningAlertIcon())
				.withMessage(textList);
	}

	public WarningDialog(String msg) {
		this.builder
			= new AlertDialogBuilder(true)
				.withOkButton()
				.withTitle("Hinweis")
				.withHeader("Hinweis")
				.withIcon(IconProvider.warningAlertIcon())
				.withMessage(msg);
	}


	public boolean showAndWait() {
		Dialog<ButtonType> dialog = builder.build();
		Optional<ButtonType> response = dialog.showAndWait();
		return response.isPresent() && response.get().getButtonData() == ButtonData.YES;
	}


	public WarningDialog withHeader(String header) {
		builder.withHeader(header);
		return this;
	}

	public WarningDialog withDetailMessage(String msg) {
		builder.withDetailMessage(msg);
		return this;
	}

}
