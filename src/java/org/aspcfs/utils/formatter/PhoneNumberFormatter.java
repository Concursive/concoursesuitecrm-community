/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.utils.formatter;

import org.aspcfs.modules.base.PhoneNumber;

import java.util.Locale;

/**
 * Takes a phone number and formats the various fields to make the records
 * consistent and presentable.
 *
 * @author matt rajkowski
 * @version $Id: PhoneNumberFormatter.java,v 1.1.2.2 2003/03/06 17:49:29
 *          mrajkowski Exp $
 * @created March 5, 2003
 */
public class PhoneNumberFormatter {

  private final static String validChars = "[]/0123456789()-., ";


  /**
   * Constructor for the PhoneNumberFormatter object
   */
  public PhoneNumberFormatter() {
  }


  /**
   * Description of the Method
   */
  public void config() {

  }


  /**
   * Tries to format phone number for the given Locale
   *
   * @param thisNumber Description of the Parameter
   * @param locale     Description of the Parameter
   */
  public static void format(PhoneNumber thisNumber, Locale locale) {
    if (locale != null) {
      if (locale == Locale.US || locale == Locale.CANADA) {
        format(thisNumber);
      } else {
        formatInternational(thisNumber);
      }
    }
  }


  /**
   * Formats a phone number for US/Canada
   *
   * @param thisNumber Description of the Parameter
   */
  public static void format(PhoneNumber thisNumber) {
    String[] number = new String[]{thisNumber.getNumber(), null};
    if (number[0] != null && number[0].length() > 0) {
      number[0] = number[0].trim();
      //Split out the extention if there is one
      extractExtension(number, "ext.");
      extractExtension(number, "ext");
      extractExtension(number, "x");
      if (number[1] != null && !"".equals(number[1])) {
        thisNumber.setExtension(number[1]);
      }
      //Format just the number
      if (!(number[0].charAt(0) == '+')) {
        //Skip if someone has entered something other than basic numbers
        if (isMostlyNumbers(number[0])) {
          number[0] = formatNumber(number[0]);
        }
      }
      thisNumber.setNumber(number[0]);
    }
  }


  /**
   * Description of the Method
   *
   * @param number Description of the Parameter
   * @return Description of the Return Value
   */
  private static String formatNumber(String number) {
    String tmpNum = extractNumbers(number);
    if (tmpNum.length() == 11 && tmpNum.charAt(0) == '1') {
      tmpNum = tmpNum.substring(1);
    }
    if (tmpNum.length() == 10) {
      if (tmpNum.charAt(0) == '1' ||
          tmpNum.charAt(0) == '0') {
        return ("+" + number);
      } else {
        StringBuffer result = new StringBuffer();
        result.append("(");
        result.append(tmpNum.substring(0, 3));
        result.append(") ");
        result.append(tmpNum.substring(3, 6));
        result.append("-");
        result.append(tmpNum.substring(6, 10));
        return result.toString();
      }
    }
    if (tmpNum.charAt(0) == '0') {
      return ("+" + number);
    }
    return number;
  }


  /**
   * Description of the Method
   *
   * @param thisNumber Description of the Parameter
   */
  public static void formatInternational(PhoneNumber thisNumber) {
    String[] number = new String[]{thisNumber.getNumber(), null};
    if (number[0] != null && number[0].length() > 0) {
      number[0] = number[0].trim();
      //Split out the extention if there is one
      extractExtension(number, "ext.");
      extractExtension(number, "ext");
      extractExtension(number, "x");
      thisNumber.setExtension(number[1]);
      //Format just the number
      if (number[0].indexOf("+") == -1) {
        //Add a +, that's all for now
        if (isMostlyNumbers(number[0])) {
          number[0] = "+" + number[0];
        }
      }
      thisNumber.setNumber(number[0]);
    }
  }


  /**
   * Gets the mostlyNumbers attribute of the PhoneNumberFormatter object
   *
   * @param number Description of the Parameter
   * @return The mostlyNumbers value
   */
  private static boolean isMostlyNumbers(String number) {
    boolean result = true;
    for (int i = 0; i < number.length(); i++) {
      if (validChars.indexOf(number.charAt(i)) == -1) {
        result = false;
      }
    }
    return result;
  }


  /**
   * Description of the Method
   *
   * @param tmp Description of the Parameter
   * @return Description of the Return Value
   */
  public final static String extractNumbers(String tmp) {
    StringBuffer sb = new StringBuffer();
    String allowed = "0123456789";
    for (int i = 0; i < tmp.length(); i++) {
      char theChar = tmp.charAt(i);
      if (allowed.indexOf(theChar) > -1) {
        sb.append(theChar);
      }
    }
    return sb.toString();
  }


  /**
   * Description of the Method
   *
   * @param number Description of the Parameter
   * @param check  Description of the Parameter
   */
  private static void extractExtension(String[] number, String check) {
    if (number[0].toLowerCase().indexOf(check) > -1) {
      number[1] = number[0].substring(
          number[0].toLowerCase().indexOf(check) + check.length()).trim();
      number[0] = number[0].substring(
          0, number[0].toLowerCase().indexOf(check)).trim();
    }
  }
}

