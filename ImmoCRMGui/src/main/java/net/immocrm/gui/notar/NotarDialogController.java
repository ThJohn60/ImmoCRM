package net.immocrm.gui.notar;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import net.immocrm.domain.Notar;
import net.immocrm.gui.control.CountryComboBox;
import net.immocrm.gui.form.AddressFormMapper;
import net.immocrm.gui.person.ContactFormMapper;

public class NotarDialogController implements Initializable {

	@FXML DialogPane dialogPane;

	@FXML TextField name;
	@FXML TextField street;
	@FXML TextField postalCode;
	@FXML TextField city;
	@FXML TextField telefon;
	@FXML TextField fax;
	@FXML TextField email;
	@FXML CountryComboBox countryBox;

	private AddressFormMapper addressMapper;
	private ContactFormMapper contactMapper;



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addressMapper = new AddressFormMapper(street, null, postalCode, city, countryBox);
		contactMapper = new ContactFormMapper(null, telefon, fax, email);
	}

	void notarToForm(Notar notar) {
		name.setText(notar.getName());
		contactMapper.contactToForm(notar);
		addressMapper.addressToForm(notar);
	}

	void notarFromForm(Notar notar) {
		notar.setName(name.getText());
		contactMapper.contactFromForm(notar);
		addressMapper.addressFromForm(notar);
	}

}
