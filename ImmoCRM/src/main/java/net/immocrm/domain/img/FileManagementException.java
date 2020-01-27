package net.immocrm.domain.img;

import java.io.IOException;

public class FileManagementException extends Exception {

	private static final long serialVersionUID = 1L;


	public FileManagementException(String message, IOException e) {
		super(message, e);
	}

}
