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
package org.aspcfs.modules.troubletickets.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.text.DateFormat;

/**
 *  Description of the Class
 *
 * @author     chris
 * @created    November 8, 2001
 * @version    $Id: TicketLog.java,v 1.25.12.1 2005/10/24 20:30:41 mrajkowski
 *      Exp $
 */
public class TicketLog extends GenericBean {

  private int id = -1;
  private int ticketId = -1;
  private String entryText = "";
  private int assignedTo = -1;
  private boolean closed = false;

  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;

  private int enteredBy = -1;
  private int modifiedBy = -1;

  private int priorityCode = -1;
  private int levelCode = -1;
  private int departmentCode = -1;
  private int catCode = -1;
  private int severityCode = -1;
  private int escalationCode = -1;
  private int stateId = -1;

  private String enteredByName = "";
  private String modifiedByName = "";

  private String companyName = "";
  private String categoryName = "";
  private String departmentName = "";
  private String stateName = null;

  private boolean systemMessage = false;
  private String assignedToName = "";
  private String priorityName = "";
  private String severityName = "";
  private String escalationName = "";


  /**
   *  Constructor for the Ticket object, creates an empty Ticket
   *
   * @since    1.0
   */
  public TicketLog() { }


  /**
   *  Constructor for the Ticket object
   *
   * @param  rs                Description of Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of Exception
   */
  public TicketLog(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of Parameter
   * @param  id                Description of Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of Exception
   */
  public TicketLog(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Ticket Log Number");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT t.*, " +
        "d.description as deptname, " +
        "tp.description AS priorityname, ts.description AS severityname, lu_te.description AS escalationname, " +
        "ct_eb.namelast AS eb_namelast, ct_eb.namefirst AS eb_namefirst, " +
        "ct_at.namelast AS at_namelast, ct_at.namefirst AS at_namefirst, " +
        "lu_ts.description AS state_name " +
        "FROM ticketlog t " +
        "LEFT JOIN ticket_category tc ON (t.cat_code = tc.id) " +
        "LEFT JOIN contact ct_eb ON (t.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_at ON (t.assigned_to = ct_at.user_id) " +
        "LEFT JOIN ticket_priority tp ON (t.pri_code = tp.code) " +
        "LEFT JOIN ticket_severity ts ON (t.scode = ts.code) " +
        "LEFT JOIN lookup_department d ON (t.department_code = d.code) " +
        "LEFT JOIN lookup_ticket_escalation lu_te ON (t.escalation_code = lu_te.code) " +
        "LEFT JOIN lookup_ticket_state lu_ts ON (t.state_id = lu_ts.code) " +
        "WHERE t.id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Ticket Log not found");
    }
  }


  /**
   *  Sets the priorityName attribute of the TicketLog object
   *
   * @param  priorityName  The new priorityName value
   */
  public void setPriorityName(String priorityName) {
    this.priorityName = priorityName;
  }


  /**
   *  Sets the Id attribute of the TicketLog object
   *
   * @param  tmp  The new Id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the Id attribute of the TicketLog object
   *
   * @param  tmp  The new Id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the TicketLog object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the modified attribute of the TicketLog object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the entered attribute of the TicketLog object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the modified attribute of the TicketLog object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the assignedToName attribute of the TicketLog object
   *
   * @param  assignedToName  The new assignedToName value
   */
  public void setAssignedToName(String assignedToName) {
    this.assignedToName = assignedToName;
  }


  /**
   *  Sets the severityName attribute of the TicketLog object
   *
   * @param  severityName  The new severityName value
   */
  public void setSeverityName(String severityName) {
    this.severityName = severityName;
  }


  /**
   *  Sets the escalationName attribute of the TicketLog object
   *
   * @param  tmp  The new escalationName value
   */
  public void setEscalationName(String tmp) {
    this.escalationName = tmp;
  }


  /**
   *  Sets the SystemMessage attribute of the TicketLog object
   *
   * @param  systemMessage  The new SystemMessage value
   */
  public void setSystemMessage(boolean systemMessage) {
    this.systemMessage = systemMessage;
  }


  /**
   *  Sets the TicketId attribute of the TicketLog object
   *
   * @param  tmp  The new TicketId value
   */
  public void setTicketId(int tmp) {
    this.ticketId = tmp;
  }


  /**
   *  Sets the TicketId attribute of the TicketLog object
   *
   * @param  tmp  The new TicketId value
   */
  public void setTicketId(String tmp) {
    this.ticketId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the DepartmentName attribute of the TicketLog object
   *
   * @param  departmentName  The new DepartmentName value
   */
  public void setDepartmentName(String departmentName) {
    this.departmentName = departmentName;
  }


  /**
   *  Sets the EntryText attribute of the TicketLog object
   *
   * @param  tmp  The new EntryText value
   */
  public void setEntryText(String tmp) {
    this.entryText = tmp;
  }


  /**
   *  Sets the AssignedTo attribute of the TicketLog object
   *
   * @param  tmp  The new AssignedTo value
   */
  public void setAssignedTo(int tmp) {
    this.assignedTo = tmp;
  }


  /**
   *  Sets the assignedTo attribute of the TicketLog object
   *
   * @param  tmp  The new assignedTo value
   */
  public void setAssignedTo(String tmp) {
    this.assignedTo = Integer.parseInt(tmp);
  }


  /**
   *  Sets the AssignedTo attribute of the TicketLog object
   *
   * @param  tmp  The new AssignedTo value
   */
  public void s(String tmp) {
    this.assignedTo = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Closed attribute of the TicketLog object
   *
   * @param  tmp  The new Closed value
   */
  public void setClosed(boolean tmp) {
    this.closed = tmp;
  }


  /**
   *  Sets the EnteredBy attribute of the TicketLog object
   *
   * @param  tmp  The new EnteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the TicketLog object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the ModifiedBy attribute of the TicketLog object
   *
   * @param  tmp  The new ModifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the TicketLog object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the PriorityCode attribute of the TicketLog object
   *
   * @param  tmp  The new PriorityCode value
   */
  public void setPriorityCode(int tmp) {
    this.priorityCode = tmp;
  }


  /**
   *  Sets the priorityCode attribute of the TicketLog object
   *
   * @param  tmp  The new priorityCode value
   */
  public void setPriorityCode(String tmp) {
    this.priorityCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the LevelCode attribute of the TicketLog object
   *
   * @param  tmp  The new LevelCode value
   */
  public void setLevelCode(int tmp) {
    this.levelCode = tmp;
  }


  /**
   *  Sets the levelCode attribute of the TicketLog object
   *
   * @param  tmp  The new levelCode value
   */
  public void setLevelCode(String tmp) {
    this.levelCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the DepartmentCode attribute of the TicketLog object
   *
   * @param  tmp  The new DepartmentCode value
   */
  public void setDepartmentCode(int tmp) {
    this.departmentCode = tmp;
  }


  /**
   *  Sets the departmentCode attribute of the TicketLog object
   *
   * @param  tmp  The new departmentCode value
   */
  public void setDepartmentCode(String tmp) {
    this.departmentCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the CatCode attribute of the TicketLog object
   *
   * @param  tmp  The new CatCode value
   */
  public void setCatCode(int tmp) {
    this.catCode = tmp;
  }


  /**
   *  Sets the catCode attribute of the TicketLog object
   *
   * @param  tmp  The new catCode value
   */
  public void setCatCode(String tmp) {
    this.catCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the SeverityCode attribute of the TicketLog object
   *
   * @param  tmp  The new SeverityCode value
   */
  public void setSeverityCode(int tmp) {
    this.severityCode = tmp;
  }


  /**
   *  Sets the severityCode attribute of the TicketLog object
   *
   * @param  tmp  The new severityCode value
   */
  public void setSeverityCode(String tmp) {
    this.severityCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the escalationCode attribute of the TicketLog object
   *
   * @param  tmp  The new escalationCode value
   */
  public void setEscalationCode(int tmp) {
    this.escalationCode = tmp;
  }


  /**
   *  Sets the escalationCode attribute of the TicketLog object
   *
   * @param  tmp  The new escalationCode value
   */
  public void setEscalationCode(String tmp) {
    this.escalationCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the EnteredByName attribute of the TicketLog object
   *
   * @param  tmp  The new EnteredByName value
   */
  public void setEnteredByName(String tmp) {
    this.enteredByName = tmp;
  }


  /**
   *  Sets the ModifiedByName attribute of the TicketLog object
   *
   * @param  tmp  The new ModifiedByName value
   */
  public void setModifiedByName(String tmp) {
    this.modifiedByName = tmp;
  }


  /**
   *  Sets the CompanyName attribute of the TicketLog object
   *
   * @param  tmp  The new CompanyName value
   */
  public void setCompanyName(String tmp) {
    this.companyName = tmp;
  }


  /**
   *  Sets the CategoryName attribute of the TicketLog object
   *
   * @param  tmp  The new CategoryName value
   */
  public void setCategoryName(String tmp) {
    this.categoryName = tmp;
  }


  /**
   *  Gets the priorityName attribute of the TicketLog object
   *
   * @return    The priorityName value
   */
  public String getPriorityName() {
    return priorityName;
  }


  /**
   *  Gets the entered attribute of the TicketLog object
   *
   * @return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modified attribute of the TicketLog object
   *
   * @return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedString attribute of the TicketLog object
   *
   * @return    The modifiedString value
   */
  public String getModifiedString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
          modified);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the enteredString attribute of the TicketLog object
   *
   * @return    The enteredString value
   */
  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
          entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the assignedToName attribute of the TicketLog object
   *
   * @return    The assignedToName value
   */
  public String getAssignedToName() {
    return assignedToName;
  }


  /**
   *  Gets the severityName attribute of the TicketLog object
   *
   * @return    The severityName value
   */
  public String getSeverityName() {
    return severityName;
  }


  /**
   *  Gets the escalationName attribute of the TicketLog object
   *
   * @return    The escalationName value
   */
  public String getEscalationName() {
    return escalationName;
  }


  /**
   *  Gets the SystemMessage attribute of the TicketLog object
   *
   * @return    The SystemMessage value
   */
  public boolean getSystemMessage() {
    return systemMessage;
  }


  /**
   *  Gets the DepartmentName attribute of the TicketLog object
   *
   * @return    The DepartmentName value
   */
  public String getDepartmentName() {
    return departmentName;
  }


  /**
   *  Gets the Id attribute of the TicketLog object
   *
   * @return    The Id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the TicketId attribute of the TicketLog object
   *
   * @return    The TicketId value
   */
  public int getTicketId() {
    return ticketId;
  }


  /**
   *  Gets the EntryText attribute of the TicketLog object
   *
   * @return    The EntryText value
   */
  public String getEntryText() {
    return entryText;
  }


  /**
   *  Gets the AssignedTo attribute of the TicketLog object
   *
   * @return    The AssignedTo value
   */
  public int getAssignedTo() {
    return assignedTo;
  }


  /**
   *  Gets the Closed attribute of the TicketLog object
   *
   * @return    The Closed value
   */
  public boolean getClosed() {
    return closed;
  }


  /**
   *  Gets the EnteredBy attribute of the TicketLog object
   *
   * @return    The EnteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the ModifiedBy attribute of the TicketLog object
   *
   * @return    The ModifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the PriorityCode attribute of the TicketLog object
   *
   * @return    The PriorityCode value
   */
  public int getPriorityCode() {
    return priorityCode;
  }


  /**
   *  Gets the LevelCode attribute of the TicketLog object
   *
   * @return    The LevelCode value
   */
  public int getLevelCode() {
    return levelCode;
  }


  /**
   *  Gets the DepartmentCode attribute of the TicketLog object
   *
   * @return    The DepartmentCode value
   */
  public int getDepartmentCode() {
    return departmentCode;
  }


  /**
   *  Gets the CatCode attribute of the TicketLog object
   *
   * @return    The CatCode value
   */
  public int getCatCode() {
    return catCode;
  }


  /**
   *  Gets the SeverityCode attribute of the TicketLog object
   *
   * @return    The SeverityCode value
   */
  public int getSeverityCode() {
    return severityCode;
  }


  /**
   *  Gets the escalationCode attribute of the TicketLog object
   *
   * @return    The escalationCode value
   */
  public int getEscalationCode() {
    return escalationCode;
  }


  /**
   *  Gets the EnteredByName attribute of the TicketLog object
   *
   * @return    The EnteredByName value
   */
  public String getEnteredByName() {
    return enteredByName;
  }


  /**
   *  Gets the ModifiedByName attribute of the TicketLog object
   *
   * @return    The ModifiedByName value
   */
  public String getModifiedByName() {
    return modifiedByName;
  }


  /**
   *  Gets the CompanyName attribute of the TicketLog object
   *
   * @return    The CompanyName value
   */
  public String getCompanyName() {
    return companyName;
  }


  /**
   *  Gets the CategoryName attribute of the TicketLog object
   *
   * @return    The CategoryName value
   */
  public String getCategoryName() {
    return categoryName;
  }


  /**
   *  Gets the stateId attribute of the TicketLog object
   *
   * @return    The stateId value
   */
  public int getStateId() {
    return stateId;
  }


  /**
   *  Sets the stateId attribute of the TicketLog object
   *
   * @param  tmp  The new stateId value
   */
  public void setStateId(int tmp) {
    this.stateId = tmp;
  }


  /**
   *  Sets the stateId attribute of the TicketLog object
   *
   * @param  tmp  The new stateId value
   */
  public void setStateId(String tmp) {
    this.stateId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the stateName attribute of the TicketLog object
   *
   * @return    The stateName value
   */
  public String getStateName() {
    return stateName;
  }


  /**
   *  Sets the stateName attribute of the TicketLog object
   *
   * @param  tmp  The new stateName value
   */
  public void setStateName(String tmp) {
    this.stateName = tmp;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (ticketId == -1) {
      throw new SQLException(
          "Log Entry must be associated to a Ticket " + this.getId());
    }
    StringBuffer sql = new StringBuffer();
    boolean doCommit = false;
    try {
      if ((doCommit = db.getAutoCommit()) == true) {
        db.setAutoCommit(false);
      }
      id = DatabaseUtils.getNextSeq(db, "ticketlog_id_seq");
      sql.append(
          "INSERT INTO ticketlog (pri_code, level_code, department_code, " + 
          "cat_code, scode, escalation_code, ticketid, " + DatabaseUtils.addQuotes(db, "comment")+ ", closed, state_id, ");
      if (id > -1) {
        sql.append("id, ");
      }
      sql.append("entered, ");
      sql.append("modified, ");
      sql.append("enteredBy, modifiedBy ) ");
      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
      if (id > -1) {
        sql.append("?, ");
      }
      if (entered != null) {
        sql.append("?, ");
      } else {
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      if (modified != null) {
        sql.append("?, ");
      } else {
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      sql.append("?, ?) ");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      DatabaseUtils.setInt(pst, ++i, this.getPriorityCode());
      pst.setInt(++i, this.getLevelCode());
      if (this.getDepartmentCode() > 0) {
        pst.setInt(++i, this.getDepartmentCode());
      } else {
        pst.setNull(++i, java.sql.Types.INTEGER);
      }
      DatabaseUtils.setInt(pst, ++i, this.getCatCode());
      DatabaseUtils.setInt(pst, ++i, this.getSeverityCode());
      DatabaseUtils.setInt(pst, ++i, this.getEscalationCode());
      DatabaseUtils.setInt(pst, ++i, this.getTicketId());
      pst.setString(++i, this.getEntryText());
      pst.setBoolean(++i, this.getClosed());
      DatabaseUtils.setInt(pst, ++i, this.getStateId());
      if (id > -1) {
        pst.setInt(++i, id);
      }
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
      id = DatabaseUtils.getCurrVal(db, "ticketlog_id_seq", id);
      this.update(db, true);
      if (doCommit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (doCommit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (doCommit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @param  ticketId       Description of Parameter
   * @param  enteredBy      Description of Parameter
   * @param  modifiedBy     Description of Parameter
   * @throws  SQLException  Description of Exception
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
   * @param  db             Description of Parameter
   * @param  override       Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public int update(Connection db, boolean override) throws SQLException {
    int resultCount = 0;
    if (ticketId == -1) {
      throw new SQLException("Log Entry must be associated to a Ticket");
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE ticketlog " +
        "SET assigned_to = ? ");
    if (override == false) {
      sql.append(
          ", modified = " + DatabaseUtils.getCurrentTimestamp(db) + " ");
    }
    sql.append("WHERE id = ? ");
    if (!override) {
      sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
    }
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getAssignedTo());
    pst.setInt(++i, id);
    if (!override && this.getModified() != null) {
      pst.setTimestamp(++i, this.getModified());
    }
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Ticket Log ID not specified.");
    }
    try {
      db.setAutoCommit(false);
      Statement st = db.createStatement();
      st.executeUpdate("DELETE FROM ticketlog WHERE id = " + this.getId());
      st.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  source  Description of Parameter
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
    this.setEscalationName(source.getEscalationName());
    this.setAssignedToName(source.getAssignedToName());
    this.setEnteredByName(source.getEnteredByName());
    this.setDepartmentName(source.getDepartmentName());
  }


  /**
   *  Description of the Method
   *
   * @param  rs             Description of Parameter
   * @throws  SQLException  Description of Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //ticketlog table
    this.setId(rs.getInt("id"));
    ticketId = rs.getInt("ticketid");
    if (rs.wasNull()) {
      ticketId = -1;
    }
    assignedTo = rs.getInt("assigned_to");
    if (rs.wasNull()) {
      assignedTo = 0;
    }
    entryText = rs.getString("comment");
    closed = rs.getBoolean("closed");
    priorityCode = rs.getInt("pri_code");
    if (rs.wasNull()) {
      priorityCode = -1;
    }
    levelCode = rs.getInt("level_code");
    departmentCode = rs.getInt("department_code");
    if (rs.wasNull()) {
      departmentCode = -1;
    }
    catCode = rs.getInt("cat_code");
    if (rs.wasNull()) {
      catCode = -1;
    }
    severityCode = rs.getInt("scode");
    if (rs.wasNull()) {
      severityCode = -1;
    }
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    escalationCode = DatabaseUtils.getInt(rs, "escalation_code");
    stateId = DatabaseUtils.getInt(rs, "state_id");
    //lookup_department table
    departmentName = rs.getString("deptname");

    //ticket_priority table
    priorityName = rs.getString("priorityname");

    //ticket_severity table
    severityName = rs.getString("severityname");

    //lookup_ticket_escalation
    escalationName = rs.getString("escalationname");

    //contact table
    enteredByName = Contact.getNameLastFirst(
        rs.getString("eb_namelast"), rs.getString("eb_namefirst"));
    assignedToName = Contact.getNameLastFirst(
        rs.getString("at_namelast"), rs.getString("at_namefirst"));

    //lookup_ticket_state table
    stateName = rs.getString("state_name");
    
  }


  /**
   *  Description of the Method
   *
   * @param  request  Description of Parameter
   */
  protected void buildRecord(HttpServletRequest request) {
    this.setEntryText(request.getParameter("newticketlogentry"));
    this.setDepartmentCode(
        Integer.parseInt(request.getParameter("departmentCode")));
    this.setTicketId(Integer.parseInt(request.getParameter("id")));
  }
}

