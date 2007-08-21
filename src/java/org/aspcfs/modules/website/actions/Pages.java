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
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.website.base.*;
import org.aspcfs.modules.website.icelet.HtmlContentPortlet;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlDialog;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Actions for the Pages module
 *
 * @author kailash
 * @version $Id: Exp $
 * @created February 10, 2006
 */
public final class Pages extends CFSModule {

  /**
   * Default: not used
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    return getReturn(context, "Default");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
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
    
  private String executeCommandAdd(ActionContext context,Connection db) throws NumberFormatException, SQLException {
    if (!(hasPermission(context, "site-editor-edit"))) {
      return ("PermissionError");
    }
    Page page = (Page) context.getFormBean();
    String pageGroupId = context.getRequest().getParameter("pageGroupId");
    if (pageGroupId == null || "".equals(pageGroupId.trim())) {
      pageGroupId = (String) context.getRequest().getAttribute("pageGroupId");
    }
    if (pageGroupId != null && !"".equals(pageGroupId.trim())) {
      page.setPageGroupId(pageGroupId);
    }
    String previousPageId = context.getRequest().getParameter("previousPageId");
    if (previousPageId == null || "".equals(previousPageId.trim())) {
      previousPageId = (String) context.getRequest().getAttribute("previousPageId");
    }
    if (previousPageId != null && !"".equals(previousPageId)) {
      page.setPreviousPageId(previousPageId);
      context.getRequest().setAttribute("previousPageId", previousPageId);
    }
    String nextPageId = context.getRequest().getParameter("nextPageId");
    if (nextPageId == null || "".equals(nextPageId.trim())) {
      nextPageId = (String) context.getRequest().getAttribute("nextPageId");
    }
    if (nextPageId != null && !"".equals(nextPageId)) {
      page.setNextPageId(nextPageId);
    }
    PageGroup pageGroup = null;
      pageGroup = new PageGroup();
      pageGroup.queryRecord(db, Integer.parseInt(pageGroupId));
      pageGroup.buildPageList(db);
      context.getRequest().setAttribute("pageGroup", pageGroup);
      context.getRequest().setAttribute("thisPage", page);
    return this.getReturn(context, "Add");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
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
    String pageGroupId = context.getRequest().getParameter("pageGroupId");
    if (pageGroupId == null || "".equals(pageGroupId.trim())) {
      pageGroupId = (String) context.getRequest().getAttribute("pageGroupId");
    }
    String pageId = context.getRequest().getParameter("pageId");
    if (pageId == null || "".equals(pageId.trim())) {
      pageId = (String) context.getRequest().getAttribute("pageId");
    }
    Page page = (Page) context.getFormBean();
    PageGroup pageGroup = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
      pageGroup = new PageGroup();
      pageGroup.queryRecord(db, Integer.parseInt(pageGroupId));
      pageGroup.buildPageList(db);
      context.getRequest().setAttribute("pageGroup", pageGroup);
      if (page.getId() == -1) {
        page = new Page(db, Integer.parseInt(pageId));
      }
      page.setPageGroupId(pageGroupId);
      context.getRequest().setAttribute("thisPage", page);
    return this.getReturn(context, "Modify");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!(hasPermission(context, "site-editor-edit"))) {
      return ("PermissionError");
    }
    String pageGroupId = context.getRequest().getParameter("pageGroupId");
    if (pageGroupId == null || "".equals(pageGroupId.trim())) {
      pageGroupId = (String) context.getRequest().getAttribute("pageGroupId");
    }
    String previousPageId = context.getRequest().getParameter("previousPageId");
    String nextPageId = context.getRequest().getParameter("nextPageId");
    boolean isValid = false;
    boolean recordInserted = false;
    int recordCount = -1;
    Page page = (Page) context.getFormBean();
    PageGroup pageGroup = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = this.getConnection(context);
      if (page.getPageGroupId() == -1) {
        page.setPageGroupId(pageGroupId);
      }
      page.setModifiedBy(getUserId(context));
      isValid = this.validateObject(context, db, page);
      if (isValid) {
        if (page.getId() > -1) {
          recordCount = page.update(db);
        } else {
          page.setPosition(page.computePagePosition(db));
          recordInserted = page.insert(db);
          if (recordInserted) {
            insertDefaultData(context, db, page);
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
        if (page.getId() > -1) {
          return executeCommandModify(context,db);
        } else {
          return executeCommandAdd(context,db);
        }
      }
      context.getRequest().setAttribute("pageGroupId", String.valueOf(page.getPageGroupId()));
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandMove(ActionContext context) {
    if (!(hasPermission(context, "site-editor-edit"))) {
      return ("PermissionError");
    }
    String pageId = context.getRequest().getParameter("pageId");
    String movePageUp = context.getRequest().getParameter("movePageUp");
    if (movePageUp == null || "".equals(movePageUp.trim())) {
      movePageUp = new String("YES");
    }
    Page page = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      page = new Page(db, Integer.parseInt(pageId));
      page.move(db, DatabaseUtils.parseBoolean(movePageUp));
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!(hasPermission(context, "site-editor-delete"))) {
      return ("PermissionError");
    }
    String siteId = context.getRequest().getParameter("siteId");
    if (siteId == null || "".equals(siteId.trim())) {
      siteId = (String) context.getRequest().getAttribute("siteId");
    }
    String pageGroupId = context.getRequest().getParameter("pageGroupId");
    if (pageGroupId == null || "".equals(pageGroupId.trim())) {
      pageGroupId = (String) context.getRequest().getAttribute("pageGroupId");
    }
    String pageId = context.getRequest().getParameter("pageId");
    if (pageId == null || "".equals(pageId.trim())) {
      pageId = (String) context.getRequest().getAttribute("pageId");
    }
    Page page = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = this.getConnection(context);
      page = new Page(db, Integer.parseInt(pageId));
      //dependencies
      DependencyList dependencies = page.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      if (dependencies.canDelete()) {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("actionphase.dependencies"));
        htmlDialog.addButton(systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='Pages.do?command=Delete&pageGroupId=" + pageGroupId + "&pageId=" + page.getId() + "&siteId=" + siteId + "&popup=true'");
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
    return getReturn(context, "ConfirmDelete");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!(hasPermission(context, "site-editor-delete"))) {
      return ("PermissionError");
    }
    String siteId = (String) context.getRequest().getParameter("siteId");
    if (siteId != null && !"".equals(siteId)) {
      context.getRequest().setAttribute("siteId", siteId);
    }
    String pageId = context.getRequest().getParameter("pageId");
    if (pageId == null || "".equals(pageId)) {
      pageId = (String) context.getRequest().getAttribute("pageId");
    }
    Page page = null;
    int tabId = -1;
    Connection db = null;
    try {
      db = getConnection(context);
      page = new Page();
      page.queryRecord(db, Integer.parseInt(pageId));
      page.buildPageVersionToView(db);
      tabId = page.getPageVersionToView().getTabIdByPageVersionId(db);
      //delete the page
      page.delete(db);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("refreshUrl", "Sites.do?command=Details&siteId=" + siteId + "&tabId=" + tabId + "&pageId=-1&popup=true");
    return getReturn(context, "Delete");
  }

  public String executeCommandSelectAlias(ActionContext context) {
    if (!hasPermission(context, "site-editor-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      int pageGroupId = Integer.parseInt(context.getRequest().getParameter("pageGroupId"));
      db = getConnection(context);
      // Generate a site map...
      // All the tab names, all the page group names, all the page names
      Site site = new Site(db, SiteList.querySiteIdFromPageGroupId(db, pageGroupId));
      site.setBuildAll(true);
      site.buildResources(db, -1, -1, Site.EDIT_MODE);
      context.getRequest().setAttribute("siteMap", site);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("SelectAliasOK");
  }



  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @param db      Description of the Parameter
   * @param page    Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insertDefaultData(ActionContext context, Connection db, Page page) throws SQLException {
    SystemStatus systemStatus = this.getSystemStatus(context);
    int userId = this.getUserId(context);
    //Insert the page_version
    PageVersion pageVersion = new PageVersion();
    pageVersion.setPageId(page.getId());
    pageVersion.setVersionNumber(PageVersion.INITIAL_VERSION);
    pageVersion.setModifiedBy(userId);
    pageVersion.insert(db);
    //Update the page
    page.setConstructionPageVersionId(pageVersion.getId());
    page.setActivePageVersionId(pageVersion.getId());
    page.setOverride(true);
    page.update(db);
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

