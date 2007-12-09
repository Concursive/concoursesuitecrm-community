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

package org.aspcfs.modules.industry.spirit.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.utils.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 *@author     chris
 *@created    July 12, 2001
 *@version    $Id$
 */
public class Maresa extends GenericBean {
  /**
   *  Description of the Field
   */
  private int maresaCifNo = -1;
  private int addressNo = 1;
  private ContactAddressList addressList = new ContactAddressList();
  private ContactPhoneNumberList phoneNumberList = new ContactPhoneNumberList();
  private ContactEmailAddressList emailAddressList = new ContactEmailAddressList();
  private String address1 = "";
  private String address2 = "";
  private String address3 = "";
  private String address4 = "";
  private String zipCode = "";
  private String city = "";
  private String state = "";
  private String phone = "";
  private String fax = "";
  private String email = "";
  private String url = "";
  private String idcsp = "001";
  private String country = "";
  private int segmentId = -1;
  private String segment = "";
  private int fcc = 0;
  private int dirBill = 0;
  private String tableName = null;
  private String orgName = "";
  private String title = "";
  private String nameFull = "";
  private int siteId = -1;


  /**
   *  Constructor for the Organization object, creates an empty Organization
   *
   *@since    1.0
   */
  public Maresa() { }


  /**
   *  Constructor for the Organization object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public Maresa(ResultSet rs) throws SQLException { }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  org_id            Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public Maresa(Connection db, int org_id) throws SQLException {
    if (org_id == -1) {
      throw new SQLException("Invalid Account");
    }
  }


  /**
   *  Sets the maresaCifNo attribute of the Maresa object
   *
   *@param  maresaCifNo  The new maresaCifNo value
   */
  public void setMaresaCifNo(int maresaCifNo) {
    this.maresaCifNo = maresaCifNo;
  }


  /**
   *  Gets the maresaCifNo attribute of the Maresa object
   *
   *@return    The maresaCifNo value
   */
  public int getMaresaCifNo() {
    return maresaCifNo;
  }


  /**
   *  Sets the siteId attribute of the Maresa object
   *
   *@param  siteId  The new siteId value
   */
  public void setSiteId(int siteId) {
    this.siteId = siteId;
  }


  /**
   *  Sets the siteId attribute of the Maresa object
   *
   *@param  tmp  The new siteId value
   */
  public void setSiteId(String tmp) {
    if (tmp != null) {
      this.siteId = Integer.parseInt(tmp);
    }
  }


  /**
   *  Gets the siteId attribute of the Maresa object
   *
   *@return    The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   *  Description of the Method Inserts Contact information iinto the fdidet
   *  table in our sales system - Maresa
   *
   *@param  SPIdb             Description of the Parameter
   *@param  CFSdb             Description of the Parameter
   *@param  maresaClient      Description of the Parameter
   *@param  contactId         Description of the Parameter
   *@param  orgId             Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection SPIdb, Connection CFSdb, int maresaClient, int contactId, int orgId) throws SQLException {

    if (!isValid(SPIdb)) {
      return false;
    }
    if (!isValid(CFSdb)) {
      return false;
    }

    Contact newContact = null;
    Organization newOrganization = null;
    newContact = new Contact(CFSdb, contactId);
    newOrganization = new Organization(CFSdb, orgId);

    int salutation = newContact.getListSalutation();
    tableName = "lookup_title";
    title = getLookupValue(CFSdb, salutation, tableName);
    title = StringUtils.trimToSizeNoDots(title, 3);

    int segmentId = newOrganization.getSegmentId();
    tableName = "lookup_segments";
    segment = getLookupValue(CFSdb, segmentId, tableName);
    segment = segment.substring(0, 3);
    orgName = newOrganization.getName();
    orgName = StringUtils.trimToSizeNoDots(orgName, 32);

    url = newOrganization.getUrl();
    url = StringUtils.trimToSizeNoDots(url, 255);

    nameFull = newContact.getNameFirst() + " " + newContact.getNameLast();
    nameFull = StringUtils.trimToSizeNoDots(nameFull, 32);
    if (newOrganization.getDirectBill()) {
      dirBill = 1;
    }

    StringBuffer sql = new StringBuffer();

    addressList.setContactId(contactId);
    addressList.buildList(CFSdb);
    Iterator iaddress = addressList.iterator();
    while (iaddress.hasNext()) {
      ContactAddress thisAddress = (ContactAddress) iaddress.next();
      if (thisAddress.getPrimaryAddress()) {
        address1 = StringUtils.toString(thisAddress.getStreetAddressLine1()).toUpperCase();
        address1 = StringUtils.trimToSizeNoDots(address1, 32);
        address2 = StringUtils.toString(thisAddress.getStreetAddressLine2()).toUpperCase();
        address2 = StringUtils.trimToSizeNoDots(address2, 32);
        address3 = StringUtils.toString(thisAddress.getStreetAddressLine3()).toUpperCase();
        address3 = StringUtils.trimToSizeNoDots(address3, 32);
        address4 = StringUtils.toString(thisAddress.getStreetAddressLine4()).toUpperCase();
        address4 = StringUtils.trimToSizeNoDots(address4, 32);
        zipCode = thisAddress.getZip();
        zipCode = StringUtils.trimToSizeNoDots(zipCode, 10);
        state = StringUtils.toString(thisAddress.getState()).toUpperCase();
        state = StringUtils.trimToSizeNoDots(state, 3);
        city = StringUtils.toString(thisAddress.getCity()).toUpperCase();
        city = StringUtils.trimToSizeNoDots(city, 26);
        country = StringUtils.toString(thisAddress.getCountry()).toUpperCase();
        country = StringUtils.trimToSizeNoDots(country, 3);
        // the break is to only get the first business address if they entered more then 1
        break;
      }
    }
    phoneNumberList.setContactId(contactId);
    phoneNumberList.buildList(CFSdb);
    Iterator iphone = phoneNumberList.iterator();
    while (iphone.hasNext()) {
      ContactPhoneNumber thisPhone = (ContactPhoneNumber) iphone.next();
      if (thisPhone.getPrimaryNumber()) {
        phone = thisPhone.getNumber();
        phone = StringUtils.trimToSizeNoDots(phone, 15);
        // the break is to only get the first business address if they entered more then 1
        break;
      }
    }
    Iterator lphone = phoneNumberList.iterator();
    while (lphone.hasNext()) {
      ContactPhoneNumber thisPhone = (ContactPhoneNumber) lphone.next();
      if (thisPhone.getTypeName().trim().equals("Fax")) {
        fax = thisPhone.getNumber();
        fax = StringUtils.trimToSizeNoDots(fax, 15);
        // the break is to only get the first business address if they entered more then 1
        break;
      }
    }
    emailAddressList.setContactId(contactId);
    emailAddressList.buildList(CFSdb);
    Iterator iemail = emailAddressList.iterator();
    while (iemail.hasNext()) {
      ContactEmailAddress thisEmail = (ContactEmailAddress) iemail.next();
      if (thisEmail.getPrimaryEmail()) {
        email = thisEmail.getEmail();
        email = StringUtils.trimToSizeNoDots(email, 255);
        // the break is to only get the first business address if they entered more then 1
        break;
      }
    }

    /*
     *  idpor = unique client number
     *  idtit = Mr. Mrs.  Dr. etc.
     *  Idint1 = Organization name
     *  idint2 = Full name of individual
     *  idad1 - idad4 = Address 1 -4
     *  idpos = zipcode
     *  idvil = City
     *  idpay = Country
     *  idco3 = segment
     *  idcsp = 001 or 002  I set it to 002????
     */
    country = "USA";

    try {
      SPIdb.setAutoCommit(false);
      sql.append("INSERT INTO FDIDET (idpor, idtit, idint1, idint2, idad1, idad2, idad3, idad4, ");
      sql.append("idpos, idvil, idpay, idco3, tel, fax, state_code, fcc, dir_bill, idcsp, mail, web_page)  ");
      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ? ) ");
      int i = 0;

      PreparedStatement pst = SPIdb.prepareStatement(sql.toString());

      pst.setInt(++i, maresaClient);
      pst.setString(++i, title);
      pst.setString(++i, orgName.toUpperCase());
      pst.setString(++i, nameFull.toUpperCase());
      pst.setString(++i, address1.toUpperCase());
      pst.setString(++i, address2.toUpperCase());
      pst.setString(++i, address3.toUpperCase());
      pst.setString(++i, address4.toUpperCase());
      pst.setString(++i, zipCode);
      pst.setString(++i, city.toUpperCase());
      pst.setString(++i, country.toUpperCase());
      pst.setString(++i, segment);
      pst.setString(++i, phone);
      pst.setString(++i, fax);
      pst.setString(++i, state);
      pst.setInt(++i, fcc);
      pst.setInt(++i, dirBill);
      pst.setString(++i, idcsp);
      pst.setString(++i, email);
      pst.setString(++i, url);
      pst.execute();
      pst.close();
      SPIdb.commit();
    } catch (SQLException e) {
      SPIdb.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      SPIdb.setAutoCommit(true);
    }

    return true;
  }



  /**
   *  Description of the Method Updates the fdidet table in Maresa
   *
   *@param  SPIdb             Description of the Parameter
   *@param  CFSdb             Description of the Parameter
   *@param  maresaClient      Description of the Parameter
   *@param  contactId         Description of the Parameter
   *@param  orgId             Description of the Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public int update(Connection SPIdb, Connection CFSdb, int maresaClient, int contactId, int orgId) throws SQLException {
    int resultCount = 0;

    if (!isValid(SPIdb)) {
      return -1;
    }
    if (!isValid(CFSdb)) {
      return -1;
    }
    Contact newContact = null;
    Organization newOrganization = null;
    newContact = new Contact(CFSdb, contactId);
    newOrganization = new Organization(CFSdb, orgId);

    int salutation = newContact.getListSalutation();
    tableName = "lookup_title";
    title = getLookupValue(CFSdb, salutation, tableName);
    title = StringUtils.trimToSizeNoDots(title, 3);
    int segmentId = newOrganization.getSegmentId();
    tableName = "lookup_segments";
    segment = getLookupValue(CFSdb, segmentId, tableName);
    segment = segment.substring(0, 3);
    orgName = newOrganization.getName();
    orgName = StringUtils.trimToSizeNoDots(orgName, 32);
    url = newOrganization.getUrl();
    url = StringUtils.trimToSizeNoDots(url, 255);
    nameFull = newContact.getNameFirst() + " " + newContact.getNameLast();
    nameFull = StringUtils.trimToSizeNoDots(nameFull, 32);
    if (newOrganization.getDirectBill()) {
      dirBill = 1;
    }

    addressList.setContactId(contactId);
    addressList.buildList(CFSdb);
    Iterator iaddress = addressList.iterator();
    while (iaddress.hasNext()) {
      ContactAddress thisAddress = (ContactAddress) iaddress.next();
      if (thisAddress.getPrimaryAddress()) {
        address1 = StringUtils.toString(thisAddress.getStreetAddressLine1()).toUpperCase();
        address1 = StringUtils.trimToSizeNoDots(address1, 32);
        address2 = StringUtils.toString(thisAddress.getStreetAddressLine2()).toUpperCase();
        address2 = StringUtils.trimToSizeNoDots(address2, 32);
        address3 = StringUtils.toString(thisAddress.getStreetAddressLine3()).toUpperCase();
        address3 = StringUtils.trimToSizeNoDots(address3, 32);
        address4 = StringUtils.toString(thisAddress.getStreetAddressLine4()).toUpperCase();
        address4 = StringUtils.trimToSizeNoDots(address4, 32);
        zipCode = thisAddress.getZip();
        zipCode = StringUtils.trimToSizeNoDots(zipCode, 10);
        state = StringUtils.toString(thisAddress.getState()).toUpperCase();
        state = StringUtils.trimToSizeNoDots(state, 3);
        city = StringUtils.toString(thisAddress.getCity()).toUpperCase();
        city = StringUtils.trimToSizeNoDots(city, 26);
        country = StringUtils.toString(thisAddress.getCountry()).toUpperCase();
        country = StringUtils.trimToSizeNoDots(country, 3);
        // the break is to only get the first business address if they entered more then 1
        break;
      }
    }
    phoneNumberList.setContactId(contactId);
    phoneNumberList.buildList(CFSdb);
    Iterator jphone = phoneNumberList.iterator();
    while (jphone.hasNext()) {
      ContactPhoneNumber thisPhone = (ContactPhoneNumber) jphone.next();
      if (thisPhone.getTypeName().trim().equals("Fax")) {
        fax = thisPhone.getNumber();
        fax = StringUtils.trimToSizeNoDots(fax, 15);
        // the break is to only get the first business address if they entered more then 1
        break;
      }
    }
    Iterator mphone = phoneNumberList.iterator();
    while (mphone.hasNext()) {
      ContactPhoneNumber thisPhone = (ContactPhoneNumber) mphone.next();
      if (thisPhone.getPrimaryNumber()) {
        phone = thisPhone.getNumber();
        phone = StringUtils.trimToSizeNoDots(phone, 15);
        // the break is to only get the first business address if they entered more then 1
        break;
      }
    }
    emailAddressList.setContactId(contactId);
    emailAddressList.buildList(CFSdb);
    Iterator iemail = emailAddressList.iterator();
    while (iemail.hasNext()) {
      ContactEmailAddress thisEmail = (ContactEmailAddress) iemail.next();
      if (thisEmail.getPrimaryEmail()) {
        email = thisEmail.getEmail();
        email = StringUtils.trimToSizeNoDots(email, 255);
        // the break is to only get the first business address if they entered more then 1
        break;
      }
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE FDIDET " +
        "SET idtit = ?, idint1 = ?, idint2 = ?," +
        "idad1 = ?, idad2 = ?, idad3 = ?, idad4 = ?, " +
        "idpos = ?, idvil = ?, idpay = ?, idco3 = ?, " +
        "tel = ?, fax = ?, state_code = ?, fcc = ?, " +
        "dir_bill = ?, mail = ?,web_page = ? ");

    sql.append("WHERE idpor = ? ");
    int i = 0;
    pst = SPIdb.prepareStatement(sql.toString());

    pst.setString(++i, title);
    pst.setString(++i, orgName.toUpperCase());
    pst.setString(++i, nameFull.toUpperCase());
    pst.setString(++i, address1.toUpperCase());
    pst.setString(++i, address2.toUpperCase());
    pst.setString(++i, address3.toUpperCase());
    pst.setString(++i, address4.toUpperCase());
    pst.setString(++i, zipCode);
    pst.setString(++i, city.toUpperCase());
    pst.setString(++i, country.toUpperCase());
    pst.setString(++i, segment);
    pst.setString(++i, phone);
    pst.setString(++i, fax);
    pst.setString(++i, state);
    pst.setInt(++i, fcc);
    pst.setInt(++i, dirBill);
    pst.setString(++i, email);
    pst.setString(++i, url);
    pst.setInt(++i, maresaClient);
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Gets the Valid attribute of the Organization object
   *
   *@param  db                Description of Parameter
   *@return                   The Valid value
   *@exception  SQLException  Description of Exception
   */
  protected boolean isValid(Connection db) throws SQLException {
    errors.clear();

    if (hasErrors()) {
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Gets the lookupValue attribute of the Maresa object
   *
   *@param  db                Description of the Parameter
   *@param  thisSiteId        Description of the Parameter
   *@param  tableName         Description of the Parameter
   *@return                   The lookupValue value
   *@exception  SQLException  Description of the Exception
   */
  public String getLookupValue(Connection db, int thisSiteId, String tableName) throws SQLException {
    String maresaSite = null;
    ResultSet rs = null;
    int items = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT description " +
        "FROM " + tableName + " " +
        "WHERE code = ? ");
    pst.setInt(1, thisSiteId);
    rs = pst.executeQuery();
    while (rs.next()) {
      maresaSite = rs.getString("description");
    }
    rs.close();
    pst.close();
    return maresaSite;
  }


  /**
   *  Description of the Method
   *
   *@param  CFSdb             Description of the Parameter
   *@param  maresaClient      Description of the Parameter
   *@param  contactId         Description of the Parameter
   *@param  orgId             Description of the Parameter
   *@param  thisSiteId        Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insertMaresaClient(Connection CFSdb, int maresaClient, int contactId, int orgId, int thisSiteId) throws SQLException {
    if (!isValid(CFSdb)) {
      return false;
    }
    StringBuffer sql = new StringBuffer();
    try {
      CFSdb.setAutoCommit(false);
      sql.append("INSERT INTO MARESA_CLIENT (org_id, contact_id, site_id, client_id ) ");
      sql.append("VALUES (?, ?, ?, ? ) ");
      int i = 0;
      PreparedStatement pst = CFSdb.prepareStatement(sql.toString());
      pst.setInt(++i, orgId);
      pst.setInt(++i, contactId);
      pst.setInt(++i, thisSiteId);
      pst.setInt(++i, maresaClient);
      pst.execute();
      pst.close();
      CFSdb.commit();
    } catch (SQLException e) {
      CFSdb.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      CFSdb.setAutoCommit(true);
    }
    return true;
  }
}


