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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: AssignmentAllocation.java,v 1.3 2004/10/29 05:14:39 matt Exp
 *          $
 * @created October 29, 2004
 */
public class AssignmentAllocation {

  private boolean valid = false;
  // allocation characteristics
  private Timestamp startDate = null;
  private Timestamp endDate = null;
  private double totalHours = 0;
  private int percentComplete = 0;
  // calculation breaks characteristics into days
  private HashMap estimatedDailyHours = new HashMap();
  private HashMap actualDailyHours = new HashMap();


  /**
   * Gets the valid attribute of the AssignmentAllocation object
   *
   * @return The valid value
   */
  public boolean getValid() {
    return valid;
  }


  /**
   * Sets the valid attribute of the AssignmentAllocation object
   *
   * @param tmp The new valid value
   */
  public void setValid(boolean tmp) {
    this.valid = tmp;
  }


  /**
   * Gets the startDate attribute of the AssignmentAllocation object
   *
   * @return The startDate value
   */
  public Timestamp getStartDate() {
    return startDate;
  }


  /**
   * Sets the startDate attribute of the AssignmentAllocation object
   *
   * @param tmp The new startDate value
   */
  public void setStartDate(Timestamp tmp) {
    this.startDate = tmp;
  }


  /**
   * Gets the endDate attribute of the AssignmentAllocation object
   *
   * @return The endDate value
   */
  public Timestamp getEndDate() {
    return endDate;
  }


  /**
   * Sets the endDate attribute of the AssignmentAllocation object
   *
   * @param tmp The new endDate value
   */
  public void setEndDate(Timestamp tmp) {
    this.endDate = tmp;
  }


  /**
   * Gets the totalHours attribute of the AssignmentAllocation object
   *
   * @return The totalHours value
   */
  public double getTotalHours() {
    return totalHours;
  }


  /**
   * Sets the totalHours attribute of the AssignmentAllocation object
   *
   * @param tmp The new totalHours value
   */
  public void setTotalHours(double tmp) {
    this.totalHours = tmp;
  }


  /**
   * Gets the percentComplete attribute of the AssignmentAllocation object
   *
   * @return The percentComplete value
   */
  public int getPercentComplete() {
    return percentComplete;
  }


  /**
   * Sets the percentComplete attribute of the AssignmentAllocation object
   *
   * @param tmp The new percentComplete value
   */
  public void setPercentComplete(int tmp) {
    this.percentComplete = tmp;
  }


  /**
   * Gets the estimatedDailyHours attribute of the AssignmentAllocation object
   *
   * @return The estimatedDailyHours value
   */
  public HashMap getEstimatedDailyHours() {
    return estimatedDailyHours;
  }


  /**
   * Sets the estimatedDailyHours attribute of the AssignmentAllocation object
   *
   * @param tmp The new estimatedDailyHours value
   */
  public void setEstimatedDailyHours(HashMap tmp) {
    this.estimatedDailyHours = tmp;
  }


  /**
   * Gets the actualDailyHours attribute of the AssignmentAllocation object
   *
   * @return The actualDailyHours value
   */
  public HashMap getActualDailyHours() {
    return actualDailyHours;
  }


  /**
   * Sets the actualDailyHours attribute of the AssignmentAllocation object
   *
   * @param tmp The new actualDailyHours value
   */
  public void setActualDailyHours(HashMap tmp) {
    this.actualDailyHours = tmp;
  }


  /**
   * Constructor for the AssignmentAllocation object
   *
   * @param assignment Description of the Parameter
   */
  public AssignmentAllocation(Assignment assignment, DailyTimesheetList timesheet) {
    // raw data
    startDate = assignment.getEstStartDate();
    if (startDate == null) {
      startDate = assignment.getStartDate();
    }
    endDate = assignment.getDueDate();
    if (endDate == null) {
      endDate = assignment.getCompleteDate();
    }
    totalHours = assignment.getEstimatedLoeHours();
    if (assignment.getActualLoeHours() > 0) {
      totalHours = assignment.getActualLoeHours();
    }
    if (startDate != null && endDate != null && endDate.before(startDate)) {
      valid = false;
    } else if (startDate == null || endDate == null) {
      valid = false;
      // On-going...
      if (startDate != null) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTimeInMillis(startDate.getTime());
        double numberOfDays = 112;
        double avgPerDay = 1;
        if (totalHours > 0) {
          if (totalHours < 8) {
            numberOfDays = 1;
          } else {
            numberOfDays = (totalHours / 8);
          }
          avgPerDay = totalHours / numberOfDays;
        }
        // plot the days
        for (int i = 0; i < numberOfDays;) {
          if (timesheet.isWorkable(startCal)) {
            i++;
            add(startCal, avgPerDay);
          }
          startCal.add(Calendar.DATE, 1);
        }
        endDate = new Timestamp(startCal.getTimeInMillis());
        valid = true;
      } else if (endDate != null) {
        Calendar endCal = Calendar.getInstance();
        endCal.setTimeInMillis(endDate.getTime());
        if (totalHours > 0) {
          double numberOfDays = 1;
          if (totalHours < 8) {
            numberOfDays = 1;
          } else {
            numberOfDays = (totalHours / 8);
          }
          double avgPerDay = totalHours / numberOfDays;
          for (int i = 0; i < numberOfDays;) {
            if (timesheet.isWorkable(endCal)) {
              i++;
              add(endCal, avgPerDay);
            }
            endCal.add(Calendar.DATE, -1);
          }
          startDate = new Timestamp(endCal.getTimeInMillis());
          valid = true;
        }
      }
    } else {
      // all data is here
      double numberOfDays = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000) + 1;
      int workableDays = getWorkableDays(timesheet, startDate, endDate);
      double avgPerDay = 1;
      if (totalHours > 0) {
        avgPerDay = totalHours / workableDays;
      } else {
        avgPerDay = workableDays / 8;
      }
      Calendar startCal = Calendar.getInstance();
      startCal.setTimeInMillis(startDate.getTime());
      for (int i = 0; i < numberOfDays; i++) {
        if (timesheet.isWorkable(startCal)) {
          add(startCal, avgPerDay);
        }
        startCal.add(Calendar.DATE, 1);
      }
      // calculate data
      // 7/1/2004
      // 7/10/2004
      // 10 hours
      // hours per day = 1
      //

      //estimdatedDates.put(date, value);
      valid = true;
    }
  }

  private int getWorkableDays(DailyTimesheetList timesheet, Timestamp startDate, Timestamp endDate) {
    int count = 0;
    Calendar startCal = Calendar.getInstance();
    startCal.setTimeInMillis(startDate.getTime());
    while (startCal.getTime().before(endDate) || startCal.getTime().equals(
        endDate)) {
      if (timesheet.isWorkable(startCal)) {
        ++count;
      }
      startCal.add(Calendar.DATE, 1);
    }
    return count;
  }


  /**
   * Description of the Method
   *
   * @param cal     Description of the Parameter
   * @param average Description of the Parameter
   */
  public void add(Calendar cal, double average) {
    SimpleDateFormat formatter = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
    formatter.applyPattern("M/d/yyyy");
    estimatedDailyHours.put(
        formatter.format(cal.getTime()), new Double(average));
  }
}

