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
import org.aspcfs.controller.*;
import org.aspcfs.utils.*;
import com.darkhorseventures.database.*;
import java.util.*;

/**
 *  This Class evaluates whether the current SystemStatus preference is to
 *  include the body objects.
 *
 *@author     Matt Rajkowski
 *@created    February 25, 2002
 *@version    $Id$
 */
public class FieldHandler extends TagSupport {
  private String sectionName = null;
  private boolean allRequired = false;
  private boolean hasNone = false;


  /**
   *  Sets the Name attribute of the FieldHandler object
   *
   *@param  tmp  The new Name value
   *@since       1.1
   */
  public final void setName(String tmp) {
    sectionName = tmp;
  }


  /**
   *  Sets the All attribute of the PermissionHandler object. If set to true
   *  then the user must have all permissions passed in.
   *
   *@param  tmp  The new All value
   *@since       1.1
   */
  public void setAll(String tmp) {
    Boolean checkAll = new Boolean(tmp);
    this.allRequired = checkAll.booleanValue();
  }


  /**
   *  Sets the None attribute of the PermissionHandler object
   *
   *@param  tmp  The new None value
   *@since       1.1
   */
  public void setNone(boolean tmp) {
    Boolean checkNone = new Boolean(tmp);
    this.hasNone = checkNone.booleanValue();
  }


  /**
   *  Checks the SystemStatus preference for the section name. A comma-separated
   *  list of fields can be used for matching.
   *
   *@return                   Description of the Returned Value
   *@exception  JspException  Description of Exception
   *@since                    1.1
   */
  public final int doStartTag() throws JspException {
    boolean result = false;
    int matches = 0;
    int checks = 0;
    ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute("ConnectionElement");
    if (ce == null) {
      System.out.println("FieldHandler-> ConnectionElement is null");
    }
    SystemStatus systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    if (systemStatus == null) {
      System.out.println("FieldHandler-> SystemStatus is null");
    }
    if (systemStatus != null) {
      StringTokenizer st = new StringTokenizer(sectionName);
      while (st.hasMoreTokens()) {
        String thisField = st.nextToken();
        ++checks;
        if (systemStatus.hasField(thisField)) {
          ++matches;
        }
      }
      if ((allRequired && matches > 0 && matches == checks) ||
          (!allRequired && matches > 0)) {
        result = true;
      }
    }

    //The request wants to know if the user does not have the permissions
    if (hasNone) {
      result = !result;
    }

    if (result) {
      return EVAL_BODY_INCLUDE;
    } else {
      return SKIP_BODY;
    }
  }

}

