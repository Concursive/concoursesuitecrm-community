package org.aspcfs.controller;

import java.util.HashMap;
import javax.servlet.http.HttpSession;
import com.darkhorseventures.framework.actions.ActionContext;
import javax.servlet.*;
import javax.servlet.http.*;

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
    this.addUser(context.getRequest(), userId);
  }


  /**
   *  Adds a feature to the User attribute of the SessionManager object, for
   *  classes that do not have access to an ActionContext, like the SecurityHook
   *
   *@param  request  The feature to be added to the User attribute
   *@param  userId   The feature to be added to the User attribute
   */
  public void addUser(HttpServletRequest request, int userId) {
    HttpSession session = request.getSession();
    UserSession thisSession = new UserSession();
    thisSession.setId(session.getId());
    thisSession.setIpAddress(request.getRemoteAddr());
    thisSession.setCreationTime(session.getCreationTime());
    if (sessions.get(new Integer(userId)) == null) {
      synchUpdate(thisSession, userId, ADD);
    } else {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("SessionManager-> User " + userId + " already has a session");
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
          System.out.println("SessionManager-> User " + userId + " Added");
        }
      } else if (action == REMOVE) {
        sessions.remove(new Integer(userId));
        if (System.getProperty("DEBUG") != null) {
          System.out.println("SessionManager-> User " + userId + " Removed");
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


  /**
   *  Returns the number of active sessions<br>
   *  NOTE: Not sure if expired sessions are removed
   *
   *@return    Description of the Return Value
   */
  public int size() {
    return sessions.size();
  }
}

