//Copyright 2003 Dark Horse Ventures

package org.aspcfs.modules.troubletickets.base;

import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import com.darkhorseventures.framework.beans.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.tasks.base.Task;
import org.aspcfs.modules.base.*;

/**
 *  Represents a Ticket Task which extends a Task
 *
 *@author     akhi_m
 *@created    May 14, 2003
 *@version    $Id$
 */
public class TicketTask extends Task {
  private int ticketId = -1;


  /**
   *  Sets the ticketId attribute of the TicketTask object
   *
   *@param  ticketId  The new ticketId value
   */
  public void setTicketId(int ticketId) {
    this.ticketId = ticketId;
  }


  /**
   *  Gets the ticketId attribute of the TicketTask object
   *
   *@return    The ticketId value
   */
  public int getTicketId() {
    return ticketId;
  }


  /**
   *  Inserts a Ticket Task
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (!isValid(db)) {
      return false;
    }
    try {
      db.setAutoCommit(false);
      boolean recordInserted = super.insert(db);
      if (recordInserted) {
        //make an entry of the link

        int i = 0;
        PreparedStatement pst = db.prepareStatement("INSERT INTO tasklink_ticket (task_id , ticket_id) VALUES (?, ?) ");
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

