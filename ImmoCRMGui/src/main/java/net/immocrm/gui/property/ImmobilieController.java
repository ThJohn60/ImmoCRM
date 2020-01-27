package net.immocrm.gui.property;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import net.immocrm.domain.Immobilie;
import net.immocrm.gui.overview.MenuItemInfo;
import net.immocrm.gui.refresh.RefreshedImmobilien;
import net.immocrm.gui.tools.EditItemController;
import net.immocrm.gui.tools.GotoItemController;

public class ImmobilieController implements Initializable {

	@FXML TableView<ImmobilieRow> tableView;
	@FXML BorderPane tablePane;
	@FXML ContextMenu tableContextMenu;

	private ImmobilieTableController tableCtrl;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableCtrl = new ImmobilieTableController(tableView);
	}


	public Immobilie getSelectedItem() {
		return tableCtrl.getDomainObject();
	}

	public void refresh(RefreshedImmobilien immobilien) {
		tableCtrl.fillTable(immobilien);
		if (immobilien.getDomain() != null) {
			tableCtrl.selectItem(immobilien.getDomain());
		}
	}

	@FXML
	public void mouseClickOnTableItem(MouseEvent event) {
		if (event.getClickCount() == 2) {
			GotoItemController ctrl = new GotoItemController();
			ctrl.gotoItem();
			event.consume();
		}
	}

	@FXML
	public void keyPressedOnTableItem(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			EditItemController editItemCtrl = new EditItemController();
			editItemCtrl.editItem();
			event.consume();
		}
	}

	public void requestFocus() {
		tableView.requestFocus();
	}


	@FXML
	public void onShowingContextMenu() {
		for (MenuItem menuItem : tableContextMenu.getItems()) {
			menuItem.setUserData(new MenuItemInfo(new ImmobilieNodeValue(getSelectedItem())));
		}
	}

}
