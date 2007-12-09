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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.StateSelect;

/**
 * Represents a mailing address to be used as a base class.
 * 
 * @author mrajkowski
 * @version $Id$
 * @created July 11, 2001
 */
public class Address {

  protected boolean isContact = false;
  protected boolean isOrder = false;
  private int id = -1;
  private int orgId = -1;
  private int contactId = -1;
  private int orderId = -1;
  private String streetAddressLine1 = "";
  private String streetAddressLine2 = "";
  private String streetAddressLine3 = "";
  private String streetAddressLine4 = "";
  private String city = null;
  private String state = null;
  private String otherState = null;
  private String zip = null;
  private String country = null;
  private String county = null;
  private double latitude = 0;
  private double longitude = 0;
  private int type = -1;
  private String typeName = null;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private boolean enabled = true;
  private Date entered = null;
  private Date modified = null;
  private boolean primaryAddress = false;

  /**
   * Sets the Id attribute of the Address object
   *
   * @param tmp
   *          The new Id value
   * @since 1.1
   */
  public void setId(int tmp) {
    this.id = tmp;
  }

  /**
   * Sets the enabled attribute of the Address object
   *
   * @param tmp
   *          The new enabled value
   */
  public void setEnabled(String tmp) {
    enabled = (DatabaseUtils.parseBoolean(tmp));
  }

  /**
   * Sets the Id attribute of the Address object
   *
   * @param tmp
   *          The new Id value
   * @since 1.8
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }

  /**
   * Sets the OrgId attribute of the Address object
   *
   * @param orgId
   *          The new OrgId value
   * @since 1.6
   */
  public void setOrgId(int orgId) {
    this.orgId = orgId;
  }

  /**
   * Sets the orgId attribute of the Address object
   *
   * @param orgId
   *          The new orgId value
   */
  public void setOrgId(String orgId) {
    this.orgId = Integer.parseInt(orgId);
  }

  /**
   * Sets the ContactId attribute of the Address object
   *
   * @param tmp
   *          The new ContactId value
   * @since 1.8
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }

  /**
   * Sets the contactId attribute of the Address object
   *
   * @param tmp
   *          The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }

  /**
   * Sets the streetAddressLine3 attribute of the Address object
   *
   * @param streetAddressLine3
   *          The new streetAddressLine3 value
   */
  public void setStreetAddressLine3(String streetAddressLine3) {
    this.streetAddressLine3 = streetAddressLine3;
  }

  /**
   * Sets the streetAddressLine4 attribute of the Address object
   *
   * @param streetAddressLine4
   *          The new streetAddressLine4 value
   */
  public void setStreetAddressLine4(String streetAddressLine4) {
    this.streetAddressLine4 = streetAddressLine4;
  }

  /**
   * Sets the otherState attribute of the Address object
   *
   * @param tmp
   *          The new otherState value
   */
  public void setOtherState(String tmp) {
    if (tmp != null) {
      if (tmp.length() == 0) {
        this.otherState = null;
      } else {
        this.otherState = tmp;
      }
    }
  }

  /**
   * Sets the primaryAddress attribute of the Address object
   *
   * @param tmp
   *          The new primaryAddress value
   */
  public void setPrimaryAddress(boolean tmp) {
    this.primaryAddress = tmp;
  }

  /**
   * Sets the primaryAddress attribute of the Address object
   *
   * @param tmp
   *          The new primaryAddress value
   */
  public void setPrimaryAddress(String tmp) {
    this.primaryAddress = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Gets the primaryAddress attribute of the Address object
   *
   * @return The primaryAddress value
   */
  public boolean getPrimaryAddress() {
    return primaryAddress;
  }

  /**
   * Gets the otherState attribute of the Address object
   *
   * @return The otherState value
   */
  public String getOtherState() {
    return otherState;
  }

  /**
   * Gets the streetAddressLine3 attribute of the Address object
   *
   * @return The streetAddressLine3 value
   */
  public String getStreetAddressLine3() {
    return streetAddressLine3;
  }

  /**
   * Gets the streetAddressLine4 attribute of the Address object
   *
   * @return The streetAddressLine4 value
   */
  public String getStreetAddressLine4() {
    return streetAddressLine4;
  }

  /**
   * Gets the contactId attribute of the Address object
   *
   * @return The contactId value
   */
  public int getContactId() {
    return contactId;
  }

  /**
   * Gets the county attribute of the Address object
   *
   * @return The county value
   */
  public String getCounty() {
    return county;
  }


  /**
   * Gets the latitude attribute of the Address object
   *
   * @return The latitude value
   */
  public double getLatitude() {
    return latitude;
  }


  /**
   * Gets the longitude attribute of the Address object
   *
   * @return The longitude value
   */
  public double getLongitude() {
    return longitude;
  }


  /**
   * Sets the StreetAddressLine1 attribute of the Address object
   *
   * @param tmp
   *          The new StreetAddressLine1 value
   * @since 1.5
   */
  public void setStreetAddressLine1(String tmp) {
    this.streetAddressLine1 = tmp;
  }

  /**
   * Sets the StreetAddressLine2 attribute of the Address object
   *
   * @param tmp
   *          The new StreetAddressLine2 value
   * @since 1.5
   */
  public void setStreetAddressLine2(String tmp) {
    this.streetAddressLine2 = tmp;
  }

  /**
   * Sets the City attribute of the Address object
   *
   * @param tmp
   *          The new City value
   * @since 1.5
   */
  public void setCity(String tmp) {
    this.city = tmp;
  }

  /**
   * Sets the State attribute of the Address object
   *
   * @param tmp
   *          The new State value
   * @since 1.5
   */
  public void setState(String tmp) {
    if (tmp != null) {
      if (tmp.length() == 0) {
        this.state = null;
      } else {
        this.state = tmp;
      }
    }
  }

  /**
   * Sets the Zip attribute of the Address object
   *
   * @param tmp
   *          The new Zip value
   * @since 1.5
   */
  public void setZip(String tmp) {
    this.zip = tmp;
  }

  /**
   * Sets the Country attribute of the Address object
   *
   * @param tmp
   *          The new Country value
   * @since 1.5
   */
  public void setCountry(String tmp) {
    this.country = tmp;
  }

  /**
   * Sets the Type attribute of the Address object, for example: Home, Work,
   * Billing, etc.
   *
   * @param tmp
   *          The new Type value
   * @since 1.6
   */
  public void setType(int tmp) {
    this.type = tmp;
  }

  /**
   * Sets the Type attribute of the Address object
   *
   * @param tmp
   *          The new Type value
   * @since 1.8
   */
  public void setType(String tmp) {
    this.type = Integer.parseInt(tmp);
  }

  /**
   * Sets the TypeName attribute of the Address object
   *
   * @param tmp
   *          The new TypeName value
   * @since 1.6
   */
  public void setTypeName(String tmp) {
    this.typeName = tmp;
  }

  /**
   * Sets the EnteredBy attribute of the Address object
   *
   * @param tmp
   *          The new EnteredBy value
   * @since 1.6
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }

  /**
   * Sets the enteredBy attribute of the Address object
   *
   * @param tmp
   *          The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }

  /**
   * Sets the ModifiedBy attribute of the Address object
   *
   * @param tmp
   *          The new ModifiedBy value
   * @since 1.6
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }

  /**
   * Sets the modifiedBy attribute of the Address object
   *
   * @param tmp
   *          The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }

  /**
   * Sets the Enabled attribute of the Address object
   *
   * @param tmp
   *          The new Enabled value
   * @since 1.8
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }

  /**
   * Gets the Id attribute of the Address object
   *
   * @return The Id value
   * @since 1.6
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the OrgId attribute of the Address object
   *
   * @return The OrgId value
   * @since 1.6
   */
  public int getOrgId() {
    return orgId;
  }

  /**
   * Gets the entered attribute of the Address object
   *
   * @return The entered value
   */
  public Timestamp getEntered() {
    return (Timestamp) entered;
  }

  /**
   * Gets the modified attribute of the Address object
   *
   * @return The modified value
   */
  public Timestamp getModified() {
    return (Timestamp) modified;
  }

  /**
   * Gets the StreetAddressLine1 attribute of the Address object
   *
   * @return The StreetAddressLine1 value
   * @since 1.5
   */
  public String getStreetAddressLine1() {
    return streetAddressLine1;
  }

  /**
   * Gets the StreetAddressLine2 attribute of the Address object
   *
   * @return The StreetAddressLine2 value
   * @since 1.5
   */
  public String getStreetAddressLine2() {
    return streetAddressLine2;
  }

  /**
   * Gets the City attribute of the Address object
   *
   * @return The City value
   * @since 1.5
   */
  public String getCity() {
    return city;
  }

  /**
   * Gets the State attribute of the Address object
   *
   * @return The State value
   * @since 1.5
   */
  public String getState() {
    StateSelect stateSelect = new StateSelect(country);
    if (stateSelect.hasCountry(country)) {
      return state;
    }
    if ("-1".equals(otherState)) {
      return "";
    } else {
      return otherState;
    }
  }

  /**
   * Gets the CityState attribute of the Address object
   *
   * @return The CityState value
   * @since 1.5
   */
  public String getCityState() {
    if (getCity() != null && getState() != null && !getCity().equals("")
        && !getState().equals("") && !"-1".equals(getState())) {
      return (getCity() + ", " + getState());
    } else if ((getCity() == null || "".equals(getCity()))
        && getState() != null && !"".equals(getState())
        && !"-1".equals(getState())) {
      return (getState());
    } else if ((getState() == null || "".equals(getState()) || "-1"
        .equals(getState()))
        && getCity() != null && !"".equals(getCity())) {
      return (getCity());
    } else {
      return ("");
    }
  }

  /**
   * Gets the Zip attribute of the Address object
   *
   * @return The Zip value
   * @since 1.5
   */
  public String getZip() {
    return zip;
  }

  /**
   * Gets the Country attribute of the Address object
   *
   * @return The Country value
   * @since 1.5
   */
  public String getCountry() {
    if (country==null || "-1".equals(country)) {
      return "";
    } else {
      return country;
    }
  }

  /**
   * Gets the Type attribute of the Address object
   *
   * @return The Type value
   * @since 1.6
   */
  public int getType() {
    return type;
  }

  /**
   * Gets the TypeName attribute of the Address object
   *
   * @return The TypeName value
   * @since 1.6
   */
  public String getTypeName() {
    return typeName;
  }

  /**
   * Sets the orderId attribute of the Address object
   *
   * @param tmp
   *          The new orderId value
   */
  public void setOrderId(int tmp) {
    this.orderId = tmp;
  }

  /**
   * Sets the orderId attribute of the Address object
   *
   * @param tmp
   *          The new orderId value
   */
  public void setOrderId(String tmp) {
    this.orderId = Integer.parseInt(tmp);
  }

  /**
   * Gets the orderId attribute of the Address object
   *
   * @return The orderId value
   */
  public int getOrderId() {
    return orderId;
  }

  /**
   * If any of the information for an address is filled in, then the address is
   * valid
   *
   * @return The Valid value
   * @since 1.10
   */
  public boolean isValid() {
    // A blank record is not valid, and having the default UNITED STATES
    // selected
    // without any other data is also invalid and does not need to be saved
    if (type == -1) {
      return false;
    }
    if ((streetAddressLine1 == null || streetAddressLine1.trim().equals("")) &&
        (streetAddressLine2 == null || streetAddressLine2.trim().equals("")) &&
        (streetAddressLine3 == null || streetAddressLine3.trim().equals("")) &&
        (streetAddressLine4 == null || streetAddressLine4.trim().equals("")) &&
        (city == null || city.trim().equals("")) &&
        (state == null || state.trim().equals("") || "-1".equals(state)) &&
        (zip == null || zip.trim().equals("")) &&
        (county == null || county.trim().equals("")) &&
        (latitude == 0) &&
        (longitude == 0)){
      return false;
    }
    return true;
  }

  /**
   * Gets the EnteredBy attribute of the Address object
   * 
   * @return The EnteredBy value
   * @since 1.8
   */
  public int getEnteredBy() {
    return enteredBy;
  }

  /**
   * Sets the entered attribute of the Address object
   * 
   * @param tmp
   *          The new entered value
   */
  public void setEntered(Date tmp) {
    this.entered = tmp;
  }

  /**
   * Sets the modified attribute of the Address object
   * 
   * @param tmp
   *          The new modified value
   */
  public void setModified(Date tmp) {
    this.modified = tmp;
  }

  /**
   * Sets the entered attribute of the Address object
   * 
   * @param tmp
   *          The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }

  /**
   * Sets the modified attribute of the Address object
   * 
   * @param tmp
   *          The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }

  /**
   * Gets the ModifiedBy attribute of the Address object
   * 
   * @return The ModifiedBy value
   * @since 1.6
   */
  public int getModifiedBy() {
    return modifiedBy;
  }

  /**
   * Gets the Enabled attribute of the Address object
   * 
   * @return The Enabled value
   * @since 1.9
   */
  public boolean getEnabled() {
    return enabled;
  }

  /**
   * Sets the county attribute of the Address object
   *
   * @param county the county to set
   */
  public void setCounty(String county) {
    this.county = county;
  }


  /**
   * Sets the latitude attribute of the Address object
   *
   * @param latitude the latitude to set
   */
  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }


  /**
   * Sets the longitude attribute of the Address object
   *
   * @param longitude the longitude to set
   */
  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }


  /**
   * Sets the latitude attribute of the Address object
   *
   * @param latitude the latitude to set
   */
  public void setLatitude(String latitude) {
    try {
      this.latitude = Double.parseDouble(latitude);
    } catch (Exception e) {
      this.latitude = 0;
    }
  }


  /**
   * Sets the longitude attribute of the Address object
   *
   * @param longitude the longitude to set
   */
  public void setLongitude(String longitude) {
    try {
      this.longitude = Double.parseDouble(longitude);
    } catch (Exception e) {
      this.longitude = 0;
    }
  }


  /**
   * Gets the Address attribute of the Address object
   * 
   * @return The Address value
   * @since 1.9
   */
  public String toString() {
    StringBuffer thisAddress = new StringBuffer();
    if (this.getStreetAddressLine1() != null
        && !this.getStreetAddressLine1().trim().equals("")) {
      thisAddress.append(this.getStreetAddressLine1().trim() + "\r\n");
    }
    if (this.getStreetAddressLine2() != null
        && !this.getStreetAddressLine2().trim().equals("")) {
      thisAddress.append(this.getStreetAddressLine2().trim() + "\r\n");
    }
    if (this.getStreetAddressLine3() != null
        && !this.getStreetAddressLine3().trim().equals("")) {
      thisAddress.append(this.getStreetAddressLine3().trim() + "\r\n");
    }
    if (this.getStreetAddressLine4() != null
        && !this.getStreetAddressLine4().trim().equals("")) {
      thisAddress.append(this.getStreetAddressLine4().trim() + "\r\n");
    }

    if (!this.getCityState().trim().equals("")) {
      thisAddress.append(this.getCityState().trim() + "\r\n");
    }
    if (this.getZip() != null && !this.getZip().trim().equals("")) {
      thisAddress.append(this.getZip().trim() + "\r\n");
    }
    if (this.getCountry() != null && !this.getCountry().trim().equals("")) {
      thisAddress.append(this.getCountry().trim() + "\r\n");
    }
    if (this.getCounty() != null && !this.getCounty().trim().equals("")) {
      thisAddress.append("County: " + this.getCounty().trim() + "\r\n");
    }
    if (this.getLatitude() != 0) {
      thisAddress.append("Latitude: " + this.getLatitude() + "\r\n");
    }
    if (this.getLongitude() != 0) {
      thisAddress.append("Longitude: " + this.getLongitude() + "\r\n");
    }
    return thisAddress.toString();
  }

  /**
   * Gets the locale attribute of the Address object
   * 
   * @return The locale value
   */
  public Locale getLocale() {
    if ("UNITED STATES".equals(country)) {
      return Locale.US;
    }
    if ("CANADA".equals(country)) {
      return Locale.CANADA;
    }
    if ("UNITED KINGDOM".equals(country)) {
      return Locale.UK;
    }
    return null;
  }

  /**
   * Populates the object from a ResultSet
   * 
   * @param rs
   *          Description of Parameter
   * @throws SQLException
   *           Description of Exception
   * @since 1.6
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    this.setId(rs.getInt("address_id"));
    if (isOrder) {
      this.setContactId(rs.getInt("contact_id"));
      if (rs.wasNull()) {
        this.setContactId(-1);
      }
    } else {
      if (!isContact) {
        this.setOrgId(rs.getInt("org_id"));
        if (rs.wasNull()) {
          this.setOrgId(-1);
        }
      } else {
        this.setContactId(rs.getInt("contact_id"));
        if (rs.wasNull()) {
          this.setContactId(-1);
        }
      }
    }
    this.setType(rs.getInt("address_type"));
    if (rs.wasNull()) {
      this.setType(-1);
    }
    this.setStreetAddressLine1(rs.getString("addrline1"));
    this.setStreetAddressLine2(rs.getString("addrline2"));
    this.setStreetAddressLine3(rs.getString("addrline3"));
    this.setStreetAddressLine4(rs.getString("addrline4"));
    this.setCity(rs.getString("city"));
    this.setState(rs.getString("state"));
    this.setOtherState(state);
    this.setZip(rs.getString("postalcode"));
    this.setCountry(rs.getString("country"));
    this.setCounty(rs.getString("county"));
    this.setLatitude(rs.getDouble("latitude"));
    this.setLongitude(rs.getDouble("longitude"));
    this.setEntered(rs.getTimestamp("entered"));
    this.setEnteredBy(rs.getInt("enteredby"));
    if (this.getEnteredBy() == -1) {
      this.setEnteredBy(0);
    }
    this.setModified(rs.getTimestamp("modified"));
    this.setModifiedBy(rs.getInt("modifiedby"));
    if (this.getModifiedBy() == -1) {
      this.setModifiedBy(0);
    }
    this.setPrimaryAddress(rs.getBoolean("primary_address"));
    this.setTypeName(rs.getString("description"));
  }

  /**
   * Description of the Method
   * 
   * @param request
   *          Description of Parameter
   * @param parseItem
   *          Description of Parameter
   * @since 1.8
   */
  public void buildRecord(HttpServletRequest request, int parseItem) {
    this.setType(request.getParameter("address" + parseItem + "type"));
    if (request.getParameter("address" + parseItem + "id") != null) {
      this.setId(request.getParameter("address" + parseItem + "id"));
    }
    String buffer = null;
    buffer = request.getParameter("address" + parseItem + "line1");
    if (buffer != null && !"".equals(buffer.trim())) {
      this.setStreetAddressLine1(buffer);
    }
    buffer = request.getParameter("address" + parseItem + "line2");
    if (buffer != null && !"".equals(buffer.trim())) {
      this.setStreetAddressLine2(buffer);
    }
    buffer = request.getParameter("address" + parseItem + "line3");
    if (buffer != null && !"".equals(buffer.trim())) {
      this.setStreetAddressLine3(buffer);
    }
    buffer = request.getParameter("address" + parseItem + "line4");
    if (buffer != null && !"".equals(buffer.trim())) {
      this.setStreetAddressLine4(buffer);
    }
    buffer = request.getParameter("address" + parseItem + "city");
    if (buffer != null && !"".equals(buffer.trim())) {
      this.setCity(buffer);
    }
    this.setCountry(request.getParameter("address" + parseItem + "country"));
    buffer = request.getParameter("address" + parseItem + "state");
    if (buffer != null && !"".equals(buffer.trim())) {
      this.setState(buffer);
    }
    buffer = request.getParameter("address" + parseItem + "otherState");
    if (buffer != null && !"".equals(buffer.trim())) {
      this.setOtherState(buffer);
    }
    buffer = request.getParameter("address" + parseItem + "zip");
    if (buffer != null && !"".equals(buffer.trim())) {
      this.setZip(buffer);
    }
    buffer = request.getParameter("address" + parseItem + "county");
    if (buffer != null && !"".equals(buffer.trim())) {
      this.setCounty(buffer);
    }
    buffer = request.getParameter("address" + parseItem + "latitude");
    if (buffer != null && !"".equals(buffer.trim())) {
      this.setLatitude(buffer);
    }
    buffer = request.getParameter("address" + parseItem + "longitude");
    if (buffer != null && !"".equals(buffer.trim())) {
      this.setLongitude(buffer);
    }
    if (request.getParameter("address" + parseItem + "delete") != null) {
      String action = request.getParameter("address" + parseItem + "delete")
          .toLowerCase();
      if (action.equals("on")) {
        this.setEnabled(false);
      }
    }
    if (request.getParameter("addressprimary") != null) {
      String tmp = request.getParameter("addressprimary");
      if (Integer.parseInt(tmp) == parseItem) {
        this.setPrimaryAddress(true);
      } else {
        this.setPrimaryAddress(false);
      }
    }
  }

  public static ArrayList getUserIdParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("enteredBy");
    thisList.add("modifiedBy");
    return thisList;
  }
}
