package org.aspcfs.modules.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.utils.*;
import java.sql.*;
import java.util.ArrayList;
import org.aspcfs.utils.web.*;
import java.util.*;
import com.zeroio.iteam.base.*;

/**
 *  Action method to construct a paged lookup list
 *
 *@author     akhilesh mathur
 *@created    --
 *@version    $Id$
 */
public final class LookupSelector extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandPopupSelector(ActionContext context) {
    Connection db = null;
    LookupList selectList = new LookupList();
    boolean listDone = false;
    String displayFieldId = null;
    PagedListInfo lookupSelectorInfo = this.getPagedListInfo(context, "LookupSelectorInfo");
    String tableName = context.getRequest().getParameter("table");
    HashMap selectedList = new HashMap();
    HashMap finalElementList = (HashMap) context.getSession().getAttribute("finalElements");
    if (context.getRequest().getParameter("previousSelection") != null) {
      int j = 0;
      StringTokenizer st = new StringTokenizer(context.getRequest().getParameter("previousSelection"), "|");
      while (st.hasMoreTokens()) {
        selectedList.put(new Integer(st.nextToken()), "");
        j++;
      }
    } else {
      //get selected list from the session
      selectedList = (HashMap) context.getSession().getAttribute("selectedElements");
    }
    if (context.getRequest().getParameter("displayFieldId") != null) {
      displayFieldId = context.getRequest().getParameter("displayFieldId");
    }
    //Flush the selectedList if its a new selection
    if (context.getRequest().getParameter("flushtemplist") != null) {
      if (((String) context.getRequest().getParameter("flushtemplist")).equalsIgnoreCase("true")) {
        if (context.getSession().getAttribute("finalElements") != null && context.getRequest().getParameter("previousSelection") == null) {
          selectedList = (HashMap) ((HashMap) context.getSession().getAttribute("finalElements")).clone();
        }
      }
    }
    int rowCount = 1;
    while (context.getRequest().getParameter("hiddenelementid" + rowCount) != null) {
      int elementId = 0;
      String elementValue = "";
      elementId = Integer.parseInt(context.getRequest().getParameter("hiddenelementid" + rowCount));
      if (context.getRequest().getParameter("checkelement" + rowCount) != null) {
        if (context.getRequest().getParameter("elementvalue" + rowCount) != null) {
          elementValue = context.getRequest().getParameter("elementvalue" + rowCount);
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
    if (context.getRequest().getParameter("finalsubmit") != null) {
      if (((String) context.getRequest().getParameter("finalsubmit")).equalsIgnoreCase("true")) {
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
    context.getRequest().setAttribute("Table", tableName);
    return ("PopLookupOK");
  }
}

