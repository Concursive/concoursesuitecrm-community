package org.aspcfs.utils.web;

import org.aspcfs.modules.mycfs.base.CalendarEvent;
import org.aspcfs.modules.mycfs.base.CalendarEventList;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import com.darkhorseventures.framework.actions.ActionContext;
import java.text.*;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.lang.reflect.*;

/**
 *  CalendarView.java Creates a monthly calendar and exports the HTML The
 *  current month is shown completely, the prev/next month is partially shown,
 *  but grayed out <p>
 *
 *  Working on outputting text entries for a date as well <p>
 *
 *  Can be used as a popup, or standalone HTML calendar, defined by parameters
 *  and/or properties <p>
 *
 *  If a date is supplied, that month is defaulted, otherwise the current month
 *  is displayed
 *
 *@author     mrajkowski based on bean from Maneesh Sahu
 *@created    March 2, 2001
 *@version    $Id$
 */
public class CalendarView {

  protected String[] monthNames = null;
  protected String[] shortMonthNames = null;
  protected DateFormatSymbols symbols = null;
  protected Calendar cal = Calendar.getInstance();
  protected int today = cal.get(Calendar.DAY_OF_MONTH);
  protected int day = cal.get(Calendar.DAY_OF_MONTH);
  protected int month = cal.get(Calendar.MONTH);
  protected int year = cal.get(Calendar.YEAR);
  protected Calendar calPrev = Calendar.getInstance();
  protected Calendar calNext = Calendar.getInstance();

  //Various settings for how the calendar looks
  protected boolean headerSpace = false;
  protected boolean monthArrows = false;
  protected boolean smallView = false;
  protected boolean frontPageView = false;
  protected boolean popup = false;
  protected boolean showSubject = true;

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


  /**
   *  The Default Constructor
   *
   *@since
   */
  public CalendarView() {
    this("EN", "US");
  }


  /**
   *  Constructor for the CalendarView object
   *
   *@param  request  Description of Parameter
   *@since
   */
  public CalendarView(HttpServletRequest request) {
    this("EN", "US");

    String year = request.getParameter("year");
    String month = request.getParameter("month");
    String day = request.getParameter("day");
    String origDay = request.getParameter("origDay");
    String origYear = request.getParameter("origYear");
    String origMonth = request.getParameter("origMonth");
    String dateString = request.getParameter("date");

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
        month = "" + monthTmp;
      } catch (NumberFormatException e) {
      }
    }
    this.setYear(year);
    this.setMonth(month);
    this.setDay(day);
  }


  /**
   *Constructor for using with the CalendarBean 
   *
   *@param  calendarInfo  Description of the Parameter
   */
  public CalendarView(CalendarBean calendarInfo) {
    this("EN", "US");
    
    this.calendarInfo = calendarInfo;
    int month = calendarInfo.getPrimaryMonth();
    int year = calendarInfo.getPrimaryYear();
    int day = calendarInfo.getDaySelected();
    this.setYear(year);
    this.setMonth(month);
    this.setDay(day);
  }


  /**
   *  Creates a CalendarView for a given locale
   *
   *@param  language  the two letter string code specifying a languge, "EN" for
   *      english for example
   *@param  region    the two letter string code specifying a region, "ES" for
   *      spain for example
   *@since
   */
  public CalendarView(String language, String region) {
    Locale theLocale = new Locale(language, region);
    setLocale(theLocale);
    this.update();
  }


  /**
   *  Sets the NumberOfCells attribute of the CalendarView object
   *
   *@param  numberOfCells  The new NumberOfCells value
   *@since
   */
  public void setNumberOfCells(int numberOfCells) {
    this.numberOfCells = numberOfCells;
  }


  /**
   *  Sets the calendarInfo attribute of the CalendarView object
   *
   *@param  calendarInfo  The new calendarInfo value
   */
  public void setCalendarInfo(CalendarBean calendarInfo) {
    this.calendarInfo = calendarInfo;
  }


  /**
   *  Sets the month property (java.lang.String) value.
   *
   *@param  monthArg  The new Month value
   *@since
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
   *  Sets the calendar by using a date object
   *
   *@param  tmp  The new Date value
   *@since
   */
  public void setDate(java.util.Date tmp) {
    cal.setTime(tmp);
    year = cal.get(Calendar.YEAR);
    month = cal.get(Calendar.MONTH);
    day = cal.get(Calendar.DAY_OF_MONTH);
    this.update();
  }


  /**
   *  Sets the FrontPageView attribute of the CalendarView object
   *
   *@param  frontPageView  The new FrontPageView value
   *@since
   */
  public void setFrontPageView(boolean frontPageView) {
    this.frontPageView = frontPageView;
  }


  /**
   *  Sets the day property (java.lang.String) value.
   *
   *@param  dayArg  The new Day value
   *@since
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
   *  Sets the day attribute of the CalendarView object
   *
   *@param  dayArg  The new day value
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
   *  Sets the ShowSubject attribute of the CalendarView object
   *
   *@param  showSubject  The new ShowSubject value
   *@since
   */
  public void setShowSubject(boolean showSubject) {
    this.showSubject = showSubject;
  }


  /**
   *  Sets the year property (java.lang.String) value.
   *
   *@param  yearArg  The new Year value
   *@since
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
   *  Sets the month property (int) value.
   *
   *@param  monthArg  The new Month value
   *@since
   */
  public void setMonth(int monthArg) {
    this.month = monthArg - 1;
    this.update();
  }


  /**
   *  Sets the year property (int) value.
   *
   *@param  yearArg  The new Year value
   *@since
   */
  public void setYear(int yearArg) {
    this.year = yearArg;
    this.update();
  }


  /**
   *  Sets the SortEvents attribute of the CalendarView object
   *
   *@param  tmp  The new SortEvents value
   *@since
   */
  public void setSortEvents(boolean tmp) {
    this.sortEvents = tmp;
  }


  /**
   *  Sets the MonthArrows attribute of the CalendarView object
   *
   *@param  tmp  The new MonthArrows value
   *@since
   */
  public void setMonthArrows(boolean tmp) {
    this.monthArrows = tmp;
  }


  /**
   *  Sets the SmallView attribute of the CalendarView object
   *
   *@param  tmp  The new SmallView value
   *@since
   */
  public void setSmallView(boolean tmp) {
    this.smallView = tmp;
  }


  /**
   *  Sets the Popup attribute of the CalendarView object
   *
   *@param  tmp  The new Popup value
   *@since
   */
  public void setPopup(boolean tmp) {
    this.popup = tmp;
    if (this.popup) {
      this.setMonthArrows(true);
      this.setSmallView(true);
    }
  }


  /**
   *  Sets the CellPadding attribute of the CalendarView object
   *
   *@param  tmp  The new CellPadding value
   *@since
   */
  public void setCellPadding(int tmp) {
    this.cellPadding = " cellpadding='" + tmp + "'";
  }


  /**
   *  Sets the CellSpacing attribute of the CalendarView object
   *
   *@param  tmp  The new CellSpacing value
   *@since
   */
  public void setCellSpacing(int tmp) {
    this.cellSpacing = " cellspacing='" + tmp + "'";
  }


  /**
   *  Sets the HeaderSpace attribute of the CalendarView object
   *
   *@param  tmp  The new HeaderSpace value
   *@since
   */
  public void setHeaderSpace(boolean tmp) {
    this.headerSpace = tmp;
  }


  /**
   *  Sets the Locale attribute of the CalendarView object
   *
   *@param  theLocale  The new Locale value
   *@since
   */
  public void setLocale(Locale theLocale) {
    symbols = new DateFormatSymbols(theLocale);
    monthNames = symbols.getMonths();
    shortMonthNames = symbols.getShortMonths();
  }


  /**
   *  Gets the calendarInfo attribute of the CalendarView object
   *
   *@return    The calendarInfo value
   */
  public CalendarBean getCalendarInfo() {
    return calendarInfo;
  }


  /**
   *  Gets the ShowSubject attribute of the CalendarView object
   *
   *@return    The ShowSubject value
   *@since
   */
  public boolean getShowSubject() {
    return showSubject;
  }


  /**
   *  Gets the NumberOfCells attribute of the CalendarView object
   *
   *@return    The NumberOfCells value
   *@since
   */
  public int getNumberOfCells() {
    return numberOfCells;
  }


  /**
   *  Gets the FrontPageView attribute of the CalendarView object
   *
   *@return    The FrontPageView value
   *@since
   */
  public boolean getFrontPageView() {
    return frontPageView;
  }


  /**
   *  Returns a list representative of the event objects
   *
   *@param  tmp1  Description of Parameter
   *@param  tmp2  Description of Parameter
   *@param  tmp3  Description of Parameter
   *@return       The Events value
   *@since
   */
  public ArrayList getEvents(String tmp1, String tmp2, String tmp3) {
    String key = tmp1 + "/" + tmp2 + "/" + tmp3;
    if (eventList.containsKey(key)) {
      return (ArrayList) eventList.get(key);
    } else {
      return new ArrayList();
    }
  }


  /**
   *  Gets the eventList attribute of the CalendarView object
   *
   *@param  tmp1  Description of the Parameter
   *@param  tmp2  Description of the Parameter
   *@param  tmp3  Description of the Parameter
   *@return       The eventList value
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
   *  Gets the eventCount attribute of the CalendarView object
   *
   *@param  tmp1           Description of the Parameter
   *@param  tmp2           Description of the Parameter
   *@param  tmp3           Description of the Parameter
   *@param  eventCategory  Description of the Parameter
   *@return                The eventCount value
   */
  public int getEventCount(int tmp1, int tmp2, int tmp3, String eventCategory) {
    String key = tmp1 + "/" + tmp2 + "/" + tmp3;
    ArrayList tmpList = null;
    int eventCount = 0;
    if (eventList.containsKey(key)) {
      tmpList = (ArrayList) eventList.get(key);
    }
    Iterator events = tmpList.iterator();
    if (events.hasNext()) {
      while (events.hasNext()) {
        CalendarEvent thisEvent = (CalendarEvent) events.next();
        if (thisEvent.getCategory().toUpperCase().startsWith(eventCategory.toUpperCase())) {
          eventCount++;
        }
      }
    }
    return eventCount;
  }


  /**
   *  Returns the cell representing the last day in the 42 cell grid Creation
   *  date: (5/2/2000 2:57:08 AM)
   *
   *@param  tmp  Description of Parameter
   *@return      int
   *@since
   */
  public int getEndCell(Calendar tmp) {
    int endCell = DAYSINMONTH[tmp.get(Calendar.MONTH)] + this.getStartCell(tmp) - 1;
    if (tmp.get(Calendar.MONTH) == Calendar.FEBRUARY
         && ((GregorianCalendar) tmp).isLeapYear(tmp.get(Calendar.YEAR))) {
      endCell++;
    }
    return endCell;
  }


  /**
   *  Returns the year of the Calendar item
   *
   *@param  tmp  Description of Parameter
   *@return      The Year value
   *@since
   */
  public int getYear(Calendar tmp) {
    return tmp.get(Calendar.YEAR);
  }


  /**
   *  Gets the Day attribute of the CalendarView object
   *
   *@return    The Day value
   *@since
   */
  public String getDay() {
    return "" + day;
  }


  /**
   *  Gets the Month attribute of the CalendarView object
   *
   *@return    The Month value
   *@since
   */
  public String getMonth() {
    return "" + (cal.get(Calendar.MONTH) + 1);
  }


  /**
   *  Gets the Year attribute of the CalendarView object
   *
   *@return    The Year value
   *@since
   */
  public String getYear() {
    return "" + cal.get(Calendar.YEAR);
  }


  /**
   *  Returns the Month Name Creation date: (5/2/2000 2:49:08 AM)
   *
   *@param  tmp  Description of Parameter
   *@return      java.lang.String
   *@since
   */
  public String getMonthName(Calendar tmp) {
    return monthNames[tmp.get(Calendar.MONTH)];
  }


  /**
   *  Returns the Short Month Name Creation date: (5/2/2000 2:49:08 AM)
   *
   *@param  tmp  Description of Parameter
   *@return      java.lang.String
   *@since
   */
  public String getShortMonthName(Calendar tmp) {
    return shortMonthNames[tmp.get(Calendar.MONTH)];
  }


  /**
   *  Returns the cell representing the first day of the month in the 42 cell
   *  grid Creation date: (5/2/2000 2:51:35 AM)
   *
   *@param  tmp  Description of Parameter
   *@return      int
   *@since
   */
  public int getStartCell(Calendar tmp) {
    Calendar beginOfMonth = Calendar.getInstance();
    beginOfMonth.set(tmp.get(Calendar.YEAR), tmp.get(Calendar.MONTH), 0);
    return beginOfMonth.get(Calendar.DAY_OF_WEEK);
  }


  /**
   *  Gets the calendarStartDate attribute of the CalendarView object
   *
   *@param  context  Description of the Parameter
   *@return          The calendarStartDate value
   */
  public java.sql.Date getCalendarStartDate(ActionContext context) {
    int displayMonth = 0;
    int displayDay = 0;
    int displayYear = 0;
    String source = context.getRequest().getParameter("source");
    if (source != null) {
      if (calendarInfo.isAgendaView() && source.equalsIgnoreCase("calendarDetails")) {
        Calendar today = Calendar.getInstance();
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
          displayDay = (this.getEndCell(calPrev) - this.getStartCell(cal) + 2 - this.getStartCell(calPrev));
          displayYear = calPrev.get(Calendar.YEAR);
        }
      } else {
        displayMonth = calPrev.get(Calendar.MONTH) + 1;
        displayDay = (this.getEndCell(calPrev) - this.getStartCell(cal) + 2 - this.getStartCell(calPrev));
        displayYear = calPrev.get(Calendar.YEAR);
      }
    }
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CalendarView-> Start Day: " + displayMonth + "/" + displayDay + "/" + displayYear);
    }
    return (java.sql.Date.valueOf(displayYear + "-" + displayMonth + "-" + displayDay));
  }


  /**
   *  Gets the calendarEndDate attribute of the CalendarView object
   *
   *@param  context  Description of the Parameter
   *@return          The calendarEndDate value
   */
  public java.sql.Date getCalendarEndDate(ActionContext context) {
    int displayMonth = 0;
    int displayDay = 0;
    int displayYear = 0;
    String source = context.getRequest().getParameter("source");
    if (source != null) {
      if (calendarInfo.isAgendaView() && source.equalsIgnoreCase("calendarDetails")) {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DATE, 6);
        displayMonth = today.get(Calendar.MONTH) + 1;
        displayDay = today.get(Calendar.DAY_OF_MONTH);
        displayYear = today.get(Calendar.YEAR);
      } else if (!source.equalsIgnoreCase("Calendar")) {
        if (calendarInfo.getCalendarView().equalsIgnoreCase("day")) {
          displayMonth = calendarInfo.getMonthSelected();
          displayDay = calendarInfo.getDaySelected();
          displayYear = calendarInfo.getYearSelected();
        } else if (calendarInfo.getCalendarView().equalsIgnoreCase("week")) {
          Calendar newDate = Calendar.getInstance();
          newDate.set(calendarInfo.getYearSelected(), calendarInfo.getStartMonthOfWeek() - 1, calendarInfo.getStartDayOfWeek());
          newDate.add(Calendar.DATE, 6);
          displayMonth = newDate.get(Calendar.MONTH) + 1;
          displayDay = newDate.get(Calendar.DATE);
          displayYear = newDate.get(Calendar.YEAR);
        } else {
          displayMonth = calNext.get(Calendar.MONTH) + 1;
          displayYear = calNext.get(Calendar.YEAR);
          displayDay = numberOfCells - getEndCell(cal) - 1;
        }
      } else {
        displayMonth = calNext.get(Calendar.MONTH) + 1;
        displayYear = calNext.get(Calendar.YEAR);
        displayDay = numberOfCells - getEndCell(cal) - 1;
      }
    }
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CalendarView-> End Day: " + displayMonth + "/" + displayDay + "/" + displayYear);
    }
    return (java.sql.Date.valueOf(displayYear + "-" + displayMonth + "-" + displayDay));
  }


  /**
   *  Returns true if today is the current calendar day being drawn
   *
   *@param  tmp     Description of Parameter
   *@param  indate  Description of Parameter
   *@return         The CurrentDay value
   *@since
   */
  public boolean isCurrentDay(Calendar tmp, int indate) {
    Calendar thisMonth = Calendar.getInstance();
    if ((indate == thisMonth.get(Calendar.DAY_OF_MONTH)) &&
        (tmp.get(Calendar.MONTH) == thisMonth.get(Calendar.MONTH)) &&
        (tmp.get(Calendar.YEAR) == thisMonth.get(Calendar.YEAR))) {
      return true;
    } else {
      return false;
    }
  }


  /**
   *  Gets the Event attribute of the CalendarView object
   *
   *@param  tmp1  Description of Parameter
   *@param  tmp2  Description of Parameter
   *@param  tmp3  Description of Parameter
   *@return       The Event value
   *@since
   */
  public CalendarEvent getEvent(String tmp1, String tmp2, String tmp3) {
    String key = tmp1 + "/" + tmp2 + "/" + tmp3;
    if (eventList.containsKey(key)) {
      return (CalendarEvent) ((ArrayList) eventList.get(key)).get(0);
    } else {
      return null;
    }
  }


  /**
   *  Returns the week day name Creation date: (5/2/2000 2:50:10 AM)
   *
   *@param  day         int
   *@param  longFormat  Description of Parameter
   *@return             java.lang.String
   *@since
   */
  public String getDayName(int day, boolean longFormat) {
    if (longFormat) {
      return symbols.getShortWeekdays()[day];
    }
    return symbols.getWeekdays()[day];
  }


  /**
   *  Gets the Today attribute of the CalendarView object
   *
   *@return    The Today value
   *@since
   */
  public String getToday() {
    Calendar today = Calendar.getInstance();
    return (this.getMonthName(today) + " " + today.get(Calendar.DAY_OF_MONTH) + ", " + today.get(Calendar.YEAR));
  }


  /**
   *  Gets the synchFrameCounter attribute of the HtmlDialog object
   *
   *@return    The synchFrameCounter value
   */
  public int getSynchFrameCounter() {
    return synchFrameCounter;
  }


  /**
   *  Description of the Method
   */
  public synchronized void decrementSynchFrameCounter() {
    --synchFrameCounter;
  }


  /**
   *  Gets the DaysEvents attribute of the CalendarView object
   *
   *@param  m  Description of Parameter
   *@param  d  Description of Parameter
   *@param  y  Description of Parameter
   *@return    The DaysEvents value
   *@since
   */
  public ArrayList getDaysEvents(int m, int d, int y) {
    int displayMonth = m + 1;
    int displayYear = y;
    int displayDay = d;

    //Get this day's events
    ArrayList tmpEvents = getEvents("" + displayMonth, "" + displayDay, "" + displayYear);

    //Sort the events
    if (sortEvents && tmpEvents.size() > 1) {
      Object sortArray[] = tmpEvents.toArray();
      Comparator comparator = null;
      comparator = new ComparatorEvent();
      Arrays.sort(sortArray, comparator);
      tmpEvents.clear();
      for (int i = 0; i < sortArray.length; i++) {
        tmpEvents.add((CalendarEvent) sortArray[i]);
      }
    }
    return tmpEvents;
  }


  /**
   *  Constructs the calendar and returns a String object with the HTML
   *
   *@return    The HTML value
   *@since
   */
  public String getHtml() {
    StringBuffer html = new StringBuffer();

    //Begin the whole table
    html.append(
        "<table width='98%' valign='top' cellspacing='0' cellpadding='0' border='0' bgcolor='#ffffff'>" +
        "<tr><td>");

    //Space at top to match CFS
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
        "<center><table height=\"100%\" width='" + tableWidth + "'" + cellSpacing + cellPadding + " class='" + pre + "calendar' id='calendarTable'>" +
        "<tr name=\"staticrow\" height=\"4%\">");

    //Display Previous Month Arrow
    if (popup) {
      if (monthArrows) {
        html.append("<th colspan='1' class='" + pre + "monthArrowPrev'>" + monthArrowPrev + "</th>");
      }

      //Display Current Month name
      if (monthArrows) {
        html.append("<th colspan='5' ");
      } else {
        html.append("<th colspan='7' ");
      }
      html.append("class='" + pre + "monthName'");
      html.append("><B>" + this.getMonthName(cal) + " " + this.getYear(cal) + "</B></th>");
      //Display Next Month Arrow
      if (monthArrows) {
        html.append("<th colspan='1' class='" + pre + "monthArrowNext'>" + monthArrowNext + "</th>");
      }
    } else {
      html.append("<th colspan=\"8\">");
      html.append(getHtmlMonthSelect());
      html.append("&nbsp;");
      html.append(getHtmlYearSelect());
      html.append("&nbsp;");
      html.append("<a href=\"javascript:showToDaysEvents('" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "','" + Calendar.getInstance().get(Calendar.DATE) + "','" + Calendar.getInstance().get(Calendar.YEAR) + "');\">Today</a>");
      html.append("</th>");
    }
    html.append("</tr>");

    //Display the Days of the Week names
    html.append("<tr name=\"staticrow\" height=\"4%\">");
    if (!popup) {
      html.append("<td width=\"4\" class=\"row1\"><font style=\"visibility:hidden\">n</font></td>");
    }

    for (int i = 1; i < 8; i++) {
      html.append("<td width=\"14%\" class='" + pre + "weekName'>");
      //width='70'
      if (popup || frontPageView) {
        html.append(this.getDayName(i, true));
      } else {
        html.append(this.getDayName(i, false));
      }
      html.append("</td>");
    }
    html.append("</tr>");
    int startCellPrev = this.getStartCell(calPrev);
    int endCellPrev = this.getEndCell(calPrev);

    int startCell = this.getStartCell(cal);
    int endCell = this.getEndCell(cal);

    int startCellNext = this.getStartCell(calNext);
    int endCellNext = this.getEndCell(calNext);
    int thisDay = 1;
    String tdClass = "";
    for (int cellNo = 0; cellNo < this.getNumberOfCells(); cellNo++) {

      // end check for start of row

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
        //The main main
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
        html.append("<td valign='top' width=\"4\" class=\"weekSelector\" name=\"weekSelector\">");
        String weekSelectedArrow = "<a href=\"javascript:showWeekEvents('" + displayYear + "','" + displayMonth + "','" + displayDay + "')\">" + "<img ALIGN=\"MIDDLE\" src=\"images/next.gif\" border=\"0\" onclick=\"javascript:switchTableClass(this,'selectedWeek','row','<%=User.getBrowserId()%>');\"></a>";
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
        html.append(" onclick=\"javascript:showDayEvents('" + displayYear + "','" + displayMonth + "','" + displayDay + "');javascript:switchTableClass(this,'selectedDay','cell','<%=User.getBrowserId()%>');\"");
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
          html.append(" id='today' class='" + ((tdClass.equalsIgnoreCase("")) ? pre + "today'" : tdClass + "'") + " name='" + pre + "today' >");
        } else {
          html.append(" class='" + ((tdClass.equalsIgnoreCase("")) ? pre + "noday'" : tdClass + "'") + " name='" + pre + "noday' >");
        }
      } else if (nextMonth) {
        if (this.isCurrentDay(calNext, displayDay)) {
          html.append(" id='today' class='" + ((tdClass.equalsIgnoreCase("")) ? pre + "today'" : tdClass + "'") + " name='" + pre + "today' >");
        } else {
          html.append(" class='" + ((tdClass.equalsIgnoreCase("")) ? pre + "noday'" : tdClass + "'") + " name='" + pre + "noday' >");
        }
      } else {
        //The main main
        if (this.isCurrentDay(cal, displayDay)) {
          html.append(" id='today' class='" + ((tdClass.equalsIgnoreCase("")) ? pre + "today'" : tdClass + "'") + " name='" + pre + "today' >");
        } else {
          html.append(" class='" + ((tdClass.equalsIgnoreCase("")) ? pre + "day'" : tdClass + "'") + " name='" + pre + "day' >");
        }
      }
      // end if block
      //Display the day in the appropriate link color
      if (popup) {
        //Popup calendar
        CalendarEvent tmpEvent = getEvent("" + displayMonth, "" + displayDay, "" + displayYear);
        String dateColor = "" + displayDay;
        if ((tmpEvent != null) && (!tmpEvent.getCategory().equals("blank"))) {
          dateColor = "<font color=#FF0000>" + displayDay + "</font>";
        } else if (!mainMonth) {
          dateColor = "<font color=#888888>" + displayDay + "</font>";
        }
        html.append("<a href=\"javascript:returnDate(" + displayDay + ", " + displayMonth + ", " + displayYear + ");\"" + ">" +
            dateColor + "</a>");
      } else {
        //Event calendar
        String dateColor = "" + displayDay;
        if (!mainMonth) {
          dateColor = "<font color=#888888>" + displayDay + "</font>";
        }
        html.append("<a href=\"javascript:showDayEvents('" + displayYear + "','" + displayMonth + "','" + displayDay + "');\">" + dateColor + "</a>");

        if (this.isHoliday(displayMonth + "", displayDay + "", displayYear + "")) {
          html.append(CalendarEvent.getIcon("holiday") + "<font color=blue><br>");
        }

        //get all events categories and respective counts.
        HashMap events = this.getEventList(displayMonth + "", displayDay + "", displayYear + "").getEventTypes();
        if (events != null) {
          Set s = events.keySet();
          Iterator i = s.iterator();
          int count = 0;
          html.append("<table width=\"12%\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"dayIcon\">");
          while (i.hasNext()) {
            Object eventCategory = i.next();
            if (((Integer) events.get(eventCategory)).intValue() > 0) {
              ++count;
              html.append("<tr><td>" + CalendarEvent.getIcon(String.valueOf(eventCategory)) + "</td><td> " + events.get(eventCategory) + "</td></tr>");
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
      Calendar tmp = Calendar.getInstance();
      int displayMonth = tmp.get(Calendar.MONTH) + 1;
      int displayYear = tmp.get(Calendar.YEAR);
      int displayDay = tmp.get(Calendar.DAY_OF_MONTH);
      html.append("<p class='smallfooter'>Today is: " + "<a href=\"javascript:returnDate(" + displayDay + ", " + displayMonth + ", " + displayYear + ");\"" + ">" + this.getToday() + "</p>");
      html.append("<input type=\"hidden\" name=\"year\" value=\"" + cal.get(Calendar.YEAR) + "\">");
      html.append("<input type=\"hidden\" name=\"month\" value=\"" + (cal.get(Calendar.MONTH) + 1) + "\">");
    }
    html.append("<input type=\"hidden\" name=\"day\" value=\"" + (cal.get(Calendar.DATE)) + "\">");

    return html.toString();
  }


  /**
   *  Gets the htmlMonthSelect attribute of the CalendarView object
   *
   *@return    The htmlMonthSelect value
   */
  private String getHtmlMonthSelect() {
    StringBuffer html = new StringBuffer();
    html.append("<select size=\"1\" name=\"primaryMonth\" onChange=\"document.forms[0].submit();\">");
    for (int month = 1; month <= 12; month++) {
      String selected = (this.getMonth().equals(String.valueOf(month))) ? " selected" : "";
      html.append("<option value=\"" + month + "\"" + selected + ">" + monthNames[month - 1] + "</option>");
    }
    html.append("</select>");
    return html.toString();
  }


  /**
   *  Gets the htmlYearSelect attribute of the CalendarView object
   *
   *@return    The htmlYearSelect value
   */
  private String getHtmlYearSelect() {
    StringBuffer html = new StringBuffer();
    html.append("<select size=\"1\" name=\"primaryYear\" onChange=\"document.forms[0].submit();\">");
    for (int year = 1998; year <= 2010; year++) {
      String selected = (this.getYear().equals(String.valueOf(year))) ? " selected" : "";
      html.append("<option value=\"" + year + "\"" + selected + ">" + year + "</option>");
    }
    html.append("</select>");
    return html.toString();
  }


  /**
   *  Returns an ArrayList of CalendarEventLists which contain CalendarEvents,
   *  including all of today's events.<p>
   *
   *  A full day is always returned, if the events do not add up to (max) then
   *  the next days is included. Scans up to 31 days.
   *
   *@param  max  Description of Parameter
   *@return      The Events value
   *@since
   */
  public ArrayList getEvents(int max) {
    ArrayList allDays = new ArrayList();
    ArrayList thisDay = null;
    String val = "";
    int count = 0;
    int loopCount = 0;
    int dayCount = 0;
    StringBuffer html = new StringBuffer();

    //Calendar tmpCal = new GregorianCalendar();
    Calendar tmpCal = Calendar.getInstance();
    Date now = new Date();
    if (calendarInfo != null) {
      if (calendarInfo.isAgendaView()) {
        dayCount = 7;
      } else if (calendarInfo.getCalendarView().equalsIgnoreCase("day")) {
        dayCount = 1;
        tmpCal.set(calendarInfo.getYearSelected(), calendarInfo.getMonthSelected() - 1, calendarInfo.getDaySelected());
      } else if (calendarInfo.getCalendarView().equalsIgnoreCase("week")) {
        dayCount = 7;
        tmpCal.set(calendarInfo.getYearSelected(), calendarInfo.getStartMonthOfWeek() - 1, calendarInfo.getStartDayOfWeek());
      }
    }

    while (count < max && loopCount < dayCount) {
      thisDay = getDaysEvents(tmpCal.get(Calendar.MONTH), tmpCal.get(Calendar.DAY_OF_MONTH), tmpCal.get(Calendar.YEAR));

      Iterator i = thisDay.iterator();
      if (i.hasNext()) {
        CalendarEventList thisEventList = new CalendarEventList();
        thisEventList.setDate(tmpCal.getTime());
        allDays.add(thisEventList);
        if (System.getProperty("DEBUG") != null) {
          System.out.println("CalendarView-> Day added");
        }
        while (i.hasNext() && count < max) {
          CalendarEvent thisEvent = (CalendarEvent) i.next();
          thisEventList.add(thisEvent);
          if (System.getProperty("DEBUG") != null) {
            System.out.println("CalendarView-> Event added: " + thisEvent.getCategory());
          }
          count++;
        }
      }
      tmpCal.add(java.util.Calendar.DATE, +1);
      loopCount++;
    }
    return allDays;
  }



  /**
   *  Adds a feature to the EventCount attribute of the CalendarView object
   *
   *@param  type   The feature to be added to the EventCount attribute
   *@param  date   The feature to be added to the EventCount attribute
   *@param  count  The feature to be added to the EventCount attribute
   */
  public void addEventCount(String type, String date, Object count) {
    CalendarEventList thisDay = null;
    if (eventList.get(date) == null) {
      eventList.put(date, new CalendarEventList());
    }
    thisDay = (CalendarEventList) eventList.get(date);
    thisDay.addEventType(type, count);
  }


  /**
   *  Sets the Calendar with the required attributes. Creation date: (5/2/2000
   *  3:06:38 AM)
   *
   *@since
   */
  public void update() {
    cal.set(year, month, day);
    //1
    calPrev.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
    calPrev.add(Calendar.MONTH, -1);
    calNext.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
    calNext.add(Calendar.MONTH, 1);
  }


  /**
   *  The calendar should have used date objects...
   *
   *@param  eventDate  The feature to be added to the Event attribute
   *@param  subject    The feature to be added to the Event attribute
   *@param  category   The feature to be added to the Event attribute
   *@param  id         The feature to be added to the Event attribute
   *@return            Description of the Return Value
   */
  public CalendarEvent addEvent(java.sql.Timestamp eventDate, String subject, String category, int id) {
    return addEvent(eventDate, subject, category, id, -1);
  }


  /**
   *  Adds a feature to the Event attribute of the CalendarView object
   *
   *@param  eventDate  The feature to be added to the Event attribute
   *@param  subject    The feature to be added to the Event attribute
   *@param  category   The feature to be added to the Event attribute
   *@param  idmain     The feature to be added to the Event attribute
   *@param  idsub      The feature to be added to the Event attribute
   *@return            Description of the Return Value
   */
  public CalendarEvent addEvent(java.sql.Timestamp eventDate, String subject, String category, int idmain, int idsub) {
    CalendarEvent thisEvent = new CalendarEvent();
    if (eventDate != null) {
      SimpleDateFormat shortDateFormat = new SimpleDateFormat("M/d/yyyy");
      String eventDateString = shortDateFormat.format(eventDate);
      return addEvent(eventDateString, "", subject, category, idmain, idsub, -1);
    }
    return null;
  }


  //Backwards compatible for month.jsp
  /**
   *  Adds a feature to the Event attribute of the CalendarView object
   *
   *@param  eventDate  The feature to be added to the Event attribute
   *@param  subject    The feature to be added to the Event attribute
   *@param  category   The feature to be added to the Event attribute
   *@return            Description of the Return Value
   */
  public CalendarEvent addEvent(String eventDate, String subject, String category) {
    return addEvent(eventDate, "", subject, category);
  }


  /**
   *  Adds a feature to the Event attribute of the CalendarView object
   *
   *@param  eventDate  The feature to be added to the Event attribute
   *@param  eventTime  The feature to be added to the Event attribute
   *@param  subject    The feature to be added to the Event attribute
   *@param  category   The feature to be added to the Event attribute
   *@return            Description of the Return Value
   */
  public CalendarEvent addEvent(String eventDate, String eventTime, String subject, String category) {
    return addEvent(eventDate, eventTime, subject, category, -1, -1, -1);
  }


  /**
   *  Adds a feature to the Event attribute of the CalendarView object
   *
   *@param  eventDate  The feature to be added to the Event attribute
   *@param  eventTime  The feature to be added to the Event attribute
   *@param  subject    The feature to be added to the Event attribute
   *@param  category   The feature to be added to the Event attribute
   *@param  id         The feature to be added to the Event attribute
   *@return            Description of the Return Value
   */
  public CalendarEvent addEvent(String eventDate, String eventTime, String subject, String category, int id) {
    return addEvent(eventDate, eventTime, subject, category, id, -1, -1);
  }


  /**
   *  Adds a feature to the Event attribute of the CalendarView object
   *
   *@param  eventDate  The feature to be added to the Event attribute
   *@param  eventTime  The feature to be added to the Event attribute
   *@param  subject    The feature to be added to the Event attribute
   *@param  category   The feature to be added to the Event attribute
   *@param  idmain     The feature to be added to the Event attribute
   *@param  idsub      The feature to be added to the Event attribute
   *@return            Description of the Return Value
   */
  public CalendarEvent addEvent(String eventDate, String eventTime, String subject, String category, int idmain, int idsub) {
    return addEvent(eventDate, eventTime, subject, category, idmain, idsub, -1);
  }


  /**
   *  Adds a feature to the Event attribute of the CalendarView object
   *
   *@param  eventDate  The feature to be added to the Event attribute
   *@param  eventTime  The feature to be added to the Event attribute
   *@param  subject    The feature to be added to the Event attribute
   *@param  category   The feature to be added to the Event attribute
   *@param  idmain     The feature to be added to the Event attribute
   *@param  idsub      The feature to be added to the Event attribute
   *@param  status     The feature to be added to the Event attribute
   *@return            Description of the Return Value
   */
  public CalendarEvent addEvent(String eventDate, String eventTime, String subject, String category, int idmain, int idsub, int status) {
    CalendarEvent thisEvent = new CalendarEvent();
    StringTokenizer st = new StringTokenizer(eventDate, "/");
    if (st.hasMoreTokens()) {
      thisEvent.setMonth(st.nextToken());
      thisEvent.setDay(st.nextToken());
      thisEvent.setYear(st.nextToken());
    }
    thisEvent.setTime(eventTime);
    thisEvent.setSubject(subject);
    thisEvent.setCategory(category);
    thisEvent.setId(idmain);
    thisEvent.setIdsub(idsub);
    thisEvent.setStatus(status);
    this.addEvent(thisEvent);
    return thisEvent;
  }


  /**
   *  Adds a feature to the Event attribute of the CalendarView object
   *
   *@param  thisEvent  The feature to be added to the Event attribute
   */
  public void addEvent(CalendarEvent thisEvent) {
    //Check to see if the eventList already has dailyEvents for the eventDate
    CalendarEventList dailyEvents = null;
    if (eventList.containsKey(thisEvent.getDateString())) {
      dailyEvents = (CalendarEventList) eventList.get(thisEvent.getDateString());
    } else {
      dailyEvents = new CalendarEventList();
    }
    //Add the event to the list
    dailyEvents.add(thisEvent);

    //Add the events to the eventList
    this.eventList.put(thisEvent.getDateString(), dailyEvents);
  }


  /**
   *  Adds a feature to the HolidaysByRange attribute of the CalendarView object
   */
  public void addHolidaysByRange() {
    ArrayList thisDay = null;
    CalendarView tempView = new CalendarView();

    if (calendarInfo != null) {
      tempView.setYear(calendarInfo.getYearSelected());
      tempView.addHolidays();
      Calendar tmpCal = Calendar.getInstance();
      int dayCount = 0;
      if (calendarInfo.isAgendaView()) {
        dayCount = 7;
      } else if (calendarInfo.getCalendarView().equalsIgnoreCase("day")) {
        dayCount = 1;
        tmpCal.set(calendarInfo.getYearSelected(), calendarInfo.getMonthSelected() - 1, calendarInfo.getDaySelected());
      } else if (calendarInfo.getCalendarView().equalsIgnoreCase("week")) {
        dayCount = 7;
        tmpCal.set(calendarInfo.getYearSelected(), calendarInfo.getStartMonthOfWeek() - 1, calendarInfo.getStartDayOfWeek());
      }
      for (int j = 0; j < dayCount; j++) {
        thisDay = tempView.getDaysEvents(tmpCal.get(Calendar.MONTH), tmpCal.get(Calendar.DAY_OF_MONTH), tmpCal.get(Calendar.YEAR));
        Iterator i = thisDay.iterator();
        if (i.hasNext()) {
          if (System.getProperty("DEBUG") != null) {
            System.out.println("CalendarView-> Day added: " + tmpCal.get(Calendar.MONTH) + "/" + tmpCal.get(Calendar.DAY_OF_MONTH) + "/" + tmpCal.get(Calendar.YEAR));
          }
          while (i.hasNext()) {
            CalendarEvent thisEvent = (CalendarEvent) i.next();
            if (thisEvent.getCategory().equalsIgnoreCase("holiday")) {
              this.addEvent(thisEvent);
            }
          }
        }
        tmpCal.add(java.util.Calendar.DATE, +1);
      }
    }
  }


  /**
   *  Adds a feature to the Holidays attribute of the CalendarView object
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
   *  Adds a feature to the Holidays attribute of the CalendarView object
   *
   *@param  theYear  The feature to be added to the Holidays attribute
   *@since
   */
  public void addHolidays(int theYear) {
    Calendar tmpCal = new GregorianCalendar();
    int dayOfWeek = -1;

    //New Year's Day
    this.addEvent("1/1/" + theYear, "", "New Year's Day", "holiday");

    //Martin Luther Kings birthday : third Monday in January;
    tmpCal.set(theYear, Calendar.JANUARY, 1);
    dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    while (dayOfWeek != Calendar.MONDAY) {
      tmpCal.add(Calendar.DATE, 1);
      dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    }
    this.addEvent("1/" + (tmpCal.get(Calendar.DATE) + 14) + "/" + theYear, "", "Martin Luther King's Birthday", "holiday");

    //Washington's birthday : third Monday in February; (President's Day)
    tmpCal.set(theYear, Calendar.FEBRUARY, 1);
    dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    while (dayOfWeek != Calendar.MONDAY) {
      tmpCal.add(Calendar.DATE, 1);
      dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    }
    this.addEvent("2/" + (tmpCal.get(Calendar.DATE) + 14) + "/" + theYear, "", "President's Day", "holiday");

    //Memorial Day : last Monday in May;
    tmpCal.set(theYear, Calendar.MAY, 1);
    dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    while (dayOfWeek != Calendar.MONDAY) {
      tmpCal.add(Calendar.DATE, 1);
      dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    }
    //With the first Monday, see if May has 4 or 5 Mondays
    tmpCal.add(Calendar.DATE, 28);
    if (tmpCal.get(Calendar.MONTH) != Calendar.MAY) {
      tmpCal.add(Calendar.DATE, -7);
    }
    this.addEvent("5/" + (tmpCal.get(Calendar.DATE)) + "/" + theYear, "", "Memorial Day", "holiday");

    //Independence Day : July 4 (moved to Monday if Sunday, Friday if Saturday);
    this.addEvent("7/4/" + theYear, "", "Independence Day", "holiday");
    tmpCal.set(theYear, Calendar.JULY, 4);
    dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    if (dayOfWeek == Calendar.SUNDAY) {
      this.addEvent("7/5/" + theYear, "", "Independence Day (Bank Holiday)", "holiday");
    } else if (dayOfWeek == Calendar.SATURDAY) {
      this.addEvent("7/3/" + theYear, "", "Independence Day (Bank Holiday)", "holiday");
    }

    //Labor Day : first Monday in September;
    tmpCal.set(theYear, Calendar.SEPTEMBER, 1);
    dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    while (dayOfWeek != Calendar.MONDAY) {
      tmpCal.add(Calendar.DATE, 1);
      dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    }
    this.addEvent("9/" + (tmpCal.get(Calendar.DATE)) + "/" + theYear, "", "Labor Day", "holiday");

    //Columbus Day : second Monday in October;
    tmpCal.set(theYear, Calendar.OCTOBER, 1);
    dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    while (dayOfWeek != Calendar.MONDAY) {
      tmpCal.add(Calendar.DATE, 1);
      dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    }
    this.addEvent("10/" + (tmpCal.get(Calendar.DATE) + 7) + "/" + theYear, "", "Columbus Day", "holiday");

    //Veteran's Day : November 11 (moved to Monday if Sunday, Friday if Saturday);
    this.addEvent("11/11/" + theYear, "", "Veteran's Day", "holiday");
    tmpCal.set(theYear, Calendar.NOVEMBER, 11);
    dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    if (dayOfWeek == Calendar.SUNDAY) {
      this.addEvent("11/12/" + theYear, "", "Veteran's Day (Bank Holiday)", "holiday");
    } else if (dayOfWeek == Calendar.SATURDAY) {
      this.addEvent("11/10/" + theYear, "", "Veteran's Day (Bank Holiday)", "holiday");
    }

    //Thanksgiving Day : fourth Thursday in November;
    tmpCal.set(theYear, Calendar.NOVEMBER, 1);
    dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    while (dayOfWeek != Calendar.THURSDAY) {
      tmpCal.add(Calendar.DATE, 1);
      dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    }
    this.addEvent("11/" + (tmpCal.get(Calendar.DATE) + 21) + "/" + theYear, "", "Thanksgiving Day", "holiday");

    //Christmas : December 25 (moved to Monday if Sunday or Friday if Saturday);
    this.addEvent("12/25/" + theYear, "", "Christmas Day", "holiday");
    //*
    tmpCal.set(theYear, Calendar.DECEMBER, 25);
    dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    if (dayOfWeek == Calendar.SUNDAY) {
      this.addEvent("12/26/" + theYear, "", "Christmas Day (Bank Holiday)", "holiday");
    } else if (dayOfWeek == Calendar.SATURDAY) {
      this.addEvent("12/24/" + theYear, "", "Christmas Day (Bank Holiday)", "holiday");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  tmpMonth  Description of the Parameter
   *@param  tmpDay    Description of the Parameter
   *@param  tmpYear   Description of the Parameter
   *@return           Description of the Returned Value
   *@since
   */
  public boolean eventExists(String tmpMonth, String tmpDay, String tmpYear) {
    if (eventList.containsKey(tmpMonth + "/" + tmpDay + "/" + tmpYear)) {
      return true;
    } else {
      return false;
    }
  }


  /**
   *  Checks to see if that day is a holiday
   *
   *@param  tmp1  Description of the Parameter
   *@param  tmp2  Description of the Parameter
   *@param  tmp3  Description of the Parameter
   *@return       The holiday value
   */
  public boolean isHoliday(String tmp1, String tmp2, String tmp3) {
    if (eventList.containsKey(tmp1 + "/" + tmp2 + "/" + tmp3)) {
      ArrayList tmpEvents = getEvents(tmp1, tmp2, tmp3);
      for (int i = 0; i < tmpEvents.size(); i++) {
        CalendarEvent tmpEvent = (CalendarEvent) tmpEvents.get(i);
        if ("holiday".equals(tmpEvent.getCategory())) {
          return true;
        }
      }
    }
    return false;
  }


  /**
   *  Description of the Class
   *
   *@author     mrajkowski
   *@created    July 26, 2001
   *@version    $Id: CalendarView.java,v 1.15 2002/04/23 17:58:04 mrajkowski Exp
   *      $
   */
  class ComparatorEvent implements Comparator {
    /**
     *  Compares two events
     *
     *@param  left   Description of Parameter
     *@param  right  Description of Parameter
     *@return        Description of the Returned Value
     *@since
     */
    public int compare(Object left, Object right) {
      if (((CalendarEvent) left).isHoliday() || ((CalendarEvent) right).isHoliday()) {
        String a = ((CalendarEvent) left).isHoliday() ? "A" : "B";
        String b = ((CalendarEvent) right).isHoliday() ? "A" : "B";
        return (a.compareTo(b));
      } else {
        return (
            ((CalendarEvent) left).getCategory().compareTo(((CalendarEvent) right).getCategory()));
      }
    }
  }

}

