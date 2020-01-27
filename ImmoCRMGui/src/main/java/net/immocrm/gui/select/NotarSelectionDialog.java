package net.immocrm.gui.select;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.TableColumn;
import net.immocrm.domain.Notar;

public class NotarSelectionDialog {

	private SelectionDialog<Notar, NotarSelectionRow> dialog;

	public NotarSelectionDialog() {
		dialog = new SelectionDialogBuilder<Notar, NotarSelectionRow>()
				.withDialogHeader("Notar auswählen")
				.addColumn(getNameCol())
				.addColumn(getPostalCodeCol())
				.addColumn(getCityCol())
				.build(new NotarItemProvider());
	}

	private TableColumn<NotarSelectionRow, String> getNameCol() {
		TableColumn<NotarSelectionRow, String> result = new TableColumn<>("Name");
		result.setCellValueFactory(
				param -> {
					return new ReadOnlyObjectWrapper<String>(param.getValue().getName());
				});
		return result;
	}

	private TableColumn<NotarSelectionRow, String> getPostalCodeCol() {
		TableColumn<NotarSelectionRow, String> result = new TableColumn<>("PLZ");
		result.setCellValueFactory(
				param -> {
					return new ReadOnlyObjectWrapper<String>(param.getValue().getPostalCode());
				});
		return result;
	}

	private TableColumn<NotarSelectionRow, String> getCityCol() {
		TableColumn<NotarSelectionRow, String> result = new TableColumn<>("Ort");
		result.setCellValueFactory(
				param -> {
					return new ReadOnlyObjectWrapper<String>(param.getValue().getCity());
				});
		return result;
	}


	public Notar showDialog() {
		return dialog.showDialog();
	}

}
