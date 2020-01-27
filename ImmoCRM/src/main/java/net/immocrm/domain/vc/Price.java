package net.immocrm.domain.vc;

import java.text.NumberFormat;
import java.util.Locale;

public class Price extends BaseValue<Long> {

	public static final NumberFormat EURO_FORMATTER = NumberFormat.getCurrencyInstance(Locale.GERMANY);
	public static final Price EMTPTY_PRICE = new Price();
	public static final String DEFAULT_CURRENCY = "€";


	public Price() {
		this((Long)null);
	}

	public Price(double val) {
		this(Math.round(val * 100));
	}

	public Price(long val) {
		this(Long.valueOf(val));
	}

	public Price(String val) {
		this(Long.decode(val));
	}

	public Price(Long val) {
		super(val);
	}


	public Double getDoubleValue() {
		return (double)value / 100;
	}


	public Price plus(Price p) {
		return Calculator.plus(this, p);
	}

	public Price multiply(Percent pc) {
		return Calculator.multiply(this, pc);
	}

	public Price multiply(Number num) {
		return Calculator.multiply(this, num.doubleValue());
	}

	public Price divideBy(Area a) {
		return Calculator.divide(this, a);
	}

	public Price multiply(double val) {
		return Calculator.multiply(this, val);
	}


	@Override
	public String toString() {
		if (value == null) {
			return "";
		}
		return EURO_FORMATTER.format(getDoubleValue());
	}

}
