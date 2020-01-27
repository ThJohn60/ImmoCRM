package net.immocrm.gui.doc;

import java.io.File;
import java.net.MalformedURLException;

import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import net.immocrm.domain.Document;
import net.immocrm.domain.img.DocFile;
import net.immocrm.gui.Main;
import net.immocrm.gui.alert.AlertProvider;

public class DokuDialogController {

	@FXML DialogPane dialogPane;
	@FXML Label headerLabel;

	@FXML TextField title;
	@FXML Label fileName;
	@FXML TextArea details;

	@FXML ImageView imageArea;

	private final PDFManager pdfMan;

	private DocFile docFile;
	private Document document;



	public DokuDialogController() {
		pdfMan = new PDFManager();
	}

	public void documentToForm(Document doc, DocFile df) {
		this.document = doc;
		docFile = df;

		title.setText(doc.getDocName());
		fileName.setText(doc.getFileName());
		details.setText(doc.getDescription());

		if (df.getFile().exists()) {
			fileName.setTextFill(Color.BLACK);
			showPDFAsImage();
		} else {
			fileName.setTextFill(Color.RED);
			AlertProvider.alertError("Die Datei " + docFile.getName() + " ist nicht (mehr) vorhanden.");
		}
	}

	private void showPDFAsImage() {
		WritableImage image = pdfMan.loadFrontPage(docFile.getFile());
		if (image != null) {
			double imageHeight = image.getHeight();
			double imageWidth = image.getWidth();
			imageArea.setFitHeight(imageArea.getFitWidth() * imageHeight / imageWidth);
			imageArea.setImage(image);
		}
	}

	public DocFile documentFromForm(Document d) {
		d.setDocName(title.getText());
		d.setFileName(fileName.getText());
		d.setDescription(details.getText());
		return docFile;
	}

	@FXML
	public void mouseClickedOnImage(MouseEvent event) {
		if (event.getClickCount() == 2) {
			try {
				Main.getApplicationHostServices().showDocument(docFile.getFile().toURI().toURL().toExternalForm());
			} catch (MalformedURLException e) {
				// can't happen
			}
		}
	}

	@FXML
	public void changeFile() {
		double dialogHeight = dialogPane.getHeight();
		File newFile = pdfMan.newFileByUser();
		if (newFile != null) {
			document.setFileName(newFile.getName());
			if (document.getDocName() == null || document.getDocName().isEmpty()) {
				String fn = document.getFileName();
				document.setDocName(fn.substring(0, fn.length() - 4));
			}
			if (docFile.getImmoFile() == null  ||  !docFile.getImmoFile().exists()) {
				docFile = new DocFile(newFile, null);
			} else if (!docFile.getImmoFileName().equals(newFile.getName())) {
				docFile = new DocFile(newFile, docFile.getImmoFile());
			}
			documentToForm(document, docFile);
			dialogPane.setPrefHeight(dialogHeight);		// workaround
		}
	}

}
