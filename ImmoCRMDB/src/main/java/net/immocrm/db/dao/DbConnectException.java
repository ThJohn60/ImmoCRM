package net.immocrm.db.dao;

public class DbConnectException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DbConnectException(String message) {
		super(message);
	}

	public DbConnectException(String message, Throwable cause) {
		super(message, cause);
	}

}
