//Copyright 2001-2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;

public class ExcludedRecipient {

  private int id = -1;
  private int campaignId = -1;
  private int contactId = -1;
  
  public ExcludedRecipient() {}
  
  public void setId(int tmp) { this.id = tmp; }
  public void setId(String tmp) { this.id = Integer.parseInt(tmp); }
  public void setCampaignId(int tmp) { this.campaignId = tmp; }
  public void setCampaignId(String tmp) { this.campaignId = Integer.parseInt(tmp); }
  public void setContactId(int tmp) { this.contactId = tmp; }
  public void setContactId(String tmp) { this.contactId = Integer.parseInt(tmp); }
  public int getId() { return id; }
  public int getCampaignId() { return campaignId; }
  public int getContactId() { return contactId; }
  
  public boolean insert(Connection db) throws SQLException {
    if (campaignId == -1) {
      throw new SQLException("Campaign ID not specified");
    }
    if (contactId == -1) {
      throw new SQLException("Contact ID not specified");
    }
    PreparedStatement pst = db.prepareStatement(
      "INSERT INTO excluded_recipient " +
      "(campaign_id, contact_id) VALUES " +
      "(?, ?) ");
    pst.setInt(1, campaignId);
    pst.setInt(2, contactId);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "excluded_recipient_id_seq");
    return true;
  }
  
  public boolean delete(Connection db) throws SQLException {
    if (id == -1) {
      if (campaignId == -1) {
        throw new SQLException("Campaign ID not specified");
      }
      if (contactId == -1) {
        throw new SQLException("Contact ID not specified");
      }
      
      PreparedStatement pst = db.prepareStatement(
      "DELETE FROM excluded_recipient " +
      "WHERE campaign_id = ? AND contact_id = ? ");
      pst.setInt(1, campaignId);
      pst.setInt(2, contactId);
      pst.execute();
      pst.close();
    } else {
      PreparedStatement pst = db.prepareStatement(
      "DELETE FROM excluded_recipient " +
      "WHERE id = ? ");
      pst.setInt(1, id);
      pst.execute();
      pst.close();
    }
    return true;
  }
}
