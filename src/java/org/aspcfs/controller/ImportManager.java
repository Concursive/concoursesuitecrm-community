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
package org.aspcfs.controller;

import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.database.ConnectionElement;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import java.lang.reflect.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.ObjectUtils;
import org.aspcfs.modules.base.Import;

/**
 *  Facilitates managing imports
 *
 *@author     Mathur
 *@created    April 26, 2004
 *@version    $id:exp$
 */
public class ImportManager {
  private Vector available = new Vector();
  private Vector busy = new Vector();
  private int maxItems = 2;
  private ConnectionPool connectionPool = null;


  /**
   *  Constructor for the ImportManager object
   */
  public ImportManager() { }


  /**
   *  Constructor for the ImportManager object
   *
   *@param  connectionPool  Description of the Parameter
   */
  public ImportManager(ConnectionPool connectionPool) {
    this.connectionPool = connectionPool;
  }


  /**
   *  Constructor for the ImportManager object
   *
   *@param  connectionPool  Description of the Parameter
   *@param  maxItems        Description of the Parameter
   */
  public ImportManager(ConnectionPool connectionPool, int maxItems) {
    this.connectionPool = connectionPool;
    this.maxItems = maxItems;
  }


  /**
   *  Sets the available attribute of the DemoManager object
   *
   *@param  tmp  The new available value
   */
  public void setAvailable(Vector tmp) {
    this.available = tmp;
  }


  /**
   *  Sets the busy attribute of the DemoManager object
   *
   *@param  tmp  The new busy value
   */
  public void setBusy(Vector tmp) {
    this.busy = tmp;
  }


  /**
   *  Sets the maxItems attribute of the DemoManager object
   *
   *@param  tmp  The new maxItems value
   */
  public void setMaxItems(int tmp) {
    this.maxItems = tmp;
  }


  /**
   *  Sets the maxItems attribute of the DemoManager object
   *
   *@param  tmp  The new maxItems value
   */
  public void setMaxItems(String tmp) {
    this.maxItems = Integer.parseInt(tmp);
  }


  /**
   *  Sets the connectionPool attribute of the DemoManager object
   *
   *@param  tmp  The new connectionPool value
   */
  public void setConnectionPool(ConnectionPool tmp) {
    this.connectionPool = tmp;
  }


  /**
   *  Gets the available attribute of the DemoManager object
   *
   *@return    The available value
   */
  public Vector getAvailable() {
    return available;
  }


  /**
   *  Gets the busy attribute of the DemoManager object
   *
   *@return    The busy value
   */
  public Vector getBusy() {
    return busy;
  }


  /**
   *  Gets the maxItems attribute of the DemoManager object
   *
   *@return    The maxItems value
   */
  public int getMaxItems() {
    return maxItems;
  }


  /**
   *  Gets the connectionPool attribute of the DemoManager object
   *
   *@return    The connectionPool value
   */
  public ConnectionPool getConnectionPool() {
    return connectionPool;
  }


  /**
   *  Gets the running attribute of the ImportManager object
   *
   *@param  importId  Description of the Parameter
   *@return           The running value
   */
  public boolean isRunning(int importId) {
    return getImport(importId) != null ? true : false;
  }


  /**
   *  Add an import to the queue for processing
   *
   *@param  thisImport  Description of the Parameter
   *@return             Description of the Return Value
   */
  public boolean add(Object thisImport) {
    //add import if not already in queue/running
    Import importObj = (Import) thisImport;
    if (!isRunning(importObj.getId())) {
      process(thisImport, 0);
    } else {
      return false;
    }
    return true;
  }


  /**
   *  Process the import
   *
   *@param  thisImport  Description of the Parameter
   *@param  status      Description of the Parameter
   */
  public synchronized void process(Object thisImport, int status) {
    try {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ImportManager -> Processing Import \"" + ((Import) thisImport).getName() + "\"");
      }
      //check thread limit
      if (busy.size() < maxItems) {
        //set the manager
        ObjectUtils.setParam(thisImport, "manager", this);

        //start the import
        Method method = thisImport.getClass().getMethod("start", null);
        method.invoke(thisImport, null);

        //change status to running
        ((Import) thisImport).setStatusId(Import.RUNNING);

        busy.add(thisImport);

        //remove from available if processing import from the queue
        if (status == 1) {
          available.remove(thisImport);
        }
      } else {
        if (status == 0) {
          //queue the import
          available.add(thisImport);

          //change status to queued
          ((Import) thisImport).setStatusId(Import.QUEUED);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   *  Get a database connection from the pool
   *
   *@param  ce                Description of the Parameter
   *@return                   The connection value
   *@exception  SQLException  Description of the Exception
   */
  public Connection getConnection(ConnectionElement ce) throws SQLException {
    if (ce != null) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ImportManager-> got ce clone");
      }
      ConnectionElement thisElement = (ConnectionElement) ce.clone();
      thisElement.setAllowCloseOnIdle(false);
      return connectionPool.getConnection(thisElement);
    }
    return null;
  }


  /**
   *  Free the database connection
   *
   *@param  db  Description of the Parameter
   */
  public void free(Connection db) {
    if (db != null) {
      connectionPool.free(db);
    }
  }


  /**
   *  Free this import and process the next one in queue
   *
   *@param  thisImport  Description of the Parameter
   */
  public void free(Object thisImport) {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("ImportManager -> Completed Import " + ((Import) thisImport).getId());
    }
    if (thisImport != null) {
      busy.remove(thisImport);
      thisImport = null;
    }

    processNextImport();
  }


  /**
   *  Processes the next available import in queue, if any
   */
  public void processNextImport() {
    //check if there are any more imports to process
    if (available.size() > 0) {
      //use first come first served
      process(available.firstElement(), 1);
    }
  }


  /**
   *  Cancel the import with specified id
   *
   *@param  importId  Description of the Parameter
   *@return           Description of the Return Value
   */
  public boolean cancel(int importId) {
    Object importClass = getImport(importId);
    try {
      if (importClass != null) {

        synchronized (this) {
          //first check if it is queued. If so, remove from queue and exit
          if (((Import) importClass).getStatusId() == Import.QUEUED) {
            available.remove(importClass);
            processNextImport();
          } else {
            Method method = importClass.getClass().getMethod("cancel", null);
            method.invoke(importClass, null);
          }
        }
        return true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }


  /**
   *  Retrieves the import, if one exists, from the list of running/queued
   *  imports
   *
   *@param  importId  Description of the Parameter
   *@return           The import value
   */
  public Object getImport(int importId) {
    Object thisImport = null;
    boolean found = false;

    //check queued impports
    Iterator i = available.iterator();
    while (i.hasNext() && !found) {
      Object tmpObj = (Import) i.next();
      Import tmp = (Import) tmpObj;
      if (tmp.getId() == importId) {
        thisImport = tmpObj;
        found = true;
      }
    }

    //check running impports
    if (!found) {
      Iterator j = busy.iterator();
      while (j.hasNext() && !found) {
        Object tmpObj = (Import) j.next();
        Import tmp = (Import) tmpObj;
        if (tmp.getId() == importId) {
          thisImport = tmpObj;
          found = true;
        }
      }
    }
    return thisImport;
  }

}

