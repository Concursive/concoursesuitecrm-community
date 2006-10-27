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

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id: TicketDefectList.java,v 1.1.4.1 2005/10/14 21:12:41
 *          mrajkowski Exp $
 * @created October 4, 2005
 */
public class TicketDefectList extends ArrayList {
  //fields
  private PagedListInfo pagedListInfo = null;
  protected int id = -1;
  protected String title = null;
  protected String description = null;
  private java.sql.Timestamp trashedDate = null;
  private int includeOnlyTrashed = Constants.FALSE;
  private int siteId = -1;
  private boolean includeAllSites = false;
  private boolean exclusiveToSite = false;
  //related records
  protected boolean buildTickets = false;
  protected int enabledOnly = Constants.UNDEFINED;
  protected int includeIfUsed = -1;

  public final static String tableName = "ticket_defect";
  public final static String uniqueField = "defect_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;


  /**
   * Constructor for the TicketDefectList object
   */
  public TicketDefectList() {
  }

  /**
   * Sets the lastAnchor attribute of the TicketDefectList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the TicketDefectList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the TicketDefectList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the TicketDefectList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the TicketDefectList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }

  /**
   * Gets the tableName attribute of the TicketDefectList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the TicketDefectList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
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
    //Build a base SQL statement for counting records
    sqlCount.append(
        " SELECT COUNT(*) AS recordcount " +
            " FROM ticket_defect td " +
            " WHERE td.defect_id > -1 ");
    createFilter(sqlFilter, db);
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
                "AND " + DatabaseUtils.toLowerCase(db) + "(td.title) < ?  ");
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
      pagedListInfo.setDefaultSort("td.title", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY td.defect_id ");
    }
    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        " td.* , ls.description AS sitename " +
            " FROM ticket_defect td " +
            " LEFT JOIN lookup_site_id ls ON (td.site_id = ls.code) " +
            " WHERE td.defect_id > -1 ");

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
      TicketDefect thisDefect = new TicketDefect(rs);
      thisDefect.buildAge();
      this.add(thisDefect);
    }
    rs.close();
    pst.close();

    if (buildTickets) {
      Iterator iter = (Iterator) this.iterator();
      while (iter.hasNext()) {
        TicketDefect defect = (TicketDefect) iter.next();
        defect.setBuildTickets(true);
        defect.buildTickets(db, defect.getSiteId());
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   * @param db        Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter, Connection db) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (id > -1) {
      sqlFilter.append("AND td.defect_id = ? ");
    }
    if (title != null) {
      sqlFilter.append("AND td.title LIKE ? ");
    }
    if (description != null) {
      sqlFilter.append("AND td.desctiption LIKE ? ");
    }
    if (includeIfUsed > -1) {
      sqlFilter.append("AND (td.defect_id IN (SELECT defect_id FROM ticket WHERE ticketid = ?) ");
      if (enabledOnly == Constants.TRUE) {
        sqlFilter.append("OR (td.enabled = ? OR (td.start_date IS NOT NULL AND td.start_date < " + DatabaseUtils.getCurrentTimestamp(db) + ")) ");
      } else if (enabledOnly == Constants.FALSE) {
        sqlFilter.append("OR (td.enabled = ? AND (td.start_date IS NULL OR td.start_date >= " + DatabaseUtils.getCurrentTimestamp(db) + ")) ");
      }
      sqlFilter.append(") ");
    } else {
      if (enabledOnly == Constants.TRUE) {
        sqlFilter.append("AND (td.enabled = ? OR (td.start_date IS NOT NULL AND td.start_date < " + DatabaseUtils.getCurrentTimestamp(db) + ")) ");
      } else if (enabledOnly == Constants.FALSE) {
        sqlFilter.append("AND (td.enabled = ? AND (td.start_date IS NULL OR td.start_date >= " + DatabaseUtils.getCurrentTimestamp(db) + ")) ");
      }
    }
    if (includeOnlyTrashed == Constants.TRUE) {
      sqlFilter.append("AND td.trashed_date IS NOT NULL ");
    } else if (includeOnlyTrashed == Constants.FALSE) {
      sqlFilter.append("AND td.trashed_date IS NULL ");
    }
    if (!includeAllSites) {
      if (siteId != -1) {
        sqlFilter.append("AND ( site_id = ? ");
        if (!exclusiveToSite) {
          sqlFilter.append("OR site_id IS NULL ");
        }
        sqlFilter.append(" ) ");
      } else {
        sqlFilter.append("AND site_id IS NULL ");
      }
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND o.entered > ? ");
      }
      sqlFilter.append("AND o.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND o.modified > ? ");
      sqlFilter.append("AND o.entered < ? ");
      sqlFilter.append("AND o.modified < ? ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (title != null) {
      pst.setString(++i, title);
    }
    if (description != null) {
      pst.setString(++i, description);
    }
    if (includeIfUsed > -1) {
      pst.setInt(++i, includeIfUsed);
    }
    if (enabledOnly == Constants.TRUE) {
      pst.setBoolean(++i, true);
    } else if (enabledOnly == Constants.FALSE) {
      pst.setBoolean(++i, false);
    }
    if (!includeAllSites && siteId != -1) {
      pst.setInt(++i, siteId);
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
   * Gets the hashMap attribute of the TicketDefectList object
   *
   * @return The hashMap value
   */
  public HashMap getHashMap() {
    HashMap map = new HashMap();
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      TicketDefect defect = (TicketDefect) iter.next();
      map.put(String.valueOf(defect.getId()), defect.getTitle() + ((defect.getSiteId() != -1) ? " (" + defect.getSiteName() + ")" : "") + (defect.isTrashed() ? " (X)" : ""));
    }
    return map;
  }


  /**
   * Gets the htmlSelectObj attribute of the TicketDefectList object
   *
   * @param selectedKey Description of the Parameter
   * @return The htmlSelectObj value
   */
  public HtmlSelect getHtmlSelectObj(int selectedKey) {
    HtmlSelect defectListSelect = new HtmlSelect();
    Iterator iter = this.iterator();
    while (iter.hasNext()) {
      TicketDefect thisDefect = (TicketDefect) iter.next();
      Timestamp currentTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
      if (!thisDefect.isDisabled()) {
        defectListSelect.addItem(thisDefect.getId(), thisDefect.getTitle() + ((thisDefect.getSiteId() != -1) ? " (" + thisDefect.getSiteName() + ")" : "") + (thisDefect.getId() == selectedKey && thisDefect.isTrashed() ? " (X)" : ""));
      } else if (thisDefect.getId() == selectedKey) {
        defectListSelect.addItem(thisDefect.getId(), thisDefect.getTitle() + ((thisDefect.getSiteId() != -1) ? " (" + thisDefect.getSiteName() + ")" : "") + " (X)");
      }
    }
    return defectListSelect;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    Iterator iter = this.iterator();
    while (iter.hasNext()) {
      TicketDefect thisDefect = (TicketDefect) iter.next();
      thisDefect.delete(db);
      iter.remove();
    }
    return true;
  }


  /*
   *  Get and Set methods
   */
  /**
   * Gets the pagedListInfo attribute of the TicketDefectList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Sets the pagedListInfo attribute of the TicketDefectList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Gets the id attribute of the TicketDefectList object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the TicketDefectList object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the TicketDefectList object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Gets the title attribute of the TicketDefectList object
   *
   * @return The title value
   */
  public String getTitle() {
    return title;
  }


  /**
   * Sets the title attribute of the TicketDefectList object
   *
   * @param tmp The new title value
   */
  public void setTitle(String tmp) {
    this.title = tmp;
  }


  /**
   * Gets the description attribute of the TicketDefectList object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Sets the description attribute of the TicketDefectList object
   *
   * @param tmp The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   * Gets the trashedDate attribute of the TicketDefectList object
   *
   * @return The trashedDate value
   */
  public java.sql.Timestamp getTrashedDate() {
    return trashedDate;
  }


  /**
   * Sets the trashedDate attribute of the TicketDefectList object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(java.sql.Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   * Sets the trashedDate attribute of the TicketDefectList object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the includeOnlyTrashed attribute of the TicketDefectList object
   *
   * @return The includeOnlyTrashed value
   */
  public int getIncludeOnlyTrashed() {
    return includeOnlyTrashed;
  }


  /**
   * Sets the includeOnlyTrashed attribute of the TicketDefectList object
   *
   * @param tmp The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(int tmp) {
    this.includeOnlyTrashed = tmp;
  }


  /**
   * Sets the includeOnlyTrashed attribute of the TicketDefectList object
   *
   * @param tmp The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(String tmp) {
    this.includeOnlyTrashed = Integer.parseInt(tmp);
  }


  /**
   * Sets the siteId attribute of the TicketDefectList object
   *
   * @param tmp The new siteId value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }


  /**
   * Sets the siteId attribute of the TicketDefectList object
   *
   * @param tmp The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }


  /**
   * Gets the siteId attribute of the TicketDefectList object
   *
   * @return The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   * Gets the buildTickets attribute of the TicketDefectList object
   *
   * @return The buildTickets value
   */
  public boolean getBuildTickets() {
    return buildTickets;
  }


  /**
   * Sets the buildTickets attribute of the TicketDefectList object
   *
   * @param tmp The new buildTickets value
   */
  public void setBuildTickets(boolean tmp) {
    this.buildTickets = tmp;
  }


  /**
   * Sets the buildTickets attribute of the TicketDefectList object
   *
   * @param tmp The new buildTickets value
   */
  public void setBuildTickets(String tmp) {
    this.buildTickets = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the enabledOnly attribute of the TicketDefectList object
   *
   * @return The enabledOnly value
   */
  public int getEnabledOnly() {
    return enabledOnly;
  }


  /**
   * Sets the enabledOnly attribute of the TicketDefectList object
   *
   * @param tmp The new enabledOnly value
   */
  public void setEnabledOnly(int tmp) {
    this.enabledOnly = tmp;
  }


  /**
   * Sets the enabledOnly attribute of the TicketDefectList object
   *
   * @param tmp The new enabledOnly value
   */
  public void setEnabledOnly(String tmp) {
    this.enabledOnly = Integer.parseInt(tmp);
  }


  /**
   * Gets the includeIfUsed attribute of the TicketDefectList object
   *
   * @return The includeIfUsed value
   */
  public int getIncludeIfUsed() {
    return includeIfUsed;
  }


  /**
   * Sets the includeIfUsed attribute of the TicketDefectList object
   *
   * @param tmp The new includeIfUsed value
   */
  public void setIncludeIfUsed(int tmp) {
    this.includeIfUsed = tmp;
  }


  /**
   * Sets the includeIfUsed attribute of the TicketDefectList object
   *
   * @param tmp The new includeIfUsed value
   */
  public void setIncludeIfUsed(String tmp) {
    this.includeIfUsed = Integer.parseInt(tmp);
  }


  /**
   * Gets the includeAllSites attribute of the TicketDefectList object
   *
   * @return The includeAllSites value
   */
  public boolean getIncludeAllSites() {
    return includeAllSites;
  }


  /**
   * Sets the includeAllSites attribute of the TicketDefectList object
   *
   * @param tmp The new includeAllSites value
   */
  public void setIncludeAllSites(boolean tmp) {
    this.includeAllSites = tmp;
  }


  /**
   * Sets the includeAllSites attribute of the TicketDefectList object
   *
   * @param tmp The new includeAllSites value
   */
  public void setIncludeAllSites(String tmp) {
    this.includeAllSites = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the exclusiveToSite attribute of the TicketDefectList object
   *
   * @return The exclusiveToSite value
   */
  public boolean getExclusiveToSite() {
    return exclusiveToSite;
  }


  /**
   * Sets the exclusiveToSite attribute of the TicketDefectList object
   *
   * @param tmp The new exclusiveToSite value
   */
  public void setExclusiveToSite(boolean tmp) {
    this.exclusiveToSite = tmp;
  }


  /**
   * Sets the exclusiveToSite attribute of the TicketDefectList object
   *
   * @param tmp The new exclusiveToSite value
   */
  public void setExclusiveToSite(String tmp) {
    this.exclusiveToSite = DatabaseUtils.parseBoolean(tmp);
  }
}

