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
package org.aspcfs.modules.pipeline.base;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;

/**
 * Container for OpportunityComponent objects.
 *
 * @author chris
 * @version $Id: OpportunityComponentList.java,v 1.5 2003/01/10 16:17:48
 *          mrajkowski Exp $
 * @created January 7, 2003
 */

public class OpportunityComponentList extends ArrayList {

  public final static String tableName = "opportunity_component";
  public final static String uniqueField = "id";
  protected java.sql.Timestamp lastAnchor = null;
  protected java.sql.Timestamp nextAnchor = null;
  protected int syncType = Constants.NO_SYNC;

  protected PagedListInfo pagedListInfo = null;
  protected int headerId = -1;
  protected int enteredBy = -1;
  protected boolean hasAlertDate = false;
  protected java.sql.Timestamp alertDate = null;
  protected int owner = -1;
  private int contactId = -1;
  private int orgId = -1;
  protected String ownerIdRange = null;
  protected java.sql.Timestamp alertRangeStart = null;
  protected java.sql.Timestamp alertRangeEnd = null;
  protected java.sql.Timestamp closeDateStart = null;
  protected java.sql.Timestamp closeDateEnd = null;
  protected boolean queryOpenOnly = false;
  protected int environment = -1;
  protected int competitors = -1;
  protected int compellingEvent = -1;
  protected int budget = -1;
  protected int controlledHierarchyOnly = Constants.UNDEFINED;
  protected int accessType = -1;
  private java.sql.Timestamp trashedDate = null;
  private boolean includeOnlyTrashed = false;
  private double defaultTerms = 1.0;
  private String defaultUnits = null;
  // for the graph
  private String units = null;
  private boolean includeOnlyForGraph = false;


  /**
   * Constructor for the OpportunityComponentList object
   */
  public OpportunityComponentList() {
  }


  /**
   * Sets the pagedListInfo attribute of the OpportunityComponentList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the headerId attribute of the OpportunityComponentList object
   *
   * @param tmp The new headerId value
   */
  public void setHeaderId(String tmp) {
    this.headerId = Integer.parseInt(tmp);
  }


  /**
   * Sets the headerId attribute of the OpportunityComponentList object
   *
   * @param tmp The new headerId value
   */
  public void setHeaderId(int tmp) {
    this.headerId = tmp;
  }


  /**
   * Sets the enteredBy attribute of the OpportunityComponentList object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the ownerIdRange attribute of the OpportunityComponentList object
   *
   * @param ownerIdRange The new ownerIdRange value
   */
  public void setOwnerIdRange(String ownerIdRange) {
    this.ownerIdRange = ownerIdRange;
  }


  /**
   * Sets the alertRangeStart attribute of the OpportunityComponentList object
   *
   * @param tmp The new alertRangeStart value
   */
  public void setAlertRangeStart(java.sql.Timestamp tmp) {
    this.alertRangeStart = tmp;
  }


  /**
   * Sets the alertRangeStart attribute of the OpportunityComponentList object
   *
   * @param tmp The new alertRangeStart value
   */
  public void setAlertRangeStart(String tmp) {
    this.alertRangeStart = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the alertRangeEnd attribute of the OpportunityComponentList object
   *
   * @param tmp The new alertRangeEnd value
   */
  public void setAlertRangeEnd(java.sql.Timestamp tmp) {
    this.alertRangeEnd = tmp;
  }


  /**
   * Sets the alertRangeEnd attribute of the OpportunityComponentList object
   *
   * @param tmp The new alertRangeEnd value
   */
  public void setAlertRangeEnd(String tmp) {
    this.alertRangeEnd = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Gets the alertRangeStart attribute of the OpportunityComponentList object
   *
   * @return The alertRangeStart value
   */
  public java.sql.Timestamp getAlertRangeStart() {
    return alertRangeStart;
  }


  /**
   * Gets the alertRangeEnd attribute of the OpportunityComponentList object
   *
   * @return The alertRangeEnd value
   */
  public java.sql.Timestamp getAlertRangeEnd() {
    return alertRangeEnd;
  }


  /**
   * Sets the hasAlertDate attribute of the OpportunityComponentList object
   *
   * @param tmp The new hasAlertDate value
   */
  public void setHasAlertDate(boolean tmp) {
    this.hasAlertDate = tmp;
  }


  /**
   * Sets the alertDate attribute of the OpportunityComponentList object
   *
   * @param tmp The new alertDate value
   */
  public void setAlertDate(java.sql.Timestamp tmp) {
    this.alertDate = tmp;
  }


  /**
   * Gets the tableName attribute of the OpportunityComponentList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the OpportunityComponentList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Gets the closeDateStart attribute of the OpportunityComponentList object
   *
   * @return The closeDateStart value
   */
  public java.sql.Timestamp getCloseDateStart() {
    return closeDateStart;
  }


  /**
   * Gets the closeDateEnd attribute of the OpportunityComponentList object
   *
   * @return The closeDateEnd value
   */
  public java.sql.Timestamp getCloseDateEnd() {
    return closeDateEnd;
  }


  /**
   * Sets the closeDateStart attribute of the OpportunityComponentList object
   *
   * @param tmp The new closeDateStart value
   */
  public void setCloseDateStart(java.sql.Timestamp tmp) {
    this.closeDateStart = tmp;
  }


  /**
   * Gets the environment attribute of the OpportunityComponentList object
   *
   * @return The environment value
   */
  public int getEnvironment() {
    return environment;
  }


  /**
   * Sets the environment attribute of the OpportunityComponentList object
   *
   * @param tmp The new environment value
   */
  public void setEnvironment(int tmp) {
    this.environment = tmp;
  }


  /**
   * Sets the environment attribute of the OpportunityComponentList object
   *
   * @param tmp The new environment value
   */
  public void setEnvironment(String tmp) {
    this.environment = Integer.parseInt(tmp);
  }


  /**
   * Gets the competitors attribute of the OpportunityComponentList object
   *
   * @return The competitors value
   */
  public int getCompetitors() {
    return competitors;
  }


  /**
   * Sets the competitors attribute of the OpportunityComponentList object
   *
   * @param tmp The new competitors value
   */
  public void setCompetitors(int tmp) {
    this.competitors = tmp;
  }


  /**
   * Sets the competitors attribute of the OpportunityComponentList object
   *
   * @param tmp The new competitors value
   */
  public void setCompetitors(String tmp) {
    this.competitors = Integer.parseInt(tmp);
  }


  /**
   * Gets the compellingEvent attribute of the OpportunityComponentList object
   *
   * @return The compellingEvent value
   */
  public int getCompellingEvent() {
    return compellingEvent;
  }


  /**
   * Sets the compellingEvent attribute of the OpportunityComponentList object
   *
   * @param tmp The new compellingEvent value
   */
  public void setCompellingEvent(int tmp) {
    this.compellingEvent = tmp;
  }


  /**
   * Sets the compellingEvent attribute of the OpportunityComponentList object
   *
   * @param tmp The new compellingEvent value
   */
  public void setCompellingEvent(String tmp) {
    this.compellingEvent = Integer.parseInt(tmp);
  }


  /**
   * Gets the budget attribute of the OpportunityComponentList object
   *
   * @return The budget value
   */
  public int getBudget() {
    return budget;
  }


  /**
   * Sets the budget attribute of the OpportunityComponentList object
   *
   * @param tmp The new budget value
   */
  public void setBudget(int tmp) {
    this.budget = tmp;
  }


  /**
   * Sets the budget attribute of the OpportunityComponentList object
   *
   * @param tmp The new budget value
   */
  public void setBudget(String tmp) {
    this.budget = Integer.parseInt(tmp);
  }


  public double getDefaultTerms() {
    return defaultTerms;
  }


  public void setDefaultTerms(double tmp) {
    this.defaultTerms = tmp;
  }


  public void setDefaultTerms(String tmp) {
    this.defaultTerms = Double.parseDouble(tmp);
  }


  public String getDefaultUnits() {
    return defaultUnits;
  }


  public void setDefaultUnits(String tmp) {
    this.defaultUnits = tmp;
  }


  /**
   * Sets the closeDateStart attribute of the OpportunityComponentList object
   *
   * @param tmp The new closeDateStart value
   */
  public void setCloseDateStart(String tmp) {
    try {
      java.util.Date tmpDate = DateFormat.getDateTimeInstance(
          DateFormat.SHORT, DateFormat.LONG).parse(tmp);
      closeDateStart = new java.sql.Timestamp(new java.util.Date().getTime());
      closeDateStart.setTime(tmpDate.getTime());
    } catch (Exception e) {
      closeDateStart = null;
    }
  }


  /**
   * Sets the closeDateEnd attribute of the OpportunityComponentList object
   *
   * @param tmp The new closeDateEnd value
   */
  public void setCloseDateEnd(java.sql.Timestamp tmp) {
    this.closeDateEnd = tmp;
  }


  /**
   * Sets the closeDateEnd attribute of the OpportunityComponentList object
   *
   * @param tmp The new closeDateEnd value
   */
  public void setCloseDateEnd(String tmp) {
    try {
      java.util.Date tmpDate = DateFormat.getDateTimeInstance(
          DateFormat.SHORT, DateFormat.LONG).parse(tmp);
      closeDateEnd = new java.sql.Timestamp(new java.util.Date().getTime());
      closeDateEnd.setTime(tmpDate.getTime());
    } catch (Exception e) {
      closeDateEnd = null;
    }
  }


  /**
   * Sets the owner attribute of the OpportunityComponentList object
   *
   * @param tmp The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }


  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   * Gets the ownerIdRange attribute of the OpportunityComponentList object
   *
   * @return The ownerIdRange value
   */
  public String getOwnerIdRange() {
    return ownerIdRange;
  }


  /**
   * Gets the queryOpenOnly attribute of the OpportunityComponentList object
   *
   * @return The queryOpenOnly value
   */
  public boolean getQueryOpenOnly() {
    return queryOpenOnly;
  }


  /**
   * Sets the queryOpenOnly attribute of the OpportunityComponentList object
   *
   * @param queryOpenOnly The new queryOpenOnly value
   */
  public void setQueryOpenOnly(boolean queryOpenOnly) {
    this.queryOpenOnly = queryOpenOnly;
  }


  /**
   * Sets the trashedDate attribute of the OpportunityComponentList object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(java.sql.Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   * Sets the trashedDate attribute of the OpportunityComponentList object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the includeOnlyTrashed attribute of the OpportunityComponentList
   * object
   *
   * @param tmp The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(boolean tmp) {
    this.includeOnlyTrashed = tmp;
  }


  /**
   * Sets the includeOnlyTrashed attribute of the OpportunityComponentList
   * object
   *
   * @param tmp The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(String tmp) {
    this.includeOnlyTrashed = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the trashedDate attribute of the OpportunityComponentList object
   *
   * @return The trashedDate value
   */
  public java.sql.Timestamp getTrashedDate() {
    return trashedDate;
  }


  /**
   * Gets the includeOnlyTrashed attribute of the OpportunityComponentList
   * object
   *
   * @return The includeOnlyTrashed value
   */
  public boolean getIncludeOnlyTrashed() {
    return includeOnlyTrashed;
  }


  /**
   * Gets the listSize attribute of the OpportunityComponentList object
   *
   * @return The listSize value
   */
  public int getListSize() {
    return this.size();
  }


  /**
   * Gets the enteredBy attribute of the OpportunityComponentList object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the hasAlertDate attribute of the OpportunityComponentList object
   *
   * @return The hasAlertDate value
   */
  public boolean getHasAlertDate() {
    return hasAlertDate;
  }


  /**
   * Gets the controlledHierarchyOnly attribute of the OpportunityComponentList
   * object
   *
   * @return The controlledHierarchyOnly value
   */
  public int getControlledHierarchyOnly() {
    return controlledHierarchyOnly;
  }


  /**
   * Sets the controlledHierarchyOnly attribute of the OpportunityComponentList
   * object
   *
   * @param tmp The new controlledHierarchyOnly value
   */
  public void setControlledHierarchyOnly(int tmp) {
    this.controlledHierarchyOnly = tmp;
  }


  /**
   * Sets the controlledHierarchyOnly attribute of the OpportunityComponentList
   * object
   *
   * @param tmp The new controlledHierarchyOnly value
   */
  public void setControlledHierarchyOnly(String tmp) {
    this.controlledHierarchyOnly = Integer.parseInt(tmp);
  }


  /**
   * Sets the controlledHierarchy attribute of the OpportunityComponentList
   * object
   *
   * @param hierarchy The new controlledHierarchy value
   * @param tmp       The new controlledHierarchy value
   */
  public void setControlledHierarchy(int hierarchy, String tmp) {
    this.controlledHierarchyOnly = hierarchy;
    this.ownerIdRange = tmp;
  }


  /**
   * Gets the accessType attribute of the OpportunityComponentList object
   *
   * @return The accessType value
   */
  public int getAccessType() {
    return accessType;
  }


  /**
   * Sets the accessType attribute of the OpportunityComponentList object
   *
   * @param tmp The new accessType value
   */
  public void setAccessType(int tmp) {
    this.accessType = tmp;
  }


  /**
   * Sets the accessType attribute of the OpportunityComponentList object
   *
   * @param tmp The new accessType value
   */
  public void setAccessType(String tmp) {
    this.accessType = Integer.parseInt(tmp);
  }


  /**
   * Sets the units attribute of the OpportunityComponentList object
   *
   * @param tmp The new units value
   */
  public void setUnits(String tmp) {
    this.units = tmp;
  }


  /**
   * Sets the includeOnlyForGraph attribute of the OpportunityComponentList
   * object
   *
   * @param tmp The new includeOnlyForGraph value
   */
  public void setIncludeOnlyForGraph(boolean tmp) {
    this.includeOnlyForGraph = tmp;
  }


  /**
   * Sets the includeOnlyForGraph attribute of the OpportunityComponentList
   * object
   *
   * @param tmp The new includeOnlyForGraph value
   */
  public void setIncludeOnlyForGraph(String tmp) {
    this.includeOnlyForGraph = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the units attribute of the OpportunityComponentList object
   *
   * @return The units value
   */
  public String getUnits() {
    return units;
  }


  /**
   * Gets the includeOnlyForGraph attribute of the OpportunityComponentList
   * object
   *
   * @return The includeOnlyForGraph value
   */
  public boolean getIncludeOnlyForGraph() {
    return includeOnlyForGraph;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param timeZone Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public HashMap queryRecordCount(Connection db, TimeZone timeZone) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;

    HashMap events = new HashMap();
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlTail = new StringBuffer();
    createFilter(db, sqlFilter);
    sqlSelect.append(
        "SELECT alertdate, count(*) as nocols " +
            "FROM opportunity_component oc " +
            "WHERE oc.opp_id > -1 ");
    sqlTail.append("GROUP BY alertdate ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlTail.toString());
    prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      String alertDate = DateUtils.getServerToUserDateString(
          timeZone, DateFormat.SHORT, rs.getTimestamp("alertdate"));
      int temp = rs.getInt("nocols");
      events.put(alertDate, new Integer(temp));
    }
    rs.close();
    pst.close();
    return events;
  }


  /**
   * Builds a subset of attributes of the Opprtunity primarily for the
   * Calendar.
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildShortList(Connection db) throws SQLException {
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    createFilter(db, sqlFilter);
    sqlSelect.append(
        "SELECT oc.opp_id, oc.id, oc.description, oc.alertdate, oc.alert, oc.closedate, oc.guessvalue " +
            "FROM opportunity_component oc  " +
            "LEFT JOIN opportunity_header oh ON (oc.opp_id = oh.opp_id) " +
            "LEFT JOIN organization org ON (oh.acctlink = org.org_id) " +
            "WHERE oc.opp_id > -1 ");
    PreparedStatement pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString());
    prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      OpportunityComponent thisOpp = new OpportunityComponent();
      thisOpp.setHeaderId(rs.getInt("opp_id"));
      thisOpp.setId(rs.getInt("id"));
      thisOpp.setDescription(rs.getString("description"));
      thisOpp.setAlertDate(rs.getTimestamp("alertdate"));
      thisOpp.setAlertText(rs.getString("alert"));
      thisOpp.setCloseDate(rs.getString("closedate"));
      thisOpp.setGuess(rs.getDouble("guessvalue"));
      this.add(thisOpp);
    }
    rs.close();
    pst.close();
  }


  /**
   * Builds a list of contacts based on several parameters. The parameters are
   * set after this object is constructed, then the buildList method is called
   * to generate the list.
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   * @since 1.1
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
            "FROM opportunity_component oc " +
            "LEFT JOIN lookup_stage y ON (oc.stage = y.code) " +
            "WHERE oc.id > -1 ");

    createFilter(db, sqlFilter);

    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(
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
                "AND " + DatabaseUtils.toLowerCase(db) + "(oc.description) < ? ");
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

      //pagedListInfo.setDefaultSort("oc.description", null);
      pagedListInfo.setDefaultSort("oc.closed", "desc");
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY oc.closed");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "oc.*, y.description AS stagename " +
            "FROM opportunity_component oc " +
            "LEFT JOIN lookup_stage y ON (oc.stage = y.code) " +
            "WHERE y.code = oc.stage " +
            "AND oc.opp_id > -1 ");
    pst = db.prepareStatement(
        sqlSelect.toString() +
            sqlFilter.toString() +
            sqlOrder.toString());
    items = prepareFilter(pst);
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, pst);
    }
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      OpportunityComponent thisOppComponent = new OpportunityComponent(rs);
      this.add(thisOppComponent);
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   */
  protected void createFilter(Connection db, StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (headerId != -1) {
      sqlFilter.append("AND oc.opp_id = ? ");
    }
    if (enteredBy != -1) {
      sqlFilter.append("AND oc.enteredby = ? ");
    }
    if (hasAlertDate) {
      sqlFilter.append("AND oc.alertdate IS NOT NULL ");
    }
    if (alertDate != null) {
      sqlFilter.append("AND oc.alertdate = ? ");
    }
    if (alertRangeStart != null) {
      sqlFilter.append("AND oc.alertdate >= ? ");
    }
    if (alertRangeEnd != null) {
      sqlFilter.append("AND oc.alertdate < ? ");
    }
    if (closeDateStart != null) {
      sqlFilter.append("AND oc.closedate >= ? ");
    }
    if (closeDateEnd != null) {
      sqlFilter.append("AND oc.closedate <= ? ");
    }
    if (controlledHierarchyOnly != Constants.UNDEFINED) {
      if (controlledHierarchyOnly == Constants.FALSE) {
        sqlFilter.append(
            "AND (oc.owner IN (" + ownerIdRange + ") " +
                "OR oc.opp_id IN (SELECT opp_id from opportunity_header x " +
                "WHERE x.access_type = ?)) ");
      } else {
        sqlFilter.append("AND oc.owner IN (" + ownerIdRange + ") ");
      }
    } else {
      if (owner != -1) {
        sqlFilter.append("AND oc.owner = ? ");
      }
      if (ownerIdRange != null) {
        sqlFilter.append("AND oc.owner in (" + this.ownerIdRange + ") ");
      }
    }
    if (queryOpenOnly) {
      sqlFilter.append("AND oc.closed IS NULL ");
    }
    if (includeOnlyTrashed) {
      sqlFilter.append("AND oc.trashed_date IS NOT NULL ");
    } else if (trashedDate != null) {
      sqlFilter.append("AND oc.trashed_date = ? ");
    } else {
      sqlFilter.append("AND oc.trashed_date IS NULL ");
    }
    if (environment != -1) {
      sqlFilter.append("AND oc.environment = ? ");
    }
    if (competitors != -1) {
      sqlFilter.append("AND oc.competitors = ? ");
    }
    if (compellingEvent != -1) {
      sqlFilter.append("AND oc.compelling_event = ? ");
    }
    if (budget != -1) {
      sqlFilter.append("AND oc.budget = ? ");
    }
    if (units != null) {
      sqlFilter.append("AND oc.units = ? ");
    }
    /*if (includeOnlyForGraph) {
      sqlFilter.append(
          "AND " + DatabaseUtils.addTimestampInterval(db, DatabaseUtils.MONTH, "terms", "closedate")
          + " > " + DatabaseUtils.getCurrentTimestamp(db) + " ) ");
    }*/
    if (includeOnlyForGraph) {
      if (defaultUnits == null || "".equals(defaultUnits)) {
        sqlFilter.append(
          "AND ((oc.units = ? AND oc.id IN ( SELECT id FROM opportunity_component WHERE " + DatabaseUtils.addTimestampInterval(
              db, DatabaseUtils.MONTH, "terms", "closedate") + " > " + DatabaseUtils.getCurrentTimestamp(
              db) + " )) OR ( oc.units = ? AND oc.id IN (  " +
              "SELECT id FROM opportunity_component WHERE " + DatabaseUtils.addTimestampInterval(
              db, DatabaseUtils.WEEK, "terms", "closedate") + " > " + DatabaseUtils.getCurrentTimestamp(
              db) + " ))) ");
      } else {
        sqlFilter.append(
          "AND oc.id IN ( SELECT id FROM opportunity_component WHERE " + DatabaseUtils.addTimestampInterval(
              db, DatabaseUtils.WEEK, "terms", "closedate", defaultUnits, java.lang.Math.round(defaultTerms)) + " > " + DatabaseUtils.getCurrentTimestamp(
              db) + " ) ");
      }
    }
    if (contactId != -1) {
      sqlFilter.append(
            "AND oc.opp_id IN (SELECT opp_id from opportunity_header x " +
                "WHERE x.contactlink = ?) ");
    }
    if (orgId != -1) {
      sqlFilter.append(
            "AND oc.opp_id IN (SELECT opp_id from opportunity_header x " +
                "WHERE x.acctlink = ?) ");
    }

  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param newOwner Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int reassignElements(Connection db, int newOwner) throws SQLException {
    int total = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      OpportunityComponent thisOpp = (OpportunityComponent) i.next();
      if (thisOpp.reassign(db, newOwner)) {
        total++;
      }
    }
    return total;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param newOwner Description of the Parameter
   * @param userId   Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int reassignElements(Connection db, int newOwner, int userId) throws SQLException {
    int total = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      OpportunityComponent thisOpp = (OpportunityComponent) i.next();
      thisOpp.setModifiedBy(userId);
      if (thisOpp.reassign(db, newOwner)) {
        total++;
      }
    }
    return total;
  }


  /**
   * Sets the parameters for the preparedStatement - these items must
   * correspond with the createFilter statement
   *
   * @param pst Description os.gerameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   * @since 1.3
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (headerId != -1) {
      pst.setInt(++i, headerId);
    }
    if (enteredBy != -1) {
      pst.setInt(++i, enteredBy);
    }
    if (alertDate != null) {
      pst.setTimestamp(++i, alertDate);
    }
    if (alertRangeStart != null) {
      pst.setTimestamp(++i, alertRangeStart);
    }
    if (alertRangeEnd != null) {
      pst.setTimestamp(++i, alertRangeEnd);
    }
    if (closeDateStart != null) {
      pst.setTimestamp(++i, closeDateStart);
    }
    if (closeDateEnd != null) {
      pst.setTimestamp(++i, closeDateEnd);
    }
    if (controlledHierarchyOnly != Constants.UNDEFINED) {
      if (controlledHierarchyOnly == Constants.FALSE) {
        pst.setInt(++i, this.getAccessType());
      }
    } else {
      if (owner != -1) {
        pst.setInt(++i, owner);
      }
    }
    if (includeOnlyTrashed) {
      // do nothing
    } else if (trashedDate != null) {
      pst.setTimestamp(++i, trashedDate);
    } else {
      // do nothing
    }
    if (environment != -1) {
      DatabaseUtils.setInt(pst, ++i, this.getEnvironment());
    }
    if (competitors != -1) {
      DatabaseUtils.setInt(pst, ++i, this.getCompetitors());
    }
    if (compellingEvent != -1) {
      DatabaseUtils.setInt(pst, ++i, this.getCompellingEvent());
    }
    if (budget != -1) {
      DatabaseUtils.setInt(pst, ++i, this.getBudget());
    }
    if (units != null) {
      pst.setString(++i, units);
    }
    if (includeOnlyForGraph && (defaultUnits == null || "".equals(defaultUnits))) {
      pst.setString(++i, "M");
      pst.setString(++i, "W");
    }
    if (contactId != -1) {
      pst.setInt(++i, contactId);
    }
    if (orgId != -1) {
      pst.setInt(++i, orgId);
    }
    return i;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param moduleId Description of the Parameter
   * @param itemId   Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static int retrieveRecordCount(Connection db, int moduleId, int itemId) throws SQLException {
    int count = 0;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT COUNT(*) as itemcount " +
            "FROM opportunity_component oc " +
            "WHERE opp_id > 0 ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      count = rs.getInt("itemcount");
    }
    rs.close();
    pst.close();
    return count;
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param toTrash   Description of the Parameter
   * @param tmpUserId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean updateStatus(Connection db, ActionContext context, boolean toTrash, int tmpUserId) throws SQLException {
    Iterator itr = this.iterator();
    while (itr.hasNext()) {
      OpportunityComponent tmpOpportunityComponent = (OpportunityComponent) itr.next();
      tmpOpportunityComponent.updateStatus(db, context, toTrash, tmpUserId);
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void invalidateUserData(ActionContext context) throws SQLException {
    Iterator itr = this.iterator();
    while (itr.hasNext()) {
      OpportunityComponent tmpOpportunityComponent = (OpportunityComponent) itr.next();
      tmpOpportunityComponent.invalidateUserData(context);
    }
  }

  public double getGuessSum() {
    double sum = 0d;
    Iterator itr = this.iterator();
    while (itr.hasNext()) {
      OpportunityComponent component = (OpportunityComponent) itr.next();
      sum += component.getGuess();
    }
    return sum;
  }
}

