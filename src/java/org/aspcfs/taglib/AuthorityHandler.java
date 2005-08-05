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

import com.darkhorseventures.database.ConnectionElement;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.login.beans.UserBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Hashtable;

/**
 * Description of the Class
 *
 * @author akhi_m
 * @version $id: exp$
 * @created June 19, 2003
 */
public class AuthorityHandler extends TagSupport {

  private int owner = -1;


  /**
   * Sets the owner attribute of the AuthorityHandler object
   *
   * @param owner The new owner value
   */
  public void setOwner(int owner) {
    this.owner = owner;
  }


  /**
   * Sets the owner attribute of the AuthorityHandler object
   *
   * @param owner The new owner value
   */
  public void setOwner(String owner) {
    this.owner = Integer.parseInt(owner);
  }


  /**
   * Gets the owner attribute of the AuthorityHandler object
   *
   * @return The owner value
   */
  public int getOwner() {
    return owner;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws JspException Description of the Exception
   */
  public final int doStartTag() throws JspException {
    try {
      //get system status
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Getting system status ");
      }
      UserBean thisUser = (UserBean) pageContext.getSession().getAttribute(
          "User");
      ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute(
          "ConnectionElement");
      SystemStatus systemStatus = null;
      if (ce != null) {
        systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute(
            "SystemStatus")).get(ce.getUrl());
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Got system status ");
        }
      }

      int userId = ((UserBean) pageContext.getSession().getAttribute("User")).getUserId();
      if (userId == owner) {
        return EVAL_BODY_INCLUDE;
      }
      User userRecord = systemStatus.getUser(userId);
      User childRecord = userRecord.getChild(owner);
      if (childRecord != null) {
        return EVAL_BODY_INCLUDE;
      }
    } catch (Exception e) {
      throw new JspException("ContactNameHandler-> Error: " + e.getMessage());
    }
    return SKIP_BODY;
  }

}

