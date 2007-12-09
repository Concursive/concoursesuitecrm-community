/*
 *  Copyright(c) 2007 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.utils;

import org.apache.poi.hssf.usermodel.*;
import org.aspcfs.modules.base.*;
import java.sql.Connection;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Folder Utility methods
 *
 * @author rajendra
 * @version $Id: FolderUtils.java 4.1 2007-05-15 11:28:31 +0530 (Mon, 15 May 2007) rajendrad Exp $
 * @created May 15,2007
 */
public class FolderUtils {
	
    public final static String ROW_SEPERATOR = "!!";
    public final static String COLUMN_SEPERATOR = "|";
    public final static String ROW_NUMBER_SEPERATOR = "::";
    public static int folderRecordsCount = -1;

	/**
	 * build and return spread sheet(source sheet) using folder data
	 * @param folderId
	 * @param numOfRecords
	 * @param db
	 * @return HSSFWorkbook
	 */
	public static HSSFWorkbook buildDataMap(int folderId, int numOfRecords, Connection db) {
		HSSFSheet sourceSheet = null;
		HSSFWorkbook sourceWb = null;
		HSSFRow row = null;
		Iterator groups = null;
		Iterator fields = null;
		Iterator records = null;
		CustomFieldRecordList recordList = null;
		CustomFieldCategory thisCategory = null;
		try {
			thisCategory = new CustomFieldCategory(db, folderId);
			thisCategory.setLinkModuleId(Constants.FOLDERS_GLOBALFOLDERS);
			thisCategory.setIncludeEnabled(Constants.TRUE);
			thisCategory.setIncludeScheduled(Constants.TRUE);
			thisCategory.setBuildResources(true);

			int currentRow = 0;
			short currentColumn = 0;
			// Create source spread sheet
			sourceWb = new HSSFWorkbook();
			sourceSheet = sourceWb.createSheet("source sheet");
			row = sourceSheet.createRow(currentRow);
			HSSFCell cell = null;

			thisCategory.buildResources(db);
			groups = thisCategory.iterator();

			while (groups.hasNext()) {
				CustomFieldGroup thisGroup = (CustomFieldGroup) groups.next();
				fields = thisGroup.iterator();
				while (fields.hasNext()) {
					CustomField thisField = (CustomField) fields.next();
					cell = row.createCell(currentColumn);
					cell.setCellValue(thisField.getName());
					currentColumn++;
				}
			}
			currentRow++;

			recordList = new CustomFieldRecordList();
			recordList.setLinkModuleId(Constants.FOLDERS_GLOBALFOLDERS);
			recordList.setCategoryId(thisCategory.getId());
			recordList.buildList(db);
			recordList.buildRecordColumns(db, thisCategory);
			records = recordList.iterator();

			while (records.hasNext() && currentRow <= numOfRecords) {
				CustomFieldRecord thisRecord = (CustomFieldRecord) records.next();
				thisCategory.setRecordId(thisRecord.getId());
				thisCategory.buildResources(db);
				groups = thisCategory.iterator();
				// Create rows in spread sheet.
				row = sourceSheet.createRow(currentRow);
				currentColumn = 0;
				while (groups.hasNext()) {
					CustomFieldGroup thisGroup = (CustomFieldGroup) groups.next();
					fields = thisGroup.iterator();
					while (fields.hasNext()) {
						CustomField thisField = (CustomField) fields.next();
						// Create a cell and put field value.
						cell = row.createCell(currentColumn);
						if (thisField.getType() == CustomField.INTEGER || thisField.getType() == CustomField.FLOAT || thisField.getType() == CustomField.CURRENCY || thisField.getType() == CustomField.PERCENT) {
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							String value = thisField.getValueHtml().replaceAll(",", "").replaceAll("&nbsp;","");
							if ("".equals(value)) {
								cell.setCellValue(0);
							} else if (thisField.getType() == CustomField.INTEGER) {
								cell.setCellValue(Integer.parseInt(thisField.getValueHtml()));
							} else if (thisField.getType() == CustomField.FLOAT) {
								cell.setCellValue(Double.parseDouble(value));
							} else if (thisField.getType() == CustomField.CURRENCY) {
								for(int index = 0;index < value.length();index++) {
									if(StringUtils.isNumeric(value.charAt(index))) {
										value = value.substring(index);
										break;
									}
								}
								cell.setCellValue(Double.parseDouble(value));
							} else if (thisField.getType() == CustomField.PERCENT) {
								cell.setCellValue(Double.parseDouble(value.substring(0, value.length() - 1)));
							}
						} else {
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(thisField.getValueHtml());
						}
						currentColumn++;
					}
				}
				currentRow++;
			}
      folderRecordsCount = currentRow;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sourceWb;
	}

	/**
	 * prepare formula sheet, evaluates formulas(on source sheet) and return HTML table
	 * @param propertyString
	 * @param folderId
	 * @param numOfRecords
	 * @param db
	 * @return String
	 */
	public static String buildSpreadSheet(String propertyString, int folderId, int numOfRecords, Connection db) {
		HSSFSheet formulaSheet = null;
		HSSFFormulaEvaluator.CellValue cellValue = null;
		int colCount = 0;
		StringTokenizer rows = new StringTokenizer(propertyString, ROW_SEPERATOR);
		// Create formula spread sheet
		HSSFWorkbook formulaWb = new HSSFWorkbook();
		formulaSheet = formulaWb.createSheet("formula sheet");
		// get source spread sheet
		HSSFWorkbook sourceWb = buildDataMap(folderId, numOfRecords, db);
		HSSFSheet sourceSheet = sourceWb.getSheet("source sheet");
		HSSFFormulaEvaluator evaluator = new HSSFFormulaEvaluator(sourceSheet, sourceWb);
		evaluator.setCurrentRow(sourceSheet.getRow(0));
		while (rows.hasMoreTokens()) {
			String[] tokens = rows.nextToken().split(ROW_NUMBER_SEPERATOR);
			HSSFRow thisRow = formulaSheet.createRow(Integer.parseInt(tokens[0]));
      StringTokenizer columns = new StringTokenizer(tokens[1].substring(1, tokens[1].length() - 1), COLUMN_SEPERATOR);
			short cellCount = 0;
			colCount = columns.countTokens();
			while (columns.hasMoreTokens()) {
				String cellElement = columns.nextToken();
				HSSFCell thisCell = thisRow.createCell(cellCount);
				if (!"".equals(cellElement) && cellElement != null && cellElement.charAt(0) == '=') {
					// evaluate formula

					try {
            String formula = cellElement.substring(1);
            if(formula.indexOf("(") != -1) {
              String cellNames[] = formula.substring(formula.indexOf("(")+1, formula.indexOf(")")).split(":");
              if(cellNames.length > 0 && cellNames.length == 2) {
                String modifiedFormula = formula.substring(0, formula.indexOf(":")+1);
                int cellNumber =  Integer.parseInt(StringUtils.getNumbersOnly(cellNames[1]));
                if(cellNumber > folderRecordsCount) {
                  String cellName = cellNames[1];
                  cellName = cellName.replaceAll(String.valueOf(cellNumber), String.valueOf(folderRecordsCount));
                  modifiedFormula = modifiedFormula.concat(cellName).concat(formula.substring(formula.indexOf(")"), formula.length()));
                  formula = modifiedFormula;
                }
              }
            }
            thisCell.setCellFormula(formula);
						cellValue = evaluator.evaluate(thisCell);
					} catch (Exception e) {
						cellValue = new HSSFFormulaEvaluator.CellValue(HSSFCell.CELL_TYPE_STRING);
						cellValue.setStringValue("Unresolvable Formula");
						e.printStackTrace();
					}
					if (cellValue.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						thisCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						if (folderId != -1) {
							thisCell.setCellValue(cellValue.getNumberValue());
						} else {
							thisCell.setCellValue("Unresolvable Formula");
						}

					} else {
						thisCell.setCellType(HSSFCell.CELL_TYPE_STRING);
						if (folderId != -1) {
							thisCell.setCellValue(cellValue.getStringValue());
						} else {
							thisCell.setCellValue("Unresolvable Formula");
						}
					}
				} else {
					thisCell.setCellType(HSSFCell.CELL_TYPE_STRING);
					thisCell.setCellValue(cellElement);
				}
				cellCount++;
			}
		}
		//traverse formula sheet -- create preview content
		StringBuffer previewContent = new StringBuffer("");
		Iterator formulaRows = formulaSheet.rowIterator();
		while (formulaRows.hasNext()) {
			HSSFRow thisRow = (HSSFRow) formulaRows.next();
			for (short index = thisRow.getFirstCellNum(); index <= thisRow.getLastCellNum(); index++) {
				HSSFCell thisCell = thisRow.getCell(index);
				if (thisCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					previewContent.append(thisCell.getNumericCellValue());
				} else {
					previewContent.append(thisCell.getStringCellValue().trim());
				}
				previewContent.append("0xfff");
			}
		}
		return previewContent.substring(0,previewContent.length()-5);
	}
	
	/**
	 * checks whether the given field exists or not.
	 * @param fieldId
	 * @param categoryList	 
	 * @return boolean return true if it exists otherwise false
	 */
	public static boolean fieldExists(String fieldId, CustomFieldCategory categoryList) {		
		Iterator categoryIterator = null;
		Iterator fieldIterator = null;	
		boolean exists = false;
		categoryIterator = categoryList.iterator();
		while (categoryIterator.hasNext()) {
			CustomFieldGroup customFieldGroup = (CustomFieldGroup) categoryIterator.next();
			fieldIterator  = customFieldGroup.iterator();
			while (fieldIterator.hasNext()) {
				CustomField customField = (CustomField) fieldIterator.next();
				if (customField.getId() == Integer.parseInt(fieldId.trim())) {					
					exists = true;
					break;
				}
			}
		}		
		return exists;		
	}
}

