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

/**
 *  Perform system level maintenance, typically setup on a cron
 *
 *@author     matt rajkowski
 *@created    9/24/2002
 *@version    $Id$
 */
public final class ProcessSystem extends CFSModule {

  /**
   *  For every cached system, the preferences are rebuilt
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandReloadSystemPrefs(ActionContext context) {
    ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");
    Iterator i = this.getSystemIterator(context);
    while (i.hasNext()) {
      SystemStatus systemStatus = (SystemStatus) i.next();
      Connection db = null;
      try {
        db = sqlDriver.getConnection(systemStatus.getConnectionElement());
        systemStatus.buildPreferences(db);
      } catch (SQLException e) {
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
    //TODO: Must invalidate the user references too...
    //NOTE: This doesn't work yet...
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandViewCron(ActionContext context) {
    try {
      CrontabEntryBean[] entryList = CrontabEntryDAO.getInstance().findAll();
      //ArrayList entries = (ArrayList)Arrays.asList(entryList);
      ArrayList entries = new ArrayList();
      for (int i = 0; i < entryList.length; i++) {
			  entries.add(entryList[i]);
      }
      context.getRequest().setAttribute("entries", entries);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ProcessSystem-> List OK");
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return "ViewCronOK";
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
}

