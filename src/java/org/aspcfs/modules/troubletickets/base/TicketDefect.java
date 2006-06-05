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
package org.aspcfs.modules.troubletickets.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *  Description of the Class
 *
 *@author     partha
 *@created    October 4, 2005
 *@version    $Id: TicketDefect.java,v 1.1.4.1 2005/10/14 21:12:41 mrajkowski
 *      Exp $
 */
public class TicketDefect extends GenericBean {

  private int id = -1;
  protected String title = null;
  protected String description = null;
  protected java.sql.Timestamp startDate = null;
  protected java.sql.Timestamp endDate = null;
  protected boolean enabled = false;
  protected java.sql.Timestamp trashedDate = null;
  // related records
  protected TicketList tickets = new TicketList();
  protected boolean buildTickets = false;
  private int ageDays = 0;
  private int ageHours = 0;

  private int siteId = -1;
  private String siteName = null;


  /**
   *  Constructor for the TicketDefect object
   */
  public TicketDefect() { }


  /**
   *  Constructor for the TicketDefect object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public TicketDefect(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the TicketDefect object
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public TicketDefect(Connection db, int id) throws SQLException {
    if (id <= -1) {
      throw new SQLException("Defect ID not specified");
    }
    queryRecord(db, id);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id <= -1) {
      throw new SQLException("Defect ID not specified");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT td.* , ls.description AS sitename " +
        "FROM ticket_defect td " +
        "LEFT JOIN lookup_site_id ls ON (td.site_id = ls.code) " +
        "WHERE td.defect_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
    if (buildTickets) {
      buildTickets(db, this.getSiteId());
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int buildTickets(Connection db, int tmpTicketSiteId) throws SQLException {
    if (tickets == null) {
      tickets = new TicketList();
    }
    tickets.setDefectId(this.getId());
    tickets.setSiteId(tmpTicketSiteId);
    tickets.setExclusiveToSite(true);
    if (tmpTicketSiteId > -1) {
      tickets.setIncludeAllSites(false);
    }
    tickets.buildList(db);
    return tickets.size();
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //ticket table
    this.setId(rs.getInt("defect_id"));
    title = rs.getString("title");
    description = rs.getString("description");
    startDate = rs.getTimestamp("start_date");
    endDate = rs.getTimestamp("end_date");
    enabled = rs.getBoolean("enabled");
    trashedDate = rs.getTimestamp("trashed_date");
    siteId = DatabaseUtils.getInt(rs, "site_id");
    siteName = rs.getString("sitename");
    if (trashedDate != null) {
      title = new String(title+" (X)");
    }
    //Calculations
    buildAge();
  }


  /**
   *  Description of the Method
   */
  public void buildAge() {
    if (startDate != null) {
      if (endDate != null) {
        float ageCheck = ((endDate.getTime() - startDate.getTime()) / 3600000);
        int totalHours = java.lang.Math.round(ageCheck);
        ageDays = java.lang.Math.round(totalHours / 24);
        ageHours = java.lang.Math.round(totalHours - (24 * ageDays));
      } else {
        float ageCheck = ((System.currentTimeMillis() - startDate.getTime()) / 3600000);
        int totalHours = java.lang.Math.round(ageCheck);
        ageDays = java.lang.Math.round(totalHours / 24);
        ageHours = java.lang.Math.round(totalHours - (24 * ageDays));
      }
    }
  }


  /**
   *  Gets the ageOf attribute of the TicketDefect object
   *
   *@return    The ageOf value
   */
  public String getAgeOf() {
    Timestamp currentTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
    if (currentTime.before(startDate)) {
      return "";
    }
    return ageDays + "d " + ageHours + "h";
  }


  /**
   *  Description of the Method
   */
  public void printDefect() {
    //ticket table
    System.out.println("TickietDefect::toString id is " + this.getId());
    System.out.println("TickietDefect::toString title is " + this.getTitle());
    System.out.println("TickietDefect::toString description is " + this.getDescription());
    System.out.println("TickietDefect::toString start date is " + (startDate != null ? startDate.toString() : "null"));
    System.out.println("TickietDefect::toString end date is " + (endDate != null ? endDate.toString() : "null"));
    System.out.println("TickietDefect::toString enabled is " + enabled);
    System.out.println("TickietDefect::toString trashedDate is " + (trashedDate != null ? trashedDate.toString() : ""));
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      id = DatabaseUtils.getNextSeq(db, "ticket_defect_defect_id_seq");
      sql.append(
          "INSERT INTO ticket_defect (" + (id > -1 ? "defect_id," : "") + " title, description, " +
          (startDate != null ? " start_date, " : ""));
      if (endDate != null) {
        sql.append("end_date, ");
      }
      sql.append("enabled, site_id ) ");
      sql.append("VALUES (" + (id > -1 ? "?," : "") + " ?, ?, " + (startDate != null ? "?," : ""));
      if (endDate != null) {
        sql.append("?, ");
      }
      sql.append("?,?) ");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setString(++i, this.getTitle());
      pst.setString(++i, this.getDescription());
      if (startDate != null) {
        DatabaseUtils.setTimestamp(pst, ++i, startDate);
      }
      if (endDate != null) {
        DatabaseUtils.setTimestamp(pst, ++i, endDate);
      }
      pst.setBoolean(++i, this.getEnabled());
      DatabaseUtils.setInt(pst, ++i, this.getSiteId());
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "ticket_defect_defect_id_seq", id);
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    if (this.getId() == -1) {
      return -1;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE ticket_defect SET " +
        " title = ?, " +
        " start_date = ?, " +
        " end_date = ?, " +
        " description = ?, " +
        " enabled = ? ," +
        " site_id = ? " +
        " WHERE defect_id = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, this.getTitle());
    DatabaseUtils.setTimestamp(pst, ++i, this.getStartDate());
    DatabaseUtils.setTimestamp(pst, ++i, this.getEndDate());
    pst.setString(++i, this.getDescription());
    pst.setBoolean(++i, enabled);
    DatabaseUtils.setInt(pst, ++i, this.getSiteId());
    pst.setInt(++i, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  toTrash           Description of the Parameter
   *@param  tmpUserId         Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean updateStatus(Connection db, boolean toTrash, int tmpUserId) throws SQLException {
    int resultCount = 0;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE ticket_defect " +
        "SET trashed_date = ? " +
        "WHERE defect_id = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    if (toTrash) {
      DatabaseUtils.setTimestamp(
          pst, ++i, new Timestamp(System.currentTimeMillis()));
    } else {
      DatabaseUtils.setTimestamp(pst, ++i, (Timestamp) null);
    }
    pst.setInt(++i, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();
    return true;
  }


  /**
   *  Gets the isNotExpired attribute of the TicketDefect object
   *
   *@return    The isNotExpired value
   */
  public boolean getIsNotExpired() {
    Timestamp current = new Timestamp(Calendar.getInstance().getTimeInMillis());
    return (enabled && (endDate == null || current.before(endDate)));
  }


  /**
   *  Gets the trashed attribute of the TicketDefect object
   *
   *@return    The trashed value
   */
  public boolean isTrashed() {
    return (trashedDate != null);
  }


  /**
   *  Gets the disabled attribute of the TicketDefect object
   *
   *@return    The disabled value
   */
  public boolean isDisabled() {
    Timestamp currentTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
    return !(this.getEnabled() 
      && (this.getEndDate() == null || this.getEndDate().after(currentTime)) 
      && this.getStartDate().before(currentTime)
      && (this.getStartDate() == null || this.getStartDate().before(currentTime)));
  }


  /**
   *  Gets the timeZoneParams attribute of the TicketDefect class
   *
   *@return    The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("startDate");
    thisList.add("endDate");
    return thisList;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Defect ID not specified");
    }
    DependencyList dependencyList = new DependencyList();
    PreparedStatement pst = null;
    ResultSet rs = null;
    int i = 0;
    // Check for this group's usage in tickets
    try {
      i = 0;
      pst = db.prepareStatement(
          "SELECT count(*) as ticketcount " +
          "FROM ticket " +
          "WHERE defect_id = ? AND trashed_date IS NULL ");
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int ticketCount = rs.getInt("ticketcount");
        if (ticketCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("ticketMap");
          thisDependency.setCount(ticketCount);
          thisDependency.setCanDelete(true);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException(e.getMessage());
    }
    return dependencyList;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement("UPDATE ticket SET defect_id=? WHERE defect_id = ? ");
    pst.setInt(1, -1);
    pst.setInt(2, this.getId());
    pst.executeUpdate();
    pst.close();

    pst = db.prepareStatement("DELETE FROM ticket_defect WHERE defect_id = ? ");
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();
    return true;
  }


  /*
   *  Get and Set methods
   */
  /**
   *  Gets the id attribute of the TicketDefect object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the TicketDefect object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the TicketDefect object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the title attribute of the TicketDefect object
   *
   *@return    The title value
   */
  public String getTitle() {
    return title;
  }


  /**
   *  Sets the title attribute of the TicketDefect object
   *
   *@param  tmp  The new title value
   */
  public void setTitle(String tmp) {
    this.title = tmp;
  }


  /**
   *  Gets the description attribute of the TicketDefect object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Sets the description attribute of the TicketDefect object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Gets the startDate attribute of the TicketDefect object
   *
   *@return    The startDate value
   */
  public java.sql.Timestamp getStartDate() {
    return startDate;
  }


  /**
   *  Sets the startDate attribute of the TicketDefect object
   *
   *@param  tmp  The new startDate value
   */
  public void setStartDate(java.sql.Timestamp tmp) {
    this.startDate = tmp;
  }


  /**
   *  Sets the startDate attribute of the TicketDefect object
   *
   *@param  tmp  The new startDate value
   */
  public void setStartDate(String tmp) {
    this.startDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the endDate attribute of the TicketDefect object
   *
   *@return    The endDate value
   */
  public java.sql.Timestamp getEndDate() {
    return endDate;
  }


  /**
   *  Sets the endDate attribute of the TicketDefect object
   *
   *@param  tmp  The new endDate value
   */
  public void setEndDate(java.sql.Timestamp tmp) {
    this.endDate = tmp;
  }


  /**
   *  Sets the endDate attribute of the TicketDefect object
   *
   *@param  tmp  The new endDate value
   */
  public void setEndDate(String tmp) {
    this.endDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the enabled attribute of the TicketDefect object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Sets the enabled attribute of the TicketDefect object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the TicketDefect object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the trashedDate attribute of the TicketDefect object
   *
   *@return    The trashedDate value
   */
  public java.sql.Timestamp getTrashedDate() {
    return trashedDate;
  }


  /**
   *  Sets the trashedDate attribute of the TicketDefect object
   *
   *@param  tmp  The new trashedDate value
   */
  public void setTrashedDate(java.sql.Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   *  Sets the trashedDate attribute of the TicketDefect object
   *
   *@param  tmp  The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the tickets attribute of the TicketDefect object
   *
   *@return    The tickets value
   */
  public TicketList getTickets() {
    return tickets;
  }


  /**
   *  Sets the tickets attribute of the TicketDefect object
   *
   *@param  tmp  The new tickets value
   */
  public void setTickets(TicketList tmp) {
    this.tickets = tmp;
  }


  /**
   *  Gets the buildTickets attribute of the TicketDefect object
   *
   *@return    The buildTickets value
   */
  public boolean getBuildTickets() {
    return buildTickets;
  }


  /**
   *  Sets the buildTickets attribute of the TicketDefect object
   *
   *@param  tmp  The new buildTickets value
   */
  public void setBuildTickets(boolean tmp) {
    this.buildTickets = tmp;
  }


  /**
   *  Sets the buildTickets attribute of the TicketDefect object
   *
   *@param  tmp  The new buildTickets value
   */
  public void setBuildTickets(String tmp) {
    this.buildTickets = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the ageDays attribute of the TicketDefect object
   *
   *@return    The ageDays value
   */
  public int getAgeDays() {
    return ageDays;
  }


  /**
   *  Sets the ageDays attribute of the TicketDefect object
   *
   *@param  tmp  The new ageDays value
   */
  public void setAgeDays(int tmp) {
    this.ageDays = tmp;
  }


  /**
   *  Sets the ageDays attribute of the TicketDefect object
   *
   *@param  tmp  The new ageDays value
   */
  public void setAgeDays(String tmp) {
    this.ageDays = Integer.parseInt(tmp);
  }


  /**
   *  Gets the ageHours attribute of the TicketDefect object
   *
   *@return    The ageHours value
   */
  public int getAgeHours() {
    return ageHours;
  }


  /**
   *  Sets the ageHours attribute of the TicketDefect object
   *
   *@param  tmp  The new ageHours value
   */
  public void setAgeHours(int tmp) {
    this.ageHours = tmp;
  }


  /**
   *  Sets the ageHours attribute of the TicketDefect object
   *
   *@param  tmp  The new ageHours value
   */
  public void setAgeHours(String tmp) {
    this.ageHours = Integer.parseInt(tmp);
  }


  /**
   *  Sets the siteId attribute of the TicketDefect object
   *
   *@param  tmp  The new siteId value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }


  /**
   *  Sets the siteId attribute of the TicketDefect object
   *
   *@param  tmp  The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the siteId attribute of the TicketDefect object
   *
   *@return    The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   *  Sets the siteName attribute of the TicketDefect object
   *
   *@param  tmp  The new siteName value
   */
  public void setSiteName(String tmp) {
    this.siteName = tmp;
  }


  /**
   *  Gets the siteName attribute of the TicketDefect object
   *
   *@return    The siteName value
   */
  public String getSiteName() {
    return siteName;
  }

}

