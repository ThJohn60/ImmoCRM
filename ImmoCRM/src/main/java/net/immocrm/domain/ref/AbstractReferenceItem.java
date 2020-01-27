package net.immocrm.domain.ref;

abstract class AbstractReferenceItem implements SelectableItem {

	private final Integer id;
	private final String text;


	AbstractReferenceItem() {
		this(null, "unbekannt");
	}

	AbstractReferenceItem(Integer id, String text) {
		this.id = id;
		this.text = text;
	}


	public boolean isUnknown() {
		return id == null;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return getText();
	}

	@Override
	public int hashCode() {
		return getText().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return getText().equals(((SelectableItem)obj).getText());
	}

}
