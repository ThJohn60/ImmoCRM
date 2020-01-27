package net.immocrm.gui.dialog;

import java.util.Optional;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;

public class BaseDialog {

	public static final String HEADER_LABEL = "#headerLabel";

	protected final Dialog<ButtonType> dialog;


	public BaseDialog(Dialog<ButtonType> dialog) {
		this.dialog = dialog;
		dialog.setResizable(true);
	}


	public DialogResponse showAndWait() {
		Optional<ButtonType> response = dialog.showAndWait();
		if (response.isPresent()) {
			return new DialogResponse(response.get().getButtonData());
		}
		return new DialogResponse(null);			// shouldn't happen
	}


	public final void setHeader(String header) {
		((Label)lookup(HEADER_LABEL)).setText(header);
	}

	public final Node lookup(String selector) {
		return getDialogPane().lookup(selector);
	}

	public final DialogPane getDialogPane() {
		return dialog.getDialogPane();
	}

	public final void setTitle(String title) {
		dialog.setTitle(title);
	}

	public final void setOnCloseRequest(EventHandler<DialogEvent> value) {
		dialog.setOnCloseRequest(value);
	}

}
