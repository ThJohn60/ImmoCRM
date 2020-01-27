package net.immocrm.domain.valid;

import net.immocrm.domain.BaseDomain;

public class DbException extends StdException {

	private static final long serialVersionUID = 1L;


	public DbException(String problemType, BaseDomain involved, Throwable cause, String pattern, Object... args) {
		super(problemType, involved, cause, pattern, args);
	}

	public DbException(String problemType, String msg, Throwable cause) {
		super(problemType, msg, cause);
	}

	public DbException(String message) {
		super(message);
	}

}
