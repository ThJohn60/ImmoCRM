package net.immocrm.gui.select;

import java.util.ArrayList;
import java.util.List;

import net.immocrm.domain.Immobilie;
import net.immocrm.domain.ImmobilieReader;

public class ImmobilieSelectionRowProvider implements TableItemProvider<Immobilie, ImmobilieSelectionRow>{

	@Override
	public List<ImmobilieSelectionRow> search(String pattern) {
		List<ImmobilieSelectionRow> result = new ArrayList<>();
		for (Immobilie im : ImmobilieReader.INSTANCE.find(pattern)) {
			result.add(new ImmobilieSelectionRow(im));
		}
		return result;
	}

	@Override
	public List<ImmobilieSelectionRow> fetchAll() {
		List<ImmobilieSelectionRow> result = new ArrayList<>();
		for (Immobilie im : ImmobilieReader.INSTANCE.find(null)) {
			result.add(new ImmobilieSelectionRow(im));
		}
		return result;
	}

}
