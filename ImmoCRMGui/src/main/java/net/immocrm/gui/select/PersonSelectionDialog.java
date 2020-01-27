package net.immocrm.gui.select;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.TableColumn;
import net.immocrm.domain.Person;

public class PersonSelectionDialog {

	private SelectionDialog<Person, PersonSelectionRow> dialog;

	public PersonSelectionDialog(String personRole) {
		dialog = new SelectionDialogBuilder<Person, PersonSelectionRow>()
				.withDialogHeader(personRole + " auswählen")
				.addColumn(getFirstNameCol())
				.addColumn(getLastNameCol())
				.addColumn(getStreetCol())
				.addColumn(getCityCol())
				.addNewPersonButton()
				.build(new PersonPersonSelectionRowProvider());
	}

	private TableColumn<PersonSelectionRow, String> getFirstNameCol() {
		TableColumn<PersonSelectionRow, String> result = new TableColumn<>("Vorname");
		result.setCellValueFactory(
				param -> {
					return new ReadOnlyObjectWrapper<String>(param.getValue().getFirstName());
				});
		return result;
	}

	private TableColumn<PersonSelectionRow, String> getLastNameCol() {
		TableColumn<PersonSelectionRow, String> result = new TableColumn<>("Nachname");
		result.setCellValueFactory(
				param -> {
					return new ReadOnlyObjectWrapper<String>(param.getValue().getLastName());
				});
		return result;
	}

	private TableColumn<PersonSelectionRow, String> getStreetCol() {
		TableColumn<PersonSelectionRow, String> result = new TableColumn<>("Strasse");
		result.setCellValueFactory(
				param -> {
					return new ReadOnlyObjectWrapper<String>(param.getValue().getHomeAddress().getStreet());
				});
		return result;
	}

	private TableColumn<PersonSelectionRow, String> getCityCol() {
		TableColumn<PersonSelectionRow, String> result = new TableColumn<>("Ort");
		result.setCellValueFactory(
				param -> {
					return new ReadOnlyObjectWrapper<String>(param.getValue().getHomeAddress().getCity());
				});
		return result;
	}


	public Person showDialog() {
		return dialog.showDialog();
	}

}
