//Copyright 2001 Dark Horse Ventures
// The createFilter method and the prepareFilter method need to have the same
// number of parameters if modified.

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.PagedListInfo;

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
  protected String getPhoneNumber(String thisType) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      PhoneNumber thisNumber = (PhoneNumber)i.next();
      if (thisType.equals(thisNumber.getTypeName())) {
        return thisNumber.getPhoneNumber();
      }
    }
    return "";
  }

  
  protected String getPhoneNumber(int thisType) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      PhoneNumber thisNumber = (PhoneNumber)i.next();
      if (thisType == thisNumber.getType()) {
        return thisNumber.getPhoneNumber();
      }
    }
    return "";
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

