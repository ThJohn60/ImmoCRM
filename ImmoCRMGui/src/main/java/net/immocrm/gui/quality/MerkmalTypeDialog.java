package net.immocrm.gui.quality;

import net.immocrm.domain.MerkmalTypeManager;
import net.immocrm.domain.quality.MerkmalTypeChangeable;
import net.immocrm.gui.FormDialogBuilder;
import net.immocrm.gui.IconProvider;
import net.immocrm.gui.dialog.BaseDialog;
import net.immocrm.gui.dialog.DialogResponse;

public class MerkmalTypeDialog  {

	private BaseDialog dialog;


	public MerkmalTypeChangeable showDialog() {
		MerkmalTypeChangeable merkmalType = MerkmalTypeManager.emptyDomain();
		dialog = createDialog(merkmalType);
		DialogResponse response = dialog.showAndWait();
		if (response.isOkDone()) {
			return merkmalType;
		}
		return null;
	}

	private BaseDialog createDialog(MerkmalTypeChangeable merkmalType) {
		BaseDialog dlg = new FormDialogBuilder("quality/QualityDialog.fxml")
				.withTitle(merkmalType)
				.withHeader("Ausstatungsmerkmale")
				.withCloseButton()
				.withGraphic(IconProvider.merkmalTypeIcon())
				.build();
		return dlg;
	}

}
