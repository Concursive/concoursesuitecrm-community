/**
 *  This file is part of the jcrontab package Copyright (C) 2001-2002 Israel
 *  Olalla This library is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or (at your
 *  option) any later version. This library is distributed in the hope that it
 *  will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 *  of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 *  General Public License for more details. You should have received a copy of
 *  the GNU Lesser General Public License along with this library; if not, write
 *  to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 *  MA 02111-1307, USA For questions, suggestions: iolalla@yahoo.com
 */
package org.jcrontab.data;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.Random;
import org.jcrontab.log.Log;

/**
 *  This class processes a CrontabEntryBean and returns a Calendar. This class
 *  is a "conversor" to convert from CrontabEntries to Calendars. Thanks to
 *  Javier Pardo for the idea and for the Algorithm
 *
 *@author     iolalla
 *@created    February 4, 2003
 *@version    $Revision$
 */

public class CalendarBuilder {

  /**
   *  This method gets thes next CrontabEntry in the given Array. Yep i know
   *  this can be plural.... i mean not only one CrontabEntry but in this case
   *  we care only for one cause only need to know the next execution time.
   *
   *@param  cebs  CrontabEntryBean[]
   *@return       CrontabEntryBean
   */

  public CrontabEntryBean getNextCrontabEntry(CrontabEntryBean[] cebs) {
    long[] times = new long[cebs.length];
    int value = 0;

    int index = 0;

    for (int i = 0; i < cebs.length; i++) {
      times[i] = buildCalendar(cebs[i]).getTime();
    }

    long number = times[index];

    for (int i = 0; i < times.length; i++) {
      if (times[i] < number) {
        number = times[i];
        value = i;
      }
    }
    return cebs[value];
  }


  /**
   *  This method builds a Date from a CrontabEntryBean. launching the same
   *  method with now as parameter
   *
   *@param  ceb  CrontabEntryBean
   *@return      Date
   */
  public Date buildCalendar(CrontabEntryBean ceb) {
    Date now = new Date(System.currentTimeMillis());
    return buildCalendar(ceb, now);
  }


  /**
   *  This method builds a Date from a CrontabEntryBean and from a starting Date
   *
   *@param  ceb        CrontabEntryBean
   *@param  afterDate  Date
   *@return            Date
   */
  public Date buildCalendar(CrontabEntryBean ceb, Date afterDate) {
    Calendar after = Calendar.getInstance();
    after.setTime(afterDate);

    int second = getNextIndex(ceb.getBSeconds(), after.get(Calendar.SECOND));
    if (second == -1) {
      second = getNextIndex(ceb.getBSeconds(), 0);
      after.add(Calendar.MINUTE, 1);
    }

    int minute = getNextIndex(ceb.getBMinutes(), after.get(Calendar.MINUTE));
    if (minute == -1) {
      second = getNextIndex(ceb.getBSeconds(), 0);
      minute = getNextIndex(ceb.getBMinutes(), 0);
      after.add(Calendar.HOUR_OF_DAY, 1);
    }

    int hour = getNextIndex(ceb.getBHours(), after.get(Calendar.HOUR_OF_DAY));
    if (hour == -1) {
      second = getNextIndex(ceb.getBSeconds(), 0);
      minute = getNextIndex(ceb.getBMinutes(), 0);
      hour = getNextIndex(ceb.getBHours(), 0);
      after.add(Calendar.DAY_OF_MONTH, 1);
    }

    int dayOfMonth = getNextIndex(ceb.getBDaysOfMonth(), after.get(Calendar.DAY_OF_MONTH) - 1);

    if (dayOfMonth == -1) {
      second = getNextIndex(ceb.getBSeconds(), 0);
      minute = getNextIndex(ceb.getBMinutes(), 0);
      hour = getNextIndex(ceb.getBHours(), 0);
      dayOfMonth = getNextIndex(ceb.getBDaysOfMonth(), 0);
      after.add(Calendar.MONTH, 1);
    }

    boolean dayMatchRealDate = false;
    while (!dayMatchRealDate) {
      if (checkDayValidInMonth(dayOfMonth + 1, after.get(Calendar.MONTH),
          after.get(Calendar.YEAR))) {
        dayMatchRealDate = true;
      } else {
        after.add(Calendar.MONTH, 1);
      }
    }

    int month = getNextIndex(ceb.getBMonths(), after.get(Calendar.MONTH));
    if (month == -1) {
      second = getNextIndex(ceb.getBSeconds(), 0);
      minute = getNextIndex(ceb.getBMinutes(), 0);
      hour = getNextIndex(ceb.getBHours(), 0);
      dayOfMonth = getNextIndex(ceb.getBDaysOfMonth(), 0);
      month = getNextIndex(ceb.getBMonths(), 0);
      after.add(Calendar.YEAR, 1);
    }

    Date byMonthDays = getTime(second, minute, hour, dayOfMonth + 1,
        month, after.get(Calendar.YEAR));
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(byMonthDays);

    boolean[] bDaysOfWeek = ceb.getBDaysOfWeek();

    if (bDaysOfWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1]) {
      return calendar.getTime();
    } else {
      calendar.add(Calendar.DAY_OF_YEAR, 1);
      return buildCalendar(ceb, calendar.getTime());
    }
  }


  /**
   *  This method builds a Date from a CrontabEntryBean and from a starting Date
   *
   *@param  minutes     int the minutes of this time
   *@param  hour        int the hour of this time
   *@param  dayOfMonth  int the dayOfMonth of this time
   *@param  month       int the month of this time
   *@param  year        int the year of this time
   *@param  seconds     Description of the Parameter
   *@return             Date builded with those parameters
   */
  private Date getTime(int seconds,
      int minutes,
      int hour,
      int dayOfMonth,
      int month,
      int year) {
    try {
      Calendar cl = Calendar.getInstance();
      cl.set(year, month, dayOfMonth, hour, minutes, seconds);
      return cl.getTime();
    } catch (Exception e) {
      Log.error("Smth was wrong:", e);
      return null;
    }
  }

  /**
   *  This method says wich is next index of this array
   *
   *@param  array  the list of booleans to check
   *@param  start  int the id where starts the search
   *@return        index int
   */
  private int getNextIndex(boolean[] array, int start) {
    for (int i = start; i < array.length; i++) {
      if (array[i]) {
        return i;
      }
    }
    return -1;
  }


  /**
   *  This says if this month has this day or not, basically this problem
   *  occurrs with 31 days in months with less days.
   *
   *@param  day    int the day so see if exists or not
   *@param  month  int the month to see it has this day or not.
   *@param  year   to see if valid ... to work with 366 days years and February
   *      :-)
   *@return        Description of the Return Value
   *@thanks        to Javier Pardo :-)
   */
  private boolean checkDayValidInMonth(int day, int month, int year) {
    try {
      Calendar cl = Calendar.getInstance();
      cl.setLenient(false);
      cl.set(Calendar.DAY_OF_MONTH, day);
      cl.set(Calendar.MONTH, month);
      cl.set(Calendar.YEAR, year);
      cl.getTime();
    } catch (IllegalArgumentException e) {
      return false;
    }
    return true;
  }
}
