package com.darkhorseventures.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.controller.*;
import java.util.*;

/**
 *  This Class evaluates a User ID and returns a Contact record from application
 *  scope.
 *
 *@author     matt
 *@created    December 14, 2001
 *@version    $Id$
 */
public class UsernameHandler extends TagSupport {

  private int userId = -1;


  /**
   *  Sets the Id attribute of the UsernameHandler object
   *
   *@param  tmp  The new Id value
   *@since
   */
  public void setId(String tmp) {
    this.userId = Integer.parseInt(tmp);
  }

  public void setId(int tmp) {
    this.userId = tmp;
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

      ConnectionElement ce = (ConnectionElement)pageContext.getSession().getAttribute("ConnectionElement");
      if (ce == null) {
        System.out.println("UsernameHandler-> ConnectionElement is null");
      }
      SystemStatus systemStatus = (SystemStatus)((Hashtable)pageContext.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
      if (systemStatus == null) {
        System.out.println("UsernameHandler-> SystemStatus is null");
      }
      UserList thisList = systemStatus.getHierarchyList();
      if (thisList == null) {
        System.out.println("UsernameHandler-> UserList is null");
      }
      User thisUser = thisList.getUser(userId);
      if (thisUser != null) {
        Contact thisContact = thisUser.getContact();
        this.pageContext.getOut().write(thisContact.getNameFirstLast());
      } else {
        System.out.println("UsernameHandler-> User is null");
        this.pageContext.getOut().write("");
      }
    } catch (Exception e) {
      throw new JspException("UsernameHandler-> Error: " + e.getMessage());
    }
    return SKIP_BODY;
  }
  
  public int doEndTag() {	
      return EVAL_PAGE;	
   }

}

