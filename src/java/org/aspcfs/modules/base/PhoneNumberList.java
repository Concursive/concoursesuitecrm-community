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

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import org.aspcfs.utils.web.PagedListInfo;

/**
 *  Contains a list of phone numbers... currently used to build the list from
 *  the database with any of the parameters to limit the results.
 *
 *@author     mrajkowski
 *@created    August 31, 2001
 *@version    $Id: PhoneNumberList.java,v 1.2 2001/09/04 15:06:16 mrajkowski Exp
 *      $
 */
public class PhoneNumberList extends Vector {

  protected PagedListInfo pagedListInfo = null;
  protected int orgId = -1;
  protected int type = -1;
  protected int contactId = -1;


  /**
   *  Sets the PagedListInfo attribute of the AddressList object
   *
   *@param  tmp  The new PagedListInfo value
   *@since       1.1
   */
  protected void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the OrgId attribute of the AddressList object
   *
   *@param  tmp  The new OrgId value
   *@since       1.1
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   *  Sets the ContactId attribute of the PhoneNumberList object
   *
   *@param  tmp  The new ContactId value
   *@since
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   *  Sets the Type attribute of the PhoneNumberList object
   *
   *@param  tmp  The new Type value
   *@since
   */
  protected void setType(int tmp) {
    this.type = tmp;
  }


  /**
   *  Gets the PhoneNumber attribute of the PhoneNumberList object
   *
   *@param  thisType  Description of Parameter
   *@return           The PhoneNumber value
   *@since
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
   *  Gets the phoneNumber attribute of the PhoneNumberList object
   *
   *@param  thisType  Description of the Parameter
   *@return           The phoneNumber value
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
   *  Gets the extension attribute of the PhoneNumberList object
   *
   *@param  tmpNumber  Description of the Parameter
   *@return            The extension value
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
   *  Returns the phone number that is marked as primary or 
   * returns the only (or last) phone number in the list.
   *
   *@return    The primaryPhoneNumber value
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
    return ((thisNumber == null)? "": thisNumber.getPhoneNumber());
  }


  /**
   *  Builds a base SQL where statement for filtering records to be used by
   *  sqlSelect and sqlCount
   *
   *@param  sqlFilter  Description of Parameter
   *@since             1.1
   */
  protected void createFilter(StringBuffer sqlFilter) {
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
  }


  /**
   *  Sets the parameters for the preparedStatement - these items must
   *  correspond with the createFilter statement
   *
   *@param  pst               Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.1
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

