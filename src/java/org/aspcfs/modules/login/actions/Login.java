package org.aspcfs.modules.login.actions;

import com.darkhorseventures.framework.actions.*;
import com.darkhorseventures.database.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.Hashtable;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.controller.*;
import org.aspcfs.modules.system.base.SiteList;
import org.aspcfs.modules.system.base.Site;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.login.beans.LoginBean;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.utils.StringUtils;
import java.io.File;
import org.aspcfs.modules.base.Constants;

/**
 *  The CFS Login module.
 *
 *@author     mrajkowski
 *@created    July 9, 2001
 *@version    $Id$
 */
public final class Login extends CFSModule {

  public final static String fs = System.getProperty("file.separator");


  /**
   *  Processes the user login
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.0
   */
  public String executeCommandLogin(ActionContext context) {
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
        (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");
    if (sqlDriver == null) {
      loginBean.setMessage("Connection pool missing!");
      return "LoginRetry";
    }
    Connection db = null;
    ConnectionElement ce = null;
    //Connect to the gatekeeper, validate this host and get new connection info
    try {
      if ("true".equals((String) context.getServletContext().getAttribute("WEBSERVER.ASPMODE"))) {
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
          loginBean.setMessage("* Access denied: Host does not exist (" +
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
    String userId2 = null;
    java.util.Date now = new java.util.Date();
    boolean continueId = false;
    try {
      db = sqlDriver.getConnection(ce);
      //A good place to initialize this SystemStatus, must be done before getting a user
      SystemStatus thisSystem = SecurityHook.retrieveSystemStatus(context.getServletContext(), db, ce);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Login-> Retrieved SystemStatus from memory : " + ((thisSystem == null) ? "false" : "true"));
      }
      //Check the license
      try {
        File keyFile = new File(getPref(context, "FILELIBRARY") + "init" + fs + "zlib.jar");
        File inputFile = new File(getPref(context, "FILELIBRARY") + "init" + fs + "input.txt");
        if (keyFile.exists() && inputFile.exists()) {
          java.security.Key key = org.aspcfs.utils.PrivateString.loadKey(getPref(context, "FILELIBRARY") + "init" + fs + "zlib.jar");
          org.aspcfs.utils.XMLUtils xml = new org.aspcfs.utils.XMLUtils(org.aspcfs.utils.PrivateString.decrypt(key, StringUtils.loadText(getPref(context, "FILELIBRARY") + "init" + fs + "input.txt")));
          //The edition will be shown
          String lpd = org.aspcfs.utils.XMLUtils.getNodeText(xml.getFirstChild("text2"));
          PreparedStatement pst = db.prepareStatement(
              "SELECT count(*) AS user_count " +
              "FROM access a, role r " +
              "WHERE a.user_id > 0 " +
              "AND a.role_id > 0 " +
              "AND a.role_id = r.role_id " +
              "AND r.role_type = ? " +
              "AND a.enabled = ? ");
          pst.setInt(1, Constants.ROLETYPE_REGULAR);
          pst.setBoolean(2, true);
          ResultSet rs = pst.executeQuery();
          if (rs.next()) {
            if (rs.getInt("user_count") <= Integer.parseInt(lpd.substring(7)) || "-1".equals(lpd.substring(7))) {
              continueId = true;
            } else {
              loginBean.setMessage("* Access denied: License error");
            }
          }
          rs.close();
          pst.close();
          userId2 = lpd.substring(7);
        } else {
          loginBean.setMessage("* Access denied: License not found");
        }
      } catch (Exception e) {
        loginBean.setMessage("* Access denied: License is not up-to-date");
      }
      if (continueId) {
        //Query the user record
        PreparedStatement pst = db.prepareStatement(
            "SELECT password, expires, alias, user_id " +
            "FROM access " +
            "WHERE lower(username) = ? " +
            "AND enabled = ? ");
        pst.setString(1, username.toLowerCase());
        pst.setBoolean(2, true);
        ResultSet rs = pst.executeQuery();
        if (!rs.next()) {
          loginBean.setMessage("* Access denied: Invalid login information.");
        } else {
          String pw = rs.getString("password");
          if (pw == null || pw.trim().equals("") || (!pw.equals(password) && !context.getServletContext().getAttribute("GlobalPWInfo").equals(password))) {
            loginBean.setMessage("* Access denied: Invalid login information.");
          } else {
            java.sql.Date expDate = rs.getDate("expires");
            if (expDate != null && now.after(expDate)) {
              loginBean.setMessage("* Access denied: Account Expired.");
            } else {
              aliasId = rs.getInt("alias");
              userId = rs.getInt("user_id");
            }
          }
        }
        rs.close();
        pst.close();
      }
      //Perform rest of user initialization if a valid user
      if (userId > -1) {
        thisUser = new UserBean();
        thisUser.setUserId(aliasId > 0 ? aliasId : userId);
        thisUser.setActualUserId(userId);
        //The user record must be in user cache to proceed
        User userRecord = thisSystem.getUser(thisUser.getUserId());
        if (userRecord != null) {
          if (System.getProperty("DEBUG") != null) {
            System.out.println("Login-> Retrieved user from memory: " + userRecord.getUsername());
          }
          thisUser.setConnectionElement(ce);
          thisUser.setClientType(context.getRequest());
          thisUser.setIdRange(userRecord.getIdRange());
          thisUser.setUserRecord(userRecord);
          //Log that the user attempted login (does not necessarily mean logged in
          //anymore due to the single-session manager below
          userRecord.setIp(context.getIpAddress());
          userRecord.updateLogin(db);
        } else {
          if (System.getProperty("DEBUG") != null) {
            System.out.println("Login-> Fatal: User not found in this System!");
          }
          if (!thisSystem.hasPermissions()) {
            System.out.println("Login-> This system does not have any permissions loaded!");
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
    // NOTE: This check is no longer valid until portal users are tracked
    //if (userId2 != null && !userId2.equals("-1") && sessionManager.size() > Integer.parseInt(userId2)) {
    //  return "LicenseError";
    //}
    context.getSession().setMaxInactiveInterval(thisSystem.getSessionTimeout());
    sessionManager.addUser(context, userId);
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
   *  Confirms if the user wants to ovreride previous session or not.<br>
   *  and informs Session Manager accordingly.
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
   *  Used for invalidating a user session
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.1
   */
  public String executeCommandLogout(ActionContext context) {
    HttpSession oldSession = context.getRequest().getSession(false);
    if (oldSession != null) {
      oldSession.invalidate();
    }
    return "LoginRetry";
  }

}

