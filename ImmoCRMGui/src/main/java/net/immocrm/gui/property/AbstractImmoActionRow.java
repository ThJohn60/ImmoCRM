package net.immocrm.gui.property;

import java.time.LocalDate;

import net.immocrm.domain.BaseDomain;
import net.immocrm.domain.vc.ImmoDate;
import net.immocrm.gui.AbstractRow;

public abstract class AbstractImmoActionRow<D extends BaseDomain> extends AbstractRow<D> implements ImmoActionRow {

	public AbstractImmoActionRow(D domain) {
		super(domain);
	}


	@Override
	public String getStartDate() {
		if (getStartImmoDate() != null  &&  !getStartImmoDate().isEmpty()) {
			return getStartImmoDate().toString();
		}
		return "";
	}

	@Override
	public String getEndDate() {
		if (getEndImmoDate() != null  &&  !getEndImmoDate().isEmpty()) {
			return getEndImmoDate().toString();
		}
		return "";
	}


	@Override
	public int hashCode() {
		return hashCodeBase().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AbstractImmoActionRow) {
			return hashCodeBase().equals(((AbstractImmoActionRow<?>)obj).hashCodeBase());
		}
		return false;
	}

	private String hashCodeBase() {
		return getName() + getRole() + getImmobilie().getId();
	}


////////////// compare //////////////

	@Override
	public int compareTo(ImmoActionRow o) {
		AbstractImmoActionRow<?> other = (AbstractImmoActionRow<?>)o;
		if (getComparableStartDate().isBefore(other.getComparableStartDate())) {
			return -1;
		}
		if (getComparableStartDate().isAfter(other.getComparableStartDate())) {
			return 1;
		}
		if (getComparableEndDate().isBefore(other.getComparableEndDate())) {
			return -1;
		}
		if (getComparableEndDate().isAfter(other.getComparableEndDate())) {
			return 1;
		}
		return 0;
	}

	private ImmoDate getComparableStartDate() {
		ImmoDate myStartDate = getStartImmoDate();
		if (myStartDate == null || myStartDate.isEmpty()) {
			return new ImmoDate(LocalDate.of(1000, 1, 1));
		}
		return myStartDate;
	}

	private ImmoDate getComparableEndDate() {
		ImmoDate myEndDate = getEndImmoDate();
		if (myEndDate == null || myEndDate.isEmpty()) {
			return new ImmoDate(LocalDate.of(3000, 1, 1));
		}
		return myEndDate;
	}

}
