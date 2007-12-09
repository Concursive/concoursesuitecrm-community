/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
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
 * Contains a list of email addresses... currently used to build the list from
 * the database with any of the parameters to limit the results.
 *
 * @author mrajkowski
 * @version $Id: EmailAddressList.java,v 1.1 2001/09/07 13:59:07 mrajkowski
 *          Exp $
 * @created September 6, 2001
 */
public class EmailAddressList extends Vector {

  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  protected PagedListInfo pagedListInfo = null;
  protected int orgId = -1;
  protected int type = -1;
  protected int contactId = -1;
  protected String username = null;

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
   * Sets the PagedListInfo attribute of the EmailAddressList object
   *
   * @param tmp The new PagedListInfo value
   * @since 1.1
   */
  protected void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the OrgId attribute of the EmailAddressList object
   *
   * @param tmp The new OrgId value
   * @since 1.1
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   * Sets the ContactId attribute of the EmailAddressList object
   *
   * @param tmp The new ContactId value
   * @since 1.1
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Sets the Type attribute of the EmailAddressList object
   *
   * @param tmp The new Type value
   * @since 1.1
   */
  public void setType(int tmp) {
    this.type = tmp;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Gets the EmailAddress attribute of the EmailAddressList object
   *
   * @param thisType Description of Parameter
   * @return The EmailAddress value
   * @since 1.1
   */
  public String getEmailAddress(String thisType) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      EmailAddress thisAddress = (EmailAddress) i.next();
      if (thisType.equals(thisAddress.getTypeName())) {
        return thisAddress.getEmail();
      }
    }
    return "";
  }


  /**
   * Gets the EmailAddress attribute of the EmailAddressList object
   *
   * @param thisItem Description of Parameter
   * @return The EmailAddress value
   * @since 1.2
   */
  public String getEmailAddress(int thisItem) {
    if (thisItem - 1 > -1 && thisItem <= this.size()) {
      EmailAddress thisAddress = (EmailAddress) this.get(thisItem - 1);
      return thisAddress.getEmail();
    }
    return "";
  }


  /**
   * Returns the email address that is marked as primary or
   * returns the only (or last) email address in the list.
   *
   * @return The primaryEmailAddress value
   */
  public String getPrimaryEmailAddress() {
    Iterator i = this.iterator();
    EmailAddress thisAddress = null;
    while (i.hasNext()) {
      thisAddress = (EmailAddress) i.next();
      if (thisAddress.getPrimaryEmail()) {
        break;
      }
    }

    return ((thisAddress == null) ? "" : thisAddress.getEmail());
  }


  /**
   * Gets the EmailAddressType attribute of the EmailAddressList object
   *
   * @param thisItem Description of Parameter
   * @return The EmailAddressType value
   * @since 1.2
   */
  public int getEmailAddressTypeId(int thisItem) {
    if (thisItem - 1 > -1 && thisItem <= this.size()) {
      EmailAddress thisAddress = (EmailAddress) this.get(thisItem - 1);
      return thisAddress.getType();
    }
    return -1;
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
      sqlFilter.append("AND emailaddress_type = ? ");
    }
    if (contactId != -1) {
      sqlFilter.append("AND contact_id = ? ");
    }
    if (username != null) {
      sqlFilter.append("AND contact_id IN (SELECT contact_id FROM " + DatabaseUtils.addQuotes(db, "access") + " WHERE lower(username) = ?) ");
    }

    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND e.entered > ? ");
      }
      sqlFilter.append("AND e.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND e.modified > ? ");
      sqlFilter.append("AND e.entered < ? ");
      sqlFilter.append("AND e.modified < ? ");
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
    if (username != null) {
      pst.setString(++i, username.toLowerCase());
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
   * Gets the concatenatedList attribute of the EmailAddressList object
   *
   * @return The concatenatedList value
   */
  public String getConcatenatedList() {
    StringBuffer result = new StringBuffer("");
    Iterator iterator = (Iterator) this.iterator();
    for (int i = 0; iterator.hasNext(); i++) {
      EmailAddress email = (EmailAddress) iterator.next();
      if (i > 0) {
        result.append(", ");
      }
      result.append(email.getEmail());
    }
    return result.toString();
  }
}

