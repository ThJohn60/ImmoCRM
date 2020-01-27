package net.immocrm.domain.valid;

import net.immocrm.domain.BaseDomain;

public class DbRemoveException extends DbException {

	private static final long serialVersionUID = 1L;

	public DbRemoveException(BaseDomain involved, String doaminTypeName, Throwable cause) {
		super("Problem beim Löschen", involved, cause, "der Eintrag \"%s\"\nkann nicht geöscht werden, da %s mit anderen Datensätzen verknüpft ist", involved, doaminTypeName);
	}

	public DbRemoveException(String message) {
		super(message);
	}

}
