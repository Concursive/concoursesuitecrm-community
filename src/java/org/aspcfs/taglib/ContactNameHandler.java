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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.HashMap;

/**
 * This Class evaluates a Contact ID and returns a Contact Name from request
 * scope.
 *
 * @author matt rajkowski
 * @version $Id: ContactNameHandler.java,v 1.1 2002/11/25 16:07:13 akhi_m Exp
 *          $
 * @created November 22, 2002
 */
public class ContactNameHandler extends TagSupport {

  private int contactId = -1;
  private String listName = null;


  /**
   * Sets the Id attribute of the ContactNameHandler object
   *
   * @param tmp The new Id value
   */
  public void setId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   * Sets the id attribute of the ContactNameHandler object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Name of the request attribute which stores the HashMap
   *
   * @param listName The new listName value
   */
  public void setListName(String listName) {
    this.listName = listName;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Returned Value
   * @throws JspException Description of Exception
   */
  public int doStartTag() throws JspException {
    try {
      HashMap contacts = (HashMap) pageContext.getRequest().getAttribute(
          listName);
      if (contacts.get(new Integer(contactId)) != null) {
        this.pageContext.getOut().write(
            (String) contacts.get(new Integer(contactId)));
      } else {
        System.out.println("ContactnameHandler-> Contact Record not found");
      }
    } catch (Exception e) {
      throw new JspException("ContactNameHandler-> Error: " + e.getMessage());
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

