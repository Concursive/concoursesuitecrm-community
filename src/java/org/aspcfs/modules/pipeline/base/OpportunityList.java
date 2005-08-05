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
import org.aspcfs.controller.ObjectValidator;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.pipeline.beans.OpportunityBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Contains a list of opportunities, which are now a combination of
 * OpportunityHeader and OpportunityComponent objects.
 *
 * @author chris
 * @version $Id: OpportunityList.java,v 1.7 2001/10/03 21:26:46 mrajkowski Exp
 *          $
 * @created August 29, 2001
 */
public class OpportunityList extends ArrayList {

  public final static int TRUE = 1;
  public final static int FALSE = 0;
  protected int includeEnabled = 1;

  //NOTE: this class is not meant to be sync'd, sync the base classes
  //OpportunityHeaderList and OpportunityComponentList

  protected PagedListInfo pagedListInfo = null;
  protected int orgId = -1;
  protected int contactId = -1;
  protected ArrayList ignoreTypeIdList = new ArrayList();
  protected String description = null;
  protected int enteredBy = -1;
  protected boolean hasAlertDate = false;
  protected java.sql.Date alertDate = null;
  protected int owner = -1;
  protected String ownerIdRange = null;
  protected String units = null;
  protected String accountOwnerIdRange = null;
  protected java.sql.Date alertRangeStart = null;
  protected java.sql.Date alertRangeEnd = null;
  protected java.sql.Date closeDateStart = null;
  protected java.sql.Date closeDateEnd = null;
  protected int stage = -1;
  private boolean queryOpenOnly = false;
  private boolean queryClosedOnly = false;
  private boolean excludeClosedComponents = false;
  private boolean buildComponentInfo = false;
  private int typeId = 0;
  private java.sql.Timestamp trashedDate = null;
  private boolean includeOnlyTrashed = false;
  private boolean includeOnlyForGraph = false;

  protected HashMap errors = new HashMap();
  protected HashMap warnings = new HashMap();


  /**
   * Constructor for the ContactList object
   *
   * @since 1.1
   */
  public OpportunityList() {
  }


  /**
   * Sets the PagedListInfo attribute of the ContactList object
   *
   * @param tmp The new PagedListInfo value
   * @since 1.1
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the OrgId attribute of the ContactList object
   *
   * @param tmp The new OrgId value
   * @since 1.1
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   * Sets the orgId attribute of the OpportunityList object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   * Sets the queryClosedOnly attribute of the OpportunityList object
   *
   * @param queryClosedOnly The new queryClosedOnly value
   */
  public void setQueryClosedOnly(boolean queryClosedOnly) {
    this.queryClosedOnly = queryClosedOnly;
  }


  /**
   * Sets the warnings attribute of the OpportunityList object
   *
   * @param tmp The new warnings value
   */
  public void setWarnings(HashMap tmp) {
    this.warnings = tmp;
  }


  /**
   * Gets the warnings attribute of the OpportunityList object
   *
   * @return The warnings value
   */
  public HashMap getWarnings() {
    return warnings;
  }


  /**
   * Gets the typeId attribute of the OpportunityList object
   *
   * @return The typeId value
   */
  public int getTypeId() {
    return typeId;
  }


  /**
   * Sets the typeId attribute of the OpportunityList object
   *
   * @param typeId The new typeId value
   */
  public void setTypeId(int typeId) {
    this.typeId = typeId;
  }


  /**
   * Sets the typeId attribute of the OpportunityList object
   *
   * @param typeId The new typeId value
   */
  public void setTypeId(String typeId) {
    this.typeId = Integer.parseInt(typeId);
  }


  /**
   * Gets the stage attribute of the OpportunityList object
   *
   * @return The stage value
   */
  public int getStage() {
    return stage;
  }


  /**
   * Sets the stage attribute of the OpportunityList object
   *
   * @param stage The new stage value
   */
  public void setStage(int stage) {
    this.stage = stage;
  }


  /**
   * Sets the stage attribute of the OpportunityList object
   *
   * @param stage The new stage value
   */
  public void setStage(String stage) {
    this.stage = Integer.parseInt(stage);
  }


  /**
   * Sets the TypeId attribute of the ContactList object
   *
   * @param tmp The new TypeId value
   * @since 1.1
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   * Sets the contactId attribute of the OpportunityList object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Gets the buildComponentInfo attribute of the OpportunityList object
   *
   * @return The buildComponentInfo value
   */
  public boolean getBuildComponentInfo() {
    return buildComponentInfo;
  }


  /**
   * Sets the buildComponentInfo attribute of the OpportunityList object
   *
   * @param buildComponentInfo The new buildComponentInfo value
   */
  public void setBuildComponentInfo(boolean buildComponentInfo) {
    this.buildComponentInfo = buildComponentInfo;
  }


  /**
   * Sets the Description attribute of the OpportunityList object
   *
   * @param description The new Description value
   */
  public void setDescription(String description) {
    this.description = description;
  }


  /**
   * Sets the EnteredBy attribute of the OpportunityList object
   *
   * @param tmp The new EnteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the accountOwnerIdRange attribute of the OpportunityList object
   *
   * @param accountOwnerIdRange The new accountOwnerIdRange value
   */
  public void setAccountOwnerIdRange(String accountOwnerIdRange) {
    this.accountOwnerIdRange = accountOwnerIdRange;
  }


  /**
   * Sets the excludeClosedComponents attribute of the OpportunityList object
   *
   * @param excludeClosedComponents The new excludeClosedComponents value
   */
  public void setExcludeClosedComponents(boolean excludeClosedComponents) {
    this.excludeClosedComponents = excludeClosedComponents;
  }


  /**
   * Gets the excludeClosedComponents attribute of the OpportunityList object
   *
   * @return The excludeClosedComponents value
   */
  public boolean getExcludeClosedComponents() {
    return excludeClosedComponents;
  }


  /**
   * Gets the includeEnabled attribute of the OpportunityList object
   *
   * @return The includeEnabled value
   */
  public int getIncludeEnabled() {
    return includeEnabled;
  }


  /**
   * Sets the includeEnabled attribute of the OpportunityList object
   *
   * @param includeEnabled The new includeEnabled value
   */
  public void setIncludeEnabled(int includeEnabled) {
    this.includeEnabled = includeEnabled;
  }


  /**
   * Sets the OwnerIdRange attribute of the OpportunityList object
   *
   * @param ownerIdRange The new OwnerIdRange value
   */
  public void setOwnerIdRange(String ownerIdRange) {
    this.ownerIdRange = ownerIdRange;
  }


  /**
   * Sets the alertRangeStart attribute of the OpportunityList object
   *
   * @param tmp The new alertRangeStart value
   */
  public void setAlertRangeStart(java.sql.Date tmp) {
    this.alertRangeStart = tmp;
  }


  /**
   * Sets the alertRangeStart attribute of the OpportunityList object
   *
   * @param tmp The new alertRangeStart value
   */
  public void setAlertRangeStart(String tmp) {
    this.alertRangeStart = java.sql.Date.valueOf(tmp);
  }


  /**
   * Sets the alertRangeEnd attribute of the OpportunityList object
   *
   * @param tmp The new alertRangeEnd value
   */
  public void setAlertRangeEnd(java.sql.Date tmp) {
    this.alertRangeEnd = tmp;
  }


  /**
   * Sets the alertRangeEnd attribute of the OpportunityList object
   *
   * @param tmp The new alertRangeEnd value
   */
  public void setAlertRangeEnd(String tmp) {
    this.alertRangeEnd = java.sql.Date.valueOf(tmp);
  }


  /**
   * Gets the alertRangeStart attribute of the OpportunityList object
   *
   * @return The alertRangeStart value
   */
  public java.sql.Date getAlertRangeStart() {
    return alertRangeStart;
  }


  /**
   * Gets the alertRangeEnd attribute of the OpportunityList object
   *
   * @return The alertRangeEnd value
   */
  public java.sql.Date getAlertRangeEnd() {
    return alertRangeEnd;
  }


  /**
   * Gets the queryOpenOnly attribute of the OpportunityList object
   *
   * @return The queryOpenOnly value
   */
  public boolean getQueryOpenOnly() {
    return queryOpenOnly;
  }


  /**
   * Sets the queryOpenOnly attribute of the OpportunityList object
   *
   * @param queryOpenOnly The new queryOpenOnly value
   */
  public void setQueryOpenOnly(boolean queryOpenOnly) {
    this.queryOpenOnly = queryOpenOnly;
  }


  /**
   * Sets the HasAlertDate attribute of the OpportunityList object
   *
   * @param tmp The new HasAlertDate value
   */
  public void setHasAlertDate(boolean tmp) {
    this.hasAlertDate = tmp;
  }


  /**
   * Sets the AlertDate attribute of the OpportunityList object
   *
   * @param tmp The new AlertDate value
   */
  public void setAlertDate(java.sql.Date tmp) {
    this.alertDate = tmp;
  }


  /**
   * Gets the closeDateStart attribute of the OpportunityList object
   *
   * @return The closeDateStart value
   */
  public java.sql.Date getCloseDateStart() {
    return closeDateStart;
  }


  /**
   * Gets the closeDateEnd attribute of the OpportunityList object
   *
   * @return The closeDateEnd value
   */
  public java.sql.Date getCloseDateEnd() {
    return closeDateEnd;
  }


  /**
   * Sets the closeDateStart attribute of the OpportunityList object
   *
   * @param tmp The new closeDateStart value
   */
  public void setCloseDateStart(java.sql.Date tmp) {
    this.closeDateStart = tmp;
  }


  /**
   * Sets the closeDateStart attribute of the OpportunityList object
   *
   * @param tmp The new closeDateStart value
   */
  public void setCloseDateStart(String tmp) {
    try {
      java.util.Date tmpDate = DateFormat.getDateInstance(3).parse(tmp);
      closeDateStart = new java.sql.Date(new java.util.Date().getTime());
      closeDateStart.setTime(tmpDate.getTime());
    } catch (Exception e) {
      closeDateStart = null;
    }
  }


  /**
   * Sets the closeDateEnd attribute of the OpportunityList object
   *
   * @param tmp The new closeDateEnd value
   */
  public void setCloseDateEnd(java.sql.Date tmp) {
    this.closeDateEnd = tmp;
  }


  /**
   * Sets the closeDateEnd attribute of the OpportunityList object
   *
   * @param tmp The new closeDateEnd value
   */
  public void setCloseDateEnd(String tmp) {
    try {
      java.util.Date tmpDate = DateFormat.getDateInstance(3).parse(tmp);
      closeDateEnd = new java.sql.Date(new java.util.Date().getTime());
      closeDateEnd.setTime(tmpDate.getTime());
    } catch (Exception e) {
      closeDateEnd = null;
    }
  }


  /**
   * Sets the owner attribute of the OpportunityList object
   *
   * @param tmp The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }


  /**
   * Sets the Units attribute of the OpportunityList object
   *
   * @param units The new Units value
   */
  public void setUnits(String units) {
    this.units = units;
  }


  /**
   * Gets the accountOwnerIdRange attribute of the OpportunityList object
   *
   * @return The accountOwnerIdRange value
   */
  public String getAccountOwnerIdRange() {
    return accountOwnerIdRange;
  }


  /**
   * Gets the OwnerIdRange attribute of the OpportunityList object
   *
   * @return The OwnerIdRange value
   */
  public String getOwnerIdRange() {
    return ownerIdRange;
  }


  /**
   * Gets the ListSize attribute of the OpportunityList object
   *
   * @return The ListSize value
   */
  public int getListSize() {
    return this.size();
  }


  /**
   * Gets the Units attribute of the OpportunityList object
   *
   * @return The Units value
   */
  public String getUnits() {
    return units;
  }


  /**
   * Gets the EnteredBy attribute of the OpportunityList object
   *
   * @return The EnteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the HasAlertDate attribute of the OpportunityList object
   *
   * @return The HasAlertDate value
   */
  public boolean getHasAlertDate() {
    return hasAlertDate;
  }


  /**
   * Gets the Description attribute of the OpportunityList object
   *
   * @return The Description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Sets the trashedDate attribute of the OpportunityList object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(java.sql.Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   * Sets the trashedDate attribute of the OpportunityList object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the includeOnlyTrashed attribute of the OpportunityList object
   *
   * @param tmp The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(boolean tmp) {
    this.includeOnlyTrashed = tmp;
  }


  /**
   * Sets the includeOnlyTrashed attribute of the OpportunityList object
   *
   * @param tmp The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(String tmp) {
    this.includeOnlyTrashed = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the includeOnlyForGraph attribute of the OpportunityList object
   *
   * @param tmp The new includeOnlyForGraph value
   */
  public void setIncludeOnlyForGraph(boolean tmp) {
    this.includeOnlyForGraph = tmp;
  }


  /**
   * Sets the includeOnlyForGraph attribute of the OpportunityList object
   *
   * @param tmp The new includeOnlyForGraph value
   */
  public void setIncludeOnlyForGraph(String tmp) {
    this.includeOnlyForGraph = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the trashedDate attribute of the OpportunityList object
   *
   * @return The trashedDate value
   */
  public java.sql.Timestamp getTrashedDate() {
    return trashedDate;
  }


  /**
   * Gets the includeOnlyTrashed attribute of the OpportunityList object
   *
   * @return The includeOnlyTrashed value
   */
  public boolean getIncludeOnlyTrashed() {
    return includeOnlyTrashed;
  }


  /**
   * Gets the includeOnlyForGraph attribute of the OpportunityList object
   *
   * @return The includeOnlyForGraph value
   */
  public boolean getIncludeOnlyForGraph() {
    return includeOnlyForGraph;
  }


  /**
   * Gets the errors attribute of the OpportunityList object
   *
   * @return The errors value
   */
  public HashMap getErrors() {
    return errors;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean hasErrors() {
    return (errors.size() > 0);
  }


  /**
   * Builds a list of contacts based on several parameters. The parameters are
   * set after this object is constructed, then the buildList method is called
   * to generate the list.
   *
   * @param db Description of Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of Exception
   * @since 1.1
   */
  public boolean buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;
    boolean canReturn = false;
    canReturn = !ObjectValidator.validate(null, db, this);
    if (canReturn) {
      return false;
    }
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM opportunity_header x " +
        "LEFT JOIN opportunity_component oc on (x.opp_id = oc.opp_id) " +
        "WHERE x.opp_id > -1 ");
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
            "AND " + DatabaseUtils.toLowerCase(db) + "(x.description) < ? ");
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
      pagedListInfo.setDefaultSort("x.description", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY x.entered");
    }
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "x.opp_id AS header_opp_id, " +
        "x.description AS header_description, " +
        "x.acctlink AS header_acctlink, " +
        "x.contactlink AS header_contactlink, " +
        "x.entered AS header_entered, " +
        "x.enteredby AS header_enteredby, " +
        "x.modified AS header_modified, " +
        "x.modifiedby AS header_modifiedby, " +
        "x.trashed_date AS header_trashed_date, " +
        "org.name AS acct_name, org.enabled AS accountenabled, " +
        "ct.namelast AS last_name, ct.namefirst AS first_name, " +
        "ct.org_name AS ctcompany, " +
        "oc.*, y.description AS stagename " +
        "FROM opportunity_header x " +
        "LEFT JOIN opportunity_component oc ON (x.opp_id = oc.opp_id) " +
        "LEFT JOIN organization org ON (x.acctlink = org.org_id) " +
        "LEFT JOIN contact ct ON (x.contactlink = ct.contact_id), " +
        "lookup_stage y " +
        "WHERE y.code = oc.stage " +
        "AND x.opp_id > 0 ");
    pst = db.prepareStatement(
        sqlSelect.toString() +
        sqlFilter.toString() +
        sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      OpportunityBean oppBean = new OpportunityBean();
      oppBean.setHeader(new OpportunityHeader(rs));
      oppBean.setComponent(new OpportunityComponent(rs));
      this.add(oppBean);
    }
    rs.close();
    pst.close();

    Iterator i = this.iterator();
    while (i.hasNext()) {
      OpportunityBean oppBean = (OpportunityBean) i.next();
      oppBean.getHeader().buildFiles(db);
      oppBean.getComponent().buildTypes(db);
      if (this.getBuildComponentInfo()) {
        oppBean.getHeader().retrieveComponentCount(db);
        oppBean.getHeader().buildTotal(db);
      }
    }
    return true;
  }


  /**
   * Adds a feature to the IgnoreTypeId attribute of the ContactList object
   *
   * @param tmp The feature to be added to the IgnoreTypeId attribute
   * @since 1.2
   */
  public void addIgnoreTypeId(String tmp) {
    ignoreTypeIdList.add(tmp);
  }


  /**
   * Adds a feature to the IgnoreTypeId attribute of the ContactList object
   *
   * @param tmp The feature to be added to the IgnoreTypeId attribute
   * @since 1.2
   */
  public void addIgnoreTypeId(int tmp) {
    ignoreTypeIdList.add(String.valueOf(tmp));
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
      OpportunityBean oppBean = (OpportunityBean) i.next();
      if (oppBean.getComponent().reassign(db, newOwner)) {
        total++;
      }
    }
    return total;
  }


  /**
   * Description of the Method
   *
   * @param db           Description of Parameter
   * @param baseFilePath Description of the Parameter
   * @param context      Description of the Parameter
   * @throws SQLException Description of Exception
   */
  public void delete(Connection db, ActionContext context, String baseFilePath) throws SQLException {
    Iterator opportunities = this.iterator();
    while (opportunities.hasNext()) {
      OpportunityBean oppBean = (OpportunityBean) opportunities.next();
      oppBean.getHeader().delete(db, context, baseFilePath);
    }
  }


  /**
   * Builds a base SQL where statement for filtering records to be used by
   * sqlSelect and sqlCount
   *
   * @param sqlFilter Description of Parameter
   * @param db        Description of the Parameter
   * @since 1.3
   */
  protected void createFilter(Connection db, StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (orgId != -1) {
      sqlFilter.append("AND x.acctlink = ? ");
    }
    if (contactId != -1) {
      sqlFilter.append("AND x.contactlink = ? ");
    }
    if (enteredBy != -1) {
      sqlFilter.append("AND x.enteredby = ? ");
    }
    if (hasAlertDate == true) {
      sqlFilter.append("AND oc.alertdate IS NOT NULL ");
    }
    if (ignoreTypeIdList.size() > 0) {
      Iterator iList = ignoreTypeIdList.iterator();
      sqlFilter.append("AND x.contactlink NOT IN (");
      while (iList.hasNext()) {
        String placeHolder = (String) iList.next();
        sqlFilter.append("?");
        if (iList.hasNext()) {
          sqlFilter.append(",");
        }
      }
      sqlFilter.append(") ");
    }
    if (description != null) {
      if (description.indexOf("%") >= 0) {
        sqlFilter.append(
            "AND " + DatabaseUtils.toLowerCase(db) + "(x.description) LIKE " + DatabaseUtils.toLowerCase(
                db) + "(?) ");
      } else {
        sqlFilter.append(
            "AND " + DatabaseUtils.toLowerCase(db) + "(x.description) = " + DatabaseUtils.toLowerCase(
                db) + "(?) ");
      }
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
    if (owner != -1) {
      sqlFilter.append("AND oc.owner = ? ");
    }
    if (ownerIdRange != null) {
      sqlFilter.append("AND oc.owner in (" + this.ownerIdRange + ") ");
    }
    if (accountOwnerIdRange != null) {
      sqlFilter.append(
          "AND x.acctlink IN (SELECT org_id FROM organization WHERE owner IN (" + accountOwnerIdRange + ")) ");
    }
    if (units != null) {
      sqlFilter.append("AND oc.units = ? ");
    }
    if (includeEnabled == TRUE || includeEnabled == FALSE) {
      sqlFilter.append("AND oc.enabled = ? ");
    }
    if (stage != -1) {
      sqlFilter.append("AND oc.stage = ? ");
    }
    if (queryOpenOnly) {
      sqlFilter.append(
          "AND x.opp_id IN (SELECT opp_id from opportunity_component oc where oc.opp_id = x.opp_id AND oc.closed IS NULL) ");
    }
    if (queryClosedOnly) {
      sqlFilter.append(
          "AND x.opp_id NOT IN (SELECT opp_id from opportunity_component oc where oc.opp_id = x.opp_id AND oc.closed IS NULL) ");
    }
    if (typeId > 0) {
      sqlFilter.append(
          "AND oc.id IN (select ocl.opp_id from opportunity_component_levels ocl where ocl.type_id = ?) ");
    }
    if (excludeClosedComponents) {
      sqlFilter.append("AND oc.closed IS NULL ");
    }
    if (includeOnlyTrashed) {
      sqlFilter.append("AND x.trashed_date IS NOT NULL ");
    } else if (trashedDate != null) {
      sqlFilter.append("AND x.trashed_date = ? ");
    } else {
      sqlFilter.append("AND x.trashed_date IS NULL ");
    }
    if (includeOnlyForGraph) {
      sqlFilter.append(
          "AND oc.id IN ( SELECT id FROM opportunity_component WHERE " + DatabaseUtils.addTimeStampInterval(
              db, DatabaseUtils.MONTH, "terms", "closedate") + " > " + DatabaseUtils.getCurrentTimestamp(
                  db) + " ) ");
    }
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
    if (orgId != -1) {
      pst.setInt(++i, orgId);
    }
    if (contactId != -1) {
      pst.setInt(++i, contactId);
    }
    if (enteredBy != -1) {
      pst.setInt(++i, enteredBy);
    }
    if (ignoreTypeIdList.size() > 0) {
      Iterator iList = ignoreTypeIdList.iterator();
      while (iList.hasNext()) {
        int thisType = Integer.parseInt((String) iList.next());
        pst.setInt(++i, thisType);
      }
    }
    if (description != null) {
      pst.setString(++i, description);
    }
    if (alertDate != null) {
      DatabaseUtils.setTimestamp(pst, ++i, new Timestamp(alertDate.getTime()));
    }
    if (alertRangeStart != null) {
      DatabaseUtils.setTimestamp(
          pst, ++i, new Timestamp(alertRangeStart.getTime()));
    }
    if (alertRangeEnd != null) {
      DatabaseUtils.setTimestamp(
          pst, ++i, new Timestamp(alertRangeEnd.getTime()));
    }
    if (closeDateStart != null) {
      DatabaseUtils.setTimestamp(
          pst, ++i, new Timestamp(closeDateStart.getTime()));
    }
    if (closeDateEnd != null) {
      DatabaseUtils.setTimestamp(
          pst, ++i, new Timestamp(closeDateEnd.getTime()));
    }
    if (owner != -1) {
      pst.setInt(++i, owner);
    }
    if (units != null) {
      pst.setString(++i, units);
    }
    if (includeEnabled == TRUE) {
      pst.setBoolean(++i, true);
    } else if (includeEnabled == FALSE) {
      pst.setBoolean(++i, false);
    }
    if (stage != -1) {
      pst.setInt(++i, stage);
    }
    if (typeId > 0) {
      pst.setInt(++i, typeId);
    }
    if (includeOnlyTrashed) {
      // do nothing
    } else if (trashedDate != null) {
      pst.setTimestamp(++i, trashedDate);
    } else {
      // do nothing
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
        "FROM opportunity_header o " +
        "WHERE o.opp_id > 0 ");
    if (moduleId == Constants.ACCOUNTS) {
      sql.append(
          "AND (o.acctlink = ? OR o.contactlink IN (SELECT contact_id FROM contact c WHERE c.org_id = ? )) ");
    }
    if (moduleId == Constants.CONTACTS) {
      sql.append("AND o.contactlink = ? ");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (moduleId == Constants.ACCOUNTS) {
      pst.setInt(1, itemId);
      pst.setInt(2, itemId);
    }
    if (moduleId == Constants.CONTACTS) {
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


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param toTrash   Description of the Parameter
   * @param tmpUserId Description of the Parameter
   * @param context   Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean updateStatus(Connection db, ActionContext context, boolean toTrash, int tmpUserId) throws SQLException {
    Iterator itr = this.iterator();
    while (itr.hasNext()) {
      OpportunityBean tmpOpportunityBean = (OpportunityBean) itr.next();
      tmpOpportunityBean.getHeader().updateStatus(
          db, context, toTrash, tmpUserId);
    }
    return true;
  }

}

