package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.utils.*;
import java.sql.*;
import java.util.ArrayList;
import com.darkhorseventures.webutils.*;
import java.util.*;
import com.zeroio.iteam.base.*;

/**
 *  Description of the Class
 *
 *@author     akhi_m
 *@created    September 5, 2002
 *@version    $Id$
 */
public final class ContactsList extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandContactList(ActionContext context) {

    PagedListInfo contactListInfo = this.getPagedListInfo(context, "ContactListInfo");
    Exception errorMessage = null;
    Connection db = null;
    ContactList contactList = null;
    contactListInfo.setEnableJavaScript(true);
    String firstFilter = "",secondFilter = "";
    String selectedIds = "",hiddenFieldId="",displayFieldId="",listType="";

    HashMap selectedList = (HashMap) context.getSession().getAttribute("selectedContacts");
    
    /*
     *  Flush the selectedList if its a new selection
     */
    if (context.getRequest().getParameter("flushtemplist") != null) {
      if (((String) context.getRequest().getParameter("flushtemplist")).equalsIgnoreCase("true")) {
        if (context.getSession().getAttribute("finalContacts") != null) {
          selectedList = (HashMap) ((HashMap) context.getSession().getAttribute("finalContacts")).clone();
        }
      }
    }
    
    if (context.getRequest().getParameter("selectedIds") != null) {
      selectedIds = context.getRequest().getParameter("selectedIds");
    }
    
    if(context.getRequest().getParameter("hiddenFieldId")!=null){
      hiddenFieldId = context.getRequest().getParameter("hiddenFieldId");
    }
    
    listType = context.getRequest().getParameter("listType");
    displayFieldId = context.getRequest().getParameter("displayFieldId");
    
    if (!contactListInfo.hasListFilters()) {
      //filter for departments & project teams
      contactListInfo.addFilter(1, "0");
    }

    HashMap finalContactList = (HashMap) context.getSession().getAttribute("finalContacts");
    boolean listDone = false;
    try {
      db = this.getConnection(context);
      //Build Department List if empty
      if (context.getSession().getAttribute("DepartmentList") == null) {
        LookupList departmentList = new LookupList(db, "lookup_department");
        departmentList.addItem(-1, "--All Departments--");
        context.getSession().setAttribute("DepartmentList", departmentList);
      }
      //Build Project List if empty
      if (context.getSession().getAttribute("ProjectListSelect") == null) {
        ProjectList projects = new ProjectList();
        projects.setUserRange(this.getUserRange(context));
        projects.setBuildAssignments(false);
        projects.setBuildIssues(false);
        projects.setGroupId(-1);
        projects.buildList(db);
        HtmlSelect htmlSelect = projects.getHtmlSelect();
        htmlSelect.addItem(-1, "--All Projects--", 0);
        context.getSession().setAttribute("ProjectListSelect", htmlSelect);
      }

      firstFilter = contactListInfo.getListView();
      contactList = new ContactList();

      /*
       *  Collect the selected entries in the contactList & store it in the session's HashMap i.e checkcontact
       *  checkcontact+rowCount is the checkbox name (value is  the contact_id)
       *  Single Email   : email as a hidden value contactemail+rowCount
       *  Multiple Emails: email as a value of selected entry from comboBox i.e contactemail_rowCount
       */
      int rowCount = 1;
      if (listType.equalsIgnoreCase("list")) {
        while (context.getRequest().getParameter("hiddencontactid" + rowCount) != null) {
          int contactId = 0;
          String emailAddress = "";
          contactId = Integer.parseInt(context.getRequest().getParameter("hiddencontactid" + rowCount));
          if (context.getRequest().getParameter("checkcontact" + rowCount) != null) {
            if (context.getRequest().getParameter("contactemail" + rowCount) != null) {
              emailAddress = context.getRequest().getParameter("contactemail" + rowCount);
            }

            //If User does not have a emailAddress replace with Name(LastFirst)
            if (emailAddress.equals("") || listType.equalsIgnoreCase("single")) {
              if (context.getRequest().getParameter("hiddenname" + rowCount) != null) {
                emailAddress = "P:" + context.getRequest().getParameter("hiddenname" + rowCount);
              }
            }

            if (selectedList.get(new Integer(contactId)) == null) {
              selectedList.put(new Integer(contactId), emailAddress);
            } else {
              selectedList.remove(new Integer(contactId));
              selectedList.put(new Integer(contactId), emailAddress);
            }
          } else {
            selectedList.remove(new Integer(contactId));
          }
          rowCount++;
        }
      }
      else{
        if(!selectedIds.equals("")){
          if(selectedList == null){
            selectedList = new HashMap();
          }
          selectedList.clear();
          selectedList.put(new Integer(Integer.parseInt(selectedIds)),"");
        }
      }

      if (context.getRequest().getParameter("finalsubmit") != null) {
        if (((String) context.getRequest().getParameter("finalsubmit")).equalsIgnoreCase("true")) {
          //If single selection then get count of row selected & fill HashMap with name & contactId
          if (listType.equalsIgnoreCase("single")) {
            rowCount = Integer.parseInt(context.getRequest().getParameter("rowcount"));
            String emailAddress = context.getRequest().getParameter("hiddenname" + rowCount);
            int contactId = Integer.parseInt(context.getRequest().getParameter("hiddencontactid" + rowCount));
            selectedList.clear();
            selectedList.put(new Integer(contactId), emailAddress);
          }
          listDone = true;
          finalContactList = (HashMap) selectedList.clone();
        }
      }

      if (context.getRequest().getParameter("listFilter1") != null) {
        secondFilter = context.getRequest().getParameter("listFilter1");
      }

      //  set Filter for retrieving addresses depending on typeOfContact
      if ((firstFilter == null || firstFilter.equals(""))) {
        firstFilter = "all";
      }
      if (firstFilter.equalsIgnoreCase("all")) {
        contactList.setPersonalId(getUserId(context));
        contactList.setOwnerIdRange(this.getUserRange(context));
      }
      if (firstFilter.equalsIgnoreCase("employees")) {
        contactList.setTypeId(Contact.EMPLOYEE_TYPE);
        if (!secondFilter.equals("")) {
          contactList.setDepartmentId(Integer.parseInt(secondFilter));
        }
      }
      if (firstFilter.equalsIgnoreCase("mycontacts")) {
        contactList.setPersonalId(getUserId(context));
        contactList.setOwner(getUserId(context));
      }
      if (firstFilter.equalsIgnoreCase("accountcontacts")) {
        contactList.setWithAccountsOnly(true);
      }
      if (firstFilter.equalsIgnoreCase("myprojects")) {
        contactList.setWithProjectsOnly(true);
        if (!secondFilter.equals("")) {
          contactList.setProjectId(Integer.parseInt(secondFilter));
        }
      }
      contactListInfo.setListView(firstFilter);
      contactList.setPagedListInfo(contactListInfo);
      contactList.setCheckUserAccess(true);
      contactList.setBuildDetails(true);
      contactList.buildList(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getRequest().setAttribute("HiddenFieldId", hiddenFieldId);
      context.getRequest().setAttribute("DisplayFieldId", displayFieldId);
      context.getRequest().setAttribute("ListType", listType);
      context.getRequest().setAttribute("ContactList", contactList);
      
        if(context.getRequest().getParameter("campaign")!=null){
                context.getRequest().setAttribute("Campaign", (String)context.getRequest().getParameter("campaign"));
        }
    
      context.getSession().setAttribute("selectedContacts", selectedList);
      if (listDone) {
        context.getSession().setAttribute("finalContacts", finalContactList);
      }
      return ("CFSContactListOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
}

