//Copyright 2003 Dark Horse Ventures

package org.aspcfs.modules.troubletickets.components;

import org.aspcfs.controller.*;
import org.aspcfs.apps.workFlowManager.*;
import org.aspcfs.controller.objectHookManager.*;
import org.aspcfs.modules.troubletickets.base.TicketList;
import java.sql.*;
import org.aspcfs.modules.base.Constants;

/**
 *  Generates a list of tickets based on specified parameters
 *
 *@author     matt rajkowski
 *@created    May 8, 2003
 *@version    $Id: GenerateTicketList.java,v 1.1 2003/05/16 18:55:58 mrajkowski
 *      Exp $
 */
public class GenerateTicketList extends ObjectHookComponent implements ComponentInterface {

  public final static String ONLY_OPEN = "ticketList.onlyOpen";
  public final static String ONLY_ASSIGNED = "ticketList.onlyAssigned";
  public final static String ONLY_UNASSIGNED = "ticketList.onlyUnassigned";
  public final static String MINUTES_OLDER_THAN = "ticketList.minutesOlderThan";
  public final static String LAST_ANCHOR = "ticketList.lastAnchor";
  public final static String NEXT_ANCHOR = "ticketList.nextAnchor";


  /**
   *  Gets the description attribute of the GenerateTicketList object
   *
   *@return    The description value
   */
  public String getDescription() {
    return "Generate a list of tickets based on specified parameters.  Are there any tickets matching the parameters?";
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
    if (context.hasParameter(GenerateTicketList.LAST_ANCHOR) || context.hasParameter(GenerateTicketList.NEXT_ANCHOR)) {
      //Tell ticketList to get a recent list only, eliminating previously reported tickets
      tickets.setSyncType(Constants.SYNC_QUERY);
      tickets.setLastAnchor(context.getParameter(GenerateTicketList.LAST_ANCHOR));
      tickets.setNextAnchor(context.getParameter(GenerateTicketList.NEXT_ANCHOR));
    }
    if (context.hasParameter(GenerateTicketList.MINUTES_OLDER_THAN)) {
      tickets.setMinutesOlderThan(context.getParameterAsInt(GenerateTicketList.MINUTES_OLDER_THAN));
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      tickets.buildList(db);
      context.setObjects(tickets);
      if (tickets.size() > 0) {
        result = true;
      }
    } catch (SQLException e) {
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return result;
  }
}

