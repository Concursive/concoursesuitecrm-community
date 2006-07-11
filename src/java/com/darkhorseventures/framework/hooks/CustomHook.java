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

import bsh.Interpreter;
import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.apps.help.ImportHelp;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.LoginBean;
import org.aspcfs.modules.service.base.TransactionStatus;
import org.aspcfs.modules.service.base.RecordList;
import org.aspcfs.modules.service.base.Record;
import org.aspcfs.modules.setup.actions.Setup;
import org.aspcfs.modules.setup.beans.DatabaseBean;
import org.aspcfs.modules.setup.beans.RegistrationBean;
import org.aspcfs.modules.setup.beans.UserSetupBean;
import org.aspcfs.modules.setup.beans.UpdateBean;
import org.aspcfs.modules.system.base.ApplicationVersion;
import org.aspcfs.modules.system.base.DatabaseVersion;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.RequestUtils;
import org.w3c.dom.Element;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletContext;
import java.io.File;
import java.net.InetAddress;
import java.security.Key;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class CustomHook {


  public final static String fs = System.getProperty("file.separator");

  public static boolean populateLoginContext(ActionContext context, Connection db, SystemStatus thisSystem, LoginBean loginBean) {
    // TODO: from Login.java
    // TODO: return true

    // License check
    boolean continueId = false;
    try {
      File keyFile = new File(
          getPref(context, "FILELIBRARY") + "init" + fs + "zlib.jar");
      File inputFile = new File(
          getPref(context, "FILELIBRARY") + "init" + fs + "input.txt");
      if (keyFile.exists() && inputFile.exists()) {
        java.security.Key key = org.aspcfs.utils.PrivateString.loadKey(
            getPref(context, "FILELIBRARY") + "init" + fs + "zlib.jar");
        org.aspcfs.utils.XMLUtils xml = new org.aspcfs.utils.XMLUtils(
            org.aspcfs.utils.PrivateString.decrypt(
                key, StringUtils.loadText(
                getPref(context, "FILELIBRARY") + "init" + fs + "input.txt")));
        //The edition will be shown
        String lpd = org.aspcfs.utils.XMLUtils.getNodeText(
            xml.getFirstChild("text2"));
        PreparedStatement pst = db.prepareStatement(
            "SELECT COUNT(*) AS user_count " +
                "FROM \"access\" a, \"role\" r " +
                "WHERE a.user_id > 0 " +
                "AND a.role_id > 0 " +
                "AND a.role_id = r.role_id " +
                "AND r.role_type = ? " +
                "AND a.enabled = ? ");
        pst.setInt(1, Constants.ROLETYPE_REGULAR);
        pst.setBoolean(2, true);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
          if (rs.getInt("user_count") <= Integer.parseInt(
              lpd.substring(7)) || "-1".equals(lpd.substring(7))) {
            continueId = true;
          } else {
            loginBean.setMessage(
                "* " + thisSystem.getLabel("login.msg.licenseError"));
          }
        }
        rs.close();
        pst.close();
        // NOTE: This check is no longer valid until portal users are tracked
        //userId2 = lpd.substring(7);
      } else {
        loginBean.setMessage(
            "* " + thisSystem.getLabel("login.msg.licenseNotFound"));
      }
    } catch (Exception e) {
      loginBean.setMessage(
          "* " + thisSystem.getLabel("login.msg.licenseNotUptoDate"));
    }
    return continueId;
  }

  protected static String getPref(ActionContext context, String param) {
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute(
        "applicationPrefs");
    if (prefs != null) {
      return prefs.get(param);
    } else {
      return null;
    }
  }

  public static boolean isOutOfDate(ApplicationPrefs prefs) {
    // TODO: from ApplicationVersion.java
    // TODO: return false
    String installedVersion = ApplicationVersion.getInstalledVersion(prefs);
    if (installedVersion != null) {
      return !installedVersion.equals(ApplicationVersion.VERSION);
    }
    return false;
  }

  public static void verifyDatabase(ActionContext context, Connection db, ApplicationPrefs prefs, ArrayList installLog, String dbNamePath) throws Exception {
    // TODO: from Upgrade.java
    String versionToInstall = null;
    boolean buildHelp = false;
    String setupPath =
        context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs;

    // Determine if an upgrade needs to be executed (2.9)
    versionToInstall = "2004-06-15";
    if (!isInstalled(db, versionToInstall)) {
      upgradeSQL(context, db, "2004-06-15.sql");
      upgradeBSH(context, db, prefs, "2004-06-15.bsh");
      installLog.add(versionToInstall + " database changes installed");
      buildHelp = true;
    }
    // Determine if an upgrade needs to be executed (2.9.1)
    versionToInstall = "2004-08-30";
    if (!isInstalled(db, versionToInstall)) {
      upgradeSQL(context, db, "2004-08-30.sql");
      upgradeBSH(context, db, prefs, "2004-08-19.bsh");
      upgradeBSH(context, db, prefs, "2004-08-20.bsh");
      upgradeBSH(context, db, prefs, "2004-08-31.bsh");
      installLog.add(versionToInstall + " database changes installed");
      buildHelp = true;
    }
    // Determine if an upgrade needs to be executed (2.9.2)
    versionToInstall = "2005-01-14";
    if (!isInstalled(db, versionToInstall)) {
      upgradeSQL(context, db, "2005-01-14.sql");
      installLog.add(versionToInstall + " database changes installed");
    }
    // Determine if an upgrade needs to be executed (3.0)
    versionToInstall = "2005-03-30";
    if (!isInstalled(db, versionToInstall)) {
      upgradeSQL(context, db, "2005-03-30.sql");
      upgradeBSH(context, db, prefs, "2005-03-30-01.bsh");
      upgradeBSH(context, db, prefs, "2005-03-30-02.bsh");
      installLog.add(versionToInstall + " database changes installed");
    }
    // Determine if an upgrade needs to be executed (3.0.1)
    versionToInstall = "2005-05-02";
    if (!isInstalled(db, versionToInstall)) {
      upgradeBSH(context, db, prefs, "2005-05-02-01.bsh");
      upgradeSQL(context, db, "2005-05-02.sql");
      installLog.add(versionToInstall + " database changes installed");
    }
    // Determine if an upgrade needs to be executed (3.1)
    versionToInstall = "2005-07-08";
    if (!isInstalled(db, versionToInstall)) {
      // Translated template files
      FileUtils.copyFile(
          new File(setupPath + "workflow_*.xml"), new File(
          dbNamePath), true);
      FileUtils.copyFile(
          new File(setupPath + "templates_*.xml"), new File(
          dbNamePath), true);
      FileUtils.copyFile(
          new File(setupPath + "application.xml"), new File(
          dbNamePath + "application.xml"), true);
      installLog.add(versionToInstall + " files installed");
      // Schema changes
      upgradeSQL(context, db, "2005-07-08.sql");
      installLog.add(versionToInstall + " database changes installed");
      // Data changes
      upgradeBSH(context, db, prefs, "2005-06-21-script02.bsh");
      upgradeBSH(context, db, prefs, "2005-06-27-script02.bsh");
      upgradeBSH(context, db, prefs, "2005-05-25-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-05-25-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-05-25-script03-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-05-25-script04-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-05-25-script05-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-05-25-script06-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-05-25-script07-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-05-25-script08-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-05-25-script09-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-05-25-script10-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-05-25-script11-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-05-25-script12-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-05-25-script13-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-05-25-script14-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-07-01-script01.bsh");
      upgradeBSH(context, db, prefs, "2005-07-07-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-07-14-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-09-29-script01-partha.bsh");
      installLog.add(versionToInstall + " database changes installed");
    }

    // 4.0 PREVIEW 2 (FROM 3.1)
    versionToInstall = "2005-08-24";
    if (!isInstalled(db, versionToInstall)) {
      upgradeBSH(context, db, prefs, "2005-08-04-script01-partha.bsh");
      upgradeSQL(context, db, "2005-08-23.sql");
      upgradeSQL(context, db, "2005-08-24.sql");
      upgradeSQL(context, db, "2005-08-30.sql");
      upgradeSQL(context, db, "2005-09-07.sql");
      upgradeSQL(context, db, "2005-09-15.sql");
      upgradeSQL(context, db, "2005-09-22.sql");
      DatabaseVersion.insertVersion(db, versionToInstall, versionToInstall);
      installLog.add(versionToInstall + " database changes installed");

      upgradeSQL(context, db, "2005-10-17.sql");
      installLog.add(versionToInstall + " database changes installed");


      upgradeSQL(context, db, "2005-10-24.sql");
      installLog.add( "2005-10-24" + " database changes installed");
      upgradeSQL(context, db, "2005-11-02.sql");
      installLog.add("2005-11-02" + " database changes installed");

      upgradeSQL(context, db, "2005-11-03.sql");
      installLog.add("2005-11-03" + " database schema updated");

      upgradeSQL(context, db, "2005-11-14.sql");
      installLog.add("2005-11-14" + " database schema updated");

      upgradeBSH(context, db, prefs, "2005-09-12-script04-partha.bsh");
      upgradeSQL(context, db, "2006-01-25.sql");
      installLog.add("2006-01-25" + " database schema updated");

      versionToInstall = "2005-10-17";
      upgradeBSH(context, db, prefs, "2005-09-12-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-09-16-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-09-22-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-09-25-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-09-29-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-10-04-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-10-04-script03-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-10-11-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-10-14-script01-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-10-17-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-10-17-script03-partha.bsh");
      DatabaseVersion.insertVersion(db, versionToInstall, versionToInstall);
      installLog.add(versionToInstall + " data additions installed");
      versionToInstall = "2005-08-30";
      upgradeBSH(context, db, prefs, "2005-08-08-mr.bsh");
      upgradeBSH(context, db, prefs, "2005-08-17-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-08-17-script02-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-08-24-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-08-29-script01-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-09-07-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-09-12-script01-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-09-12-script02-ananth.bsh");
      DatabaseVersion.insertVersion(db, versionToInstall, versionToInstall);
      installLog.add(versionToInstall + " data additions installed");
      versionToInstall = "2005-10-24";
      upgradeBSH(context, db, prefs, "2005-10-21-script02-kailash.bsh");
      upgradeBSH(context, db, prefs, "2005-10-21-script03-kailash.bsh");
      upgradeBSH(context, db, prefs, "2005-10-21-script04-kailash.bsh");
      upgradeBSH(context, db, prefs, "2005-10-21-script06-kailash.bsh");
      upgradeBSH(context, db, prefs, "2005-10-21-script07-kailash.bsh");
      upgradeBSH(context, db, prefs, "2005-10-21-script08-kailash.bsh");
      installLog.add(versionToInstall + " data additions installed");
      // Needs to run after .bsh script because of drop statements
      upgradeSQL(context, db, "2005-10-25.sql");
      installLog.add(versionToInstall + " secondary database changes installed");
      DatabaseVersion.insertVersion(db, versionToInstall, versionToInstall);
      versionToInstall = "2005-11-03";
      upgradeBSH(context, db, prefs, "2005-10-26-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-11-03-script01-mr.bsh");
      upgradeBSH(context, db, prefs, "2005-11-03-script02-mr.bsh");
      upgradeBSH(context, db, prefs, "2005-11-03-script03-mr.bsh");
      upgradeBSH(context, db, prefs, "2005-11-03-script04-mr.bsh");
      upgradeBSH(context, db, prefs, "2005-11-03-script06-mr.bsh");
      installLog.add(versionToInstall + " data additions installed");
      DatabaseVersion.insertVersion(db, versionToInstall, versionToInstall);
      versionToInstall = "2005-11-14";
      upgradeBSH(context, db, prefs, "2005-09-12-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-09-12-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-09-12-script03-andhalf-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-09-22-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-09-25-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-11-07-script01-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-11-07-script02-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-11-08-script01-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-11-09-script01-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-11-11-script01-mr.bsh");
      upgradeBSH(context, db, prefs, "2005-11-03-script04-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-11-09-script02-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-11-28-script01-mr.bsh");
      upgradeBSH(context, db, prefs, "2005-12-01-script01-partha.bsh");
      installLog.add(versionToInstall + " data additions installed");
      DatabaseVersion.insertVersion(db, versionToInstall, versionToInstall);
      versionToInstall = "2005-12-19";
      upgradeBSH(context, db, prefs, "2005-12-14-script01-mr.bsh");
      installLog.add(versionToInstall + " data additions installed");
      DatabaseVersion.insertVersion(db, versionToInstall, versionToInstall);
      versionToInstall = "2006-01-25";
      upgradeBSH(context, db, prefs, "2006-01-09-script02-ananth.bsh");
      upgradeBSH(context, db, prefs, "2006-01-12-script01-mr.bsh");
      upgradeBSH(context, db, prefs, "2006-01-20-script01-ananth.bsh");
      upgradeBSH(context, db, prefs, "2006-01-20-script02-ananth.bsh");
      installLog.add(versionToInstall + " data additions installed");
      DatabaseVersion.insertVersion(db, versionToInstall, versionToInstall);
    }

    // 4.0 PREVIEW 2 (FROM 3.2pre7)
    versionToInstall = "2005-10-17";
    if (!isInstalled(db, versionToInstall)) {
      upgradeSQL(context, db, "2005-10-17.sql");
      installLog.add(versionToInstall + " database changes installed");

      upgradeSQL(context, db, "2005-10-24.sql");
      installLog.add( "2005-10-24" + " database changes installed");
      upgradeSQL(context, db, "2005-11-02.sql");
      installLog.add("2005-11-02" + " database changes installed");

      upgradeSQL(context, db, "2005-11-03.sql");
      installLog.add("2005-11-03" + " database schema updated");

      upgradeSQL(context, db, "2005-11-14.sql");
      installLog.add("2005-11-14" + " database schema updated");

      upgradeBSH(context, db, prefs, "2005-09-12-script04-partha.bsh");
      upgradeSQL(context, db, "2006-01-25.sql");
      installLog.add("2006-01-25" + " database schema updated");

      versionToInstall = "2005-10-17";
      upgradeBSH(context, db, prefs, "2005-09-12-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-09-16-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-09-22-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-09-25-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-09-29-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-10-04-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-10-04-script03-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-10-11-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-10-14-script01-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-10-17-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-10-17-script03-partha.bsh");
      DatabaseVersion.insertVersion(db, versionToInstall, versionToInstall);
      installLog.add(versionToInstall + " data additions installed");
      versionToInstall = "2005-08-30";
      upgradeBSH(context, db, prefs, "2005-08-08-mr.bsh");
      upgradeBSH(context, db, prefs, "2005-08-17-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-08-17-script02-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-08-04-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-08-24-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-08-29-script01-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-09-07-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-09-12-script01-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-09-12-script02-ananth.bsh");
      DatabaseVersion.insertVersion(db, versionToInstall, versionToInstall);
      installLog.add(versionToInstall + " data additions installed");
      versionToInstall = "2005-10-24";
      upgradeBSH(context, db, prefs, "2005-10-21-script02-kailash.bsh");
      upgradeBSH(context, db, prefs, "2005-10-21-script03-kailash.bsh");
      upgradeBSH(context, db, prefs, "2005-10-21-script04-kailash.bsh");
      upgradeBSH(context, db, prefs, "2005-10-21-script06-kailash.bsh");
      upgradeBSH(context, db, prefs, "2005-10-21-script07-kailash.bsh");
      upgradeBSH(context, db, prefs, "2005-10-21-script08-kailash.bsh");
      installLog.add(versionToInstall + " data additions installed");
      // Needs to run after .bsh script because of drop statements
      upgradeSQL(context, db, "2005-10-25.sql");
      installLog.add(versionToInstall + " secondary database changes installed");
      DatabaseVersion.insertVersion(db, versionToInstall, versionToInstall);
      versionToInstall = "2005-11-03";
      upgradeBSH(context, db, prefs, "2005-10-26-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-11-03-script01-mr.bsh");
      upgradeBSH(context, db, prefs, "2005-11-03-script02-mr.bsh");
      upgradeBSH(context, db, prefs, "2005-11-03-script03-mr.bsh");
      upgradeBSH(context, db, prefs, "2005-11-03-script04-mr.bsh");
      upgradeBSH(context, db, prefs, "2005-11-03-script06-mr.bsh");
      installLog.add(versionToInstall + " data additions installed");
      DatabaseVersion.insertVersion(db, versionToInstall, versionToInstall);
      versionToInstall = "2005-11-14";
      upgradeBSH(context, db, prefs, "2005-09-12-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-09-12-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-09-12-script03-andhalf-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-09-22-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-09-25-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-11-07-script01-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-11-07-script02-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-11-08-script01-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-11-09-script01-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-11-11-script01-mr.bsh");
      upgradeBSH(context, db, prefs, "2005-11-03-script04-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-11-09-script02-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-11-28-script01-mr.bsh");
      upgradeBSH(context, db, prefs, "2005-12-01-script01-partha.bsh");
      installLog.add(versionToInstall + " data additions installed");
      DatabaseVersion.insertVersion(db, versionToInstall, versionToInstall);
      versionToInstall = "2005-12-19";
      upgradeBSH(context, db, prefs, "2005-12-14-script01-mr.bsh");
      installLog.add(versionToInstall + " data additions installed");
      DatabaseVersion.insertVersion(db, versionToInstall, versionToInstall);
      versionToInstall = "2006-01-25";
      upgradeBSH(context, db, prefs, "2006-01-09-script02-ananth.bsh");
      upgradeBSH(context, db, prefs, "2006-01-12-script01-mr.bsh");
      upgradeBSH(context, db, prefs, "2006-01-20-script01-ananth.bsh");
      upgradeBSH(context, db, prefs, "2006-01-20-script02-ananth.bsh");
      installLog.add(versionToInstall + " data additions installed");
      DatabaseVersion.insertVersion(db, versionToInstall, versionToInstall);
    }


    // 4.0 Preview 2 Alpha
    versionToInstall = "2005-12-19";
    if (!isInstalled(db, versionToInstall)) {
      upgradeBSH(context, db, prefs, "2005-12-14-script01-mr.bsh");
      installLog.add(versionToInstall + " data additions installed");
      DatabaseVersion.insertVersion(db, versionToInstall, versionToInstall);
    }

    // 4.0 Preview 2 Final
    versionToInstall = "2006-01-25";
    if (!isInstalled(db, versionToInstall)) {
      upgradeSQL(context, db, "2006-01-25.sql");
      installLog.add(versionToInstall + " database schema updated");
      upgradeBSH(context, db, prefs, "2006-01-09-script02-ananth.bsh");
      upgradeBSH(context, db, prefs, "2006-01-12-script01-mr.bsh");
      upgradeBSH(context, db, prefs, "2006-01-20-script01-ananth.bsh");
      upgradeBSH(context, db, prefs, "2006-01-20-script02-ananth.bsh");
      installLog.add(versionToInstall + " data additions installed");
      DatabaseVersion.insertVersion(db, versionToInstall, versionToInstall);
    }

    // 4.0 Test 2 Final
    versionToInstall = "2006-04-12";
    if (!isInstalled(db, versionToInstall)) {
      upgradeSQL(context, db, "2006-04-12.sql");
      installLog.add(versionToInstall + " database schema updated");
      upgradeBSH(context, db, prefs, "2006-02-06-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2006-02-13-script01-ananth.bsh");
      upgradeBSH(context, db, prefs, "2006-02-20-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2006-03-02-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2006-03-02-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-08-24-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2006-03-13-script01-ananth.bsh");
      upgradeBSH(context, db, prefs, "2006-03-15-script01-ananth.bsh");
      installLog.add(versionToInstall + " data additions installed");
      DatabaseVersion.insertVersion(db, versionToInstall, versionToInstall);
    }

    versionToInstall = "2006-04-17";
    if (!isInstalled(db, versionToInstall)) {
      upgradeBSH(context, db, prefs, "2005-03-30-02.bsh");
      upgradeBSH(context, db, prefs, "2005-07-07-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2006-04-17-script01-partha.bsh");
      installLog.add(versionToInstall + " data additions installed");
      DatabaseVersion.insertVersion(db, versionToInstall, versionToInstall);
    }

    // 4.0.1
    versionToInstall = "2006-05-08";
    if (!isInstalled(db, versionToInstall)) {
      // Missing locale permissions
      upgradeBSH(context, db, prefs, "2005-08-17-script02-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-08-24-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2006-03-13-script01-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-10-04-script03-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-08-29-script01-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-11-03-script01-mr.bsh");
      upgradeBSH(context, db, prefs, "2005-11-03-script02-mr.bsh");
      upgradeBSH(context, db, prefs, "2005-11-03-script03-mr.bsh");
      upgradeBSH(context, db, prefs, "2005-11-03-script04-mr.bsh");
      upgradeBSH(context, db, prefs, "2005-10-04-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-10-11-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-10-17-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-08-17-script02-partha.bsh");
      // Missing locale reports
      upgradeBSH(context, db, prefs, "2006-01-20-script01-ananth.bsh");
      upgradeBSH(context, db, prefs, "2006-03-15-script01-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-09-12-script01-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-09-12-script02-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-11-07-script01-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-11-07-script02-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-11-08-script01-ananth.bsh");
      upgradeBSH(context, db, prefs, "2005-11-09-script01-ananth.bsh");
      upgradeBSH(context, db, prefs, "2006-01-20-script02-ananth.bsh");
      // Missing locale editors
      upgradeBSH(context, db, prefs, "2005-09-07-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-09-12-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-11-03-script04-ananth.bsh");
      // Missingle locale lookup list editors
      upgradeBSH(context, db, prefs, "2005-11-03-script06-mr.bsh");
      upgradeBSH(context, db, prefs, "2006-02-06-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2006-05-08-script01-mr.bsh");
      upgradeBSH(context, db, prefs, "2005-09-25-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-10-21-script02-kailash.bsh");
      upgradeBSH(context, db, prefs, "2005-09-22-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-10-17-script03-partha.bsh");
      upgradeBSH(context, db, prefs, "2005-12-14-script01-mr.bsh");
      upgradeBSH(context, db, prefs, "2006-02-13-script01-ananth.bsh");
      installLog.add(versionToInstall + " data additions installed");
      DatabaseVersion.insertVersion(db, versionToInstall, versionToInstall);
    }

    // 4.1 beta 1
    versionToInstall = "2006-06-01";
    if (!isInstalled(db, versionToInstall)) {
      // New web site module
      upgradeSQL(context, db, "2006-06-01.sql");
      installLog.add(versionToInstall + " database schema updated");
      upgradeBSH(context, db, prefs, "2006-02-23-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2006-02-23-script02-partha.bsh");
      upgradeBSH(context, db, prefs, "2006-02-23-script03-partha.bsh");
      upgradeBSH(context, db, prefs, "2006-05-18-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2006-05-19-script01-partha.bsh");
      upgradeBSH(context, db, prefs, "2006-06-01-script01-mr.bsh");
      installLog.add(versionToInstall + " data additions installed");
      DatabaseVersion.insertVersion(db, versionToInstall, versionToInstall);
    }

    // 4.1 beta 2
    versionToInstall = "2006-06-16";
    if (!isInstalled(db, versionToInstall)) {
      // New web site module
      upgradeSQL(context, db, "2006-06-16.sql");
      installLog.add(versionToInstall + " database schema updated");
      upgradeBSH(context, db, prefs, "2006-05-25-script01-aliaksei.bsh");
      upgradeBSH(context, db, prefs, "2006-05-25-script02-aliaksei.bsh");
      installLog.add(versionToInstall + " data additions installed");
      DatabaseVersion.insertVersion(db, versionToInstall, versionToInstall);
    }

    // 4.1 beta 3
    versionToInstall = "2006-06-30";
    if (!isInstalled(db, versionToInstall)) {
      // New web site module
      upgradeSQL(context, db, "2006-06-30.sql");
      installLog.add(versionToInstall + " database schema updated");
      DatabaseVersion.insertVersion(db, versionToInstall, versionToInstall);
    }

    // 4.1 beta 4
    versionToInstall = "2006-07-11";
    if (!isInstalled(db, versionToInstall)) {
      // New portlets
      upgradeBSH(context, db, prefs, "2006-06-01-script01-mr.bsh");
      installLog.add(versionToInstall + " data additions installed");
      DatabaseVersion.insertVersion(db, versionToInstall, versionToInstall);
    }

    // NOTE: Make sure to update SetupUtils with version info whenever a new
    // upgrade version is installed

    // Aux application files... overwrite all
    FileUtils.copyFile(
        new File(setupPath + "workflow_*.xml"), new File(
        dbNamePath), true);
    FileUtils.copyFile(
        new File(setupPath + "templates_*.xml"), new File(
        dbNamePath), true);
    FileUtils.copyFile(
        new File(setupPath + "application.xml"), new File(
        dbNamePath + "application.xml"), true);

    // Reinstall the help file if requested...
    if (buildHelp) {
      renewConnection(context, db);
      // Install the help (blank tables should already exist)
      ImportHelp help = new ImportHelp();
      help.buildHelpInformation(
          context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs + "help.xml");
      help.buildExistingPermissionCategories(db);
      help.insertHelpRecords(db);
      help.buildTableOfContents();
      help.insertTableOfContents(db);
      renewConnection(context, db);
    }
  }

  /**
   * Gets the installed attribute of the Upgrade object
   *
   * @param db      Description of the Parameter
   * @param version Description of the Parameter
   * @return The installed value
   * @throws SQLException Description of the Exception
   */
  private static boolean isInstalled(Connection db, String version) throws SQLException {
    boolean isInstalled = false;
    // Query the installed version
    PreparedStatement pst = db.prepareStatement(
        "SELECT script_version " +
            "FROM database_version " +
            "WHERE script_version = ? ");
    pst.setString(1, version);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      isInstalled = true;
    }
    rs.close();
    pst.close();
    return isInstalled;
  }


  /**
   * Description of the Method
   *
   * @param context  Description of the Parameter
   * @param db       Description of the Parameter
   * @param baseName Description of the Parameter
   * @throws Exception Description of the Exception
   */
  private static void upgradeSQL(ActionContext context, Connection db, String baseName) throws Exception {
    renewConnection(context, db);
    switch (DatabaseUtils.getType(db)) {
      case DatabaseUtils.POSTGRESQL:
        System.out.println(
            "Upgrade-> Executing PostgreSQL script " + baseName);
        DatabaseUtils.executeSQL(
            db,
            context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs + "postgresql_" + baseName);
        break;
      case DatabaseUtils.DAFFODILDB:
        System.out.println(
            "Upgrade-> Executing DaffodilDB script " + baseName);
        DatabaseUtils.executeSQL(
            db,
            context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs + "daffodildb_" + baseName);
        break;
      case DatabaseUtils.DB2:
        System.out.println("Upgrade-> Executing DB2 script " + baseName);
        DatabaseUtils.executeSQL(
            db,
            context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs + "db2_" + baseName);
        break;
      case DatabaseUtils.FIREBIRD:
        System.out.println("Upgrade-> Executing Firebird script " + baseName);
        DatabaseUtils.executeSQL(
            db,
            context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs + "firebird_" + baseName);
        break;
      case DatabaseUtils.MSSQL:
        System.out.println("Upgrade-> Executing MSSQL script " + baseName);
        DatabaseUtils.executeSQL(
            db,
            context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs + "mssql_" + baseName);
        break;
      case DatabaseUtils.ORACLE:
        System.out.println("Upgrade-> Executing Oracle script " + baseName);
        DatabaseUtils.executeSQL(
            db,
            context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs + "oracle_" + baseName);
        break;
      default:
        if (System.getProperty("DEBUG") != null) {
          System.out.println(
              "Upgrade-> * Database could not be determined: " + DatabaseUtils.getType(
                  db));
        }
        break;
    }

  }


  /**
   * Description of the Method
   *
   * @param context    Description of the Parameter
   * @param scriptName Description of the Parameter
   * @throws Exception Description of the Exception
   */
  private static void upgradeBSH(ActionContext context, Connection db, ApplicationPrefs prefs, String scriptName) throws Exception {
    System.out.println("Upgrade-> Executing BeanShell script " + scriptName);
    renewConnection(context, db);
    // Prepare bean shell script, if needed
    Interpreter script = new Interpreter();
    script.set("db", db);
    script.set("dbFileLibraryPath", prefs.get("FILELIBRARY"));
    script.set("fileLibraryPath", context.getServletContext().getRealPath("/"));
    script.set("languagePath", context.getServletContext().getRealPath("/") + "WEB-INF/languages/");
    script.set("iceletsPath", context.getServletContext().getRealPath("/") + "WEB-INF/icelets/");
    script.set("locale", prefs.get("SYSTEM.LANGUAGE"));
    script.source(
        context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs + scriptName);
  }

  protected static void renewConnection(ActionContext context, Connection db) {
    //Connections are usually checked out and expire, this will renew the expiration
    //time
    if (db != null) {
      ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute(
          "ConnectionPool");
      sqlDriver.renew(db);
    }
  }

  public static void populateSetup(ActionContext context, String path) {
    // Trigger the registration stuff
    context.getServletContext().setAttribute("APP_VERSION", ApplicationVersion.VERSION);
    //Check if key exists, if not force user to use a new key
    if (path == null) {
      path = context.getServletContext().getRealPath("/") + "WEB-INF" + fs;
    }
    String keyFile = path + "init" + fs + "zlib.jar";
    File thisFile = new File(keyFile);
    if (thisFile.exists()) {
      context.getRequest().setAttribute("found", "true");
    }
  }

  public static String populateRegister(ActionContext context, String path) {
    String reg = context.getRequest().getParameter("doReg");
    if ("have".equals(reg)) {
      //If user says they want to continue, see if the file exists and that the
      //user validated it, otherwise show the reg screen
      if (isValidLicense(context, path)) {
        //move on to next action and see if they are there...
        return "ValidateOK";
      }
      return "SetupHaveRegOK";
    } else if ("need".equals(reg)) {
      RegistrationBean bean = (RegistrationBean) context.getFormBean();
      if (bean.getConfigured() == -1) {
        try {
          //Prepare the form... just add the server name
          InetAddress i = InetAddress.getLocalHost();
          //this returns the name
          bean.setProfile(i.getHostName());
        } catch (Exception e) {
        }
      }
      context.getRequest().setAttribute(
          "server",
          HTTPUtils.getServerName(
              context.getRequest().getScheme() + "://" +
                  RequestUtils.getServerUrl(context.getRequest())));
      return "SetupNeedRegOK";
    } else if ("restore".equals(reg)) {
      return "SkipRegistrationOK";
    }
    return "SkipRegistrationOK";
  }

  /**
   * Gets the validLicense attribute of the Setup object
   *
   * @param context Description of the Parameter
   * @return The validLicense value
   */
  private static boolean isValidLicense(ActionContext context, String path) {
    try {
      //Load the key and license, make check then return true/false
      if (path == null) {
        path = context.getServletContext().getRealPath("/") + "WEB-INF" + fs;
      }
      String keyFile = path + "init" + fs + "zlib.jar";
      File thisFile = new File(keyFile);
      if (!thisFile.exists()) {
        return false;
      }
      Key key = PrivateString.loadKey(keyFile);
      //Load the license
      String licenseFile = path + "init" + fs + "input.txt";
      String licenseXml = StringUtils.loadText(licenseFile);
      if (licenseXml == null) {
        return false;
      }
      XMLUtils xml = new XMLUtils(PrivateString.decrypt(key, licenseXml));
      String entered = XMLUtils.getNodeText(xml.getFirstChild("entered"));
      if (entered == null) {
        return false;
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public synchronized static String populateSendReg(ActionContext context, ApplicationPrefs prefs) {
    if (Setup.isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    RegistrationBean bean = (RegistrationBean) context.getFormBean();
    if (context.getParameter("ssl") == null) {
      bean.setSsl(false);
    }
    context.getRequest().setAttribute(
        "server",
        HTTPUtils.getServerName(
            context.getRequest().getScheme() + "://" +
                RequestUtils.getServerUrl(context.getRequest())));
    try {
      // TODO: difficult to implement
      /*
      boolean isValid = false;
      isValid = validateObject(context, null, bean);
      if (!isValid) {
        return "SendRegERROR";
      }
      */
      String response = null;
      Key key = null;
      //Send the XML
      if ((Object) context.getServletContext().getAttribute("cfs.setup") == null)
      {
        //Make the graphs dir to store the graphs (must be done before using system)
        if (System.getProperty("DEBUG") != null) {
          System.out.println("CustomHook-> create graph dir");
        }
        String graphPath =
            context.getServletContext().getRealPath("/") + "graphs" + fs;
        File graphDirectory = new File(graphPath);
        graphDirectory.mkdirs();
        //Make sure the temporary path is writeable (use WEB-INF)
        String keyFilePath = context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "init" + fs;
        File thisPath = new File(keyFilePath);
        if (System.getProperty("DEBUG") != null) {
          System.out.println("CustomHook-> make init dir: " + keyFilePath);
        }
        thisPath.mkdirs();
        //Get or make the key file
        String keyFile = keyFilePath + "zlib.jar";
        if (System.getProperty("DEBUG") != null) {
          System.out.println("CustomHook-> generate key: " + keyFilePath + "zlib.jar");
        }
        key = PrivateString.generateKeyFile(keyFile);
      }
      //Erase the existing validation file since a new license is being requested
      try {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("CustomHook-> license file: " + context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "init" + fs + "input.txt");
        }

        File licenseFile = new File(
            context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "init" + fs + "input.txt");
        if (licenseFile.exists()) {
          licenseFile.delete();
        }
      } catch (Exception io) {
        io.printStackTrace(System.out);
      }

      //Encode the key for transmission
      if (System.getProperty("DEBUG") != null) {
        System.out.println("CustomHook-> Base64 encode");
      }
      BASE64Encoder encoder = new BASE64Encoder();
      System.out.println("1");
      bean.setZlib(encoder.encode(ObjectUtils.toByteArray(key)));
      System.out.println("2");
      bean.setText(PrivateString.encrypt(key, "5USERBINARY-4.0"));
      System.out.println("3");
      bean.setWebserver(
          HTTPUtils.getServerName(
              context.getRequest().getScheme() + "://" +
                  RequestUtils.getServerUrl(context.getRequest())));
      System.out.println("4");
      //Configure proxy server
      Properties systemSettings = System.getProperties();
      if (bean.getProxy()) {
        systemSettings.put("proxySet", "true");
        systemSettings.put("http.proxyHost", bean.getProxyHost());
        systemSettings.put("http.proxyPort", bean.getProxyPort());
        prefs.add("PROXYSERVER", "true");
        prefs.add("PROXYSERVER.HOST", bean.getProxyHost());
        prefs.add("PROXYSERVER.PORT", bean.getProxyPort());
      } else {
        systemSettings.put("proxySet", "false");
        prefs.add("PROXYSERVER", null);
        prefs.add("PROXYSERVER.HOST", null);
        prefs.add("PROXYSERVER.PORT", null);
      }
      // Sending Packet
      System.out.println("CustomHook-> Prepare send");
      String packet = bean.toXmlString();
      System.out.println("CustomHook-> Sending: " + packet);
      if (bean.getSsl()) {
        response = HTTPUtils.sendPacket(
            "https://registration.centriccrm.com/LicenseServer.do?command=SubmitRegistration", packet);
      } else {
        response = HTTPUtils.sendPacket(
            "http://registration.centriccrm.com/LicenseServer.do?command=SubmitRegistration", packet);
      }
      if (response == null) {
        System.out.println("CustomHook-> NULL RESPONSE");
        context.getRequest().setAttribute(
            "actionError", prefs.getLabel(
            "object.validation.actionError.unspecifiedErrorNoResponse", prefs.get("SYSTEM.LANGUAGE")));
        return "SendRegERROR";
      }
      TransactionStatus thisStatus = processResponse(response);
      if (thisStatus.getStatusCode() != 0) {
        HashMap map = new HashMap();
        map.put("${thisStatus.message}", thisStatus.getMessage());
        context.getRequest().setAttribute(
            "actionError",
            Setup.getLabel(
                map, prefs.getLabel(
                "object.validation.actionError.unspecifiedErrorRejectedRegistration", prefs.get("SYSTEM.LANGUAGE"))));
        return "SendRegERROR";
      }
    } catch (java.io.IOException errorMessage) {
      //Socket connection error
      context.getRequest().setAttribute(
          "actionError", "Connection could not be made... Check your internet connection: " + errorMessage.getMessage());
      return "SendRegERROR";
    } catch (org.w3c.dom.DOMException xmlError) {
      //XML error
      context.getRequest().setAttribute(
          "actionError", "The license server did not respond correctly, the following error occurred: " + xmlError.getMessage());
      return "SendRegERROR";
    } catch (Exception e) {
      e.printStackTrace(System.out);
      //XML error
      context.getRequest().setAttribute(
          "actionError", "An error occurred, the supplied error is: " + e.getMessage());
      return "SendRegERROR";
    }
    return "SubmitRegistrationOK";
  }

  public static TransactionStatus processResponse(String response) {
    try {
      XMLUtils responseXML = new XMLUtils(response);
      Element responseNode = responseXML.getFirstChild("response");
      TransactionStatus thisStatus = new TransactionStatus(responseNode);
      return thisStatus;
    } catch (Exception e) {
      return null;
    }
  }

  public static String populateValidate(ActionContext context, ApplicationPrefs prefs, String path) {
    if (Setup.isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    try {
      //Load the key
      String keyFile = context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "init" + fs + "zlib.jar";
      Key key = PrivateString.loadKey(keyFile);
      //Check the request
      String license = context.getRequest().getParameter("license");
      if (license == null) {
        context.getRequest().setAttribute(
            "actionError", prefs.getLabel(
            "object.validation.actionError.invalidKeyEntered", prefs.get("SYSTEM.LANGUAGE")));
        return "ValidateRETRY";
      }
      //See if <license> and </license> are included
      if (license != null && (license.indexOf("<license>") == -1 || license.indexOf(
          "</license>") == -1)) {
        context.getRequest().setAttribute(
            "actionError",
            "The entered key did not validate because the key did not start with <license> and did not end with </license>, try entering it again.");
        return "ValidateRETRY";
      }
      //Decode the license into XML
      String licenseXml = license.substring(
          license.indexOf("<license>") + 9,
          license.lastIndexOf("</license>"));
      licenseXml = StringUtils.replace(licenseXml, " ", "\r\n");
      //Try decoding the license to make sure it's good
      XMLUtils xml = new XMLUtils(PrivateString.decrypt(key, licenseXml));
      //The license is presumed good here so save the xml, it will be tested again
      String entered = XMLUtils.getNodeText(xml.getFirstChild("entered"));
      if (entered != null) {
        //save the license -- it came from the server
        StringUtils.saveText(
            context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "init" + fs + "input.txt", licenseXml);
      }
      if (isValidLicense(context, path)) {
        return "ValidateOK";
      } else {
        throw new Exception(
            "Invalid key for this system -- try again or request a new key");
      }
    } catch (Exception e) {
      if (e.getMessage() == null) {
        context.getRequest().setAttribute(
            "actionError", prefs.getLabel(
            "object.validation.actionError.invalidLicenseCode", prefs.get("SYSTEM.LANGUAGE")));
      } else {
        HashMap map = new HashMap();
        map.put("${error.message}", e.getMessage());
        context.getRequest().setAttribute(
            "actionError",
            Setup.getLabel(
                map, prefs.getLabel(
                "object.validation.actionError.licenseProcessingError", prefs.get("SYSTEM.LANGUAGE"))));
      }
      return "ValidateRETRY";
    }
  }

  public static boolean populateDirectory(ActionContext context, ApplicationPrefs prefs, String userFileLibrary) {
    String initPath = context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "init" + fs;
    File inputFile = new File(initPath + "input.txt");
    File inputFileDest = new File(userFileLibrary + "init" + fs + "input.txt");
    if (inputFile.exists()) {
      //Copy input.txt to target directory (always do this)
      FileUtils.copyFile(inputFile, inputFileDest, true);
    }
    File zlibFile = new File(initPath + "zlib.jar");
    File zlibFileDest = new File(userFileLibrary + "init" + fs + "zlib.jar");
    if (zlibFile.exists()) {
      //Copy zlib.jar to target directory (always do this, since user may have re-registered)
      FileUtils.copyFile(zlibFile, zlibFileDest, true);
    }
    //Verify the source and destination input.txt file
    if (!inputFile.exists() && !inputFileDest.exists()) {
      context.getRequest().setAttribute(
          "actionError", prefs.getLabel(
          "object.validation.actionError.fileLibraryPathMissingInputFile", prefs.get("SYSTEM.LANGUAGE")));
      return false;
    }
    //Verify the source and destination zlib.jar file
    if (!zlibFile.exists() && !zlibFileDest.exists()) {
      context.getRequest().setAttribute(
          "actionError", prefs.getLabel(
          "object.validation.actionError.fileLibraryPathMissingZLIBFile", prefs.get("SYSTEM.LANGUAGE")));
      return false;
    }
    return true;
  }


  public static void populateDatabaseBean(DatabaseBean bean) {
    if (bean.isEmbedded()) {
      if ("DaffodilDB".equals(bean.getType())) {
        bean.setName("centric_crm");
        bean.setUser("daffodil");
        bean.setPassword("daffodil");
      }
      if ("Firebird".equals(bean.getType())) {
        bean.setName("centric_crm.fdb");
        bean.setUser("firebird");
        bean.setPassword("firebird");
      }
    }
  }


  public static void populateDatabase(DatabaseBean bean, String setupPath, String dbPath) throws Exception {
    if ("DaffodilDB".equals(bean.getType())) {
      // Explode the daffodildb centric_crm schema
      ZipUtils.extract(new File(setupPath + "daffodildb.zip"), dbPath);
    }
    if ("Firebird".equals(bean.getType())) {
      // Explode the firebird centric_crm schema
      ZipUtils.extract(new File(setupPath + "firebird.zip"), dbPath);
      bean.setPort("3050");
    }
  }


  public static void populateServerInfo(ActionContext context, String path, Key key) throws Exception {
    //Access the license to get the email address
    String licenseFile = path + "init" + fs + "input.txt";
    String licenseXml = StringUtils.loadText(licenseFile);
    XMLUtils xml = new XMLUtils(PrivateString.decrypt(key, licenseXml));
    String userAddress = XMLUtils.getNodeText(xml.getFirstChild("email"));
    context.getRequest().setAttribute("userAddress", userAddress);
  }

  public static void populateDatabaseUser(ActionContext context, String path, UserSetupBean bean) throws Exception {
    if (bean.getConfigured() == -1) {
      //Populate the data from the license
      String licenseFile = path + "init" + fs + "input.txt";
      String licenseXml = StringUtils.loadText(licenseFile);
      Key key = Setup.getKey(context);
      XMLUtils xml = new XMLUtils(PrivateString.decrypt(key, licenseXml));
      bean.setNameFirst(
          XMLUtils.getNodeText(xml.getFirstChild("nameFirst")));
      bean.setNameLast(XMLUtils.getNodeText(xml.getFirstChild("nameLast")));
      bean.setCompany(XMLUtils.getNodeText(xml.getFirstChild("company")));
      bean.setEmail(XMLUtils.getNodeText(xml.getFirstChild("email")));
      bean.setUsername(
          bean.getNameFirst().toLowerCase() + "." + bean.getNameLast().toLowerCase());
    }
  }

  public static String populateUser(Connection db, String fileLibrary) throws Exception {
    // TODO: return null;
    // TODO: From Users.java
    //Load the license and see how many users can be added
    java.security.Key key = org.aspcfs.utils.PrivateString.loadKey(
        fileLibrary + "init" + fs + "zlib.jar");
    org.aspcfs.utils.XMLUtils xml = new org.aspcfs.utils.XMLUtils(
        org.aspcfs.utils.PrivateString.decrypt(
            key, org.aspcfs.utils.StringUtils.loadText(
            fileLibrary + "init" + fs + "input.txt")));
    String lpd = org.aspcfs.utils.XMLUtils.getNodeText(
        xml.getFirstChild("text2"));
    //Check the license
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(*) AS user_count " +
            "FROM \"access\" a, \"role\" r " +
            "WHERE a.user_id > 0 " +
            "AND a.role_id > 0 " +
            "AND a.role_id = r.role_id " +
            "AND r.role_type = ? " +
            "AND a.enabled = ? ");
    pst.setInt(1, Constants.ROLETYPE_REGULAR);
    pst.setBoolean(2, true);
    ResultSet rs = pst.executeQuery();
    int current = 0;
    if (rs.next()) {
      current = rs.getInt("user_count");
    }
    rs.close();
    pst.close();
    if (!"-1".equals(lpd.substring(7)) && current >= Integer.parseInt(
        lpd.substring(7))) {
      return ("LicenseError");
    }
    return null;
  }


  public static String populateLicense(ActionContext context, SystemStatus systemStatus) {
    // TODO: return null;
    //Prepare to change the prefs
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute(
        "applicationPrefs");
    if (prefs == null) {
      return "ModifyError";
    }
    //Process the parameters
    String process = context.getRequest().getParameter("doLicense");
    try {
      //If remote check, then perform steps similar to the initial setup
      if ("internet".equals(process)) {
        UpdateBean bean = new UpdateBean();
        //Send the current key, email address, profile, and crc
        java.security.Key key = org.aspcfs.utils.PrivateString.loadKey(
            getPref(context, "FILELIBRARY") + "init" + fs + "zlib.jar");
        XMLUtils xml = new XMLUtils(
            org.aspcfs.utils.PrivateString.decrypt(
                key,
                StringUtils.loadText(
                    getPref(context, "FILELIBRARY") + "init" + fs + "input.txt")));
        //Encode the license for transmission
        BASE64Encoder encoder = new BASE64Encoder();
        bean.setZlib(encoder.encode(ObjectUtils.toByteArray(key)));
        bean.setEmail(XMLUtils.getNodeText(xml.getFirstChild("email")));
        bean.setProfile(XMLUtils.getNodeText(xml.getFirstChild("profile")));
        bean.setText(PrivateString.encrypt(key, "UPGRADE-1.0"));
        bean.setText2(XMLUtils.getNodeText(xml.getFirstChild("text2")));
        //Make sure the server received the key ok
        String response = null;
        boolean ssl = true;
        if (ssl) {
          response = HTTPUtils.sendPacket(
              "https://registration.centriccrm.com/LicenseServer.do?command=RequestLicense", bean.toXmlString());
        } else {
          response = HTTPUtils.sendPacket(
              "http://registration.centriccrm.com/LicenseServer.do?command=RequestLicense", bean.toXmlString());
        }
        if (response == null) {
          context.getRequest().setAttribute(
              "actionError", systemStatus.getLabel(
                  "object.validation.unspecifiedErrorNoResponse"));
          return "LicenseCheckERROR";
        }
        XMLUtils responseXML = new XMLUtils(response);
        TransactionStatus thisStatus = new TransactionStatus(
            responseXML.getFirstChild("response"));
        if (thisStatus.getStatusCode() != 0) {
          context.getRequest().setAttribute(
              "actionError", systemStatus.getLabel(
                  "object.validation.unspecifiedErrorRequestRejected") + thisStatus.getMessage());
          return "LicenseCheckERROR";
        }
        //Response is good so save the new license
        RecordList recordList = thisStatus.getRecordList();
        if (recordList != null && recordList.getName().equals("license") && !recordList.isEmpty()) {
          Record record = (Record) recordList.get(0);
          if (record != null && record.getAction().equals("update")) {
            String updatedLicense = (String) record.get("license");
            //Validate and save it
            XMLUtils xml2 = new XMLUtils(
                PrivateString.decrypt(key, updatedLicense));
            String entered = XMLUtils.getNodeText(
                xml2.getFirstChild("entered"));
            if (entered == null) {
              return "LicenseCheckERROR";
            }
            StringUtils.saveText(
                getPref(context, "FILELIBRARY") + "init" + fs + "input.txt", updatedLicense);
            context.getServletContext().setAttribute(
                "APP_TEXT", XMLUtils.getNodeText(
                    xml2.getFirstChild("edition")));
            String text2 = XMLUtils.getNodeText(xml2.getFirstChild("text2")).substring(
                7);
            if ("-1".equals(text2)) {
              context.getServletContext().removeAttribute("APP_SIZE");
            } else {
              context.getServletContext().setAttribute("APP_SIZE", text2);
            }
            context.getServletContext().setAttribute(
                "APP_ORGANIZATION", XMLUtils.getNodeText(
                    xml2.getFirstChild("company")));
            return "LicenseUpdatedOK";
          }
        }
      }
      //If manual input, then allow user to input the license code
      if ("manual".equals(process)) {

      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return "LicenseCheckERROR";
    }
    return "LicenseCheckOK";
  }

  public static String populateSecurityHook(ApplicationPrefs applicationPrefs) {
    // TODO: return null
    //Version check
    if (applicationPrefs.isUpgradeable()) {
      return "UpgradeCheck";
    }
    return null;
  }

  public static void populateApplicationPrefs(ServletContext context, ApplicationPrefs prefs) {
    // Verify the license
    if (prefs.has("FILELIBRARY")) {
      String edition = null;
      String crc = null;
      try {
        File zlib = new File(
            prefs.get("FILELIBRARY") + "init" + fs + "zlib.jar");
        File input = new File(
            prefs.get("FILELIBRARY") + "init" + fs + "input.txt");
        if (zlib.exists() && input.exists()) {
          //If key and license exists, read in and parse
          java.security.Key key = org.aspcfs.utils.PrivateString.loadKey(
              prefs.get("FILELIBRARY") + "init" + fs + "zlib.jar");
          org.aspcfs.utils.XMLUtils xml = new org.aspcfs.utils.XMLUtils(
              org.aspcfs.utils.PrivateString.decrypt(
                  key, StringUtils.loadText(
                  prefs.get("FILELIBRARY") + "init" + fs + "input.txt")));
          //The edition will be shown
          edition = org.aspcfs.utils.XMLUtils.getNodeText(
              xml.getFirstChild("edition"));
          crc = org.aspcfs.utils.XMLUtils.getNodeText(
              xml.getFirstChild("text2"));
          if (edition != null) {
            context.setAttribute("APP_TEXT", edition);
            if ("-1".equals(crc.substring(7))) {
              context.removeAttribute("APP_SIZE");
            } else {
              context.setAttribute("APP_SIZE", crc.substring(7));
            }
          }
          //The licensed organization will be shown
          String organization = org.aspcfs.utils.XMLUtils.getNodeText(
              xml.getFirstChild("company"));
          if (organization != null) {
            context.setAttribute("APP_ORGANIZATION", organization);
          }
          //String gdot = org.aspcfs.utils.XMLUtils.getNodeText(xml.getFirstChild("text2"));
        }
      } catch (Exception e) {
        //Could not read in key and process license
      }
      //Set the edition text
      if (edition == null) {
        if ("true".equals(prefs.get("WEBSERVER.ASPMODE"))) {
          context.setAttribute("APP_TEXT", "Enterprise Edition");
        } else {
          context.removeAttribute("cfs.setup");
        }
      }
    }
  }

  public static String populateAdminConfig(String module) {
    if ("LICENSE".equals(module)) {
      return "ModifyLicenseOK";
    }
    return null;
  }

}