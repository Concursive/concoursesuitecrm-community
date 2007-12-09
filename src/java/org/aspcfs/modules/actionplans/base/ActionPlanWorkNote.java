/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.actionplans.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    September 20, 2005
 */
public class ActionPlanWorkNote extends GenericBean {
  private int id = -1;
  private int planWorkId = -1;
  private String description = null;
  private java.sql.Timestamp submitted = null;
  private int submittedBy = -1;
  protected Timestamp entered = null;
  protected Timestamp modified = null;


  /**
   *  Gets the entered attribute of the ActionPlanWorkNote object
   *
   * @return    The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   *  Sets the entered attribute of the ActionPlanWorkNote object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the ActionPlanWorkNote object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the modified attribute of the ActionPlanWorkNote object
   *
   * @return    The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   *  Sets the modified attribute of the ActionPlanWorkNote object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the ActionPlanWorkNote object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }



  /**
   *  Gets the submittedBy attribute of the ActionPlanWorkNote object
   *
   * @return    The submittedBy value
   */
  public int getSubmittedBy() {
    return submittedBy;
  }


  /**
   *  Sets the submittedBy attribute of the ActionPlanWorkNote object
   *
   * @param  tmp  The new submittedBy value
   */
  public void setSubmittedBy(int tmp) {
    this.submittedBy = tmp;
  }


  /**
   *  Sets the submittedBy attribute of the ActionPlanWorkNote object
   *
   * @param  tmp  The new submittedBy value
   */
  public void setSubmittedBy(String tmp) {
    this.submittedBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the id attribute of the ActionPlanWorkNote object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the ActionPlanWorkNote object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ActionPlanWorkNote object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the planWorkId attribute of the ActionPlanWorkNote object
   *
   * @return    The planWorkId value
   */
  public int getPlanWorkId() {
    return planWorkId;
  }


  /**
   *  Sets the planWorkId attribute of the ActionPlanWorkNote object
   *
   * @param  tmp  The new planWorkId value
   */
  public void setPlanWorkId(int tmp) {
    this.planWorkId = tmp;
  }


  /**
   *  Sets the planWorkId attribute of the ActionPlanWorkNote object
   *
   * @param  tmp  The new planWorkId value
   */
  public void setPlanWorkId(String tmp) {
    this.planWorkId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the description attribute of the ActionPlanWorkNote object
   *
   * @return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Sets the description attribute of the ActionPlanWorkNote object
   *
   * @param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Gets the submitted attribute of the ActionPlanWorkNote object
   *
   * @return    The submitted value
   */
  public java.sql.Timestamp getSubmitted() {
    return submitted;
  }


  /**
   *  Sets the submitted attribute of the ActionPlanWorkNote object
   *
   * @param  tmp  The new submitted value
   */
  public void setSubmitted(java.sql.Timestamp tmp) {
    this.submitted = tmp;
  }


  /**
   *  Sets the submitted attribute of the ActionPlanWorkNote object
   *
   * @param  tmp  The new submitted value
   */
  public void setSubmitted(String tmp) {
    this.submitted = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Constructor for the ActionPlanWorkNote object
   */
  public ActionPlanWorkNote() { }


  /**
   *  Constructor for the ActionPlanWorkNote object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public ActionPlanWorkNote(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the ActionPlanWorkNote object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public ActionPlanWorkNote(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  id             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Action Plan Work Note ID specified");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT apwn.* " +
        "FROM action_plan_work_notes apwn " +
        "WHERE apwn.note_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();

    if (this.getId() == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  rs             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("note_id");
    planWorkId = rs.getInt("plan_work_id");
    description = rs.getString("description");
    submitted = rs.getTimestamp("submitted");
    submittedBy = rs.getInt("submittedby");
    entered = rs.getTimestamp("entered");
    modified = rs.getTimestamp("modified");
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "action_plan_work_notes_note_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO action_plan_work_notes " +
        "(" + (id > -1 ? "note_id, " : "") +
        "entered, modified, " +
        "plan_work_id, description, submitted, submittedby) " +
        "VALUES (" +
        (id > -1 ? "?, " : "") +
        (entered != null ? "?, " : (DatabaseUtils.getCurrentTimestamp(db) + ", ")) +
        (modified != null ? "?, " : (DatabaseUtils.getCurrentTimestamp(db) + ", ")) +
        "?, ?, ?, ?)");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (entered != null) {
      pst.setTimestamp(++i, this.getEntered());
    }
    if (modified != null) {
      pst.setTimestamp(++i, modified);
    }
    pst.setInt(++i, planWorkId);
    pst.setString(++i, description);
    DatabaseUtils.setTimestamp(pst, ++i, submitted);
    pst.setInt(++i, submittedBy);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "action_plan_work_notes_note_id_seq", id);
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean update(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE action_plan_work_notes " +
        "SET plan_work_id = ?, description = ?, submitted = ?, submittedby = ?, " +
        "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
        "WHERE note_id = ? ");
    int i = 0;
    pst.setInt(++i, planWorkId);
    pst.setString(++i, description);
    DatabaseUtils.setTimestamp(pst, ++i, submitted);
    pst.setInt(++i, submittedBy);
    pst.setInt(++i, id);
    pst.executeUpdate();
    pst.close();
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Statement st = db.createStatement();
    st.executeUpdate("DELETE FROM action_plan_work_notes WHERE note_id = " + this.getId());
    st.close();
  }


  /**
   *  Gets the timeZoneParams attribute of the ActionPlanWorkNote class
   *
   * @return    The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("submitted");
    return thisList;
  }
}

