//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.StringTokenizer;
import java.sql.*;
import com.darkhorseventures.utils.DateUtils;
import com.darkhorseventures.utils.DatabaseUtils;

public class CampaignListGroup {

  int campaignId = -1;
  int groupId = -1;

  public CampaignListGroup() { }
  
  public CampaignListGroup(Connection db, String id) throws SQLException {
          queryRecord(db, Integer.parseInt(id));
  }
  
  public CampaignListGroup(Connection db, int id) throws SQLException {
          queryRecord(db, id);
  }
  
public int getCampaignId() { return campaignId; }
public int getGroupId() { return groupId; }
public void setCampaignId(int tmp) { this.campaignId = tmp; }
public void setGroupId(int tmp) { this.groupId = tmp; }
public void setCampaignId(String tmp) { this.campaignId = Integer.parseInt(tmp); }
public void setGroupId(String tmp) { this.groupId = Integer.parseInt(tmp); }
  
  public void queryRecord(Connection db, int id) throws SQLException {
          if (id == -1) {
                  throw new SQLException("Campaign List Group not found.");
          }
          
          PreparedStatement pst = db.prepareStatement("SELECT c.* " +
                "FROM campaign_list_groups c " +
                "WHERE c.campaign_id = ? and c.group_id = ? ");
          pst.setInt(1, campaignId);
          pst.setInt(2, groupId);
          ResultSet rs = pst.executeQuery();
        if (rs.next()) {
                buildRecord(rs);
        } else {
            rs.close();
            throw new SQLException("Campaign List Group not found.");
        }
            rs.close();
  }  
  
  public boolean insert(Connection db) throws SQLException {
          
    StringBuffer sql = new StringBuffer();

    try {
      db.setAutoCommit(false);
      sql.append(
          "INSERT INTO campaign_list_groups (campaign_id, group_id) ");
      sql.append("VALUES (?, ?) ");
      
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      if (campaignId > -1) {
	      pst.setInt(++i, this.getCampaignId());
      } else {
	      pst.setNull(++i, java.sql.Types.INTEGER);
      }
      
      if (groupId > -1) {
	      pst.setInt(++i, this.getGroupId());
      } else {
	      pst.setNull(++i, java.sql.Types.INTEGER);
      }
        
      pst.execute();
      pst.close();

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
    campaignId = rs.getInt("campaign_id");
    groupId = rs.getInt("group_id");
  }

}

