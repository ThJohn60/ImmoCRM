package net.immocrm.gui.doc;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import net.immocrm.domain.Document;
import net.immocrm.domain.DocumentManager;
import net.immocrm.domain.DocumentReader;
import net.immocrm.domain.Immobilie;
import net.immocrm.gui.Main;
import net.immocrm.gui.alert.AlertProvider;

public class DokuTabController {

	@FXML Tab dokuTab;
	@FXML TableView<DokuRow> dokuTable;

	private final DocumentManager docMan;
	private final PDFManager pdfMan;

	private Immobilie immobilie;


	public DokuTabController() {
		docMan = new DocumentManager();
		pdfMan = new PDFManager();
	}

	public void showDocs(Immobilie im) {
		this.immobilie = im;
		Integer immoId = im.getId();
		refresh(immoId);
	}

	private void refresh(Integer immoId) {
		List<Document> docs = DocumentReader.INSTANCE.fetchByImmobilie(immoId);
		dokuTable.getItems().clear();
		for (Document document : docs) {
			dokuTable.getItems().add(new DokuRow(document));
		}
	}

	@FXML
	public void importDoku() {
		Document document = pdfMan.importDocument(immobilie);
		if (document != null) {
			refresh(immobilie.getId());
		}
	}


	@FXML
	public void editDoku() {
		DokuRow dokuRow = dokuTable.getSelectionModel().getSelectedItem();
		if (dokuRow != null) {
			Document document = new DokuDialog().showDialog(immobilie, dokuRow.getDocument());
			if (document != null) {
				refresh(document.getImmobilie().getId());
			}
		}
	}

	@FXML
	public void openDoku() {
		DokuRow dokuRow = dokuTable.getSelectionModel().getSelectedItem();
		if (dokuRow != null) {
			File docFile = dokuRow.getDocument().getFile();
			try {
				Main.getApplicationHostServices().showDocument(docFile.toURI().toURL().toExternalForm());
			} catch (MalformedURLException e) {
				// can't happen
			}
		}
	}


	@FXML
	public void deleteDoku() {
		DokuRow dokuRow = dokuTable.getSelectionModel().getSelectedItem();
		if (dokuRow != null) {
			if (AlertProvider.confirmRemove(dokuRow.getDocument())) {
				docMan.delete(dokuRow.getDocument());
				refresh(dokuRow.getDocument().getImmobilie().getId());
			}
		}
	}

	@FXML
	public void mouseClickOnTableItem(MouseEvent event) {
		if (event.getClickCount() == 2) {
			editDoku();
		}
	}

	@FXML
	public void keyPressedOnTableItem(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			openDoku();
		}
	}

}
