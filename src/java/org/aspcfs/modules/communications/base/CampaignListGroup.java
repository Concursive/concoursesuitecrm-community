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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author -
 * @version $Id: CampaignListGroup.java,v 1.4 2003/03/07 14:13:39 mrajkowski
 *          Exp $
 * @created January 15, 2003
 */
public class CampaignListGroup {

  int campaignId = -1;
  int groupId = -1;


  /**
   * Constructor for the CampaignListGroup object
   */
  public CampaignListGroup() {
  }


  /**
   * Constructor for the CampaignListGroup object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public CampaignListGroup(Connection db, String id) throws SQLException {
    queryRecord(db, Integer.parseInt(id));
  }


  /**
   * Constructor for the CampaignListGroup object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public CampaignListGroup(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   * Gets the campaignId attribute of the CampaignListGroup object
   *
   * @return The campaignId value
   */
  public int getCampaignId() {
    return campaignId;
  }


  /**
   * Gets the groupId attribute of the CampaignListGroup object
   *
   * @return The groupId value
   */
  public int getGroupId() {
    return groupId;
  }


  /**
   * Sets the campaignId attribute of the CampaignListGroup object
   *
   * @param tmp The new campaignId value
   */
  public void setCampaignId(int tmp) {
    this.campaignId = tmp;
  }


  /**
   * Sets the groupId attribute of the CampaignListGroup object
   *
   * @param tmp The new groupId value
   */
  public void setGroupId(int tmp) {
    this.groupId = tmp;
  }


  /**
   * Sets the campaignId attribute of the CampaignListGroup object
   *
   * @param tmp The new campaignId value
   */
  public void setCampaignId(String tmp) {
    this.campaignId = Integer.parseInt(tmp);
  }


  /**
   * Sets the groupId attribute of the CampaignListGroup object
   *
   * @param tmp The new groupId value
   */
  public void setGroupId(String tmp) {
    this.groupId = Integer.parseInt(tmp);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Campaign List Group not found.");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT c.* " +
        "FROM campaign_list_groups c " +
        "WHERE c.campaign_id = ? and c.group_id = ? ");
    pst.setInt(1, campaignId);
    pst.setInt(2, groupId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Campaign List Group not found.");
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

    StringBuffer sql = new StringBuffer();

    try {
      db.setAutoCommit(false);
      sql.append("INSERT INTO campaign_list_groups (campaign_id, group_id) ");
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
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    campaignId = rs.getInt("campaign_id");
    groupId = rs.getInt("group_id");
  }

}

