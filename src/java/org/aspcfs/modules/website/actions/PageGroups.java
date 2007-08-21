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
 * @created    May 5, 2006
 */
public final class PageGroups extends CFSModule {

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
    Connection db = null;
    String status = null;
    try {
      db = this.getConnection(context);
      status = this.executeCommandAdd(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return status;
  }      
        
  private String executeCommandAdd(ActionContext context,Connection db) throws SQLException {
    if (!(hasPermission(context, "site-editor-edit"))) {
      return ("PermissionError");
    }
    PageGroup pageGroup = (PageGroup) context.getFormBean();
    String tabId = context.getRequest().getParameter("tabId");
    if (tabId == null || "".equals(tabId.trim())) {
      tabId = (String) context.getRequest().getAttribute("tabId");
    }
    if (tabId != null && !"".equals(tabId.trim())) {
      pageGroup.setTabId(tabId);
    }
    String previousPageGroupId = context.getRequest().getParameter("previousPageGroupId");
    if (previousPageGroupId == null || "".equals(previousPageGroupId.trim())) {
      previousPageGroupId = (String) context.getRequest().getAttribute("previousPageGroupId");
    }
    if (previousPageGroupId != null && !"".equals(previousPageGroupId)) {
      pageGroup.setPreviousPageGroupId(previousPageGroupId);
      context.getRequest().setAttribute("previousPageGroupId", previousPageGroupId);
    }
    String nextPageGroupId = context.getRequest().getParameter("nextPageGroupId");
    if (nextPageGroupId == null || "".equals(nextPageGroupId.trim())) {
      nextPageGroupId = (String) context.getRequest().getAttribute("nextPageGroupId");
    }
    if (nextPageGroupId != null && !"".equals(nextPageGroupId)) {
      pageGroup.setNextPageGroupId(nextPageGroupId);
    }
    Tab tab = null;
      db = this.getConnection(context);
      tab = new Tab();
      tab.queryRecord(db, Integer.parseInt(tabId));
      tab.buildPageGroupList(db);
      context.getRequest().setAttribute("tab", tab);
      context.getRequest().setAttribute("thisPageGroup", pageGroup);
    return this.getReturn(context, "Add");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    Connection db = null;
    String status = null;
    try {
      db = this.getConnection(context);
      status = this.executeCommandModify(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return status;
  }  

  private String executeCommandModify(ActionContext context,Connection db) throws NumberFormatException, SQLException {  
    if (!(hasPermission(context, "site-editor-edit"))) {
      return ("PermissionError");
    }
    String tabId = context.getRequest().getParameter("tabId");
    if (tabId == null || "".equals(tabId.trim())) {
      tabId = (String) context.getRequest().getAttribute("tabId");
    }
    String pageGroupId = context.getRequest().getParameter("pageGroupId");
    if (pageGroupId == null || "".equals(pageGroupId.trim())) {
      pageGroupId = (String) context.getRequest().getAttribute("pageGroupId");
    }
    PageGroup pageGroup = (PageGroup) context.getFormBean();
    Tab tab = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
      tab = new Tab();
      tab.queryRecord(db, Integer.parseInt(tabId));
      tab.buildPageGroupList(db);
      context.getRequest().setAttribute("tab", tab);
      if (pageGroup.getId() == -1) {
        pageGroup = new PageGroup(db, Integer.parseInt(pageGroupId));
      }
      pageGroup.setTabId(tabId);
      context.getRequest().setAttribute("thisPageGroup", pageGroup);
    return this.getReturn(context, "Modify");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!(hasPermission(context, "site-editor-edit"))) {
      return ("PermissionError");
    }
    String tabId = context.getRequest().getParameter("tabId");
    if (tabId == null || "".equals(tabId.trim())) {
      tabId = (String) context.getRequest().getAttribute("tabId");
    }
    String previousPageId = context.getRequest().getParameter("previousPageId");
    String nextPageId = context.getRequest().getParameter("nextPageId");
    boolean isValid = false;
    boolean recordInserted = false;
    int recordCount = -1;
    PageGroup pageGroup = (PageGroup) context.getFormBean();
    Tab tab = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = this.getConnection(context);
      if (pageGroup.getTabId() == -1) {
        pageGroup.setTabId(tabId);
      }
      pageGroup.setModifiedBy(getUserId(context));
      isValid = this.validateObject(context, db, pageGroup);
      if (isValid) {
        if (pageGroup.getId() > -1) {
          recordCount = pageGroup.update(db);
        } else {
          pageGroup.setPosition(pageGroup.computePageGroupPosition(db));
          recordInserted = pageGroup.insert(db);
          if (recordInserted) {
            insertDefaultData(context, db, pageGroup.getId());
          }
        }
      }
      if (!isValid || (!recordInserted && recordCount == -1)) {
        if (nextPageId != null && !"".equals(nextPageId.trim())) {
          context.getRequest().setAttribute("nextPageId", nextPageId);
        }
        if (previousPageId != null && !"".equals(previousPageId.trim())) {
          context.getRequest().setAttribute("previousPageId", previousPageId);
        }
        if (pageGroup.getId() > -1) {
          return executeCommandModify(context,db);
        } else {
          return executeCommandAdd(context,db);
        }
      }
      context.getRequest().setAttribute("tabId", String.valueOf(pageGroup.getTabId()));
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
    String pageGroupId = context.getRequest().getParameter("pageGroupId");
    String movePageGroupUp = context.getRequest().getParameter("movePageGroupUp");
    if (movePageGroupUp == null || "".equals(movePageGroupUp.trim())) {
      movePageGroupUp = new String("YES");
    }
    PageGroup pageGroup = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      pageGroup = new PageGroup(db, Integer.parseInt(pageGroupId));
      pageGroup.move(db, DatabaseUtils.parseBoolean(movePageGroupUp));
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
    if (!(hasPermission(context, "site-editor-delete"))) {
      return ("PermissionError");
    }
    String siteId = context.getRequest().getParameter("siteId");
    if (siteId == null || "".equals(siteId.trim())) {
      siteId = (String) context.getRequest().getAttribute("siteId");
    }
    String tabId = context.getRequest().getParameter("tabId");
    if (tabId == null || "".equals(tabId.trim())) {
      tabId = (String) context.getRequest().getAttribute("tabId");
    }
    String pageGroupId = context.getRequest().getParameter("pageGroupId");
    if (pageGroupId == null || "".equals(pageGroupId.trim())) {
      pageGroupId = (String) context.getRequest().getAttribute("pageGroupId");
    }
    PageGroup pageGroup = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = this.getConnection(context);
      pageGroup = new PageGroup(db, Integer.parseInt(pageGroupId));
      //dependencies
      DependencyList dependencies = pageGroup.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      if (dependencies.canDelete()) {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("actionphase.dependencies"));
        htmlDialog.addButton(systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='PageGroups.do?command=Delete&pageGroupId=" + pageGroup.getId() + "&siteId=" + siteId + "&popup=true'");
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
    if (!(hasPermission(context, "site-editor-delete"))) {
      return ("PermissionError");
    }
    String siteId = (String) context.getRequest().getParameter("siteId");
    if (siteId != null && !"".equals(siteId)) {
      context.getRequest().setAttribute("siteId", siteId);
    }
    String pageGroupId = context.getRequest().getParameter("pageGroupId");
    if (pageGroupId == null || "".equals(pageGroupId)) {
      pageGroupId = (String) context.getRequest().getAttribute("pageGroupId");
    }
    PageGroup pageGroup = null;
    int tabId = -1;
    Connection db = null;
    try {
      db = getConnection(context);
      pageGroup = new PageGroup();
      pageGroup.queryRecord(db, Integer.parseInt(pageGroupId));
      pageGroup.buildPageList(db);
      tabId = pageGroup.getTabId();
      //delete the pageGroup
      pageGroup.delete(db);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("refreshUrl", "Sites.do?command=Details&siteId=" + siteId + "&tabId=" + tabId + "&pageId=-1&popup=true");
    return this.getReturn(context, "Delete");
  }


  /**
   *  Description of the Method
   *
   * @param  context           Description of the Parameter
   * @param  db                Description of the Parameter
   * @param  pageGroupId       Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void insertDefaultData(ActionContext context, Connection db, int pageGroupId) throws SQLException {
    SystemStatus systemStatus = this.getSystemStatus(context);
    int userId = this.getUserId(context);
    //Insert the page_version
    PageVersion pageVersion = new PageVersion();
    pageVersion.setVersionNumber(PageVersion.INITIAL_VERSION);
    pageVersion.setModifiedBy(userId);
    pageVersion.insert(db);
    //Insert the page
    Page page = new Page();
    page.setName(systemStatus.getLabel("", "Home"));
    page.setPageGroupId(pageGroupId);
    page.setConstructionPageVersionId(pageVersion.getId());
    page.setActivePageVersionId(pageVersion.getId());
    page.setPosition(Page.INITIAL_POSITION);
    page.setEnabled(true);
    page.setModifiedBy(userId);
    page.insert(db);
    //Update the page version
    pageVersion = new PageVersion(db, pageVersion.getId());
    pageVersion.setOverride(true);
    pageVersion.setPageId(page.getId());
    pageVersion.update(db);
    //Insert the page_row
    PageRow pageRow = new PageRow();
    pageRow.setPageVersionId(page.getConstructionPageVersionId());
    pageRow.setPosition(PageRow.INITIAL_POSITION);
    pageRow.setEnabled(true);
    pageRow.setModifiedBy(userId);
    pageRow.insert(db);
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
    rowColumn.setPageRowId(pageRow.getId());
    rowColumn.setModifiedBy(userId);
    rowColumn.setEnabled(true);
    rowColumn.setWidth(100);
    rowColumn.setPosition(RowColumn.INITIAL_POSITION);
    if (htmlIcelet != null && htmlIcelet.getId() > -1) {
      rowColumn.setIceletId(htmlIcelet.getId());
    }
    rowColumn.insert(db);
    //Insert the icelet.
    if (htmlIcelet != null && htmlIcelet.getId() > -1) {
      IceletProperty property = new IceletProperty();
      property.setRowColumnId(rowColumn.getId());
      property.setValue("Please enter your html text here");
      property.setTypeConstant(HtmlContentPortlet.PROPERTY_HTMLTEXT);
      property.setModifiedBy(userId);
      property.insert(db);
    }
  }
}

