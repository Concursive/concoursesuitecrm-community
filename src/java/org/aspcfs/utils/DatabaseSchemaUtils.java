/*
 *  Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.apps.transfer.reader.cfsdatabasereader.Property;
import org.aspcfs.apps.transfer.reader.cfsdatabasereader.PropertyMap;
import org.aspcfs.apps.transfer.reader.cfsdatabasereader.PropertyMapList;

/**
 *  Provides utility methods for database schema definition and related
 *  operations
 *
 * @author     Ananth
 * @created    March 16, 2007
 */
public class DatabaseSchemaUtils {

  /**
   *  Adds a feature to the PrimaryKey attribute of the DatabaseSchemaUtils
   *  object
   *
   * @param  db                The feature to be added to the PrimaryKey
   *      attribute
   * @param  primaryKey        The feature to be added to the PrimaryKey
   *      attribute
   * @param  dbTable           The feature to be added to the PrimaryKey
   *      attribute
   * @exception  SQLException  Description of the Exception
   */
  public static void addPrimaryKey(Connection db, DatabaseTable dbTable, String primaryKey) throws SQLException {
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }

      //Create a clone 'y' of the table 'x' specified
      DatabaseTable clone = new DatabaseTable(dbTable);
      clone.setTableName(dbTable.getTableName() + "_clone");
      clone.setSequenceName("");
      clone.create(db);

      System.out.println("Inserting into clone");
      //Move data from 'x' into its clone 'y'
      dbTable.selectInto(db, clone);

      //Delete data from 'x' and drop table 'x'. No need to drop the sequence since the table has no primary key
      System.out.println("Dropping dbTable");
      dbTable.drop(db, false);

      System.out.println("Creating dbTable");
      //Create a new 'x' with a primary key column
      DatabaseColumn column = new DatabaseColumn(primaryKey, java.sql.Types.INTEGER);
      column.setIsPrimaryKey(true);
      dbTable.addColumn(0, column);
      dbTable.create(db);

      System.out.println("Inserting back into dbTable");
      //Move data from 'y' into the new 'x'
      clone.selectInto(db, dbTable);

      //Delete data from 'y' and drop clone 'y'. No need to drop the sequence since the clone has no primary key
      System.out.println("Dropping clone");
      clone.drop(db, false);

      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace(System.out);
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
  }


  /**
   *  Adds a feature to the PrimaryKey attribute of the DatabaseSchemaUtils
   *  class
   *
   * @param  db                The feature to be added to the PrimaryKey
   *      attribute
   * @param  primaryKeys       The feature to be added to the PrimaryKey
   *      attribute
   * @exception  SQLException  Description of the Exception
   */
  public static void addPrimaryKey(Connection db, PropertyMapList mappings, HashMap primaryKeys) throws SQLException {
    Iterator i = primaryKeys.keySet().iterator();
    while (i.hasNext()) {
      String elementName = (String) i.next();
      PropertyMap thisMap = (PropertyMap) mappings.getMap(elementName);
      if (thisMap != null) {
        System.out.println("\nElement name: " + elementName);
        DatabaseTable dbTable = new DatabaseTable(
            thisMap.getTable(),
            thisMap.getSequence());
        dbTable.setDbType(DatabaseUtils.getType(db));

        Iterator properties = thisMap.iterator();
        while (properties.hasNext()) {
          Property thisProperty = (Property) properties.next();
          if (!thisProperty.getField().equals(thisMap.getUniqueField())) {
            DatabaseColumn column = new DatabaseColumn(
                thisProperty.getField(),
                thisProperty.getSqlType());
            if (thisProperty.hasLookupValue()) {
              PropertyMap lookupMap = (PropertyMap) mappings.getMap(thisProperty.getLookupValue());
              column.setReferenceTable(lookupMap.getTable());
              column.setReferenceColumn(lookupMap.getUniqueField());
            }
            column.setAllowsNull(thisProperty.allowsNull());
            if (thisProperty.getSize() != null) {
              column.setSize(thisProperty.getSize());
            }
            if (thisProperty.hasDefaultValue()) {
              column.setDefaultValue(thisProperty.getDefaultValue());
            }
            //print(column.toString());
            dbTable.addColumn(column);
          }
        }
        System.out.println("Adding primary key...");
        DatabaseSchemaUtils.addPrimaryKey(
            db, dbTable, (String) primaryKeys.get(elementName));
      }
    }
  }
}

