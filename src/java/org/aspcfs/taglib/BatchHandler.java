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
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.BatchInfo;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *  Description of the Class
 *
 *@author     Ananth
 *@version
 *@created    February 16, 2006
 */
public class BatchHandler extends TagSupport {
  private String object = null;


  /**
   *  Gets the object attribute of the BatchHandler object
   *
   *@return    The object value
   */
  public String getObject() {
    return object;
  }


  /**
   *  Sets the object attribute of the BatchHandler object
   *
   *@param  tmp  The new object value
   */
  public void setObject(String tmp) {
    this.object = tmp;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public int doStartTag() {
    try {
      BatchInfo batchInfo = (BatchInfo) pageContext.getRequest().
            getAttribute(object);
            
      if (batchInfo != null) {
        JspWriter out = this.pageContext.getOut();
        out.write("<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\" " +
            "SRC=\"javascript/processBatch.js\"></SCRIPT>");
  
        out.write("<form name=\"" + batchInfo.getName() + "Form" + "\" action=\"" +
            (batchInfo.getAction() != null ? batchInfo.getAction() : "") + "\" method=\"post\">");
      } else {
        System.out.println(
            "BatchHandler-> Control not found in request: " + object);
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return EVAL_BODY_INCLUDE;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public int doEndTag() {
    try {
      BatchInfo batchInfo = (BatchInfo) pageContext.getRequest().
            getAttribute(object);
            
      if (batchInfo != null) {
        JspWriter out = this.pageContext.getOut();
        out.write("<input type=\"hidden\" name=\"batchId\" value=\"" + batchInfo.getName() + "\">");
        out.write("<input type=\"hidden\" name=\"batchSize\" value=\"" + batchInfo.getSize() + "\">");
        out.write("<input type=\"hidden\" name=\"selected\" value=\"" + batchInfo.getSelected() + "\">");
        out.write("</form>");
      } else {
        System.out.println(
            "BatchHandler-> Control not found in request: " + object);
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return EVAL_PAGE;
  }
}

