package net.immocrm.gui;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.collections.ObservableMap;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.immocrm.domain.Constants;
import net.immocrm.domain.tool.DBConnector;


public class Main extends Application {

	private static Main me;

	public static boolean isStarted() {
		return me.isStarted;
	}

	public static HostServices getApplicationHostServices() {
		return me.getHostServices();
	}

	public static Window getApplicationWindow() {
		return me.appScene.getWindow();
	}

	public static void addCloseListener(AppCloseListener e) {
		me.closeHandler.addListener(e);
	}

	/////////////////////////////////////////////////////////////////////

	private final DBConnector dbConnector;
	private final ApplicationCloseHandler closeHandler;

	private Scene appScene;
	private boolean isStarted = false;


	public Main() {
		dbConnector = new DBConnector();
		closeHandler = new ApplicationCloseHandler();
		Main.me = this;
	}

	@Override
	public void start(Stage primaryStage) {
		double startTime = System.currentTimeMillis();
		primaryStage.getIcons().add(IconProvider.smallAppIcon());
		primaryStage.getIcons().add(IconProvider.largeAppIcon());

		try {
			if (dbConnect()) {
				MainSceneBuilder sceneBuilder = new MainSceneBuilder(closeHandler);
				appScene = sceneBuilder.build(primaryStage);
				addAccelerators();
				isStarted = true;

				MainController.getRefreshDispatcher().refreshInitial();
				double runtime = System.currentTimeMillis() - startTime;
				System.out.println(String.format("Startzeit: %.1fs", (runtime / 1000)));
			}
		} catch(Exception e) {
			if (Constants.DEBUG_MODE) {
				e.printStackTrace();
			}
			exitByError(e);
		}
		System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
	}

	private void addAccelerators() {
		ObservableMap<KeyCombination,Runnable> accelerators = appScene.getAccelerators();
		accelerators.put(new KeyCodeCombination(KeyCode.PAGE_DOWN), new Runnable() {

			@Override
			public void run() {
				MainController.instance().pageDown();
			}
		} );
		accelerators.put(new KeyCodeCombination(KeyCode.PAGE_UP), new Runnable() {

			@Override
			public void run() {
				MainController.instance().pageUp();
			}
		} );
	}

	private boolean dbConnect() {
		try {
			dbConnector.connect();
			return true;
		} catch (Exception e) {
			exitByError(e);
		}
		return false;
	}

	private void exitByError(Exception e) {
		new Alert(AlertType.ERROR, e.getMessage()).showAndWait();
		Platform.exit();
	}


	@Override
	public void stop() throws Exception {
		closeHandler.performCloseActions();
		dbConnector.disconnect();
	}

}
