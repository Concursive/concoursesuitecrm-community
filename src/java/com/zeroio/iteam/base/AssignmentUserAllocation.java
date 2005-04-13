/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.zeroio.iteam.base;

import java.util.HashMap;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    October 29, 2004
 *@version    $Id: AssignmentUserAllocation.java,v 1.3 2004/10/29 05:14:39 matt
 *      Exp $
 */
public class AssignmentUserAllocation extends HashMap {

  private AssignmentProjectAllocation projectAllocation = null;
  private int userId = -1;
  // calculation breaks characteristics into days
  private HashMap estimatedDailyHours = new HashMap();
  private HashMap actualDailyHours = new HashMap();
  private DailyTimesheetList timesheet = null;


  /**
   *  Constructor for the AssignmentUserAllocation object
   */
  public AssignmentUserAllocation() { }


  /**
   *  Constructor for the AssignmentUserAllocation object
   *
   *@param  tmp  Description of the Parameter
   */
  public AssignmentUserAllocation(int tmp) {
    userId = tmp;
  }


  /**
   *  Gets the projectMap attribute of the AssignmentUserAllocation object
   *
   *@param  id  Description of the Parameter
   *@return     The projectMap value
   */
  public AssignmentProjectAllocation getProjectMap(int id) {
    Integer projectId = new Integer(id);
    AssignmentProjectAllocation projectMap = (AssignmentProjectAllocation) this.get(projectId);
    if (projectMap == null) {
      projectMap = new AssignmentProjectAllocation();
      this.put(projectId, projectMap);
    }
    projectMap.setUserAllocation(this);
    return projectMap;
  }


  /**
   *  Gets the projectAllocation attribute of the AssignmentUserAllocation
   *  object
   *
   *@return    The projectAllocation value
   */
  public AssignmentProjectAllocation getProjectAllocation() {
    return projectAllocation;
  }


  /**
   *  Sets the projectAllocation attribute of the AssignmentUserAllocation
   *  object
   *
   *@param  tmp  The new projectAllocation value
   */
  public void setProjectAllocation(AssignmentProjectAllocation tmp) {
    this.projectAllocation = tmp;
  }


  /**
   *  Gets the userId attribute of the AssignmentUserAllocation object
   *
   *@return    The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   *  Sets the userId attribute of the AssignmentUserAllocation object
   *
   *@param  tmp  The new userId value
   */
  public void setUserId(int tmp) {
    this.userId = tmp;
  }


  /**
   *  Sets the userId attribute of the AssignmentUserAllocation object
   *
   *@param  tmp  The new userId value
   */
  public void setUserId(String tmp) {
    this.userId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the estimatedDailyHours attribute of the AssignmentUserAllocation
   *  object
   *
   *@return    The estimatedDailyHours value
   */
  public HashMap getEstimatedDailyHours() {
    return estimatedDailyHours;
  }


  /**
   *  Sets the estimatedDailyHours attribute of the AssignmentUserAllocation
   *  object
   *
   *@param  tmp  The new estimatedDailyHours value
   */
  public void setEstimatedDailyHours(HashMap tmp) {
    this.estimatedDailyHours = tmp;
  }


  /**
   *  Gets the actualDailyHours attribute of the AssignmentUserAllocation object
   *
   *@return    The actualDailyHours value
   */
  public HashMap getActualDailyHours() {
    return actualDailyHours;
  }


  /**
   *  Sets the actualDailyHours attribute of the AssignmentUserAllocation object
   *
   *@param  tmp  The new actualDailyHours value
   */
  public void setActualDailyHours(HashMap tmp) {
    this.actualDailyHours = tmp;
  }

  public DailyTimesheetList getTimesheet() {
    if (timesheet == null) {
      timesheet = new DailyTimesheetList();
    }
    return timesheet;
  }

  public void setTimesheet(DailyTimesheetList timesheet) {
    this.timesheet = timesheet;
  }

  /**
   *  Adds a feature to the Entry attribute of the AssignmentUserAllocation
   *  object
   *
   *@param  assignment  The feature to be added to the Entry attribute
   */
  public void addEntry(Assignment assignment) {
    AssignmentAllocation alloc = new AssignmentAllocation(assignment, timesheet);
    if (alloc.getValid()) {
      this.update(alloc);
      //projectAllocation.update(alloc);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  alloc  Description of the Parameter
   */
  private void update(AssignmentAllocation alloc) {
    Iterator days = alloc.getEstimatedDailyHours().keySet().iterator();
    while (days.hasNext()) {
      String date = (String) days.next();
      Double hours = (Double) alloc.getEstimatedDailyHours().get(date);
      add(date, hours);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  date   Description of the Parameter
   *@param  hours  Description of the Parameter
   */
  private void add(String date, Double hours) {
    Double value = (Double) estimatedDailyHours.get(date);
    if (value == null) {
      value = new Double(0.0);
    }
    value = new Double(value.doubleValue() + hours.doubleValue());
    estimatedDailyHours.put(date, value);
  }
}

