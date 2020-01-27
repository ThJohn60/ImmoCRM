package net.immocrm.gui.property;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Order;
import net.immocrm.gui.tree.AbstractTreeController;
import net.immocrm.gui.tree.BaseNodeValue;
import net.immocrm.gui.tree.StdNodeValue;
import net.immocrm.gui.tree.TreeItemFactory;

public class ImmobilieTreeController extends AbstractTreeController<Immobilie> {

	public ImmobilieTreeController(TreeView<BaseNodeValue> immobilieTreeView, TreeItem<BaseNodeValue> root) {
		super(immobilieTreeView, root);
	}


	@Override
	protected TreeItem<BaseNodeValue> firstLevelNode(Immobilie immobilie) {
		TreeItem<BaseNodeValue> treeItem = TreeItemFactory.immobileTreeItem(immobilie);
		treeItem.setExpanded(false);
		return treeItem;
	}

	@Override
	protected void addSecondLevelChildren(Immobilie immobilie, ObservableList<TreeItem<BaseNodeValue>> children) {
		children.clear();
		for (Order order: immobilie.getOrders()) {
			children.add(TreeItemFactory.orderTreeItemImmobilieParent(order));
		}
	}

	@Override
	public boolean selectItem(Immobilie immobilie) {
		for (TreeItem<BaseNodeValue> ti : root.getChildren()) {
			if (((StdNodeValue<?>)ti.getValue()).getImmobilie().equals(immobilie)) {
				root.setExpanded(true);
				tree.getSelectionModel().select(ti);
				tree.getFocusModel().focus(tree.getSelectionModel().getSelectedIndex());
				return true;
			}
		}
		return false;
	}

	@Override
	protected TreeItem<BaseNodeValue> findFirstLevelNode(Immobilie im) {
		for (TreeItem<BaseNodeValue> treeItem : root.getChildren()) {
			BaseNodeValue value = treeItem.getValue();
			if (value.getImmobilie().equals(im)) {
				return treeItem;
			}
		}
		return null;
	}

}
