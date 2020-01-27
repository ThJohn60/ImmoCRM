package net.immocrm.domain.vc;

public class IncorrectAreaException extends IncorrectValueException {

	private static final long serialVersionUID = 1L;

	public IncorrectAreaException(String fieldName, String value, Throwable cause) {
		super(fieldName, value, "Bitte eine korrekte Fläche in " + Area.SQUARE_METER + " eingeben.", cause);
	}

}
