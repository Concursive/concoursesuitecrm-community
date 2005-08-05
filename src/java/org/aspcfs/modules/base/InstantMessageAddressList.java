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
  protected PagedListInfo pagedListInfo = null;
  protected int type = -1;
  protected int service = -1;
  protected int contactId = -1;


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
    Iterator i = this.iterator();
    while (i.hasNext()) {
      InstantMessageAddress thisAddress = (InstantMessageAddress) i.next();
      if (thisAddress.getPrimaryIM()) {
        return thisAddress.getAddressIM();
      }
    }
    return "";
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

    return i;
  }
}

