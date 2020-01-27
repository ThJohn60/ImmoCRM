package net.immocrm.gui.termin;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.immocrm.domain.termin.Termin;
import net.immocrm.gui.IconProvider;

public class TerminContextMenu extends ContextMenu {

	private final TerminActions terminActions;


	public TerminContextMenu(Termin termin, TerminRefreshable refreshable) {
		getItems().add(editTerminMenu(termin));
		if (!termin.isIndependentTermin()) {
			getItems().add(editDomainMenu(termin));
			getItems().add(gotoMenu(termin));
		}
		terminActions = new TerminActions(refreshable);
	}

	private MenuItem editTerminMenu(Termin termin) {
		MenuItem menuItem = new MenuItem();
		menuItem.setText("Termin bearbeiten");
		menuItem.setGraphic(new ImageView(new Image(IconProvider.class.getResourceAsStream("/rsc/icon/edit_18dp.png"))));
		menuItem.setOnAction(event -> terminActions.editTermin(termin));
		return menuItem;
	}

	private MenuItem editDomainMenu(Termin termin) {
		MenuItem menuItem = new MenuItem();
		menuItem.setText(getMenuText(termin, "bearbeiten"));
		menuItem.setGraphic(new ImageView(new Image(IconProvider.class.getResourceAsStream("/rsc/icon/edit_18dp.png"))));
		menuItem.setOnAction(event -> terminActions.editDomainObject(termin));
		return menuItem;
	}

	private MenuItem gotoMenu(Termin termin) {
		MenuItem menuItem = new MenuItem();
		menuItem.setText(getMenuText(termin, "anzeigen"));
		menuItem.setGraphic(new ImageView(new Image(IconProvider.class.getResourceAsStream("/rsc/icon/arrow_18dp.png"))));
		menuItem.setOnAction(event -> terminActions.gotoDomainObject(termin));
		return menuItem;
	}

	private String getMenuText(Termin termin, String actionText) {
		String menuText = null;
		if (termin.isOrder()) {
			menuText = "Auftrag " + actionText;
		} else if (termin.isPerson()) {
			menuText = "Person " + actionText;
		} else if (termin.isRequest()) {
			menuText = "Anfrage " + actionText;
		}
		return menuText;
	}

}
