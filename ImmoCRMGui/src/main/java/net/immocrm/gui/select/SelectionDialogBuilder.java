package net.immocrm.gui.select;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import net.immocrm.domain.BaseDomain;
import net.immocrm.gui.BaseDialogBuilder;
import net.immocrm.gui.IconProvider;
import net.immocrm.gui.dialog.BaseDialog;
import net.immocrm.gui.person.PersonDialog;

public class SelectionDialogBuilder<T extends BaseDomain, S extends SelectionRow<T>> {

	private final Dialog<ButtonType> dialog;
	private final SelectionDialogController<T, S> dialogCtrl;


	public SelectionDialogBuilder() {
		dialog = new Dialog<ButtonType>();
		FXMLLoader fxmlLoader = BaseDialogBuilder.getFxmlLoader("select/SelectionDialog.fxml");
		dialog.setDialogPane((DialogPane)BaseDialogBuilder.loadPane(fxmlLoader));
		dialogCtrl = fxmlLoader.getController();
	}


	public SelectionDialogBuilder<T, S> withTitle(String title) {
		dialog.setTitle(title);
		return this;
	}

	public SelectionDialogBuilder<T, S> withDialogHeader(String header) {
		dialogCtrl.setHeaderText(header);
		return this;
	}

	public SelectionDialogBuilder<T, S> addColumn(TableColumn<S, String> col) {
		dialogCtrl.addColumn(col);
		return this;
	}

	public SelectionDialogBuilder<T, S> addButton(Button newItemButton) {
		dialogCtrl.setNewItemButton(newItemButton);
		return this;
	}

	public SelectionDialogBuilder<T, S> addNewPersonButton() {
		Button button = new Button("Neue Person");
		button.setGraphic(IconProvider.addPersonButtonIcon());
		button.setOnAction(event -> {
			T p = (T)new PersonDialog().showDialog();
			if (p != null) {
				dialogCtrl.showItems();
				dialogCtrl.selectItem(p);
			}
			event.consume();
		});
		return addButton(button);
	}


	public SelectionDialog<T, S> build(TableItemProvider<T, S> itemProvider) {
		return new SelectionDialog<T, S>(new BaseDialog(dialog), itemProvider, dialogCtrl);
	}

}
