package net.immocrm.domain.ref;

public enum EnergieausweisTypeEnum implements EnumWithId {
	Bedarfsausweis(1),
	Verbrauchsausweis(2),
	Unbekannt(3);

	private final int id;


	private EnergieausweisTypeEnum(int id){
		this.id = id;
	}


	@Override
	public int getId() {
		return id;
	}

	public static EnergieausweisTypeEnum getById(int id) {
		for (EnergieausweisTypeEnum en : EnergieausweisTypeEnum.values()) {
			if (en.getId() == id) {
				return en;
			}
		}
		throw new IllegalArgumentException("no enum found with id=" + id);
	}

}

