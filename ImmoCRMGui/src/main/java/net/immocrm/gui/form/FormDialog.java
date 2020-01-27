package net.immocrm.gui.form;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import net.immocrm.gui.dialog.BaseDialog;
import net.immocrm.gui.dialog.DialogResponse;

public class FormDialog extends BaseDialog {

	private final FormChangeListener changeListener;


	public FormDialog(Dialog<ButtonType> dialog) {
		super(dialog);
		this.changeListener = new FormChangeListener();
	}


	@Override
	public final DialogResponse showAndWait() {
		changeListener.addChangeListenerToProperties(getDialogPane());
		return super.showAndWait();
	}

	public boolean isAnyFieldChanged() {
		return changeListener.isAnyFieldChanged();
	}

}
