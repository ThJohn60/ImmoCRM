package net.immocrm.gui.transfer;

import net.immocrm.domain.valid.DbException;
import net.immocrm.gui.FormDialogBuilder;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.dialog.DialogSaveValidator;
import net.immocrm.gui.form.FormDialog;
import net.immocrm.gui.form.NoActionException;

public class AddressImportDialog {

	private AddressImportController ctrl;


	public void showDialog() {
		FormDialogBuilder builder = createFormDialog();
		FormDialog dlg = builder.build();
		ctrl = builder.getController();
		ctrl.setOkButton(builder.getOkButton());
		dlg.showAndWait();
	}


	private FormDialogBuilder createFormDialog() {
		String fxmlFileName = "transfer/AddressImportDialog.fxml";
		return new FormDialogBuilder(fxmlFileName)
				.withTitle("Import")
				.withHeader("Adressen importieren")
				.withOkCancelButtons()
				.setOnCloseRequestOkHandler(getDialogSaveHandler());
	}

	private DialogSaveValidator getDialogSaveHandler() {
		return event -> {
			try {
				ctrl.importAddresses();
				return true;
			} catch (NoActionException e) {
			} catch (DbException e) {
				AlertProvider.alertException(e);
			} catch (Exception e) {
				AlertProvider.alertError(e.getMessage());
				e.printStackTrace();
			}
			return false;
		};
	}

}
