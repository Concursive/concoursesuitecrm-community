/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.util.*;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.controller.SystemStatus;
import com.darkhorseventures.database.ConnectionElement;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    June 19, 2003
 *@version    $Id$
 */
public class RoleHandler extends TagSupport {

  private int roleId = -1;


  /**
   *  Sets the id attribute of the RoleHandler object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.roleId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the id attribute of the RoleHandler object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.roleId = tmp;
  }


  /**
   *  Description of the Method
   *
   *@return                   Description of the Return Value
   *@exception  JspException  Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {
      ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute("ConnectionElement");
      if (ce == null) {
        System.out.println("RoleHandler-> ConnectionElement is null");
      }
      boolean output = false;
      SystemStatus systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
      if (systemStatus != null) {
        LookupList roleList = (LookupList) systemStatus.getLookupList(null, "lookup_project_role");
        if (roleList != null) {
          String role = roleList.getSelectedValue(roleId);
          if (role != null) {
            this.pageContext.getOut().write(StringUtils.toHtml(role));
            output = true;
          }
        }
      }
      if (!output) {
        this.pageContext.getOut().write("&nbsp;");
      }
    } catch (Exception e) {
      throw new JspException("RoleHandler Error: " + e.getMessage());
    }
    return SKIP_BODY;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public int doEndTag() {
    return EVAL_PAGE;
  }

}

