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
package org.aspcfs.modules.quotes.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id$
 * @created November 1, 2004
 */
public class QuoteRemark extends GenericBean {
  private int id = -1;
  private int quoteId = -1;
  private int remarkId = -1;
//Resources
  private String remarkName = null;


  /**
   * Gets the id attribute of the QuoteRemark object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the QuoteRemark object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the QuoteRemark object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Gets the quoteId attribute of the QuoteRemark object
   *
   * @return The quoteId value
   */
  public int getQuoteId() {
    return quoteId;
  }


  /**
   * Sets the quoteId attribute of the QuoteRemark object
   *
   * @param tmp The new quoteId value
   */
  public void setQuoteId(int tmp) {
    this.quoteId = tmp;
  }


  /**
   * Sets the quoteId attribute of the QuoteRemark object
   *
   * @param tmp The new quoteId value
   */
  public void setQuoteId(String tmp) {
    this.quoteId = Integer.parseInt(tmp);
  }


  /**
   * Gets the remarkId attribute of the QuoteRemark object
   *
   * @return The remarkId value
   */
  public int getRemarkId() {
    return remarkId;
  }


  /**
   * Sets the remarkId attribute of the QuoteRemark object
   *
   * @param tmp The new remarkId value
   */
  public void setRemarkId(int tmp) {
    this.remarkId = tmp;
  }


  /**
   * Sets the remarkId attribute of the QuoteRemark object
   *
   * @param tmp The new remarkId value
   */
  public void setRemarkId(String tmp) {
    this.remarkId = Integer.parseInt(tmp);
  }


  /**
   * Gets the remarkName attribute of the QuoteRemark object
   *
   * @return The remarkName value
   */
  public String getRemarkName() {
    return remarkName;
  }


  /**
   * Sets the remarkName attribute of the QuoteRemark object
   *
   * @param tmp The new remarkName value
   */
  public void setRemarkName(String tmp) {
    this.remarkName = tmp;
  }


  /**
   * Constructor for the QuoteRemark object
   */
  public QuoteRemark() {
  }


  /**
   * Constructor for the QuoteRemark object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public QuoteRemark(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the QuoteRemark object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public QuoteRemark(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Quote Remark ID");
    }
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT " +
            "qr.*, " +
            "lqr.description AS remark_name " +
            "FROM quote_remark qr " +
            "LEFT JOIN quote_entry qe ON (qr.quote_id = qe.quote_id) " +
            "LEFT JOIN lookup_quote_remarks lqr ON (qr.remark_id = lqr.code) " +
            "WHERE qr.map_id = ? ");

    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException("Quote Remark Entry not found");
    }
  }


  public QuoteRemark(Connection db, int qId, int cId) throws SQLException {
    if (qId == -1 || cId == -1) {
      throw new SQLException("Invalid Quote ID or Remark ID");
    }
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT " +
            "qr.*, " +
            "lqr.description AS remark_name " +
            "FROM quote_remark qr " +
            "LEFT JOIN quote_entry qe ON (qr.quote_id = qe.quote_id) " +
            "LEFT JOIN lookup_quote_remarks lqr ON (qr.remark_id = lqr.code) " +
            "WHERE qr.remark_id = ? " +
            "AND qr.quote_id = ? ");

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
      throw new SQLException("Quote Remark Entry not found");
    }
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    //quote_condition table
    this.setId(rs.getInt("map_id"));
    this.setQuoteId(rs.getInt("quote_id"));
    this.setRemarkId(rs.getInt("remark_id"));

    //lookup_quote_condition table
    this.setRemarkName(rs.getString("remark_name"));
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (quoteId == -1 || remarkId == -1) {
      return false;
    }
    int i = 0;
    id = DatabaseUtils.getNextSeq(db, "quote_remark_map_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO quote_remark " +
            "(" + (id > -1 ? "map_id, " : "") + "quote_id, remark_id ) " +
            "VALUES (" + (id > -1 ? "?, " : "") + "?, ?) ");
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, this.getQuoteId());
    pst.setInt(++i, this.getRemarkId());
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "quote_remark_map_id_seq", id);
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Quote Remark ID not specified");
    }
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = null;
      pst = db.prepareStatement("DELETE FROM quote_remark WHERE map_id = ?");
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
   * Description of the Method
   *
   * @param db          Description of the Parameter
   * @param description Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int insertLookup(Connection db, String description) throws SQLException {
    int tempId = DatabaseUtils.getNextSeq(db, "lookup_quote_remarks_code_seq");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO lookup_quote_remarks " +
            "(" + (tempId > -1 ? "code, " : "") + "description) " +
            "VALUES (" + (tempId > -1 ? "?," : "") + "?)");
    if (tempId > -1) {
      pst.setInt(++i, tempId);
    }
    pst.setString(++i, description);
    pst.execute();
    pst.close();
    return DatabaseUtils.getCurrVal(
        db, "lookup_quote_remarks_code_seq", tempId);
  }

  public void copyQuoteRemark(Connection db, int quoteId) throws SQLException {
    QuoteRemark copy = new QuoteRemark();
    copy.setQuoteId(quoteId);
    copy.setRemarkId(this.getRemarkId());
    copy.setRemarkName(this.getRemarkName());
    copy.insert(db);
  }

}


