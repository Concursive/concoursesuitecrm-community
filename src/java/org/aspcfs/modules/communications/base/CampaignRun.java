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
package org.aspcfs.modules.communications.base;

import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id$
 * @created September 16, 2004
 */
public class CampaignRun {

  private int id = -1;
  private int campaignId = -1;
  private int status = 0;
  private java.sql.Timestamp runDate = null;
  private int totalContacts = 0;
  private int totalSent = 0;
  private int totalReplied = 0;
  private int totalBounced = 0;


  /**
   * Sets the id attribute of the CampaignRun object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the CampaignRun object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the campaignId attribute of the CampaignRun object
   *
   * @param tmp The new campaignId value
   */
  public void setCampaignId(int tmp) {
    this.campaignId = tmp;
  }


  /**
   * Sets the campaignId attribute of the CampaignRun object
   *
   * @param tmp The new campaignId value
   */
  public void setCampaignId(String tmp) {
    this.campaignId = Integer.parseInt(tmp);
  }


  /**
   * Sets the status attribute of the CampaignRun object
   *
   * @param tmp The new status value
   */
  public void setStatus(int tmp) {
    this.status = tmp;
  }


  /**
   * Sets the status attribute of the CampaignRun object
   *
   * @param tmp The new status value
   */
  public void setStatus(String tmp) {
    this.status = Integer.parseInt(tmp);
  }


  /**
   * Sets the runDate attribute of the CampaignRun object
   *
   * @param tmp The new runDate value
   */
  public void setRunDate(java.sql.Timestamp tmp) {
    this.runDate = tmp;
  }


  /**
   * Sets the runDate attribute of the CampaignRun object
   *
   * @param tmp The new runDate value
   */
  public void setRunDate(String tmp) {
    this.runDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the totalContacts attribute of the CampaignRun object
   *
   * @param tmp The new totalContacts value
   */
  public void setTotalContacts(int tmp) {
    this.totalContacts = tmp;
  }


  /**
   * Sets the totalContacts attribute of the CampaignRun object
   *
   * @param tmp The new totalContacts value
   */
  public void setTotalContacts(String tmp) {
    this.totalContacts = Integer.parseInt(tmp);
  }


  /**
   * Sets the totalSent attribute of the CampaignRun object
   *
   * @param tmp The new totalSent value
   */
  public void setTotalSent(int tmp) {
    this.totalSent = tmp;
  }


  /**
   * Sets the totalSent attribute of the CampaignRun object
   *
   * @param tmp The new totalSent value
   */
  public void setTotalSent(String tmp) {
    this.totalSent = Integer.parseInt(tmp);
  }


  /**
   * Sets the totalReplied attribute of the CampaignRun object
   *
   * @param tmp The new totalReplied value
   */
  public void setTotalReplied(int tmp) {
    this.totalReplied = tmp;
  }


  /**
   * Sets the totalReplied attribute of the CampaignRun object
   *
   * @param tmp The new totalReplied value
   */
  public void setTotalReplied(String tmp) {
    this.totalReplied = Integer.parseInt(tmp);
  }


  /**
   * Sets the totalBounced attribute of the CampaignRun object
   *
   * @param tmp The new totalBounced value
   */
  public void setTotalBounced(int tmp) {
    this.totalBounced = tmp;
  }


  /**
   * Sets the totalBounced attribute of the CampaignRun object
   *
   * @param tmp The new totalBounced value
   */
  public void setTotalBounced(String tmp) {
    this.totalBounced = Integer.parseInt(tmp);
  }


  /**
   * Gets the id attribute of the CampaignRun object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the campaignId attribute of the CampaignRun object
   *
   * @return The campaignId value
   */
  public int getCampaignId() {
    return campaignId;
  }


  /**
   * Gets the status attribute of the CampaignRun object
   *
   * @return The status value
   */
  public int getStatus() {
    return status;
  }


  /**
   * Gets the runDate attribute of the CampaignRun object
   *
   * @return The runDate value
   */
  public java.sql.Timestamp getRunDate() {
    return runDate;
  }


  /**
   * Gets the totalContacts attribute of the CampaignRun object
   *
   * @return The totalContacts value
   */
  public int getTotalContacts() {
    return totalContacts;
  }


  /**
   * Gets the totalSent attribute of the CampaignRun object
   *
   * @return The totalSent value
   */
  public int getTotalSent() {
    return totalSent;
  }


  /**
   * Gets the totalReplied attribute of the CampaignRun object
   *
   * @return The totalReplied value
   */
  public int getTotalReplied() {
    return totalReplied;
  }


  /**
   * Gets the totalBounced attribute of the CampaignRun object
   *
   * @return The totalBounced value
   */
  public int getTotalBounced() {
    return totalBounced;
  }


  /**
   * Constructor for the CampaignRun object
   */
  public CampaignRun() {
  }


  /**
   * Constructor for the CampaignRun object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public CampaignRun(Connection db, int id) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM campaign_run " +
        "WHERE id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Campaign Run ID not found");
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (campaignId == -1) {
      throw new SQLException("Campaign ID not specified");
    }
    StringBuffer sql = new StringBuffer();
    id = DatabaseUtils.getNextSeq(db, "campaign_run_id_seq");
    sql.append(
        "INSERT INTO campaign_run " +
        "(campaign_id, status, " +
        "total_contacts, total_sent, total_replied, ");
    if (runDate != null) {
      sql.append("run_date, ");
    }
    if (id > -1) {
      sql.append("id, ");
    }
    sql.append("total_bounced) ");
    sql.append("VALUES ");
    sql.append("(?, ?, ?, ?, ?, ");
    if (runDate != null) {
      sql.append("?, ");
    }
    if (id > -1) {
      sql.append("?, ");
    }
    sql.append("?) ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, campaignId);
    pst.setInt(++i, status);
    pst.setInt(++i, totalContacts);
    pst.setInt(++i, totalSent);
    pst.setInt(++i, totalReplied);
    if (runDate != null) {
      pst.setTimestamp(++i, runDate);
    }
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, totalBounced);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "campaign_run_id_seq", id);
    return true;
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("id");
    campaignId = rs.getInt("campaign_id");
    status = rs.getInt("status");
    runDate = rs.getTimestamp("run_date");
    totalContacts = rs.getInt("total_contacts");
    totalSent = rs.getInt("total_sent");
    totalReplied = rs.getInt("total_replied");
    totalBounced = rs.getInt("total_bounced");
  }
}

