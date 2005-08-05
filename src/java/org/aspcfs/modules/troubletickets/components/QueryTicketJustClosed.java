/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.troubletickets.components;

import org.aspcfs.apps.workFlowManager.ComponentContext;
import org.aspcfs.apps.workFlowManager.ComponentInterface;
import org.aspcfs.controller.objectHookManager.ObjectHookComponent;
import org.aspcfs.modules.troubletickets.base.Ticket;

/**
 * Description of the Class
 *
 * @author mrajkowski
 * @version $Id: QueryTicketJustClosed.java,v 1.3 2003/01/14 21:23:13 akhi_m
 *          Exp $
 * @created January 14, 2003
 */
public class QueryTicketJustClosed extends ObjectHookComponent implements ComponentInterface {

  /**
   * Gets the description attribute of the QueryTicketJustClosed object
   *
   * @return The description value
   */
  public String getDescription() {
    return "Was the ticket just closed?";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean execute(ComponentContext context) {
    Ticket thisTicket = (Ticket) context.getThisObject();
    Ticket previousTicket = (Ticket) context.getPreviousObject();
    if (context.isUpdate()) {
      return (thisTicket.getCloseIt() && !previousTicket.getCloseIt());
    } else if (context.isInsert()) {
      return thisTicket.getCloseIt();
    }
    return false;
  }
}

