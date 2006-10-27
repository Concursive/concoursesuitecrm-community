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
package org.aspcfs.modules.base;

import org.aspcfs.utils.web.PagedListInfo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

/**
 * Description of the Class
 *
 * @author Ananth
 * @created April 21, 2005
 */
public class InstantMessageAddressList extends Vector {
  public final static String tableName = "action_item_log";
  public final static String uniqueField = "log_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  protected PagedListInfo pagedListInfo = null;
  protected int type = -1;
  protected int service = -1;
  protected int contactId = -1;
  protected String serviceName = null;


  /**
   * Sets the lastAnchor attribute of the ActionItemList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the ActionItemList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the ActionItemList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the ActionItemList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the ActionItemList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }

  /**
   * Gets the tableName attribute of the ActionItemList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the ActionItemList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Gets the pagedListInfo attribute of the InstantMessageAddressList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Sets the pagedListInfo attribute of the InstantMessageAddressList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Gets the type attribute of the InstantMessageAddressList object
   *
   * @return The type value
   */
  public int getType() {
    return type;
  }


  /**
   * Sets the type attribute of the InstantMessageAddressList object
   *
   * @param tmp The new type value
   */
  public void setType(int tmp) {
    this.type = tmp;
  }


  /**
   * Sets the type attribute of the InstantMessageAddressList object
   *
   * @param tmp The new type value
   */
  public void setType(String tmp) {
    this.type = Integer.parseInt(tmp);
  }


  /**
   * Gets the service attribute of the InstantMessageAddressList object
   *
   * @return The service value
   */
  public int getService() {
    return service;
  }


  /**
   * Sets the service attribute of the InstantMessageAddressList object
   *
   * @param tmp The new service value
   */
  public void setService(int tmp) {
    this.service = tmp;
  }


  /**
   * Sets the service attribute of the InstantMessageAddressList object
   *
   * @param tmp The new service value
   */
  public void setService(String tmp) {
    this.service = Integer.parseInt(tmp);
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  /**
   * Gets the contactId attribute of the InstantMessageAddressList object
   *
   * @return The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   * Sets the contactId attribute of the InstantMessageAddressList object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Sets the contactId attribute of the InstantMessageAddressList object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   * Gets the instantMessageAddressTypeId attribute of the
   * InstantMessageAddressList object
   *
   * @param thisItem Description of the Parameter
   * @return The instantMessageAddressTypeId value
   */
  public int getInstantMessageAddressTypeId(int thisItem) {
    if (thisItem - 1 > -1 && thisItem <= this.size()) {
      InstantMessageAddress thisAddress = (InstantMessageAddress) this.get(
          thisItem - 1);
      return thisAddress.getAddressIMType();
    }
    return -1;
  }


  /**
   * Gets the instantMessageAddressServiceId attribute of the
   * InstantMessageAddressList object
   *
   * @param thisItem Description of the Parameter
   * @return The instantMessageAddressServiceId value
   */
  public int getInstantMessageAddressServiceId(int thisItem) {
    if (thisItem - 1 > -1 && thisItem <= this.size()) {
      InstantMessageAddress thisAddress = (InstantMessageAddress) this.get(
          thisItem - 1);
      return thisAddress.getAddressIMService();
    }
    return -1;
  }


  /**
   * Gets the iMAddressWithType attribute of the InstantMessageAddressList
   * object
   *
   * @param thisType Description of the Parameter
   * @return The iMAddressWithType value
   */
  public String getIMAddressWithType(String thisType) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      InstantMessageAddress thisAddress = (InstantMessageAddress) i.next();
      if (thisType.equals(thisAddress.getAddressIMTypeName())) {
        return thisAddress.getAddressIM();
      }
    }
    return "";
  }


  /**
   * Gets the primaryInstantMessageAddress attribute of the
   * InstantMessageAddressList object
   *
   * @return The primaryInstantMessageAddress value
   */
  public String getPrimaryInstantMessageAddress() {
    InstantMessageAddress thisAddress = null;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      thisAddress = (InstantMessageAddress) i.next();
      if (thisAddress.getPrimaryIM()) {
        return thisAddress.getAddressIM();
      }
    }
    return ((thisAddress == null) ? "" : thisAddress.getAddressIM());
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (type != -1) {
      sqlFilter.append("AND imaddress_type = ? ");
    }

    if (service != -1) {
      sqlFilter.append("AND imaddress_service = ? ");
    }

    if (contactId != -1) {
      sqlFilter.append("AND contact_id = ? ");
    }

    if (serviceName != null) {
      sqlFilter.append("AND lims.description = ? ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND entered > ? ");
      }
      sqlFilter.append("AND entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND modified > ? ");
      sqlFilter.append("AND entered < ? ");
      sqlFilter.append("AND modified < ? ");
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

    if (type != -1) {
      pst.setInt(++i, type);
    }

    if (service != -1) {
      pst.setInt(++i, service);
    }

    if (contactId != -1) {
      pst.setInt(++i, contactId);
    }

    if (serviceName != null) {
      pst.setString(++i, serviceName);
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
}

