package net.immocrm.gui.transfer;

import net.immocrm.domain.transfer.CsvFileContent;

public class ImportValidator {


	public String check(CsvFileContent content, String[] expectedColumns, int expectedColCnt) {
		if (content.isHeaderEmpty()) {
			return "In der Datei fehlen (teilweise) die Spaltenüberschriften";
		}
		int colCount = getColCount(content, expectedColumns);
		if (colCount == 0) {
			return "In der Datei wurde keine passende Spalten gefunden.\nEventuell das Trennzeichen (;) prüfen.";
		}
		if (colCount < expectedColCnt) {
			return "In der Datei wurden nur " + colCount + " passende Spalten gefunden.\nErwartet werden mindestens " + expectedColCnt + " Spalten";
		}
		return null;
	}

	private int getColCount(CsvFileContent content, String[] expectedColumns) {
		int colCount = 0;
		for (String hdCol : content.getHeader()) {
			for (String expCol : expectedColumns) {
				if (expCol.equalsIgnoreCase(hdCol)) {
					colCount++;
					break;
				}
			}
		}
		return colCount;
	}

}
