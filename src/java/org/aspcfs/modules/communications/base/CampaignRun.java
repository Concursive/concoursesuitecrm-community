//Copyright 2001-2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;

public class CampaignRun {

  private int id = -1;
  private int campaignId = -1;
  private int status = 0;
  private java.sql.Timestamp runDate = null;
  private int totalContacts = 0;
  private int totalSent = 0;
  private int totalReplied = 0;
  private int totalBounced = 0;
  
  public void setId(int tmp) { this.id = tmp; }
  public void setId(String tmp) { this.id = Integer.parseInt(tmp); }
  
  public void setCampaignId(int tmp) { this.campaignId = tmp; }
  public void setCampaignId(String tmp) { this.campaignId = Integer.parseInt(tmp); }
  
  public void setStatus(int tmp) { this.status = tmp; }
  public void setStatus(String tmp) { this.status = Integer.parseInt(tmp); }
  
  public void setRunDate(java.sql.Timestamp tmp) { this.runDate = tmp; }
  public void setRunDate(String tmp) { this.runDate = DatabaseUtils.parseTimestamp(tmp); }
  
  public void setTotalContacts(int tmp) { this.totalContacts = tmp; }
  public void setTotalContacts(String tmp) { this.totalContacts = Integer.parseInt(tmp); }
  
  public void setTotalSent(int tmp) { this.totalSent = tmp; }
  public void setTotalSent(String tmp) { this.totalSent = Integer.parseInt(tmp); }
  
  public void setTotalReplied(int tmp) { this.totalReplied = tmp; }
  public void setTotalReplied(String tmp) { this.totalReplied = Integer.parseInt(tmp); }
  
  public void setTotalBounced(int tmp) { this.totalBounced = tmp; }
  public void setTotalBounced(String tmp) { this.totalBounced = Integer.parseInt(tmp); }
  
  public int getId() { return id; }
  public int getCampaignId() { return campaignId; }
  public int getStatus() { return status; }
  public java.sql.Timestamp getRunDate() { return runDate; }
  public int getTotalContacts() { return totalContacts; }
  public int getTotalSent() { return totalSent; }
  public int getTotalReplied() { return totalReplied; }
  public int getTotalBounced() { return totalBounced; }

  
  public CampaignRun() {}
  
  public boolean insert(Connection db) throws SQLException {
    if (campaignId == -1) {
      throw new SQLException("Campaign ID not specified");
    }
    StringBuffer sql = new StringBuffer();
    sql.append(
      "INSERT INTO campaign_run " +
      "(campaign_id, status, " +
      "total_contacts, total_sent, total_replied, ");
    if (runDate != null) {
      sql.append("run_date, ");
    }
    sql.append("total_bounced) ");
    sql.append("VALUES ");
    sql.append("(?, ?, ?, ?, ?, ");
    if (runDate != null) {
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
    pst.setInt(++i, totalBounced);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "campaign_run_id_seq");
    return true;
  }
}
