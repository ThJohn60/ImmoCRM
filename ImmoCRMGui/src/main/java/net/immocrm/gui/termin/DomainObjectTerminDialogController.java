package net.immocrm.gui.termin;

import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import net.immocrm.domain.Address;
import net.immocrm.domain.termin.Termin;
import net.immocrm.gui.control.DateTimePicker;

public class DomainObjectTerminDialogController implements TerminDialogController {

	@FXML DialogPane dialogPane;
	@FXML StackPane dialogContentPane;

	@FXML DateTimePicker dateField;
	@FXML Label streetLbl;
	@FXML Label cityLbl;
	@FXML Label participantLbl;
	@FXML Label detailsLbl;



	@Override
	public void terminToForm(Termin termin) {
		dateField.setDateTime(termin.getDate());
		Address address = termin.getAddress();
		if (address != null) {
			streetLbl.setText(address.getStreet());
			cityLbl.setText(address.getCity());
		}
		participantLbl.setText(termin.getParticipantsCommaSeperated());
//		participantLbl.setWrapText(true);
		detailsLbl.setText(termin.getDetailsWithLineBreaks());
	}

	@Override
	public void terminFromForm(Termin termin) {
		termin.setDate(dateField.getDateTime());
	}

}
