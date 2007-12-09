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
package org.aspcfs.modules.setup.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Used for retrieving Registration items from the database
 *
 * @author matt rajkowski
 * @version $Id: RegistrationList.java,v 1.2 2003/11/21 22:05:54 mrajkowski
 *          Exp $
 * @created November 21, 2003
 */
public class RegistrationList extends ArrayList {
  public final static String tableName = "registration";
  public final static String uniqueField = "registration_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  private PagedListInfo pagedListInfo = null;

  /**
   * Sets the lastAnchor attribute of the RegistrationList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the RegistrationList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the RegistrationList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the RegistrationList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the RegistrationList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }

  /**
   * Sets the PagedListInfo attribute of the RegistrationList object. <p>
   * <p/>
   * The query results will be constrained to the PagedListInfo parameters.
   *
   * @param tmp The new PagedListInfo value
   * @since 1.1
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }

  /**
   * Gets the tableName attribute of the RegistrationList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the RegistrationList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Retrieves a specific entry from the database
   *
   * @param db      Description of the Parameter
   * @param email   Description of the Parameter
   * @param profile Description of the Parameter
   * @param enabled Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static Registration locate(Connection db, String email, String profile, boolean enabled) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
            "FROM registration " +
            "WHERE email = ? AND profile = ? AND enabled = ? ");
    pst.setString(1, email);
    pst.setString(2, profile);
    pst.setBoolean(3, enabled);
    ResultSet rs = pst.executeQuery();
    Registration previous = null;
    if (rs.next()) {
      previous = new Registration(rs);
    }
    rs.close();
    pst.close();
    return previous;
  }

}

