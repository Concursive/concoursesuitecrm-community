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
import com.darkhorseventures.framework.hooks.CustomHook;
import org.aspcfs.controller.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.RolePermission;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.LoginBean;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.sync.utils.SyncUtils;
import org.aspcfs.modules.system.base.Site;
import org.aspcfs.modules.system.base.SiteList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.LDAPUtils;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

  public String executeCommandDefault(ActionContext context) {
    // Will need to use the following objects in some way...
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
    Connection db = null;
    try {
      Site thisSite = SecurityHook.retrieveSite(context.getServletContext(), context.getRequest());
      // Store a ConnectionElement in session for the LabelHandler to find the corresponding language
      ConnectionElement ce = thisSite.getConnectionElement();
      context.getSession().setAttribute("ConnectionElement", ce);
      db = getConnection(context, ce);
      // Load the system status for the corresponding site w/specified language
      SecurityHook.retrieveSystemStatus(context.getServletContext(), db, ce, thisSite.getLanguage());
    } catch (Exception e) {
      System.out.println("Login-> Default error: " + e.getMessage());
    } finally {
      freeConnection(context, db);
    }
    // Determine entry page
    String scheme = context.getRequest().getScheme();
    // If SSL is configured, but this user isn't using SSL, then go to the welcome page
    if ("true".equals((String) context.getServletContext().getAttribute("ForceSSL")) &&
        scheme.equals("http")) {
      context.getRequest().setAttribute("LAYOUT.JSP", prefs.get("LAYOUT.JSP.WELCOME"));
    } else {
      context.getRequest().setAttribute("LAYOUT.JSP", prefs.get("LAYOUT.JSP.LOGIN"));
    }
    return "IndexPageOK";
  }


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
    loginBean.checkURL(context);
    String username = loginBean.getUsername();
    String password = loginBean.getPassword();
    String serverName = context.getRequest().getServerName();
    // Throw out empty passwords
    if (password == null || "".equals(password.trim())) {
      return "LoginRetry";
    }
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
        (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");
    if (sqlDriver == null) {
      loginBean.setMessage("Connection pool missing!");
      return "LoginRetry";
    }
    Connection db = null;
    ConnectionElement ce = null;
    //Connect to the gatekeeper, validate this host and get new connection info
    try {
      if ("true".equals((String) context.getServletContext().getAttribute("WEBSERVER.ASPMODE")))
      {
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
        Site thisSite = SecurityHook.retrieveSite(context.getServletContext(), context.getRequest());
        thisSystem = SecurityHook.retrieveSystemStatus(
            context.getServletContext(), db, ce, thisSite.getLanguage());
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Login-> Retrieved SystemStatus from memory : " + ((thisSystem == null) ? "false" : "true"));
        }
        continueId = CustomHook.populateLoginContext(context, db, thisSystem, loginBean);
      }
      //Query the user record
      String pw = null;
      java.sql.Date expDate = null;
      int tmpUserId = -1;
      int roleType = -1;
      if (continueId) {
        // NOTE: The following is the simplest valid SQL that works
        // on all versions of Centric CRM.  It must not be
        // modified with new fields because .war users need to
        // be able to login first, before the upgrade has happened
        PreparedStatement pst = db.prepareStatement(
            "SELECT a." + DatabaseUtils.addQuotes(db, "password") + ", a.role_id, r." + DatabaseUtils.addQuotes(db, "role") + ", a.expires, a.alias, a.user_id, r.role_type " +
                "FROM " + DatabaseUtils.addQuotes(db, "access") + " a, " + DatabaseUtils.addQuotes(db, "role") + " r " +
                "WHERE a.role_id = r.role_id " +
                "AND " + DatabaseUtils.toLowerCase(db) + "(a.username) = ? " +
                "AND a.enabled = ? ");
        pst.setString(1, username.toLowerCase());
        pst.setBoolean(2, true);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
          pw = rs.getString("password");
          roleId = rs.getInt("role_id");
          role = rs.getString("role");
          expDate = rs.getDate("expires");
          aliasId = rs.getInt("alias");
          tmpUserId = rs.getInt("user_id");
          roleType = rs.getInt("role_type");
        }
        rs.close();
        pst.close();
        if (tmpUserId == -1) {
          // NOTE: This could be modified so that LDAP records get inserted into CRM on the fly if they do not exist yet

          // User record not found in database
          loginBean.setMessage(
              "* " + thisSystem.getLabel("login.msg.invalidLoginInfo"));
          if (System.getProperty("DEBUG") != null) {
            System.out.println("Login-> User record not found in database for: " + username.toLowerCase());
          }
        } else if (expDate != null && now.after(expDate)) {
          // Login expired
          loginBean.setMessage(
              "* " + thisSystem.getLabel("login.msg.accountExpired"));
        } else {
          // User exists, now verify password
          boolean ldapEnabled = "true".equals(applicationPrefs.get("LDAP.ENABLED"));
          if (ldapEnabled && roleType == Constants.ROLETYPE_REGULAR) {
            // See if the CRM username and password matches in LDAP
            int ldapResult = LDAPUtils.authenticateUser(applicationPrefs, db, loginBean);
            if (ldapResult == LDAPUtils.RESULT_VALID) {
              userId = tmpUserId;
            }
          } else {
            // Validate against Centric CRM for PortalRole users
            if (pw == null || pw.trim().equals("") ||
                (!pw.equals(password) && !context.getServletContext().getAttribute(
                    "GlobalPWInfo").equals(password))) {
              loginBean.setMessage(
                  "* " + thisSystem.getLabel("login.msg.invalidLoginInfo"));
            } else {
              userId = tmpUserId;
            }
          }
        }
      }
      //Perform rest of user initialization if a valid user
      if (userId > -1) {
        thisUser = new UserBean();
        thisUser.setSessionId(context.getSession().getId());
        thisUser.setUserId(aliasId > 0 ? aliasId : userId);
        thisUser.setActualUserId(userId);
        thisUser.setConnectionElement(ce);
        thisUser.setClientType(context.getRequest());
        if (thisSystem != null) {
          //The user record must be in user cache to proceed
          User userRecord = thisSystem.getUser(thisUser.getUserId());
          if (userRecord != null) {
            if (System.getProperty("DEBUG") != null) {
              System.out.println("Login-> Retrieved user from memory: " + userRecord.getUsername());
            }
            thisUser.setIdRange(userRecord.getIdRange());
            thisUser.setUserRecord(userRecord);
            //Log that the user attempted login (does not necessarily mean logged in
            //anymore due to the single-session manager below
            userRecord.setIp(context.getIpAddress());
            userRecord.updateLogin(db);
            if(!CFSModule.isOfflineMode(context)){
              userRecord.checkWebdavAccess(
                  db, context.getRequest().getParameter("password"));
            }
          }
          if (!thisSystem.hasPermissions()) {
            System.out.println("Login-> This system does not have any permissions loaded!");
          }
        } else {
          if (System.getProperty("DEBUG") != null) {
            System.out.println("Login-> Fatal: User not found in this System!");
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

    try{
      if (SyncUtils.isOfflineMode(applicationPrefs)){
        //Check state of Offline application
        SyncUtils.checkOfflineState(context.getServletContext());
        if(SyncUtils.isSyncConflict(applicationPrefs)){
          RolePermission.setReadOnlyOfflinePermissionsForAll(db, (SystemStatus) ((Hashtable) context.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl()));
        }
      }
    } catch (SQLException e) {
      loginBean.setMessage("* Access: " + e.getMessage());
      thisUser = null;
    } finally {
      if (db != null) {
        sqlDriver.free(db);
      }
    }

    //If user record is not found, ask them to login again
    if (thisUser == null) {
      if (isOfflineMode(context)) {
        //Offline Database Corrupt
        return "OfflineLoginERROR";
      }
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
      SystemStatus thisSystem = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
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
      context.getSession().setMaxInactiveInterval(
          thisSystem.getSessionTimeout());
      sessionManager.addUser(context, userId);
    }
    // TODO: Replace this so it does not need to be maintained
    // NOTE: Make sure to update this similar code in the following method
    String redirectTo = context.getRequest().getParameter("redirectTo");
    if (thisUser.getRoleType() == Constants.ROLETYPE_REGULAR) {
      if (redirectTo != null) {
        //context.getRequest().removeAttribute("PageLayout");
        return "RedirectURL";
      }
      return "LoginOK";
    } else if (thisUser.getRoleType() == Constants.ROLETYPE_CUSTOMER) {
      return "CustomerPortalLoginOK";
    } else if (thisUser.getRoleType() == Constants.ROLETYPE_PRODUCTS) {
      return "ProductsPortalLoginOK";
    }
    if (redirectTo != null) {
      //context.getRequest().removeAttribute("PageLayout");
      return "RedirectURL";
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
      SystemStatus systemStatus = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute("SystemStatus")).get(thisUser.getConnectionElement().getUrl());
      context.getSession().setMaxInactiveInterval(systemStatus.getSessionTimeout());
      //replace userSession in SessionManager HashMap & reset timeout
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Login-> Invalidating old Session");
      }
      SessionManager sessionManager = systemStatus.getSessionManager();
      sessionManager.replaceUserSession(context, thisUser.getActualUserId());
      // TODO: Replace this so it does not need to be maintained
      // NOTE: Make sure to update this similar code in the previous method
      if (thisUser.getRoleType() == Constants.ROLETYPE_REGULAR) {
        ApplicationPrefs applicationPrefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
        if (applicationPrefs.isUpgradeable()) {
          if (thisUser.getRoleId() == 1 || "Administrator".equals(thisUser.getRole()))
          {
            return "PerformUpgradeOK";
          } else {
            return "UpgradeCheck";
          }
        }
        String redirectTo = context.getRequest().getParameter("redirectTo");
        if (redirectTo != null) {
          context.getRequest().removeAttribute("PageLayout");
          return "RedirectURL";
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
    String redirectTo = context.getRequest().getParameter("redirectTo");
    if (redirectTo != null) {
      context.getRequest().removeAttribute("PageLayout");
      return "RedirectURL";
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
      oldSession.removeAttribute("User");
      oldSession.invalidate();
    }
    return "LoginRetry";
  }

}
