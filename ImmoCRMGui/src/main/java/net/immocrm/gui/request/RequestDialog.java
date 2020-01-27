package net.immocrm.gui.request;

import net.immocrm.domain.Request;
import net.immocrm.domain.RequestManager;
import net.immocrm.domain.TextMaker;
import net.immocrm.domain.valid.DbException;
import net.immocrm.domain.valid.ValidationIssues;
import net.immocrm.domain.vc.IncorrectValueException;
import net.immocrm.gui.FormDialogBuilder;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.dialog.DialogResponse;
import net.immocrm.gui.dialog.DialogSaveValidator;
import net.immocrm.gui.form.FormDialog;
import net.immocrm.gui.form.NoActionException;

public class RequestDialog {

	private RequestDialogController ctrl;
	private boolean doSave;


	public RequestDialog() {
		this(true);
	}

	public RequestDialog(boolean doSave) {
		this.doSave = doSave;
	}


	public Request showDialog(Request request) {
		FormDialogBuilder builder = createFormDialog(request);
		FormDialog dlg = builder.build();
		ctrl = builder.getController();
		ctrl.requestToForm(request);
		DialogResponse response = dlg.showAndWait();
		if (response.isOkDone()) {
			return request;
		}
		return null;
	}


	private FormDialogBuilder createFormDialog(Request request) {
		return new FormDialogBuilder("request/RequestDialog.fxml")
				.withTitle(request)
				.withHeader(TextMaker.INSTANCE.getDialogHeader(request))
				.withOkCancelButtons()
				.setOnCloseRequestOkHandler(getDialogSaveHandler(request));
	}

	private DialogSaveValidator getDialogSaveHandler(Request request) {
		return event -> {
			try {
				ctrl.requestFromForm(request);
				if (request.getPurchaser() == null || request.getPurchaser().isEmpty()) {
					AlertProvider.alertWarning("Bitte einen Interessenten eingeben oder auswählen.");
					return false;
				}
				if (doSave) {
					return save(request);
				}
				return true;
			} catch (NoActionException e) {
			} catch (IncorrectValueException e) {
				AlertProvider.alertException(e);
			} catch (DbException e) {
				AlertProvider.alertException(e);
			} catch (Exception e) {
				AlertProvider.alertError(e.getMessage());
			}
			return false;
		};
	}

	private boolean save(Request request) {
		ValidationIssues issues = request.validate();
		if (issues.hasErrors()) {
			AlertProvider.alertIssues(issues);
			return false;
		}
		RequestManager requestMan = new RequestManager();
		requestMan.save(request);
		return true;
	}

}
