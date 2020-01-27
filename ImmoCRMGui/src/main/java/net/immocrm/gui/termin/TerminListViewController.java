package net.immocrm.gui.termin;

import java.time.LocalDate;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import net.immocrm.domain.TerminManager;
import net.immocrm.domain.termin.Termin;
import net.immocrm.domain.vc.NumberValue;
import net.immocrm.gui.control.NumberField;

public class TerminListViewController implements TerminRefreshable {

	@FXML TableView<TerminRow> terminTable;
	@FXML NumberField dayCntField;

	private final TerminManager terminMan;
	private final TerminActions terminActions;


	public TerminListViewController() {
		terminMan = new TerminManager();
		terminActions = new TerminActions(this);
	}


	public void requestFocus() {
		terminTable.requestFocus();
	}

	@FXML
	public void fetchByEnter(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			refreshByTerminAction();
		}
	}

	@FXML
	public void dayCntFieldReset() {
		dayCntField.clear();
		refreshByTerminAction();
	}

	@Override
	public void refresh() {
		refreshByTerminAction();
	}

	@Override
	@FXML
	public void refreshByTerminAction() {
		NumberValue number = dayCntField.getValidatedNumber();
		int cntDays = number.isEmpty() ? 0 : number.getValue();
		LocalDate startDate = LocalDate.now().minusDays(cntDays);
		List<Termin> list = terminMan.fetchByDates(startDate, LocalDate.of(3000, 1, 1));
		fillView(list);
	}

	private void fillView(List<Termin> domainList) {
		ObservableList<TerminRow> tableContent = terminTable.getItems();
		tableContent.clear();
		for (Termin d : domainList) {
			tableContent.add(new TerminRow(d));
		}
		terminTable.setItems(tableContent);
	}

	@FXML
	public void mouseClickOnTableItem(MouseEvent event) {
		if (event.getClickCount() == 2) {
			gotoDomainObject();
		}
	}

	@FXML
	public void keyPressedOnTableItem(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			editTermin();
		}
	}

	@FXML
	public void gotoDomainObject() {
		TerminRow row = terminTable.getSelectionModel().getSelectedItem();
		if (row != null) {
			Termin termin = row.getTermin();
			terminActions.gotoDomainObject(termin);
		}
	}

	@FXML
	public void editDomainObject() {
		TerminRow row = terminTable.getSelectionModel().getSelectedItem();
		if (row != null) {
			terminActions.editDomainObject(row.getTermin());
		}
	}

	@FXML
	public void editTermin() {
		TerminRow row = terminTable.getSelectionModel().getSelectedItem();
		if (row != null) {
			terminActions.editTermin(row.getTermin());
		}
	}

}
