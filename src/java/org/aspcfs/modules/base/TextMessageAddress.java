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
 * Represents an email address.
 *
 * @author kailash
 * @version $Id$
 * @created January 11, 2005
 */
public class TextMessageAddress {

  public final static int BUSINESS = 1;
  protected boolean isContact = false;
  private int id = -1;
  private int orgId = -1;
  private int contactId = -1;
  private int type = -1;
  private String typeName = "Main";
  private String textMessageAddress = "";
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private boolean enabled = true;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private boolean primaryTextMessageAddress = false;


  /**
   * Sets the isContact attribute of the TextMessageAddress object
   *
   * @param tmp The new isContact value
   */
  public void setIsContact(boolean tmp) {
    this.isContact = tmp;
  }


  /**
   * Sets the id attribute of the TextMessageAddress object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the TextMessageAddress object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the orgId attribute of the TextMessageAddress object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   * Sets the orgId attribute of the TextMessageAddress object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   * Sets the contactId attribute of the TextMessageAddress object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Sets the contactId attribute of the TextMessageAddress object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   * Sets the type attribute of the TextMessageAddress object
   *
   * @param tmp The new type value
   */
  public void setType(int tmp) {
    this.type = tmp;
  }


  /**
   * Sets the type attribute of the TextMessageAddress object
   *
   * @param tmp The new type value
   */
  public void setType(String tmp) {
    this.type = Integer.parseInt(tmp);
  }


  /**
   * Sets the typeName attribute of the TextMessageAddress object
   *
   * @param tmp The new typeName value
   */
  public void setTypeName(String tmp) {
    this.typeName = tmp;
  }


  /**
   * Sets the textMessageAddress attribute of the TextMessageAddress object
   *
   * @param tmp The new textMessageAddress value
   */
  public void setTextMessageAddress(String tmp) {
    this.textMessageAddress = tmp;
  }


  /**
   * Sets the enteredBy attribute of the TextMessageAddress object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the TextMessageAddress object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the TextMessageAddress object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the TextMessageAddress object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the enabled attribute of the TextMessageAddress object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the TextMessageAddress object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the entered attribute of the TextMessageAddress object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the TextMessageAddress object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the modified attribute of the TextMessageAddress object
   *
   * @param tmp The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the modified attribute of the TextMessageAddress object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the primaryTextMessageAddress attribute of the TextMessageAddress
   * object
   *
   * @param tmp The new primaryTextMessageAddress value
   */
  public void setPrimaryTextMessageAddress(boolean tmp) {
    this.primaryTextMessageAddress = tmp;
  }


  /**
   * Sets the primaryTextMessageAddress attribute of the TextMessageAddress
   * object
   *
   * @param tmp The new primaryTextMessageAddress value
   */
  public void setPrimaryTextMessageAddress(String tmp) {
    this.primaryTextMessageAddress = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the isContact attribute of the TextMessageAddress object
   *
   * @return The isContact value
   */
  public boolean getIsContact() {
    return isContact;
  }


  /**
   * Gets the id attribute of the TextMessageAddress object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the orgId attribute of the TextMessageAddress object
   *
   * @return The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   * Gets the contactId attribute of the TextMessageAddress object
   *
   * @return The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   * Gets the type attribute of the TextMessageAddress object
   *
   * @return The type value
   */
  public int getType() {
    return type;
  }


  /**
   * Gets the typeName attribute of the TextMessageAddress object
   *
   * @return The typeName value
   */
  public String getTypeName() {
    return typeName;
  }


  /**
   * Gets the textMessageAddress attribute of the TextMessageAddress object
   *
   * @return The textMessageAddress value
   */
  public String getTextMessageAddress() {
    return textMessageAddress;
  }


  /**
   * Gets the enteredBy attribute of the TextMessageAddress object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modifiedBy attribute of the TextMessageAddress object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the enabled attribute of the TextMessageAddress object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the entered attribute of the TextMessageAddress object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the modified attribute of the TextMessageAddress object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the primaryTextMessageAddress attribute of the TextMessageAddress
   * object
   *
   * @return The primaryTextMessageAddress value
   */
  public boolean getPrimaryTextMessageAddress() {
    return primaryTextMessageAddress;
  }


  /**
   * If an textMessageAddress address is filled in, and a type is selected, then the email
   * address is valid.
   *
   * @return The Valid value
   * @since 1.5
   */
  public boolean isValid() {
    return (type > -1 && textMessageAddress != null && !textMessageAddress.trim().equals(
        ""));
  }


  /**
   * Description of the Method
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   * @since 1.4
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    this.setId(rs.getInt("address_id"));
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
    if (rs.wasNull()) {
      this.setType(-1);
    }
    this.setTextMessageAddress(rs.getString("textmessageaddress"));
    this.setEntered(rs.getTimestamp("entered"));
    this.setEnteredBy(rs.getInt("enteredby"));

    if (enteredBy == -1) {
      this.setEnteredBy(0);
    }

    this.setModified(rs.getTimestamp("modified"));
    this.setModifiedBy(rs.getInt("modifiedby"));
    if (isContact) {
      this.setPrimaryTextMessageAddress(
          rs.getBoolean("primary_textmessage_address"));
    }
    this.setType(rs.getInt("textmessageaddress_type"));
    this.setTypeName(rs.getString("description"));

    if (modifiedBy == -1) {
      this.setModifiedBy(0);
    }
  }


  /**
   * Description of the Method
   *
   * @param request   Description of Parameter
   * @param parseItem Description of Parameter
   * @since 1.4
   */
  public void buildRecord(HttpServletRequest request, int parseItem) {

    this.setType(request.getParameter("textmessage" + parseItem + "type"));
    if (request.getParameter("textmessage" + parseItem + "id") != null) {
      this.setId(request.getParameter("textmessage" + parseItem + "id"));
    }
    this.setTextMessageAddress(
        request.getParameter("textmessage" + parseItem + "address"));
    if (request.getParameter("textmessage" + parseItem + "delete") != null) {
      String action = request.getParameter(
          "textmessage" + parseItem + "delete").toLowerCase();
      if (action.equals("on")) {
        this.setEnabled(false);
      }
    }
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public String toString() {
    return (
        "=[Email Address]=============================================\r\n" +
        " Id: " + this.getId() + "\r\n" +
        " Org Id: " + this.getOrgId() + "\r\n" +
        " Contact Id: " + this.getContactId() + "\r\n" +
        " Type: " + this.getType() + "\r\n" +
        " Type Name: " + this.getTypeName() + "\r\n" +
        " Email: " + this.getTextMessageAddress() + "\r\n" +
        " Entered By: " + this.getEnteredBy() + "\r\n" +
        " Modified By: " + this.getModifiedBy() + "\r\n"
        );
  }
}

