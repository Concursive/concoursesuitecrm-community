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

package org.jcrontab;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;
import org.jcrontab.data.CalendarBuilder;
import org.jcrontab.data.CrontabEntryBean;
import org.jcrontab.data.CrontabEntryDAO;
import org.jcrontab.data.DataNotFoundException;
import org.jcrontab.log.Log;

/**
 *  This class represents the Thread that loads the information from the DAO's
 *  and maintains the list of events to execute by the Crontab.
 *
 *@author     $Author$
 *@created    February 4, 2003
 *@version    $Revision$
 */

public class Cron extends Thread {

  private static boolean shouldRun = true;

  private final static String GENERATE_TIMETABLE_EVENT = "gen_timetable";

  private Crontab crontab;

  private int iFrec;

  private static int minute = 60000;

  public static Properties prop = null;

  private static CrontabBean[] eventsQueue;

  private static CrontabEntryBean[] crontabEntryArray = null;

  private static CalendarBuilder calb = null;

  /**
   *  Used to lookup the time this class was loaded in the System object. Value
   *  is the fully qualified classname + ".load-time"
   *
   *@see    #myClassLoadTime
   */
  private final static String LOAD_TIME_PROPERTY = Cron.class.getName() + ".load-time";

  /**
   *  This is a mechanism to avoid multiple instances of this _class_ being
   *  loaded at the same time. In some environments, like Weblogic for instance,
   *  an application can be reloaded at run time. By remembering the value when
   *  the class is instantiated and comparing it to the current value from
   *  System.getProperty({@link #LOAD_TIME_PROPERTY}) we can determine whether
   *  the class has been reloaded. The {@link #run}() method needs to check
   *  {@link #isClassReloaded} to see whether it should continue processing.
   *
   *@see    #LOAD_TIME_PROPERTY
   *@see    #isClassReloaded
   */
  private long myClassLoadTime;

  static {
    System.setProperty(LOAD_TIME_PROPERTY, String.valueOf(System.currentTimeMillis()));
  }


  /**
   *  Constructor of a Cron. This one doesn't receive any parameters to make it
   *  easier to build an instance of Cron
   */
  public Cron() {
    // Remember the time the class was loaded.
    this.myClassLoadTime = Long.parseLong(System.getProperty(LOAD_TIME_PROPERTY));
    crontab = Crontab.getInstance();
    iFrec = 3600;
    calb = new CalendarBuilder();
  }


  /**
   *  Constructor of a Cron
   *
   *@param  cront                     Crontab The Crontab that the cron must
   *      call to generate new tasks
   *@param  iTimeTableGenerationFrec  int Frecuency of generation of new time
   *      table entries.
   */
  public Cron(Crontab cront, int iTimeTableGenerationFrec) {
    // Remember the time the class was loaded.
    this.myClassLoadTime = Long.parseLong(System.getProperty(LOAD_TIME_PROPERTY));
    crontab = cront;
    iFrec = iTimeTableGenerationFrec * 60;
  }


  /**
   *  Checks whether this class has been reloaded since this instance was
   *  instantiated.
   *
   *@return    true if the class has been reloaded, false if all is okay
   */
  private boolean isClassReloaded() {
    if (this.myClassLoadTime != Long.parseLong(System.getProperty(LOAD_TIME_PROPERTY))) {
      Log.info("This class has been reloaded, so I am a runaway daemon. Canceling.");
      return true;
    } else {
      return false;
    }
  }


  /**
   *  Runs the Cron Thread. This method is the method called by the crontab
   *  class. this method is inherited from Thread Class
   */
  public void run() {
    // this counter is used to save array`s position
    int counter = 0;
    try {
      // Waits until the next minute to begin
      // waitNextMinute();
      // Generates events list
      generateEvents();
    } catch (Exception e) {
      Log.error(e.toString(), e);
    }
    // Infinite loop, this thread will stop when the jvm is stopped
    // shouldRun tells the system if should stop at some moment.
    while (shouldRun) {
      // The event...
      CrontabBean nextEv = eventsQueue[counter];
      long intervalToSleep = nextEv.getTime() - System.currentTimeMillis();
      if (intervalToSleep > 0) {
        // Waits until the next event
        try {
          synchronized (this) {
            Log.debug("Interval to sleep : " + intervalToSleep);
            wait(intervalToSleep);
          }
        } catch (InterruptedException e) {
          // Waits until the next minute to begin
          // waitNextMinute();
          // Generates events list
          generateEvents();
          // Continues loop
          continue;
        }
      }
      // it's incremented here to mantain array reference.
      counter++;
      // If it is a generate time table event, does it.
      if (nextEv.getClassName().equals(GENERATE_TIMETABLE_EVENT)) {
        // Generates events list
        generateEvents();
        // reinitialized the array
        counter = 0;
      } else {
        // Else, then tell the crontab to create the new task
        crontab.newTask(nextEv.getClassName(), nextEv.getMethodName(),
            nextEv.getExtraInfo(), nextEv.getConnectionContext());
      }
    }
  }


  /**
   *  This method waits until the next minute to synxhonize the Cron activity
   *  eith the system clock
   *
   *@deprecated
   */
  private void waitNextMinute() {
    // Waits until the next minute
    long tmp = System.currentTimeMillis();
    long intervalToSleep;
    // If modulus different to 0 then should wait the interval
    // if(tmp % minute != 0) {
    // long intervalToSleep = ((((long)(tmp / minute))+1) * minute) - tmp;
    // Waits until the next minute
    if (crontabEntryArray != null) {
      CrontabEntryBean nextCeb = calb.getNextCrontabEntry(crontabEntryArray);
      intervalToSleep = calb.buildCalendar(nextCeb).getTime();
    } else {
      intervalToSleep = ((((long) (tmp / minute)) + 1) * minute) - tmp;
    }
    try {
      synchronized (this) {
        Log.debug("this is the interval to sleep : " + intervalToSleep);
        wait(intervalToSleep);
      }
    } catch (InterruptedException e) {
      // Waits again (recursivity?)
      waitNextMinute();
    }
    //}
  }


  /**
   *  Tell The system that should stop
   *
   *@throws  Exception
   */
  public static void stopInTheNextMinute() {
    shouldRun = false;
  }


  /**
   *  Loads the CrontabEntryBeans from the DAO
   *
   *@return             CrontabEntryBean[] the resultant array of
   *      CrontabEntryBean
   *@throws  Exception
   */
  private static CrontabEntryBean[] readCrontab(Object cp) throws Exception {
    crontabEntryArray = CrontabEntryDAO.getInstance().findAll(cp);
    return crontabEntryArray;
  }


  /**
   *  Generates new time table entries (for new events). IN fact this method
   *  does more or less everything, this method tells the DAO to look for
   *  CrontabEntryArray, generates the CrontabBeans and puts itself as the last
   *  event to generate again the list of events. Nice Method. :-)
   */
  public void generateEvents() {
    // This loads the info from the DAO
    try {
      crontabEntryArray = null;
      crontabEntryArray = readCrontab(crontab.getConnectionPool());
      // This Vector is created cause don't know how big is the list
      // of events
      Vector lista1 = new Vector();
      // Rounds the calendar to the previous minute
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date((System.currentTimeMillis())));
      for (int i = 0; i < iFrec; i++) {
        for (int j = 0; j < crontabEntryArray.length; j++) {
          if (crontabEntryArray[j].equals(cal) && shouldRunToday(crontabEntryArray[j].getBusinessDays())) {
            CrontabBean ev = new CrontabBean();
            ev.setId(j);
            ev.setCalendar(cal);
            ev.setTime(cal.getTime().getTime());
            ev.setClassName(crontabEntryArray[j].getClassName());
            ev.setMethodName(crontabEntryArray[j].getMethodName());
            ev.setExtraInfo(crontabEntryArray[j].getExtraInfo());
            ev.setConnectionContext(crontabEntryArray[j].getConnectionContext());
            lista1.add(ev);
          }
        }
        cal.add(Calendar.SECOND, 1);
      }
      // The last event is the new generation of the event list
      CrontabBean ev = new CrontabBean();
      ev.setCalendar(cal);
      ev.setTime(cal.getTime().getTime());
      ev.setClassName(GENERATE_TIMETABLE_EVENT);
      ev.setMethodName("");
      lista1.add(ev);
      eventsQueue = new CrontabBean[lista1.size()];
      for (int i = 0; i < lista1.size(); i++) {
        eventsQueue[i] = (CrontabBean) lista1.get(i);
      }
    } catch (Exception e) {
      // Rounds the calendar to this minute
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date(((long)
          (System.currentTimeMillis() / 60000))
           * 60000));
      // Adds to the calendar the iFrec Minutes
      cal.add(Calendar.MINUTE, iFrec);
      CrontabBean ev = new CrontabBean();
      ev.setCalendar(cal);
      ev.setTime(cal.getTime().getTime());
      ev.setClassName(GENERATE_TIMETABLE_EVENT);
      ev.setMethodName("");
      // Sets the GENERATE_TIMETABLE_EVENT as the
      // last event
      eventsQueue = new CrontabBean[1];
      eventsQueue[0] = ev;
      if (e instanceof DataNotFoundException) {
        Log.info(e.toString());
      } else {
        Log.error(e.toString(), e);
      }
    }
  }


  /**
   *  This method says if this CrontabEntryBean should run or not
   *
   *@param  should      Description of the Parameter
   *@return             Description of the Return Value
   *@throws  Exception
   */
  private boolean shouldRunToday(boolean should) throws Exception {
    if (!Crontab.getInstance().isHoliday()) {
      return true;
    } else if (should) {
      return true;
    } else {
      return false;
    }
  }
}

