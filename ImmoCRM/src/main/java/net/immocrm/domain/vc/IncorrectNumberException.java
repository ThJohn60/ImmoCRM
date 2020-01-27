package net.immocrm.domain.vc;

public class IncorrectNumberException extends IncorrectValueException {

	private static final long serialVersionUID = 1L;

	public IncorrectNumberException(String fieldName, String value, Throwable cause) {
		super(fieldName, value, "Bitte eine korrekte Zahl eingeben.", cause);
	}

}
