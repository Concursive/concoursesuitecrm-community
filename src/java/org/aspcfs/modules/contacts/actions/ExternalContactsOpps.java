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
package org.aspcfs.modules.contacts.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.pipeline.base.OpportunityComponent;
import org.aspcfs.modules.pipeline.base.OpportunityComponentList;
import org.aspcfs.modules.pipeline.base.OpportunityHeader;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.beans.OpportunityBean;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;

import java.sql.Connection;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    October 15, 2001
 *@version    $Id: ExternalContactsOpps.java,v 1.3 2002/02/05 19:44:43 chris Exp
 *      $
 */
public final class ExternalContactsOpps extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandViewOpps(ActionContext context) {

    Exception errorMessage = null;
    String contactId = context.getRequest().getParameter("contactId");

    addModuleBean(context, "External Contacts", "Opportunities");

    PagedListInfo oppPagedListInfo = this.getPagedListInfo(context, "ExternalOppsPagedListInfo");
    oppPagedListInfo.setLink("ExternalContactsOpps.do?command=ViewOpps&contactId=" + contactId + RequestUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId"));

    Connection db = null;
    OpportunityHeaderList oppList = new OpportunityHeaderList();
    Contact thisContact = null;
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);

      if (!hasPermission(context, "contacts-external_contacts-opportunities-view") || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-opportunities-view")))) {
        return ("PermissionError");
      }

      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      oppList.setPagedListInfo(oppPagedListInfo);
      oppList.setBuildTotalValues(true);
      oppList.setContactId(contactId);
      if ("all".equals(oppPagedListInfo.getListView())) {
        oppList.setOwnerIdRange(this.getUserRange(context));
        oppList.setQueryOpenOnly(true);
      } else if ("closed".equals(oppPagedListInfo.getListView())) {
        oppList.setOwnerIdRange(this.getUserRange(context));
        oppList.setQueryClosedOnly(true);
      } else {
        oppList.setOwner(this.getUserId(context));
        oppList.setQueryOpenOnly(true);
      }
      oppList.buildList(db);
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("opportunityHeaderList", oppList);
      boolean popup = "true".equals(context.getRequest().getParameter("popup"));
      if (popup) {
        return "ListOppsPopupOK";
      }
      return ("ListOppsOK");
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
  public String executeCommandPrepare(ActionContext context) {
    Exception errorMessage = null;
    String contactId = context.getRequest().getParameter("contactId");
    Contact thisContact = null;
    Connection db = null;
    String id = context.getRequest().getParameter("headerId");
    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
    User thisRec = thisUser.getUserRecord();
    UserList shortChildList = thisRec.getShortChildList();
    UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
    userList.setMyId(getUserId(context));
    userList.setMyValue(thisUser.getContact().getNameLastFirst());
    userList.setIncludeMe(true);
    userList.setExcludeDisabledIfUnselected(true);
    userList.setExcludeExpiredIfUnselected(true);
    context.getRequest().setAttribute("UserList", userList);
    try {
      db = this.getConnection(context);
      if (context.getRequest().getAttribute("ContactDetails") == null) {
        thisContact = new Contact(db, contactId);
        if (!hasAuthority(db, context, thisContact)) {
          return ("PermissionError");
        }
        context.getRequest().setAttribute("ContactDetails", thisContact);
      } else {
        thisContact = (Contact) context.getRequest().getAttribute("ContactDetails");
      }

      if (id != null && !"-1".equals(id)) {
        if (!hasPermission(context, "contacts-external_contacts-opportunities-add") || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-opportunities-add")))) {
          return ("PermissionError");
        }
      }

      //check if a header needs to be built.
      if (id != null && !"-1".equals(id)) {
        //Build the container item
        OpportunityHeader oppHeader = new OpportunityHeader(db, Integer.parseInt(id));
        context.getRequest().setAttribute("opportunityHeader", oppHeader);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      addModuleBean(context, "External Contacts", "Opportunities");
      return "PrepareOppOK";
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
  public String executeCommandSave(ActionContext context) {
    boolean recordInserted = false;
    boolean isValid = false;
    OpportunityBean newOpp = (OpportunityBean) context.getRequest().getAttribute("OppDetails");
    String contactId = context.getRequest().getParameter("contactId");
    Contact thisContact = null;

    //set types
    newOpp.getComponent().setTypeList(context.getRequest().getParameterValues("selectedList"));
    newOpp.getComponent().setEnteredBy(getUserId(context));
    newOpp.getComponent().setModifiedBy(getUserId(context));
    newOpp.getHeader().setEnteredBy(getUserId(context));
    newOpp.getHeader().setModifiedBy(getUserId(context));

    Connection db = null;
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);

      if (!hasPermission(context, "contacts-external_contacts-opportunities-add") || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-opportunities-add")))) {
        return ("PermissionError");
      }

      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      isValid = this.validateObject(context, db, newOpp.getHeader());
      isValid = this.validateObject(context, db, newOpp.getComponent()) && isValid;
      if (isValid) {
        recordInserted = newOpp.insert(db, context);
      }
      if (!recordInserted) {
        LookupList typeSelect = new LookupList(db, "lookup_opportunity_types");
        context.getRequest().setAttribute("TypeSelect", typeSelect);
        context.getRequest().setAttribute("TypeList", newOpp.getComponent().getTypeList());
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordInserted) {
      addRecentItem(context, newOpp.getHeader());
      context.getRequest().setAttribute("headerId", String.valueOf(newOpp.getHeader().getId()));
      return (executeCommandDetailsOpp(context));
    }
    return (executeCommandPrepare(context));
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandUpdateOpp(ActionContext context) {
    Connection db = null;
    int resultCount = 0;
    boolean isValid = false;
    String headerId = context.getRequest().getParameter("headerId");
    String contactId = context.getRequest().getParameter("contactId");

    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact(db, contactId);
      if (!hasPermission(context, "contacts-external_contacts-opportunities-edit") || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-opportunities-edit")))) {
        return ("PermissionError");
      }

      OpportunityHeader oppHeader = new OpportunityHeader(db, Integer.parseInt(headerId));
      if (!hasAuthority(context, oppHeader.getEnteredBy())) {
        return "PermissionError";
      }
      oppHeader.setModifiedBy(getUserId(context));
      oppHeader.setDescription(context.getRequest().getParameter("description"));
      isValid = this.validateObject(context, db, oppHeader);
      if (isValid) {
        resultCount = oppHeader.update(db);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    if (resultCount == 1) {
      if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter("return").equals("list")) {
        return (executeCommandViewOpps(context));
      } else {
        return (executeCommandDetailsOpp(context));
      }
    } else if (resultCount == -1 || !isValid) {
      return executeCommandModifyOpp(context);
    } else {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSaveComponent(ActionContext context) {
    boolean recordInserted = false;
    boolean isValid = false;
    int resultCount = 0;
    Contact thisContact = null;
    String permission = "contacts-external_contacts-opportunities-add";

    OpportunityComponent newComponent = (OpportunityComponent) context.getFormBean();
    newComponent.setTypeList(context.getRequest().getParameterValues("selectedList"));
    newComponent.setEnteredBy(getUserId(context));
    newComponent.setModifiedBy(getUserId(context));

    String action = (newComponent.getId() > 0 ? "modify" : "insert");
    String contactId = context.getRequest().getParameter("contactId");

    if ("modify".equals(action)) {
      permission = "contacts-external_contacts-opportunities-edit";
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);

      //check permissions
      if (!hasPermission(context, permission) || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-opportunities-edit")))) {
        return ("PermissionError");
      }

      if (newComponent.getId() > 0) {
        newComponent.setModifiedBy(getUserId(context));
        if (!(hasAuthority(db, context, thisContact) || hasAuthority(context, newComponent.getOwner()))) {
          return "PermissionError";
        }
        isValid = this.validateObject(context, db, newComponent);
        if (isValid) {
          resultCount = newComponent.update(db, context);
        }
      } else {
        if (!hasAuthority(db, context, thisContact)) {
          return ("PermissionError");
        }
        isValid = this.validateObject(context, db, newComponent);
        if (isValid) {
          recordInserted = newComponent.insert(db, context);
        }
      }
      if (recordInserted) {
        addRecentItem(context, newComponent);
      } else if (resultCount == 1) {
        newComponent.queryRecord(db, newComponent.getId());
      } else {
        //rebuild the form
        LookupList typeSelect = new LookupList(db, "lookup_opportunity_types");
        context.getRequest().setAttribute("TypeSelect", typeSelect);
        context.getRequest().setAttribute("TypeList", newComponent.getTypeList());
        if ("modify".equals(action) && resultCount == -1) {
          UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
          User thisRec = thisUser.getUserRecord();
          UserList shortChildList = thisRec.getShortChildList();
          UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
          userList.setMyId(getUserId(context));
          userList.setMyValue(thisUser.getContact().getNameLastFirst());
          userList.setIncludeMe(true);
          userList.setExcludeDisabledIfUnselected(true);
          userList.setExcludeExpiredIfUnselected(true);
          context.getRequest().setAttribute("UserList", userList);
        }
      }
      context.getRequest().setAttribute("ComponentDetails", newComponent);
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if ("insert".equals(action)) {
      if (recordInserted) {
        return (executeCommandDetailsOpp(context));
      } else {
        return executeCommandPrepare(context);
      }
    } else {
      if (resultCount == 1) {
        if ("list".equals(context.getRequest().getParameter("return"))) {
          return (executeCommandDetailsOpp(context));
        } else {
          return executeCommandDetailsComponent(context);
        }
      } else {
        if (resultCount == -1 || !isValid) {
          return executeCommandPrepare(context);
        }
        context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
        return ("UserError");
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDetailsOpp(ActionContext context) {
    Exception errorMessage = null;
    int headerId = -1;
    addModuleBean(context, "External Contacts", "Opportunities");
    if (context.getRequest().getParameter("headerId") != null) {
      headerId = Integer.parseInt(context.getRequest().getParameter("headerId"));
    } else {
      headerId = Integer.parseInt((String) context.getRequest().getAttribute("headerId"));
    }
    String contactId = context.getRequest().getParameter("contactId");
    Connection db = null;
    Contact thisContact = null;
    OpportunityHeader thisHeader = null;
    OpportunityComponentList componentList = null;

    PagedListInfo componentListInfo = this.getPagedListInfo(context, "ComponentListInfo");
    componentListInfo.setLink("ExternalContactsOpps.do?command=DetailsOpp&headerId=" + headerId + "&contactId=" + contactId + RequestUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId"));
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);

      if (!hasPermission(context, "contacts-external_contacts-opportunities-view") || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-opportunities-view")))) {
        return ("PermissionError");
      }

      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);

      thisHeader = new OpportunityHeader();
      thisHeader.setBuildComponentCount(true);
      thisHeader.queryRecord(db, headerId);
      context.getRequest().setAttribute("opportunityHeader", thisHeader);

      componentList = new OpportunityComponentList();
      componentList.setPagedListInfo(componentListInfo);
      componentList.setOwnerIdRange(this.getUserRange(context));
      componentList.setHeaderId(thisHeader.getId());
      componentList.buildList(db);
      context.getRequest().setAttribute("componentList", componentList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      boolean popup = "true".equals(context.getRequest().getParameter("popup"));
      addRecentItem(context, thisHeader);
      if (popup) {
        return ("DetailsOppPopupOK");
      }
      return ("DetailsOppOK");
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
  public String executeCommandModifyOpp(ActionContext context) {
    Exception errorMessage = null;
    addModuleBean(context, "External Contacts", "Opportunities");
    int headerId = Integer.parseInt(context.getRequest().getParameter("headerId"));
    String contactId = context.getRequest().getParameter("contactId");
    Connection db = null;
    OpportunityHeader thisHeader = null;
    Contact thisContact = null;
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);

      if (!hasPermission(context, "contacts-external_contacts-opportunities-edit") || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-opportunities-edit")))) {
        return ("PermissionError");
      }

      if (!hasAuthority(db, context, thisContact)) {
        return "PermissionError";
      }
      thisHeader = new OpportunityHeader();
      thisHeader.setBuildComponentCount(true);
      thisHeader.queryRecord(db, headerId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      boolean popup = "true".equals(context.getRequest().getParameter("popup"));
      context.getRequest().setAttribute("opportunityHeader", thisHeader);
      addRecentItem(context, thisHeader);
      if (popup) {
        return ("ModifyOppPopupOK");
      }
      return ("ModifyOppOK");
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
  public String executeCommandDetailsComponent(ActionContext context) {
    OpportunityComponent thisComponent = null;
    Contact thisContact = null;
    Connection db = null;
    addModuleBean(context, "External Contacts", "Opportunities");
    //Parameters
    String componentId = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");
    try {
      db = this.getConnection(context);
      //Load the contact
      thisContact = new Contact(db, contactId);

      if (!hasPermission(context, "contacts-external_contacts-opportunities-view") || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-opportunities-view")))) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);
      //Load the component
      thisComponent = new OpportunityComponent(db, Integer.parseInt(componentId));
      if (!(hasAuthority(db, context, thisContact) || hasAuthority(context, thisComponent.getOwner()))) {
        return "PermissionError";
      }
      thisComponent.checkEnabledOwnerAccount(db);
      context.getRequest().setAttribute("oppComponentDetails", thisComponent);
      //Load the opportunity header for display
      OpportunityHeader oppHeader = new OpportunityHeader(db, thisComponent.getHeaderId());
      context.getRequest().setAttribute("opportunityHeader", oppHeader);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (!(hasAuthority(context, thisContact.getOwner()) || hasAuthority(context, thisComponent.getOwner()))) {
      return "PermissionError";
    }
    boolean popup = "true".equals(context.getRequest().getParameter("popup"));
    addRecentItem(context, thisComponent);
    if (popup) {
      return ("DetailsComponentPopupOK");
    }
    return ("DetailsComponentOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    HtmlDialog htmlDialog = new HtmlDialog();
    String headerId = context.getRequest().getParameter("headerId");
    String contactId = context.getRequest().getParameter("contactId");
    Connection db = null;
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      Contact thisContact = new Contact(db, contactId);
      if (!hasPermission(context, "contacts-external_contacts-opportunities-delete") || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-opportunities-delete")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      OpportunityHeader thisOpp = new OpportunityHeader(db, headerId);
      DependencyList dependencies = thisOpp.processDependencies(db);
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(systemStatus.getLabel("confirmdelete.caution")+"\n"+dependencies.getHtmlString());
      htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));
      htmlDialog.addButton(systemStatus.getLabel("button.deleteAll"), "javascript:window.location.href='ExternalContactsOpps.do?command=DeleteOpp&contactId=" + contactId + "&id=" + headerId + RequestUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId|sourcePopup") + "'");
      htmlDialog.addButton(systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
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
  public String executeCommandConfirmComponentDelete(ActionContext context) {
    Exception errorMessage = null;
    OpportunityComponent thisComponent = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    String id = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");

    Connection db = null;
    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact(db, contactId);

      if (!hasPermission(context, "contacts-external_contacts-opportunities-delete") || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-opportunities-edit")))) {
        return ("PermissionError");
      }

      thisComponent = new OpportunityComponent(db, id);
      if (!(hasAuthority(db, context, thisContact) || hasAuthority(context, thisComponent.getOwner()))) {
        return "PermissionError";
      }
      SystemStatus systemStatus = this.getSystemStatus(context);
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.contact.opps.delete"));
      htmlDialog.setShowAndConfirm(false);
      htmlDialog.setDeleteUrl("javascript:window.location.href='ExternalContactsOppComponents.do?command=DeleteComponent&contactId=" + contactId + "&id=" + id + RequestUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId|sourcePopup") + "'");
      htmlDialog.addButton(systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
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


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDeleteOpp(ActionContext context) {
    if (!hasPermission(context, "contacts-external_contacts-opportunities-delete")) {
      return ("PermissionError");
    }
    String popup = (String) context.getRequest().getParameter("sourcePopup");
    Exception errorMessage = null;
    boolean recordDeleted = false;
    String contactId = context.getRequest().getParameter("contactId");
    OpportunityHeader newOpp = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact(db, contactId);

      if (!hasPermission(context, "contacts-external_contacts-opportunities-delete") || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-opportunities-delete")))) {
        return ("PermissionError");
      }

      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      newOpp = new OpportunityHeader(db, context.getRequest().getParameter("id"));
      recordDeleted = newOpp.delete(db, context, this.getPath(context, "opportunities"));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute("contactId", contactId);
        deleteRecentItem(context, newOpp);
        if (popup!= null && "true".equals(popup)) {
          context.getRequest().setAttribute("id", ""+newOpp.getId());
          return "OppDeletePopupOK";
        }
        context.getRequest().setAttribute("refreshUrl", "ExternalContactsOpps.do?command=ViewOpps&contactId=" + contactId + RequestUtils.addLinkParams(context.getRequest(), "popupType|actionId|popup"));
        return "OppDeleteOK";
      } else {
        processErrors(context, newOpp.getErrors());
        return (executeCommandViewOpps(context));
      }
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
  public String executeCommandDeleteComponent(ActionContext context) {
    Exception errorMessage = null;
    String popup = (String) context.getRequest().getParameter("sourcePopup");
    boolean recordDeleted = false;
    OpportunityComponent component = null;
    String contactId = context.getRequest().getParameter("contactId");
    Connection db = null;
    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact(db, contactId);

      if (!hasPermission(context, "contacts-external_contacts-opportunities-delete") || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-opportunities-delete")))) {
        return ("PermissionError");
      }

      component = new OpportunityComponent(db, context.getRequest().getParameter("id"));
      if (!(hasAuthority(db, context, thisContact) || hasAuthority(context, component.getOwner()))) {
        return "PermissionError";
      }
      recordDeleted = component.delete(db, context, this.getPath(context, "opportunities"));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordDeleted) {
        deleteRecentItem(context, component);
        if (popup!= null && "true".equals(popup)) {
          context.getRequest().setAttribute("id", ""+component.getHeaderId());
          return "OppDeletePopupOK";
        }
        context.getRequest().setAttribute("refreshUrl", "ExternalContactsOpps.do?command=DetailsOpp&headerId=" + component.getHeaderId() + "&contactId=" + contactId + RequestUtils.addLinkParams(context.getRequest(), "popupType|actionId|popup"));
        return ("ComponentDeleteOK");
      } else {
        processErrors(context, component.getErrors());
        return (executeCommandViewOpps(context));
      }
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
  public String executeCommandModifyComponent(ActionContext context) {
    Exception errorMessage = null;
    Contact thisContact = null;
    Connection db = null;
    OpportunityComponent component = null;
    addModuleBean(context, "External Contacts", "Opportunities");

    String contactId = context.getRequest().getParameter("contactId");
    String componentId = context.getRequest().getParameter("id");

    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
    User thisRec = thisUser.getUserRecord();
    UserList shortChildList = thisRec.getShortChildList();
    UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
    userList.setMyId(getUserId(context));
    userList.setMyValue(thisUser.getContact().getNameLastFirst());
    userList.setIncludeMe(true);
    userList.setExcludeDisabledIfUnselected(true);
    userList.setExcludeExpiredIfUnselected(true);
    context.getRequest().setAttribute("UserList", userList);

    try {
      db = this.getConnection(context);
      component = new OpportunityComponent(db, componentId);
      thisContact = new Contact(db, contactId);

      if (!hasPermission(context, "contacts-external_contacts-opportunities-edit") || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-opportunities-edit")))) {
        return ("PermissionError");
      }

      if (!(hasAuthority(db, context, thisContact) || !hasAuthority(context, component.getOwner()))) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getRequest().setAttribute("ComponentDetails", component);
      addRecentItem(context, component);
      return executeCommandPrepare(context);
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
}

