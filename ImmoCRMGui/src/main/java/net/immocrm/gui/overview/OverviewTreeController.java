package net.immocrm.gui.overview;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import net.immocrm.gui.MainController;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.order.OrderTreeController;
import net.immocrm.gui.person.PersonTreeController;
import net.immocrm.gui.property.AddImagesController;
import net.immocrm.gui.property.ImmobilieTreeController;
import net.immocrm.gui.refresh.RefreshedImmobilien;
import net.immocrm.gui.refresh.RefreshedOrders;
import net.immocrm.gui.refresh.RefreshedPersons;
import net.immocrm.gui.request.RequestController;
import net.immocrm.gui.tools.ClipboardController;
import net.immocrm.gui.tools.DeleteItemController;
import net.immocrm.gui.tools.EditItemController;
import net.immocrm.gui.tools.MailSendController;
import net.immocrm.gui.tree.BaseNodeValue;
import net.immocrm.gui.tree.StringNodeValue;

public class OverviewTreeController implements Initializable {

	public static final String PERSONS_ROOT = "Personen";
	public static final String ORDERS_ROOT = "Aufträge nach Jahrgängen";
	public static final String IMMOBILIEN_ROOT = "Immobilien";

	@FXML TreeView<BaseNodeValue> treeView;
	@FXML ContextMenu treeContextMenu;

	private OrderTreeController orderTreeCtrl;
	private ImmobilieTreeController immobilieTreeCtrl;
	private PersonTreeController personTreeCtrl;

	private TreeContextMenuManager contextMenuMan;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buildTree();
		MainController.getRefreshDispatcher().setOverviewTreeCtrl(this);
		contextMenuMan = new TreeContextMenuManager(treeContextMenu);
	}


	private void buildTree() {
		TreeItem<BaseNodeValue> root = rootTreeItem("invisibleRoot");
		treeView.setRoot(root);
		treeView.setShowRoot(false);
		TreeItem<BaseNodeValue> orderRootTreeItem = rootTreeItem(ORDERS_ROOT);
		TreeItem<BaseNodeValue> immobilieRootTreeItem = rootTreeItem(IMMOBILIEN_ROOT);
		TreeItem<BaseNodeValue> personRootTreeItem = rootTreeItem(PERSONS_ROOT);
		root.getChildren().add(immobilieRootTreeItem);
		root.getChildren().add(personRootTreeItem);
		root.getChildren().add(orderRootTreeItem);

		orderTreeCtrl = new OrderTreeController(treeView, orderRootTreeItem);
		immobilieTreeCtrl = new ImmobilieTreeController(treeView, immobilieRootTreeItem);
		personTreeCtrl = new PersonTreeController(treeView, personRootTreeItem);
	}

	private TreeItem<BaseNodeValue> rootTreeItem(String rootNodeName) {
		return new TreeItem<>(new StringNodeValue(rootNodeName));
	}

	@FXML
	public void collapseAll() {
		personTreeCtrl.collapseAll();
		immobilieTreeCtrl.collapseAll();
		orderTreeCtrl.collapseAll();
	}

	@FXML
	public void expandAll() {
		personTreeCtrl.expandAll();
		immobilieTreeCtrl.expandAll();
		orderTreeCtrl.expandAll();
	}

	private BaseNodeValue getSelectedItem() {
		BaseNodeValue result = orderTreeCtrl.getSelectedItem();
		return result;
	}


	public void refreshInitial() {
		refreshAll();
		immobilieTreeCtrl.expandfirstLevel();
		orderTreeCtrl.collapseAll();
		personTreeCtrl.collapseAll();
//	too much:
//		treeView.getSelectionModel().selectedItemProperty().addListener(
//				(observable, oldValue, newValue) -> selectionPerformed(newValue));
	}

	public void refreshAll() {
		refresh(MainController.getRefreshDispatcher().getRefreshedPersons());
		refresh(MainController.getRefreshDispatcher().getRefreshedOrders());
		refresh(MainController.getRefreshDispatcher().getRefreshedImmobilien());
	}

	public void refresh(RefreshedPersons refreshInfo) {
		personTreeCtrl.refreshTree(refreshInfo);
		if (refreshInfo.isFiltered()) {
			personTreeCtrl.expandfirstLevel();
		}
	}

	public void refresh(RefreshedImmobilien refreshInfo) {
		immobilieTreeCtrl.refreshTree(refreshInfo);
		if (refreshInfo.isFiltered()) {
			immobilieTreeCtrl.expandfirstLevel();
		}
	}

	public void refresh(RefreshedOrders refreshInfo) {
		if (refreshInfo.isDelete() || refreshInfo.isNewObject()) {
			refreshAll();
		} else {
			orderTreeCtrl.fillTree(refreshInfo);
			if (refreshInfo.isFiltered()) {
				orderTreeCtrl.expandAll();
			}
		}
	}


	@SuppressWarnings("unused")
	private void selectionPerformed(TreeItem<BaseNodeValue> treeItem) {
		if (treeItem == null) {
			return;
		}
		if (immobilieTreeCtrl.isInDeleteOperation()
				|| personTreeCtrl.isInDeleteOperation()
				|| orderTreeCtrl.isInDeleteOperation()) {
			return;
		}
		if (treeItem.getValue().isPersonNode()) {
			MainController.instance().showPersonDetails(treeItem.getValue().getPerson());
		} else if (treeItem.getValue().isImmobilieNode()) {
			MainController.instance().showImmobileDetails(treeItem.getValue().getImmobilie());
		} else if (treeItem.getValue().isOrderNode()) {
			MainController.instance().showOrderDetails(treeItem.getValue().getOrder());
		}
	}


	@FXML
	public void onShowingContextMenu() {
		contextMenuMan.onShowingContextMenu(getSelectedItem());
	}

	@FXML
	public void mouseClickOnTreeItem(MouseEvent event) {
		if (event.getClickCount() == 2) {
			gotoItem();
			event.consume();
		}
	}

	@FXML
	public void keyPressedOnTableItem(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			BaseNodeValue selectedItem = getSelectedItem();
			if (selectedItem != null) {
				editItem();
				orderTreeCtrl.deselect();
				event.consume();
				if (selectedItem.isOrderNode()) {
					orderTreeCtrl.selectItem(selectedItem.getOrder());
				} else if (selectedItem.isPersonNode()) {
					personTreeCtrl.selectItem(selectedItem.getPerson());
				} else if (selectedItem.isImmobilieNode()) {
					immobilieTreeCtrl.selectItem(selectedItem.getImmobilie());
				}
			}
		}
	}


	@FXML
	public void gotoItem() {
		BaseNodeValue treeNode = getSelectedItem();
		if (treeNode != null) {
			if (treeNode.isImmobilieNode()) {
				MainController.instance().showImmobileDetails(treeNode.getImmobilie());
			} else if (treeNode.isOrderNode()) {
				MainController.instance().showOrderDetails(treeNode.getOrder());
			} else if (treeNode.isPersonNode()) {
				MainController.instance().showPersonDetails(treeNode.getPerson());
			}
		}
	}

	@FXML
	public boolean editItem() {
		BaseNodeValue treeNode = getSelectedItem();
		if (treeNode != null) {
			EditItemController editItemCtrl = new EditItemController();
			if (treeNode.isImmobilieNode()) {
				return editItemCtrl.editImmobilie(treeNode.getImmobilie());
			}
			if (treeNode.isOrderNode()) {
				return editItemCtrl.editOrder(treeNode.getOrder());
			}
			if (treeNode.isPersonNode()) {
				return editItemCtrl.editPerson(treeNode.getPerson(), false) != null;
			}
		}
		return false;
	}


	@FXML
	public void newRequest() {
		BaseNodeValue treeNode = getSelectedItem();
		if (treeNode != null) {
			if (treeNode.isOrderNode()) {
				RequestController ctrl = new RequestController();
				ctrl.newRequest(getSelectedItem().getOrder(), true);
			}
		}
	}

	@FXML
	public void addImages() {
		AddImagesController addImagesCtrl = new AddImagesController();
		BaseNodeValue treeNode = getSelectedItem();
		if (treeNode != null) {
			addImagesCtrl.addImages(treeNode.getImmobilie());
		}
	}

	@FXML
	public void copyToClipboard() {
		BaseNodeValue treeNode = getSelectedItem();
		if (treeNode != null) {
			if (treeNode.isImmobilieNode()) {
				ClipboardController.INSTANCE.copyToClipboard(treeNode.getImmobilie());
			} else if (treeNode.isOrderNode()) {
				ClipboardController.INSTANCE.copyToClipboard(treeNode.getOrder());
			} else if (treeNode.isPersonNode()) {
				ClipboardController.INSTANCE.copyAddressToClipboard(treeNode.getPerson());
			}
		}
	}


	@FXML
	public void copyAddressToClipboard() {
		BaseNodeValue treeNode = getSelectedItem();
		if (treeNode != null) {
			if (treeNode.isImmobilieNode()) {
				ClipboardController.INSTANCE.copyAddressToClipboard(treeNode.getImmobilie().getOwner());
			} else if (treeNode.isOrderNode()) {
				ClipboardController.INSTANCE.copyAddressToClipboard(treeNode.getOrder().getCustomer());
			} else if (treeNode.isPersonNode()) {
				ClipboardController.INSTANCE.copyAddressToClipboard(treeNode.getPerson());
			}
		}
	}

	@FXML
	public void showInMap() {
		BaseNodeValue treeNode = getSelectedItem();
		if (treeNode != null) {
			if (treeNode.isImmobilieNode()) {
				MainController.instance().openMapTab(treeNode.getImmobilie().getAddress());
			} else if (treeNode.isOrderNode()) {
				MainController.instance().openMapTab(treeNode.getOrder().getImmobilieAddress());
			} else if (treeNode.isPersonNode()) {
				MainController.instance().openMapTab(treeNode.getPerson().getHomeAddress());
			}
		}
	}

	@FXML
	public void mailToPerson() {
		BaseNodeValue treeNode = getSelectedItem();
		if (treeNode != null) {
			if (treeNode.isImmobilieNode()) {
				MailSendController.INSTANCE.sendMail(treeNode.getImmobilie().getOwner());
			} else if (treeNode.isOrderNode()) {
				MailSendController.INSTANCE.sendMail(treeNode.getOrder().getCustomer());
			} else if (treeNode.isPersonNode()) {
				MailSendController.INSTANCE.sendMail(treeNode.getPerson());
			}
		}
	}

	@FXML
	public void deleteItem() {
		BaseNodeValue selected = getSelectedItem();
		if (selected != null) {
			DeleteItemController ctrl = new DeleteItemController();
			if (selected.isOrderNode()) {
				ctrl.deleteOrder(selected.getOrder());
			} else if (selected.isPersonNode()) {
				ctrl.deletePerson(selected.getPerson());
			} else if (selected.isImmobilieNode()) {
				ctrl.deleteImmobilie(selected.getImmobilie());
			}
		} else {
			AlertProvider.alertMissingRemoveItem();
		}
	}

}
