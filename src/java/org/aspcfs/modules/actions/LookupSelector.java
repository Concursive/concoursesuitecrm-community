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
package org.aspcfs.modules.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.StateSelect;

import java.sql.Connection;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Action method to construct a paged lookup list
 *
 * @author akhilesh mathur
 * @version $Id: LookupSelector.java,v 1.7.16.2 2004/11/12 20:37:02 partha Exp
 *          $
 * @created --
 */
public final class LookupSelector extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandPopupSelector(ActionContext context) {
    Connection db = null;
    LookupList selectList = new LookupList();
    boolean listDone = false;
    String displayFieldId = null;
    String hiddenFieldId = null;
    String listType = context.getRequest().getParameter("listType");
    String tableName = context.getRequest().getParameter("table");
    HashMap selectedList = new HashMap();
    if (context.getRequest().getParameter("displayFieldId") != null) {
      displayFieldId = context.getRequest().getParameter("displayFieldId");
    }

    //  This condition is added here for processing State/Provinance Lists for selected country
    //  Country is passed in tableName parameter
    //  Here displayFieldId is compared to name of the State/Provinance field name
    if (displayFieldId.substring(5).equals("searchmultiplecodeContactStateList") ||
        displayFieldId.substring(5).equals("searchmultiplecodeAccountStateList")) {
      SystemStatus systemStatus = this.getSystemStatus(context);
      StateSelect stateSelect = new StateSelect(systemStatus, tableName);
      String temp = null;
      if (context.getRequest().getParameter("previousSelection") != null) {
        StringTokenizer st = new StringTokenizer(context.getRequest().getParameter("previousSelection"), ";");
        while (st.hasMoreTokens()) {
          temp = st.nextToken();
          if (temp != null && !temp.trim().equals("")) {
            selectedList.put(new String(temp.substring(0, temp.indexOf(','))), temp.substring(temp.indexOf(',') + 1));
          }
        }
      }
      context.getSession().setAttribute("selectedElements", selectedList);
      context.getRequest().setAttribute("stateSelect", stateSelect.getHtmlSelectObj(tableName));
      context.getRequest().setAttribute("DisplayFieldId", displayFieldId);
    } else {
      PagedListInfo lookupSelectorInfo = this.getPagedListInfo(
          context, "LookupSelectorInfo");
      HashMap finalElementList = (HashMap) context.getSession().getAttribute(
          "finalElements");
      if (context.getRequest().getParameter("previousSelection") != null) {
        int j = 0;
        StringTokenizer st = new StringTokenizer(
            context.getRequest().getParameter("previousSelection"), "|");
        StringTokenizer st1 = new StringTokenizer(
            context.getRequest().getParameter("previousSelectionDisplay"), "|");
        while (st.hasMoreTokens()) {
          selectedList.put(new Integer(st.nextToken()), st1.nextToken());
          j++;
        }
      } else {
        //get selected list from the session
        selectedList = (HashMap) context.getSession().getAttribute(
            "selectedElements");
      }
      if (context.getRequest().getParameter("hiddenFieldId") != null) {
        hiddenFieldId = context.getRequest().getParameter("hiddenFieldId");
      }
      //Flush the selectedList if its a new selection
      if (context.getRequest().getParameter("flushtemplist") != null) {
        if (((String) context.getRequest().getParameter("flushtemplist")).equalsIgnoreCase(
            "true")) {
          if (context.getSession().getAttribute("finalElements") != null && context.getRequest().getParameter(
              "previousSelection") == null) {
            selectedList = (HashMap) ((HashMap) context.getSession().getAttribute(
                "finalElements")).clone();
          }
        }
      }
      int rowCount = 1;
      if ("list".equals(listType)) {
        while (context.getRequest().getParameter("hiddenelementid" + rowCount) != null) {
          int elementId = 0;
          String elementValue = "";
          elementId = Integer.parseInt(
              context.getRequest().getParameter("hiddenelementid" + rowCount));
          if (context.getRequest().getParameter("checkelement" + rowCount) != null) {
            if (context.getRequest().getParameter("elementvalue" + rowCount) != null) {
              elementValue = context.getRequest().getParameter(
                  "elementvalue" + rowCount);
            }
            if (selectedList.get(new Integer(elementId)) == null) {
              selectedList.put(new Integer(elementId), elementValue);
            } else {
              selectedList.remove(new Integer(elementId));
              selectedList.put(new Integer(elementId), elementValue);
            }
          } else {
            selectedList.remove(new Integer(elementId));
          }
          rowCount++;
        }
      }

      if (context.getRequest().getParameter("finalsubmit") != null) {
        if (((String) context.getRequest().getParameter("finalsubmit")).equalsIgnoreCase(
            "true")) {
          //Handle single selection case
          if ("single".equals(listType)) {
            rowCount = Integer.parseInt(
                context.getRequest().getParameter("rowcount"));
            int siteId = Integer.parseInt(
                context.getRequest().getParameter("hiddenelementid" + rowCount));
            String siteValue = context.getRequest().getParameter(
                "elementvalue" + rowCount);
            selectedList.clear();
            selectedList.put(new Integer(siteId), siteValue);
          }

          finalElementList = (HashMap) selectedList.clone();
          context.getSession().setAttribute("finalElements", finalElementList);
        }
      }
      try {
        db = this.getConnection(context);
        selectList.setTableName(tableName);
        selectList.setPagedListInfo(lookupSelectorInfo);
        selectList.setSelectedItems(selectedList);
        selectList.buildList(db);
      } catch (Exception e) {
        context.getRequest().setAttribute("Error", e);
        return ("SystemError");
      } finally {
        context.getRequest().setAttribute("BaseList", selectList);
        this.freeConnection(context, db);
      }
      context.getSession().setAttribute("selectedElements", selectedList);
      context.getRequest().setAttribute("DisplayFieldId", displayFieldId);
      context.getRequest().setAttribute("hiddenFieldId", hiddenFieldId);
      context.getRequest().setAttribute("Table", tableName);
    }
    return ("PopLookupOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandPopupSingleSelector(ActionContext context) {
    Connection db = null;
    LookupList selectList = null;
    boolean listDone = false;
    String lookupId = (String) context.getRequest().getParameter("lookupId");
    String moduleId = (String) context.getRequest().getParameter("moduleId");
    String displayFieldId = null;
    PagedListInfo lookupSelectorInfo = this.getPagedListInfo(
        context, "LookupSingleSelectorInfo");
    if (context.getRequest().getParameter("displayFieldId") != null) {
      displayFieldId = context.getRequest().getParameter("displayFieldId");
    }
    try {
      db = this.getConnection(context);
      lookupSelectorInfo.setLink(
          "LookupSelector.do?command=PopupSingleSelector&lookupId=" + lookupId + "&moduleId=" + moduleId + "&displayFieldId=" + displayFieldId);
      selectList = new LookupList(
          db, Integer.parseInt(moduleId), Integer.parseInt(lookupId));
      selectList.setPagedListInfo(lookupSelectorInfo);
      selectList.buildList(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      context.getRequest().setAttribute("BaseList", selectList);
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("DisplayFieldId", displayFieldId);
    context.getRequest().setAttribute("lookupId", lookupId);
    context.getRequest().setAttribute("moduleId", moduleId);
    return ("PopLookupSingleOK");
  }

}

