package com.darkhorseventures.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.controller.*;
import java.util.*;

/**
 *  This Class evaluates a Contact ID and returns a Contact Name from request
 *  scope.
 *
 *@author     Mathur
 *@created    November 22, 2002
 *@version    $Id$
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
        this.pageContext.getOut().write((String)contacts.get(new Integer(contactId)));
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

