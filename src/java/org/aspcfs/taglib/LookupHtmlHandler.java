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

import com.darkhorseventures.database.ConnectionElement;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Converts a list specified to a html dropdown based on the LookupList
 * included.
 *
 * @author Mathur
 * @version $Id: LookupHtmlHandler.java,v 1.2 2003/03/21 13:53:27 mrajkowski
 *          Exp $
 * @created March 17, 2003
 */
public class LookupHtmlHandler extends TagSupport implements TryCatchFinally {
  private String listType = null;
  private String listName = null;
  private String lookupName = null;

  public void doCatch(Throwable throwable) throws Throwable {
    // Required but not needed
  }

  public void doFinally() {
    // Reset each property or else the value gets reused
    listType = null;
    listName = null;
    lookupName = null;
  }

  /**
   * Sets the listName attribute of the LookupHtmlHandler object
   *
   * @param listName The new listName value
   */
  public void setListName(String listName) {
    this.listName = listName;
  }


  /**
   * Sets the lookupList attribute of the LookupHtmlHandler object
   *
   * @param lookupName The new lookupName value
   */
  public void setLookupName(String lookupName) {
    this.lookupName = lookupName;
  }


  /**
   * Sets the listType attribute of the LookupHtmlHandler object
   *
   * @param listType The new listType value
   */
  public void setListType(String listType) {
    this.listType = listType;
  }


  /**
   * Gets the listType attribute of the LookupHtmlHandler object
   *
   * @return The listType value
   */
  public String getListType() {
    return listType;
  }


  /**
   * Gets the listName attribute of the LookupHtmlHandler object
   *
   * @return The listName value
   */
  public String getListName() {
    return listName;
  }


  /**
   * Gets the lookupList attribute of the LookupHtmlHandler object
   *
   * @return The lookupList value
   */
  public String getLookupName() {
    return lookupName;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws JspException Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {
      // Translated labels needed
      String noneSelected = "None Selected";
      // Use the system status if available
      ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute(
          "ConnectionElement");
      if (ce == null) {
        ApplicationPrefs prefs = (ApplicationPrefs) pageContext.getServletContext().getAttribute(
            "applicationPrefs");
        if (prefs != null) {
          noneSelected = prefs.getLabel("accounts.accounts_add.NoneSelected", prefs.get("SYSTEM.LANGUAGE"));
        }
      } else {
        SystemStatus systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute(
            "SystemStatus")).get(ce.getUrl());
        // Look up the label key in system status to get the value
        if (systemStatus != null) {
          noneSelected = systemStatus.getLabel("accounts.accounts_add.NoneSelected", "None Selected");
        }
      }
      //TODO: if a lookupType is specified like hashmap, logic needs to be included to handle that case.
      ArrayList selectedList = (ArrayList) pageContext.getRequest().getAttribute(
          listName);
      ArrayList lookupList = (ArrayList) pageContext.getRequest().getAttribute(
          lookupName);
      if (selectedList != null && lookupList != null) {
        Iterator i = selectedList.iterator();
        if (i.hasNext()) {
          while (i.hasNext()) {
            int val = Integer.parseInt((String) i.next());
            Iterator j = lookupList.iterator();
            while (j.hasNext()) {
              Object thisElt = (Object) j.next();
              //Class[] rsClass = new Class[]{Class.forName(lookupElementClass)};
              Method method = thisElt.getClass().getMethod(
                  "getCodeString", (java.lang.Class[]) null);
              Object result = method.invoke(
                  thisElt, (java.lang.Object[]) null);
              int code = Integer.parseInt((String) result);
              if (code == val) {
                method = thisElt.getClass().getMethod(
                    "getDescription", (java.lang.Class[]) null);
                result = method.invoke(thisElt, (java.lang.Object[]) null);
                String description = (String) result;
                this.pageContext.getOut().write(
                    "<option value=\"" + code + "\">" + description + "</option>");
              }
            }
          }
        } else {
          this.pageContext.getOut().write("<option value=\"-1\">" + noneSelected + "</option>");
        }
      } else {
        this.pageContext.getOut().write("<option value=\"-1\">" + noneSelected + "</option>");
      }
    } catch (Exception e) {
      throw new JspException("LookupHtmlHandler - > Error: " + e.getMessage());
    }
    return SKIP_BODY;
  }
}
