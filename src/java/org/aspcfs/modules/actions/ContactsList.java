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
    contactListInfo.setEnableJavaScript(true);
    
    Exception errorMessage = null;
    Connection db = null;
    ContactList contactList = null;
    boolean listDone = false;
    
    String firstFilter = "", secondFilter = "";
    String selectedIds = "", hiddenFieldId="", displayFieldId="", listType="";

    listType = context.getRequest().getParameter("listType");
    displayFieldId = context.getRequest().getParameter("displayFieldId");
    
    //not sure if this is needed
    if (context.getRequest().getParameter("selectedIds") != null) {
      selectedIds = context.getRequest().getParameter("selectedIds");
    }
    
    if(context.getRequest().getParameter("hiddenFieldId")!=null){
      hiddenFieldId = context.getRequest().getParameter("hiddenFieldId");
    }
    
    //filter for departments & project teams
    if (!contactListInfo.hasListFilters()) {
      contactListInfo.addFilter(1, "0");
    }
    
    HashMap selectedList = new HashMap();
    //initialize from page, if list...
    //put in session
    
    if (context.getRequest().getParameter("previousSelection") != null) {
        int j = 0;
        
        StringTokenizer st = new StringTokenizer(context.getRequest().getParameter("previousSelection"), "|");

        while (st.hasMoreTokens()) {
          //selectedList.put( new Integer(st.nextToken()), "chris@darkhorseventures.com" );
          
          selectedList.put( new Integer(st.nextToken()), "" );
          
          j++;
        }
    }  else {
        //get selected list from the session
        selectedList = (HashMap) context.getSession().getAttribute("selectedContacts");
        
    }
    
    //DEBUG iterate thru session object
    /**
    if (selectedList != null && selectedList.size() > 0) {
            Iterator keyIterator = selectedList.keySet().iterator();
            while(keyIterator.hasNext()) {
                    Integer tempKey = (Integer)keyIterator.next();
                    System.out.println("KEY: " + tempKey + ", " + selectedList.get(tempKey));
            }
    }
    */
    //end DEBUG
    
    //what is "finalContacts"?
    
    //Flush the selectedList if its a new selection
    if (context.getRequest().getParameter("flushtemplist") != null) {
      if (((String) context.getRequest().getParameter("flushtemplist")).equalsIgnoreCase("true")) {
        if (context.getSession().getAttribute("finalContacts") != null && context.getRequest().getParameter("previousSelection") == null ) {
          selectedList = (HashMap) ((HashMap) context.getSession().getAttribute("finalContacts")).clone();
        }
      }
    }
    
    HashMap finalContactList = (HashMap) context.getSession().getAttribute("finalContacts");
    
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
      
      //we want only contacts with valid user accounts if we are not in campaign groups 
      if (context.getRequest().getParameter("campaign") != null && context.getRequest().getParameter("campaign").length() > 0) {
          if (!(((String) context.getRequest().getParameter("campaign")).equalsIgnoreCase("true"))) {
            contactList.setIncludeEnabledUsersOnly(true);
          } 
      } else if (context.getRequest().getParameter("allcontacts") != null && context.getRequest().getParameter("allcontacts").length() > 0) {
          if (!(((String) context.getRequest().getParameter("allcontacts")).equalsIgnoreCase("true"))) {
            contactList.setIncludeEnabledUsersOnly(true);
          } 
      } else {
        contactList.setIncludeEnabledUsersOnly(true);
      }

      /*
       *  Collect the selected entries in the contactList & store it in the session's HashMap i.e checkcontact
       *  checkcontact+rowCount is the checkbox name (value is  the contact_id)
       *  Single Email   : email as a hidden value contactemail+rowCount
       *  Multiple Emails: email as a value of selected entry from comboBox i.e contactemail_rowCount
       */
      int rowCount = 1;
      
      //list
      if (listType.equalsIgnoreCase("list")) {
        while (context.getRequest().getParameter("hiddencontactid" + rowCount) != null) {
          int contactId = 0;
          String emailAddress = "";
          contactId = Integer.parseInt(context.getRequest().getParameter("hiddencontactid" + rowCount));
          
          if (context.getRequest().getParameter("checkcontact" + rowCount) != null) {
            if (context.getRequest().getParameter("contactemail" + rowCount) != null) {
                    
              //we want this "emailAddress" variable to be the email only if we are not in Campaign Mgr. 
              if (context.getRequest().getParameter("campaign") == null) {
                      emailAddress = context.getRequest().getParameter("contactemail" + rowCount);
              } else if (!(((String) context.getRequest().getParameter("campaign")).equalsIgnoreCase("true"))) {
                      emailAddress = context.getRequest().getParameter("contactemail" + rowCount);
              }
              
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
      
      if (context.getRequest().getParameter("campaign") != null) {
              if (((String) context.getRequest().getParameter("campaign")).equalsIgnoreCase("true")) {
                      context.getRequest().setAttribute("Campaign", (String)context.getRequest().getParameter("campaign"));
              }
      }
      
      if (context.getRequest().getParameter("allcontacts") != null) {
              if (((String) context.getRequest().getParameter("allcontacts")).equalsIgnoreCase("true")) {
                      context.getRequest().setAttribute("AllContacts", (String)context.getRequest().getParameter("allcontacts"));
              }
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

