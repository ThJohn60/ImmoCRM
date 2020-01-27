package net.immocrm.gui.order;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Notar;
import net.immocrm.domain.Order;
import net.immocrm.domain.OrderHistory;
import net.immocrm.domain.Person;
import net.immocrm.domain.Request;
import net.immocrm.domain.RequestReader;
import net.immocrm.domain.TextMaker;
import net.immocrm.gui.IconProvider;
import net.immocrm.gui.MainController;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.form.MerkmalInfoMapper;
import net.immocrm.gui.img.ImageViewController;
import net.immocrm.gui.property.ImmobilieDialog;
import net.immocrm.gui.refresh.RefreshType;
import net.immocrm.gui.request.RequestController;
import net.immocrm.gui.request.RequestRow;
import net.immocrm.gui.tools.DeleteItemController;
import net.immocrm.gui.tools.EditItemController;

public class OrderDetailController implements Initializable {

	@FXML Tab orderTab;
	@FXML ImageView detailViewIcon;
	@FXML ScrollPane scrollPane;

	@FXML Label headLine;

	@FXML Label street;
	@FXML Label city;
	@FXML Label immoType;
	@FXML Label wohnflaeche;
	@FXML Label roomcnt;

	@FXML Label orderState;
	@FXML Label ablage;
	@FXML Label field1;
	@FXML Label field2;

	@FXML Label customerLabel;
	@FXML Label ownerName;
	@FXML Label ownerStreet;
	@FXML Label ownerCity;
	@FXML Label buyerLabel;
	@FXML Label buyerName;
	@FXML Label buyerStreet;
	@FXML Label buyerCity;

	@FXML Label notarTermin;
	@FXML Label handoverTermin;
	@FXML Label billTermin;
	@FXML Label payedTermin;

	@FXML Label desiredPriceLabel;
	@FXML Label realPriceLabel;
	@FXML Label priceLabel;
	@FXML Label desiredPrice;
	@FXML Label realPrice;
	@FXML Label price;
	@FXML Label provision;

	@FXML Label notarLabel;
	@FXML Label notarName;
	@FXML Label notarCity;

	@FXML GridPane eigenschaftenBox;
	@FXML GridPane ausstatungBox;
	@FXML Label ausstattung;

	@FXML Pane imageArea;
	@FXML Label internalNoticeLabel;
	@FXML Label notice;
	@FXML Label objektbeschreibungLabel;
	@FXML Label objektbeschreibung;
	@FXML Label lagebeschreibungLabel;
	@FXML Label lagebeschreibung;

	@FXML TableView<RequestRow> requestTable;
	@FXML Hyperlink newOrderLink;

	private Order order;
	private ImageViewController imageCtrl;
	private OrderDialogHelper dialogHelper;
	@FXML TableView<OrderHistoryRow> historyTable;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setupVerticalScrolling();
		imageCtrl = new ImageViewController(imageArea);
	}

	private void setupVerticalScrolling() {
		scrollPane.setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent scrollEvent) {
				double deltaY = scrollEvent.getDeltaY() * 4; // *4 to make the scrolling a bit faster
				double height = scrollPane.getContent().getBoundsInLocal().getHeight();
				double vValue = scrollPane.getVvalue();
				scrollPane.setVvalue(vValue + -deltaY / height);
			}
		});
	}


	public void showOrder(Order o) {
		order = o;
		dialogHelper = o.isSaleOrder() ? new SaleOrderDialogHelper() : new RentOrderDialogHelper();

		detailViewIcon.setImage(IconProvider.orderIcon32dp(o));
		headLine.setText(TextMaker.INSTANCE.getDetailsHeader(o));

		orderState.setText(order.getOrderState().toString());
		ablage.setText(order.getAblage());
		field1.setText(order.getField1());
		field2.setText(order.getField2());

		fillImmobilieSections(o.getImmobilie());
		fillCustomerBuyerSection(order.getCustomer(), o.getBuyer());
		fillAbschlussSection(order.getNotar());
		fillRequestTable(o);
		fillHistoryTable(o);

		notarTermin.setText(order.getSettlementDateTime().toString());
		handoverTermin.setText(order.getHandoverDateTime().toString());
		billTermin.setText(order.getBillDate().toString());
		payedTermin.setText(order.getPayedDate().toString());
	}

	private void fillImmobilieSections(Immobilie immobilie) {
		immoType.setText(immobilie.getImmobilieTypeName());
		if (!immobilie.getWohnflaeche().isEmpty()) {
			wohnflaeche.setText(immobilie.getWohnflaeche().toString() + " Wohnfläche");
		}
		if (!immobilie.getRoomCnt().isEmpty()) {
			roomcnt.setText(immobilie.getRoomCnt().toString() + " Zimmer");
		}

		notice.setText(immobilie.getInternalNotice());
		objektbeschreibung.setText(immobilie.getObjektbeschreibung());
		lagebeschreibung.setText(immobilie.getLagebeschreibung());
		fillTextArea(internalNoticeLabel, notice, immobilie.getInternalNotice());
		fillTextArea(objektbeschreibungLabel, objektbeschreibung, immobilie.getObjektbeschreibung());
		fillTextArea(lagebeschreibungLabel, lagebeschreibung, immobilie.getLagebeschreibung());

		street.setText(immobilie.getAddress().getCompleteStreet());
		city.setText(immobilie.getAddress().getPostalCodeAndCity());
		showEigenschaften(immobilie);
		showAusstatung(immobilie);
		if (immobilie.getAusstattung() != null) {
			ausstattung.setText(immobilie.getAusstattung());
		}
		imageCtrl.showImages(immobilie.getImageDir());
	}

	private void fillTextArea(Label textAreaFieldLabel, Label textAreaField, String text) {
		if (text != null && !text.isEmpty()) {
			textAreaFieldLabel.setVisible(true);
			textAreaField.setText(text);
			textAreaField.setPadding(new Insets(0, 0, 20, 0));
		} else {
			textAreaFieldLabel.setVisible(true);
			textAreaField.setPadding(new Insets(0));
		}
	}


	private void fillAbschlussSection(Notar notar) {
		if (notar != null) {
			notarName.setText(notar.getName());
			notarCity.setText(notar.getCity());
		}
		notarLabel.setVisible(dialogHelper.isNotarBoxVisible());
		notarName.setVisible(dialogHelper.isNotarBoxVisible());
		notarCity.setVisible(dialogHelper.isNotarBoxVisible());

		provision.setText(dialogHelper.calcProvision(order));
		desiredPriceLabel.setText(dialogHelper.getDesiredPriceLabel());
		realPriceLabel.setText(dialogHelper.getRealPriceLabel());
		priceLabel.setText(dialogHelper.getPriceLabel());

		desiredPrice.setText(order.getCustomerPrice().toString());
		realPrice.setText(order.getEstimatedPrice().toString());
		price.setText(order.getSettlementPrice().toString());
	}


	private void fillCustomerBuyerSection(Person customer, Person buyer) {
		customerLabel.setText(dialogHelper.getCustomerLabel());
		ownerName.setText(customer.getName());
		ownerStreet.setText(customer.getHomeAddress().getStreet());
		ownerCity.setText(customer.getHomeAddress().getPostalCodeAndCity());

		buyerLabel.setText(dialogHelper.getBuyerLabel());
		buyerName.setText(buyer.getName());
		buyerStreet.setText(buyer.getHomeAddress().getStreet());
		buyerCity.setText(buyer.getHomeAddress().getPostalCodeAndCity());
	}


	public ScrollPane getScrollPane() {
		return scrollPane;
	}

	public void requestFocus() {
		scrollPane.requestFocus();
	}



	private void showEigenschaften(Immobilie im) {
		MerkmalInfoMapper merkmalMapper = new MerkmalInfoMapper(eigenschaftenBox);
		merkmalMapper.eigenschaftenToInfoForm(im);
	}

	private void showAusstatung(Immobilie im) {
		MerkmalInfoMapper merkmalMapper = new MerkmalInfoMapper(ausstatungBox);
		merkmalMapper.ausstattungToInfoForm(im);
	}


	@FXML
	public void editOrder() {
		Order changedOrder = new OrderDialog().showDialog(order);
		MainController.getRefreshDispatcher().refreshOrderViews(changedOrder, RefreshType.updateItem);
	}


	@FXML
	public void editImmobilie() {
		new EditItemController().editImmobilie(order.getImmobilie());
	}

	@FXML
	public void gotoImmobilien() {
		MainController.instance().showImmobileDetails(order.getImmobilie());
	}

	@FXML
	public void editEigenschaften() {
		Immobilie changedImmobilie = new ImmobilieDialog().showDialog(order.getImmobilie(), 1);
		MainController.getRefreshDispatcher().refreshImmobilieViews(changedImmobilie, RefreshType.updateItem);
	}

	@FXML
	public void editAustattung() {
		Immobilie changedImmobilie = new ImmobilieDialog().showDialog(order.getImmobilie(), 2);
		MainController.getRefreshDispatcher().refreshImmobilieViews(changedImmobilie, RefreshType.updateItem);
	}


	@FXML
	public void editNotice() {
		Immobilie changedImmobilie = new ImmobilieDialog().showDialog(order.getImmobilie(), 4);
		MainController.getRefreshDispatcher().refreshImmobilieViews(changedImmobilie, RefreshType.updateItem);
	}


	@FXML
	public void showOrderAddressInMap() {
		MainController.instance().openMapTab(order.getImmobilie().getAddress());
	}


	@FXML
	public void editCustomer() {
		EditItemController ctrl = new EditItemController();
		ctrl.editPerson(order.getCustomer(), true);
	}

	@FXML
	public void gotoCustomer() {
		MainController.instance().showPersonDetails(order.getCustomer());
	}

	@FXML
	public void showCustomerAddressInMap() {
		MainController.instance().openMapTab(order.getCustomer().getHomeAddress());
	}


	@FXML
	public void editBuyer() {
		if (!order.getBuyer().isEmpty()) {
			EditItemController ctrl = new EditItemController();
			ctrl.editPerson(order.getBuyer(), true);
		}
	}

	@FXML
	public void gotoPurchaser() {
		if (!order.getBuyer().isEmpty()) {
			MainController.instance().showPersonDetails(order.getBuyer());
		}
	}

	@FXML
	public void showPurchaserAddressInMap() {
		if (!order.getBuyer().getHomeAddress().isEmpty()) {
			MainController.instance().openMapTab(order.getBuyer().getHomeAddress());
		}
	}

	//////////////// request table

	@FXML
	public void requestTableKlicked(MouseEvent event) {
		if (event.getClickCount() == 2) {
			RequestRow selectedItem = requestTable.getSelectionModel().getSelectedItem();
			if (selectedItem != null) {
				RequestController requestCtrl = new RequestController();
				Request request = requestCtrl.showDialog(selectedItem.getDomain());
				if (request != null) {
					fillRequestTable(order);
				}
			}
		}
	}

	@FXML
	public void newRequest() {
		RequestController requestCtrl = new RequestController();
		Request newRequest = requestCtrl.newRequest(order, true);
		if (newRequest != null) {
			fillRequestTable(order);
		}
	}

	@FXML
	public void editRequest() {
		RequestRow row = requestTable.getSelectionModel().getSelectedItem();
		if (row != null) {
			RequestController requestCtrl = new RequestController();
			Request r = requestCtrl.showDialog(row.getDomain());
			if (r != null) {
				ObservableList<RequestRow> items = requestTable.getItems();
				items.remove(row);
				items.add(new RequestRow(r));
			}
		}
	}

	@FXML
	public void gotoRequestPerson() {
		RequestRow row = requestTable.getSelectionModel().getSelectedItem();
		if (row != null) {
			MainController.instance().showPersonDetails(row.getDomain().getPurchaser());
		}
	}

	@FXML
	public void deleteRequest() {
		RequestRow requestRow = requestTable.getSelectionModel().getSelectedItem();
		if (requestRow == null) {
			AlertProvider.alertMissingRemoveItem();
			return;
		}
		new DeleteItemController().deleteRequest(requestRow.getDomain());
	}

	private void fillRequestTable(Order o) {
		List<Request> requests = RequestReader.INSTANCE.fetchByOrderId(o.getId());
		ObservableList<RequestRow> tableContent = requestTable.getItems();
		tableContent.clear();
		for (Request r : requests) {
			tableContent.add(new RequestRow(r));
		}
		requestTable.setItems(tableContent);
		requestTable.getSelectionModel().clearSelection();
	}

	private void fillHistoryTable(Order o) {
		ObservableList<OrderHistoryRow> items = historyTable.getItems();
		items.clear();
		for (OrderHistory h : o.getHistory()) {
			items.add(new OrderHistoryRow(h));
		}
	}


	@FXML
	public void importImage() {
		imageCtrl.importImage();
	}

	@FXML
	public void deleteImage() {
		if (AlertProvider.confirm("Sind Sie sicher, dass Sie das Bild löschen wollen?")) {
			imageCtrl.deleteImage();
		}
	}

}
