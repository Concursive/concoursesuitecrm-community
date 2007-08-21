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
package org.aspcfs.modules.troubletickets.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.troubletickets.base.TicketDefect;
import org.aspcfs.modules.troubletickets.base.TicketDefectList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 *  Description of the Class
 *
 *@author     partha
 *@created    October 4, 2005
 *@version    $Id: TroubleTicketDefects.java,v 1.1.4.3 2005/11/14 21:05:14
 *      kbhoopal Exp $
 */
public final class TroubleTicketDefects extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!(hasPermission(context, "tickets-defects-view"))) {
      return ("DefaultError");
    }
    return (this.executeCommandView(context));
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!(hasPermission(context, "tickets-defects-view"))) {
      return ("PermissionError");
    }
    PagedListInfo defectListInfo = this.getPagedListInfo(context, "defectListInfo");
    defectListInfo.setLink("TroubleTicketDefects.do?command=View");
    TicketDefectList defects = new TicketDefectList();
    User user = this.getUser(context, this.getUserId(context));
    Connection db = null;
    try {
      db = this.getConnection(context);
      defects.setBuildTickets(true);
      defects.setPagedListInfo(defectListInfo);
      defectListInfo.setSearchCriteria(defects, context);
      defects.setSiteId(this.getUserSiteId(context));
      if (user.getSiteId() == -1) {
        defects.setIncludeAllSites(true);
      }
      defects.buildList(db);
      context.getRequest().setAttribute("defects", defects);

      buildFormElements(context, db);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ListOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
    if (!(hasPermission(context, "tickets-defects-add"))) {
      return ("PermissionError");
    }
    TicketDefect defect = (TicketDefect) context.getFormBean();
      if (context.getRequest().getParameter("title") == null) {
        defect.setStartDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        defect.setEnabled(true);
        if (this.getUserSiteId(context) == -1){
          defect.setSiteId(Constants.INVALID_SITE);
        } else {
          defect.setSiteId(this.getUserSiteId(context));
        }
      }
      context.getRequest().setAttribute("defect", defect);
      buildFormElements(context, db);
    return ("AddOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
    
  private String executeCommandModify(ActionContext context,Connection db) throws Exception {
    if (!(hasPermission(context, "tickets-defects-edit"))) {
      return ("PermissionError");
    }
    String defectId = context.getRequest().getParameter("defectId");
    if (defectId == null || "".equals(defectId)) {
      defectId = (String) context.getRequest().getAttribute("defectId");
    }

    TicketDefect defect = (TicketDefect) context.getFormBean();
      if (defect.getId() == -1) {
        defect = new TicketDefect(db, Integer.parseInt(defectId));
      }
      //Check access permission to defect record
      if (!isRecordAccessPermitted(context, defect)) {
        return ("PermissionError");
      }
      defect.printDefect();
      context.getRequest().setAttribute("defect", defect);
      buildFormElements(context, db);
    return ("ModifyOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!(hasPermission(context, "tickets-defects-add"))) {
      return ("PermissionError");
    }
    boolean isValid = false;
    boolean recordInserted = false;
    int resultCount = -1;
    TicketDefect defect = (TicketDefect) context.getFormBean();
    Connection db = null;
    try {
      db = this.getConnection(context);
      //Check access permission to defect record
      if (!isRecordAccessPermitted(context, defect)) {
        return ("PermissionError");
      }
      isValid = this.validateObject(context, db, defect);
      if (isValid) {
        if (defect.getId() > -1) {
          resultCount = defect.update(db);
        } else {
          recordInserted = defect.insert(db);
        }
      }
      context.getRequest().setAttribute("defectId", String.valueOf(defect.getId()));
      if (!isValid || (!recordInserted && resultCount == -1)) {
        context.getRequest().setAttribute("defect", defect);
        if (defect.getId() != -1) {
          return executeCommandModify(context,db);
        } else {
          return executeCommandAdd(context,db);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("SaveOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "tickets-defects-view"))) {
      return ("PermissionError");
    }
    String defectId = context.getRequest().getParameter("defectId");
    if (defectId == null || "".equals(defectId)) {
      defectId = (String) context.getRequest().getAttribute("defectId");
    }
    TicketDefect defect = new TicketDefect();
    Connection db = null;
    try {
      db = this.getConnection(context);
      defect.queryRecord(db, Integer.parseInt(defectId));
      //Check access permission to defect record
      if (defect.getSiteId() != -1){
        if (!isRecordAccessPermitted(context, defect)) {
          return ("PermissionError");
        }
      }
      defect.buildTickets(db, this.getUserSiteId(context));
      context.getRequest().setAttribute("defect", defect);

      buildFormElements(context, db);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("DetailsOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!(hasPermission(context, "tickets-defects-delete"))) {
      return ("PermissionError");
    }
    String defectId = context.getRequest().getParameter("defectId");
    if (defectId == null || "".equals(defectId)) {
      defectId = (String) context.getRequest().getAttribute("defectId");
    }
    TicketDefect defect = new TicketDefect();
    Connection db = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      defect.setBuildTickets(true);
      defect.queryRecord(db, Integer.parseInt(defectId));
      context.getRequest().setAttribute("defect", defect);
      //Check access permission to defect record
      if (!isRecordAccessPermitted(context, defect)) {
        return ("PermissionError");
      }

      DependencyList dependencies = defect.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      if (dependencies.canDelete()) {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("ticketDefect.dependencies"));
        htmlDialog.addButton(
            systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='TroubleTicketDefects.do?command=Trash&defectId=" + defect.getId() + "';");
        htmlDialog.addButton(
            systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
      } else {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.unableHeader"));
        htmlDialog.addButton("OK", "javascript:parent.window.close()");
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getSession().setAttribute("Dialog", htmlDialog);
    return ("ConfirmDeleteOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandTrash(ActionContext context) {
    if (!hasPermission(context, "tickets-defects-delete")) {
      return ("PermissionError");
    }
    boolean recordUpdated = false;
    TicketDefect defect = null;
    Connection db = null;
    //Parameters
    String passedId = context.getRequest().getParameter("defectId");
    try {
      db = this.getConnection(context);
      defect = new TicketDefect(db, Integer.parseInt(passedId));
      //Check access permission to defect record
      if (!isRecordAccessPermitted(context, defect)) {
        return ("PermissionError");
      }
      recordUpdated = defect.updateStatus(db, true, this.getUserId(context));
      if (recordUpdated) {
        String returnType = (String) context.getRequest().getParameter("return");
        context.getRequest().setAttribute("refreshUrl", "TroubleTicketDefects.do?command=View");
        return ("DeleteOK");
      }
      return (executeCommandView(context));
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!(hasPermission(context, "tickets-defects-delete"))) {
      return ("PermissionError");
    }
    String defectId = context.getRequest().getParameter("defectId");
    if (defectId == null || "".equals(defectId)) {
      defectId = (String) context.getRequest().getAttribute("defectId");
    }
    TicketDefect defect = new TicketDefect();
    Connection db = null;
    try {
      db = getConnection(context);
      defect.setBuildTickets(true);
      defect.queryRecord(db, Integer.parseInt(defectId));
      //Check access permission to defect record
      if (!isRecordAccessPermitted(context, defect)) {
        return ("PermissionError");
      }
      //delete the defect
      defect.delete(db);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("refreshUrl", "TroubleTicketDefects.do?command=View");
    return "DeleteOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context           Description of the Parameter
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildFormElements(ActionContext context, Connection db) throws SQLException {
    SystemStatus systemStatus = this.getSystemStatus(context);

    LookupList siteList = new LookupList(db, "lookup_site_id");
//    siteList.addItem(Constants.INVALID_SITE, systemStatus.getLabel("accounts.allSites"));
    siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute("SiteList", siteList);
  }
}
/*
 *  public String executeCommandXXXX(ActionContext context) {
 *  if (!(hasPermission(context, "tickets-defects-view"))) {
 *  return ("PermissionError");
 *  }
 *  Connection db = null;
 *  try {
 *  db = this.getConnection(context);
 *  } catch (Exception e) {
 *  e.printStackTrace();
 *  context.getRequest().setAttribute("Error", e);
 *  return ("SystemError");
 *  } finally {
 *  this.freeConnection(context, db);
 *  }
 *  return ("XXXXOK");
 *  }
 */

