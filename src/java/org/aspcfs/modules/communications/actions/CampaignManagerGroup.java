package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.Vector;
import java.util.Iterator;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;

/**
 *  Actions for dealing with Groups in the Communications Module
 *
 *@author     mrajkowski
 *@created    December 11, 2001
 *@version    $Id: CampaignManagerGroup.java,v 1.7 2001/12/20 19:33:26
 *      mrajkowski Exp $
 */
public final class CampaignManagerGroup extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandShowContactsPopup(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-groups-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    SearchCriteriaList thisSCL = new SearchCriteriaList();

    context.getSession().removeAttribute("CampaginGroupsContactsInfo");
    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "CampaignGroupsContactsInfo");
    pagedListInfo.setLink("/CampaignManagerGroup.do?command=ShowContactsPopup&popup=true");

    try {
      db = this.getConnection(context);
      thisSCL.setEnteredBy(getUserId(context));
      thisSCL.setModifiedBy(getUserId(context));
      thisSCL.setOwner(getUserId(context));
      thisSCL.setContactSource(Integer.parseInt(context.getRequest().getParameter("source")));

      ContactList contacts = new ContactList();
      contacts.setScl(thisSCL, this.getUserId(context), this.getUserRange(context));
      contacts.setPagedListInfo(pagedListInfo);
      contacts.buildList(db);
      context.getRequest().setAttribute("ContactList", contacts);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("PopupOK");
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
   *@since           1.1
   */
  public String executeCommandView(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-groups-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "CampaignGroupListInfo");
    pagedListInfo.setLink("/CampaignManagerGroup.do?command=View");

    Connection db = null;
    SearchCriteriaListList sclList = new SearchCriteriaListList();

    try {
      db = this.getConnection(context);
      sclList.setPagedListInfo(pagedListInfo);
      if ("all".equals(pagedListInfo.getListView())) {
        sclList.setOwnerIdRange(this.getUserRange(context));
      } else {
        sclList.setOwner(this.getUserId(context));
      }
      sclList.buildList(db);
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
      submenu = "ManageGroups";
    }
    context.getRequest().setAttribute("submenu", submenu);
    addModuleBean(context, submenu, "View Groups");

    if (errorMessage == null) {
      context.getRequest().setAttribute("sclList", sclList);
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
  public String executeCommandAdd(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-groups-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    String passedId = null;

    Connection db = null;
    SearchFieldList searchFieldList = new SearchFieldList();
    SearchOperatorList stringOperatorList = new SearchOperatorList();
    SearchOperatorList dateOperatorList = new SearchOperatorList();
    SearchOperatorList numberOperatorList = new SearchOperatorList();

    passedId = context.getRequest().getParameter("id");

    // building the search field and operator lists
    try {
      db = this.getConnection(context);

      ContactTypeList typeList = new ContactTypeList(db);
      LookupList ctl = typeList.getLookupList("typeId", 0);
      ctl.setJsEvent("onChange = \"javascript:setText(document.searchForm.typeId)\"");
      context.getRequest().setAttribute("ContactTypeList", ctl);

      searchFieldList.buildFieldList(db);
      stringOperatorList.buildOperatorList(db, 0);
      dateOperatorList.buildOperatorList(db, 1);
      numberOperatorList.buildOperatorList(db, 2);

      if (passedId != null) {
        SearchCriteriaList scl = new SearchCriteriaList(db, passedId);
        context.getSession().setAttribute("SCL", scl);
      }

      this.buildContactSource(context);
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
      submenu = "ManageGroups";
    }
    context.getRequest().setAttribute("submenu", submenu);
    addModuleBean(context, submenu, "Build New Group");

    if (errorMessage == null) {

      context.getRequest().setAttribute("SearchFieldList", searchFieldList);
      context.getRequest().setAttribute("StringOperatorList", stringOperatorList);
      context.getRequest().setAttribute("DateOperatorList", dateOperatorList);
      context.getRequest().setAttribute("NumberOperatorList", numberOperatorList);
      return ("AddOK");
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

    if (!(hasPermission(context, "campaign-campaigns-groups-delete"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordDeleted = false;

    String passedId = null;
    SearchCriteriaList thisSCL = null;

    passedId = context.getRequest().getParameter("id");

    Connection db = null;
    try {
      db = this.getConnection(context);
      thisSCL = new SearchCriteriaList(db, passedId);
      recordDeleted = thisSCL.delete(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordDeleted) {
        return ("DeleteOK");
      } else {
        processErrors(context, thisSCL.getErrors());
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
   *@since           1.1
   */
  public String executeCommandInsert(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-groups-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    boolean recordInserted = false;

    //SearchFormBean thisSearchForm = (SearchFormBean)context.getRequest().getAttribute("SearchForm");
    SearchFormBean thisSearchForm = (SearchFormBean) context.getFormBean();
    SearchCriteriaList thisSCL = thisSearchForm.getSearchCriteriaList();

    try {
      db = this.getConnection(context);
      thisSCL.setGroupName(thisSearchForm.getGroupName());
      thisSCL.setContactSource(thisSearchForm.getContactSource());
      thisSCL.setEnteredBy(getUserId(context));
      thisSCL.setModifiedBy(getUserId(context));
      thisSCL.setOwner(getUserId(context));
      recordInserted = thisSCL.insert(db);

      if (!recordInserted) {
        processErrors(context, thisSCL.getErrors());
      } else {

        context.getRequest().setAttribute("id", "" + thisSCL.getId());

        context.getSession().removeAttribute("CampaignGroupsPreviewInfo");
        PagedListInfo pagedListInfo = this.getPagedListInfo(context, "CampaignGroupsPreviewInfo");
        pagedListInfo.setLink("/CampaignManagerGroup.do?command=Preview&id=" + thisSCL.getId());

        ContactList contacts = new ContactList();
        contacts.setScl(thisSCL, this.getUserId(context), this.getUserRange(context));
        contacts.setPagedListInfo(pagedListInfo);
        contacts.buildList(db);
        context.getRequest().setAttribute("ContactList", contacts);
      }

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted) {
        addModuleBean(context, "ManageGroups", "Preview");
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
  public String executeCommandModify(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-groups-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;

    SearchFieldList searchFieldList = new SearchFieldList();
    SearchOperatorList stringOperatorList = new SearchOperatorList();
    SearchOperatorList dateOperatorList = new SearchOperatorList();
    SearchOperatorList numberOperatorList = new SearchOperatorList();

    String passedId = context.getRequest().getParameter("id");

    // building the search field and operator lists
    try {
      db = this.getConnection(context);

      ContactTypeList typeList = new ContactTypeList(db);
      LookupList ctl = typeList.getLookupList("typeId", 0);
      ctl.setJsEvent("onChange = \"javascript:setText(document.searchForm.typeId)\"");
      context.getRequest().setAttribute("ContactTypeList", ctl);

      searchFieldList.buildFieldList(db);
      stringOperatorList.buildOperatorList(db, 0);
      dateOperatorList.buildOperatorList(db, 1);
      numberOperatorList.buildOperatorList(db, 2);

      this.buildContactSource(context);

      if (passedId != null) {
        SearchCriteriaList scl = new SearchCriteriaList(db, passedId);
        context.getRequest().setAttribute("SCL", scl);
      }
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
      submenu = "ManageGroups";
    }
    context.getRequest().setAttribute("submenu", submenu);
    addModuleBean(context, submenu, "Modify Criteria");

    if (errorMessage == null) {
      context.getRequest().setAttribute("SearchFieldList", searchFieldList);
      context.getRequest().setAttribute("StringOperatorList", stringOperatorList);
      context.getRequest().setAttribute("DateOperatorList", dateOperatorList);
      context.getRequest().setAttribute("NumberOperatorList", numberOperatorList);
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

    if (!(hasPermission(context, "campaign-campaigns-groups-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;

    SearchFormBean thisSearchForm = (SearchFormBean) context.getRequest().getAttribute("SearchForm");
    SearchCriteriaList thisSCL = thisSearchForm.getSearchCriteriaList();

    try {
      db = this.getConnection(context);
      thisSCL.setId(Integer.parseInt(context.getRequest().getParameter("id")));
      thisSCL.setGroupName(thisSearchForm.getGroupName());
      thisSCL.setContactSource(thisSearchForm.getContactSource());
      thisSCL.setOwner(thisSearchForm.getOwner());
      thisSCL.setModifiedBy(getUserId(context));
      resultCount = thisSCL.update(db);

      if (resultCount == -1) {
        processErrors(context, thisSCL.getErrors());
      } else {

        context.getRequest().setAttribute("id", "" + thisSCL.getId());

        context.getSession().removeAttribute("CampaignGroupsPreviewInfo");
        PagedListInfo pagedListInfo = this.getPagedListInfo(context, "CampaignGroupsPreviewInfo");
        pagedListInfo.setLink("/CampaignManagerGroup.do?command=Preview&id=" + thisSCL.getId());

        ContactList contacts = new ContactList();
        contacts.setScl(thisSCL, this.getUserId(context), this.getUserRange(context));
        contacts.setPagedListInfo(pagedListInfo);
        contacts.buildList(db);
        context.getRequest().setAttribute("ContactList", contacts);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "ManageGroups", "Preview");
    if (errorMessage == null) {
      if (resultCount == -1) {
        return executeCommandModify(context);
      } else if (resultCount == 1) {
        return ("UpdateOK");
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
   *@since
   */
  public String executeCommandPreview(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-groups-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;

    try {
      db = this.getConnection(context);
      SearchCriteriaList thisSCL = new SearchCriteriaList(db, context.getRequest().getParameter("id"));
      context.getRequest().setAttribute("SCL", thisSCL);
      context.getRequest().setAttribute("id", "" + thisSCL.getId());

      PagedListInfo pagedListInfo = this.getPagedListInfo(context, "CampaignGroupsPreviewInfo");
      pagedListInfo.setLink("/CampaignManagerGroup.do?command=Preview&id=" + thisSCL.getId());

      ContactList contacts = new ContactList();
      contacts.setScl(thisSCL, this.getUserId(context), this.getUserRange(context));
      contacts.setPagedListInfo(pagedListInfo);
      contacts.buildList(db);
      context.getRequest().setAttribute("ContactList", contacts);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "ManageGroups", "Preview");

    if (errorMessage == null) {
      return ("PreviewOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   */
  public void buildContactSource(ActionContext context) {
    HtmlSelect contactSource = new HtmlSelect();
    contactSource.addItem(SearchCriteriaList.SOURCE_ALL_ACCOUNTS, "All Accounts");
    contactSource.addItem(SearchCriteriaList.SOURCE_MY_ACCOUNTS, "My Accounts");
    contactSource.addItem(SearchCriteriaList.SOURCE_MY_ACCOUNT_HIERARCHY, "My Account Hierarchy");
    contactSource.addItem(SearchCriteriaList.SOURCE_MY_CONTACTS, "My Contacts");
    contactSource.addItem(SearchCriteriaList.SOURCE_EMPLOYEES, "Employees");
    context.getRequest().setAttribute("ContactSource", contactSource);
  }

}

