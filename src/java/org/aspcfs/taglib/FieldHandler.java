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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 * This Class evaluates whether the current SystemStatus preference is to
 * include the body objects.
 *
 * @author Matt Rajkowski
 * @version $Id$
 * @created February 25, 2002
 */
public class FieldHandler extends TagSupport implements TryCatchFinally {
  private String sectionName = null;
  private boolean allRequired = false;
  private boolean hasNone = false;

  public void doCatch(Throwable throwable) throws Throwable {
    // Required but not needed
  }

  public void doFinally() {
    // Reset each property or else the value gets reused
    sectionName = null;
    allRequired = false;
    hasNone = false;
  }

  /**
   * Sets the Name attribute of the FieldHandler object
   *
   * @param tmp The new Name value
   * @since 1.1
   */
  public final void setName(String tmp) {
    sectionName = tmp;
  }


  /**
   * Sets the All attribute of the PermissionHandler object. If set to true
   * then the user must have all permissions passed in.
   *
   * @param tmp The new All value
   * @since 1.1
   */
  public void setAll(String tmp) {
    Boolean checkAll = new Boolean(tmp);
    this.allRequired = checkAll.booleanValue();
  }


  public void setAll(boolean tmp) {
    allRequired = tmp;
  }


  public void setNone(boolean tmp) {
    hasNone = tmp;
  }

  public void setNone(String tmp) {
    Boolean checkNone = new Boolean(tmp);
    this.hasNone = checkNone.booleanValue();
  }

  /**
   * Checks the SystemStatus preference for the section name. A comma-separated
   * list of fields can be used for matching.
   *
   * @return Description of the Returned Value
   * @throws JspException Description of Exception
   * @since 1.1
   */
  public final int doStartTag() throws JspException {
    boolean result = false;
    int matches = 0;
    int checks = 0;
    ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute(
        "ConnectionElement");
    SystemStatus systemStatus = null;
    if (ce == null) {
      System.out.println("FieldHandler-> ConnectionElement is null");
      systemStatus = (SystemStatus) pageContext.getRequest().getAttribute("systemStatus");
    } else {
      systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute(
          "SystemStatus")).get(ce.getUrl());
    }
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
      // Determine if none=true
      if (hasNone) {
        // Show the field unless it is listed in system.xml
        if (matches == 0) {
          result = true;
        } else {
          // Each value must match
          if (allRequired) {
            if (matches == checks) {
              result = false;
            } else {
              result = true;
            }
          } else {
            result = false;
          }
        }
      } else {
        // Show the field only if it is listed in system.xml
        if (matches > 0) {
          // Each value must match
          if (allRequired) {
            if (matches == checks) {
              result = true;
            } else {
              result = false;
            }
          } else {
            result = true;
          }
        } else {
          result = false;
        }
      }
    }
    if (result) {
      return EVAL_BODY_INCLUDE;
    } else {
      return SKIP_BODY;
    }
  }
}