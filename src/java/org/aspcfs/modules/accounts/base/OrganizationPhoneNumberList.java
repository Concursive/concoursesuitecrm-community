//Copyright 2001 Dark Horse Ventures

package org.aspcfs.modules.accounts.base;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.base.EmailAddressList;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.accounts.base.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *  Contains a list of phone numbers... currently used to build the list from
 *  the database with any of the parameters to limit the results.
 *
 *@author     mrajkowski
 *@created    September 4, 2001
 *@version    $Id: OrganizationPhoneNumberList.java,v 1.3 2002/09/11 15:29:43
 *      chris Exp $
 */
public class OrganizationPhoneNumberList extends PhoneNumberList {

  public final static String tableName = "organization_phone";
  public final static String uniqueField = "phone_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;


  /**
   *  Constructor for the OrganizationPhoneNumberList object
   *
   *@since    1.1
   */
  public OrganizationPhoneNumberList() { }


  /**
   *  Constructor for the OrganizationPhoneNumberList object
   *
   *@param  request  Description of the Parameter
   */
  public OrganizationPhoneNumberList(HttpServletRequest request) {
    int i = 0;
    while (request.getParameter("phone" + (++i) + "type") != null) {
      OrganizationPhoneNumber thisPhoneNumber = new OrganizationPhoneNumber();
      thisPhoneNumber.buildRecord(request, i);
      if (thisPhoneNumber.isValid()) {
        this.addElement(thisPhoneNumber);
      }
    }
  }


  /**
   *  Sets the lastAnchor attribute of the OrganizationPhoneNumberList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the OrganizationPhoneNumberList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the syncType attribute of the OrganizationPhoneNumberList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   *  Gets the tableName attribute of the OrganizationPhoneNumberList object
   *
   *@return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the uniqueField attribute of the OrganizationPhoneNumberList object
   *
   *@return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Gets the lastAnchor attribute of the OrganizationPhoneNumberList object
   *
   *@return    The lastAnchor value
   */
  public java.sql.Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   *  Gets the nextAnchor attribute of the OrganizationPhoneNumberList object
   *
   *@return    The nextAnchor value
   */
  public java.sql.Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   *  Gets the syncType attribute of the OrganizationPhoneNumberList object
   *
   *@return    The syncType value
   */
  public int getSyncType() {
    return syncType;
  }


  /**
   *  Gets the htmlSelect attribute of the OrganizationPhoneNumberList object
   *
   *@param  selectName  Description of the Parameter
   *@param  defaultKey  Description of the Parameter
   *@return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect phoneListSelect = new HtmlSelect();

    Iterator i = this.iterator();
    while (i.hasNext()) {
      PhoneNumber thisNumber = (PhoneNumber) i.next();
      String elementText = null;

      elementText = String.valueOf(thisNumber.getTypeName().charAt(0)) + ":";
      elementText += thisNumber.getNumber();
      phoneListSelect.addItem(
          thisNumber.getId(),
          elementText);
    }
    return phoneListSelect.getHtml(selectName, defaultKey);
  }


  /**
   *  Builds a list of addresses based on several parameters. The parameters are
   *  set after this object is constructed, then the buildList method is called
   *  to generate the list.
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public void buildList(Connection db) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for returning records
    sqlSelect.append("SELECT * " +
        "FROM organization_phone p, lookup_orgphone_types l " +
        "WHERE p.phone_type = l.code ");

    //Need to build a base SQL statement for counting records
    sqlCount.append("SELECT COUNT(*) AS recordcount " +
        "FROM organization_phone p, lookup_orgphone_types l " +
        "WHERE p.phone_type = l.code ");

    createFilter(sqlFilter);

    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() +
          sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();

      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString() +
            sqlFilter.toString() +
            "AND phone_type < ? ");
        items = prepareFilter(pst);
        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }

      //Determine column to sort by
      if (pagedListInfo.getColumnToSortBy() != null && !pagedListInfo.getColumnToSortBy().equals("")) {
        sqlOrder.append("ORDER BY " + pagedListInfo.getColumnToSortBy() + ", phone_type ");
        if (pagedListInfo.getSortOrder() != null && !pagedListInfo.getSortOrder().equals("")) {
          sqlOrder.append(pagedListInfo.getSortOrder() + " ");
        }
      } else {
        sqlOrder.append("ORDER BY phone_type ");
      }

      //Determine items per page
      if (pagedListInfo.getItemsPerPage() > 0) {
        sqlOrder.append("LIMIT " + pagedListInfo.getItemsPerPage() + " ");
      }

      sqlOrder.append("OFFSET " + pagedListInfo.getCurrentOffset() + " ");
    }

    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      OrganizationPhoneNumber thisPhoneNumber = new OrganizationPhoneNumber(rs);
      this.addElement(thisPhoneNumber);
    }
    rs.close();
    pst.close();
  }

}

