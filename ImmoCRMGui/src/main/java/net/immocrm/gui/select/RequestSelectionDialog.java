package net.immocrm.gui.select;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.TableColumn;
import net.immocrm.domain.Order;
import net.immocrm.domain.Request;

public class RequestSelectionDialog {

	private SelectionDialog<Request, RequestSelectionRow> dialog;

	public RequestSelectionDialog(Order order) {
		dialog = new SelectionDialogBuilder<Request, RequestSelectionRow>()
				.withDialogHeader("Anfrage auswählen")
				.addColumn(getNameCol())
				.addColumn(getAddress())
				.addColumn(getPriceCol())
				.build(new RequestItemProvider(order));
	}


	private TableColumn<RequestSelectionRow, String> getNameCol() {
		TableColumn<RequestSelectionRow, String> result = new TableColumn<>("Name");
		result.setCellValueFactory(
				param -> {
					return new ReadOnlyObjectWrapper<String>(param.getValue().getName());
				});
		return result;
	}

	private TableColumn<RequestSelectionRow, String> getAddress() {
		TableColumn<RequestSelectionRow, String> result = new TableColumn<>("PLZ");
		result.setCellValueFactory(
				param -> {
					return new ReadOnlyObjectWrapper<String>(param.getValue().getAddress());
				});
		return result;
	}

	private TableColumn<RequestSelectionRow, String> getPriceCol() {
		TableColumn<RequestSelectionRow, String> result = new TableColumn<>("Preis");
		result.setCellValueFactory(
				param -> {
					return new ReadOnlyObjectWrapper<String>(param.getValue().getRequestPrice());
				});
		return result;
	}


	public Request showDialog() {
		return dialog.showDialog();
	}

}
