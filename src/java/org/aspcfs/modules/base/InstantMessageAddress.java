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

import org.aspcfs.utils.DatabaseUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author Ananth
 * @created April 21, 2005
 */
public class InstantMessageAddress {
  public final static int BUSINESS = 1;
  protected boolean isContact = false;
  private int id = -1;
  private int contactId = -1;
  private int addressIMType = -1;
  private String addressIMTypeName = "Main";
  private int addressIMService = -1;
  private String addressIMServiceName = "";
  private String addressIM = "";
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private boolean enabled = true;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private boolean primaryIM = false;


  /**
   * Gets the id attribute of the InstantMessageAddress object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the InstantMessageAddress object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the InstantMessageAddress object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Gets the contactId attribute of the InstantMessageAddress object
   *
   * @return The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   * Sets the contactId attribute of the InstantMessageAddress object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Sets the contactId attribute of the InstantMessageAddress object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   * Gets the addressIMType attribute of the InstantMessageAddress object
   *
   * @return The addressIMType value
   */
  public int getAddressIMType() {
    return addressIMType;
  }


  /**
   * Sets the addressIMType attribute of the InstantMessageAddress object
   *
   * @param tmp The new addressIMType value
   */
  public void setAddressIMType(int tmp) {
    this.addressIMType = tmp;
  }


  /**
   * Sets the addressIMType attribute of the InstantMessageAddress object
   *
   * @param tmp The new addressIMType value
   */
  public void setAddressIMType(String tmp) {
    this.addressIMType = Integer.parseInt(tmp);
  }


  /**
   * Gets the addressIMTypeName attribute of the InstantMessageAddress object
   *
   * @return The addressIMTypeName value
   */
  public String getAddressIMTypeName() {
    return addressIMTypeName;
  }


  /**
   * Sets the addressIMTypeName attribute of the InstantMessageAddress object
   *
   * @param tmp The new addressIMTypeName value
   */
  public void setAddressIMTypeName(String tmp) {
    this.addressIMTypeName = tmp;
  }


  /**
   * Gets the addressIMService attribute of the InstantMessageAddress object
   *
   * @return The addressIMService value
   */
  public int getAddressIMService() {
    return addressIMService;
  }


  /**
   * Sets the addressIMService attribute of the InstantMessageAddress object
   *
   * @param tmp The new addressIMService value
   */
  public void setAddressIMService(int tmp) {
    this.addressIMService = tmp;
  }


  /**
   * Sets the addressIMService attribute of the InstantMessageAddress object
   *
   * @param tmp The new addressIMService value
   */
  public void setAddressIMService(String tmp) {
    this.addressIMService = Integer.parseInt(tmp);
  }


  /**
   * Gets the addressIMServiceName attribute of the InstantMessageAddress
   * object
   *
   * @return The addressIMServiceName value
   */
  public String getAddressIMServiceName() {
    return addressIMServiceName;
  }


  /**
   * Sets the addressIMServiceName attribute of the InstantMessageAddress
   * object
   *
   * @param tmp The new addressIMServiceName value
   */
  public void setAddressIMServiceName(String tmp) {
    this.addressIMServiceName = tmp;
  }


  /**
   * Gets the addressIM attribute of the InstantMessageAddress object
   *
   * @return The addressIM value
   */
  public String getAddressIM() {
    return addressIM;
  }


  /**
   * Sets the addressIM attribute of the InstantMessageAddress object
   *
   * @param tmp The new addressIM value
   */
  public void setAddressIM(String tmp) {
    this.addressIM = tmp;
  }


  /**
   * Gets the enteredBy attribute of the InstantMessageAddress object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Sets the enteredBy attribute of the InstantMessageAddress object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the InstantMessageAddress object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Gets the modifiedBy attribute of the InstantMessageAddress object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Sets the modifiedBy attribute of the InstantMessageAddress object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the InstantMessageAddress object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Gets the enabled attribute of the InstantMessageAddress object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Sets the enabled attribute of the InstantMessageAddress object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the InstantMessageAddress object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the entered attribute of the InstantMessageAddress object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Sets the entered attribute of the InstantMessageAddress object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the InstantMessageAddress object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the modified attribute of the InstantMessageAddress object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Sets the modified attribute of the InstantMessageAddress object
   *
   * @param tmp The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the modified attribute of the InstantMessageAddress object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the primaryIM attribute of the InstantMessageAddress object
   *
   * @return The primaryIM value
   */
  public boolean getPrimaryIM() {
    return primaryIM;
  }


  /**
   * Sets the primaryIM attribute of the InstantMessageAddress object
   *
   * @param tmp The new primaryIM value
   */
  public void setPrimaryIM(boolean tmp) {
    this.primaryIM = tmp;
  }


  /**
   * Sets the primaryIM attribute of the InstantMessageAddress object
   *
   * @param tmp The new primaryIM value
   */
  public void setPrimaryIM(String tmp) {
    this.primaryIM = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the valid attribute of the InstantMessageAddress object
   *
   * @return The valid value
   */
  public boolean isValid() {
    return (addressIMType > -1 && addressIMService > -1
        && addressIM != null && !"".equals(addressIM.trim()));
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    this.setId(rs.getInt("address_id"));
    if (isContact) {
      this.setContactId(rs.getInt("contact_id"));
      if (rs.wasNull()) {
        this.setContactId(-1);
      }
    }
    this.setAddressIMType(rs.getInt("imaddress_type"));
    if (rs.wasNull()) {
      this.setAddressIMType(-1);
    }
    this.setAddressIMService(rs.getInt("imaddress_service"));
    if (rs.wasNull()) {
      this.setAddressIMService(-1);
    }
    this.setAddressIMTypeName(rs.getString("type_description"));
    this.setAddressIMServiceName(rs.getString("service_description"));
    this.setAddressIM(rs.getString("imaddress"));
    this.setEntered(rs.getTimestamp("entered"));
    this.setEnteredBy(rs.getInt("enteredby"));

    if (enteredBy == -1) {
      this.setEnteredBy(0);
    }

    this.setModified(rs.getTimestamp("modified"));
    this.setModifiedBy(rs.getInt("modifiedby"));
    if (isContact) {
      this.setPrimaryIM(rs.getBoolean("primary_im"));
    }

    if (modifiedBy == -1) {
      this.setModifiedBy(0);
    }
  }


  /**
   * Description of the Method
   *
   * @param request   Description of the Parameter
   * @param parseItem Description of the Parameter
   */
  public void buildRecord(HttpServletRequest request, int parseItem) {
    this.setAddressIMType(
        request.getParameter("instantmessage" + parseItem + "type"));
    this.setAddressIMService(
        request.getParameter("instantmessage" + parseItem + "service"));
    if (request.getParameter("instantmessage" + parseItem + "id") != null) {
      this.setId(request.getParameter("instantmessage" + parseItem + "id"));
    }
    this.setAddressIM(
        request.getParameter("instantmessage" + parseItem + "address"));
    if (request.getParameter("instantmessage" + parseItem + "delete") != null) {
      String action = request.getParameter(
          "instantmessage" + parseItem + "delete").toLowerCase();
      if (action.equals("on")) {
        this.setEnabled(false);
      }
    }
  }
}

