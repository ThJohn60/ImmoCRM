package net.immocrm.db.dao;

import org.eclipse.persistence.exceptions.DatabaseException;

public class DatabaseExceptionManager {

	public static boolean isIntegrityConstraintViolation(Exception e) {
		DatabaseException dbException = getDatabaseException(e);
		if (dbException != null) {
			return dbException.getMessage().contains("DerbySQLIntegrityConstraintViolationException");
		}
		return false;
	}


	static DatabaseException getDatabaseException(Exception e) {
		if (e instanceof DatabaseException) {
			return (DatabaseException)e;
		} else if (e.getCause() instanceof DatabaseException) {
			return (DatabaseException)e.getCause();
		}
		return null;
	}

}
