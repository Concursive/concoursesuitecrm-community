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
 *@version    $Id: RoleSelectHandler.java,v 1.1.2.2 2004/04/08 14:55:53 rvasista
 *      Exp $
 */
public class RoleSelectHandler extends TagSupport {

  private String name = null;
  private int value = -1;
  private String onChange = null;


  /**
   *  Sets the name attribute of the RoleSelectHandler object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the value attribute of the RoleSelectHandler object
   *
   *@param  tmp  The new value value
   */
  public void setValue(String tmp) {
    this.value = Integer.parseInt(tmp);
  }


  /**
   *  Sets the value attribute of the RoleSelectHandler object
   *
   *@param  tmp  The new value value
   */
  public void setValue(int tmp) {
    this.value = tmp;
  }


  /**
   *  Sets the onChange attribute of the RoleSelectHandler object
   *
   *@param  tmp  The new onChange value
   */
  public void setOnChange(String tmp) {
    this.onChange = tmp;
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
          if (onChange != null) {
            roleList.setJsEvent("onChange=\"" + onChange + "\"");
          }
          this.pageContext.getOut().write(roleList.getHtmlSelect(name, value));
          output = true;
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

