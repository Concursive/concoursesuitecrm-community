package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import org.theseus.actions.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.controller.SystemStatus;
import java.util.Hashtable;

/**
 *  The CFS Login module.
 *
 *@author     mrajkowski
 *@created    July 9, 2001
 *@version    $Id$
 */
public final class Login extends GenericAction {

  /**
   *  Processes the user login
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.0
   */
  public String executeCommandLogin(ActionContext context) {

    LoginBean loginBean = (LoginBean)context.getFormBean();
    String username = loginBean.getUsername();
    String password = loginBean.getPassword();
    String serverName = context.getRequest().getServerName();
    String gkHost = (String)context.getServletContext().getAttribute("GKHOST");
    String gkUser = (String)context.getServletContext().getAttribute("GKUSER");
    String gkUserPw = (String)context.getServletContext().getAttribute("GKUSERPW");
    String siteCode = (String)context.getServletContext().getAttribute("SiteCode");
    String sql;

    ConnectionElement gk = new ConnectionElement(gkHost, gkUser, gkUserPw);
    ConnectionElement ce = null;
    Connection db = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    ConnectionPool sqlDriver =
        (ConnectionPool)context.getServletContext().getAttribute("ConnectionPool");
    if (sqlDriver == null) {
      loginBean.setMessage("Connection pool missing!");
      return "LoginRetry";
    }

    ///////////////////////////////////////////////////////////
    //	Get connected to gatekeeper database,
    //	Validate this host and get the assigned database
    //	name and credentials.
    //
    sql = "SELECT * FROM sites " +
        "WHERE sitecode = ? " +
        "AND vhost = ? ";
    try {
      db = sqlDriver.getConnection(gk);
      pst = db.prepareStatement(sql);
      pst.setString(1, siteCode);
      pst.setString(2, serverName);
      rs = pst.executeQuery();
      if (rs.next()) {
        String dbName = rs.getString("dbname");
        ce = new ConnectionElement(
            rs.getString("dbhost") + ":" + rs.getInt("dbport") +
            "/" + dbName,
            rs.getString("dbuser"),
            rs.getString("dbpw")
            );
        ce.setDbName(dbName);
      } else {
        loginBean.setMessage("* Access denied: Host does not exist ("
             + serverName + "@" + siteCode + ")");
      }
      rs.close();
      pst.close();

    } catch (Exception e) {
      loginBean.setMessage("* Gatekeeper: " + e.getMessage());
    }

    sqlDriver.free(db);

    if (ce == null) {
      return "LoginRetry";
    }

    ///////////////////////////////////////////////////////////
    //	Get connected to Customer database,
    //	Validate this user.
    //
    UserBean thisUser = null;
    try {
      int userId = -1;
      db = sqlDriver.getConnection(ce);
      
      //Create the SystemStatus object if it does not exist for this connection,
      //needs to be done before creating the user object
      if (!((Hashtable)context.getServletContext().getAttribute("SystemStatus")).containsKey(ce.getUrl())) {
        synchronized (this) {
          if (!((Hashtable)context.getServletContext().getAttribute("SystemStatus")).containsKey(ce.getUrl())) {
            ((Hashtable)context.getServletContext().getAttribute("SystemStatus")).put(ce.getUrl(), new SystemStatus(db));
            System.out.println("Login-> Added new System Status object: " + ce.getUrl());
          }
        }
      }
      
      sql = 
        "SELECT * FROM access " +
        "WHERE lower(username) = ? " +
        "AND enabled = true ";
      pst = db.prepareStatement(sql);
      pst.setString(1, username.toLowerCase());
      rs = pst.executeQuery();
      if (!rs.next()) {
        loginBean.setMessage("* Access denied: Username not found.");
      } else {
        String pw = rs.getString("password");
        if (pw == null || !pw.equals(password)) {
          loginBean.setMessage("* Access denied: Invalid password.");
        } else {
          userId = rs.getInt("user_id");
        }
      }
      rs.close();
      pst.close();

      if (userId > -1) {
        System.out.println("Login-> Getting user " + userId + " from memory");
        SystemStatus thisSystem = (SystemStatus)((Hashtable)context.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
        System.out.println("Login-> Getting SystemStatus from memory : " + ((thisSystem == null)?"false":"true"));
        thisUser = new UserBean(thisSystem, userId);
        if (thisUser != null) {
          System.out.println("Login-> updating user");
          thisUser.getUserRecord().updateLogin(db);
          thisUser.setBrowserType(context.getBrowser());
          thisUser.setTemplate("template0");
          thisUser.getUserRecord().setBuildContact(false);
          thisUser.getUserRecord().setBuildHierarchy(false);
          thisUser.getUserRecord().setBuildPermissions(true);
          thisUser.getUserRecord().buildResources(db);
        }
      }
    } catch (Exception e) {
      loginBean.setMessage("* Access: " + e.getMessage());
      e.printStackTrace(System.out);
      thisUser = null;
    }

    sqlDriver.free(db);

    if (thisUser == null) {
      return "LoginRetry";
    }

    ///////////////////////////////////////////////////////////
    //	Get user permissions (or dfeault permissions).
    //	(Continue if no permissions)
    //

    //thisUser.addPermission("modules{mycfs,companydirectory,contacts,leads,opportunities,accounts,troubletickets}", true);
    //thisUser.addPermission("globalitems{search,myitems,recentitems}", true);

    context.getSession().setAttribute("User", thisUser);
    context.getSession().setAttribute("ConnectionElement", ce);

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

