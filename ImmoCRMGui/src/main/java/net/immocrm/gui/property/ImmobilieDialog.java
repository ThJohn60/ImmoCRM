package net.immocrm.gui.property;

import net.immocrm.domain.Immobilie;
import net.immocrm.domain.ImmobilieManager;
import net.immocrm.domain.ImmobilieReader;
import net.immocrm.domain.TextMaker;
import net.immocrm.domain.valid.ValidationIssues;
import net.immocrm.domain.vc.IncorrectValueException;
import net.immocrm.gui.FormDialogBuilder;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.dialog.DialogResponse;
import net.immocrm.gui.dialog.DialogSaveValidator;
import net.immocrm.gui.form.FormDialog;
import net.immocrm.gui.form.NoActionException;

public class ImmobilieDialog {

	private ImmobilieDialogController ctrl;


	public Immobilie showDialog(Immobilie immobilie) {
		return showDialog(immobilie, 0);
	}

	public Immobilie showDialog(Immobilie im, int showTabNr) {
		if (!im.isNew()) {
			im = ImmobilieReader.INSTANCE.fetchById(im.getId());
		}
		final Immobilie immobilie = im;
		FormDialogBuilder formBuilder = buildImmobilieDialog(immobilie, showTabNr);
		FormDialog dlg = formBuilder.build();
		ctrl = formBuilder.getController();

		ctrl.valuesToForm(immobilie);
		DialogResponse response = dlg.showAndWait();
		if (response.isOkDone()) {
			return immobilie;
		}
		return null;
	}

	public FormDialogBuilder buildImmobilieDialog(Immobilie immobilie, int showTabNr) {
		FormDialogBuilder formBuilder = new FormDialogBuilder("property/ImmobilieDialog.fxml")
				.withTitle(immobilie)
				.withHeader(TextMaker.INSTANCE.getDialogHeader(immobilie))
				.openTab(showTabNr)
				.withOkCancelButtons()
				.setOnCloseRequestOkHandler(getDialogSaveHandler(immobilie));
		return formBuilder;
	}

	private DialogSaveValidator getDialogSaveHandler(Immobilie immobilie) {
		return event -> {
			try {
				ctrl.valuesFromForm(immobilie);
				ValidationIssues issues = immobilie.validate();
				if (issues.hasErrors()) {
					AlertProvider.alertIssues(issues);
					return false;
				}
				ImmobilieManager immobilieMan = new ImmobilieManager();
				immobilieMan.save(immobilie);
				return true;
			} catch (NoActionException e) {
				e.printStackTrace();
				return false;
			} catch (IncorrectValueException e) {
				e.printStackTrace();
				AlertProvider.alertException(e);
			} catch (Exception e) {
				e.printStackTrace();
				AlertProvider.alertException(e);
			}
			return false;
		};
	}

}
