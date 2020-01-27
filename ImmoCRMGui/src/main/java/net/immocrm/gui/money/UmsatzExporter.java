package net.immocrm.gui.money;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javafx.collections.ObservableList;

public class UmsatzExporter {

	public HSSFWorkbook createExcelWorkbook(ObservableList<UmsatzRow> guiRows) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Umsätze");

		int rowCnt = 0;
		HSSFRow row = sheet.createRow(rowCnt++);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("Datum");
		cell = row.createCell(1);
		cell.setCellValue("Provision");
		cell = row.createCell(2);
		cell.setCellValue("Details");

		for (UmsatzRow umsatzRow : guiRows) {
			row = sheet.createRow(rowCnt++);

			cell = row.createCell(0);
			cell.setCellValue(umsatzRow.getDate());
			HSSFCellStyle style = wb.createCellStyle();
			style.setDataFormat((short)0xe);
			cell.setCellStyle(style);

			cell = row.createCell(1);
			cell.setCellValue(umsatzRow.getUmsatz().getUmsatz().getDoubleValue());
			style = wb.createCellStyle();
			style.setDataFormat((short)3);
			cell.setCellStyle(style);

			cell = row.createCell(2);
			cell.setCellValue(umsatzRow.getDetails());
		}

		for (int i = 0; i < 3; i++) {
			sheet.autoSizeColumn(i);
		}

		return wb;
	}

}
