package net.immocrm.gui.select;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import net.immocrm.domain.BaseDomain;
import net.immocrm.gui.IconProvider;
import net.immocrm.gui.dialog.ButtonManager;

public class SelectionDialogController<T extends BaseDomain, S extends SelectionRow<T>> implements Initializable {

	@FXML DialogPane pane;
	@FXML Label headerLabel;
	@FXML TableView<S> table;

	@FXML TextField searchField;
	@FXML Hyperlink searchButton;
	@FXML Hyperlink resetSearch;

	@FXML Pane newButtonPane;
	@FXML Button newButton;

	private TableItemProvider<T, S> itemProvider;
	private ButtonManager buttonManager;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setSearchFieldKeyHandler();
		setSearchButtonHandler();
		setSearchResetButtonHandler();
		buttonManager = new ButtonManager(pane);
		buttonManager.addOkButton();
		buttonManager.addCancelButton();
		setDoubleKlickHandler();
		setEnterKeyHandler();
	}

	private void setSearchFieldKeyHandler() {
		searchField.setOnKeyReleased(event -> {
//			if (event.getCode() == KeyCode.ENTER) {
				showItems();
				event.consume();
//			}
		});
	}

	private void setSearchButtonHandler() {
		searchButton.setGraphic(IconProvider.searchButtonIcon());
		searchButton.setOnAction(event -> {
			showItems();
			event.consume();
		});
	}

	private void setSearchResetButtonHandler() {
		resetSearch.setGraphic(IconProvider.clearButtonIcon());
		resetSearch.setOnAction(event -> {
			searchField.clear();
			showAllItems();
		});
	}

	private void setDoubleKlickHandler() {
		table.setOnMouseReleased(event ->  {
			if (event.getClickCount() == 2) {
				S item = table.getFocusModel().getFocusedItem();
				table.getSelectionModel().select(item);
				buttonManager.getButton(ButtonData.OK_DONE).fire();
			}
		});
	}

	private void setEnterKeyHandler() {
		table.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				buttonManager.getButton(ButtonData.OK_DONE).fire();
			}
		});
	}



	public void setItemProvider(TableItemProvider<T, S> itemProvider) {
		this.itemProvider = itemProvider;
	}

	public void addColumn(TableColumn<S, String> col) {
		table.getColumns().add(col);
	}

	public void setHeaderText(String header) {
		headerLabel.setText(header);
	}

	public void setNewItemButton(Button newItemButton) {
		newButtonPane.getChildren().add(newItemButton);
	}

	public S getSelectedItem() {
		return table.getSelectionModel().getSelectedItem();
	}


	public void showItems() {
		if (searchField.getText().trim().isEmpty()) {
			showAllItems();
		} else {
			fillTable(itemProvider.search(searchField.getText()));
		}
	}

	public void showAllItems() {
		fillTable(itemProvider.fetchAll());
	}

	private void fillTable(List<S> entities) {
		ObservableList<S> tableContent = table.getItems();
		tableContent.clear();
		for (S s : entities) {
			tableContent.add(s);
		}
	}

	public void selectItem(T p) {
		for (S r : table.getItems()) {
			if (r.getDomainObject().isSameId(p)) {
				table.getSelectionModel().select(r);
			}
		}
	}

}
