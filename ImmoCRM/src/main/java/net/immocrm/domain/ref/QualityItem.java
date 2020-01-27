package net.immocrm.domain.ref;

import net.immocrm.domain.quality.MerkmalTypeChangeable;

public class QualityItem implements SelectableItem {

	private final MerkmalTypeChangeable type;
	
	
	public QualityItem(MerkmalTypeChangeable type) {
		this.type = type;
	}


	@Override
	public Integer getId() {
		return type.getId();
	}

	@Override
	public String getText() {
		return type.getName();
	}

	public MerkmalTypeChangeable getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return getText();
	}

}
