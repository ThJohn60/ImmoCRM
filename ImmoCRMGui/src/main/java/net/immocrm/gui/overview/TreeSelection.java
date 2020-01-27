package net.immocrm.gui.overview;

import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Person;

public interface TreeSelection {

	void selectInTree(Person person);
	void selectInTree(Immobilie immobilie);

}
