package net.immocrm.domain.valid;

public class TruncationError extends Issue {

	private static final String LENGTH_PATTERN = "auf die Länge";

	
	public TruncationError(String exceptionMessage) {
		super(getErrorMessage(exceptionMessage), Severity.ERROR);
	}


	private static String getErrorMessage(String exceptionMessage) {
		StringBuilder errText = new StringBuilder();
		errText.append("Das Feld mit dem Inhalt ")
			.append(getFieldContent(exceptionMessage))
			.append(" ist zu lang. Maximal ")
			.append(getMaxFieldLength(exceptionMessage))
			.append(" Zeichen sind erlaubt.");
		return errText.toString();
	}

	private static String getFieldContent(String exceptionMessage) {
		int fieldContentBegin = exceptionMessage.indexOf('\'');
		int fieldContentEnd = exceptionMessage.substring(fieldContentBegin+1).indexOf('\'') + fieldContentBegin+1;
		String fieldContent = exceptionMessage.substring(fieldContentBegin, fieldContentEnd);
		if (fieldContent.length() > 50) {
			fieldContent = fieldContent.substring(0, 50) + "...";
		}
		return fieldContent;
	}
	
	private static String getMaxFieldLength(String exceptionMessage) {
		int lengthValueBegin = exceptionMessage.indexOf(LENGTH_PATTERN) + LENGTH_PATTERN.length() + 1;
		int lengthValueEnd = exceptionMessage.substring(lengthValueBegin + 1).indexOf(' ') + lengthValueBegin + 1;
		String lengthValue = exceptionMessage.substring(lengthValueBegin, lengthValueEnd);
		return lengthValue;
	}

}
