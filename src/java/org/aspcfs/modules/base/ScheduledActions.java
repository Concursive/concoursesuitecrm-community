package org.aspcfs.modules.base;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;

/**
 *  All scheduled actions should implement this interface to ensure the
 *  required properties for generating a list of scheduled actions are met.
 *
 *@author     mathur
 *@created    July 16, 2004
 *@version    $Id$
 */
public interface ScheduledActions {

  /**
   *  Sets the module attribute of the ScheduledActions object
   *
   *@param  thisModule  The new module value
   */
  public void setModule(CFSModule thisModule);


  /**
   *  Sets the context attribute of the ScheduledActions object
   *
   *@param  thisContext  The new context value
   */
  public void setContext(ActionContext thisContext);


  /**
   *  Sets the alertRangeStart attribute of the ScheduledActions object
   *
   *@param  startDate  The new alertRangeStart value
   */
  public void setAlertRangeStart(java.sql.Timestamp startDate);


  /**
   *  Sets the alertRangeEnd attribute of the ScheduledActions object
   *
   *@param  endDate  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(java.sql.Timestamp endDate);
}

