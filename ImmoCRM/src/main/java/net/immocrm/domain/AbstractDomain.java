package net.immocrm.domain;

import net.immocrm.db.BaseEntity;
import net.immocrm.domain.vc.ImmoDateTime;

abstract class AbstractDomain implements BaseDomain {

	abstract BaseEntity getEntity();


	@Override
	public Integer getId() {
		return getEntity().getId();
	}

	@Override
	public boolean isNew() {
		return getId() == null;
	}
	
	@Override
	public boolean isSameId(BaseDomain d) {
		return getId() != null && d.getId() != null && getId().equals(d.getId()); 
	}
	
	@Override
	public ImmoDateTime getCreateTimestamp() {
		return new ImmoDateTime(getEntity().getCreatedOn());
	}

}
