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

/**
 *  This Bean reresents the basis to build BussnesDays logic, basically
 *  represents the Holiday
 *
 *@author     iolalla
 *@created    February 4, 2003
 *@version    $Revision$
 */
public class HoliDay {

  /**
   *  This id is the primay key of this Bean
   */
  private int id;
  /**
   *  This string stores the info necesary to get the right date
   */
  private Date date;


  /**
   *  This id setter
   *
   *@param  id  int the id of this bean
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   *  This id getter
   *
   *@return    id int the id of this bean
   */
  public int getId() {
    return this.id;
  }


  /**
   *  This date setter
   *
   *@param  date    The new date value
   */
  public void setDate(Date date) {
    this.date = date;
  }


  /**
   *  date Getter
   *
   *@return    String the date of this holiday
   */
  public Date getDate() {
    return this.date;
  }


  /**
   *  Represents the HoliDay in ASCII format
   *
   *@return    the returning string
   */
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("[id: " + id + " ]\t ");
    sb.append("[date: " + date + " ]\n");
    return sb.toString();
  }


  /**
   *@param  obj  Object to compare with the Holidays Bean
   *@return      true if the time table entry matchs with the Object given false
   *      otherwise
   */

  public boolean equals(Object obj) {
    HoliDay holiday = null;
    if (!(obj instanceof HoliDay)) {
      return false;
    } else {
      holiday = (HoliDay) obj;
    }
    if (id != holiday.getId()) {
      return false;
    }
    if (date != null && !date.equals(holiday.getDate())) {
      return false;
    }
    return true;
  }


  /**
   *  Helps to do the castings in a more simple way.
   *
   *@param  obj  Object to cast to CrontabEntryBean
   *@return      The resulting array of CrontabEntryBean
   */
  public static HoliDay[] toArray(Object[] obj) {
    HoliDay[] holiday = new HoliDay[obj.length];
    for (int i = 0; i < obj.length; i++) {
      holiday[i] = (HoliDay) obj[i];
    }
    return holiday;
  }

}

