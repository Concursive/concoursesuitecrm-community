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
import org.aspcfs.utils.*;
import org.aspcfs.controller.*;
import java.util.*;

/**
 *  This Class evaluates a Contact ID and returns a Contact Name from request
 *  scope.
 *
 *@author     matt rajkowski
 *@created    November 22, 2002
 *@version    $Id: ContactNameHandler.java,v 1.1 2002/11/25 16:07:13 akhi_m Exp
 *      $
 */
public class ContactNameHandler extends TagSupport {

  private int contactId = -1;
  private String listName = null;


  /**
   *  Sets the Id attribute of the ContactNameHandler object
   *
   *@param  tmp  The new Id value
   *@since
   */
  public void setId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the id attribute of the ContactNameHandler object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.contactId = tmp;
  }


  /**
   *  Name of the request attribute which stores the HashMap
   *
   *@param  listName  The new listName value
   */
  public void setListName(String listName) {
    this.listName = listName;
  }


  /**
   *  Description of the Method
   *
   *@return                   Description of the Returned Value
   *@exception  JspException  Description of Exception
   *@since
   */
  public int doStartTag() throws JspException {
    try {

      HashMap contacts = (HashMap) pageContext.getRequest().getAttribute(listName);
      if (contacts.get(new Integer(contactId)) != null) {
        this.pageContext.getOut().write((String) contacts.get(new Integer(contactId)));
      } else {
        System.out.println("ContactnameHandler-> Contact Record not found");
      }
    } catch (Exception e) {
      throw new JspException("ContactNameHandler-> Error: " + e.getMessage());
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

