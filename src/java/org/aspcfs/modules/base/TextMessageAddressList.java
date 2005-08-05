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
 * Contains a list of text message addresses... currently used to build the
 * list from the database with any of the parameters to limit the results.
 *
 * @author kailash
 * @version $Id: TextMessageAddressList.java,v 1.1 2001/09/07 13:59:07
 *          mrajkowski Exp $
 * @created January 11, 2005
 */
public class TextMessageAddressList extends Vector {

  protected PagedListInfo pagedListInfo = null;
  protected int orgId = -1;
  protected int type = -1;
  protected int contactId = -1;


  /**
   * Sets the PagedListInfo attribute of the TextMessageAddressList object
   *
   * @param tmp The new PagedListInfo value
   * @since 1.1
   */
  protected void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the orgId attribute of the TextMessageAddressList object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   * Sets the orgId attribute of the TextMessageAddressList object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   * Sets the type attribute of the TextMessageAddressList object
   *
   * @param tmp The new type value
   */
  public void setType(int tmp) {
    this.type = tmp;
  }


  /**
   * Sets the type attribute of the TextMessageAddressList object
   *
   * @param tmp The new type value
   */
  public void setType(String tmp) {
    this.type = Integer.parseInt(tmp);
  }


  /**
   * Sets the contactId attribute of the TextMessageAddressList object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Sets the contactId attribute of the TextMessageAddressList object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   * Gets the TextMessageAddress attribute of the TextMessageAddressList object
   *
   * @param thisType Description of Parameter
   * @return The EmailAddress value
   * @since 1.1
   */
  public String getTextMessageAddress(String thisType) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      TextMessageAddress thisAddress = (TextMessageAddress) i.next();
      if (thisType.equals(thisAddress.getTypeName())) {
        return thisAddress.getTextMessageAddress();
      }
    }
    return "";
  }


  /**
   * Gets the TextMessageAddress attribute of the TextMessageAddressList object
   *
   * @param thisItem Description of Parameter
   * @return The EmailAddress value
   * @since 1.2
   */
  public String getTextMessageAddress(int thisItem) {
    if (thisItem - 1 > -1 && thisItem <= this.size()) {
      TextMessageAddress thisAddress = (TextMessageAddress) this.get(
          thisItem - 1);
      return thisAddress.getTextMessageAddress();
    }
    return "";
  }


  /**
   * Gets the TextMessageAddressTypeId attribute of the TextMessageAddressList
   * object
   *
   * @param thisItem Description of Parameter
   * @return The EmailAddressType value
   * @since 1.2
   */
  public int getTextMessageAddressTypeId(int thisItem) {
    if (thisItem - 1 > -1 && thisItem <= this.size()) {
      TextMessageAddress thisAddress = (TextMessageAddress) this.get(
          thisItem - 1);
      return thisAddress.getType();
    }
    return -1;
  }


  /**
   * Returns the text message address that is marked as primary or
   * returns the only (or last) text message  address in the list.
   *
   * @return The primaryTextMessageAddress value
   */
  public String getPrimaryTextMessageAddress() {
    Iterator i = this.iterator();
    TextMessageAddress thisAddress = null;
    while (i.hasNext()) {
      thisAddress = (TextMessageAddress) i.next();
      if (thisAddress.getPrimaryTextMessageAddress()) {
        break;
      }
    }
    return ((thisAddress == null) ? "" : thisAddress.getTextMessageAddress());
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
      sqlFilter.append("AND textmessageaddress_type = ? ");
    }

    if (contactId != -1) {
      sqlFilter.append("AND contact_id = ? ");
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

    return i;
  }

}

