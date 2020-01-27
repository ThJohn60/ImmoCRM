package net.immocrm.gui.person;

import static net.immocrm.gui.tree.TreeItemFactory.orderTreeItemPersonParent;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import net.immocrm.domain.Order;
import net.immocrm.domain.Person;
import net.immocrm.gui.IconProvider;
import net.immocrm.gui.tree.AbstractTreeController;
import net.immocrm.gui.tree.BaseNodeValue;
import net.immocrm.gui.tree.StdNodeValue;

public class PersonTreeController extends AbstractTreeController<Person> {

	public PersonTreeController(TreeView<BaseNodeValue> personTreeView, TreeItem<BaseNodeValue> root) {
		super(personTreeView, root);
	}


	@Override
	protected TreeItem<BaseNodeValue> firstLevelNode(Person person) {
		return new TreeItem<>(new PersonNodeValue(person, ""), IconProvider.personTreeIcon());
	}

	@Override
	protected void addSecondLevelChildren(Person person, ObservableList<TreeItem<BaseNodeValue>> children) {
		children.clear();
		for (Order order : person.getOrders()) {
			TreeItem<BaseNodeValue> ti = orderTreeItemPersonParent(order);
			children.add(ti);
		}
	}

	@Override
	public boolean selectItem(Person person) {
		for (TreeItem<BaseNodeValue> ti : root.getChildren()) {
			if (((StdNodeValue<?>)ti.getValue()).getPerson().equals(person)) {
				root.setExpanded(true);
				tree.getSelectionModel().select(ti);
				tree.getFocusModel().focus(tree.getSelectionModel().getSelectedIndex());
				return true;
			}
		}
		return false;
	}

	@Override
	protected TreeItem<BaseNodeValue> findFirstLevelNode(Person p) {
		for (TreeItem<BaseNodeValue> treeItem : root.getChildren()) {
			BaseNodeValue value = treeItem.getValue();
			if (value.getPerson().equals(p)) {
				return treeItem;
			}
		}
		return null;
	}

}
