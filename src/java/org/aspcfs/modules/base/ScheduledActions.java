package org.aspcfs.modules.base;

/**
 *  Description of the Interface
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
  public void setAlertRangeStart(java.sql.Date startDate);


  /**
   *  Sets the alertRangeEnd attribute of the ScheduledActions object
   *
   *@param  endDate  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(java.sql.Date endDate);
}

