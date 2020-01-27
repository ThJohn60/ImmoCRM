package net.immocrm.gui.select;

import net.immocrm.domain.BaseDomain;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.dialog.BaseDialog;
import net.immocrm.gui.dialog.DialogResponse;

public class SelectionDialog<T extends BaseDomain, S extends SelectionRow<T>> {

	private final BaseDialog dialog;
	private final SelectionDialogController<T, S> dialogCtrl;


	public SelectionDialog(BaseDialog dialog, TableItemProvider<T, S> itemProvider, SelectionDialogController<T, S> ctrl) {
		this.dialog = dialog;
		this.dialogCtrl = ctrl;
		ctrl.setItemProvider(itemProvider);
	}


	public T showDialog() {
		dialogCtrl.showAllItems();
		while (true) {
			DialogResponse response = dialog.showAndWait();
			if (!response.isOkDone()) {
				return null;
			}
			S selectedItem = dialogCtrl.getSelectedItem();
			if (selectedItem != null) {
				return selectedItem.getDomainObject();
			}
			AlertProvider.alertInfo("Bitte einen Eintrag auswählen oder den Dialog abbrechen!");
		}
	}

}
