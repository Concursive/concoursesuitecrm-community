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
package org.aspcfs.modules.communications.base;

import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id$
 * @created September 16, 2004
 */
public class ExcludedRecipient {

  private int id = -1;
  private int campaignId = -1;
  private int contactId = -1;


  /**
   * Constructor for the ExcludedRecipient object
   */
  public ExcludedRecipient() {
  }


  /**
   * Sets the id attribute of the ExcludedRecipient object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the ExcludedRecipient object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the campaignId attribute of the ExcludedRecipient object
   *
   * @param tmp The new campaignId value
   */
  public void setCampaignId(int tmp) {
    this.campaignId = tmp;
  }


  /**
   * Sets the campaignId attribute of the ExcludedRecipient object
   *
   * @param tmp The new campaignId value
   */
  public void setCampaignId(String tmp) {
    this.campaignId = Integer.parseInt(tmp);
  }


  /**
   * Sets the contactId attribute of the ExcludedRecipient object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Sets the contactId attribute of the ExcludedRecipient object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   * Gets the id attribute of the ExcludedRecipient object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the campaignId attribute of the ExcludedRecipient object
   *
   * @return The campaignId value
   */
  public int getCampaignId() {
    return campaignId;
  }


  /**
   * Gets the contactId attribute of the ExcludedRecipient object
   *
   * @return The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (campaignId == -1) {
      throw new SQLException("Campaign ID not specified");
    }
    if (contactId == -1) {
      throw new SQLException("Contact ID not specified");
    }
    id = DatabaseUtils.getNextSeq(db, "excluded_recipient_id_seq");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO excluded_recipient " +
        "(" + (id > -1 ? "id, " : "") + "campaign_id, contact_id) VALUES " +
        "(" + (id > -1 ? "?, " : "") + "?, ?) ");
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, campaignId);
    pst.setInt(++i, contactId);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "excluded_recipient_id_seq", id);
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (id == -1) {
      if (campaignId == -1) {
        throw new SQLException("Campaign ID not specified");
      }
      if (contactId == -1) {
        throw new SQLException("Contact ID not specified");
      }

      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM excluded_recipient " +
          "WHERE campaign_id = ? AND contact_id = ? ");
      pst.setInt(1, campaignId);
      pst.setInt(2, contactId);
      pst.execute();
      pst.close();
    } else {
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM excluded_recipient " +
          "WHERE id = ? ");
      pst.setInt(1, id);
      pst.execute();
      pst.close();
    }
    return true;
  }
}

