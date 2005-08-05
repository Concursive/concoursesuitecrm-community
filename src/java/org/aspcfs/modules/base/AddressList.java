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
 * Contains a list of addresses... currently used to build the list from the
 * database with any of the parameters to limit the results. This is a base
 * class that should not be called directly -- use ContactAddressList or
 * OrganizationAddressList to define the database query.
 *
 * @author mrajkowski
 * @version $Id$
 * @created August 31, 2001
 */
public class AddressList extends Vector {

  protected PagedListInfo pagedListInfo = null;
  protected int orgId = -1;
  protected int type = -1;
  protected int contactId = -1;
  protected int orderId = -1;


  /**
   * Sets the PagedListInfo attribute of the AddressList object
   *
   * @param tmp The new PagedListInfo value
   * @since 1.1
   */
  public void setPagedListInfo(PagedListInfo tmp) {
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
   * Sets the contactId attribute of the AddressList object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Sets the orderId attribute of the AddressList object
   *
   * @param tmp The new orderId value
   */
  public void setOrderId(int tmp) {
    this.orderId = tmp;
  }


  /**
   * Sets the Type attribute of the AddressList object
   *
   * @param tmp The new Type value
   * @since 1.1
   */
  public void setType(int tmp) {
    this.type = tmp;
  }


  /**
   * Gets the address attribute of the AddressList object
   *
   * @param thisType Description of the Parameter
   * @return The address value
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
   * Returns the address that is marked as primary or
   * returns the only (or last) address in the list.
   *
   * @return The primaryAddress value
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
   * Builds a base SQL where statement for filtering records to be used by
   * sqlSelect and sqlCount
   *
   * @param sqlFilter Description of Parameter
   * @since 1.1
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

    if (orderId != -1) {
      pst.setInt(++i, orderId);
    }

    return i;
  }

}

