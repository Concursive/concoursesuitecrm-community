//Copyright 2002 Dark Horse Ventures
package org.aspcfs.modules.service.base;

import com.darkhorseventures.controller.*;
import com.darkhorseventures.cfsbase.*;
import org.theseus.actions.*;
import java.util.*;

/**
 *  When a packet request is initiated through ProcessPacket, this context
 *  is created to pass information throughout the packet transactions.
 *
 *@author     matt rajkowski
 *@created    November 11, 2002
 *@version    $Id$
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


  /**
   *  Constructor for the PacketContext object
   */
  public PacketContext() { }


  /**
   *  Sets the authenticationItem attribute of the PacketContext object
   *
   *@param  tmp  The new authenticationItem value
   */
  public void setAuthenticationItem(AuthenticationItem tmp) {
    this.authenticationItem = tmp;
  }


  /**
   *  Sets the objectMap attribute of the PacketContext object
   *
   *@param  tmp  The new objectMap value
   */
  public void setObjectMap(HashMap tmp) {
    this.objectMap = tmp;
  }


  /**
   *  Sets the clientManager attribute of the PacketContext object
   *
   *@param  tmp  The new clientManager value
   */
  public void setClientManager(SyncClientManager tmp) {
    this.clientManager = tmp;
  }


  /**
   *  Sets the objectHookManager attribute of the PacketContext object
   *
   *@param  tmp  The new objectHookManager value
   */
  public void setObjectHookManager(ObjectHookManager tmp) {
    this.objectHookManager = tmp;
  }


  /**
   *  Sets the connectionPool attribute of the PacketContext object
   *
   *@param  tmp  The new connectionPool value
   */
  public void setConnectionPool(ConnectionPool tmp) {
    this.connectionPool = tmp;
  }


  /**
   *  Sets the connectionElement attribute of the PacketContext object
   *
   *@param  tmp  The new connectionElement value
   */
  public void setConnectionElement(ConnectionElement tmp) {
    this.connectionElement = tmp;
  }


  /**
   *  Sets the workflowManager attribute of the PacketContext object
   *
   *@param  tmp  The new workflowManager value
   */
  public void setWorkflowManager(WorkflowManager tmp) {
    this.workflowManager = tmp;
  }


  /**
   *  Sets the actionContext attribute of the PacketContext object
   *
   *@param  tmp  The new actionContext value
   */
  public void setActionContext(ActionContext tmp) {
    this.actionContext = tmp;
  }


  /**
   *  Sets the clientMap attribute of the PacketContext object
   *
   *@param  tmp  The new clientMap value
   */
  public void setClientMap(SyncClientMap tmp) {
    this.clientMap = tmp;
  }


  /**
   *  Gets the authenticationItem attribute of the PacketContext object
   *
   *@return    The authenticationItem value
   */
  public AuthenticationItem getAuthenticationItem() {
    return authenticationItem;
  }


  /**
   *  Gets the objectMap attribute of the PacketContext object
   *
   *@return    The objectMap value
   */
  public HashMap getObjectMap() {
    return objectMap;
  }


  /**
   *  Gets the clientManager attribute of the PacketContext object
   *
   *@return    The clientManager value
   */
  public SyncClientManager getClientManager() {
    return clientManager;
  }


  /**
   *  Gets the objectHookManager attribute of the PacketContext object
   *
   *@return    The objectHookManager value
   */
  public ObjectHookManager getObjectHookManager() {
    return objectHookManager;
  }


  /**
   *  Gets the connectionPool attribute of the PacketContext object
   *
   *@return    The connectionPool value
   */
  public ConnectionPool getConnectionPool() {
    return connectionPool;
  }


  /**
   *  Gets the connectionElement attribute of the PacketContext object
   *
   *@return    The connectionElement value
   */
  public ConnectionElement getConnectionElement() {
    return connectionElement;
  }


  /**
   *  Gets the workflowManager attribute of the PacketContext object
   *
   *@return    The workflowManager value
   */
  public WorkflowManager getWorkflowManager() {
    return workflowManager;
  }


  /**
   *  Gets the actionContext attribute of the PacketContext object
   *
   *@return    The actionContext value
   */
  public ActionContext getActionContext() {
    return actionContext;
  }


  /**
   *  Gets the clientMap attribute of the PacketContext object
   *
   *@return    The clientMap value
   */
  public SyncClientMap getClientMap() {
    return clientMap;
  }
}

