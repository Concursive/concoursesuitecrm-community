//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfs.troubletickets.component;

import com.darkhorseventures.controller.*;
import com.darkhorseventures.cfsbase.*;

public class QueryTicketJustUpdated extends Component implements ComponentInterface {
  
  public String getDescription() {
    return "Returns whether a ticket was just updated in CFS";
  }
  
  public boolean execute(ComponentContext context) {
    return ((Ticket)context.getThisObject() != null &&
            (Ticket)context.getPreviousObject() != null);
  }
}
