package net.immocrm.domain.ref;

public enum Category {

	Allgemeines,
	Heizung,
	Bad,
	KFZ_Stellplatz,
	Zustand,
	Sonstiges,
	Immobilentyp,
	Internes;

	public String getName() {
		return name().replaceAll("_", "-");
	}

	public static Category byName(String name) {
		String t = name.replaceAll("-", "_");
		return valueOf(t);
	}

}
