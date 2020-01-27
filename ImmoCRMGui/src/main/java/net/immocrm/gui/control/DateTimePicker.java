package net.immocrm.gui.control;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.StringTokenizer;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import net.immocrm.domain.vc.ImmoDateTime;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.form.NoActionException;

public class DateTimePicker extends FlowPane {

	private final DatePicker datePicker;
	private final TextField timeField;
	private String fieldName;


	public DateTimePicker() {
		datePicker = new DatePicker();
		datePicker.setPrefWidth(120.0);
		getChildren().add(datePicker);

		Region region = new Region();
		region.setPrefWidth(10);
		getChildren().add(region);

		timeField = new TextField();
		timeField.setPromptText("hh:mm");
		timeField.setPrefWidth(60.0);
		getChildren().add(timeField);
	}

	public ImmoDateTime getDateTime() {
		LocalDate date = datePicker.getValue();
		if (date == null) {
			return new ImmoDateTime();
		}
		LocalTime time = getTime();
		return new ImmoDateTime(LocalDateTime.of(date, time));
	}

	private LocalTime getTime() {
		String timeAsText = timeField.getText();
		if (timeAsText.isEmpty()) {
			return LocalTime.MIDNIGHT;
		}
		StringTokenizer tokenizer = new StringTokenizer(timeAsText, ":");

		LocalTime time;
		try {
			time = null;
			if (tokenizer.countTokens() == 1) {
				time = LocalTime.of(Integer.parseInt(tokenizer.nextToken()), 0);
			} else if (tokenizer.countTokens() == 2) {
				time = LocalTime.of(Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()));
			}
		} catch (Exception e) {
			timeField.requestFocus();
			AlertProvider.alertInputError("Bitte eine korrekte Uhrzeit (hh:mm)\nin das Uhrzeitfeld eingeben.");
			throw new NoActionException();
		}
		return time;
	}

	public void setDateTime(ImmoDateTime idt) {
		if (idt != null  &&  !idt.isEmpty()) {
			LocalDateTime dt = idt.toLocalDateTime();
			datePicker.setValue(dt.toLocalDate());
			LocalTime time = dt.toLocalTime();
			if (!time.equals(LocalTime.MIDNIGHT)) {
				timeField.setText(String.format("%02d:%02d", time.getHour(), time.getMinute()));
			}
		}
	}

	public void setEnable(boolean v) {
		datePicker.setDisable(!v);
		timeField.setDisable(!v);
	}

	public void setDateInvisible(boolean v) {
		datePicker.setVisible(!v);
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}
