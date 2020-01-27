package net.immocrm.gui.person;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import net.immocrm.domain.Address;
import net.immocrm.domain.Person;
import net.immocrm.domain.vc.ImmoDate;
import net.immocrm.gui.control.CountryComboBox;
import net.immocrm.gui.form.AddressFormMapper;
import net.immocrm.gui.select.AddressSelectionDialog;

public class PersonFormController implements Initializable {

	public static final boolean TWO_ADRESSES = false;

	@FXML TextField titel;
	@FXML TextField vorname;
	@FXML TextField middlename;
	@FXML TextField nachname;
	@FXML DatePicker birthday;

	@FXML TextField street;
	@FXML TextField streetb;
	@FXML TextField postalCode;
	@FXML TextField city;

	@FXML TextField mobile;
	@FXML TextField telefon;
	@FXML TextField fax;
	@FXML TextField email;
	@FXML ComboBox<String> anredeSelectionBox;
	@FXML CountryComboBox countryBox;

	private ContactFormMapper contactMapper;
	private AddressFormMapper addressMapper;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		anredeSelectionBox.getItems().addAll("Frau", "Herr");
		contactMapper = new ContactFormMapper(mobile, telefon, fax, email);
		addressMapper = new AddressFormMapper(street, streetb, postalCode, city, countryBox);
	}


	@FXML
	public void selectAddress(Event event) {
		AddressSelectionDialog addressDlg = new AddressSelectionDialog();
		Address address = addressDlg.showDialog();
		if (address != null) {
			addressMapper.addressToForm(address);
		}
		event.consume();
	}

	public void personToForm(Person person) {
		if (person.isEmpty()) {
			anredeSelectionBox.setValue("Herr");
		} else {
			anredeSelectionBox.getSelectionModel().select(person.getAnrede());
		}
		titel.setText(person.getTitle());
		vorname.setText(person.getFirstName());
		nachname.setText(person.getLastName());
		middlename.setText(person.getMiddleName());
		if (person.getBirthday() != null) {
			birthday.setValue(person.getBirthday().toLocalDate());
		}
		addressMapper.addressToForm(person.getHomeAddress());
		contactMapper.contactToForm(person.getHomeContact());
	}

	public void personFromForm(Person person) {
		person.setAnrede(anredeSelectionBox.getSelectionModel().getSelectedItem());
		person.setTitle(titel.getText());
		person.setFirstName(vorname.getText());
		person.setLastName(nachname.getText());
		person.setMiddleName(middlename.getText());
		if (birthday.getValue() != null) {
			person.setBirthday(new ImmoDate(birthday.getValue()));
		}
		addressMapper.addressFromForm(person.getHomeAddress());
		contactMapper.contactFromForm(person.getHomeContact());
	}

	public void enableNameFields(boolean b) {
		vorname.setEditable(b);
		nachname.setEditable(b);
		middlename.setEditable(b);
	}

}
