package net.immocrm.domain.valid;

import static net.immocrm.domain.valid.ValidationTool.checkEmpty;
import static net.immocrm.domain.valid.ValidationTool.isNullOrEmpty;
import static net.immocrm.domain.valid.ValidationTool.validatePerRegex;

import net.immocrm.domain.Address;

public class AddressValidator implements Validator<Address> {

	private static final String POSTAL_CODE_REGEX = "[0-9]{4,5}";

	@Override
	public ValidationIssues validate(Address addr) {
		String postalCode = addr.getPostalCode();
		if (!isNullOrEmpty(postalCode) && !checkPostalCode(postalCode)) {
			ValidationIssues issues = new ValidationIssues();
			issues.addError("Bitte eine korrekte Postleitzahl eingeben.");
			return issues;
		}
		return ValidationIssues.emptyIssues;
	}

	public ValidationIssues checkComplete(Address addr) {
		ValidationIssues issues = new ValidationIssues();
		checkEmpty(addr.getStreet(), "Strasse", issues);
		checkEmpty(addr.getPostalCode(), "PLZ", issues);
		checkEmpty(addr.getCity(), "Ort", issues);
		return issues;
	}

	public boolean checkPostalCode(String postalCode) {
		return validatePerRegex(postalCode, POSTAL_CODE_REGEX);
	}

}
