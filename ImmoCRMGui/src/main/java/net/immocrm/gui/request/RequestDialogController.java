package net.immocrm.gui.request;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import net.immocrm.domain.Request;
import net.immocrm.gui.BaseDialogBuilder;
import net.immocrm.gui.dialog.NoticeController;

public class RequestDialogController implements Initializable {

	@FXML TabPane tabPane;

	private RequestFormController requestCtrl;
	private NoticeController noticeCtrl;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		requestCtrl = buildTab("request/RequestForm.fxml");
		noticeCtrl = buildTab("dialog/NoticeTab.fxml");
		noticeCtrl.setPromptText("Hier Notizen zur Anfrage eingeben");
	}

	@SuppressWarnings("unchecked")
	private <T> T buildTab(String fxmlFileName) {
		FXMLLoader fxmlLoader = BaseDialogBuilder.getFxmlLoader(fxmlFileName);
		Tab tab = BaseDialogBuilder.load(fxmlLoader);
		T ctrl = (T)fxmlLoader.getController();
		tabPane.getTabs().add(tab);
		return ctrl;
	}


	public void requestToForm(Request request) {
		requestCtrl.requestToForm(request);
		noticeCtrl.setNotice(request.getNotice());
	}

	public void requestFromForm(Request request) {
		requestCtrl.requestFromForm(request);
		request.setNotice(noticeCtrl.getNotice());
	}

}
