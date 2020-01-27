package net.immocrm.gui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.immocrm.domain.Constants;
import net.immocrm.gui.skin.SkinManager;

public class MainSceneBuilder {

	private final ApplicationCloseHandler closeHandler;
	private final FXMLLoader fxmlLoader;


	public MainSceneBuilder(ApplicationCloseHandler closeHandler) {
		this.closeHandler = closeHandler;
		this.fxmlLoader = new FXMLLoader(getClass().getResource("MainView.fxml"));
	}


	public MainController getController() {
		return fxmlLoader.getController();
	}

	public <T> T getRoot() {
		return fxmlLoader.getRoot();
	}


	public Scene build(Stage primaryStage) throws IOException {
		Scene scene = createScene();
		primaryStage.setScene(scene);

		configureMainWindowByPreferences(primaryStage);
		AppCloseListener listener = SkinManager.init();
		closeHandler.addListener(listener);

		primaryStage.show();
		getController().restoreDividerPos();
		return scene;
	}

	private Scene createScene() throws IOException {
		Scene scene = new Scene(fxmlLoader.load());
		scene.setUserData(this);
		return scene;
	}

	private void configureMainWindowByPreferences(Stage primaryStage) {
		primaryStage.setTitle(Constants.APPLICATION_NAME);
		WindowsSizeManager sizeManager = new WindowsSizeManager(primaryStage);
		closeHandler.addListener(sizeManager);
		sizeManager.setWindowSize();
	}

}
