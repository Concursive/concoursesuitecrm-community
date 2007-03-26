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

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Vector;

/**
 * Contains a list of phone numbers... currently used to build the list from
 * the database with any of the parameters to limit the results.
 *
 * @author mrajkowski
 * @version $Id: PhoneNumberList.java,v 1.2 2001/09/04 15:06:16 mrajkowski Exp
 *          $
 * @created August 31, 2001
 */
public class PhoneNumberList extends Vector {

  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  protected PagedListInfo pagedListInfo = null;
  protected int orgId = -1;
  protected int type = -1;
  protected int contactId = -1;
  protected String number = null;
  protected String extension = null;
  protected boolean usersOnly = false;

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
   * Sets the PagedListInfo attribute of the AddressList object
   *
   * @param tmp The new PagedListInfo value
   * @since 1.1
   */
  protected void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }

  /**
   * Sets the OrgId attribute of the AddressList object
   *
   * @param tmp The new OrgId value
   * @since 1.1
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
   * Sets the ContactId attribute of the PhoneNumberList object
   *
   * @param tmp The new ContactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Sets the Type attribute of the PhoneNumberList object
   *
   * @param tmp The new Type value
   */
  protected void setType(int tmp) {
    this.type = tmp;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public void setUsersOnly(boolean usersOnly) {
    this.usersOnly = usersOnly;
  }

  /**
   * Gets the PhoneNumber attribute of the PhoneNumberList object
   *
   * @param thisType Description of Parameter
   * @return The PhoneNumber value
   */
  public String getPhoneNumber(String thisType) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      PhoneNumber thisNumber = (PhoneNumber) i.next();
      if (thisType.equals(thisNumber.getTypeName())) {
        return thisNumber.getPhoneNumber();
      }
    }
    return "";
  }


  /**
   * Gets the phoneNumber attribute of the PhoneNumberList object
   *
   * @param thisType Description of the Parameter
   * @return The phoneNumber value
   */
  public String getPhoneNumber(int thisType) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      PhoneNumber thisNumber = (PhoneNumber) i.next();
      if (thisType == thisNumber.getType()) {
        return thisNumber.getPhoneNumber();
      }
    }
    return "";
  }


  /**
   * Gets the extension attribute of the PhoneNumberList object
   *
   * @param tmpNumber Description of the Parameter
   * @return The extension value
   */
  public String getExtension(String tmpNumber) {
    Iterator i = this.iterator();
    System.out.println("tmpNumber " + tmpNumber);
    while (i.hasNext()) {
      PhoneNumber thisNumber = (PhoneNumber) i.next();
      if (tmpNumber.equals(thisNumber.getPhoneNumber())) {
        return thisNumber.getExtension();
      }
    }
    return "";
  }


  /**
   * Returns the phone number that is marked as primary or
   * returns the only (or last) phone number in the list.
   *
   * @return The primaryPhoneNumber value
   */
  public String getPrimaryPhoneNumber() {
    Iterator i = this.iterator();
    PhoneNumber thisNumber = null;
    while (i.hasNext()) {
      thisNumber = (PhoneNumber) i.next();
      if (thisNumber.getPrimaryNumber()) {
        break;
      }
    }
    return ((thisNumber == null) ? "" : thisNumber.getPhoneNumber());
  }


  /**
   * Builds a base SQL where statement for filtering records to be used by
   * sqlSelect and sqlCount
   *
   * @param sqlFilter Description of Parameter
   * @since 1.1
   */
  protected void createFilter(Connection db, StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (orgId != -1) {
      sqlFilter.append("AND org_id = ? ");
    }
    if (type != -1) {
      sqlFilter.append("AND phone_type = ? ");
    }
    if (contactId != -1) {
      sqlFilter.append("AND contact_id = ? ");
    }
    if (number != null) {
      sqlFilter.append("AND number = ? ");
    }
    if (extension != null) {
      sqlFilter.append("AND extension = ? ");
    }
    if (usersOnly) {
      sqlFilter.append("AND contact_id IN (SELECT contact_id FROM " + DatabaseUtils.addQuotes(db, "access") + " WHERE enabled = ? AND (expires IS NULL OR expires < CURRENT_TIMESTAMP)) ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND p.entered > ? ");
      }
      sqlFilter.append("AND p.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND p.modified > ? ");
      sqlFilter.append("AND p.entered < ? ");
      sqlFilter.append("AND p.modified < ? ");
    }
  }


  /**
   * Sets the parameters for the preparedStatement - these items must
   * correspond with the createFilter statement
   *
   * @param pst Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   * @since 1.1
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
    if (number != null) {
      pst.setString(++i, number);
    }
    if (extension != null) {
      pst.setString(++i, extension);
    }
    if (usersOnly) {
      pst.setBoolean(++i, true);
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

