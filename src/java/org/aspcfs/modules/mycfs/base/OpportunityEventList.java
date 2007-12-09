/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.mycfs.base;

import org.aspcfs.modules.pipeline.base.OpportunityComponent;
import org.aspcfs.modules.pipeline.base.OpportunityComponentList;

/**
 * Description of the Class
 *
 * @author Mathur
 * @version $Id: OpportunityEventList.java,v 1.2 2004/08/04 20:01:56
 *          mrajkowski Exp $
 * @created October 23, 2003
 */
public class OpportunityEventList {
  OpportunityComponentList alertOpps = new OpportunityComponentList();
  int size = 0;


  /**
   * Sets the size attribute of the TaskEventList object
   *
   * @param size The new size value
   */
  public void setSize(int size) {
    this.size = size;
  }


  /**
   * Sets the size attribute of the TaskEventList object
   *
   * @param size The new size value
   */
  public void setSize(Integer size) {
    this.size = size.intValue();
  }


  /**
   * Sets the alertOpps attribute of the OpportunityEventList object
   *
   * @param alertOpps The new alertOpps value
   */
  public void setAlertOpps(OpportunityComponentList alertOpps) {
    this.alertOpps = alertOpps;
  }


  /**
   * Gets the alertOpps attribute of the OpportunityEventList object
   *
   * @return The alertOpps value
   */
  public OpportunityComponentList getAlertOpps() {
    return alertOpps;
  }


  /**
   * Gets the size attribute of the TaskEventList object
   *
   * @return The size value
   */
  public int getSize() {
    return size;
  }


  /**
   * Gets the sizeString attribute of the TaskEventList object
   *
   * @return The sizeString value
   */
  public String getSizeString() {
    return String.valueOf(size);
  }


  /**
   * Adds a call to the list dealert on the status of the Task
   *
   * @param thisOpp The feature to be added to the Event attribute
   */
  public void addEvent(OpportunityComponent thisOpp) {
    if (thisOpp != null) {
      alertOpps.add(thisOpp);
    }
  }
}

