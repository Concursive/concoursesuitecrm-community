//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.PagedListInfo;
import com.darkhorseventures.webutils.HtmlSelect;

/**
 *  A list of User objects.
 *
 *@author     mrajkowski
 *@created    September 19, 2001
 *@version    $Id$
 */
public class UserList extends Vector {

	private PagedListInfo pagedListInfo = null;
	private String emptyHtmlSelectRecord = null;
	private int roleId = -1;
	private int managerId = -1;
	private User managerUser = null;
	private boolean buildContact = false;
	private boolean buildPermissions = false;
	private boolean buildHierarchy = false;
	private boolean topLevel = false;
	private int department = -1;
	private int enabled = -1;
	private String jsEvent = null;
	private boolean includeAliases = false;

	private boolean includeMe = false;
	private String myValue = "";
	private int myId = -1;

	public final static int TRUE = 1;
	public final static int FALSE = 0;


	/**
	 *  Constructor for the UserList object
	 *
	 *@since    1.1
	 */
	public UserList() { }


	/**
	 *  Constructor for the UserList object
	 *
	 *@param  db                Description of Parameter
	 *@param  doHierarchy       Description of Parameter
	 *@param  parentUser        Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since                    1.9
	 */
	public UserList(Connection db, User parentUser, boolean doHierarchy) throws SQLException {
		this.managerId = parentUser.getId();
		this.managerUser = parentUser;
		this.buildHierarchy = doHierarchy;
		buildList(db);
	}


	/**
	 *  Sets the PagedListInfo attribute of the UserList object
	 *
	 *@param  tmp  The new PagedListInfo value
	 *@since       1.4
	 */
	public void setPagedListInfo(PagedListInfo tmp) {
		this.pagedListInfo = tmp;
	}


	/**
	 *  Sets the EmptyHtmlSelectRecord attribute of the UserList object
	 *
	 *@param  tmp  The new EmptyHtmlSelectRecord value
	 *@since       1.4
	 */
	public void setEmptyHtmlSelectRecord(String tmp) {
		this.emptyHtmlSelectRecord = tmp;
	}


	/**
	 *  Sets the RoleId attribute of the UserList object
	 *
	 *@param  tmp  The new RoleId value
	 *@since       1.8
	 */
	public void setRoleId(int tmp) {
		this.roleId = tmp;
	}


	/**
	 *  Sets the Department attribute of the UserList object
	 *
	 *@param  department  The new Department value
	 *@since
	 */
	public void setDepartment(int department) {
		this.department = department;
	}


	/**
	 *  Sets the Department attribute of the UserList object
	 *
	 *@param  department  The new Department value
	 *@since
	 */
	public void setDepartment(String department) {
		this.department = Integer.parseInt(department);
	}


	/**
	 *  Sets the JsEvent attribute of the UserList object
	 *
	 *@param  jsEvent  The new JsEvent value
	 *@since
	 */
	public void setJsEvent(String jsEvent) {
		this.jsEvent = jsEvent;
	}


	/**
	 *  Sets the Enabled attribute of the UserList object
	 *
	 *@param  tmp  The new Enabled value
	 *@since
	 */
	public void setEnabled(int tmp) {
		this.enabled = tmp;
	}


	/**
	 *  Sets the IncludeMe attribute of the UserList object
	 *
	 *@param  tmp  The new IncludeMe value
	 *@since       1.19
	 */
	public void setIncludeMe(boolean tmp) {
		this.includeMe = tmp;
	}


	/**
	 *  Sets the MyValue attribute of the UserList object
	 *
	 *@param  tmp  The new MyValue value
	 *@since       1.19
	 */
	public void setMyValue(String tmp) {
		this.myValue = tmp;
	}


	/**
	 *  Sets the MyId attribute of the UserList object
	 *
	 *@param  tmp  The new MyId value
	 *@since       1.19
	 */
	public void setMyId(int tmp) {
		this.myId = tmp;
	}
public boolean getIncludeAliases() {
	return includeAliases;
}
public void setIncludeAliases(boolean includeAliases) {
	this.includeAliases = includeAliases;
}


	/**
	 *  Sets the ManagerId attribute of the UserList object
	 *
	 *@param  tmp  The new ManagerId value
	 *@since       1.8
	 */
	public void setManagerId(int tmp) {
		this.managerId = tmp;
	}


	/**
	 *  Sets the TopLevel attribute of the UserList object
	 *
	 *@param  tmp  The new TopLevel value
	 *@since       1.20
	 */
	public void setTopLevel(boolean tmp) {
		this.topLevel = tmp;
	}



	/**
	 *  Sets the BuildContact attribute of the UserList object
	 *
	 *@param  tmp  The new BuildContact value
	 *@since       1.18
	 */
	public void setBuildContact(boolean tmp) {
		this.buildContact = tmp;
	}


	/**
	 *  Sets the BuildPermissions attribute of the UserList object
	 *
	 *@param  tmp  The new BuildPermissions value
	 *@since       1.17
	 */
	public void setBuildPermissions(boolean tmp) {
		this.buildPermissions = tmp;
	}


	/**
	 *  Sets the BuildHierarchy attribute of the UserList object
	 *
	 *@param  tmp  The new BuildHierarchy value
	 *@since       1.17
	 */
	public void setBuildHierarchy(boolean tmp) {
		this.buildHierarchy = tmp;
	}


	/**
	 *  Sets the ManagerUser attribute of the UserList object
	 *
	 *@param  tmp  The new ManagerUser value
	 *@since
	 */
	public void setManagerUser(User tmp) {
		this.managerUser = tmp;
	}


	/**
	 *  Gets the JsEvent attribute of the UserList object
	 *
	 *@return    The JsEvent value
	 *@since
	 */
	public String getJsEvent() {
		return jsEvent;
	}


	/**
	 *  Gets the Department attribute of the UserList object
	 *
	 *@return    The Department value
	 *@since
	 */
	public int getDepartment() {
		return department;
	}


	/**
	 *  Gets the IncludeMe attribute of the UserList object
	 *
	 *@return    The IncludeMe value
	 *@since     1.19
	 */
	public boolean getIncludeMe() {
		return includeMe;
	}


	/**
	 *  Gets the MyValue attribute of the UserList object
	 *
	 *@return    The MyValue value
	 *@since     1.19
	 */
	public String getMyValue() {
		return myValue;
	}


	/**
	 *  Gets the MyId attribute of the UserList object
	 *
	 *@return    The MyId value
	 *@since     1.19
	 */
	public int getMyId() {
		return myId;
	}


	/**
	 *  Gets the ListSize attribute of the UserList object
	 *
	 *@return    The ListSize value
	 *@since     1.9
	 */
	public int getListSize() {
		return this.size();
	}


	/**
	 *  Gets the HtmlSelect attribute of the UserList object
	 *
	 *@param  selectName  Description of Parameter
	 *@return             The HtmlSelect value
	 *@since              1.4
	 */
	public String getHtmlSelect(String selectName) {
		return getHtmlSelect(selectName, -1);
	}


	/**
	 *  Gets the HtmlSelect attribute of the UserList object
	 *
	 *@param  selectName  Description of Parameter
	 *@param  defaultKey  Description of Parameter
	 *@return             The HtmlSelect value
	 *@since              1.4
	 */
	public String getHtmlSelect(String selectName, int defaultKey) {
		HtmlSelect userListSelect = new HtmlSelect();
		userListSelect.setJsEvent(jsEvent);
		
		if (emptyHtmlSelectRecord != null) {
			userListSelect.addItem(-1, emptyHtmlSelectRecord);
		}
		if (includeMe == true) {
			userListSelect.addItem(myId, myValue);
		}
		Iterator i = this.iterator();
		while (i.hasNext()) {
			User thisUser = (User) i.next();
			userListSelect.addItem(
					thisUser.getId(),
					thisUser.getContact().getNameLast() + ", " +
					thisUser.getContact().getNameFirst());
		}
		return userListSelect.getHtml(selectName, defaultKey);
	}


	/**
	 *  Gets the UserListIds attribute of the UserList object
	 *
	 *@param  toInclude  Description of Parameter
	 *@return            The UserListIds value
	 *@since             1.17
	 */
	public String getUserListIds(int toInclude) {
		String values = "" + toInclude;

		Iterator i = this.iterator();

		if (i.hasNext()) {
			values = values + ", ";
		}

		while (i.hasNext()) {
			User thisUser = (User) i.next();
			values = values + thisUser.getId();

			if (i.hasNext()) {
				values = values + ", ";
			}
		}

		return values;
	}


	/**
	 *  Gets the UserListIds attribute of the UserList object
	 *
	 *@return    The UserListIds value
	 *@since     1.16
	 */
	public String getUserListIds() {
		String values = "";
		Iterator i = this.iterator();

		while (i.hasNext()) {
			User thisUser = (User) i.next();
			values = values + thisUser.getId();

			if (i.hasNext()) {
				values = values + ", ";
			}
		}

		return values;
	}


	/**
	 *  Gets the User attribute of the UserList object
	 *
	 *@param  childId  Description of Parameter
	 *@return          The User value
	 *@since
	 */
	public User getUser(int childId) {
		Iterator i = this.iterator();
		while (i.hasNext()) {
			User thisUser = (User) i.next();
			if (thisUser.getId() == childId) {
				return thisUser;
			}
			User childUser = thisUser.getChild(childId);
			if (childUser != null) {
				return childUser;
			}
		}
		return null;
	}


	/**
	 *  Gets the TopUser attribute of the UserList object
	 *
	 *@param  userId  Description of Parameter
	 *@return         The TopUser value
	 *@since
	 */
	public User getTopUser(int userId) {
		Iterator i = this.iterator();
		while (i.hasNext()) {
			User thisUser = (User) i.next();
			if (thisUser.getId() == userId) {
				return thisUser;
			}
		}
		return null;
	}


	/**
	 *  Gets the ManagerUser attribute of the UserList object
	 *
	 *@return    The ManagerUser value
	 *@since
	 */
	public User getManagerUser() {
		return managerUser;
	}


	/**
	 *  Generates the user list from the database
	 *
	 *@param  db                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since                    1.4
	 */
	public void buildList(Connection db) throws SQLException {

		PreparedStatement pst = null;
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();

		sqlSelect.append(
				"SELECT a.username, a.password, a.role_id, a.last_login, a.manager_id, " +
        "a.last_ip, a.timezone, a.startofday as access_startofday, " +
        "a.endofday as access_endofday, a.expires, a.alias, " +
        "a.contact_id as contact_id_link, a.user_id as access_user_id, " +
        "a.enabled as access_enabled, a.assistant, " +
        "a.entered as access_entered, a.enteredby as access_enteredby, " +
        "a.modified as access_modified, a.modifiedby as access_modifiedby, " +
				"r.role, " +
				"m.namefirst as mgr_namefirst, m.namelast as mgr_namelast, " +
				"als.namefirst as als_namefirst, als.namelast as als_namelast, " +
        "c.*, d.description as departmentname, t.description as type_name, " +
        "ct_owner.namelast as o_namelast, ct_owner.namefirst as o_namefirst, " +
        "ct_eb.namelast as eb_namelast, ct_eb.namefirst as eb_namefirst, " +
        "ct_mb.namelast as mb_namelast, ct_mb.namefirst as mb_namefirst, " +
        "o.name as org_name " +
				"FROM access a " +
				"LEFT JOIN contact c ON (a.contact_id = c.contact_id) " +
				"LEFT JOIN lookup_contact_types t ON (c.type_id = t.code) " +
				"LEFT JOIN organization o ON (c.org_id = o.org_id) " +
				"LEFT JOIN lookup_department d ON (c.department = d.code) " +
				"LEFT JOIN contact ct_owner ON (c.owner = ct_owner.user_id) " +
				"LEFT JOIN contact ct_eb ON (c.enteredby = ct_eb.user_id) " +
				"LEFT JOIN contact ct_mb ON (c.modifiedby = ct_mb.user_id) " +
				"LEFT JOIN contact als ON (a.alias = als.user_id) " +
				"LEFT JOIN contact m ON (a.manager_id = m.user_id), " +
				"role r " +
				"WHERE a.role_id = r.role_id ");

		sqlCount.append("SELECT COUNT(*) AS recordcount " +
				"FROM access a LEFT JOIN contact c ON (a.contact_id = c.contact_id), role r " +
				"WHERE a.role_id = r.role_id ");

		createFilter(sqlFilter);

		if (pagedListInfo != null) {
			//Get the total number of records matching filter
			pst = db.prepareStatement(sqlCount.toString() +
					sqlFilter.toString());
			items = prepareFilter(pst);
			rs = pst.executeQuery();
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
				pagedListInfo.setMaxRecords(maxRecords);
			}
			pst.close();
			rs.close();

			//Determine the offset, based on the filter, for the first record to show
			if (!pagedListInfo.getCurrentLetter().equals("")) {
				pst = db.prepareStatement(sqlCount.toString() +
						sqlFilter.toString() +
						"AND c.namelast < ? ");
				items = prepareFilter(pst);
				pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
				rs = pst.executeQuery();
				if (rs.next()) {
					int offsetCount = rs.getInt("recordcount");
					pagedListInfo.setCurrentOffset(offsetCount);
				}
				rs.close();
				pst.close();
			}

			//Determine column to sort by
			if (pagedListInfo.getColumnToSortBy() == null || pagedListInfo.getColumnToSortBy().equals("")) {
				pagedListInfo.setColumnToSortBy("namelast");
			}
			sqlOrder.append("ORDER BY " + pagedListInfo.getColumnToSortBy() + " ");
			if (pagedListInfo.getSortOrder() != null && !pagedListInfo.getSortOrder().equals("")) {
				sqlOrder.append(pagedListInfo.getSortOrder() + " ");
			}

			//Determine items per page
			if (pagedListInfo.getItemsPerPage() > 0) {
				sqlOrder.append("LIMIT " + pagedListInfo.getItemsPerPage() + " ");
			}

			sqlOrder.append("OFFSET " + pagedListInfo.getCurrentOffset() + " ");
		}
		else {
			sqlOrder.append("ORDER BY c.namelast ");
		}

		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		items = prepareFilter(pst);
		rs = pst.executeQuery();
		while (rs.next()) {
			User thisUser = new User(rs);
			if (thisUser.getContactId() > -1) {
				thisUser.setContact(new Contact(rs));
			}
			if (managerUser != null) {
				thisUser.setManagerId(managerUser.getId());
				thisUser.setManagerUser(managerUser);
			}
			this.addElement(thisUser);
		}
		
		rs.close();
		pst.close();
		
		buildResources(db);
	}


	/**
	 *  For each user, the contact information is retrieved
	 *
	 *@param  db                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since                    1.4
	 */
	private void buildResources(Connection db) throws SQLException {
		if (buildContact || buildPermissions || buildHierarchy) {
			Iterator i = this.iterator();
			while (i.hasNext()) {
				User thisUser = (User) i.next();
				thisUser.setBuildContact(buildContact);
				thisUser.setBuildPermissions(buildPermissions);
				thisUser.setBuildHierarchy(buildHierarchy);
				thisUser.buildResources(db);
			}
		}
	}


	/**
	 *  Limits the recods that are retrieved, works with prepareFilter
	 *
	 *@param  sqlFilter  Description of Parameter
	 *@since             1.4
	 */
	private void createFilter(StringBuffer sqlFilter) {
		if (sqlFilter == null) {
			sqlFilter = new StringBuffer();
		}
		if (includeAliases) {
			sqlFilter.append("AND a.alias > -1 ");
		} 
		if (!(includeAliases)) {
			sqlFilter.append("AND a.alias = -1 ");
		}
		if (roleId > -1) {
			sqlFilter.append("AND r.role_id = ? ");
		}
		if (managerId > -1) {
			sqlFilter.append("AND a.manager_id = ? ");
		}

		if (department > -1) {
			sqlFilter.append("AND c.department = ? ");
		}
		else {
			if (topLevel) {
				sqlFilter.append("AND a.manager_id = -1 ");
			}
			else {
				sqlFilter.append("AND a.contact_id > -1 ");
			}
		}
		
		if (enabled > -1) {
			sqlFilter.append("AND a.enabled = ? ");
		}
	
	}


	/**
	 *  Limits the recods that are retrieved, works with createFilter
	 *
	 *@param  pst               Description of Parameter
	 *@return                   Description of the Returned Value
	 *@exception  SQLException  Description of Exception
	 *@since                    1.4
	 */
	private int prepareFilter(PreparedStatement pst) throws SQLException {
		int i = 0;
		if (roleId > -1) {
			pst.setInt(++i, roleId);
		}
		if (managerId > -1) {
			pst.setInt(++i, managerId);
		}
		if (department > -1) {
			pst.setInt(++i, department);
		}
    if (enabled > -1) {
      pst.setBoolean(++i, enabled == TRUE);
    }

		return i;
	}
}


