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
    UserBean userSession = (UserBean)request.getSession().getAttribute("User");

    // Get the intended action, if going to the login module, then let it proceed
    String action = request.getServletPath();
    int slash = action.lastIndexOf("/");
    action = action.substring(slash + 1);

    if (userSession == null && !action.toUpperCase().startsWith("LOGIN") && !action.toUpperCase().startsWith("PROCESS")) {
      LoginBean failedSession = new LoginBean();
      failedSession.setMessage("* Please login, your session has expired");
      request.setAttribute("LoginBean", failedSession);
      return "SecurityCheck";
    } else {
      if (!action.toUpperCase().startsWith("PROCESS") && "true".equals((String)servlet.getServletConfig().getServletContext().getAttribute("ForceSSL")) &&
        "http".equals(request.getScheme())) {
        LoginBean failedSession = new LoginBean();
        failedSession.setMessage("* A secure connection is required");
        request.setAttribute("LoginBean", failedSession);
        if (System.getProperty("DEBUG") != null) {
          System.out.println("A secure connection is required");
        }
        return "SecurityCheck";
      }
      
      if (userSession != null) {
        request.setAttribute("moduleAction", action);
        ConnectionElement ce = (ConnectionElement)request.getSession().getAttribute("ConnectionElement");
        if (ce == null) {
          System.out.println("SecurityHook-> Fatal: CE is null");
        }
        SystemStatus systemStatus = (SystemStatus)((Hashtable)servlet.getServletConfig().getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
        
        if (userSession.getHierarchyCheck().before(systemStatus.getHierarchyCheck()) ||
            userSession.getPermissionCheck().before(systemStatus.getPermissionCheck())) {
          Connection db = null;
          try {
            db = ((ConnectionPool)servlet.getServletConfig().getServletContext().getAttribute("ConnectionPool")).getConnection(ce);
            
            if (userSession.getHierarchyCheck().before(systemStatus.getHierarchyCheck())) {
              if (System.getProperty("DEBUG") != null) System.out.println("SecurityHook-> ** Getting you a new user record");
              User updatedUser = systemStatus.getHierarchyList().getUser(userSession.getUserId());
              userSession.setUserRecord(updatedUser);
              userSession.setHierarchyCheck(new java.util.Date());
              System.out.println("SecurityHook-> Updating user session with new user record");
            }
        
            User updatedUser = userSession.getUserRecord();
            if (System.getProperty("DEBUG") != null) System.out.println("SecurityHook-> ** Getting you new permissions");
            if (userSession.getHierarchyCheck().before(systemStatus.getHierarchyCheck())) {
              updatedUser.setBuildContact(true);
            } else {
              updatedUser.setBuildContact(false);
            }
            updatedUser.setBuildPermissions(true);
            updatedUser.setBuildHierarchy(false);
            updatedUser.buildResources(db);
            userSession.setPermissionCheck(new java.util.Date());
            System.out.println("SecurityHook-> Updating user session with new permissions");
          } catch (SQLException e) {
          } finally {
            ((ConnectionPool)servlet.getServletConfig().getServletContext().getAttribute("ConnectionPool")).free(db);
          }
        }
      }
      return null;
    }
  }

}

