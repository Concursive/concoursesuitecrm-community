/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.utils;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import com.zeroio.iteam.base.Requirement;
import com.zeroio.iteam.base.Assignment;
import com.isavvix.tools.FileInfo;
import java.util.*;
// Excel
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
// XML
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.aspcfs.utils.XMLUtils;

/**
 *  Imports data from other formats into an outline
 *
 *@author     matt rajkowski
 *@created    June 30, 2004
 *@version    $Id$
 */
public class AssignmentImporter {

  /**
   *  Description of the Method
   *
   *@param  fileInfo       Description of the Parameter
   *@param  requirement    Description of the Parameter
   *@param  db             Description of the Parameter
   *@return                Description of the Return Value
   *@exception  Exception  Description of the Exception
   */
  public static boolean parse(FileInfo fileInfo, Requirement requirement, Connection db) throws Exception {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("AssignmentImporter-> trying to parse: " + fileInfo.getClientFileName().toLowerCase());
    }
    if (fileInfo.getClientFileName().toLowerCase().endsWith(".xls")) {
      return parseExcel(fileInfo.getFileContents(), requirement, db);
    }
    if (fileInfo.getClientFileName().toLowerCase().endsWith(".xmloutline")) {
      return parseOmniOutliner(fileInfo.getFileContents(), requirement, db);
    }
    return false;
  }



  /**
   *  Description of the Method
   *
   *@param  requirement       Description of the Parameter
   *@param  db                Description of the Parameter
   *@param  buffer            Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public static boolean parseExcel(byte[] buffer, Requirement requirement, Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("AssignmentImporter-> parseExcel");
    }
    try {
      db.setAutoCommit(false);
      // stream the Excel Spreadsheet from the uploaded byte array
      POIFSFileSystem fs =
          new POIFSFileSystem(new ByteArrayInputStream(buffer));
      HSSFWorkbook hssfworkbook = new HSSFWorkbook(fs);
      // get the first sheet
      HSSFSheet sheet = hssfworkbook.getSheetAt(0);
      // define objects for housing spreadsheet data
      HSSFCell currentStateCell = null;
      HSSFCell currentSalesCell = null;
      HSSFRow currentRow = sheet.getRow(0);
      // parse each row, create and insert into a new requirement with a tree
      int rows = sheet.getPhysicalNumberOfRows();
      if (System.getProperty("DEBUG") != null) {
        System.out.println("AssignmentImporter-> Number of rows: " + rows);
      }
      // Columns
      int columnHeader = -1;
      int columnMax = -1;
      boolean columnItemComplete = false;
      short itemColumn = -1;
      short priorityColumn = -1;
      short assignedToColumn = -1;
      short effortColumn = -1;
      short startColumn = -1;
      short endColumn = -1;

      // parse
      for (int r = 0; r < rows; r++) {
        currentRow = sheet.getRow(r);

        if (currentRow != null) {
          // Search for header
          if (columnHeader == -1) {
            int cells = currentRow.getPhysicalNumberOfCells();
            for (short c = 0; c < cells; c++) {
              HSSFCell cell = currentRow.getCell(c);
              if (cell != null) {
                if ("Item".equals(cell.getStringCellValue())) {
                  columnHeader = r;
                  itemColumn = c;
                  columnMax = c;
                } else if (itemColumn > -1 && !columnItemComplete && c > itemColumn) {
                  if ("".equals(cell.getStringCellValue().trim())) {
                    columnMax = c;
                  } else if (!"".equals(cell.getStringCellValue().trim())) {
                    columnItemComplete = true;
                  }
                }
                if ("Priority".equals(cell.getStringCellValue())) {
                  columnHeader = r;
                  priorityColumn = c;
                } else if ("Assigned To".equals(cell.getStringCellValue())) {
                  columnHeader = r;
                  assignedToColumn = c;
                } else if ("Effort".equals(cell.getStringCellValue())) {
                  columnHeader = r;
                  effortColumn = c;
                } else if ("Start".equals(cell.getStringCellValue())) {
                  columnHeader = r;
                  startColumn = c;
                } else if ("End".equals(cell.getStringCellValue())) {
                  columnHeader = r;
                  endColumn = c;
                }
              }
            }
          }
          // Process each column
          if (columnHeader > -1 && r > columnHeader) {
            boolean gotOne = false;
            Assignment assignment = new Assignment();
            assignment.setProjectId(requirement.getProjectId());
            assignment.setRequirementId(requirement.getId());
            // Activities and folders
            if (itemColumn > -1) {
              // Get the first indent level that has data
              for (short c = itemColumn; c <= columnMax; c++) {
                HSSFCell cell = currentRow.getCell(c);
                if (cell != null && !"".equals(cell.getStringCellValue().trim())) {
                  assignment.setRole(cell.getStringCellValue());
                  assignment.setIndent(c);
                  gotOne = true;
                  break;
                }
              }
            }
            if (gotOne) {
              // Effort
              if (effortColumn > -1) {
                HSSFCell cell = currentRow.getCell(effortColumn);
                if (cell != null) {
                  assignment.setEstimatedLoe(cell.getStringCellValue());
                }
              }
              // Start Date
              if (startColumn > -1) {
                HSSFCell cell = currentRow.getCell(effortColumn);
                if (cell != null) {
                  assignment.setEstStartDate(cell.getStringCellValue());
                }
              }
              // Due Date
              if (endColumn > -1) {
                HSSFCell cell = currentRow.getCell(effortColumn);
                if (cell != null) {
                  assignment.setDueDate(cell.getStringCellValue());
                }
              }
              assignment.setEnteredBy(requirement.getEnteredBy());
              assignment.setModifiedBy(requirement.getModifiedBy());
              assignment.setStatusId(1);
              assignment.setPriorityId(2);
              assignment.insert(db);
              if (System.getProperty("DEBUG") != null) {
                System.out.println("AssignmentImporter-> Assignment Inserted: " + assignment.getId());
              }
            }
          }
        }
      }

      db.commit();
    } catch (Exception e) {
      db.rollback();
      e.printStackTrace(System.out);
      return false;
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  buffer            Description of the Parameter
   *@param  requirement       Description of the Parameter
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public static boolean parseOmniOutliner(byte[] buffer, Requirement requirement, Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("AssignmentImporter-> parseOmniOutliner");
    }
    try {
      db.setAutoCommit(false);
      // stream the XML Outline from the uploaded byte array, ignore the DTD
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      builder.setEntityResolver(
        new EntityResolver() {
          public InputSource resolveEntity(java.lang.String publicId, java.lang.String systemId)
               throws SAXException, java.io.IOException {
            return new InputSource(new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?>".getBytes()));
          }
        });
      Document document = builder.parse(new ByteArrayInputStream(buffer));
      // Position
      boolean positionItemComplete = false;
      // Umm... put in an object
      short itemPosition = -1;
      short priorityPosition = -1;
      short assignedToPosition = -1;
      short effortPosition = -1;
      short startPosition = -1;
      short endPosition = -1;
      // Parse the columns
      ArrayList columnList = new ArrayList();
      Element columnElement = XMLUtils.getFirstChild(document, "oo:columns");
      XMLUtils.getAllChildren(columnElement, columnList);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("AssignmentImporter-> Columns: " + columnList.size());
      }
      Iterator columnIterator = columnList.iterator();
      short position = -1;
      boolean foundOutline = false;
      while (columnIterator.hasNext()) {
        Element columnNode = (Element) columnIterator.next();
        Element column = XMLUtils.getFirstChild(columnNode, "oo:title");
        String columnName = XMLUtils.getNodeText(column);
        System.out.println("AssignmentImporter-> Column name: " + columnName);
        if ("yes".equals((String) columnNode.getAttribute("is-outline-column"))) {
          foundOutline = true;
        }
        if (foundOutline) {
          ++position;
          if ("topic".equalsIgnoreCase(columnName)) {
            positionItemComplete = true;
            itemPosition = position;
          }
          if (positionItemComplete) {
            if ("priority".equalsIgnoreCase(columnName)) {
              priorityPosition = position;
            } else if ("lead".equalsIgnoreCase(columnName)) {
              assignedToPosition = position;
            } else if ("effort".equalsIgnoreCase(columnName)) {
              effortPosition = position;
            } else if ("start".equalsIgnoreCase(columnName)) {
              startPosition = position;
            } else if ("end".equalsIgnoreCase(columnName)) {
              endPosition = position;
            }
          }
        }
      }
      // Process the outline
      if (positionItemComplete) {
        ArrayList itemList = new ArrayList();
        Element rootElement = XMLUtils.getFirstChild(document, "oo:root");
        XMLUtils.getAllChildren(rootElement, itemList);
        if (System.getProperty("DEBUG") != null) {
          System.out.println("AssignmentImporter-> Items: " + itemList.size());
        }
        // Go through the items, for each item see if it has children, with items, etc.
        Iterator itemIterator = itemList.iterator();
        while (itemIterator.hasNext()) {
          Element itemElement = (Element) itemIterator.next();
          parseItemElement(itemElement, db, 0, requirement.getProjectId(), requirement.getId(),
              requirement.getEnteredBy(), requirement.getModifiedBy(),
              itemPosition, priorityPosition, assignedToPosition, effortPosition, startPosition, endPosition);
        }
      }
      db.commit();
    } catch (Exception e) {
      db.rollback();
      e.printStackTrace(System.out);
      return false;
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  item                Description of the Parameter
   *@param  db                  Description of the Parameter
   *@param  indent              Description of the Parameter
   *@param  projectId           Description of the Parameter
   *@param  requirementId       Description of the Parameter
   *@param  enteredBy           Description of the Parameter
   *@param  modifiedBy          Description of the Parameter
   *@param  itemPosition        Description of the Parameter
   *@param  priorityPosition    Description of the Parameter
   *@param  assignedToPosition  Description of the Parameter
   *@param  effortPosition      Description of the Parameter
   *@param  startPosition       Description of the Parameter
   *@param  endPosition         Description of the Parameter
   *@exception  Exception       Description of the Exception
   */
  private static void parseItemElement(Element item, Connection db, int indent, int projectId, int requirementId,
      int enteredBy, int modifiedBy,
      int itemPosition, int priorityPosition, int assignedToPosition, int effortPosition, int startPosition, int endPosition) throws Exception {
    // Get the values for the item
    ArrayList valuesList = new ArrayList();
    Element valuesElement = XMLUtils.getFirstChild(item, "oo:values");
    XMLUtils.getAllChildren(valuesElement, valuesList);
    // Insert the assignment
    Assignment assignment = new Assignment();
    assignment.setProjectId(projectId);
    assignment.setRequirementId(requirementId);
    assignment.setRole(extractText((Element) valuesList.get(itemPosition)));
    assignment.setIndent(indent);
    if (effortPosition > -1) {
      assignment.setEstimatedLoe(extractText((Element) valuesList.get(effortPosition)));
    }
    if (startPosition > -1) {
      assignment.setEstStartDate(extractText((Element) valuesList.get(startPosition)));
    }
    if (endPosition > -1) {
      assignment.setDueDate(extractText((Element) valuesList.get(endPosition)));
    }
    assignment.setEnteredBy(enteredBy);
    assignment.setModifiedBy(modifiedBy);
    assignment.setStatusId(1);
    assignment.setPriorityId(2);
    assignment.insert(db);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("AssignmentImporter-> Assignment Inserted: " + assignment.getId());
    }

    // See if there are children, then parse the children items
    Element childrenElement = XMLUtils.getFirstChild(item, "oo:children");
    if (childrenElement != null) {
      ArrayList itemList = new ArrayList();
      XMLUtils.getAllChildren(childrenElement, itemList);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("AssignmentImporter-> Children items: " + itemList.size());
      }
      // Go through the items, for each item see if it has children, with items, etc.
      Iterator itemIterator = itemList.iterator();
      while (itemIterator.hasNext()) {
        Element itemElement = (Element) itemIterator.next();
        parseItemElement(itemElement, db, (indent + 1), projectId, requirementId,
            enteredBy, modifiedBy,
            itemPosition, priorityPosition, assignedToPosition, effortPosition, startPosition, endPosition);
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  element        Description of the Parameter
   *@return                Description of the Return Value
   *@exception  Exception  Description of the Exception
   */
  public static String extractText(Element element) throws Exception {
    Element p = XMLUtils.getFirstChild(element, "oo:p");
    if (p == null) {
      System.out.println("TEXT: " + XMLUtils.getNodeText(element));
      return XMLUtils.getNodeText(element);
    }
    System.out.println("TEXT: " + XMLUtils.getNodeText(p));
    return XMLUtils.getNodeText(p);
  }
}

