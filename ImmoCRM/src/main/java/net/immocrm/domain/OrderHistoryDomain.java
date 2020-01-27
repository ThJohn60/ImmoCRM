package net.immocrm.domain;

import net.immocrm.db.OrderHistoryEntity;
import net.immocrm.domain.valid.ValidationIssues;

class OrderHistoryDomain extends AbstractDomain implements OrderHistory {

	private final OrderHistoryEntity entity;


	OrderHistoryDomain() {
		this(null);
	}

	OrderHistoryDomain(OrderHistoryEntity e) {
		this.entity = e != null ? e : new OrderHistoryEntity();
	}


	@Override
	public Order getOrder() {
		return DomainFactory.createDomain(entity.getOrder());
	}



	@Override
	public String getOldValue() {
		return entity.getOldValue();
	}

	public void setOldValue(String oldValue) {
		entity.setOldValue(oldValue);
	}

	@Override
	public String getNewValue() {
		return entity.getNewValue();
	}

	public void setNewValue(String newValue) {
		entity.setNewValue(newValue);
	}

	@Override
	public String getDescription() {
		return entity.getDescription();
	}

	public void setDescription(String description) {
		entity.setDescription(description);
	}


	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	OrderHistoryEntity getEntity() {
		return entity;
	}


	@Override
	public String getDomainName() {
		return "";
	}

	@Override
	public ValidationIssues validate() {
		return ValidationIssues.emptyIssues;
	}


	@Override
	public String toString() {
		return String.format("%s: %s", getCreateTimestamp(), getDescription());
	}

	@Override
	public int compareTo(OrderHistory o) {
		return getCreateTimestamp().compareTo(o.getCreateTimestamp());
	}

}
