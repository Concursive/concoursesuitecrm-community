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
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.utils.Template;

import javax.servlet.jsp.tagext.TagSupport;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 *  This Class evaluates whether a SystemStatus preference exists for the
 *  supplied label.
 *
 *@author     Matt Rajkowski
 *@created    February 25, 2002
 *@version    $Id: LabelHandler.java,v 1.3.180.2 2004/08/30 14:13:43 mrajkowski
 *      Exp $
 */
public class LabelHandler extends TagSupport {
  private String labelName = null;
  private HashMap params = null;
  private boolean mainMenuItem = false;
  private boolean subMenuItem = false;

  /**
   *  Sets the Name attribute of the LabelHandler object
   *
   *@param  tmp  The new Name value
   *@since       1.1
   */
  public final void setName(String tmp) {
    labelName = tmp;
  }

  public final void setMainMenuItem(boolean tmp) {
    mainMenuItem = tmp;
  }

  public final void setSubMenuItem(boolean tmp) {
    subMenuItem = tmp;
  }


  /**
   *  Sets the param attribute of the LabelHandler object
   *
   *@param  tmp  The new params value
   */
  public void setParam(String tmp) {
    params = new HashMap();
    StringTokenizer tokens = new StringTokenizer(tmp, "|");
    while (tokens.hasMoreTokens()) {
      String pair = tokens.nextToken();
      String param = pair.substring(0, pair.indexOf("="));
      String value = pair.substring(pair.indexOf("=") + 1);
      params.put("${" + param + "}", value);
    }
  }


  /**
   *  Checks to see if the SystemStatus has a preference set for this label. If
   *  so, the found label will be used, otherwise the body tag will be used.
   *
   *@return                   Description of the Returned Value
   *@since                    1.1
   */
  public final int doStartTag() {
    String newLabel = null;

    // Use the system status if available
    ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute("ConnectionElement");
    if (ce == null) {
      ApplicationPrefs prefs = (ApplicationPrefs) pageContext.getServletContext().getAttribute("applicationPrefs");
      if (prefs != null) {
        newLabel = prefs.getLabel(labelName);
      }
    } else {
      SystemStatus systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
      if (systemStatus == null) {
        System.out.println("LabelHandler-> SystemStatus is null");
      }
      // Look up the label key in system status to get the value
      if (systemStatus != null) {
        if (mainMenuItem) {
          newLabel = systemStatus.getMenuProperty(labelName, "page_title");
        } else if (subMenuItem) {
          newLabel = systemStatus.getSubMenuProperty(labelName);
        } else {
          newLabel = systemStatus.getLabel(labelName);
        }
      }
    }
    // If there are any parameters to substitute then do so
    if (newLabel != null && params != null && params.size() > 0) {
      Template labelText = new Template(newLabel);
      labelText.setParseElements(params);
      newLabel = labelText.getParsedText();
    }
    // Output the label value, else output the body of the tag
    if (newLabel != null) {
      try {
        this.pageContext.getOut().write(newLabel);
        return SKIP_BODY;
      } catch (java.io.IOException e) {
        //Nowhere to output
      }
    }
    return EVAL_BODY_INCLUDE;
  }

}

