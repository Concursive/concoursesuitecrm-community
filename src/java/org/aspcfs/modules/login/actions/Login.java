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
    String gkDriver = (String) context.getServletContext().getAttribute("GKDRIVER");
    String gkHost = (String) context.getServletContext().getAttribute("GKHOST");
    String gkUser = (String) context.getServletContext().getAttribute("GKUSER");
    String gkUserPw = (String) context.getServletContext().getAttribute("GKUSERPW");
    String siteCode = (String) context.getServletContext().getAttribute("SiteCode");
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

    ///////////////////////////////////////////////////////////
    //	Get connected to Customer database,
    //	Validate this user.
    //
    UserBean thisUser = null;
    int userId = -1;
    int aliasId = -1;
    java.util.Date now = new java.util.Date();
    try {
      db = sqlDriver.getConnection(ce);
      //A good place to initialize this SystemStatus, must be done before getting a user
      SystemStatus thisSystem = SecurityHook.retrieveSystemStatus(context.getServletContext(), db, ce);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Login-> Retrieved SystemStatus from memory : " + ((thisSystem == null) ? "false" : "true"));
      }
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
      //Perform rest of user initialization if a valid user
      if (userId > -1) {
        thisUser = new UserBean();
        thisUser.setUserId(aliasId > 0 ? aliasId : userId);
        thisUser.setActualUserId(userId);
        //The user record must be in user cache to proceed
        User userRecord = thisSystem.getUser(thisUser.getUserId());
        if (userRecord != null) {
          if (System.getProperty("DEBUG") != null) {
            System.out.println("SystemStatus-> Retrieved user from memory: " + userRecord.getUsername());
          }
          thisUser.setConnectionElement(ce);
          thisUser.setClientType(context.getRequest());
          thisUser.setTemplate("template0");
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
      e.printStackTrace(System.out);
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
    context.getSession().setMaxInactiveInterval(thisSystem.getSessionTimeout());
    sessionManager.addUser(context, userId);
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
      SessionManager sessionManager = systemStatus.getSessionManager();
      UserSession oldSession = sessionManager.getUserSession(thisUser.getActualUserId());
      if (!oldSession.getId().equals(context.getRequest().getSession().getId())) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Login -- > Invalidating old Session");
        }
        UserSession currentSession = sessionManager.replaceUserSession(context, thisUser.getActualUserId());
        context.getSession().setMaxInactiveInterval(systemStatus.getSessionTimeout());
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

