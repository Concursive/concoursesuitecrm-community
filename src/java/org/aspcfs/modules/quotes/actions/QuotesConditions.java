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
package org.aspcfs.modules.quotes.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.quotes.base.*;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id: QuotesConditions.java,v 1.1.2.3 2004/11/08 18:35:49 partha
 *          Exp $
 * @created October 29, 2004
 */
public final class QuotesConditions extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandPopupSelector(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-edit") || hasPermission(
        context, "accounts-quotes-edit")
        || hasPermission(context, "leads-opportunities-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    String quoteId = (String) context.getRequest().getParameter("quoteId");
    LookupList selectList = new LookupList();
    boolean listDone = false;
    String displayFieldId = null;
    String type = (String) context.getRequest().getParameter("type");
    if (type != null && !"".equals(type)) {
      context.getRequest().setAttribute("type", type);
    }
    PagedListInfo lookupSelectorInfo = this.getPagedListInfo(
        context, "QuoteConditionSelectorInfo");
    String tableName = context.getRequest().getParameter("table");
    HashMap selectedList = new HashMap();
    try {
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
      if (context.getRequest().getParameter("displayFieldId") != null) {
        displayFieldId = context.getRequest().getParameter("displayFieldId");
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
      context.getSession().setAttribute("selectedElements", selectedList);
      context.getRequest().setAttribute("DisplayFieldId", displayFieldId);
      context.getRequest().setAttribute("Table", tableName);
      db = this.getConnection(context);
      if (quoteId != null && !"".equals(quoteId.trim())) {
        Quote quote = new Quote(db, Integer.parseInt(quoteId));
        context.getRequest().setAttribute("quote", quote);
      }

      if (context.getRequest().getParameter("finalsubmit") != null) {
        if (((String) context.getRequest().getParameter("finalsubmit")).equalsIgnoreCase(
            "true")) {
          context.getSession().removeAttribute("selectedElements");
          context.getSession().removeAttribute("finalElements");
          if (displayFieldId != null && !"".equals(displayFieldId)) {
            if (displayFieldId.equals("conditions")) {
              return saveFinalConditions(context, db, selectedList, quoteId);
            } else if (displayFieldId.equals("remarks")) {
              return saveFinalRemarks(context, db, selectedList, quoteId);
            }
          }
        }
      }
      selectList.setTableName(tableName);
      lookupSelectorInfo.setColumnToSortBy("description");
      selectList.setPagedListInfo(lookupSelectorInfo);
      lookupSelectorInfo.setSearchCriteria(selectList, context);
      selectList.setSelectedItems(selectedList);
      selectList.buildList(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      context.getRequest().setAttribute("BaseList", selectList);
      this.freeConnection(context, db);
    }
    return "PopLookupOK";
  }


  /**
   * Description of the Method
   *
   * @param context         Description of the Parameter
   * @param db              Description of the Parameter
   * @param finalSelections Description of the Parameter
   * @param quoteId         Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public String saveFinalConditions(ActionContext context, Connection db, HashMap finalSelections, String quoteId) throws SQLException {
    if (!(hasPermission(context, "quotes-quotes-edit") || hasPermission(
        context, "accounts-quotes-edit")
        || hasPermission(context, "leads-opportunities-edit"))) {
      return ("PermissionError");
    }
    //Check access permission to organization record
    Quote quote = new Quote(db, Integer.parseInt(quoteId));
    if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
      return ("PermissionError");
    }
    QuoteConditionList oldConditions = new QuoteConditionList();
    QuoteConditionList newConditions = new QuoteConditionList();
    oldConditions.setQuoteId(quoteId);
    oldConditions.buildList(db);
    HashMap currentConditions = oldConditions.retrieveMap();
    Iterator keySet = (Iterator) finalSelections.keySet().iterator();
    while (keySet.hasNext()) {
      Integer key = (Integer) keySet.next();
      String value = (String) finalSelections.get(key);
      if (!currentConditions.containsKey("" + key.intValue())) {
        QuoteCondition condition = new QuoteCondition();
        condition.setQuoteId(quoteId);
        condition.setConditionId(key.intValue());
        condition.insert(db);
        newConditions.add(condition);
      } else {
        String buffer = (String) currentConditions.remove("" + key.intValue());
      }
    }
    if (!oldConditions.fixDeletedConditions(db, currentConditions)) {
      return "SystemError";
    } else {
      return "SaveOK";
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-edit") || hasPermission(
        context, "accounts-quotes-edit")
        || hasPermission(context, "leads-opportunities-edit"))) {
      return ("PermissionError");
    }
    String quoteId = (String) context.getRequest().getParameter("quoteId");
    Quote quote = null;
    Connection db = null;
    try {
      db = getConnection(context);
      if (quoteId != null && !"".equals(quoteId)) {
        quote = new Quote(db, Integer.parseInt(quoteId));
        //Check access permission to organization record
        if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
          return ("PermissionError");
        }
        context.getRequest().setAttribute("quote", quote);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "AddOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-edit") || hasPermission(
        context, "accounts-quotes-edit")
        || hasPermission(context, "leads-opportunities-edit"))) {
      return ("PermissionError");
    }
    boolean isValid = false;
    String quoteId = (String) context.getRequest().getParameter("quoteId");
    String description = (String) context.getRequest().getParameter(
        "description");
    boolean recordInserted = false;
    int conditionId = -1;
    Quote quote = null;
    Connection db = null;
    try {
      db = getConnection(context);
      if (quoteId != null && !"".equals(quoteId)) {
        //Check access permission to organization record
        quote = new Quote(db, Integer.parseInt(quoteId));
        if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
          return ("PermissionError");
        }
        context.getRequest().setAttribute("quote", quote);
        QuoteCondition condition = new QuoteCondition();
        condition.setConditionName(description);
        isValid = this.validateObject(context, db, condition);
        if (isValid) {
          conditionId = condition.insertLookup(db, description);
        }
        if (conditionId != -1 && isValid) {
          condition.setConditionId(conditionId);
          condition.setQuoteId(quote.getId());
          recordInserted = condition.insert(db);
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (!recordInserted || !isValid) {
      if (description == null || "".equals(description.trim())) {
        description = "";
      }
      context.getRequest().setAttribute("description", description);
      return "AddOK";
    }
    return "SaveOK";
  }


  /**
   * Description of the Method
   *
   * @param context         Description of the Parameter
   * @param db              Description of the Parameter
   * @param finalSelections Description of the Parameter
   * @param quoteId         Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public String saveFinalRemarks(ActionContext context, Connection db, HashMap finalSelections, String quoteId) throws SQLException {
    if (!(hasPermission(context, "quotes-quotes-edit") || hasPermission(
        context, "accounts-quotes-edit")
        || hasPermission(context, "leads-opportunities-edit"))) {
      return ("PermissionError");
    }
    //Check access permission to organization record
    Quote quote = new Quote(db, Integer.parseInt(quoteId));
    if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
      return ("PermissionError");
    }
    QuoteRemarkList oldRemarks = new QuoteRemarkList();
    QuoteRemarkList newRemarks = new QuoteRemarkList();
    oldRemarks.setQuoteId(quoteId);
    oldRemarks.buildList(db);
    HashMap currentRemarks = oldRemarks.retrieveMap();
    Iterator keySet = (Iterator) finalSelections.keySet().iterator();
    while (keySet.hasNext()) {
      Integer key = (Integer) keySet.next();
      String value = (String) finalSelections.get(key);
      if (!currentRemarks.containsKey("" + key.intValue())) {
        QuoteRemark remark = new QuoteRemark();
        remark.setQuoteId(quoteId);
        remark.setRemarkId(key.intValue());
        remark.insert(db);
        newRemarks.add(remark);
      } else {
        String buffer = (String) currentRemarks.remove("" + key.intValue());
      }
    }
    if (!oldRemarks.fixDeletedRemarks(db, currentRemarks)) {
      return "SystemError";
    } else {
      return "SaveOK";
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAddRemark(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-edit") || hasPermission(
        context, "accounts-quotes-edit")
        || hasPermission(context, "leads-opportunities-edit"))) {
      return ("PermissionError");
    }
    String quoteId = (String) context.getRequest().getParameter("quoteId");
    Quote quote = null;
    Connection db = null;
    try {
      db = getConnection(context);
      if (quoteId != null && !"".equals(quoteId)) {
        quote = new Quote(db, Integer.parseInt(quoteId));
        //Check access permission to organization record
        if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
          return ("PermissionError");
        }
        context.getRequest().setAttribute("quote", quote);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "AddRemarkOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSaveRemark(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-edit") || hasPermission(
        context, "accounts-quotes-edit")
        || hasPermission(context, "leads-opportunities-edit"))) {
      return ("PermissionError");
    }
    String quoteId = (String) context.getRequest().getParameter("quoteId");
    String description = (String) context.getRequest().getParameter(
        "description");
    boolean isValid = false;
    int remarkId = -1;
    boolean recordInserted = false;
    Quote quote = null;
    Connection db = null;
    try {
      db = getConnection(context);
      if (quoteId != null && !"".equals(quoteId)) {
        quote = new Quote(db, Integer.parseInt(quoteId));
        //Check access permission to organization record
        if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
          return ("PermissionError");
        }
        context.getRequest().setAttribute("quote", quote);
        QuoteRemark remark = new QuoteRemark();
        remark.setRemarkName(description);
        isValid = this.validateObject(context, db, remark);
        if (isValid) {
          remarkId = remark.insertLookup(db, description);
        }
        if (remarkId != -1 && isValid) {
          remark.setRemarkId(remarkId);
          remark.setQuoteId(quote.getId());
          recordInserted = remark.insert(db);
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (!recordInserted || !isValid) {
      if (description == null || "".equals(description.trim())) {
        description = "";
      }
      context.getRequest().setAttribute("description", description);
      return "AddRemarkOK";
    }
    return "SaveOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandRemoveCondition(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-edit") || hasPermission(
        context, "accounts-quotes-edit")
        || hasPermission(context, "leads-opportunities-edit"))) {
      return ("PermissionError");
    }
    String quoteId = (String) context.getRequest().getParameter("quoteId");
    String conditionId = (String) context.getRequest().getParameter(
        "conditionId");
    String orgId = (String) context.getRequest().getParameter("orgId");
    String headerId = (String) context.getRequest().getParameter("headerId");
    String contactId = (String) context.getRequest().getParameter("contactId");
    Connection db = null;
    try {
      db = getConnection(context);
      //Check access permission to organization record
      Quote quote = new Quote(db, Integer.parseInt(quoteId));
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      QuoteCondition quoteCondition = new QuoteCondition();
      quoteCondition.queryRecord(
          db, Integer.parseInt(quoteId), Integer.parseInt(conditionId));
      quoteCondition.delete(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (contactId != null && !"".equals(contactId)){
      if (orgId != null && !"".equals(orgId) &&
          headerId != null && !"".equals(headerId)){
          return "RemoveConditionContactOppOK";
      }
    } else if (orgId != null && !"".equals(orgId)) {
      return "RemoveConditionOrgOK";
    } else if (headerId != null && !"".equals(headerId)) {
      return "RemoveConditionOppOK";
    }
    return "RemoveConditionOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandRemoveRemark(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-edit") || hasPermission(
        context, "accounts-quotes-edit")
        || hasPermission(context, "leads-opportunities-edit"))) {
      return ("PermissionError");
    }
    String quoteId = (String) context.getRequest().getParameter("quoteId");
    String remarkId = (String) context.getRequest().getParameter("remarkId");
    String orgId = (String) context.getRequest().getParameter("orgId");
    String headerId = (String) context.getRequest().getParameter("headerId");
    String contactId = (String) context.getRequest().getParameter("contactId");
    Connection db = null;
    try {
      db = getConnection(context);
      //Check access permission to organization record
      Quote quote = new Quote(db, Integer.parseInt(quoteId));
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      QuoteRemark quoteRemark = new QuoteRemark(
          db, Integer.parseInt(quoteId), Integer.parseInt(remarkId));
      quoteRemark.delete(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (contactId != null && !"".equals(contactId)){
      if (orgId != null && !"".equals(orgId) &&
          headerId != null && !"".equals(headerId)){
          return "RemoveRemarkContactOppOK";
      }
    } else if (orgId != null && !"".equals(orgId)) {
      return "RemoveRemarkOrgOK";
    } else if (headerId != null && !"".equals(headerId)) {
      return "RemoveRemarkOppOK";
    }
    return "RemoveRemarkOK";
  }

}

