package net.immocrm.gui.doc;

import net.immocrm.domain.Document;
import net.immocrm.domain.DocumentManager;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.img.DocFile;
import net.immocrm.domain.img.DocProvider;
import net.immocrm.domain.valid.DbException;
import net.immocrm.domain.vc.IncorrectValueException;
import net.immocrm.gui.FormDialogBuilder;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.dialog.DialogResponse;
import net.immocrm.gui.dialog.DialogSaveValidator;
import net.immocrm.gui.form.FormDialog;
import net.immocrm.gui.form.NoActionException;

public class DokuDialog {

	private DokuDialogController ctrl;

	public Document showDialog(Immobilie immo, Document doc) {
		return showDialog(immo, doc, doc.getDocFile());
	}

	public Document showDialog(Immobilie immo, Document doc, DocFile docFile) {
		FormDialogBuilder builder = createFormDialog(immo, doc);
		FormDialog dlg = builder.build();
		ctrl = builder.getController();
		ctrl.documentToForm(doc, docFile);
		DialogResponse response = dlg.showAndWait();
		if (response.isOkDone()) {
			return doc;
		}
		return null;
	}

	private FormDialogBuilder createFormDialog(Immobilie immo, Document doc) {
		return new FormDialogBuilder("doc/DokuDialog.fxml")
				.withTitle("Dokument")
				.withHeader(doc.getDocName() == null ? "Dokument für " + immo.getImmobilieTypeName() : doc.getDocName())
				.withOkCancelButtons()
				.setOnCloseRequestOkHandler(getDialogSaveHandler(doc));
	}

	private DialogSaveValidator getDialogSaveHandler(Document doc) {
		return event -> {
			try {
				DocFile docFile = ctrl.documentFromForm(doc);
				if (docFile.isNew()  ||  docFile.isFileChanged()) {
					DocProvider docProvider = new DocProvider(doc.getDocDir());
					docProvider.copyDoc(docFile.getNewFile());
					if (!docFile.isNew()) {
						docFile.getImmoFile().delete();
					}
				}
				return save(doc);
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

	private boolean save(Document doc) {
		new DocumentManager().save(doc);
		return true;
	}

}
