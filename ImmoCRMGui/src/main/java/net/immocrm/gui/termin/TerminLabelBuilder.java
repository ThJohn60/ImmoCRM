package net.immocrm.gui.termin;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import net.immocrm.domain.termin.Termin;

public class TerminLabelBuilder {

	private TerminRefreshable refreshable;
	private TerminActions terminActions;


	public TerminLabelBuilder(TerminRefreshable refreshable) {
		this.refreshable = refreshable;
		terminActions = new TerminActions(refreshable);
	}


	public Label terminLabel(GridPane calArea, Termin termin) {
		Label terminLabel = new Label();
		ColumnConstraints constraints = calArea.getColumnConstraints().get(0);
		terminLabel.setPrefWidth(constraints.getPrefWidth());
		terminLabel.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				terminActions.editTermin(termin);
			}
		});
		terminLabel.setContextMenu(new TerminContextMenu(termin, refreshable));
		terminLabel.setPadding(new Insets(2));
		terminLabel.setBorder(border(termin));
		terminLabel.setText(getTime(termin) + termin.getCalendarLabel());
		return terminLabel;
	}

	private Border border(Termin termin) {
		Color borderColor = termin.isIndependentTermin() ? Color.DARKBLUE : Color.GREEN;
		BorderStroke stroke = new BorderStroke(borderColor, BorderStrokeStyle.DOTTED, new CornerRadii(4), new BorderWidths(1));
		return new Border(stroke, null);
	}

	private String getTime(Termin termin) {
		if (termin.getDate().getHour() == 0 && termin.getDate().getMinute() == 0) {
			return "";
		}
		return String.format("%02d:%02d ", termin.getDate().getHour(), termin.getDate().getMinute());
	}

}
