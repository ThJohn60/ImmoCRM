package net.immocrm.domain.ref;

public enum ImmobilieCategoryEnum implements EnumWithId {
	Haus(1, 'H'),
	Wohnung(2, 'W'),
	Gewerbegebäude(3, 'B'),
	Grundstück(4, 'G'),
	Stellplatz(5, 'S'),
	Sonstiges(6, 'E');

	private final int id;
	private final String shortcut;

	
	private ImmobilieCategoryEnum(int id, char shortcut){
		this.id = id;
		this.shortcut = String.valueOf(shortcut);		
	}

	
	@Override
	public int getId() {
		return id;
	}

	public String getShortcut() {
		return shortcut;
	}

	public boolean isCompatible(String categoryShortCuts) {
		return categoryShortCuts.contains(shortcut);
	}


	public static ImmobilieCategoryEnum getById(int id) {
		for (ImmobilieCategoryEnum en : ImmobilieCategoryEnum.values()) {
			if (en.getId() == id) {
				return en;
			}
		}
		throw new IllegalArgumentException("no enum found with id=" + id);
	}

}

