package org.aspcfs.controller;

import javax.servlet.http.HttpSession;
import com.darkhorseventures.framework.actions.ActionContext;

/**
 *  Stores a user's session data
 *
 *@author     Mathur
 *@created    November 22, 2002
 *@version    $Id$
 */

public class UserSession {

  String id = null;
  int maxInactiveInterval = -1;
  long lastAccessed = -1;
  long creationTime = -1;
  String ipAddress = null;


  /**
   *  Constructor for the UserSession object
   */
  public UserSession() { }


  /**
   *  Constructor for the UserSession object
   *
   *@param  context  Description of the Parameter
   */
  public UserSession(ActionContext context) {
    build(context);
  }


  /**
   *  Sets the id attribute of the UserSession object
   *
   *@param  id  The new id value
   */
  public void setId(String id) {
    this.id = id;
  }


  /**
   *  Sets the maxInactiveTimeInterval attribute of the UserSession object
   *
   *@param  maxInactiveInterval  The new maxInactiveInterval value
   */
  public void setMaxInactiveInterval(int maxInactiveInterval) {
    this.maxInactiveInterval = maxInactiveInterval;
  }


  /**
   *  Sets the lastAccessed attribute of the UserSession object
   *
   *@param  lastAccessed  The new lastAccessed value
   */
  public void setLastAccessed(long lastAccessed) {
    this.lastAccessed = lastAccessed;
  }


  /**
   *  Sets the ipAddress attribute of the UserSession object
   *
   *@param  ipAddress  The new ipAddress value
   */
  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }


  /**
   *  Sets the creationTime attribute of the UserSession object
   *
   *@param  creationTime  The new creationTime value
   */
  public void setCreationTime(long creationTime) {
    this.creationTime = creationTime;
  }


  /**
   *  Gets the creationTime attribute of the UserSession object
   *
   *@return    The creationTime value
   */
  public long getCreationTime() {
    return creationTime;
  }


  /**
   *  Gets the id attribute of the UserSession object
   *
   *@return    The id value
   */
  public String getId() {
    return id;
  }


  /**
   *  Gets the maxInactiveTimeInterval attribute of the UserSession object
   *
   *@return    The maxInactiveTimeInterval value
   */
  public int getMaxInactiveInterval() {
    return maxInactiveInterval;
  }


  /**
   *  Gets the lastAccessed attribute of the UserSession object
   *
   *@return    The lastAccessed value
   */
  public long getLastAccessed() {
    return lastAccessed;
  }


  /**
   *  Gets the ipAddress attribute of the UserSession object
   *
   *@return    The ipAddress value
   */
  public String getIpAddress() {
    return ipAddress;
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   */
  private void build(ActionContext context) {
    HttpSession session = context.getSession();
    id = session.getId();
    ipAddress = context.getIpAddress();
    creationTime = session.getCreationTime();
  }


  /**
   *  updates session attributes which might change with time
   *
   *@param  session  Description of the Parameter
   */
  private void update(HttpSession session) {
    lastAccessed = session.getLastAccessedTime();
  }

}

