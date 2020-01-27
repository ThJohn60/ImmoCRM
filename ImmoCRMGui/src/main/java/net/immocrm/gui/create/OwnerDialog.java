package net.immocrm.gui.create;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import net.immocrm.domain.DomainFactory;
import net.immocrm.domain.Person;
import net.immocrm.gui.BaseDialogBuilder;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.dialog.DialogSaveValidator;
import net.immocrm.gui.person.PersonFormController;

public class OwnerDialog extends AbstractCreationDialog<Person> {

	private final PersonFormController ctrl;


	public OwnerDialog(AssistentDialogFrame dialogFrame) {
		super(dialogFrame);
		dialogFrame.addButtons(ButtonData.BACK_PREVIOUS, ButtonData.NEXT_FORWARD, ButtonData.CANCEL_CLOSE);
		dialogFrame.setHeader("Auftraggeber");
		FXMLLoader fxmlLoader = BaseDialogBuilder.getFxmlLoader("person/PersonForm.fxml");
		Tab tab = BaseDialogBuilder.load(fxmlLoader);
		dialogFrame.setContentPane((Pane)tab.getContent());
		ctrl = fxmlLoader.getController();
	}

	@Override
	protected DialogSaveValidator getValidator() {
		return event -> {
			Person person = DomainFactory.newPerson();
			ctrl.personFromForm(person);
			if (person.isEmpty()) {
				AlertProvider.alertInfo("Bitte den Auftraggeber bzw. Eigentümer auswählen oder erfassen!");
				return false;
			}
			return true;
		};
	}

	@Override
	protected void valuesToForm(Person person) {
		ctrl.personToForm(person);
	}

	@Override
	protected void valuesFromForm(Person person) {
		ctrl.personFromForm(person);
	}

}
