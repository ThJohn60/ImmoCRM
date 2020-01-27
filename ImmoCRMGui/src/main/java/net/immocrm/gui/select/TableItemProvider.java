package net.immocrm.gui.select;

import java.util.List;

import net.immocrm.domain.BaseDomain;

public interface TableItemProvider<T extends BaseDomain, S extends SelectionRow<T>> {

	List<S> search(String pattern);
	List<S> fetchAll();

}
