package net.immocrm.gui.transfer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import net.immocrm.domain.transfer.CsvFileContent;

public class CsvFileReader {

	private Window ownerWindow;


	public CsvFileReader(Window ownerWindow) {
		this.ownerWindow = ownerWindow;
	}


	public File getChosenFile(File dir) {
		FileChooser fileChooser = new FileChooser();
		ExtensionFilter filter = new FileChooser.ExtensionFilter("Textdateien im CSV-Format", "*.csv");
		fileChooser.getExtensionFilters().add(filter);
		if (dir != null) {
			fileChooser.setInitialDirectory(dir);
		}
		File result = fileChooser.showOpenDialog(ownerWindow);
		return result;
	}


	public CsvFileContent readFile(File file) throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			checkUtf8BomMarker(br);
			String line = br.readLine();
			String[] cols = getColumns(line);
			CsvFileContent result = new CsvFileContent(cols);
			line = br.readLine();
			while (line != null) {
				result.add(getColumns(line));
				line = br.readLine();
			}
			return result;
		}
	}


	private void checkUtf8BomMarker(BufferedReader br) throws IOException {
		br.mark(4);
		if ('\ufeff' != br.read()) {
			br.reset(); // not the UTF-8 BOM marker
		}
	}

	private String[] getColumns(String line) {
		line = line.replaceAll(";", "; ");
		StringTokenizer st = new StringTokenizer(line, ";");
		String[] cols = new String[st.countTokens()];
		for (int i = 0; i < cols.length; i++) {
			cols[i] = st.nextToken().trim();
		}
		return cols;
	}

}
