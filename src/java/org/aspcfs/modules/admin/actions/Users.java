package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;

/**
 *  Action methods for managing users
 *
 *@author     mrajkowski
 *@created    September 17, 2001
 *@version    $Id$
 */
public final class Users extends CFSModule {
	
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
      /**
      Iterator i = list.iterator();
      while (i.hasNext()) {
        User thisUser = (User)i.next();
        Contact thisContact = this.getUser(context, thisUser.getId()).getContact();
        thisUser.setContact(thisContact);
      }
      */
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
	 */
	public String executeCommandReassign(ActionContext context) {
		Exception errorMessage = null;

		Connection db = null;
		Statement st = null;
		ResultSet rs = null;
		
		UserBean thisUser = (UserBean)context.getSession().getAttribute("User");
		
		//this is how we get the multiple-level heirarchy...recursive function.
		
		User thisRec = thisUser.getUserRecord();
		
		UserList shortChildList = thisRec.getShortChildList();
		UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
		userList.setMyId(getUserId(context));
		userList.setMyValue(thisUser.getNameLast() + ", " + thisUser.getNameFirst());
		userList.setIncludeMe(true);
		context.getRequest().setAttribute("UserList", userList);

		/**
		try {
			db = this.getConnection(context);

			UserList userList = new UserList();
			userList.setBuildContact(true);
			userList.setIncludeMe(true);
			userList.setMyId(getUserId(context));
			userList.setMyValue(getNameLast(context) + ", " + getNameFirst(context));
			userList.setManagerId(getUserId(context));
			userList.buildList(db);
			context.getRequest().setAttribute("UserList", userList);
		}
		catch (Exception e) {
			errorMessage = e;
		}
		finally {
			this.freeConnection(context, db);
		}
		*/

		addModuleBean(context, "Reassign", "Bulk Reassign");
		if (errorMessage == null) {
			return ("ReassignOK");
		}
		else {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		}
	}
	
	public String executeCommandDoReassign(ActionContext context) {
		Exception errorMessage = null;

		Connection db = null;
		int resultCount = 0;
		String sql = null;
		
		int fromPerson = Integer.parseInt(context.getRequest().getParameter("ownerFrom"));
		int toPerson = Integer.parseInt(context.getRequest().getParameter("ownerTo"));
		String whichTable = context.getRequest().getParameter("whichTable");
    
    if ("ticket".equals(whichTable)) {
      sql =
        "UPDATE ticket " +
        "SET assigned_to = ? " +
        "WHERE assigned_to = ? AND closed IS NULL ";
      
    } else if ("activity".equals(whichTable)) {
      sql =
        "UPDATE project_assignments " +
        "SET user_assign_id = ?, assign_date = CURRENT_TIMESTAMP " +
        "WHERE user_assign_id = ? AND status_id NOT IN (SELECT code FROM lookup_project_status WHERE type in (3,4)) ";
      
    } else if ("opportunity-open".equals(whichTable)) {
      sql =
        "UPDATE opportunity " +
        "SET owner = ? " +
        "WHERE owner = ? AND closed IS NULL ";
        
    } else if ("contact".equals(whichTable)) {
	    sql =
	    	"UPDATE contact " +
		"SET owner = ? " +
		"WHERE owner = ? AND type_id != 2 ";
    
    } else if (whichTable != null && !whichTable.equals("")) {
      sql =
        "UPDATE " + whichTable + " " +
        "SET owner = ? " +
        "WHERE owner = ? ";
    }

		try {
      if (sql != null) {
        db = this.getConnection(context);
        int i = 0;
        PreparedStatement pst = db.prepareStatement(sql.toString());
        pst.setInt(++i, toPerson);
        pst.setInt(++i, fromPerson);
        resultCount = pst.executeUpdate();
        pst.close();
      }
		}	catch (SQLException e) {
			errorMessage = e;
		}	finally {
			this.freeConnection(context, db);
		}
		
		if (errorMessage == null) {
			if (resultCount == -1) {
				context.getRequest().setAttribute("Error", "<b>These records could not be bulk-reassigned</b>");
				return ("UserError");
			} else {
				//FOR GRAPH CLEARING
				
				if ( whichTable.startsWith("opportunity") ) {
					invalidateUserInMemory(fromPerson, context);
					invalidateUserInMemory(toPerson, context);
				}
				
        context.getRequest().setAttribute("count", "" + resultCount);
				return("DoReassignOK");
			}
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
			context.getRequest().setAttribute("ContactTypeList", contactTypeList);

			ContactList contactList = new ContactList();
			contactList.setPersonalId(getUserId(context));
			contactList.setTypeId(Integer.parseInt(typeId));
			contactList.setCheckUserAccess(true);
			contactList.setBuildDetails(false);
			contactList.buildList(db);
			context.getRequest().setAttribute("ContactList", contactList);

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


	/**
	 *  Generates the form data for step 2 of adding a new user
	 *
	 *@param  context  Description of Parameter
	 *@return          Description of the Returned Value
	 *@since           1.7
	 */
	public String executeCommandInsertUserDecision(ActionContext context) {
		Exception errorMessage = null;
		addModuleBean(context, "Add User", "Add New User");

		String newAccount = context.getRequest().getParameter("newAccount");
		String typeId = context.getRequest().getParameter("typeId");
		String contactId = context.getRequest().getParameter("contactId");

		//New account is selected
		if (newAccount != null && newAccount.equals("on")) {
			return ("NewAccountFirstOK");
		}

		//Using a current account, but no contact was selected
		if (typeId == null || typeId.equals("") || contactId == null || contactId.equals("")) {
			context.getRequest().setAttribute("usernameError",
					"Select a contact or choose to create a new contact first");
			return (executeCommandInsertUserForm(context));
		}

		//A contact was selected, so proceed
		Connection db = null;
		try {
			db = this.getConnection(context);

			Contact thisContact = new Contact(db, contactId);
			context.getRequest().setAttribute("Contact", thisContact);

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
		catch (Exception e) {
			errorMessage = e;
		}
		finally {
			this.freeConnection(context, db);
		}

		if (errorMessage == null) {
			return ("UserInsertForm2OK");
		}
		else {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		}
	}


	/**
	 *  Adds the user to the database
	 *
	 *@param  context  Description of Parameter
	 *@return          Description of the Returned Value
	 *@since           1.7
	 */
	public String executeCommandAddUser(ActionContext context) {
		Exception errorMessage = null;
		Connection db = null;
		boolean recordInserted = false;
		try {
			db = getConnection(context);
			User thisUser = (User) context.getRequest().getAttribute("UserRecord");
			thisUser.setEnteredBy(getUserId(context));
			thisUser.setModifiedBy(getUserId(context));
			recordInserted = thisUser.insert(db, context);
			if (recordInserted) {
				thisUser = new User(db, "" + thisUser.getId());
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
				return (executeCommandInsertUserDecision(context));
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


	/**
	 *  Setup the form for modifying a user
	 *
	 *@param  context  Description of Parameter
	 *@return          Description of the Returned Value
	 *@since           1.12
	 */
	public String executeCommandModifyUser(ActionContext context) {
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
			userList.buildList(db);
      //Iterator i = userList.iterator();
      //while (i.hasNext()) {
      //  User thisUser = (User)i.next();
      //  Contact thisContact = this.getUser(context, thisUser.getId()).getContact();
      //  thisUser.setContact(thisContact);
      //}
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
			} else if (resultCount == 1) {
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
				return ("UserUpdateOK");
			}
			else {
				context.getRequest().setAttribute("Error",
						"<b>This record could not be updated because someone else updated it first.</b><p>" +
						"You can hit the back button to review the changes that could not be committed, " +
						"but you must reload the record and make the changes again.");
				return ("UserError");
			}
		}
		else {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		}

	}

}

