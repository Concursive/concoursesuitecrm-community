//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import org.theseus.actions.*;
import java.sql.*;
import java.text.*;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    January 8, 2002
 */
public class Call extends GenericBean {

	private int id = -1;
	private int orgId = -1;
	private int contactId = -1;
	private int callTypeId = -1;
	private int oppId = -1;
	private String callType = "";
	private int length = 0;
	private String subject = "";
	private String notes = "";
	private String entered = "";
	private String alertDate = "";
	private int enteredBy = -1;
	private String enteredName = "";
	private String modified = null;
	private int modifiedBy = -1;
	private String modifiedName = "";
	private String contactName = "";


	/**
	 *  Constructor for the Call object
	 *
	 *@since
	 */
	public Call() { }


	/**
	 *  Constructor for the Call object
	 *
	 *@param  rs                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public Call(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}
public int getOppId() {
	return oppId;
}
public void setOppId(int oppId) {
	this.oppId = oppId;
}
public void setOppId(String oppId) {
	this.oppId = Integer.parseInt(oppId);
}
public String getContactName() {
	return contactName;
}
public void setContactName(String contactName) {
	this.contactName = contactName;
}

	/**
	 *  Constructor for the Call object
	 *
	 *@param  db                Description of Parameter
	 *@param  callId            Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public Call(Connection db, String callId) throws SQLException {

		Statement st = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT c.*, t.*, " +
				"e.namefirst as efirst, e.namelast as elast, " +
				"m.namefirst as mfirst, m.namelast as mlast, " +
				"ct.namefirst as ctfirst, ct.namelast as ctlast " +
				"FROM call_log c " +
				"LEFT JOIN contact ct ON (c.contact_id = ct.contact_id) " +
				"  LEFT JOIN lookup_call_types t ON (c.call_type_id = t.code), " +
				"contact e LEFT JOIN access a1 ON (e.contact_id = a1.contact_id), " +
				"contact m LEFT JOIN access a2 ON (m.contact_id = a2.contact_id) " +
				
				"WHERE c.enteredby = a1.user_id " +
				"AND c.modifiedby = a2.user_id ");

		if (callId != null && !callId.equals("")) {
			sql.append("AND call_id = " + callId + " ");
		}
		else {
			throw new SQLException("Call ID not specified.");
		}

		st = db.createStatement();
		rs = st.executeQuery(sql.toString());
		if (rs.next()) {
			buildRecord(rs);
		}
		else {
			rs.close();
			st.close();
			throw new SQLException("Call record not found.");
		}
		rs.close();
		st.close();
	}


	/**
	 *  Sets the AlertDate attribute of the Call object
	 *
	 *@param  alertDate  The new AlertDate value
	 *@since
	 */
	public void setAlertDate(String alertDate) {
		this.alertDate = alertDate;
	}


	/**
	 *  Sets the Id attribute of the Call object
	 *
	 *@param  tmp  The new Id value
	 *@since
	 */
	public void setId(int tmp) {
		this.id = tmp;
	}


	/**
	 *  Sets the Id attribute of the Call object
	 *
	 *@param  tmp  The new Id value
	 *@since
	 */
	public void setId(String tmp) {
		try {
			this.id = Integer.parseInt(tmp);
		}
		catch (Exception e) {
		}
	}


	/**
	 *  Sets the OrgId attribute of the Call object
	 *
	 *@param  tmp  The new OrgId value
	 *@since
	 */
	public void setOrgId(int tmp) {
		this.orgId = tmp;
	}


	/**
	 *  Sets the OrgId attribute of the Call object
	 *
	 *@param  tmp  The new OrgId value
	 *@since
	 */
	public void setOrgId(String tmp) {
		try {
			this.orgId = Integer.parseInt(tmp);
		}
		catch (Exception e) {
		}
	}


	/**
	 *  Sets the ContactId attribute of the Call object
	 *
	 *@param  tmp  The new ContactId value
	 *@since
	 */
	public void setContactId(int tmp) {
		this.contactId = tmp;
	}


	/**
	 *  Sets the ContactId attribute of the Call object
	 *
	 *@param  tmp  The new ContactId value
	 *@since
	 */
	public void setContactId(String tmp) {
		try {
			this.contactId = Integer.parseInt(tmp);
		}
		catch (Exception e) {
		}
	}


	/**
	 *  Sets the CallTypeId attribute of the Call object
	 *
	 *@param  tmp  The new CallTypeId value
	 *@since
	 */
	public void setCallTypeId(int tmp) {
		this.callTypeId = tmp;
	}


	/**
	 *  Sets the CallTypeId attribute of the Call object
	 *
	 *@param  tmp  The new CallTypeId value
	 *@since
	 */
	public void setCallTypeId(String tmp) {
		try {
			this.callTypeId = Integer.parseInt(tmp);
		}
		catch (Exception e) {
		}
	}


	/**
	 *  Sets the Length attribute of the Call object
	 *
	 *@param  tmp  The new Length value
	 *@since
	 */
	public void setLength(int tmp) {
		this.length = tmp;
	}


	/**
	 *  Sets the Length attribute of the Call object
	 *
	 *@param  tmp  The new Length value
	 *@since
	 */
	public void setLength(String tmp) {
		try {
			this.length = Integer.parseInt(tmp);
		}
		catch (Exception e) {
		}
	}


	/**
	 *  Sets the Subject attribute of the Call object
	 *
	 *@param  tmp  The new Subject value
	 *@since
	 */
	public void setSubject(String tmp) {
		this.subject = tmp;
	}


	/**
	 *  Sets the Notes attribute of the Call object
	 *
	 *@param  tmp  The new Notes value
	 *@since
	 */
	public void setNotes(String tmp) {
		this.notes = tmp;
	}


	/**
	 *  Sets the Entered attribute of the Call object
	 *
	 *@param  tmp  The new Entered value
	 *@since
	 */
	public void setEntered(String tmp) {
		this.entered = tmp;
	}


	/**
	 *  Sets the EnteredBy attribute of the Call object
	 *
	 *@param  tmp  The new EnteredBy value
	 *@since
	 */
	public void setEnteredBy(int tmp) {
		this.enteredBy = tmp;
	}


	/**
	 *  Sets the Modified attribute of the Call object
	 *
	 *@param  tmp  The new Modified value
	 *@since
	 */
	public void setModified(String tmp) {
		this.modified = tmp;
	}


	/**
	 *  Sets the ModifiedBy attribute of the Call object
	 *
	 *@param  tmp  The new ModifiedBy value
	 *@since
	 */
	public void setModifiedBy(int tmp) {
		this.modifiedBy = tmp;
	}


	/**
	 *  Gets the AlertDate attribute of the Call object
	 *
	 *@return    The AlertDate value
	 *@since
	 */
	public String getAlertDate() {
		return alertDate;
	}


	/**
	 *  Gets the Id attribute of the Call object
	 *
	 *@return    The Id value
	 *@since
	 */
	public int getId() {
		return id;
	}


	/**
	 *  Gets the OrgId attribute of the Call object
	 *
	 *@return    The OrgId value
	 *@since
	 */
	public int getOrgId() {
		return orgId;
	}


	/**
	 *  Gets the ContactId attribute of the Call object
	 *
	 *@return    The ContactId value
	 *@since
	 */
	public int getContactId() {
		return contactId;
	}


	/**
	 *  Gets the CallTypeId attribute of the Call object
	 *
	 *@return    The CallTypeId value
	 *@since
	 */
	public int getCallTypeId() {
		return callTypeId;
	}


	/**
	 *  Gets the CallType attribute of the Call object
	 *
	 *@return    The CallType value
	 *@since
	 */
	public String getCallType() {
		return callType;
	}


	/**
	 *  Gets the Length attribute of the Call object
	 *
	 *@return    The Length value
	 *@since
	 */
	public int getLength() {
		return length;
	}


	/**
	 *  Gets the LengthString attribute of the Call object
	 *
	 *@return    The LengthString value
	 *@since
	 */
	public String getLengthString() {
		return ("" + length);
	}


	/**
	 *  Gets the LengthText attribute of the Call object
	 *
	 *@return    The LengthText value
	 *@since
	 */
	public String getLengthText() {
		if (length > 0) {
			return (length + " min.");
		}
		else {
			return "";
		}
	}


	/**
	 *  Gets the Subject attribute of the Call object
	 *
	 *@return    The Subject value
	 *@since
	 */
	public String getSubject() {
		return subject;
	}


	/**
	 *  Gets the Notes attribute of the Call object
	 *
	 *@return    The Notes value
	 *@since
	 */
	public String getNotes() {
		return notes;
	}


	/**
	 *  Gets the Entered attribute of the Call object
	 *
	 *@return    The Entered value
	 *@since
	 */
	public String getEntered() {
		return entered;
	}


	/**
	 *  Gets the EnteredBy attribute of the Call object
	 *
	 *@return    The EnteredBy value
	 *@since
	 */
	public int getEnteredBy() {
		return enteredBy;
	}


	/**
	 *  Gets the Modified attribute of the Call object
	 *
	 *@return    The Modified value
	 *@since
	 */
	public String getModified() {
		return modified;
	}


	/**
	 *  Gets the ModifiedBy attribute of the Call object
	 *
	 *@return    The ModifiedBy value
	 *@since
	 */
	public int getModifiedBy() {
		return modifiedBy;
	}


	/**
	 *  Gets the EnteredName attribute of the Call object
	 *
	 *@return    The EnteredName value
	 *@since
	 */
	public String getEnteredName() {
		return enteredName;
	}


	/**
	 *  Gets the ModifiedName attribute of the Call object
	 *
	 *@return    The ModifiedName value
	 *@since
	 */
	public String getModifiedName() {
		return modifiedName;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  db                Description of Parameter
	 *@param  context           Description of Parameter
	 *@return                   Description of the Returned Value
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public boolean insert(Connection db, ActionContext context) throws SQLException {

		if (!isValid(db)) {
			return false;
		}

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO call_log ");
		sql.append("(org_id, contact_id, opp_id, call_type_id, length, subject, notes, enteredby, modifiedby, alertdate) ");
		sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

		int i = 0;
		PreparedStatement pst = db.prepareStatement(sql.toString());

		pst.setInt(++i, this.getOrgId());
		pst.setInt(++i, this.getContactId());
		pst.setInt(++i, this.getOppId());
		pst.setInt(++i, this.getCallTypeId());
		pst.setInt(++i, this.getLength());
		pst.setString(++i, this.getSubject());
		pst.setString(++i, this.getNotes());
		pst.setInt(++i, this.getEnteredBy());
		pst.setInt(++i, this.getModifiedBy());
		
		if (alertDate == null || alertDate.equals("")) {
			pst.setNull(++i, java.sql.Types.DATE);
		} else {
			pst.setDate(++i, convertStringToSqlDate(this.getAlertDate(), DateFormat.SHORT));
		}

		pst.execute();
		pst.close();

		Statement st = db.createStatement();
		ResultSet rs = st.executeQuery("select currval('call_log_call_id_seq')");
		if (rs.next()) {
			this.setId(rs.getInt(1));
		}
		rs.close();
		st.close();

		return true;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  db                Description of Parameter
	 *@param  context           Description of Parameter
	 *@return                   Description of the Returned Value
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public boolean delete(Connection db, ActionContext context) throws SQLException {
		if (this.getId() == -1) {
			throw new SQLException("ID was not specified");
		}

		int recordCount = 0;

		Statement st = db.createStatement();
		recordCount = st.executeUpdate(
				"DELETE FROM call_log " +
				"WHERE call_id = " + id + " ");
		st.close();

		if (recordCount == 0) {
			errors.put("actionError", "Call could not be deleted because it no longer exists.");
			return false;
		}
		else {
			return true;
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  db                Description of Parameter
	 *@param  context           Description of Parameter
	 *@return                   Description of the Returned Value
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public int update(Connection db, ActionContext context) throws SQLException {
		if (this.getId() == -1) {
			throw new SQLException("Call ID was not specified");
		}

		if (!isValid(db)) {
			return -1;
		}

		int resultCount = 0;

		PreparedStatement pst = null;
		StringBuffer sql = new StringBuffer();

		sql.append(
				"UPDATE call_log " +
				"SET call_type_id = ?, length = ?, subject = ?, notes = ?, modifiedby = ?, alertdate = ?, modified = CURRENT_TIMESTAMP " +
				"WHERE call_id = ? " +
				"AND modified = ? ");

		int i = 0;
		pst = db.prepareStatement(sql.toString());
		pst.setInt(++i, callTypeId);
		pst.setInt(++i, length);
		pst.setString(++i, subject);
		pst.setString(++i, notes);
		pst.setInt(++i, this.getModifiedBy());
		if (alertDate == null || alertDate.equals("")) {
			pst.setNull(++i, java.sql.Types.DATE);
		} else {
			pst.setDate(++i, convertStringToSqlDate(this.getAlertDate(), DateFormat.SHORT));
		}
		pst.setInt(++i, this.getId());
		pst.setTimestamp(++i, java.sql.Timestamp.valueOf(this.getModified()));

		resultCount = pst.executeUpdate();
		pst.close();

		return resultCount;
	}


	/**
	 *  Gets the Valid attribute of the Call object
	 *
	 *@param  db                Description of Parameter
	 *@return                   The Valid value
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	protected boolean isValid(Connection db) throws SQLException {
		errors.clear();
		if ((subject == null || subject.trim().equals("")) &&
				(notes == null || notes.trim().equals(""))) {
			errors.put("actionError", "Cannot insert a blank record");
		}

		if (contactId == -1 && orgId == -1) {
			errors.put("actionError", "Record is not associated with a valid record");
		}

		if (length < 0) {
			errors.put("lengthError", "Length cannot be less than 0");
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
		id = rs.getInt("call_id");
		orgId = rs.getInt("org_id");
		contactId = rs.getInt("contact_id");
		oppId = rs.getInt("opp_id");
		callTypeId = rs.getInt("code");
		callType = rs.getString("description");
		length = rs.getInt("length");
		subject = rs.getString("subject");
		notes = rs.getString("notes");

		java.sql.Timestamp tmpDateCreated = rs.getTimestamp("entered");
		if (tmpDateCreated != null) {
			entered = shortDateTimeFormat.format(tmpDateCreated);
		}
		else {
			entered = "";
		}
		/**
		java.sql.Timestamp tmpAlertDate = rs.getTimestamp("alertdate");
		if (tmpAlertDate != null) {
			alertDate = shortDateTimeFormat.format(tmpAlertDate);
		}
		else {
			alertDate = "";
		}
		*/
		
		java.sql.Date thisAlertDate = rs.getDate("alertdate");
		if (thisAlertDate != null) {
			alertDate = shortDateFormat.format(thisAlertDate);
		} else {
			alertDate = "";
		}

		enteredBy = rs.getInt("enteredby");
		enteredName = fullName(rs.getString("efirst"), rs.getString("elast"));

		java.sql.Timestamp tmpLastModified = rs.getTimestamp("modified");
		modified = tmpLastModified.toString();
		modifiedBy = rs.getInt("modifiedby");
		modifiedName = fullName(rs.getString("mfirst"), rs.getString("mlast"));
		contactName = fullName(rs.getString("ctfirst"), rs.getString("ctlast"));
	}


	/**
	 *  Description of the Method
	 *
	 *@param  fn  Description of Parameter
	 *@param  ln  Description of Parameter
	 *@return     Description of the Returned Value
	 *@since
	 */
	private String fullName(String fn, String ln) {
		return ((fn.trim() + " " + ln.trim()).trim());
	}

}

