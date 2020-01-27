package net.immocrm.gui.termin;

import java.time.LocalDate;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import net.immocrm.domain.termin.Termin;

public class CalendarBuilder {

	private static final int COL_COUNT = 7;
	private static final int ROW_COUNT = 5;

	private final TerminLabelBuilder labelBuilder;

	private GridPane calArea;
	private VBox[] calDays;


	public CalendarBuilder(TerminRefreshable refreshable) {
		labelBuilder = new TerminLabelBuilder(refreshable);
	}

	public void createCalendarView(GridPane area, GridPane weekDayHeader) {
		this.calArea = area;
		this.calDays = dayFields();
		weekDayHeader(weekDayHeader);
	}

	private void weekDayHeader(GridPane weekDayHeader) {
		String[] weekDays = {"Mo", "Di", "Mi", "Do", "Fr", "Sa", "So",};
		for (int col = 0; col < COL_COUNT; col++) {
			HBox hBox = new HBox();
			hBox.getChildren().add(new Label(weekDays[col]));
			hBox.setAlignment(Pos.CENTER);
        	VBox vBox = new VBox();
        	vBox.setPadding(new Insets(5));
        	vBox.getStyleClass().add("weekday-header");
        	vBox.getChildren().add(hBox);
			weekDayHeader.add(vBox, col, 0);
			weekDayHeader.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
		}
	}

	private VBox[] dayFields() {
		VBox[] days = new VBox[COL_COUNT * ROW_COUNT];
		for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < COL_COUNT; col++) {
            	int index = col + row * COL_COUNT;
            	days[index] = new VBox();
            	days[index].setPadding(new Insets(5));
            	days[index].setFillWidth(true);
                calArea.add(days[index], col, row);
            }
        }
		return days;
	}


	public void buildTerminLabel(int offsetInMonth, Termin termin) {
		Label terminLabel = labelBuilder.terminLabel(calArea, termin);
		calDays[getIndex(offsetInMonth, termin)].getChildren().add(terminLabel);
	}

	private int getIndex(int offsetInMonth, Termin termin) {
		LocalDate date = termin.getDate().toLocalDateTime().toLocalDate();
		return offsetInMonth + date.getDayOfMonth() - 2;
	}

	public void buildDayCountLabels(LocalDate displayedMonth, int offsetInMonth) {
		int daysInMonth = displayedMonth.lengthOfMonth();
        int monthDayCount = 1;
        int fieldCount = 1;

		for (VBox day : calDays) {
			day.getChildren().clear();
			if (fieldCount < offsetInMonth || monthDayCount > daysInMonth) {	// outside month
				day.setStyle("-fx-background-color: #D5F2F5");
			} else {												// inside month
				day.getChildren().add(dayLabel(monthDayCount));
				day.getStyleClass().add("day-field");
				if (fieldCount % 7 == 0) {
					day.setStyle("-fx-background-color: #d2fed2");
				} else {
					day.setStyle("-fx-background-color: white");
				}
				monthDayCount++;
			}
			fieldCount++;
		}
		colorCurrentDay(displayedMonth, offsetInMonth);
		adjustFieldSize();
	}

	private Label dayLabel(int lblCount) {
		Label calendarDayLbl = new Label(Integer.toString(lblCount));
		calendarDayLbl.setPadding(new Insets(3));
		calendarDayLbl.setStyle("-fx-text-fill:darkslategray");
		return calendarDayLbl;
	}

	private void colorCurrentDay(LocalDate displayedMonth, int offsetInMonth) {
		LocalDate today = LocalDate.now();
		if (!displayedMonth.isAfter(today)  &&  !displayedMonth.plusMonths(1).withDayOfMonth(1).isBefore(today)) {
			int fieldIndex = offsetInMonth + today.getDayOfMonth() - 2;
			if (fieldIndex >= 0 && fieldIndex < calDays.length) {
				calDays[fieldIndex].setStyle("-fx-background-color: #f9fcc3");
			}
		}
	}

	private void adjustFieldSize() {
		double width = ((GridPane)calDays[0].getParent()).getWidth();
		calArea.getColumnConstraints().clear();
        for (int i = 0; i < COL_COUNT; i++) {
        	calArea.getColumnConstraints().add(new ColumnConstraints(5, width / 5, width / 5, Priority.ALWAYS, HPos.CENTER, true));
        }
        double height = ((GridPane)calDays[0].getParent()).getWidth();
		calArea.getRowConstraints().clear();
        for (int i = 0; i < ROW_COUNT; i++) {
        	calArea.getRowConstraints().add(new RowConstraints(5, height / 5, height / 5, Priority.ALWAYS, VPos.CENTER, true));
        }
	}

}
