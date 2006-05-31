/*
 *  Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INC OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.website.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.website.base.*;
import org.aspcfs.modules.website.framework.IceletManager;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlDialog;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    April 28, 2006
 * @version    $Id: Exp$
 */
public final class RowColumns extends CFSModule {

  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    return this.getReturn(context, "Default");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandMove(ActionContext context) {
    if (!(hasPermission(context, "site-editor-edit"))) {
      return ("PermissionError");
    }
    String rowColumnId = context.getRequest().getParameter("rowColumnId");
    String moveRowColumnLeft = context.getRequest().getParameter("moveRowColumnLeft");
    if (moveRowColumnLeft == null || "".equals(moveRowColumnLeft.trim())) {
      moveRowColumnLeft = new String("YES");
    }
    RowColumn rowColumn = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      rowColumn = new RowColumn(db, Integer.parseInt(rowColumnId));
      rowColumn.move(db, DatabaseUtils.parseBoolean(moveRowColumnLeft));
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "Move");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAddSubRow(ActionContext context) {
    if (!(hasPermission(context, "site-editor-edit"))) {
      return ("PermissionError");
    }
    String rowColumnId = context.getRequest().getParameter("rowColumnId");
    RowColumn rowColumn = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      rowColumn = new RowColumn();
      rowColumn.setBuildSubRows(true);
      rowColumn.queryRecord(db, Integer.parseInt(rowColumnId));
      rowColumn.addSubRow(db);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "Move");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!(hasPermission(context, "site-editor-edit"))) {
      return ("PermissionError");
    }
    String siteId = context.getRequest().getParameter("siteId");
    if (siteId == null || "".equals(siteId.trim())) {
      siteId = (String) context.getRequest().getAttribute("siteId");
    }
    String pageVersionId = context.getRequest().getParameter("pageVersionId");
    if (pageVersionId == null || "".equals(pageVersionId.trim())) {
      pageVersionId = (String) context.getRequest().getAttribute("pageVersionId");
    }
    String rowColumnId = context.getRequest().getParameter("rowColumnId");
    if (rowColumnId == null || "".equals(rowColumnId.trim())) {
      rowColumnId = (String) context.getRequest().getAttribute("rowColumnId");
    }
    RowColumn rowColumn = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = this.getConnection(context);
      rowColumn = new RowColumn(db, Integer.parseInt(rowColumnId));
      //dependencies
      DependencyList dependencies = rowColumn.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      if (dependencies.canDelete()) {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("actionphase.dependencies"));
        htmlDialog.addButton(systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='RowColumns.do?command=Delete&pageVersionId=" + pageVersionId + "&rowColumnId=" + rowColumn.getId() + "&siteId=" + siteId + "&popup=true'");
        htmlDialog.addButton(systemStatus.getLabel("button.cancel"), "javascript:parent.window.close();");
      } else {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.unableHeader"));
        htmlDialog.addButton("OK", "javascript:parent.window.close()");
      }
      context.getSession().setAttribute("Dialog", htmlDialog);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "ConfirmDelete");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!(hasPermission(context, "site-editor-edit"))) {
      return ("PermissionError");
    }
    String siteId = context.getRequest().getParameter("siteId");
    if (siteId == null || "".equals(siteId.trim())) {
      siteId = (String) context.getRequest().getAttribute("siteId");
    }
    String pageVersionId = (String) context.getRequest().getParameter("pageVersionId");
    if (pageVersionId != null && !"".equals(pageVersionId)) {
      context.getRequest().setAttribute("pageVersionId", pageVersionId);
    }
    String rowColumnId = context.getRequest().getParameter("rowColumnId");
    if (rowColumnId == null || "".equals(rowColumnId)) {
      rowColumnId = (String) context.getRequest().getAttribute("rowColumnId");
    }
    PageVersion pageVersion = null;
    RowColumn rowColumn = null;
    int tabId = -1;
    Connection db = null;
    try {
      db = getConnection(context);
      if (pageVersionId != null && !"".equals(pageVersionId.trim()) && !"-1".equals(pageVersionId.trim())) {
        pageVersion = new PageVersion(db, Integer.parseInt(pageVersionId));
        tabId = pageVersion.getTabIdByPageVersionId(db);
      }
      rowColumn = new RowColumn();
      rowColumn.queryRecord(db, Integer.parseInt(rowColumnId));
      //delete the rowColumn
      rowColumn.delete(db);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("refreshUrl", "Sites.do?command=Details&siteId=" + siteId + "&tabId=" + tabId + "&pageId=" + (pageVersion != null? pageVersion.getPageId(): -1) + "&popup=true");
    return this.getReturn(context, "Delete");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAddIcelet(ActionContext context) {
    if (!(hasPermission(context, "site-editor-edit"))) {
      return ("PermissionError");
    }
    String pageRowId = context.getRequest().getParameter("pageRowId");
    if (pageRowId == null || "".equals(pageRowId.trim())) {
      pageRowId = (String) context.getRequest().getAttribute("pageRowId");
    }
    RowColumn rowColumn = (RowColumn) context.getFormBean();
    String nextRowColumnId = context.getRequest().getParameter("nextRowColumnId");
    if (nextRowColumnId == null || "".equals(nextRowColumnId.trim())) {
      nextRowColumnId = (String) context.getRequest().getAttribute("nextRowColumnId");
    }
    if (nextRowColumnId != null && !"".equals(nextRowColumnId)) {
      rowColumn.setNextRowColumnId(nextRowColumnId);
      context.getRequest().setAttribute("nextRowColumnId", nextRowColumnId);
    }
    String buildLastColumn = context.getRequest().getParameter("buildLastColumn");
    PageRow pageRow = null;
    IceletList iceletList = new IceletList();
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = this.getConnection(context);
      pageRow = new PageRow();
      pageRow.setBuildRowColumnList(true);
      pageRow.queryRecord(db, Integer.parseInt(pageRowId));
      context.getRequest().setAttribute("pageRow", pageRow);
      if (buildLastColumn != null && DatabaseUtils.parseBoolean(buildLastColumn)) {
        //If the "Add Column" is selected from the row dropdown
        RowColumnList columnList = new RowColumnList();
        columnList.setPageRowId(pageRowId);
        columnList.setBuildLastPosition(true);
        columnList.buildList(db);
        rowColumn.setPreviousRowColumnId(columnList.getLastPositionColumnId());
        context.getRequest().setAttribute("previousRowColumnId", columnList.getLastPositionColumnId());
      } else {
        //If the "Add Column after" is selected from the column dropdown
        String previousRowColumnId = context.getRequest().getParameter("previousRowColumnId");
        if (previousRowColumnId == null || "".equals(previousRowColumnId.trim())) {
          previousRowColumnId = (String) context.getRequest().getAttribute("previousRowColumnId");
        }
        if (previousRowColumnId != null && !"".equals(previousRowColumnId)) {
          rowColumn.setPreviousRowColumnId(previousRowColumnId);
          context.getRequest().setAttribute("previousRowColumnId", previousRowColumnId);
        }
      }
      //Build the iceletList
      iceletList.buildList(db);
      iceletList.setEmptyHtmlSelectRecord(systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("icelets", iceletList);
      rowColumn.setEnabled(true);
      rowColumn.setWidth(50);
      context.getRequest().setAttribute("rowColumn", rowColumn);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "AddIcelet");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandReplaceIcelet(ActionContext context) {
    if (!(hasPermission(context, "site-editor-edit"))) {
      return ("PermissionError");
    }
    String pageRowId = context.getRequest().getParameter("pageRowId");
    if (pageRowId == null || "".equals(pageRowId.trim())) {
      pageRowId = (String) context.getRequest().getAttribute("pageRowId");
    }
    String rowColumnId = context.getRequest().getParameter("rowColumnId");
    if (rowColumnId == null || "".equals(rowColumnId.trim())) {
      rowColumnId = (String) context.getRequest().getAttribute("rowColumnId");
    }
    RowColumn rowColumn = (RowColumn) context.getFormBean();
    String iceletId = context.getRequest().getParameter("iceletId");
    String width = context.getRequest().getParameter("width");
    PageRow pageRow = null;
    IceletList iceletList = new IceletList();
    Icelet icelet = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = this.getConnection(context);
      pageRow = new PageRow();
      pageRow.setBuildRowColumnList(true);
      pageRow.queryRecord(db, Integer.parseInt(pageRowId));
      context.getRequest().setAttribute("pageRow", pageRow);
      if (rowColumn.getId() == -1) {
        rowColumn.queryRecord(db, Integer.parseInt(rowColumnId));
        if (rowColumn.getIceletId() != -1) {
          rowColumn.buildIcelet(db);
//          rowColumn.setDefaultPropertyMap(this.getIcletPrefs(context, rowColumn.getIcelet().getConfiguratorClass()));
//          rowColumn.buildIceletPropertyMap(db);
        }
      }
      rowColumn.setModifiedBy(this.getUserId(context));
      if (width != null && !"".equals(width.trim())) {
        rowColumn.setWidth(width);
      }
      if (iceletId != null && !"".equals(iceletId.trim())) {
        if (rowColumn.getIceletId() != Integer.parseInt(iceletId)) {
          rowColumn.setIceletId(iceletId);
          rowColumn.setIcelet(null);
          rowColumn.setIceletPropertyMap(null);
        }
      }
      iceletList.buildList(db);
      iceletList.setEmptyHtmlSelectRecord(systemStatus.getLabel("calendar.none.4dashes"));
      if (rowColumn.getIceletId() > -1) {
        icelet = (Icelet) iceletList.getIceletById(rowColumn.getIceletId());
        context.getRequest().setAttribute("icelet", icelet);
      }
      context.getRequest().setAttribute("icelets", iceletList);
      context.getRequest().setAttribute("rowColumn", rowColumn);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "ReplaceIcelet");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandModifyIceletProperties(ActionContext context) {
    if (!(hasPermission(context, "site-editor-edit"))) {
      return ("PermissionError");
    }
    String rowColumnId = context.getRequest().getParameter("rowColumnId");
    if (rowColumnId == null || "".equals(rowColumnId.trim())) {
      rowColumnId = (String) context.getRequest().getAttribute("rowColumnId");
    }
    String iceletId = context.getRequest().getParameter("iceletId");
    String width = context.getRequest().getParameter("width");
    RowColumn rowColumn = (RowColumn) context.getFormBean();
    SystemStatus systemStatus = this.getSystemStatus(context);
    HashMap propertyMap = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      if (rowColumnId != null && !"".equals(rowColumnId.trim()) && !"-1".equals(rowColumnId.trim()) && rowColumn.getId() == -1) {
        rowColumn.setBuildIcelet(true);
        rowColumn.setBuildIceletPropertyMap(true);
        rowColumn.queryRecord(db, Integer.parseInt(rowColumnId.trim()));
      }
      if (iceletId != null && !"".equals(iceletId.trim())) {
        rowColumn.setIceletId(iceletId);
      }
      if (width != null && !"".equals(width.trim())) {
        rowColumn.setWidth(width);
      }
      context.getRequest().setAttribute("rowColumn", rowColumn);
      if (rowColumn.getIceletId() > -1) {
        Icelet icelet = new Icelet(db, Integer.parseInt(iceletId));
        propertyMap = (HashMap) this.getIcletPrefs(context, icelet.getConfiguratorClass());
        context.getRequest().setAttribute("icelet", icelet);
        context.getRequest().setAttribute("propertyMap", propertyMap);
        LookupList leadSourceSelect = systemStatus.getLookupList(db, "lookup_contact_source");
        leadSourceSelect.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
        context.getRequest().setAttribute("leadSourceSelect", leadSourceSelect);
      }
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (propertyMap == null || propertyMap.size() == 0) {
      return executeCommandSaveIcelet(context);
    }
    return this.getReturn(context, "ModifyIceletProperties");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSaveIcelet(ActionContext context) {
    if (!(hasPermission(context, "site-editor-edit"))) {
      return ("PermissionError");
    }
    String pageRowId = context.getRequest().getParameter("pageRowId");
    if (pageRowId == null || "".equals(pageRowId.trim())) {
      pageRowId = (String) context.getRequest().getAttribute("pageRowId");
    }
    String previousRowColumnId = context.getRequest().getParameter("previousRowColumnId");
    String nextRowColumnId = context.getRequest().getParameter("nextRowColumnId");
    String rowColumnId = context.getRequest().getParameter("rowColumnId");
    if (rowColumnId == null || "".equals(rowColumnId.trim())) {
      rowColumnId = (String) context.getRequest().getAttribute("rowColumnId");
    }
    String iceletId = context.getRequest().getParameter("iceletId");
    String width = context.getRequest().getParameter("width");
    RowColumn rowColumn = (RowColumn) context.getFormBean();
    if (iceletId != null && !"".equals(iceletId.trim())) {
      rowColumn.setIceletId(iceletId);
    }
    if (width != null && !"".equals(width.trim())) {
      rowColumn.setWidth(width);
    }
    RowColumn oldSelection = new RowColumn();
    boolean recordInserted = false;
    int recordCount = -1;
    boolean isValid = false;
    Connection db = null;
    try {
      db = this.getConnection(context);
      //Build the old Row Column if it exists
      if (rowColumn.getId() != -1) {
        oldSelection.setBuildIcelet(true);
        //Build the old icelet properties if they exist
        oldSelection.setBuildIceletPropertyMap(true);
        oldSelection.queryRecord(db, Integer.parseInt(rowColumnId));
//        rowColumn.setPosition(oldSelection.getPosition());
//        rowColumn.setPageRowId(oldSelection.getPageRowId());
      }
      rowColumn.setModifiedBy(this.getUserId(context));
      rowColumn.setEnabled(true); //Duplicate code.
      if (rowColumn.getIceletPropertyMap() == null) {
        rowColumn.setIceletPropertyMap(new IceletPropertyMap());
      }
      rowColumn.getIceletPropertyMap().setModifiedBy(rowColumn.getModifiedBy());
      //If rowColumn is being modified..
      if (rowColumn.getId() > -1) {
        rowColumn.getIceletPropertyMap().setIceletRowColumnId(rowColumn.getId());
        //Build the icelet properties map from the request
        rowColumn.getIceletPropertyMap().setRequestItems(context);
        if (oldSelection.getIceletId() > -1) {
          //delete old properties and insert new properties rowColumn
          rowColumn.parsePropertyMapEntries(db, oldSelection);
          //Update the row column
          rowColumn.update(db);
          rowColumn.queryRecord(db, rowColumn.getId());
        } else {
          //Just inserting a new icelet for the RC
          rowColumn.getIceletPropertyMap().insert(db);
          rowColumn.setOverride(true);
          //Update the row column
          rowColumn.update(db);
          rowColumn.queryRecord(db, rowColumn.getId());
        }
      } else {
        //Row column is being inserted
        rowColumn.setPosition(rowColumn.computeRowColumnPosition(db));
        //Insert the rowColumn.
        recordInserted = rowColumn.insert(db);
        rowColumn.queryRecord(db, rowColumn.getId());
        //Insert the new icelet properties for the row column
        //Build the icelet properties map from the request
        rowColumn.getIceletPropertyMap().setRequestItems(context);
        //Set the rowColumn id for all the icelet properties
        rowColumn.getIceletPropertyMap().addAdditionalInformation(rowColumn.getId(), rowColumn.getModifiedBy());
        //Insert the icelet properties map
        rowColumn.getIceletPropertyMap().insert(db);
      }
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "SaveIcelet");
  }
}

