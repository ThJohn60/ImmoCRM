package net.immocrm.db;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

import net.immocrm.db.tools.EntityUtil;

abstract class AbstractEntity implements BaseEntity {

	@Override
	public abstract Integer getId();
	
	@Override
	public boolean isNew() {
		return getId() == null;
	}
	
	
	@Override
	public abstract Timestamp getCreatedOn();
	public abstract void setCreatedOn(Timestamp ldt);
	
	
	protected void setDefaultCreatedOn() {
		if (getCreatedOn() == null) {
			setCreatedOn(EntityUtil.toTimestamp(LocalDateTime.now()));
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntity other = (AbstractEntity) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
	
}
