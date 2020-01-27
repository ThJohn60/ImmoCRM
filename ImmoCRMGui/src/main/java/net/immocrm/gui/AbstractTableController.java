package net.immocrm.gui;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import net.immocrm.domain.BaseDomain;
import net.immocrm.gui.refresh.RefreshedDomain;

public abstract class AbstractTableController<D extends BaseDomain, R extends AbstractRow<D>> {

	private final TableView<R> table;


	protected AbstractTableController(TableView<R> table) {
		this.table = table;
	}

	public D getDomainObject() {
		TableViewSelectionModel<R> selectionModel = table.getSelectionModel();
		if (selectionModel.getSelectedItem() != null) {
			return selectionModel.getSelectedItem().getDomain();
		}
		return null;
	}

	public boolean selectItem(D d) {
		for (R r : table.getItems()) {
			if (r.getDomain().isSameId(d)) {
				table.getSelectionModel().select(r);
				return true;
			}
		}
		return false;
	}

	public void fillTable(RefreshedDomain<D> refreshedDomain) {
		D domain = refreshedDomain.getDomain();
		switch (refreshedDomain.getCauseType()) {
		case all:
			fillTable(refreshedDomain.getList());
			break;
		case filtered:
			fillTable(refreshedDomain.getList());
			break;
		case deleteItem:
			R row = findRow(domain);
			if (row != null) {
				table.getItems().remove(row);
				table.getSelectionModel().select(row);
			}
			break;
		case insertItem:
			fillTable(refreshedDomain.getList());
			selectItem(domain);
			break;
		case updateItem:
			row = findRow(domain);
			if (row != null) {
				row.setDomain(domain);
				table.getSelectionModel().select(row);
			}
			break;
		default:
			break;
		}
	}

	private R findRow(D domain) {
		for (R r : table.getItems()) {
			if (r.getDomain().isSameId(domain)) {
				return r;
			}
		}
		return null;
	}

	private void fillTable(List<D> domainList) {
		ObservableList<R> tableContent = table.getItems();
		tableContent.clear();
		for (D d : domainList) {
			tableContent.add(newTableRow(d));
		}
		table.setItems(tableContent);
		table.getSelectionModel().clearSelection();
	}

	protected abstract R newTableRow(D d);

}
