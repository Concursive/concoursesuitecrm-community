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
import org.aspcfs.utils.ObjectUtils;
import com.zeroio.iteam.base.Project;

/**
 *  Tag for retrieving the name of the specified tab
 *
 *@author     matt rajkowski
 *@created    September 2, 2003
 *@version    $Id: ProjectLabelHandler.java,v 1.1.2.1 2004/03/19 21:00:49
 *      rvasista Exp $
 */
public class ProjectLabelHandler extends TagSupport {

  private String name = null;
  private String object = null;


  /**
   *  Sets the name attribute of the ProjectLabelHandler object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the object attribute of the ProjectLabelHandler object
   *
   *@param  tmp  The new object value
   */
  public void setObject(String tmp) {
    this.object = tmp;
  }


  /**
   *  Description of the Method
   *
   *@return                   Description of the Return Value
   *@exception  JspException  Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {
      Project thisProject = (Project) pageContext.getRequest().getAttribute(object);
      String value = name;
      if (thisProject != null) {
        value = ObjectUtils.getParam(thisProject, "Label" + name);
      }
      if (value != null && !"".equals(value)) {
        pageContext.getOut().write(value);
      } else {
        pageContext.getOut().write(name);
      }
    } catch (Exception e) {
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

