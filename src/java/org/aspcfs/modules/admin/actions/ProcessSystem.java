package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import com.darkhorseventures.cfsmodule.CFSModule;
import com.darkhorseventures.controller.*;
import com.darkhorseventures.cfsbase.*;
import java.util.*;
import java.io.File;

/** Perform system level maintenance, typically setup on a cron
 * @author matt rajkowski
 * @created 9/24/2002
 * @version $Id$
 */
public final class ProcessSystem extends CFSModule {
  /** Erases the day's graphing data since the graphs need to be rebuilt daily */  
  public String executeCommandClearGraphData(ActionContext context) {
    clearGraphData(context);
    deleteGraphFiles(context);
    return null;
  }
  
  /** Deletes all of the files in graphs should be run each night */  
  private void deleteGraphFiles(ActionContext context) {
    File graphFolder = new File(context.getServletContext().getRealPath("/") + "graphs" + fs);
    if (graphFolder.isDirectory()) {
      File[] files = graphFolder.listFiles();
      if (files != null) {
        for (int i = 0; i<files.length; i++) {
          File thisFile = files[i];
          thisFile.delete();
        }
      }
    }
  }

  /** Goes through each user and flags the graph data as invalid */  
  private void clearGraphData(ActionContext context) {
    Hashtable globalStatus = (Hashtable)context.getServletContext().getAttribute("SystemStatus");

    Iterator i = globalStatus.values().iterator();
    UserList shortChildList = new UserList();
    UserList fullChildList = new UserList();

    while (i.hasNext()) {
      SystemStatus thisStatus = (SystemStatus)i.next();

      UserList thisList = thisStatus.getHierarchyList();
      Iterator j = thisList.iterator();
      while (j.hasNext()) {
        User thisUser = (User)j.next();
        shortChildList = thisUser.getShortChildList();
        fullChildList = thisUser.getFullChildList(shortChildList, new UserList());

        Iterator k = fullChildList.iterator();
        while (k.hasNext()) {
          User indUser = (User)k.next();
          indUser.setIsValid(false,true);
        }
      }
    }
  }
}