package org.aspcfs.modules.contacts.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.ActionContext;
import java.sql.*;
import java.util.Vector;
import java.io.*;
import java.sql.*;
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
 *@author     chris
 *@created    October 15, 2001
 *@version    $Id: ExternalContactsOpps.java,v 1.3 2002/02/05 19:44:43 chris Exp
 *      $
 */
public final class ExternalContactsOpps extends CFSModule {

  private final static int EMPLOYEE_TYPE = 0;


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandViewOpps(ActionContext context) {

    if (!(hasPermission(context, "contacts-external_contacts-opportunities-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    String contactId = context.getRequest().getParameter("contactId");

    addModuleBean(context, "External Contacts", "Opportunities");

    PagedListInfo oppPagedListInfo = this.getPagedListInfo(context, "OpportunityPagedListInfo");
    oppPagedListInfo.setLink("/ExternalContactsOpps.do?command=ViewOpps&contactId=" + contactId);

    Connection db = null;
    OpportunityHeaderList oppList = new OpportunityHeaderList();
    Contact thisContact = null;

    try {
      db = this.getConnection(context);
      oppList.setPagedListInfo(oppPagedListInfo);
      oppList.setBuildTotalValues(true);
      oppList.setContactId(contactId);
      oppList.buildList(db);
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("OpportunityHeaderList", oppList);
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
   *@since
   */
  public String executeCommandAddOpp(ActionContext context) {

    if (!(hasPermission(context, "contacts-external_contacts-opportunities-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    String contactId = context.getRequest().getParameter("contactId");
    Contact thisContact = null;

    Connection db = null;

    try {
      db = this.getConnection(context);
      buildAddFormElements(db, context);
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "External Contacts", "Opportunities");
      return ("AddOppOK");
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
  public String executeCommandAddOppComponent(ActionContext context) {

    if (!(hasPermission(context, "contacts-external_contacts-opportunities-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    Contact thisContact = null;
    String contactId = null;
    String oppId = null;
    OpportunityHeader oppHeader = null;

    if (context.getRequest().getParameter("contactId") != null) {
      contactId = context.getRequest().getParameter("contactId");
    }
    if (context.getRequest().getParameter("id") != null) {
      oppId = context.getRequest().getParameter("id");
    }

    try {
      db = this.getConnection(context);
      buildAddFormElements(db, context);

      //get the header info
      oppHeader = new OpportunityHeader(db, oppId);
      context.getRequest().setAttribute("OpportunityHeader", oppHeader);

      //build the contact
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "External Contacts", "Opportunities");
      return ("AddOppComponentOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db       Description of the Parameter
   *@param  context  Description of the Parameter
   */
  public void buildAddFormElements(Connection db, ActionContext context) {
    Exception errorMessage = null;

    HtmlSelect busTypeSelect = new HtmlSelect();
    busTypeSelect.setSelectName("type");
    busTypeSelect.addItem("N", "New");
    busTypeSelect.addItem("E", "Existing");
    busTypeSelect.build();

    HtmlSelect unitSelect = new HtmlSelect();
    unitSelect.setSelectName("units");
    unitSelect.addItem("M", "Months");
    unitSelect.build();

    context.getRequest().setAttribute("BusTypeList", busTypeSelect);
    context.getRequest().setAttribute("UnitTypeList", unitSelect);

    try {
      LookupList stageSelect = new LookupList(db, "lookup_stage");
      context.getRequest().setAttribute("StageList", stageSelect);
    } catch (SQLException e) {
      errorMessage = e;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandInsertOpp(ActionContext context) {

    if (!(hasPermission(context, "contacts-external_contacts-opportunities-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordInserted = false;
    String contactId = null;
    Contact thisContact = null;
    Connection db = null;
    OpportunityHeader resultHeader = null;
    OpportunityComponentList componentList = null;

    OpportunityBean newOpp = (OpportunityBean) context.getRequest().getAttribute("OppDetails");

    //set types
    newOpp.getComponent().setTypeList(context.getRequest().getParameterValues("selectedList"));
    newOpp.getComponent().setOwner(getUserId(context));
    newOpp.getComponent().setEnteredBy(getUserId(context));
    newOpp.getComponent().setModifiedBy(getUserId(context));
    newOpp.getHeader().setEnteredBy(getUserId(context));
    newOpp.getHeader().setModifiedBy(getUserId(context));

    if (context.getRequest().getParameter("contactId") != null) {
      contactId = context.getRequest().getParameter("contactId");
    }

    try {
      db = this.getConnection(context);
      recordInserted = newOpp.insert(db, context);

      if (recordInserted) {
        resultHeader = new OpportunityHeader(db, newOpp.getHeader().getOppId());
        context.getRequest().setAttribute("HeaderDetails", resultHeader);

        PagedListInfo componentListInfo = this.getPagedListInfo(context, "ComponentListInfo");
        componentListInfo.setLink("ExternalContactsOpps.do?command=DetailsOpp&id=" + resultHeader.getOppId() + "&contactId=" + contactId);
        componentList = new OpportunityComponentList();
        componentList.setPagedListInfo(componentListInfo);
        componentList.setOppId(resultHeader.getOppId());
        componentList.buildList(db);
        context.getRequest().setAttribute("ComponentList", componentList);
      } else {
        processErrors(context, newOpp.getHeader().getErrors());
        processErrors(context, newOpp.getComponent().getErrors());
      }

      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);

    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted) {
        addRecentItem(context, resultHeader);
        return ("DetailsOppOK");
      } else {
        return (executeCommandAddOpp(context));
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
  public String executeCommandInsertOppComponent(ActionContext context) {

    if (!(hasPermission(context, "contacts-external_contacts-opportunities-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordInserted = false;
    Contact thisContact = null;
    String contactId = null;
    Connection db = null;

    OpportunityComponent newComponent = (OpportunityComponent) context.getFormBean();

    if (context.getRequest().getParameter("contactId") != null) {
      contactId = context.getRequest().getParameter("contactId");
    }

    //set types
    newComponent.setTypeList(context.getRequest().getParameterValues("selectedList"));
    newComponent.setOwner(getUserId(context));
    newComponent.setEnteredBy(getUserId(context));
    newComponent.setModifiedBy(getUserId(context));

    try {
      db = this.getConnection(context);
      recordInserted = newComponent.insert(db, context);

      if (recordInserted) {
        context.getRequest().setAttribute("OppComponentDetails", newComponent);
        addRecentItem(context, newComponent);
      } else {
        processErrors(context, newComponent.getErrors());
      }
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted) {
        return (executeCommandDetailsOpp(context));
      } else {
        return (executeCommandAddOppComponent(context));
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
  public String executeCommandDetailsOpp(ActionContext context) {

    if (!(hasPermission(context, "contacts-external_contacts-opportunities-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    int oppId = -1;
    addModuleBean(context, "External Contacts", "Opportunities");

    if (context.getRequest().getParameter("oppId") != null) {
      oppId = Integer.parseInt(context.getRequest().getParameter("oppId"));
    }

    String contactId = context.getRequest().getParameter("contactId");

    Connection db = null;
    OpportunityHeader thisHeader = null;
    Contact thisContact = null;
    OpportunityComponentList componentList = null;

    PagedListInfo componentListInfo = this.getPagedListInfo(context, "ComponentListInfo");
    componentListInfo.setLink("ExternalContactsOpps.do?command=DetailsOpp&id=" + oppId + "&contactId=" + contactId);

    try {
      db = this.getConnection(context);

      thisHeader = new OpportunityHeader();
      thisHeader.setBuildComponentCount(true);
      thisHeader.queryRecord(db, oppId);

      //check whether or not the owner is an active User
      //thisHeader.checkEnabledOwnerAccount(db);

      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);

      componentList = new OpportunityComponentList();
      componentList.setPagedListInfo(componentListInfo);
      componentList.setOwnerIdRange(this.getUserRange(context));
      componentList.setOppId(thisHeader.getOppId());
      componentList.buildList(db);
      context.getRequest().setAttribute("ComponentList", componentList);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("HeaderDetails", thisHeader);
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
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModifyOpp(ActionContext context) {

    if (!(hasPermission(context, "contacts-external_contacts-opportunities-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    int oppId = -1;
    addModuleBean(context, "External Contacts", "Opportunities");

    if (context.getRequest().getParameter("oppId") != null) {
      oppId = Integer.parseInt(context.getRequest().getParameter("oppId"));
    }

    String contactId = context.getRequest().getParameter("contactId");

    Connection db = null;
    OpportunityHeader thisHeader = null;
    Contact thisContact = null;

    try {
      db = this.getConnection(context);

      thisHeader = new OpportunityHeader();
      thisHeader.setBuildComponentCount(true);
      thisHeader.queryRecord(db, oppId);

      //check whether or not the owner is an active User
      //thisHeader.checkEnabledOwnerAccount(db);

      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("HeaderDetails", thisHeader);
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
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDetailsComponent(ActionContext context) {

    if (!(hasPermission(context, "contacts-external_contacts-opportunities-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    addModuleBean(context, "External Contacts", "Opportunities");

    String componentId = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");

    Connection db = null;
    OpportunityComponent thisComponent = null;
    Contact thisContact = null;

    try {
      db = this.getConnection(context);
      thisComponent = new OpportunityComponent(db, Integer.parseInt(componentId));
      thisComponent.checkEnabledOwnerAccount(db);
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {

      if (!hasAuthority(context, thisComponent.getOwner())) {
        return ("PermissionError");
      }

      context.getRequest().setAttribute("OppComponentDetails", thisComponent);
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
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    OpportunityHeader thisOpp = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    String id = null;
    String contactId = null;

    if (!(hasPermission(context, "contacts-external_contacts-opportunities-delete"))) {
      return ("PermissionError");
    }

    if (context.getRequest().getParameter("id") != null) {
      id = context.getRequest().getParameter("id");
    }
    if (context.getRequest().getParameter("contactId") != null) {
      contactId = context.getRequest().getParameter("contactId");
    }

    try {
      db = this.getConnection(context);
      thisOpp = new OpportunityHeader(db, id);
      DependencyList dependencies = thisOpp.processDependencies(db);
      htmlDialog.addMessage(dependencies.getHtmlString());
      htmlDialog.setTitle("CFS: Confirm Delete");
      htmlDialog.setHeader("This object has the following dependencies within CFS:");
      htmlDialog.addButton("Delete All", "javascript:window.location.href='ExternalContactsOpps.do?command=DeleteOpp&contactId=" + contactId + "&id=" + id + "'");
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
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfirmComponentDelete(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    OpportunityComponent thisComponent = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    String id = null;
    String contactId = null;

    if (!(hasPermission(context, "contacts-external_contacts-opportunities-delete"))) {
      return ("PermissionError");
    }

    if (context.getRequest().getParameter("oppId") != null) {
      id = context.getRequest().getParameter("oppId");
    }
    if (context.getRequest().getParameter("contactId") != null) {
      contactId = context.getRequest().getParameter("contactId");
    }

    try {
      db = this.getConnection(context);
      thisComponent = new OpportunityComponent(db, id);
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
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDeleteOpp(ActionContext context) {

    if (!(hasPermission(context, "contacts-external_contacts-opportunities-delete"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordDeleted = false;
    OpportunityHeader newOpp = null;
    String contactId = null;

    if (context.getRequest().getParameter("contactId") != null) {
      contactId = context.getRequest().getParameter("contactId");
    }

    Connection db = null;
    try {
      db = this.getConnection(context);
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
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDeleteComponent(ActionContext context) {

    if (!(hasPermission(context, "contacts-external_contacts-opportunities-delete"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordDeleted = false;
    OpportunityComponent component = null;
    String contactId = null;
    Connection db = null;

    if (context.getRequest().getParameter("contactId") != null) {
      contactId = context.getRequest().getParameter("contactId");
    }

    try {
      db = this.getConnection(context);
      component = new OpportunityComponent(db, context.getRequest().getParameter("id"));
      recordDeleted = component.delete(db, context, this.getPath(context, "opportunities"));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute("refreshUrl", "ExternalContactsOpps.do?command=DetailsOpp&oppId=" + component.getOppId() + "&contactId=" + contactId);
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
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModifyComponent(ActionContext context) {

    if (!(hasPermission(context, "contacts-external_contacts-opportunities-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Contact thisContact = null;
    Connection db = null;
    OpportunityComponent component = null;
    addModuleBean(context, "External Contacts", "Opportunities");

    String contactId = context.getRequest().getParameter("contactId");
    String passedId = context.getRequest().getParameter("id");

    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

    //this is how we get the multiple-level heirarchy...recursive function.

    User thisRec = thisUser.getUserRecord();

    UserList shortChildList = thisRec.getShortChildList();
    UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
    userList.setMyId(getUserId(context));
    userList.setMyValue(thisUser.getNameLast() + ", " + thisUser.getNameFirst());
    userList.setIncludeMe(true);
    userList.setExcludeDisabledIfUnselected(true);
    context.getRequest().setAttribute("UserList", userList);

    try {
      db = this.getConnection(context);
      buildAddFormElements(db, context);
      component = new OpportunityComponent(db, passedId);
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (!hasAuthority(context, component.getOwner())) {
        return ("PermissionError");
      }

      context.getRequest().setAttribute("OppComponentDetails", component);
      addRecentItem(context, component);
      return ("ComponentModifyOK");
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
  public String executeCommandUpdateOpp(ActionContext context) {

    if (!(hasPermission(context, "contacts-external_contacts-opportunities-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    OpportunityHeader oppHeader = null;
    String oppId = null;

    if (context.getRequest().getParameter("oppId") != null) {
      oppId = context.getRequest().getParameter("oppId");
    }

    try {
      db = this.getConnection(context);
      oppHeader = new OpportunityHeader(db, oppId);
      oppHeader.setModifiedBy(getUserId(context));
      oppHeader.setDescription(context.getRequest().getParameter("description"));
      resultCount = oppHeader.update(db);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        processErrors(context, oppHeader.getErrors());
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
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandUpdateComponent(ActionContext context) {

    if (!(hasPermission(context, "contacts-external_contacts-opportunities-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    Contact thisContact = null;
    OpportunityHeader header = null;

    OpportunityComponent component = (OpportunityComponent) context.getFormBean();
    component.setTypeList(context.getRequest().getParameterValues("selectedList"));

    String contactId = context.getRequest().getParameter("contactId");

    try {
      db = this.getConnection(context);
      component.setModifiedBy(getUserId(context));
      resultCount = component.update(db, context);

      if (resultCount == 1) {
        component.queryRecord(db, component.getId());
        context.getRequest().setAttribute("OppComponentDetails", component);
      }

      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);

    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        processErrors(context, component.getErrors());
        return executeCommandModifyComponent(context);
      } else if (resultCount == 1) {
        if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter("return").equals("list")) {
          return (executeCommandDetailsOpp(context));
        } else {
          return ("DetailsComponentOK");
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

}

