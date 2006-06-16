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
package org.aspcfs.controller;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.hooks.CustomHook;
import com.darkhorseventures.framework.servlets.ControllerHook;
import com.zeroio.iteam.base.ProjectList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.LoginBean;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.system.base.Site;
import org.aspcfs.modules.system.base.SiteList;
import org.aspcfs.utils.SiteUtils;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Every request to the ServletController executes this code.
 *
 * @author mrajkowski
 * @version $Id$
 * @created July 9, 2001
 */
public class SecurityHook implements ControllerHook {

  public final static String fs = System.getProperty("file.separator");


  /**
   * Checks to see if a User session object exists, if not then the security
   * check fails.<p>
   * <p/>
   * The security check also compares the date/time of the user's permissions
   * to the date/time someone changed the user's permissions in the database.
   *
   * @param request Description of Parameter
   * @param servlet Description of Parameter
   * @return Description of the Returned Value
   * @since 1.1
   */
  public String securityCheck(Servlet servlet, HttpServletRequest request) {
    UserBean userSession = (UserBean) request.getSession().getAttribute(
        "User");
    ConnectionElement ceSession = (ConnectionElement) request.getSession().getAttribute("ConnectionElement");

    // Get the intended action, if going to a public module or the login module, then let it proceed
    String action = request.getServletPath();
    int slash = action.lastIndexOf("/");
    action = action.substring(slash + 1);
    ApplicationPrefs applicationPrefs = (ApplicationPrefs) servlet.getServletConfig().getServletContext().getAttribute(
        "applicationPrefs");

    //Login and Process modules bypass security and must implement their own
    if (action.toUpperCase().startsWith("LOGIN") ||
        action.toUpperCase().startsWith("SETUP") ||
        action.toUpperCase().startsWith("UPGRADE") ||
        action.toUpperCase().startsWith("LICENSESERVER") ||
        action.toUpperCase().startsWith("PROCESS")) {
      return null;
    }

    // Special handling for Portal because the user interacts with database-
    // driven site
    if (action.toUpperCase().startsWith("PORTAL")) {
      Site thisSite = SecurityHook.retrieveSite(servlet.getServletConfig().getServletContext(), request);
      if (userSession == null || ceSession == null) {
        // Give them a connection element to snoop around the portal
        ceSession = thisSite.getConnectionElement();
        request.getSession().setAttribute("ConnectionElement", ceSession);
        // Allow portal mode to create a default session with guest capabilities
        userSession = new UserBean();
        userSession.setUserId(-2);
        userSession.setActualUserId(-2);
        userSession.setIdRange("-2");
        userSession.setConnectionElement(ceSession);
        userSession.setClientType(request);
        // TODO: Set a default time zone for user
        // TODO: Set a default currency for user
        // TODO: Set default locale: language, country
        // Trigger the tracker now that a ConnectionElement is in place...
        request.getSession().setAttribute("User", userSession);
      }
      Connection db = null;
      try {
        db = ((ConnectionPool) servlet.getServletConfig().getServletContext().getAttribute(
            "ConnectionPool")).getConnection(ceSession);
        SecurityHook.retrieveSystemStatus(servlet.getServletConfig().getServletContext(), db, ceSession, thisSite.getLanguage());
      } catch (Exception e) {
      } finally {
        ((ConnectionPool) servlet.getServletConfig().getServletContext().getAttribute(
            "ConnectionPool")).free(db);
      }
      return null;
    }

    String value = CustomHook.populateSecurityHook(applicationPrefs);
    if (value != null) {
      return value;
    }

    // URL forwarding
    String requestedURL = (String) request.getAttribute("requestedURL");
    if (requestedURL == null && "GET".equals(request.getMethod()) && request.getParameter("redirectTo") == null)
    {
      String uri = request.getRequestURI();
      String queryString = request.getQueryString();
      requestedURL = uri.substring(uri.lastIndexOf("/") + 1) + (queryString == null ? "" : "?" + queryString);
      request.setAttribute("requestedURL", requestedURL);
    }

    //User is supposed to have a valid session, so fail security check
    if (userSession == null || userSession.getUserId() < 0) {
      LoginBean failedSession = new LoginBean();
      failedSession.setMessage("* Please login, your session has expired");
      request.setAttribute("LoginBean", failedSession);
      return "SecurityCheck";
    }

    //Check to see if this site requires SSL
    if ("true".equals(
        (String) servlet.getServletConfig().getServletContext().getAttribute(
            "ForceSSL")) &&
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
    if (userSession != null && userSession.getUserId() > -1) {
      ConnectionElement ce = userSession.getConnectionElement();
      if (ce == null) {
        System.out.println("SecurityHook-> Fatal: CE is null");
        return ("SystemError");
      }

      Hashtable globalStatus = (Hashtable) servlet.getServletConfig().getServletContext().getAttribute(
          "SystemStatus");
      if (globalStatus == null) {
        //NOTE: This shouldn't occur
        System.out.println(
            "SecurityHook-> Fatal: SystemStatus Hashtable is null!");
      }

      SystemStatus systemStatus = (SystemStatus) globalStatus.get(ce.getUrl());
      if (systemStatus == null) {
        //NOTE: This happens when the context is reloaded and the user's
        //session was serialized and reloaded, but the systemStatus isn't reloaded
        Connection db = null;
        try {
          Site thisSite = retrieveSite(servlet.getServletConfig().getServletContext(), request);
          db = ((ConnectionPool) servlet.getServletConfig().getServletContext().getAttribute(
              "ConnectionPool")).getConnection(ce);
          systemStatus = SecurityHook.retrieveSystemStatus(
              servlet.getServletConfig().getServletContext(), db, ce, thisSite.getLanguage());
        } catch (Exception e) {
        } finally {
          ((ConnectionPool) servlet.getServletConfig().getServletContext().getAttribute(
              "ConnectionPool")).free(db);
        }
      }
      //For Help and QA button, set the request with the module that was selected
      String actionQuery = request.getQueryString();
      if (actionQuery == null || actionQuery.indexOf("actionSource") == -1) {
        request.setAttribute("moduleAction", action);
        request.setAttribute("moduleCommand", request.getParameter("command"));
        request.setAttribute("moduleSection", request.getParameter("section"));
      }
      //Check the session manager to see if this session is valid
      SessionManager thisManager = systemStatus.getSessionManager();
      UserSession sessionInfo = thisManager.getUserSession(
          userSession.getActualUserId());
      //The context reloaded and didn't reload the sessionManager userSession,
      //so add the user back
      if (sessionInfo == null) {
        request.getSession().setMaxInactiveInterval(
            systemStatus.getSessionTimeout());
        thisManager.addUser(request, userSession.getActualUserId());
      }
      //If the user has a different session than what's in the manager, log the user out
      if (sessionInfo != null &&
          !sessionInfo.getId().equals(request.getSession().getId())) {
        if (request.getSession(false) != null) {
          request.getSession(false).invalidate();
        }
        LoginBean failedSession = new LoginBean();
        failedSession.setMessage(
            "* Please login, your session expired because you logged in from " + sessionInfo.getIpAddress());
        request.setAttribute("LoginBean", failedSession);
        return "SecurityCheck";
      }
      //The context reloaded and didn't reload the sessionManager userSession,
      //so add the user back
      if (sessionInfo == null) {
        request.getSession().setMaxInactiveInterval(
            systemStatus.getSessionTimeout());
        thisManager.addUser(request, userSession.getActualUserId());
        sessionInfo = thisManager.getUserSession(
            userSession.getActualUserId());
      }
      // Set the last accessed time to track usage
      sessionInfo.setLastAccessed(System.currentTimeMillis());
      //Check to see if new permissions should be loaded...
      //Calling getHierarchyCheck() and getPermissionCheck() will block the
      //user until hierarchy and permisions have been rebuilt
      if (userSession.getHierarchyCheck().before(
          systemStatus.getHierarchyCheck()) ||
          userSession.getPermissionCheck().before(
              systemStatus.getPermissionCheck())) {
        Connection db = null;
        try {
          db = ((ConnectionPool) servlet.getServletConfig().getServletContext().getAttribute(
              "ConnectionPool")).getConnection(ce);
          if (userSession.getHierarchyCheck().before(
              systemStatus.getHierarchyCheck())) {
            if (System.getProperty("DEBUG") != null) {
              System.out.println(
                  "SecurityHook-> ** Getting you a new user record");
            }
            User updatedUser = systemStatus.getUser(userSession.getUserId());
            userSession.setUserRecord(updatedUser);
            userSession.setHierarchyCheck(new java.util.Date());
            if (System.getProperty("DEBUG") != null) {
              System.out.println(
                  "SecurityHook-> Updating user session with new user record");
            }
          }
          //Reload contact info
          User updatedUser = userSession.getUserRecord();
          if (userSession.getHierarchyCheck().before(
              systemStatus.getHierarchyCheck())) {
            updatedUser.setBuildContact(true);
          } else {
            updatedUser.setBuildContact(false);
          }
          updatedUser.setBuildHierarchy(false);
          updatedUser.buildResources(db);
          userSession.setPermissionCheck(new java.util.Date());
        } catch (SQLException e) {
        } finally {
          ((ConnectionPool) servlet.getServletConfig().getServletContext().getAttribute(
              "ConnectionPool")).free(db);
        }
      }
      // NOTE: Right now ALL users have the same currency and language as the system
      // In a later version, this will be removed.
      // This happens every request, just in case the application level preference changes
      userSession.getUserRecord().setCurrency(
          applicationPrefs.get("SYSTEM.CURRENCY"));
      userSession.getUserRecord().setLanguage(
          systemStatus.getLanguage());
    }
    return null;
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @param request Description of the Parameter
   * @return Description of the Return Value
   */
  public static Site retrieveSite(ServletContext context, HttpServletRequest request) {
    String serverName = request.getServerName();
    ConnectionPool sqlDriver =
        (ConnectionPool) context.getAttribute("ConnectionPool");
    ApplicationPrefs prefs = (ApplicationPrefs) context.getAttribute(
        "applicationPrefs");
    SiteList sites = SiteUtils.getSiteList(prefs, sqlDriver, serverName);
    if (sites.size() == 1) {
      return (Site) sites.get(0);
    }
    return null;
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @param ce      Description of the Parameter
   * @return Description of the Return Value
   */
  public static Site retrieveSite(ServletContext context, ConnectionElement ce) {
    ConnectionPool sqlDriver =
        (ConnectionPool) context.getAttribute("ConnectionPool");
    ApplicationPrefs prefs = (ApplicationPrefs) context.getAttribute(
        "applicationPrefs");
    SiteList sites = SiteUtils.getSiteList(prefs, sqlDriver, ce);
    if (sites.size() == 1) {
      return (Site) sites.get(0);
    }
    return null;
  }

  /**
   * Description of the Method
   *
   * @param context  Description of the Parameter
   * @param db       Description of the Parameter
   * @param ce       Description of the Parameter
   * @param language Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static synchronized SystemStatus retrieveSystemStatus(ServletContext context, Connection db, ConnectionElement ce, String language) throws SQLException {
    //SystemStatusList is created in InitHook
    Hashtable statusList = (Hashtable) context.getAttribute("SystemStatus");
    //Create the SystemStatus object if it does not exist for this connection,
    if (!statusList.containsKey(ce.getUrl())) {
      SystemStatus newSystemStatus = new SystemStatus();
      newSystemStatus.setConnectionElement((ConnectionElement) ce.clone());
      //Store the fileLibrary path for processes like Workflow
      ApplicationPrefs prefs = (ApplicationPrefs) context.getAttribute(
          "applicationPrefs");
      newSystemStatus.setFileLibraryPath(
          prefs.get("FILELIBRARY") + ce.getDbName() + fs);
      newSystemStatus.queryRecord(db);
      ConnectionPool cp = (ConnectionPool) context.getAttribute(
          "ConnectionPool");
      // Determine the URL for accessing this site
      String link = "";
      String url = "";
      if (prefs.has("WEBSERVER.URL")) {
        url = prefs.get("WEBSERVER.URL");
      } else {
        SiteList sites = SiteUtils.getSiteList(prefs, cp, ce);
        if (sites.size() > 0) {
          Site thisSite = (Site) sites.get(0);
          url = thisSite.getVirtualHost();
          // Port?
          String port = prefs.get("WEBSERVER.PORT");
          if (port != null && !port.equals("80") && !port.equals("443")) {
            url += ":" + port;
          }
          // Context?
          String contextName = prefs.get("WEBSERVER.CONTEXT");
          if (contextName != null) {
            url += contextName;
          }
        }
      }
      if ("true".equals(prefs.get("FORCESSL"))) {
        link = "https://" + url;
      } else {
        link = "http://" + url;
      }
      if (link.endsWith("/")) {
        link = link.substring(0, link.length() - 1);
      }
      newSystemStatus.setUrl(link);
      //Cache the role list for the RoleHandler taglib
      newSystemStatus.getLookupList(db, "lookup_project_role");
      statusList.put(ce.getUrl(), newSystemStatus);
      if (System.getProperty("DEBUG") != null) {
        System.out.println(
            "SecurityHook-> Added new System Status object: " + ce.getUrl());
      }
      //Cache the project names, updated by hooks
      HashMap projectNameCache = ProjectList.buildNameList(db);
      newSystemStatus.getObjects().put(
          Constants.SYSTEM_PROJECT_NAME_LIST, projectNameCache);
      //Give the systemStatus a handle to applicationPrefs for language support
      newSystemStatus.setApplicationPrefs(prefs);
      // Use application language setting
      if (language != null) {
        newSystemStatus.setLanguage(language);
      } else {
        // Default to the application's language
        newSystemStatus.setLanguage(prefs.get("SYSTEM.LANGUAGE"));
      }
      prefs.addDictionary(context, language);
      prefs.addIcelets(context, language);
      newSystemStatus.startServers(context);
    }
    return (SystemStatus) statusList.get(ce.getUrl());
  }
}

