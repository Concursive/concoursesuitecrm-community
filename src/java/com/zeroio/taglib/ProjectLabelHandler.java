/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
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

