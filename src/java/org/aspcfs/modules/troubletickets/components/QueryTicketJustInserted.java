//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfs.troubletickets.component;

import com.darkhorseventures.controller.*;
import com.darkhorseventures.cfsbase.*;

public class QueryTicketJustInserted extends ObjectHookComponent implements ComponentInterface {
  
  public String getDescription() {
    return "Was the ticket just inserted?";
  }
  
  public boolean execute(ComponentContext context) {
    return ((Ticket)context.getThisObject() != null &&
            (Ticket)context.getPreviousObject() == null);
  }
}
