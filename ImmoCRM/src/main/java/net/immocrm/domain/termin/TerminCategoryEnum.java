package net.immocrm.domain.termin;

public enum TerminCategoryEnum {
	notar("Notartermin", 1),
	handover("Übergabe", 2),
	billed("Rechnung gestellt", 3),
	birthday("Geburtstag", 4),
	besichtigung("Besichtigung", 5),
	meeting("Besprechung", 6),
	personal("Persönlich", 7),
	other("Sonstiges", 8);

	private final String name;
	private final int id;

	private TerminCategoryEnum(String name, int id) {
		this.name = name;
		this.id = id;
	}


	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static TerminCategoryEnum byId(int id) {
		for (TerminCategoryEnum en : TerminCategoryEnum.values()) {
			if (en.getId() == id) {
				return en;
			}
		}
		throw new IllegalArgumentException("no enum found with id=" + id);
	}

	public static TerminCategoryEnum byName(String n) {
		for (TerminCategoryEnum en : TerminCategoryEnum.values()) {
			if (en.getName().equals(n)) {
				return en;
			}
		}
		throw new IllegalArgumentException("no enum found with name=" + n);
	}

}
