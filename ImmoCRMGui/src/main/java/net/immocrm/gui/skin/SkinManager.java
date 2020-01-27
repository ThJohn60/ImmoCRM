package net.immocrm.gui.skin;

import com.sun.javafx.css.StyleManager;

import javafx.fxml.FXML;
import javafx.scene.control.RadioMenuItem;
import net.immocrm.domain.Preference;
import net.immocrm.domain.PreferenceManager;
import net.immocrm.gui.AppCloseListener;


public class SkinManager implements AppCloseListener {

	private static final String STANDARD_STYLE_SHEET_FILE_NAME = "application.css";
	private static final String BLUE_STYLE_SHEET_FILE_NAME = "blueStyle.css";
	private static final String ORANGE_STYLE_SHEET_FILE_NAME = "orangeStyle.css";

	private static SkinManager me;

	public static AppCloseListener init() {
		Preference pref = new PreferenceManager().fetchPreferences(PreferenceManager.SKIN);
		String skin = (pref != null) ? pref.getValue() : STANDARD_STYLE_SHEET_FILE_NAME;
		me.changeToSkin(skin);
		return me;
	}


	@FXML RadioMenuItem standardSkinMenu;
	@FXML RadioMenuItem blueSkinMenu;
	@FXML RadioMenuItem orangeSkinMenu;

	private String currentSkin;


	public SkinManager() {
		me = this;
	}


	@Override
	public void closeAction() {
		new PreferenceManager().save(PreferenceManager.SKIN, currentSkin);
	}

	@FXML
	public void showStandardSkin() {
		changeToSkin(STANDARD_STYLE_SHEET_FILE_NAME);
	}

	@FXML
	public void showBlueSkin() {
		changeToSkin(BLUE_STYLE_SHEET_FILE_NAME);
	}

	@FXML
	public void showOrangeSkin() {
		changeToSkin(ORANGE_STYLE_SHEET_FILE_NAME);
	}

	private void changeToSkin(String skin) {
		if (currentSkin != null) {
			String oldSkinUrl = getClass().getResource(currentSkin).toExternalForm();
			StyleManager.getInstance().removeUserAgentStylesheet(oldSkinUrl);
		}

		String newSkinUrl = getClass().getResource(skin).toExternalForm();
		StyleManager.getInstance().addUserAgentStylesheet(newSkinUrl);
		checkSuitableMenu(skin);
		currentSkin = skin;
	}

	private void checkSuitableMenu(String skin) {
		if (skin.equals(STANDARD_STYLE_SHEET_FILE_NAME)) {
			standardSkinMenu.setSelected(true);
		} else if (skin.equals(ORANGE_STYLE_SHEET_FILE_NAME)) {
			orangeSkinMenu.setSelected(true);
		} else if (skin.equals(BLUE_STYLE_SHEET_FILE_NAME)) {
			blueSkinMenu.setSelected(true);
		}
	}

}
