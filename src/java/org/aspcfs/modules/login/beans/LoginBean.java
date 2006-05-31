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
package org.aspcfs.modules.login.beans;

import com.darkhorseventures.framework.beans.GenericBean;
import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.webdav.WebdavServlet;
import org.aspcfs.utils.PasswordHash;

/**
 * Used by the Login module for passing formatted data to the Login JSP
 *
 * @author chris
 * @version $Id$
 * @created July 2, 2001
 */
public class LoginBean extends GenericBean {
  private String message = "";
  private String username = "";
  private String password = "";
  private String webdavPassword = "";
  private String ldapPassword = "";
  private String redirectTo = null;

  /** Field names of this bean, used in place of hard-coded strings in ObjectValidator and /setup/configure_authentication_validate.jsp
   They are set in static {} block, so that their string values are not compiled into class files of jasper-processed JSP.
   Otherwise if these field names were changed in future, Tomcat's work subdirectory would need to be purged, so that JSPs got recompiled.
   */
  //public static final String USERNAME, PASSWORD, UNENCRYPTED_PASSWORD;
  //static {
  //  USERNAME= "username";
  //  PASSWORD="password";
  //  UNENCRYPTED_PASSWORD="unencryptedPassword";
  //}

  /**
   * Default constructor.
   *
   * @since 1.0
   */
  public LoginBean() { }


  /**
   * Sets the Username attribute of the object.
   *
   * @param tmp The new Username value
   * @since 1.0
   */
  public void setUsername(String tmp) {
    this.username = tmp;
  }


  /**
   * Sets the Password attribute of the object to the encrypted value of the
   * given value.
   *
   * @param tmp The new Password value
   * @since 1.0
   */
  public void setPassword(String tmp) {
    PasswordHash pwh = new PasswordHash();
    this.password = PasswordHash.encrypt(tmp);
    //Enrypt the webdav password. 'username' should be already set for a valid webdav password
    this.webdavPassword = PasswordHash.encrypt(
        username + ":" +
            WebdavServlet.CFS_USER_REALM + ":" + tmp);
    this.ldapPassword = tmp;
  }

  /**
   * Sets the Message attribute of the LoginBean object
   *
   * @param tmp The new Message value
   * @since 1.1
   */
  public void setMessage(String tmp) {
    this.message = tmp;
  }


  /**
   * Gets the Username attribute of the object.
   *
   * @return The Username value
   * @since 1.0
   */
  public String getUsername() {
    return username;
  }


  /**
   * Gets the password attribute of the object. The value stored in this object
   * is encrypted -- this method returns the encrypted value. The original
   * value is not stored.
   *
   * @return The Password value
   * @since 1.0
   */
  public String getPassword() {
    return password;
  }


  /**
   * Gets the Message attribute of the LoginBean object
   *
   * @return The Message value
   * @since 1.1
   */
  public String getMessage() {
    return message;
  }


  /**
   * Gets the webdavPassword attribute of the LoginBean object
   *
   * @return The webdavPassword value
   */
  public String getWebdavPassword() {
    return webdavPassword;
  }

  public String getLdapPassword() {
    return ldapPassword;
  }

  public String getRedirectTo() {
    return redirectTo;
  }

  public void setRedirectTo(String redirectTo) {
    this.redirectTo = redirectTo;
  }

  public void checkURL(ActionContext context) {
    if (redirectTo == null) {
        String requestedURL = (String) context.getRequest().getAttribute("requestedURL");
        if (requestedURL != null) {
          redirectTo = requestedURL;
        }
      }

  }
}
