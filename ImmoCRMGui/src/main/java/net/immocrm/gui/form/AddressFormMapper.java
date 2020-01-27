package net.immocrm.gui.form;

import javafx.scene.control.TextField;
import net.immocrm.domain.Address;
import net.immocrm.domain.valid.AddressValidator;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.control.CountryComboBox;

public class AddressFormMapper {

	private final AddressValidator validator;

	private final TextField street;
	private final TextField streetb;
	private final TextField postalCode;
	private final TextField city;
	private final CountryComboBox countryBox;


	public AddressFormMapper(TextField street, TextField streetb, TextField postalCode, TextField city, CountryComboBox countryBox) {
		this.countryBox = countryBox;
		this.street = street;
		this.streetb = streetb;
		this.postalCode = postalCode;
		this.city = city;
		this.validator = new AddressValidator();
	}


	public void addressToForm(Address address) {
		street.setText(address.getStreet());
		if (streetb != null) {
			streetb.setText(address.getStreet2());
		}
		postalCode.setText(address.getPostalCode());
		city.setText(address.getCity());
		countryBox.setValue(address.getCountry());
	}

	public void addressFromForm(Address address) {
		address.setStreet(street.getText());
		if (streetb != null) {
			address.setStreet2(streetb.getText());
		}
		address.setPostalCode(getPostalCode());
		address.setCity(city.getText());
		address.setCountry(countryBox.getValue());
	}

	private String getPostalCode() {
		String value = postalCode.getText();
		if (value == null || validator.checkPostalCode(value)) {
			return value;
		}
		postalCode.requestFocus();
		AlertProvider.alertInputError("Bitte eine korrekte Postleitzahl eingeben.");
		throw new NoActionException();
	}

}
