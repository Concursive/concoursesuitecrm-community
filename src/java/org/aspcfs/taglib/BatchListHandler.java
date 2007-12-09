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
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.web.BatchInfo;
import org.aspcfs.utils.web.HtmlSelect;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author Ananth
 * @created February 16, 2006
 */
public class BatchListHandler extends TagSupport implements TryCatchFinally {
  public final static int SELECT = 1;

  private String label = null;
  private String object = null;
  private int type = SELECT;
  private String returnURL = null;

  //resources
  private HashMap batchItems = new HashMap();

  public void doCatch(Throwable throwable) throws Throwable {
    // Required but not needed
  }

  public void doFinally() {
    // Reset each property or else the value gets reused
    label = null;
    object = null;
    type = SELECT;
    returnURL = null;
  }

  /**
   * Sets the object attribute of the BatchListHandler object
   *
   * @param tmp The new object value
   */
  public void setObject(String tmp) {
    object = tmp;
  }


  /**
   * Sets the returnURL attribute of the BatchListHandler object
   *
   * @param tmp The new returnURL value
   */
  public void setReturnURL(String tmp) {
    returnURL = tmp;
  }


  /**
   * Sets the label attribute of the BatchListHandler object
   *
   * @param tmp The new label value
   */
  public void setLabel(String tmp) {
    label = tmp;
  }


  /**
   * Sets the type attribute of the BatchListHandler object
   *
   * @param tmp The new type value
   */
  public void setType(String tmp) {
    type = Integer.parseInt(tmp);
  }


  /**
   * Sets the type attribute of the BatchListHandler object
   *
   * @param tmp The new type value
   */
  public void setType(int tmp) {
    type = tmp;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public int doStartTag() {
    try {
      setValue("batchItems", batchItems);
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return EVAL_BODY_INCLUDE;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public int doEndTag() {
    ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute(
        "ConnectionElement");
    SystemStatus systemStatus = null;
    if (ce != null) {
      Hashtable statusList = (Hashtable) pageContext.getServletContext().getAttribute("SystemStatus");
      systemStatus = (SystemStatus) statusList.get(ce.getUrl());
    }
    StringBuffer sb = new StringBuffer();
    try {
      UserBean thisUser = (UserBean) pageContext.getSession().
          getAttribute("User");

      BatchInfo batchInfo = (BatchInfo) pageContext.getRequest().
          getAttribute(object);

      if (batchInfo != null) {
        HtmlSelect batchMenu = new HtmlSelect();
        batchMenu.setIdName("batchActions");

        Iterator i = batchItems.keySet().iterator();
        while (i.hasNext()) {
          String batchItem = (String) i.next();
          String itemAction = (String) batchItems.get(batchItem);
          batchMenu.addItem(itemAction, batchItem);
        }

        sb.append("[<a href=\"javascript:SetChecked(1,'" +
            batchInfo.getName() + "','" + batchInfo.getName() + "Form" + "'," +
            "'" + thisUser.getBrowserId() + "');\">");

        if (systemStatus != null) {
          sb.append(systemStatus.getLabel("quotes.checkAll") + "</a>]&nbsp;");
        } else {
          sb.append("Check All</a>]&nbsp;");
        }

        sb.append("[<a href=\"javascript:SetChecked(0,'" + batchInfo.getName() +
            "','" + batchInfo.getName() + "Form" + "'," +
            "'" + thisUser.getBrowserId() + "');\">");

        if (systemStatus != null) {
          sb.append(systemStatus.getLabel("quotes.clearAll") + "</a>]&nbsp;");
        } else {
          sb.append("Clear All</a>]&nbsp;");
        }

        sb.append((label != null ? (label + ": ") : "") +
            batchMenu.getHtml("batchActions", batchInfo.getSelected()));

        if (systemStatus != null) {
          sb.append("<input type=\"button\" value=\"" + systemStatus.getLabel("button.processBatch") + "\" " +
              "onClick=\"javascript:submitBatch('" + batchInfo.getName() + "Form', 'batchActions','" +
              batchInfo.getName() + "');\">");
        } else {
          sb.append("<input type=\"button\" value=\"Process Batch\" " +
              "onClick=\"javascript:submitBatch('" + batchInfo.getName() + "Form', 'batchActions', '" +
              batchInfo.getName() + "');\">");
        }

        if (returnURL != null && !"".equals(returnURL.trim())) {
          sb.append("<input type=\"hidden\" name=\"" + batchInfo.getName() +
              "ReturnURL\" value=\"" + returnURL + "\">");
        }

        //TODO: write the html instead of requesting the parent tag to write it out
        ((TagSupport) getParent()).setValue(
            "batchHTML", sb.toString().trim());
      } else {
        System.out.println(
            "BatchListHandler-> Control not found in request: " + object);
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return EVAL_PAGE;
  }
}

