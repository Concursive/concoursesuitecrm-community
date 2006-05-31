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
package org.aspcfs.modules.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.ProjectList;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.FilterList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Creates a Contacts List with 5 filters & subfilters . Can be used in two
 * variants : Single/Multiple<br>
 * Single and mutiple define if the selection can be single/multiple
 *
 * @author akhi_m
 * @version $Id: ContactsList.java,v 1.16.66.1 2004/01/23 15:33:26 ananth Exp
 *          $
 * @created September 5, 2002
 */
public final class ContactsList extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandContactList(ActionContext context) {
    Connection db = null;
    ContactList contactList = null;
    boolean listDone = false;
    String selectedIds = context.getRequest().getParameter("selectedIds");
    String listType = context.getRequest().getParameter("listType");
    String type = context.getRequest().getParameter("type");
    if (type != null && !"".equals(type) && !"null".equals(type)) {
      context.getRequest().setAttribute("type", type);
    }
    String displayType = context.getRequest().getParameter("displayType");
    HashMap selectedList = new HashMap();
    //initialize from page, if list...
    //put in session
    if (context.getRequest().getParameter("previousSelection") != null) {
      StringTokenizer st = new StringTokenizer(
          context.getRequest().getParameter("previousSelection"), "|");
      StringTokenizer st1 = new StringTokenizer(
          context.getRequest().getParameter("previousSelectionDisplay"), "|");
      while (st.hasMoreTokens()) {
        selectedList.put(new Integer(st.nextToken()), st1.nextToken());
      }
    } else {
      //get selected list from the session
      selectedList = (HashMap) context.getSession().getAttribute(
          "selectedContacts");
    }
    //Flush the selectedList if its a new selection
    if ("true".equals(
        ((String) context.getRequest().getParameter("flushtemplist")))) {
      if (context.getSession().getAttribute("finalContacts") != null && context.getRequest().getParameter(
          "previousSelection") == null) {
        selectedList = (HashMap) ((HashMap) context.getSession().getAttribute(
            "finalContacts")).clone();
      }
    }
    HashMap finalContactList = (HashMap) context.getSession().getAttribute(
        "finalContacts");
    try {
      db = this.getConnection(context);
      SystemStatus thisSystem = this.getSystemStatus(context);
      //Build Department List if empty
      if (context.getSession().getAttribute("DepartmentList") == null) {
        LookupList departmentList = new LookupList(db, "lookup_department");
        departmentList.addItem(
            -1, "--" + thisSystem.getLabel("contact.allDepartments") + "--"); //All Departments
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
        htmlSelect.addItem(
            -1, "--" + thisSystem.getLabel("contact.allProjects") + "--", 0); //All Projects
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
        while (context.getRequest().getParameter("hiddencontactid" + rowCount) != null)
        {
          int contactId = 0;
          String emailAddress = "";
          contactId = Integer.parseInt(
              context.getRequest().getParameter("hiddencontactid" + rowCount));

          if (context.getRequest().getParameter("checkcontact" + rowCount) != null)
          {
            if (context.getRequest().getParameter("contactemail" + rowCount) != null)
            {

              //we want this "emailAddress" variable to be the email only if we are not in Campaign Mgr.
              if (context.getRequest().getParameter("campaign") == null) {
                emailAddress = context.getRequest().getParameter(
                    "contactemail" + rowCount);
              } else if (!(((String) context.getRequest().getParameter(
                  "campaign")).equalsIgnoreCase("true"))) {
                emailAddress = context.getRequest().getParameter(
                    "contactemail" + rowCount);
              }
            }

            //If User does not have a emailAddress or if it is not a message use Name(LastFirst)
            if (emailAddress.equals("") || "single".equals(listType) || "name".equals(
                displayType)) {
              if (context.getRequest().getParameter("hiddenname" + rowCount) != null)
              {
                emailAddress = "P:" + context.getRequest().getParameter(
                    "hiddenname" + rowCount);
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
      if ("true".equals(
          (String) context.getRequest().getParameter("finalsubmit"))) {
        //Handle single selection case
        if ("single".equals(listType)) {
          rowCount = Integer.parseInt(
              context.getRequest().getParameter("rowcount"));
          String emailAddress = context.getRequest().getParameter(
              "hiddenname" + rowCount);
          int contactId = Integer.parseInt(
              context.getRequest().getParameter("hiddencontactid" + rowCount));
          selectedList.clear();
          selectedList.put(new Integer(contactId), emailAddress);
        }
        listDone = true;
        finalContactList = selectedList;
      }
      //Set ContactList Parameters and build the list
      setParameters(contactList, context, db);
      contactList.setLeadsOnly(Constants.FALSE);
      contactList.buildList(db);
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("ContactList", contactList);
    if ("true".equals((String) context.getRequest().getParameter("campaign"))) {
      context.getRequest().setAttribute(
          "Campaign", (String) context.getRequest().getParameter("campaign"));
    }

    context.getSession().setAttribute("selectedContacts", selectedList);
    if (listDone) {
      context.getSession().setAttribute("finalContacts", finalContactList);
    }
    return ("CFSContactListOK");
  }


  /**
   * Sets the parameters attribute of the ContactsList object
   *
   * @param contactList The new parameters value
   * @param context     The new parameters value
   */
  private void setParameters(ContactList contactList, ActionContext context, Connection db) throws Exception {
    SystemStatus thisSystem = this.getSystemStatus(context);
    // search for a particular contact
    String defaultFirstName = thisSystem.getLabel(
        "accounts.accounts_add.FirstName");
    String defaultLastName = thisSystem.getLabel(
        "accounts.accounts_add.LastName");

    String firstName = context.getRequest().getParameter("firstName");
    String lastName = context.getRequest().getParameter("lastName");

    if (firstName != null) {
      if (!defaultFirstName.equals(firstName) && !"".equals(firstName.trim())) {
        contactList.setFirstName("%" + firstName + "%");
      }
    }
    if (lastName != null) {
      if (!defaultLastName.equals(lastName) && !"".equals(lastName.trim())) {
        contactList.setLastName("%" + lastName + "%");
      }
    }
    if (context.getRequest().getParameter("reset") != null) {
      context.getSession().removeAttribute("ContactListInfo");
    }
    PagedListInfo contactListInfo = this.getPagedListInfo(
        context, "ContactListInfo");

    //filter for departments & project teams
    if (!contactListInfo.hasListFilters()) {
      contactListInfo.addFilter(1, "0");
    }
    String leads = context.getRequest().getParameter("leads");
    if (leads != null && !"".equals(leads.trim())) {
      context.getRequest().setAttribute("leads", leads);
    }
    String recipient = context.getRequest().getParameter("recipient");
    if (recipient != null && !"".equals(recipient)) {
      context.getRequest().setAttribute("recipient", recipient);
    }
    String secondFilter = context.getRequest().getParameter("listFilter1");
    String usersOnly = context.getRequest().getParameter("usersOnly");
    String hierarchy = context.getRequest().getParameter("hierarchy");
    String nonUsersOnly = context.getRequest().getParameter("nonUsersOnly");
    String orgId = context.getRequest().getParameter("orgId");
    String siteIdUser = context.getRequest().getParameter("siteIdUser");
    String siteIdContact = context.getRequest().getParameter("siteIdContact");
    String siteIdOrg = context.getRequest().getParameter("siteIdOrg");
    String mySiteOnly = context.getRequest().getParameter("mySiteOnly");
    String departmentId = context.getRequest().getParameter("departmentId");
    String siteIdString = context.getRequest().getParameter("siteId");
    String ticketIdString = context.getRequest().getParameter("ticketId");
    String includeAllSites = context.getRequest().getParameter("includeAllSites");
    String allowDuplicateRecipient = context.getRequest().getParameter("allowDuplicateRecipient");
    if (allowDuplicateRecipient != null && !"".equals(allowDuplicateRecipient.trim())) {
      context.getRequest().setAttribute("allowDuplicateRecipient", allowDuplicateRecipient);
    }
    String source = context.getRequest().getParameter("hiddensource");
    if (source != null && !"".equals(source.trim())) {
      context.getRequest().setAttribute("hiddensource", source);
    }
    String actionItemId = context.getRequest().getParameter("actionItemId");
    if (actionItemId != null && !"".equals(actionItemId.trim())) {
      context.getRequest().setAttribute("actionItemId", actionItemId);
    }
    int siteId = this.getUserSiteId(context);
    if (siteIdString != null && !"".equals(siteIdString.trim())) {
      siteId = Integer.parseInt(siteIdString);
    }
    if (siteIdUser != null && !"".equals(siteIdUser.trim()) && !"-1".equals(siteIdUser.trim())) {
      User user = this.getUser(context, Integer.parseInt(siteIdUser.trim()));
      siteId = user.getSiteId();
    }
    if (siteIdContact != null && !"".equals(siteIdContact.trim()) && !"-1".equals(siteIdContact.trim())) {
      Contact contact = new Contact(db, Integer.parseInt(siteIdContact.trim()));
      siteId = contact.getSiteId();
    }
    if (siteIdOrg != null && !"".equals(siteIdOrg.trim()) && !"-1".equals(siteIdOrg.trim())) {
      Organization org = new Organization(db, Integer.parseInt(siteIdOrg));
      siteId = org.getSiteId();
    }
    if ((ticketIdString != null) &&
        !"".equals(ticketIdString) &&
        !"null".equals(ticketIdString) &&
        !"-1".equals(ticketIdString)) {
      Ticket ticket = new Ticket(db, Integer.parseInt(ticketIdString));
      siteId = ticket.getOrgSiteId();
    }
    if (!isSiteAccessPermitted(context, String.valueOf(siteId))) {
      throw new SQLException("PermissionError");
    }
/*
    if (user.getSiteId() != -1 && user.getSiteId() != siteId) {
      throw new SQLException("PermissionError");
    }
*/
    //add filters
    FilterList filters = new FilterList(thisSystem, context.getRequest());
    context.getRequest().setAttribute("Filters", filters);

    //  set Filter for retrieving addresses depending on typeOfContact
    String firstFilter = filters.getFirstFilter(contactListInfo.getListView());

    if (firstFilter.equalsIgnoreCase("all")) {
      contactList.addIgnoreTypeId(Contact.EMPLOYEE_TYPE);
      contactList.addIgnoreTypeId(Contact.LEAD_TYPE);
      contactList.setAllContacts(
          true, this.getUserId(context), this.getUserRange(context));
      contactList.setSiteId(siteId);
    }
    if (firstFilter.equalsIgnoreCase("employees")) {
      contactList.setEmployeesOnly(Constants.TRUE);
      if (secondFilter != null && !"".equals(secondFilter)) {
        contactList.setDepartmentId(Integer.parseInt(secondFilter));
      }
      contactList.setSiteId(siteId);
    }
    if (firstFilter.equalsIgnoreCase("mycontacts")) {
      contactList.addIgnoreTypeId(Contact.EMPLOYEE_TYPE);
      contactList.addIgnoreTypeId(Contact.LEAD_TYPE);
      contactList.setOwner(this.getUserId(context));
      contactList.setSiteId(siteId);
      contactList.setPersonalId(this.getUserId(context));
    }
    if (firstFilter.equalsIgnoreCase("accountcontacts")) {
      contactList.setWithAccountsOnly(true);
      contactList.setSiteId(siteId);
    }
    if (firstFilter.equalsIgnoreCase("myprojects")) {
      contactList.setWithProjectsOnly(true);
      if (secondFilter != null && !"".equals(secondFilter)) {
        contactList.setProjectId(Integer.parseInt(secondFilter));
      }
      contactList.setSiteId(siteId);
    }
    contactListInfo.setListView(firstFilter);
    contactList.setPagedListInfo(contactListInfo);
    contactList.setCheckUserAccess(true);
    contactList.setBuildDetails(true);
    contactListInfo.setSearchCriteria(contactList, context);
    contactList.setBuildTypes(true);
    if ("true".equals(usersOnly)) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ContactsList-> Only ROLETYPE_REGULAR is valid");
      }
      contactList.setIncludeEnabledUsersOnly(true);
      if (hierarchy != null && !"".equals(hierarchy)) {
        contactList.setHierarchialUsers(hierarchy);
      }
      contactList.setUserRoleType(Constants.ROLETYPE_REGULAR);
    }
    if ("true".equals(nonUsersOnly)) {
      contactList.setIncludeNonUsersOnly(true);
    }
    if (orgId != null && !"".equals(orgId.trim())) {
      contactList.setOrgId(Integer.parseInt(orgId));
    }
    if ("true".equals(usersOnly)) {
      if (departmentId != null && !"".equals(departmentId.trim()) && !"null".equals(departmentId.trim()))
      {
        context.getRequest().setAttribute("departmentId", departmentId);
        contactList.setDepartmentId(Integer.parseInt(departmentId));
      }
    }

    // Setting filter criterea for campaign groups based on
    // siteId of the organization associated with the account contacts.
    if ("true".equals(context.getRequest().getParameter("campaign")) &&
        firstFilter.equalsIgnoreCase("accountcontacts")) {
      contactList.setSiteId(siteId);
    }
    if (mySiteOnly != null && "true".equals(mySiteOnly)) {
      contactList.setExclusiveToSite(true);
    }
    if (includeAllSites != null && "true".equals(includeAllSites.trim())) {
      if (siteId == -1) {
        contactList.setIncludeAllSites(true);
      }
    }
    context.getRequest().setAttribute("includeAllSites", includeAllSites);
    context.getRequest().setAttribute("siteId", String.valueOf(siteId));
    context.getRequest().setAttribute("mySiteOnly", mySiteOnly);
  }
}

