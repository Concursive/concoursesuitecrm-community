package org.aspcfs.modules.login.beans;

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import javax.servlet.http.*;
import javax.servlet.*;
import com.darkhorseventures.database.ConnectionElement;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.admin.base.User;

/**
 *  User Session record -- maintained while the user is logged in
 *
 *@author     matt rajkowski
 *@created    September 21, 2001
 *@version    $Id$
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

  //TODO: Remove these from the user's session because they can't be serialized
  private User userRecord = null;


  /**
   *  Constructor for the UserBean object
   */
  public UserBean() { }


  /**
   *  Sets the userId attribute of the UserBean object
   *
   *@param  tmp  The new userId value
   */
  public void setUserId(int tmp) {
    userId = tmp;
  }


  /**
   *  Sets the idRange attribute of the UserBean object
   *
   *@param  tmp  The new idRange value
   */
  public void setIdRange(String tmp) {
    this.idRange = tmp;
  }


  /**
   *  Sets the CssFile attribute of the userBean object
   *
   *@param  tmp  The new CssFile value
   *@since       1.14
   */
  public void setCssFile(String tmp) {
    this.cssFile = tmp;
  }


  /**
   *  Sets the Template attribute of the User object
   *
   *@param  tmp  The new Template value
   *@since       1.14
   */
  public void setTemplate(String tmp) {
    this.template = tmp;
  }


  /**
   *  Sets the PermissionCheck attribute of the User object
   *
   *@param  tmp  The new PermissionCheck value
   *@since       1.12
   */
  public void setPermissionCheck(java.util.Date tmp) {
    this.permissionCheck = tmp;
  }


  /**
   *  Sets the HierarchyCheck attribute of the User object
   *
   *@param  tmp  The new HierarchyCheck value
   *@since       1.12
   */
  public void setHierarchyCheck(java.util.Date tmp) {
    this.hierarchyCheck = tmp;
  }


  /**
   *  Sets the User attribute of the User object, added to support the
   *  SecurityCheck
   *
   *@param  tmp  The new User value
   *@since       1.13
   */
  public void setUserRecord(User tmp) {
    this.userRecord = tmp;
  }


  /**
   *  Sets the clientType attribute of the UserBean object
   *
   *@param  tmp  The new clientType value
   */
  public void setClientType(ClientType tmp) {
    this.clientType = tmp;
  }


  /**
   *  Sets the clientType attribute of the UserBean object
   *
   *@param  request  The new clientType value
   */
  public void setClientType(HttpServletRequest request) {
    this.clientType = new ClientType(request);
  }


  /**
   *  Sets the connectionElement attribute of the UserBean object
   *
   *@param  connectionElement  The new connectionElement value
   */
  public void setConnectionElement(ConnectionElement connectionElement) {
    this.connectionElement = connectionElement;
  }


  /**
   *  Sets the actualUserId attribute of the UserBean object
   *
   *@param  actualUserId  The new aliasId value
   */
  public void setActualUserId(int actualUserId) {
    this.actualUserId = actualUserId;
  }


  /**
   *  Gets the actualUserId which would be different in case of an aliased user.
   *
   *@return    The actualUserId value
   */
  public int getActualUserId() {
    return actualUserId;
  }


  /**
   *  Gets the systemStatus attribute of the UserBean object
   *
   *@param  config  Description of the Parameter
   *@return         The systemStatus value
   */
  public SystemStatus getSystemStatus(ServletConfig config) {
    return (this.getSystemStatus(config.getServletContext()));
  }


  /**
   *  Gets the systemStatus attribute of the UserBean object
   *
   *@param  context  Description of the Parameter
   *@return          The systemStatus value
   */
  public SystemStatus getSystemStatus(ServletContext context) {
    Hashtable globalStatus = (Hashtable) context.getAttribute("SystemStatus");
    return (SystemStatus) globalStatus.get(connectionElement.getUrl());
  }



  /**
   *  Gets the connectionElement attribute of the UserBean object
   *
   *@return    The connectionElement value
   */
  public ConnectionElement getConnectionElement() {
    return connectionElement;
  }


  /**
   *  Gets the User attribute of the User object
   *
   *@return    The User value
   *@since     1.10
   */
  public User getUserRecord() {
    return userRecord;
  }



  /**
   *  Returns the user's identifier
   *
   *@return    String
   *@since     1.14
   */
  public String getUsername() {
    return userRecord.getUsername();
  }


  /**
   *  Gets the UserId attribute of the userBean object
   *
   *@return    The UserId value
   *@since     1.14
   */
  public int getUserId() {
    return userId;
  }


  /**
   *  Gets the browserId attribute of the UserBean object
   *
   *@return    The browserId value
   */
  public String getBrowserId() {
    return clientType.getBrowserId();
  }


  /**
   *  Gets the browserVersion attribute of the UserBean object
   *
   *@return    The browserVersion value
   */
  public double getBrowserVersion() {
    return clientType.getBrowserVersion();
  }


  /**
   *  Gets the browserIdAndOS attribute of the UserBean object
   *
   *@return    The browserIdAndOS value
   */
  public String getBrowserIdAndOS() {
    return clientType.getBrowserIdAndOS();
  }


  /**
   *  Gets the Role attribute of the userBean object
   *
   *@return    The Role value
   *@since     1.14
   */
  public int getRoleId() {
    return getUserRecord().getRoleId();
  }


  /**
   *  Gets the Role attribute of the User object
   *
   *@return    The Role value
   *@since     1.9
   */
  public String getRole() {
    return getUserRecord().getRole();
  }


  /**
   *  Gets the CssFile attribute of the userBean object
   *
   *@return    The CssFile value
   *@since     1.14
   */
  public String getCssFile() {
    return cssFile;
  }


  /**
   *  Gets the FirstName attribute of the User object
   *
   *@return    The FirstName value
   *@since     1.14
   */
  public String getNameFirst() {
    return getUserRecord().getContact().getNameFirst();
  }


  /**
   *  Gets the LastName attribute of the User object
   *
   *@return    The LastName value
   *@since     1.14
   */
  public String getNameLast() {
    return getUserRecord().getContact().getNameLast();
  }


  /**
   *  Gets the nameFirstLast attribute of the UserBean object
   *
   *@return    The nameFirstLast value
   */
  public String getNameFirstLast() {
    return getUserRecord().getContact().getNameFirstLast();
  }


  /**
   *  Gets the Template attribute of the User object
   *
   *@return    The Template value
   *@since     1.14
   */
  public String getTemplate() {
    return template;
  }


  /**
   *  Gets the comma separated IdRange attribute of the User object. Each id
   *  represents a user id in which this user can access data for, including the
   *  user's own id.
   *
   *@return    The comma separated IdRange attribute
   *@since     1.12
   */
  public String getIdRange() {
    return idRange;
  }


  /**
   *  Gets the clientType attribute of the UserBean object
   *
   *@return    The clientType value
   */
  public ClientType getClientType() {
    return clientType;
  }


  /**
   *  Gets the PermissionCheck attribute of the User object
   *
   *@return    The PermissionCheck value
   *@since     1.12
   */
  public java.util.Date getPermissionCheck() {
    return permissionCheck;
  }


  /**
   *  Gets the HierarchyCheck attribute of the User object
   *
   *@return    The HierarchyCheck value
   *@since     1.12
   */
  public java.util.Date getHierarchyCheck() {
    return hierarchyCheck;
  }
}

