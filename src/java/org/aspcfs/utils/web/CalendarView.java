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
package org.aspcfs.utils.web;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.mycfs.base.CalendarEvent;
import org.aspcfs.modules.mycfs.base.CalendarEventList;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.ObjectUtils;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.holidays.CAHolidays;
import org.aspcfs.utils.holidays.DEHolidays;
import org.aspcfs.utils.holidays.UKHolidays;
import org.aspcfs.utils.holidays.USHolidays;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * CalendarView.java Creates a monthly calendar and exports the HTML The
 * current month is shown completely, the prev/next month is partially shown,
 * but grayed out <p>
 * <p/>
 * Working on outputting text entries for a date as well <p>
 * <p/>
 * Can be used as a popup, or standalone HTML calendar, defined by parameters
 * and/or properties <p>
 * <p/>
 * If a date is supplied, that month is defaulted, otherwise the current month
 * is displayed
 *
 * @author mrajkowski based on bean from Maneesh Sahu
 * @version $Id: CalendarView.java,v 1.37.12.1 2004/11/12 19:34:31 mrajkowski
 *          Exp $
 * @created March 2, 2001
 */
public class CalendarView {

  protected String[] monthNames = null;
  protected String[] shortMonthNames = null;
  protected DateFormatSymbols symbols = null;
  protected Calendar cal = null;
  protected int today = -1;
  protected int day = -1;
  protected int month = -1;
  protected int year = -1;
  protected Calendar calPrev = null;
  protected Calendar calNext = null;
  protected Locale locale = null;

  //Various settings for how the calendar looks
  protected boolean headerSpace = false;
  protected boolean monthArrows = false;
  protected boolean smallView = false;
  protected boolean frontPageView = false;
  protected boolean popup = false;
  protected boolean showSubject = true;
  protected String borderSize = "";

  protected String cellPadding = "";
  protected String cellSpacing = "";
  protected int numberOfCells = 42;

  //Events that can be displayed on the calendar
  protected HashMap eventList = new HashMap();
  protected boolean sortEvents = false;
  //NOTE: DO NOT USE THIS LIST DIRECTLY BECAUSE OF LEAP YEARS
  public final static int[] DAYSINMONTH = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
  public final static String[] MONTHS = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
  //parameter for synchronization of session object
  private int synchFrameCounter = 1;
  CalendarBean calendarInfo = null;

  //timezone
  TimeZone timeZone = null;

  //link to translator
  SystemStatus systemStatus = null;


  /**
   * The Default Constructor
   */
  public CalendarView() {
    this("en", "US");
  }


  /**
   * Constructor for the CalendarView object
   *
   * @param request Description of Parameter
   */
  public CalendarView(HttpServletRequest request) {
    String year = request.getParameter("year");
    String month = request.getParameter("month");
    String day = request.getParameter("day");
    String timeZone = request.getParameter("timeZone");
    String language = request.getParameter("language");
    String country = request.getParameter("country");

    // Initialize calendar
    setLocale(language, country);

    //If the user clicks the next/previous arrow, increment/decrement the month
    //Range checking is not necessary on the month.  The calendar object automatically
    //increments the year when necessary
    if (month != null) {
      try {
        int monthTmp = Integer.parseInt(month);
        if (request.getParameter("next.x") != null) {
          ++monthTmp;
        }
        if (request.getParameter("prev.x") != null) {
          monthTmp += -1;
        }
        month = String.valueOf(monthTmp);
      } catch (NumberFormatException e) {
      }
    }
    //set time zone
    if (timeZone != null && !"".equals(timeZone)) {
      cal.setTimeZone(TimeZone.getTimeZone(timeZone));
    }
    this.setYear(year);
    this.setMonth(month);
    this.setDay(day);
  }


  /**
   * Constructor for using with the CalendarBean
   *
   * @param calendarInfo Description of the Parameter
   * @param locale       Description of the Parameter
   */
  public CalendarView(CalendarBean calendarInfo, Locale locale) {
    this.calendarInfo = calendarInfo;
    // Initialize the calendars
    setLocale(locale);
    // set time zone and update the Calendar
    this.setTimeZone(calendarInfo.getTimeZone());
    cal.setTimeZone(timeZone);
    calPrev.setTimeZone(timeZone);
    calNext.setTimeZone(timeZone);
    this.setYear(calendarInfo.getPrimaryYear());
    this.setMonth(calendarInfo.getPrimaryMonth());
    this.setDay(calendarInfo.getDaySelected());
  }


  /**
   * Creates a CalendarView for a given locale
   *
   * @param language the two letter string code specifying a languge, "EN" for
   *                 english for example
   * @param region   the two letter string code specifying a region, "ES" for
   *                 spain for example
   */
  public CalendarView(String language, String region) {
    Locale theLocale = new Locale(language, region);
    setLocale(theLocale);
  }


  /**
   * Constructor for the CalendarView object
   *
   * @param theLocale Description of the Parameter
   */
  public CalendarView(Locale theLocale) {
    setLocale(theLocale);
  }


  /**
   * Sets the NumberOfCells attribute of the CalendarView object
   *
   * @param numberOfCells The new NumberOfCells value
   */
  public void setNumberOfCells(int numberOfCells) {
    this.numberOfCells = numberOfCells;
  }


  /**
   * Sets the calendarInfo attribute of the CalendarView object
   *
   * @param calendarInfo The new calendarInfo value
   */
  public void setCalendarInfo(CalendarBean calendarInfo) {
    this.calendarInfo = calendarInfo;
  }


  /**
   * Sets the month property (java.lang.String) value.
   *
   * @param monthArg The new Month value
   */
  public void setMonth(String monthArg) {
    if ((monthArg != null) && (!monthArg.equals(""))) {
      try {
        this.month = Integer.parseInt(monthArg) - 1;
        this.update();
      } catch (Exception exc) {
      }
    }
  }


  /**
   * Sets the calendar by using a date object
   *
   * @param tmp The new Date value
   */
  public void setDate(java.util.Date tmp) {
    cal.setTime(tmp);
    year = cal.get(Calendar.YEAR);
    month = cal.get(Calendar.MONTH);
    day = cal.get(Calendar.DAY_OF_MONTH);
    this.update();
  }


  /**
   * Sets the FrontPageView attribute of the CalendarView object
   *
   * @param frontPageView The new FrontPageView value
   */
  public void setFrontPageView(boolean frontPageView) {
    this.frontPageView = frontPageView;
  }


  /**
   * Sets the day property (java.lang.String) value.
   *
   * @param dayArg The new Day value
   */
  public void setDay(String dayArg) {
    if ((dayArg != null) && (!dayArg.equals(""))) {
      try {
        this.day = Integer.parseInt(dayArg);
        this.update();
      } catch (Exception exc) {
      }
    } else {
      this.day = 1;
      this.update();
    }
  }


  /**
   * Sets the day attribute of the CalendarView object
   *
   * @param dayArg The new day value
   */
  public void setDay(int dayArg) {
    if (dayArg != -1 && dayArg != 0) {
      try {
        this.day = dayArg;
        this.update();
      } catch (Exception exc) {
      }
    } else {
      this.day = 1;
      this.update();
    }
  }


  /**
   * Sets the ShowSubject attribute of the CalendarView object
   *
   * @param showSubject The new ShowSubject value
   */
  public void setShowSubject(boolean showSubject) {
    this.showSubject = showSubject;
  }


  /**
   * Sets the year property (java.lang.String) value.
   *
   * @param yearArg The new Year value
   */
  public void setYear(String yearArg) {
    if ((yearArg != null) && (!yearArg.equals(""))) {
      try {
        this.year = Integer.parseInt(yearArg);
        if (yearArg.length() == 2) {
          if (yearArg.startsWith("9")) {
            this.year = Integer.parseInt("19" + yearArg);
          } else {
            this.year = Integer.parseInt("20" + yearArg);
          }
        }
        this.update();
      } catch (Exception exc) {
      }
    }
  }


  /**
   * Sets the month property (int) value.
   *
   * @param monthArg The new Month value
   */
  public void setMonth(int monthArg) {
    this.month = monthArg - 1;
    this.update();
  }


  /**
   * Sets the year property (int) value.
   *
   * @param yearArg The new Year value
   */
  public void setYear(int yearArg) {
    this.year = yearArg;
    this.update();
  }


  /**
   * Sets the SortEvents attribute of the CalendarView object
   *
   * @param tmp The new SortEvents value
   */
  public void setSortEvents(boolean tmp) {
    this.sortEvents = tmp;
  }


  /**
   * Sets the MonthArrows attribute of the CalendarView object
   *
   * @param tmp The new MonthArrows value
   */
  public void setMonthArrows(boolean tmp) {
    this.monthArrows = tmp;
  }


  /**
   * Sets the SmallView attribute of the CalendarView object
   *
   * @param tmp The new SmallView value
   */
  public void setSmallView(boolean tmp) {
    this.smallView = tmp;
  }


  /**
   * Sets the Popup attribute of the CalendarView object
   *
   * @param tmp The new Popup value
   */
  public void setPopup(boolean tmp) {
    this.popup = tmp;
    if (this.popup) {
      this.setMonthArrows(true);
      this.setSmallView(true);
    }
  }


  /**
   * Sets the CellPadding attribute of the CalendarView object
   *
   * @param tmp The new CellPadding value
   */
  public void setCellPadding(int tmp) {
    this.cellPadding = " cellpadding='" + tmp + "'";
  }


  /**
   * Sets the CellSpacing attribute of the CalendarView object
   *
   * @param tmp The new CellSpacing value
   */
  public void setCellSpacing(int tmp) {
    this.cellSpacing = " cellspacing='" + tmp + "'";
  }


  /**
   * Sets the BorderSize attribute of the CalendarView object
   *
   * @param tmp The new BorderSize value
   */
  public void setBorderSize(int tmp) {
    this.borderSize = "border=" + tmp + " ";
  }


  /**
   * Sets the HeaderSpace attribute of the CalendarView object
   *
   * @param tmp The new HeaderSpace value
   */
  public void setHeaderSpace(boolean tmp) {
    this.headerSpace = tmp;
  }


  /**
   * Sets the Locale attribute of the CalendarView object
   *
   * @param theLocale The new Locale value
   */
  public void setLocale(Locale theLocale) {
    if (locale == null) {
      symbols = new DateFormatSymbols(theLocale);
      monthNames = symbols.getMonths();
      shortMonthNames = symbols.getShortMonths();
      locale = theLocale;
      cal = Calendar.getInstance(locale);
      today = cal.get(Calendar.DAY_OF_MONTH);
      day = cal.get(Calendar.DAY_OF_MONTH);
      month = cal.get(Calendar.MONTH);
      year = cal.get(Calendar.YEAR);
      calPrev = Calendar.getInstance(locale);
      calNext = Calendar.getInstance(locale);
      this.update();
    }
  }


  /**
   * Sets the locale attribute of the CalendarView object
   *
   * @param language The new locale value
   * @param country  The new locale value
   */
  public void setLocale(String language, String country) {
    if (language == null) {
      language = "en";
      country = "US";
    }
    Locale theLocale = new Locale(language, country);
    setLocale(theLocale);
  }


  /**
   * Sets the timeZone attribute of the CalendarView object
   *
   * @param timeZone The new timeZone value
   */
  public void setTimeZone(TimeZone timeZone) {
    this.timeZone = timeZone;
  }


  /**
   * Gets the timeZone attribute of the CalendarView object
   *
   * @return The timeZone value
   */
  public TimeZone getTimeZone() {
    return timeZone;
  }


  /**
   * Gets the systemStatus attribute of the CalendarView object
   *
   * @return The systemStatus value
   */
  public SystemStatus getSystemStatus() {
    return systemStatus;
  }


  /**
   * Sets the systemStatus attribute of the CalendarView object
   *
   * @param tmp The new systemStatus value
   */
  public void setSystemStatus(SystemStatus tmp) {
    this.systemStatus = tmp;
  }


  /**
   * Gets the calendarInfo attribute of the CalendarView object
   *
   * @return The calendarInfo value
   */
  public CalendarBean getCalendarInfo() {
    return calendarInfo;
  }


  /**
   * Gets the ShowSubject attribute of the CalendarView object
   *
   * @return The ShowSubject value
   */
  public boolean getShowSubject() {
    return showSubject;
  }


  /**
   * Gets the NumberOfCells attribute of the CalendarView object
   *
   * @return The NumberOfCells value
   */
  public int getNumberOfCells() {
    return numberOfCells;
  }


  /**
   * Gets the FrontPageView attribute of the CalendarView object
   *
   * @return The FrontPageView value
   */
  public boolean getFrontPageView() {
    return frontPageView;
  }


  /**
   * Returns a list representative of the event objects
   *
   * @param tmp1 Description of Parameter
   * @param tmp2 Description of Parameter
   * @param tmp3 Description of Parameter
   * @return The Events value
   */
  public CalendarEventList getEvents(String tmp1, String tmp2, String tmp3) {
    String key = tmp1 + "/" + tmp2 + "/" + tmp3;
    if (eventList.containsKey(key)) {
      return (CalendarEventList) eventList.get(key);
    } else {
      return new CalendarEventList();
    }
  }


  /**
   * Gets the events attribute of the CalendarView object
   *
   * @param tmp1      Description of the Parameter
   * @param tmp2      Description of the Parameter
   * @param tmp3      Description of the Parameter
   * @param eventType Description of the Parameter
   * @return The events value
   */
  public ArrayList getEvents(String tmp1, String tmp2, String tmp3, String eventType) {
    String key = tmp1 + "/" + tmp2 + "/" + tmp3;
    if (eventList.containsKey(key)) {
      CalendarEventList daysEvents = (CalendarEventList) eventList.get(key);
      return (ArrayList) daysEvents.getEvents(eventType);
    } else {
      return new ArrayList();
    }
  }


  /**
   * Gets the eventList attribute of the CalendarView object
   *
   * @param tmp1 Description of the Parameter
   * @param tmp2 Description of the Parameter
   * @param tmp3 Description of the Parameter
   * @return The eventList value
   */
  public CalendarEventList getEventList(String tmp1, String tmp2, String tmp3) {
    String key = tmp1 + "/" + tmp2 + "/" + tmp3;
    if (eventList.containsKey(key)) {
      return (CalendarEventList) eventList.get(key);
    } else {
      return new CalendarEventList();
    }
  }


  /**
   * Sets the eventList attribute of the CalendarView object
   *
   * @param key           The new eventList value
   * @param thisEventList The new eventList value
   */
  public void setEventList(String key, Object thisEventList) {
    eventList.put(key, thisEventList);
  }


  /**
   * Gets the eventList attribute of the CalendarView object
   *
   * @return The eventList value
   */
  public HashMap getEventList() {
    return eventList;
  }


  /**
   * Gets the eventList attribute of the CalendarView object
   *
   * @param key Description of the Parameter
   * @return The eventList value
   */
  public CalendarEventList getEventList(String key) {
    if (eventList.containsKey(key)) {
      return (CalendarEventList) eventList.get(key);
    } else {
      return new CalendarEventList();
    }
  }


  /**
   * Gets the eventCount attribute of the CalendarView object
   *
   * @param tmp1      Description of the Parameter
   * @param tmp2      Description of the Parameter
   * @param tmp3      Description of the Parameter
   * @param eventType Description of the Parameter
   * @return The eventCount value
   */
  public int getEventCount(int tmp1, int tmp2, int tmp3, String eventType) {
    String key = tmp1 + "/" + tmp2 + "/" + tmp3;
    return ((ArrayList) this.getEventList(key).getEvents(eventType)).size();
  }


  /**
   * Returns the cell representing the last day in the 42 cell grid Creation
   * date: (5/2/2000 2:57:08 AM)
   *
   * @param tmp Description of Parameter
   * @return int
   */
  public int getEndCell(Calendar tmp) {
    int endCell = DAYSINMONTH[tmp.get(Calendar.MONTH)] + this.getStartCell(
        tmp) - 1;
    if (tmp.get(Calendar.MONTH) == Calendar.FEBRUARY
        && ((GregorianCalendar) tmp).isLeapYear(tmp.get(Calendar.YEAR))) {
      endCell++;
    }
    return endCell;
  }


  /**
   * Returns the year of the Calendar item
   *
   * @param tmp Description of Parameter
   * @return The Year value
   */
  public int getYear(Calendar tmp) {
    return tmp.get(Calendar.YEAR);
  }


  /**
   * Gets the Day attribute of the CalendarView object
   *
   * @return The Day value
   */
  public String getDay() {
    return String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
  }


  /**
   * Gets the Month attribute of the CalendarView object
   *
   * @return The Month value
   */
  public String getMonth() {
    return String.valueOf(cal.get(Calendar.MONTH) + 1);
  }


  /**
   * Gets the Year attribute of the CalendarView object
   *
   * @return The Year value
   */
  public String getYear() {
    return String.valueOf(cal.get(Calendar.YEAR));
  }


  /**
   * Returns the Month Name Creation date: (5/2/2000 2:49:08 AM)
   *
   * @param tmp Description of Parameter
   * @return java.lang.String
   */
  public String getMonthName(Calendar tmp) {
    return monthNames[tmp.get(Calendar.MONTH)];
  }


  /**
   * Returns the Short Month Name Creation date: (5/2/2000 2:49:08 AM)
   *
   * @param tmp Description of Parameter
   * @return java.lang.String
   */
  public String getShortMonthName(Calendar tmp) {
    return shortMonthNames[tmp.get(Calendar.MONTH)];
  }


  /**
   * Returns the cell representing the first day of the month in the 42 cell
   * grid Creation date: (5/2/2000 2:51:35 AM)
   *
   * @param tmp Description of Parameter
   * @return int
   */
  public int getStartCell(Calendar tmp) {
    Calendar beginOfMonth = Calendar.getInstance(locale);
    beginOfMonth.set(tmp.get(Calendar.YEAR), tmp.get(Calendar.MONTH), 0);
    int baseDay = beginOfMonth.get(Calendar.DAY_OF_WEEK) - tmp.getFirstDayOfWeek() + 1;
    if (baseDay < 1) {
      baseDay = 7 + baseDay;
    }
    return baseDay;
  }


  /**
   * Gets the calendarStartDate attribute of the CalendarView object
   *
   * @param context Description of the Parameter
   * @return The calendarStartDate value
   */
  public String getCalendarStartDate(ActionContext context) {
    int displayMonth = 0;
    int displayDay = 0;
    int displayYear = 0;
    String source = context.getRequest().getParameter("source");
    if (source != null) {
      if (calendarInfo.isAgendaView() && source.equalsIgnoreCase(
          "calendarDetails")) {
        Calendar today = Calendar.getInstance(timeZone, locale);
        displayMonth = today.get(Calendar.MONTH) + 1;
        displayDay = today.get(Calendar.DAY_OF_MONTH);
        displayYear = today.get(Calendar.YEAR);
      } else if (!source.equalsIgnoreCase("Calendar")) {
        if (calendarInfo.getCalendarView().equalsIgnoreCase("day")) {
          displayMonth = calendarInfo.getMonthSelected();
          displayDay = calendarInfo.getDaySelected();
          displayYear = calendarInfo.getYearSelected();
        } else if (calendarInfo.getCalendarView().equalsIgnoreCase("week")) {
          displayMonth = calendarInfo.getStartMonthOfWeek();
          displayDay = calendarInfo.getStartDayOfWeek();
          displayYear = calendarInfo.getYearSelected();
        } else {
          displayMonth = calPrev.get(Calendar.MONTH) + 1;
          displayDay = (this.getEndCell(calPrev) - this.getStartCell(cal) + 2 - this.getStartCell(
              calPrev));
          displayYear = calPrev.get(Calendar.YEAR);
        }
      } else {
        displayMonth = calPrev.get(Calendar.MONTH) + 1;
        displayDay = (this.getEndCell(calPrev) - this.getStartCell(cal) + 2 - this.getStartCell(
            calPrev));
        displayYear = calPrev.get(Calendar.YEAR);
      }
    }
    if (System.getProperty("DEBUG") != null) {
      System.out.println(
          "CalendarView-> Start Day: " + displayMonth + "/" + displayDay + "/" + displayYear);
    }
    return (displayMonth + "/" + displayDay + "/" + displayYear);
  }


  /**
   * Gets the calendarEndDate attribute of the CalendarView object
   *
   * @param context Description of the Parameter
   * @return The calendarEndDate value
   */
  public String getCalendarEndDate(ActionContext context) {
    int displayMonth = 0;
    int displayDay = 0;
    int displayYear = 0;
    String source = context.getRequest().getParameter("source");
    if (source != null) {
      if (calendarInfo.isAgendaView() && source.equalsIgnoreCase(
          "calendarDetails")) {
        Calendar today = Calendar.getInstance(timeZone, locale);
        today.add(Calendar.DATE, 7);
        displayMonth = today.get(Calendar.MONTH) + 1;
        displayDay = today.get(Calendar.DAY_OF_MONTH);
        displayYear = today.get(Calendar.YEAR);
      } else if (!source.equalsIgnoreCase("Calendar")) {
        if (calendarInfo.getCalendarView().equalsIgnoreCase("day")) {
          Calendar tmpCal = Calendar.getInstance(locale);
          tmpCal.set(
              calendarInfo.getYearSelected(), calendarInfo.getMonthSelected() - 1, calendarInfo.getDaySelected());
          tmpCal.add(java.util.Calendar.DATE, +1);
          displayMonth = tmpCal.get(Calendar.MONTH) + 1;
          displayDay = tmpCal.get(Calendar.DAY_OF_MONTH);
          displayYear = tmpCal.get(Calendar.YEAR);
        } else if (calendarInfo.getCalendarView().equalsIgnoreCase("week")) {
          Calendar newDate = Calendar.getInstance(timeZone, locale);
          newDate.set(
              calendarInfo.getYearSelected(), calendarInfo.getStartMonthOfWeek() - 1, calendarInfo.getStartDayOfWeek());
          newDate.add(Calendar.DATE, 7);
          displayMonth = newDate.get(Calendar.MONTH) + 1;
          displayDay = newDate.get(Calendar.DATE);
          displayYear = newDate.get(Calendar.YEAR);
        } else {
          displayMonth = calNext.get(Calendar.MONTH) + 1;
          displayYear = calNext.get(Calendar.YEAR);
          displayDay = numberOfCells - getEndCell(cal) - 1;
        }
      } else {
        Calendar tmpCal = Calendar.getInstance(locale);
        tmpCal.set(
            calNext.get(Calendar.YEAR), calNext.get(Calendar.MONTH), (numberOfCells - getEndCell(
                cal) - 1));
        tmpCal.add(java.util.Calendar.DATE, +1);
        displayMonth = tmpCal.get(Calendar.MONTH) + 1;
        displayDay = tmpCal.get(Calendar.DAY_OF_MONTH);
        displayYear = tmpCal.get(Calendar.YEAR);
      }
    }
    if (System.getProperty("DEBUG") != null) {
      System.out.println(
          "CalendarView-> End Day: " + displayMonth + "/" + displayDay + "/" + displayYear);
    }
    return (displayMonth + "/" + displayDay + "/" + displayYear);
  }


  /**
   * Returns true if today is the current calendar day being drawn
   *
   * @param tmp    Description of Parameter
   * @param indate Description of Parameter
   * @return The CurrentDay value
   */
  public boolean isCurrentDay(Calendar tmp, int indate) {
    Calendar thisMonth = Calendar.getInstance(locale);
    if (timeZone != null) {
      thisMonth.setTimeZone(timeZone);
    }
    if ((indate == thisMonth.get(Calendar.DAY_OF_MONTH)) &&
        (tmp.get(Calendar.MONTH) == thisMonth.get(Calendar.MONTH)) &&
        (tmp.get(Calendar.YEAR) == thisMonth.get(Calendar.YEAR))) {
      return true;
    } else {
      return false;
    }
  }


  /**
   * Returns the week day name Creation date: (5/2/2000 2:50:10 AM)
   *
   * @param day        int
   * @param longFormat Description of Parameter
   * @return java.lang.String
   */
  public String getDayName(int day, boolean longFormat) {
    if (day > 7) {
      day = day - 7;
    }
    if (longFormat) {
      return symbols.getShortWeekdays()[day];
    }
    return symbols.getWeekdays()[day];
  }


  /**
   * Gets the Today attribute of the CalendarView object
   *
   * @return The Today value
   */
  public String getToday() {
    Calendar today = Calendar.getInstance(locale);
    if (timeZone != null) {
      today.setTimeZone(timeZone);
    }
    if (locale != null) {
      SimpleDateFormat formatter = (SimpleDateFormat) SimpleDateFormat.getDateInstance(
          DateFormat.LONG, locale);
      return formatter.format(today.getTime());
    } else {
      return (this.getMonthName(today) + " " + today.get(
          Calendar.DAY_OF_MONTH) + ", " + today.get(Calendar.YEAR));
    }
  }


  /**
   * Gets the synchFrameCounter attribute of the HtmlDialog object
   *
   * @return The synchFrameCounter value
   */
  public int getSynchFrameCounter() {
    return synchFrameCounter;
  }


  /**
   * Description of the Method
   */
  public synchronized void decrementSynchFrameCounter() {
    --synchFrameCounter;
  }


  /**
   * Gets the DaysEvents attribute of the CalendarView object
   *
   * @param m Description of Parameter
   * @param d Description of Parameter
   * @param y Description of Parameter
   * @return The DaysEvents value
   */
  public CalendarEventList getDaysEvents(int m, int d, int y) {
    int displayMonth = m + 1;
    int displayYear = y;
    int displayDay = d;

    //Get this day's events
    return getEvents("" + displayMonth, "" + displayDay, "" + displayYear);
  }


  /**
   * Constructs the calendar and returns a String object with the HTML
   *
   * @return The HTML value
   */
  public String getHtml() {
    StringBuffer html = new StringBuffer();

    //Begin the whole table
    html.append(
        "<table width='98%' valign='top' cellspacing='0' cellpadding='0' border='0' bgcolor='#ffffff'>" +
        "<tr><td>");

    //Space at top to match
    if (headerSpace) {
      html.append(
          "<table width=100% align=center cellspacing=0 cellpadding=0 border=0>" +
          "<tr><td>&nbsp;</td></tr>" +
          "</table>");
    }

    String monthArrowPrev = "";
    String monthArrowNext = "";
    if (monthArrows) {
      monthArrowPrev = "<INPUT TYPE=\"IMAGE\" NAME=\"prev\" ALIGN=\"MIDDLE\" SRC=\"images/prev.gif\">";
      monthArrowNext = "<INPUT TYPE=\"IMAGE\" NAME=\"next\" ALIGN=\"MIDDLE\" SRC=\"images/next.gif\">";
    }

    //If popup, then use small formats of each class
    String tableWidth = "100%";
    String pre = "";
    if (popup) {
      pre = "small";
      tableWidth = "155";
    } else if (frontPageView) {
      tableWidth = "300";
    }
    //Display Calendar
    html.append(
        "<center><table height=\"100%\" width='" + tableWidth + "' " + borderSize + cellSpacing + cellPadding + " class='" + pre + "calendar' id='calendarTable'>" +
        "<tr name=\"staticrow\" height=\"4%\">");

    //Display Previous Month Arrow
    if (popup) {
      if (monthArrows) {
        html.append(
            "<th class='" + pre + "monthArrowPrev'>" + monthArrowPrev + "</th>");
      }

      //Display Current Month name
      if (monthArrows) {
        html.append("<th colspan='5' ");
      } else {
        html.append("<th colspan='7' ");
      }
      html.append("class='" + pre + "monthName'");
      html.append(
          "><B>" + StringUtils.toHtml(this.getMonthName(cal)) + " " + this.getYear(
              cal) + "</B></th>");
      //Display Next Month Arrow
      if (monthArrows) {
        html.append(
            "<th class='" + pre + "monthArrowNext'>" + monthArrowNext + "</th>");
      }
    } else {
      html.append("<th colspan=\"8\">");
      html.append(getHtmlMonthSelect());
      html.append("&nbsp;");
      html.append(getHtmlYearSelect());
      html.append("&nbsp;");
      Calendar tmp = Calendar.getInstance(locale);
      if (timeZone != null) {
        tmp.setTimeZone(timeZone);
      }
      if (systemStatus != null) {
        html.append(
            "<a href=\"javascript:showToDaysEvents('" + (tmp.get(
                Calendar.MONTH) + 1) + "','" + tmp.get(Calendar.DATE) + "','" + tmp.get(
                    Calendar.YEAR) + "');\">" + systemStatus.getLabel(
                        "calendar.Today") + "</a>");
      } else {
        html.append(
            "<a href=\"javascript:showToDaysEvents('" + (tmp.get(
                Calendar.MONTH) + 1) + "','" + tmp.get(Calendar.DATE) + "','" + tmp.get(
                    Calendar.YEAR) + "');\">Today</a>");
      }
      html.append("</th>");
    }
    html.append("</tr>");

    //Display the Days of the Week names
    html.append("<tr name=\"staticrow\" height=\"4%\">");
    if (!popup) {
      html.append(
          "<td width=\"4\" class=\"row1\"><font style=\"visibility:hidden\">n</font></td>");
    }

    // Use locale...
    int firstDayOfWeek = cal.getFirstDayOfWeek();
    for (int i = firstDayOfWeek; i < firstDayOfWeek + 7; i++) {
      html.append("<td width=\"14%\" class='" + pre + "weekName'>");
      if (popup || frontPageView) {
        html.append(StringUtils.toHtml(this.getDayName(i, true)));
      } else {
        html.append(StringUtils.toHtml(this.getDayName(i, false)));
      }
      html.append("</td>");
    }
    html.append("</tr>");
    int startCellPrev = this.getStartCell(calPrev);
    int endCellPrev = this.getEndCell(calPrev);

    int startCell = this.getStartCell(cal);
    int endCell = this.getEndCell(cal);

    int thisDay = 1;
    String tdClass = "";
    for (int cellNo = 0; cellNo < this.getNumberOfCells(); cellNo++) {
      boolean prevMonth = false;
      boolean nextMonth = false;
      boolean mainMonth = false;
      int displayDay = 0;
      int displayMonth = 0;
      int displayYear = 0;
      if (cellNo < startCell) {
        //The previous month
        displayMonth = calPrev.get(Calendar.MONTH) + 1;
        displayYear = calPrev.get(Calendar.YEAR);
        displayDay = (endCellPrev - startCell + 2 + cellNo - startCellPrev);
        prevMonth = true;
      } else if (cellNo > endCell) {
        //The next month
        displayMonth = calNext.get(Calendar.MONTH) + 1;
        displayYear = calNext.get(Calendar.YEAR);
        if (endCell + 1 == cellNo) {
          thisDay = 1;
        }
        displayDay = thisDay;
        nextMonth = true;
        thisDay++;
      } else {
        //The main month
        mainMonth = true;
        displayMonth = cal.get(Calendar.MONTH) + 1;
        displayYear = cal.get(Calendar.YEAR);
        displayDay = thisDay;
        thisDay++;
      }

      if (cellNo % 7 == 0) {
        tdClass = "";
        html.append("<tr");
        if (!popup) {
          if (calendarInfo.getCalendarView().equalsIgnoreCase("week")) {
            if (displayMonth == calendarInfo.getStartMonthOfWeek() && displayDay == calendarInfo.getStartDayOfWeek()) {
              html.append(" class=\"selectedWeek\" ");
              tdClass = "selectedDay";
            }
          }
        }
        html.append(">");
      }
      if (!popup && (cellNo % 7 == 0)) {
        html.append(
            "<td valign='top' width=\"4\" class=\"weekSelector\" name=\"weekSelector\">");
        String weekSelectedArrow = "<a href=\"javascript:showWeekEvents('" + displayYear + "','" + displayMonth + "','" + displayDay + "')\">" + "<img ALIGN=\"MIDDLE\" src=\"images/next.gif\" border=\"0\" onclick=\"javascript:switchTableClass(this,'selectedWeek','row');\"></a>";
        html.append(weekSelectedArrow);
        html.append("</td>");
      }

      html.append("<td valign='top'");
      if (!smallView) {
        if (!frontPageView) {
          html.append(" height='70'");
        } else {
          html.append(" height='45'");
        }
      }
      if (!popup) {
        html.append(
            " onclick=\"javascript:showDayEvents('" + displayYear + "','" + displayMonth + "','" + displayDay + "');javascript:switchTableClass(this,'selectedDay','cell');\"");
        if (calendarInfo.getCalendarView().equalsIgnoreCase("day")) {
          tdClass = "";
          if (displayMonth == calendarInfo.getMonthSelected() && displayDay == calendarInfo.getDaySelected()) {
            tdClass = "selectedDay";
          }
        }
      }

      if (prevMonth) {
        //The previous month
        if (this.isCurrentDay(calPrev, displayDay)) {
          html.append(
              " id='today' class='" + ((tdClass.equalsIgnoreCase("")) ? pre + "today'" : tdClass + "'") + " name='" + pre + "today' >");
        } else {
          html.append(
              " class='" + ((tdClass.equalsIgnoreCase("")) ? pre + "noday'" : tdClass + "'") + " name='" + pre + "noday' >");
        }
      } else if (nextMonth) {
        if (this.isCurrentDay(calNext, displayDay)) {
          html.append(
              " id='today' class='" + ((tdClass.equalsIgnoreCase("")) ? pre + "today'" : tdClass + "'") + " name='" + pre + "today' >");
        } else {
          html.append(
              " class='" + ((tdClass.equalsIgnoreCase("")) ? pre + "noday'" : tdClass + "'") + " name='" + pre + "noday' >");
        }
      } else {
        //The main main
        if (this.isCurrentDay(cal, displayDay)) {
          html.append(
              " id='today' class='" + ((tdClass.equalsIgnoreCase("")) ? pre + "today'" : tdClass + "'") + " name='" + pre + "today' >");
        } else {
          html.append(
              " class='" + ((tdClass.equalsIgnoreCase("")) ? pre + "day'" : tdClass + "'") + " name='" + pre + "day' >");
        }
      }
      // end if block
      //Display the day in the appropriate link color
      if (popup) {
        //Popup calendar
        String tmpEvent = (String) eventList.get(
            displayMonth + "/" + displayDay + "/" + displayYear);
        String dateColor = "" + displayDay;
        if ("highlight".equals(tmpEvent)) {
          dateColor = "<font color=#FF0000>" + displayDay + "</font>";
        } else if (!mainMonth) {
          dateColor = "<font color=#888888>" + displayDay + "</font>";
        }
        html.append(
            "<a href=\"javascript:returnDate(" + displayDay + ", " + displayMonth + ", " + displayYear + ");\"" + ">" +
            dateColor + "</a>");
      } else {
        //Event calendar
        String dateColor = "" + displayDay;
        if (!mainMonth) {
          dateColor = "<font color=#888888>" + displayDay + "</font>";
        }
        html.append(
            "<a href=\"javascript:showDayEvents('" + displayYear + "','" + displayMonth + "','" + displayDay + "');\">" + dateColor + "</a>");

        if (this.isHoliday(
            String.valueOf(displayMonth), String.valueOf(displayDay), String.valueOf(
                displayYear))) {
          html.append(
              CalendarEvent.getIcon("holiday", systemStatus) + "<font color=\"blue\"><br />");
        }

        //get all events categories and respective counts.
        HashMap events = this.getEventList(
            String.valueOf(displayMonth), String.valueOf(displayDay), String.valueOf(
                displayYear));
        if (events.size() > 0) {
          html.append(
              "<table width=\"12%\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"dayIcon\">");
          for (int i = 0; i < Array.getLength(CalendarEventList.EVENT_TYPES); i++) {
            String eventType = CalendarEventList.EVENT_TYPES[i];
            if (events.containsKey(eventType)) {
              if (!eventType.equals(CalendarEventList.EVENT_TYPES[7])) {
                Object eventObj = events.get(eventType);
                //use reflection to call the size method on the event list object
                String eventSize = (String) ObjectUtils.getObject(
                    eventObj, "sizeString");
                html.append(
                    "<tr><td>" + CalendarEvent.getIcon(
                        eventType, systemStatus) + "</td><td> " + eventSize + "</td></tr>");
              }
            }
          }
          html.append("</table>");
        }
        //end of events display.
      }
      html.append("</td>");
      if ((cellNo + 1) % 7 == 0) {
        html.append("</tr>");
      }
      // end check for end of row
    }
    // end for-loop

    html.append("</table></center></td></tr>");
    html.append("</table>");

    //Display a link that selects today
    if (popup) {
      Calendar tmp = Calendar.getInstance(locale);
      if (timeZone != null) {
        tmp.setTimeZone(timeZone);
      }
      int displayMonth = tmp.get(Calendar.MONTH) + 1;
      int displayYear = tmp.get(Calendar.YEAR);
      int displayDay = tmp.get(Calendar.DAY_OF_MONTH);
      if (systemStatus != null) {
        html.append(
            "<p class=\"smallfooter\">" + StringUtils.toHtml(
                systemStatus.getLabel("calendar.Today")) + ": " + "<a href=\"javascript:returnDate(" + displayDay + ", " + displayMonth + ", " + displayYear + ");\"" + ">" + StringUtils.toHtml(
                    this.getToday()) + "</p>");
      } else {
        html.append(
            "<p class=\"smallfooter\">Today: " + "<a href=\"javascript:returnDate(" + displayDay + ", " + displayMonth + ", " + displayYear + ");\"" + ">" + this.getToday() + "</p>");
      }
      html.append(
          "<input type=\"hidden\" name=\"year\" value=\"" + cal.get(
              Calendar.YEAR) + "\">");
      html.append(
          "<input type=\"hidden\" name=\"month\" value=\"" + (cal.get(
              Calendar.MONTH) + 1) + "\">");
    }
    html.append(
        "<input type=\"hidden\" name=\"day\" value=\"" + (cal.get(
            Calendar.DATE)) + "\">");
    return html.toString();
  }


  /**
   * Gets the htmlMonthSelect attribute of the CalendarView object
   *
   * @return The htmlMonthSelect value
   */
  private String getHtmlMonthSelect() {
    StringBuffer html = new StringBuffer();
    html.append(
        "<select size=\"1\" name=\"primaryMonth\" onChange=\"document.monthBean.submit();\">");
    for (int monthInt = 1; monthInt <= 12; monthInt++) {
      String selected = (this.getMonth().equals(String.valueOf(monthInt))) ? " selected" : "";
      html.append(
          "<option value=\"" + monthInt + "\"" + selected + ">" + StringUtils.toHtml(
              monthNames[monthInt - 1]) + "</option>");
    }
    html.append("</select>");
    return html.toString();
  }


  /**
   * Gets the htmlYearSelect attribute of the CalendarView object
   *
   * @return The htmlYearSelect value
   */
  private String getHtmlYearSelect() {
    StringBuffer html = new StringBuffer();
    html.append(
        "<select size=\"1\" name=\"primaryYear\" onChange=\"document.monthBean.submit();\">");
    for (int yearInt = cal.get(Calendar.YEAR) - 5; yearInt <= cal.get(
        Calendar.YEAR) + 5; yearInt++) {
      String selected = (this.getYear().equals(String.valueOf(yearInt))) ? " selected" : "";
      html.append(
          "<option value=\"" + yearInt + "\"" + selected + ">" + yearInt + "</option>");
    }
    html.append("</select>");
    return html.toString();
  }


  /**
   * Returns an ArrayList of CalendarEventLists which contain CalendarEvents,
   * including all of today's events.<p>
   * <p/>
   * A full day is always returned, if the events do not add up to (max) then
   * the next days is included. Scans up to 31 days.
   *
   * @param max Description of Parameter
   * @return The Events value
   */
  public ArrayList getEvents(int max) {
    ArrayList allDays = new ArrayList();
    int count = 0;
    int loopCount = 0;
    int dayCount = 0;

    Calendar tmpCal = Calendar.getInstance(timeZone, locale);
    if (calendarInfo != null) {
      if (calendarInfo.isAgendaView()) {
        dayCount = 7;
      } else if (calendarInfo.getCalendarView().equalsIgnoreCase("day")) {
        dayCount = 1;
        tmpCal.set(
            calendarInfo.getYearSelected(), calendarInfo.getMonthSelected() - 1, calendarInfo.getDaySelected());
      } else if (calendarInfo.getCalendarView().equalsIgnoreCase("week")) {
        dayCount = 7;
        tmpCal.set(
            calendarInfo.getYearSelected(), calendarInfo.getStartMonthOfWeek() - 1, calendarInfo.getStartDayOfWeek());
      }
    }
    while (count < max && loopCount < dayCount) {
      CalendarEventList thisEventList = getDaysEvents(
          tmpCal.get(Calendar.MONTH), tmpCal.get(Calendar.DAY_OF_MONTH), tmpCal.get(
              Calendar.YEAR));
      if (thisEventList.size() > 0) {
        thisEventList.setDate(DateUtils.getDate(tmpCal));
        allDays.add(thisEventList);
        if (System.getProperty("DEBUG") != null) {
          System.out.println("CalendarView-> Day added ");
        }
      }
      tmpCal.add(java.util.Calendar.DATE, +1);
      loopCount++;
      count++;
    }
    return allDays;
  }


  /**
   * Sets the Calendar with the required attributes. Creation date: (5/2/2000
   * 3:06:38 AM)
   */
  public void update() {
    cal.set(year, month, day);
    calPrev.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
    calPrev.add(Calendar.MONTH, -1);
    calNext.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
    calNext.add(Calendar.MONTH, 1);
  }


  /**
   * Adds a feature to the Events attribute of the CalendarView object
   *
   * @param eventDate The feature to be added to the Events attribute
   * @param eventType The feature to be added to the Events attribute
   * @param events    The feature to be added to the Events attribute
   */
  public void addEvents(String eventDate, String eventType, Object events) {
    CalendarEventList dailyEvents = null;
    if (eventList.containsKey(eventDate)) {
      dailyEvents = (CalendarEventList) eventList.get(eventDate);
    } else {
      dailyEvents = new CalendarEventList();
    }
    //Add the event to the list
    dailyEvents.put(eventType, events);

    //Add the events to the eventList
    this.eventList.put(eventDate, dailyEvents);
  }


  //Backwards compatible for month.jsp
  /**
   * Adds a feature to the Event attribute of the CalendarView object
   *
   * @param eventDate The feature to be added to the Event attribute
   * @param eventType The feature to be added to the Event attribute
   * @param event     The feature to be added to the Event attribute
   */
  public void addEvent(String eventDate, String eventType, Object event) {
    CalendarEventList dailyEvents = null;
    if (eventList.containsKey(eventDate)) {
      dailyEvents = (CalendarEventList) eventList.get(eventDate);
    } else {
      dailyEvents = new CalendarEventList();
    }
    //Add the event to the list
    dailyEvents.addEvent(eventType, event);
    if (System.getProperty("DEBUG") != null) {
      System.out.println(
          "CalendarView-> Event Type: " + eventType + " added on " + eventDate);
    }
    //Add the events to the eventList
    this.eventList.put(eventDate, dailyEvents);
  }


  /**
   * Adds a feature to the EventCount attribute of the CalendarView object
   *
   * @param eventType  The feature to be added to the EventCount attribute
   * @param eventCount The feature to be added to the EventCount attribute
   * @param eventDate  The feature to be added to the EventCount attribute
   */
  public void addEventCount(String eventDate, String eventType, Object eventCount) {
    CalendarEventList dailyEvents = null;
    if (eventList.containsKey(eventDate)) {
      dailyEvents = (CalendarEventList) eventList.get(eventDate);
    } else {
      dailyEvents = new CalendarEventList();
    }
    //Add the event to the list
    dailyEvents.addEventCount(eventType, eventCount);

    //Add the events to the eventList
    this.eventList.put(eventDate, dailyEvents);
  }


  /**
   * Gets the eventList attribute of the CalendarView object
   *
   * @param eventDate Description of the Parameter
   * @param eventType Description of the Parameter
   * @return The eventList value
   */
  public Object getEventList(String eventDate, String eventType) {
    CalendarEventList dailyEvents = getEventList(eventDate);
    Object thisEventList = dailyEvents.getEvents(eventType);
    this.eventList.put(eventDate, dailyEvents);
    return thisEventList;
  }


  /**
   * Adds a feature to the Holidays attribute of the CalendarView object
   */
  public void addHolidays() {
    int minYear = calPrev.get(Calendar.YEAR);
    int maxYear = calNext.get(Calendar.YEAR);
    if (minYear != maxYear) {
      addHolidays(minYear);
    }
    addHolidays(maxYear);
  }


  /**
   * Adds holidays for the specified year for the specified Locale
   *
   * @param theYear The feature to be added to the Holidays attribute
   */
  public void addHolidays(int theYear) {
    if (locale != null) {
      //TODO: use reflection to add holidays
      if ("US".equals(locale.getCountry())) {
        USHolidays.addTo(this, theYear);
      } else if ("CA".equals(locale.getCountry())) {
        CAHolidays.addTo(this, theYear);
      } else if ("DE".equals(locale.getCountry())) {
        DEHolidays.addTo(this, theYear);
      } else if ("UK".equals(locale.getCountry())) {
        UKHolidays.addTo(this, theYear);
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param tmpMonth Description of the Parameter
   * @param tmpDay   Description of the Parameter
   * @param tmpYear  Description of the Parameter
   * @return Description of the Returned Value
   */
  public boolean eventExists(String tmpMonth, String tmpDay, String tmpYear) {
    if (eventList.containsKey(tmpMonth + "/" + tmpDay + "/" + tmpYear)) {
      return true;
    } else {
      return false;
    }
  }


  /**
   * Checks to see if that day is a holiday
   *
   * @param tmp1 Description of the Parameter
   * @param tmp2 Description of the Parameter
   * @param tmp3 Description of the Parameter
   * @return The holiday value
   */
  public boolean isHoliday(String tmp1, String tmp2, String tmp3) {
    if (eventList.containsKey(tmp1 + "/" + tmp2 + "/" + tmp3)) {
      ArrayList tmpEvents = getEvents(
          tmp1, tmp2, tmp3, CalendarEventList.EVENT_TYPES[7]);
      if (tmpEvents.size() > 0) {
        return true;
      }
    }
    return false;
  }


  /**
   * Description of the Class
   *
   * @author mrajkowski
   * @version $Id: CalendarView.java,v 1.15 2002/04/23 17:58:04 mrajkowski Exp
   *          $
   * @created July 26, 2001
   */
  class ComparatorEvent implements Comparator {
    /**
     * Compares two events
     *
     * @param left  Description of Parameter
     * @param right Description of Parameter
     * @return Description of the Returned Value
     */
    public int compare(Object left, Object right) {
      if (((CalendarEvent) left).isHoliday() || ((CalendarEvent) right).isHoliday()) {
        String a = ((CalendarEvent) left).isHoliday() ? "A" : "B";
        String b = ((CalendarEvent) right).isHoliday() ? "A" : "B";
        return (a.compareTo(b));
      } else {
        return (
            ((CalendarEvent) left).getCategory().compareTo(
                ((CalendarEvent) right).getCategory()));
      }
    }
  }

}

