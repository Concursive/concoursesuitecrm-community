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

import org.jcrontab.Crontab;

/**
 * This Factory builds a HoliDay Source using teh given information.
 *
 * @author iolalla
 * @version $Revision$
 * @created February 4, 2003
 */

public class HoliDayFactory {

  private static HoliDaySource hds = null;


  /**
   * Default Constructor private
   */
  private HoliDayFactory() {
  }


  /**
   * This method returns the DataFactory of the System This method grants the
   * Singleton pattern
   *
   * @return DataSource I have a lot of doubts about how this
   *         method is done.
   * @throws Exception Description of the Exception
   */
  public static HoliDaySource getInstance() throws Exception {
    if (hds == null) {
      hds = ((HoliDaySource) Class.forName(
          Crontab.getInstance()
          .getProperty("org.jcrontab.data.holidaysource"))
          .newInstance());
    }
    return hds;
  }


  /**
   * This method returns the HoliDaySource of the System
   *
   * @return HoliDaySource I have a lot of doubts about how this method is
   *         done.
   */

  public HoliDaySource getDAO() {
    return hds;
  }
}

