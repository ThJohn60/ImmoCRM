package net.immocrm.db.dao;

public class IntegrityConstraintViolationException extends Exception {

	private static final long serialVersionUID = 1L;


	public IntegrityConstraintViolationException(Throwable cause) {
		super("Der Datensatz kann nicht gelöscht werden, da er mit anderen Datensätzen verknüpft ist", cause);
	}
	
}
