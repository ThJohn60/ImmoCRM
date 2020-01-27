package net.immocrm.gui.select;

import java.util.ArrayList;
import java.util.List;

import net.immocrm.domain.Address;
import net.immocrm.domain.AddressManager;

public class AddressItemProvider implements TableItemProvider<Address, AddressSelectionRow>{

	private final AddressManager addressMan;


	public AddressItemProvider() {
		addressMan = new AddressManager();
	}


	@Override
	public List<AddressSelectionRow> search(String pattern) {
		List<AddressSelectionRow> result = new ArrayList<>();
		for (Address addr : addressMan.search(pattern)) {
			result.add(new AddressSelectionRow(addr));
		}
		return result;
	}

	@Override
	public List<AddressSelectionRow> fetchAll() {
		List<AddressSelectionRow> result = new ArrayList<>();
		for (Address addr : addressMan.fetchAll()) {
			result.add(new AddressSelectionRow(addr));
		}
		return result;
	}

}
