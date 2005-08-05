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
package org.aspcfs.modules.admin.actions;

import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Import;
import org.aspcfs.utils.web.RequestUtils;

import java.io.File;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Perform system level maintenance, typically setup on a cron
 *
 * @author matt rajkowski
 * @version $Id: ProcessSystem.java,v 1.14 2003/12/10 21:32:17 mrajkowski Exp
 *          $
 * @created 9/24/2002
 */
public final class ProcessSystem extends CFSModule {

  /**
   * For every cached system, the preferences are reloaded
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandReloadSystemPrefs(ActionContext context) {
    if (!allow(context)) {
      return ("PermissionError");
    }
    ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute(
        "ConnectionPool");
    Connection db = null;
    Iterator i = this.getSystemIterator(context);
    while (i.hasNext()) {
      SystemStatus systemStatus = (SystemStatus) i.next();
      try {
        db = sqlDriver.getConnection(systemStatus.getConnectionElement());
        systemStatus.buildPreferences(db);
      } catch (Exception e) {
      } finally {
        sqlDriver.free(db);
      }
    }
    return "ProcessOK";
  }


  /**
   * Removes all systems from the cache
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandClearSystems(ActionContext context) {
    if (!allow(context)) {
      return ("PermissionError");
    }
    Iterator i = this.getSystemIterator(context);
    while (i.hasNext()) {
      SystemStatus thisStatus = (SystemStatus) i.next();
      i.remove();
    }
    return "ProcessOK";
  }


  /**
   * Gets an Iterator of all SystemStatus objects that are cached
   *
   * @param context Description of the Parameter
   * @return The systemIterator value
   */
  private Iterator getSystemIterator(ActionContext context) {
    Hashtable globalStatus = (Hashtable) context.getServletContext().getAttribute(
        "SystemStatus");
    return globalStatus.values().iterator();
  }


  /**
   * For each JSP found in the context path, a URL is constructed and requested
   * so that the server compiles the JSP
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandPrecompileJSPs(ActionContext context) {
    File baseDir = new File(context.getServletContext().getRealPath("/"));
    precompileDirectory(context, baseDir, "/");
    return "ProcessOK";
  }


  /**
   * Action to begin precompiling JSPs by specifying the directory to compile
   *
   * @param context       Description of the Parameter
   * @param thisDirectory Description of the Parameter
   * @param dir           Description of the Parameter
   */
  private void precompileDirectory(ActionContext context, File thisDirectory, String dir) {
    File[] listing = thisDirectory.listFiles();
    for (int i = 0; i < listing.length; i++) {
      File thisFile = listing[i];
      if (thisFile.isDirectory()) {
        precompileDirectory(context, thisFile, dir + thisFile.getName() + "/");
      } else {
        precompileJSP(context, thisFile, dir);
      }
    }
  }


  /**
   * Method to compile a JSP by making an http request of the JSP
   *
   * @param context  Description of the Parameter
   * @param thisFile Description of the Parameter
   * @param dir      Description of the Parameter
   */
  private void precompileJSP(ActionContext context, File thisFile, String dir) {
    if (thisFile.getName().endsWith(".jsp") &&
        !thisFile.getName().endsWith("_include.jsp") &&
        !thisFile.getName().endsWith("_menu.jsp")) {
      String serverName = "http://" + RequestUtils.getServerUrl(
          context.getRequest());
      String jsp = serverName + dir + thisFile.getName();
      try {
        URL url = new URL(jsp);
        URLConnection conn = url.openConnection();
        Object result = conn.getContent();
      } catch (Exception e) {

      }
    }
  }


  /**
   * Currently only the localhost is allowed
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  private boolean allow(ActionContext context) {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("IP FROM REQUEST: " + context.getIpAddress());
    }
    try {
      if (System.getProperty("DEBUG") != null) {
        System.out.println(
            "Compare to: " + InetAddress.getLocalHost().getHostAddress());
      }
      return ("127.0.0.1".equals(context.getIpAddress()) ||
          InetAddress.getLocalHost().getHostAddress().equals(
              context.getIpAddress()));
    } catch (Exception e) {
      return false;
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public synchronized boolean deleteImportedRecords(ActionContext context) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("ProcessSystem-> Checking imports for deletion");
    }
    ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute(
        "ConnectionPool");
    Connection db = null;
    PreparedStatement pst = null;
    Iterator i = this.getSystemIterator(context);
    while (i.hasNext()) {
      SystemStatus systemStatus = (SystemStatus) i.next();
      try {
        db = sqlDriver.getConnection(systemStatus.getConnectionElement());
        if (db != null) {
          db.setAutoCommit(false);
          ResultSet rs = null;
          ArrayList imports = new ArrayList();
          pst = db.prepareStatement(
              "SELECT import_id FROM import " +
              "WHERE status_id = ? ");
          pst.setInt(1, Import.DELETED);
          rs = pst.executeQuery();
          while (rs.next()) {
            imports.add(String.valueOf(rs.getInt("import_id")));
          }
          rs.close();
          pst.close();

          Iterator j = imports.iterator();
          while (j.hasNext()) {
            int thisImportId = Integer.parseInt((String) j.next());
            if (System.getProperty("DEBUG") != null) {
              System.out.println(
                  "ProcessSystem-> Deleting import " + thisImportId);
            }
            pst = db.prepareStatement(
                "DELETE FROM contact_emailaddress " +
                "WHERE EXISTS (SELECT contact_id from contact c where c.contact_id = contact_emailaddress.contact_id AND import_id = ?) ");
            pst.setInt(1, thisImportId);
            pst.executeUpdate();
            pst.close();

            pst = db.prepareStatement(
                "DELETE FROM contact_phone " +
                "WHERE EXISTS (SELECT contact_id from contact c where c.contact_id = contact_phone.contact_id AND import_id = ?) ");
            pst.setInt(1, thisImportId);
            pst.executeUpdate();
            pst.close();

            pst = db.prepareStatement(
                "DELETE FROM contact_address " +
                "WHERE EXISTS (SELECT contact_id from contact c where c.contact_id = contact_address.contact_id AND import_id = ?) ");
            pst.setInt(1, thisImportId);
            pst.executeUpdate();
            pst.close();

            pst = db.prepareStatement(
                "DELETE FROM contact " +
                "WHERE import_id = ?");
            pst.setInt(1, thisImportId);
            pst.executeUpdate();
            pst.close();

            //check if there are any accounts to be deleted(delete account only if a new account was created while importing contacts)
            pst = db.prepareStatement(
                "DELETE FROM organization " +
                "WHERE import_id = ?");
            pst.setInt(1, thisImportId);
            pst.executeUpdate();
            pst.close();

            pst = db.prepareStatement(
                "DELETE FROM import " +
                "WHERE import_id = ?");
            pst.setInt(1, thisImportId);
            pst.executeUpdate();
            pst.close();
            db.commit();
          }
        }
      } catch (Exception e) {
        if (db != null) {
          db.rollback();
        }
      } finally {
        if (db != null) {
          db.setAutoCommit(true);
          sqlDriver.free(db);
        }
      }
    }
    return true;
  }
}

