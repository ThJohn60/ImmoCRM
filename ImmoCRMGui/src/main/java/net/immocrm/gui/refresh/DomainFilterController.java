package net.immocrm.gui.refresh;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import net.immocrm.gui.MainController;

public class DomainFilterController implements Initializable {

	private static DomainFilterController instance;

	public static final void requestFocus() {
		instance.filterField.requestFocus();
	}

	@FXML CheckBox activeOnlyFilter;
	@FXML CheckBox saleFilter;
	@FXML CheckBox rentFilter;
	@FXML CheckBox flats;
	@FXML CheckBox houses;
	@FXML CheckBox office;
	@FXML CheckBox site;

	@FXML Hyperlink clearSearch;
	@FXML TextField filterField;

	private DomainViewFilter filter;
	private RefreshDispatcher refreshDispatcher;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ImmobilieFilter immobilieFilter = new ImmobilieFilter(filterField, flats, houses, office, site);
		filter = new DomainViewFilter(filterField, activeOnlyFilter, saleFilter, rentFilter, immobilieFilter);
		refreshDispatcher = MainController.getRefreshDispatcher();
		refreshDispatcher.setFilter(filter);
		instance = this;
	}

	@FXML
	public void refreshAll() {
		refreshDispatcher.refreshAll();
	}

	@FXML
	public void searchByEnter(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			refreshAllViews();
			filterField.requestFocus();
		}
	}

	@FXML
	public void search() {
		refreshAllViews();
	}

	@FXML
	public void searchReset() {
		filterField.clear();
		activeOnlyFilter.setSelected(false);
		saleFilter.setSelected(false);
		rentFilter.setSelected(false);

		flats.setSelected(false);
		houses.setSelected(false);
		office.setSelected(false);
		site.setSelected(false);

		refreshAllViews();
	}

	private void refreshAllViews() {
		refreshDispatcher.refreshAll();
		filterField.requestFocus();
	}


	@FXML
	public void consumerEnter(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
//TODO			treeCtrl.requestFocus();
		}
	}

}
