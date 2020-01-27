package net.immocrm.gui.pref;

import java.util.ArrayList;
import java.util.List;

import net.immocrm.domain.PreferenceManager;
import net.immocrm.domain.ref.Category;
import net.immocrm.gui.FormDialogBuilder;
import net.immocrm.gui.dialog.BaseDialog;

public class PreferenceDialog {

	@SuppressWarnings("unused")
	private final PreferenceManager preferenceMan;

	private BaseDialog dialog;


	public PreferenceDialog() {
		this.preferenceMan = new PreferenceManager();
	}
	
	public void showDialog() {
		dialog = createDialog();
		dialog.showAndWait();
	}

	private BaseDialog createDialog() {
		List<String> catTexts = new ArrayList<>();
		for (Category c : Category.values()) {
			catTexts.add(c.getName());
		}
		BaseDialog dlg = new FormDialogBuilder("pref/PreferenceDialog.fxml")
				.withTitle("Einstellungen")
//				.withHeader("Einstellungen")
				.withOkCancelButtons()
				.build();
		return dlg;
	}

}
