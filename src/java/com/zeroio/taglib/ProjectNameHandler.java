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
import com.darkhorseventures.database.*;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Constants;
/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    January 21, 2004
 *@version    $Id: ProjectNameHandler.java,v 1.1.2.2 2004/04/08 14:55:53
 *      rvasista Exp $
 */
public class ProjectNameHandler extends TagSupport {

  private int projectId = -1;


  /**
   *  Sets the id attribute of the ProjectNameHandler object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.projectId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the id attribute of the ProjectNameHandler object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.projectId = tmp;
  }


  /**
   *  Outputs the project name if found from the ProjectNameCache
   *
   *@return                   Description of the Return Value
   *@exception  JspException  Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {
      ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute("ConnectionElement");
      if (ce == null) {
        System.out.println("ProjectNameHandler-> ConnectionElement is null");
      }
      boolean output = false;
      Hashtable systemStatus = (Hashtable) pageContext.getServletContext().getAttribute("SystemStatus");
      if (systemStatus != null) {
        SystemStatus thisSystem = (SystemStatus) systemStatus.get(ce.getUrl());
        if (thisSystem != null) {
          HashMap projectCache = (HashMap) thisSystem.getObject(Constants.SYSTEM_PROJECT_NAME_LIST);
          if (projectCache != null) {
            String projectName = (String) projectCache.get(new Integer(projectId));
            if (projectName != null) {
              this.pageContext.getOut().write(StringUtils.toHtml(projectName));
              output = true;
            }
          }
        }
      }
      if (!output) {
        this.pageContext.getOut().write("&nbsp;");
      }
    } catch (Exception e) {
      throw new JspException("Username Error: " + e.getMessage());
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

