package net.immocrm.gui.property;

import javafx.scene.control.TableView;
import net.immocrm.domain.Immobilie;
import net.immocrm.gui.AbstractTableController;

public class ImmobilieTableController extends AbstractTableController<Immobilie, ImmobilieRow> {

	public ImmobilieTableController(TableView<ImmobilieRow> table) {
		super(table);
	}

	@Override
	protected ImmobilieRow newTableRow(Immobilie immobilie) {
		return new ImmobilieRow(immobilie);
	}

}
