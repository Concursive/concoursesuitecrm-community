//Copyright 2001 Dark Horse Ventures
// The createFilter method and the prepareFilter method need to have the same
// number of parameters if modified.

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.PagedListInfo;
import com.darkhorseventures.webutils.HtmlSelect;
import com.darkhorseventures.utils.DatabaseUtils;

/**
 *  Description of the Class
 *
 *@author     Wesley_S_Gillette
 *@created    November 14, 2001
 *@version    MessageList
 */
public class MessageList extends Vector {

  private PagedListInfo pagedListInfo = null;
  private String name = "";
  private String description = "";
  private int owner = -1;
  private String ownerIdRange = null;
  private String jsEvent = null;


  /**
   *  Constructor for the MessageList object
   *
   *@since
   */
  public MessageList() { }


  /**
   *  Sets the pagedListInfo attribute of the MessageList object
   *
   *@param  tmp  The new pagedListInfo value
   *@since
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the name attribute of the MessageList object
   *
   *@param  tmp  The new name value
   *@since
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the description attribute of the MessageList object
   *
   *@param  tmp  The new description value
   *@since
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
   *  Gets the pagedListInfo attribute of the MessageList object
   *
   *@return    The pagedListInfo value
   *@since
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the name attribute of the MessageList object
   *
   *@return    The name value
   *@since
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the description attribute of the MessageList object
   *
   *@return    The description value
   *@since
   */
  public String getDescription() {
    return description;
  }



  /**
   *  Gets the htmlSelect attribute of the MessageList object
   *
   *@param  selectName  Description of Parameter
   *@return             The htmlSelect value
   *@since
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
   *@since
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect messageListSelect = new HtmlSelect();
    messageListSelect.setJsEvent(jsEvent);
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Message thisMessage = (Message) i.next();
      messageListSelect.addItem(
          thisMessage.getId(),
          thisMessage.getName());
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
   *  Description of the Method
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
      pst.close();
      rs.close();

      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString() +
            sqlFilter.toString() +
            "AND name < ? ");
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
      this.addElement(thisMessage);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (owner != -1) {
      sqlFilter.append("AND m.enteredby = " + owner + " ");
    }

    if (ownerIdRange != null) {
      sqlFilter.append("AND m.enteredby IN (" + ownerIdRange + ") ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst  Description of Parameter
   *@return      Description of the Returned Value
   */
  private int prepareFilter(PreparedStatement pst) {
    int i = 0;

    return i;
  }

}

