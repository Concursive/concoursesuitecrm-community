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
package org.aspcfs.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import org.aspcfs.controller.*;
import org.aspcfs.modules.login.beans.UserBean;
import com.darkhorseventures.database.ConnectionElement;
import java.util.StringTokenizer;
import java.util.Hashtable;

/**
 *  This Class evaluates whether the current User's session has the given
 *  permissions. The user may need any single permission, one of several given
 *  permissions, or all of the given permissions to check.
 *
 *@author     Matt Rajkowski
 *@created    October 12, 2001
 *@version    $Id: PermissionHandler.java,v 1.7 2003/11/14 14:17:05 mrajkowski
 *      Exp $
 */
public class PermissionHandler extends TagSupport {
  private String permissionName = null;
  private boolean allRequired = false;
  private boolean hasNone = false;


  /**
   *  Sets the Name attribute of the PermissionHandler object
   *
   *@param  tmp  The new Name value
   *@since       1.1
   */
  public final void setName(String tmp) {
    permissionName = tmp;
  }


  /**
   *  Sets the All attribute of the PermissionHandler object. If set to true
   *  then the user must have all permissions passed in.
   *
   *@param  tmp  The new All value
   *@since       1.1
   */
  public void setAll(String tmp) {
    Boolean checkAll = new Boolean(tmp);
    this.allRequired = checkAll.booleanValue();
  }


  /**
   *  Sets the None attribute of the PermissionHandler object
   *
   *@param  tmp  The new None value
   *@since       1.1
   */
  public void setNone(String tmp) {
    Boolean checkNone = new Boolean(tmp);
    this.hasNone = checkNone.booleanValue();
  }


  /**
   *  Checks to see if the user has permission to access the body of the tag. A
   *  comma-separated list of permissions can be used to match any of the
   *  permissions.
   *
   *@return                   Description of the Returned Value
   *@exception  JspException  Description of Exception
   *@since                    1.1
   */
  public final int doStartTag() throws JspException {
    if (permissionName == null || "".equals(permissionName)) {
      return EVAL_BODY_INCLUDE;
    }
    boolean result = false;
    int matches = 0;
    int checks = 0;
    UserBean thisUser = (UserBean) pageContext.getSession().getAttribute("User");
    if (thisUser != null) {
      ConnectionElement ce = thisUser.getConnectionElement();
      SystemStatus systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
      StringTokenizer st = new StringTokenizer(permissionName, ",");
      while (st.hasMoreTokens()) {
        String thisPermission = st.nextToken();
        ++checks;
        if (systemStatus.hasPermission(thisUser.getUserId(), thisPermission)) {
          ++matches;
        }
      }
      if ((allRequired && matches > 0 && matches == checks) ||
          (!allRequired && matches > 0)) {
        result = true;
      }
    }

    //The request wants to know if the user does not have the permissions
    if (hasNone) {
      result = !result;
    }

    if (result) {
      return EVAL_BODY_INCLUDE;
    } else {
      return SKIP_BODY;
    }
  }

}

