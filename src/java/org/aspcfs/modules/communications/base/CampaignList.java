package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.PagedListInfo;
import com.darkhorseventures.webutils.HtmlSelect;
import com.darkhorseventures.utils.DatabaseUtils;

/**
 *  Represents the combination of a Message, Recipients, and Schedule details.
 *
 *@author     Wesley_S_Gillette
 *@created    November 16, 2001
 *@version    $Id$
 */
public class CampaignList extends Vector {

  public final static int TRUE = 1;
  public final static int FALSE = 0;

  private PagedListInfo pagedListInfo = null;
  private String name = "";
  private String description = "";
  private java.sql.Date activeDate = null;
  private int enabled = -1;
  private int active = -1;

  private boolean incompleteOnly = false;
  private boolean completeOnly = false;
  private int owner = -1;
  private String ownerIdRange = null;
  private String idRange = null;
  private int ready = -1;


  /**
   *  Constructor for the CampaignList object
   *
   *@since    1.1
   */
  public CampaignList() { }


  /**
   *  Sets the pagedListInfo attribute of the CampaignList object
   *
   *@param  tmp  The new pagedListInfo value
   *@since       1.1
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the name attribute of the CampaignList object
   *
   *@param  tmp  The new name value
   *@since       1.1
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the description attribute of the CampaignList object
   *
   *@param  tmp  The new description value
   *@since       1.1
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the ActiveDate attribute of the CampaignList object
   *
   *@param  tmp  The new ActiveDate value
   *@since       1.2
   */
  public void setActiveDate(java.sql.Date tmp) {
    this.activeDate = tmp;
  }


  /**
   *  Sets the Enabled attribute of the CampaignList object
   *
   *@param  tmp  The new Enabled value
   *@since       1.2
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the Active attribute of the CampaignList object
   *
   *@param  tmp  The new Active value
   *@since
   */
  public void setActive(int tmp) {
    this.active = tmp;
  }


  /**
   *  Sets the ready attribute of the CampaignList object
   *
   *@param  tmp  The new ready value
   */
  public void setReady(int tmp) {
    this.ready = tmp;
  }


  /**
   *  Sets the IncompleteOnly attribute of the CampaignList object
   *
   *@param  tmp  The new IncompleteOnly value
   *@since
   */
  public void setIncompleteOnly(boolean tmp) {
    this.incompleteOnly = tmp;
  }


  /**
   *  Sets the CompleteOnly attribute of the CampaignList object
   *
   *@param  tmp  The new CompleteOnly value
   *@since
   */
  public void setCompleteOnly(boolean tmp) {
    this.completeOnly = tmp;
  }


  /**
   *  Sets the owner attribute of the CampaignList object
   *
   *@param  tmp  The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }


  /**
   *  Sets the ownerIdRange attribute of the CampaignList object
   *
   *@param  tmp  The new ownerIdRange value
   */
  public void setOwnerIdRange(String tmp) {
    this.ownerIdRange = tmp;
  }


  /**
   *  Sets the idRange attribute of the CampaignList object
   *
   *@param  idRange  The new idRange value
   */
  public void setIdRange(String idRange) {
    this.idRange = idRange;
  }


  /**
   *  Gets the pagedListInfo attribute of the CampaignList object
   *
   *@return    The pagedListInfo value
   *@since     1.1
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the idRange attribute of the CampaignList object
   *
   *@return    The idRange value
   */
  public String getIdRange() {
    return idRange;
  }


  /**
   *  Gets the name attribute of the CampaignList object
   *
   *@return    The name value
   *@since     1.1
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the description attribute of the CampaignList object
   *
   *@return    The description value
   *@since     1.1
   */
  public String getDescription() {
    return description;
  }



  /**
   *  Gets the htmlSelect attribute of the CampaignList object
   *
   *@param  selectName  Description of Parameter
   *@return             The htmlSelect value
   *@since              1.1
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }



  /**
   *  Gets the htmlSelect attribute of the CampaignList object
   *
   *@param  selectName  Description of Parameter
   *@param  defaultKey  Description of Parameter
   *@return             The htmlSelect value
   *@since              1.1
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect campaignListSelect = new HtmlSelect();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Campaign thisCampaign = (Campaign) i.next();
      campaignListSelect.addItem(
          thisCampaign.getId(),
          thisCampaign.getName());
    }
    return campaignListSelect.getHtml(selectName, defaultKey);
  }


  /**
   *  Gets the ActiveDate attribute of the CampaignList object
   *
   *@return    The ActiveDate value
   *@since     1.2
   */
  public java.sql.Date getActiveDate() {
    return activeDate;
  }


  /**
   *  Gets the Enabled attribute of the CampaignList object
   *
   *@return    The Enabled value
   *@since     1.2
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   *  Gets the Active attribute of the CampaignList object
   *
   *@return    The Active value
   *@since
   */
  public int getActive() {
    return active;
  }


  /**
   *  Gets the IncompleteOnly attribute of the CampaignList object
   *
   *@return    The IncompleteOnly value
   *@since
   */
  public boolean getIncompleteOnly() {
    return incompleteOnly;
  }


  /**
   *  Gets the CompleteOnly attribute of the CampaignList object
   *
   *@return    The CompleteOnly value
   *@since
   */
  public boolean getCompleteOnly() {
    return completeOnly;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
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
        "FROM campaign c " +
        "WHERE c.campaign_id > -1 ");

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
            "AND c.name < ? ");
        items = prepareFilter(pst);
        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
        pagedListInfo.setDefaultSort("c.name", null);
      } else {
        //Determine column to sort by
        if (completeOnly) {
          pagedListInfo.setDefaultSort("active_date", "desc");
        } else {
          pagedListInfo.setDefaultSort("active_date", null);
        }
      }
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY c.modified desc ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "c.*, msg.name as messageName, dt.description as delivery " +
        "FROM campaign c " +
        "LEFT JOIN message msg ON (c.message_id = msg.id) " +
        "LEFT JOIN lookup_delivery_options dt ON (c.send_method_id = dt.code) " +
        "WHERE c.campaign_id > -1 ");

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
      Campaign thisCamp = new Campaign(rs);
      this.addElement(thisCamp);
    }
    rs.close();
    pst.close();

    buildResources(db);
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of Parameter
   *@since             1.1
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (activeDate != null) {
      sqlFilter.append("AND c.active_date = ? ");
    }

    if (enabled == FALSE || enabled == TRUE) {
      sqlFilter.append("AND c.enabled = ? ");
    }

    if (active == FALSE || active == TRUE) {
      sqlFilter.append("AND c.active = ? ");
    }

    if (incompleteOnly) {
      sqlFilter.append("AND ( c.message_id < 1 OR c.campaign_id NOT IN (SELECT DISTINCT campaign_id FROM campaign_list_groups) OR active_date IS NULL OR active = ?) ");
    }

    if (completeOnly) {
      sqlFilter.append("AND ( c.message_id > 0 AND c.campaign_id IN (SELECT DISTINCT campaign_id FROM campaign_list_groups) AND active_date IS NOT NULL AND active = ?) ");
    }

    if (owner > -1) {
      sqlFilter.append("AND c.enteredby = " + owner + " ");
    }

    if (ownerIdRange != null) {
      sqlFilter.append("AND c.enteredBy IN (" + ownerIdRange + ") ");
    }

    if (idRange != null) {
      sqlFilter.append("AND c.campaign_id IN (" + idRange + ") ");
    }

    if (ready == TRUE) {
      sqlFilter.append("AND c.status_id IN (" + Campaign.QUEUE + ", " + Campaign.STARTED + ") ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;

    if (activeDate != null) {
      pst.setDate(++i, activeDate);
    }
    
    if (enabled == FALSE) {
      pst.setBoolean(++i, false);
    } else if (enabled == TRUE) {
      pst.setBoolean(++i, true);
    }
    
    if (active == FALSE) {
      pst.setBoolean(++i, false);
    } else if (active == TRUE) {
      pst.setBoolean(++i, true);
    }
    
    if (incompleteOnly) {
      pst.setBoolean(++i, false);
    }
    
    if (completeOnly) {
      pst.setBoolean(++i, true);
    }

    return i;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  private void buildResources(Connection db) throws SQLException {
    Iterator i = this.iterator();

    while (i.hasNext()) {
      Campaign thisCampaign = (Campaign) i.next();
      thisCampaign.buildRecipientCount(db);
      thisCampaign.setGroupList(db);
      thisCampaign.buildFileCount(db);
    }
  }

}

