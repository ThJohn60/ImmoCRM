package net.immocrm.domain.quality;

import net.immocrm.domain.ref.Category;
import net.immocrm.domain.ref.DataTypeEnum;

public interface MerkmalTypeChangeable extends MerkmalType {

	void setName(String name);
	void setDescription(String description);

	void setImmobilieTypes(String immobilieTypes);
	void setDataType(DataTypeEnum dataType);

	boolean isActive();
	void setActive(boolean active);
	
	void setCategory(Category category);
	
}