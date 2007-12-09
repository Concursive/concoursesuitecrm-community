/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.apps.users;

import org.aspcfs.modules.system.base.Site;
import org.aspcfs.modules.system.base.SiteList;
import org.aspcfs.utils.AppUtils;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.SiteUtils;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id$
 * @created December 8, 2004
 */
public class UserCleanup {

  private HashMap config = new HashMap();
  private ArrayList taskList = new ArrayList();

  public final static String fs = System.getProperty("file.separator");
  public final static String lf = System.getProperty("line.separator");


  /**
   * Commandline startup
   *
   * @param args Description of the Parameter
   */
  public static void main(String args[]) {
    if (args.length == 0) {
      System.out.println("Usage: UserCleanup [config file]");
      System.out.println("ExitValue: 2");
      System.exit(0);
    }
    System.setProperty("DEBUG", "2");
    UserCleanup thisCleanup = new UserCleanup();
    thisCleanup.execute(args);
    System.exit(0);
  }


  /**
   * jcrontab startup
   *
   * @param args Description of the Parameter
   */
  public static void doTask(String args[]) {
    if (args.length == 0) {
      System.out.println("Usage: UserCleanup [config file]");
      System.out.println("ExitValue: 2");
    } else {
      UserCleanup thisCleanup = new UserCleanup();
      thisCleanup.execute(args);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("UserCleanup ExitValue: 0");
      }
    }
  }


  /**
   * process the expired/disabled users
   *
   * @param args Description of the Parameter
   */
  private void execute(String args[]) {
    String filename = args[0];
    taskList.add("org.aspcfs.apps.users.task.ProcessUserCleanup");
    AppUtils.loadConfig(filename, config);
    if (config.containsKey("FILELIBRARY")) {
      String baseName = (String) config.get("GATEKEEPER.URL");
      String dbUser = (String) config.get("GATEKEEPER.USER");
      String dbPass = (String) config.get("GATEKEEPER.PASSWORD");
      Connection db = null;
      try {
        SiteList siteList = SiteUtils.getSiteList(config);
        //Process each site
        if (System.getProperty("DEBUG") != null) {
          System.out.println("UserCleanup-> Processing each site");
        }
        Iterator i = siteList.iterator();
        while (i.hasNext()) {
          Site thisSite = (Site) i.next();
          Class.forName(thisSite.getDatabaseDriver());
          db = DatabaseUtils.getConnection(
              thisSite.getDatabaseUrl(),
              thisSite.getDatabaseUsername(),
              thisSite.getDatabasePassword());
          baseName = thisSite.getSiteCode();
          Iterator classes = taskList.iterator();
          while (classes.hasNext()) {
            try {
              //Construct the object, which executes the task
              Class thisClass = Class.forName((String) classes.next());
              Class[] paramClass = new Class[]{Class.forName(
                  "java.sql.Connection"), Class.forName("java.lang.String")};
              Constructor constructor = thisClass.getConstructor(paramClass);
              Object[] paramObject = new Object[]{db, args[1]};
              Object theTask = constructor.newInstance(paramObject);
              theTask = null;
            } catch (Exception e) {
              e.printStackTrace(System.out);
            }
          }
          db.close();
        }
      } catch (Exception exc) {
        exc.printStackTrace(System.out);
        System.err.println("UserCleanup-> Error: " + exc.toString());
      } finally {
        if (db != null) {
          try {
            db.close();
            db = null;
          } catch (Exception e) {
          }
        }
      }
    }
  }
}

