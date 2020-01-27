package net.immocrm.gui;

import javafx.stage.Stage;
import net.immocrm.domain.PreferenceManager;

class WindowsSizeManager implements AppCloseListener {

	private static final long MIN_WIDTH = 1080L;
	private static final long MIN_HEIGHT = 800L;

	private final PreferenceManager prefMan = new PreferenceManager();

	private final Stage primaryStage;


	public WindowsSizeManager(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}


	void saveSize() {
		prefMan.save(PreferenceManager.APP_WIDTH, Math.round(primaryStage.getWidth()));
		prefMan.save(PreferenceManager.APP_HEIGHT, Math.round(primaryStage.getHeight()));
	}

	void setWindowSize() {
		primaryStage.setMinHeight(MIN_HEIGHT);
		primaryStage.setMinWidth(MIN_WIDTH);
		primaryStage.setHeight(getHeight(MIN_WIDTH));
		primaryStage.setWidth(getWidth(MIN_HEIGHT));
	}

	private double getWidth(double defaultValue) {
		Long width = prefMan.fetchLong(PreferenceManager.APP_WIDTH);
		if (width != null) {
			return width;
		}
		return defaultValue;
	}

	private double getHeight(double defaultValue) {
		Long height = prefMan.fetchLong(PreferenceManager.APP_HEIGHT);
		if (height != null) {
			return height;
		}
		return defaultValue;
	}


	@Override
	public void closeAction() {
		saveSize();
	}

}
