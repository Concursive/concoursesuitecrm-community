package org.aspcfs.modules.mycfs.base;

import java.util.*;
import org.aspcfs.modules.pipeline.base.OpportunityComponent;
import org.aspcfs.modules.pipeline.base.OpportunityComponentList;

/**
 *  Description of the Class
 *
 *@author     Mathur
 *@created    October 23, 2003
 *@version    $Id$
 */
public class OpportunityEventList {
  OpportunityComponentList alertOpps = new OpportunityComponentList();
  int size = 0;


  /**
   *  Sets the size attribute of the TaskEventList object
   *
   *@param  size  The new size value
   */
  public void setSize(int size) {
    this.size = size;
  }


  /**
   *  Sets the size attribute of the TaskEventList object
   *
   *@param  size  The new size value
   */
  public void setSize(Integer size) {
    this.size = size.intValue();
  }


  /**
   *  Sets the alertOpps attribute of the OpportunityEventList object
   *
   *@param  alertOpps  The new alertOpps value
   */
  public void setAlertOpps(OpportunityComponentList alertOpps) {
    this.alertOpps = alertOpps;
  }


  /**
   *  Gets the alertOpps attribute of the OpportunityEventList object
   *
   *@return    The alertOpps value
   */
  public OpportunityComponentList getAlertOpps() {
    return alertOpps;
  }


  /**
   *  Gets the size attribute of the TaskEventList object
   *
   *@return    The size value
   */
  public int getSize() {
    return size;
  }


  /**
   *  Gets the sizeString attribute of the TaskEventList object
   *
   *@return    The sizeString value
   */
  public String getSizeString() {
    return String.valueOf(size);
  }


  /**
   *  Adds a call to the list dealert on the status of the Task
   *
   *@param  thisOpp  The feature to be added to the Event attribute
   */
  public void addEvent(OpportunityComponent thisOpp) {
    if (thisOpp != null) {
      alertOpps.add(thisOpp);
    }
  }
}

