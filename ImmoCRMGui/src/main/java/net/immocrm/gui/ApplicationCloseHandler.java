package net.immocrm.gui;

import java.util.ArrayList;
import java.util.List;

public class ApplicationCloseHandler {

	private final List<AppCloseListener> closeListener;


	public ApplicationCloseHandler() {
		this.closeListener = new ArrayList<AppCloseListener>();
	}


	public boolean addListener(AppCloseListener e) {
		return closeListener.add(e);
	}

	public void performCloseActions() {
		for (AppCloseListener listener : closeListener) {
			try {
				listener.closeAction();
			} catch (Exception e) {
				// ignore
			}
		}
	}

}
