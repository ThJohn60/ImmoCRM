package net.immocrm.gui.notar;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import net.immocrm.domain.Notar;
import net.immocrm.gui.refresh.RefreshedNotars;
import net.immocrm.gui.tools.ClipboardController;
import net.immocrm.gui.tools.EditItemController;

public class NotarController implements Initializable {

	@FXML TableView<NotarRow> tableView;

	private NotarTableController tableCtrl;
	private EditItemController editItemCtrl;


	public NotarController() {
		editItemCtrl = new EditItemController();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableCtrl = new NotarTableController(tableView);
	}

	@FXML
	public void keyPressedOnTable(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			editItemCtrl.editItem();
		}
	}

	@FXML
	public void mouseClickOnTable(MouseEvent event) {
		if (event.getClickCount() == 2) {
			editItemCtrl.editItem();
		}
	}


	@FXML
	public void copyAddressToClipboard() {
		Notar notar = tableCtrl.getDomainObject();
		if (notar != null) {
			ClipboardController.INSTANCE.copyAddressToClipboard(notar.getName(), notar.getAddress());
		}
	}

	public Notar getSelectedItem() {
		return tableCtrl.getDomainObject();
	}

	public void refresh(RefreshedNotars notars) {
		tableCtrl.fillTable(notars);
	}

	public void requestFocus() {
		tableView.requestFocus();
	}

}
