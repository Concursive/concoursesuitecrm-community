//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.StringTokenizer;
import java.sql.*;
import com.darkhorseventures.utils.DateUtils;
import com.darkhorseventures.utils.DatabaseUtils;

public class ScheduledRecipient {

  int id = -1;
  int campaignId = -1;
  int contactId = -1;
  int runId = -1;
  int statusId = 0;
  String status = null;
  java.sql.Timestamp statusDate = null;
  java.sql.Timestamp scheduledDate = null;
  java.sql.Timestamp sentDate = null;
  java.sql.Timestamp replyDate = null;
  java.sql.Timestamp bounceDate = null;

  public ScheduledRecipient() { }
  
  public ScheduledRecipient(Connection db, String id) throws SQLException {
          queryRecord(db, Integer.parseInt(id));
  }
  
  public ScheduledRecipient(Connection db, int id) throws SQLException {
          queryRecord(db, id);
  }
  
public void setId(int tmp) { this.id = tmp; }
public void setId(String tmp) { this.id = Integer.parseInt(tmp); }
public void setCampaignId(int tmp) { this.campaignId = tmp; }
public void setCampaignId(String tmp) { this.campaignId = Integer.parseInt(tmp); }
public void setContactId(int tmp) { this.contactId = tmp; }
public void setContactId(String tmp) { this.contactId = Integer.parseInt(tmp); }
public void setRunId(int tmp) { this.runId = tmp; }
public void setRunId(String tmp) { this.runId = Integer.parseInt(tmp); }
public void setStatusId(int tmp) { this.statusId = tmp; }
public void setStatusId(String tmp) { this.statusId = Integer.parseInt(tmp); }

public void setStatus(String tmp) { this.status = tmp; }

public void setStatusDate(java.sql.Timestamp tmp) { this.statusDate = tmp; }

  public void setStatusDate(String tmp) {
    this.statusDate = DateUtils.parseTimestampString(tmp);
  }
  
public void setScheduledDate(java.sql.Timestamp tmp) { this.scheduledDate = tmp; }

  public void setScheduledDate(String tmp) {
    this.scheduledDate = DateUtils.parseTimestampString(tmp);
  }
  
public void setSentDate(java.sql.Timestamp tmp) { this.sentDate = tmp; }

  public void setSentDate(String tmp) {
    this.sentDate = DateUtils.parseTimestampString(tmp);
  }
  
public void setReplyDate(java.sql.Timestamp tmp) { this.replyDate = tmp; }

  public void setReplyDate(String tmp) {
    this.replyDate = DateUtils.parseTimestampString(tmp);
  }
  
public void setBounceDate(java.sql.Timestamp tmp) { this.bounceDate = tmp; }

  public void setBounceDate(String tmp) {
    this.bounceDate = DateUtils.parseTimestampString(tmp);
  }


public int getId() { return id; }
public int getCampaignId() { return campaignId; }
public int getContactId() { return contactId; }
public int getRunId() { return runId; }
public int getStatusId() { return statusId; }
public String getStatus() { return status; }
public java.sql.Timestamp getStatusDate() { return statusDate; }
public java.sql.Timestamp getScheduledDate() { return scheduledDate; }
public java.sql.Timestamp getSentDate() { return sentDate; }
public java.sql.Timestamp getReplyDate() { return replyDate; }
public java.sql.Timestamp getBounceDate() { return bounceDate; }

  
  public void queryRecord(Connection db, int id) throws SQLException {
          Statement st = null;
          ResultSet rs = null;

          StringBuffer sql = new StringBuffer();
          sql.append(
                "SELECT s.*, " +
                "FROM scheduled_recipient s " +
                "WHERE s.id > -1 ");

        if (id > -1) {
                sql.append("AND s.id = " + id + " ");
        } else {
                throw new SQLException("Scheduled Recipient not found.");
        }

        st = db.createStatement();
        rs = st.executeQuery(sql.toString());
        
        if (rs.next()) {
                buildRecord(rs);
        } else {
            rs.close();
            st.close(); 
            throw new SQLException("Scheduled Recipient not found.");
        }
            rs.close();
            st.close();
  }  
  
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();

    try {
      db.setAutoCommit(false);
      sql.append(
          "INSERT INTO scheduled_recipient (campaign_id, contact_id, run_id, status_id, status, status_date, scheduled_date, " +
          "sent_date, reply_date, bounce_date) ");
      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?");
      
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, this.getCampaignId());
      if (campaignId > -1) {
	      pst.setInt(++i, this.getCampaignId());
      } else {
	      pst.setNull(++i, java.sql.Types.INTEGER);
      }
      pst.setInt(++i, this.getContactId());
      if (campaignId > -1) {
	      pst.setInt(++i, this.getContactId());
      } else {
	      pst.setNull(++i, java.sql.Types.INTEGER);
      }      
      
      pst.setInt(++i, this.getRunId());
      pst.setInt(++i, this.getStatusId());
      pst.setString(++i, this.getStatus());
      pst.setTimestamp(++i, statusDate);
      pst.setTimestamp(++i, scheduledDate);
      pst.setTimestamp(++i, sentDate);
      pst.setTimestamp(++i, replyDate);
      pst.setTimestamp(++i, bounceDate);          
        
      pst.execute();
      pst.close();

      id = DatabaseUtils.getCurrVal(db, "scheduled_recipient_id_seq");
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }

  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("id");
    campaignId = rs.getInt("campaign_id");
    if (rs.wasNull()) {
            campaignId = -1;
    }
    contactId = rs.getInt("contact_id");
    if (rs.wasNull()) {
            contactId = -1;
    }
    runId = rs.getInt("run_id");
    statusId = rs.getInt("status_id");
    status = rs.getString("status");
    statusDate = rs.getTimestamp("status_date");
    scheduledDate = rs.getTimestamp("scheduled_date");
    sentDate = rs.getTimestamp("sent_date");
    replyDate = rs.getTimestamp("reply_date");
    bounceDate = rs.getTimestamp("bounce_date");
  }

}

