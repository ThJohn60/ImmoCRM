package net.immocrm.gui.person;

import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import net.immocrm.domain.Address;
import net.immocrm.domain.Contact;
import net.immocrm.domain.ImmoTransaction;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Order;
import net.immocrm.domain.Person;
import net.immocrm.domain.Request;
import net.immocrm.domain.RequestReader;
import net.immocrm.domain.TextMaker;
import net.immocrm.gui.BaseDialogBuilder;
import net.immocrm.gui.IconProvider;
import net.immocrm.gui.MainController;
import net.immocrm.gui.create.NewImmobilieManager;
import net.immocrm.gui.order.OrderRow;
import net.immocrm.gui.order.OrderTableInDetailsMenuController;
import net.immocrm.gui.property.ImmoActionRow;
import net.immocrm.gui.property.ImmobilieActionRow;
import net.immocrm.gui.property.ImmobilieTableInDetailsMenuController;
import net.immocrm.gui.property.RequestImmoActionRow;
import net.immocrm.gui.property.TransactionRow;
import net.immocrm.gui.refresh.RefreshType;
import net.immocrm.gui.tools.MailSendController;

public class PersonDetailController implements Initializable {

	@FXML ImageView detailViewIcon;
	@FXML Label headLine;

	@FXML ScrollPane scrollPane;
	@FXML VBox vBox;

	@FXML Label anrede;
	@FXML Label vorname;
	@FXML Label nachname;
	@FXML Label birthday;
	@FXML Label mobile;
	@FXML Label telefon;
	@FXML Label fax;
	@FXML Label email;

	@FXML Label city;
	@FXML Label postalCode;
	@FXML Label streetb;
	@FXML Label street;
	@FXML Label notice;

	@FXML TableView<ImmoActionRow> immoTable;
	@FXML FlowPane immoTableHyperLinks;
	@FXML TableView<OrderRow> orderTable;
	@FXML FlowPane orderTableHyperLinks;

	private OrderTableInDetailsMenuController orderMenuCtrl;
	private OrderTableInDetailsMenuController orderHyperlinkCtrl;
	private ImmobilieTableInDetailsMenuController immoMenuCtrl;
	private ImmobilieTableInDetailsMenuController immoHyperlinkCtrl;

	private Person person;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addContextMenuToOrderTable();
		addHyperlinksToOrderTable();
		addContextMenuToImmoTable();
		addHyperlinksToImmoTable();
	}

	private void addContextMenuToOrderTable() {
		FXMLLoader fxmlLoader = BaseDialogBuilder.getFxmlLoader("order/OrderTableInDetailsMenu.fxml");
		ContextMenu contextMenu = BaseDialogBuilder.load(fxmlLoader);
		orderMenuCtrl = fxmlLoader.getController();
		orderTable.setContextMenu(contextMenu);
		orderMenuCtrl.setOrderTable(orderTable);
	}

	private void addHyperlinksToOrderTable() {
		FXMLLoader fxmlLoader = BaseDialogBuilder.getFxmlLoader("order/OrderTableInDetailsHyperlinks.fxml");
		Pane hyperlinkPane = BaseDialogBuilder.loadPane(fxmlLoader);
		orderHyperlinkCtrl = fxmlLoader.getController();
		orderTableHyperLinks.getChildren().add(hyperlinkPane);
		orderHyperlinkCtrl.setOrderTable(orderTable);
	}

	private void addContextMenuToImmoTable() {
		FXMLLoader fxmlLoader = BaseDialogBuilder.getFxmlLoader("property/ImmoTableInDetailsMenu.fxml");
		ContextMenu contextMenu = BaseDialogBuilder.load(fxmlLoader);
		immoMenuCtrl = fxmlLoader.getController();
		immoTable.setContextMenu(contextMenu);
		immoMenuCtrl.setImmobilieTable(immoTable);
	}

	private void addHyperlinksToImmoTable() {
		FXMLLoader fxmlLoader = BaseDialogBuilder.getFxmlLoader("property/ImmoTableInDetailsHyperlinks.fxml");
		Pane hyperlinkPane = BaseDialogBuilder.loadPane(fxmlLoader);
		immoHyperlinkCtrl = fxmlLoader.getController();
		immoTableHyperLinks.getChildren().add(hyperlinkPane);
		immoHyperlinkCtrl.setImmobilieTable(immoTable);
	}


	public ScrollPane getScrollPane() {
		return scrollPane;
	}

	public void requestFocus() {
		scrollPane.requestFocus();
	}


	public void showPerson(Person p) {
		person = p;
		detailViewIcon.setImage(IconProvider.personIcon32dp(person));
		headLine.setText(TextMaker.INSTANCE.getDetailsHeader(person));

		orderMenuCtrl.setPerson(person);
		orderHyperlinkCtrl.setPerson(person);
		immoMenuCtrl.setPerson(person);
		immoHyperlinkCtrl.setPerson(person);

		anrede.setText(getAnredeAndTitle(person));
		vorname.setText(person.getVornamen());
		nachname.setText(person.getLastName());
		birthday.setText(person.getBirthday().toString());

		setAddress(person.getHomeAddress());
		setContact(person.getHomeContact());

		notice.setText(person.getNotice());

		fillImmoTable();
		fillOrderTable(person.getOrders());
//		newImmoLink.setTooltip(new Tooltip("Ein neues Objekt für " + p.getName() + " eingeben"));
	}

	private void setAddress(Address a) {
		street.setText(a.getStreet());
		streetb.setText(a.getStreet2());
		postalCode.setText(a.getPostalCode());
		city.setText(a.getCity());
	}

	private void setContact(Contact c) {
		mobile.setText(c.getCellNumber());
		telefon.setText(c.getTelNumber());
		fax.setText(c.getFaxNumber());
		email.setText(c.getEmailAddress());
	}

	private void fillImmoTable() {
		immoTable.getColumns().get(0).setText(person.getName());
		immoTable.getItems().clear();
		immoTable.getItems().addAll(getRows());
		immoTable.getSelectionModel().clearSelection();
	}

	private Collection<ImmoActionRow> getRows() {
		Set<ImmoActionRow> rows = new HashSet<>();
		for (Immobilie immo : person.getImmobilien()) {
			rows.add(new ImmobilieActionRow(immo));
		}
		for (ImmoTransaction immo : person.getImmoTransaction()) {
			rows.add(new TransactionRow(immo));
		}
		for (Request r : RequestReader.INSTANCE.fetchByPurchaserId(person.getId())) {
			rows.add(new RequestImmoActionRow(r));
		}
		return rows;
	}

	@FXML
	public void immoTableKlicked(MouseEvent event) {
		if (event.getClickCount() == 2) {
			ImmoActionRow selectedItem = immoTable.getSelectionModel().getSelectedItem();
			if  (selectedItem != null) {
				MainController.instance().showImmobileDetails(selectedItem.getImmobilie());
			}
		}
	}

	private void fillOrderTable(List<Order> orders) {
		ObservableList<OrderRow> tableContent = orderTable.getItems();
		tableContent.clear();
		for (Order order : orders) {
			tableContent.add(new OrderRow(order));
		}
		orderTable.getSelectionModel().clearSelection();
	}

	@FXML
	public void newImmobilie() {
		NewImmobilieManager dlgMan = new NewImmobilieManager();
		dlgMan.setCustomer(person);
		Immobilie immobilie = dlgMan.newImmobilieAssistent();
		if (immobilie != null) {
			person.getImmobilien().add(immobilie);
			fillImmoTable();
			MainController.getRefreshDispatcher().refreshImmobilieViews(immobilie, RefreshType.insertItem);
		}
	}


	@FXML
	public void orderTableKlicked(MouseEvent event) {
		if (event.getClickCount() == 2) {
			OrderRow selectedItem = orderTable.getSelectionModel().getSelectedItem();
			if  (selectedItem != null) {
				MainController.instance().showOrderDetails(selectedItem.getDomain());
			}
		}
	}

	private String getAnredeAndTitle(Person p) {
		StringBuffer result = new StringBuffer();
		if (p.getAnrede() != null) {
			result.append(p.getAnrede()).append(" ");
		}
		if (p.getTitle() != null) {
			result.append(p.getTitle());
		}
		return result.toString();
	}


	@FXML
	public void editAddress() {
		Person changedPerson = new PersonDialog().showDialog(person);
		if (changedPerson != null) {
			MainController.getRefreshDispatcher().refreshPersonViews(changedPerson, RefreshType.updateItem);
		}
	}


	@FXML
	public void editNotice() {
		Person changedPerson = new PersonDialog().showDialog(person, 1, false);
		if (changedPerson != null) {
			person = changedPerson;
			notice.setText(changedPerson.getNotice());
			MainController.getRefreshDispatcher().refreshPersonViews(changedPerson, RefreshType.updateItem);
		}
	}


	@FXML
	public void sendMail() {
		MailSendController.INSTANCE.sendMail(person);
	}

	@FXML
	public void showInMap() {
		MainController.instance().openMapTab(person.getHomeAddress());
	}

}
