package net.immocrm.gui.property;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import net.immocrm.domain.Immobilie;
import net.immocrm.gui.BaseDialogBuilder;
import net.immocrm.gui.dialog.NoticeController;
import net.immocrm.gui.doc.DokuTabController;
import net.immocrm.gui.img.ImageTabController;

public class ImmobilieDialogController implements Initializable {

	@FXML TabPane tabPane;

	private ImmobilieFormController detailCtrl;
	private NoticeController noticeCtrl;
	private EigenschaftenController eigenschaftenCtrl;
	private AusstattungController ausstattungCtrl;
	private ImageTabController imageTabCtrl;
	private DokuTabController docCtrl;



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		detailCtrl = buildTab("property/ImmobilieForm.fxml");
		eigenschaftenCtrl = buildTab("property/Eigenschaften.fxml");
		ausstattungCtrl = buildTab("property/Ausstattung.fxml");
		imageTabCtrl = buildTab("img/ImageTab.fxml");
		docCtrl = buildTab("doc/DokuTab.fxml");
		noticeCtrl = buildTab("dialog/NoticeTab.fxml");
		noticeCtrl.setPromptText("Hier Notizen zur Immobilie eingeben");
		noticeCtrl.setTabLabelText("Interne Notizen");
	}

	@SuppressWarnings("unchecked")
	private <T> T buildTab(String fxmlFileName) {
		FXMLLoader fxmlLoader = BaseDialogBuilder.getFxmlLoader(fxmlFileName);
		Tab tab = BaseDialogBuilder.load(fxmlLoader);
		T ctrl = (T)fxmlLoader.getController();
		tabPane.getTabs().add(tab);
		return ctrl;
	}


	public void valuesToForm(Immobilie immobilie) {
		detailCtrl.valuesToForm(immobilie);
		eigenschaftenCtrl.valuesToForm(immobilie);
		ausstattungCtrl.valuesToForm(immobilie);
		imageTabCtrl.showImages(immobilie.getImageDir());
		docCtrl.showDocs(immobilie);
		noticeCtrl.setNotice(immobilie.getInternalNotice());
		noticeCtrl.setPromptText("Hier Notizen zur Immobilie eingeben");
	}

	public void valuesFromForm(Immobilie immobilie) {
		detailCtrl.valuesFromForm(immobilie);
		eigenschaftenCtrl.valuesFromForm(immobilie);
		ausstattungCtrl.valuesFromForm(immobilie);
		immobilie.setInternalNotice(noticeCtrl.getNotice());
	}

}
