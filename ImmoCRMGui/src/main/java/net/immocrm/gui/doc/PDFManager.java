package net.immocrm.gui.doc;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import net.immocrm.domain.Document;
import net.immocrm.domain.DomainFactory;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.img.DocFile;
import net.immocrm.gui.Main;
import net.immocrm.gui.alert.AlertProvider;

public class PDFManager {


//	void saveFile(DocumentDirectory docDir, File file) {
//		new Runnable() {
//
//			@Override
//			public void run() {
//				try {
//					DocProvider docProvider = new DocProvider(docDir);
//					docProvider.copyDoc(file);
//				} catch (FileManagementException e) {
//					AlertProvider.alertError(e.getMessage());
//				}
//			}
//		}.run();
//	}

	public Document importDocument(Immobilie immobilie) {
		File pdfFile = newFileByUser();
		if (pdfFile != null) {
			Document newDoc = DomainFactory.newDocument();
			newDoc.setImmobilie(immobilie);
			newDoc.setFileName(pdfFile.getName());
			String fn = newDoc.getFileName();
			newDoc.setDocName(fn.substring(0, fn.length() - 4));
			Document document = new DokuDialog().showDialog(immobilie, newDoc, new DocFile(pdfFile, null));
			if (document != null) {
//				saveFile(immobilie.getDocDir(), pdfFile);
			}
			return document;
		}
		return null;
	}

	public File newFileByUser() {
		FileChooser chooser = new FileChooser();
		String homeDir = System.getProperty("user.home");
		if (System.getProperty("os.name").contains("Window")) {
			homeDir = homeDir + "\\" + "Documents";
		}
		chooser.setInitialDirectory(new File(homeDir));
		chooser.setTitle("Datei öffnen");
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*." + "pdf"));
		File pdfFile = chooser.showOpenDialog(Main.getApplicationWindow());
		return pdfFile;
	}

	public WritableImage loadFrontPage(File pdfFile) {
		WritableImage image = null;
		try (PDDocument pdDocument = PDDocument.load(pdfFile)) {
			PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);
			image = SwingFXUtils.toFXImage(pdfRenderer.renderImage(0), null);
		} catch (IOException e) {
			AlertProvider.alertError("Fehler beim Lesen der Datei " + pdfFile.getName(), e.getLocalizedMessage());
			e.printStackTrace();
		}
		return image;
	}


}
