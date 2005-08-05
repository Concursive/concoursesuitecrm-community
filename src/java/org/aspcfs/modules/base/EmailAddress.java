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

import org.aspcfs.utils.DateUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents an email address.
 *
 * @author mrajkowski
 * @version $Id$
 * @created September 6, 2001
 */
public class EmailAddress {

  public final static int BUSINESS = 1;
  protected boolean isContact = false;
  private int id = -1;
  private int orgId = -1;
  private int contactId = -1;
  private int type = -1;
  private String typeName = "Main";
  private String email = "";
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private boolean enabled = true;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private boolean primaryEmail = false;


  /**
   * Sets the Id attribute of the EmailAddress object
   *
   * @param tmp The new Id value
   * @since 1.4
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the Id attribute of the EmailAddress object
   *
   * @param tmp The new Id value
   * @since 1.4
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the OrgId attribute of the EmailAddress object
   *
   * @param tmp The new OrgId value
   * @since 1.4
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   * Sets the orgId attribute of the EmailAddress object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   * Sets the ContactId attribute of the EmailAddress object
   *
   * @param tmp The new ContactId value
   * @since 1.4
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Sets the contactId attribute of the EmailAddress object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   * Sets the Type attribute of the EmailAddress object
   *
   * @param tmp The new Type value
   * @since 1.4
   */
  public void setType(int tmp) {
    this.type = tmp;
  }


  /**
   * Sets the Type attribute of the EmailAddress object
   *
   * @param tmp The new Type value
   * @since 1.4
   */
  public void setType(String tmp) {
    this.type = Integer.parseInt(tmp);
  }


  /**
   * Sets the TypeName attribute of the EmailAddress object
   *
   * @param tmp The new TypeName value
   * @since 1.4
   */
  public void setTypeName(String tmp) {
    this.typeName = tmp;
  }


  /**
   * Sets the Email attribute of the EmailAddress object
   *
   * @param tmp The new Email value
   * @since 1.4
   */
  public void setEmail(String tmp) {
    this.email = tmp;
  }


  /**
   * Sets the EnteredBy attribute of the EmailAddress object
   *
   * @param tmp The new EnteredBy value
   * @since 1.4
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the EmailAddress object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the enabled attribute of the EmailAddress object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    enabled = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   * Sets the ModifiedBy attribute of the EmailAddress object
   *
   * @param tmp The new ModifiedBy value
   * @since 1.4
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the EmailAddress object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the Enabled attribute of the EmailAddress object
   *
   * @param tmp The new Enabled value
   * @since 1.4
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the primaryEmail attribute of the EmailAddress object
   *
   * @param tmp The new primaryEmail value
   */
  public void setPrimaryEmail(boolean tmp) {
    this.primaryEmail = tmp;
  }


  /**
   * Sets the primaryEmail attribute of the EmailAddress object
   *
   * @param tmp The new primaryEmail value
   */
  public void setPrimaryEmail(String tmp) {
    this.primaryEmail = ("primaryEmail".equals(tmp));
  }


  /**
   * Gets the Id attribute of the EmailAddress object
   *
   * @return The Id value
   * @since 1.4
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the OrgId attribute of the EmailAddress object
   *
   * @return The OrgId value
   * @since 1.4
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   * Gets the ContactId attribute of the EmailAddress object
   *
   * @return The ContactId value
   * @since 1.4
   */
  public int getContactId() {
    return contactId;
  }


  /**
   * Gets the Type attribute of the EmailAddress object
   *
   * @return The Type value
   * @since 1.4
   */
  public int getType() {
    return type;
  }


  /**
   * Gets the TypeName attribute of the EmailAddress object
   *
   * @return The TypeName value
   * @since 1.4
   */
  public String getTypeName() {
    return typeName;
  }


  /**
   * Gets the entered attribute of the EmailAddress object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the modified attribute of the EmailAddress object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Sets the entered attribute of the EmailAddress object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the modified attribute of the EmailAddress object
   *
   * @param tmp The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the entered attribute of the EmailAddress object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   * Sets the modified attribute of the EmailAddress object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   * Gets the Email attribute of the EmailAddress object
   *
   * @return The Email value
   * @since 1.4
   */
  public String getEmail() {
    return email;
  }


  /**
   * Gets the EnteredBy attribute of the EmailAddress object
   *
   * @return The EnteredBy value
   * @since 1.4
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the ModifiedBy attribute of the EmailAddress object
   *
   * @return The ModifiedBy value
   * @since 1.4
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the Enabled attribute of the EmailAddress object
   *
   * @return The Enabled value
   * @since 1.4
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the primaryEmail attribute of the EmailAddress object
   *
   * @return The primaryEmail value
   */
  public boolean getPrimaryEmail() {
    return primaryEmail;
  }


  /**
   * If an email address is filled in, and a type is selected, then the email
   * address is valid.
   *
   * @return The Valid value
   * @since 1.5
   */
  public boolean isValid() {
    return (type > -1 && email != null && !email.trim().equals(""));
  }


  /**
   * Description of the Method
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   * @since 1.4
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    this.setId(rs.getInt("emailaddress_id"));
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
    this.setType(rs.getInt("emailaddress_type"));
    if (rs.wasNull()) {
      this.setType(-1);
    }
    this.setTypeName(rs.getString("description"));
    this.setEmail(rs.getString("email"));
    this.setEntered(rs.getTimestamp("entered"));
    this.setEnteredBy(rs.getInt("enteredby"));

    if (enteredBy == -1) {
      this.setEnteredBy(0);
    }

    this.setModified(rs.getTimestamp("modified"));
    this.setModifiedBy(rs.getInt("modifiedby"));
    this.setPrimaryEmail(rs.getBoolean("primary_email"));

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
    this.setType(request.getParameter("email" + parseItem + "type"));
    if (request.getParameter("email" + parseItem + "id") != null) {
      this.setId(request.getParameter("email" + parseItem + "id"));
    }
    this.setEmail(request.getParameter("email" + parseItem + "address"));
    if (request.getParameter("email" + parseItem + "delete") != null) {
      String action = request.getParameter("email" + parseItem + "delete").toLowerCase();
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
        " Email: " + this.getEmail() + "\r\n" +
        " Entered By: " + this.getEnteredBy() + "\r\n" +
        " Modified By: " + this.getModifiedBy() + "\r\n"
        );
  }
}

