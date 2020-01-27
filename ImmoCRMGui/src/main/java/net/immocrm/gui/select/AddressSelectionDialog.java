package net.immocrm.gui.select;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.TableColumn;
import net.immocrm.domain.Address;

public class AddressSelectionDialog {

	private SelectionDialog<Address, AddressSelectionRow> dialog;

	public AddressSelectionDialog() {
		dialog = new SelectionDialogBuilder<Address, AddressSelectionRow>()
				.withDialogHeader("Adresse kopieren")
				.addColumn(getCityCol())
				.addColumn(getPostalCodeCol())
				.addColumn(getStreetCol())
				.build(new AddressItemProvider());
	}

	private TableColumn<AddressSelectionRow, String> getStreetCol() {
		TableColumn<AddressSelectionRow, String> result = new TableColumn<>("Strasse");
		result.setCellValueFactory(
				param -> {
					return new ReadOnlyObjectWrapper<String>(param.getValue().getStreet());
				});
		return result;
	}

	private TableColumn<AddressSelectionRow, String> getPostalCodeCol() {
		TableColumn<AddressSelectionRow, String> result = new TableColumn<>("PLZ");
		result.setCellValueFactory(
				param -> {
					return new ReadOnlyObjectWrapper<String>(param.getValue().getPostalCode());
				});
		return result;
	}

	private TableColumn<AddressSelectionRow, String> getCityCol() {
		TableColumn<AddressSelectionRow, String> result = new TableColumn<>("Ort");
		result.setCellValueFactory(
				param -> {
					return new ReadOnlyObjectWrapper<String>(param.getValue().getCity());
				});
		return result;
	}


	public Address showDialog() {
		return dialog.showDialog();
	}

}
