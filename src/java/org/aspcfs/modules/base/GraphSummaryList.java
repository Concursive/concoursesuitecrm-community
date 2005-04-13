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

import org.aspcfs.utils.DatabaseUtils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Hashtable;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    October 30, 2001
 *@version    $Id: GraphSummaryList.java,v 1.7 2003/01/09 18:07:35 mrajkowski
 *      Exp $
 */
public class GraphSummaryList extends Hashtable {

  int id = -1;
  boolean isValid = false;
  String lastFileName = null;
  Hashtable values = new Hashtable();
  int size = -1;
  boolean isFutureDateRange = false;
  public final static int MONTH_RANGE = 0;
  public final static int DATE_RANGE = 1;
  public final static int YEAR_RANGE = 2;
  public final static int WEEK_RANGE = 3;
  public final static int TEN_DAY_RANGE = 4;


  /**
   *  Constructor for the GraphSummaryList object
   */
  public GraphSummaryList() {
    String[] valKeys = getRange(12);
    Double initialVal = new Double(0.0);
    for (int i = 0; i < 12; i++) {
      this.values.put(valKeys[i], initialVal);
    }
  }


  /**
   *  Constructor for the GraphSummaryList object
   *
   * @param  range     Description of the Parameter
   * @param  type      Description of the Parameter
   * @param  inFuture  Description of the Parameter
   */
  public GraphSummaryList(int range, int type, boolean inFuture) {
    this.isFutureDateRange = inFuture;
    this.setSize(range);
    String[] valKeys = new String[range];
    if (type == GraphSummaryList.MONTH_RANGE) {
      valKeys = getMonthRange(range);
    } else if (type == GraphSummaryList.YEAR_RANGE) {
      //valKeys = getYearRange(range,);
    } else if (type == GraphSummaryList.WEEK_RANGE) {
      valKeys = getWeekRange(range);
    } else if (type == GraphSummaryList.DATE_RANGE) {
      valKeys = getDayRange(range);
    } else if (type == GraphSummaryList.TEN_DAY_RANGE) {
      valKeys = getTenDayRange(range);
    }
    Double initialVal = new Double(0.0);
    for (int i = 0; i < range; i++) {
      this.values.put(valKeys[i], initialVal);
    }
  }


  /**
   *  Sets the LastFileName attribute of the GraphSummaryList object
   *
   *@param  lastFileName  The new LastFileName value
   */
  public void setLastFileName(String lastFileName) {
    this.lastFileName = lastFileName;
  }


  /**
   *  Sets the Id attribute of the GraphSummaryList object
   *
   *@param  id  The new Id value
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   *  Sets the Value attribute of the GraphSummaryList object
   *
   *@param  val        The new Value value
   *@param  yearMonth  The new value value
   */
  public void setValue(String yearMonth, Double val) {
    if (!this.values.containsKey(yearMonth)) {
      this.values.put(yearMonth, val);
    } else {
      addToValue(yearMonth, val);
    }
  }


  /**
   *  Sets the IsValid attribute of the GraphSummaryList object
   *
   *@param  isValid  The new IsValid value
   */
  public void setIsValid(boolean isValid) {
    this.isValid = isValid;
  }


  /**
   *  Gets the LastFileName attribute of the GraphSummaryList object
   *
   *@return    The LastFileName value
   */
  public String getLastFileName() {
    return lastFileName;
  }


  /**
   *  Gets the Id attribute of the GraphSummaryList object
   *
   *@return    The Id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the Value attribute of the GraphSummaryList object
   *
   *@param  which  Description of Parameter
   *@return        The Value value
   *@since
   */
  public Double getValue(String which) {
    if (!this.values.containsKey(which)) {
      return new Double(0.0);
    } else {
      return (Double) this.values.get(which);
    }
  }


  /**
   *  Gets the Range attribute of the GraphSummaryList object
   *
   *@param  size  Description of Parameter
   *@return       The Range value
   */
  public String[] getRange(int size) {
    String[] valKeys = new String[size];
    Calendar rightNow = Calendar.getInstance();
    int day = rightNow.get(Calendar.DATE);
    int year = rightNow.get(Calendar.YEAR);
    int month = rightNow.get(Calendar.MONTH);
    for (int x = 0; x < size; x++) {
      valKeys[x] = String.valueOf(year) + String.valueOf(month);
      rightNow.add(Calendar.MONTH, +1);
      year = rightNow.get(Calendar.YEAR);
      month = rightNow.get(Calendar.MONTH);
    }
    return valKeys;
  }


  /**
   *  Gets the monthRange attribute of the GraphSummaryList object
   *
   * @param  size  Description of the Parameter
   * @return       The monthRange value
   */
  public String[] getMonthRange(int size) {
    String[] valKeys = new String[size];
    Calendar rightNow = Calendar.getInstance();
    int year = rightNow.get(Calendar.YEAR);
    int month = rightNow.get(Calendar.MONTH);
    for (int x = 0; x < size; x++) {
      valKeys[x] = String.valueOf(year) + String.valueOf(month);
      if (isFutureDateRange) {
        rightNow.add(Calendar.MONTH, +1);
      } else {
        rightNow.add(Calendar.MONTH, -1);
      }
      year = rightNow.get(Calendar.YEAR);
      month = rightNow.get(Calendar.MONTH);
    }
    return valKeys;
  }


  /**
   *  Gets the tenDayRange attribute of the GraphSummaryList object
   *
   * @param  size  Description of the Parameter
   * @return       The tenDayRange value
   */
  public String[] getTenDayRange(int size) {
    String[] valKeys = new String[size];
    Calendar rightNow = Calendar.getInstance();
    int day = rightNow.get(Calendar.DATE);
    int year = rightNow.get(Calendar.YEAR);
    int month = rightNow.get(Calendar.MONTH);
    for (int x = 0; x < size; x++) {
      valKeys[x] = String.valueOf(year) + String.valueOf(month) + String.valueOf(day);
      if (day <= 10) {
        if (isFutureDateRange) {
          rightNow.add(Calendar.DATE, +(10 - rightNow.get(Calendar.DATE)));
        } else {
          rightNow.add(Calendar.DATE, -rightNow.get(Calendar.DATE));
        }
      } else if (day <= 20 && day > 10) {
        if (isFutureDateRange) {
          rightNow.add(Calendar.DATE, +(20 - rightNow.get(Calendar.DATE)));
        } else {
          rightNow.add(Calendar.DATE, -(rightNow.get(Calendar.DATE) - 10));
        }
      } else if (day <= 31 && day > 20) {
        if (isFutureDateRange) {
          rightNow.add(Calendar.DATE, +(getDaysInMonth(rightNow.get(Calendar.MONTH), rightNow.get(Calendar.YEAR)) - rightNow.get(Calendar.DATE)));
        } else {
          rightNow.add(Calendar.DATE, -(rightNow.get(Calendar.DATE) - 20));
        }
      }
      year = rightNow.get(Calendar.YEAR);
      month = rightNow.get(Calendar.MONTH);
      day = rightNow.get(Calendar.DATE);
    }
    return valKeys;
  }


  /**
   *  Gets the weekRange attribute of the GraphSummaryList object
   *
   * @param  size  Description of the Parameter
   * @return       The weekRange value
   */
  public String[] getWeekRange(int size) {
    String[] valKeys = new String[size];
    Calendar rightNow = Calendar.getInstance();
    rightNow.add(Calendar.DATE, -(rightNow.get(Calendar.DAY_OF_WEEK) - rightNow.getFirstDayOfWeek()));
    int day = rightNow.get(Calendar.DATE);
    int year = rightNow.get(Calendar.YEAR);
    int month = rightNow.get(Calendar.MONTH);
    for (int x = 0; x < size; x++) {
      valKeys[x] = String.valueOf(year) + String.valueOf(month) + String.valueOf(day);
      if (isFutureDateRange) {
        rightNow.add(Calendar.WEEK_OF_YEAR, +1);
      } else {
        rightNow.add(Calendar.WEEK_OF_YEAR, -1);
      }
      rightNow.add(Calendar.DATE, -(rightNow.get(Calendar.DAY_OF_WEEK) - rightNow.getFirstDayOfWeek()));
      year = rightNow.get(Calendar.YEAR);
      month = rightNow.get(Calendar.MONTH);
      day = rightNow.get(Calendar.DATE);
    }
    return valKeys;
  }


  /**
   *  Gets the dayRange attribute of the GraphSummaryList object
   *
   * @param  size  Description of the Parameter
   * @return       The dayRange value
   */
  public String[] getDayRange(int size) {
    String[] valKeys = new String[size];
    Calendar rightNow = Calendar.getInstance();
    int day = rightNow.get(Calendar.DATE);
    int year = rightNow.get(Calendar.YEAR);
    int month = rightNow.get(Calendar.MONTH);
    for (int x = 0; x < size; x++) {
      valKeys[x] = String.valueOf(year) + String.valueOf(month) + String.valueOf(day);
      if (isFutureDateRange) {
        rightNow.add(Calendar.DATE, +1);
      } else {
        rightNow.add(Calendar.DATE, -1);
      }
      year = rightNow.get(Calendar.YEAR);
      month = rightNow.get(Calendar.MONTH);
      day = rightNow.get(Calendar.DATE);
    }
    return valKeys;
  }


  /**
   *  Gets the yearRange attribute of the GraphSummaryList object
   *
   *@param  size  Description of the Parameter
   *@param  y     Description of the Parameter
   *@return       The yearRange value
   */
  public String[] getYearRange(int size, int y) {
    String[] valKeys = new String[size];
    Calendar rightNow = Calendar.getInstance();
    rightNow.set(Calendar.YEAR, y);
    int year = rightNow.get(Calendar.YEAR);
    int month = 0;
    for (int x = 0; x < size; x++) {
      valKeys[x] = String.valueOf(year) + String.valueOf(month);
      month++;
    }
    return valKeys;
  }


  /**
   *  Gets the IsValid attribute of the GraphSummaryList object
   *
   *@return    The IsValid value
   */
  public boolean getIsValid() {
    return isValid;
  }


  /**
   *  Adds a feature to the ToValue attribute of the GraphSummaryList object
   *
   *@param  which  The feature to be added to the ToValue attribute
   *@param  val    The feature to be added to the ToValue attribute
   */
  public void addToValue(String which, Double val) {
    Double tempValue = (Double) this.values.get(which);
    tempValue = new Double(tempValue.doubleValue() + val.doubleValue());
    this.values.put(which, tempValue);
  }


  /**
   *  Gets the size attribute of the GraphSummaryList object
   *
   * @return    The size value
   */
  public int getSize() {
    return size;
  }


  /**
   *  Sets the size attribute of the GraphSummaryList object
   *
   * @param  tmp  The new size value
   */
  public void setSize(int tmp) {
    this.size = tmp;
  }


  /**
   *  Sets the size attribute of the GraphSummaryList object
   *
   * @param  tmp  The new size value
   */
  public void setSize(String tmp) {
    this.size = Integer.parseInt(tmp);
  }


  /**
   *  Gets the isFutureDateRange attribute of the GraphSummaryList object
   *
   * @return    The isFutureDateRange value
   */
  public boolean getIsFutureDateRange() {
    return isFutureDateRange;
  }


  /**
   *  Sets the isFutureDateRange attribute of the GraphSummaryList object
   *
   * @param  tmp  The new isFutureDateRange value
   */
  public void setIsFutureDateRange(boolean tmp) {
    this.isFutureDateRange = tmp;
  }


  /**
   *  Sets the isFutureDateRange attribute of the GraphSummaryList object
   *
   * @param  tmp  The new isFutureDateRange value
   */
  public void setIsFutureDateRange(String tmp) {
    this.isFutureDateRange = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the daysInMonth attribute of the GraphSummaryList object
   *
   * @param  month  Description of the Parameter
   * @param  year   Description of the Parameter
   * @return        The daysInMonth value
   */
  public int getDaysInMonth(int month, int year) {
    switch (month) {
      case Calendar.APRIL:
      case Calendar.JUNE:
      case Calendar.SEPTEMBER:
      case Calendar.NOVEMBER:
        return 30;
      case Calendar.FEBRUARY:
        return ((Math.IEEEremainder((double) year, (double) 4) == 0) ? 29 : 28);
    }
    return 31;
  }


  /**
   *  Gets the values attribute of the GraphSummaryList object
   *
   * @return    The values value
   */
  public Hashtable getValues() {
    return values;
  }


  /**
   *  Sets the values attribute of the GraphSummaryList object
   *
   * @param  tmp  The new values value
   */
  public void setValues(Hashtable tmp) {
    this.values = tmp;
  }
}

