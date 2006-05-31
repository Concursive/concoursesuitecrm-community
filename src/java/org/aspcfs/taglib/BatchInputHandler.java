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
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.BatchInfo;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *  Description of the Class
 *
 *@author     Ananth
 *@version
 *@created    February 16, 2006
 */
public class BatchInputHandler extends TagSupport {
  private String object = null;
  private int value = -1;
  private String hiddenParams = null;
  private String hiddenValues = null;


  /**
   *  Sets the object attribute of the BatchInputHandler object
   *
   *@param  tmp  The new object value
   */
  public void setObject(String tmp) {
    this.object = tmp;
  }


  /**
   *  Sets the value attribute of the BatchInputHandler object
   *
   *@param  tmp  The new value value
   */
  public void setValue(int tmp) {
    this.value = tmp;
  }


  /**
   *  Sets the value attribute of the BatchInputHandler object
   *
   *@param  tmp  The new value value
   */
  public void setValue(String tmp) {
    this.value = Integer.parseInt(tmp);
  }


  /**
   *  Sets the hiddenValues attribute of the BatchInputHandler object
   *
   *@param  tmp  The new hiddenValues value
   */
  public void setHiddenValues(String tmp) {
    this.hiddenValues = tmp;
  }


  /**
   *  Sets the hiddenParams attribute of the BatchInputHandler object
   *
   *@param  tmp  The new hiddenParams value
   */
  public void setHiddenParams(String tmp) {
    this.hiddenParams = tmp;
  }



  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public int doStartTag() {
    try {
      BatchHandler batchTag = (BatchHandler) findAncestorWithClass(this,
          BatchHandler.class);
      if (batchTag == null) {
        throw new JspTagException("BatchList Tag being used without a enclosing Batch Tag");
      }
      
      UserBean thisUser = (UserBean) pageContext.getSession().
            getAttribute("User");
            
      BatchInfo batchInfo = (BatchInfo) pageContext.getRequest().
            getAttribute(object);
            
      if (batchInfo != null) {
        JspWriter out = this.pageContext.getOut();
        int count = batchInfo.getCount();
        
        out.write("<input type=\"checkbox\" name=\"" + (batchInfo.getName() + 
            count) + "\" value=\"" + value + "\" " +
                "onClick=\"highlight(this,'" + thisUser.getBrowserId() + "');\">");

        StringTokenizer st1 = new StringTokenizer(hiddenParams, "|");
        StringTokenizer st2 = new StringTokenizer(hiddenValues, "|");
        while (st1.hasMoreTokens() && st2.hasMoreTokens()) {
          String param = st1.nextToken();
          String value = st2.nextToken();
          out.write("<input type=\"hidden\" name=\"hidden" + 
              (param.substring(0, 1).toUpperCase() + param.substring(1) + count) + "\" " +
                  "value=\"" + value + "\">");
        }
      } else {
        System.out.println(
            "BatchInputHandler-> Control not found in request: " + object);
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

