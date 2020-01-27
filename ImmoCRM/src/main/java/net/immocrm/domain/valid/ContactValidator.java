package net.immocrm.domain.valid;

import static net.immocrm.domain.valid.ValidationTool.isNullOrEmpty;
import static net.immocrm.domain.valid.ValidationTool.validatePerRegex;

import net.immocrm.domain.Contact;

public class ContactValidator implements Validator<Contact> {

	private static final String MAIL_REGEX = "^[A-Z0-9._%+-]{1,64}@(?:[A-Z0-9-]{1,63}\\.){1,125}[A-Z]{2,63}$";
	private static final String PHONE_NUMBER_REGEX = "[0-9 -/]{1,20}";

	@Override
	public ValidationIssues validate(Contact ct) {
		ValidationIssues issues = new ValidationIssues();
		if (!checkEmailAddress(ct.getEmailAddress())) {
			issues.addError("Bitte eine korrekte Maildadresse eingeben.");
		}
		if (!checkPhoneNumber(ct.getCellNumber())) {
			issues.addError("Bitte eine korrekte Handynummer eingeben.");
		}
		if (!checkPhoneNumber(ct.getTelNumber())) {
			issues.addError("Bitte eine korrekte Telefonnummer eingeben.");
		}
		if (!checkPhoneNumber(ct.getFaxNumber())) {
			issues.addError("Bitte eine korrekte Faxnummer eingeben.");
		}
		return issues;
	}

	public boolean checkPhoneNumber(String number) {
		return isNullOrEmpty(number) || validatePerRegex(number, PHONE_NUMBER_REGEX);
	}

	public boolean checkEmailAddress(String mailAddress) {
		return isNullOrEmpty(mailAddress) || validatePerRegex(mailAddress, MAIL_REGEX);
	}

}
