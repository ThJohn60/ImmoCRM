package net.immocrm.gui.tree;

import javafx.scene.control.TreeItem;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Order;
import net.immocrm.domain.TextMaker;
import net.immocrm.gui.IconProvider;
import net.immocrm.gui.order.OrderNodeValue;
import net.immocrm.gui.property.ImmobilieNodeValue;

public final class TreeItemFactory {

	public static TreeItem<BaseNodeValue> orderTreeItemPersonParent(Order order) {
		OrderNodeValue nodeValue = new OrderNodeValue(order, TextMaker.INSTANCE.getTreeNodePersonParentText(order));
		return new TreeItem<>(nodeValue, IconProvider.openOrder16Icon());
	}

	public static TreeItem<BaseNodeValue> orderTreeItemImmobilieParent(Order order) {
		OrderNodeValue nodeValue = new OrderNodeValue(order, TextMaker.INSTANCE.getTreeNodeImmobileParentText(order));
		return new TreeItem<>(nodeValue, IconProvider.openOrder16Icon());
	}

	public static TreeItem<BaseNodeValue> immobileTreeItem(Immobilie immobilie) {
		TreeItem<BaseNodeValue> treeItem = new TreeItem<>(new ImmobilieNodeValue(immobilie), IconProvider.immobilieIcon(immobilie));
		return treeItem;
	}

}
