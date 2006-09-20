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

package org.aspcfs.modules.industry.spirit.base;

import java.util.*;
import java.sql.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.industry.spirit.base.MaresaClient;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.contacts.base.*;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    January 8, 2002
 *@version    $Id$
 */
public class MaresaClientList extends ArrayList {

  /**
   *  Description of the Field
   */
  public final static String tableName = "call_log";
  /**
   *  Description of the Field
   */
  public final static String uniqueField = "call_id";
  /**
   *  Description of the Field
   */
  protected PagedListInfo pagedListInfo = null;
  /**
   *  Description of the Field
   */
  protected int siteId = -1;
  protected int orgId = -1;
  protected int contactId = -1;
  /**
   *  Description of the Field
   */
  protected int defaultKey = -1;
  /**
   *  Description of the Field
   */
  protected String siteClient = null;



  /**
   *  Constructor for the CallList object
   *
   *@since
   */
  public MaresaClientList() { }


  /**
   *  Constructor for the RevenueTypeList object
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public MaresaClientList(Connection db, String thisContact) throws SQLException {
    contactId = Integer.parseInt(thisContact);
    buildList(db);
  }


  public MaresaClientList(Connection db, int contactId) throws SQLException {
    this.contactId = contactId;
    buildList(db);
  }
  
  
  /**
   *  Sets the PagedListInfo attribute of the CallList object
   *
   *@param  tmp  The new PagedListInfo value
   *@since
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the segmentId attribute of the MaresaClientList object
   *
   *@param  segmentId  The new segmentId value
   
  public void setSegmentId(int segmentId) {
    this.segmentId = segmentId;
  }
*/

  /**
   *  Sets the siteId attribute of the MaresaClientList object
   *
   *@param  siteId  The new siteId value
   */
  public void setSiteId(int siteId) {
    this.siteId = siteId;
  }


  /**
   *  Sets the siteId attribute of the MaresaClientList object
   *
   *@param  tmp  The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the siteId attribute of the MaresaClientList object
   *
   *@return    The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   *  Sets the siteClient attribute of the MaresaClientList object
   *
   *@param  siteClient  The new siteClient value
   */
  public void setSiteClient(String siteClient) {
    this.siteClient = siteClient;
  }


  /**
   *  Gets the siteClient attribute of the MaresaClientList object
   *
   *@return    The siteClient value
   */
  public String getSiteClient() {
    return siteClient;
  }



  /**
   *  Gets the PagedListInfo attribute of the CallList object
   *
   *@return    The PagedListInfo value
   *@since
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the segmentId attribute of the MaresaClientList object
   *
   *@return    The segmentId value
   
  public int getSegmentId() {
    return segmentId;
  }
*/
  /**
   *  Sets the segmetnId attribute of the Segment object
   *
   *@param  tmp  The new segmetnId value
   
  public void setSegmentId(String tmp) {
    this.segmentId = Integer.parseInt(tmp);
  }
*/

  /**
   *  Sets the defaultKey attribute of the RevenueTypeList object
   *
   *@param  tmp  The new defaultKey value
   */
  public void setDefaultKey(int tmp) {
    this.defaultKey = tmp;
  }


  /**
   *  Sets the defaultKey attribute of the RevenueTypeList object
   *
   *@param  tmp  The new defaultKey value
   */
  public void setDefaultKey(String tmp) {
    this.defaultKey = Integer.parseInt(tmp);
  }



  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
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
        "SELECT COUNT(*) as recordcount " +
        "FROM lookup_site_id lsi, maresa_client mc  " +
        "WHERE code > -1 and lsi.code = mc.site_id ");
    createFilter(db,sqlFilter);
    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
      //pst = db.prepareStatement(sqlCount.toString());
      items = prepareFilter(db,pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();
      //Determine column to sort by
      pagedListInfo.setDefaultSort("level", "code");
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY code DESC ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "maresa_id, client_id, site_id " +
        "FROM lookup_site_id, maresa_client " +
        "WHERE code > -1 and lookup_site_id.code = maresa_client.site_id ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(db,pst);
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, pst);
    }
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

      //DPM
      MaresaClient thisMaresaClient = new MaresaClient(rs);
      this.add(thisMaresaClient);
      ++count;
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
   */
  protected void createFilter(Connection db,StringBuffer sqlFilter) throws SQLException {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    Contact newContact = null;
    newContact = new Contact(db, contactId);
    orgId = newContact.getOrgId();
    if (orgId > -1) {
      sqlFilter.append("AND org_id = ? ");
    }
    if (contactId > -1) {
      sqlFilter.append("AND contact_id = ? ");
    }
    //sqlFilter.append("AND code = ? ");
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  protected int prepareFilter(Connection db,PreparedStatement pst) throws SQLException {
    int i = 0;
    int j = 1;
    if (orgId > -1) {
      pst.setInt(++i, orgId);
    }
    if (contactId > -1) {
      pst.setInt(++i, contactId);
    }
    return i;
  }


  /**
   *  Gets the htmlSelect attribute of the RevenueTypeList object
   *
   *@param  selectName  Description of the Parameter
   *@return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, defaultKey);
  }


  /**
   *  Gets the htmlSelect attribute of the RevenueTypeList object
   *
   *@param  selectName  Description of the Parameter
   *@param  defaultKey  Description of the Parameter
   *@return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    LookupList maresaClientSelect = new LookupList();
    maresaClientSelect = getLookupList(selectName, defaultKey);
    return maresaClientSelect.getHtmlSelect(selectName, defaultKey);
  }


  /**
   *  Gets the lookupList attribute of the RevenueTypeList object
   *
   *@param  selectName  Description of the Parameter
   *@param  defaultKey  Description of the Parameter
   *@return             The lookupList value
   */
  public LookupList getLookupList(String selectName, int defaultKey) {
    LookupList maresaClientSelect = new LookupList();
    /*
     *  segmentSelect.setJsEvent(jsEvent);
     *  segmentSelect.setSelectSize(this.getSize());
     *  segmentSelect.setMultiple(this.getMultiple());
     */
     
    Iterator i = this.iterator();
    
    while (i.hasNext()) {
      MaresaClient thisMaresaClient = (MaresaClient) i.next();
      
      maresaClientSelect.appendItem(thisMaresaClient.getSiteId(), thisMaresaClient.getSiteClient());
    }

    return maresaClientSelect;
  }
  
    

}


