package org.aspcfs.modules.actionlist.actions;

import java.util.HashMap;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.actionlist.base.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.modules.base.Constants;
import java.sql.*;

/**
 *  Description of the Class
 *
 *@author     Mathur
 *@created    April 18, 2003
 *@version    $id:exp$
 */
public final class MyActionLists extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-view"))) {
      return ("PermissionError");
    }
    return executeCommandList(context);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    addModuleBean(context, "My Action Lists", "Action Lists");
    String linkModuleId = context.getRequest().getParameter("linkModuleId");
    PagedListInfo actionListInfo = this.getPagedListInfo(context, "ActionListInfo");
    actionListInfo.setLink("MyActionLists.do?command=List&linkModuleId=" + linkModuleId);
    Connection db = null;
    try {
      db = getConnection(context);
      ActionLists thisList = new ActionLists();
      thisList.setPagedListInfo(actionListInfo);
      thisList.setLinkModuleId(Integer.parseInt(linkModuleId));
      thisList.setBuildDetails(true);
      thisList.setOwner(this.getUserId(context));
      if (!"all".equals(actionListInfo.getListView())) {
        if ("complete".equals(actionListInfo.getListView())) {
          thisList.setCompleteOnly(true);
        } else {
          thisList.setInProgressOnly(true);
        }
      }
      thisList.buildList(db);
      context.getRequest().setAttribute("ActionLists", thisList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "ListOK";
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
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    addModuleBean(context, "My Action Lists", "");
    String actionId = context.getRequest().getParameter("id");
    Connection db = null;

    ActionList thisList = (ActionList) context.getFormBean();
    try {
      db = getConnection(context);
      thisList.queryRecord(db, Integer.parseInt(actionId));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "DetailsOK";
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
  public String executeCommandAdd(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-add"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "My Action Lists", "Add an Action List");
    return "PrepareAddOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-edit"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    addModuleBean(context, "My Action Lists", "");
    String actionId = context.getRequest().getParameter("id");
    Connection db = null;

    ActionList thisList = (ActionList) context.getFormBean();
    try {
      db = getConnection(context);
      thisList.queryRecord(db, Integer.parseInt(actionId));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "PrepareModifyOK";
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
  public String executeCommandSave(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-add"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    boolean recordInserted = false;
    int resultCount = 0;
    HashMap errors = new HashMap();
    addModuleBean(context, "My Action Lists", "");

    ActionList thisList = (ActionList) context.getFormBean();
    thisList.setModifiedBy(this.getUserId(context));
    String action = (thisList.getId() > 0 ? "modify" : "insert");
    String criteria = context.getRequest().getParameter("searchCriteriaText");

    Connection db = null;
    try {
      db = getConnection(context);
      if ("insert".equals(action)) {
        if (criteria != null && !"".equals(criteria)) {
          thisList.setEnteredBy(this.getUserId(context));
          recordInserted = thisList.insert(db);

          //if list was new and saved correctly insert contacts (TODO: after implementing commit across requests //MyActionContacts can be used for this
          if (recordInserted) {
            SearchCriteriaList thisSCL = null;
            //The criteria that makes up the contact list query
            thisSCL = new SearchCriteriaList(criteria);
            thisSCL.setGroupName("Action List");
            thisSCL.setEnteredBy(getUserId(context));
            thisSCL.setModifiedBy(getUserId(context));
            thisSCL.setOwner(getUserId(context));
            thisSCL.buildRelatedResources(db);

            //Build the contactList
            ContactList contacts = new ContactList();
            contacts.setScl(thisSCL, this.getUserId(context), this.getUserRange(context));
            contacts.setBuildDetails(true);
            contacts.setBuildTypes(false);
            contacts.buildList(db);

            //save action contacts
            ActionContactsList contactsList = new ActionContactsList();
            contactsList.setActionId(thisList.getId());
            contactsList.setEnteredBy(this.getUserId(context));
            contactsList.insert(db, contacts);
          }
        } else {
          errors.put("criteriaError", "Could not save the form as criteria was not defined");
          processErrors(context, errors);
        }
      } else {
        resultCount = thisList.update(db);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordInserted) {
        return "InsertOK";
      } else if (resultCount == 1) {
        return (executeCommandList(context));
      }
      return (executeCommandAdd(context));
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
    if (!(hasPermission(context, "myhomepage-action-lists-delete"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    addModuleBean(context, "My Action Lists", "");

    String actionId = context.getRequest().getParameter("id");
    String linkModuleId = context.getRequest().getParameter("linkModuleId");
    HtmlDialog htmlDialog = new HtmlDialog();
    ActionList thisList = (ActionList) context.getFormBean();

    try {
      db = getConnection(context);
      thisList.queryRecord(db, Integer.parseInt(actionId));
      DependencyList dependencies = thisList.processDependencies(db);
      htmlDialog.addMessage(dependencies.getHtmlString());

      if (dependencies.size() == 0) {
        htmlDialog.setTitle("Confirm");
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setDeleteUrl("javascript:window.location.href='MyActionLists.do?command=Delete&id=" + actionId + "&linkModuleId=" + linkModuleId + "'");
      } else {
        htmlDialog.setTitle("Confirm");
        htmlDialog.setHeader("This list has the following dependencies within Dark Horse CRM:");
        htmlDialog.addButton("Delete All", "javascript:window.location.href='MyActionLists.do?command=Delete&id=" + actionId + "&linkModuleId=" + linkModuleId + "'");
        htmlDialog.addButton("No", "javascript:parent.window.close()");
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getSession().setAttribute("Dialog", htmlDialog);
      return "ConfirmDeleteOK";
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
  public String executeCommandDelete(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-delete"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    addModuleBean(context, "My Action Lists", "");
    String actionId = context.getRequest().getParameter("id");
    String linkModuleId = context.getRequest().getParameter("linkModuleId");
    Connection db = null;

    ActionList thisList = (ActionList) context.getFormBean();
    try {
      db = getConnection(context);
      thisList.queryRecord(db, Integer.parseInt(actionId));
      thisList.delete(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getRequest().setAttribute("refreshUrl", "MyActionLists.do?command=List&linkModuleId=" + linkModuleId);
      return "DeleteOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
}

