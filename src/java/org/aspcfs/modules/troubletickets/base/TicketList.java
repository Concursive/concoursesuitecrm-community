//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.PagedListInfo;
import com.darkhorseventures.utils.DatabaseUtils;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    December 5, 2001
 *@version    $Id$
 */
public class TicketList extends Vector {
  
  public static final String tableName = "ticket";
  public static final String uniqueField = "ticketid";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  private PagedListInfo pagedListInfo = null;
  private int enteredBy = -1;
  private boolean onlyOpen = false;
  private boolean onlyClosed = false;
  private int id = -1;
  private int orgId = -1;
  private int department = -1;
  private int assignedTo = -1;
  private boolean unassignedToo = false;
  private int severity = 0;
  private int priority = 0;
  private String accountOwnerIdRange = null;
  private String description = null;

  private String searchText = "";
  private boolean sendNotification = true;


  /**
   *  Constructor for the TicketList object
   *
   *@since
   */
  public TicketList() { }


  /**
   *  Sets the Id attribute of the TicketList object
   *
   *@param  id  The new Id value
   *@since
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   *  Sets the Id attribute of the TicketList object
   *
   *@param  id  The new Id value
   *@since
   */
  public void setId(String id) {
    this.id = Integer.parseInt(id);
  }


  /**
   *  Sets the assignedTo attribute of the TicketList object
   *
   *@param  assignedTo  The new assignedTo value
   */
  public void setAssignedTo(int assignedTo) {
    this.assignedTo = assignedTo;
  }


  /**
   *  Sets the unassignedToo attribute of the TicketList object
   *
   *@param  unassignedToo  The new unassignedToo value
   */
  public void setUnassignedToo(boolean unassignedToo) {
    this.unassignedToo = unassignedToo;
  }
  
public boolean getSendNotification() {
	return sendNotification;
}
public void setSendNotification(boolean sendNotification) {
	this.sendNotification = sendNotification;
}


  /**
   *  Sets the severity attribute of the TicketList object
   *
   *@param  tmp  The new severity value
   */
  public void setSeverity(int tmp) {
    this.severity = tmp;
  }


  /**
   *  Sets the priority attribute of the TicketList object
   *
   *@param  tmp  The new priority value
   */
  public void setPriority(int tmp) {
    this.priority = tmp;
  }


  /**
   *  Sets the severity attribute of the TicketList object
   *
   *@param  tmp  The new severity value
   */
  public void setSeverity(String tmp) {
    this.severity = Integer.parseInt(tmp);
  }

public String getTableName() { return tableName; }
public String getUniqueField() { return uniqueField; }

  /**
   *  Sets the priority attribute of the TicketList object
   *
   *@param  tmp  The new priority value
   */
  public void setPriority(String tmp) {
    this.priority = Integer.parseInt(tmp);
  }


  /**
   *  Sets the searchText attribute of the TicketList object
   *
   *@param  searchText  The new searchText value
   */
  public void setSearchText(String searchText) {
    this.searchText = searchText;
  }


  /**
   *  Sets the accountOwnerIdRange attribute of the TicketList object
   *
   *@param  accountOwnerIdRange  The new accountOwnerIdRange value
   */
  public void setAccountOwnerIdRange(String accountOwnerIdRange) {
    this.accountOwnerIdRange = accountOwnerIdRange;
  }


  /**
   *  Sets the OrgId attribute of the TicketList object
   *
   *@param  orgId  The new OrgId value
   *@since
   */
  public void setOrgId(int orgId) {
    this.orgId = orgId;
  }


  /**
   *  Sets the OrgId attribute of the TicketList object
   *
   *@param  orgId  The new OrgId value
   *@since
   */
  public void setOrgId(String orgId) {
    this.orgId = Integer.parseInt(orgId);
  }

  /**
   *  Sets the PagedListInfo attribute of the TicketList object
   *
   *@param  tmp  The new PagedListInfo value
   *@since
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }
  
  /**
   *  Sets the EnteredBy attribute of the TicketList object
   *
   *@param  tmp  The new EnteredBy value
   *@since
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }

  public String getDescription() {
          return description;
  }

  public void setDescription(String description) {
          this.description = description;
  }


  /**
   *  Sets the onlyClosed attribute of the TicketList object
   *
   *@param  onlyClosed  The new onlyClosed value
   */
  public void setOnlyClosed(boolean onlyClosed) {
    this.onlyClosed = onlyClosed;
  }


  /**
   *  Sets the OnlyOpen attribute of the TicketList object
   *
   *@param  onlyOpen  The new OnlyOpen value
   *@since
   */
  public void setOnlyOpen(boolean onlyOpen) {
    this.onlyOpen = onlyOpen;
  }


  /**
   *  Sets the Department attribute of the TicketList object
   *
   *@param  department  The new Department value
   *@since
   */
  public void setDepartment(int department) {
    this.department = department;
  }


  /**
   *  Gets the assignedTo attribute of the TicketList object
   *
   *@return    The assignedTo value
   */
  public int getAssignedTo() {
    return assignedTo;
  }


  /**
   *  Gets the unassignedToo attribute of the TicketList object
   *
   *@return    The unassignedToo value
   */
  public boolean getUnassignedToo() {
    return unassignedToo;
  }


  /**
   *  Gets the severity attribute of the TicketList object
   *
   *@return    The severity value
   */
  public int getSeverity() {
    return severity;
  }


  /**
   *  Gets the priority attribute of the TicketList object
   *
   *@return    The priority value
   */
  public int getPriority() {
    return priority;
  }


  /**
   *  Gets the searchText attribute of the TicketList object
   *
   *@return    The searchText value
   */
  public String getSearchText() {
    return searchText;
  }


  /**
   *  Gets the accountOwnerIdRange attribute of the TicketList object
   *
   *@return    The accountOwnerIdRange value
   */
  public String getAccountOwnerIdRange() {
    return accountOwnerIdRange;
  }


  /**
   *  Gets the onlyClosed attribute of the TicketList object
   *
   *@return    The onlyClosed value
   */
  public boolean getOnlyClosed() {
    return onlyClosed;
  }


  /**
   *  Gets the OrgId attribute of the TicketList object
   *
   *@return    The OrgId value
   *@since
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Gets the Id attribute of the TicketList object
   *
   *@return    The Id value
   *@since
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the OnlyOpen attribute of the TicketList object
   *
   *@return    The OnlyOpen value
   *@since
   */
  public boolean getOnlyOpen() {
    return onlyOpen;
  }


  /**
   *  Gets the Department attribute of the TicketList object
   *
   *@return    The Department value
   *@since
   */
  public int getDepartment() {
    return department;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
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
        "FROM ticket t " +
        "WHERE t.ticketid > 0 ");

    createFilter(sqlFilter, db);

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
            "AND t.problem < ? ");
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
      pagedListInfo.setDefaultSort("t.entered", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY t.entered ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "t.*, " +
        "o.name AS orgname, o.enabled AS orgenabled, " +
        "ld.description AS dept, " +
        "tp.description AS ticpri, " +
        "ts.description AS ticsev, " +
        "tc.description AS catname, " +
        "lu_ts.description AS sourcename, " +
        "ct_eb.namelast AS eb_namelast, ct_eb.namefirst AS eb_namefirst, " +
        "ct_mb.namelast AS mb_namelast, ct_mb.namefirst AS mb_namefirst, " +
        "ct_owner.namelast AS owner_namelast, ct_owner.namefirst AS owner_namefirst " +
        "FROM ticket t " +
        "LEFT JOIN organization o ON (t.org_id = o.org_id) " +
        "LEFT JOIN lookup_department ld ON (t.department_code = ld.code) " +
        "LEFT JOIN ticket_priority tp ON (t.pri_code = tp.code) " +
        "LEFT JOIN ticket_severity ts ON (t.scode = ts.code) " +
        "LEFT JOIN ticket_category tc ON (t.cat_code = tc.id) " +
        "LEFT JOIN contact ct_owner ON (t.assigned_to = ct_owner.user_id) " +
        "LEFT JOIN contact ct_eb ON (t.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (t.modifiedby = ct_mb.user_id) " +
        "LEFT JOIN lookup_ticketsource lu_ts ON (t.source_code = lu_ts.code) " +
        "WHERE t.ticketid > 0 ");
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
      Ticket thisTicket = new Ticket(rs);
      
      if (!getSendNotification()) {
              thisTicket.setSendNotification(false);
      }
      
      this.addElement(thisTicket);
    }
    rs.close();
    pst.close();
    
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Ticket thisTicket = (Ticket)i.next();
      thisTicket.buildFiles(db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator tickets = this.iterator();
    while (tickets.hasNext()) {
      Ticket thisTicket = (Ticket) tickets.next();
      thisTicket.delete(db);
    }
  }
  
  public int reassignElements(Connection db, int newOwner) throws SQLException {
    int total = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Ticket thisTicket = (Ticket) i.next();
      if (thisTicket.reassign(db, newOwner)) {
        total++;
      }
    }
    return total;
  }      

  /**
   *  Builds a base SQL where statement for filtering records to be used by
   *  sqlSelect and sqlCount
   *
   *@param  sqlFilter  Description of Parameter
   *@since             1.2
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) {
    if (searchText == null || (searchText.equals(""))) {
      if (enteredBy > -1) {
        sqlFilter.append("AND t.enteredby = ? ");
      }
      
      if (description != null) {
              if (description.indexOf("%") >= 0) {
                      sqlFilter.append("AND lower(t.problem) like lower(?) ");
              } else {
                      sqlFilter.append("AND lower(t.problem) = lower(?) ");
              }
      }

      if (onlyOpen == true) {
        sqlFilter.append("AND t.closed IS NULL ");
      }

      if (onlyClosed == true) {
        sqlFilter.append("AND t.closed IS NOT NULL ");
      }

      if (id > -1) {
        sqlFilter.append("AND t.ticketid = ? ");
      }

      if (orgId > -1) {
        sqlFilter.append("AND t.org_id = ? ");
      }

      if (department > -1) {
        if (unassignedToo == true) {
          sqlFilter.append("AND (t.department_code in (?, 0, -1) OR (t.department_code IS NULL)) ");
        } else {
          sqlFilter.append("AND t.department_code = ? ");
        }
      }

      if (assignedTo > -1) {
        sqlFilter.append("AND t.assigned_to = ? ");
      }

      if (severity > 0) {
        sqlFilter.append("AND t.scode = ? ");
      }

      if (priority > 0) {
        sqlFilter.append("AND t.pri_code = ? ");
      }

      if (accountOwnerIdRange != null) {
        sqlFilter.append("AND t.org_id IN (SELECT org_id FROM organization WHERE owner IN (" + accountOwnerIdRange + ")) ");
      }
    } else {
      if (DatabaseUtils.getType(db) == DatabaseUtils.MSSQL) {
        sqlFilter.append(
          "AND ( LOWER(CONVERT(VARCHAR(2000),t.problem)) LIKE LOWER(?) OR " +
          "LOWER(CONVERT(VARCHAR(2000),t.comment)) LIKE LOWER(?) OR " +
          "LOWER(CONVERT(VARCHAR(2000),t.solution)) LIKE LOWER(?) ) ");
      } else {
        sqlFilter.append(
          "AND ( LOWER(t.problem) LIKE LOWER(?) OR " +
          "LOWER(t.comment) LIKE LOWER(?) OR " +
          "LOWER(t.solution) LIKE LOWER(?) ) ");
      }
    }
  }


  /**
   *  Sets the parameters for the preparedStatement - these items must
   *  correspond with the createFilter statement
   *
   *@param  pst               Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.2
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;

    if (searchText == null || (searchText.equals(""))) {

      if (enteredBy > -1) {
        pst.setInt(++i, enteredBy);
      }
      if (description != null) {
        pst.setString(++i, description);
      }
      if (id > -1) {
        pst.setInt(++i, id);
      }
      if (orgId > -1) {
        pst.setInt(++i, orgId);
      }
      if (department > -1) {
        pst.setInt(++i, department);
      }
      if (assignedTo > -1) {
        pst.setInt(++i, assignedTo);
      }
      if (severity > 0) {
        pst.setInt(++i, severity);
      }
      if (priority > 0) {
        pst.setInt(++i, priority);
      }
    } else {
      pst.setString(++i, searchText);
      pst.setString(++i, searchText);
      pst.setString(++i, searchText);
    }

    return i;
  }
  
  public static int retrieveRecordCount(Connection db, int moduleId, int itemId) throws SQLException {
    int count = 0;
    StringBuffer sql = new StringBuffer();
    sql.append(
      "SELECT COUNT(*) as itemcount " +
      "FROM ticket t " +
      "WHERE ticketid > 0 ");
    if (moduleId == Constants.ACCOUNTS) {  
      sql.append("AND t.org_id = ?");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (moduleId == Constants.ACCOUNTS) {
      pst.setInt(1, itemId);
    }
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      count = rs.getInt("itemcount");
    }
    rs.close();
    pst.close();
    return count;
  }

}

