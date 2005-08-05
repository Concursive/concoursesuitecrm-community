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

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Vector;

/**
 * This HoliDaySource builds a basic holidays information source.
 *
 * @author iolalla
 * @version $Revision$
 * @created February 4, 2003
 */
public class HoliDayFileSource implements HoliDaySource {
  private HoliDay[] hol = null;


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws Exception Description of the Exception
   */
  public HoliDay[] findAll() throws Exception {

    if (hol != null) {
      return hol;
    }

    String filename = Crontab.getInstance().getProperty(
        "org.jcrontab.data.holidaysfilesource");
    String dateFormat = Crontab.getInstance().getProperty(
        "org.jcrontab.data.dateFormat");

    Vector listOfLines = new Vector();

    if (filename == null || filename == "") {
      throw new FileNotFoundException(
          "Should provide a valid file" +
          "name plz set correctly org.jcrontab.data.holidaysfilesource");
    }

    if (dateFormat == null || dateFormat == "") {
      dateFormat = "dd/MM/yyyy";
    }

    InputStream fis = new FileInputStream(filename);

    BufferedReader input = new BufferedReader(new InputStreamReader(fis));

    SimpleDateFormat formater = new SimpleDateFormat(dateFormat);

    String strLine;

    while ((strLine = input.readLine()) != null) {
      //System.out.println(strLine);
      strLine = strLine.trim();
      listOfLines.add(strLine);
    }
    fis.close();

    hol = new HoliDay[listOfLines.size()];
    for (int i = 0; i < hol.length; i++) {
      HoliDay holiday = new HoliDay();
      holiday.setId(i);
      holiday.setDate(formater.parse((String) listOfLines.get(i)));
      hol[i] = holiday;
    }
    return hol;
  }
}

