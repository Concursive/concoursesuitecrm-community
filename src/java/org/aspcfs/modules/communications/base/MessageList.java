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

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
/**
 *  Contains a list of Campaign Message objects. The list can be built by
 *  setting parameters and then calling buildList.
 *
 *@author     Wesley_S_Gillette
 *@created    November 14, 2001
 *@version    MessageList
 */
public class MessageList extends ArrayList {

  public final static int EXCLUDE_PERSONAL = -1;
  public final static int IGNORE_PERSONAL = -2;

  private PagedListInfo pagedListInfo = null;
  private String name = "";
  private String description = "";
  private int owner = -1;
  private String ownerIdRange = null;
  private String jsEvent = null;
  //Combination filters
  private boolean allMessages = false;
  private boolean controlledHierarchyOnly = false;
  //access type filters
  private int ruleId = -1;
  private int personalId = EXCLUDE_PERSONAL;


  /**
   *  Constructor for the MessageList object
   */
  public MessageList() { }


  /**
   *  Sets the pagedListInfo attribute of the MessageList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the name attribute of the MessageList object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the description attribute of the MessageList object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the owner attribute of the MessageList object
   *
   *@param  tmp  The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }


  /**
   *  Sets the ownerIdRange attribute of the MessageList object
   *
   *@param  tmp  The new ownerIdRange value
   */
  public void setOwnerIdRange(String tmp) {
    this.ownerIdRange = tmp;
  }


  /**
   *  Sets the jsEvent attribute of the MessageList object
   *
   *@param  tmp  The new jsEvent value
   */
  public void setJsEvent(String tmp) {
    this.jsEvent = tmp;
  }


  /**
   *  Sets the allMessages attribute of the MessageList object
   *
   *@param  allMessages  The new allMessages value
   */
  public void setAllMessages(boolean allMessages) {
    this.allMessages = allMessages;
  }


  /**
   *  Sets the allMessages attribute of the MessageList object
   *
   *@param  allMessages   The new allMessages value
   *@param  owner         The new allMessages value
   *@param  ownerIdRange  The new allMessages value
   */
  public void setAllMessages(boolean allMessages, int owner, String ownerIdRange) {
    this.ownerIdRange = ownerIdRange;
    this.allMessages = allMessages;
    this.personalId = owner;
  }


  /**
   *  Sets the ruleId attribute of the MessageList object
   *
   *@param  ruleId  The new ruleId value
   */
  public void setRuleId(int ruleId) {
    this.ruleId = ruleId;
  }


  /**
   *  Sets the controlledHierarchyOnly attribute of the MessageList object
   *
   *@param  controlledHierarchyOnly  The new controlledHierarchyOnly value
   */
  public void setControlledHierarchyOnly(boolean controlledHierarchyOnly) {
    this.controlledHierarchyOnly = controlledHierarchyOnly;
  }



  /**
   *  Sets the controlledHierarchyOnly attribute of the MessageList object
   *
   *@param  controlledHierarchyOnly  The new controlledHierarchyOnly value
   *@param  ownerIdRange             The new controlledHierarchyOnly value
   */
  public void setControlledHierarchyOnly(boolean controlledHierarchyOnly, String ownerIdRange) {
    this.controlledHierarchyOnly = controlledHierarchyOnly;
    this.ownerIdRange = ownerIdRange;
  }


  /**
   *  Sets the personalId attribute of the MessageList object
   *
   *@param  personalId  The new personalId value
   */
  public void setPersonalId(int personalId) {
    this.personalId = personalId;
  }


  /**
   *  Gets the personalId attribute of the MessageList object
   *
   *@return    The personalId value
   */
  public int getPersonalId() {
    return personalId;
  }


  /**
   *  Gets the controlledHierarchyOnly attribute of the MessageList object
   *
   *@return    The controlledHierarchyOnly value
   */
  public boolean getControlledHierarchyOnly() {
    return controlledHierarchyOnly;
  }


  /**
   *  Gets the ruleId attribute of the MessageList object
   *
   *@return    The ruleId value
   */
  public int getRuleId() {
    return ruleId;
  }


  /**
   *  Gets the allMessages attribute of the MessageList object
   *
   *@return    The allMessages value
   */
  public boolean getAllMessages() {
    return allMessages;
  }


  /**
   *  Gets the pagedListInfo attribute of the MessageList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the name attribute of the MessageList object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the description attribute of the MessageList object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }



  /**
   *  Gets the htmlSelect attribute of the MessageList object
   *
   *@param  selectName  Description of Parameter
   *@return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }



  /**
   *  Gets the htmlSelect attribute of the MessageList object
   *
   *@param  selectName  Description of Parameter
   *@param  defaultKey  Description of Parameter
   *@return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect messageListSelect = new HtmlSelect();
    messageListSelect.setJsEvent(jsEvent);
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Message thisMessage = (Message) i.next();
      messageListSelect.addItem(
          thisMessage.getId(),
          (thisMessage.getName() != null && !"".equals(thisMessage.getName())) ? thisMessage.getName() : "\"Unnamed Message\"");
    }
    return messageListSelect.getHtml(selectName, defaultKey);
  }


  /**
   *  Adds a feature to the Item attribute of the MessageList object
   *
   *@param  key   The feature to be added to the Item attribute
   *@param  name  The feature to be added to the Item attribute
   */
  public void addItem(int key, String name) {
    Message thisMessage = new Message();
    thisMessage.setId(key);
    thisMessage.setName(name);
    if (this.size() == 0) {
      this.add(thisMessage);
    } else {
      this.add(0, thisMessage);
    }
  }


  /**
   *  Queries the database and adds Message objects to this collection based on
   *  any specified parameters.
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM message m " +
        "WHERE m.id > -1 ");

    createFilter(sqlFilter);

    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() +
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
        pst = db.prepareStatement(sqlCount.toString() +
            sqlFilter.toString() +
            "AND lower(name) < ? ");
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
      pagedListInfo.setDefaultSort("name", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY name ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "m.* " +
        "FROM message m " +
        "LEFT JOIN contact ct_eb ON (m.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (m.modifiedby = ct_mb.user_id) " +
        "WHERE m.id > -1 ");
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
      Message thisMessage = new Message(rs);
      this.add(thisMessage);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Appends any list filters that were specified to the SQL statement
   *
   *@param  sqlFilter  Description of Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (owner != -1) {
      sqlFilter.append("AND m.enteredby = ? ");
    }

    if (controlledHierarchyOnly) {
      sqlFilter.append("AND m.enteredby IN (" + ownerIdRange + ") ");
    }

    if (ruleId != -1) {
      sqlFilter.append("AND m.access_type IN (SELECT code from lookup_access_types where rule_id = ? AND code = m.access_type) ");
    }

    if (allMessages) {
      //get contact in users hierarchy
      sqlFilter.append("AND (m.enteredby IN (" + ownerIdRange + ") OR m.access_type IN (SELECT code from lookup_access_types WHERE rule_id = ? AND code = m.access_type)) ");
    }

    switch (personalId) {
        case IGNORE_PERSONAL:
          break;
        case EXCLUDE_PERSONAL:
          sqlFilter.append("AND m.access_type NOT IN (SELECT code from lookup_access_types WHERE rule_id = ? AND code = m.access_type) ");
          break;
        default:
          sqlFilter.append("AND (m.access_type NOT IN (SELECT code from lookup_access_types WHERE rule_id = ? AND code = m.access_type)  OR (m.access_type IN (SELECT code from lookup_access_types WHERE rule_id = ? AND code = m.access_type) AND m.enteredby = ?)) ");
          break;
    }
  }


  /**
   *  Sets the PreparedStatement parameters that were added in createFilter
   *
   *@param  pst               Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;

    if (owner != -1) {
      pst.setInt(++i, owner);
    }

    if (ruleId != -1) {
      pst.setInt(++i, ruleId);
    }

    if (allMessages) {
      pst.setInt(++i, AccessType.PUBLIC);
    }

    switch (personalId) {
        case IGNORE_PERSONAL:
          break;
        case EXCLUDE_PERSONAL:
          pst.setInt(++i, AccessType.PERSONAL);
          break;
        default:
          pst.setInt(++i, AccessType.PERSONAL);
          pst.setInt(++i, AccessType.PERSONAL);
          pst.setInt(++i, personalId);
          break;
    }
    return i;
  }


  /**
   *  Checks to see if the specified messageId is in this collection of Message
   *  objects
   *
   *@param  messageId  Message ID to look for
   *@return            Returns true if found, else false
   */
  public boolean hasId(int messageId) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Message thisMessage = (Message) i.next();
      if (thisMessage.getId() == messageId) {
        return true;
      }
    }
    return false;
  }

}

