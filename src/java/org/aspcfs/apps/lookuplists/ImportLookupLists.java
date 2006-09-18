/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.apps.lookuplists;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

/**
 * Description of the Class
 *
 * @author kbhoopal
 * @version $Id: ImportLookupLists.java,v 1.1.4.3 2005/03/03 19:06:02
 *          mrajkowski Exp $ Exp $
 * @created February 18, 2005
 */
public class ImportLookupLists {
  private LookupLists lookupLists = null;
  private ArrayList completeLookupListList = null;
  private HashMap customLookupListHandlers = null;
  private ArrayList lookupListsWithCompleteTableNameAsKey = null;
  private ArrayList lookupListsWithLevelAsIsFromAttributeList = null;
  private HashMap globallyUniqueIds = null;
  private ArrayList lookupListsWithNoLevelColumn = null;
  private HashMap lookupListsWithNoDescription = null;

  public ImportLookupLists() {
  }


  /**
   * The main program for the ImportLookupLists class
   *
   * @param args The command line arguments
   */
  public static void main(String[] args) {
    if ((args.length != 4) && (args.length != 5)) {
      System.out.println(
          "Usage: java ImportLookupLists [filepath][driver][uri][user] <passwd>");
    } else {
      System.setProperty("DEBUG", "1");
      new ImportLookupLists(args);
    }
    System.exit(0);
  }


  /**
   * Constructor for the ImportLookupLists object
   *
   * @param args Description of the Parameter
   */
  public ImportLookupLists(String[] args) {
    ConnectionPool sqlDriver = null;
    try {
      sqlDriver = new ConnectionPool();
      String filePath = args[0];
      String driver = args[1];
      String uri = args[2];
      String username = args[3];
      String passwd = "";
      if (args.length == 5) {
        passwd = args[4];
      }
      sqlDriver.setForceClose(false);
      sqlDriver.setMaxConnections(5);
      //Test a single connection
      ConnectionElement thisElement = new ConnectionElement(
          uri, username, passwd);
      thisElement.setDriver(driver);
      Connection db = sqlDriver.getConnection(thisElement);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Reading Lookup Lists from XML...");
      }
      importLookups(db, filePath);
      sqlDriver.free(db);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(2);
    }
  }

  public void importLookups(Connection db, String filePath) throws Exception {
    //fetch lookup lists that need custom handling
    initializeCustomHandlers();
    lookupLists = new LookupLists();
    completeLookupListList = lookupLists.buildLookupLists(
        filePath, customLookupListHandlers, lookupListsWithNoDescription);
    globallyUniqueIds = lookupLists.getGloballyUniqueIds();
    insertLookupListList(db);
  }

  /**
   * Description of the Method
   *
   * @throws Exception Description of the Exception
   */
  private void insertLookupListList(Connection db) throws Exception {
    Iterator lookupListListIterator = completeLookupListList.iterator();
    System.out.println("\nInserting values into:");
    while (lookupListListIterator.hasNext()) {
      HashMap lookupListObjects = (HashMap) lookupListListIterator.next();
      ArrayList rowElements = (ArrayList) lookupListObjects.get("rows");
      HashMap columnTypes = (HashMap) lookupListObjects.get("attributes");
      String tableName = (String) lookupListObjects.get("tableName");
      String key = (String) lookupListObjects.get("key");
      Boolean useLevelAsIs = (Boolean) lookupListObjects.get("useLevelAsIs");
      Iterator rowElementsIterator = rowElements.iterator();
      System.out.println(tableName);
      int count = 0;
      while (rowElementsIterator.hasNext()) {
        HashMap row = (HashMap) rowElementsIterator.next();
        insertLookupListElement(
            db, tableName, key, useLevelAsIs, row, columnTypes, (++count * 10));
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param tableName    Description of the Parameter
   * @param key          Description of the Parameter
   * @param useLevelAsIs Description of the Parameter
   * @param row          Description of the Parameter
   * @param columnTypes  Description of the Parameter
   * @param level        Description of the Parameter
   * @throws Exception Description of the Exception
   */
  private void insertLookupListElement(Connection db, String tableName, String key, Boolean useLevelAsIs, HashMap row, HashMap columnTypes, int level) throws Exception {
    int dbId = -1;
    String primaryKey = ((key == null || "".equals(key)) ? "code" : key);
    StringBuffer sqlString = new StringBuffer();
    StringBuffer sqlColumnNames = new StringBuffer();
    StringBuffer sqlColumnValues = new StringBuffer();

    sqlString.append("INSERT INTO " + DatabaseUtils.getTableName(db, tableName));

    Set columnNames = row.keySet();
    Iterator columnNameIterator = columnNames.iterator();
    boolean hasColumns = false;
    boolean enabledFound = false;
    int locallyUniqueId = -1;
    String enabledValue = "";
    boolean defaultItemFound = false;
    String defaultItemValue = "";
    boolean firstColumn = true;
    while (columnNameIterator.hasNext()) {
      hasColumns = true;
      String columnName = (String) columnNameIterator.next();
      if ("enabled".equals(columnName)) {
        enabledFound = true;
        enabledValue = (String) row.get(columnName);
      } else if ("default_item".equals(columnName)) {
        defaultItemFound = true;
        defaultItemValue = (String) row.get(columnName);
      } else if ("LUID".equals(columnName)) {
        locallyUniqueId = Integer.parseInt((String) row.get(columnName));
      } else if ("level".equals(columnName)) {
        //nothing
      } else {
        sqlColumnNames.append(((!firstColumn) ? ", " : "") + (columnName.equals("type") ? DatabaseUtils.addQuotes(db, "type") : columnName));
        sqlColumnValues.append(((!firstColumn) ? ", " : "") + "?");
        firstColumn = false;
      }
    }
    if (hasColumns) {
      // Determine sequence name and pre-insert value
      String sequenceName = null;
      if (needsCompleteTableNameForKey(tableName)) {
        sequenceName = tableName + "_" + primaryKey + "_seq";
      } else {
        sequenceName = ((tableName.length() > 22) ? tableName.substring(0, 22) : tableName) + "_" + primaryKey + "_seq";
      }
      dbId = DatabaseUtils.getNextSeq(db, sequenceName);
      // Finalize the query
      if (dbId > -1) {
        sqlColumnNames.append(", " + primaryKey);
      }
      sqlColumnNames.append(
          (enabledFound ? ", enabled" : "") + (defaultItemFound ? ", default_item" : "") + (hasLevelColumn(
              tableName) ? ", " + DatabaseUtils.addQuotes(db, "level") + " " : ""));

      StringBuffer sqlColumnNamesString = new StringBuffer();
      sqlColumnNamesString.append(" ( " + sqlColumnNames.toString() + " ) ");
      if (dbId > -1) {
        sqlColumnValues.append(", ? ");
      }
      sqlColumnValues.append(
          (enabledFound ? ", ? " : "") + (defaultItemFound ? ", ? " : "") + (hasLevelColumn(
              tableName) ? ", ? " : ""));

      StringBuffer sqlColumnValuesString = new StringBuffer();
      sqlColumnNamesString.append(
          " VALUES ( " + sqlColumnValues.toString() + " ) ");

      // Insert the item
      PreparedStatement pst = db.prepareStatement(
          sqlString.toString() + sqlColumnNamesString.toString() + sqlColumnValuesString.toString());
      StringTokenizer st = new StringTokenizer(sqlColumnNames.toString(), ",");
      int i = 0;
      while (st.hasMoreTokens()) {
        String columnName = st.nextToken().trim();
        String columnType = "";
        if (("enabled".equals(columnName)) ||
            ("default_item".equals(columnName)) ||
            ("level".equals(columnName) ||  DatabaseUtils.addQuotes(db, "level").equals(columnName))) {
          if ("level".equals(columnName) || DatabaseUtils.addQuotes(db, "level").equals(columnName) && (useLevelAsIs.booleanValue() == true)) {
            try{
              if ((String) row.get("level") != null) {
                level = Integer.parseInt((String) row.get("level"));
              } else {
                level = Integer.parseInt((String) row.get(DatabaseUtils.addQuotes(db, "level")));
              }
            } catch(NumberFormatException e) {
              // TODO: NumberFormatException exception until Derby installation...
            }
          }
          continue;
        } else {
          if (DatabaseUtils.addQuotes(db, "type").equals(columnName)) {
            columnName = "type";
          }
          columnType = (String) columnTypes.get(columnName);
          if (columnName.equals(primaryKey) && dbId > -1) {
            pst.setInt(++i, dbId);
          } else if (columnType == null) {
            // if no type is specified, assume datatype as string
            pst.setString(++i, (String) row.get(columnName));
          } else if ("integer".equals(columnType)) {
            pst.setInt(++i, Integer.parseInt((String) row.get(columnName)));
          } else if ("string".equals(columnType)) {
            pst.setString(++i, (String) row.get(columnName));
          } else if ("boolean".equals(columnType)) {
            pst.setBoolean(
                ++i, (("true".equals((String) row.get(columnName))) ? true : false));
          } else if (columnType.indexOf("LUID") != -1) {
            // this column is a foreign key to another table, so fetch the id
            //of the corresponding record
            pst.setInt(
                ++i, ((Integer) globallyUniqueIds.get(
                    (String) row.get(columnName))).intValue());
          }
        }
      }
      //for "enabled" column set to false if false, else true
      if (enabledFound) {
        pst.setBoolean(++i, "false".equals(enabledValue) ? false : true);
      }

      //for "default_item" column set to true if true, else false
      if (defaultItemFound) {
        pst.setBoolean(++i, "true".equals(defaultItemValue) ? true : false);
      }

      //find if the table has a level column
      if (hasLevelColumn(tableName)) {
        pst.setInt(++i, level);
      }
      pst.execute();
      pst.close();
      dbId = DatabaseUtils.getCurrVal(db, sequenceName, dbId);
      // Cache the id for future reference when a dependent table needs to be inserted
      if (locallyUniqueId != -1) {
        globallyUniqueIds.put(
            tableName + "." + locallyUniqueId, new Integer(dbId));
      }
    }
  }


  /**
   * Initilize the tables that need Custom Handlers
   */
  private void initializeCustomHandlers() {
    customLookupListHandlers = new HashMap();
    //Look up tables that have more than one description like fields
    //or having a description like field that does not have "description" for its column name
    customLookupListHandlers.put(
        "lookup_relationship_types", "lookupRelationshipTypes");
    customLookupListHandlers.put(
        "product_option_configurator", "productOptionConfigurator");
    customLookupListHandlers.put("field_types", "fieldTypes");
    customLookupListHandlers.put("survey", "survey");
		customLookupListHandlers.put("web_layout","webLayout");
		customLookupListHandlers.put("web_style","webStyle");

    //Look up tables where the table name is more than 22 characters long,
    //but the table name is not truncated
    lookupListsWithCompleteTableNameAsKey = new ArrayList();
    lookupListsWithCompleteTableNameAsKey.add(
        "lookup_document_store_permission_category");
    lookupListsWithCompleteTableNameAsKey.add("lookup_document_store_role");
    lookupListsWithCompleteTableNameAsKey.add(
        "lookup_document_store_permission");
    lookupListsWithCompleteTableNameAsKey.add(
        "lookup_project_permission_category");
    lookupListsWithCompleteTableNameAsKey.add("lookup_project_permission");
    lookupListsWithCompleteTableNameAsKey.add("lookup_orderaddress_types");
    lookupListsWithCompleteTableNameAsKey.add("lookup_relationship_types");
    lookupListsWithCompleteTableNameAsKey.add("product_option_configurator");

    //Look tables where the level column maps to a constant in the
    //the code. The value for the level column are specified in the
    //the xml file. The level column for these lookups DOES NOT
    //default to the order of appearance of the records.
    lookupListsWithLevelAsIsFromAttributeList = new ArrayList();
    lookupListsWithLevelAsIsFromAttributeList.add(
        "lookup_document_store_role");
    lookupListsWithLevelAsIsFromAttributeList.add("lookup_project_role");

    //These lookup tables do not have a level column
    lookupListsWithNoLevelColumn = new ArrayList();
    lookupListsWithNoLevelColumn.add("field_types");
    lookupListsWithNoLevelColumn.add("product_option_configurator");
    lookupListsWithNoLevelColumn.add("search_fields");
    lookupListsWithNoLevelColumn.add("survey");
    lookupListsWithNoLevelColumn.add("action_plan_constants");
    lookupListsWithNoLevelColumn.add("step_action_map");
		lookupListsWithNoLevelColumn.add("web_layout");
		lookupListsWithNoLevelColumn.add("web_style");
		
    lookupListsWithNoDescription = new HashMap();
    lookupListsWithNoDescription.put("step_action_map", "step_action_map");
  }


  /**
   * Determines if the complete table name is required for the primary key
   *
   * @param tableName Description of the Parameter
   * @return Description of the Return Value
   */
  private boolean needsCompleteTableNameForKey(String tableName) {
    Iterator tableNameIterator = lookupListsWithCompleteTableNameAsKey.iterator();
    while (tableNameIterator.hasNext()) {
      String tmpTableName = (String) tableNameIterator.next();
      if (tableName.equals(tmpTableName)) {
        return true;
      }
    }
    return false;
  }


  /**
   * Determines if the table has a level column
   *
   * @param tableName Description of the Parameter
   * @return Description of the Return Value
   */
  private boolean hasLevelColumn(String tableName) {
    Iterator tableNameIterator = lookupListsWithNoLevelColumn.iterator();
    while (tableNameIterator.hasNext()) {
      String tmpTableName = (String) tableNameIterator.next();
      if (tableName.equals(tmpTableName)) {
        return false;
      }
    }
    return true;
  }
}
