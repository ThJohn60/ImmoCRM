package net.immocrm.gui.person;

import net.immocrm.domain.DomainFactory;
import net.immocrm.domain.Person;
import net.immocrm.domain.PersonManager;
import net.immocrm.domain.PersonReader;
import net.immocrm.domain.TextMaker;
import net.immocrm.domain.valid.ValidationIssues;
import net.immocrm.gui.FormDialogBuilder;
import net.immocrm.gui.IconProvider;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.dialog.DialogResponse;
import net.immocrm.gui.dialog.DialogSaveValidator;
import net.immocrm.gui.form.FormDialog;
import net.immocrm.gui.form.NoActionException;

public class PersonDialog {

	private PersonDialogController dialogCtrl;


	public Person showDialog() {
		return showDialog(DomainFactory.newPerson());
	}

	public Person showDialog(Person person) {
		return showDialog(person, 0, false);
	}

	public Person showDialog(Person person, int showTabNr, boolean disableNameChange) {
		if (!person.isNew()) {
			person = PersonReader.INSTANCE.fetchById(person.getId());
		}
		FormDialog dlg = buildDialog(person, showTabNr, disableNameChange);
		DialogResponse response = dlg.showAndWait();
		if (response.isOkDone()) {
			return person;
		}
		return null;
	}

	private FormDialog buildDialog(Person person, int showTabNr, boolean disableNameChange) {
		FormDialogBuilder builder = getDialogBuilder(person, showTabNr);
		dialogCtrl = builder.getController();
		builder.setOnCloseRequestOkHandler(getDialogSaveHandler(person));
		dialogCtrl.enableNameFields(!disableNameChange);
		dialogCtrl.personToForm(person);
		return builder.build();
	}

	private FormDialogBuilder getDialogBuilder(Person person, int showTabNr) {
		FormDialogBuilder builder = new FormDialogBuilder("person/PersonDialog.fxml")
				.withTitle(person)
				.withHeader(person.isNew() ? "Neue Person" : TextMaker.INSTANCE.getDialogHeader(person))
				.withDialogIcon(IconProvider.personDialogIcon())
				.withOkCancelButtons()
				.openTab(showTabNr);
		return builder;
	}

	private DialogSaveValidator getDialogSaveHandler(Person person) {
		return event -> {
			return saveHandler(person);
		};
	}

	private boolean saveHandler(Person person) {
		try {
			PersonManager personMan = new PersonManager();

			Person testPerson = DomainFactory.newPerson();
			dialogCtrl.personFromForm(testPerson);
			ValidationIssues issues = testPerson.validate();
			if (issues.hasErrors()) {
				AlertProvider.alertIssues(issues);
				return false;
			}
			dialogCtrl.personFromForm(person);
			personMan.save(person);
			return true;
		} catch (NoActionException e) {
		} catch (Exception e) {
			AlertProvider.alertError(e.getMessage());
		}
		return false;
	}

}
