package net.immocrm.domain.quality;

import net.immocrm.domain.BaseDomain;
import net.immocrm.domain.ref.Category;

public interface Merkmal extends BaseDomain {

	String getValue();
	boolean isSet();

	String getTypeName();

	MerkmalType getMerkmalType();
	Category getCategory();

}