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

import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.*;

/**
 *  Description of the Class
 *
 *@author     Ananth
 *@version
 *@created    February 16, 2006
 */
public class BatchItemHandler extends TagSupport {
  private String display = null;
  private String link = null;
  private String params = null;


  /**
   *  Gets the display attribute of the BatchItemHandler object
   *
   *@return    The display value
   */
  public String getDisplay() {
    return display;
  }


  /**
   *  Sets the display attribute of the BatchItemHandler object
   *
   *@param  tmp  The new display value
   */
  public void setDisplay(String tmp) {
    this.display = tmp;
  }


  /**
   *  Gets the link attribute of the BatchItemHandler object
   *
   *@return    The link value
   */
  public String getLink() {
    return link;
  }


  /**
   *  Sets the link attribute of the BatchItemHandler object
   *
   *@param  tmp  The new link value
   */
  public void setLink(String tmp) {
    this.link = tmp;
  }


  /**
   *  Gets the params attribute of the BatchItemHandler object
   *
   *@return    The params value
   */
  public String getParams() {
    return params;
  }


  /**
   *  Sets the params attribute of the BatchItemHandler object
   *
   *@param  tmp  The new params value
   */
  public void setParams(String tmp) {
    this.params = tmp;
  }



  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public int doStartTag() {
    try {
      TagSupport parent = (TagSupport) getParent();
      HashMap batchItems = (HashMap) 
          parent.getValue("batchItems");
        
      if (batchItems != null) {
        if (display != null && link != null) {
          batchItems.put(display, 
            link + (params != null ? "&" + params : ""));
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
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

