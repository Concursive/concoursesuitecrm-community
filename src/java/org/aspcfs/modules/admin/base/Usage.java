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
package org.aspcfs.modules.admin.base;

import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Used for tracking user usage. All usage should be able to be tracked to a
 * specific user. The actual usage may be a count or a sum of items, as
 * determined by the usage action.<p>
 * <p/>
 * Ex. Tracking # of emails sent would leave recordSize blank, but tracking the
 * size of email sent would put the number of bytes in the recordSize.
 *
 * @author matt rajkowski
 * @version $Id$
 * @created December 6, 2002
 */
public class Usage {
  private int id = -1;
  private int enteredBy = -1;
  private int action = -1;
  private int recordId = -1;
  private long recordSize = -1;


  /**
   * Constructor for the Usage object
   */
  public Usage() {
  }


  /**
   * Sets the user in which the usage originated from.<p>
   * <p/>
   * Ex. The person sending an email, NOT the person who the email was sent to.
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the Usage object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the type of usage. This is a unique int that refers to a specific
   * action.<p>
   * <p/>
   * Ex. Constants.USAGE_FILE_UPLOAD = 1 tracks when a user sent a file and the
   * size of the file.
   *
   * @param tmp The new action value
   */
  public void setAction(int tmp) {
    this.action = tmp;
  }


  /**
   * Sets the action attribute of the Usage object
   *
   * @param tmp The new action value
   */
  public void setAction(String tmp) {
    this.action = Integer.parseInt(tmp);
  }


  /**
   * If the usage refers back to a record, then set the id.<p>
   * <p/>
   * Ex. When tracking communications emails, the communication id is set.
   *
   * @param tmp The new recordId value
   */
  public void setRecordId(int tmp) {
    this.recordId = tmp;
  }


  /**
   * Sets the recordId attribute of the Usage object
   *
   * @param tmp The new recordId value
   */
  public void setRecordId(String tmp) {
    this.recordId = Integer.parseInt(tmp);
  }


  /**
   * Sets the recordSize attribute of the Usage object. This is typically the
   * number of bytes, but could be used for some other measurement like time,
   * depending on the action.
   *
   * @param tmp The new recordSize value
   */
  public void setRecordSize(long tmp) {
    this.recordSize = tmp;
  }


  /**
   * Sets the recordSize attribute of the Usage object
   *
   * @param tmp The new recordSize value
   */
  public void setRecordSize(String tmp) {
    this.recordSize = Integer.parseInt(tmp);
  }


  /**
   * Gets the enteredBy attribute of the Usage object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the action attribute of the Usage object
   *
   * @return The action value
   */
  public int getAction() {
    return action;
  }


  /**
   * Gets the recordId attribute of the Usage object
   *
   * @return The recordId value
   */
  public int getRecordId() {
    return recordId;
  }


  /**
   * Gets the recordSize attribute of the Usage object
   *
   * @return The recordSize value
   */
  public long getRecordSize() {
    return recordSize;
  }


  /**
   * Records the usage in a table.
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (action == -1) {
      throw new SQLException("Action not specified");
    }
    id = DatabaseUtils.getNextSeq(db, "usage_log_usage_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO usage_log " +
        "(" + (id > -1 ? "usage_id, " : "") + "enteredby, \"action\", record_id, record_size) " +
        "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?) ");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    DatabaseUtils.setInt(pst, ++i, enteredBy);
    DatabaseUtils.setInt(pst, ++i, action);
    DatabaseUtils.setInt(pst, ++i, recordId);
    DatabaseUtils.setInt(pst, ++i, (new Long(recordSize)).intValue());
    pst.executeUpdate();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "usage_log_usage_id_seq", id);
    return true;
  }
}
