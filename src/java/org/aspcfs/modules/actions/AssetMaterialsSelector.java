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
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.assets.base.*;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    October 19, 2005
 * @version    $Id$
 */
public final class AssetMaterialsSelector extends CFSModule {

  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandPopupSelector(ActionContext context) {
    if (!hasPermission(context, "accounts-assets-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    String assetId = (String) context.getRequest().getParameter("assetId");
    LookupList selectList = new LookupList();
    boolean listDone = false;
    String displayFieldId = null;
    PagedListInfo lookupSelectorInfo = this.getPagedListInfo(context, "AssetMaterialsSelectorInfo");
    String tableName = context.getRequest().getParameter("table");
    HashMap selectedQtys = new HashMap();
    try {
      HashMap finalQtyList = (HashMap) context.getSession().getAttribute("finalQuantities");
      if (context.getRequest().getParameter("previousSelection") != null) {
        int j = 0;
        StringTokenizer st = new StringTokenizer(context.getRequest().getParameter("previousSelection"), "|");
        StringTokenizer st2 = new StringTokenizer(context.getRequest().getParameter("previousSelectionQuantity"), "|");
        while (st.hasMoreTokens()) {
          Integer key = new Integer(st.nextToken());
          selectedQtys.put(key, st2.nextToken());
          j++;
        }
      } else {
        //get selected list from the session
        selectedQtys = (HashMap) context.getSession().getAttribute("selectedQuantities");
      }
      if (context.getRequest().getParameter("displayFieldId") != null) {
        displayFieldId = context.getRequest().getParameter("displayFieldId");
      }
      //Flush the selectedQty if its a new selection
      String flushTempList = (String) context.getRequest().getParameter("flushtemplist");
      if (flushTempList != null && "true".equalsIgnoreCase(flushTempList)) {
        if (context.getSession().getAttribute("finalQuantities") != null && context.getRequest().getParameter("previousSelection") == null) {
          selectedQtys = (HashMap) ((HashMap) context.getSession().getAttribute("finalQuantities")).clone();
        }
      }
      int rowCount = 1;
      while (context.getRequest().getParameter("hiddenelementid" + rowCount) != null) {
        int elementId = 0;
        String elementQty = "";
        elementId = Integer.parseInt(context.getRequest().getParameter("hiddenelementid" + rowCount));
        if (context.getRequest().getParameter("checkelement" + rowCount) != null) {
          if (context.getRequest().getParameter("elementqty" + rowCount) != null) {
            elementQty = context.getRequest().getParameter("elementqty" + rowCount);
          }
          if (selectedQtys.get(new Integer(elementId)) == null) {
            selectedQtys.put(new Integer(elementId), elementQty);
          } else {
            selectedQtys.remove(new Integer(elementId));
            selectedQtys.put(new Integer(elementId), elementQty);
          }
        } else {
          selectedQtys.remove(new Integer(elementId));
        }
        rowCount++;
      }
      context.getSession().setAttribute("selectedQuantities", selectedQtys);
      context.getRequest().setAttribute("DisplayFieldId", displayFieldId);
      context.getRequest().setAttribute("Table", tableName);
      db = this.getConnection(context);
      if (assetId != null && !"".equals(assetId.trim())) {
        Asset asset = new Asset(db, assetId);
        context.getRequest().setAttribute("asset", asset);
        context.getRequest().setAttribute("assetId", String.valueOf(asset.getId()));
      }
      if (context.getRequest().getParameter("finalsubmit") != null) {
        if (((String) context.getRequest().getParameter("finalsubmit")).equalsIgnoreCase("true")) {
          context.getSession().removeAttribute("selectedQuantities");
          context.getSession().removeAttribute("finalQuantities");
          if (displayFieldId != null && !"".equals(displayFieldId)) {
            context.getRequest().setAttribute("selectedQtys", selectedQtys);
            return "SaveOK";
          }
        }
      }
      selectList.setTableName(tableName);
      selectList.setPagedListInfo(lookupSelectorInfo);
      selectList.setSelectedItems(selectedQtys);
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
}

