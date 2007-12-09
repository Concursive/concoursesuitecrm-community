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
package org.aspcfs.modules.admin.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.service.base.SyncClient;
import org.aspcfs.modules.service.base.SyncClientList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;

/**
 * Description of the Class
 *
 * @author srini
 * @version $Id$
 * @created December 21, 2005
 */
public class AdminClientManager extends CFSModule {

  /**
   * Action that prepares a list of sync clients
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandShowClients(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    //Configure the pagedList
    PagedListInfo listInfo = getPagedListInfo(context, "SyncListInfo");
    listInfo.setLink("AdminClientManager.do?command=ShowClients");
    try {
      db = getConnection(context);
      //get the http-xml clients 
      SyncClientList syncClientList = new SyncClientList();
      syncClientList.setPagedListInfo(listInfo);
      syncClientList.buildList(db);
      context.getRequest().setAttribute(
          "syncClientList", syncClientList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    // forward to JSP
    addModuleBean(context, "Configuration", "Configuration");
    return ("ShowClientsOK");
  }

  /**
   * Action that shows add sync client form.
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAddClientForm(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    SyncClient syncClient = null;
    try {
      db = getConnection(context);
      User user = this.getUser(context, this.getUserId(context));
      syncClient = (SyncClient) context.getFormBean();
      if (syncClient == null) {
        syncClient = new SyncClient();
      }
      syncClient.setEnteredBy(user.getId());
      syncClient.setEnabled(true);
      context.getRequest().setAttribute("syncClient", syncClient);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    // forward to JSP
    addModuleBean(context, "Configuration", "Configuration");
    return ("AddClientFormOK");
  }


  /**
   * Action that adds/updates sync client.
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAddClient(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    SyncClient syncClient = null;
    boolean isValid = false;
    int resultCount = -1;
    try {
      db = getConnection(context);
      User user = this.getUser(context, this.getUserId(context));
      syncClient = (SyncClient) context.getFormBean();
      isValid = this.validateObject(context, db, syncClient);
      if (isValid) {
        syncClient.setModifiedBy(user.getId());
        if (syncClient.getId() == -1) {
          boolean success = syncClient.insert(db);
          resultCount = success ? 1 : 0;
        } else {
          resultCount = syncClient.update(db);
        }
      } else {
        context.getRequest().setAttribute("syncClient", syncClient);
        if (syncClient.getId() == -1) {
          return ("AddClientFormOK");
        } else {
          return ("ModifyClientFormOK");
        }
      }

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    // forward to JSP
    addModuleBean(context, "Configuration", "Configuration");
    return ("UpdateClientOK");
  }


  /**
   * Action that deletes sync client.
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDeleteClient(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    SyncClient syncClient = null;
    try {
      db = getConnection(context);
      String clientId = context.getRequest().getParameter("id");
      int id = -1;
      if (clientId != null) {
        id = Integer.parseInt(clientId);
        syncClient = new SyncClient(db, id);
        syncClient.delete(db);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    // forward to JSP
    addModuleBean(context, "Configuration", "Configuration");
    return ("DeleteClientOK");
  }

  /**
   * Action that enables sync client.
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */

  public String executeCommandEnableClient(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    SyncClient syncClient = null;
    try {
      db = getConnection(context);
      User user = this.getUser(context, this.getUserId(context));
      String clientId = context.getRequest().getParameter("id");
      int id = -1;
      if (clientId != null) {
        id = Integer.parseInt(clientId);
        syncClient = new SyncClient(db, id);
        syncClient.setEnabled(true);
        syncClient.setModifiedBy(user.getId());
        syncClient.update(db);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    // forward to JSP
    addModuleBean(context, "Configuration", "Configuration");
    return ("EnableClientOK");
  }

  /**
   * Action that disables sync client.
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */

  public String executeCommandDisableClient(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    SyncClient syncClient = null;
    try {
      db = getConnection(context);
      User user = this.getUser(context, this.getUserId(context));
      String clientId = context.getRequest().getParameter("id");
      int id = -1;
      if (clientId != null) {
        id = Integer.parseInt(clientId);
        syncClient = new SyncClient(db, id);
        syncClient.setModifiedBy(user.getId());
        syncClient.setEnabled(false);
        syncClient.update(db);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    // forward to JSP
    addModuleBean(context, "Configuration", "Configuration");
    return ("EnableClientOK");
  }


  /**
   * Action that shows edit sync client form.
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModifyClientForm(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    SyncClient syncClient = null;
    try {
      db = getConnection(context);
      User user = this.getUser(context, this.getUserId(context));
      String clientId = context.getRequest().getParameter("id");
      int id = -1;
      if (clientId != null) {
        id = Integer.parseInt(clientId);
        syncClient = new SyncClient(db, id);
      } else {
        syncClient = new SyncClient();
      }

      context.getRequest().setAttribute("syncClient", syncClient);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    // forward to JSP
    addModuleBean(context, "Configuration", "Configuration");
    return ("ModifyClientFormOK");
  }
}
