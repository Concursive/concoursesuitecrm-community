package com.darkhorseventures.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.servlets.ControllerHook;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.cfsmodule.*;
import com.darkhorseventures.utils.*;
import java.util.Hashtable;
import java.sql.*;

/**
 *  Every request to the ServletController executes this code.
 *
 *@author     mrajkowski
 *@created    July 9, 2001
 *@version    $Id$
 */
public class SecurityHook implements ControllerHook {

  public final static String fs = System.getProperty("file.separator");
  
  /**
   *  Checks to see if a User session object exists, if not then the security
   *  check fails.<p>
   *
   *  The security check also compares the date/time of the user's permissions
   *  to the date/time someone changed the user's permissions in the database.
   *
   *@param  request  Description of Parameter
   *@param  servlet  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.1
   */
  public String securityCheck(Servlet servlet, HttpServletRequest request) {
    UserBean userSession = (UserBean) request.getSession().getAttribute("User");

    // Get the intended action, if going to the login module, then let it proceed
    String action = request.getServletPath();
    int slash = action.lastIndexOf("/");
    action = action.substring(slash + 1);
    
    //Login and Process modules bypass security and must implement their own
    if (action.toUpperCase().startsWith("LOGIN") ||
        action.toUpperCase().startsWith("PROCESS")) {
      return null;
    }

    //User is supposed to have a valid session, so fail security check
    if (userSession == null) {
      LoginBean failedSession = new LoginBean();
      failedSession.setMessage("* Please login, your session has expired");
      request.setAttribute("LoginBean", failedSession);
      return "SecurityCheck";
    }
    
    //Check to see if this site requires SSL
    if ("true".equals((String) servlet.getServletConfig().getServletContext().getAttribute("ForceSSL")) &&
        "http".equals(request.getScheme())) {
      LoginBean failedSession = new LoginBean();
      failedSession.setMessage("* A secure connection is required");
      request.setAttribute("LoginBean", failedSession);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("A secure connection is required");
      }
      return "SecurityCheck";
    }

    //Good so far...
    if (userSession != null) {
      ConnectionElement ce = userSession.getConnectionElement();
      if (ce == null) {
        System.out.println("SecurityHook-> Fatal: CE is null");
        return ("SystemError");
      }
      
      Hashtable globalStatus = (Hashtable) servlet.getServletConfig().getServletContext().getAttribute("SystemStatus");
      if (globalStatus == null) {
        //NOTE: This shouldn't occur
        System.out.println("SecurityHook-> Fatal: SystemStatus Hashtable is null!");
      }
      
      SystemStatus systemStatus = (SystemStatus) globalStatus.get(ce.getUrl());
      if (systemStatus == null) {
        //NOTE: This happens when the context is reloaded and the user's
        //session was serialized and reloaded
        Connection db = null;
        try {
          db = ((ConnectionPool) servlet.getServletConfig().getServletContext().getAttribute("ConnectionPool")).getConnection(ce);
          systemStatus = SecurityHook.retrieveSystemStatus(servlet.getServletConfig().getServletContext(), db, ce);
          systemStatus.getSessionManager().addUser(request, userSession.getUserId());
        } catch (Exception e) {
        } finally {
          ((ConnectionPool) servlet.getServletConfig().getServletContext().getAttribute("ConnectionPool")).free(db);
        }
      }
      request.setAttribute("moduleAction", action);
      
      //Check the session manager to see if this session is valid
      SessionManager thisManager = systemStatus.getSessionManager();
      UserSession sessionInfo = thisManager.getUserSession(userSession.getActualUserId());
      if (sessionInfo != null && !sessionInfo.getId().equals(request.getSession().getId()) && !action.toUpperCase().startsWith("LOGIN")) {
        if (request.getSession(false) != null) {
          request.getSession(false).invalidate();
        }
        LoginBean failedSession = new LoginBean();
        failedSession.setMessage("* Please login, your session expired because you logged in from " + sessionInfo.getIpAddress());
        request.setAttribute("LoginBean", failedSession);
        return "SecurityCheck";
      }
      
      //Calling getHierarchyCheck() and getPermissionCheck() will block the
      //user until hierarchy and permisions have been rebuilt
      if (userSession.getHierarchyCheck().before(systemStatus.getHierarchyCheck()) ||
          userSession.getPermissionCheck().before(systemStatus.getPermissionCheck())) {
        Connection db = null;
        try {
          db = ((ConnectionPool) servlet.getServletConfig().getServletContext().getAttribute("ConnectionPool")).getConnection(ce);
          if (userSession.getHierarchyCheck().before(systemStatus.getHierarchyCheck())) {
            if (System.getProperty("DEBUG") != null) {
              System.out.println("SecurityHook-> ** Getting you a new user record");
            }
            User updatedUser = systemStatus.getUser(userSession.getUserId());
            userSession.setUserRecord(updatedUser);
            userSession.setHierarchyCheck(new java.util.Date());
            if (System.getProperty("DEBUG") != null) {
              System.out.println("SecurityHook-> Updating user session with new user record");
            }
          }

          User updatedUser = userSession.getUserRecord();
          if (userSession.getHierarchyCheck().before(systemStatus.getHierarchyCheck())) {
            updatedUser.setBuildContact(true);
          } else {
            updatedUser.setBuildContact(false);
          }
          updatedUser.setBuildHierarchy(false);
          updatedUser.buildResources(db);
          userSession.setPermissionCheck(new java.util.Date());
        } catch (SQLException e) {
        } finally {
          ((ConnectionPool) servlet.getServletConfig().getServletContext().getAttribute("ConnectionPool")).free(db);
        }
      }
    }
    return null;
  }

  public static synchronized SystemStatus retrieveSystemStatus(ServletContext context, Connection db, ConnectionElement ce) throws SQLException {
    //SystemStatusList is created in InitHook
    Hashtable statusList = (Hashtable) context.getAttribute("SystemStatus");
    //Create the SystemStatus object if it does not exist for this connection,
    if (!statusList.containsKey(ce.getUrl())) {
      //synchronized (this) {
        if (!statusList.containsKey(ce.getUrl())) {
          SystemStatus newSystemStatus = new SystemStatus();
          newSystemStatus.setConnectionElement((ConnectionElement) ce.clone());
          newSystemStatus.setFileLibraryPath(
              context.getRealPath("/") + "WEB-INF" + fs +
              "fileLibrary" + fs + ce.getDbName() + fs);
          newSystemStatus.queryRecord(db);
          statusList.put(ce.getUrl(), newSystemStatus);
          if (System.getProperty("DEBUG") != null) {
            System.out.println("CFSModule-> Added new System Status object: " + ce.getUrl());
          }
        }
      //}
    }
    return (SystemStatus) statusList.get(ce.getUrl());
  }
}

