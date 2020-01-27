package net.immocrm.gui.overview;

import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Order;
import net.immocrm.domain.Person;

public interface OverviewTreeSelectionListener {

	void showPersonDetails(Person person);

	void showImmobileDetails(Immobilie immobilie);

	void showOrderDetails(Order order);

}
