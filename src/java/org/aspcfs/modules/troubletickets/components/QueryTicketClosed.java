//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfs.troubletickets.component;

import com.darkhorseventures.controller.*;
import com.darkhorseventures.cfsbase.*;

public class QueryTicketClosed extends ObjectHookComponent implements ComponentInterface {
  
  public String getDescription() {
    return "Is the ticket closed?";
  }
  
  public boolean execute(ComponentContext context) {
    Ticket thisTicket = (Ticket)context.getThisObject();
    Ticket previousTicket = (Ticket)context.getPreviousObject();
    if (context.isInsert() || context.isUpdate()) {
      return thisTicket.isClosed();
    } else if (context.isDelete()) {
      return previousTicket.isClosed();
    } else {
      return false;
    }
  }
}
