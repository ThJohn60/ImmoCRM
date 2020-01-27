package net.immocrm.gui.order;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import net.immocrm.domain.DomainFactory;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Notar;
import net.immocrm.domain.Order;
import net.immocrm.domain.Person;
import net.immocrm.domain.Request;
import net.immocrm.domain.ref.OrderStateEnum;
import net.immocrm.domain.vc.DomainTool;
import net.immocrm.gui.MainController;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.control.DateTimePicker;
import net.immocrm.gui.control.ImmoDatePicker;
import net.immocrm.gui.control.OrderStateComboBox;
import net.immocrm.gui.notar.NotarDialog;
import net.immocrm.gui.property.ImmobilieDialog;
import net.immocrm.gui.refresh.RefreshType;
import net.immocrm.gui.select.NotarSelectionDialog;
import net.immocrm.gui.select.PersonSelectionDialog;
import net.immocrm.gui.select.RequestSelectionDialog;
import net.immocrm.gui.tools.EditItemController;

public class OrderFormController implements Initializable {

	@FXML VBox orderDetailContainer;

	@FXML Label customerLabel;
	@FXML Label buyerLabel;

	@FXML Label immoType;
	@FXML Label immoStreet;
	@FXML Label immoCity;
	@FXML Label wohnflaeche;
	@FXML Label roomcnt;

	@FXML OrderStateComboBox state;
	@FXML TextField aktenzeichen;
	@FXML TextField field1;
	@FXML TextField field2;
	@FXML Label settlementPrice;

	@FXML Label customerName;
	@FXML Label customerStreet;
	@FXML Label customerCity;

	@FXML VBox notarBox;
	@FXML Label notarName;
	@FXML Label notarStreet;
	@FXML Label notarCity;

	@FXML DateTimePicker notarDate;
	@FXML DateTimePicker handoverDate;
	@FXML ImmoDatePicker billDate;
	@FXML ImmoDatePicker payedDate;

	@FXML Label buyerName;
	@FXML Label buyerStreet;
	@FXML Label buyerCity;

	@FXML Button ownerChangeButton;

	private Notar notarInView;
	private Person buyerInView;
	private Order orderInView;

	private OrderDialogHelper dialogHelper;

	private FinanceFormController financeFormCtrl;



	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void setHelper(OrderDialogHelper dialogHelper) {
		this.dialogHelper = dialogHelper;
	}

	public void valuesToForm(Order order) {
		ownerChangeButton.setVisible(order.isSaleOrder());
		ownerChangeButton.setDisable(order.isFinished());

		customerLabel.setText(dialogHelper.getCustomerLabel());
		buyerLabel.setText(dialogHelper.getBuyerLabel());
		notarBox.setVisible(dialogHelper.isNotarBoxVisible());
		notarDate.setEnable(dialogHelper.isNotarBoxVisible());

		orderInView = order;
		state.setValue(order.getOrderState());
		aktenzeichen.setText(order.getAblage());
		field1.setText(order.getField1());
		field2.setText(order.getField2());

		immoToForm(order.getImmobilie());

		customerToForm(order.getCustomer());
		buyerToForm(order.getBuyer());
		notarToForm(order.getNotar());
		datesToForm(order);
	}

	public void setFinanceController(FinanceFormController ctrl) {
		this.financeFormCtrl = ctrl;
	}

	private void immoToForm(Immobilie immobilie) {
		immoType.setText(immobilie.getImmobilieTypeName());
		immoStreet.setText(immobilie.getAddress().getStreet());
		immoCity.setText(immobilie.getAddress().getPostalCodeAndCity());

		if (!immobilie.getWohnflaeche().isEmpty()) {
			wohnflaeche.setText(immobilie.getWohnflaeche().toString() + " Wohnfläche");
		}
		if (!immobilie.getRoomCnt().isEmpty()) {
			roomcnt.setText(immobilie.getRoomCnt().toString() + " Zimmer");
		}
	}

	private void datesToForm(Order order) {
		notarDate.setDateTime(order.getSettlementDateTime());
		settlementPrice.setText(order.getSettlementPrice().toString());
		handoverDate.setDateTime(order.getHandoverDateTime());
		billDate.setValue(order.getBillDate().toLocalDate());
		payedDate.setValue(order.getPayedDate().toLocalDate());
	}

	private void customerToForm(Person customer) {
		customerName.setText(customer.getName());
		customerStreet.setText(customer.getHomeAddress().getStreet());
		customerCity.setText(customer.getHomeAddress().getPostalCodeAndCity());
	}


	public void valuesFromForm(Order order) {
		order.setOrderState(getOrderState());
		order.setAblage(aktenzeichen.getText());
		order.setField1Id(field1.getText());
		order.setField2Id(field2.getText());
		order.setNotar(notarInView);
		order.setBuyer(buyerInView);
		order.setSettlementDateTime(notarDate.getDateTime());
		order.setHandoverDateTime(handoverDate.getDateTime());
		order.setBillDate(billDate.getImmoDate());
		order.setPayedDate(payedDate.getImmoDate());
	}


	public OrderStateEnum getOrderState() {
		return state.getValue();
	}


	@FXML
	public void editImmo() {
		Immobilie changedImmobilie = new ImmobilieDialog().showDialog(orderInView.getImmobilie(), 0);
		if (changedImmobilie != null) {
			valuesToForm(orderInView);
		}
	}


	@FXML
	public void editCustomer(ActionEvent event) {
		Person owner = new EditItemController().editPerson(orderInView.getCustomer(), true);
		if (owner != null) {
			customerToForm(owner);
		}
		event.consume();
	}

	@FXML
	public void selectCustomer(ActionEvent event) {
		Person owner = new PersonSelectionDialog("Eigentümer").showDialog();
		if (owner != null) {
			if (!owner.equals(orderInView.getImmobilie().getOwner())) {
				String customerRole = orderInView.isSaleOrder() ? "Verkäufer" : "Vermieter";
				boolean ok = AlertProvider.confirm("Der ausgewählte " + customerRole + " " + owner.getName()
				+ " stimmt nicht mit dem aktuellen Eigentümer "
				+ orderInView.getImmobilie().getOwner().getName() + " überein. Der Eigentümer wird ebenfalls geändert.\nWollen sie die Änderung übernehmen?");
				if (ok) {
					orderInView.getImmobilie().setOwner(owner);
					orderInView.setCustomer(owner);
					customerToForm(owner);
				}
			} else {
				orderInView.setCustomer(owner);
				customerToForm(owner);
			}
		}
		event.consume();
	}


	@FXML
	public void selectNotar(ActionEvent event) {
		Notar notar = new NotarSelectionDialog().showDialog();
		if (notar != null) {
			notarToForm(notar);
		}
		event.consume();
	}

	@FXML
	public void newNotar(ActionEvent event) {
		Notar notar = new NotarDialog().showDialog();
		if (notar != null) {
			notarToForm(notar);
		}
		event.consume();
	}

	@FXML
	public void removeNotar() {
		orderInView.setNotar(null);
		notarInView = orderInView.getNotar();
		notarToForm(notarInView);
	}

	private void notarToForm(Notar notar) {
		notarInView = notar;
		if (DomainTool.isEmpty(notar)) {
			notarName.setText("");
			notarStreet.setText("");
			notarCity.setText("");
		} else {
			notarName.setText(notar.getName());
			notarStreet.setText(notar.getStreet());
			notarCity.setText(notar.getPostalCode() + " " + notar.getCity());
		}
	}


	@FXML
	public void selectBuyer() {
		Person buyer = new PersonSelectionDialog(dialogHelper.getBuyerLabel()).showDialog();
		if (buyer != null) {
			buyerToForm(buyer);
		}
	}

	@FXML
	public void removeBuyer() {
		buyerToForm(DomainFactory.newPerson());
	}

	private void buyerToForm(Person buyer) {
		buyerInView = buyer;
		if (buyer != null) {
			buyerName.setText(buyer.getName());
			buyerStreet.setText(buyer.getHomeAddress().getStreet());
			buyerCity.setText(buyer.getHomeAddress().getPostalCodeAndCity());
		}
	}

	@FXML
	public void selectRequest(ActionEvent event) {
		Request request = new RequestSelectionDialog(orderInView).showDialog();
		if (request != null) {
			buyerToForm(request.getPurchaser());
			financeFormCtrl.setSettlementPrice(request.getRequestPrice());		// TODO?
		}
		event.consume();
	}

	@FXML
	public void ownerChange() {
		valuesFromForm(orderInView);
		Order order = new OrderSettlementDialog().showDialog(orderInView);
		if (order != null) {
			valuesToForm(order);
			financeFormCtrl.valuesToForm(order);
			orderInView = order;
			MainController.getRefreshDispatcher().refreshOrderViews(order, RefreshType.updateItem);
		}
	}

}