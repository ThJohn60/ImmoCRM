package net.immocrm.gui.order;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import net.immocrm.domain.Notar;
import net.immocrm.domain.Order;
import net.immocrm.domain.Person;
import net.immocrm.domain.Request;
import net.immocrm.domain.vc.DomainTool;
import net.immocrm.gui.control.DateTimePicker;
import net.immocrm.gui.control.PriceField;
import net.immocrm.gui.notar.NotarDialog;
import net.immocrm.gui.person.PersonDialog;
import net.immocrm.gui.select.NotarSelectionDialog;
import net.immocrm.gui.select.PersonSelectionDialog;
import net.immocrm.gui.select.RequestSelectionDialog;

public class OrderSettlementController implements Initializable {

	@FXML Label ostCustomerPrice;
	@FXML Label ostEstimatedPrice;
	@FXML PriceField ostSettlementPrice;
	@FXML DateTimePicker notarAppointment;

	@FXML Label notarName;
	@FXML Label notarStreet;
	@FXML Label notarCity;

	@FXML Label purchaserName;
	@FXML Label purchaserStreet;
	@FXML Label purchaserCity;

	@FXML Label ownerName;
	@FXML Label ownerStreet;
	@FXML Label ownerCity;

	private Person purchaserInview;
	private Notar notarInview;
	private Order orderInView;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void orderToForm(Order order) {
		this.orderInView = order;
		purchaserToForm(order.getBuyer());
		ownerToForm(order.getCustomer());
		notarToForm(order.getNotar());
		ostCustomerPrice.setText(order.getCustomerPrice().toString());
		ostEstimatedPrice.setText(order.getEstimatedPrice().toString());
		ostSettlementPrice.setPrice(order.getSettlementPrice());
		notarAppointment.setDateTime(order.getSettlementDateTime());
	}

	private void ownerToForm(Person owner) {
		if (owner != null) {
			ownerName.setText(owner.getName());
			ownerStreet.setText(owner.getHomeAddress().getStreet());
			ownerCity.setText(owner.getHomeAddress().getPostalCodeAndCity());
		}
	}

	private void purchaserToForm(Person purchaser) {
		purchaserInview = purchaser;
		if (purchaser != null) {
			purchaserName.setText(purchaser.getName());
			purchaserStreet.setText(purchaser.getHomeAddress().getStreet());
			purchaserCity.setText(purchaser.getHomeAddress().getPostalCodeAndCity());
		}
	}

	private void notarToForm(Notar notar) {
		notarInview = notar;
		if (!DomainTool.isEmpty(notar)) {
			notarName.setText(notar.getName());
			notarStreet.setText(notar.getStreet());
			notarCity.setText(notar.getPostalCode() + " " + notar.getCity());
		}
	}


	public void orderFromForm(Order order) {
		order.setBuyer(purchaserInview);
		order.setNotar(notarInview);
		order.setSettlementPrice(ostSettlementPrice.getValidatedPrice());
		order.setSettlementDateTime(notarAppointment.getDateTime());
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
	public void selectPurchaser(ActionEvent event) {
		Person person = new PersonSelectionDialog("Käufer").showDialog();
		if (person != null) {
			purchaserToForm(person);
		}
		event.consume();
	}

	@FXML
	public void newPurchaser(ActionEvent event) {
		Person purchaser = new PersonDialog().showDialog();
		if (purchaser != null) {
			purchaserToForm(purchaser);
		}
		event.consume();
	}

	@FXML
	public void selectRequest(ActionEvent event) {
		Request request = new RequestSelectionDialog(orderInView).showDialog();
		if (request != null) {
			purchaserToForm(request.getPurchaser());
		}
		event.consume();
	}

}
