package net.immocrm.gui.termin;

import net.immocrm.domain.IndependentTermin;
import net.immocrm.domain.TerminManager;
import net.immocrm.domain.termin.Termin;
import net.immocrm.domain.valid.DbException;
import net.immocrm.domain.vc.IncorrectValueException;
import net.immocrm.gui.FormDialogBuilder;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.dialog.DialogResponse;
import net.immocrm.gui.dialog.DialogSaveValidator;
import net.immocrm.gui.form.FormDialog;
import net.immocrm.gui.form.NoActionException;

public class TerminDialog {

	private TerminDialogController ctrl;


	public Termin showDialog() {
		return showDialog(new IndependentTermin());

	}

	public Termin showDialog(Termin termin) {
		FormDialogBuilder builder = createFormDialog(termin);
		FormDialog dlg = builder.build();
		ctrl = builder.getController();
		ctrl.terminToForm(termin);
		DialogResponse response = dlg.showAndWait();
		if (response.isOkDone()) {
			return termin;
		}
		return null;
	}


	private FormDialogBuilder createFormDialog(Termin termin) {
		String fxmlFileName = termin.isIndependentTermin() ?  "termin/IndependentTerminDialog.fxml" : "termin/TerminDialog.fxml";
		return new FormDialogBuilder(fxmlFileName)
				.withTitle("Termin")
				.withHeader(termin.getCategoryName())
				.withOkCancelButtons()
				.setOnCloseRequestOkHandler(getDialogSaveHandler(termin));
	}

	private DialogSaveValidator getDialogSaveHandler(Termin termin) {
		return event -> {
			try {
				ctrl.terminFromForm(termin);
				return save(termin);
			} catch (NoActionException e) {
			} catch (IncorrectValueException e) {
				AlertProvider.alertException(e);
			} catch (DbException e) {
				AlertProvider.alertException(e);
			} catch (Exception e) {
				AlertProvider.alertError(e.getMessage());
				e.printStackTrace();
			}
			return false;
		};
	}

	private boolean save(Termin termin) {
		new TerminManager().save(termin);
		return true;
	}

}
