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
package org.aspcfs.modules.base;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;

/**
 * All scheduled actions should implement this interface to ensure the required
 * properties for generating a list of scheduled actions are met.
 *
 * @author mathur
 * @version $Id: ScheduledActions.java,v 1.5 2004/08/04 20:01:56 mrajkowski
 *          Exp $
 * @created July 16, 2004
 */
public interface ScheduledActions {

  /**
   * Sets the module attribute of the ScheduledActions object
   *
   * @param thisModule The new module value
   */
  public void setModule(CFSModule thisModule);


  /**
   * Sets the context attribute of the ScheduledActions object
   *
   * @param thisContext The new context value
   */
  public void setContext(ActionContext thisContext);


  /**
   * Sets the alertRangeStart attribute of the ScheduledActions object
   *
   * @param startDate The new alertRangeStart value
   */
  public void setAlertRangeStart(java.sql.Timestamp startDate);


  /**
   * Sets the alertRangeEnd attribute of the ScheduledActions object
   *
   * @param endDate The new alertRangeEnd value
   */
  public void setAlertRangeEnd(java.sql.Timestamp endDate);
}

