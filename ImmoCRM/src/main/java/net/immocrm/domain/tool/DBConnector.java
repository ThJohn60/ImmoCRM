package net.immocrm.domain.tool;

import net.immocrm.db.dao.DbAlreadyOpenException;
import net.immocrm.db.dao.DbConnectException;
import net.immocrm.db.dao.EntityManagerProvider;

public class DBConnector {

	private final EntityManagerProvider emp;

	private boolean connected;


	public DBConnector() {
		this.emp = new EntityManagerProvider();
	}


	public void connect() throws Exception {
		try {
			emp.connect();
			connected = true;
		} catch (DbAlreadyOpenException e) {
			throw new Exception(e.getMessage());
		} catch (DbConnectException e) {
			int colon = e.getMessage().indexOf(":");
			String msg = (colon > 0) ? e.getMessage().substring(colon + 1) : e.getMessage();
			throw new Exception(msg);
		}
	}

	public void disconnect() {
		try {
			if (connected) {
				emp.shutdown();
			}
		} catch (Exception e) {
			// ignore
		}
	}

}
