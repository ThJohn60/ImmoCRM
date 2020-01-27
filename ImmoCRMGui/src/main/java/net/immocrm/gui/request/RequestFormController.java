package net.immocrm.gui.request;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import net.immocrm.domain.Person;
import net.immocrm.domain.Request;
import net.immocrm.gui.control.DateTimePicker;
import net.immocrm.gui.control.PriceField;
import net.immocrm.gui.select.PersonSelectionDialog;
import net.immocrm.gui.tools.EditItemController;

public class RequestFormController implements Initializable {

	@FXML DialogPane dialogPane;
	@FXML VBox dialogContentPane;
	@FXML TabPane tabPane;

	@FXML Label purchaserName;
	@FXML Label purchaserStreet;
	@FXML Label purchaserCity;
	@FXML Label customerPrice;
	@FXML PriceField requestPrice;
	@FXML DateTimePicker viewingAppointment;

	private Person purchaserInview;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}


	public void requestToForm(Request request) {
		viewingAppointment.setDateTime(request.getBesichtigungstermin());
		customerPrice.setText(request.getOrder().getCustomerPrice().toString());
		requestPrice.setText(request.getRequestPrice().toString());
		purchaserToForm(request.getPurchaser());
	}

	private void purchaserToForm(Person purchaser) {
		purchaserInview = purchaser;
		if (purchaser != null) {
			purchaserName.setText(purchaser.getName());
			purchaserStreet.setText(purchaser.getHomeAddress().getStreet());
			purchaserCity.setText(purchaser.getHomeAddress().getPostalCodeAndCity());
		}
	}

	public void requestFromForm(Request request) {
		request.setPurchaser(purchaserInview);
		request.setRequestPrice(requestPrice.getValidatedPrice());
		request.setBesichtigungstermin(viewingAppointment.getDateTime());
	}


	@FXML
	public void selectPurchaser() {
		PersonSelectionDialog dlg = new PersonSelectionDialog("Interessent");
		Person purchaser = dlg.showDialog();
		if (purchaser != null) {
			purchaserToForm(purchaser);
		}
	}

	@FXML
	public void editPurchaser() {
		if (!purchaserInview.isEmpty()) {
			Person purchaser = new EditItemController().editPerson(purchaserInview, true);
			if (purchaser != null) {
				purchaserToForm(purchaser);
			}
		}
	}

}
