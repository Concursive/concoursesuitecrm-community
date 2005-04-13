package org.aspcfs.utils.holidays;

import java.util.*;

/**
 *  Calculates and returns Easter Day for specified year.
 *
 *@author     Originally from Mark Lussier, AppVision <MLussier@best.com>
 *@created    November 17, 2004
 *@version    $Id$
 */
public class EasterHoliday {

  /**
   *  Gets the calendar attribute of the EasterHoliday class
   *
   *@param  year  Description of the Parameter
   *@return       The calendar value
   */
  public final static GregorianCalendar getCalendar(int year) {
    int nMonth;
    int nDay;
    int nMoon;
    int nEpact;
    int nSunday;
    int nGold;
    int nCent;
    int nCorx;
    int nCorz;
    // The Golden Number of the year in the 19 year Metonic Cycle
    nGold = (year % 19) + 1;
    // Calculate the Century: }
    nCent = (year / 100) + 1;
    // Number of years in which leap year was dropped in order...
    // to keep in step with the sun: }
    nCorx = (3 * nCent) / 4 - 12;
    // Special correction to syncronize Easter with moon's orbit
    nCorz = (8 * nCent + 5) / 25 - 5;
    // Find Sunday
    nSunday = (5 * year) / 4 - nCorx - 10;
    // Set Epact - specifies occurrence of full moon
    nEpact = (11 * nGold + 20 + nCorz - nCorx) % 30;
    if (nEpact < 0) {
      nEpact = nEpact + 30;
    }
    if (((nEpact == 25) && (nGold > 11)) || (nEpact == 24)) {
      nEpact = nEpact + 1;
    }
    // Find Full Moon
    nMoon = 44 - nEpact;
    if (nMoon < 21) {
      nMoon = nMoon + 30;
    }
    // Advance to Sunday
    nMoon = nMoon + 7 - ((nSunday + nMoon) % 7);
    if (nMoon > 31) {
      nMonth = 4;
      nDay = nMoon - 31;
    } else {
      nMonth = 3;
      nDay = nMoon;
    }
    if (System.getProperty("DEBUG") != null) {
      System.out.println("EasterHoliday-> Date: " + nMonth + "/" + nDay + "/" + year);
    }
    return new GregorianCalendar(year, nMonth - 1, nDay);
  }
}

