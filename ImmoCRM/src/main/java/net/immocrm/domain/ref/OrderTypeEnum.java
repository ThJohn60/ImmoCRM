package net.immocrm.domain.ref;

public enum OrderTypeEnum implements EnumWithId {
	Verkauf(1),
	Kauf(2),
	Vermietung(3),
	Mietgesuch(4);

	private final int id;

	
	private OrderTypeEnum(int id){
		this.id = id;		
	}

	
	@Override
	public int getId() {
		return id;
	}

	public static OrderTypeEnum byId(int id) {
		for (OrderTypeEnum en : OrderTypeEnum.values()) {
			if (en.getId() == id) {
				return en;
			}
		}
		throw new IllegalArgumentException("no enum found with id=" + id);
	}

}

