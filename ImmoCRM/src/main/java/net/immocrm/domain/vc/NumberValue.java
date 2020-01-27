package net.immocrm.domain.vc;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class NumberValue extends BaseValue<Integer> {

	public static final NumberValue EMTPTY_NUMBER = new NumberValue();
	public static final NumberFormat NUMBER_FORMATTER = NumberFormat.getNumberInstance(Locale.GERMANY);

	public static int parse(String number) throws ParseException {
		return NUMBER_FORMATTER.parse(number).intValue();
	}


	public NumberValue() {
		this(null);
	}

	public NumberValue(Integer val) {
		super(val);
	}


	@Override
	public String toString() {
		if (value == null) {
			return "";
		}
		return value.toString();
	}

}
