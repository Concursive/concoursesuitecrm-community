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

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    October 29, 2004
 *@version    $Id: AssignmentProjectAllocation.java,v 1.3 2004/10/29 05:14:39
 *      matt Exp $
 */
public class AssignmentProjectAllocation extends HashMap {

  private AssignmentUserAllocation userAllocation = null;
  private int projectId = -1;
  // calculation breaks characteristics into days
  private HashMap estimatedDailyHours = new HashMap();
  private HashMap actualDailyHours = new HashMap();


  /**
   *  Constructor for the AssignmentProjectAllocation object
   */
  public AssignmentProjectAllocation() { }


  /**
   *  Constructor for the AssignmentProjectAllocation object
   *
   *@param  tmp  Description of the Parameter
   */
  public AssignmentProjectAllocation(int tmp) {
    projectId = tmp;
  }


  /**
   *  Gets the userMap attribute of the AssignmentProjectAllocation object
   *
   *@param  id  Description of the Parameter
   *@return     The userMap value
   */
  public AssignmentUserAllocation getUserMap(int id, Connection db, Timestamp startDate, Timestamp endDate) throws SQLException {
    Integer userId = new Integer(id);
    AssignmentUserAllocation userMap = (AssignmentUserAllocation) this.get(userId);
    if (userMap == null) {
      userMap = new AssignmentUserAllocation();
      // Generate the user's timesheet for display
      DailyTimesheetList timesheet = userMap.getTimesheet();
      timesheet.setUserId(id);
      timesheet.setStartDate(startDate);
      timesheet.setEndDate(endDate);
      timesheet.buildList(db);
      this.put(userId, userMap);
    }
    userMap.setProjectAllocation(this);
    return userMap;
  }


  /**
   *  Gets the userAllocation attribute of the AssignmentProjectAllocation
   *  object
   *
   *@return    The userAllocation value
   */
  public AssignmentUserAllocation getUserAllocation() {
    return userAllocation;
  }


  /**
   *  Sets the userAllocation attribute of the AssignmentProjectAllocation
   *  object
   *
   *@param  tmp  The new userAllocation value
   */
  public void setUserAllocation(AssignmentUserAllocation tmp) {
    this.userAllocation = tmp;
  }


  /**
   *  Gets the projectId attribute of the AssignmentProjectAllocation object
   *
   *@return    The projectId value
   */
  public int getProjectId() {
    return projectId;
  }


  /**
   *  Sets the projectId attribute of the AssignmentProjectAllocation object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   *  Sets the projectId attribute of the AssignmentProjectAllocation object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(String tmp) {
    this.projectId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the estimatedDailyHours attribute of the AssignmentProjectAllocation
   *  object
   *
   *@return    The estimatedDailyHours value
   */
  public HashMap getEstimatedDailyHours() {
    return estimatedDailyHours;
  }


  /**
   *  Sets the estimatedDailyHours attribute of the AssignmentProjectAllocation
   *  object
   *
   *@param  tmp  The new estimatedDailyHours value
   */
  public void setEstimatedDailyHours(HashMap tmp) {
    this.estimatedDailyHours = tmp;
  }


  /**
   *  Gets the actualDailyHours attribute of the AssignmentProjectAllocation
   *  object
   *
   *@return    The actualDailyHours value
   */
  public HashMap getActualDailyHours() {
    return actualDailyHours;
  }


  /**
   *  Sets the actualDailyHours attribute of the AssignmentProjectAllocation
   *  object
   *
   *@param  tmp  The new actualDailyHours value
   */
  public void setActualDailyHours(HashMap tmp) {
    this.actualDailyHours = tmp;
  }


  /**
   *  Adds a feature to the Entry attribute of the AssignmentProjectAllocation
   *  object
   *
   *@param  assignment  The feature to be added to the Entry attribute
   */
  public void addEntry(Assignment assignment, DailyTimesheetList timesheet) {
    AssignmentAllocation alloc = new AssignmentAllocation(assignment, timesheet);
    if (alloc.getValid()) {
      this.update(alloc);
      //userAllocation.update(alloc);
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

