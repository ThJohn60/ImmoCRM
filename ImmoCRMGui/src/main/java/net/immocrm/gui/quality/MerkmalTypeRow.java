package net.immocrm.gui.quality;

import net.immocrm.domain.quality.MerkmalType;
import net.immocrm.gui.AbstractRow;

public class MerkmalTypeRow extends AbstractRow<MerkmalType> {

	public MerkmalTypeRow(MerkmalType domain) {
		super(domain);
	}

	
	public String getName() {
		return domain.getName();
	}

	public String getCategory() {
		return domain.getCategory().getName();
	}
	
	public String getDataType() {
		switch (domain.getDataType()) {
		case area:
			return "Fläche";
		case bool:
			return "Ja/Nein";
		case curr:
			return "Währung";
		case num:
			return "Numerisch";
		case text:
			return "Text";
		default:
			break;
		}
		return "";
	}
	
}
