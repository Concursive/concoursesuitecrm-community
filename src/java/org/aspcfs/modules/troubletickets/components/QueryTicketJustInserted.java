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
public class QueryTicketJustInserted extends ObjectHookComponent implements ComponentInterface {

  /**
   *  Gets the description attribute of the QueryTicketJustInserted object
   *
   *@return    The description value
   */
  public String getDescription() {
    return "Was the ticket just inserted?";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public boolean execute(ComponentContext context) {
    return ((Ticket) context.getThisObject() != null &&
        (Ticket) context.getPreviousObject() == null);
  }
}

