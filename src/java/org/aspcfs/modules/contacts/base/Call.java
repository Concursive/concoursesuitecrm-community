//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import org.theseus.actions.*;
import java.sql.*;
import java.text.*;
import com.darkhorseventures.utils.DatabaseUtils;
import com.darkhorseventures.utils.DateUtils;

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
  private int enteredBy = -1;
  private String enteredName = "";
  private int modifiedBy = -1;
  private String modifiedName = "";
  private String contactName = "";
  private String alertText = "";

  private java.sql.Date alertDate = null;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;


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


  /**
   *  Constructor for the Call object
   *
   *@param  db                Description of Parameter
   *@param  callId            Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public Call(Connection db, String callId) throws SQLException {
          queryRecord(db, Integer.parseInt(callId));
  }
  
  public Call(Connection db, int callId) throws SQLException {
          queryRecord(db, callId);
  }
          
          
  public void queryRecord(Connection db, int callId) throws SQLException {
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
        "LEFT JOIN lookup_call_types t ON (c.call_type_id = t.code) " +
        "LEFT JOIN contact e ON (c.enteredby = e.user_id) " +
        "LEFT JOIN contact m ON (c.modifiedby = m.user_id) " +
        "WHERE call_id > -1 ");
    if (callId > -1) {
      sql.append("AND call_id = " + callId + " ");
    } else {
      throw new SQLException("Valid call ID not specified.");
    }

    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      st.close();
      throw new SQLException("Call record not found.");
    }
    rs.close();
    st.close();
  }


  /**
   *  Sets the oppId attribute of the Call object
   *
   *@param  oppId  The new oppId value
   */
  public void setOppId(int oppId) {
    this.oppId = oppId;
  }


  /**
   *  Sets the oppId attribute of the Call object
   *
   *@param  oppId  The new oppId value
   */
  public void setOppId(String oppId) {
    this.oppId = Integer.parseInt(oppId);
  }


  /**
   *  Sets the contactName attribute of the Call object
   *
   *@param  contactName  The new contactName value
   */
  public void setContactName(String contactName) {
    this.contactName = contactName;
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
   *  Sets the alertDate attribute of the Call object
   *
   *@param  alertDate  The new alertDate value
   */
  public void setAlertDate(java.sql.Date alertDate) {
    this.alertDate = alertDate;
  }


  /**
   *  Sets the alertDate attribute of the Call object
   *
   *@param  tmp  The new alertDate value
   */
  public void setAlertDate(String tmp) {
    this.alertDate = DateUtils.parseDateString(tmp);
    /**
    try {
      java.util.Date tmpDate = DateFormat.getDateInstance(3).parse(tmp);
      alertDate = new java.sql.Date(new java.util.Date().getTime());
      alertDate.setTime(tmpDate.getTime());
    } catch (Exception e) {
      alertDate = null;
    }
    */
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
    } catch (Exception e) {
    }
  }


  /**
   *  Sets the entered attribute of the Call object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the modified attribute of the Call object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the entered attribute of the Call object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the modified attribute of the Call object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
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

public String getAlertText() {
	return alertText;
}
public void setAlertText(String alertText) {
	this.alertText = alertText;
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
    } catch (Exception e) {
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
    } catch (Exception e) {
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
    } catch (Exception e) {
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
    } catch (Exception e) {
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
   *  Sets the EnteredBy attribute of the Call object
   *
   *@param  tmp  The new EnteredBy value
   *@since
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }

  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
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
  
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the oppId attribute of the Call object
   *
   *@return    The oppId value
   */
  public int getOppId() {
    return oppId;
  }


  /**
   *  Gets the contactName attribute of the Call object
   *
   *@return    The contactName value
   */
  public String getContactName() {
    return contactName;
  }


  /**
   *  Gets the alertDate attribute of the Call object
   *
   *@return    The alertDate value
   */
  public java.sql.Date getAlertDate() {
    return alertDate;
  }


  /**
   *  Gets the alertDateString attribute of the Call object
   *
   *@return    The alertDateString value
   */
  public String getAlertDateString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(alertDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the alertDateStringLongYear attribute of the Call object
   *
   *@return    The alertDateStringLongYear value
   */
  public String getAlertDateStringLongYear() {
    String tmp = "";
    try {
      SimpleDateFormat formatter = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.LONG);
      formatter.applyPattern("M/d/yyyy");
      return formatter.format(alertDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  public static String getAlertDateStringLongYear(java.sql.Date alertDate) {
    String tmp = "";
    try {
      SimpleDateFormat formatter = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.LONG);
      formatter.applyPattern("M/d/yyyy");
      return formatter.format(alertDate);
    }
    catch (NullPointerException e) {
    }
    return tmp;
  }
  
  
  /**
   *  Gets the entered attribute of the Call object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modified attribute of the Call object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedString attribute of the Call object
   *
   *@return    The modifiedString value
   */
  public String getModifiedString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(modified);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the enteredString attribute of the Call object
   *
   *@return    The enteredString value
   */
  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(entered);
    } catch (NullPointerException e) {
    }
    return tmp;
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
    } else {
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
   *  Gets the EnteredBy attribute of the Call object
   *
   *@return    The EnteredBy value
   *@since
   */
  public int getEnteredBy() {
    return enteredBy;
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
          return(insert(db));
  }
  
  public boolean insert(Connection db) throws SQLException {
    if (!isValid(db)) {
      return false;
    }

    StringBuffer sql = new StringBuffer();
    sql.append(
      "INSERT INTO call_log " +
      "(org_id, contact_id, opp_id, call_type_id, length, subject, notes, alertdate, alert, ");
                if (entered != null) {
                        sql.append("entered, ");
                }
                if (modified != null) {
                        sql.append("modified, ");
                }      
      sql.append("enteredBy, modifiedBy ) ");
      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ");
                if (entered != null) {
                        sql.append("?, ");
                }
                if (modified != null) {
                        sql.append("?, ");
                }
      sql.append("?, ?) ");
    int i = 0;
    
    PreparedStatement pst = db.prepareStatement(sql.toString());
        if (this.getOrgId() > 0) {
                pst.setInt(++i, this.getOrgId());
        } else {
                pst.setNull(++i, java.sql.Types.INTEGER);
        }
        if (this.getContactId() > 0) {
                pst.setInt(++i, this.getContactId());
        } else {
                pst.setNull(++i, java.sql.Types.INTEGER);
        }
        if (this.getOppId() > 0) {
                pst.setInt(++i, this.getOppId());
        } else {
                pst.setNull(++i, java.sql.Types.INTEGER);
        }
        if (this.getCallTypeId() > 0) {
                pst.setInt(++i, this.getCallTypeId());
        } else {
                pst.setNull(++i, java.sql.Types.INTEGER);
        }
        
    pst.setInt(++i, this.getLength());
    pst.setString(++i, this.getSubject());
    pst.setString(++i, this.getNotes());
    if (alertDate == null) {
      pst.setNull(++i, java.sql.Types.DATE);
    } else {
      pst.setDate(++i, this.getAlertDate());
    }
    pst.setString(++i, this.getAlertText());
        if (entered != null) {
                pst.setTimestamp(++i, entered);
        }
        if (modified != null) {
                pst.setTimestamp(++i, modified);
        }
      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getModifiedBy());
    pst.execute();
    pst.close();

    id = DatabaseUtils.getCurrVal(db, "call_log_call_id_seq");
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

    int recordCount = 0;

    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM call_log " +
        "WHERE call_id = ? ");
    pst.setInt(1, id);
    recordCount = pst.executeUpdate();
    pst.close();

    if (recordCount == 0) {
      errors.put("actionError", "Call could not be deleted because it no longer exists.");
      return false;
    } else {
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
        "SET call_type_id = ?, length = ?, subject = ?, notes = ?, " +
        "modifiedby = ?, alertdate = ?, alert = ?, " +
        "modified = CURRENT_TIMESTAMP " +
        "WHERE call_id = ? " +
        "AND modified = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    
        if (this.getCallTypeId() > 0) {
                pst.setInt(++i, this.getCallTypeId());
        } else {
                pst.setNull(++i, java.sql.Types.INTEGER);
        }
        
    pst.setInt(++i, length);
    pst.setString(++i, subject);
    pst.setString(++i, notes);
    pst.setInt(++i, this.getModifiedBy());
    if (alertDate == null) {
      pst.setNull(++i, java.sql.Types.DATE);
    } else {
      pst.setDate(++i, this.getAlertDate());
    }
    pst.setString(++i, this.getAlertText());
    pst.setInt(++i, this.getId());
    pst.setTimestamp(++i, this.getModified());

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

    if (contactId == -1 && orgId == -1 && oppId == -1) {
      errors.put("actionError", "Call is not associated with a valid record");
    }

    if (length < 0) {
      errors.put("lengthError", "Length cannot be less than 0");
    }

    if (hasErrors()) {
      return false;
    } else {
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
    //call_log table
    id = rs.getInt("call_id");
    orgId = rs.getInt("org_id");
    if (rs.wasNull()) {
            orgId = -1;
    }
    contactId = rs.getInt("contact_id");
    if (rs.wasNull()) {
            contactId = -1;
    }    
    oppId = rs.getInt("opp_id");
    if (rs.wasNull()) {
            oppId = -1;
    }
    length = rs.getInt("length");
    subject = rs.getString("subject");
    notes = rs.getString("notes");
    alertDate = rs.getDate("alertdate");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    alertText = rs.getString("alert");
    //lookup_call_types table
    callTypeId = rs.getInt("code");
    if (rs.wasNull()) {
            callTypeId = -1;
    }
    callType = rs.getString("description");
    //contact table
    enteredName = fullName(rs.getString("efirst"), rs.getString("elast"));
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

