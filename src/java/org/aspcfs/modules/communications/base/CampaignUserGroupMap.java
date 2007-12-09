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
package org.aspcfs.modules.communications.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.Template;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.text.DateFormat;
import java.util.*;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    March 10, 2006
 * @version    $Id: Exp$
 */
public class CampaignUserGroupMap extends GenericBean {
  private int id = -1;
  private int campaignId = -1;
  private int userGroupId = -1;
  private String groupName = null;
  private boolean canDelete = false;


  /**
   *  Constructor for the CampaignUserGroupMap object
   */
  public CampaignUserGroupMap() { }


  /**
   *  Constructor for the CampaignUserGroupMap object
   *
   * @param  db                Description of the Parameter
   * @param  tmpId             Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public CampaignUserGroupMap(Connection db, String tmpId) throws SQLException {
    queryRecord(db, Integer.parseInt(tmpId));
  }


  /**
   *  Constructor for the CampaignUserGroupMap object
   *
   * @param  db                Description of the Parameter
   * @param  campaignId        Description of the Parameter
   * @param  userGroupId       Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public CampaignUserGroupMap(Connection db, int campaignId, int userGroupId) throws SQLException {
    queryRecord(db, campaignId, userGroupId);
  }


  /**
   *  Constructor for the CampaignUserGroupMap object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public CampaignUserGroupMap(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        "SELECT cgm.*, ug.group_name AS name " +
        "FROM campaign_group_map cgm " +
        "LEFT JOIN user_group ug ON (cgm.user_group_id = ug.group_id) " +
        "WHERE cgm.map_id = ? ");
    pst.setInt(1, id);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  campaignId        Description of the Parameter
   * @param  userGroupId       Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int campaignId, int userGroupId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        "SELECT cgm.*, ug.group_name AS name " +
        "FROM campaign_group_map cgm " +
        "LEFT JOIN user_group ug ON (cgm.user_group_id = ug.group_id) " +
        "WHERE cgm.campaign_id = ? " +
        "AND cgm.user_group_id = ? ");
    pst.setInt(1, campaignId);
    pst.setInt(2, userGroupId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  void buildRecord(ResultSet rs) throws SQLException {
    //campaign_group_map table
    id = rs.getInt("map_id");
    campaignId = rs.getInt("campaign_id");
    userGroupId = rs.getInt("user_group_id");
    //user_group table
    groupName = rs.getString("name");
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean parse(Connection db) throws SQLException {
    if (canDelete && this.getId() > -1) {
      return delete(db);
    }
    PreparedStatement pst = null;
    ResultSet rs = null;
    int i = 0;
    if (id == -1) {
      id = DatabaseUtils.getNextSeq(db, "campaign_group_map_map_id_seq");
      pst = db.prepareStatement("INSERT INTO campaign_group_map (" + (id > -1 ? "map_id," : "") + " campaign_id, user_group_id) " +
          " VALUES (" + (this.getId() > -1 ? "?," : "") + "?,?) ");
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setInt(++i, this.getCampaignId());
      pst.setInt(++i, this.getUserGroupId());
      pst.execute();
      id = DatabaseUtils.getCurrVal(db, "campaign_group_map_map_id_seq", id);
      pst.close();
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement("DELETE FROM campaign_group_map WHERE map_id = ? ");
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();
    return true;
  }


  /*
   *  Get and Set methods
   */
  /**
   *  Gets the id attribute of the CampaignUserGroupMap object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the CampaignUserGroupMap object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the CampaignUserGroupMap object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the campaignId attribute of the CampaignUserGroupMap object
   *
   * @return    The campaignId value
   */
  public int getCampaignId() {
    return campaignId;
  }


  /**
   *  Sets the campaignId attribute of the CampaignUserGroupMap object
   *
   * @param  tmp  The new campaignId value
   */
  public void setCampaignId(int tmp) {
    this.campaignId = tmp;
  }


  /**
   *  Sets the campaignId attribute of the CampaignUserGroupMap object
   *
   * @param  tmp  The new campaignId value
   */
  public void setCampaignId(String tmp) {
    this.campaignId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the userGroupId attribute of the CampaignUserGroupMap object
   *
   * @return    The userGroupId value
   */
  public int getUserGroupId() {
    return userGroupId;
  }


  /**
   *  Sets the userGroupId attribute of the CampaignUserGroupMap object
   *
   * @param  tmp  The new userGroupId value
   */
  public void setUserGroupId(int tmp) {
    this.userGroupId = tmp;
  }


  /**
   *  Sets the userGroupId attribute of the CampaignUserGroupMap object
   *
   * @param  tmp  The new userGroupId value
   */
  public void setUserGroupId(String tmp) {
    this.userGroupId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the groupName attribute of the CampaignUserGroupMap object
   *
   * @return    The groupName value
   */
  public String getGroupName() {
    return groupName;
  }


  /**
   *  Sets the groupName attribute of the CampaignUserGroupMap object
   *
   * @param  tmp  The new groupName value
   */
  public void setGroupName(String tmp) {
    this.groupName = tmp;
  }


  /**
   *  Gets the canDelete attribute of the CampaignUserGroupMap object
   *
   * @return    The canDelete value
   */
  public boolean getCanDelete() {
    return canDelete;
  }


  /**
   *  Sets the canDelete attribute of the CampaignUserGroupMap object
   *
   * @param  tmp  The new canDelete value
   */
  public void setCanDelete(boolean tmp) {
    this.canDelete = tmp;
  }


  /**
   *  Sets the canDelete attribute of the CampaignUserGroupMap object
   *
   * @param  tmp  The new canDelete value
   */
  public void setCanDelete(String tmp) {
    this.canDelete = DatabaseUtils.parseBoolean(tmp);
  }
}

