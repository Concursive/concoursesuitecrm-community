package org.aspcfs.modules.admin.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.database.ConnectionPool;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import java.util.*;
import java.io.File;
import java.sql.*;
import org.jcrontab.data.CrontabEntryBean;
import org.jcrontab.data.CrontabEntryDAO;
import java.net.*;

/**
 *  Perform system level maintenance, typically setup on a cron
 *
 *@author     matt rajkowski
 *@created    9/24/2002
 *@version    $Id: ProcessSystem.java,v 1.14 2003/12/10 21:32:17 mrajkowski Exp
 *      $
 */
public final class ProcessSystem extends CFSModule {

  /**
   *  For every cached system, the preferences are reloaded
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandReloadSystemPrefs(ActionContext context) {
    if (!allow(context)) {
      return ("PermissionError");
    }
    ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");
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
   *  Removes all systems from the cache
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
   *  For every system, invalidates and deletes the day's graphing data since
   *  the graphs need to be rebuilt daily
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandClearGraphData(ActionContext context) {
    clearGraphData(context);
    deleteGraphFiles(context);
    return "ProcessOK";
  }


  /**
   *  Deletes all of the files in graphs
   *
   *@param  context  Description of the Parameter
   */
  private void deleteGraphFiles(ActionContext context) {
    File graphFolder = new File(context.getServletContext().getRealPath("/") + "graphs" + fs);
    if (graphFolder.isDirectory()) {
      File[] files = graphFolder.listFiles();
      if (files != null) {
        for (int i = 0; i < files.length; i++) {
          File thisFile = files[i];
          thisFile.delete();
        }
      }
    }
  }


  /**
   *  Goes through each user and flags the graph data as invalid
   *
   *@param  context  Description of the Parameter
   */
  private void clearGraphData(ActionContext context) {
    UserList shortChildList = null;
    UserList fullChildList = null;
    Iterator i = this.getSystemIterator(context);
    while (i.hasNext()) {
      SystemStatus thisStatus = (SystemStatus) i.next();
      UserList thisList = thisStatus.getHierarchyList();
      Iterator j = thisList.iterator();
      while (j.hasNext()) {
        User thisUser = (User) j.next();
        shortChildList = thisUser.getShortChildList();
        fullChildList = thisUser.getFullChildList(shortChildList, new UserList());
        Iterator k = fullChildList.iterator();
        while (k.hasNext()) {
          User indUser = (User) k.next();
          indUser.setIsValid(false, true);
          indUser.setRevenueIsValid(false, true);
        }
      }
    }
  }


  /**
   *  Gets an Iterator of all SystemStatus objects that are cached
   *
   *@param  context  Description of the Parameter
   *@return          The systemIterator value
   */
  private Iterator getSystemIterator(ActionContext context) {
    Hashtable globalStatus = (Hashtable) context.getServletContext().getAttribute("SystemStatus");
    return globalStatus.values().iterator();
  }


  /**
   *  For each JSP found in the context path, a URL is constructed and requested
   *  so that the server compiles the JSP
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandPrecompileJSPs(ActionContext context) {
    File baseDir = new File(context.getServletContext().getRealPath("/"));
    precompileDirectory(context, baseDir, "/");
    return "ProcessOK";
  }


  /**
   *  Action to begin precompiling JSPs by specifying the directory to compile
   *
   *@param  context        Description of the Parameter
   *@param  thisDirectory  Description of the Parameter
   *@param  dir            Description of the Parameter
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
   *  Method to compile a JSP by making an http request of the JSP
   *
   *@param  context   Description of the Parameter
   *@param  thisFile  Description of the Parameter
   *@param  dir       Description of the Parameter
   */
  private void precompileJSP(ActionContext context, File thisFile, String dir) {
    if (thisFile.getName().endsWith(".jsp") &&
        !thisFile.getName().endsWith("_include.jsp") &&
        !thisFile.getName().endsWith("_menu.jsp")) {
      String serverName = "http://" + HTTPUtils.getServerUrl(context.getRequest());
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
   *  Currently only the localhost is allowed
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  private boolean allow(ActionContext context) {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("IP FROM REQUEST: " + context.getIpAddress());
    }
    try {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Compare to: " + InetAddress.getLocalHost().getHostAddress());
      }
      return ("127.0.0.1".equals(context.getIpAddress()) ||
          InetAddress.getLocalHost().getHostAddress().equals(context.getIpAddress()));
    } catch (Exception e) {
      return false;
    }
  }
}

