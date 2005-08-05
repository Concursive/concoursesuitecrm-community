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

import com.darkhorseventures.database.ConnectionElement;
import com.zeroio.iteam.base.Project;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.ObjectUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Hashtable;

/**
 * Tag for retrieving the name of the specified tab
 *
 * @author matt rajkowski
 * @version $Id: ProjectLabelHandler.java,v 1.1.2.1 2004/03/19 21:00:49
 *          rvasista Exp $
 * @created September 2, 2003
 */
public class ProjectLabelHandler extends TagSupport {

  private String name = null;
  private String object = null;
  private String type = null;


  /**
   * Sets the type attribute of the ProjectLabelHandler object
   *
   * @param tmp The new type value
   */
  public void setType(String tmp) {
    this.type = tmp;
  }


  /**
   * Sets the name attribute of the ProjectLabelHandler object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Sets the object attribute of the ProjectLabelHandler object
   *
   * @param tmp The new object value
   */
  public void setObject(String tmp) {
    this.object = tmp;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws JspException Description of the Exception
   */
  public int doStartTag() throws JspException {
    ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute(
        "ConnectionElement");
    if (ce == null) {
      System.out.println("ProjectLabelHandler-> ConnectionElement is null");
    }
    SystemStatus systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute(
        "SystemStatus")).get(ce.getUrl());
    if (systemStatus == null) {
      System.out.println("ProjectLabelHandler-> SystemStatus is null");
    }

    try {
      Project thisProject = (Project) pageContext.getRequest().getAttribute(
          object);
      String value = name;
      if (thisProject != null) {
        value = ObjectUtils.getParam(thisProject, "Label" + name);
      }
      if (value != null && !"".equals(value)) {
        pageContext.getOut().write(value);
      } else {
        //Check to see if there exists a translation for this display value
        if (systemStatus != null) {
          if (type != null && systemStatus.getLabel(type) != null) {
            name = systemStatus.getLabel(type);
          }
        }
        pageContext.getOut().write(name);
      }
    } catch (Exception e) {
    }
    return SKIP_BODY;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public int doEndTag() {
    return EVAL_PAGE;
  }

}

