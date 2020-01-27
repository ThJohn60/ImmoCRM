package net.immocrm.gui.select;

import java.util.ArrayList;
import java.util.List;

import net.immocrm.domain.Notar;
import net.immocrm.domain.NotarReader;

public class NotarItemProvider implements TableItemProvider<Notar, NotarSelectionRow>{

	@Override
	public List<NotarSelectionRow> search(String pattern) {
		List<NotarSelectionRow> result = new ArrayList<>();
		for (Notar no : NotarReader.INSTANCE.find(pattern)) {
			result.add(new NotarSelectionRow(no));
		}
		return result;
	}

	@Override
	public List<NotarSelectionRow> fetchAll() {
		List<NotarSelectionRow> result = new ArrayList<>();
		for (Notar no : NotarReader.INSTANCE.fetchAll()) {
			result.add(new NotarSelectionRow(no));
		}
		return result;
	}

}
