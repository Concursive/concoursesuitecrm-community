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
import org.aspcfs.controller.SystemStatus;
import com.darkhorseventures.database.ConnectionElement;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.admin.base.UserList;
import java.util.*;

/**
 *  Description of the Class
 *
 *@author     Mathur
 *@created    March 25, 2003
 *@version    $Id: UserListSelectHandler.java,v 1.1 2003/03/26 22:31:44 akhi_m
 *      Exp $
 */
public class UserListSelectHandler extends TagSupport {
  private String selectName = null;
  private String jsEvent = null;
  private String emptyHtmlSelectRecord = null;
  private int defaultKey = -1;
  private boolean includeMe = false;
  private boolean excludeDisabledIfUnselected = false;


  /**
   *  Sets the jsEvent attribute of the UserListSelectHandler object
   *
   *@param  jsEvent  The new jsEvent value
   */
  public void setJsEvent(String jsEvent) {
    this.jsEvent = jsEvent;
  }


  /**
   *  Sets the emptyHtmlSelectRecord attribute of the UserListSelectHandler
   *  object
   *
   *@param  emptyHtmlSelectRecord  The new emptyHtmlSelectRecord value
   */
  public void setEmptyHtmlSelectRecord(String emptyHtmlSelectRecord) {
    this.emptyHtmlSelectRecord = emptyHtmlSelectRecord;
  }


  /**
   *  Sets the includeMe attribute of the UserListSelectHandler object
   *
   *@param  includeMe  The new includeMe value
   */
  public void setIncludeMe(boolean includeMe) {
    this.includeMe = includeMe;
  }


  /**
   *  Sets the includeMe attribute of the UserListSelectHandler object
   *
   *@param  tmp  The new includeMe value
   */
  public void setIncludeMe(String tmp) {
    this.includeMe = "true".equals(tmp);
  }


  /**
   *  Sets the excludeDisabledIfUnselected attribute of the
   *  UserListSelectHandler object
   *
   *@param  excludeDisabledIfUnselected  The new excludeDisabledIfUnselected
   *      value
   */
  public void setExcludeDisabledIfUnselected(boolean excludeDisabledIfUnselected) {
    this.excludeDisabledIfUnselected = excludeDisabledIfUnselected;
  }


  /**
   *  Sets the excludeDisabledIfUnselected attribute of the
   *  UserListSelectHandler object
   *
   *@param  tmp  The new excludeDisabledIfUnselected value
   */
  public void setExcludeDisabledIfUnselected(String tmp) {
    this.excludeDisabledIfUnselected = "true".equals(tmp);
  }


  /**
   *  Sets the defaultKey attribute of the UserListSelectHandler object
   *
   *@param  defaultKey  The new defaultKey value
   */
  public void setDefaultKey(int defaultKey) {
    this.defaultKey = defaultKey;
  }


  /**
   *  Sets the defaultKey attribute of the UserListSelectHandler object
   *
   *@param  tmp  The new defaultKey value
   */
  public void setDefaultKey(String tmp) {
    this.defaultKey = Integer.parseInt(tmp);
  }


  /**
   *  Sets the selectName attribute of the UserListSelectHandler object
   *
   *@param  selectName  The new selectName value
   */
  public void setSelectName(String selectName) {
    this.selectName = selectName;
  }


  /**
   *  Gets the defaultKey attribute of the UserListSelectHandler object
   *
   *@return    The defaultKey value
   */
  public int getDefaultKey() {
    return defaultKey;
  }


  /**
   *  Gets the selectName attribute of the UserListSelectHandler object
   *
   *@return    The selectName value
   */
  public String getSelectName() {
    return selectName;
  }


  /**
   *  Gets the jsEvent attribute of the UserListSelectHandler object
   *
   *@return    The jsEvent value
   */
  public String getJsEvent() {
    return jsEvent;
  }


  /**
   *  Gets the emptyHtmlSelectRecord attribute of the UserListSelectHandler
   *  object
   *
   *@return    The emptyHtmlSelectRecord value
   */
  public String getEmptyHtmlSelectRecord() {
    return emptyHtmlSelectRecord;
  }


  /**
   *  Gets the includeMe attribute of the UserListSelectHandler object
   *
   *@return    The includeMe value
   */
  public boolean getIncludeMe() {
    return includeMe;
  }


  /**
   *  Gets the excludeDisabledIfUnselected attribute of the
   *  UserListSelectHandler object
   *
   *@return    The excludeDisabledIfUnselected value
   */
  public boolean getExcludeDisabledIfUnselected() {
    return excludeDisabledIfUnselected;
  }


  /**
   *  Description of the Method
   *
   *@return                   Description of the Return Value
   *@exception  JspException  Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {
      System.out.println("1");
      ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute("ConnectionElement");
      if (ce == null) {
        System.out.println("UsernameHandler-> ConnectionElement is null");
      }
      SystemStatus systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
      if (systemStatus == null) {
        System.out.println("UsernameHandler-> SystemStatus is null");
      }
      if (emptyHtmlSelectRecord != null) {
        this.pageContext.getOut().write("<option value=\"-1\">" + emptyHtmlSelectRecord + "</option>");
      }
      if (includeMe) {
        User thisUser = ((UserBean) pageContext.getSession().getAttribute("User")).getUserRecord();
        this.pageContext.getOut().write("<option value=\"" + thisUser.getId() + "\">" + thisUser.getContact().getNameLastFirst() + "</option>");
      }
      UserList userList = systemStatus.getHierarchyList();
      Iterator i = userList.iterator();
      while (i.hasNext()) {
        User thisUser = (User) i.next();
        String elementText = null;

        elementText = Contact.getNameLastFirst(thisUser.getContact().getNameLast(), thisUser.getContact().getNameFirst());

        if (!(thisUser.getEnabled())) {
          elementText += " *";
        }

        if ((thisUser.getEnabled() && thisUser.getContact() != null && thisUser.getRoleId() != -1) || (!thisUser.getEnabled() && !excludeDisabledIfUnselected) || (excludeDisabledIfUnselected && thisUser.getId() == defaultKey)) {
          this.pageContext.getOut().write("<option value=\"" + thisUser.getId() + "\">" + thisUser.getContact().getNameLastFirst() + "</option>");
        }
      }
    } catch (Exception e) {
      throw new JspException("UserListSelectHandler - > Error: " + e.getMessage());
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


