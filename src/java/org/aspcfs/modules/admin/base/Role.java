//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.DateFormat;

/**
 *  Represents a Role (User Group)
 *
 *@author     mrajkowski
 *@created    September 19, 2001
 *@version    $Id$
 */
public class Role extends GenericBean {
	protected int id = -1;
	protected String role = null;
	protected String description = null;

	protected java.sql.Timestamp entered = null;
	protected java.sql.Timestamp modified = null;

	protected int enteredBy = -1;
	protected int modifiedBy = -1;
	protected boolean enabled = true;
	protected RolePermissionList permissionList = new RolePermissionList();
	protected UserList userList = new UserList();

	protected boolean buildHierarchy = false;


	/**
	 *  Constructor for the Role object
	 *
	 *@since
	 */
	public Role() { }


	/**
	 *  Constructor for the Role object
	 *
	 *@param  rs                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public Role(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}


	/**
	 *  Constructor for the Role object
	 *
	 *@param  db                Description of Parameter
	 *@param  roleId            Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public Role(Connection db, int roleId) throws SQLException {
		Statement st = null;
		ResultSet rs = null;

		String sql =
				"SELECT * FROM role " +
				"WHERE role_id = " + roleId + " ";

		st = db.createStatement();
		rs = st.executeQuery(sql);
		if (rs.next()) {
			buildRecord(rs);
		}
		else {
			rs.close();
			st.close();
			throw new SQLException("Role record not found.");
		}
		rs.close();
		st.close();

		buildResources(db);
	}


	/**
	 *  Sets the Entered attribute of the Role object
	 *
	 *@param  tmp  The new Entered value
	 *@since
	 */
	public void setEntered(java.sql.Timestamp tmp) {
		this.entered = tmp;
	}


	/**
	 *  Sets the Modified attribute of the Role object
	 *
	 *@param  tmp  The new Modified value
	 *@since
	 */
	public void setModified(java.sql.Timestamp tmp) {
		this.modified = tmp;
	}


	/**
	 *  Sets the Entered attribute of the Role object
	 *
	 *@param  tmp  The new Entered value
	 *@since
	 */
	public void setEntered(String tmp) {
		this.entered = java.sql.Timestamp.valueOf(tmp);
	}


	/**
	 *  Sets the Modified attribute of the Role object
	 *
	 *@param  tmp  The new Modified value
	 *@since
	 */
	public void setModified(String tmp) {
		this.modified = java.sql.Timestamp.valueOf(tmp);
		;
	}


	/**
	 *  Sets the Id attribute of the Role object
	 *
	 *@param  tmp  The new Id value
	 *@since
	 */
	public void setId(int tmp) {
		this.id = tmp;
	}


	/**
	 *  Sets the Id attribute of the Role object
	 *
	 *@param  tmp  The new Id value
	 *@since
	 */
	public void setId(String tmp) {
		this.id = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the Role attribute of the Role object
	 *
	 *@param  tmp  The new Role value
	 *@since
	 */
	public void setRole(String tmp) {
		this.role = tmp;
	}


	/**
	 *  Sets the Description attribute of the Role object
	 *
	 *@param  tmp  The new Description value
	 *@since
	 */
	public void setDescription(String tmp) {
		this.description = tmp;
	}


	/**
	 *  Sets the EnteredBy attribute of the Role object
	 *
	 *@param  tmp  The new EnteredBy value
	 *@since
	 */
	public void setEnteredBy(int tmp) {
		this.enteredBy = tmp;
	}


	/**
	 *  Sets the EnteredBy attribute of the Role object
	 *
	 *@param  tmp  The new EnteredBy value
	 *@since
	 */
	public void setEnteredBy(String tmp) {
		this.enteredBy = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the ModifiedBy attribute of the Role object
	 *
	 *@param  tmp  The new ModifiedBy value
	 *@since
	 */
	public void setModifiedBy(int tmp) {
		this.modifiedBy = tmp;
	}


	/**
	 *  Sets the ModifiedBy attribute of the Role object
	 *
	 *@param  tmp  The new ModifiedBy value
	 *@since
	 */
	public void setModifiedBy(String tmp) {
		this.modifiedBy = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the Enabled attribute of the Role object
	 *
	 *@param  tmp  The new Enabled value
	 *@since
	 */
	public void setEnabled(boolean tmp) {
		this.enabled = tmp;
	}


	/**
	 *  Sets the Enabled attribute of the Role object
	 *
	 *@param  tmp  The new Enabled value
	 *@since
	 */
	public void setEnabled(String tmp) {
		if (tmp.toLowerCase().equals("false")) {
			this.enabled = false;
		}
		else {
			this.enabled = true;
		}
	}


	/**
	 *  Sets the RequestItems attribute of the Role object
	 *
	 *@param  request  The new RequestItems value
	 *@since
	 */
	public void setRequestItems(HttpServletRequest request) {
		permissionList = new RolePermissionList(request);
	}


	/**
	 *  Gets the Entered attribute of the Role object
	 *
	 *@return    The Entered value
	 *@since
	 */
	public java.sql.Timestamp getEntered() {
		return entered;
	}


	/**
	 *  Gets the Modified attribute of the Role object
	 *
	 *@return    The Modified value
	 *@since
	 */
	public java.sql.Timestamp getModified() {
		return modified;
	}


	/**
	 *  Gets the ModifiedString attribute of the Role object
	 *
	 *@return    The ModifiedString value
	 *@since
	 */
	public String getModifiedString() {
		String tmp = "";
		try {
			return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(modified);
		}
		catch (NullPointerException e) {
		}
		return tmp;
	}


	/**
	 *  Gets the EnteredString attribute of the Role object
	 *
	 *@return    The EnteredString value
	 *@since
	 */
	public String getEnteredString() {
		String tmp = "";
		try {
			return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(entered);
		}
		catch (NullPointerException e) {
		}
		return tmp;
	}


	/**
	 *  Gets the Id attribute of the Role object
	 *
	 *@return    The Id value
	 *@since
	 */
	public int getId() {
		return id;
	}


	/**
	 *  Gets the Role attribute of the Role object
	 *
	 *@return    The Role value
	 *@since
	 */
	public String getRole() {
		return role;
	}


	/**
	 *  Gets the Description attribute of the Role object
	 *
	 *@return    The Description value
	 *@since
	 */
	public String getDescription() {
		return description;
	}


	/**
	 *  Gets the EnteredBy attribute of the Role object
	 *
	 *@return    The EnteredBy value
	 *@since
	 */
	public int getEnteredBy() {
		return enteredBy;
	}


	/**
	 *  Gets the ModifiedBy attribute of the Role object
	 *
	 *@return    The ModifiedBy value
	 *@since
	 */
	public int getModifiedBy() {
		return modifiedBy;
	}


	/**
	 *  Gets the Enabled attribute of the Role object
	 *
	 *@return    The Enabled value
	 *@since
	 */
	public boolean getEnabled() {
		return enabled;
	}


	/**
	 *  Gets the PermissionList attribute of the Role object
	 *
	 *@return    The PermissionList value
	 *@since
	 */
	public RolePermissionList getPermissionList() {
		return permissionList;
	}


	/**
	 *  Gets the UserList attribute of the Role object
	 *
	 *@return    The UserList value
	 *@since
	 */
	public UserList getUserList() {
		return userList;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  db                Description of Parameter
	 *@return                   Description of the Returned Value
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public int update(Connection db) throws SQLException {
		if (this.getId() == -1) {
			throw new SQLException("ID was not specified");
		}

		if (!isValid(db)) {
			return -1;
		}

		int resultCount = 0;
		try {
			db.setAutoCommit(false);

			PreparedStatement pst = null;
			StringBuffer sql = new StringBuffer();

			//Process the base role record
			sql.append(
					"UPDATE role " +
					"SET role = ?, description = ?, modified = CURRENT_TIMESTAMP, " +
					"modifiedby = ? " +
					"WHERE modified = ? AND role_id = " + id);

			pst = db.prepareStatement(sql.toString());
			int i = 0;
			pst.setString(++i, this.getRole());
			pst.setString(++i, this.getDescription());
			pst.setInt(++i, this.getModifiedBy());
			pst.setTimestamp(++i, this.getModified());

			resultCount = pst.executeUpdate();
			pst.close();

			//Process the permissions
			deletePermissions(db);
			insertPermissions(db);

			db.commit();
		}
		catch (Exception e) {
			db.rollback();
			db.setAutoCommit(true);
			throw new SQLException(e.getMessage());
		}

		db.setAutoCommit(true);
		return resultCount;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  db                Description of Parameter
	 *@return                   Description of the Returned Value
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public synchronized boolean insert(Connection db) throws SQLException {

		if (!isValid(db)) {
			return false;
		}

		try {
			db.setAutoCommit(false);

			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO role ");
			sql.append("(role, description, ");
			sql.append("enteredby, modifiedby ) ");
			sql.append("VALUES (?, ?, ");
			sql.append("?, ?) ");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setString(++i, getRole());
			pst.setString(++i, getDescription());
			pst.setInt(++i, getEnteredBy());
			pst.setInt(++i, getModifiedBy());
			pst.execute();
			pst.close();

			Statement st = db.createStatement();
			ResultSet rs = st.executeQuery("select currval('role_role_id_seq')");
			if (rs.next()) {
				this.setId(rs.getInt(1));
			}
			rs.close();
			st.close();

			insertPermissions(db);

			db.commit();
		}
		catch (SQLException e) {
			db.rollback();
			db.setAutoCommit(true);
			throw new SQLException(e.getMessage());
		}
		db.setAutoCommit(true);
		return true;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  db                Description of Parameter
	 *@return                   Description of the Returned Value
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public boolean delete(Connection db) throws SQLException {
		if (this.getId() == -1) {
			throw new SQLException("ID was not specified");
		}

		if (hasUsers(db, true)) {
			errors.put("actionError", "Role cannot be deleted... there are active users assigned to this role.");
			return false;
		}

		int recordCount = 0;

		try {
			db.setAutoCommit(false);
			Statement st = db.createStatement();

			if (hasUsers(db, false)) {
				recordCount = st.executeUpdate(
						"UPDATE role " +
						"SET enabled = false " +
						"WHERE role_id = " + id + " ");
			}
			else {
				deletePermissions(db);
				recordCount = st.executeUpdate(
						"DELETE FROM role " +
						"WHERE role_id = " + id + " ");
			}
			st.close();
			db.commit();
		}
		catch (SQLException e) {
			db.rollback();
			db.setAutoCommit(true);
			throw new SQLException(e.getMessage());
		}
		db.setAutoCommit(true);

		if (recordCount == 0) {
			errors.put("actionError", "Role could not be deleted because it no longer exists.");
			return false;
		}
		else {
			return true;
		}
	}


	/**
	 *  Adds a feature to the Permission attribute of the Role object
	 *
	 *@param  db                The feature to be added to the Permission attribute
	 *@param  permissionId      The feature to be added to the Permission attribute
	 *@param  add               The feature to be added to the Permission attribute
	 *@param  view              The feature to be added to the Permission attribute
	 *@param  edit              The feature to be added to the Permission attribute
	 *@param  delete            The feature to be added to the Permission attribute
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public void addPermission(Connection db, int permissionId, boolean add, boolean view, boolean edit, boolean delete) throws SQLException {
		int i = 0;
		PreparedStatement pst = db.prepareStatement(
				"INSERT INTO role_permission " +
				"(role_id, permission_id, role_add, role_view, role_edit, role_delete) " +
				"VALUES (?, ?, ?, ?, ?, ? ) ");
		pst.setInt(++i, getId());
		pst.setInt(++i, permissionId);
		pst.setBoolean(++i, add);
		pst.setBoolean(++i, view);
		pst.setBoolean(++i, edit);
		pst.setBoolean(++i, delete);
		pst.execute();
		pst.close();
	}


	/**
	 *  Gets the Valid attribute of the Role object
	 *
	 *@param  db                Description of Parameter
	 *@return                   The Valid value
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	protected boolean isValid(Connection db) throws SQLException {
		errors.clear();

		if (role == null || role.trim().equals("")) {
			errors.put("roleError", "Role cannot be left blank");
		}
		else {
			if (isDuplicate(db)) {
				errors.put("roleError", "Role name is already in use");
			}
		}

		if (description == null || description.trim().equals("")) {
			errors.put("descriptionError", "Description cannot be left blank");
		}

		if (hasErrors()) {
			return false;
		}
		else {
			return true;
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  rs                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	protected void buildRecord(ResultSet rs) throws SQLException {
		id = rs.getInt("role_id");
		role = rs.getString("role");
		description = rs.getString("description");

		entered = rs.getTimestamp("entered");

		enteredBy = rs.getInt("enteredby");
		modified = rs.getTimestamp("modified");
		modifiedBy = rs.getInt("modifiedby");
		enabled = rs.getBoolean("enabled");
	}


	/**
	 *  Description of the Method
	 *
	 *@param  db                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	protected void buildResources(Connection db) throws SQLException {
		permissionList = new RolePermissionList(db, id);
		buildUserList(db);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  db                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	protected void buildUserList(Connection db) throws SQLException {
		userList.setRoleId(id);
		userList.setBuildHierarchy(buildHierarchy);
		userList.buildList(db);
	}


	/**
	 *  Gets the Duplicate attribute of the Role object
	 *
	 *@param  db                Description of Parameter
	 *@return                   The Duplicate value
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	private boolean isDuplicate(Connection db) throws SQLException {
		boolean duplicate = false;

		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT * " +
				"FROM role " +
				"WHERE lower(role) = lower(?)  " +
				"AND enabled = true ");

		if (id > -1) {
			sql.append("AND role_id <> " + id + " ");
		}

		PreparedStatement pst = db.prepareStatement(sql.toString());
		pst.setString(1, getRole());
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			duplicate = true;
		}
		rs.close();
		pst.close();
		return duplicate;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  db                Description of Parameter
	 *@param  activeUsers       Description of Parameter
	 *@return                   Description of the Returned Value
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	private boolean hasUsers(Connection db, boolean activeUsers) throws SQLException {
		int resultCount = -1;
		Statement st = db.createStatement();
		ResultSet rs = st.executeQuery(
				"SELECT count(*) AS count " +
				"FROM access " +
				"WHERE role_id = " + id + " " +
				(activeUsers ? "AND enabled = true " : ""));
		if (rs.next()) {
			resultCount = rs.getInt("count");
		}
		rs.close();
		st.close();
		if (resultCount > 0) {
			return true;
		}
		else {
			return false;
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  db                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	private void deletePermissions(Connection db) throws SQLException {
		Statement st = db.createStatement();
		st.execute("DELETE FROM role_permission " +
				"WHERE role_id = " + id);
		st.close();
	}


	/**
	 *  Description of the Method
	 *
	 *@param  db                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	private void insertPermissions(Connection db) throws SQLException {
		Iterator ipermission = permissionList.keySet().iterator();
		while (ipermission.hasNext()) {
			Permission thisPermission = (Permission) permissionList.get((String) ipermission.next());
			if (thisPermission.getEnabled()) {
				addPermission(db,
						thisPermission.getId(),
						thisPermission.getAdd(),
						thisPermission.getView(),
						thisPermission.getEdit(),
						thisPermission.getDelete());
			}
		}
	}
}

