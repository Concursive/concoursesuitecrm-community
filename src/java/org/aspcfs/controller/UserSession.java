package org.aspcfs.controller;

import java.util.*;
import java.sql.*;
import javax.servlet.http.HttpSession;
import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.admin.base.*;

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
  HashMap viewpoints = null;


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
   *  Sets the viewpoints attribute of the UserSession object
   *
   *@param  viewpoints  The new viewpoints value
   */
  public void setViewpoints(HashMap viewpoints) {
    this.viewpoints = viewpoints;
  }


  /**
   *  Gets the viewpoints attribute of the UserSession object
   *
   *@return    The viewpoints value
   */
  public HashMap getViewpoints() {
    return viewpoints;
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


  /**
   *  Description of the Method
   */
  public void invalidateViewpoints() {
    viewpoints = null;
  }


  /**
   *  Builds Viewpoints for a single permission
   *
   *@param  db                Description of the Parameter
   *@param  userId            Description of the Parameter
   *@param  permName          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildViewpoints(Connection db, String permName, int userId) throws SQLException {
    if(viewpoints == null){
      viewpoints = new HashMap();
    }
    //build the viewpoints
    ViewpointList vpList = new ViewpointList();
    vpList.setUserId(userId);
    vpList.setBuildResources(true);
    vpList.setIncludeEnabledOnly(true);
    vpList.buildList(db);

    //build details for each viewpoints and store them
    Iterator i = vpList.iterator();
    while (i.hasNext()) {
      Viewpoint thisVp = (Viewpoint) i.next();
      ViewpointPermissionList vpPermList = thisVp.getPermissionList();
      Iterator j = vpPermList.keySet().iterator();
      while (j.hasNext()) {
        Permission thisPermission = (Permission) vpPermList.get((String) j.next());
        String pName = thisPermission.getName();
        if (permName.equals(pName)) {
            if (viewpoints.containsKey(pName)) {
              ArrayList vpUsers = (ArrayList) viewpoints.get(pName);
              viewpoints.remove(pName);
              vpUsers.add(new Integer(thisVp.getVpUserId()));
              viewpoints.put(pName, vpUsers);
            } else {
              ArrayList vpUsers = new ArrayList();
              //if user is aliased insert the alias user Id
              if(thisVp.getVpUser().getAlias() != -1){
                vpUsers.add(new Integer(thisVp.getVpUser().getAlias()));
              }else{
                vpUsers.add(new Integer(thisVp.getVpUserId()));
              }
              viewpoints.put(pName, vpUsers);
            }
        }
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  userId            Description of the Parameter
   *@param  permName          Description of the Parameter
   *@return                   The viewpoints value
   *@exception  SQLException  Description of the Exception
   */
  public HashMap getViewpoints(Connection db, String permName, int userId) throws SQLException {
    // if viewpoints is null because viewpoints were updated or this module has never been demanded then build Viewpoints
    if (viewpoints == null || (viewpoints != null && !viewpoints.containsKey(permName))) {
      buildViewpoints(db, permName, userId);
    }
    return viewpoints;
  }


  /**
   *  Gets the viewpointValid attribute of the UserSession object
   *
   *@return    The viewpointValid value
   */
  public boolean isViewpointsValid() {
    if (viewpoints == null) {
      return false;
    }
    return true;
  }
}

