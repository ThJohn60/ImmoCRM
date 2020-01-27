package net.immocrm.domain.ref;

public enum OrderStateEnum implements EnumWithId {
	OPEN(1, false, "Offen"),
	BEFORE_NOTAR(7, true, "Vor Notartermin"),
	AFTER_NOTAR(2, true, "Vor Übergabe"),
	HANDOVER(3, false, "Nach Übergabe"),
	BILLED(4, false, "Rechnung gestellt"),
	FINISHED(5, false, "Abgeschlossen"),
	CANCELED(6, false, "Abgebrochen");


	private final int id;
	private final boolean saleOrderOnly;
	private final String label;


	private OrderStateEnum(int id, boolean saleOrderOnly, String l) {
		this.id = id;
		this.saleOrderOnly = saleOrderOnly;
		this.label = l;
	}


	@Override
	public int getId() {
		return id;
	}

	public boolean isSaleOrderOnly() {
		return saleOrderOnly;
	}

	public String getLabel() {
		return label;
	}

	public boolean isFinished() {
		return this == FINISHED  ||  this == CANCELED;
	}


	public static OrderStateEnum getById(int id) {
		for (OrderStateEnum en : OrderStateEnum.values()) {
			if (en.getId() == id) {
				return en;
			}
		}
		throw new IllegalArgumentException("no enum found with id=" + id);
	}

	@Override
	public String toString() {
		return label;
	}

}
