package net.immocrm.gui.termin;

import net.immocrm.domain.Request;
import net.immocrm.domain.termin.Termin;
import net.immocrm.gui.MainController;
import net.immocrm.gui.refresh.RefreshType;
import net.immocrm.gui.request.RequestDialog;
import net.immocrm.gui.tools.EditItemController;

public class TerminActions {

	private final TerminRefreshable refreshable;


	public TerminActions(TerminRefreshable refreshable) {
		this.refreshable = refreshable;
	}


	public void editTermin(Termin t) {
		Termin termin = new TerminDialog().showDialog(t);
		if (termin != null) {
			if (termin.isOrder()) {
				MainController.getRefreshDispatcher().refreshOrderViews(termin.getOrder(), RefreshType.updateItem);
			} else if (termin.isPerson()) {
				MainController.getRefreshDispatcher().refreshPersonViews(termin.getPerson(), RefreshType.updateItem);
			} else if (termin.isRequest()) {
				MainController.getRefreshDispatcher().refreshOrderViews(termin.getRequest().getOrder(), RefreshType.updateItem);
			}
			refreshable.refresh();
		}
	}

	public void editDomainObject(Termin termin) {
		EditItemController ctrl = new EditItemController();
		if (termin.isOrder()) {
			ctrl.editOrder(termin.getOrder());
		} else if (termin.isPerson()) {
			ctrl.editPerson(termin.getPerson(), true);
		} else if (termin.isRequest()) {
			Request request = new RequestDialog().showDialog(termin.getRequest());
			if (request != null) {
				MainController.getRefreshDispatcher().refreshOrderViews(request.getOrder(), RefreshType.updateItem);
			}
		}
	}

	public void gotoDomainObject(Termin termin) {
		if (termin.isOrder()) {
			MainController.instance().showOrderDetails(termin.getOrder());
		} else if (termin.isPerson()) {
			MainController.instance().showPersonDetails(termin.getPerson());
		} else if (termin.isRequest()) {
			MainController.instance().showOrderDetails(termin.getRequest().getOrder());
		}
	}

	public void newTermin() {
		Termin t = new TerminDialog().showDialog();
		if (t != null) {
			refreshable.refresh();
		}
	}

}
