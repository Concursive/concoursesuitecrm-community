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
package org.aspcfs.taglib;

import com.darkhorseventures.database.ConnectionElement;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.login.beans.UserBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Hashtable;

/**
 *  Description of the Class
 *
 * @author     akhi_m
 * @created    June 19, 2003
 * @version    $Id: TicketLogList.java,v 1.11 2003/03/07 14:47:27 mrajkowski Exp
 *          $
 */
public class AuthorityHandler extends TagSupport {

  private String owner = null;


  /**
   *  Gets the owner attribute of the AuthorityHandler object
   *
   * @return    The owner value
   */
  public String getOwner() {
    return owner;
  }


  /**
   *  Sets the owner attribute of the AuthorityHandler object
   *
   * @param  tmp  The new owner value
   */
  public void setOwner(String tmp) {
    this.owner = tmp;
  }


  /**
   *  Sets the owner attribute of the AuthorityHandler object
   *
   * @param  tmp  The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = String.valueOf(tmp);
  }


  /**
   *  Description of the Method
   *
   * @return                Description of the Return Value
   * @throws  JspException  Description of the Exception
   */
  public final int doStartTag() throws JspException {
    try {
      UserBean thisUser = (UserBean) pageContext.getSession().getAttribute(
          "User");
      ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute(
          "ConnectionElement");
      SystemStatus systemStatus = null;
      if (ce != null) {
        systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute(
            "SystemStatus")).get(ce.getUrl());
      }

      int userId = ((UserBean) pageContext.getSession().getAttribute("User")).getUserId();
      String[] owners = owner.split(",");
      boolean flag = false;
      for (int i = 0; i < owners.length; i++) {
        String oneOwner = owners[i];
        if (userId == Integer.parseInt(oneOwner)) {
          flag = true;
          break;
        }
        User userRecord = systemStatus.getUser(userId);
        User childRecord = null;
        if (oneOwner != null && !"".equals(oneOwner.trim())) {
          childRecord = userRecord.getChild(Integer.parseInt(oneOwner));
        }
        if (childRecord != null) {
          flag = true;
          break;
        }
      }
      if (flag) {
        return EVAL_BODY_INCLUDE;
      }
    } catch (Exception e) {
      throw new JspException("ContactNameHandler-> Error: " + e.getMessage());
    }
    return SKIP_BODY;
  }
}

