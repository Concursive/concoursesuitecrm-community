package org.aspcfs.modules.base;

/**
 *  All scheduled actions should implement this interface to ensure the
 *  required properties for generating a list of scheduled actions are met.
 *
 *@author     mathur
 *@version    $Id$
 */
public interface ScheduledActions {
  /**
   *  Sets the userId attribute of the ScheduledActions object
   *
   *@param  userId  The new userId value
   */
  public void setUserId(int userId);


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

