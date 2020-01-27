package net.immocrm.domain.vc;

public class Area extends BaseValue<Double> {

	public static final Area EMTPTY_AREA = new Area();
	public static final String SQUARE_METER = "\u33A1";


	public Area() {
		super(null);
	}

	public Area(Double val) {
		super(val);
	}


	@Override
	public String toString() {
		if (value == null) {
			return "";
		}
		return NumberValue.NUMBER_FORMATTER.format(value) + " " + SQUARE_METER;
	}

}
