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
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import java.util.Hashtable;

/**
 * Description of the Class
 *
 * @author
 * @version $Id$
 * @created
 */
public class DocumentsRoleSelectHandler extends TagSupport implements TryCatchFinally {

  private String name = null;
  private int value = -1;
  private String onChange = null;
  private boolean isPortalUser = false;

  public void doCatch(Throwable throwable) throws Throwable {
    // Required but not needed
  }

  public void doFinally() {
    // Reset each property or else the value gets reused
    name = null;
    value = -1;
    onChange = null;
    isPortalUser = false;
  }

  /**
   * Sets the name attribute of the DocumentsRoleSelectHandler object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Sets the value attribute of the DocumentsRoleSelectHandler object
   *
   * @param tmp The new value value
   */
  public void setValue(String tmp) {
    this.value = Integer.parseInt(tmp);
  }


  /**
   * Sets the value attribute of the DocumentsRoleSelectHandler object
   *
   * @param tmp The new value value
   */
  public void setValue(int tmp) {
    this.value = tmp;
  }


  /**
   * Sets the onChange attribute of the DocumentsRoleSelectHandler object
   *
   * @param tmp The new onChange value
   */
  public void setOnChange(String tmp) {
    this.onChange = tmp;
  }

  /**
   * Sets the isPortalUser attribute of the DocumentsRoleSelectHandler object
   *
   * @param tmp The new isPortalUser value
   */
  public void setIsPortalUser(boolean tmp) {
    this.isPortalUser = tmp;
  }


  /**
   * Sets the isPortalUser attribute of the DocumentsRoleSelectHandler object
   *
   * @param tmp The new isPortalUser value
   */
  public void setIsPortalUser(String tmp) {
    this.isPortalUser = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws JspException Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {
      ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute(
          "ConnectionElement");
      if (ce == null) {
        System.out.println("RoleHandler-> ConnectionElement is null");
      }
      boolean output = false;
      SystemStatus systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute(
          "SystemStatus")).get(ce.getUrl());
      if (systemStatus != null) {
        LookupList roleList = (LookupList) systemStatus.getLookupList(
            null, "lookup_document_store_role");
        if (roleList != null && isPortalUser) {
          roleList = (LookupList) roleList.clone();
          //Remove the Manager that has a level 1.
          roleList.removeElementByLevel(1);
        }
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
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public int doEndTag() {
    return EVAL_PAGE;
  }

}

