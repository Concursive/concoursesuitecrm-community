//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    November 8, 2001
 */
public class TicketLog extends GenericBean {

	private int id = -1;
	private int ticketId = -1;
	private String entryText = "";
	private int assignedTo = -1;
	private boolean closed = false;

	private String entered = "";
	private int enteredBy = -1;
	private String modified = null;
	private int modifiedBy = -1;

	private int priorityCode = -1;
	private int levelCode = -1;
	private int departmentCode = -1;
	private int catCode = -1;
	private int severityCode = -1;

	private String enteredByName = "";
	private String modifiedByName = "";

	private String companyName = "";
	private String categoryName = "";
	private String departmentName = "";
	
	private boolean systemMessage = false;
	private String assignedToName = "";
	private String priorityName = "";
	private String severityName = "";


	/**
	 *  Constructor for the Ticket object, creates an empty Ticket
	 *
	 *@since    1.0
	 */
	public TicketLog() { }


	/**
	 *  Constructor for the Ticket object
	 *
	 *@param  rs                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public TicketLog(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}
public String getPriorityName() {
	return priorityName;
}
public void setPriorityName(String priorityName) {
	this.priorityName = priorityName;
}


	/**
	 *  Description of the Method
	 *
	 *@param  db                Description of Parameter
	 *@param  id                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public TicketLog(Connection db, int id) throws SQLException {

		if (id == -1) {
			throw new SQLException("Invalid Ticket Log Number");
		}

		PreparedStatement pst = null;
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT t.*, d.description as deptname, ct_eb.namelast || ', ' || ct_eb.namefirst as ebname, ct_at.namelast || ', ' || ct_at.namefirst as atname, tp.description as priorityname, ts.description as severityname " +
				"FROM ticketlog t " +
				"LEFT JOIN ticket_category tc ON (t.cat_code = tc.id) " +
				"LEFT JOIN contact ct_eb ON (t.enteredby = ct_eb.user_id) " +
				"LEFT JOIN contact ct_at ON (t.assigned_to = ct_at.user_id) " +
				"LEFT JOIN ticket_priority tp ON (t.pri_code = tp.code) " +
				"LEFT JOIN ticket_severity ts ON (t.scode = ts.code) " +
				"LEFT JOIN lookup_department d ON (t.department_code = d.code) " +
				"WHERE t.id = " + id + " ");

		Statement st = null;
		ResultSet rs = null;
		st = db.createStatement();
		rs = st.executeQuery(sql.toString());

		if (rs.next()) {
			buildRecord(rs);
		}
		else {
			rs.close();
			st.close();
			throw new SQLException("Ticket Log not found");
		}
		rs.close();
		st.close();
	}


	/**
	 *  Sets the Id attribute of the TicketLog object
	 *
	 *@param  tmp  The new Id value
	 *@since
	 */
	public void setId(int tmp) {
		this.id = tmp;
	}


	/**
	 *  Sets the Id attribute of the TicketLog object
	 *
	 *@param  tmp  The new Id value
	 *@since
	 */
	public void setId(String tmp) {
		this.id = Integer.parseInt(tmp);
	}

public String getAssignedToName() {
	return assignedToName;
}
public void setAssignedToName(String assignedToName) {
	this.assignedToName = assignedToName;
}

public String getSeverityName() {
	return severityName;
}
public void setSeverityName(String severityName) {
	this.severityName = severityName;
}


	/**
	 *  Sets the SystemMessage attribute of the TicketLog object
	 *
	 *@param  systemMessage  The new SystemMessage value
	 *@since
	 */
	public void setSystemMessage(boolean systemMessage) {
		this.systemMessage = systemMessage;
	}


	/**
	 *  Sets the TicketId attribute of the TicketLog object
	 *
	 *@param  tmp  The new TicketId value
	 *@since
	 */
	public void setTicketId(int tmp) {
		this.ticketId = tmp;
	}


	/**
	 *  Sets the TicketId attribute of the TicketLog object
	 *
	 *@param  tmp  The new TicketId value
	 *@since
	 */
	public void setTicketId(String tmp) {
		this.ticketId = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the DepartmentName attribute of the TicketLog object
	 *
	 *@param  departmentName  The new DepartmentName value
	 *@since
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}


	/**
	 *  Sets the EntryText attribute of the TicketLog object
	 *
	 *@param  tmp  The new EntryText value
	 *@since
	 */
	public void setEntryText(String tmp) {
		this.entryText = tmp;
	}


	/**
	 *  Sets the AssignedTo attribute of the TicketLog object
	 *
	 *@param  tmp  The new AssignedTo value
	 *@since
	 */
	public void setAssignedTo(int tmp) {
		this.assignedTo = tmp;
	}


	/**
	 *  Sets the AssignedTo attribute of the TicketLog object
	 *
	 *@param  tmp  The new AssignedTo value
	 *@since
	 */
	public void setAssignedTo(String tmp) {
		this.assignedTo = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the Closed attribute of the TicketLog object
	 *
	 *@param  tmp  The new Closed value
	 *@since
	 */
	public void setClosed(boolean tmp) {
		this.closed = tmp;
	}


	/**
	 *  Sets the Entered attribute of the TicketLog object
	 *
	 *@param  tmp  The new Entered value
	 *@since
	 */
	public void setEntered(String tmp) {
		this.entered = tmp;
	}


	/**
	 *  Sets the EnteredBy attribute of the TicketLog object
	 *
	 *@param  tmp  The new EnteredBy value
	 *@since
	 */
	public void setEnteredBy(int tmp) {
		this.enteredBy = tmp;
	}


	/**
	 *  Sets the Modified attribute of the TicketLog object
	 *
	 *@param  tmp  The new Modified value
	 *@since
	 */
	public void setModified(String tmp) {
		this.modified = tmp;
	}


	/**
	 *  Sets the ModifiedBy attribute of the TicketLog object
	 *
	 *@param  tmp  The new ModifiedBy value
	 *@since
	 */
	public void setModifiedBy(int tmp) {
		this.modifiedBy = tmp;
	}


	/**
	 *  Sets the PriorityCode attribute of the TicketLog object
	 *
	 *@param  tmp  The new PriorityCode value
	 *@since
	 */
	public void setPriorityCode(int tmp) {
		this.priorityCode = tmp;
	}


	/**
	 *  Sets the LevelCode attribute of the TicketLog object
	 *
	 *@param  tmp  The new LevelCode value
	 *@since
	 */
	public void setLevelCode(int tmp) {
		this.levelCode = tmp;
	}


	/**
	 *  Sets the DepartmentCode attribute of the TicketLog object
	 *
	 *@param  tmp  The new DepartmentCode value
	 *@since
	 */
	public void setDepartmentCode(int tmp) {
		this.departmentCode = tmp;
	}


	/**
	 *  Sets the CatCode attribute of the TicketLog object
	 *
	 *@param  tmp  The new CatCode value
	 *@since
	 */
	public void setCatCode(int tmp) {
		this.catCode = tmp;
	}


	/**
	 *  Sets the SeverityCode attribute of the TicketLog object
	 *
	 *@param  tmp  The new SeverityCode value
	 *@since
	 */
	public void setSeverityCode(int tmp) {
		this.severityCode = tmp;
	}


	/**
	 *  Sets the EnteredByName attribute of the TicketLog object
	 *
	 *@param  tmp  The new EnteredByName value
	 *@since
	 */
	public void setEnteredByName(String tmp) {
		this.enteredByName = tmp;
	}


	/**
	 *  Sets the ModifiedByName attribute of the TicketLog object
	 *
	 *@param  tmp  The new ModifiedByName value
	 *@since
	 */
	public void setModifiedByName(String tmp) {
		this.modifiedByName = tmp;
	}


	/**
	 *  Sets the CompanyName attribute of the TicketLog object
	 *
	 *@param  tmp  The new CompanyName value
	 *@since
	 */
	public void setCompanyName(String tmp) {
		this.companyName = tmp;
	}


	/**
	 *  Sets the CategoryName attribute of the TicketLog object
	 *
	 *@param  tmp  The new CategoryName value
	 *@since
	 */
	public void setCategoryName(String tmp) {
		this.categoryName = tmp;
	}


	/**
	 *  Gets the SystemMessage attribute of the TicketLog object
	 *
	 *@return    The SystemMessage value
	 *@since
	 */
	public boolean getSystemMessage() {
		return systemMessage;
	}


	/**
	 *  Gets the DepartmentName attribute of the TicketLog object
	 *
	 *@return    The DepartmentName value
	 *@since
	 */
	public String getDepartmentName() {
		return departmentName;
	}


	/**
	 *  Gets the Id attribute of the TicketLog object
	 *
	 *@return    The Id value
	 *@since
	 */
	public int getId() {
		return id;
	}


	/**
	 *  Gets the TicketId attribute of the TicketLog object
	 *
	 *@return    The TicketId value
	 *@since
	 */
	public int getTicketId() {
		return ticketId;
	}


	/**
	 *  Gets the EntryText attribute of the TicketLog object
	 *
	 *@return    The EntryText value
	 *@since
	 */
	public String getEntryText() {
		return entryText;
	}


	/**
	 *  Gets the AssignedTo attribute of the TicketLog object
	 *
	 *@return    The AssignedTo value
	 *@since
	 */
	public int getAssignedTo() {
		return assignedTo;
	}


	/**
	 *  Gets the Closed attribute of the TicketLog object
	 *
	 *@return    The Closed value
	 *@since
	 */
	public boolean getClosed() {
		return closed;
	}


	/**
	 *  Gets the Entered attribute of the TicketLog object
	 *
	 *@return    The Entered value
	 *@since
	 */
	public String getEntered() {
		return entered;
	}


	/**
	 *  Gets the EnteredBy attribute of the TicketLog object
	 *
	 *@return    The EnteredBy value
	 *@since
	 */
	public int getEnteredBy() {
		return enteredBy;
	}


	/**
	 *  Gets the Modified attribute of the TicketLog object
	 *
	 *@return    The Modified value
	 *@since
	 */
	public String getModified() {
		return modified;
	}


	/**
	 *  Gets the ModifiedBy attribute of the TicketLog object
	 *
	 *@return    The ModifiedBy value
	 *@since
	 */
	public int getModifiedBy() {
		return modifiedBy;
	}


	/**
	 *  Gets the PriorityCode attribute of the TicketLog object
	 *
	 *@return    The PriorityCode value
	 *@since
	 */
	public int getPriorityCode() {
		return priorityCode;
	}


	/**
	 *  Gets the LevelCode attribute of the TicketLog object
	 *
	 *@return    The LevelCode value
	 *@since
	 */
	public int getLevelCode() {
		return levelCode;
	}


	/**
	 *  Gets the DepartmentCode attribute of the TicketLog object
	 *
	 *@return    The DepartmentCode value
	 *@since
	 */
	public int getDepartmentCode() {
		return departmentCode;
	}


	/**
	 *  Gets the CatCode attribute of the TicketLog object
	 *
	 *@return    The CatCode value
	 *@since
	 */
	public int getCatCode() {
		return catCode;
	}


	/**
	 *  Gets the SeverityCode attribute of the TicketLog object
	 *
	 *@return    The SeverityCode value
	 *@since
	 */
	public int getSeverityCode() {
		return severityCode;
	}


	/**
	 *  Gets the EnteredByName attribute of the TicketLog object
	 *
	 *@return    The EnteredByName value
	 *@since
	 */
	public String getEnteredByName() {
		return enteredByName;
	}


	/**
	 *  Gets the ModifiedByName attribute of the TicketLog object
	 *
	 *@return    The ModifiedByName value
	 *@since
	 */
	public String getModifiedByName() {
		return modifiedByName;
	}


	/**
	 *  Gets the CompanyName attribute of the TicketLog object
	 *
	 *@return    The CompanyName value
	 *@since
	 */
	public String getCompanyName() {
		return companyName;
	}


	/**
	 *  Gets the CategoryName attribute of the TicketLog object
	 *
	 *@return    The CategoryName value
	 *@since
	 */
	public String getCategoryName() {
		return categoryName;
	}



	/**
	 *  Description of the Method
	 *
	 *@param  db                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public void insert(Connection db) throws SQLException {

		if (ticketId == -1) {
			throw new SQLException("Log Entry must be associated to a Ticket");
		}

		StringBuffer sql = new StringBuffer();

		try {

			db.setAutoCommit(false);

			sql.append(
					"INSERT INTO TICKETLOG (enteredby, modifiedby, pri_code, level_code, department_code, cat_code, scode, ticketid, comment, closed ) " +
					"VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, this.getEnteredBy());
			pst.setInt(++i, this.getModifiedBy());
			pst.setInt(++i, this.getPriorityCode());
			pst.setInt(++i, this.getLevelCode());
			pst.setInt(++i, this.getDepartmentCode());
			pst.setInt(++i, this.getCatCode());
			pst.setInt(++i, this.getSeverityCode());
			pst.setInt(++i, this.getTicketId());
			pst.setString(++i, this.getEntryText());
			pst.setBoolean(++i, this.getClosed());

			pst.execute();

			System.out.println("HERE: " + pst.toString());

			pst.close();

			Statement st = db.createStatement();
			ResultSet rs = st.executeQuery("select currval('ticketlog_id_seq')");

			if (rs.next()) {
				this.setId(rs.getInt(1));
			}
			rs.close();
			st.close();

			this.update(db, true);
			db.commit();
		}
		catch (SQLException e) {
			db.rollback();
			db.setAutoCommit(true);
			throw new SQLException(e.getMessage());
		}
		finally {
			db.setAutoCommit(true);
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  db                Description of Parameter
	 *@param  ticketId          Description of Parameter
	 *@param  enteredBy         Description of Parameter
	 *@param  modifiedBy        Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public void process(Connection db, int ticketId, int enteredBy, int modifiedBy) throws SQLException {
		if (ticketId != -1) {
			this.setEnteredBy(modifiedBy);
			this.setModifiedBy(modifiedBy);
			this.insert(db);
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  db                Description of Parameter
	 *@param  override          Description of Parameter
	 *@return                   Description of the Returned Value
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public int update(Connection db, boolean override) throws SQLException {
		int resultCount = 0;

		if (ticketId == -1) {
			throw new SQLException("Log Entry must be associated to a Ticket");
		}

		PreparedStatement pst = null;
		StringBuffer sql = new StringBuffer();

		sql.append(
				"UPDATE ticketlog SET assigned_to = ?, " +
				"modified = CURRENT_TIMESTAMP " +
				"WHERE id = ? ");
		if (!override) {
			sql.append("AND modified = ? ");
		}

		int i = 0;
		pst = db.prepareStatement(sql.toString());
		pst.setInt(++i, assignedTo);
		pst.setInt(++i, id);
		if (!override) {
			pst.setTimestamp(++i, java.sql.Timestamp.valueOf(this.getModified()));
		}

		resultCount = pst.executeUpdate();
		pst.close();

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
	public boolean delete(Connection db) throws SQLException {
		if (this.getId() == -1) {
			throw new SQLException("Ticket Log ID not specified.");
		}

		Statement st = db.createStatement();

		try {
			db.setAutoCommit(false);
			st.executeUpdate("DELETE FROM ticketlog WHERE id = " + this.getId());
			db.commit();
		}
		catch (SQLException e) {
			db.rollback();
		}
		finally {
			db.setAutoCommit(true);
			st.close();
		}
		return true;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  source  Description of Parameter
	 *@since
	 */
	public void createSysMsg(TicketLog source) {
		this.setEnteredBy(source.getModifiedBy());
		this.setEntered(source.getEntered());
		this.setDepartmentCode(source.getDepartmentCode());
		this.setAssignedTo(source.getAssignedTo());
		this.setTicketId(source.getId());
		this.setPriorityCode(source.getPriorityCode());
		this.setSeverityCode(source.getSeverityCode());
		this.setSystemMessage(true);
 		
		this.setClosed(source.getClosed());
		this.setPriorityName(source.getPriorityName());
		this.setSeverityName(source.getSeverityName());
		this.setAssignedToName(source.getAssignedToName());
		this.setEnteredByName(source.getEnteredByName());
		this.setDepartmentName(source.getDepartmentName());
	}


	/**
	 *  Gets the Valid attribute of the TicketLog object
	 *
	 *@return    The Valid value
	 *@since
	 */
	protected boolean isValid() {
		errors.clear();

		System.out.println(ticketId + " " + entryText + " " + enteredBy + " " + modifiedBy);

		if (ticketId == -1 || (entryText == null || entryText.trim().equals("")) || enteredBy == -1 || modifiedBy == -1) {
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
		this.setId(rs.getInt("id"));
		ticketId = rs.getInt("ticketid");

		entryText = rs.getString("comment");
		assignedTo = rs.getInt("assigned_to");
		
		closed = rs.getBoolean("closed");
		departmentName = rs.getString("deptname");

		priorityCode = rs.getInt("pri_code");
		levelCode = rs.getInt("level_code");
		departmentCode = rs.getInt("department_code");
		catCode = rs.getInt("cat_code");
		severityCode = rs.getInt("scode");
		enteredByName = rs.getString("ebname");
		assignedToName = rs.getString("atname");
		priorityName = rs.getString("priorityname");
		severityName = rs.getString("severityname");

		java.sql.Timestamp tmpDateCreated = rs.getTimestamp("entered");
		if (tmpDateCreated != null) {
			entered = shortDateTimeFormat.format(tmpDateCreated);
		}
		else {
			entered = "";
		}

		enteredBy = rs.getInt("enteredby");
		java.sql.Timestamp tmpLastModified = rs.getTimestamp("modified");
		modified = tmpLastModified.toString();
		modifiedBy = rs.getInt("modifiedby");
	}


	/**
	 *  Description of the Method
	 *
	 *@param  request  Description of Parameter
	 *@since
	 */
	protected void buildRecord(HttpServletRequest request) {
		this.setEntryText(request.getParameter("newticketlogentry"));
		this.setDepartmentCode(Integer.parseInt(request.getParameter("departmentCode")));
		this.setTicketId(Integer.parseInt(request.getParameter("id")));
	}
}

