package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;
import com.zeroio.iteam.base.*;

/**
 *  Action methods for managing users
 *
 *@author     mrajkowski
 *@created    September 17, 2001
 *@version    $Id$
 */
public final class Users extends CFSModule {

	/**
	 *  Description of the Method
	 *
	 *@param  context  Description of Parameter
	 *@return          Description of the Returned Value
	 *@since
	 */
	public String executeCommandDefault(ActionContext context) {
		return executeCommandListUsers(context);
	}


	/**
	 *  Lists the users in the system that have a corresponding contact record
	 *
	 *@param  context  Description of Parameter
	 *@return          Description of the Returned Value
	 *@since           1.6
	 */
	public String executeCommandListUsers(ActionContext context) {

		if (!(hasPermission(context, "admin-users-view"))) {
			return ("PermissionError");
		}

		Exception errorMessage = null;

		PagedListInfo listInfo = getPagedListInfo(context, "UserListInfo");
		listInfo.setLink("/Users.do?command=ListUsers");

		Connection db = null;
		UserList list = new UserList();

		try {
			db = getConnection(context);

			if ("disabled".equals(listInfo.getListView())) {
				list.setEnabled(UserList.FALSE);
			}
			else if ("aliases".equals(listInfo.getListView())) {
				list.setIncludeAliases(true);
				list.setEnabled(UserList.TRUE);
			}
			else {
				list.setEnabled(UserList.TRUE);
			}
			list.setPagedListInfo(listInfo);
			list.setBuildContact(false);
			list.setBuildHierarchy(false);
			list.setBuildPermissions(false);
			list.buildList(db);

		}
		catch (Exception e) {
			errorMessage = e;
		}
		finally {
			this.freeConnection(context, db);
		}

		addModuleBean(context, "Users", "User List");
		if (errorMessage == null) {
			context.getRequest().setAttribute("UserList", list);
			return ("ListUsersOK");
		}
		else {
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
	public String executeCommandReassign(ActionContext context) {

		if (!(hasPermission(context, "admin-reassign-view"))) {
			return ("PermissionError");
		}

		Exception errorMessage = null;
		Connection db = null;

		User thisRec = ((UserBean) context.getSession().getAttribute("User")).getUserRecord();
    int userId = -1;
    User sourceUser = null;
    
    OrganizationList sourceAccounts = null;
    ContactList sourceContacts = null;
    UserList sourceUsers = null;
    TicketList sourceOpenTickets = null;
    RevenueList sourceRevenue = null;
    AssignmentList sourceAssignments = null;
    OpportunityList sourceOpportunities = null;
    OpportunityList sourceOpenOpportunities = null;
    
    //this is how we get the multiple-level heirarchy...recursive function.
		UserList shortChildList = thisRec.getShortChildList();
		UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
		userList.setMyId(getUserId(context));
		userList.setMyValue(thisRec.getContact().getNameLastFirst());
		userList.setIncludeMe(true);
    userList.setEmptyHtmlSelectRecord("None Selected");
		
    if (context.getRequest().getParameter("userId") != null) {
      userId = Integer.parseInt(context.getRequest().getParameter("userId"));
    }

    if (userId > -1) {    
      if (userId == thisRec.getId()) {
        sourceUser = thisRec;
      } else {
        sourceUser = (User)thisRec.getChild(userId);
      }
    }
    
    if (sourceUser != null) {
      try {
        db = getConnection(context);
        
        sourceAccounts = new OrganizationList();
        sourceAccounts.setOwnerId(userId);
        sourceAccounts.buildList(db);
        context.getRequest().setAttribute("SourceAccounts", sourceAccounts);
        
        sourceContacts = new ContactList();
        sourceContacts.setOwner(userId);
        sourceContacts.buildList(db);
        context.getRequest().setAttribute("SourceContacts", sourceContacts);
        
        sourceUsers = new UserList();
        sourceUsers.setManagerId(userId);
        sourceUsers.buildList(db);
        context.getRequest().setAttribute("SourceUsers", sourceUsers);
        
        sourceOpenTickets = new TicketList();
        sourceOpenTickets.setAssignedTo(userId);
        sourceOpenTickets.setOnlyOpen(true);
        sourceOpenTickets.buildList(db);
        context.getRequest().setAttribute("SourceOpenTickets", sourceOpenTickets);
        
        sourceRevenue = new RevenueList();
        sourceRevenue.setOwner(userId);
        sourceRevenue.buildList(db);
        context.getRequest().setAttribute("SourceRevenue", sourceRevenue);
        
        sourceAssignments = new AssignmentList();
        sourceAssignments.setAssignmentsForUser(userId);
        sourceAssignments.setIncompleteOnly(true);
        sourceAssignments.buildList(db);
        context.getRequest().setAttribute("SourceAssignments", sourceAssignments);
        
        sourceOpportunities = new OpportunityList();
        sourceOpportunities.setOwner(userId);
        sourceOpportunities.buildList(db);
        context.getRequest().setAttribute("SourceOpportunities", sourceOpportunities);
        
        sourceOpenOpportunities = new OpportunityList();
        sourceOpenOpportunities.setOwner(userId);
        sourceOpenOpportunities.setQueryOpenOnly(true);
        sourceOpenOpportunities.buildList(db);
        context.getRequest().setAttribute("SourceOpenOpportunities", sourceOpenOpportunities);
      } catch (Exception e) {
        errorMessage = e;
      } finally {
        this.freeConnection(context, db);
      }
    }
    
    context.getRequest().setAttribute("SourceUser", sourceUser);
    context.getRequest().setAttribute("UserList", userList);
    context.getRequest().setAttribute("UserSelectList", userList.clone());
		addModuleBean(context, "Reassign", "Bulk Reassign");
		if (errorMessage == null) {
			return ("ReassignOK");
		}
		else {
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
	public String executeCommandDoReassign(ActionContext context) {

		if (!(hasPermission(context, "admin-reassign-edit"))) {
			return ("PermissionError");
		}
    
    User thisRec = ((UserBean) context.getSession().getAttribute("User")).getUserRecord();
    int userId = -1;
    User sourceUser = null;
		Exception errorMessage = null;
		Connection db = null;
    
        
    if (context.getRequest().getParameter("userId") != null) {
      userId = Integer.parseInt(context.getRequest().getParameter("userId"));
    }
    
    if (userId > -1) {
      if (userId == thisRec.getId()) {
        sourceUser = thisRec;
      } else {
        sourceUser = (User)thisRec.getChild(userId);
      }
    }
    
    if (sourceUser == null) {
      return ("PermissionError");
    }  
    
    OrganizationList sourceAccounts = null;
    int targetIdAccounts = -1;
    
    ContactList sourceContacts = null;
    int targetIdContacts = -1;
    
    OpportunityList sourceOpenOpps = null;
    int targetIdOpenOpps = -1;
    
    OpportunityList sourceOpps = null;
    int targetIdOpps = -1;    
    
    RevenueList sourceRevenue = null;
    int targetIdRevenue = -1;
    
    TicketList sourceOpenTickets = null;
    int targetIdOpenTickets = -1;  
    
    UserList sourceUsers = null;
    int targetIdUsers = -1;   
    
    AssignmentList sourceAssignments = null;
    int targetIdAssignments = -1;
    
    if (context.getRequest().getParameter("ownerToAccounts") != null) {
      targetIdAccounts = Integer.parseInt(context.getRequest().getParameter("ownerToAccounts"));
    }
    if (context.getRequest().getParameter("ownerToContacts") != null) {
      targetIdContacts = Integer.parseInt(context.getRequest().getParameter("ownerToContacts"));
    }
    if (context.getRequest().getParameter("ownerToOpenOpps") != null) {
      targetIdOpenOpps = Integer.parseInt(context.getRequest().getParameter("ownerToOpenOpps"));
    }    
    if (context.getRequest().getParameter("ownerToOpenClosedOpps") != null) {
      targetIdOpps = Integer.parseInt(context.getRequest().getParameter("ownerToOpenClosedOpps"));
    }     
    if (context.getRequest().getParameter("ownerToRevenue") != null) {
      targetIdRevenue = Integer.parseInt(context.getRequest().getParameter("ownerToRevenue"));
    }     
    if (context.getRequest().getParameter("ownerToOpenTickets") != null) {
      targetIdOpenTickets = Integer.parseInt(context.getRequest().getParameter("ownerToOpenTickets"));
    }    
    if (context.getRequest().getParameter("ownerToUsers") != null) {
      targetIdUsers = Integer.parseInt(context.getRequest().getParameter("ownerToUsers"));
    }    
    
    if (context.getRequest().getParameter("ownerToActivities") != null) {
      targetIdAssignments = Integer.parseInt(context.getRequest().getParameter("ownerToActivities"));
    }      
    
    try {
        db = getConnection(context);
        
        if (targetIdAccounts > -1) {
          sourceAccounts = new OrganizationList();
          sourceAccounts.setOwnerId(userId);
          sourceAccounts.buildList(db);
          sourceAccounts.reassignElements(db, targetIdAccounts);
        }
        
        if (targetIdContacts > -1) {
          sourceContacts = new ContactList();
          sourceContacts.setOwner(userId);
          sourceContacts.buildList(db);
          sourceContacts.reassignElements(db, targetIdContacts);
        }
        
        if (targetIdOpenOpps > -1) {
          sourceOpenOpps = new OpportunityList();
          sourceOpenOpps.setOwner(userId);
          sourceOpenOpps.setQueryOpenOnly(true);
          sourceOpenOpps.buildList(db);
          sourceOpenOpps.reassignElements(db, targetIdOpenOpps);
          invalidateUserInMemory(targetIdOpenOpps, context);
        }        
        
        if (targetIdOpps > -1) {
          sourceOpps = new OpportunityList();
          sourceOpps.setOwner(userId);
          sourceOpps.buildList(db);
          sourceOpps.reassignElements(db, targetIdOpps);
          invalidateUserInMemory(targetIdOpps, context);
        }
        
        if (targetIdRevenue > -1) {
          sourceRevenue = new RevenueList();
          sourceRevenue.setOwner(userId);
          sourceRevenue.buildList(db);
          sourceRevenue.reassignElements(db, targetIdRevenue);
          invalidateUserInMemory(targetIdRevenue, context);
        }        
        
        if (targetIdOpenTickets > -1) {
          sourceOpenTickets = new TicketList();
          sourceOpenTickets.setAssignedTo(userId);
          sourceOpenTickets.setOnlyOpen(true);
          sourceOpenTickets.buildList(db);
          sourceOpenTickets.reassignElements(db, targetIdOpenTickets);
        }
        
        if (targetIdUsers > -1) {
          sourceUsers = new UserList();
          sourceUsers.setManagerId(userId);
          sourceUsers.buildList(db);          
          sourceUsers.reassignElements(db, targetIdUsers);
          thisRec.setBuildHierarchy(true);
          thisRec.buildResources(db);
        }        
        
        if (targetIdAssignments > -1) {
          sourceAssignments = new AssignmentList();
          sourceAssignments.setAssignmentsForUser(userId);
          sourceAssignments.setIncompleteOnly(true);
          sourceAssignments.buildList(db);    
          sourceAssignments.reassignElements(db, targetIdAssignments);
        }
        
    } catch (Exception e) {
        errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
      
    if (errorMessage == null) {

      if (targetIdOpps > -1 || targetIdOpenOpps > -1 || targetIdRevenue > -1) {
        invalidateUserData(context, getUserId(context));
        invalidateUserInMemory(sourceUser.getId(), context);
      }
        
      return ("DoReassignOK");
		}
		else {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		}

	}



	/**
	 *  Generates the User for display
	 *
	 *@param  context  Description of Parameter
	 *@return          Description of the Returned Value
	 *@since           1.6
	 */
	public String executeCommandUserDetails(ActionContext context) {

		if (!(hasPermission(context, "admin-users-view"))) {
			return ("PermissionError");
		}

		Exception errorMessage = null;

		String id = context.getRequest().getParameter("id");
		String action = context.getRequest().getParameter("action");

		Connection db = null;
		User thisUser = new User();

		try {
			db = this.getConnection(context);
			thisUser.setBuildContact(true);
			thisUser.buildRecord(db, Integer.parseInt(id));
			context.getRequest().setAttribute("UserRecord", thisUser);
			addRecentItem(context, thisUser);

			if (action != null && action.equals("modify")) {
				//RoleList roleList = new RoleList(db);
				//context.getRequest().setAttribute("RoleList", roleList);
			}
		}
		catch (Exception e) {
			errorMessage = e;
		}
		finally {
			this.freeConnection(context, db);
		}

		if (errorMessage == null) {
			if (action != null && action.equals("modify")) {
				addModuleBean(context, "Users", "Modify User Details");
				return ("UserDetailsModifyOK");
			}
			else {
				addModuleBean(context, "Users", "View User Details");
				return ("UserDetailsOK");
			}
		}
		else {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		}

	}


	/**
	 *  Generates the form data for inserting a new user
	 *
	 *@param  context  Description of Parameter
	 *@return          Description of the Returned Value
	 *@since           1.6
	 */
	public String executeCommandInsertUserForm(ActionContext context) {

		if (!(hasPermission(context, "admin-users-add"))) {
			return ("PermissionError");
		}

		Exception errorMessage = null;
		addModuleBean(context, "Add User", "Add New User");

		Connection db = null;
		try {
			String typeId = context.getRequest().getParameter("typeId");
			if (typeId == null || typeId.equals("")) {
				typeId = "" + Contact.EMPLOYEE_TYPE;
			}
			String contactId = context.getRequest().getParameter("contactId");

			db = this.getConnection(context);
			ContactTypeList contactTypeList = new ContactTypeList();
			contactTypeList.setShowEmployees(true);
			contactTypeList.buildList(db);
			contactTypeList.setDefaultKey(typeId);
      contactTypeList.setJsEvent("onChange=\"javascript:updateContactList();\"");
			context.getRequest().setAttribute("ContactTypeList", contactTypeList);

			ContactList contactList = new ContactList();
      contactList.setEmptyHtmlSelectRecord("-- Please Select --");
      contactList.setIncludeNonUsersOnly(true);
      contactList.setPersonalId(getUserId(context));
      contactList.setTypeId(Integer.parseInt(typeId));
			contactList.buildList(db);
			context.getRequest().setAttribute("ContactList", contactList);
      
			RoleList roleList = new RoleList();
			roleList.setEmptyHtmlSelectRecord("-- Please Select --");
			roleList.buildList(db);
			context.getRequest().setAttribute("RoleList", roleList);    
      
			UserList userList = new UserList();
			userList.setEmptyHtmlSelectRecord("-- None --");
			userList.setBuildContact(true);
			userList.buildList(db);
			context.getRequest().setAttribute("UserList", userList);
      
		}
		catch (Exception e) {
			errorMessage = e;
		}
		finally {
			this.freeConnection(context, db);
		}

		if (errorMessage == null) {
			return ("UserInsertFormOK");
		}
		else {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		}
	}

  public String executeCommandContactJSList(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    try {
      String typeId = context.getRequest().getParameter("typeId");
			if (typeId == null || typeId.equals("")) {
				typeId = "" + Contact.EMPLOYEE_TYPE;
			}      
      db = this.getConnection(context);
      
			ContactList contactList = new ContactList();
			contactList.setPersonalId(getUserId(context));
      contactList.setTypeId(Integer.parseInt(typeId));
      contactList.setIncludeNonUsersOnly(true);
			contactList.buildList(db);
			context.getRequest().setAttribute("ContactList", contactList);
      
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    return ("ContactJSListOK");
  }

	/**
	 *  Adds the user to the database
	 *
	 *@param  context  Description of Parameter
	 *@return          Description of the Returned Value
	 *@since           1.7
	 */
	public String executeCommandAddUser(ActionContext context) {

		if (!(hasPermission(context, "admin-users-add"))) {
			return ("PermissionError");
		}

		Exception errorMessage = null;
		Connection db = null;
		boolean recordInserted = false;
    boolean contactInserted = false;
    
		try {
			db = getConnection(context);
			User thisUser = (User) context.getRequest().getAttribute("UserRecord");
    
      if (context.getRequest().getParameter("typeId") != null) {
        ((Contact)thisUser.getContact()).setTypeId(context.getRequest().getParameter("typeId"));
      }
      
			thisUser.setEnteredBy(getUserId(context));
			thisUser.setModifiedBy(getUserId(context));
      
			recordInserted = thisUser.insert(db, context);
			if (recordInserted) {
        thisUser.setBuildContact(true);
				thisUser = new User(db, thisUser.getId());
				context.getRequest().setAttribute("UserRecord", thisUser);
				updateSystemHierarchyCheck(db, context);
			}
			else {
				processErrors(context, thisUser.getErrors());
			}
		}
		catch (Exception e) {
			errorMessage = e;
		}
		finally {
			freeConnection(context, db);
		}

		if (errorMessage == null) {
			if (recordInserted) {
				return ("UserDetailsOK");
			}
			else {
				return (executeCommandInsertUserForm(context));
			}
		}
		else {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		}
	}


	/**
	 *  Deletes the user from the database
	 *
	 *@param  context  Description of Parameter
	 *@return          Description of the Returned Value
	 *@since           1.12
	 */
	public String executeCommandDeleteUser(ActionContext context) {

		if (!(hasPermission(context, "admin-users-delete"))) {
			return ("PermissionError");
		}

		Exception errorMessage = null;
		boolean recordDeleted = false;
		User thisUser = null;

		Connection db = null;
		try {
			db = this.getConnection(context);
			thisUser = new User(db, context.getRequest().getParameter("id"));
			recordDeleted = thisUser.delete(db);
		}
		catch (Exception e) {
			errorMessage = e;
		}
		finally {
			this.freeConnection(context, db);
		}

		addModuleBean(context, "View Users", "Delete User");
		if (errorMessage == null) {
			if (recordDeleted) {
				return ("UserDeleteOK");
			}
			else {
				processErrors(context, thisUser.getErrors());
				return (executeCommandListUsers(context));
			}
		}
		else {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		}
	}
  
	public String executeCommandDisableUser(ActionContext context) {

		if (!(hasPermission(context, "admin-users-delete"))) {
			return ("PermissionError");
		}

		Exception errorMessage = null;
		boolean recordDisabled = false;
		User thisUser = null;
    Ticket thisTicket = null;
    User thisRec = ((UserBean) context.getSession().getAttribute("User")).getUserRecord();
    User managerUser = null;

		Connection db = null;
		try {
			db = this.getConnection(context);
			thisUser = new User(db, context.getRequest().getParameter("id"));
      
      recordDisabled = thisUser.delete(db);
      
      if (recordDisabled) {
        thisTicket = new Ticket();
        thisTicket.setProblem("CFS User " + thisUser.getUsername() + " has been disabled by " + thisRec.getUsername() + 
              ".  Since you are the direct manager of " + thisUser.getUsername() + ", you have been notified.  It is essential that " +
              "any data still directly associated with this disabled User gets re-assigned as soon as possible.");
        thisTicket.setEnteredBy(thisRec.getId());
        thisTicket.setModifiedBy(thisRec.getId());
        thisTicket.setOrgId(0);
        thisTicket.setContactId(thisUser.getContactId());
        thisTicket.setSeverityCode(3);
        thisTicket.setPriorityCode(3);
        
        if (thisUser.getManagerId() > -1) {
          managerUser = new User();
          managerUser.setBuildContact(true);
          managerUser.setBuildHierarchy(true);
          managerUser.buildResources(db);
          updateSystemHierarchyCheck(db, context);
          thisTicket.setAssignedTo(managerUser.getId());
          thisTicket.setDepartmentCode(managerUser.getContact().getDepartment());
        } else {
          thisTicket.setDepartmentCode(thisUser.getContact().getDepartment());
        }
        
        thisTicket.insert(db);
      }
		}
		catch (Exception e) {
			errorMessage = e;
		}
		finally {
			this.freeConnection(context, db);
		}

		addModuleBean(context, "View Users", "Delete User");
		if (errorMessage == null) {
			if (recordDisabled) {
				return ("UserDeleteOK");
			}
			else {
				processErrors(context, thisUser.getErrors());
				return (executeCommandListUsers(context));
			}
		}
		else {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		}
	}    
  
	public String executeCommandEnableUser(ActionContext context) {

		if (!(hasPermission(context, "admin-users-edit"))) {
			return ("PermissionError");
		}

		Exception errorMessage = null;
		boolean recordEnabled = false;
		User thisUser = null;
    User managerUser = null;

		Connection db = null;
		try {
			db = this.getConnection(context);
			thisUser = new User(db, context.getRequest().getParameter("id"));
			recordEnabled = thisUser.enable(db);
      
      if (recordEnabled) {
        updateSystemHierarchyCheck(db, context);
      }
      
		}
		catch (Exception e) {
			errorMessage = e;
		}
		finally {
			this.freeConnection(context, db);
		}

		addModuleBean(context, "View Users", "Enable User");
		if (errorMessage == null) {
			if (!recordEnabled) {
				processErrors(context, thisUser.getErrors());
			}
      return (executeCommandListUsers(context));
		}
		else {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		}
	}   
  
	public String executeCommandDisableUserConfirm(ActionContext context) {

		if (!(hasPermission(context, "admin-users-delete"))) {
			return ("PermissionError");
		}
    
    Exception errorMessage = null;
    User thisUser = null;
    User managerUser = null;
    
		Connection db = null;
		try {
			db = this.getConnection(context);
			thisUser = new User();
      thisUser.setBuildContact(true);
      thisUser.buildRecord(db, Integer.parseInt(context.getRequest().getParameter("id")));
      
      if (thisUser.getManagerId() > -1) {
        managerUser = new User();
        managerUser.setBuildContact(true);
        managerUser.buildRecord(db, thisUser.getManagerId());
      }
		}
		catch (Exception e) {
			errorMessage = e;
		}
		finally {
			this.freeConnection(context, db);
		}    
    
    addModuleBean(context, "Users", "Disable User");
		if (errorMessage == null) {
			/**
      if (recordDeleted) {
				return ("UserDeleteOK");
			}
			else {
				processErrors(context, thisUser.getErrors());
				return (executeCommandListUsers(context));
			}
      */
      
      context.getRequest().setAttribute("User", thisUser);
      context.getRequest().setAttribute("ManagerUser", managerUser);
      return("UserDisableConfirmOK");
      
		}
		else {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		}

    /**
		Exception errorMessage = null;
		boolean recordDeleted = false;
		User thisUser = null;

		Connection db = null;
		try {
			db = this.getConnection(context);
			thisUser = new User(db, context.getRequest().getParameter("id"));
			recordDeleted = thisUser.delete(db);
		}
		catch (Exception e) {
			errorMessage = e;
		}
		finally {
			this.freeConnection(context, db);
		}

		addModuleBean(context, "View Users", "Delete User");
		if (errorMessage == null) {
			if (recordDeleted) {
				return ("UserDeleteOK");
			}
			else {
				processErrors(context, thisUser.getErrors());
				return (executeCommandListUsers(context));
			}
		}
		else {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		}
    */
	}  


	/**
	 *  Setup the form for modifying a user
	 *
	 *@param  context  Description of Parameter
	 *@return          Description of the Returned Value
	 *@since           1.12
	 */
	public String executeCommandModifyUser(ActionContext context) {

		if (!(hasPermission(context, "admin-users-edit"))) {
			return ("PermissionError");
		}

		addModuleBean(context, "View Users", "Modify User");
		Exception errorMessage = null;

		String userId = context.getRequest().getParameter("id");

		Connection db = null;
		User newUser = null;

		try {
			db = this.getConnection(context);
			newUser = new User(db, userId);

			UserList userList = new UserList();
			userList.setEmptyHtmlSelectRecord("-- None --");
			userList.setBuildContact(false);
      userList.setExcludeDisabledIfUnselected(true);
			userList.buildList(db);

			context.getRequest().setAttribute("UserList", userList);

			RoleList roleList = new RoleList();
			roleList.setEmptyHtmlSelectRecord("-- None --");
			roleList.buildList(db);
			context.getRequest().setAttribute("RoleList", roleList);
		}
		catch (Exception e) {
			errorMessage = e;
		}
		finally {
			this.freeConnection(context, db);
		}

		if (errorMessage == null) {
			context.getRequest().setAttribute("UserRecord", newUser);
			return ("UserModifyOK");
		}
		else {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		}
	}


	/**
	 *  Update the user record
	 *
	 *@param  context  Description of Parameter
	 *@return          Description of the Returned Value
	 *@since           1.12
	 */
	public String executeCommandUpdateUser(ActionContext context) {

		if (!(hasPermission(context, "admin-users-edit"))) {
			return ("PermissionError");
		}

		Exception errorMessage = null;

		User newUser = (User) context.getFormBean();

		Connection db = null;
		int resultCount = 0;

		try {
			db = this.getConnection(context);
			resultCount = newUser.update(db, context);
			if (resultCount == -1) {
				UserList userList = new UserList();
				userList.setEmptyHtmlSelectRecord("-- None --");
				userList.setBuildContact(true);
				userList.buildList(db);
				context.getRequest().setAttribute("UserList", userList);

				RoleList roleList = new RoleList();
				roleList.setEmptyHtmlSelectRecord("-- None --");
				roleList.buildList(db);
				context.getRequest().setAttribute("RoleList", roleList);
			}
			else if (resultCount == 1) {
				updateSystemHierarchyCheck(db, context);
				updateSystemPermissionCheck(context);
			}
		}
		catch (SQLException e) {
			errorMessage = e;
		}
		finally {
			this.freeConnection(context, db);
		}

		if (errorMessage == null) {
			if (resultCount == -1) {
				processErrors(context, newUser.getErrors());
				return ("UserModifyOK");
			}
			else if (resultCount == 1) {
				context.getRequest().setAttribute("id", context.getRequest().getParameter("id"));
				if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter("return").equals("list")) {
					return (executeCommandListUsers(context));
				} else {
					return ("UserUpdateOK");
				}
			}
			else {
				context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
				return ("UserError");
			}
		}
		else {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		}

	}

}

