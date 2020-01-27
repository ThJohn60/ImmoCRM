package net.immocrm.gui.control;

import java.util.List;

import javafx.scene.control.ComboBox;
import net.immocrm.domain.quality.MerkmalType;

public class MerkmalTypeComboBox extends ComboBox<MerkmalType> {

	public void fill(List<MerkmalType> merkmalTypes) {
		getItems().clear();
		getItems().addAll(merkmalTypes);
	}

}
