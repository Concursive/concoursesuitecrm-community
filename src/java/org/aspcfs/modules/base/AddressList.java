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
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Vector;
import java.util.HashMap;

/**
 *  Contains a list of addresses... currently used to build the list from the
 *  database with any of the parameters to limit the results. This is a base
 *  class that should not be called directly -- use ContactAddressList or
 *  OrganizationAddressList to define the database query.
 *
 * @author     mrajkowski
 * @created    August 31, 2001
 * @version    $Id: AddressList.java 12404 2005-08-05 13:37:07 -0400 (Fri, 05
 *      Aug 2005) mrajkowski $
 */
public class AddressList extends Vector{

  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  protected PagedListInfo pagedListInfo = null;
  protected int orgId = -1;
  protected int type = -1;
  protected int contactId = -1;
  protected int orderId = -1;

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setLastAnchor(java.sql.Timestamp)
   */
  public void setLastAnchor(Timestamp lastAnchor) {
    this.lastAnchor = lastAnchor;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setLastAnchor(java.lang.String)
   */
  public void setLastAnchor(String lastAnchor) {
    this.lastAnchor = java.sql.Timestamp.valueOf(lastAnchor);
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setNextAnchor(java.sql.Timestamp)
   */
  public void setNextAnchor(Timestamp nextAnchor) {
    this.nextAnchor = nextAnchor;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setNextAnchor(java.lang.String)
   */
  public void setNextAnchor(String nextAnchor) {
    this.nextAnchor = java.sql.Timestamp.valueOf(nextAnchor);
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setSyncType(int)
   */
  public void setSyncType(int syncType) {
    this.syncType = syncType;
  }
  
  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setSyncType(String)
   */
  public void setSyncType(String syncType) {
    this.syncType = Integer.parseInt(syncType);
  }

  /**
   *  Sets the PagedListInfo attribute of the AddressList object
   *
   * @param  tmp  The new PagedListInfo value
   * @since       1.1
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the OrgId attribute of the AddressList object
   *
   * @param  tmp  The new OrgId value
   * @since       1.1
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }

  /**
   *  Sets the OrgId attribute of the AddressList object
   *
   * @param  tmp  The new OrgId value
   * @since       1.1
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }

  /**
   *  Sets the contactId attribute of the AddressList object
   *
   * @param  tmp  The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   *  Sets the orderId attribute of the AddressList object
   *
   * @param  tmp  The new orderId value
   */
  public void setOrderId(int tmp) {
    this.orderId = tmp;
  }


  /**
   *  Sets the Type attribute of the AddressList object
   *
   * @param  tmp  The new Type value
   * @since       1.1
   */
  public void setType(int tmp) {
    this.type = tmp;
  }


  /**
   *  Gets the address attribute of the AddressList object
   *
   * @param  thisType  Description of the Parameter
   * @return           The address value
   */
  public Address getAddress(String thisType) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Address thisAddress = (Address) i.next();
      if (thisType.equals(thisAddress.getTypeName())) {
        return thisAddress;
      }
    }
    return new Address();
  }


  /**
   *  Returns the address that is marked as primary or returns the only (or
   *  last) address in the list.
   *
   * @return    The primaryAddress value
   */
  public Address getPrimaryAddress() {
    Iterator i = this.iterator();
    Address thisAddress = null;
    while (i.hasNext()) {
      thisAddress = (Address) i.next();
      if (thisAddress.getPrimaryAddress()) {
        break;
      }
    }
    return thisAddress;
  }


  /**
   *  Builds a base SQL where statement for filtering records to be used by
   *  sqlSelect and sqlCount
   *
   * @param  sqlFilter  Description of Parameter
   * @since             1.1
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (orgId != -1) {
      sqlFilter.append("AND org_id = ? ");
    }
    if (type != -1) {
      sqlFilter.append("AND address_type = ? ");
    }
    if (contactId != -1) {
      sqlFilter.append("AND contact_id = ? ");
    }
    if (orderId != -1) {
      sqlFilter.append("AND order_id = ? ");
    }

    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND a.entered > ? ");
      }
      sqlFilter.append("AND a.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND a.modified > ? ");
      sqlFilter.append("AND a.entered < ? ");
      sqlFilter.append("AND a.modified < ? ");
    }
  }


  /**
   *  Sets the parameters for the preparedStatement - these items must
   *  correspond with the createFilter statement
   *
   * @param  pst            Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   * @since                 1.1
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (orgId != -1) {
      pst.setInt(++i, orgId);
    }
    if (type != -1) {
      pst.setInt(++i, type);
    }
    if (contactId != -1) {
      pst.setInt(++i, contactId);
    }
    if (orderId != -1) {
      pst.setInt(++i, orderId);
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
   *  Gets the countries attribute of the AddressList object
   *
   * @return    The countries value
   */
  public String getCountries() {
    StringBuffer result = new StringBuffer();
    Iterator iter = this.iterator();
    while (iter.hasNext()) {
      Address thisAddress = (Address) iter.next();
      if (thisAddress.getCountry() != null) {
        result.append(thisAddress.getCountry() + (iter.hasNext()?",":""));
      }
    }
    return result.toString();
  }
  
  public HashMap getSelectedStatesHashMap() {
    HashMap map = new HashMap();
    Iterator iter = this.iterator();
    while (iter.hasNext()) {
      Address thisAddress = (Address) iter.next();
      if (thisAddress.getCountry() != null) {
        map.put(thisAddress.getCountry(), thisAddress.getState());
      }
    }
    return map;
  }
}

