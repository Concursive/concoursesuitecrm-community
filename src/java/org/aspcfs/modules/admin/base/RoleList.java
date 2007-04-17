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
package org.aspcfs.modules.admin.base;

import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Builds a list of user Roles
 *
 * @author matt rajkowski
 * @version $Id$
 * @created January 14, 2002
 */
public class RoleList extends ArrayList implements SyncableList {

  private static final long serialVersionUID = -3640700750213144515L;
  private PagedListInfo pagedListInfo = null;
  private String emptyHtmlSelectRecord = null;
  private int enabledState = Constants.TRUE;
  private boolean buildUsers = false;
  private boolean buildUserCount = false;
  private int roleType = -1;
  private int excludeRoleType = -1;
  private String jsEvent = null;

  //Sync variables
  public final static String tableName = "role";
  public final static String uniqueField = "role_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  /**
   * Constructor for the RoleList object
   */
  public RoleList() {
  }

  /**
   * Gets the tableName attribute of the UserList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the UserList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }

  /**
   * Sets the lastAnchor attribute of the UserList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the UserList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the UserList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the UserList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the UserList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   * Sets the syncType attribute of the UserList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }

  /**
   * Sets the pagedListInfo attribute of the RoleList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the emptyHtmlSelectRecord attribute of the RoleList object
   *
   * @param tmp The new emptyHtmlSelectRecord value
   */
  public void setEmptyHtmlSelectRecord(String tmp) {
    this.emptyHtmlSelectRecord = tmp;
  }


  /**
   * Sets the enabledState attribute of the RoleList object
   *
   * @param booleanInt The new enabledState value
   */
  public void setEnabledState(int booleanInt) {
    enabledState = booleanInt;
  }

  public void setEnabledState(String booleanInt) {
    enabledState = Integer.parseInt(booleanInt);
  }

  /**
   * Sets the buildUsers attribute of the RoleList object
   *
   * @param tmp The new buildUsers value
   */
  public void setBuildUsers(boolean tmp) {
    this.buildUsers = tmp;
  }


  /**
   * Sets the buildUserCount attribute of the RoleList object
   *
   * @param tmp The new buildUserCount value
   */
  public void setBuildUserCount(boolean tmp) {
    this.buildUserCount = tmp;
  }


  /**
   * Sets the roleType attribute of the RoleList object
   *
   * @param tmp The new roleType value
   */
  public void setRoleType(int tmp) {
    this.roleType = tmp;
  }


  /**
   * Sets the roleType attribute of the RoleList object
   *
   * @param tmp The new roleType value
   */
  public void setRoleType(String tmp) {
    this.roleType = Integer.parseInt(tmp);
  }


  /**
   * Sets the excludeRoleType attribute of the RoleList object
   *
   * @param tmp The new excludeRoleType value
   */
  public void setExcludeRoleType(int tmp) {
    this.excludeRoleType = tmp;
  }

  public void setJsEvent(String jsEvent) {
    this.jsEvent = jsEvent;
  }

  public String getJsEvent() {
    return jsEvent;
  }

  /**
   * Gets the htmlSelect attribute of the RoleList object
   *
   * @param selectName Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   * Gets the htmlSelect attribute of the RoleList object Selects the regular
   * roles from the role list
   *
   * @param selectName Description of the Parameter
   * @param defaultKey Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect roleListSelect = new HtmlSelect();
    if (emptyHtmlSelectRecord != null) {
      roleListSelect.addItem(-1, emptyHtmlSelectRecord);
    }
    if (jsEvent != null) {
      roleListSelect.setJsEvent(jsEvent);
    }
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Role thisRole = (Role) i.next();
      roleListSelect.addItem(
          thisRole.getId(),
          thisRole.getRole());
    }
    return roleListSelect.getHtml(selectName, defaultKey);
  }


  /**
   * Gets the lookupList attribute of the RoleList object
   *
   * @return The lookupList value
   */
  public LookupList getLookupList() {
    LookupList thisList = new LookupList();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Role thisRole = (Role) i.next();
      thisList.addItem(
          thisRole.getId(),
          thisRole.getRole());
    }
    return thisList;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = this.prepareList(db);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
    while (rs.next()) {
      this.add(RoleList.getObject(rs));
    }
    rs.close();
    if(pst != null){
      pst.close();
    }

    //Build resources
    if (buildUsers || buildUserCount) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        Role thisRole = (Role) i.next();
        if (buildUsers) {
          thisRole.buildUserList(db);
        }
        if (buildUserCount) {
          thisRole.buildUserCount(db, true);
        }
      }
    }
  }

  /**
   * Description of the Method
   *
   * @param rs
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public static Role getObject(ResultSet rs) throws SQLException {
    Role role = new Role(rs);
    return role;
  }

  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (roleType != -1) {
      sqlFilter.append("AND role_type = ? ");
    }
    if (excludeRoleType != -1) {
      sqlFilter.append("AND role_type <> ? ");
    }
    if (enabledState != Constants.UNDEFINED) {
      sqlFilter.append("AND enabled = ? ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND entered > ? ");
      }
      sqlFilter.append("AND entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND modified > ? ");
      sqlFilter.append("AND entered < ? ");
      sqlFilter.append("AND modified < ? ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (roleType != -1) {
      pst.setInt(++i, roleType);
    }
    if (excludeRoleType != -1) {
      pst.setInt(++i, excludeRoleType);
    }
    if (enabledState != Constants.UNDEFINED) {
      pst.setBoolean(++i, enabledState == Constants.TRUE);
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      pst.setTimestamp(++i, nextAnchor);
    }
    if (syncType == Constants.SYNC_UPDATES) {
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, nextAnchor);
    }
    return i;
  }

  /**
   * Description of the Method
   *
   * @param db
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public PreparedStatement prepareList(Connection db) throws SQLException {
    ResultSet rs = null;
    int items = -1;
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
            "FROM " + DatabaseUtils.addQuotes(db, "role") + " r " +
            "WHERE r.role_id > -1 ");
    createFilter(sqlFilter);
    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      PreparedStatement pst = db.prepareStatement(
          sqlCount.toString() +
              sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();
      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(
            sqlCount.toString() +
                sqlFilter.toString() +
                "AND " + DatabaseUtils.toLowerCase(db) + "(" + DatabaseUtils.addQuotes(db, "role") + ") < ? ");
        items = prepareFilter(pst);
        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }
      //Determine column to sort by
      pagedListInfo.setDefaultSort("role", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY " + DatabaseUtils.addQuotes(db, "role") + " ");
    }
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "r.* " +
            "FROM " + DatabaseUtils.addQuotes(db, "role") + " r " +
            "WHERE r.role_id > -1 ");
    PreparedStatement pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    return pst;
  }

  /**
   * Gets the roleNameFromId attribute of the RoleList object
   *
   * @param id Description of the Parameter
   * @return The roleNameFromId value
   */
  public String getRoleNameFromId(int id) {
    String result = null;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Role thisRole = (Role) i.next();
      if (id == thisRole.getId()) {
        result = thisRole.getRole();
        break;
      }
    }
    return result;
  }

}

