package net.immocrm.domain.quality;

import net.immocrm.db.MerkmalEntity;
import net.immocrm.domain.MerkmalDomain;

public class BooleanMerkmalDomain extends MerkmalDomain {

	private Boolean boolValue;


	public BooleanMerkmalDomain(MerkmalType type, MerkmalEntity entity) {
		super(type, entity);
		assert type.isBoolean();
		boolValue = entity.getValue() != null ? Boolean.valueOf(entity.getValue()) : null;
	}

	public BooleanMerkmalDomain(MerkmalType type, Boolean bool) {
		super(type, bool.toString());
		boolValue = bool;
	}


	@Override
	public String getValue() {
		return boolValue != null ? boolValue.toString() : Boolean.FALSE.toString();
	}

	@Override
	public boolean isBooleanType() {
		return true;
	}

	@Override
	public boolean isSet() {
		return boolValue != null && boolValue.booleanValue();
	}

}
