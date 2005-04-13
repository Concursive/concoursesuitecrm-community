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
package org.aspcfs.modules.quotes.base;

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.accounts.base.*;

/**
 *  Description of the Class
 *
 *@author     partha
 *@created    October 28, 2004
 *@version    $Id$
 */
public class QuoteLog extends GenericBean {
  private int id = -1;
  private int quoteId = -1;
  private int sourceId = -1;
  private double grandTotal = 0;
  private int statusId = -1;
  private int termsId = -1;
  private int typeId = -1;
  private String notes = null;
  private int deliveryId = -1;
  private Timestamp issuedDate = null;
  private int submitAction = -1;
  private Timestamp closed = null;
  // record status
  private Timestamp entered = null;
  private int enteredBy = -1;
  private Timestamp modified = null;
  private int modifiedBy = -1;
  //Additional resources
  private String sourceName = null;
  private String statusName = null;
  private String termsName = null;
  private String typeName = null;
  private String deliveryName = null;
  private String lastName = null;
  private String firstName = null;
  private boolean systemMessage = false;


  /**
   *  Gets the id attribute of the QuoteLog object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the QuoteLog object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the QuoteLog object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the sourceId attribute of the QuoteLog object
   *
   *@return    The sourceId value
   */
  public int getSourceId() {
    return sourceId;
  }


  /**
   *  Sets the sourceId attribute of the QuoteLog object
   *
   *@param  tmp  The new sourceId value
   */
  public void setSourceId(int tmp) {
    this.sourceId = tmp;
  }


  /**
   *  Sets the sourceId attribute of the QuoteLog object
   *
   *@param  tmp  The new sourceId value
   */
  public void setSourceId(String tmp) {
    this.sourceId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the grandTotal attribute of the QuoteLog object
   *
   *@return    The grandTotal value
   */
  public double getGrandTotal() {
    return grandTotal;
  }


  /**
   *  Sets the grandTotal attribute of the QuoteLog object
   *
   *@param  tmp  The new grandTotal value
   */
  public void setGrandTotal(double tmp) {
    this.grandTotal = tmp;
  }


  /**
   *  Sets the grandTotal attribute of the QuoteLog object
   *
   *@param  tmp  The new grandTotal value
   */
  public void setGrandTotal(String tmp) {
    this.grandTotal = Double.parseDouble(tmp);
  }


  /**
   *  Gets the statusId attribute of the QuoteLog object
   *
   *@return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Sets the statusId attribute of the QuoteLog object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the QuoteLog object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the termsId attribute of the QuoteLog object
   *
   *@return    The termsId value
   */
  public int getTermsId() {
    return termsId;
  }


  /**
   *  Sets the termsId attribute of the QuoteLog object
   *
   *@param  tmp  The new termsId value
   */
  public void setTermsId(int tmp) {
    this.termsId = tmp;
  }


  /**
   *  Sets the termsId attribute of the QuoteLog object
   *
   *@param  tmp  The new termsId value
   */
  public void setTermsId(String tmp) {
    this.termsId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the typeId attribute of the QuoteLog object
   *
   *@return    The typeId value
   */
  public int getTypeId() {
    return typeId;
  }


  /**
   *  Sets the typeId attribute of the QuoteLog object
   *
   *@param  tmp  The new typeId value
   */
  public void setTypeId(int tmp) {
    this.typeId = tmp;
  }


  /**
   *  Sets the typeId attribute of the QuoteLog object
   *
   *@param  tmp  The new typeId value
   */
  public void setTypeId(String tmp) {
    this.typeId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the notes attribute of the QuoteLog object
   *
   *@return    The notes value
   */
  public String getNotes() {
    return notes;
  }


  /**
   *  Sets the notes attribute of the QuoteLog object
   *
   *@param  tmp  The new notes value
   */
  public void setNotes(String tmp) {
    this.notes = tmp;
  }


  /**
   *  Gets the deliveryId attribute of the QuoteLog object
   *
   *@return    The deliveryId value
   */
  public int getDeliveryId() {
    return deliveryId;
  }


  /**
   *  Sets the deliveryId attribute of the QuoteLog object
   *
   *@param  tmp  The new deliveryId value
   */
  public void setDeliveryId(int tmp) {
    this.deliveryId = tmp;
  }


  /**
   *  Sets the deliveryId attribute of the QuoteLog object
   *
   *@param  tmp  The new deliveryId value
   */
  public void setDeliveryId(String tmp) {
    this.deliveryId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the entered attribute of the QuoteLog object
   *
   *@return    The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   *  Sets the entered attribute of the QuoteLog object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the QuoteLog object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the enteredBy attribute of the QuoteLog object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Sets the enteredBy attribute of the QuoteLog object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the QuoteLog object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the modified attribute of the QuoteLog object
   *
   *@return    The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   *  Sets the modified attribute of the QuoteLog object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the QuoteLog object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the modifiedBy attribute of the QuoteLog object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Sets the modifiedBy attribute of the QuoteLog object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the QuoteLog object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the sourceName attribute of the QuoteLog object
   *
   *@return    The sourceName value
   */
  public String getSourceName() {
    return sourceName;
  }


  /**
   *  Sets the sourceName attribute of the QuoteLog object
   *
   *@param  tmp  The new sourceName value
   */
  public void setSourceName(String tmp) {
    this.sourceName = tmp;
  }


  /**
   *  Gets the statusName attribute of the QuoteLog object
   *
   *@return    The statusName value
   */
  public String getStatusName() {
    return statusName;
  }


  /**
   *  Sets the statusName attribute of the QuoteLog object
   *
   *@param  tmp  The new statusName value
   */
  public void setStatusName(String tmp) {
    this.statusName = tmp;
  }


  /**
   *  Gets the termsName attribute of the QuoteLog object
   *
   *@return    The termsName value
   */
  public String getTermsName() {
    return termsName;
  }


  /**
   *  Sets the termsName attribute of the QuoteLog object
   *
   *@param  tmp  The new termsName value
   */
  public void setTermsName(String tmp) {
    this.termsName = tmp;
  }


  /**
   *  Gets the typeName attribute of the QuoteLog object
   *
   *@return    The typeName value
   */
  public String getTypeName() {
    return typeName;
  }


  /**
   *  Sets the typeName attribute of the QuoteLog object
   *
   *@param  tmp  The new typeName value
   */
  public void setTypeName(String tmp) {
    this.typeName = tmp;
  }


  /**
   *  Gets the deliveryName attribute of the QuoteLog object
   *
   *@return    The deliveryName value
   */
  public String getDeliveryName() {
    return deliveryName;
  }


  /**
   *  Sets the deliveryName attribute of the QuoteLog object
   *
   *@param  tmp  The new deliveryName value
   */
  public void setDeliveryName(String tmp) {
    this.deliveryName = tmp;
  }


  /**
   *  Gets the lastName attribute of the QuoteLog object
   *
   *@return    The lastName value
   */
  public String getLastName() {
    return lastName;
  }


  /**
   *  Sets the lastName attribute of the QuoteLog object
   *
   *@param  tmp  The new lastName value
   */
  public void setLastName(String tmp) {
    this.lastName = tmp;
  }


  /**
   *  Gets the firstName attribute of the QuoteLog object
   *
   *@return    The firstName value
   */
  public String getFirstName() {
    return firstName;
  }


  /**
   *  Sets the firstName attribute of the QuoteLog object
   *
   *@param  tmp  The new firstName value
   */
  public void setFirstName(String tmp) {
    this.firstName = tmp;
  }


  /**
   *  Gets the quoteId attribute of the QuoteLog object
   *
   *@return    The quoteId value
   */
  public int getQuoteId() {
    return quoteId;
  }


  /**
   *  Sets the quoteId attribute of the QuoteLog object
   *
   *@param  tmp  The new quoteId value
   */
  public void setQuoteId(int tmp) {
    this.quoteId = tmp;
  }


  /**
   *  Sets the quoteId attribute of the QuoteLog object
   *
   *@param  tmp  The new quoteId value
   */
  public void setQuoteId(String tmp) {
    this.quoteId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the systemMessage attribute of the QuoteLog object
   *
   *@return    The systemMessage value
   */
  public boolean getSystemMessage() {
    return systemMessage;
  }


  /**
   *  Sets the systemMessage attribute of the QuoteLog object
   *
   *@param  tmp  The new systemMessage value
   */
  public void setSystemMessage(boolean tmp) {
    this.systemMessage = tmp;
  }


  /**
   *  Sets the systemMessage attribute of the QuoteLog object
   *
   *@param  tmp  The new systemMessage value
   */
  public void setSystemMessage(String tmp) {
    this.systemMessage = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the issuedDate attribute of the QuoteLog object
   *
   *@return    The issuedDate value
   */
  public Timestamp getIssuedDate() {
    return issuedDate;
  }


  /**
   *  Sets the issuedDate attribute of the QuoteLog object
   *
   *@param  tmp  The new issuedDate value
   */
  public void setIssuedDate(Timestamp tmp) {
    this.issuedDate = tmp;
  }


  /**
   *  Sets the issuedDate attribute of the QuoteLog object
   *
   *@param  tmp  The new issuedDate value
   */
  public void setIssuedDate(String tmp) {
    this.issuedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the submitAction attribute of the QuoteLog object
   *
   *@return    The submitAction value
   */
  public int getSubmitAction() {
    return submitAction;
  }


  /**
   *  Sets the submitAction attribute of the QuoteLog object
   *
   *@param  tmp  The new submitAction value
   */
  public void setSubmitAction(int tmp) {
    this.submitAction = tmp;
  }


  /**
   *  Sets the submitAction attribute of the QuoteLog object
   *
   *@param  tmp  The new submitAction value
   */
  public void setSubmitAction(String tmp) {
    this.submitAction = Integer.parseInt(tmp);
  }


  /**
   *  Gets the closed attribute of the QuoteLog object
   *
   *@return    The closed value
   */
  public Timestamp getClosed() {
    return closed;
  }


  /**
   *  Sets the closed attribute of the QuoteLog object
   *
   *@param  tmp  The new closed value
   */
  public void setClosed(Timestamp tmp) {
    this.closed = tmp;
  }


  /**
   *  Sets the closed attribute of the QuoteLog object
   *
   *@param  tmp  The new closed value
   */
  public void setClosed(String tmp) {
    this.closed = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Constructor for the QuoteLog object
   */
  public QuoteLog() { }


  /**
   *  Constructor for the QuoteLog object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public QuoteLog(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the QuoteLog object
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public QuoteLog(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Quote Log Number");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT q.*, " +
        "lqso.description AS source_name, " +
        "lqst.description AS status_name, " +
        "lqtm.description AS terms_name, " +
        "lqty.description AS type_name, " +
        "lqd.description AS delivery_name," +
        "ct_eb.namelast AS eb_namelast, ct_eb.namefirst AS eb_namefirst " +
        "FROM quotelog AS q " +
        "LEFT JOIN contact ct_eb ON (q.enteredby = ct_eb.user_id) " +
        "LEFT JOIN lookup_quote_status AS lqst ON (q.status_id = lqst.code) " +
        "LEFT JOIN lookup_quote_source AS lqso ON (q.source_id = lqso.code) " +
        "LEFT JOIN lookup_quote_terms AS lqtm ON (q.terms_id = lqtm.code) " +
        "LEFT JOIN lookup_quote_type AS lqty ON (q.type_id = lqty.code) " +
        "LEFT JOIN lookup_quote_delivery AS lqd ON (q.delivery_id = lqd.code) " +
        "WHERE q.id = ? "
        );
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("QuoteLog not found");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (quoteId == -1) {
      throw new SQLException("Log entry must be associated with a Quote");
    }
    StringBuffer sql = new StringBuffer();
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }

      sql.append("INSERT INTO quotelog (quote_id, source_id, status_id,");
      sql.append("terms_id, type_id, delivery_id, issued_date, submit_action, closed, ");
      if (entered != null) {
        sql.append(" entered, ");
      }
      if (modified != null) {
        sql.append(" modified, ");
      }
      sql.append(" enteredby, modifiedby) ");
      sql.append("VALUES (?,?,?,?,?,?,?,?,?, ");
      if (entered != null) {
        sql.append("?,");
      }
      if (modified != null) {
        sql.append("?,");
      }
      sql.append("?,?) ");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, quoteId);
      DatabaseUtils.setInt(pst, ++i, sourceId);
      DatabaseUtils.setInt(pst, ++i, statusId);
      DatabaseUtils.setInt(pst, ++i, termsId);
      DatabaseUtils.setInt(pst, ++i, typeId);
      DatabaseUtils.setInt(pst, ++i, deliveryId);
      DatabaseUtils.setTimestamp(pst, ++i, issuedDate);
      DatabaseUtils.setInt(pst, ++i, submitAction);
      DatabaseUtils.setTimestamp(pst, ++i, closed);
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
      id = DatabaseUtils.getCurrVal(db, "quotelog_id_seq");
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  quoteId           Description of the Parameter
   *@param  enteredBy         Description of the Parameter
   *@param  modifiedBy        Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void process(Connection db, int quoteId, int enteredBy, int modifiedBy) throws SQLException {
    if (quoteId != -1) {
      this.setEnteredBy(modifiedBy);
      this.setModifiedBy(modifiedBy);
      this.insert(db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Quote Log ID not specified.");
    }
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      Statement st = db.createStatement();
      st.executeUpdate("DELETE FROM quotelog WHERE id = " + this.getId());
      st.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   *  Gets the valid attribute of the QuoteLog object
   *
   *@return    The valid value
   */
  protected boolean isValid() {
    errors.clear();
    if (quoteId == -1 || (notes == null || notes.trim().equals("")) || enteredBy == -1 || modifiedBy == -1) {
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //quotelog table
    id = DatabaseUtils.getInt(rs, "id");
    quoteId = DatabaseUtils.getInt(rs, "quote_id");
    sourceId = DatabaseUtils.getInt(rs, "source_id");
    statusId = DatabaseUtils.getInt(rs, "status_id");
    termsId = DatabaseUtils.getInt(rs, "terms_id");
    typeId = DatabaseUtils.getInt(rs, "type_id");
    notes = rs.getString("notes");
    grandTotal = rs.getDouble("grand_total");
    enteredBy = DatabaseUtils.getInt(rs, "enteredby");
    entered = rs.getTimestamp("entered");
    modifiedBy = DatabaseUtils.getInt(rs, "modifiedby");
    modified = rs.getTimestamp("modified");
    issuedDate = rs.getTimestamp("issued_date");
    submitAction = DatabaseUtils.getInt(rs, "submit_action");
    closed = rs.getTimestamp("closed");

    //lookup source table
    sourceName = rs.getString("source_name");
    //lookup status table
    statusName = rs.getString("status_name");
    //lookup terms table
    termsName = rs.getString("terms_name");
    //lookup type table
    typeName = rs.getString("type_name");
    //lookup delivery table
    deliveryName = rs.getString("delivery_name");
    // contact table
    this.setFirstName(rs.getString("eb_namefirst"));
    this.setLastName(rs.getString("eb_namelast"));
  }


  /**
   *  Description of the Method
   *
   *@param  request  Description of the Parameter
   */
  protected void buildRecord(HttpServletRequest request) {
    this.setNotes(request.getParameter("notes"));
    this.setQuoteId(Integer.parseInt(request.getParameter("quoteId")));
  }


  /**
   *  Description of the Method
   *
   *@param  source  Description of the Parameter
   */
  public void createSysMsg(QuoteLog source) {
    this.setEnteredBy(source.getModifiedBy());
    this.setEntered(source.getEntered());
    this.setQuoteId(source.getQuoteId());
    this.setSourceId(source.getSourceId());
    this.setStatusId(source.getStatusId());
    this.setTermsId(source.getTermsId());
    this.setTypeId(source.getTypeId());
    this.setDeliveryId(source.getDeliveryId());
    this.setIssuedDate(source.getIssuedDate());
    this.setSubmitAction(source.getSubmitAction());
    this.setClosed(source.getClosed());
    this.setSystemMessage(true);

    this.setSourceName(source.getSourceName());
    this.setStatusName(source.getStatusName());
    this.setTermsName(source.getTermsName());
    this.setTypeName(source.getTypeName());
    this.setDeliveryName(source.getDeliveryName());
    this.setLastName(source.getLastName());
    this.setFirstName(source.getFirstName());
  }
}

