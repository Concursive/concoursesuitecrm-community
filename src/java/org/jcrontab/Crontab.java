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

import java.io.*;
import java.util.*;
import org.jcrontab.data.*;
import org.jcrontab.log.Log;

/**
 *  Manages the creation and execution of all the scheduled tasks of jcrontab.
 *  This class is the core of the jcrontab
 *
 *@author     $Author$
 *@created    February 4, 2003
 *@version    $Revision$
 */

public class Crontab {
  private HashMap tasks;
  private HashMap loadedClasses;
  private int iNextTaskID;
  private Properties prop = new Properties();
  private int iTimeTableGenerationFrec = 3;
  /**
   *  The Cron that controls the execution of the tasks
   */
  private Cron cron;
  private boolean stoping = false;
  private boolean daemon = true;

  private static String strFileName = System.getProperty("user.home") +
      System.getProperty("file.separator") +
      ".jcrontab" +
      System.getProperty("file.separator") +
      "jcrontab.properties";
  private boolean isInternalConfig = true;
  /**
   *  The only instance of this cache
   */
  private static Crontab singleton = null;


  /**
   *  Crontab constructor Change the default constructor to public if you need
   *  more than an instance running on the system
   */
  private Crontab() {
    tasks = new HashMap();
    loadedClasses = new HashMap();
    iNextTaskID = 1;
  }


  /**
   *  Returns the only instance of this class we've choosen a singleton pattern
   *  to avoid launch different Crontab If you need diferent crontab classes to
   *  be launched only should Change the private constructor to public.
   *
   *@return    singleton the only instance of this class
   */
  public static synchronized Crontab getInstance() {
    if (singleton == null) {
      singleton = new Crontab();
    }
    return singleton;
  }


  /**
   *  Initializes the crontab, reading task table from configuration file
   *
   *@throws  Exception
   */
  public void init() throws Exception {
    // Properties prop = new Properties
    // Creates the thread Cron, wich generates the engine events
    loadConfig();
    cron = new Cron(this, iTimeTableGenerationFrec);
    cron.setName("Cron");
    cron.setDaemon(daemon);
    cron.start();
    stoping = false;
  }


  /**
   *  Initializes the crontab, reading task table from configuration file
   *
   *@param  strFileName               Name of the tasks configuration file
   *@throws  Exception
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
    stoping = false;
  }


  /**
   *  Used by the loadCrontabServlet to start Crontab with the configuration
   *  passed in a Properties object.
   *
   *@param  props                     a <code>Properties</code> object
   *@throws  Exception
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
    // Creates the thread Cron, wich generates the engine events
    cron = new Cron(this, iTimeTableGenerationFrec);
    cron.setName("Cron");
    cron.setDaemon(daemon);
    cron.start();
    stoping = false;
  }


  /**
   *  UnInitializes the Crontab. Calls to the method stopInTheNextMinute() of
   *  the Cron.
   *
   */
  public void uninit() {
    if (stoping) {
      return;
    }
    stoping = true;
    cron.stopInTheNextMinute();
  }


  /**
   *  UnInitializes the crontab. Calls to the method join() of each of the tasks
   *  running.
   *
   *@param  iSecondsToWait  Number of seconds to wait for the tasks to end their
   *      process before returning from this method
   */
  public void uninit(int iSecondsToWait) {
    if (stoping) {
      return;
    }
    try {
      // Updates uninitializing flag
      stoping = true;
      cron.stopInTheNextMinute();
      CronTask[] tasks = getAllTasks();

      for (int i = tasks.length - 1; i >= 0; i--) {
        tasks[i].join(iSecondsToWait);
      }

    } catch (InterruptedException e) {
      Log.error(e.toString(), e);
    }
  }


  /**
   *  This method sets the Cron to daemon or not
   *
   *@param  daemon      The new daemon value
   *@throws  Exception
   */
  public void setDaemon(boolean daemon) {
    this.daemon = daemon;
  }


  /**
   *  This method loads the config for the whole Crontab. If this method doesn't
   *  find the files creates itself them
   *
   *@throws  Exception
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

      for (Enumeration e = prop.propertyNames(); e.hasMoreElements(); ) {
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
        throw new FileNotFoundException("Unable to find: " +
            strFileName);
      }
    }
  }


  /**
   *  This method gets the value of the given property
   *
   *@param  property
   *@return           value
   */
  public String getProperty(String property) {
    return prop.getProperty(property);
  }


  /**
   *  This method sets the given property
   *
   *@param  property
   *@param  value
   */
  public void setProperty(String property, String value) {
    prop.setProperty(property, value);
  }


  /**
   *  This method Stores in the properties File the given property and all the
   *  "live" properties
   *
   *@param  property
   *@param  value
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
   *  This method says if today is a holiday or not
   *
   *@return             true if today is holiday false otherWise
   *@throws  Exception
   */
  public boolean isHoliday() throws Exception {
    if (getProperty("org.jcrontab.data.holidaysource") == null
         || getProperty("org.jcrontab.data.holidaysource") == "") {
      return false;
    }
    Date today = Calendar.getInstance().getTime();
    HoliDay[] holidays = HoliDayFactory.getInstance().findAll();

    for (int i = 0; i < holidays.length; i++) {
      if (holidays[i].getDate().getDay() == today.getDay() &&
          holidays[i].getDate().getMonth() == today.getMonth()) {
        return true;
      }
    }
    return false;
  }


  /**
   *  Creates and runs a new task
   *
   *@param  strClassName   Name of the task
   *@param  strMethodName  Name of the method that will be called
   *@param  strExtraInfo   Extra Information given to the task
   *@return                The identifier of the new task created, or -1 if
   *      could not create the new task (maximum number of tasks exceeded or
   *      another error)
   */
  public synchronized int newTask(String strClassName,
      String strMethodName, String[] strExtraInfo) {
    CronTask newTask;
    Class cl;
    int iTaskID;

    // Do not run new tasks if it is uninitializing
    if (stoping) {
      return -1;
    }
    String params = "";
    try {
      iTaskID = iNextTaskID;

      cl = (Class) (loadedClasses.get(strClassName));
      // If the class was not previously created, then creates it
      if (cl == null) {
        cl = Class.forName(strClassName.trim());
        loadedClasses.put(strClassName, cl);
      }

      // Creates the new task
      newTask = new CronTask();
      newTask.setParams(this, iTaskID, strClassName, strMethodName,
          strExtraInfo);
      // Aded name to newTask to show a name instead of Threads whe
      // logging
      // Thanks to Sander Verbruggen
      int lastDot = strClassName.lastIndexOf(".");
      if (lastDot > 0 && lastDot < strClassName.length()) {
        String classOnlyName = strClassName.substring(lastDot + 1);
        newTask.setName(classOnlyName);
      }

      synchronized (tasks) {
        tasks.put(new Integer(iTaskID),
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
      Log.error("Smth was wrong with" +
          strClassName +
          "#" +
          strMethodName +
          " " +
          params, e);
    }
    return -1;
  }


  /**
   *  Removes a task from the internal arrays of active tasks. This method is
   *  called from method run() of CronTask when a task has finished.
   *
   *@param  iTaskID  Identifier of the task to delete
   *@return          true if the task was deleted correctly, false otherwise
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
   *  Returns an array with all active tasks
   *
   *@return    An array with all active tasks NOTE: Does not returns the
   *      internal array because it is synchronized, returns a copy of it.
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
   *  Internal class that represents an entry in the task table
   *
   *@author     matt rajkowski
   *@created    February 4, 2003
   *@version    $Id$
   */
  private class TaskTableEntry {
    String strClassName;
    CronTask task;


    /**
     *  Constructor of an entry of the task table
     *
     *@param  strClassName  Name of the class of the task
     *@param  task          Reference to the task
     */
    public TaskTableEntry(String strClassName,
        CronTask task) {
      this.strClassName = strClassName;
      this.task = task;
    }
  }
}

