package org.aspcfs.modules.contacts.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.ActionContext;
import java.sql.*;
import java.io.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.pipeline.base.*;
import org.aspcfs.modules.pipeline.beans.OpportunityBean;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.DependencyList;

/**
 *  Description of the Class
 *
 * @author     chris
 * @created    October 15, 2001
 * @version    $Id: ExternalContactsOpps.java,v 1.3 2002/02/05 19:44:43 chris Exp
 *      $
 */
public final class ExternalContactsOpps extends CFSModule {

  private final static int EMPLOYEE_TYPE = 0;


  /**
   *  Description of the Method
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since
   */
  public String executeCommandViewOpps(ActionContext context) {
    if (!hasPermission(context, "contacts-external_contacts-opportunities-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    String contactId = context.getRequest().getParameter("contactId");

    addModuleBean(context, "External Contacts", "Opportunities");

    PagedListInfo oppPagedListInfo = this.getPagedListInfo(context, "ExternalOppsPagedListInfo");
    oppPagedListInfo.setLink("ExternalContactsOpps.do?command=ViewOpps&contactId=" + contactId);

    Connection db = null;
    OpportunityHeaderList oppList = new OpportunityHeaderList();
    Contact thisContact = null;
    try {
      db = this.getConnection(context);
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
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("contactDetails", thisContact);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (!hasAuthority(context, thisContact.getOwner())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("opportunityHeaderList", oppList);
      return ("ListOppsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
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
    context.getRequest().setAttribute("UserList", userList);
    if (id != null && !"-1".equals(id)) {
      if (!hasPermission(context, "contacts-external_contacts-opportunities-add")) {
        return ("PermissionError");
      }
    }
    try {
      db = this.getConnection(context);
      if (context.getRequest().getAttribute("ContactDetails") == null) {
        thisContact = new Contact(db, contactId);
        context.getRequest().setAttribute("ContactDetails", thisContact);
      } else {
        thisContact = (Contact) context.getRequest().getAttribute("ContactDetails");
      }
      //check if a header needs to be built.
      if (id != null && !"-1".equals(id)) {
        //Build the container item
        OpportunityHeader oppHeader = new OpportunityHeader(db, Integer.parseInt(id));
        context.getRequest().setAttribute("OpportunityHeader", oppHeader);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (!hasAuthority(context, thisContact.getOwner())) {
        return ("PermissionError");
      }
      addModuleBean(context, "External Contacts", "Opportunities");
      return "PrepareOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!hasPermission(context, "contacts-external_contacts-opportunities-add")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    boolean recordInserted = false;
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
      if (!hasAuthority(context, thisContact.getOwner())) {
        return "Permission Error";
      }
      recordInserted = newOpp.insert(db, context);
      if (!recordInserted) {
        processErrors(context, newOpp.getHeader().getErrors());
        processErrors(context, newOpp.getComponent().getErrors());
        LookupList typeSelect = new LookupList(db, "lookup_opportunity_types");
        context.getRequest().setAttribute("TypeSelect", typeSelect);
        context.getRequest().setAttribute("TypeList", newOpp.getComponent().getTypeList());
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordInserted) {
        addRecentItem(context, newOpp.getHeader());
        context.getRequest().setAttribute("headerId", String.valueOf(newOpp.getHeader().getId()));
        return (executeCommandDetailsOpp(context));
      } else {
        return (executeCommandPrepare(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  public String executeCommandUpdateOpp(ActionContext context) {
    if (!hasPermission(context, "contacts-external_contacts-opportunities-edit")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    String headerId = context.getRequest().getParameter("headerId");

    try {
      db = this.getConnection(context);
      OpportunityHeader oppHeader = new OpportunityHeader(db, Integer.parseInt(headerId));
      if (!hasAuthority(context, oppHeader.getEnteredBy())) {
        return "PermissionError";
      }
      oppHeader.setModifiedBy(getUserId(context));
      oppHeader.setDescription(context.getRequest().getParameter("description"));
      resultCount = oppHeader.update(db);
      if (resultCount == -1) {
        processErrors(context, oppHeader.getErrors());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        return executeCommandModifyOpp(context);
      } else if (resultCount == 1) {
        if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter("return").equals("list")) {
          return (executeCommandViewOpps(context));
        } else {
          return (executeCommandDetailsOpp(context));
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
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSaveComponent(ActionContext context) {
    Exception errorMessage = null;
    boolean recordInserted = false;
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
    if (!hasPermission(context, permission)) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      if (newComponent.getId() > 0) {
        newComponent.setModifiedBy(getUserId(context));
        if (!(hasAuthority(context, thisContact.getOwner()) || hasAuthority(context, newComponent.getOwner()))) {
          return "PermissionError";
        }
        resultCount = newComponent.update(db, context);
      } else {
        if (!hasAuthority(context, thisContact.getOwner())) {
          return "PermissionError";
        }
        recordInserted = newComponent.insert(db, context);
      }
      if (recordInserted) {
        addRecentItem(context, newComponent);
      } else if (resultCount == 1) {
        newComponent.queryRecord(db, newComponent.getId());
      } else {
        processErrors(context, newComponent.getErrors());
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
          context.getRequest().setAttribute("UserList", userList);
        }
      }
      context.getRequest().setAttribute("ComponentDetails", newComponent);
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if ("insert".equals(action)) {
        if (recordInserted) {
          return (executeCommandDetailsOpp(context));
        } else {
          return executeCommandPrepare(context);
        }
      } else {
        if (resultCount == -1) {
          return executeCommandPrepare(context);
        } else if (resultCount == 1) {
          if ("list".equals(context.getRequest().getParameter("return"))) {
            return (executeCommandDetailsOpp(context));
          } else {
            return executeCommandDetailsComponent(context);
          }
        } else {
          context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
          return ("UserError");
        }
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since
   */
  public String executeCommandDetailsOpp(ActionContext context) {
    if (!hasPermission(context, "contacts-external_contacts-opportunities-view")) {
      return ("PermissionError");
    }
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
    componentListInfo.setLink("ExternalContactsOpps.do?command=DetailsOpp&headerId=" + headerId + "&contactId=" + contactId);
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("contactDetails", thisContact);

      thisHeader = new OpportunityHeader();
      thisHeader.setBuildComponentCount(true);
      thisHeader.queryRecord(db, headerId);
      context.getRequest().setAttribute("headerDetails", thisHeader);

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
      if (!hasAuthority(context, thisContact.getOwner())) {
        return ("PermissionError");
      }
      addRecentItem(context, thisHeader);
      return ("DetailsOppOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandModifyOpp(ActionContext context) {
    if (!hasPermission(context, "contacts-external_contacts-opportunities-edit")) {
      return ("PermissionError");
    }
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
      if (!hasAuthority(context, thisContact.getOwner())) {
        return "PermissionError";
      }
      thisHeader = new OpportunityHeader();
      thisHeader.setBuildComponentCount(true);
      thisHeader.queryRecord(db, headerId);
      context.getRequest().setAttribute("contactDetails", thisContact);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getRequest().setAttribute("headerDetails", thisHeader);
      addRecentItem(context, thisHeader);
      return ("ModifyOppOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDetailsComponent(ActionContext context) {
    if (!hasPermission(context, "contacts-external_contacts-opportunities-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    addModuleBean(context, "External Contacts", "Opportunities");
    String componentId = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");
    OpportunityComponent thisComponent = null;

    Connection db = null;
    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact(db, contactId);
      thisComponent = new OpportunityComponent(db, Integer.parseInt(componentId));
      if (!(hasAuthority(context, thisContact.getOwner()) || hasAuthority(context, thisComponent.getOwner()))) {
        return "PermissionError";
      }
      thisComponent.checkEnabledOwnerAccount(db);
      context.getRequest().setAttribute("contactDetails", thisContact);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (!hasAuthority(context, thisComponent.getOwner())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("oppComponentDetails", thisComponent);
      addRecentItem(context, thisComponent);
      return ("DetailsComponentOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!hasPermission(context, "contacts-external_contacts-opportunities-delete")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    String headerId = context.getRequest().getParameter("headerId");
    String contactId = context.getRequest().getParameter("contactId");
    Connection db = null;
    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact(db, contactId);
      if (!hasAuthority(context, thisContact.getOwner())) {
        return "PermissionError";
      }
      OpportunityHeader thisOpp = new OpportunityHeader(db, headerId);
      DependencyList dependencies = thisOpp.processDependencies(db);
      htmlDialog.addMessage(dependencies.getHtmlString());
      htmlDialog.setTitle("CFS: Confirm Delete");
      htmlDialog.setHeader("This object has the following dependencies within CFS:");
      htmlDialog.addButton("Delete All", "javascript:window.location.href='ExternalContactsOpps.do?command=DeleteOpp&contactId=" + contactId + "&id=" + headerId + "'");
      htmlDialog.addButton("Cancel", "javascript:parent.window.close()");
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
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandConfirmComponentDelete(ActionContext context) {
    if (!hasPermission(context, "contacts-external_contacts-opportunities-delete")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    OpportunityComponent thisComponent = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    String id = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");

    Connection db = null;
    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact(db, contactId);
      thisComponent = new OpportunityComponent(db, id);
      if (!(hasAuthority(context, thisContact.getOwner()) || hasAuthority(context, thisComponent.getOwner()))) {
        return "PermissionError";
      }
      htmlDialog.setTitle("CFS: General Contacts Opportunities");
      htmlDialog.setShowAndConfirm(false);
      htmlDialog.setDeleteUrl("javascript:window.location.href='ExternalContactsOppComponents.do?command=DeleteComponent&contactId=" + contactId + "&id=" + id + "'");
      htmlDialog.addButton("Cancel", "javascript:parent.window.close()");
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
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since
   */
  public String executeCommandDeleteOpp(ActionContext context) {
    if (!hasPermission(context, "contacts-external_contacts-opportunities-delete")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    boolean recordDeleted = false;
    String contactId = context.getRequest().getParameter("contactId");
    OpportunityHeader newOpp = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact(db, contactId);
      if (!hasAuthority(context, thisContact.getOwner())) {
        return "PermissionError";
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
        context.getRequest().setAttribute("refreshUrl", "ExternalContactsOpps.do?command=ViewOpps&contactId=" + contactId);
        deleteRecentItem(context, newOpp);
        return ("OppDeleteOK");
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
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDeleteComponent(ActionContext context) {
    if (!hasPermission(context, "contacts-external_contacts-opportunities-delete")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    boolean recordDeleted = false;
    OpportunityComponent component = null;
    String contactId = context.getRequest().getParameter("contactId");
    Connection db = null;
    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact(db, contactId);
      component = new OpportunityComponent(db, context.getRequest().getParameter("id"));
      if (!(hasAuthority(context, thisContact.getOwner()) || hasAuthority(context, component.getOwner()))) {
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
        context.getRequest().setAttribute("refreshUrl", "ExternalContactsOpps.do?command=DetailsOpp&headerId=" + component.getHeaderId() + "&contactId=" + contactId);
        deleteRecentItem(context, component);
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
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandModifyComponent(ActionContext context) {
    if (!hasPermission(context, "contacts-external_contacts-opportunities-edit")) {
      return ("PermissionError");
    }
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
    context.getRequest().setAttribute("UserList", userList);

    try {
      db = this.getConnection(context);
      component = new OpportunityComponent(db, componentId);
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (!(hasAuthority(context, thisContact.getOwner()) || hasAuthority(context, component.getOwner()))) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ComponentDetails", component);
      addRecentItem(context, component);
      return executeCommandPrepare(context);
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
}

