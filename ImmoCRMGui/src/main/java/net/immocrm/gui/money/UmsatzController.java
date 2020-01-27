package net.immocrm.gui.money;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import net.immocrm.domain.Umsatz;
import net.immocrm.domain.UmsatzManager;
import net.immocrm.domain.vc.Year;
import net.immocrm.gui.Main;
import net.immocrm.gui.MainController;
import net.immocrm.gui.alert.AlertProvider;

public class UmsatzController {

	@FXML TableView<UmsatzRow> umsatzTable;
	@FXML CheckBox yearIncomeSwitch;

	private final UmsatzManager umsatzMan;


	public UmsatzController() {
		umsatzMan = new UmsatzManager();
	}


	public void refresh() {
		ObservableList<UmsatzRow> tableContent = umsatzTable.getItems();
		tableContent.clear();

		List<Umsatz> list = umsatzMan.fetchAll();
		if (yearIncomeSwitch.isSelected()) {
			Map<Year, YearUmsatz> map = getMappedYearUmsatz(list);
			List<Year> years = new ArrayList<>(map.keySet());
			Collections.sort(years);
			for (Year year : years) {
				tableContent.add(new UmsatzRow(map.get(year)));
			}
		} else {
			for (Umsatz umsatz : list) {
				tableContent.add(new UmsatzRow(umsatz));
			}
		}
	}

	private Map<Year, YearUmsatz> getMappedYearUmsatz(List<Umsatz> list) {
		Map<Year,YearUmsatz> map = new HashMap<>();
		for (Umsatz umsatz : list) {
			YearUmsatz yearUmsatz = map.get(umsatz.getYear());
			if (yearUmsatz == null) {
				yearUmsatz = new YearUmsatz(umsatz.getYear(), umsatz.getUmsatz());
				map.put(umsatz.getYear(), yearUmsatz);
			} else {
				yearUmsatz.addUmsatz(umsatz.getUmsatz());
			}
		}
		return map;
	}

	public void requestFocus() {
		umsatzTable.requestFocus();
	}


	@FXML
	public void keyPressedOnTableItem(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			gotoOrder();
		}
	}


	@FXML
	public void mouseClickOnTableItem(MouseEvent event) {
		if (event.getClickCount() == 2) {
			gotoOrder();
		}
	}


	@FXML
	public void gotoOrder() {
		UmsatzRow umsatzRow = umsatzTable.getSelectionModel().getSelectedItem();
		if (umsatzRow != null  &&  umsatzRow.getUmsatz().getOrder() != null) {
			MainController.instance().showOrderDetails(umsatzRow.getUmsatz().getOrder());
		}
	}

	@FXML
	public void toggleIncomeType() {
		refresh();
	}

	@FXML
	public void saveAsExcelFile() {
		UmsatzExporter umsatzExporter = new UmsatzExporter();
		HSSFWorkbook wb = umsatzExporter.createExcelWorkbook(umsatzTable.getItems());
		while (true) {
			if (saveWorkbook(wb)) {
				break;
			}
		}
	}

	private boolean saveWorkbook(HSSFWorkbook wb) {
		FileChooser chooser = fileChooser();
		File file = chooser.showSaveDialog(Main.getApplicationWindow());
		if (file != null) {
			try (FileOutputStream out = new FileOutputStream(file)) {
				wb.write(out);
				out.flush();
				out.close();
			} catch (IOException e) {
				AlertProvider.alertError("Fehler beim Schreiben der Datei " + file.getName(), e.getMessage(), e);
				return false;
			}
		}
		return true;
	}

	private FileChooser fileChooser() {
		FileChooser chooser = new FileChooser();
		String homeDir = System.getProperty("user.home");
		if (System.getProperty("os.name").contains("Window")) {
			homeDir = homeDir + "\\" + "Documents";
		}
		chooser.setInitialDirectory(new File(homeDir));
		chooser.setInitialFileName("Umsätze.xlsx");
		chooser.setTitle("Exceldatei speichern");
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel", "*." + "xlsx"));
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel", "*." + "xls"));
		return chooser;
	}

}
