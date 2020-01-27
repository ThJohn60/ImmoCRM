package net.immocrm.domain.vc;

import static net.immocrm.domain.valid.ValidationTool.isNullOrEmpty;

import java.text.ParseException;
import java.time.LocalDate;

public class ValuesParser {

	public static Year parseYear(String number) throws ParseException {
		if (isNullOrEmpty(number)) {
			return Year.EMPTY_YEAR;
		}
		int yearNr = NumberValue.parse(number);

		return new Year(checkValue(yearNr));
	}

	private static Integer checkValue(int val) throws ParseException {
		int currentYear = LocalDate.now().getYear();
		if (val <= currentYear % 100 + 5) {
			return 2000 + val;
		}
		if (val < 100) {
			return 1900 + val;
		}
		if (val < 1000) {
			throw new ParseException("", 0);
		}
		return val;
	}



	public static NumberValue parseNumber(String number) throws ParseException {
		if (isNullOrEmpty(number)) {
			return NumberValue.EMTPTY_NUMBER;
		}
		return new NumberValue(NumberValue.parse(number));
	}

	public static Price parsePrice(String value) throws ParseException {
		if (isNullOrEmpty(value)) {
			return Price.EMTPTY_PRICE;
		}
		if (!value.contains(Price.DEFAULT_CURRENCY)) {
			value += " " + Price.DEFAULT_CURRENCY;
		}
		return new Price(Price.EURO_FORMATTER.parse(value).doubleValue());
	}

	public static Area parseArea(String area) throws ParseException {
		if (isNullOrEmpty(area)) {
			return Area.EMTPTY_AREA;
		}
		String trimmedArea = area.replaceAll(Area.SQUARE_METER, "").toLowerCase().replaceAll("qm", "").replaceAll("m2", "").trim();
		return new Area(NumberValue.NUMBER_FORMATTER.parse(trimmedArea).doubleValue());
	}

}
