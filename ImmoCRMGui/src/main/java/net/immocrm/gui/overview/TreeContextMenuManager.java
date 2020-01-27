package net.immocrm.gui.overview;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import net.immocrm.gui.tree.BaseNodeValue;

public class TreeContextMenuManager {

	private static final List<String> ROOT_ID_LIST = Arrays.asList("newOrder", "newImmobilie", "newPerson");

	private final ContextMenu contextMenu;
	private final Map<String, MenuItem> items = new HashMap<>();


	public TreeContextMenuManager(ContextMenu cm) {
		this.contextMenu = cm;
		for (MenuItem menuItem : contextMenu.getItems()) {
			items.put(menuItem.getId(), menuItem);
		}
	}

	public void onShowingContextMenu(BaseNodeValue node) {
		if (node == null || !node.isDomainNode()) {
			for (MenuItem menuItem : contextMenu.getItems()) {
				if (!ROOT_ID_LIST.contains(menuItem.getId())) {
					menuItem.setDisable(true);
				}
			}
			return;
		}
		for (MenuItem menuItem : items.values()) {
			menuItem.setDisable(false);
		}
		contextMenu.getItems().clear();
		if (node.isImmobilieNode()) {
			contextMenu.getItems().add(items.get("newOrder"));
			contextMenu.getItems().add(items.get("editItem"));
			contextMenu.getItems().add(items.get("addImages"));
			contextMenu.getItems().add(new SeparatorMenuItem());
			contextMenu.getItems().add(items.get("gotoItem"));
			contextMenu.getItems().add(items.get("showInMap"));
			contextMenu.getItems().add(items.get("copyToClipboard"));
			contextMenu.getItems().add(items.get("copyAddressToClipboard"));
			contextMenu.getItems().add(new SeparatorMenuItem());
			contextMenu.getItems().add(items.get("deleteItem"));
		} else if (node.isPersonNode()) {
			contextMenu.getItems().add(items.get("newImmobilie"));
			contextMenu.getItems().add(items.get("newOrder"));
			contextMenu.getItems().add(items.get("editItem"));
			contextMenu.getItems().add(new SeparatorMenuItem());
			contextMenu.getItems().add(items.get("gotoItem"));
			contextMenu.getItems().add(items.get("showInMap"));
			contextMenu.getItems().add(items.get("mailToPerson"));
			contextMenu.getItems().add(items.get("copyToClipboard"));
			contextMenu.getItems().add(items.get("copyAddressToClipboard"));
			contextMenu.getItems().add(new SeparatorMenuItem());
			contextMenu.getItems().add(items.get("deleteItem"));
		} else if (node.isOrderNode()) {
			contextMenu.getItems().add(items.get("newRequest"));
			contextMenu.getItems().add(items.get("editItem"));
			contextMenu.getItems().add(new SeparatorMenuItem());
			contextMenu.getItems().add(items.get("gotoItem"));
			contextMenu.getItems().add(items.get("copyToClipboard"));
			contextMenu.getItems().add(items.get("copyAddressToClipboard"));
			contextMenu.getItems().add(new SeparatorMenuItem());
			contextMenu.getItems().add(items.get("deleteItem"));
		}
		for (MenuItem menuItem : contextMenu.getItems()) {
			menuItem.setUserData(new MenuItemInfo(node));
		}
	}

}
