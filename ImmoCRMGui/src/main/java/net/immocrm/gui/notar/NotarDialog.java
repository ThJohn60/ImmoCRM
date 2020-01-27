package net.immocrm.gui.notar;

import net.immocrm.domain.DomainFactory;
import net.immocrm.domain.Notar;
import net.immocrm.domain.NotarManager;
import net.immocrm.domain.TextMaker;
import net.immocrm.domain.valid.StdException;
import net.immocrm.domain.valid.ValidationIssues;
import net.immocrm.gui.FormDialogBuilder;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.dialog.DialogResponse;
import net.immocrm.gui.dialog.DialogSaveValidator;
import net.immocrm.gui.form.FormDialog;
import net.immocrm.gui.form.NoActionException;

public class NotarDialog {

	private NotarDialogController ctrl;


	public Notar showDialog() {
		return showDialog(DomainFactory.newNotar());
	}

	public Notar showDialog(Notar notar) {
		FormDialogBuilder builder = new FormDialogBuilder("notar/NotarDialog.fxml")
				.withTitle(notar)
				.withHeader(notar.isNew() ? "Neuer Notar" : TextMaker.INSTANCE.getDialogHeader(notar))
				.withOkCancelButtons()
				.setOnCloseRequestOkHandler(getDialogSaveHandler(notar));
		ctrl = builder.getController();
		FormDialog dlg = builder.build();
		ctrl.notarToForm(notar);
		DialogResponse response = dlg.showAndWait();
		if (response.isOkDone()) {
			return notar;
		}
		return null;
	}

	private DialogSaveValidator getDialogSaveHandler(Notar notar) {
		return event -> {
			try {
				ctrl.notarFromForm(notar);
				ValidationIssues issues = notar.validate();
				if (issues.hasErrors()) {
					AlertProvider.alertIssues(issues);
					return false;
				}
				NotarManager notarMan = new NotarManager();
				notarMan.save(notar);
				return true;
			} catch (NoActionException e) {
			} catch (StdException e) {
				AlertProvider.alertError(e.getMessage());
			} catch (Exception e) {
				AlertProvider.alertException(e);
			}
			return false;
		};
	}

}
