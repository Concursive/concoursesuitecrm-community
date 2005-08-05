package org.aspcfs.utils.holidays;

import org.aspcfs.modules.mycfs.base.CalendarEvent;
import org.aspcfs.modules.mycfs.base.CalendarEventList;
import org.aspcfs.utils.web.CalendarView;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * US Holidays for the CalendarView class
 *
 * @author matt rajkowski
 * @version $Id$
 * @created November 17, 2004
 */
public class CAHolidays {

  /**
   * Adds a feature to the To attribute of the USHolidays class
   *
   * @param calendarView The feature to be added to the To attribute
   * @param theYear      The feature to be added to the To attribute
   */
  public final static void addTo(CalendarView calendarView, int theYear) {
    // NOTE: Remember that java month is 0-based and CalendarEvent is 1-based
    Calendar tmpCal = new GregorianCalendar();
    CalendarEvent thisEvent = null;
    int dayOfWeek = -1;

    //New Year's Day; Jan. 1
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("New Year's Day");
    calendarView.addEvent(
        "1/1/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);
    
    //Good Friday; Friday before Easter
    tmpCal = EasterHoliday.getCalendar(theYear);
    dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    while (dayOfWeek != Calendar.FRIDAY) {
      tmpCal.add(Calendar.DATE, -1);
      dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    }
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Good Friday");
    calendarView.addEvent(
        (tmpCal.get(Calendar.MONTH) + 1) + "/" + tmpCal.get(Calendar.DATE) + "/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);
    
    //Easter Monday; Monday following Easter
    tmpCal = EasterHoliday.getCalendar(theYear);
    dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    while (dayOfWeek != Calendar.MONDAY) {
      tmpCal.add(Calendar.DATE, 1);
      dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    }
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Easter Monday");
    calendarView.addEvent(
        (tmpCal.get(Calendar.MONTH) + 1) + "/" + tmpCal.get(Calendar.DATE) + "/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);
    
    //Victoria Day; Monday preceding May 25
    tmpCal.set(theYear, Calendar.MAY, 25);
    dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    // If the 25th is a Monday, then we need the one before
    if (tmpCal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
      tmpCal.add(Calendar.DATE, -7);
    }
    while (dayOfWeek != Calendar.MONDAY) {
      tmpCal.add(Calendar.DATE, -1);
      dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    }
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Victoria Day");
    calendarView.addEvent(
        "5/" + tmpCal.get(Calendar.DATE) + "/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);
    
    //Canada Day; July 1 (July 2 when July 1 is a Sunday)
    tmpCal.set(theYear, Calendar.JULY, 1);
    dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    if (tmpCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
      // Set to July 2
      tmpCal.add(Calendar.DATE, 1);
    }
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Canada Day");
    calendarView.addEvent(
        "7/" + tmpCal.get(Calendar.DATE) + "/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);

    //Labour Day : first Monday in September;
    tmpCal.set(theYear, Calendar.SEPTEMBER, 1);
    dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    while (dayOfWeek != Calendar.MONDAY) {
      tmpCal.add(Calendar.DATE, 1);
      dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    }
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Labour Day");
    calendarView.addEvent(
        "9/" + tmpCal.get(Calendar.DATE) + "/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);

    //Thanksgiving Day : (second Monday of October)
    tmpCal.set(theYear, Calendar.OCTOBER, 1);
    dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    while (dayOfWeek != Calendar.MONDAY) {
      tmpCal.add(Calendar.DATE, 1);
      dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    }
    tmpCal.add(Calendar.DATE, 7);
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Thanksgiving Day");
    calendarView.addEvent(
        "10/" + tmpCal.get(Calendar.DATE) + "/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);

    //Remembrance Day : November 11
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Remembrance Day");
    calendarView.addEvent(
        "11/11/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);
    
    //Christmas : December 25;
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Christmas");
    calendarView.addEvent(
        "12/25/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);
    
    //TODO: Boxing Day : December 26 (moved to Monday if Sunday or Friday if Saturday);
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Boxing Day");
    calendarView.addEvent(
        "12/26/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);
  }
}

