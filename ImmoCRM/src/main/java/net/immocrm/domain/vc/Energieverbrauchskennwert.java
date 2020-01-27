package net.immocrm.domain.vc;

public class Energieverbrauchskennwert extends BaseValue<Double> {

	public static final String UNIT = "kWh/(a m²)";


	public Energieverbrauchskennwert() {
		this(null);
	}

	public Energieverbrauchskennwert(Double val) {
		super(val);
	}


	@Override
	public String toString() {
		if (value == null) {
			return "";
		}
		return NumberValue.NUMBER_FORMATTER.format(value) + " " + UNIT;
	}

}
