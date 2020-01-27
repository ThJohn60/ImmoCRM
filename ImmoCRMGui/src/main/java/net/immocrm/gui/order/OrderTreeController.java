package net.immocrm.gui.order;

import static net.immocrm.gui.tree.TreeItemFactory.orderTreeItemPersonParent;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import net.immocrm.domain.Order;
import net.immocrm.gui.refresh.RefreshedOrders;
import net.immocrm.gui.tree.AbstractTreeController;
import net.immocrm.gui.tree.BaseNodeValue;
import net.immocrm.gui.tree.StringNodeValue;

public class OrderTreeController extends AbstractTreeController<Order> {

	private OrderNodesProvider nodesProvider;


	public OrderTreeController(TreeView<BaseNodeValue> orderTreeView, TreeItem<BaseNodeValue> root) {
		super(orderTreeView, root);
	}


	public void fillTree(RefreshedOrders orders) {
		nodesProvider = new OrderNodesProvider(orders.getList());
		super.refreshTree(orders);
	}


	@Override
	protected void fillTree(List<Order> domainList) {
		ObservableList<TreeItem<BaseNodeValue>> yearNodes = root.getChildren();
		yearNodes.clear();
		for (String year : nodesProvider.getYears()) {
			TreeItem<BaseNodeValue> yearNodeItem = new TreeItem<>(new StringNodeValue(year));
			yearNodes.add(yearNodeItem);
			ObservableList<TreeItem<BaseNodeValue>> orderNodes = yearNodeItem.getChildren();
			for (Order order : nodesProvider.getOrderListByYear(year)) {
				orderNodes.add(orderTreeItemPersonParent(order));
			}
		}
		root.setExpanded(true);
//		tree.getSelectionModel().clearSelection();
	}


	@Override
	protected TreeItem<BaseNodeValue> findFirstLevelNode(Order order) {
		String orderYear = nodesProvider.getOrderYear(order);
		for (TreeItem<BaseNodeValue> yearNode : root.getChildren()) {
			if (orderYear.equals(yearNode.getValue().toString())) {
				for (TreeItem<BaseNodeValue> orderNode : yearNode.getChildren()) {
					if (orderNode.getValue().getOrder().equals(order)) {
						return orderNode;
					}
				}
				return null;
			}
		}
		return null;
	}


	@Override
	public boolean selectItem(Order order) {
		TreeItem<BaseNodeValue> orderNode = findFirstLevelNode(order);
		if (orderNode != null) {
			expandOrCollapse(orderNode.getParent(), true);
//			tree.getSelectionModel().select(orderNode);
//			tree.getFocusModel().focus(tree.getSelectionModel().getSelectedIndex());
			return true;
		}
		return false;
	}

	@Override
	protected void refreshItem(Order order) {
		TreeItem<BaseNodeValue> orderNode = findFirstLevelNode(order);
		if (orderNode != null) {
			((OrderNodeValue)orderNode.getValue()).setDomain(order);
			expandOrCollapse(orderNode.getParent(), true);
//			tree.getSelectionModel().select(orderNode);
			tree.getFocusModel().focus(tree.getSelectionModel().getSelectedIndex());
		}
	}


	@Override
	protected TreeItem<BaseNodeValue> firstLevelNode(Order order) {
		return orderTreeItemPersonParent(order);
	}

	@Override
	protected void addSecondLevelChildren(Order order, ObservableList<TreeItem<BaseNodeValue>> children) {
		children.clear();
	}

}
