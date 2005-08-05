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

import java.util.StringTokenizer;

/**
 * This class parses a Line and returns CrontabEntryBean. This class + Is done
 * to do more modular and eficient
 *
 * @author iolalla
 * @version $Revision$
 * @created February 4, 2003
 */

public class CrontabParser {

  /**
   * Parses a string describing this time table entry and sets the neded
   * variables in order to build a CrontabEntry. Crontab Line usually something
   * similar to: * * * * * org.jcrontab.jcrontab
   *
   * @param entry the line to parse
   * @return Description of the Return Value
   * @throws CrontabEntryException Error parsing the string
   */
  public CrontabEntryBean marshall(String entry)
      throws CrontabEntryException {
    boolean[] bHours = new boolean[24];
    boolean[] bMinutes = new boolean[60];
    boolean[] bMonths = new boolean[12];
    boolean[] bDaysOfWeek = new boolean[7];
    boolean[] bDaysOfMonth = new boolean[31];

    CrontabEntryBean ceb = new CrontabEntryBean();

    StringTokenizer tokenizer = new StringTokenizer(entry);

    int numTokens = tokenizer.countTokens();
    for (int i = 0; tokenizer.hasMoreElements(); i++) {
      String token = tokenizer.nextToken();
      switch (i) {
        case 0:
          // Minutes
          parseToken(token, bMinutes, false);
          ceb.setBMinutes(bMinutes);
          ceb.setMinutes(token);
          break;
        case 1:
          // Hours
          parseToken(token, bHours, false);
          ceb.setBHours(bHours);
          ceb.setHours(token);
          break;
        case 2:
          // Days of month
          parseToken(token, bDaysOfMonth, true);
          ceb.setBDaysOfMonth(bDaysOfMonth);
          ceb.setDaysOfMonth(token);
          break;
        case 3:
          // Months
          parseToken(token, bMonths, true);
          ceb.setBMonths(bMonths);
          ceb.setMonths(token);
          break;
        case 4:
          // Days of week
          parseToken(token, bDaysOfWeek, false);
          ceb.setBDaysOfWeek(bDaysOfWeek);
          ceb.setDaysOfWeek(token);
          break;
        case 5:
          // Name of the class
          String className;
          // Name of the class
          String methodName;
          try {
            int index = token.indexOf("#");
            if (index > 0) {
              StringTokenizer tokenize = new StringTokenizer(token, "#");
              className = tokenize.nextToken();
              methodName = tokenize.nextToken();
              ceb.setClassName(className);
              ceb.setMethodName(methodName);
            } else {
              className = token;
              ceb.setClassName(className);
            }
          } catch (Exception e) {
            throw new CrontabEntryException(entry);
          }
          break;
        case 6:
          // Extra Information
          String[] extraInfo = new String[numTokens - 6];
          boolean bextraInfo = true;
          for (extraInfo[i - 6] = token; tokenizer.hasMoreElements();
               extraInfo[i - 6] = tokenizer.nextToken()) {
            i++;
          }
          ceb.setBExtraInfo(bextraInfo);
          ceb.setExtraInfo(extraInfo);
          break;
        default:
          break;
      }
    }

    // At least 6 token
    if (numTokens < 6) {
      throw new CrontabEntryException(
          "The number of items is < 6 at " + entry);
    }

    return ceb;
  }


  /**
   * Parses a string describing this time table entry
   *
   * @param ceb Description of the Parameter
   * @return String describing the time table entry
   *         usuarlly something like: * * * * * org.jcrontab.jcrontab
   * @throws CrontabEntryException Error parsing the string
   */
  public String unmarshall(CrontabEntryBean ceb) throws CrontabEntryException {
    final StringBuffer sb = new StringBuffer();
    sb.append(ceb.getMinutes() + " ");
    sb.append(ceb.getHours() + " ");
    sb.append(ceb.getDaysOfMonth() + " ");
    sb.append(ceb.getMonths() + " ");
    sb.append(ceb.getDaysOfWeek() + " ");
    if ("".equals(ceb.getMethodName())) {
      sb.append(ceb.getClassName() + " ");
    } else {
      sb.append(ceb.getClassName() + "#" + ceb.getMethodName() + " ");
    }
    String[] extraInfo = ceb.getExtraInfo();
    if (extraInfo != null) {
      for (int i = 0; i < extraInfo.length; i++) {
        sb.append(extraInfo[i] + " ");
      }
    }
    return sb.toString();
  }


  /**
   * Parses a token and fills the array of booleans that represents this
   * CrontabEntryBean
   *
   * @param token       String to parser usually smth like [ * , 2-3
   *                    , 2,3,4 ,4/5 ]
   * @param arrayBool   this array is the most efficient way to
   *                    compare entries
   * @param bBeginInOne says if the array begins in 0 or in 1
   * @throws CrontabEntryException Error parsing the string
   */

  public void parseToken(String token, boolean[] arrayBool,
                         boolean bBeginInOne)
      throws CrontabEntryException {
    // This line initializes all the array of booleans instead of doing so
    // in the CrontabEntryBean Constructor.
    // for (int i = 0; i < arrayBool.length ; i++) arrayBool[i]=false;

    int i;
    try {
      if (token.equals("*")) {
        for (i = 0; i < arrayBool.length; i++) {
          arrayBool[i] = true;
        }
        return;
      }

      int index = token.indexOf(",");
      if (index > 0) {
        StringTokenizer tokenizer = new StringTokenizer(token, ",");
        while (tokenizer.hasMoreTokens()) {
          parseToken(tokenizer.nextToken(), arrayBool, bBeginInOne);
        }
        return;
      }

      index = token.indexOf("-");
      if (index > 0) {
        int start = Integer.parseInt(token.substring(0, index));
        int end = Integer.parseInt(token.substring(index + 1));

        if (bBeginInOne) {
          start--;
          end--;
        }

        for (int j = start; j <= end; j++) {
          arrayBool[j] = true;
        }
        return;
      }

      index = token.indexOf("/");
      if (index > 0) {
        int each = Integer.parseInt(token.substring(index + 1));
        for (int j = 0; j < arrayBool.length; j += each) {
          arrayBool[j] = true;
        }
        return;
      } else {
        int iValue = Integer.parseInt(token);
        if (bBeginInOne) {
          iValue--;
        }
        arrayBool[iValue] = true;
        return;
      }
    } catch (Exception e) {
      throw new CrontabEntryException("Smth was wrong with " + token);
    }
  }
}

