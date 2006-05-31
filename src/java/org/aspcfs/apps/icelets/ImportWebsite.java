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
import org.aspcfs.modules.website.utils.SiteImporter;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.XMLUtils;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    March 1, 2006
 *@version    $Id: Exp $
 */
public class ImportWebsite {


  /**
   *  Constructor for the ImportWebsite object
   */
  public ImportWebsite() { }


  /**
   *  The main program for the ImportWebsite class
   *
   *@param  args  The command line arguments
   */
  public static void main(String[] args) {
    if ((args.length != 5) && (args.length != 6)) {
      System.out.println(
          "Usage: java ImportWebsite [filepath][webapp path][driver][uri][user] <passwd>");
    } else {
      System.setProperty("DEBUG", "1");
      new ImportWebsite(args);
    }
    System.exit(0);
  }


  /**
   *  Constructor for the ImportWebsite object
   *
   *@param  args  Description of the Parameter
   */
  public ImportWebsite(String[] args) {
    ConnectionPool sqlDriver = null;
    try {
      sqlDriver = new ConnectionPool();
      String filePath = args[0];
      String webappPath = args[1];
      String driver = args[2];
      String uri = args[3];
      String username = args[4];
      String passwd = "";
      if (args.length == 6) {
        passwd = args[5];
      }
      sqlDriver.setForceClose(false);
      sqlDriver.setMaxConnections(5);
      //Test a single connection
      ConnectionElement thisElement = new ConnectionElement(
          uri, username, passwd);
      thisElement.setDriver(driver);
      Connection db = sqlDriver.getConnection(thisElement);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Reading website from ZIP...");
      }
      new SiteImporter(filePath, webappPath, db);
      sqlDriver.free(db);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(2);
    }
  }
}

