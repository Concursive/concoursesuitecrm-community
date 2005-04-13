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
package org.aspcfs.modules.admin.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    December 15, 2004
 * @version    $Id$
 */
public final class UsersList extends CFSModule {

  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    return executeCommandListUsers(context);
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandListUsers(ActionContext context) {
    if (!hasPermission(context, "myhomepage-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    String previous = (String) context.getRequest().getParameter("previous");
    String parent = (String) context.getRequest().getParameter("parent");
    User user = null;
    UserList completeList = new UserList();
    ArrayList openUsers = new ArrayList();
    getOpenUsers(context, openUsers);
    ArrayList images = new ArrayList();
    try {
      db = getConnection(context);
      user = new User(db, this.getUserId(context), true);
      if (previous != null && !"".equals(previous)) {
        buildImages(db, user.getShortChildList(), completeList, images, previous, openUsers);
      } else {
        buildImages(db, user.getShortChildList(), completeList, images, null, openUsers);
      }
      context.getRequest().setAttribute("user", user);
      context.getRequest().setAttribute("completeList", completeList);
      context.getRequest().setAttribute("images", images);
      if (parent == null || "".equals(parent)) {
        parent = "" + user.getId();
      }
      context.getRequest().setAttribute("openUsers", openUsers);
      context.getRequest().setAttribute("parent", parent);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Users", "Users List");
    return ("ListUserHierarchyOK");
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  children       Description of the Parameter
   * @param  all            Description of the Parameter
   * @param  images         Description of the Parameter
   * @param  previous       Description of the Parameter
   * @param  openUsers      Description of the Parameter
   * @exception  Exception  Description of the Exception
   */
  private void buildImages(Connection db, UserList children, UserList all, ArrayList images, String previous, ArrayList openUsers) throws Exception {
    Iterator childIterator = (Iterator) children.iterator();
    String currentString = null;
    if (previous != null) {
      currentString = previous + "&nbsp; &nbsp;";
    } else {
      currentString = "&nbsp; &nbsp;";
    }
    while (childIterator.hasNext()) {
      User child = (User) childIterator.next();
      String childString = null;
      boolean flag = checkUser(openUsers, "" + child.getId());
      if (child.getChildUsers().size() > 0 && childIterator.hasNext()) {
        childString = currentString + "<a href=\"javascript:" + (!flag ? "addUser" : "removeUser") + "('" + child.getId() + "');\"><img alt='' src='images/tree/tree5" + (flag ? "o" : "c") + ".gif' border='0' align='absmiddle' height=\"18\" width=\"19\"/></a>&nbsp;";
      } else if (child.getChildUsers().size() > 0 && !childIterator.hasNext()) {
        childString = currentString + "<a href=\"javascript:" + (!flag ? "addUser" : "removeUser") + "('" + child.getId() + "');\"><img alt='' src='images/tree/tree6" + (flag ? "o" : "c") + ".gif' border='0' align='absmiddle' height=\"18\" width=\"19\"/>&nbsp;";
      } else if (child.getChildUsers().size() == 0 && childIterator.hasNext()) {
        childString = currentString + "<img alt='' src='images/tree/tree3.gif\' border='0' align='absmiddle' height=\"18\" width=\"19\"/>&nbsp;";
      } else if (child.getChildUsers().size() == 0 && !childIterator.hasNext()) {
        childString = currentString + "<img alt='' src='images/tree/tree4.gif' border='0' align='absmiddle' height=\"18\" width=\"19\"/>&nbsp;";
      }
      all.add(child);
      images.add(childString);
      if (child.getShortChildList().size() > 0 && flag) {
        childString = currentString + "<img alt='' src='images/tree/tree2.gif' border='0' align='absmiddle' height=\"18\" width=\"19\"/>&nbsp;";
        buildImages(db, child.getShortChildList(), all, images, childString, openUsers);
      }
    }
  }


  /**
   *  Gets the openUsers attribute of the UsersList object
   *
   * @param  context    Description of the Parameter
   * @param  openUsers  Description of the Parameter
   */
  private void getOpenUsers(ActionContext context, ArrayList openUsers) {
    String hideUser = context.getRequest().getParameter("hideUser");
    boolean flag = false;
    if (hideUser != null && !"".equals(hideUser)) {
      flag = true;
    }
    for (int i = 1; ; i++) {
      String openUser = context.getRequest().getParameter("hidden_" + i);
      if (openUser != null && !"".equals(openUser)) {
        if ((!flag) || (flag && !openUser.equals(hideUser))) {
          if (!checkUser(openUsers, openUser)) {
            openUsers.add(openUser);
          }
        }
      } else {
        break;
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  list  Description of the Parameter
   * @param  str   Description of the Parameter
   * @return       Description of the Return Value
   */
  private boolean checkUser(ArrayList list, String str) {
    Iterator iterator = list.iterator();
    while (iterator.hasNext()) {
      String test = (String) iterator.next();
      if (test.equals(str)) {
        return true;
      }
    }
    return false;
  }

}

