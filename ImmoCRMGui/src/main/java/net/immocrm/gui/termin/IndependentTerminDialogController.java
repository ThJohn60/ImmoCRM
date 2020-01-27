package net.immocrm.gui.termin;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import net.immocrm.domain.IndependentTermin;
import net.immocrm.domain.termin.Termin;
import net.immocrm.domain.vc.ImmoDateTime;
import net.immocrm.gui.control.DateTimePicker;
import net.immocrm.gui.control.TerminCategoryComboBox;

public class IndependentTerminDialogController implements TerminDialogController, Initializable {

	@FXML DialogPane dialogPane;
	@FXML Label headerLabel;

	@FXML TextField title;
	@FXML TerminCategoryComboBox category;
	@FXML DateTimePicker startDate;
	@FXML DateTimePicker endDate;
	@FXML TextField loc;
	@FXML TextField participant;
	@FXML TextArea details;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		category.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				headerLabel.setText(newValue);
			}
		});
	}

	@Override
	public void terminToForm(Termin t) {
		IndependentTermin termin = (IndependentTermin)t;
		title.setText(termin.getTitle());
		startDate.setDateTime(termin.getDate());
		endDate.setDateTime(termin.getEndDate());
		endDate.setDateInvisible(true);
		category.setCategory(termin.getCategory());
		loc.setText(termin.getLocation());
		participant.setText(termin.getParticipant());
		details.setText(termin.getDetails());
	}

	@Override
	public void terminFromForm(Termin t) {
		IndependentTermin termin = (IndependentTermin)t;
		termin.setTitle(title.getText());
		termin.setDate(startDate.getDateTime());
		ImmoDateTime endDateTime = endDate.getDateTime();
		termin.setEndDate(endDateTime);
		termin.setCategory(category.getCategory());
		termin.setLocation(loc.getText());
		termin.setParticipant(participant.getText());
		termin.setDetails(details.getText());
	}

}
