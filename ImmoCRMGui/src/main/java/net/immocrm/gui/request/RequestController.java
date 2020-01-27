package net.immocrm.gui.request;

import javafx.fxml.FXML;
import net.immocrm.domain.DomainFactory;
import net.immocrm.domain.Order;
import net.immocrm.domain.Request;
import net.immocrm.domain.RequestManager;
import net.immocrm.domain.RequestReader;
import net.immocrm.gui.MainController;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.refresh.RefreshType;

public class RequestController {

	@FXML
	public void newRequest() {
		if (MainController.instance().getOrder() != null) {
			Request request = newRequest(MainController.instance().getOrder(), true);
			if (request != null) {
				MainController.getRefreshDispatcher().refreshOrderViews(request.getOrder(), RefreshType.updateItem);
			}
		} else {
			AlertProvider.alertMissingOrderItem();
		}
	}

	public Request newRequest(Order order, boolean doSave) {
		Request request = DomainFactory.newRequest(order);
		Request result = showRequest(request, doSave);
		return result;
	}

	public Request showDialog(Request request) {
		if (!request.isNew()) {
			request = RequestReader.INSTANCE.fetchById(request.getId());
		}
		return showRequest(request, true);
	}

	private Request showRequest(Request request, boolean doSave) {
		RequestDialog dlg = new RequestDialog(doSave);
		Request result = dlg.showDialog(request);
		return result;
	}

	public boolean delete(Request r) {
		new RequestManager().delete(r);
		return true;
	}

}
