package net.immocrm.gui.create;

import net.immocrm.domain.BaseDomain;
import net.immocrm.domain.Constants;
import net.immocrm.domain.vc.IncorrectValueException;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.dialog.DialogResponse;
import net.immocrm.gui.dialog.DialogSaveValidator;
import net.immocrm.gui.form.NoActionException;

@SuppressWarnings("unused")
public abstract class AbstractCreationDialog<D extends BaseDomain> {

	protected final AssistentDialogFrame dlgFrame;


	public AbstractCreationDialog(AssistentDialogFrame dlgFrame) {
		this.dlgFrame = dlgFrame;
		dlgFrame.setOnCloseRequestHandler(getValidator());
	}

	protected DialogSaveValidator getValidator() {
		return event -> {
			return true;
		};
	}


	public DialogResponse showAndWait(D domain) {
		valuesToForm(domain);
		while (true) {
			try {
				DialogResponse response = dlgFrame.showAndWait();
				if (!response.isCanceled()) {
					valuesFromForm(domain);
				}
				return response;
			} catch (NoActionException e) {
			} catch (IncorrectValueException e) {
				AlertProvider.alertException(e);
			} catch (Exception e) {
				if (Constants.DEBUG_MODE) {
					e.printStackTrace();
				}
				AlertProvider.alertException(e);
			}
		}
	}

	protected void valuesToForm(D domain) {
		// default do nothing
	}

	protected void valuesFromForm(D domain) {
		// default do nothing
	}

}