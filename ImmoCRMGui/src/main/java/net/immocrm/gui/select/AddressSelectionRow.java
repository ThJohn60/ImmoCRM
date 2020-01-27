package net.immocrm.gui.select;

import net.immocrm.domain.Address;

public class AddressSelectionRow extends SelectionRow<Address> {

	private final Address address;


	public AddressSelectionRow(Address address) {
		super(address);
		this.address = address;
	}


	public String getStreet() {
		return address.getStreet();
	}

	public String getPostalCode() {
		return address.getPostalCode();
	}

	public String getCity() {
		return address.getCity();
	}

}
