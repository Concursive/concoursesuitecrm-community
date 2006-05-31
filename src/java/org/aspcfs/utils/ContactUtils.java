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
package org.aspcfs.utils;

import org.aspcfs.modules.base.EmailAddress;
import org.aspcfs.modules.base.EmailAddressList;
import org.aspcfs.modules.base.Import;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id$
 * @created March 14, 2005
 */
public class ContactUtils {
  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param lastName Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static String foundDuplicateLastName(Connection db, String lastName, int siteId) throws SQLException {
    if (lastName != null && !"".equals(lastName)) {
      ContactList contacts = new ContactList();
      contacts.setLastName(lastName);
      contacts.setSiteId(siteId);
      contacts.setExclusiveToSite(true);
      if (siteId == -1) {
        contacts.setIncludeAllSites(true);
      }
      contacts.buildList(db);
      if (contacts.size() > 1) {
        return ((Contact) contacts.get(0)).getNameFull();
      }
    }
    return "";
  }


  /**
   * Description of the Method
   *
   * @param db              Description of the Parameter
   * @param lastName        Description of the Parameter
   * @param userIdRange     Description of the Parameter
   * @param hasAcPermission Description of the Parameter
   * @param hasGcPermission Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static boolean hasDuplicateLastName(Connection db, String lastName, String userIdRange, boolean hasAcPermission, boolean hasGcPermission) throws SQLException {
    boolean result = false;
    int i = 0;
    if (lastName == null || "".equals(lastName)) {
      return result;
    }
    PreparedStatement pst = null;
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer(
        "SELECT COUNT(*) AS record_count " +
        "FROM contact " +
        "WHERE lead = ? " +
        "AND employee = ? " +
        "AND (status_id IS NULL OR status_id = ?) " +
        "AND (" + DatabaseUtils.toLowerCase(db) + "(namelast) = ? AND namelast IS NOT NULL) " +
        "AND trashed_date IS NULL " +
        "AND enabled = ? ");
    if (!hasAcPermission || !hasGcPermission) {
      sql.append("AND ( ");
    }
    if (!hasAcPermission) {
      sql.append("(org_id IS NULL " +
        "AND owner IN (" + userIdRange + ")) ");
    }
    if (!hasAcPermission && !hasGcPermission) {
      sql.append("OR ");
    }
    if (!hasGcPermission) {
      sql.append("org_id IS NOT NULL ");
    }
    if (!hasAcPermission || !hasGcPermission) {
      sql.append(") ");
    }
    pst = db.prepareStatement(sql.toString());
    pst.setBoolean(++i, false);
    pst.setBoolean(++i, false);
    pst.setInt(++i, Import.PROCESSED_APPROVED);
    pst.setString(++i, lastName.toLowerCase());
    pst.setBoolean(++i, true);
    rs = pst.executeQuery();
    if (rs.next()) {
      if (rs.getInt("record_count") > 0) {
        result = true;
      }
    }
    rs.close();
    pst.close();
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db          Description of the Parameter
   * @param companyName Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static ContactList foundDuplicateCompany(Connection db, String companyName, int siteId) throws SQLException {
    ContactList contacts = new ContactList();
    if (companyName != null && !"".equals(companyName)) {
      contacts.setCompany(companyName);
      contacts.setSiteId(siteId);
      contacts.setExclusiveToSite(true);
      if (siteId == -1) {
        contacts.setIncludeAllSites(true);
      }
      contacts.buildList(db);
    }
    return contacts;
  }


  /**
   * Description of the Method
   *
   * @param db                   Description of the Parameter
   * @param company              Description of the Parameter
   * @param userIdRange          Description of the Parameter
   * @param hasAccountPermission Description of the Parameter
   * @param hasGcPermission      Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static String hasDuplicateCompany(Connection db, String company, String userIdRange, boolean hasAccountPermission, boolean hasGcPermission) throws SQLException {
    StringBuffer result = new StringBuffer("");
    if (company == null || "".equals(company.trim())) {
      return result.toString();
    }
    int i = 0;
    PreparedStatement pst = null;
    ResultSet rs = null;
    if (hasGcPermission) {
      pst = db.prepareStatement(
          "SELECT COUNT(*) AS record_count " +
          "FROM contact " +
          "WHERE lead = ? " +
          "AND employee = ? " +
          "AND (status_id IS NULL OR status_id = ?) " +
          "AND enabled = ? " +
          "AND org_id IS NULL " +
          "AND (" + DatabaseUtils.toLowerCase(db) + "(company) = ? AND company IS NOT NULL) " +
          "AND trashed_date IS NULL " +
          "AND owner IN (" + userIdRange + ") ");
      pst.setBoolean(++i, false);
      pst.setBoolean(++i, false);
      pst.setInt(++i, Import.PROCESSED_APPROVED);
      pst.setBoolean(++i, true);
      pst.setString(++i, company.toLowerCase());
      rs = pst.executeQuery();
      if (rs.next()) {
        if (rs.getInt("record_count") > 0) {
          result.append("general_contact");
        }
      }
      rs.close();
      pst.close();
    }
    if (hasAccountPermission) {
      i = 0;
      pst = db.prepareStatement(
          "SELECT COUNT(*) AS record_count FROM organization " +
          "WHERE " + DatabaseUtils.toLowerCase(db) + "(name) = ? " +
          "AND trashed_date IS NULL " +
          "AND enabled = ? ");
      pst.setString(++i, company.toLowerCase());
      pst.setBoolean(++i, true);
      rs = pst.executeQuery();
      if (rs.next()) {
        int recordCount = rs.getInt("record_count");
        if (recordCount > 0) {
          if (!"".equals(result.toString())) {
            result.append("|");
          }
          result.append("account");
        }
      }
      rs.close();
      pst.close();
    }
    return result.toString();
  }


  /**
   * Description of the Method
   *
   * @param db           Description of the Parameter
   * @param emailAddress Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static String foundDuplicateEmailAddress(Connection db, String emailAddress, int siteId) throws SQLException {
    if (emailAddress != null && !"".equals(emailAddress)) {
      ContactList contacts = new ContactList();
      contacts.setEmailAddress(emailAddress);
      contacts.setSiteId(siteId);
      contacts.setExclusiveToSite(true);
      if (siteId == -1) {
        contacts.setIncludeAllSites(true);
      }
      contacts.buildList(db);
      if (contacts.size() > 1) {
        return ((Contact) contacts.get(0)).getNameFull();
      }
    }
    return "";
  }


  /**
   * Description of the Method
   *
   * @param db              Description of the Parameter
   * @param emailAddresses  Description of the Parameter
   * @param userIdRange     Description of the Parameter
   * @param hasGcPermission Description of the Parameter
   * @param hasAcPermission Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static HashMap hasDuplicateEmailAddresses(Connection db, String emailAddresses, String userIdRange, boolean hasGcPermission, boolean hasAcPermission) throws SQLException {
    int i = 0;
    HashMap map = new HashMap();
    if (emailAddresses == null || "".equals(emailAddresses.trim())) {
      return map;
    }
    PreparedStatement pst = null;
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer(
        "SELECT ce.email AS email, COUNT(c.contact_id) AS record_count " +
        "FROM contact_emailaddress ce " +
        "LEFT JOIN contact c ON (ce.contact_id = c.contact_id ) " +
        "AND c.lead = ? " +
        "AND c.employee = ? " +
        "AND (c.status_id IS NULL OR c.status_id = ?) " +
        "AND c.trashed_date IS NULL " +
        "AND c.enabled = ? " +
        "AND ce.email IN (" + StringUtils.parseToDbString(emailAddresses) + ") " +
        "AND c.owner IN (" + userIdRange + ") " +
        "GROUP BY ce.email ");
    if (!hasAcPermission) {
      sql.append("AND c.org_id IS NULL ");
    }
    if (!hasGcPermission) {
      sql.append("AND c.org_id IS NOT NULL ");
    }
    pst = db.prepareStatement(sql.toString());
    pst.setBoolean(++i, false);
    pst.setBoolean(++i, false);
    pst.setInt(++i, Import.PROCESSED_APPROVED);
    pst.setBoolean(++i, true);
    rs = pst.executeQuery();
    while (rs.next()) {
      String email = rs.getString("email");
      int recordCount = rs.getInt("record_count");
      if (recordCount > 0) {
        map.put(email, new Boolean(true));
      } else {
        map.put(email, new Boolean(false));
      }
    }
    rs.close();
    pst.close();
    return map;
  }


  /**
   * Description of the Method
   *
   * @param db             Description of the Parameter
   * @param emailAddresses Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static HashMap foundDuplicateEmailAddresses(Connection db, EmailAddressList emailAddresses, int siteId) throws SQLException {
    HashMap map = new HashMap();
    Iterator iterator = emailAddresses.iterator();
    while (iterator.hasNext()) {
      EmailAddress emailAddress = (EmailAddress) iterator.next();
      ContactList contacts = new ContactList();
      contacts.setSiteId(siteId);
      contacts.setExclusiveToSite(true);
      if (siteId == -1) {
        contacts.setIncludeAllSites(true);
      }
      contacts.setEmailAddress(emailAddress.getEmail());
      contacts.buildList(db);
      if (contacts.size() > 1) {
        map.put(
            emailAddress.getEmail(), ((Contact) contacts.get(0)).getNameFull());
      }
    }
    return map;
  }
}

