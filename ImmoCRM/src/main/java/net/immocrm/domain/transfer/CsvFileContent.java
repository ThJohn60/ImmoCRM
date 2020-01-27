package net.immocrm.domain.transfer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvFileContent {

	private final String[] header;
	private final List<String[]> rows;
	private int[] indexMap;


	public CsvFileContent(String[] header) {
		this.header = header;
		rows = new ArrayList<>();
	}


	public String[] getHeader() {
		return header;
	}

	public int getRowCount() {
		return rows.size();
	}

	public int getColumnCount() {
		return header.length;
	}

	public boolean isHeaderEmpty() {
		for (int i = 0; i < header.length; i++) {
			if (header[i] == null || header[i].isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public boolean isEmpty() {
		return rows.isEmpty();
	}

	public boolean add(String[] e) {
		return rows.add(e);
	}

	public void initIndexMap(String[] expectedColums) {
		indexMap = new int[expectedColums.length];
		Arrays.fill(indexMap, -1);
		for (int i = 0; i < expectedColums.length; i++) {
			for (int j = 0; j < header.length; j++) {
				if (expectedColums[i].equalsIgnoreCase(header[j])) {
					indexMap[i] = j;
				}
			}
		}
	}

	public String getMappedField(int rowIndex, int colIndex) {
		String[] row = rows.get(rowIndex);
		if (indexMap[colIndex] != -1 && indexMap[colIndex] < row.length) {
			return row[indexMap[colIndex]];
		}
		return null;
	}

}
