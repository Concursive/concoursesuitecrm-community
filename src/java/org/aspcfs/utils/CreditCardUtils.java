package org.aspcfs.utils;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.web.HtmlSelect;

/**
 *  Description of the Class
 *
 * @author     ananth
 * @created    May 6, 2004
 * @version    $Id$
 */
public class CreditCardUtils {

  public final static String VISA = "Visa";
  public final static String MASTER_CARD = "Master Card";
  public final static String AMERICAN_EXPRESS = "American Express";
  public final static String DISCOVER = "Discover";

  private String nameOnCard = null;
  private String type = null;
  private String number = null;
  private String expirationMonth = null;
  private String expirationYear = null;
  private HashMap errors = new HashMap();



  /**
   *  Sets the nameOnCard attribute of the CreditCardUtils object
   *
   * @param  tmp  The new nameOnCard value
   */
  public void setNameOnCard(String tmp) {
    this.nameOnCard = tmp;
  }


  /**
   *  Sets the paymentType attribute of the CreditCardUtils object
   *
   * @param  tmp  The new paymentType value
   */
  public void setType(String tmp) {
    this.type = tmp;
  }


  /**
   *  Sets the number attribute of the CreditCardUtils object
   *
   * @param  tmp  The new number value
   */
  public void setNumber(String tmp) {
    this.number = tmp;
  }


  /**
   *  Sets the expirationMonth attribute of the CreditCardUtils object
   *
   * @param  tmp  The new expirationMonth value
   */
  public void setExpirationMonth(String tmp) {
    this.expirationMonth = tmp;
  }


  /**
   *  Sets the expirationYear attribute of the CreditCardUtils object
   *
   * @param  tmp  The new expirationYear value
   */
  public void setExpirationYear(String tmp) {
    this.expirationYear = tmp;
  }


  /**
   *  Gets the nameOnCard attribute of the CreditCardUtils object
   *
   * @return    The nameOnCard value
   */
  public String getNameOnCard() {
    return nameOnCard;
  }


  /**
   *  Gets the paymentType attribute of the CreditCardUtils object
   *
   * @return    The paymentType value
   */
  public String getType() {
    return type;
  }


  /**
   *  Gets the number attribute of the CreditCardUtils object
   *
   * @return    The number value
   */
  public String getNumber() {
    return number;
  }


  /**
   *  Gets the expirationMonth attribute of the CreditCardUtils object
   *
   * @return    The expirationMonth value
   */
  public String getExpirationMonth() {
    return expirationMonth;
  }


  /**
   *  Gets the expirationYear attribute of the CreditCardUtils object
   *
   * @return    The expirationYear value
   */
  public String getExpirationYear() {
    return expirationYear;
  }


  /**
   *  Gets the errors attribute of the CreditCardUtils object
   *
   * @return    The errors value
   */
  public HashMap getErrors() {
    return errors;
  }


  /**
   *  Gets the valid attribute of the CreditCardUtils object
   *
   * @return    The valid value
   */
  public boolean isValid() {
    errors.clear();
    if (nameOnCard == null || "".equals(nameOnCard.trim())) {
      errors.put("nameOnCardError", "Name on Card is required");
    }
    if (type == null || "".equals(type.trim())) {
      errors.put("typeError", "Payment Type is required");
    }
    if (number == null || "".equals(number.trim())) {
      errors.put("numberError", "Credit Card number is required");
    }
    System.out.println("CreditCardUtils -> expirationYear : " + expirationYear);
    System.out.println("CreditCardUtils -> expirationMonth : " + expirationMonth);
    if (expirationMonth != null && !"".equals(expirationMonth.trim())) {
      if (expirationYear != null && !"".equals(expirationYear.trim())) {
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH) + 1;
        if (year == Integer.parseInt(expirationYear)) {
          if (month + 1 > Integer.parseInt(expirationMonth)) {
            errors.put("expirationMonthError", "The card seems to have expired");
          }
        }
      }
    }
    if (!hasErrors()) {
      checkCardValidity(type, number);
    }
    return (!hasErrors());
  }


  /**
   *  Description of the Method
   *
   * @param  cardNum  Description of the Parameter
   * @return          Description of the Return Value
   */
  private boolean checkCardNumWithMod10(String cardNum) {
    int i = 0;
    double[] cc = new double[16];
    double checksum = 0.0;
    boolean validcc = false;

    // assign each digit of the card number to a space in the array
    for (i = 0; i < cardNum.length(); i++) {
      cc[i] = Math.floor(Double.parseDouble(cardNum.substring(i, i + 1)));
    }

    // walk through every other digit doing our magic
    // if the card number is sixteen digits then start at the
    // first digit (position 0), otherwise start from the
    // second (position 1)
    for (i = (cardNum.length() % 2); i < cardNum.length(); i += 2) {
      double a = cc[i] * 2;
      if (a >= 10) {
        String aStr = String.valueOf(a);
        String b = aStr.substring(0, 1);
        String c = aStr.substring(1, 2);
        cc[i] = Math.floor(Double.parseDouble(b)) + Math.floor(Double.parseDouble(c));
      } else {
        cc[i] = a;
      }
    }

    // add up all of the digits in the array
    for (i = 0; i < cardNum.length(); i++) {
      checksum += Math.floor(cc[i]);
    }

    // if the checksum is evenly divisble by 10
    // then this is a valid card number
    validcc = ((checksum % 10) == 0);
    return validcc;
  }


  /**
   *  Description of the Method
   *
   * @param  cardNum  Description of the Parameter
   * @return          Description of the Return Value
   */
  private String cleanCardNum(String cardNum) {
    int i = 0;
    String ch = "";
    String allowed = "0123456789";
    StringBuffer newCard = new StringBuffer();

    while (i < cardNum.length()) {
      ch = cardNum.substring(i, i + 1);
      if (allowed.indexOf(ch) > -1) {
        newCard.append(ch);
      } else {
        if (!" ".equals(ch) && !"-".equals(ch)) {
          errors.put("numberError", "card number contains invalid characters");
          return "";
        }
      }
      i++;
    }
    return newCard.toString().trim();
  }


  /**
   *  Description of the Method
   *
   * @param  cardType  Description of the Parameter
   * @param  cardNum   Description of the Parameter
   * @return           Description of the Return Value
   */
  public boolean checkCardValidity(String cardType, String cardNum) {
    String validCard = "";
    int cardLength = 0;
    boolean cardLengthOK = false;
    int cardStart = 0;
    boolean cardStartOK = false;

    // check if the card type is valid
    if ((!cardType.equals(this.VISA)) && (!cardType.equals(this.MASTER_CARD)) &&
        (!cardType.equals(this.AMERICAN_EXPRESS)) && (!cardType.equals(this.DISCOVER))) {
      errors.put("typeError", "credit card type is invalid");
      return false;
    } else {
      cardType = cardType.substring(0, 1);
    }

    //clean up any spaces or dashes in the card number
    validCard = cleanCardNum(cardNum);
    if (!"".equals(validCard)) {
      // check the first digit to see if it matches the card type
      cardStart = Integer.parseInt(validCard.substring(0, 1));
      cardStartOK = (((cardType.equals("V")) && (cardStart == 4)) ||
          ((cardType.equals("M")) && (cardStart == 5)) ||
          ((cardType.equals("A")) && (cardStart == 3)) ||
          ((cardType.equals("D")) && (cardStart == 6)));
      if (!(cardStartOK)) {
        // card number's first digit doesn't match card type
        errors.put("numberError", "card number does not match the card type selected.");
        return false;
      }
      // the card number is good now, so check to make sure
      // it's a the right length
      cardLength = validCard.length();
      cardLengthOK = (((cardType.equals("V")) && ((cardLength == 13) || (cardLength == 16))) ||
          ((cardType.equals("M")) && (cardLength == 16)) ||
          ((cardType.equals("A")) && (cardLength == 15)) ||
          ((cardType.equals("D")) && (cardLength == 16)));
      if (!(cardLengthOK)) {
        // not the right length
        errors.put("numberError", "make sure you've entered all of the digits on your card.");
        return false;
      }

      // card number seems OK so do the Mod10
      if (checkCardNumWithMod10(validCard)) {
        return true;
      } else {
        errors.put("numberError", "Please make sure you've entered your card number correctly.");
        return false;
      }
    } else {
      return false;
    }
  }


  /**
   *  Sets the requestItems attribute of the CreditCardUtils object
   *
   * @param  request  The new requestItems value
   */
  public void setRequestItems(HttpServletRequest request) {
    if (request.getParameter("nameOnCard") != null) {
      nameOnCard = (String) request.getParameter("nameOnCard");
    }
    if (request.getParameter("type") != null) {
      type = (String) request.getParameter("type");
    }
    if (request.getParameter("number") != null) {
      number = (String) request.getParameter("number");
    }
    if (request.getParameter("expirationMonth") != null) {
      expirationMonth = (String) request.getParameter("expirationMonth");
    }
    if (request.getParameter("expirationYear") != null) {
      expirationYear = (String) request.getParameter("expirationYear");
    }
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public boolean hasErrors() {
    return (errors.size() > 0);
  }


  /**
   *  Gets the selectExpirationMonth attribute of the CreditCardUtils object
   *
   * @return    The selectExpirationMonth value
   */
  public HtmlSelect getSelectExpirationMonth() {
    HtmlSelect select = new HtmlSelect();
    select.setDefaultValue(expirationMonth);
    for (int i = 1; i <= 12; ++i) {
      select.addItem(Integer.toString(i));
    }
    return select;
  }


  /**
   *  Gets the selectExpirationYear attribute of the CreditCardUtils object
   *
   * @return    The selectExpirationYear value
   */
  public HtmlSelect getSelectExpirationYear() {
    HtmlSelect select = new HtmlSelect();
    Calendar today = Calendar.getInstance();
    int year = today.get(Calendar.YEAR);
    select.setDefaultValue(expirationYear);
    for (int i = year; i <= year + 10; ++i) {
      select.addItem(Integer.toString(i));
    }
    return select;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public String displayOnlyLastFour() {
    String display = "";
    if (number != null) {
      int length = number.length();
      for (int i = 0; i < length ; ++i) {
        char c = number.charAt(i);
        if (c != ' ' && c != '-') {
          if (i < length - 4) { display += "x"; }
          if (i == length - 4) { display += "-" + c; }
          if (i > length - 4) { display += c; }
        }
      }
    }
    return display.trim();
  }
}

