package net.immocrm.domain.vc;

public class Year extends NumberValue {

	public static final Year EMPTY_YEAR = new Year();

	public Year() {
		super();
	}

	public Year(Integer val) {
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
