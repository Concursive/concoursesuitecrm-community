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
public class Ticket extends GenericBean {

	Contact thisContact = new Contact();

	private String errorMessage = "";
	private int id = -1;
	private int orgId = -1;
	private int contactId = -1;
	private int owner = -1;
	private int assignedTo = -1;

	private String problem = "";
	private String comment = "";
	private String solution = "";

	private int priorityCode = -1;
	private int levelCode = -1;
	private int departmentCode = -1;
	private int sourceCode = -1;
	private int catCode = 0;
	private int subCat1 = 0;
	private int subCat2 = 0;
	private int subCat3 = 0;
	private int severityCode = -1;

	private String entered = "";
	private int enteredBy = -1;
	private String modified = null;
	private int modifiedBy = -1;
	private String closed = null;

	private String ownerName = "";
	private String enteredByName = "";
	private String modifiedByName = "";

	private String companyName = "";
	private String categoryName = "";
	private String departmentName = "";
	private String priorityName = "";
	private String severityName = "";
	private String sourceName = "";

	private String newticketlogentry = "";

	private boolean closeIt = false;

	private int ageOf = 0;

	private TicketLogList history = new TicketLogList();


	/**
	 *  Constructor for the Ticket object, creates an empty Ticket
	 *
	 *@since    1.0
	 */
	public Ticket() { }


	/**
	 *  Constructor for the Ticket object
	 *
	 *@param  rs                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public Ticket(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  db                Description of Parameter
	 *@param  id                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public Ticket(Connection db, int id) throws SQLException {

		if (id == -1) {
			throw new SQLException("Invalid Ticket Number");
		}

		PreparedStatement pst = null;
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT t.*, o.name as orgname, ld.description as dept, tp.description as ticpri, " +
        "ts.description as ticsev, tc.description as catname, " +
        "ct_eb.namelast || ', ' || ct_eb.namefirst as eb_name, ct_mb.namelast || ', ' || ct_mb.namefirst as mb_name, ct_owner.namelast || ', ' || ct_owner.namefirst as owner_name,  " +
				"lu_ts.description as sourcename " +
				"FROM ticket t " +
				"LEFT JOIN organization o ON (t.org_id = o.org_id) " +
				"LEFT JOIN lookup_department ld ON (t.department_code = ld.code) " +
				"LEFT JOIN ticket_priority tp ON (t.pri_code = tp.code) " +
				"LEFT JOIN ticket_severity ts ON (t.scode = ts.code) " +
				"LEFT JOIN ticket_category tc ON (t.cat_code = tc.id) " +
				"LEFT JOIN contact ct_owner ON (t.assigned_to = ct_owner.user_id) " +
				"LEFT JOIN contact ct_eb ON (t.enteredby = ct_eb.user_id) " +
				"LEFT JOIN contact ct_mb ON (t.modifiedby = ct_mb.user_id) " +
				"LEFT JOIN lookup_ticketsource lu_ts ON (t.source_code = lu_ts.code) " +
				"WHERE t.ticketid = " + id + " ");

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
			throw new SQLException("Ticket not found");
		}
		rs.close();
		st.close();

		thisContact = new Contact(db, "" + this.getContactId());
		history.setTicketId(this.getId());
		history.buildList(db);
	}


	/**
	 *  Sets the ThisContact attribute of the Ticket object
	 *
	 *@param  thisContact  The new ThisContact value
	 *@since
	 */
	public void setThisContact(Contact thisContact) {
		this.thisContact = thisContact;
	}


	/**
	 *  Sets the Newticketlogentry attribute of the Ticket object
	 *
	 *@param  newticketlogentry  The new Newticketlogentry value
	 *@since
	 */
	public void setNewticketlogentry(String newticketlogentry) {
		this.newticketlogentry = newticketlogentry;
	}


	/**
	 *  Sets the AssignedTo attribute of the Ticket object
	 *
	 *@param  assignedTo  The new AssignedTo value
	 *@since
	 */
	public void setAssignedTo(int assignedTo) {
		this.assignedTo = assignedTo;
	}


	/**
	 *  Sets the SubCat1 attribute of the Ticket object
	 *
	 *@param  tmp  The new SubCat1 value
	 *@since
	 */
	public void setSubCat1(int tmp) {
		this.subCat1 = tmp;
	}


	/**
	 *  Sets the SubCat2 attribute of the Ticket object
	 *
	 *@param  tmp  The new SubCat2 value
	 *@since
	 */
	public void setSubCat2(int tmp) {
		this.subCat2 = tmp;
	}


	/**
	 *  Sets the SourceName attribute of the Ticket object
	 *
	 *@param  sourceName  The new SourceName value
	 *@since
	 */
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getPaddedId() {
		String padded = ("" + this.getId());
		
		while (padded.length() < 6) {
			padded = "0" + padded;
		}
		
		return padded;
	}

	/**
	 *  Sets the SubCat3 attribute of the Ticket object
	 *
	 *@param  tmp  The new SubCat3 value
	 *@since
	 */
	public void setSubCat3(int tmp) {
		this.subCat3 = tmp;
	}


	/**
	 *  Sets the SubCat1 attribute of the Ticket object
	 *
	 *@param  tmp  The new SubCat1 value
	 *@since
	 */
	public void setSubCat1(String tmp) {
		this.subCat1 = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the SubCat2 attribute of the Ticket object
	 *
	 *@param  tmp  The new SubCat2 value
	 *@since
	 */
	public void setSubCat2(String tmp) {
		this.subCat2 = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the SubCat3 attribute of the Ticket object
	 *
	 *@param  tmp  The new SubCat3 value
	 *@since
	 */
	public void setSubCat3(String tmp) {
		this.subCat3 = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the AssignedTo attribute of the Ticket object
	 *
	 *@param  assignedTo  The new AssignedTo value
	 *@since
	 */
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = Integer.parseInt(assignedTo);
	}


	/**
	 *  Sets the DepartmentName attribute of the Ticket object
	 *
	 *@param  departmentName  The new DepartmentName value
	 *@since
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}


	/**
	 *  Sets the CloseIt attribute of the Ticket object
	 *
	 *@param  closeIt  The new CloseIt value
	 *@since
	 */
	public void setCloseIt(boolean closeIt) {
		this.closeIt = closeIt;
	}


	/**
	 *  Sets the SeverityName attribute of the Ticket object
	 *
	 *@param  severityName  The new SeverityName value
	 *@since
	 */
	public void setSeverityName(String severityName) {
		this.severityName = severityName;
	}


	/**
	 *  Sets the ErrorMessage attribute of the Ticket object
	 *
	 *@param  tmp  The new ErrorMessage value
	 *@since
	 */
	public void setErrorMessage(String tmp) {
		this.errorMessage = tmp;
	}


	/**
	 *  Sets the History attribute of the Ticket object
	 *
	 *@param  history  The new History value
	 *@since
	 */
	public void setHistory(TicketLogList history) {
		this.history = history;
	}


	/**
	 *  Sets the Id attribute of the Ticket object
	 *
	 *@param  tmp  The new Id value
	 *@since
	 */
	public void setId(int tmp) {
		this.id = tmp;
		history.setTicketId(tmp);
	}


	/**
	 *  Sets the Id attribute of the Ticket object
	 *
	 *@param  tmp  The new Id value
	 *@since
	 */
	public void setId(String tmp) {
		this.id = Integer.parseInt(tmp);
		history.setTicketId(Integer.parseInt(tmp));
	}


	/**
	 *  Sets the CompanyName attribute of the Ticket object
	 *
	 *@param  companyName  The new CompanyName value
	 *@since
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	/**
	 *  Sets the OrgId attribute of the Ticket object
	 *
	 *@param  tmp  The new OrgId value
	 *@since
	 */
	public void setOrgId(int tmp) {
		this.orgId = tmp;
	}


	/**
	 *  Sets the OrgId attribute of the Ticket object
	 *
	 *@param  tmp  The new OrgId value
	 *@since
	 */
	public void setOrgId(String tmp) {
		this.orgId = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the ContactId attribute of the Ticket object
	 *
	 *@param  tmp  The new ContactId value
	 *@since
	 */
	public void setContactId(int tmp) {
		this.contactId = tmp;
	}


	/**
	 *  Sets the PriorityName attribute of the Ticket object
	 *
	 *@param  priorityName  The new PriorityName value
	 *@since
	 */
	public void setPriorityName(String priorityName) {
		this.priorityName = priorityName;
	}


	/**
	 *  Sets the ContactId attribute of the Ticket object
	 *
	 *@param  tmp  The new ContactId value
	 *@since
	 */
	public void setContactId(String tmp) {
		this.contactId = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the AgeOf attribute of the Ticket object
	 *
	 *@param  ageOf  The new AgeOf value
	 *@since
	 */
	public void setAgeOf(int ageOf) {
		this.ageOf = ageOf;
	}


	/**
	 *  Sets the Owner attribute of the Ticket object
	 *
	 *@param  tmp  The new Owner value
	 *@since
	 */
	public void setOwner(int tmp) {
		this.owner = tmp;
	}


	/**
	 *  Sets the Owner attribute of the Ticket object
	 *
	 *@param  tmp  The new Owner value
	 *@since
	 */
	public void setOwner(String tmp) {
		this.owner = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the CategoryName attribute of the Ticket object
	 *
	 *@param  categoryName  The new CategoryName value
	 *@since
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	/**
	 *  Sets the Problem attribute of the Ticket object
	 *
	 *@param  tmp  The new Problem value
	 *@since
	 */
	public void setProblem(String tmp) {
		this.problem = tmp;
	}


	/**
	 *  Sets the Comment attribute of the Ticket object
	 *
	 *@param  tmp  The new Comment value
	 *@since
	 */
	public void setComment(String tmp) {
		this.comment = tmp;
	}


	/**
	 *  Sets the Solution attribute of the Ticket object
	 *
	 *@param  tmp  The new Solution value
	 *@since
	 */
	public void setSolution(String tmp) {
		this.solution = tmp;
	}


	/**
	 *  Sets the PriorityCode attribute of the Ticket object
	 *
	 *@param  tmp  The new PriorityCode value
	 *@since
	 */
	public void setPriorityCode(int tmp) {
		this.priorityCode = tmp;
	}


	/**
	 *  Sets the PriorityCode attribute of the Ticket object
	 *
	 *@param  tmp  The new PriorityCode value
	 *@since
	 */
	public void setPriorityCode(String tmp) {
		this.priorityCode = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the LevelCode attribute of the Ticket object
	 *
	 *@param  tmp  The new LevelCode value
	 *@since
	 */
	public void setLevelCode(int tmp) {
		this.levelCode = tmp;
	}


	/**
	 *  Sets the DepartmentCode attribute of the Ticket object
	 *
	 *@param  tmp  The new DepartmentCode value
	 *@since
	 */
	public void setDepartmentCode(int tmp) {
		this.departmentCode = tmp;
	}


	/**
	 *  Sets the DepartmentCode attribute of the Ticket object
	 *
	 *@param  tmp  The new DepartmentCode value
	 *@since
	 */
	public void setDepartmentCode(String tmp) {
		this.departmentCode = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the SourceCode attribute of the Ticket object
	 *
	 *@param  tmp  The new SourceCode value
	 *@since
	 */
	public void setSourceCode(int tmp) {
		this.sourceCode = tmp;
	}


	/**
	 *  Sets the SourceCode attribute of the Ticket object
	 *
	 *@param  tmp  The new SourceCode value
	 *@since
	 */
	public void setSourceCode(String tmp) {
		this.sourceCode = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the CatCode attribute of the Ticket object
	 *
	 *@param  tmp  The new CatCode value
	 *@since
	 */
	public void setCatCode(int tmp) {
		this.catCode = tmp;
	}


	/**
	 *  Sets the CatCode attribute of the Ticket object
	 *
	 *@param  tmp  The new CatCode value
	 *@since
	 */
	public void setCatCode(String tmp) {
		this.catCode = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the SeverityCode attribute of the Ticket object
	 *
	 *@param  tmp  The new SeverityCode value
	 *@since
	 */
	public void setSeverityCode(int tmp) {
		this.severityCode = tmp;
	}


	/**
	 *  Sets the SeverityCode attribute of the Ticket object
	 *
	 *@param  tmp  The new SeverityCode value
	 *@since
	 */
	public void setSeverityCode(String tmp) {
		this.severityCode = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the Entered attribute of the Ticket object
	 *
	 *@param  tmp  The new Entered value
	 *@since
	 */
	public void setEntered(String tmp) {
		this.entered = tmp;
	}


	/**
	 *  Sets the EnteredBy attribute of the Ticket object
	 *
	 *@param  tmp  The new EnteredBy value
	 *@since
	 */
	public void setEnteredBy(int tmp) {
		this.enteredBy = tmp;
	}


	/**
	 *  Sets the EnteredBy attribute of the Ticket object
	 *
	 *@param  tmp  The new EnteredBy value
	 *@since
	 */
	public void setEnteredBy(String tmp) {
		this.enteredBy = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the Modified attribute of the Ticket object
	 *
	 *@param  tmp  The new Modified value
	 *@since
	 */
	public void setModified(String tmp) {
		this.modified = tmp;
	}


	/**
	 *  Sets the ModifiedBy attribute of the Ticket object
	 *
	 *@param  tmp  The new ModifiedBy value
	 *@since
	 */
	public void setModifiedBy(int tmp) {
		this.modifiedBy = tmp;
	}


	/**
	 *  Sets the ModifiedBy attribute of the Ticket object
	 *
	 *@param  tmp  The new ModifiedBy value
	 *@since
	 */
	public void setModifiedBy(String tmp) {
		this.modifiedBy = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the Closed attribute of the Ticket object
	 *
	 *@param  tmp  The new Closed value
	 *@since
	 */
	public void setClosed(String tmp) {
		this.closed = tmp;
	}


	/**
	 *  Sets the OwnerName attribute of the Ticket object
	 *
	 *@param  tmp  The new OwnerName value
	 *@since
	 */
	public void setOwnerName(String tmp) {
		this.ownerName = tmp;
	}


	/**
	 *  Sets the EnteredByName attribute of the Ticket object
	 *
	 *@param  tmp  The new EnteredByName value
	 *@since
	 */
	public void setEnteredByName(String tmp) {
		this.enteredByName = tmp;
	}


	/**
	 *  Sets the ModifiedByName attribute of the Ticket object
	 *
	 *@param  tmp  The new ModifiedByName value
	 *@since
	 */
	public void setModifiedByName(String tmp) {
		this.modifiedByName = tmp;
	}


	/**
	 *  Sets the RequestItems attribute of the Ticket object
	 *
	 *@param  request  The new RequestItems value
	 *@since
	 */
	public void setRequestItems(HttpServletRequest request) {
		history = new TicketLogList(request, this.getModifiedBy());
	}


	/**
	 *  Gets the ThisContact attribute of the Ticket object
	 *
	 *@return    The ThisContact value
	 *@since
	 */
	public Contact getThisContact() {
		return thisContact;
	}

	/**
	 *  Gets the SourceName attribute of the Ticket object
	 *
	 *@return    The SourceName value
	 *@since
	 */
	public String getSourceName() {
		return sourceName;
	}


	/**
	 *  Gets the SubCat1 attribute of the Ticket object
	 *
	 *@return    The SubCat1 value
	 *@since
	 */
	public int getSubCat1() {
		return subCat1;
	}


	/**
	 *  Gets the SubCat2 attribute of the Ticket object
	 *
	 *@return    The SubCat2 value
	 *@since
	 */
	public int getSubCat2() {
		return subCat2;
	}


	/**
	 *  Gets the SubCat3 attribute of the Ticket object
	 *
	 *@return    The SubCat3 value
	 *@since
	 */
	public int getSubCat3() {
		return subCat3;
	}


	/**
	 *  Gets the Newticketlogentry attribute of the Ticket object
	 *
	 *@return    The Newticketlogentry value
	 *@since
	 */
	public String getNewticketlogentry() {
		return newticketlogentry;
	}


	/**
	 *  Gets the AssignedTo attribute of the Ticket object
	 *
	 *@return    The AssignedTo value
	 *@since
	 */
	public int getAssignedTo() {
		return assignedTo;
	}


	/**
	 *  Gets the CloseIt attribute of the Ticket object
	 *
	 *@return    The CloseIt value
	 *@since
	 */
	public boolean getCloseIt() {
		return closeIt;
	}


	/**
	 *  Gets the SeverityName attribute of the Ticket object
	 *
	 *@return    The SeverityName value
	 *@since
	 */
	public String getSeverityName() {
		return severityName;
	}


	/**
	 *  Gets the PriorityName attribute of the Ticket object
	 *
	 *@return    The PriorityName value
	 *@since
	 */
	public String getPriorityName() {
		return priorityName;
	}


	/**
	 *  Gets the DepartmentName attribute of the Ticket object
	 *
	 *@return    The DepartmentName value
	 *@since
	 */
	public String getDepartmentName() {
		return departmentName;
	}


	/**
	 *  Gets the History attribute of the Ticket object
	 *
	 *@return    The History value
	 *@since
	 */
	public TicketLogList getHistory() {
		return history;
	}


	/**
	 *  Gets the AgeOf attribute of the Ticket object
	 *
	 *@return    The AgeOf value
	 *@since
	 */
	public String getAgeOf() {
		return ageOf + "d";
	}


	/**
	 *  Gets the CategoryName attribute of the Ticket object
	 *
	 *@return    The CategoryName value
	 *@since
	 */
	public String getCategoryName() {
		return categoryName;
	}


	/**
	 *  Gets the CompanyName attribute of the Ticket object
	 *
	 *@return    The CompanyName value
	 *@since
	 */
	public String getCompanyName() {
		return companyName;
	}


	/**
	 *  Gets the ErrorMessage attribute of the Ticket object
	 *
	 *@return    The ErrorMessage value
	 *@since
	 */
	public String getErrorMessage() {
		return errorMessage;
	}


	/**
	 *  Gets the Id attribute of the Ticket object
	 *
	 *@return    The Id value
	 *@since
	 */
	public int getId() {
		return id;
	}


	/**
	 *  Gets the OrgId attribute of the Ticket object
	 *
	 *@return    The OrgId value
	 *@since
	 */
	public int getOrgId() {
		return orgId;
	}


	/**
	 *  Gets the ContactId attribute of the Ticket object
	 *
	 *@return    The ContactId value
	 *@since
	 */
	public int getContactId() {
		return contactId;
	}


	/**
	 *  Gets the Owner attribute of the Ticket object
	 *
	 *@return    The Owner value
	 *@since
	 */
	public int getOwner() {
		return owner;
	}


	/**
	 *  Gets the Problem attribute of the Ticket object
	 *
	 *@return    The Problem value
	 *@since
	 */
	public String getProblem() {
		return problem;
	}


	/**
	 *  Gets the Comment attribute of the Ticket object
	 *
	 *@return    The Comment value
	 *@since
	 */
	public String getComment() {
		return comment;
	}


	/**
	 *  Gets the Solution attribute of the Ticket object
	 *
	 *@return    The Solution value
	 *@since
	 */
	public String getSolution() {
		return solution;
	}


	/**
	 *  Gets the PriorityCode attribute of the Ticket object
	 *
	 *@return    The PriorityCode value
	 *@since
	 */
	public int getPriorityCode() {
		return priorityCode;
	}


	/**
	 *  Gets the LevelCode attribute of the Ticket object
	 *
	 *@return    The LevelCode value
	 *@since
	 */
	public int getLevelCode() {
		return levelCode;
	}


	/**
	 *  Gets the DepartmentCode attribute of the Ticket object
	 *
	 *@return    The DepartmentCode value
	 *@since
	 */
	public int getDepartmentCode() {
		return departmentCode;
	}


	/**
	 *  Gets the SourceCode attribute of the Ticket object
	 *
	 *@return    The SourceCode value
	 *@since
	 */
	public int getSourceCode() {
		return sourceCode;
	}


	/**
	 *  Gets the CatCode attribute of the Ticket object
	 *
	 *@return    The CatCode value
	 *@since
	 */
	public int getCatCode() {
		return catCode;
	}


	/**
	 *  Gets the SeverityCode attribute of the Ticket object
	 *
	 *@return    The SeverityCode value
	 *@since
	 */
	public int getSeverityCode() {
		return severityCode;
	}


	/**
	 *  Gets the Entered attribute of the Ticket object
	 *
	 *@return    The Entered value
	 *@since
	 */
	public String getEntered() {
		return entered;
	}


	/**
	 *  Gets the EnteredBy attribute of the Ticket object
	 *
	 *@return    The EnteredBy value
	 *@since
	 */
	public int getEnteredBy() {
		return enteredBy;
	}


	/**
	 *  Gets the Modified attribute of the Ticket object
	 *
	 *@return    The Modified value
	 *@since
	 */
	public String getModified() {
		return modified;
	}


	/**
	 *  Gets the ModifiedBy attribute of the Ticket object
	 *
	 *@return    The ModifiedBy value
	 *@since
	 */
	public int getModifiedBy() {
		return modifiedBy;
	}


	/**
	 *  Gets the Closed attribute of the Ticket object
	 *
	 *@return    The Closed value
	 *@since
	 */
	public String getClosed() {
		return closed;
	}


	/**
	 *  Gets the OwnerName attribute of the Ticket object
	 *
	 *@return    The OwnerName value
	 *@since
	 */
	public String getOwnerName() {
		if (ownerName == null || ownerName.equals("")) {
			return "--unassigned--";
		}
		else {
			return ownerName;
		}
	}


	/**
	 *  Gets the EnteredByName attribute of the Ticket object
	 *
	 *@return    The EnteredByName value
	 *@since
	 */
	public String getEnteredByName() {
		return enteredByName;
	}


	/**
	 *  Gets the ModifiedByName attribute of the Ticket object
	 *
	 *@return    The ModifiedByName value
	 *@since
	 */
	public String getModifiedByName() {
		return modifiedByName;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  db                Description of Parameter
	 *@return                   Description of the Returned Value
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public boolean insert(Connection db) throws SQLException {

		if (!isValid(db)) {
			return false;
		}

		StringBuffer sql = new StringBuffer();

		try {

			db.setAutoCommit(false);

			sql.append(
					"INSERT INTO TICKET (org_id, problem, enteredby, modifiedby, pri_code, department_code, cat_code, scode ) " +
					"VALUES ( ?, ?, ?, ?, ?, ?, ?, ? ) ");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, this.getOrgId());
			pst.setString(++i, this.getProblem());
			pst.setInt(++i, this.getEnteredBy());
			pst.setInt(++i, this.getModifiedBy());
			pst.setInt(++i, this.getPriorityCode());
			pst.setInt(++i, this.getDepartmentCode());
			pst.setInt(++i, this.getCatCode());
			pst.setInt(++i, this.getSeverityCode());

			pst.execute();
			pst.close();
			
			Statement st = db.createStatement();
			ResultSet rs = st.executeQuery("select currval('ticket_ticketid_seq')");
			if (rs.next()) {
				this.setId(rs.getInt(1));
			}
			rs.close();
			st.close();

			this.update(db);
      
      if (this.getAssignedTo() > -1 && !this.getCloseIt()) {
        Notification thisNotification = new Notification();
        thisNotification.setUserToNotify(this.getAssignedTo());
        thisNotification.setModule("Tickets");
        thisNotification.setItemId(this.getId());
        thisNotification.setItemModified(null);
        thisNotification.setSubject("New Ticket Assigned: " + this.getId());
        thisNotification.setMessageToSend(
          "A new ticket has been added to CFS and assigned to you:<br>&nbsp;<br>&nbsp;<br>" +
          "Ticket # " + this.getId() + "<br>" +
          "Problem: " + this.getProblem() + "<br><br>" +
          "###");
        thisNotification.setType(Notification.EMAIL);
        thisNotification.notifyUser(db);
      }
      
      
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
		return true;
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

		if (!isValid(db)) {
			return -1;
		}

		PreparedStatement pst = null;
		StringBuffer sql = new StringBuffer();

		sql.append(
				"UPDATE ticket SET comment = ?, department_code = ?, pri_code = ?, scode = ?, cat_code = ?, assigned_to = ?, " +
				"subcat_code1 = ?, subcat_code2 = ?, subcat_code3 = ?, source_code = ?, contact_id = ?, problem = ?, " +
				"modified = CURRENT_TIMESTAMP ");

		if (this.getCloseIt() == true) {
			sql.append(
					", closed = CURRENT_TIMESTAMP, solution = ? ");
		}

		sql.append(
				"WHERE ticketid = ? ");

		//if (!override) {
		//	sql.append("AND modified = ? ");
		//}

		int i = 0;
		pst = db.prepareStatement(sql.toString());
		pst.setString(++i, comment);
		pst.setInt(++i, departmentCode);
		pst.setInt(++i, priorityCode);
		pst.setInt(++i, severityCode);
		pst.setInt(++i, catCode);
		pst.setInt(++i, assignedTo);
		pst.setInt(++i, this.getSubCat1());
		pst.setInt(++i, this.getSubCat2());
		pst.setInt(++i, this.getSubCat3());
		pst.setInt(++i, this.getSourceCode());
		pst.setInt(++i, this.getContactId());
		pst.setString(++i, this.getProblem());

		if (this.getCloseIt() == true) {
			pst.setString(++i, this.getSolution());
		}

		pst.setInt(++i, id);

		//if (!override) {
		//	pst.setTimestamp(++i, java.sql.Timestamp.valueOf(this.getModified()));
		//}

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
			throw new SQLException("Ticket ID not specified.");
		}

		Statement st = db.createStatement();

		try {
			db.setAutoCommit(false);
			st.executeUpdate("DELETE FROM ticket WHERE ticketid = " + this.getId());
			st.executeUpdate("DELETE FROM ticketlog WHERE ticketid = " + this.getId());
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
	 *@param  db                Description of Parameter
	 *@return                   Description of the Returned Value
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public int update(Connection db) throws SQLException {

		int i = -1;

		if (!isValid(db)) {
			return -1;
		}

		try {
			db.setAutoCommit(false);
			i = this.update(db, false);

			//if (this.getNewticketlogentry() != null && !(this.getNewticketlogentry().equals(""))) {
			TicketLog thisEntry = new TicketLog();
			thisEntry.setEnteredBy(this.getModifiedBy());
			thisEntry.setDepartmentCode(this.getDepartmentCode());
			thisEntry.setAssignedTo(this.getAssignedTo());

			if (this.getNewticketlogentry() != null && !(this.getNewticketlogentry().equals(""))) {
				thisEntry.setEntryText(this.getNewticketlogentry());
			}

			thisEntry.setTicketId(this.getId());
			thisEntry.setPriorityCode(this.getPriorityCode());
			thisEntry.setSeverityCode(this.getSeverityCode());

			if (this.getCloseIt() == true) {
				thisEntry.setClosed(true);
			}

			history.addElement(thisEntry);
			//}

			Iterator hist = history.iterator();
			while (hist.hasNext()) {
				TicketLog thisLog = (TicketLog) hist.next();
				System.out.println("processing with vals: " + this.getId() + ", " + this.getEnteredBy() + ", " + this.getModifiedBy());
				thisLog.process(db, this.getId(), this.getEnteredBy(), this.getModifiedBy());
			}

			db.commit();
		}
		catch (SQLException e) {
			db.rollback();
			db.setAutoCommit(true);
			throw new SQLException(e.getMessage());
		}

		db.setAutoCommit(true);
		return i;
	}


	/**
	 *  Gets the Valid attribute of the Ticket object
	 *
	 *@param  db                Description of Parameter
	 *@return                   The Valid value
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	protected boolean isValid(Connection db) throws SQLException {
		errors.clear();

		if (problem == null || problem.trim().equals("")) {
			errors.put("problemError", "A problem description is required");
		}
		else if (closeIt == true && (solution == null || solution.trim().equals(""))) {
			errors.put("closedError", "A solution is required when closing a ticket");
		}
		
		else if (orgId == -1) {
			errors.put("orgIdError", "You must associate an Account with a Ticket");
		}
		
		else if (contactId == -1) {
			errors.put("contactIdError", "You must associate a Contact with a Ticket");
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
		this.setId(rs.getInt("ticketid"));
		orgId = rs.getInt("org_id");
		contactId = rs.getInt("contact_id");
		owner = rs.getInt("enteredby");
		problem = rs.getString("problem");
		comment = rs.getString("comment");
		solution = rs.getString("solution");

		companyName = rs.getString("orgname");
		departmentName = rs.getString("dept");
		priorityName = rs.getString("ticpri");
		severityName = rs.getString("ticsev");
		categoryName = rs.getString("catname");
		assignedTo = rs.getInt("assigned_to");

		//ageOf = Integer.parseInt(rs.getString("age"));

		priorityCode = rs.getInt("pri_code");
		levelCode = rs.getInt("level_code");
		departmentCode = rs.getInt("department_code");
		sourceCode = rs.getInt("source_code");
		catCode = rs.getInt("cat_code");
		sourceName = rs.getString("sourcename");

		subCat1 = rs.getInt("subcat_code1");
		subCat2 = rs.getInt("subcat_code2");
		subCat3 = rs.getInt("subcat_code3");

		severityCode = rs.getInt("scode");

		enteredByName = rs.getString("eb_name");
		modifiedByName = rs.getString("mb_name");
		ownerName = rs.getString("owner_name");
		closed = rs.getString("closed");

		java.sql.Timestamp tmpDateCreated = rs.getTimestamp("entered");
		if (tmpDateCreated != null) {
			entered = shortDateTimeFormat.format(tmpDateCreated);
      float ageCheck = ((System.currentTimeMillis() - tmpDateCreated.getTime())/86400000);
      ageOf = java.lang.Math.round(ageCheck);
		}	else {
			entered = "";
		}

		enteredBy = rs.getInt("enteredby");
		java.sql.Timestamp tmpLastModified = rs.getTimestamp("modified");
		modified = tmpLastModified.toString();
		modifiedBy = rs.getInt("modifiedby");
	}

}

