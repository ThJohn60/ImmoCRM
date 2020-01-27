package net.immocrm.gui.create;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Label;

public class StepHint {

	private final Map<NewOrderSteps, Label> lblByOrderStep;
	private final Map<NewImmobilieSteps, Label> lblByImmobilieStep;


	public StepHint(AssistentDialogFrame dlgFrame, NewOrderSteps[] steps) {
		this();
		for (int i = 0; i < steps.length; i++) {
			Label stepLabel = (Label)dlgFrame.lookup("#step" + (i+1));
			lblByOrderStep.put(steps[i], stepLabel);
		}
	}

	public StepHint(AssistentDialogFrame dlgFrame, NewImmobilieSteps[] steps) {
		this();
		for (int i = 0; i < steps.length; i++) {
			Label stepLabel = (Label)dlgFrame.lookup("#step" + (i+1));
			lblByImmobilieStep.put(steps[i], stepLabel);
		}
	}

	private StepHint() {
		this.lblByOrderStep = new HashMap<>();
		this.lblByImmobilieStep = new HashMap<>();
	}


	public void setHint(NewOrderSteps step) {
		clearHint(lblByOrderStep.values());
		Label stepLabel = lblByOrderStep.get(step);
		stepLabel.setStyle("-fx-text-fill: red");
	}

	public void setHint(NewImmobilieSteps step) {
		clearHint(lblByImmobilieStep.values());
		Label stepLabel = lblByImmobilieStep.get(step);
		stepLabel.setStyle("-fx-text-fill: red");
	}

	private void clearHint(Collection<Label> values) {
		for (Label lbl : values) {
			lbl.setStyle("");
		}
	}
	
}
