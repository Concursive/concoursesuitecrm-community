package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;
import com.darkhorseventures.controller.CustomForm;

/**
 *  Actions for dealing with Messages in the Communications Module
 *
 *@author     chris price
 *@created    October 18, 2001
 *@version    $Id: CampaignManager.java,v 1.1 2001/10/18 12:45:57 mrajkowski Exp
 *      $
 */
public final class CampaignManagerMessage extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandView(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-messages-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "CampaignMessageListInfo");
    pagedListInfo.setLink("/CampaignManagerMessage.do?command=View");

    Connection db = null;
    MessageList messageList = new MessageList();

    try {
      db = this.getConnection(context);
      messageList.setPagedListInfo(pagedListInfo);
      if ("all".equals(pagedListInfo.getListView())) {
        messageList.setOwnerIdRange(this.getUserRange(context));
      } else {
        messageList.setOwner(this.getUserId(context));
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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandModify(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-messages-edit"))) {
      return ("PermissionError");
    }

    addModuleBean(context, "View Messages", "Modify Message");
    Exception errorMessage = null;

    String passedId = context.getRequest().getParameter("id");

    Connection db = null;
    Message newMessage = null;
    try {
      db = this.getConnection(context);
      newMessage = new Message(db, passedId);
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
      
      if (!hasAuthority(context, newMessage.getEnteredBy())) {
        return ("PermissionError");
      }       
      
      context.getRequest().setAttribute("Message", newMessage);
      return ("ModifyOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandUpdate(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-messages-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    addModuleBean(context, "ManageMessages", "Message Details");

    MessageList messageList = null;
    Message newMessage = (Message) context.getFormBean();

    Connection db = null;
    int resultCount = 0;

    try {
      newMessage.setModifiedBy(getUserId(context));
      //IE5.0+ uses an HTML Editor, all others use a Text Editor
      UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
      if (!("ie".equals(thisUser.getBrowserId()) && thisUser.getBrowserVersion() >= 5.0)) {
        newMessage.setMessageText(StringUtils.toHtmlText(newMessage.getMessageText()));
      }
      db = this.getConnection(context);
      resultCount = newMessage.update(db);
      if (resultCount == -1) {
        processErrors(context, newMessage.getErrors());
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        context.getRequest().setAttribute("MessageDetails", newMessage);
        return ("ModifyOK");
      } else if (resultCount == 1) {
        context.getRequest().setAttribute("MessageDetails", newMessage);
        if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter("return").equals("list")) {
          return (executeCommandView(context));
        } else {
          return ("UpdateOK");
        }
      } else {
        context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
        return ("UserError");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-messages-view"))) {
      return ("PermissionError");
    }

    addModuleBean(context, "ManageMessages", "View Message Details");
    Exception errorMessage = null;

    String messageId = context.getRequest().getParameter("id");

    Connection db = null;
    Message newMessage = null;

    try {
      db = this.getConnection(context);
      newMessage = new Message(db, messageId);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      
      if (!hasAuthority(context, newMessage.getEnteredBy())) {
        return ("PermissionError");
      }       
      
      context.getRequest().setAttribute("MessageDetails", newMessage);
      return ("DetailsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
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
    context.getRequest().setAttribute("submenu", submenu);
    addModuleBean(context, submenu, "Add Message");
    return ("AddOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandInsert(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-messages-add"))) {
      return ("PermissionError");
    }

    addModuleBean(context, "BuildNew", "Add New Message");
    Exception errorMessage = null;
    Connection db = null;
    boolean recordInserted = false;

    Message newMessage = (Message) context.getFormBean();

    try {
      newMessage.setEnteredBy(getUserId(context));
      newMessage.setModifiedBy(getUserId(context));
      //IE5.0+ uses an HTML Editor, all others use a Text Editor
      UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
      if (!("ie".equals(thisUser.getBrowserId()) && thisUser.getBrowserVersion() >= 5.0)) {
        newMessage.setMessageText(StringUtils.toHtmlText(newMessage.getMessageText()));
      }
      db = this.getConnection(context);
      recordInserted = newMessage.insert(db);
      if (recordInserted) {
        newMessage = new Message(db, newMessage.getId());
        context.getRequest().setAttribute("Message", newMessage);
      } else {
        processErrors(context, newMessage.getErrors());
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted) {
        return ("InsertOK");
      } else {
        return (executeCommandAdd(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDelete(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-messages-delete"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordDeleted = false;
    Message thisMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisMessage = new Message(db, context.getRequest().getParameter("id"));
      recordDeleted = thisMessage.delete(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute("refreshUrl", "CampaignManagerMessage.do?command=View");
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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandPreviewMessage(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-messages-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    addModuleBean(context, "ManageCampaigns", "Build New Campaign");
    Connection db = null;

    String messageId = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      Message thisMessage = new Message(db, messageId);
      context.getRequest().setAttribute("Message", thisMessage);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return "PreviewOK";
    } else {
      return "PreviewOK";
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
      thisMessage = new Message(db, id);
      htmlDialog.setTitle("CFS: Campaign Manager");

      htmlDialog.setRelationships(thisMessage.processDependencies(db));

      if (htmlDialog.getRelationships().size() == 0) {
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setDeleteUrl("javascript:window.location.href='CampaignManagerMessage.do?command=Delete&id=" + id + "'");
      } else {
        htmlDialog.setHeader("This message cannot be deleted because at least one campaign is using it.");
        htmlDialog.addButton("OK", "javascript:parent.window.close()");
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

