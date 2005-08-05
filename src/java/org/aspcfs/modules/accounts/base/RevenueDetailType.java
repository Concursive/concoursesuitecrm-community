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
package org.aspcfs.modules.accounts.base;

import java.sql.ResultSet;

/**
 * Description of the Class
 *
 * @author Mathur
 * @version $Id$
 * @created January 13, 2003
 */
public class RevenueDetailType {

  private int id = 0;
  private String description = null;
  private boolean enabled = true;


  /**
   * Constructor for the RevenueDetailType object
   */
  public RevenueDetailType() {
  }


  /**
   * Constructor for the RevenueDetailType object
   *
   * @param rs Description of the Parameter
   * @throws java.sql.SQLException Description of the Exception
   */
  public RevenueDetailType(ResultSet rs) throws java.sql.SQLException {
    id = rs.getInt("code");
    description = rs.getString("description");
    enabled = rs.getBoolean("enabled");
  }


  /**
   * Sets the id attribute of the RevenueDetailType object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the RevenueDetailType object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the description attribute of the RevenueDetailType object
   *
   * @param tmp The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   * Sets the enabled attribute of the RevenueDetailType object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Gets the id attribute of the RevenueDetailType object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the description attribute of the RevenueDetailType object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Gets the enabled attribute of the RevenueDetailType object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }

}

