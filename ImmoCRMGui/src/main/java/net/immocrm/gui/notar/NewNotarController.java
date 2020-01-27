package net.immocrm.gui.notar;

import javafx.fxml.FXML;
import net.immocrm.domain.DomainFactory;
import net.immocrm.domain.Notar;
import net.immocrm.gui.MainController;
import net.immocrm.gui.refresh.RefreshType;

public class NewNotarController {

	@FXML
	public void newNotar() {
		NotarDialog dialog = new NotarDialog();
		Notar notar = dialog.showDialog(DomainFactory.newNotar());
		if (notar != null) {
			MainController.getRefreshDispatcher().refreshNotarViews(notar, RefreshType.insertItem);
		}
	}

}
