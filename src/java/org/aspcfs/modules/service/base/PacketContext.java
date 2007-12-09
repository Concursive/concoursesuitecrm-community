/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.service.base;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.apps.workFlowManager.WorkflowManager;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.controller.objectHookManager.ObjectHookManager;
import org.aspcfs.modules.login.base.AuthenticationItem;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;

import java.util.HashMap;

/**
 *  When a packet request is initiated through ProcessPacket, this context is
 *  created to pass information throughout the packet transactions.
 *
 * @author     matt rajkowski
 * @created    November 11, 2002
 * @version    $Id$
 */
public class PacketContext {

  private AuthenticationItem authenticationItem = null;
  private HashMap objectMap = null;
  private SyncClientManager clientManager = null;
  private ObjectHookManager objectHookManager = null;
  private ConnectionPool connectionPool = null;
  private ConnectionElement connectionElement = null;
  private WorkflowManager workflowManager = null;
  private ActionContext actionContext = null;
  private SyncClientMap clientMap = null;
  private SystemStatus systemStatus = null;
  private UserBean userBean = null;
  private ApplicationPrefs applicationPrefs = null;
  private boolean disableSyncMap = false;


  /**
   *  Constructor for the PacketContext object
   */
  public PacketContext() { }


  /**
   *  Gets the systemStatus attribute of the PacketContext object
   *
   * @return    The systemStatus value
   */
  public SystemStatus getSystemStatus() {
    return systemStatus;
  }


  /**
   *  Sets the systemStatus attribute of the PacketContext object
   *
   * @param  tmp  The new systemStatus value
   */
  public void setSystemStatus(SystemStatus tmp) {
    this.systemStatus = tmp;
  }


  /**
   *  Gets the userBean attribute of the PacketContext object
   *
   * @return    The userBean value
   */
  public UserBean getUserBean() {
    return userBean;
  }


  /**
   *  Sets the userBean attribute of the PacketContext object
   *
   * @param  tmp  The new userBean value
   */
  public void setUserBean(UserBean tmp) {
    this.userBean = tmp;
  }


  /**
   *  Sets the authenticationItem attribute of the PacketContext object
   *
   * @param  tmp  The new authenticationItem value
   */
  public void setAuthenticationItem(AuthenticationItem tmp) {
    this.authenticationItem = tmp;
  }


  /**
   *  Sets the objectMap attribute of the PacketContext object
   *
   * @param  tmp  The new objectMap value
   */
  public void setObjectMap(HashMap tmp) {
    this.objectMap = tmp;
  }


  /**
   *  Sets the clientManager attribute of the PacketContext object
   *
   * @param  tmp  The new clientManager value
   */
  public void setClientManager(SyncClientManager tmp) {
    this.clientManager = tmp;
  }


  /**
   *  Sets the objectHookManager attribute of the PacketContext object
   *
   * @param  tmp  The new objectHookManager value
   */
  public void setObjectHookManager(ObjectHookManager tmp) {
    this.objectHookManager = tmp;
  }


  /**
   *  Sets the connectionPool attribute of the PacketContext object
   *
   * @param  tmp  The new connectionPool value
   */
  public void setConnectionPool(ConnectionPool tmp) {
    this.connectionPool = tmp;
  }


  /**
   *  Sets the connectionElement attribute of the PacketContext object
   *
   * @param  tmp  The new connectionElement value
   */
  public void setConnectionElement(ConnectionElement tmp) {
    this.connectionElement = tmp;
  }


  /**
   *  Sets the workflowManager attribute of the PacketContext object
   *
   * @param  tmp  The new workflowManager value
   */
  public void setWorkflowManager(WorkflowManager tmp) {
    this.workflowManager = tmp;
  }


  /**
   *  Sets the actionContext attribute of the PacketContext object
   *
   * @param  tmp  The new actionContext value
   */
  public void setActionContext(ActionContext tmp) {
    this.actionContext = tmp;
  }


  /**
   *  Sets the clientMap attribute of the PacketContext object
   *
   * @param  tmp  The new clientMap value
   */
  public void setClientMap(SyncClientMap tmp) {
    this.clientMap = tmp;
  }


  /**
   *  Gets the authenticationItem attribute of the PacketContext object
   *
   * @return    The authenticationItem value
   */
  public AuthenticationItem getAuthenticationItem() {
    return authenticationItem;
  }


  /**
   *  Gets the objectMap attribute of the PacketContext object
   *
   * @return    The objectMap value
   */
  public HashMap getObjectMap() {
    return objectMap;
  }


  /**
   *  Gets the clientManager attribute of the PacketContext object
   *
   * @return    The clientManager value
   */
  public SyncClientManager getClientManager() {
    return clientManager;
  }


  /**
   *  Gets the objectHookManager attribute of the PacketContext object
   *
   * @return    The objectHookManager value
   */
  public ObjectHookManager getObjectHookManager() {
    return objectHookManager;
  }


  /**
   *  Gets the connectionPool attribute of the PacketContext object
   *
   * @return    The connectionPool value
   */
  public ConnectionPool getConnectionPool() {
    return connectionPool;
  }


  /**
   *  Gets the connectionElement attribute of the PacketContext object
   *
   * @return    The connectionElement value
   */
  public ConnectionElement getConnectionElement() {
    return connectionElement;
  }


  /**
   *  Gets the workflowManager attribute of the PacketContext object
   *
   * @return    The workflowManager value
   */
  public WorkflowManager getWorkflowManager() {
    return workflowManager;
  }


  /**
   *  Gets the actionContext attribute of the PacketContext object
   *
   * @return    The actionContext value
   */
  public ActionContext getActionContext() {
    return actionContext;
  }


  /**
   *  Gets the clientMap attribute of the PacketContext object
   *
   * @return    The clientMap value
   */
  public SyncClientMap getClientMap() {
    return clientMap;
  }


  /**
   * @return    the applicationPrefs
   */
  public ApplicationPrefs getApplicationPrefs() {
    return applicationPrefs;
  }


  /**
   * @param  applicationPrefs  the applicationPrefs to set
   */
  public void setApplicationPrefs(ApplicationPrefs applicationPrefs) {
    this.applicationPrefs = applicationPrefs;
  }


  /**
   *  Gets the disableSyncMap attribute of the PacketContext object
   *
   * @return    The disableSyncMap value
   */
  public boolean getDisableSyncMap() {
    return disableSyncMap;
  }


  /**
   *  Sets the disableSyncMap attribute of the PacketContext object
   *
   * @param  tmp  The new disableSyncMap value
   */
  public void setDisableSyncMap(boolean tmp) {
    this.disableSyncMap = tmp;
  }


  /**
   *  Sets the disableSyncMap attribute of the PacketContext object
   *
   * @param  tmp  The new disableSyncMap value
   */
  public void setDisableSyncMap(String tmp) {
    this.disableSyncMap = DatabaseUtils.parseBoolean(tmp);
  }

}

