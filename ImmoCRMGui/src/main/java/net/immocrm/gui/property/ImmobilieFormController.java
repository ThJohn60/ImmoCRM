package net.immocrm.gui.property;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import net.immocrm.domain.Address;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.MerkmalTypeManager;
import net.immocrm.domain.Person;
import net.immocrm.domain.ref.Category;
import net.immocrm.domain.ref.ImmobilieCategoryEnum;
import net.immocrm.gui.control.AreaField;
import net.immocrm.gui.control.CountryComboBox;
import net.immocrm.gui.control.MerkmalTypeComboBox;
import net.immocrm.gui.control.NumberField;
import net.immocrm.gui.form.AddressFormMapper;
import net.immocrm.gui.person.PersonDialog;
import net.immocrm.gui.select.AddressSelectionDialog;
import net.immocrm.gui.select.PersonSelectionDialog;
import net.immocrm.gui.tools.EditItemController;

public class ImmobilieFormController implements Initializable {

	@FXML MerkmalTypeComboBox propertyType;
	@FXML NumberField roomCnt;
	@FXML AreaField wohnflaeche;
	@FXML TextField wohneinheit;
	@FXML TextArea lagebeschreibung;

	@FXML Label customerName;
	@FXML Label customerStreet;
	@FXML Label customerCity;

	@FXML TextField street;
	@FXML TextField streetb;
	@FXML TextField postalCode;
	@FXML TextField city;

	@FXML FlowPane ownerHyperlinkPane;
	@FXML Hyperlink lnkEditCustomer;
	@FXML Hyperlink lnkSelectCustomer;
	@FXML CountryComboBox countryBox;

	private AddressFormMapper addrMapper;
	private Person personInView;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addrMapper = new AddressFormMapper(street, streetb, postalCode, city, countryBox);
	}

	public void removeOwnerHyperLinks() {
		ownerHyperlinkPane.getChildren().remove(lnkEditCustomer);
		ownerHyperlinkPane.getChildren().remove(lnkSelectCustomer);
	}

	public void valuesFromForm(Immobilie immobilie) {
		immobilie.setOwner(personInView);
		addrMapper.addressFromForm(immobilie.getAddress());

		immobilie.setImmobilieType(propertyType.getValue());
		immobilie.setRoomCnt(roomCnt.getValidatedNumber());
		immobilie.setWohnflaeche(wohnflaeche.getValidatedArea());
		immobilie.setWohneinheit(wohneinheit.getText());

		immobilie.setLagebeschreibung(lagebeschreibung.getText());
	}


	public void valuesToForm(Immobilie immobilie) {
		ownerToForm(immobilie.getOwner());
		addrMapper.addressToForm(immobilie.getAddress());

		buildFields(immobilie.getImmobilieCategory());
		propertyType.setValue(immobilie.getImmobilieType());

		roomCnt.setText(immobilie.getRoomCnt().toString());
		wohnflaeche.setArea(immobilie.getWohnflaeche());
		wohneinheit.setText(immobilie.getWohneinheit());
		lagebeschreibung.setText(immobilie.getLagebeschreibung());
	}

	private void buildFields(ImmobilieCategoryEnum immoCategory) {
		MerkmalTypeManager mtm = new MerkmalTypeManager();
		propertyType.fill(mtm.getTypeOfCategory(Category.Immobilentyp, immoCategory));
	}


	@FXML
	public void newCustomer(ActionEvent event) {
		Person owner = new PersonDialog().showDialog();
		if (owner != null) {
			ownerToForm(owner);
		}
		event.consume();
	}

	@FXML
	public void editCustomer(ActionEvent event) {
		new EditItemController().editPerson(personInView, true);
		event.consume();
	}

	@FXML
	public void selectCustomer(ActionEvent event) {
		Person owner = new PersonSelectionDialog("Eigentümer").showDialog();
		if (owner != null) {
			ownerToForm(owner);
		}
		event.consume();
	}

	private void ownerToForm(Person owner) {
		personInView = owner;
		customerName.setText(owner.getName());
		customerStreet.setText(owner.getHomeAddress().getStreet());
		customerCity.setText(owner.getHomeAddress().getPostalCodeAndCity());
	}

	@FXML
	public void selectAddress(ActionEvent event) {
		Address address = new AddressSelectionDialog().showDialog();
		if (address != null) {
			addrMapper.addressToForm(address);
		}
		event.consume();
	}

}
