//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfs.troubletickets.component;

import com.darkhorseventures.controller.*;
import com.darkhorseventures.cfsbase.*;

public class QueryTicketJustAssigned extends ObjectHookComponent implements ComponentInterface {
  
  public String getDescription() {
    return "Was this ticket just assigned or re-assigned?";
  }
  
  public boolean execute(ComponentContext context) {
    Ticket thisTicket = (Ticket)context.getThisObject();
    Ticket previousTicket = (Ticket)context.getPreviousObject();
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
