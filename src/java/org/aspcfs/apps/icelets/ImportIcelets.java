/*
 *  Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.apps.icelets;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
import org.aspcfs.modules.website.base.*;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Description of the Class
 *
 * @author kbhoopal
 * @version $Id: Exp $
 * @created February 22, 2006
 */
public class ImportIcelets {


  /**
   * Constructor for the ImportIcelets object
   */
  public ImportIcelets() {
  }


  /**
   * The main program for the ImportIcelets class
   *
   * @param args The command line arguments
   */
  public static void main(String[] args) {
    if ((args.length != 4) && (args.length != 5)) {
      System.out.println(
          "Usage: java ImportIcelets [filepath][driver][uri][user] <passwd>");
    } else {
      System.setProperty("DEBUG", "1");
      new ImportIcelets(args);
    }
    System.exit(0);
  }


  /**
   * Constructor for the ImportIcelets object
   *
   * @param args Description of the Parameter
   */
  public ImportIcelets(String[] args) {
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
        System.out.println("Reading Icelets from XML...");
      }
      HashMap iceletMap = IceletList.load(filePath);
      insertIceletList(db, iceletMap);
      sqlDriver.free(db);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(2);
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws Exception Description of the Exception
   */
  public static void insertIceletList(Connection db, HashMap iceletMap) throws Exception {
    IceletList persistantIceletList = new IceletList();
    persistantIceletList.buildList(db);
    Set iceletSet = iceletMap.keySet();
    Iterator iceletSetIterator = iceletSet.iterator();
    while (iceletSetIterator.hasNext()) {
      String key = (String) iceletSetIterator.next();
      Icelet newIcelet = (Icelet) iceletMap.get(key);
      String iceletClass = newIcelet.getConfiguratorClass();
      int version = newIcelet.getVersion();

      Iterator persistantIceletIterator = persistantIceletList.iterator();
      boolean foundIcelet = false;
      boolean foundPreviousVersion = false;
      Icelet persistantIcelet = null;
      while (persistantIceletIterator.hasNext()) {
        persistantIcelet = (Icelet) persistantIceletIterator.next();
        if (persistantIcelet.getConfiguratorClass().equals(iceletClass)) {
          foundIcelet = true;
          if (persistantIcelet.getVersion() < version) {
            foundPreviousVersion = true;
          }
          break;
        }
      }
      if (!foundIcelet) {
        //insert icelet into database
        newIcelet.setEnabled(true);
        newIcelet.insert(db);
        newIcelet.insertIceletDashboardMapList(db);
        newIcelet.insertIceletCustomTabMapList(db);
        newIcelet.insertPublicWebSiteIcelets(db);
      }
      if (foundPreviousVersion) {
        //update icelet in the database
        persistantIcelet.setName(newIcelet.getName());
        persistantIcelet.setDescription(newIcelet.getDescription());
        persistantIcelet.setConfiguratorClass(newIcelet.getConfiguratorClass());
        persistantIcelet.setEnabled(true);
        persistantIcelet.setVersion(newIcelet.getVersion());
        persistantIcelet.update(db);
        updateRowColumnProperties(db, persistantIcelet, newIcelet);
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param db               Description of the Parameter
   * @param persistantIcelet Description of the Parameter
   * @param newIcelet        Description of the Parameter
   * @throws Exception Description of the Exception
   */
  public static void updateRowColumnProperties(Connection db, Icelet persistantIcelet, Icelet newIcelet) throws Exception {
    RowColumnList rowColumnList = new RowColumnList();
    rowColumnList.setBuildIceletPropertyMap(true);
    rowColumnList.setBuildIcelet(true);
    rowColumnList.setIceletId(persistantIcelet.getId());
    rowColumnList.buildList(db);

    Iterator rowColumnIterator = rowColumnList.iterator();
    while (rowColumnIterator.hasNext()) {
      RowColumn rowColumn = (RowColumn) rowColumnIterator.next();
      if (rowColumn.getIceletId() == persistantIcelet.getId()) {

        //iceletproperties that are persistant in the database for each rowColumn
        IceletPropertyMap persistantIceletPropertyMap = rowColumn.getIceletPropertyMap();

        //iceletproperties from icelet_<LOCALE_NAME>.xml
        IceletPropertyMap iceletPropertyMap = newIcelet.getIceletPropertyMap();

        //update the icelet_property table to remove unused properties
        Set persistantIceletPropertySet = persistantIceletPropertyMap.keySet();
        Iterator persistantIceletPropertySetIterator = persistantIceletPropertySet.iterator();
        while (persistantIceletPropertySetIterator.hasNext()) {
          Integer key = (Integer) persistantIceletPropertySetIterator.next();
          if (!iceletPropertyMap.containsKey(key)) {
            IceletProperty persistantIceletProperty = (IceletProperty) persistantIceletPropertyMap.get(key);
            persistantIceletProperty.delete(db);
          }
        }

        //update the icelet_property table to add all necessary properties
        Set iceletPropertySet = iceletPropertyMap.keySet();
        Iterator iceletPropertySetIterator = iceletPropertySet.iterator();
        while (iceletPropertySetIterator.hasNext()) {
          Integer key = (Integer) iceletPropertySetIterator.next();
          IceletProperty iceletProperty = (IceletProperty) iceletPropertyMap.get(key);
          if (iceletProperty.getAutoAdd()) {
            iceletProperty.setValue(iceletProperty.getDefaultValue());
            iceletProperty.setRowColumnId(rowColumn.getId());
            iceletProperty.setEnteredBy(0);
            iceletProperty.setModifiedBy(0);
            iceletProperty.insert(db);
          }
        }
      }
    }
  }
}

