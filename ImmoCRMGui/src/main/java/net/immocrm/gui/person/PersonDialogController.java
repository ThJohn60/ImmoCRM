package net.immocrm.gui.person;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import net.immocrm.domain.Person;
import net.immocrm.gui.BaseDialogBuilder;
import net.immocrm.gui.dialog.NoticeController;

public class PersonDialogController implements Initializable {

	@FXML TabPane tabPane;

	private PersonFormController formCtrl;
	private NoticeController noticeCtrl;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		formCtrl = buildTab("person/PersonForm.fxml");
		noticeCtrl = buildTab("dialog/NoticeTab.fxml");
		noticeCtrl.setPromptText("Hier Notizen zur Person eingeben");
	}

	@SuppressWarnings("unchecked")
	private <T> T buildTab(String fxmlFileName) {
		FXMLLoader fxmlLoader = BaseDialogBuilder.getFxmlLoader(fxmlFileName);
		Tab tab = BaseDialogBuilder.load(fxmlLoader);
		T ctrl = (T)fxmlLoader.getController();
		tabPane.getTabs().add(tab);
		return ctrl;
	}


	public void personToForm(Person person) {
		formCtrl.personToForm(person);
		noticeCtrl.setNotice(person.getNotice());
	}

	public void personFromForm(Person person) {
		formCtrl.personFromForm(person);
		person.setNotice(noticeCtrl.getNotice());
	}

	public void enableNameFields(boolean b) {
		formCtrl.enableNameFields(b);
	}

}
