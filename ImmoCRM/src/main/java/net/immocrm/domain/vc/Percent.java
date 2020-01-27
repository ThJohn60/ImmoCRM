package net.immocrm.domain.vc;

public class Percent extends BaseValue<Double> {

	public static final String PERCENT_SYMBOL = "%";


	public Percent() {
		super(null);
	}

	public Percent(Double val) {
		super(val);
	}

	public double getFactor() {
		return toNumber() / 100;
	}

	public double toNumber() {
		if (isEmpty()) {
			return 0.0;
		}
		return value.doubleValue();
	}

	@Override
	public String toString() {
		if (isEmpty()) {
			return "";
		}
		return NumberValue.NUMBER_FORMATTER.format(value) + " " + PERCENT_SYMBOL;
	}

}
