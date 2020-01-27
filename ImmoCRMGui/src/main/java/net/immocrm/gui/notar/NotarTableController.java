package net.immocrm.gui.notar;

import javafx.scene.control.TableView;
import net.immocrm.domain.Notar;
import net.immocrm.gui.AbstractTableController;

public class NotarTableController extends AbstractTableController<Notar, NotarRow> {

	protected NotarTableController(TableView<NotarRow> table) {
		super(table);
	}

	@Override
	protected NotarRow newTableRow(Notar d) {
		return new NotarRow(d);
	}

}
