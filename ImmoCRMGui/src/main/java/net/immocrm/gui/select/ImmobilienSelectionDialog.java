package net.immocrm.gui.select;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.TableColumn;
import net.immocrm.domain.Immobilie;

public class ImmobilienSelectionDialog {

	private SelectionDialog<Immobilie, ImmobilieSelectionRow> dialog;

	public ImmobilienSelectionDialog() {
		dialog = new SelectionDialogBuilder<Immobilie, ImmobilieSelectionRow>()
				.withDialogHeader("Immobilie auswählen")
				.addColumn(getImmobilieType())
				.addColumn(getStreetCol())
				.addColumn(getCityCol())
				.build(new ImmobilieSelectionRowProvider());
	}

	private TableColumn<ImmobilieSelectionRow, String> getImmobilieType() {
		TableColumn<ImmobilieSelectionRow, String> result = new TableColumn<>("Typ");
		result.setCellValueFactory(
				param -> {
					return new ReadOnlyObjectWrapper<String>(param.getValue().getImmobilieType());
				});
		return result;
	}

	private TableColumn<ImmobilieSelectionRow, String> getStreetCol() {
		TableColumn<ImmobilieSelectionRow, String> result = new TableColumn<>("Strasse");
		result.setCellValueFactory(
				param -> {
					return new ReadOnlyObjectWrapper<String>(param.getValue().getAddress().getStreet());
				});
		return result;
	}

	private TableColumn<ImmobilieSelectionRow, String> getCityCol() {
		TableColumn<ImmobilieSelectionRow, String> result = new TableColumn<>("Ort");
		result.setCellValueFactory(
				param -> {
					return new ReadOnlyObjectWrapper<String>(param.getValue().getAddress().getCity());
				});
		return result;
	}


	public Immobilie showDialog() {
		return dialog.showDialog();
	}

}
