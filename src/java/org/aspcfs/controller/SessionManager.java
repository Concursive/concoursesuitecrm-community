package com.darkhorseventures.controller;

import java.util.HashMap;
import javax.servlet.http.HttpSession;
import org.theseus.actions.ActionContext;

/**
 *  Handles all sessions running on the web server
 *
 *@author     Mathur
 *@created    November 22, 2002
 *@version    $Id: SessionManager.java,v 1.1.2.1 2002/11/26 19:51:17 akhi_m Exp
 *      $
 */

public class SessionManager {

  public final static int ADD = 1;
  public final static int REMOVE = 2;
  HashMap sessions = new HashMap();


  /**
   *  Constructor for the SessionManager object
   */
  public SessionManager() { }


  //can be used later to increase performance by reducing rehash of HashMap each time.
  /*
   *  public SessionManager(int userCount) {
   *  sessions = new HashMap(userCount);
   *  }
   */
  /**
   *  Sets the sessions attribute of the SessionManager object
   *
   *@param  sessions  The new sessions value
   */
  public void setSessions(HashMap sessions) {
    this.sessions = sessions;
  }


  /**
   *  Gets all sessions running on this web server
   *
   *@return    The sessions value
   */
  public HashMap getSessions() {
    return sessions;
  }


  /**
   *  Adds a user to the HashMap in synch mode
   *
   *@param  userId   The feature to be added to the User attribute
   *@param  context  The feature to be added to the User attribute
   */
  public void addUser(ActionContext context, int userId) {
    UserSession thisSession = new UserSession(context);
    if (sessions.get(new Integer(userId)) == null) {
      synchUpdate(thisSession, userId, ADD);
    } else {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("SessionManager -- > User " + userId + " already has a session");
      }
    }
  }


  /**
   *  Adds/Removes an entry from HashMap in synch mode
   *
   *@param  thisSession  Description of the Parameter
   *@param  userId       Description of the Parameter
   *@param  action       Description of the Parameter
   */
  public void synchUpdate(UserSession thisSession, int userId, int action) {
    synchronized (this) {
      if (action == ADD) {
        sessions.put(new Integer(userId), thisSession);
        if (System.getProperty("DEBUG") != null) {
          System.out.println("SessionManager -- > User " + userId + " Added");
        }
      } else if (action == REMOVE) {
        sessions.remove(new Integer(userId));
        if (System.getProperty("DEBUG") != null) {
          System.out.println("SessionManager -- > User " + userId + " Removed");
        }
      }
    }
  }


  /**
   *  Remove a user from HashMap in synch mode
   *
   *@param  userId  Description of the Parameter
   */
  public void removeUser(int userId) {
    UserSession thisSession = (UserSession) sessions.get(new Integer(userId));
    if (thisSession != null) {
      synchUpdate(thisSession, userId, REMOVE);
    }
  }


  /**
   *  checks to see if the user is logged in
   *
   *@param  userId  Description of the Parameter
   *@return         The userLoggedIn value
   */
  public boolean isUserLoggedIn(int userId) {
    return sessions.containsKey(new Integer(userId));
  }


  /**
   *  Gets the userSession attribute of the SessionManager object
   *
   *@param  userId  Description of the Parameter
   *@return         The userSession value
   */
  public UserSession getUserSession(int userId) {
    if (sessions.get(new Integer(userId)) != null) {
      return (UserSession) sessions.get(new Integer(userId));
    }
    return null;
  }


  /**
   *  Replace a user's session<br>
   *  Is called when user logs from more than one machine
   *
   *@param  context  Description of the Parameter
   *@param  userId   Description of the Parameter
   *@return          Description of the Return Value
   */
  public UserSession replaceUserSession(ActionContext context, int userId) {
    removeUser(userId);
    addUser(context, userId);
    return getUserSession(userId);
  }
}

