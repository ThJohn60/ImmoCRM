package net.immocrm.gui.quality;

import javafx.fxml.FXML;

public class MerkmalTypeMenuItemController {

	@FXML
	public void immobilienAttributes() {
		MerkmalTypeDialog qualityTypeDialog = new MerkmalTypeDialog();
		qualityTypeDialog.showDialog();
	}


}
