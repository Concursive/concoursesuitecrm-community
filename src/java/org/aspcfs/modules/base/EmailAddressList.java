//Copyright 2001 Dark Horse Ventures
// The createFilter method and the prepareFilter method need to have the same
// number of parameters if modified.

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.PagedListInfo;

/**
 *  Contains a list of email addresses... currently used to build the list from
 *  the database with any of the parameters to limit the results.
 *
 *@author     mrajkowski
 *@created    September 6, 2001
 *@version    $Id: EmailAddressList.java,v 1.1 2001/09/07 13:59:07 mrajkowski
 *      Exp $
 */
public class EmailAddressList extends Vector {

  protected PagedListInfo pagedListInfo = null;
  protected int orgId = -1;
  protected int type = -1;
  protected int contactId = -1;


  /**
   *  Sets the PagedListInfo attribute of the EmailAddressList object
   *
   *@param  tmp  The new PagedListInfo value
   *@since       1.1
   */
  protected void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the OrgId attribute of the EmailAddressList object
   *
   *@param  tmp  The new OrgId value
   *@since       1.1
   */
  protected void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   *  Sets the ContactId attribute of the EmailAddressList object
   *
   *@param  tmp  The new ContactId value
   *@since       1.1
   */
  protected void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   *  Sets the Type attribute of the EmailAddressList object
   *
   *@param  tmp  The new Type value
   *@since       1.1
   */
  protected void setType(int tmp) {
    this.type = tmp;
  }


  /**
   *  Gets the EmailAddress attribute of the EmailAddressList object
   *
   *@param  thisType  Description of Parameter
   *@return           The EmailAddress value
   *@since            1.1
   */
  protected String getEmailAddress(String thisType) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      EmailAddress thisAddress = (EmailAddress)i.next();
      if (thisType.equals(thisAddress.getTypeName())) {
        return thisAddress.getEmail();
      }
    }
    return "";
  }


  /**
   *  Gets the EmailAddress attribute of the EmailAddressList object
   *
   *@param  thisItem  Description of Parameter
   *@return           The EmailAddress value
   *@since            1.2
   */
  protected String getEmailAddress(int thisItem) {
    if (thisItem - 1 > -1 && thisItem <= this.size()) {
      EmailAddress thisAddress = (EmailAddress)this.get(thisItem - 1);
      return thisAddress.getEmail();
    }
    return "";
  }


  /**
   *  Gets the EmailAddressType attribute of the EmailAddressList object
   *
   *@param  thisItem  Description of Parameter
   *@return           The EmailAddressType value
   *@since            1.2
   */
  protected int getEmailAddressTypeId(int thisItem) {
    if (thisItem - 1 > -1 && thisItem <= this.size()) {
      EmailAddress thisAddress = (EmailAddress)this.get(thisItem - 1);
      return thisAddress.getType();
    }
    return -1;
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
      sqlFilter.append("AND emailaddress_type = ? ");
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

