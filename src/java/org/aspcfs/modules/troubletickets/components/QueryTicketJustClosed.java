//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfs.troubletickets.component;

import com.darkhorseventures.controller.*;
import com.darkhorseventures.cfsbase.*;

public class QueryTicketJustClosed extends ObjectHookComponent implements ComponentInterface {
  
  public String getDescription() {
    return "Was the ticket just closed?";
  }
  
  public boolean execute(ComponentContext context) {
    Ticket thisTicket = (Ticket)context.getThisObject();
    Ticket previousTicket = (Ticket)context.getPreviousObject();
    if (context.isUpdate()) {
      return (thisTicket.getCloseIt() && !previousTicket.getCloseIt());
    } else if (context.isInsert()) {
      return thisTicket.getCloseIt();
    }
    return false;
  }
}
