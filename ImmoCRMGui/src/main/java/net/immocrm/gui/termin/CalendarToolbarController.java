package net.immocrm.gui.termin;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import net.immocrm.gui.MainController;
import net.immocrm.gui.control.MonthComboBox;
import net.immocrm.gui.control.YearField;

public class CalendarToolbarController implements Initializable {

	@FXML Button todayButton;
	@FXML Hyperlink monthForeward;
	@FXML MonthComboBox monthBox;
	@FXML Hyperlink monthBackward;
	@FXML YearField yearField;

	private TerminRefreshable refreshable;
	private TerminActions terminActions;


	public void setRefreshable(TerminRefreshable refreshable) {
		this.refreshable = refreshable;
		terminActions = new TerminActions(refreshable);
	}

	public LocalDate getMonthDate() {
		return LocalDate.of(yearField.getYearNumber(), monthBox.getMonthNumber(), 1);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setToday();
	}

	@FXML
	public void monthKeyActions(KeyEvent event) {
		if (event.getCode() == KeyCode.RIGHT  &&  !event.getTarget().equals(yearField)) {
			monthForeward();
		} else if (event.getCode() == KeyCode.LEFT  &&  !event.getTarget().equals(yearField)) {
			monthBackward();
		} else if (event.getCode() == KeyCode.PAGE_DOWN) {
			yearField.setYear(yearField.getYearNumber() + 1);
			refreshable.refreshByTerminAction();
		} else if (event.getCode() == KeyCode.PAGE_UP) {
			yearField.setYear(yearField.getYearNumber() - 1);
			refreshable.refreshByTerminAction();
		}
		refreshable.refreshByTerminAction();
	}


	private void setToday() {
		LocalDate now = LocalDate.now();
		monthBox.setMonthNumber(now.getMonthValue());
		yearField.setYear(now.getYear());
	}

	@FXML
	public void today() {
		setToday();
		refreshable.refreshByTerminAction();
	}

	@FXML
	public void monthBoxSelected() {
		refreshable.refreshByTerminAction();
	}

	@FXML
	public void monthBackward() {
		int mn = monthBox.getMonthNumber() - 1;
		if (mn < 1) {
			mn = 12;
			yearField.setYear(yearField.getYearNumber() - 1);
		}
		monthBox.setMonthNumber(mn);
		refreshable.refreshByTerminAction();
	}

	@FXML
	public void monthForeward() {
		int mn = monthBox.getMonthNumber() + 1;
		if (mn > 12) {
			mn = 1;
			yearField.setYear(yearField.getYearNumber() + 1);
		}
		monthBox.setMonthNumber(mn);
		refreshable.refreshByTerminAction();
	}

	@FXML
	public void yearChanged() {
		refreshable.refreshByTerminAction();
	}

	@FXML
	public void newTermin() {
		terminActions.newTermin();
	}

	@FXML
	public void gotoTerminList() {
		MainController.instance().openTerminList();
	}

}
