package net.immocrm.domain;

import net.immocrm.db.MerkmalTypeEntity;
import net.immocrm.domain.quality.MerkmalTypeChangeable;
import net.immocrm.domain.ref.Category;
import net.immocrm.domain.ref.CategoryMapper;
import net.immocrm.domain.ref.DataTypeEnum;
import net.immocrm.domain.ref.ImmobilieCategoryEnum;
import net.immocrm.domain.valid.MerkmalTypeValidator;
import net.immocrm.domain.valid.ValidationIssues;

public class MerkmalTypeDomain extends AbstractDomain implements MerkmalTypeChangeable {

	private MerkmalTypeEntity entity;


	MerkmalTypeDomain() {
		this(null);
	}

	MerkmalTypeDomain(MerkmalTypeEntity e) {
		this.entity = e != null ? e : new MerkmalTypeEntity();
	}


	@Override
	public String getText() {
		return getName();
	}

	@Override
	public String getName() {
		return entity.getName();
	}

	@Override
	public void setName(String name) {
		entity.setName(name);
	}


	@Override
	public boolean isActive() {
		return entity.isActive();
	}

	@Override
	public void setActive(boolean active) {
		entity.setActive(active);
	}

	public boolean isInternal() {
		return entity.isInternal();
	}


	@Override
	public Category getCategory() {
		return CategoryMapper.getCategory(entity.getMerkmalTypeCategory());
	}


	@Override
	public void setCategory(Category category) {
		entity.setCategory(CategoryMapper.getType(category));
	}

	@Override
	public String getImmobileTypes() {
		return entity.getImmobilieTypes();
	}

	@Override
	public void setImmobilieTypes(String types) {
		entity.setImmobilieTypes(types);
	}

	@Override
	public DataTypeEnum getDataType() {
		return DataTypeEnum.valueOf(entity.getDataType());
	}

	@Override
	public void setDataType(DataTypeEnum dataType) {
		entity.setDataType(dataType.name());
	}

	@Override
	public boolean isNumeric() {
		return getDataType() == DataTypeEnum.num  ||  getDataType() == DataTypeEnum.year;
	}

	@Override
	public boolean isCurrency() {
		return getDataType() == DataTypeEnum.curr;
	}

	@Override
	public boolean isArea() {
		return getDataType() == DataTypeEnum.area;
	}

	@Override
	public boolean isText() {
		return getDataType() == DataTypeEnum.text;
	}

	@Override
	public boolean isBoolean() {
		return getDataType() == DataTypeEnum.bool;
	}


	@Override
	public String getDescription() {
		return entity.getDescription();
	}

	@Override
	public void setDescription(String description) {
		entity.setDescription(description);
	}

	@Override
	public boolean isWohnung() {
		return ImmobilieCategoryEnum.Wohnung.isCompatible(entity.getImmobilieTypes());
	}

	@Override
	public boolean isHaus() {
		return ImmobilieCategoryEnum.Haus.isCompatible(entity.getImmobilieTypes());
	}

	@Override
	public boolean isGeschaeft() {
		return ImmobilieCategoryEnum.Gewerbegebäude.isCompatible(entity.getImmobilieTypes());
	}

	@Override
	public boolean isGrundstueck() {
		return ImmobilieCategoryEnum.Grundstück.isCompatible(entity.getImmobilieTypes());
	}

	@Override
	public boolean isKfzStellplatz() {
		return ImmobilieCategoryEnum.Stellplatz.isCompatible(entity.getImmobilieTypes());
	}

	@Override
	public boolean isSonstigesElse() {
		return ImmobilieCategoryEnum.Sonstiges.isCompatible(entity.getImmobilieTypes());
	}


	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	MerkmalTypeEntity getEntity() {
		return entity;
	}


	@Override
	public String getDomainName() {
		return "Ausstattungsmerkmale";
	}

	@Override
	public ValidationIssues validate() {
		return new MerkmalTypeValidator().validate(this);
	}


	@Override
	public int hashCode() {
		if (getId() == null) {
			return getName().hashCode();
		}
		return getId().intValue();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MerkmalTypeDomain other = (MerkmalTypeDomain)obj;
		if (entity.getId() == null || other.getId() == null) {
			return getName().equals(other.getName());
		}
		return entity.getId().intValue() ==  other.getId().intValue();
	}

	@Override
	public String toString() {
		return getName();
	}

}
