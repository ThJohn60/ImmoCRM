package net.immocrm.gui.person;

import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import net.immocrm.domain.Contact;
import net.immocrm.domain.valid.ContactValidator;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.form.NoActionException;

public class ContactFormMapper {

	private final ContactValidator validator;

	private final TextField mobile;
	private final TextField telefon;
	private final TextField fax;
	private final TextField email;


	public ContactFormMapper(TextField mobile, TextField telefon, TextField fax, TextField email) {
		this.mobile = mobile;
		this.telefon = telefon;
		this.fax = fax;
		this.email = email;

		validator = new ContactValidator();
	}


	public void contactToForm(Contact contact) {
		if (mobile != null) {
			mobile.setText(contact.getCellNumber());
		}
		telefon.setText(contact.getTelNumber());
		fax.setText(contact.getFaxNumber());
		email.setText(contact.getEmailAddress());
	}

	public void contactFromForm(Contact contact) {
		if (mobile != null) {
			contact.setCellNumber(getPhoneNumber(mobile));
		}
		contact.setTelNumber(getPhoneNumber(telefon));
		contact.setFaxNumber(getPhoneNumber(fax));
		contact.setEmailAddress(getEMail());
	}


	private String getPhoneNumber(TextInputControl txtField) {
		if (txtField == null) {
			return null;
		}
		String value = txtField.getText();
		if (validator.checkPhoneNumber(value)) {
			return value;
		}
		txtField.requestFocus();
		AlertProvider.alertInputError("Bitte eine korrekte Telefonnummer eingeben.");
		throw new NoActionException();
	}

	private String getEMail() {
		if (email != null) {
			String value = email.getText();
			if (validator.checkEmailAddress(value)) {
				return value;
			}
			email.requestFocus();
			AlertProvider.alertInputError("Bitte eine korrekte Maildadresse eingeben.");
			throw new NoActionException();
		}
		return null;
	}


	public void clearFields() {
		if (mobile != null) {
			mobile.clear();
		}
		telefon.clear();
		fax.clear();
		email.clear();
	}

}
