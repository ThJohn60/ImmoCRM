package net.immocrm.domain.vc;

import net.immocrm.domain.valid.StdException;

public class IncorrectValueException extends StdException {

	private static final long serialVersionUID = 1L;

	private final String fieldName;
	private final Object value;
	private final String corrctionMsg;


	public IncorrectValueException(String fieldName, String msg, Throwable cause) {
		super("Interpretationsproblem in Feld \"" + fieldName + "\"", msg, cause);
		this.fieldName = fieldName;
		this.value = null;
		this.corrctionMsg = "";
	}

	public IncorrectValueException(String fieldName, String value, String corrctionMsg, Throwable cause) {
		super("Interpretationsproblem in Feld \"" + fieldName + "\"", cause, "Der Wert %s ist ungültig. %s", value, corrctionMsg);
		this.fieldName = fieldName;
		this.value = value;
		this.corrctionMsg = corrctionMsg;
	}


	public String getFieldName() {
		return fieldName;
	}

	public Object getValue() {
		return value;
	}

	public String getCorrctionMsg() {
		return corrctionMsg;
	}

}
