package org.aspcfs.modules.actionlist.actions;

import java.sql.*;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.zeroio.webutils.*;
import com.isavvix.tools.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.*;
import org.aspcfs.controller.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.actionlist.base.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.communications.base.*;

/**
 *  Represents Action Contacts on a list and possible actions
 *  that can be performed on each action contact
 *
 *@author     akhi_m
 *@created    April 23, 2003
 *@version    $id:exp$
 */
public final class MyActionContacts extends CFSModule {

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
   *  Lists action contacts on a list based on the filter.
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    String actionId = context.getRequest().getParameter("actionId");
    addModuleBean(context, "My Action Lists", "Action Contacts");
    if ("true".equals(context.getRequest().getParameter("reset"))) {
      context.getSession().removeAttribute("ContactActionListInfo");
    }
    PagedListInfo contactActionListInfo = this.getPagedListInfo(context, "ContactActionListInfo");
    contactActionListInfo.setLink("MyActionContacts.do?command=List&actionId=" + actionId);
    Connection db = null;
    try {
      db = getConnection(context);
      ActionContactsList thisList = new ActionContactsList();
      thisList.setPagedListInfo(contactActionListInfo);
      thisList.setActionId(Integer.parseInt(actionId));
      thisList.setBuildHistory(true);
      if (!"all".equals(contactActionListInfo.getListView())) {
        if ("complete".equals(contactActionListInfo.getListView())) {
          thisList.setCompleteOnly(true);
        } else {
          thisList.setInProgressOnly(true);
        }
      }
      thisList.buildList(db);
      context.getRequest().setAttribute("ActionContacts", thisList);

      ActionList actionList = new ActionList(db, Integer.parseInt(actionId));
      context.getRequest().setAttribute("ActionList", actionList);
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
   *  Add new contacts to the list.
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandPrepare(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-add"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    String actionId = (String) context.getRequest().getParameter("actionId");
    ActionList actionList = null;
    addModuleBean(context, "My Action Lists", "Action Contacts");
    Connection db = null;
    try {
      db = getConnection(context);

      SearchFieldList searchFieldList = new SearchFieldList();
      SearchOperatorList stringOperatorList = new SearchOperatorList();
      SearchOperatorList dateOperatorList = new SearchOperatorList();
      SearchOperatorList numberOperatorList = new SearchOperatorList();

      HtmlSelect contactSource = new HtmlSelect();
      contactSource.addItem(SearchCriteriaList.SOURCE_MY_CONTACTS, "My General Contacts");
      contactSource.addItem(SearchCriteriaList.SOURCE_ALL_CONTACTS, "All General Contacts");
      contactSource.addItem(SearchCriteriaList.SOURCE_ALL_ACCOUNTS, "All Account Contacts");
      context.getRequest().setAttribute("ContactSource", contactSource);

      ContactTypeList typeList = new ContactTypeList(db);
      LookupList ctl = typeList.getLookupList("typeId", 0);
      ctl.setJsEvent("onChange = \"javascript:setText(document.searchForm.typeId)\"");
      context.getRequest().setAttribute("ContactTypeList", ctl);

      LookupList accountTypeList = new LookupList(db, "lookup_account_types");
      accountTypeList.setSelectSize(1);
      context.getRequest().setAttribute("AccountTypeList", accountTypeList);

      searchFieldList.buildFieldList(db);
      context.getRequest().setAttribute("SearchFieldList", searchFieldList);

      stringOperatorList.buildOperatorList(db, 0);
      context.getRequest().setAttribute("StringOperatorList", stringOperatorList);

      dateOperatorList.buildOperatorList(db, 1);
      context.getRequest().setAttribute("DateOperatorList", dateOperatorList);

      numberOperatorList.buildOperatorList(db, 2);
      context.getRequest().setAttribute("NumberOperatorList", numberOperatorList);

      if (actionId != null && !"-1".equals("actionId")) {
        actionList = new ActionList(db, Integer.parseInt(actionId));
        context.getRequest().setAttribute("ActionList", actionList);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (context.getRequest().getParameter("actionSource") != null) {
        return "PrepareContactsOK";
      }
      return "PrepareOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Save action contacts to the list based on criteria
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-edit"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    SearchCriteriaList thisSCL = null;
    String criteria = context.getRequest().getParameter("searchCriteriaText");
    String actionId = context.getRequest().getParameter("actionId");
    addModuleBean(context, "My Action Lists", "Action Contacts");
    Connection db = null;
    try {
      //The criteria that makes up the contact list query
      thisSCL = new SearchCriteriaList(criteria);
      thisSCL.setGroupName("Action List");
      thisSCL.setEnteredBy(getUserId(context));
      thisSCL.setModifiedBy(getUserId(context));
      thisSCL.setOwner(getUserId(context));
      db = this.getConnection(context);
      thisSCL.buildRelatedResources(db);

      //Build the contactList
      ContactList contacts = new ContactList();
      contacts.setScl(thisSCL, this.getUserId(context), this.getUserRange(context));
      contacts.setBuildDetails(true);
      contacts.setBuildTypes(false);
      contacts.buildList(db);

      //save action contacts
      ActionContactsList thisList = new ActionContactsList();
      thisList.setActionId(Integer.parseInt(actionId));
      thisList.setEnteredBy(this.getUserId(context));
      thisList.insert(db, contacts);
      context.getRequest().setAttribute("ActionContacts", thisList);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "InsertOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Update contacts on action list
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandUpdate(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-edit"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "My Action Lists", "");
    String selectedContacts = context.getRequest().getParameter("selectedContacts");
    String actionId = context.getRequest().getParameter("actionId");
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = getConnection(context);
      ActionContactsList thisList = new ActionContactsList();
      thisList.setActionId(Integer.parseInt(actionId));
      thisList.setEnteredBy(this.getUserId(context));
      thisList.update(db, selectedContacts);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return executeCommandList(context);
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *   Modify contacts on action list
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-edit"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "My Action Lists", "");
    String actionId = context.getRequest().getParameter("actionId");
    boolean buildContacts = true;
    if ("false".equals(context.getRequest().getParameter("doBuild"))) {
      buildContacts = false;
    }
    HashMap selectedList = null;
    Exception errorMessage = null;
    Connection db = null;
    try {
      if (buildContacts) {
        db = getConnection(context);
        selectedList = new HashMap();
        ActionContactsList thisList = new ActionContactsList();
        thisList.setActionId(Integer.parseInt(actionId));
        thisList.buildList(db);

        Iterator i = thisList.iterator();
        while (i.hasNext()) {
          ActionContact thisContact = (ActionContact) i.next();
          selectedList.put(new Integer(thisContact.getLinkItemId()), "");
        }
        context.getSession().setAttribute("selectedContacts", selectedList);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (buildContacts) {
        return "ModifyOK";
      }
      return "ModifyNoBuildOK";
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
    if (!(hasPermission(context, "myhomepage-action-lists-edit"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "My Action Lists", "");
    return "PrepareOK";
  }


  /**
   *  View history of actions performed on a action contact
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandViewHistory(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-view"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "My Action Lists", "");
    String itemId = context.getRequest().getParameter("itemId");
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = getConnection(context);
      ActionItemLogList thisList = new ActionItemLogList();
      thisList.setItemId(Integer.parseInt(itemId));
      thisList.setBuildDetails(true);
      thisList.buildList(db);
      context.getRequest().setAttribute("History", thisList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "ViewHistoryOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Change the status of an action contact
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandProcessImage(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-edit"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    int count = 0;

    String id = (String) context.getRequest().getParameter("id");

    //Start the download
    try {
      StringTokenizer st = new StringTokenizer(id, "|");
      String fileName = st.nextToken();
      String imageType = st.nextToken();
      int contactId = Integer.parseInt(st.nextToken());
      int status = Integer.parseInt(st.nextToken());
      db = this.getConnection(context);
      ActionContact thisContact = new ActionContact(db, contactId);

      if (status == ActionContact.DONE) {
        thisContact.updateStatus(db, true);
      } else {
        thisContact.updateStatus(db, false);
      }
      this.freeConnection(context, db);
      if (count != -1) {
        String filePath = context.getServletContext().getRealPath("/") + "images" + fs + fileName;
        FileDownload fileDownload = new FileDownload();
        fileDownload.setFullPath(filePath);
        fileDownload.setDisplayName(fileName);
        if (fileDownload.fileExists()) {
          fileDownload.sendFile(context, "image/" + imageType);
        } else {
          System.err.println("Image-> Trying to send a file that does not exist");
        }
      } else {
        processErrors(context, thisContact.getErrors());
      }
    } catch (java.net.SocketException se) {
      //User either canceled the download or lost connection
      System.out.println("MyActionContacts -> ProcessImage : Download canceled or connection lost");
    } catch (Exception e) {
      errorMessage = e;
      this.freeConnection(context, db);
      System.out.println(e.toString());
    }
    return ("-none-");
  }


  /**
   *  Prepare form for sending a message to an action contact
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandPrepareMessage(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-edit"))) {
      return ("PermissionError");
    }
    String msgId = context.getRequest().getParameter("messageId");
    String contactId = context.getRequest().getParameter("contactId");
    Message thisMessage = null;
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = getConnection(context);
      Contact recipient = new Contact(db, Integer.parseInt(contactId));

      if (msgId != null && !"0".equals(msgId)) {
        thisMessage = new Message(db, Integer.parseInt(msgId));
        context.getRequest().setAttribute("Message", thisMessage);
      }
      if ("".equals(recipient.getEmailAddress("Business"))) {
        context.getRequest().setAttribute("actionError", "This contact does not have a valid email address associated");
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return this.getReturn(context, "PrepareMessage");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Sends the entered message to the action contact.
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSendMessage(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-edit"))) {
      return ("PermissionError");
    }
    String msgId = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");
    String actionId = context.getRequest().getParameter("actionId");
    String actionListId = context.getRequest().getParameter("actionListId");
    boolean messageSent = false;
    Message thisMessage = (Message) context.getFormBean();
    Exception errorMessage = null;
    Connection db = null;
    
    InstantCampaign actionCampaign = new InstantCampaign();
    try {
      db = getConnection(context);
      //build the Action List
      ActionList actionList = new ActionList(db, Integer.parseInt(actionListId));
      
      //check if message if valid
      if (actionCampaign.isValid(thisMessage)) {
        //insert the message if it is not inserted yet
        if (msgId != null && !"".equals(msgId)) {
          thisMessage.setModifiedBy(this.getUserId(context));
          thisMessage.update(db);
        } else {
          SystemStatus systemStatus = this.getSystemStatus(context);
          LookupList list = systemStatus.getLookupList(db, "lookup_access_types");
          thisMessage.setAccessType(list.getIdFromValue("Public"));
          thisMessage.setDisableNameValidation(true);
          thisMessage.setModifiedBy(this.getUserId(context));
          thisMessage.setEnteredBy(this.getUserId(context));
        }
        //create an instant campaign and activate it
        actionCampaign.setName(actionList.getDescription());
        actionCampaign.setEnteredBy(this.getUserId(context));
        actionCampaign.setModifiedBy(this.getUserId(context));
        actionCampaign.addRecipient(db, Integer.parseInt(contactId));
        actionCampaign.setMessage(thisMessage);
        boolean activated = actionCampaign.activate(db);

        //log the campaign in history
        if (activated) {
          ActionItemLog thisLog = new ActionItemLog();
          thisLog.setEnteredBy(this.getUserId(context));
          thisLog.setModifiedBy(this.getUserId(context));
          thisLog.setItemId(Integer.parseInt(actionId));
          thisLog.setLinkItemId(actionCampaign.getId());
          thisLog.setType(Constants.CAMPAIGN_OBJECT);
          thisLog.insert(db);

          messageSent = true;

          //build the contact for confirming message
          context.getRequest().setAttribute("Recipient", new Contact(db, Integer.parseInt(contactId)));
        }
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (messageSent) {
        return this.getReturn(context, "SendMessage");
      }
      processErrors(context, thisMessage.getErrors());
      return this.getReturn(context, "PrepareMessage");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
}

