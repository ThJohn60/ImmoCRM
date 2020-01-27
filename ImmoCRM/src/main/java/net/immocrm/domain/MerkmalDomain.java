package net.immocrm.domain;

import net.immocrm.db.MerkmalEntity;
import net.immocrm.domain.quality.Merkmal;
import net.immocrm.domain.quality.MerkmalType;
import net.immocrm.domain.ref.Category;
import net.immocrm.domain.valid.MerkmalValidator;
import net.immocrm.domain.valid.ValidationIssues;

public class MerkmalDomain extends AbstractDomain implements Merkmal {

	private final MerkmalEntity entity;
	private final MerkmalType merkmalType;


	public MerkmalDomain(MerkmalType type, String value) {
		this(type);
		entity.setValue(value);
	}

	public MerkmalDomain(MerkmalType type) {
		this(type, new MerkmalEntity());
	}

	public MerkmalDomain(MerkmalType type, MerkmalEntity entity) {
		this.merkmalType = type;
		this.entity = entity;
		entity.setType(((MerkmalTypeDomain)type).getEntity());
	}


	@Override
	public String getValue() {
		return entity.getValue();
	}

	public void setValue(String value) {
		entity.setValue(value);
	}


	public Integer getImmobilieId() {
		return entity.getImmobilieId();
	}

	public void setImmobilieId(Integer id) {
		entity.setImmobilieId(id);
	}

	@Override
	public MerkmalType getMerkmalType() {
		return merkmalType;
	}

	@Override
	public String getTypeName() {
		return merkmalType.getName();
	}

	protected boolean isBooleanType() {
		return false;
	}

	@Override
	public Category getCategory() {
		return merkmalType.getCategory();
	}

	@Override
	public boolean isSet() {
		throw new UnsupportedOperationException("access not allowd for non bool attribute");
	}

	@Override
	public boolean isEmpty() {
		if (merkmalType.isBoolean()) {
			return !isSet();
		}
		return getValue() == null || getValue().isEmpty();
	}

	@Override
	protected MerkmalEntity getEntity() {
		return entity;
	}

	@Override
	public String getDomainName() {
		return "Ausstaungsmerkmale";
	}


	@Override
	public ValidationIssues validate() {
		return new MerkmalValidator().validate(this);
	}

	@Override
	public String toString() {
		return getTypeName() + ": " + getValue();
	}

}
