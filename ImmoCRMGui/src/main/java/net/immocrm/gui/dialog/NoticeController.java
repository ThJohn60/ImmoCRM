package net.immocrm.gui.dialog;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;

public class NoticeController {

	@FXML TextArea notice;
	@FXML Tab tabLabel;


	public void setPromptText(String text) {
		notice.setPromptText(text);
	}

	public String getNotice() {
		return notice.getText();
	}

	public void setNotice(String text) {
		notice.setText(text);
	}

	public void setTabLabelText(String l) {
		tabLabel.setText(l);
	}

}
