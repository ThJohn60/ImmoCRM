package net.immocrm.gui.refresh;

public interface PersonViewRefreshable {

	boolean isVisible();

	void refresh(RefreshedPersons persons);

}
