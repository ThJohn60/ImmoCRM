package net.immocrm.gui.property;

import net.immocrm.domain.Immobilie;
import net.immocrm.domain.TextMaker;
import net.immocrm.gui.tree.StdNodeValue;

public class ImmobilieNodeValue extends StdNodeValue<Immobilie> {


	public ImmobilieNodeValue(Immobilie immobilie) {
		super(immobilie);
	}


	@Override
	public boolean isImmobilieNode() {
		return true;
	}

	@Override
	public Immobilie getImmobilie() {
		return super.getDomain();
	}


	@Override
	public String toString() {
		return TextMaker.INSTANCE.getTreeNodeText(getImmobilie());
	}

}
