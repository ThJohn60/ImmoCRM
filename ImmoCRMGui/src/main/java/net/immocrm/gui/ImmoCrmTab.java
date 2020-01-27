package net.immocrm.gui;

import javafx.scene.control.Tab;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Order;
import net.immocrm.domain.Person;
import net.immocrm.gui.refresh.RefreshedImmobilien;
import net.immocrm.gui.refresh.RefreshedOrders;
import net.immocrm.gui.refresh.RefreshedPersons;

public interface ImmoCrmTab {

	Tab getTab();
	boolean isSelected();

	void refreshPersons(RefreshedPersons persons);
	void refreshImmobilie(RefreshedImmobilien immobilien);
	void refreshOrders(RefreshedOrders orders);
	void refresh();

	Order getSelectedOrder();
	Immobilie getSelectedImmobilie();
	Person getSelectedPerson();

}
