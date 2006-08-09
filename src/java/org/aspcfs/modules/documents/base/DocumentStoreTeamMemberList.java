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
package org.aspcfs.modules.documents.base;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.modules.base.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Represents a list of members of a document store
 *
 * @author
 * @version $Id: DocumentStoreTeamMemberList.java,v 1.1.2.2 2004/11/10
 *          23:13:18 kbhoopal Exp $
 * @created
 */
public class DocumentStoreTeamMemberList extends ArrayList {
  public final static String USER = "user";
  public final static String GROUP = "group";
  public final static String ROLE = "role";
  public final static String DEPARTMENT = "department";

  private PagedListInfo pagedListInfo = null;
  private String emptyHtmlSelectRecord = null;
  private DocumentStore documentStore = null;

  private String memberType = null;
  private String groupType = null;
  private int documentStoreId = -1;
  private int userLevel = -1;
  private String insertMembers = null;
  private String deleteMembers = null;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private int forDocumentStoreUser = -1;

  private boolean employeesOnly = false;
  private boolean accountContactsOnly = false;
  private boolean portalUsersOnly = false;

  /**
   * Constructor for the DocumentStoreTeamMemberList object
   */
  public DocumentStoreTeamMemberList() {
  }


  /**
   * Sets the pagedListInfo attribute of the DocumentStoreTeamMemberList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the emptyHtmlSelectRecord attribute of the
   * DocumentStoreTeamMemberList object
   *
   * @param tmp The new emptyHtmlSelectRecord value
   */
  public void setEmptyHtmlSelectRecord(String tmp) {
    this.emptyHtmlSelectRecord = tmp;
  }


  /**
   * Sets the documentStore attribute of the DocumentStoreTeamMemberList object
   *
   * @param tmp The new documentStore value
   */
  public void setDocumentStore(DocumentStore tmp) {
    this.documentStore = tmp;
  }


  /**
   * Sets the memberType attribute of the DocumentStoreTeamMemberList object
   *
   * @param tmp The new memberType value
   */
  public void setMemberType(String tmp) {
    this.memberType = tmp;
  }


  /**
   * Sets the groupType attribute of the DocumentStoreTeamMemberList object
   *
   * @param tmp The new groupType value
   */
  public void setGroupType(String tmp) {
    this.groupType = tmp;
  }


  /**
   * Sets the documentStoreId attribute of the DocumentStoreTeamMemberList
   * object
   *
   * @param tmp The new documentStoreId value
   */
  public void setDocumentStoreId(int tmp) {
    this.documentStoreId = tmp;
  }


  /**
   * Sets the documentStoreId attribute of the DocumentStoreTeamMemberList
   * object
   *
   * @param tmp The new documentStoreId value
   */
  public void setDocumentStoreId(String tmp) {
    this.documentStoreId = Integer.parseInt(tmp);
  }


  /**
   * Sets the userLevel attribute of the DocumentStoreTeamMemberList object
   *
   * @param tmp The new userLevel value
   */
  public void setUserLevel(int tmp) {
    this.userLevel = tmp;
  }


  /**
   * Sets the userLevel attribute of the DocumentStoreTeamMemberList object
   *
   * @param tmp The new userLevel value
   */
  public void setUserLevel(String tmp) {
    this.userLevel = Integer.parseInt(tmp);
  }


  /**
   * Sets the insertMembers attribute of the DocumentStoreTeamMemberList object
   *
   * @param tmp The new insertMembers value
   */
  public void setInsertMembers(String tmp) {
    this.insertMembers = tmp;
  }


  /**
   * Sets the deleteMembers attribute of the DocumentStoreTeamMemberList object
   *
   * @param tmp The new deleteMembers value
   */
  public void setDeleteMembers(String tmp) {
    this.deleteMembers = tmp;
  }


  /**
   * Sets the enteredBy attribute of the DocumentStoreTeamMemberList object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the DocumentStoreTeamMemberList object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the DocumentStoreTeamMemberList object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the DocumentStoreTeamMemberList object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the forDocumentStoreUser attribute of the DocumentStoreTeamMemberList
   * object
   *
   * @param tmp The new forDocumentStoreUser value
   */
  public void setForDocumentStoreUser(int tmp) {
    this.forDocumentStoreUser = tmp;
  }


  /**
   * Sets the forDocumentStoreUser attribute of the DocumentStoreTeamMemberList
   * object
   *
   * @param tmp The new forDocumentStoreUser value
   */
  public void setForDocumentStoreUser(String tmp) {
    this.forDocumentStoreUser = Integer.parseInt(tmp);
  }


  /**
   * Sets the employeesOnly attribute of the DocumentStoreTeamMemberList object
   *
   * @param tmp The new employeesOnly value
   */
  public void setEmployeesOnly(boolean tmp) {
    this.employeesOnly = tmp;
  }


  /**
   * Sets the employeesOnly attribute of the DocumentStoreTeamMemberList object
   *
   * @param tmp The new employeesOnly value
   */
  public void setEmployeesOnly(String tmp) {
    this.employeesOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the accountContactsOnly attribute of the DocumentStoreTeamMemberList
   * object
   *
   * @param tmp The new accountContactsOnly value
   */
  public void setAccountContactsOnly(boolean tmp) {
    this.accountContactsOnly = tmp;
  }


  /**
   * Sets the accountContactsOnly attribute of the DocumentStoreTeamMemberList
   * object
   *
   * @param tmp The new accountContactsOnly value
   */
  public void setAccountContactsOnly(String tmp) {
    this.accountContactsOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the pagedListInfo attribute of the DocumentStoreTeamMemberList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Gets the emptyHtmlSelectRecord attribute of the
   * DocumentStoreTeamMemberList object
   *
   * @return The emptyHtmlSelectRecord value
   */
  public String getEmptyHtmlSelectRecord() {
    return emptyHtmlSelectRecord;
  }


  /**
   * Gets the documentStore attribute of the DocumentStoreTeamMemberList object
   *
   * @return The documentStore value
   */
  public DocumentStore getDocumentStore() {
    return documentStore;
  }


  /**
   * Gets the memberType attribute of the DocumentStoreTeamMemberList object
   *
   * @return The memberType value
   */
  public String getMemberType() {
    return memberType;
  }


  /**
   * Gets the groupType attribute of the DocumentStoreTeamMemberList object
   *
   * @return The groupType value
   */
  public String getGroupType() {
    return groupType;
  }


  /**
   * Gets the documentStoreId attribute of the DocumentStoreTeamMemberList
   * object
   *
   * @return The documentStoreId value
   */
  public int getDocumentStoreId() {
    return documentStoreId;
  }


  /**
   * Gets the userLevel attribute of the DocumentStoreTeamMemberList object
   *
   * @return The userLevel value
   */
  public int getUserLevel() {
    return userLevel;
  }


  /**
   * Gets the insertMembers attribute of the DocumentStoreTeamMemberList object
   *
   * @return The insertMembers value
   */
  public String getInsertMembers() {
    return insertMembers;
  }


  /**
   * Gets the deleteMembers attribute of the DocumentStoreTeamMemberList object
   *
   * @return The deleteMembers value
   */
  public String getDeleteMembers() {
    return deleteMembers;
  }


  /**
   * Gets the enteredBy attribute of the DocumentStoreTeamMemberList object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modifiedBy attribute of the DocumentStoreTeamMemberList object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the forDocumentUser attribute of the DocumentStoreTeamMemberList
   * object
   *
   * @return The forDocumentUser value
   */
  public int getForDocumentStoreUser() {
    return forDocumentStoreUser;
  }


  /**
   * Gets the employeesOnly attribute of the DocumentStoreTeamMemberList object
   *
   * @return The employeesOnly value
   */
  public boolean getEmployeesOnly() {
    return employeesOnly;
  }


  /**
   * Gets the accountContactsOnly attribute of the DocumentStoreTeamMemberList
   * object
   *
   * @return The accountContactsOnly value
   */
  public boolean getAccountContactsOnly() {
    return accountContactsOnly;
  }


  /**
	 * @return Returns the portalUsersOnly.
	 */
	public boolean getPortalUsersOnly() {
		return portalUsersOnly;
	}


	/**
	 * @param portalUsersOnly The portalUsersOnly to set.
	 */
	public void setPortalUsersOnly(boolean portalUsersOnly) {
		this.portalUsersOnly = portalUsersOnly;
	}


	/**
   * Description of the Method
   *
   * @param thisId Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean hasUserId(int thisId) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      DocumentStoreTeamMember thisMember = (DocumentStoreTeamMember) i.next();
      if (thisMember.getItemId() == thisId) {
        return true;
      }
    }
    return false;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    String tableName = "";
    if (memberType.equals(DocumentStoreTeamMemberList.USER)) {
      tableName = "document_store_user_member";
    }
    if (memberType.equals(DocumentStoreTeamMemberList.ROLE)) {
      tableName = "document_store_role_member";
    }
    if (memberType.equals(DocumentStoreTeamMemberList.DEPARTMENT)) {
      tableName = DatabaseUtils.getTableName(db, "document_store_department_member");
    }
    if ("".equals(tableName)) {
      throw new SQLException("table not specified");
    }
    buildMemberList(db, tableName);
  }


  /**
   * Constructor for the buildUserMemberList object
   *
   * @param db        Description of the Parameter
   * @param tableName Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void buildMemberList(Connection db, String tableName) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    //Need to build a base SQL statement for counting records
    if (memberType.equals(DocumentStoreTeamMemberList.USER)) {
      sqlCount.append(
          "SELECT COUNT(*) AS recordcount " +
          "FROM " + tableName + " um, contact u, lookup_document_store_role r " +
          "WHERE um.document_store_id > -1 " +
          "AND um.item_id = u.user_id " +
          "AND um.userlevel = r.code ");
    }
    if (memberType.equals(DocumentStoreTeamMemberList.ROLE)) {
      sqlCount.append(
          "SELECT COUNT(*) AS recordcount " +
          "FROM " + tableName + " um, \"role\" rl, lookup_document_store_role r " +
          "WHERE um.document_store_id > -1 " +
          "AND um.item_id = rl.role_id " +
          "AND um.userlevel = r.code ");
    }
    if (memberType.equals(DocumentStoreTeamMemberList.DEPARTMENT)) {
      sqlCount.append(
          "SELECT COUNT(*) AS recordcount " +
          "FROM " + tableName + " um, lookup_department d, lookup_document_store_role r " +
          "WHERE um.document_store_id > -1 " +
          "AND um.item_id = d.code " +
          "AND um.userlevel = r.code ");
    }
    if (pagedListInfo == null) {
      pagedListInfo = new PagedListInfo();
      pagedListInfo.setItemsPerPage(-1);
    }
    //Get the total number of records matching filter
    this.createFilter(sqlFilter, tableName);
    pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (rs.next()) {
      int maxRecords = rs.getInt("recordcount");
      pagedListInfo.setMaxRecords(maxRecords);
    }
    rs.close();
    pst.close();

    //Determine column to sort by
    pagedListInfo.setDefaultSort("r.\"level\"", null);
    pagedListInfo.appendSqlTail(db, sqlOrder);
    //Need to build a base SQL statement for returning records
    pagedListInfo.appendSqlSelectHead(db, sqlSelect);

    if (memberType.equals(DocumentStoreTeamMemberList.USER)) {
      sqlSelect.append(
          "um.*, r.\"level\" " +
          "FROM " + tableName + " um, contact u, lookup_document_store_role r " +
          "WHERE um.document_store_id > -1 " +
          "AND um.item_id = u.user_id " +
          "AND um.userlevel = r.code ");
    }
    if (memberType.equals(DocumentStoreTeamMemberList.ROLE)) {
      sqlSelect.append(
          "um.*, r.\"level\" " +
          "FROM " + tableName + " um, \"role\" rl, lookup_document_store_role r " +
          "WHERE um.document_store_id > -1 " +
          "AND um.item_id = rl.role_id " +
          "AND um.userlevel = r.code ");
    }
    if (memberType.equals(DocumentStoreTeamMemberList.DEPARTMENT)) {
      sqlSelect.append(
          "um.*, r.\"level\" " +
          "FROM " + tableName + " um, lookup_department d, lookup_document_store_role r " +
          "WHERE um.document_store_id > -1 " +
          "AND um.item_id = d.code " +
          "AND um.userlevel = r.code ");
    }
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      DocumentStoreTeamMember thisTeamMember = new DocumentStoreTeamMember(rs);
      thisTeamMember.setDocumentStore(documentStore);
      this.add(thisTeamMember);
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   * @param tableName Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter, String tableName) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (documentStoreId > -1) {
      sqlFilter.append("AND document_store_id = ? ");
    }
    if (forDocumentStoreUser > -1) {
      sqlFilter.append("AND item_id = ? " + " AND  status = -1 ");
    }
    if (userLevel > -1) {
      sqlFilter.append("AND userlevel = ? ");
    }
    if ((memberType.equals(DocumentStoreTeamMemberList.USER)) &&
        (employeesOnly)) {
      sqlFilter.append(" AND u.org_id = 0 ");
    }
    if ((memberType.equals(DocumentStoreTeamMemberList.USER)) &&
        (accountContactsOnly)) {
      sqlFilter.append(" AND u.org_id > 0 ");
      sqlFilter.append(" AND um.role_type != ").append(Constants.ROLETYPE_CUSTOMER).append(" ");
    }
    if ((memberType.equals(DocumentStoreTeamMemberList.USER)) &&
        (portalUsersOnly)) {
      sqlFilter.append(" AND u.org_id > 0 ");
      sqlFilter.append(" AND um.role_type = ").append(Constants.ROLETYPE_CUSTOMER).append(" ");
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
    if (documentStoreId > -1) {
      pst.setInt(++i, documentStoreId);
    }
    if (forDocumentStoreUser > -1) {
      pst.setInt(++i, forDocumentStoreUser);
    }
    if (userLevel > -1) {
      pst.setInt(++i, userLevel);
    }
    return i;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean updateUserMembership(Connection db) throws SQLException {
    try {
      db.setAutoCommit(false);
      //Add new members
      if (insertMembers != null && (!insertMembers.equals("")) && documentStoreId > -1) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("TeamMemberList-> New: " + insertMembers);
        }
        StringTokenizer items = new StringTokenizer(insertMembers, "|");
        while (items.hasMoreTokens()) {
          int itemId = -1;
          String itemIdValue = items.nextToken();
          if (itemIdValue.indexOf("@") > 0) {
            itemId = org.aspcfs.modules.admin.base.User.getIdByEmailAddress(
                db, itemIdValue);
          } else {
            itemId = Integer.parseInt(itemIdValue);
          }
          if (itemId == -1) {
            //If not, add them to the TeamMemberList...
            //The lead will be asked whether to send an email and invite and to
            //enter their name.
            // NOTE: This feature not supported yet
            //TeamMember member = new TeamMember();
            //User user = new User();
            //user.setEmail(itemIdValue);
            //member.setUser(user);
            //this.add(member);
          } else {
          	User user = new User(db, itemId);
            // See if ID is already on the team
            if (!isUserOnTeam(db, documentStoreId, itemId)) {
              // Insert the member
              PreparedStatement pst = db.prepareStatement(
                  "INSERT INTO document_store_user_member " +
                  "(document_store_id, item_id, userlevel, enteredby, modifiedby, status, role_type) " +
                  "VALUES (?, ?, ?, ?, ?, ?, ?) ");
              int i = 0;
              pst.setInt(++i, documentStoreId);
              pst.setInt(++i, itemId);
              DatabaseUtils.setInt(pst, ++i, userLevel);
              pst.setInt(++i, enteredBy);
              pst.setInt(++i, modifiedBy);
              pst.setInt(++i, DocumentStoreTeamMember.STATUS_ADDED);
              DatabaseUtils.setInt(pst, ++i, user.getRoleType());
              pst.execute();
              pst.close();
            }
          }
        }
      }
      //Removed deleted members
      if ((deleteMembers != null) && (!deleteMembers.equals("")) && documentStoreId > -1) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("TeamMemberList-> Del: " + deleteMembers);
        }
        //Delete everyone but self
        StringTokenizer items = new StringTokenizer(deleteMembers, "|");
        while (items.hasMoreTokens()) {
          String itemId = items.nextToken();
          if (Integer.parseInt(itemId) != modifiedBy) {
            PreparedStatement pst = db.prepareStatement(
                "DELETE FROM document_store_user_member " +
                "WHERE document_store_id = ? " +
                "AND item_id = ?");
            pst.setInt(1, documentStoreId);
            pst.setInt(2, Integer.parseInt(itemId));
            pst.execute();
            pst.close();
          }
        }
      }
      db.commit();
      db.setAutoCommit(true);
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db            Description of the Parameter
   * @param tmpMemberType Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db, String tmpMemberType) throws SQLException {
    Iterator team = this.iterator();
    while (team.hasNext()) {
      DocumentStoreTeamMember thisMember = (DocumentStoreTeamMember) team.next();
      thisMember.delete(db, tmpMemberType);
    }
  }


  /**
   * Gets the onTeam attribute of the TeamMemberList object
   *
   * @param db              Description of the Parameter
   * @param documentStoreId Description of the Parameter
   * @param userId          Description of the Parameter
   * @return The onTeam value
   * @throws SQLException Description of the Exception
   */
  public static boolean isUserOnTeam(Connection db, int documentStoreId, int userId) throws SQLException {
    boolean exists = false;
    PreparedStatement pst = db.prepareStatement(
        "SELECT userlevel " +
        "FROM document_store_user_member " +
        "WHERE document_store_id = ? " +
        "AND item_id = ? ");
    pst.setInt(1, documentStoreId);
    pst.setInt(2, userId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      exists = true;
    }
    rs.close();
    pst.close();
    return exists;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean updateGroupMembership(Connection db) throws SQLException {
    try {
      db.setAutoCommit(false);
      //Add new members
      if (insertMembers != null && (!insertMembers.equals("")) && documentStoreId > -1) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("TeamMemberList-> New: " + insertMembers);
        }
        StringTokenizer items = new StringTokenizer(insertMembers, "|");
        while (items.hasMoreTokens()) {
          int itemId = -1;
          int siteId = -1;
          String itemIdValue = items.nextToken();
          int indexValue = -1;
          String tableName = "";
          if (itemIdValue.indexOf("-D") > -1) {
            indexValue = itemIdValue.indexOf("-D");
            tableName = DatabaseUtils.getTableName(db, "document_store_department_member");
          } else {
            indexValue = itemIdValue.indexOf("-R");
            tableName = "document_store_role_member";
          }
          itemId = Integer.parseInt(itemIdValue.substring(0, indexValue));
          siteId = Integer.parseInt(itemIdValue.substring(indexValue+2, itemIdValue.length()).trim());
          if (itemId != -1) {
            // See if ID is already on the team
            if (!isGroupOnTeam(db, documentStoreId, itemId, tableName, siteId)) {
              // Insert the member
              PreparedStatement pst = db.prepareStatement(
                  "INSERT INTO " + tableName +
                  " (document_store_id, item_id, userlevel, enteredby, modifiedby, status, site_id) " +
                  " VALUES (?, ?, ?, ?, ?, ?, ?) ");
              int i = 0;
              pst.setInt(++i, documentStoreId);
              pst.setInt(++i, itemId);
              DatabaseUtils.setInt(pst, ++i, userLevel);
              pst.setInt(++i, enteredBy);
              pst.setInt(++i, modifiedBy);
              pst.setInt(++i, DocumentStoreTeamMember.STATUS_ADDED);
              DatabaseUtils.setInt(pst, ++i, siteId);
              pst.execute();
              pst.close();
            }
          }
        }
      }
      //Removed deleted members
      if ((deleteMembers != null) && (!deleteMembers.equals("")) && documentStoreId > -1) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("TeamMemberList-> Del: " + deleteMembers);
        }
        //Delete everyone but self
        StringTokenizer items = new StringTokenizer(deleteMembers, "|");
        while (items.hasMoreTokens()) {
          String itemIdValue = items.nextToken();

          int indexValue = -1;
          String tableName = "";
          if (itemIdValue.indexOf("-D") > -1) {
            indexValue = itemIdValue.indexOf("-D");
            tableName = DatabaseUtils.getTableName(db, "document_store_department_member");
          } else {
            indexValue = itemIdValue.indexOf("-R");
            tableName = "document_store_role_member";
          }
          int itemId = Integer.parseInt(itemIdValue.substring(0, indexValue));
          int siteId = Integer.parseInt(itemIdValue.substring(indexValue+2, itemIdValue.length()).trim());

          PreparedStatement pst = db.prepareStatement(
              " DELETE FROM " + tableName +
              " WHERE document_store_id = ? " +
              " AND item_id = ? " +
              " AND "+(siteId == -1?" site_id IS NULL ":" site_id = ? "));
          pst.setInt(1, documentStoreId);
          pst.setInt(2, itemId);
          if (siteId > -1) {
            pst.setInt(3, siteId);
          }
          pst.execute();
          pst.close();
        }
      }
      db.commit();
      db.setAutoCommit(true);
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    }
    return true;
  }


  /**
   * Gets the groupOnTeam attribute of the DocumentStoreTeamMemberList class
   *
   * @param db              Description of the Parameter
   * @param documentStoreId Description of the Parameter
   * @param roleId          Description of the Parameter
   * @param tableName       Description of the Parameter
   * @return The groupOnTeam value
   * @throws SQLException Description of the Exception
   */
  public static boolean isGroupOnTeam(Connection db, int documentStoreId, int roleId, String tableName, int tmpSiteId) throws SQLException {
    boolean exists = false;
    PreparedStatement pst = db.prepareStatement(
        " SELECT userlevel " +
        " FROM " + tableName +
        " WHERE document_store_id = ? " +
        " AND item_id = ? " +
        " AND "+ (tmpSiteId == -1?"site_id IS NULL ":"site_id = ? "));
    pst.setInt(1, documentStoreId);
    pst.setInt(2, roleId);
    if (tmpSiteId > -1) {
      pst.setInt(3, tmpSiteId);
    }
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      exists = true;
    }
    rs.close();
    pst.close();
    return exists;
  }


  /**
   * Re-Assigns the management of document stores from one user to an another
   *
   * @param db           Description of the Parameter
   * @param fromUserId   Description of the Parameter
   * @param toUserItemId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public static void reassignElements(Connection db, int fromUserId, int toUserItemId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        " UPDATE document_store_user_member " +
        " SET item_id = ? " +
        " WHERE item_id = ? " +
        " AND userlevel = ? ");

    int i = 0;
    pst.setInt(++i, toUserItemId);
    pst.setInt(++i, fromUserId);
    pst.setInt(++i, DocumentStoreTeamMember.DOCUMENTSTORE_MANAGER);
    pst.execute();

    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db           Description of the Parameter
   * @param fromUserId   Description of the Parameter
   * @param toUserItemId Description of the Parameter
   * @param userId       Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public static void reassignElements(Connection db, int fromUserId, int toUserItemId, int userId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        " UPDATE document_store_user_member " +
        " SET item_id = ?, modifiedby = ? " +
        " WHERE item_id = ? " +
        " AND userlevel = ? ");
    int i = 0;
    pst.setInt(++i, toUserItemId);
    pst.setInt(++i, userId);
    pst.setInt(++i, fromUserId);
    pst.setInt(++i, DocumentStoreTeamMember.DOCUMENTSTORE_MANAGER);
    pst.execute();
    pst.close();
  }
  
  public void setSiteIdForMembers(SystemStatus systemStatus) throws SQLException {
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      DocumentStoreTeamMember member = (DocumentStoreTeamMember) iter.next();
      if (member.getItemId() != -1) {
        User user = systemStatus.getUser(member.getItemId());
        if (user != null) {
          member.setSiteId(user.getSiteId());
        }
      }
    }
  }
}

