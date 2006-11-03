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
package com.darkhorseventures.framework.hooks;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.login.beans.LoginBean;
import org.aspcfs.modules.setup.beans.DatabaseBean;
import org.aspcfs.modules.setup.beans.UserSetupBean;
import org.aspcfs.modules.system.base.ApplicationVersion;

import javax.servlet.ServletContext;
import java.security.Key;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Hooks to perform custom activities based on deployment needs
 *
 * @version $Id: Organization.java,v 1.82.2.1 2004/07/26 20:46:39 kbhoopal
 *          Exp $
 * @created January 13, 2006
 */
public class CustomHook {

  public final static String fs = System.getProperty("file.separator");

  public static boolean populateLoginContext(ActionContext context, Connection db, SystemStatus thisSystem, LoginBean loginBean) {
    return true;
  }

  public static boolean isOutOfDate(ApplicationPrefs prefs) {
    prefs.add("VERSION", ApplicationVersion.VERSION);
    return false;
  }

  public static void verifyDatabase(ActionContext context, Connection db, ApplicationPrefs prefs, ArrayList installLog, String dbNamePath) throws Exception {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CustomHook-> verifyDatabase");
    }
  }

  public static void populateSetup(ActionContext context, String path) {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CustomHook-> populateSetup");
    }
  }

  public static String populateRegister(ActionContext context, String path) {
    return "SkipRegistrationOK";
  }

  public synchronized static String populateSendReg(ActionContext context, ApplicationPrefs prefs) {
    return null;
  }

  public static String populateValidate(ActionContext context, ApplicationPrefs prefs, String path) {
    return null;
  }

  public static boolean populateDirectory(ActionContext context, ApplicationPrefs prefs, String userFileLibrary) {
    return true;
  }


  public static void populateDirectoryPath(ActionContext context) {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CustomHook-> populateDiretoryPath");
    }
  }

  public static void populateDatabaseBean(DatabaseBean bean) {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CustomHook-> populateDatabaseBean");
    }
  }


  public static void populateDatabase(DatabaseBean bean, String setupPath, String dbPath) throws Exception {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CustomHook-> populateDatabase");
    }
  }


  public static void populateServerInfo(ActionContext context, String path, Key key) throws Exception {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CustomHook-> populateServerInfo");
    }
  }

  public static void populateDatabaseUser(ActionContext context, String path, UserSetupBean bean) throws Exception {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CustomHook-> populateDatabaseUser");
    }
  }

  public static String populateUser(Connection db, String fileLibrary) throws Exception {
    return null;
  }


  public static String populateLicense(ActionContext context, SystemStatus systemStatus) {
    return null;
  }

  public static String populateSecurityHook(ApplicationPrefs applicationPrefs) {
    return null;
  }

  public static void populateApplicationPrefs(ServletContext context, ApplicationPrefs prefs) {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CustomHook-> populateApplicationPrefs");
    }
  }

  public static String populateAdminConfig(String module) {
    return null;
  }

  public static void populateDatabaseCheck(ActionContext context) {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CustomHook-> populateDatabaseCheck");
    }
  }
}
