//Copyright 2002 Dark Horse Ventures

package org.aspcfs.modules.troubletickets.components;

import org.aspcfs.controller.*;
import org.aspcfs.apps.workFlowManager.*;
import org.aspcfs.controller.objectHookManager.*;
import org.aspcfs.modules.troubletickets.base.Ticket;

/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    January 14, 2003
 *@version    $Id$
 */
public class QueryTicketJustAssigned extends ObjectHookComponent implements ComponentInterface {

  /**
   *  Gets the description attribute of the QueryTicketJustAssigned object
   *
   *@return    The description value
   */
  public String getDescription() {
    return "Was the ticket just assigned or re-assigned?";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public boolean execute(ComponentContext context) {
    Ticket thisTicket = (Ticket) context.getThisObject();
    Ticket previousTicket = (Ticket) context.getPreviousObject();
    if (thisTicket != null) {
      if (previousTicket != null) {
        //Ticket was updated
        return ((thisTicket.getAssignedTo() != previousTicket.getAssignedTo())
             && thisTicket.getAssignedTo() > 0);
      } else {
        //Ticket was inserted
        return (thisTicket.getAssignedTo() > 0);
      }
    }
    return false;
  }
}

