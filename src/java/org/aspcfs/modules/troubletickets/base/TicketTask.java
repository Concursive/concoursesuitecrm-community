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
package org.aspcfs.modules.troubletickets.base;

import org.aspcfs.modules.tasks.base.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Represents a Ticket Task which extends a Task
 *
 * @author akhi_m
 * @version $Id$
 * @created May 14, 2003
 */
public class TicketTask extends Task {
  private int ticketId = -1;


  /**
   * Sets the ticketId attribute of the TicketTask object
   *
   * @param ticketId The new ticketId value
   */
  public void setTicketId(int ticketId) {
    this.ticketId = ticketId;
  }


  /**
   * Gets the ticketId attribute of the TicketTask object
   *
   * @return The ticketId value
   */
  public int getTicketId() {
    return ticketId;
  }


  /**
   * Inserts a Ticket Task
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    try {
      db.setAutoCommit(false);
      boolean recordInserted = super.insert(db);
      if (recordInserted) {
        //make an entry of the link

        int i = 0;
        PreparedStatement pst = db.prepareStatement(
            "INSERT INTO tasklink_ticket (task_id , ticket_id) VALUES (?, ?) ");
        pst.setInt(++i, this.getId());
        pst.setInt(++i, this.getTicketId());
        pst.execute();
        pst.close();
        db.commit();
      }
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }
}

