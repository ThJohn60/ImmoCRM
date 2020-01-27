package net.immocrm.db.dao;

public class DbAlreadyOpenException extends DbConnectException {

	private static final long serialVersionUID = 1L;

	public DbAlreadyOpenException() {
		super("Die Applikation ist bereits geöffnet. Sie kann nur einmal gestartet werden.");
	}

}
