package net.immocrm.gui.termin;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import net.immocrm.domain.TerminManager;
import net.immocrm.domain.termin.Termin;
import net.immocrm.gui.BaseDialogBuilder;
import net.immocrm.gui.MainController;

public class CalendarController implements Initializable, TerminRefreshable {

	@FXML BorderPane calendarPane;
	@FXML VBox centerArea;

	@FXML GridPane calArea;
	@FXML GridPane weekDayHeader;

	private final TerminManager terminMan;
	private final TerminActions terminActions;

	private LocalDate displayedMonth;
	private CalendarBuilder builder;
	private List<Termin> terminList;
	private CalendarToolbarController ctrl;


	public CalendarController() {
		terminMan = new TerminManager();
		terminActions = new TerminActions(this);
		displayedMonth = LocalDate.of(1000, 1, 1);
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		builder = new CalendarBuilder(this);
		builder.createCalendarView(calArea, weekDayHeader);
		FXMLLoader fxmlLoader = BaseDialogBuilder.getFxmlLoader("termin/CalendarToolbar.fxml");
		ToolBar bar = BaseDialogBuilder.load(fxmlLoader);
		calendarPane.setTop(bar);
		ctrl = fxmlLoader.getController();
		ctrl.setRefreshable(this);
	}

	@Override
	public void refresh() {
		displayedMonth = LocalDate.of(1000, 1, 1);
		refreshByTerminAction();
	}

	@Override
	public void refreshByTerminAction() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				LocalDate selectedMonth = ctrl.getMonthDate();
				if (!displayedMonth.equals(selectedMonth)) {
					displayedMonth = selectedMonth;
					terminList = terminMan.fetchByDates(displayedMonth.withDayOfMonth(1), displayedMonth.plusMonths(1).withDayOfMonth(1));
				}
		    	int offsetInMonth = displayedMonth.withDayOfMonth(1).getDayOfWeek().getValue();
		    	builder.buildDayCountLabels(displayedMonth, offsetInMonth);
				showDates(offsetInMonth);
			}
		});

	}

	private void showDates(int offsetInMonth) {
        for (Termin termin : terminList) {
			builder.buildTerminLabel(offsetInMonth, termin);
		}
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
