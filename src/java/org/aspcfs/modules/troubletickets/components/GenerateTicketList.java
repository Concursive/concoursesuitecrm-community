//Copyright 2003 Dark Horse Ventures

package org.aspcfs.modules.troubletickets.components;

import org.aspcfs.controller.*;
import org.aspcfs.apps.workFlowManager.*;
import org.aspcfs.controller.objectHookManager.*;
import org.aspcfs.modules.troubletickets.base.TicketList;
import java.sql.*;

/**
 *  Generates a list of tickets based on specified parameters
 *
 *@author     matt rajkowski
 *@created    May 8, 2003
 *@version    $Id$
 */
public class GenerateTicketList extends ObjectHookComponent implements ComponentInterface {

  public final static String ONLY_OPEN = "ticketList.onlyOpen";
  public final static String ONLY_ASSIGNED = "ticketList.onlyAssigned";
  public final static String ONLY_UNASSIGNED = "ticketList.onlyUnassigned";
  public final static String AGE_IN_MINUTES = "ticketList.ageInMinutes";

  /**
   *  Gets the description attribute of the GenerateTicketList object
   *
   *@return    The description value
   */
  public String getDescription() {
    return "Generate a list of tickets based on specified parameters";
  }


  /**
   *  Builds a TicketList, based on context parameters, then puts the list in
   *  the context to be used by other components
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public boolean execute(ComponentContext context) {
    boolean result = false;
    TicketList tickets = new TicketList();
    tickets.setOnlyOpen(context.getParameterAsBoolean(GenerateTicketList.ONLY_OPEN));
    if (context.hasParameter(GenerateTicketList.ONLY_ASSIGNED)) {
      tickets.setOnlyAssigned(context.getParameterAsBoolean(GenerateTicketList.ONLY_ASSIGNED));
    }
    if (context.hasParameter(GenerateTicketList.ONLY_UNASSIGNED)) {
      tickets.setOnlyUnassigned(context.getParameterAsBoolean(GenerateTicketList.ONLY_UNASSIGNED));
    }
    //tickets.setAgeInMinutes(context.getParameterAsInt(GenerateTicketList.AGE_IN_MINUTES));
    Connection db = null;
    try {
      db = this.getConnection(context);
      tickets.buildList(db);
      context.setObjects(tickets);
      result = true;
    } catch (SQLException e) {
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return result;
  }
}

