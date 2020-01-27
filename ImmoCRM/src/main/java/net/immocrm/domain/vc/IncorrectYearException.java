package net.immocrm.domain.vc;

public class IncorrectYearException extends IncorrectValueException {

	private static final long serialVersionUID = 1L;

	public IncorrectYearException(String fieldName, String value, Throwable cause) {
		super(fieldName, value, "Bitte eine korrekte Jahreszahl eingeben.", cause);
	}

}
