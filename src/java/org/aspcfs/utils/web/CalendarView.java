package com.darkhorseventures.utils;

import com.darkhorseventures.cfsbase.CalendarEvent;
import com.darkhorseventures.cfsbase.CalendarEventList;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;

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

  protected String borderSize = "";
  protected String cellPadding = "";
  protected String cellSpacing = "";
  protected int numberOfCells = 42;

  //Events that can be displayed on the calendar
  protected Hashtable eventList = new Hashtable();
  protected boolean sortEvents = false;
  public final static int[] DAYSINMONTH = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};


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
   *  Sets the BorderSize attribute of the CalendarView object
   *
   *@param  tmp  The new BorderSize value
   *@since
   */
  public void setBorderSize(int tmp) {
    this.borderSize = " border='" + tmp + "'";
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
  /*
   *  public String getEvents() {
   *  StringBuffer tmp = new StringBuffer();
   *  for (Enumeration e = eventList.elements() ; e.hasMoreElements() ;) {
   *  tmp.append(e.nextElement().toString());
   *  }
   *  return tmp.toString();
   *  }
   */
  public Vector getEvents(String tmp1, String tmp2, String tmp3) {
    String key = tmp1 + "/" + tmp2 + "/" + tmp3;
    if (eventList.containsKey(key)) {
      return (Vector)eventList.get(key);
    } else {
      return new Vector();
    }
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
         && ((GregorianCalendar)tmp).isLeapYear(tmp.get(Calendar.YEAR))) {
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
    return "" + cal.get(Calendar.DAY_OF_MONTH);
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
      return (CalendarEvent)((Vector)eventList.get(key)).get(0);
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
   *  Gets the DaysEvents attribute of the CalendarView object
   *
   *@param  m  Description of Parameter
   *@param  d  Description of Parameter
   *@param  y  Description of Parameter
   *@return    The DaysEvents value
   *@since
   */
  public Vector getDaysEvents(int m, int d, int y) {
    int displayMonth = m + 1;
    int displayYear = y;
    int displayDay = d;

    //Get this day's events
    Vector tmpEvents = getEvents("" + displayMonth, "" + displayDay, "" + displayYear);

    //Sort the events
    if (sortEvents && tmpEvents.size() > 1) {
      Object sortArray[] = tmpEvents.toArray();
      Comparator comparator = null;
      comparator = new ComparatorEvent();
      Arrays.sort(sortArray, comparator);
      tmpEvents.clear();
      for (int i = 0; i < sortArray.length; i++) {
        tmpEvents.addElement((CalendarEvent)sortArray[i]);
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
    html.append("<table width='98%' align='center' cellspacing='0' cellpadding='0' border='0' bgcolor='#ffffff'>");
    html.append("<tr><td>");

    //Space at top to match CFS
    if (headerSpace) {
      html.append("<table width=100% align=center cellspacing=0 cellpadding=0 border=0>");
      html.append("<tr><td>&nbsp;</td></tr>");
      html.append("</table>");
    }

    String monthArrowPrev = "";
    String monthArrowNext = "";
    if (monthArrows) {
      monthArrowPrev = "<INPUT TYPE=\"IMAGE\" NAME=\"prev\" ALIGN=\"MIDDLE\" SRC=\"/images/prev.gif\">";
      monthArrowNext = "<INPUT TYPE=\"IMAGE\" NAME=\"next\" ALIGN=\"MIDDLE\" SRC=\"/images/next.gif\">";
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
    html.append("<center><table width='" + tableWidth + "'" + borderSize + cellSpacing + cellPadding + " class='" + pre + "calendar' bordercolorlight='#000000' bordercolor='#FFFFFF'>");
    html.append("<tr>");

    //Display Previous Month Arrow
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
    html.append("</tr>");

    //Display the Days of the Week names
    html.append("<tr>");
    for (int i = 1; i < 8; i++) {
      html.append("<td width='14%' class='" + pre + "weekName'>");
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

    for (int cellNo = 0, day = 1; cellNo < this.getNumberOfCells(); cellNo++) {
      if (cellNo % 7 == 0) {
        html.append("<tr>");
      }
      // end check for start of row

      html.append("<td valign='top'");
      if (!smallView) {
        if (!frontPageView) {
          html.append("height='70'");
        } else {
          html.append("height='45'");
        }
      }

      boolean mainMonth = false;
      int displayDay = 0;
      int displayMonth = 0;
      int displayYear = 0;
      if (cellNo < startCell) {
        //The previous month
        displayMonth = calPrev.get(Calendar.MONTH) + 1;
        displayYear = calPrev.get(Calendar.YEAR);
        displayDay = (endCellPrev - startCell + 2 + cellNo - startCellPrev);
        if (this.isCurrentDay(calPrev, displayDay)) {
          html.append(" class='" + pre + "today'>");
        } else {
          html.append(" class='" + pre + "noday'>");
        }
      } else if (cellNo > endCell) {
        //The next month
        displayMonth = calNext.get(Calendar.MONTH) + 1;
        displayYear = calNext.get(Calendar.YEAR);
        if (endCell + 1 == cellNo) {
          day = 1;
        }
        displayDay = day;
        if (this.isCurrentDay(calNext, displayDay)) {
          html.append(" class='" + pre + "today'>");
        } else {
          html.append(" class='" + pre + "noday'>");
        }
        day++;
      } else {
        //The main main
        mainMonth = true;
        displayMonth = cal.get(Calendar.MONTH) + 1;
        displayYear = cal.get(Calendar.YEAR);
        displayDay = day;
        if (this.isCurrentDay(cal, displayDay)) {
          html.append(" class='" + pre + "today'>");
        } else {
          html.append(" class='" + pre + "day'>");
        }
        day++;
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
        html.append("<a href=\"javascript:openWindow('" + displayDay + "');\">" + dateColor + "</a>");

        //Get this day's events
        Vector tmpEvents = getEvents("" + displayMonth, "" + displayDay, "" + displayYear);

        //Sort the events
        if (sortEvents && tmpEvents.size() > 1) {
          Object sortArray[] = tmpEvents.toArray();
          Comparator comparator = null;
          comparator = new ComparatorEvent();
          Arrays.sort(sortArray, comparator);
          tmpEvents.clear();
          for (int i = 0; i < sortArray.length; i++) {
            tmpEvents.addElement((CalendarEvent)sortArray[i]);
          }
        }

        //Enumerate the events and display them
        for (int i = 0; i < tmpEvents.size(); i++) {
          CalendarEvent tmpEvent = (CalendarEvent)tmpEvents.get(i);
          if ((tmpEvent != null) && (!tmpEvent.getCategory().equals("blank"))) {
            if (tmpEvent.getCategory().equals("holiday")) {
              html.append("<br>" + tmpEvent.getIcon() + "<font color=blue> ");
              if (this.getShowSubject() == true) {
                html.append(tmpEvent.getSubject() + "</font>");
              }
              ;
            } else {
              if (i == 0) {
                html.append("<br>" + tmpEvent.getIcon() + " ");
                if (this.getShowSubject() == true) {
                  html.append(tmpEvent.getSubject());
                }
                ;
              } else {
                html.append(tmpEvent.getIcon() + " ");
                if (this.getShowSubject() == true) {
                  html.append(tmpEvent.getSubject());
                }
                ;
              }
            }
          }
        }
        tmpEvents = null;
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
      ;
      html.append("<p class='smallfooter'>Today is: " + "<a href=\"javascript:returnDate(" + displayDay + ", " + displayMonth + ", " + displayYear + ");\"" + ">" + this.getToday() + "</p>");
    }

    html.append("<input type=\"hidden\" name=\"year\" value=\"" + cal.get(Calendar.YEAR) + "\">");
    html.append("<input type=\"hidden\" name=\"month\" value=\"" + (cal.get(Calendar.MONTH) + 1) + "\">");

    return html.toString();
  }


  /**
   *  Returns a vector of CalendarEventLists which contain CalendarEvents,
   *  including all of today's events.<p>
   *
   *  A full day is always returned, if the events do not add up to (max) then
   *  the next days is included. Scans up to 31 days.
   *
   *@param  max  Description of Parameter
   *@return      The Events value
   *@since
   */
  public Vector getEvents(int max) {
    Vector allDays = new Vector();
    Vector thisDay = null;
    String val = "";
    int count = 0;
    int loopCount = 0;
    StringBuffer html = new StringBuffer();

    Calendar tmpCal = new GregorianCalendar();
    Date now = new Date();
    tmpCal.setTime(now);

    while (count < max && loopCount < 31) {
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
          CalendarEvent thisEvent = (CalendarEvent)i.next();
          thisEventList.add(thisEvent);
          if (System.getProperty("DEBUG") != null) {
            System.out.println("CalendarView-> Event added");
          }
          count++;
        }
      }
      tmpCal.add(java.util.Calendar.DATE, +1);
      loopCount++;
    }

    if (System.getProperty("DEBUG") != null) {
      System.out.println("CalendarView-> Returning " + allDays.size());
    }

    return allDays;
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
   *  Adds an event to the calendar
   *
   *@param  eventDate  The feature to be added to the Event attribute
   *@param  eventTime  The feature to be added to the Event attribute
   *@param  subject    The feature to be added to the Event attribute
   *@param  category   The feature to be added to the Event attribute
   *@since
   */
  public void addEvent(String eventDate, String eventTime, String subject, String category) {
    //Create a calendar event object
    CalendarEvent tmp = new CalendarEvent();
    StringTokenizer st = new StringTokenizer(eventDate, "/");
    if (st.hasMoreTokens()) {
      tmp.setMonth(st.nextToken());
      tmp.setDay(st.nextToken());
      tmp.setYear(st.nextToken());
    }
    tmp.setTime(eventTime);
    tmp.setSubject(subject);
    tmp.setCategory(category);

    //Check to see if the eventList already has dailyEvents for the eventDate
    Vector dailyEvents = null;
    if (eventList.containsKey(eventDate)) {
      dailyEvents = (Vector)eventList.get(eventDate);
    } else {
      dailyEvents = new Vector();
    }

    //Add the event to the list
    dailyEvents.addElement(tmp);

    //Add the events to the eventList
    this.eventList.put(eventDate, dailyEvents);
  }


  //Backwards compatible for month.jsp
  /**
   *  Adds a feature to the Event attribute of the CalendarView object
   *
   *@param  eventDate  The feature to be added to the Event attribute
   *@param  subject    The feature to be added to the Event attribute
   *@param  category   The feature to be added to the Event attribute
   *@since
   */
  public void addEvent(String eventDate, String subject, String category) {
    this.addEvent(eventDate, "", subject, category);
  }


  /**
   *  Adds a feature to the Holidays attribute of the CalendarView object
   *
   *@since
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

    //Washington's birthday : third Monday in February;
    tmpCal.set(theYear, Calendar.FEBRUARY, 1);
    dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    while (dayOfWeek != Calendar.MONDAY) {
      tmpCal.add(Calendar.DATE, 1);
      dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    }
    this.addEvent("2/" + (tmpCal.get(Calendar.DATE) + 14) + "/" + theYear, "", "Washington's Birthday", "holiday");

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


  //Checks the Event Hashtable to see if an event exists
  /**
   *  Description of the Method
   *
   *@param  tmp1  Description of Parameter
   *@param  tmp2  Description of Parameter
   *@param  tmp3  Description of Parameter
   *@return       Description of the Returned Value
   *@since
   */
  public boolean eventExists(String tmp1, String tmp2, String tmp3) {
    if (eventList.containsKey(tmp1 + "/" + tmp2 + "/" + tmp3)) {
      return true;
    } else {
      return false;
    }
  }


  /**
   *  Description of the Class
   *
   *@author     mrajkowski
   *@created    July 26, 2001
   *@version    $Id$
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
      if (((CalendarEvent)left).isHoliday() || ((CalendarEvent)right).isHoliday()) {
        String a = ((CalendarEvent)left).isHoliday()?"A":"B";
        String b = ((CalendarEvent)right).isHoliday()?"A":"B";
        return (a.compareTo(b));
      } else {
      return (
        ((CalendarEvent)left).getDateTimeString().compareTo(((CalendarEvent)right).getDateTimeString()));
      }
    }
  }

}

