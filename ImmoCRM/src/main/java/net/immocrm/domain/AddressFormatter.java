package net.immocrm.domain;

class AddressFormatter {

	public String getAddressText(Address a) {
		if (a.isEmpty()) {
			return "";
		}
		if (a.getStreet2() != null) {
			return a.getStreet() + "\n" + a.getStreet2() + "\n" + a.getPostalCodeAndCity();
		}
		return a.getStreet() + "\n" + getPostalCodeAndCity(a);
	}

	public String getCompleteStreet(Address a) {
		if (a.getStreet2() != null) {
			return a.getStreet() + ", " + a.getStreet2();
		}
		return a.getStreet();
	}

	public String getPostalCodeAndCity(Address a) {
		if (a.isEmpty()) {
			return "";
		}
		return a.getPostalCode() + " " + a.getCity();
	}

}
