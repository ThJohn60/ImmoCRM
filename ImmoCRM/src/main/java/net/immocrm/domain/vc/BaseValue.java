package net.immocrm.domain.vc;

import java.util.Objects;

public class BaseValue<T extends Number> implements Comparable<BaseValue<?>> {

	protected final T value;


	public BaseValue(T val) {
		this.value = val;
	}


	public T getValue() {
		return value;
	}

	public boolean isEmpty() {
		return value == null;
	}


	@Override
	public int hashCode() {
		return Objects.hashCode(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		BaseValue<?> other = (BaseValue<?>) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (other.value == null) {
			return false;
		} else if (value.getClass() != other.value.getClass()) {
			return false;
		} else if (Math.abs(value.doubleValue() - other.value.doubleValue()) >= 0.001)
			return false;
		return true;
	}


	@Override
	public int compareTo(BaseValue<?> o) {
		if (getComparableNumber().doubleValue() < o.getComparableNumber().doubleValue()) {
			return -1;
		}
		if (getComparableNumber().doubleValue() > o.getComparableNumber().doubleValue()) {
			return 1;
		}
		return 0;
	}

	private Number getComparableNumber() {
		if (value == null) {
			return Double.MIN_VALUE;
		}
		return value;
	}

}
