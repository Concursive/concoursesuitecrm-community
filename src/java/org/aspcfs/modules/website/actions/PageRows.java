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
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
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
import org.aspcfs.modules.website.icelet.HtmlContentPortlet;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlDialog;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    April 28, 2006
 * @version    $Id: Exp$
 */
public final class PageRows extends CFSModule {

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
  public String executeCommandAdd(ActionContext context) {
    if (!(hasPermission(context, "site-editor-edit"))) {
      return ("PermissionError");
    }
    String pageVersionId = context.getRequest().getParameter("pageVersionId");
    String rowColumnId = context.getRequest().getParameter("rowColumnId");
    PageRow pageRow = (PageRow) context.getFormBean();
    String previousPageRowId = context.getRequest().getParameter("previousPageRowId");
    if (previousPageRowId != null && !"".equals(previousPageRowId.trim())) {
      pageRow.setPreviousPageRowId(previousPageRowId);
    }
    String nextPageRowId = context.getRequest().getParameter("nextPageRowId");
    if (nextPageRowId != null && !"".equals(nextPageRowId.trim())) {
      pageRow.setNextPageRowId(nextPageRowId);
    }
    PageVersion pageVersion = null;
    RowColumn rowColumn = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = this.getConnection(context);
      if (pageVersionId != null && !"".equals(pageVersionId.trim()) && !"-1".equals(pageVersionId.trim())) {
        pageVersion = new PageVersion(db, Integer.parseInt(pageVersionId));
        if (pageRow.getPageVersionId() == -1) {
          pageRow.setPageVersionId(pageVersionId);
        }
      } else if (rowColumnId != null && !"".equals(rowColumnId.trim()) && !"-1".equals(rowColumnId.trim())) {
        rowColumn = new RowColumn(db, Integer.parseInt(rowColumnId));
        if (pageRow.getRowColumnId() == -1) {
          pageRow.setRowColumnId(rowColumnId);
        }
      }
      pageRow.setModifiedBy(getUserId(context));
      pageRow.setEnabled(true);
      pageRow.setPosition(pageRow.computePageRowPosition(db));
      pageRow.insert(db);
      insertDefaultData(context, db, pageRow.getId());
//      context.getRequest().setAttribute("pageId", String.valueOf(pageVersion.getPageId()));
//      context.getRequest().setAttribute("tabId", String.valueOf(pageVersion.getTabIdByPageVersionId(db)));
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "Save");
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
    String pageVersionId = context.getRequest().getParameter("pageVersionId");
    String rowColumnId = context.getRequest().getParameter("rowColumnId");
    String pageRowId = context.getRequest().getParameter("pageRowId");
    String movePageRowUp = context.getRequest().getParameter("movePageRowUp");
    if (movePageRowUp == null || "".equals(movePageRowUp.trim())) {
      movePageRowUp = new String("YES");
    }
    PageVersion pageVersion = null;
    RowColumn rowColumn = null;
    PageRow pageRow = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      pageRow = new PageRow(db, Integer.parseInt(pageRowId));
      if (pageVersionId != null && !"".equals(pageVersionId.trim()) && !"-1".equals(pageVersionId.trim())) {
        pageVersion = new PageVersion(db, Integer.parseInt(pageVersionId));
        if (pageRow.getPageVersionId() == -1) {
          pageRow.setPageVersionId(pageVersionId);
        }
      } else if (rowColumnId != null && !"".equals(rowColumnId.trim()) && !"-1".equals(rowColumnId.trim())) {
        rowColumn = new RowColumn(db, Integer.parseInt(rowColumnId));
        if (pageRow.getRowColumnId() == -1) {
          pageRow.setRowColumnId(rowColumnId);
        }
      }
      pageRow.move(db, DatabaseUtils.parseBoolean(movePageRowUp));
//      context.getRequest().setAttribute("pageId", String.valueOf(pageVersion.getPageId()));
//      context.getRequest().setAttribute("tabId", String.valueOf(pageVersion.getTabIdByPageVersionId(db)));
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
    String pageRowId = context.getRequest().getParameter("pageRowId");
    if (pageRowId == null || "".equals(pageRowId.trim())) {
      pageRowId = (String) context.getRequest().getAttribute("pageRowId");
    }
    RowColumn rowColumn = null;
    PageRow pageRow = null;
    PageVersion pageVersion = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = this.getConnection(context);
      pageRow = new PageRow(db, Integer.parseInt(pageRowId));
      if (rowColumnId != null && !"".equals(rowColumnId.trim()) && !"-1".equals(rowColumnId.trim())) {
        rowColumn = new RowColumn(db, Integer.parseInt(rowColumnId));
      }
      //dependencies
      DependencyList dependencies = pageRow.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      if (dependencies.canDelete()) {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("actionphase.dependencies"));
        htmlDialog.addButton(systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='PageRows.do?command=Delete"+(pageRow.getPageVersionId() != -1?"&pageVersionId=" + pageRow.getPageVersionId():"") + "&pageRowId=" + pageRow.getId() + "&siteId=" + siteId + "&popup=true'");
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
    String pageRowId = context.getRequest().getParameter("pageRowId");
    if (pageRowId == null || "".equals(pageRowId)) {
      pageRowId = (String) context.getRequest().getAttribute("pageRowId");
    }
    PageVersion pageVersion = null;
    PageRow pageRow = null;
    int tabId = -1;
    Connection db = null;
    try {
      db = getConnection(context);
      if (pageVersionId != null && !"".equals(pageVersionId.trim()) && !"-1".equals(pageVersionId.trim())) {
        pageVersion = new PageVersion(db, Integer.parseInt(pageVersionId));
        tabId = pageVersion.getTabIdByPageVersionId(db);
      }
      pageRow = new PageRow();
      pageRow.queryRecord(db, Integer.parseInt(pageRowId));
      //delete the pageRow
      pageRow.delete(db);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    String refreshUrl = (String) context.getSession().getAttribute("refreshUrl");
    if (refreshUrl != null) {
    	context.getRequest().setAttribute("refreshUrl", refreshUrl);
    	context.getSession().setAttribute("refreshUrl", null);
    }
    else {
    	context.getRequest().setAttribute("refreshUrl", "Sites.do?command=Details&siteId=" + siteId + "&tabId=" + tabId + "&pageId=" + (pageVersion != null ?pageVersion.getPageId(): -1) + "&popup=true");
    }
    return this.getReturn(context, "Delete");
  }


  /**
   *  Description of the Method
   *
   * @param  context           Description of the Parameter
   * @param  db                Description of the Parameter
   * @param  rowId             Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void insertDefaultData(ActionContext context, Connection db, int rowId) throws SQLException {
    SystemStatus systemStatus = this.getSystemStatus(context);
    int userId = this.getUserId(context);
    //Build the icelet from the database
    Icelet htmlIcelet = null;
    IceletList iceletList = new IceletList();
    iceletList.setConfiguratorClass("HtmlContentPortlet");
    iceletList.buildList(db);
    if (iceletList.size() > 0) {
      htmlIcelet = (Icelet) iceletList.get(0);
    }
    //Insert the row_column
    RowColumn rowColumn = new RowColumn();
    rowColumn.setPageRowId(rowId);
    rowColumn.setModifiedBy(userId);
    rowColumn.setEnabled(true);
    rowColumn.setWidth(100);
    if (htmlIcelet != null && htmlIcelet.getId() > -1) {
      rowColumn.setIceletId(htmlIcelet.getId());
    }
    rowColumn.setPosition(RowColumn.INITIAL_POSITION);
    rowColumn.insert(db);
    if (htmlIcelet != null && htmlIcelet.getId() > -1) {
      //Insert the icelet.
      IceletProperty property = new IceletProperty();
      property.setRowColumnId(rowColumn.getId());
      property.setValue("Please enter your html text here");
      property.setTypeConstant(HtmlContentPortlet.PROPERTY_HTMLTEXT);
      property.setModifiedBy(userId);
      property.insert(db);
    }
  }
}

