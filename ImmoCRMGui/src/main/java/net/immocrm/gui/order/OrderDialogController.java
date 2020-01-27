package net.immocrm.gui.order;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import net.immocrm.domain.Order;
import net.immocrm.gui.BaseDialogBuilder;
import net.immocrm.gui.dialog.NoticeController;
import net.immocrm.gui.request.RequestTabController;

public class OrderDialogController implements Initializable {

	@FXML TabPane tabPane;

	private OrderFormController orderFormCtrl;
	private FinanceFormController financeFormCtrl;
	private NoticeController noticeCtrl;
	private RequestTabController requestCtrl;

	private OrderDialogHelper dialogHelper;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		orderFormCtrl = buildTab("order/OrderForm.fxml");
		noticeCtrl = buildTab("dialog/NoticeTab.fxml");
		requestCtrl = buildTab("request/RequestTableTab.fxml");
	}

	@SuppressWarnings("unchecked")
	private <T> T buildTab(String fxmlFileName) {
		FXMLLoader fxmlLoader = BaseDialogBuilder.getFxmlLoader(fxmlFileName);
		Tab tab = BaseDialogBuilder.load(fxmlLoader);
		T ctrl = (T)fxmlLoader.getController();
		tabPane.getTabs().add(tab);
		return ctrl;
	}


	public void valuesToForm(Order order) {
		dialogHelper = order.isSaleOrder() ? new SaleOrderDialogHelper() : new RentOrderDialogHelper();

		orderFormCtrl.setHelper(dialogHelper);
		orderFormCtrl.valuesToForm(order);

		FXMLLoader fxmlLoader = BaseDialogBuilder.getFxmlLoader(dialogHelper.getFinanceOrderFormFxmlFilePath());
		Tab financeTab = BaseDialogBuilder.load(fxmlLoader);
		financeFormCtrl = fxmlLoader.getController();
		orderFormCtrl.setFinanceController(financeFormCtrl);
		tabPane.getTabs().add(1, financeTab);

		financeFormCtrl.valuesToForm(order);
		requestCtrl.requestsToForm(order);

		noticeCtrl.setPromptText("Hier Notizen zum Auftrag eingeben");
		noticeCtrl.setNotice(order.getNotice());
		tabPane.getSelectionModel().select(0);
	}

	public void valuesFromForm(Order order) {
		orderFormCtrl.valuesFromForm(order);
		financeFormCtrl.valuesFromForm(order);
		order.setNotice(noticeCtrl.getNotice());
	}

}
