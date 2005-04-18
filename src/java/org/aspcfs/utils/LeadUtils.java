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

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.Constants;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    March 2, 2005
 * @version    $Id: LeadUtils.java,v 1.1.4.4 2005/03/17 18:12:57 mrajkowski Exp
 *      $
 */
public class LeadUtils {

  /**
   *  This method checks and sets the read status of the Lead. If the lead is
   *  being read by someone else, it returns false. If the lead is being read by
   *  the user or its not being read before, it returns true.
   *
   * @param  db                Description of the Parameter
   * @param  contactId         Description of the Parameter
   * @param  userId            The new readStatus value
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public static synchronized int setReadStatus(Connection db, int contactId, int userId) throws SQLException {
    if (contactId == -1) {
      return -1;
    }
    int readId = -1;
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        "SELECT user_id " +
        "FROM contact_lead_read_map clrm " +
        "WHERE clrm.contact_id = ? ");
    pst.setInt(1, contactId);
    rs = pst.executeQuery();
    if (rs.next()) {
      readId = DatabaseUtils.getInt(rs, "user_id");
    }
    rs.close();
    pst.close();

    if (readId == userId) {
      pst = db.prepareStatement("DELETE FROM contact_lead_skipped_map WHERE contact_id= ? AND user_id = ? ");
      pst.setInt(1, contactId);
      pst.setInt(2, userId);
      pst.execute();
      pst.close();
      return readId;
    } else if (readId != -1) {
      return readId;
    } else if (readId == -1) {
      pst = db.prepareStatement("DELETE FROM contact_lead_skipped_map WHERE contact_id= ? AND user_id = ? ");
      pst.setInt(1, contactId);
      pst.setInt(2, userId);
      pst.execute();
      pst.close();
      pst = db.prepareStatement("INSERT INTO contact_lead_read_map (contact_id, user_id) VALUES(?,?)");
      pst.setInt(1, contactId);
      pst.setInt(2, userId);
      pst.execute();
      pst.close();
      return userId;
    }
    return -1;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  contactId         Description of the Parameter
   * @param  userId            Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public static synchronized boolean skipLead(Connection db, int contactId, int userId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int mapId = -1;
    int readId = -1;

    //Check to see if the user is the owner of the record being skipped
    int ownerId = 0;
    pst = db.prepareStatement("SELECT owner FROM contact WHERE contact_id = ?");
    pst.setInt(1, contactId);
    rs = pst.executeQuery();
    if (rs.next()) {
      ownerId = DatabaseUtils.getInt(rs, "owner");
    }
    rs.close();
    pst.close();

    //Check the read_map table to see who is reading the lead
    pst = db.prepareStatement("SELECT map_id, user_id FROM contact_lead_read_map WHERE contact_id = ?");
    pst.setInt(1, contactId);
    rs = pst.executeQuery();
    if (rs.next()) {
      mapId = DatabaseUtils.getInt(rs, "map_id");
      readId = DatabaseUtils.getInt(rs, "user_id");
    }
    rs.close();
    pst.close();

    if (mapId == -1) {
      //This case is impossible unless there is a programming error
      return false;
    } else {
      if (userId != readId || ownerId == userId) {
        //User wants to skip the lead that has been read by another user.
        //OR User wants to skip the lead that has already been assigned to the user.
        //Allow the skip.
      } else if (userId == readId && ownerId == -1) {
        //Remove the mapping of the user and the contact from the read map
        pst = db.prepareStatement("DELETE FROM contact_lead_read_map WHERE map_id = ?");
        pst.setInt(1, mapId);
        pst.execute();
        pst.close();

        //User wants to skip the lead. Add an entry to the skipped table
        pst = db.prepareStatement("INSERT INTO contact_lead_skipped_map (contact_id, user_id) VALUES (?, ?)");
        pst.setInt(1, contactId);
        pst.setInt(2, userId);
        pst.execute();
        pst.close();
      }
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  contactId         Description of the Parameter
   * @param  userId            Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public static synchronized int cleanUpContact(Connection db, int contactId, int userId) throws SQLException {
    PreparedStatement pst = null;
    //delete the contact_id related record in the contact_lead_read_map
    pst = db.prepareStatement("DELETE FROM contact_lead_read_map WHERE contact_id = ?");
    pst.setInt(1, contactId);
    int size = pst.executeUpdate();
    pst.close();

    //delete the contact_id related record in the contact_lead_skipped_map
    pst = db.prepareStatement("DELETE FROM contact_lead_skipped_map WHERE contact_id = ?");
    pst.setInt(1, contactId);
    size = size + pst.executeUpdate();
    pst.close();

    return size;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  contactId         Description of the Parameter
   * @param  userId            Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public static synchronized boolean tryToAssignLead(Connection db, int contactId, int userId) throws SQLException {
    boolean lead = true;
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        "SELECT lead " +
        "FROM contact c " +
        "WHERE c.contact_id = ? ");
    pst.setInt(1, contactId);
    rs = pst.executeQuery();
    if (rs.next()) {
      lead = rs.getBoolean("lead");
    }
    rs.close();
    pst.close();

    if (!lead) {
      return false;
    }

    //delete the read status
    pst = db.prepareStatement("DELETE FROM contact_lead_read_map WHERE contact_id = ? ");
    pst.setInt(1, contactId);
    pst.execute();
    pst.close();

    pst = db.prepareStatement("UPDATE contact SET owner = ? WHERE contact_id = ?");
    pst.setInt(1, userId);
    pst.setInt(2, contactId);
    int result = pst.executeUpdate();
    pst.close();

    pst = db.prepareStatement("INSERT INTO contact_lead_read_map (contact_id, user_id) VALUES(?,?)");
    pst.setInt(1, contactId);
    pst.setInt(2, userId);
    pst.execute();
    pst.close();
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  contactId         Description of the Parameter
   * @param  userId            Description of the Parameter
   * @param  ownerId           Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public static synchronized boolean tryToAssignLead(Connection db, int contactId, int userId, int ownerId) throws SQLException {
    boolean lead = true;
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        "SELECT lead " +
        "FROM contact c " +
        "WHERE c.contact_id = ? ");
    pst.setInt(1, contactId);
    rs = pst.executeQuery();
    if (rs.next()) {
      lead = rs.getBoolean("lead");
    }
    rs.close();
    pst.close();

    if (!lead) {
      return false;
    }

    //delete the read status
    pst = db.prepareStatement("DELETE FROM contact_lead_read_map WHERE contact_id = ? ");
    pst.setInt(1, contactId);
    pst.execute();
    pst.close();

    pst = db.prepareStatement("UPDATE contact SET owner = ? WHERE contact_id = ?");
    pst.setInt(1, userId);
    pst.setInt(2, contactId);
    int result = pst.executeUpdate();
    pst.close();

    if (userId == ownerId) {
      pst = db.prepareStatement("INSERT INTO contact_lead_read_map (contact_id, user_id) VALUES(?,?)");
      pst.setInt(1, contactId);
      pst.setInt(2, userId);
      pst.execute();
      pst.close();
    }
    return true;
  }


  /**
   *  Gets the nextLead attribute of the LeadUtils class
   *
   * @param  db                Description of the Parameter
   * @param  contactId         Description of the Parameter
   * @param  criteria          Description of the Parameter
   * @return                   The nextLead value
   * @exception  SQLException  Description of the Exception
   */
  public static synchronized int getNextLead(Connection db, int contactId, ContactList criteria) throws SQLException {
    int nextContactId = -1;
    PreparedStatement pst = null;
    ResultSet rs = null;
    /**
     *  For the oldestFirst ordering, contactId is 0. If the newestFirst
     *  ordering is selected, oldestFirst is false. Hence contactId is
     *  Max(contact_id) + 1 if contactId = 0. Get the maximum contact_id in this
     *  case and set it to contactId
     */
    if (criteria.getOldestFirst() == Constants.FALSE && contactId == 0) {
      pst = db.prepareStatement("SELECT MAX(contact_id) as contact_id FROM contact ");
      rs = pst.executeQuery();
      if (rs.next()) {
        contactId = DatabaseUtils.getInt(rs, "contact_id") + 1;
      }
      rs.close();
      pst.close();
    }
    StringBuffer sql = new StringBuffer("");
    if (criteria.getOldestFirst() != Constants.FALSE) {
      /**
       *  In the oldest first ordering, get the minimum record greater than the
       *  contactId
       */
      sql.append("SELECT MIN(c.contact_id) AS contact_id ");
    } else {
      /**
       *  In the newest first ordering, get the maximum record less than the
       *  contactId
       */
      sql.append("SELECT MAX(c.contact_id) AS contact_id ");
    }
    sql.append("FROM contact c ");
    if (criteria.getOldestFirst() != Constants.FALSE) {
      sql.append("WHERE c.contact_id > ? ");
    } else {
      sql.append("WHERE c.contact_id < ? ");
    }
    createFilter(db, sql, criteria);
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, 1, contactId);
    prepareFilter(pst, criteria);
    rs = pst.executeQuery();
    if (rs.next()) {
      /**
       *  If the required contactId does not exist, DatabaseUtils ensures that
       *  the returned id is -1
       */
      nextContactId = DatabaseUtils.getInt(rs, "contact_id");
    }
    rs.close();
    pst.close();
    return nextContactId;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  sqlFilter         Description of the Parameter
   * @param  criteria          Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  private static void createFilter(Connection db, StringBuffer sqlFilter, ContactList criteria) throws SQLException {
    int owner = criteria.getOwner();
    int leadStatus = criteria.getLeadStatus();
    int leadsOnly = criteria.getLeadsOnly();
    int readBy = criteria.getReadBy();
    int source = criteria.getSource();
    int rating = criteria.getRating();
    int leadStatusExists = criteria.getLeadStatusExists();
    int postalCode = criteria.getPostalCode();
    int employeesOnly = criteria.getEmployeesOnly();
    boolean ownerOrReader = criteria.getOwnerOrReader();
    int hasConversionDate = criteria.getHasConversionDate();
    String country = criteria.getCountry();
    String emailAddress = criteria.getEmailAddress();
    Timestamp enteredStart = criteria.getEnteredStart();
    Timestamp enteredEnd = criteria.getEnteredEnd();
    Timestamp conversionDateStart = criteria.getConversionDateStart();
    Timestamp conversionDateEnd = criteria.getConversionDateEnd();

    sqlFilter.append("AND c.lead = ? ");

    if (owner != -1) {
      sqlFilter.append("AND c.owner = ? ");
    }

    if (leadStatus > 0 && employeesOnly == Constants.UNDEFINED) {
      if (leadStatus == Contact.LEAD_UNPROCESSED || leadStatus == Contact.LEAD_TRASHED || leadStatus == Contact.LEAD_ASSIGNED) {
        sqlFilter.append("AND c.lead_status = ? ");
      }
    } else if (leadsOnly == Constants.TRUE && leadStatus == Contact.LEAD_UNREAD && readBy == -1 && !ownerOrReader && employeesOnly == Constants.UNDEFINED) {
      sqlFilter.append("AND c.lead_status = ? ");
      sqlFilter.append(
          "AND c.contact_id NOT IN ( " +
          "SELECT clm.contact_id AS contact_id FROM contact_lead_read_map clm WHERE clm.user_id <> ? ) " +
          "AND c.contact_id NOT IN ( " +
          "SELECT clsm.contact_id AS contact_id FROM contact_lead_skipped_map clsm WHERE clsm.user_id = ?) " +
          "");
    } else if (leadStatus == -1 && readBy == -1 && employeesOnly == Constants.UNDEFINED && leadsOnly == Constants.TRUE) {
      sqlFilter.append("AND c.lead_status IN (?, ?, ?) ");
    }
    if (source > -1) {
      sqlFilter.append("AND c.source = ? ");
    }

    if (rating > -1) {
      sqlFilter.append("AND c.rating = ? ");
    }

    if (leadsOnly == Constants.TRUE && readBy > -1 && !ownerOrReader) {
      sqlFilter.append(
          "AND c.contact_id NOT IN ( " +
          "SELECT clsm.contact_id AS contact_id FROM contact_lead_skipped_map clsm WHERE clsm.user_id = ?) " +
          "AND c.contact_id IN ( " +
          "SELECT clrm.contact_id AS contact_id FROM contact_lead_read_map clrm WHERE clrm.user_id = ? ) ");
    }

    if (leadStatusExists == Constants.TRUE) {
      sqlFilter.append("AND c.lead_status IS NOT NULL ");
    } else if (leadStatusExists == Constants.FALSE) {
      sqlFilter.append("AND c.lead_status IS NULL ");
    }

    if (enteredStart != null) {
      sqlFilter.append("AND c.entered >= ? ");
    }

    if (enteredEnd != null) {
      sqlFilter.append("AND c.entered <= ? ");
    }

    if (conversionDateStart != null) {
      sqlFilter.append("AND c.conversion_date >= ? ");
    }

    if (conversionDateEnd != null) {
      sqlFilter.append("AND c.conversion_date <= ? ");
    }

    if (emailAddress != null) {
      sqlFilter.append("AND c.contact_id IN (SELECT cc.contact_id FROM " +
          "contact cc LEFT JOIN contact_emailaddress ce ON (cc.contact_id = ce.contact_id ) " +
          "WHERE cc.contact_id = c.contact_id AND ce.email = ? )");
    }

    if (postalCode != -1) {
      sqlFilter.append("AND c.contact_id IN (" +
          "SELECT cc.contact_id FROM contact cc LEFT JOIN contact_address ca " +
          "ON (cc.contact_id = ca.contact_id) " +
          "WHERE ca.postalcode = ? ) ");
    }

    if (hasConversionDate == Constants.TRUE) {
      sqlFilter.append("AND c.conversion_date IS NOT NULL ");
    } else if (hasConversionDate == Constants.FALSE) {
      sqlFilter.append("AND c.conversion_date IS NULL ");
    }

    if (country != null && !"-1".equals(country)) {
      sqlFilter.append("AND c.contact_id IN (SELECT cc.contact_id FROM " +
          "contact cc LEFT JOIN contact_address ca ON (cc.contact_id = ca.contact_id) " +
          "WHERE ca.country = ? ) ");
    }

    if (ownerOrReader) {
      sqlFilter.append(
          "AND c.contact_id NOT IN ( " +
          "SELECT clsm.contact_id AS contact_id FROM contact_lead_skipped_map clsm WHERE clsm.user_id = ?) " +
          "AND (c.owner = ? OR c.contact_id IN (SELECT cr.contact_id AS contact_id " +
          "FROM contact_lead_read_map cr WHERE cr.user_id = ?)) ");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  pst               Description of the Parameter
   * @param  criteria          Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  private static int prepareFilter(PreparedStatement pst, ContactList criteria) throws SQLException {
    int i = 1;
    int owner = criteria.getOwner();
    int leadStatus = criteria.getLeadStatus();
    int leadsOnly = criteria.getLeadsOnly();
    int readBy = criteria.getReadBy();
    int source = criteria.getSource();
    int rating = criteria.getRating();
    int leadStatusExists = criteria.getLeadStatusExists();
    int postalCode = criteria.getPostalCode();
    int userId = criteria.getUserId();
    int employeesOnly = criteria.getEmployeesOnly();
    boolean ownerOrReader = criteria.getOwnerOrReader();
    int hasConversionDate = criteria.getHasConversionDate();
    String country = criteria.getCountry();
    String emailAddress = criteria.getEmailAddress();
    Timestamp enteredStart = criteria.getEnteredStart();
    Timestamp enteredEnd = criteria.getEnteredEnd();
    Timestamp conversionDateStart = criteria.getConversionDateStart();
    Timestamp conversionDateEnd = criteria.getConversionDateEnd();

    pst.setBoolean(++i, true);

    if (owner != -1) {
      pst.setInt(++i, owner);
    }
    if (leadStatus > 0 && employeesOnly == Constants.UNDEFINED) {
      if (leadStatus == Contact.LEAD_UNPROCESSED || leadStatus == Contact.LEAD_TRASHED || leadStatus == Contact.LEAD_ASSIGNED) {
        pst.setInt(++i, leadStatus);
      }
    } else if (leadsOnly == Constants.TRUE && leadStatus == Contact.LEAD_UNREAD && readBy == -1 && !ownerOrReader && employeesOnly == Constants.UNDEFINED) {
      pst.setInt(++i, Contact.LEAD_UNPROCESSED);
      pst.setInt(++i, userId);
      pst.setInt(++i, userId);
    } else if (leadStatus == -1 && readBy == -1 && employeesOnly == Constants.UNDEFINED && leadsOnly == Constants.TRUE) {
      pst.setInt(++i, Contact.LEAD_TRASHED);
      pst.setInt(++i, Contact.LEAD_ASSIGNED);
      pst.setInt(++i, Contact.LEAD_UNPROCESSED);
    }
    if (source > -1) {
      pst.setInt(++i, source);
    }
    if (rating > -1) {
      pst.setInt(++i, rating);
    }
    if (leadsOnly == Constants.TRUE && readBy > -1 && !ownerOrReader) {
      pst.setInt(++i, readBy);
      pst.setInt(++i, readBy);
    }

    if (enteredStart != null) {
      pst.setTimestamp(++i, enteredStart);
    }

    if (enteredEnd != null) {
      pst.setTimestamp(++i, enteredEnd);
    }

    if (conversionDateStart != null) {
      pst.setTimestamp(++i, conversionDateStart);
    }

    if (conversionDateEnd != null) {
      pst.setTimestamp(++i, conversionDateEnd);
    }

    if (emailAddress != null) {
      pst.setString(++i, emailAddress);
    }

    if (postalCode != -1) {
      pst.setInt(++i, postalCode);
    }

    if (country != null && !"-1".equals(country)) {
      pst.setString(++i, country);
    }

    if (ownerOrReader) {
      pst.setInt(++i, readBy);
      pst.setInt(++i, owner);
      pst.setInt(++i, readBy);
    }
    return i;
  }
}

