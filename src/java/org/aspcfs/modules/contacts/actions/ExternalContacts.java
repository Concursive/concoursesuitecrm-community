package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.Vector;
import java.io.*;
import java.sql.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;

/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    September 24, 2001
 *@version    $Id: ExternalContacts.java,v 1.2 2001/10/03 15:40:42 mrajkowski
 *      Exp $
 */
public final class ExternalContacts extends CFSModule {

  /**
   *  Generates a list of external contacts
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.1
   */
  public String executeCommandListContacts(ActionContext context) {

    Exception errorMessage = null;
    
    PagedListInfo externalContactsInfo = this.getPagedListInfo(context, "ExternalContactsInfo");
    externalContactsInfo.setLink("/ExternalContacts.do?command=ListContacts");
    
    String passedFirst = context.getRequest().getParameter("firstname");
    String passedMiddle = context.getRequest().getParameter("middlename");
    String passedLast = context.getRequest().getParameter("lastname");
    String passedTitle = context.getRequest().getParameter("title");
    String passedCompany = context.getRequest().getParameter("company");

    Connection db = null;
    ContactList contactList = new ContactList();
    
    //this is search stuff
      
      if (passedFirst != null && !(passedFirst.equals(""))) {
        passedFirst = "%" + passedFirst + "%";
        contactList.setFirstName(passedFirst);
      }
      if (passedMiddle != null && !(passedMiddle.equals(""))) {
        passedMiddle = "%" + passedMiddle + "%";
        contactList.setMiddleName(passedMiddle);
      }
      if (passedLast != null && !(passedLast.equals(""))) {
        passedLast = "%" + passedLast + "%";
        contactList.setLastName(passedLast);
      }
      if (passedTitle != null && !(passedTitle.equals(""))) {
        passedTitle = "%" + passedTitle + "%";
        contactList.setTitle(passedTitle);
      }
      if (passedCompany != null && !(passedCompany.equals(""))) {
        passedCompany = "%" + passedCompany + "%";
        contactList.setCompany(passedCompany);
      }
      
    //end search stuff
    
    try {
      db = this.getConnection(context);
      context.getSession().removeAttribute("ContactMessageListInfo");

      contactList.setPagedListInfo(externalContactsInfo);
      contactList.addIgnoreTypeId(Contact.EMPLOYEE_TYPE);
      contactList.setPersonalId(this.getUserId(context));
      
      if ("all".equals(externalContactsInfo.getListView())) {
        contactList.setOwnerIdRange(this.getUserRange(context));
      } else {
        contactList.setOwner(this.getUserId(context));
      }
      
      contactList.buildList(db);
      
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "External Contacts", "External List");
    if (errorMessage == null) {
      context.getRequest().setAttribute("ContactList", contactList);
      return ("ListContactsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandMessageDetails(ActionContext context) {
    Exception errorMessage = null;
    addModuleBean(context, "External Contacts", "Message Details");
    Connection db = null;
    Contact thisContact = null;
    Message newMessage = null;
    
    String campaignId = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      String contactId = context.getRequest().getParameter("contactId");
      thisContact = new Contact(db, contactId);
      Campaign campaign = new Campaign(db, campaignId);
      context.getRequest().setAttribute("Campaign", campaign);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("ContactDetails", thisContact);
      return ("MessageDetailsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandViewMessages(ActionContext context) {
    Connection db = null;
    Exception errorMessage = null;
    Contact thisContact = null;
    
    try {
      db = this.getConnection(context);
      
      String contactId = context.getRequest().getParameter("contactId");
      
      context.getSession().removeAttribute("ContactMessageListInfo");
      PagedListInfo pagedListInfo = this.getPagedListInfo(context, "ContactMessageListInfo");
      pagedListInfo.setLink("/ExternalContacts.do?command=ViewMessages&contactId=" + contactId);
      
      thisContact = new Contact(db, contactId);
      String msgRange = thisContact.getCampaignMessageRange(db);
      context.getRequest().setAttribute("ContactDetails", thisContact);
      
      CampaignList campaignList = new CampaignList();
      campaignList.setPagedListInfo(pagedListInfo);
      
      campaignList.setCompleteOnly(true);
      campaignList.setIdRange(msgRange);
      
      if ("all".equals(pagedListInfo.getListView())) {
        campaignList.setOwnerIdRange(this.getUserRange(context));
      } else {
        campaignList.setOwner(this.getUserId(context));
      }
      
      campaignList.buildList(db);
      context.getRequest().setAttribute("campList", campaignList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "External Contacts", "Messages");
      return ("ViewMessagesOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  
  public String executeCommandAddFolderRecord(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    Contact thisContact = null;

    try {
      String contactId = context.getRequest().getParameter("contactId");
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);

      String selectedCatId = (String)context.getRequest().getParameter("catId");
      CustomFieldCategory thisCategory = new CustomFieldCategory(db, 
        Integer.parseInt(selectedCatId));
      thisCategory.setLinkModuleId(Constants.CONTACTS);
      thisCategory.setLinkItemId(thisContact.getId());
      thisCategory.setIncludeEnabled(Constants.TRUE);
      thisCategory.setIncludeScheduled(Constants.TRUE);
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      context.getRequest().setAttribute("Category", thisCategory);
      
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "External Contacts", "Add Folder Record");
      return ("AddFolderRecordOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  
  
   public String executeCommandFields(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    Contact thisContact = null;

    String recordId = null;
    
    try {
      String contactId = context.getRequest().getParameter("contactId");
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
      
      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.CONTACTS);
      thisList.setIncludeEnabled(Constants.TRUE);
      thisList.setIncludeScheduled(Constants.TRUE);
      thisList.setBuildResources(false);
      thisList.buildList(db);
      context.getRequest().setAttribute("CategoryList", thisList);
      
      String selectedCatId = (String)context.getRequest().getParameter("catId");
      if (selectedCatId == null) {
        selectedCatId = (String)context.getRequest().getAttribute("catId");
      }
      if (selectedCatId == null) {
        selectedCatId = "" + thisList.getDefaultCategoryId();
      }
      context.getRequest().setAttribute("catId", selectedCatId);
      
      recordId = context.getRequest().getParameter("recId");
      
      if (recordId == null) {
        CustomFieldCategory thisCategory = thisList.getCategory(Integer.parseInt(selectedCatId));
        context.getRequest().setAttribute("Category", thisCategory);
        
        CustomFieldRecordList recordList = new CustomFieldRecordList();
        recordList.setLinkModuleId(Constants.CONTACTS);
        recordList.setLinkItemId(thisContact.getId());
        recordList.setCategoryId(thisCategory.getId());
        recordList.buildList(db);
        context.getRequest().setAttribute("Records", recordList);
      } else {
        CustomFieldCategory thisCategory = thisList.getCategory(Integer.parseInt(selectedCatId));
        thisCategory.setLinkModuleId(Constants.CONTACTS);
        thisCategory.setLinkItemId(thisContact.getId());
        thisCategory.setRecordId(Integer.parseInt(recordId));
        thisCategory.setIncludeEnabled(Constants.TRUE);
        thisCategory.setIncludeScheduled(Constants.TRUE);
        thisCategory.setBuildResources(true);
        thisCategory.buildResources(db);
        context.getRequest().setAttribute("Category", thisCategory);
      }
      
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "External Contacts", "Custom Fields Details");
      if (recordId == null) {
        return ("FieldRecordListOK");
      } else {
        return ("FieldsOK");
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
   *@since           1.2
   */
  public String executeCommandSearchContactsForm(ActionContext context) {
    addModuleBean(context, "Search Contacts", "Contacts Search");
    return ("SearchContactsFormOK");
  }


  /**
   *  This method retrieves the details of a single contact. The resulting
   *  Contact object is added to the request if successful.<p>
   *
   *  This method handles output for both viewing and modifying a contact.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.1
   */
  public String executeCommandContactDetails(ActionContext context) {
    Exception errorMessage = null;

    String contactId = context.getRequest().getParameter("id");
    String action = context.getRequest().getParameter("action");
    
    UserBean thisUser = (UserBean)context.getSession().getAttribute("User");
	
    //this is how we get the multiple-level heirarchy...recursive function.
	
    User thisRec = thisUser.getUserRecord();
	
    UserList shortChildList = thisRec.getShortChildList();
    UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
    userList.setMyId(getUserId(context));
    userList.setMyValue(thisUser.getNameLast() + ", " + thisUser.getNameFirst());
    userList.setIncludeMe(true);
    context.getRequest().setAttribute("UserList", userList);

    Connection db = null;
    Contact thisContact = null;

    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
      addRecentItem(context, thisContact);

      if (action != null && action.equals("modify")) {
        buildFormElements(context, db);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (action != null && action.equals("modify")) {
        addModuleBean(context, "External Contacts", "Modify Contact Details");
	context.getSession().removeAttribute("ContactMessageListInfo");
        return ("ContactDetailsModifyOK");
      } else {
        addModuleBean(context, "External Contacts", "View Contact Details");
	context.getSession().removeAttribute("ContactMessageListInfo");
        return ("ContactDetailsOK");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  This method retrieves a Contact bean that was submitted, then updates the
   *  database with the results. <p>
   *
   *  If someone else has already updated the database record, then a message is
   *  displayed for the user and the record is not updated.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.1
   */
  public String executeCommandUpdateContact(ActionContext context) {
    Exception errorMessage = null;

    Contact thisContact = (Contact)context.getFormBean();

    Connection db = null;
    int resultCount = 0;

    try {
      thisContact.setRequestItems(context.getRequest());
      thisContact.setModifiedBy(getUserId(context));
      db = this.getConnection(context);
      resultCount = thisContact.update(db);
      if (resultCount == -1) {
        processErrors(context, thisContact.getErrors());
        buildFormElements(context, db);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "External Contacts", "Update Contact");
    if (errorMessage == null) {
      if (resultCount == -1) {
        return ("ContactDetailsModifyOK");
      } else if (resultCount == 1) {
        return ("ContactDetailsUpdateOK");
      } else {
        context.getRequest().setAttribute("Error",
            "<b>This record could not be updated because someone else updated it first.</b><p>" +
            "You can hit the back button to review the changes that could not be committed, " +
            "but you must reload the record and make the changes again.");
        return ("UserError");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Prepares info when adding a new contact
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.1
   */
  public String executeCommandInsertContactForm(ActionContext context) {
    Exception errorMessage = null;

    addModuleBean(context, "Add Contact", "Add a new contact");

    Connection db = null;
    try {
      db = this.getConnection(context);
      buildFormElements(context, db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getSession().removeAttribute("ContactMessageListInfo");
      return ("ContactInsertFormOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Process the insert form
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.1
   */
  public String executeCommandInsertContact(ActionContext context) {
    Exception errorMessage = null;
    boolean recordInserted = false;

    Contact thisContact = (Contact)context.getFormBean();
    thisContact.setRequestItems(context.getRequest());
    thisContact.setEnteredBy(getUserId(context));
    thisContact.setModifiedBy(getUserId(context));
    thisContact.setOwner(getUserId(context));

    Connection db = null;
    try {
      db = this.getConnection(context);
      recordInserted = thisContact.insert(db);
      if (recordInserted) {
        thisContact = new Contact(db, "" + thisContact.getId());
        context.getRequest().setAttribute("ContactDetails", thisContact);
        addRecentItem(context, thisContact);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "External Contacts", "Add a new contact");
    if (errorMessage == null) {
      if (recordInserted) {
        return ("ContactDetailsOK");
      } else {
        processErrors(context, thisContact.getErrors());
        return (executeCommandInsertContactForm(context));
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
  public String executeCommandDeleteContact(ActionContext context) {
    Exception errorMessage = null;
    boolean recordDeleted = false;
    Contact thisContact = null;

    Connection db = null;
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, context.getRequest().getParameter("id"));
      recordDeleted = thisContact.delete(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "External Contacts", "Delete a contact");
    if (errorMessage == null) {
      if (recordDeleted) {
	context.getSession().removeAttribute("ContactMessageListInfo");
        return ("ContactDeleteOK");
      } else {
        processErrors(context, thisContact.getErrors());
        return (executeCommandListContacts(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Common method for populating shared form elements
   *
   *@param  context           Description of Parameter
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.3
   */
  protected void buildFormElements(ActionContext context, Connection db) throws SQLException {
    ContactTypeList contactTypeList = new ContactTypeList();
    contactTypeList.setShowPersonal(true);
    contactTypeList.buildList(db);
    contactTypeList.addItem(0, "--None--");
    
    context.getRequest().setAttribute("ContactTypeList", contactTypeList);

    LookupList phoneTypeList = new LookupList(db, "lookup_contactphone_types");
    context.getRequest().setAttribute("ContactPhoneTypeList", phoneTypeList);

    LookupList emailTypeList = new LookupList(db, "lookup_contactemail_types");
    context.getRequest().setAttribute("ContactEmailTypeList", emailTypeList);

    LookupList addressTypeList = new LookupList(db, "lookup_contactaddress_types");
    context.getRequest().setAttribute("ContactAddressTypeList", addressTypeList);
  }
  
  public String executeCommandModifyFields(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    Contact thisContact = null;

    try {
      String contactId = context.getRequest().getParameter("contactId");
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);

      String selectedCatId = (String)context.getRequest().getParameter("catId");
      String recordId = (String)context.getRequest().getParameter("recId");
      
      CustomFieldCategory thisCategory = new CustomFieldCategory(db, 
        Integer.parseInt(selectedCatId));
      thisCategory.setLinkModuleId(Constants.CONTACTS);
      thisCategory.setLinkItemId(thisContact.getId());
      thisCategory.setRecordId(Integer.parseInt(recordId));
      thisCategory.setIncludeEnabled(Constants.TRUE);
      thisCategory.setIncludeScheduled(Constants.TRUE);
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      context.getRequest().setAttribute("Category", thisCategory);
      
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "External Contacts", "Modify Custom Fields");
      return ("ModifyFieldsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandUpdateFields(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    Contact thisContact = null;
    int resultCount = 0;

    try {
      String contactId = context.getRequest().getParameter("contactId");
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
      
      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.CONTACTS);
      thisList.setIncludeEnabled(Constants.TRUE);
      thisList.setIncludeScheduled(Constants.TRUE);
      thisList.setBuildResources(false);
      thisList.buildList(db);
      context.getRequest().setAttribute("CategoryList", thisList);

      String selectedCatId = (String)context.getRequest().getParameter("catId");
      String recordId = (String)context.getRequest().getParameter("recId");
      
      context.getRequest().setAttribute("catId", selectedCatId);
      CustomFieldCategory thisCategory = new CustomFieldCategory(db, 
        Integer.parseInt(selectedCatId));
      thisCategory.setLinkModuleId(Constants.CONTACTS);
      thisCategory.setLinkItemId(thisContact.getId());
      thisCategory.setRecordId(Integer.parseInt(recordId));
      thisCategory.setIncludeEnabled(Constants.TRUE);
      thisCategory.setIncludeScheduled(Constants.TRUE);
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      thisCategory.setParameters(context);
      thisCategory.setModifiedBy(this.getUserId(context));
      resultCount = thisCategory.update(db);
      if (resultCount == -1) {
        if (System.getProperty("DEBUG") != null) System.out.println("Contacts-> ModifyField validation error");
      } else {
        thisCategory.buildResources(db);
      }
      context.getRequest().setAttribute("Category", thisCategory);
      
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        return ("ModifyFieldsOK");
      } else if (resultCount == 1) {
        return ("UpdateFieldsOK");
      } else {
        context.getRequest().setAttribute("Error",
            "<b>This record could not be updated because someone else updated it first.</b><p>" +
            "You can hit the back button to review the changes that could not be committed, " +
            "but you must reload the record and make the changes again.");
        return ("UserError");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandInsertFields(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    int resultCode = -1;
    Contact thisContact = null;

    try {
      String contactId = context.getRequest().getParameter("contactId");
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
      
      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.CONTACTS);
      thisList.setIncludeEnabled(Constants.TRUE);
      thisList.setIncludeScheduled(Constants.TRUE);
      thisList.setBuildResources(false);
      thisList.buildList(db);
      context.getRequest().setAttribute("CategoryList", thisList);

      String selectedCatId = (String)context.getRequest().getParameter("catId");
      context.getRequest().setAttribute("catId", selectedCatId);
      CustomFieldCategory thisCategory = new CustomFieldCategory(db, 
        Integer.parseInt(selectedCatId));
      thisCategory.setLinkModuleId(Constants.CONTACTS);
      thisCategory.setLinkItemId(thisContact.getId());
      thisCategory.setIncludeEnabled(Constants.TRUE);
      thisCategory.setIncludeScheduled(Constants.TRUE);
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      thisCategory.setParameters(context);
      thisCategory.setEnteredBy(this.getUserId(context));
      thisCategory.setModifiedBy(this.getUserId(context));
      resultCode = thisCategory.insert(db);
      if (resultCode == -1) {
        if (System.getProperty("DEBUG") != null) System.out.println("Contacts-> InsertField validation error");
      }
      context.getRequest().setAttribute("Category", thisCategory);
      
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCode == -1) {
        return ("AddFolderRecordOK");
      } else {
        return (this.executeCommandFields(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }

}

