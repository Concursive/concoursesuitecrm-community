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
package org.aspcfs.modules.actionlist.base;

import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactList;

/**
 *  List of Action Contacts
 *
 * @author     akhi_m
 * @created    April 23, 2003
 */
public class ActionContactsList extends ArrayList {
  private boolean buildHistory = false;
  private int actionId = -1;
  private int enteredBy = -1;
  private PagedListInfo pagedListInfo = null;
  private boolean completeOnly = false;
  private boolean inProgressOnly = false;


  /**
   *Constructor for the ActionContactsList object
   */
  public ActionContactsList() { }


  /**
   *  Sets the buildHistory attribute of the ActionContactsList object
   *
   * @param  buildHistory  The new buildHistory value
   */
  public void setBuildHistory(boolean buildHistory) {
    this.buildHistory = buildHistory;
  }


  /**
   *  Sets the actionId attribute of the ActionContactsList object
   *
   * @param  actionId  The new actionId value
   */
  public void setActionId(int actionId) {
    this.actionId = actionId;
  }


  /**
   *  Sets the pagedListInfo attribute of the ActionContactsList object
   *
   * @param  pagedListInfo  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
  }


  /**
   *  Sets the enteredBy attribute of the ActionContactsList object
   *
   * @param  enteredBy  The new enteredBy value
   */
  public void setEnteredBy(int enteredBy) {
    this.enteredBy = enteredBy;
  }


  /**
   *  Sets the completeOnly attribute of the ActionContactsList object
   *
   * @param  completeOnly  The new completeOnly value
   */
  public void setCompleteOnly(boolean completeOnly) {
    this.completeOnly = completeOnly;
  }


  /**
   *  Sets the inProgressOnly attribute of the ActionContactsList object
   *
   * @param  inProgressOnly  The new inProgressOnly value
   */
  public void setInProgressOnly(boolean inProgressOnly) {
    this.inProgressOnly = inProgressOnly;
  }


  /**
   *  Gets the completeOnly attribute of the ActionContactsList object
   *
   * @return    The completeOnly value
   */
  public boolean getCompleteOnly() {
    return completeOnly;
  }


  /**
   *  Gets the inProgressOnly attribute of the ActionContactsList object
   *
   * @return    The inProgressOnly value
   */
  public boolean getInProgressOnly() {
    return inProgressOnly;
  }


  /**
   *  Gets the enteredBy attribute of the ActionContactsList object
   *
   * @return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the pagedListInfo attribute of the ActionContactsList object
   *
   * @return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the actionId attribute of the ActionContactsList object
   *
   * @return    The actionId value
   */
  public int getActionId() {
    return actionId;
  }


  /**
   *  Gets the buildHistory attribute of the ActionContactsList object
   *
   * @return    The buildHistory value
   */
  public boolean getBuildHistory() {
    return buildHistory;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    //Build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM action_item ai " +
        "WHERE ai.item_id > -1 ");
    createFilter(sqlFilter);
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

      //Determine column to sort by
      pagedListInfo.setDefaultSort("ai.entered", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY ai.entered ");
    }
    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "ai.item_id, ai.action_id, ai.link_item_id, ai.completedate, ai.enteredby, ai.entered, " +
        "ai.modifiedby, ai.modified, ai.enabled " +
        "FROM action_item ai " +
        "WHERE ai.item_id > -1 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    int count = 0;
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.getItemsPerPage() > 0 &&
          DatabaseUtils.getType(db) == DatabaseUtils.MSSQL &&
          count >= pagedListInfo.getItemsPerPage()) {
        break;
      }
      ++count;
      ActionContact thisContact = new ActionContact(rs);
      this.add(thisContact);
    }
    rs.close();
    pst.close();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionContact thisContact = (ActionContact) i.next();
      thisContact.buildContact(db, thisContact.getLinkItemId());
      thisContact.buildMostRecentHistoryItem(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  sqlFilter  Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (actionId != -1) {
      sqlFilter.append("AND ai.action_id  = ? ");
    }

    if (completeOnly) {
      sqlFilter.append("AND (ai.completedate IS NOT NULL) ");
    } else if (inProgressOnly) {
      sqlFilter.append("AND (ai.completedate IS NULL) ");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  pst               Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;

    if (actionId != -1) {
      pst.setInt(++i, actionId);
    }
    return i;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  contactList       Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void insert(Connection db, ContactList contactList) throws SQLException {
    Iterator i = contactList.iterator();
    while (i.hasNext()) {
      Contact thisContact = (Contact) i.next();
      ActionItem actionContact = new ActionItem();
      actionContact.setEnteredBy(this.getEnteredBy());
      actionContact.setModifiedBy(this.getEnteredBy());
      actionContact.setActionId(this.getActionId());
      actionContact.setLinkItemId(thisContact.getId());
      if (!isContactOnList(db, thisContact.getId())) {
        actionContact.insert(db);
      }
      this.add(actionContact);
    }
  }


  /**
   *  Updates the action list based on the new set of action contacts
   *
   * @param  db                Description of the Parameter
   * @param  contacts          Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void update(Connection db, String contacts) throws SQLException {

    int i = 0;

    //delete log items for deleted contacts
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM action_item_log " +
        "WHERE item_id IN (SELECT item_id from action_item ai where ai.action_id = ? AND ai.link_item_id NOT IN (" + contacts + ") ) "
        );
    pst.setInt(++i, this.getActionId());
    pst.execute();
    pst.close();

    //remove deleted contacts
    i = 0;
    pst = db.prepareStatement(
        "DELETE FROM action_item " +
        "WHERE action_id = ? AND link_item_id NOT IN (" + contacts + ") "
        );
    pst.setInt(++i, this.getActionId());
    pst.execute();
    pst.close();

    StringTokenizer tokens = new StringTokenizer(contacts, ",");
    while (tokens.hasMoreTokens()) {
      String contactId = (String) tokens.nextToken();
      ActionItem actionContact = new ActionItem();
      if (!isContactOnList(db, Integer.parseInt(contactId))) {
        actionContact.setEnteredBy(this.getEnteredBy());
        actionContact.setModifiedBy(this.getEnteredBy());
        actionContact.setActionId(this.getActionId());
        actionContact.setLinkItemId(Integer.parseInt(contactId));
        actionContact.insert(db);
      }
    }
  }


  /**
   *  Checks to see if the specified contact is on this list
   *
   * @param  db                Description of the Parameter
   * @param  contactId         Description of the Parameter
   * @return                   The contactOnList value
   * @exception  SQLException  Description of the Exception
   */
  public boolean isContactOnList(Connection db, int contactId) throws SQLException {
    boolean onList = false;
    PreparedStatement pst = db.prepareStatement(
        "SELECT item_id " +
        "FROM action_item " +
        "WHERE action_id = ? AND link_item_id = ? ");
    pst.setInt(1, this.getActionId());
    pst.setInt(2, contactId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      onList = true;
    }
    rs.close();
    pst.close();
    return onList;
  }
}

