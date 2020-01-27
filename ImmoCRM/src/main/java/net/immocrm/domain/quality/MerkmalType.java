package net.immocrm.domain.quality;

import net.immocrm.domain.BaseDomain;
import net.immocrm.domain.ref.Category;
import net.immocrm.domain.ref.DataTypeEnum;
import net.immocrm.domain.ref.SelectableItem;

public interface MerkmalType extends BaseDomain, SelectableItem {

	String getName();
	String getDescription();

	String getImmobileTypes();
	DataTypeEnum getDataType();

	Category getCategory();

	boolean isWohnung();
	boolean isHaus();
	boolean isGeschaeft();
	boolean isGrundstueck();
	boolean isKfzStellplatz();
	boolean isSonstigesElse();
	
	boolean isNumeric();
	boolean isBoolean();
	boolean isCurrency();
	boolean isArea();
	boolean isText();
	
}