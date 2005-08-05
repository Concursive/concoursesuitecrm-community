package org.aspcfs.utils.holidays;

import org.aspcfs.modules.mycfs.base.CalendarEvent;
import org.aspcfs.modules.mycfs.base.CalendarEventList;
import org.aspcfs.utils.web.CalendarView;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * UK Holidays for the CalendarView class
 *
 * @author matt rajkowski
 * @version $Id$
 * @created April 6, 2005
 */
public class UKHolidays {

  /**
   * Adds a feature to the To attribute of the USHolidays class
   *
   * @param calendarView The feature to be added to the To attribute
   * @param theYear      The feature to be added to the To attribute
   */
  public final static void addTo(CalendarView calendarView, int theYear) {

    Calendar tmpCal = new GregorianCalendar();
    CalendarEvent thisEvent = null;
    int dayOfWeek = -1;

    //New Year's Day
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("New Year's Day");
    calendarView.addEvent(
        "1/1/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);
    //TODO: Bank Holiday adjustment?


    //2 days before Easter; Good Friday
    tmpCal = EasterHoliday.getCalendar(theYear);
    tmpCal.add(Calendar.DATE, -2);
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Good Friday");
    calendarView.addEvent(
        (tmpCal.get(Calendar.MONTH) + 1) + "/" + tmpCal.get(Calendar.DATE) + "/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);

    //Easter Sunday
    tmpCal = EasterHoliday.getCalendar(theYear);
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Easter");
    calendarView.addEvent(
        (tmpCal.get(Calendar.MONTH) + 1) + "/" + tmpCal.get(Calendar.DATE) + "/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);

    //Easter Monday
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

    //Early May Bank Holiday: first Monday in May
    tmpCal.set(theYear, Calendar.MAY, 1);
    dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    while (dayOfWeek != Calendar.MONDAY) {
      tmpCal.add(Calendar.DATE, 1);
      dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    }
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Early May Bank Holiday");
    calendarView.addEvent(
        "5/" + (tmpCal.get(Calendar.DATE)) + "/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);

    //Spring Bank Holiday: last Monday in May;
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
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Spring Bank Holiday");
    calendarView.addEvent(
        "5/" + (tmpCal.get(Calendar.DATE)) + "/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);

    //Summer Bank Holiday: last Monday in August;
    tmpCal.set(theYear, Calendar.AUGUST, 1);
    dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    while (dayOfWeek != Calendar.MONDAY) {
      tmpCal.add(Calendar.DATE, 1);
      dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    }
    //With the first Monday, see if August has 4 or 5 Mondays
    tmpCal.add(Calendar.DATE, 28);
    if (tmpCal.get(Calendar.MONTH) != Calendar.AUGUST) {
      tmpCal.add(Calendar.DATE, -7);
    }
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Summer Bank Holiday");
    calendarView.addEvent(
        "8/" + (tmpCal.get(Calendar.DATE)) + "/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);


    //Christmas : December 25
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Christmas Day");
    calendarView.addEvent(
        "12/25/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);
    //TODO: Bank Holiday adjustment?
    /*
    thisEvent = new CalendarEvent();
    tmpCal.set(theYear, Calendar.DECEMBER, 25);
    dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    if (dayOfWeek == Calendar.SUNDAY) {
      thisEvent.setSubject("Christmas Day (Bank Holiday)");
      calendarView.addEvent("12/26/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);
    } else if (dayOfWeek == Calendar.SATURDAY) {
      thisEvent.setSubject("Christmas Day (Bank Holiday)");
      calendarView.addEvent("12/24/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);
    }
    */

    //TODO: Bank Holiday adjustment?
    //Boxing Day : December 26 Adjustment?
    /*
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Boxing Day");
    calendarView.addEvent("12/26/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);


    dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    if (dayOfWeek == Calendar.SUNDAY) {
      thisEvent.setSubject("Boxing Day");
      calendarView.addEvent("12/27/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);
    } else if (dayOfWeek == Calendar.SATURDAY) {
      thisEvent.setSubject("Boxing Day");
      calendarView.addEvent("12/25/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);
    }
    */
  }
}

