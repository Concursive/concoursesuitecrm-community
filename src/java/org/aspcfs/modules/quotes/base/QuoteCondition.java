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

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  Description of the Class
 *
 *@author     partha
 *@created    October 28, 2004
 *@version    $Id: QuoteCondition.java,v 1.1.2.2 2004/11/12 21:42:18 partha Exp
 *      $
 */
public class QuoteCondition extends GenericBean {
  private int id = -1;
  private int quoteId = -1;
  private int conditionId = -1;
//Resources
  private String conditionName = null;


  /**
   *  Gets the id attribute of the QuoteCondition object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the QuoteCondition object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the QuoteCondition object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the quoteId attribute of the QuoteCondition object
   *
   *@return    The quoteId value
   */
  public int getQuoteId() {
    return quoteId;
  }


  /**
   *  Sets the quoteId attribute of the QuoteCondition object
   *
   *@param  tmp  The new quoteId value
   */
  public void setQuoteId(int tmp) {
    this.quoteId = tmp;
  }


  /**
   *  Sets the quoteId attribute of the QuoteCondition object
   *
   *@param  tmp  The new quoteId value
   */
  public void setQuoteId(String tmp) {
    this.quoteId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the conditionId attribute of the QuoteCondition object
   *
   *@return    The conditionId value
   */
  public int getConditionId() {
    return conditionId;
  }


  /**
   *  Sets the conditionId attribute of the QuoteCondition object
   *
   *@param  tmp  The new conditionId value
   */
  public void setConditionId(int tmp) {
    this.conditionId = tmp;
  }


  /**
   *  Sets the conditionId attribute of the QuoteCondition object
   *
   *@param  tmp  The new conditionId value
   */
  public void setConditionId(String tmp) {
    this.conditionId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the conditionName attribute of the QuoteCondition object
   *
   *@return    The conditionName value
   */
  public String getConditionName() {
    return conditionName;
  }


  /**
   *  Sets the conditionName attribute of the QuoteCondition object
   *
   *@param  tmp  The new conditionName value
   */
  public void setConditionName(String tmp) {
    this.conditionName = tmp;
  }


  /**
   *  Constructor for the QuoteCondition object
   */
  public QuoteCondition() { }


  /**
   *  Constructor for the QuoteCondition object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public QuoteCondition(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the QuoteCondition object
   *
   *@param  db                Description of the Parameter
   *@param  mapId             Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public QuoteCondition(Connection db, int mapId) throws SQLException {
    queryRecord(db, mapId);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Quote Condition ID");
    }
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT " +
        "qc.*, " +
        "lqc.description AS condition_name " +
        "FROM quote_condition AS qc " +
        "LEFT JOIN quote_entry AS qe ON (qc.quote_id = qe.quote_id) " +
        "LEFT JOIN lookup_quote_condition AS lqc ON (qc.condition_id = lqc.code) " +
        "WHERE qc.map_id = ? "
        );

    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException("Quote Condition Entry not found");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  qId               Description of the Parameter
   *@param  cId               Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int qId, int cId) throws SQLException {
    if (qId == -1 || cId == -1) {
      throw new SQLException("Invalid Quote Id and Condition ID");
    }
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT " +
        "qc.*, " +
        "lqc.description AS condition_name " +
        "FROM quote_condition AS qc " +
        "LEFT JOIN quote_entry AS qe ON (qc.quote_id = qe.quote_id) " +
        "LEFT JOIN lookup_quote_condition AS lqc ON (qc.condition_id = lqc.code) " +
        "WHERE qc.condition_id = ? " +
        "AND qc.quote_id = ? "
        );

    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(1, cId);
    pst.setInt(2, qId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException("Quote Condition Entry not found");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    //quote_condition table
    this.setId(rs.getInt("map_id"));
    this.setQuoteId(rs.getInt("quote_id"));
    this.setConditionId(rs.getInt("condition_id"));

    //lookup_quote_condition table
    this.setConditionName(rs.getString("condition_name"));
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (quoteId == -1 || conditionId == -1) {
      return false;
    }
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO quote_condition (quote_id, condition_id ) " +
        "VALUES (?,?) ");
    pst.setInt(++i, this.getQuoteId());
    pst.setInt(++i, this.getConditionId());
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "quote_condition_map_id_seq");
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Quote Condition ID not specified");
    }
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = null;
      pst = db.prepareStatement("DELETE FROM quote_condition WHERE map_id = ?");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
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
   *@param  description       Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int insertLookup(Connection db, String description) throws SQLException {
    PreparedStatement pst = db.prepareStatement("INSERT INTO lookup_quote_condition (description) VALUES (?)");
    pst.setString(1, description);
    pst.execute();
    pst.close();
    return DatabaseUtils.getCurrVal(db, "lookup_quote_condition_code_seq");
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  quoteId           Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void copyQuoteCondition(Connection db, int quoteId) throws SQLException {
    QuoteCondition copy = new QuoteCondition();
    copy.setConditionId(this.getConditionId());
    copy.setConditionName(this.getConditionName());
    copy.setQuoteId(quoteId);
    copy.insert(db);
  }

}

