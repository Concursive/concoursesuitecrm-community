/**
 *  This file is part of the jcrontab package Copyright (C) 2001-2002 Israel
 *  Olalla This library is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or (at your
 *  option) any later version. This library is distributed in the hope that it
 *  will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 *  of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 *  General Public License for more details. You should have received a copy of
 *  the GNU Lesser General Public License along with this library; if not, write
 *  to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 *  MA 02111-1307, USA For questions, suggestions: iolalla@yahoo.com
 */
package org.jcrontab;

import org.aspcfs.utils.Dictionary;
import org.jcrontab.data.HoliDay;
import org.jcrontab.data.HoliDayFactory;
import org.jcrontab.log.Log;

import javax.servlet.ServletContext;
import java.io.*;
import java.sql.Connection;
import java.util.*;

/**
 * Manages the creation and execution of all the scheduled tasks of jcrontab.
 * This class is the core of the jcrontab
 *
 * @author Israel Olalla
 * @version $Id$
 * @created November 2002
 */

public class Crontab {
  private HashMap tasks;
  private HashMap loadedClasses;
  private int iNextTaskID;
  private Properties prop = new Properties();
  private int iTimeTableGenerationFrec = 3;
  private Object connectionPool = null;
  private ServletContext servletContext = null;
  private Dictionary dictionary = null;

  /**
   * The Cron that controls the execution of the tasks
   */
  private Cron cron;
  private boolean stopping = false;
  private boolean daemon = true;

  private static String strFileName = System.getProperty("user.home") +
      System.getProperty("file.separator") +
      ".jcrontab" +
      System.getProperty("file.separator") +
      "jcrontab.properties";
  private boolean isInternalConfig = true;
  /**
   * The only instance of this cache
   */
  private static Crontab singleton = null;


  /**
   * Crontab constructor Change the default constructor to public if you need
   * more than an instance running on the system
   */
  private Crontab() {
    tasks = new HashMap();
    loadedClasses = new HashMap();
    iNextTaskID = 1;
  }


  /**
   * Returns the only instance of this class we've choosen a singleton pattern
   * to avoid launch different Crontab If you need diferent crontab classes to
   * be launched only should Change the private constructor to public.
   *
   * @return singleton the only instance of this class
   */
  public static synchronized Crontab getInstance() {
    if (singleton == null) {
      singleton = new Crontab();
    }
    return singleton;
  }


  /**
   * Initializes the crontab, reading task table from configuration file
   *
   * @throws Exception
   */
  public void init() throws Exception {
    // Properties prop = new Properties
    // Creates the thread Cron, wich generates the engine events
    loadConfig();
    cron = new Cron(this, iTimeTableGenerationFrec);
    cron.setName("Cron");
    cron.setDaemon(daemon);
    cron.start();
    stopping = false;
  }


  /**
   * Initializes the crontab, reading task table from configuration file
   *
   * @param strFileName Name of the tasks configuration file
   * @throws Exception
   */
  public void init(String strFileName)
      throws Exception {

    this.strFileName = strFileName;
    loadConfig();
    String refreshFrequency =
        getProperty("org.jcrontab.Crontab.refreshFrequency");
    if (refreshFrequency != null) {
      this.iTimeTableGenerationFrec = Integer.parseInt(refreshFrequency);
    }
    // Creates the thread Cron, wich generates the engine events
    cron = new Cron(this, iTimeTableGenerationFrec);
    isInternalConfig = true;
    cron.setName("Cron");
    cron.setDaemon(daemon);
    cron.start();
    stopping = false;
  }


  /**
   * Used by the loadCrontabServlet to start Crontab with the configuration
   * passed in a Properties object.
   *
   * @param props a <code>Properties</code> object
   * @throws Exception
   */
  public void init(Properties props)
      throws Exception {
    this.strFileName = null;
    String refreshFrequency =
        props.getProperty("org.jcrontab.Crontab.refreshFrequency");
    this.prop = props;
    if (refreshFrequency != null) {
      this.iTimeTableGenerationFrec = Integer.parseInt(refreshFrequency);
    }
    
    //Populate the dictionary
    String fs = System.getProperty("file.separator");
    String languagePath = getProperty("org.jcrontab.path.DefaultFilePath") + ".." + fs + "languages" + fs;
    String systemLanguage = getProperty("org.jcrontab.data.SystemLanguage");
    dictionary = new Dictionary(languagePath, "en_US");
    if (systemLanguage != null) {
      if (!"en_US".equals(systemLanguage)) {
        //Override the text with a selected language
        dictionary.load(languagePath, systemLanguage);
      }
    }
        
    // Creates the thread Cron, wich generates the engine events
    cron = new Cron(this, iTimeTableGenerationFrec);
    cron.setName("Cron");
    cron.setDaemon(daemon);
    cron.start();
    stopping = false;
  }


  /**
   * UnInitializes the Crontab. Calls to the method stopInTheNextMinute() of
   * the Cron.
   */
  public void uninit() {
    if (stopping) {
      return;
    }
    stopping = true;
    cron.stopInTheNextMinute();
  }


  /**
   * UnInitializes the crontab. Calls to the method join() of each of the tasks
   * running.
   *
   * @param iSecondsToWait Number of seconds to wait for the tasks to end their
   *                       process before returning from this method
   */
  public void uninit(int iSecondsToWait) {
    if (stopping) {
      return;
    }
    try {
      // Updates uninitializing flag
      stopping = true;
      cron.stopInTheNextMinute();
      CronTask[] tasks = getAllTasks();
      for (int i = tasks.length - 1; i >= 0; i--) {
        tasks[i].join(iSecondsToWait);
      }
    } catch (InterruptedException e) {
      Log.error(e.toString(), e);
    }
    connectionPool = null;
    servletContext = null;
    //NOTE: even though the tasks are done, waits for the cron thread to finish
    try {
      cron.join(iSecondsToWait);
    } catch (Exception e) {
    }
  }


  /**
   * This method sets the Cron to daemon or not
   *
   * @param daemon The new daemon value
   */
  public void setDaemon(boolean daemon) {
    this.daemon = daemon;
  }


  /**
   * This method loads the config for the whole Crontab. If this method doesn't
   * find the files creates itself them
   *
   * @throws Exception
   */
  private void loadConfig() throws Exception {
    // Get the Params from the config File
    // Don't like those three lines. But are the only way i have to grant
    // It works in any O.S.
    if (strFileName.indexOf("\\") != -1) {
      strFileName = strFileName.replace('\\', '/');
    }
    try {
      File filez = new File(strFileName);
      FileInputStream input = new FileInputStream(filez);
      prop.load(input);
      input.close();

      for (Enumeration e = prop.propertyNames(); e.hasMoreElements();) {
        String ss = (String) e.nextElement();
        Log.debug(ss + " : " + prop.getProperty(ss));
      }

    } catch (FileNotFoundException fnfe) {
      if (isInternalConfig) {
        org.jcrontab.data.DefaultFiles.createJcrontabDir();
        org.jcrontab.data.DefaultFiles.createCrontabFile();
        org.jcrontab.data.DefaultFiles.createPropertiesFile();
        loadConfig();
      } else {
        throw new FileNotFoundException(
            "Unable to find: " +
            strFileName);
      }
    }
  }


  /**
   * Sets the connectionPool attribute of the Crontab object
   *
   * @param tmp The new connectionPool value
   */
  public void setConnectionPool(Object tmp) {
    this.connectionPool = tmp;
  }


  /**
   * Sets the servletContext attribute of the Crontab object
   *
   * @param tmp The new servletContext value
   */
  public void setServletContext(ServletContext tmp) {
    this.servletContext = tmp;
  }


  /**
   * Gets the connectionPool attribute of the Crontab object
   *
   * @return The connectionPool value
   */
  public Object getConnectionPool() {
    return connectionPool;
  }


  /**
   * Gets the servletContext attribute of the Crontab object
   *
   * @return The servletContext value
   */
  public ServletContext getServletContext() {
    return servletContext;
  }


  /**
   * This method gets the value of the given property
   *
   * @param property
   * @return value
   */
  public String getProperty(String property) {
    return prop.getProperty(property);
  }


  /**
   * This method sets the given property
   *
   * @param property
   * @param value
   */
  public void setProperty(String property, String value) {
    prop.setProperty(property, value);
  }


  /**
   * This method Stores in the properties File the given property and all the
   * "live" properties
   *
   * @param property
   * @param value
   */
  public void storeProperty(String property, String value) {
    prop.setProperty(property, value);
    try {
      File filez = new File(strFileName);
      filez.delete();
      OutputStream out = new FileOutputStream(filez);
      prop.store(out, "Jcrontab Automatic Properties");
    } catch (Exception e) {
      Log.error(e.toString(), e);
    }
  }


  /**
   * This method says if today is a holiday or not
   *
   * @return true if today is holiday false otherWise
   * @throws Exception
   */
  public boolean isHoliday() throws Exception {
    if (getProperty("org.jcrontab.data.holidaysource") == null
        || getProperty("org.jcrontab.data.holidaysource") == "") {
      return false;
    }
    Calendar today = Calendar.getInstance();
    Calendar holiday = Calendar.getInstance();
    HoliDay[] holidays = HoliDayFactory.getInstance().findAll();
    for (int i = 0; i < holidays.length; i++) {
      holiday.setTime(holidays[i].getDate());
      if (holiday.get(Calendar.DAY_OF_MONTH) == today.get(
          Calendar.DAY_OF_MONTH) &&
          holiday.get(Calendar.MONTH) == today.get(Calendar.MONTH)) {
        return true;
      }
    }
    return false;
  }


  /**
   * Creates and runs a new task
   *
   * @param strClassName      Name of the task
   * @param strMethodName     Name of the method that will be called
   * @param strExtraInfo      Extra Information given to the task
   * @param connectionContext Description of the Parameter
   * @return The identifier of the new task created, or -1 if
   *         could not create the new task (maximum number of tasks exceeded or
   *         another error)
   */
  public synchronized int newTask(String strClassName,
                                  String strMethodName, String[] strExtraInfo, Object connectionContext) {
    // Do not run new tasks if jcron is uninitializing
    if (stopping) {
      return -1;
    }
    if (connectionContext != null) {
      //This task is running within the CFS framework and will have access to the
      //SystemStatus object during execution
      if (servletContext != null && connectionPool != null) {
        //Get the corresponding connection element for this event
        com.darkhorseventures.database.ConnectionElement ce =
            (com.darkhorseventures.database.ConnectionElement) connectionContext;
        com.darkhorseventures.database.ConnectionPool cp =
            (com.darkhorseventures.database.ConnectionPool) connectionPool;
        //Get the corresponding system status for this connection element
        Connection db = null;
        org.aspcfs.controller.SystemStatus thisSystem = null;
        try {
          db = cp.getConnection(ce);
          thisSystem = org.aspcfs.controller.SecurityHook.retrieveSystemStatus(
              servletContext, db, ce);
        } catch (Exception e) {
        } finally {
          if (db != null) {
            cp.free(db);
          }
          if (thisSystem != null) {
            thisSystem.processEvent(servletContext, strClassName, cp, ce);
          }
        }
      }
    } else {
      //This task is being executed directly and does not have access to the
      //servlet context
      CronTask newTask;
      Class cl;
      int iTaskID;
      String params = "";
      try {
        iTaskID = iNextTaskID;
        cl = (Class) (loadedClasses.get(strClassName));
        // Creates the new task
        newTask = new CronTask();
        //See if any text substitution is necessary for this task
        if (strExtraInfo != null && strExtraInfo.length > 0) {
          for (int i = 0; i < strExtraInfo.length; i++) {
            //Perform substitutions org.jcrontab.path.DefaultFilePath
            if (strExtraInfo[i].indexOf("${FILEPATH}") > -1) {
              strExtraInfo[i] = replace(
                  strExtraInfo[i], "${FILEPATH}",
                  getProperty("org.jcrontab.path.DefaultFilePath"));
            }
            if (strExtraInfo[i].indexOf("${WEBSERVER.URL}") > -1) {
              strExtraInfo[i] = replace(
                  strExtraInfo[i], "${WEBSERVER.URL}",
                  getProperty("org.jcrontab.path.WebServerUrl"));
            }
          }
        }
        newTask.setDictionary(dictionary);
        newTask.setParams(
            this, iTaskID, strClassName, strMethodName,
            strExtraInfo);
        // Added name to newTask to show a name instead of Threads when
        // logging.  Thanks to Sander Verbruggen
        int lastDot = strClassName.lastIndexOf(".");
        if (lastDot > 0 && lastDot < strClassName.length()) {
          String classOnlyName = strClassName.substring(lastDot + 1);
          newTask.setName(classOnlyName);
        }
        synchronized (tasks) {
          tasks.put(
              new Integer(iTaskID),
              new TaskTableEntry(strClassName, newTask));
        }
        // Starts the task execution
        newTask.setName("Crontask-" + iTaskID);
        newTask.start();
        if (strExtraInfo != null && strExtraInfo.length > 0) {
          for (int i = 0; i < strExtraInfo.length; i++) {
            params += strExtraInfo[i] + " ";
          }
        }
        Log.info(strClassName + "#" + strMethodName + " " + params);
        // Increments the next task identifier
        iNextTaskID++;
        return iTaskID;
      } catch (Exception e) {
        Log.error(
            "Something was wrong with" +
            strClassName +
            "#" +
            strMethodName +
            " " +
            params, e);
      }
    }
    return -1;
  }


  /**
   * Removes a task from the internal arrays of active tasks. This method is
   * called from method run() of CronTask when a task has finished.
   *
   * @param iTaskID Identifier of the task to delete
   * @return true if the task was deleted correctly, false otherwise
   */
  public boolean deleteTask(int iTaskID) {
    synchronized (tasks) {
      if (tasks.remove(new Integer(iTaskID)) == null) {
        return false;
      }
      return true;
    }
  }


  /**
   * Returns an array with all active tasks
   *
   * @return An array with all active tasks NOTE: Does not returns the
   *         internal array because it is synchronized, returns a copy of it.
   */
  public CronTask[] getAllTasks() {
    CronTask[] t;
    synchronized (tasks) {
      int i = 0;
      t = new CronTask[tasks.size()];
      Iterator iter = tasks.values().iterator();
      while (iter.hasNext()) {
        t[i] = ((TaskTableEntry) (iter.next())).task;
        i++;
      }
    }
    return t;
  }


  /**
   * Helper method to replace a string with another string in a string
   *
   * @param str Description of the Parameter
   * @param o   Description of the Parameter
   * @param n   Description of the Parameter
   * @return Description of the Return Value
   */
  public static String replace(String str, String o, String n) {
    boolean all = true;
    if (str != null && o != null && o.length() > 0 && n != null) {
      StringBuffer result = null;
      int oldpos = 0;
      do {
        int pos = str.indexOf(o, oldpos);
        if (pos < 0) {
          break;
        }
        if (result == null) {
          result = new StringBuffer();
        }
        result.append(str.substring(oldpos, pos));
        result.append(n);
        pos += o.length();
        oldpos = pos;
      } while (all);
      if (oldpos == 0) {
        return str;
      } else {
        result.append(str.substring(oldpos));
        return result.toString();
      }
    } else {
      return str;
    }
  }


  /**
   * Internal class that represents an entry in the task table
   *
   * @author Israel Olalla
   * @version $Id$
   * @created November 2002
   */
  private class TaskTableEntry {
    String strClassName;
    CronTask task;


    /**
     * Constructor of an entry of the task table
     *
     * @param strClassName Name of the class of the task
     * @param task         Reference to the task
     */
    public TaskTableEntry(String strClassName,
                          CronTask task) {
      this.strClassName = strClassName;
      this.task = task;
    }
  }
}

