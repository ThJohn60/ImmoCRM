package net.immocrm.domain.vc;

public class IncorrectPriceException extends IncorrectValueException {

	private static final long serialVersionUID = 1L;

	public IncorrectPriceException(String fieldName, String value, Throwable cause) {
		super(fieldName, value, "Bitte einen korrekten Eurobetrag eingeben.", cause);
	}

}
