package org.aspcfs.modules.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.*;
import java.sql.*;
import java.util.ArrayList;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.base.FilterList;
import java.util.*;
import com.zeroio.iteam.base.*;

/**
 *  Creates a Contacts List with 5 filters & subfilters . Can be used in two
 *  variants : Single/Multiple<br>
 *  Single and mutiple define if the selection can be single/multiple
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

    Exception errorMessage = null;
    Connection db = null;
    ContactList contactList = null;
    boolean listDone = false;

    String selectedIds = context.getRequest().getParameter("selectedIds");
    String listType = context.getRequest().getParameter("listType");

    HashMap selectedList = new HashMap();
    //initialize from page, if list...
    //put in session

    if (context.getRequest().getParameter("previousSelection") != null) {
      int j = 0;
      StringTokenizer st = new StringTokenizer(context.getRequest().getParameter("previousSelection"), "|");
      while (st.hasMoreTokens()) {
        selectedList.put(new Integer(st.nextToken()), "");
        j++;
      }
    } else {
      //get selected list from the session
      selectedList = (HashMap) context.getSession().getAttribute("selectedContacts");
    }

    //Flush the selectedList if its a new selection
    if ("true".equals(((String) context.getRequest().getParameter("flushtemplist")))) {
      if (context.getSession().getAttribute("finalContacts") != null && context.getRequest().getParameter("previousSelection") == null) {
        selectedList = (HashMap) ((HashMap) context.getSession().getAttribute("finalContacts")).clone();
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

      contactList = new ContactList();

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
            if (emailAddress.equals("") || "single".equals(listType)) {
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
      } else {
        if (selectedIds != null && !"".equals(selectedIds)) {
          if (selectedList == null) {
            selectedList = new HashMap();
          }
          selectedList.clear();
          selectedList.put(new Integer(Integer.parseInt(selectedIds)), "");
        }
      }

      if ("true".equals((String) context.getRequest().getParameter("finalsubmit"))) {
        //Handle single selection case
        if ("single".equals(listType)) {
          rowCount = Integer.parseInt(context.getRequest().getParameter("rowcount"));
          String emailAddress = context.getRequest().getParameter("hiddenname" + rowCount);
          int contactId = Integer.parseInt(context.getRequest().getParameter("hiddencontactid" + rowCount));
          selectedList.clear();
          selectedList.put(new Integer(contactId), emailAddress);
        }
        listDone = true;
        finalContactList = selectedList;
      }

      //Set ContactList Parameters and build the list
      setParameters(contactList, context);
      contactList.buildList(db);
      
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getRequest().setAttribute("ContactList", contactList);
      if ("true".equals((String) context.getRequest().getParameter("campaign"))) {
        context.getRequest().setAttribute("Campaign", (String) context.getRequest().getParameter("campaign"));
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


  /**
   *  Sets the parameters attribute of the ContactsList object
   *
   *@param  contactList  The new parameters value
   *@param  context      The new parameters value
   */
  private void setParameters(ContactList contactList, ActionContext context) {

    if (context.getRequest().getParameter("reset") != null) {
      context.getSession().removeAttribute("ContactListInfo");
    }
    PagedListInfo contactListInfo = this.getPagedListInfo(context, "ContactListInfo");
    contactListInfo.setEnableJavaScript(true);

    //filter for departments & project teams
    if (!contactListInfo.hasListFilters()) {
      contactListInfo.addFilter(1, "0");
    }

    String secondFilter = context.getRequest().getParameter("listFilter1");
    String usersOnly = context.getRequest().getParameter("usersOnly");
    String nonUsersOnly = context.getRequest().getParameter("nonUsersOnly");

    //add filters
    FilterList filters = new FilterList(context.getRequest());
    context.getRequest().setAttribute("Filters", filters);
    
    //  set Filter for retrieving addresses depending on typeOfContact
    String firstFilter = filters.getFirstFilter(contactListInfo.getListView());

    if (firstFilter.equalsIgnoreCase("all")) {
      contactList.setPersonalId(getUserId(context));
      contactList.setOwnerIdRange(this.getUserRange(context));
    }
    if (firstFilter.equalsIgnoreCase("employees")) {
      contactList.setTypeId(Contact.EMPLOYEE_TYPE);
      if (secondFilter != null && !"".equals(secondFilter)) {
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
      if (secondFilter != null && !"".equals(secondFilter)) {
        contactList.setProjectId(Integer.parseInt(secondFilter));
      }
    }
    contactListInfo.setListView(firstFilter);
    contactList.setPagedListInfo(contactListInfo);
    contactList.setCheckUserAccess(true);
    contactList.setBuildDetails(true);
    contactList.setBuildTypes(true);
    if ("true".equals(usersOnly)) {
      contactList.setIncludeEnabledUsersOnly(true);
    }
    if ("true".equals(nonUsersOnly)) {
      contactList.setIncludeNonUsersOnly(true);
    }
  }
}


