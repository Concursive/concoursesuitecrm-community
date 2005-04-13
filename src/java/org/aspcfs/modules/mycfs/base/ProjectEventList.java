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
package org.aspcfs.modules.mycfs.base;

import java.util.*;
import com.zeroio.iteam.base.Assignment;
import com.zeroio.iteam.base.AssignmentList;

/**
 *  Description of the Class
 *
 *@author     Ananth
 *@created    February 16, 2005
 */
public class ProjectEventList {
  AssignmentList pendingAssignments = new AssignmentList();
  int size = 0;


  /**
   *  Gets the pendingAssignments attribute of the ProjectEventList object
   *
   *@return    The pendingAssignments value
   */
  public AssignmentList getPendingAssignments() {
    return pendingAssignments;
  }


  /**
   *  Sets the pendingAssignments attribute of the ProjectEventList object
   *
   *@param  tmp  The new pendingAssignments value
   */
  public void setPendingAssignments(AssignmentList tmp) {
    this.pendingAssignments = tmp;
  }


  /**
   *  Gets the size attribute of the ProjectEventList object
   *
   *@return    The size value
   */
  public int getSize() {
    return size;
  }


  /**
   *  Sets the size attribute of the ProjectEventList object
   *
   *@param  tmp  The new size value
   */
  public void setSize(int tmp) {
    this.size = tmp;
  }


  /**
   *  Sets the size attribute of the ProjectEventList object
   *
   *@param  tmp  The new size value
   */
  public void setSize(String tmp) {
    this.size = Integer.parseInt(tmp);
  }


  /**
   *  Sets the size attribute of the ProjectEventList object
   *
   *@param  size  The new size value
   */
  public void setSize(Integer size) {
    this.size = size.intValue();
  }


  /**
   *  Gets the sizeString attribute of the ProjectEventList object
   *
   *@return    The sizeString value
   */
  public String getSizeString() {
    return String.valueOf(size);
  }


  /**
   *  Adds a feature to the Event attribute of the ProjectEventList object
   *
   *@param  thisAssignment  The feature to be added to the Event attribute
   */
  public void addEvent(Assignment thisAssignment) {
    if (thisAssignment != null) {
      pendingAssignments.add(thisAssignment);
    }
  }
}


