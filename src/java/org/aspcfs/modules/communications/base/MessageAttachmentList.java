/*
 *  Copyright(c) 2007 Concursive Corporation (http://www.concursive.com/) All
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

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Contains a list of Campaign MessageAttachment objects. The list can be built by
 * setting parameters and then calling buildList.
 *
 * @author Zhenya
 * @version MessageAttachmentList
 * @created January 26, 2007
 */
public class MessageAttachmentList extends ArrayList {
  private PagedListInfo pagedListInfo = null;
  public final static String tableName = "message_file_attachment";
  public final static String uniqueField = "attachment_id";
  private int linkModuleId = -1;
  private int linkItemId = -1;

  public MessageAttachmentList() {
  }

  public MessageAttachmentList(Connection db, int linkModuleId, int linkItemId) throws SQLException {
    this.linkModuleId = linkModuleId;
    this.linkItemId = linkItemId;
    buildList(db);
  }

  /**
   * Gets the tableName attribute of the MessageAttachmentList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the MessageAttachmentList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }

  /**
   * Gets the linkModuleId attribute of the MessageAttachment object
   *
   * @return The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }

  /**
   * Sets the linkModuleId attribute of the MessageAttachment object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   * Sets the linkModuleId attribute of the MessageAttachment object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(String tmp) {
    this.setLinkModuleId(Integer.parseInt(tmp));
  }

  /**
   * Gets the linkItemId attribute of the MessageAttachment object
   *
   * @return The linkItemId value
   */
  public int getLinkItemId() {
    return linkItemId;
  }

  /**
   * Sets the linkItemId attribute of the MessageAttachment object
   *
   * @param tmp The new linkItemId value
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }

  /**
   * Sets the linkItemId attribute of the MessageAttachment object
   *
   * @param tmp The new linkItemId value
   */
  public void setLinkItemId(String tmp) {
    this.setLinkItemId(Integer.parseInt(tmp));
  }

  /**
   * Adds a feature to the Item attribute of the MessageAttachmentList object
   *
   * @param fileId The feature to be added to the Item attribute
   */
  public void addItem(int fileId) {
    MessageAttachment thisMessageAttachment = new MessageAttachment();
    thisMessageAttachment.setFileItemId(fileId);
    if (this.size() == 0) {
      this.add(thisMessageAttachment);
    } else {
      this.add(0, thisMessageAttachment);
    }
  }

  /**
   * Description of the Method
   *
   * @param sqlFilter Description of Parameter
   */
  private void createFilter(Connection db, StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (linkModuleId > -1) {
      sqlFilter.append("AND mfa.link_module_id = ? ");
    }
    if (linkItemId > -1) {
      sqlFilter.append("AND mfa.link_item_id = ? ");
    }
  }


  /**
   * Sets the PreparedStatement parameters that were added in createFilter
   *
   * @param pst Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (linkModuleId > -1) {
      pst.setInt(++i, linkModuleId);
    }
    if (linkItemId > -1) {
      pst.setInt(++i, linkItemId);
    }
    return i;
  }

  /**
   * Sets the PreparedStatement parameters that were added in createFilter
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
            "FROM message_file_attachment mfa " +
            "WHERE mfa.attachment_id  > -1 ");
    createFilter(db, sqlFilter);
    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
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
                "AND " + DatabaseUtils.toLowerCase(db) + "(mfa.filename) < ? ");
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
      pagedListInfo.setDefaultSort("mfa.filename", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY mfa.filename ");
    }
    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "mfa.* " +
            "FROM message_file_attachment mfa " +
            "WHERE mfa.attachment_id  > -1 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, pst);
    }
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      MessageAttachment thisAttachment = new MessageAttachment(rs);
      thisAttachment.buildFileItems(db, false);
      this.add(thisAttachment);
    }
    rs.close();
    pst.close();
  }

  public boolean delete(Connection db, int linkModuleId, int linkItemId) throws SQLException {
    this.linkModuleId = linkModuleId;
    this.linkItemId = linkItemId;
    PreparedStatement pst = null;
    SQLException message = null;
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      pst = db.prepareStatement(
          "DELETE FROM message_file_attachment WHERE link_module_id = ? and link_item_id = ? ");
      pst.setInt(1, this.getLinkModuleId());
      pst.setInt(2, this.getLinkItemId());
      pst.execute();
      pst.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      message = e;
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    if (message != null) {
      throw new SQLException(message.getMessage());
    }
    return true;
  }

  public boolean copyTo(Connection db, int toLinkModuleId, int toLinkItemId, boolean remuvOld) throws SQLException {
    SQLException message = null;
    try {
      MessageAttachmentList oldAttachmentList = new MessageAttachmentList();
      oldAttachmentList.delete(db, toLinkModuleId, toLinkItemId);
      Iterator it = this.iterator();
      while (it.hasNext()) {
        MessageAttachment thisAttachment = (MessageAttachment) it.next();
        MessageAttachment newAttachment = new MessageAttachment();
        newAttachment.setLinkModuleId(toLinkModuleId);
        newAttachment.setLinkItemId(toLinkItemId);
        newAttachment.setFileItemId(thisAttachment.getFileItemId());
        newAttachment.setFileName(thisAttachment.getFileName());
        newAttachment.setSize(thisAttachment.getSize());
        newAttachment.setVersion(thisAttachment.getVersion());
        newAttachment.insert(db);
      }
    } catch (SQLException e) {
      message = e;
    }
    if (message != null) {
      throw new SQLException(message.getMessage());
    }
    return true;
  }
}

