package net.immocrm.gui.quality;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import net.immocrm.domain.MerkmalTypeManager;
import net.immocrm.domain.quality.MerkmalType;
import net.immocrm.domain.quality.MerkmalTypeChangeable;
import net.immocrm.domain.ref.Category;
import net.immocrm.domain.valid.DbSaveException;
import net.immocrm.domain.valid.ValidationIssues;
import net.immocrm.gui.alert.AlertProvider;

public class MerkmalTypeDialogController implements Initializable {

	@FXML ToggleGroup typeToggle;
	@FXML TableView<MerkmalTypeRow> merkmalTable;
	@FXML DialogPane dialogPane;

	private final MerkmalTypeManager merkmalTypeMan;

	private MerkmalTypeDialogMapper mapper;
	@FXML ComboBox<String> category;


	public MerkmalTypeDialogController() {
		merkmalTypeMan = new MerkmalTypeManager();
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		refresh();
		mapper = new MerkmalTypeDialogMapper(dialogPane);
		merkmalTable.getSelectionModel().selectedItemProperty().addListener(
			(observable, oldValue, newValue) ->
				{
					MerkmalTypeChangeable merkmalType = merkmalTypeMan.fetchById(newValue.getDomain().getId());
					mapper.merkmalTypeToForm(merkmalType);
				});
		category.getItems().addAll(getCategoryNames());
	}

	private void refresh() {
		fillView(merkmalTypeMan.getAllTypes());
	}

	private void fillView(List<MerkmalType> domainList) {
		ObservableList<MerkmalTypeRow> tableContent = merkmalTable.getItems();
		tableContent.clear();
		for (MerkmalType d : domainList) {
			tableContent.add(new MerkmalTypeRow(d));
		}
		merkmalTable.setItems(tableContent);
		merkmalTable.getSelectionModel().clearSelection();
	}

	private List<String> getCategoryNames() {
		List<String> catTexts = new ArrayList<>();
		for (Category c : Category.values()) {
			catTexts.add(c.getName());
		}
		return catTexts;
	}


	public TableView<MerkmalTypeRow> getMerkmalTable() {
		return merkmalTable;
	}


	@FXML
	public void saveNewMerkmalType() {
		save(MerkmalTypeManager.emptyDomain());
	}

	@FXML
	public void saveMerkmalType() {
		MerkmalTypeRow selectedItem = merkmalTable.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			MerkmalTypeChangeable merkmalType = merkmalTypeMan.fetchById(selectedItem.getId());
			save(merkmalType);
		}
	}

	private void save(MerkmalTypeChangeable merkmalType) {
		try {
			mapper.qualityTypeFromForm(merkmalType);
			ValidationIssues issues = merkmalType.validate();
			if (issues.hasErrors()) {
				AlertProvider.alertIssues(issues);
			} else {
				merkmalTypeMan.save(merkmalType);
				refresh();
			}
		} catch (DbSaveException e) {
			AlertProvider.alertError(e.getProblemType(), e.getMessage(), e.getCause());
		}
	}

}
