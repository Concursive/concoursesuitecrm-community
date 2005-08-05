package org.aspcfs.utils.holidays;

import org.aspcfs.modules.mycfs.base.CalendarEvent;
import org.aspcfs.modules.mycfs.base.CalendarEventList;
import org.aspcfs.utils.web.CalendarView;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * DE Holidays for the CalendarView class
 *
 * @author matt rajkowski
 * @version $Id$
 * @created November 18, 2004
 */
public class DEHolidays {

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
    thisEvent.setSubject("Neujahr");
    calendarView.addEvent(
        "1/1/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);

    //2 days before Easter; Karfreitag
    tmpCal = EasterHoliday.getCalendar(theYear);
    tmpCal.add(Calendar.DATE, -2);
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Karfreitag");
    calendarView.addEvent(
        (tmpCal.get(Calendar.MONTH) + 1) + "/" + tmpCal.get(Calendar.DATE) + "/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);

    //Easter; Ostersonntag
    tmpCal = EasterHoliday.getCalendar(theYear);
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Ostersonntag");
    calendarView.addEvent(
        (tmpCal.get(Calendar.MONTH) + 1) + "/" + tmpCal.get(Calendar.DATE) + "/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);

    //Easter Monday; Monday following Easter; Ostermontag
    tmpCal = EasterHoliday.getCalendar(theYear);
    dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    while (dayOfWeek != Calendar.MONDAY) {
      tmpCal.add(Calendar.DATE, 1);
      dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    }
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Ostermontag");
    calendarView.addEvent(
        (tmpCal.get(Calendar.MONTH) + 1) + "/" + tmpCal.get(Calendar.DATE) + "/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);

    //39 days after Easter; Christi Himmelfahrt
    tmpCal = EasterHoliday.getCalendar(theYear);
    tmpCal.add(Calendar.DATE, 39);
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Christi Himmelfahrt");
    calendarView.addEvent(
        (tmpCal.get(Calendar.MONTH) + 1) + "/" + tmpCal.get(Calendar.DATE) + "/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);

    //49 days after Easter; Pfingstsonntag
    tmpCal = EasterHoliday.getCalendar(theYear);
    tmpCal.add(Calendar.DATE, 49);
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Pfingstsonntag");
    calendarView.addEvent(
        (tmpCal.get(Calendar.MONTH) + 1) + "/" + tmpCal.get(Calendar.DATE) + "/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);

    //50 days after Easter; Pfingstmontag
    tmpCal = EasterHoliday.getCalendar(theYear);
    tmpCal.add(Calendar.DATE, 50);
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Pfingstmontag");
    calendarView.addEvent(
        (tmpCal.get(Calendar.MONTH) + 1) + "/" + tmpCal.get(Calendar.DATE) + "/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);

    //May 1; Maifeiertag
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Maifeiertag");
    calendarView.addEvent(
        "5/1/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);

    //October 3; Day of German Unity; Tag der Deutschen Einheit
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Tag der Deutschen Einheit");
    calendarView.addEvent(
        "10/3/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);

    //October 31; Reformation Day; Reformationstag
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Reformationstag");
    calendarView.addEvent(
        "10/31/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);

    //3rd Wednesday in November; Repentance Day;
    tmpCal.set(theYear, Calendar.NOVEMBER, 1);
    dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    while (dayOfWeek != Calendar.WEDNESDAY) {
      tmpCal.add(Calendar.DATE, 1);
      dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
    }
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("Bu\u00df- und Bettag");
    calendarView.addEvent(
        "11/" + (tmpCal.get(Calendar.DATE) + 14) + "/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);

    //December 25; 1. Weihnachtsfeiertag
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("1. Weihnachtsfeiertag");
    calendarView.addEvent(
        "12/25/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);

    //December 26; 2. Weihnachtsfeiertag
    thisEvent = new CalendarEvent();
    thisEvent.setSubject("2. Weihnachtsfeiertag");
    calendarView.addEvent(
        "12/26/" + theYear, CalendarEventList.EVENT_TYPES[7], thisEvent);
  }
}

