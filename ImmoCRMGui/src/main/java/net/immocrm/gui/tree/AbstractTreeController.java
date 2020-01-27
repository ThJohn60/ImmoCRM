package net.immocrm.gui.tree;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import net.immocrm.domain.BaseDomain;
import net.immocrm.gui.refresh.RefreshedDomain;

public abstract class AbstractTreeController<D extends BaseDomain> {

	protected final TreeView<BaseNodeValue> tree;
	protected final TreeItem<BaseNodeValue> root;
	private boolean inDeleteOperation = false;


	public AbstractTreeController(TreeView<BaseNodeValue> tree, TreeItem<BaseNodeValue> root) {
		this.tree = tree;
		this.root = root;
	}


	public boolean isInDeleteOperation() {
		return inDeleteOperation;
	}


	public BaseNodeValue getSelectedItem() {
		TreeItem<BaseNodeValue> selectedTreeItem = tree.getSelectionModel().getSelectedItem();
		if (selectedTreeItem != null && selectedTreeItem.getValue().isDomainNode()) {
			return selectedTreeItem.getValue();
		}
		return null;
	}

	public void deselect() {
		tree.getSelectionModel().clearSelection();
	}


	public void expandAll() {
		expandOrCollapseAll(true);
	}

	public void collapseAll() {
		expandOrCollapseAll(false);
	}

	public void expandOrCollapseAll(boolean expand) {
		expandOrCollapse(root, expand);
	}

	protected void expandOrCollapse(TreeItem<?> node, boolean expand) {
		node.setExpanded(expand);
		for (TreeItem<?> treeItem : node.getChildren()) {
			expandOrCollapse(treeItem, expand);
		}
	}

	public void expandfirstLevel() {
		root.setExpanded(true);
		for (TreeItem<?> secondLevelnode : root.getChildren()) {
			secondLevelnode.setExpanded(false);
		}
	}



	public void refreshTree(RefreshedDomain<D> refreshedDomain) {
		D domain = refreshedDomain.getDomain();
		switch (refreshedDomain.getCauseType()) {
		case all:
			fillTree(refreshedDomain.getList());
			break;
		case filtered:
			fillTree(refreshedDomain.getList());
			expandAll();
			break;
		case deleteItem:
			TreeItem<BaseNodeValue> node = findFirstLevelNode(domain);
			if (node != null) {
				inDeleteOperation = true;
				try {
					node.getParent().getChildren().remove(node);
				} finally {
					inDeleteOperation = false;
				}
			}
			break;
		case insertItem:
			fillTree(refreshedDomain.getList());
			selectItem(domain);
			break;
		case updateItem:
			refreshItem(domain);
			selectItem(domain);
			break;
		default:
			break;
		}
	}

	protected void fillTree(List<D> domainList) {
		root.getChildren().clear();
		for (D d : domainList) {
			TreeItem<BaseNodeValue> node = firstLevelNode(d);
			node.setExpanded(false);
			addSecondLevelChildren(d, node.getChildren());
			root.getChildren().add(node);
		}
	}

	protected void refreshItem(D d) {
		TreeItem<BaseNodeValue> treeItem = findFirstLevelNode(d);
		if (treeItem != null) {
			int index = root.getChildren().indexOf(treeItem);
			if (index >= 0) {
				TreeItem<BaseNodeValue> domainTreeItem = firstLevelNode(d);
				expandOrCollapse(domainTreeItem, true);
				addSecondLevelChildren(d, domainTreeItem.getChildren());
				root.getChildren().remove(index);
				root.getChildren().add(index, domainTreeItem);
			}
		}
	}


	public abstract boolean selectItem(D domain);

	protected abstract TreeItem<BaseNodeValue> findFirstLevelNode(D d);
	protected abstract TreeItem<BaseNodeValue> firstLevelNode(D domain);

	protected abstract void addSecondLevelChildren(D domain, ObservableList<TreeItem<BaseNodeValue>> children);

}
