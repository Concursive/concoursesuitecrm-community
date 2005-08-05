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
package org.aspcfs.modules.communications.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.communications.base.Message;
import org.aspcfs.modules.communications.base.MessageList;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.util.HashMap;

/**
 * Actions for dealing with Messages in the Communications Module
 *
 * @author chris price
 * @version $Id: CampaignManager.java,v 1.1 2001/10/18 12:45:57 mrajkowski Exp
 *          $
 * @created October 18, 2001
 */
public final class CampaignManagerMessage extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandView(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-messages-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    PagedListInfo pagedListInfo = this.getPagedListInfo(
        context, "CampaignMessageListInfo");
    pagedListInfo.setLink("CampaignManagerMessage.do?command=View");
    Connection db = null;
    MessageList messageList = new MessageList();
    try {
      db = this.getConnection(context);
      messageList.setPagedListInfo(pagedListInfo);
      if ("all".equals(pagedListInfo.getListView())) {
        messageList.setAllMessages(
            true, this.getUserId(context), this.getUserRange(context));
      } else if ("hierarchy".equals(pagedListInfo.getListView())) {
        messageList.setControlledHierarchyOnly(
            true, this.getUserRange(context));
        messageList.setPersonalId(this.getUserId(context));
      } else if ("personal".equals(pagedListInfo.getListView())) {
        messageList.setOwner(this.getUserId(context));
        messageList.setRuleId(AccessType.PERSONAL);
        messageList.setPersonalId(MessageList.IGNORE_PERSONAL);
      } else {
        messageList.setOwner(this.getUserId(context));
        messageList.setPersonalId(MessageList.IGNORE_PERSONAL);
      }
      messageList.buildList(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    String submenu = context.getRequest().getParameter("submenu");
    if (submenu == null) {
      submenu = (String) context.getRequest().getAttribute("submenu");
    }
    if (submenu == null) {
      submenu = "ManageMessages";
    }
    context.getRequest().setAttribute("submenu", submenu);
    addModuleBean(context, submenu, "View Messages");
    if (errorMessage == null) {
      context.getRequest().setAttribute("MessageList", messageList);
      return ("ViewOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-messages-edit"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Messages", "Modify Message");
    Exception errorMessage = null;
    String messageId = context.getRequest().getParameter("id");
    Connection db = null;
    Message newMessage = null;
    try {
      db = this.getConnection(context);
      newMessage = new Message(db, messageId);
      if (!hasAuthority(db, context, newMessage)) {
        return ("PermissionError");
      }
      //get access types
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(
          db, AccessType.COMMUNICATION_MESSAGES);
      context.getRequest().setAttribute("AccessTypeList", accessTypeList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    String submenu = context.getRequest().getParameter("submenu");
    if (submenu == null) {
      submenu = (String) context.getRequest().getAttribute("submenu");
    }
    if (submenu == null) {
      submenu = "ManageMessages";
    }
    context.getRequest().setAttribute("submenu", submenu);
    addModuleBean(context, submenu, "Modify Message");
    if (errorMessage == null) {
      context.getRequest().setAttribute("Message", newMessage);
      return ("ModifyOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandUpdate(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-messages-edit"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "ManageMessages", "Message Details");
    Connection db = null;
    boolean isValid = false;
    int resultCount = 0;
    String id = (String) context.getRequest().getParameter("id");
    Message newMessage = (Message) context.getFormBean();
    try {
      db = this.getConnection(context);
      Message oldMessage = new Message(db, Integer.parseInt(id));
      if (!hasAuthority(db, context, oldMessage)) {
        return ("PermissionError");
      }
      newMessage.setModifiedBy(getUserId(context));
      isValid = this.validateObject(context, db, newMessage);
      if (isValid) {
        resultCount = newMessage.update(db);
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCount == 1) {
      context.getRequest().setAttribute("MessageDetails", newMessage);
      if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
          "return").equals("list")) {
        return (executeCommandView(context));
      } else {
        return ("UpdateOK");
      }
    } else {
      if (resultCount == -1 || !isValid) {
        context.getRequest().setAttribute("MessageDetails", newMessage);
        return ("ModifyOK");
      }
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-messages-view"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "ManageMessages", "View Message Details");
    String messageId = context.getRequest().getParameter("id");
    Connection db = null;
    Message newMessage = null;
    try {
      db = this.getConnection(context);
      newMessage = new Message(db, messageId);
      if (!hasAuthority(db, context, newMessage)) {
        return ("PermissionError");
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("MessageDetails", newMessage);
    return ("DetailsOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandAdd(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-messages-add"))) {
      return ("PermissionError");
    }

    String submenu = context.getRequest().getParameter("submenu");
    if (submenu == null) {
      submenu = (String) context.getRequest().getAttribute("submenu");
    }
    if (submenu == null) {
      submenu = "ManageMessages";
    }
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      //get access types
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(
          db, AccessType.COMMUNICATION_MESSAGES);
      context.getRequest().setAttribute("AccessTypeList", accessTypeList);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    context.getRequest().setAttribute("submenu", submenu);
    addModuleBean(context, submenu, "Add Message");

    if (errorMessage == null) {
      return getReturn(context, "Add");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandInsert(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-messages-add"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "BuildNew", "Add New Message");
    Connection db = null;
    boolean recordInserted = false;
    boolean isValid = false;
    Message newMessage = (Message) context.getFormBean();
    try {
      newMessage.setEnteredBy(getUserId(context));
      newMessage.setModifiedBy(getUserId(context));
      db = this.getConnection(context);
      isValid = this.validateObject(context, db, newMessage);
      if (isValid) {
        recordInserted = newMessage.insert(db);
      }
      if (recordInserted) {
        newMessage = new Message(db, newMessage.getId());
        context.getRequest().setAttribute("Message", newMessage);
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordInserted) {
      return ("InsertOK");
    }
    return (executeCommandAdd(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-messages-delete"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    boolean recordDeleted = false;
    Message thisMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisMessage = new Message(db, context.getRequest().getParameter("id"));
      if (!hasAuthority(db, context, thisMessage)) {
        return ("PermissionError");
      }
      recordDeleted = thisMessage.delete(db);
      if (!recordDeleted) {
        String inactiveCounter = "" + thisMessage.getInactiveCount();
        String label = "";
        HashMap map = new HashMap();
        map.put("${message.inactiveCount}", inactiveCounter);
        map.put(
            "${message.campaign}", (thisMessage.getInactiveCount() == 1 ? "campaign is" : "campaigns are"));
        map.put(
            "${message.use}", (thisMessage.getInactiveCount() == 1 ? "uses" : "use"));
        Template template = new Template(
            systemStatus.getLabel(
                "object.validation.actionError.canNotDeleteMessage"));
        template.setParseElements(map);
        thisMessage.getErrors().put("actionError", template.getParsedText());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute(
            "refreshUrl", "CampaignManagerMessage.do?command=View");
        return ("DeleteOK");
      } else {
        processErrors(context, thisMessage.getErrors());
        return (executeCommandView(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandPreviewMessage(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-messages-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "ManageCampaigns", "Build New Campaign");
    Connection db = null;
    String messageId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      Message thisMessage = new Message(db, messageId);
      context.getRequest().setAttribute("Message", thisMessage);
    } catch (Exception e) {
    } finally {
      this.freeConnection(context, db);
    }
    return "PreviewOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandClone(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-messages-add"))) {
      return ("PermissionError");
    }

    addModuleBean(context, "View Messages", "Clone Message");
    Exception errorMessage = null;

    String passedId = context.getRequest().getParameter("id");

    Connection db = null;
    Message newMessage = null;
    try {
      db = this.getConnection(context);
      newMessage = new Message(db, passedId);

      //get access types
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(
          db, AccessType.COMMUNICATION_MESSAGES);
      context.getRequest().setAttribute("AccessTypeList", accessTypeList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    String submenu = context.getRequest().getParameter("submenu");
    if (submenu == null) {
      submenu = (String) context.getRequest().getAttribute("submenu");
    }
    if (submenu == null) {
      submenu = "ManageMessages";
    }
    context.getRequest().setAttribute("submenu", submenu);
    addModuleBean(context, submenu, "Modify Message");

    if (errorMessage == null) {
      context.getRequest().setAttribute("Message", newMessage);
      return ("AddOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    Message thisMessage = null;

    HtmlDialog htmlDialog = new HtmlDialog();
    String id = null;

    if (!(hasPermission(context, "campaign-campaigns-messages-delete"))) {
      return ("PermissionError");
    }

    if (context.getRequest().getParameter("id") != null) {
      id = context.getRequest().getParameter("id");
    }

    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      thisMessage = new Message(db, id);
      if (!hasAuthority(db, context, thisMessage)) {
        return ("PermissionError");
      }
      DependencyList dependencies = thisMessage.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));

      if (dependencies.size() == 0) {
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setDeleteUrl(
            "javascript:window.location.href='CampaignManagerMessage.do?command=Delete&id=" + id + "'");
      } else {
        htmlDialog.setHeader(
            systemStatus.getLabel("confirmdelete.messageCampaignHeader"));
        htmlDialog.addButton(
            systemStatus.getLabel("button.ok"), "javascript:parent.window.close()");
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getSession().setAttribute("Dialog", htmlDialog);
      return ("ConfirmDeleteOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
}

