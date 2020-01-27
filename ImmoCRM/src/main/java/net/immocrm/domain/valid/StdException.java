package net.immocrm.domain.valid;

import net.immocrm.domain.BaseDomain;

public class StdException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String problemType;
	private BaseDomain involved;


	public StdException(String problemType, BaseDomain involved, Throwable cause) {
		super(cause);
		this.problemType = problemType;
		this.involved = involved;
	}

	public StdException(String problemType, BaseDomain involved, String msg) {
		super(msg);
		this.problemType = problemType;
		this.involved = involved;
	}

	public StdException(String problemType, BaseDomain involved, Throwable cause, String pattern, Object... args) {
		this(problemType, cause, pattern, args);
		this.involved = involved;
	}

	public StdException(String problemType, String pattern, Object... args) {
		this(problemType, String.format(pattern, args));
	}

	public StdException(String problemType, Throwable cause, String pattern, Object... args) {
		this(problemType, String.format(pattern, args), cause);
	}

	public StdException(String problemType, String msg, Throwable cause) {
		super(msg, cause);
		this.problemType = problemType;
	}

	public StdException(String problemType, String msg) {
		super(msg);
		this.problemType = problemType;
	}

	public StdException(String message, Throwable cause) {
		super(message, cause);
		this.problemType = "";
	}

	public StdException(String message) {
		super(message);
		this.problemType = "";
	}


	public String getProblemType() {
		return problemType;
	}

	public BaseDomain getInvolvedDomainObject() {
		return involved;
	}

}
