//Public domain code from Google
package org.aspcfs.utils;

import java.util.*;
import java.text.*;

/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    September 25, 2001
 *@version    $Id: CurrencyFormat.java,v 1.1.1.1 2002/01/14 19:49:27 mrajkowski
 *      Exp $
 */
public class CurrencyFormat {

  //double dollarAmount = 0.00;
  Locale[] locales = NumberFormat.getAvailableLocales();
  DecimalFormatSymbols currencySymbols = new DecimalFormatSymbols(Locale.US);


  /**
   *  The main program for the CurrencyFormat class
   *
   *@since
   */
  public CurrencyFormat() { }


  /**
   *  Gets the currency attribute of the CurrencyFormat object
   *
   *@param  dollarAmount  Description of the Parameter
   *@return               The currency value
   */
  public String getCurrency(double dollarAmount) {
    return formatCurrency(dollarAmount, currencySymbols, Locale.US);
  }


  /**
   *  Format a currency amount in a designated denomination locale but displays
   *  in according to the displayLocal Just a kludge to show it can be done.
   *
   *@param  value            Description of Parameter
   *@param  currencySymbols  Description of Parameter
   *@param  displayLocale    Description of Parameter
   *@return                  Description of the Returned Value
   *@since
   */
  private static String formatCurrency(double value,
      DecimalFormatSymbols currencySymbols, Locale displayLocale) {
    // this is the only way I can find to get the locale specfic
    // currency pattern.
    ResourceBundle resource = ResourceBundle.getBundle
        ("java.text.resources.LocaleElements", displayLocale);
    String[] numberPatterns =
        resource.getStringArray("NumberPatterns");

    // look for the currency sign
    // to show international currency symbol.
    int moneySignAt = numberPatterns[1].indexOf('\u00A4');
    String pattern = numberPatterns[1].substring(0, moneySignAt) +
        "\u00A4\u00A4" +
        numberPatterns[1].substring(moneySignAt);
    DecimalFormatSymbols symbols = new
        DecimalFormatSymbols(displayLocale);
    // fix up the currency symbols to the currency locale

    symbols.setCurrencySymbol(currencySymbols.getCurrencySymbol());

    symbols.setInternationalCurrencySymbol(currencySymbols.getInternationalCurrencySymbol());
    DecimalFormat df = new DecimalFormat(pattern, symbols);
    return justify(displayLocale.getDisplayName()) +
        df.format(value);
  }


  /**
   *  Description of the Method
   *
   *@param  s  Description of Parameter
   *@return    Description of the Returned Value
   *@since
   */
  private static String justify(String s) {
    StringBuffer buf = new StringBuffer(s);
    for (int i = 0, end = 35 - s.length(); i < end; i++) {
      buf.append(".");
    }
    return buf.toString();
  }


  /**
   *  Gets the currencyString attribute of the CurrencyFormat class
   *
   *@param  value  Description of the Parameter
   *@return        The currencyString value
   */
  public static String getCurrencyString(Double value, Locale locale) {
    String currencyAsString = "";
    try {
      NumberFormat nf = NumberFormat.getInstance(locale);
      currencyAsString = nf.format(value);
    } catch (Exception e) {
    }
    return currencyAsString;
  }

}

