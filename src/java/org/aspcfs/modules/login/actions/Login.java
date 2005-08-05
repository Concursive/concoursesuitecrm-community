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
package org.aspcfs.modules.login.actions;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.LoginBean;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.system.base.Site;
import org.aspcfs.modules.system.base.SiteList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.StringUtils;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Hashtable;

/**
 * The Login module.
 *
 * @author mrajkowski
 * @version $Id$
 * @created July 9, 2001
 */
public final class Login extends CFSModule {

  public final static String fs = System.getProperty("file.separator");


  /**
   * Processes the user login
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   * @since 1.0
   */
  public String executeCommandLogin(ActionContext context) {
    ApplicationPrefs applicationPrefs = (ApplicationPrefs) context.getServletContext().getAttribute(
        "applicationPrefs");
    //Process the login request
    LoginBean loginBean = (LoginBean) context.getFormBean();
    String username = loginBean.getUsername();
    String password = loginBean.getPassword();
    String serverName = context.getRequest().getServerName();
    //Prepare the gatekeeper
    String gkDriver = getPref(context, "GATEKEEPER.DRIVER");
    String gkHost = getPref(context, "GATEKEEPER.URL");
    String gkUser = getPref(context, "GATEKEEPER.USER");
    String gkUserPw = getPref(context, "GATEKEEPER.PASSWORD");
    String siteCode = getPref(context, "GATEKEEPER.APPCODE");
    ConnectionElement gk = new ConnectionElement(gkHost, gkUser, gkUserPw);
    gk.setDriver(gkDriver);
    //Prepare the database connection
    ConnectionPool sqlDriver =
        (ConnectionPool) context.getServletContext().getAttribute(
            "ConnectionPool");
    if (sqlDriver == null) {
      loginBean.setMessage("Connection pool missing!");
      return "LoginRetry";
    }
    Connection db = null;
    ConnectionElement ce = null;
    //Connect to the gatekeeper, validate this host and get new connection info
    try {
      if ("true".equals(
          (String) context.getServletContext().getAttribute(
              "WEBSERVER.ASPMODE"))) {
        //Scan for the virtual host
        db = sqlDriver.getConnection(gk);
        SiteList siteList = new SiteList();
        siteList.setSiteCode(siteCode);
        siteList.setVirtualHost(serverName);
        siteList.buildList(db);
        if (siteList.size() > 0) {
          Site thisSite = (Site) siteList.get(0);
          ce = new ConnectionElement(
              thisSite.getDatabaseUrl(),
              thisSite.getDatabaseUsername(),
              thisSite.getDatabasePassword());
          ce.setDbName(thisSite.getDatabaseName());
          ce.setDriver(thisSite.getDatabaseDriver());
        } else {
          loginBean.setMessage(
              "* Access denied: Host does not exist (" +
              serverName + ")");
        }
      } else {
        //A single database is configured, so use it only regardless of ip/domain name
        ce = new ConnectionElement(gkHost, gkUser, gkUserPw);
        ce.setDbName(getPref(context, "GATEKEEPER.DATABASE"));
        ce.setDriver(gkDriver);
      }
    } catch (Exception e) {
      loginBean.setMessage("* Gatekeeper: " + e.getMessage());
    } finally {
      if (db != null) {
        sqlDriver.free(db);
      }
    }
    if (ce == null) {
      return "LoginRetry";
    }
    // Connect to the customer database and validate user
    UserBean thisUser = null;
    int userId = -1;
    int aliasId = -1;
    int roleId = -1;
    String role = null;
    String userId2 = null;
    java.util.Date now = new java.util.Date();
    boolean continueId = false;
    try {
      SystemStatus thisSystem = null;
      db = sqlDriver.getConnection(ce);
      // If system is not upgraded, perform lightweight validation to ensure backwards compatibility
      if (applicationPrefs.isUpgradeable()) {
        continueId = true;
      } else {
        //A good place to initialize this SystemStatus, must be done before getting a user
        thisSystem = SecurityHook.retrieveSystemStatus(
            context.getServletContext(), db, ce);
        if (System.getProperty("DEBUG") != null) {
          System.out.println(
              "Login-> Retrieved SystemStatus from memory : " + ((thisSystem == null) ? "false" : "true"));
        }
        // BEGIN DHV CODE ONLY
        //License check
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
                "SELECT count(*) AS user_count " +
                "FROM access a, \"role\" r " +
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
            userId2 = lpd.substring(7);
          } else {
            loginBean.setMessage(
                "* " + thisSystem.getLabel("login.msg.licenseNotFound"));
          }
        } catch (Exception e) {
          loginBean.setMessage(
              "* " + thisSystem.getLabel("login.msg.licenseNotUptoDate"));
        }
        // END DHV CODE ONLY
      }
      //Query the user record
      // BEGIN DHV CODE ONLY
      if (continueId) {
        // END DHV CODE ONLY
        PreparedStatement pst = db.prepareStatement(
            "SELECT a.password, a.expires, a.alias, a.user_id, a.role_id, r.\"role\" " +
            "FROM access a, \"role\" r " +
            "WHERE a.role_id = r.role_id " +
            "AND " + DatabaseUtils.toLowerCase(db) + "(a.username) = ? " +
            "AND a.enabled = ? ");
        pst.setString(1, username.toLowerCase());
        pst.setBoolean(2, true);
        ResultSet rs = pst.executeQuery();
        if (!rs.next()) {
          loginBean.setMessage(
              "* " + thisSystem.getLabel("login.msg.invalidLoginInfo"));
        } else {
          String pw = rs.getString("password");
          if (pw == null || pw.trim().equals("") || (!pw.equals(password) && !context.getServletContext().getAttribute(
              "GlobalPWInfo").equals(password))) {
            loginBean.setMessage(
                "* " + thisSystem.getLabel("login.msg.invalidLoginInfo"));
          } else {
            java.sql.Date expDate = rs.getDate("expires");
            if (expDate != null && now.after(expDate)) {
              loginBean.setMessage(
                  "* " + thisSystem.getLabel("login.msg.accountExpired"));
            } else {
              aliasId = rs.getInt("alias");
              userId = rs.getInt("user_id");
              roleId = rs.getInt("role_id");
              role = rs.getString("role");
            }
          }
        }
        rs.close();
        pst.close();
        // BEGIN DHV CODE ONLY
      }
      // END DHV CODE ONLY
      //Perform rest of user initialization if a valid user
      if (userId > -1) {
        thisUser = new UserBean();
        thisUser.setUserId(aliasId > 0 ? aliasId : userId);
        thisUser.setActualUserId(userId);
        thisUser.setConnectionElement(ce);
        thisUser.setClientType(context.getRequest());
        if (thisSystem != null) {
          //The user record must be in user cache to proceed
          User userRecord = thisSystem.getUser(thisUser.getUserId());
          if (userRecord != null) {
            if (System.getProperty("DEBUG") != null) {
              System.out.println(
                  "Login-> Retrieved user from memory: " + userRecord.getUsername());
            }
            thisUser.setIdRange(userRecord.getIdRange());
            thisUser.setUserRecord(userRecord);
            //Log that the user attempted login (does not necessarily mean logged in
            //anymore due to the single-session manager below
            userRecord.setIp(context.getIpAddress());
            userRecord.updateLogin(db);
            userRecord.checkWebdavAccess(
                db, context.getRequest().getParameter("password"));
          }
          if (!thisSystem.hasPermissions()) {
            System.out.println(
                "Login-> This system does not have any permissions loaded!");
          }
        } else {
          if (System.getProperty("DEBUG") != null) {
            System.out.println(
                "Login-> Fatal: User not found in this System!");
          }
        }
      } else {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Login-> Fatal: User does not have an Id!");
        }
      }
    } catch (Exception e) {
      loginBean.setMessage("* Access: " + e.getMessage());
      if (System.getProperty("DEBUG") != null) {
        e.printStackTrace(System.out);
      }
      thisUser = null;
    } finally {
      if (db != null) {
        sqlDriver.free(db);
      }
    }
    //If user record is not found, ask them to login again
    if (thisUser == null) {
      return "LoginRetry";
    }
    //A valid user must have this information in their session, or the
    //security manager will not let them access any secure pages
    context.getSession().setAttribute("User", thisUser);
    context.getSession().setAttribute("ConnectionElement", ce);
    if (applicationPrefs.isUpgradeable()) {
      if (roleId == 1 || "Administrator".equals(role)) {
        context.getSession().setAttribute("UPGRADEOK", "UPGRADEOK");
        return "PerformUpgradeOK";
      } else {
        return "UpgradeCheck";
      }
    } else {
      //Check to see if user is already logged in.
      //If not then add them to the valid users list
      SystemStatus thisSystem = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute(
          "SystemStatus")).get(ce.getUrl());
      SessionManager sessionManager = thisSystem.getSessionManager();
      if (sessionManager.isUserLoggedIn(userId)) {
        UserSession thisSession = sessionManager.getUserSession(userId);
        context.getSession().setMaxInactiveInterval(300);
        context.getRequest().setAttribute("Session", thisSession);
        return "LoginVerifyOK";
      }
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Login-> Session Size: " + sessionManager.size());
      }
      // BEGIN DHV CODE ONLY
      // NOTE: This check is no longer valid until portal users are tracked
      //if (userId2 != null && !userId2.equals("-1") && sessionManager.size() > Integer.parseInt(userId2)) {
      //  return "LicenseError";
      //}
      // END DHV CODE ONLY
      context.getSession().setMaxInactiveInterval(
          thisSystem.getSessionTimeout());
      sessionManager.addUser(context, userId);
    }
    // TODO: Replace this so it does not need to be maintained
    // NOTE: Make sure to update this similar code in the following method
    if (thisUser.getRoleType() == Constants.ROLETYPE_REGULAR) {
      return "LoginOK";
    } else if (thisUser.getRoleType() == Constants.ROLETYPE_CUSTOMER) {
      return "CustomerPortalLoginOK";
    } else if (thisUser.getRoleType() == Constants.ROLETYPE_PRODUCTS) {
      return "ProductsPortalLoginOK";
    }
    return "LoginOK";
  }


  /**
   * Confirms if the user wants to ovreride previous session or not.<br>
   * and informs Session Manager accordingly.
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandLoginConfirm(ActionContext context) {
    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
    if (thisUser == null) {
      return executeCommandLogout(context);
    }
    String action = context.getRequest().getParameter("override");
    if ("yes".equals(action)) {
      SystemStatus systemStatus = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute(
          "SystemStatus")).get(thisUser.getConnectionElement().getUrl());
      context.getSession().setMaxInactiveInterval(
          systemStatus.getSessionTimeout());
      //replace userSession in SessionManager HashMap & reset timeout
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Login-> Invalidating old Session");
      }
      SessionManager sessionManager = systemStatus.getSessionManager();
      sessionManager.replaceUserSession(context, thisUser.getActualUserId());
      // TODO: Replace this so it does not need to be maintained
      // NOTE: Make sure to update this similar code in the previous method
      if (thisUser.getRoleType() == Constants.ROLETYPE_REGULAR) {
        ApplicationPrefs applicationPrefs = (ApplicationPrefs) context.getServletContext().getAttribute(
            "applicationPrefs");
        if (applicationPrefs.isUpgradeable()) {
          if (thisUser.getRoleId() == 1 || "Administrator".equals(
              thisUser.getRole())) {
            return "PerformUpgradeOK";
          } else {
            return "UpgradeCheck";
          }
        }
        return "LoginOK";
      } else if (thisUser.getRoleType() == Constants.ROLETYPE_CUSTOMER) {
        return "CustomerPortalLoginOK";
      } else if (thisUser.getRoleType() == Constants.ROLETYPE_PRODUCTS) {
        return "ProductsPortalLoginOK";
      }
    } else {
      //logout user from current session
      return executeCommandLogout(context);
    }
    return "LoginOK";
  }


  /**
   * Used for invalidating a user session
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   * @since 1.1
   */
  public String executeCommandLogout(ActionContext context) {
    HttpSession oldSession = context.getRequest().getSession(false);
    if (oldSession != null) {
      oldSession.invalidate();
    }
    return "LoginRetry";
  }

}

