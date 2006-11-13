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

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.utils.web.ClientType;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Hashtable;
import java.util.Locale;

/**
 *  User Session record -- maintained while the user is logged in
 *
 * @author     matt rajkowski
 * @created    September 21, 2001
 * @version    $Id: UserBean.java,v 1.21.12.1 2005/11/03 19:51:45 mrajkowski Exp
 *      $
 */
public class UserBean extends GenericBean {
  private int userId = -1;
  private int actualUserId = -1;
  private String template = null;
  private String cssFile = "";
  private java.util.Date permissionCheck = new java.util.Date();
  private java.util.Date hierarchyCheck = new java.util.Date();
  private String idRange = "";
  private ClientType clientType = null;
  private ConnectionElement connectionElement = null;
  private String sessionId = null;

  //TODO: Remove these from the user's session because they can't be serialized
  private User userRecord = null;


  /**
   *  Constructor for the UserBean object
   */
  public UserBean() { }


  /**
   *  Sets the userId attribute of the UserBean object
   *
   * @param  tmp  The new userId value
   */
  public void setUserId(int tmp) {
    userId = tmp;
  }


  /**
   *  Sets the idRange attribute of the UserBean object
   *
   * @param  tmp  The new idRange value
   */
  public void setIdRange(String tmp) {
    this.idRange = tmp;
  }


  /**
   *  Sets the CssFile attribute of the userBean object
   *
   * @param  tmp  The new CssFile value
   * @since       1.14
   */
  public void setCssFile(String tmp) {
    this.cssFile = tmp;
  }


  /**
   *  Sets the Template attribute of the User object
   *
   * @param  tmp  The new Template value
   * @since       1.14
   */
  public void setTemplate(String tmp) {
    this.template = tmp;
  }


  /**
   *  Sets the PermissionCheck attribute of the User object
   *
   * @param  tmp  The new PermissionCheck value
   * @since       1.12
   */
  public void setPermissionCheck(java.util.Date tmp) {
    this.permissionCheck = tmp;
  }


  /**
   *  Sets the HierarchyCheck attribute of the User object
   *
   * @param  tmp  The new HierarchyCheck value
   * @since       1.12
   */
  public void setHierarchyCheck(java.util.Date tmp) {
    this.hierarchyCheck = tmp;
  }


  /**
   *  Sets the User attribute of the User object, added to support the
   *  SecurityCheck
   *
   * @param  tmp  The new User value
   * @since       1.13
   */
  public void setUserRecord(User tmp) {
    this.userRecord = tmp;
  }


  /**
   *  Sets the clientType attribute of the UserBean object
   *
   * @param  tmp  The new clientType value
   */
  public void setClientType(ClientType tmp) {
    this.clientType = tmp;
  }


  /**
   *  Sets the clientType attribute of the UserBean object
   *
   * @param  request  The new clientType value
   */
  public void setClientType(HttpServletRequest request) {
    this.clientType = new ClientType(request);
  }


  /**
   *  Sets the connectionElement attribute of the UserBean object
   *
   * @param  connectionElement  The new connectionElement value
   */
  public void setConnectionElement(ConnectionElement connectionElement) {
    this.connectionElement = connectionElement;
  }


  /**
   *  Sets the actualUserId attribute of the UserBean object
   *
   * @param  actualUserId  The new aliasId value
   */
  public void setActualUserId(int actualUserId) {
    this.actualUserId = actualUserId;
  }


  /**
   *  Gets the actualUserId which would be different in case of an aliased user.
   *
   * @return    The actualUserId value
   */
  public int getActualUserId() {
    return actualUserId;
  }


  /**
   *  Gets the systemStatus attribute of the UserBean object
   *
   * @param  config  Description of the Parameter
   * @return         The systemStatus value
   */
  public SystemStatus getSystemStatus(ServletConfig config) {
    return (this.getSystemStatus(config.getServletContext()));
  }


  /**
   *  Gets the systemStatus attribute of the UserBean object
   *
   * @param  context  Description of the Parameter
   * @return          The systemStatus value
   */
  public SystemStatus getSystemStatus(ServletContext context) {
    Hashtable globalStatus = (Hashtable) context.getAttribute("SystemStatus");
    return (SystemStatus) globalStatus.get(connectionElement.getUrl());
  }


  /**
   *  Gets the connectionElement attribute of the UserBean object
   *
   * @return    The connectionElement value
   */
  public ConnectionElement getConnectionElement() {
    return connectionElement;
  }


  /**
   *  Gets the User attribute of the User object
   *
   * @return    The User value
   * @since     1.10
   */
  public User getUserRecord() {
    return userRecord;
  }


  /**
   *  Returns the user's identifier
   *
   * @return    String
   * @since     1.14
   */
  public String getUsername() {
    return userRecord.getUsername();
  }


  /**
   *  Gets the UserId attribute of the userBean object
   *
   * @return    The UserId value
   * @since     1.14
   */
  public int getUserId() {
    return userId;
  }


  /**
   *  Gets the browserId attribute of the UserBean object
   *
   * @return    The browserId value
   */
  public String getBrowserId() {
    return clientType.getBrowserId();
  }


  /**
   *  Gets the browserVersion attribute of the UserBean object
   *
   * @return    The browserVersion value
   */
  public double getBrowserVersion() {
    return clientType.getBrowserVersion();
  }


  /**
   *  Gets the Role attribute of the userBean object
   *
   * @return    The Role value
   * @since     1.14
   */
  public int getRoleId() {
    return getUserRecord().getRoleId();
  }


  /**
   *  Gets the Role attribute of the User object
   *
   * @return    The Role value
   * @since     1.9
   */
  public String getRole() {
    return getUserRecord().getRole();
  }


  /**
   *  Gets the roleType attribute of the UserBean object
   *
   * @return    The roleType value
   */
  public int getRoleType() {
    return getUserRecord().getRoleType();
  }


  /**
   *  Gets the siteId attribute of the UserBean object
   *
   * @return    The siteId value
   */
  public int getSiteId() {
    return getUserRecord().getSiteId();
  }


  /**
   *  Gets the orgId attribute of the UserBean object
   *
   * @return    The orgId value
   */
  public int getOrgId() {
    return getUserRecord().getContact().getOrgId();
  }


  /**
   *  Gets the CssFile attribute of the userBean object
   *
   * @return    The CssFile value
   * @since     1.14
   */
  public String getCssFile() {
    return cssFile;
  }


  /**
   *  Gets the contact attribute of the UserBean object
   *
   * @return    The contact value
   */
  public Contact getContact() {
    return getUserRecord().getContact();
  }


  /**
   *  Gets the contactName attribute of the UserBean object
   *
   * @return    The contactName value
   */
  public String getContactName() {
    return getUserRecord().getContact().getNameFirstLast();
  }


  /**
   *  Gets the locale attribute of the UserBean object
   *
   * @return    The locale value
   */
  public Locale getLocale() {
    return getUserRecord().getLocale();
  }


  /**
   *  Gets the timeZone attribute of the UserBean object
   *
   * @return    The timeZone value
   */
  public String getTimeZone() {
    return getUserRecord().getTimeZone();
  }


  /**
   *  Gets the currency attribute of the UserBean object
   *
   * @return    The currency value
   */
  public String getCurrency() {
    return getUserRecord().getCurrency();
  }


  /**
   *  Gets the Template attribute of the User object
   *
   * @return    The Template value
   * @since     1.14
   */
  public String getTemplate() {
    return template;
  }


  /**
   *  Gets the comma separated IdRange attribute of the User object. Each id
   *  represents a user id in which this user can access data for, including the
   *  user's own id.
   *
   * @return    The comma separated IdRange attribute
   * @since     1.12
   */
  public String getIdRange() {
    return getUserRecord().getIdRange();
  }


  /**
   *  Gets the clientType attribute of the UserBean object
   *
   * @return    The clientType value
   */
  public ClientType getClientType() {
    return clientType;
  }


  /**
   *  Gets the PermissionCheck attribute of the User object
   *
   * @return    The PermissionCheck value
   * @since     1.12
   */
  public java.util.Date getPermissionCheck() {
    return permissionCheck;
  }


  /**
   *  Gets the HierarchyCheck attribute of the User object
   *
   * @return    The HierarchyCheck value
   * @since     1.12
   */
  public java.util.Date getHierarchyCheck() {
    return hierarchyCheck;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }
}

