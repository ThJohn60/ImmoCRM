package net.immocrm.gui.transfer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import net.immocrm.domain.DomainFactory;
import net.immocrm.domain.Person;
import net.immocrm.domain.PersonManager;
import net.immocrm.domain.transfer.CsvFileContent;
import net.immocrm.gui.Main;
import net.immocrm.gui.alert.AlertProvider;

public class PersonImportController implements Initializable {

	private static final String[] EXPECTED_COLUMNS = new String[] { "Vorname", "Nachname", "straße", "plz",  "ort", "Festnetz", "Mobil", "Fax", "Email",  };

	@FXML DialogPane dialogPane;
	@FXML Label fileName;
	@FXML Label successLabel;
	@FXML TextFlow importantNote;

	private Button okButton;

	private static File file;

	private final CsvFileReader fileReader;
	private final ImportValidator importValidator;

	private CsvFileContent content;


	public PersonImportController() {
		fileReader = new CsvFileReader(Main.getApplicationWindow());
		importValidator = new ImportValidator();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		new ImportDialogHelper().setImportantNote(importantNote.getChildren());
	}


	public void setOkButton(Button okButton) {
		this.okButton = okButton;
	}


	@FXML
	public void choosedFile() {
		successLabel.setText("");
		fileName.setText("");
		file = fileReader.getChosenFile(file == null ? null : file.getParentFile());
		if (file != null) {
			fileName.setText(file.getName());
			try {
				content = fileReader.readFile(file);
				String msg = importValidator.check(content, EXPECTED_COLUMNS, 3);
				boolean success = msg == null;
				if (success) {
					successLabel.setText("Die Datei kann eingelesen werden.");
					successLabel.setTextFill(Color.DARKGREEN);
				} else {
					successLabel.setText(msg);
					successLabel.setTextFill(Color.DARKRED);
				}

				okButton.setDisable(!success);
			} catch (FileNotFoundException e) {
				AlertProvider.alertError("Die Datei\n" + file.getAbsolutePath() + "\nist (nicht) mehr vorhanden.");
			} catch (IOException e) {
				AlertProvider.alertError("Problem beim Einlesen der Datei\n" + file.getAbsolutePath() + "\n" + e.getMessage());
			}
		}
	}


	public void importPersons() {
		if (content == null) {
			return;
		}
		content.initIndexMap(EXPECTED_COLUMNS);
		PersonManager personMan = new PersonManager();
		int successfulImportCnt = 0;
		for (int i = 0; i < content.getRowCount(); i++) {
			Person p = DomainFactory.newPerson();
			p.setFirstName(content.getMappedField(i, 0));
			p.setLastName(content.getMappedField(i, 1));
			p.setStreet(content.getMappedField(i, 2));
			p.setPostalCode(content.getMappedField(i, 3));
			p.setCity(content.getMappedField(i, 4));
			p.getHomeContact().setTelNumber(content.getMappedField(i, 5));
			p.getHomeContact().setCellNumber(content.getMappedField(i, 6));
			p.getHomeContact().setFaxNumber(content.getMappedField(i, 7));
			p.getHomeContact().setEmailAddress(content.getMappedField(i, 8));
			personMan.save(p);
			successfulImportCnt++;
		}
		if (successfulImportCnt < content.getRowCount()) {
			AlertProvider.alertInfo("Es wurden " + successfulImportCnt + " von "  +  content.getRowCount() + " importiert");
		}
	}

}
