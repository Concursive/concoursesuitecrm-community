package org.aspcfs.apps.reportRunner;

import org.aspcfs.modules.system.base.*;
import java.sql.*;
import java.util.*;
import org.aspcfs.utils.*;
import java.lang.reflect.*;
import org.aspcfs.controller.ApplicationPrefs;

/**
 *  Cleans up all Jasper Reports, either as a standalone application or as a
 *  jcron task
 *
 *@author     matt rajkowski
 *@created    November 11, 2003
 *@version    $Id$
 */
public class ReportCleanup {

  private HashMap config = new HashMap();
  private ArrayList taskList = new ArrayList();

  public final static String fs = System.getProperty("file.separator");
  public final static String lf = System.getProperty("line.separator");


  /**
   *  Commandline startup
   *
   *@param  args  Description of the Parameter
   */
  public static void main(String args[]) {
    if (args.length == 0) {
      System.out.println("Usage: ReportCleanup [config file]");
      System.out.println("ExitValue: 2");
      System.exit(0);
    }
    System.setProperty("DEBUG", "2");
    ReportCleanup thisCleanup = new ReportCleanup();
    thisCleanup.execute(args);
    System.exit(0);
  }


  /**
   *  jcrontab startup
   *
   *@param  args  Description of the Parameter
   */
  public static void doTask(String args[]) {
    if (args.length == 0) {
      System.out.println("Usage: ReportCleanup [config file]");
      System.out.println("ExitValue: 2");
    } else {
      ReportCleanup thisCleanup = new ReportCleanup();
      thisCleanup.execute(args);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ReportCleanup ExitValue: 0");
      }
    }
  }


  /**
   *  process the reports
   *
   *@param  args  Description of the Parameter
   */
  private void execute(String args[]) {
    String filename = args[0];
    taskList.add("org.aspcfs.apps.reportRunner.task.ProcessJasperCleanup");
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
          System.out.println("ReportCleanup-> Processing each site");
        }
        Iterator i = siteList.iterator();
        while (i.hasNext()) {
          Site thisSite = (Site) i.next();
          Class.forName(thisSite.getDatabaseDriver());
          db = DriverManager.getConnection(
              thisSite.getDatabaseUrl(),
              thisSite.getDatabaseUsername(),
              thisSite.getDatabasePassword());
          baseName = thisSite.getSiteCode();
          Iterator classes = taskList.iterator();
          while (classes.hasNext()) {
            try {
              //Construct the object, which executes the task
              Class thisClass = Class.forName((String) classes.next());
              Class[] paramClass = new Class[]{Class.forName("java.sql.Connection"), Site.class, HashMap.class};
              Constructor constructor = thisClass.getConstructor(paramClass);
              Object[] paramObject = new Object[]{db, thisSite, config};
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
        System.err.println("ReportCleanup-> Error: " + exc.toString());
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

