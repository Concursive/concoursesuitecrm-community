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
package org.aspcfs.modules.pipeline.base;

import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.Note;
/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    January 14, 2003
 *@version    $Id: OpportunityNote.java,v 1.4 2003/03/07 14:41:52 mrajkowski Exp
 *      $
 */
public class OpportunityNote extends Note {

  /**
   *  Constructor for the OpportunityNote object
   */
  public OpportunityNote() {
    isContact = false;
    isOrg = false;
  }


  /**
   *  Constructor for the OpportunityNote object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public OpportunityNote(ResultSet rs) throws SQLException {
    isContact = false;
    isOrg = false;
    buildRecord(rs);
  }


  /**
   *  Constructor for the OpportunityNote object
   *
   *@param  db                Description of the Parameter
   *@param  noteId            Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public OpportunityNote(Connection db, String noteId) throws SQLException {
    isContact = false;
    isOrg = false;
    if (noteId == null) {
      throw new SQLException("Note ID not specified.");
    }

    Statement st = null;
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT * " +
        "FROM note " +
        "WHERE id = " + noteId + " ");
    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    st.close();
    if (this.getId() == -1) {
      throw new SQLException("Note record not found.");
    }
  }


  /**
   *  Determines what to do if this record is marked for INSERT, UPDATE, or
   *  DELETE
   *
   *@param  db                Description of Parameter
   *@param  enteredBy         Description of Parameter
   *@param  modifiedBy        Description of Parameter
   *@param  oppHeaderId       Description of the Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public void process(Connection db, int oppHeaderId, int enteredBy, int modifiedBy) throws SQLException {
    if (this.getEnabled() == true) {
      if (this.getId() == -1) {
        this.insert(db, oppHeaderId, enteredBy);
      } else {
        this.update(db, modifiedBy);
      }
    } else {
      this.delete(db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  enteredBy         Description of the Parameter
   *@param  oppHeaderId       Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insert(Connection db, int oppHeaderId, int enteredBy) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO note " +
        "(org_id, contact_id, opp_id, subject, body, enteredby, modifiedby) " +
        "VALUES " +
        "(?, ?, ?, ?, ?, ?, ?) ");
    int i = 0;
    pst.setInt(++i, this.getOrgId());
    pst.setInt(++i, this.getContactId());
    pst.setInt(++i, oppHeaderId);
    pst.setString(++i, this.getSubject());
    pst.setString(++i, this.getBody());
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, enteredBy);
    pst.execute();
    pst.close();
    this.setId(DatabaseUtils.getCurrVal(db, "note_id_seq"));
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  modifiedBy        Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */

  public void update(Connection db, int modifiedBy) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE note " +
        "SET body = ?, subject = ?, modifiedby = ? " +
        "WHERE id = ? ");
    int i = 0;
    pst.setString(++i, this.getBody());
    pst.setString(++i, this.getSubject());
    pst.setInt(++i, modifiedBy);
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public void delete(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM note " +
        "WHERE id = ? ");
    int i = 0;
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
  }

}

