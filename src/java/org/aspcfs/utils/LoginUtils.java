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
package org.aspcfs.utils;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.LoginBean;
import org.aspcfs.modules.login.beans.UserBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Useful methods while validating a user login from various clients
 *
 * @author Ananth
 * @created December 27, 2005
 */
public class LoginUtils {
  private String username = null;
  private String password = null;

  private int userId = -1;
  private int roleId = -1;
  private String role = null;

  private int roleType = -1;
  private int aliasId = -1;
  private int tmpUserId = -1;
  private java.sql.Timestamp expires = null;
  private boolean hasWebdavAccess = false;
  private boolean hasHttpApiAccess = false;

  //resources
  private SystemStatus thisSystem = null;
  private ApplicationPrefs applicationPrefs = null;
  private String code = null;
  private UserBean thisUser = null;
  private boolean built = false;

  /**
   * Constructor for the LoginUtils object
   *
   * @param username Description of the Parameter
   * @param password Description of the Parameter
   * @param db       Description of the Parameter
   * @throws Exception Description of the Exception
   */
  public LoginUtils(Connection db, String username, String password) throws Exception {
    this.username = username;
    this.password = PasswordHash.encrypt(password);
    build(db);
  }


  /**
   * Constructor for the LoginUtils object
   *
   * @param loginBean Description of the Parameter
   * @param db        Description of the Parameter
   * @throws Exception Description of the Exception
   */
  public LoginUtils(Connection db, LoginBean loginBean) throws Exception {
    this.username = loginBean.getUsername();
    this.password = loginBean.getPassword();
    build(db);
  }


  /**
   * Gets the thisUser attribute of the LoginUtils object
   *
   * @return The thisUser value
   */
  public UserBean getUserBean() {
    return thisUser;
  }


  /**
   * Sets the systemStatus attribute of the LoginUtils object
   *
   * @param systemStatus The new systemStatus value
   */
  public void setSystemStatus(SystemStatus systemStatus) {
    this.thisSystem = systemStatus;
  }


  /**
   * Sets the applicationPrefs attribute of the LoginUtils object
   *
   * @param prefs The new applicationPrefs value
   */
  public void setApplicationPrefs(ApplicationPrefs prefs) {
    this.applicationPrefs = prefs;
  }


  /**
   * Gets the userId attribute of the LoginUtils object
   *
   * @return The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   * Gets the roleId attribute of the LoginUtils object
   *
   * @return The roleId value
   */
  public int getRoleId() {
    return roleId;
  }


  /**
   * Gets the role attribute of the LoginUtils object
   *
   * @return The role value
   */
  public String getRole() {
    return role;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws Exception Description of the Exception
   */
  private void build(Connection db) throws Exception {
    PreparedStatement pst = db.prepareStatement(
        "SELECT a." + DatabaseUtils.addQuotes(db, "password") + ", a.expires, a.alias, a.user_id, a.role_id, " +
            "a.allow_webdav_access, a.allow_httpapi_access, r." + DatabaseUtils.addQuotes(db, "role") + ", r.role_type " +
            "FROM " + DatabaseUtils.addQuotes(db, "access") + " a, " + DatabaseUtils.addQuotes(db, "role") + " r " +
            "WHERE a.role_id = r.role_id " +
            "AND " + DatabaseUtils.toLowerCase(db) + "(a.username) = ? " +
            "AND a.enabled = ? ");
    pst.setString(1, username);
    pst.setBoolean(2, true);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      thisUser = new UserBean();
      //store values temporarily
      code = rs.getString("password");
      roleId = rs.getInt("role_id");
      role = rs.getString("role");
      expires = rs.getTimestamp("expires");
      hasHttpApiAccess = rs.getBoolean("allow_httpapi_access");
      hasWebdavAccess = rs.getBoolean("allow_webdav_access");
      aliasId = rs.getInt("alias");
      tmpUserId = rs.getInt("user_id");
      roleType = rs.getInt("role_type");
    }

    built = true;

    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  private boolean hasExpired() {
    java.util.Date now = new java.util.Date();
    if (expires != null && now.after(expires)) {
      return true;
    }
    return false;
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @param db      Description of the Parameter
   * @return Description of the Return Value
   * @throws Exception Description of the Exception
   */
  public boolean isUserValid(ActionContext context, Connection db) throws Exception {
    return (isUserValid(context, db, null));
  }


  /**
   * Description of the Method
   *
   * @param context   Description of the Parameter
   * @param loginBean Description of the Parameter
   * @param db        Description of the Parameter
   * @return Description of the Return Value
   * @throws Exception Description of the Exception
   */
  public boolean isUserValid(ActionContext context, Connection db, LoginBean loginBean) throws Exception {
    if (!built) {
      this.build(db);
    }
    if (tmpUserId == -1) {
      // NOTE: This could be modified so that LDAP records get inserted into CRM on the fly if they do not exist yet
      // User record not found in database
      if (loginBean != null) {
        loginBean.setMessage("* " + (thisSystem != null ? thisSystem.getLabel("login.msg.invalidLoginInfo") : "Invalid Login Info"));
      }
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Login-> User record not found in database for: " + username.toLowerCase());
      }
    } else if (hasExpired()) {
      // Login expired
      if (loginBean != null) {
        loginBean.setMessage("* " + (thisSystem != null ? thisSystem.getLabel("login.msg.accountExpired") : "Account Expired"));
      }
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
        // Validate against Centric CRM
        if (code == null || code.trim().equals("") ||
            (!code.equals(password) && !context.getServletContext().getAttribute(
                "GlobalPWInfo").equals(password))) {
          if (loginBean != null) {
            loginBean.setMessage("* " + (thisSystem != null ? thisSystem.getLabel("login.msg.invalidLoginInfo") : "Invalid Login Info"));
          }
        } else {
          userId = tmpUserId;
        }
      }
    }

    if (tmpUserId > -1) {
      thisUser.setUserId(aliasId > 0 ? aliasId : userId);
      thisUser.setActualUserId(userId);
      thisUser.setClientType(context.getRequest());
    }

    return (userId > -1);
  }
  
  public boolean isPortalUserValid(Connection db) throws Exception {
    return (isPortalUserValid(db, null));
  }
  
  public boolean isPortalUserValid(Connection db, LoginBean loginBean) throws Exception {
    if (!built) {
      this.build(db);
    }
    if (tmpUserId == -1) {
      // NOTE: This could be modified so that LDAP records get inserted into CRM on the fly if they do not exist yet
      // User record not found in database
      if (loginBean != null) {
        loginBean.setMessage("* " + (thisSystem != null ? thisSystem.getLabel("login.msg.invalidLoginInfo") : "Invalid Login Info"));
      }
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Login-> User record not found in database for: " + username.toLowerCase());
      }
    } else if (hasExpired()) {
      // Login expired
      if (loginBean != null) {
        loginBean.setMessage("* " + (thisSystem != null ? thisSystem.getLabel("login.msg.accountExpired") : "Account Expired"));
      }
    } else {
    
        // Validate against Centric CRM
        if (code == null || code.trim().equals("") ||
            (!code.equals(password) )) {
          if (loginBean != null) {
            loginBean.setMessage("* " + (thisSystem != null ? thisSystem.getLabel("login.msg.invalidLoginInfo") : "Invalid Login Info"));
          }
        } else {
          userId = tmpUserId;
        }
     
    }

    if (tmpUserId > -1) {
      thisUser.setUserId(aliasId > 0 ? aliasId : userId);
      thisUser.setActualUserId(userId);

    }

    return (userId > -1);
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean hasHttpApiAccess(Connection db) throws Exception {
    if (!built) {
      build(db);
    }
    return hasHttpApiAccess;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean hasWebavAccess(Connection db) throws Exception {
    if (!built) {
      build(db);
    }
    return hasWebdavAccess;
  }
}

