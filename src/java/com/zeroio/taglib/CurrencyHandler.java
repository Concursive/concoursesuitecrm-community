/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 *  This Class formats the specified amount with the specified currency
 *
 *@author     matt rajkowski
 *@created    March 17, 2004
 *@version    $Id$
 */
public class CurrencyHandler extends TagSupport {

  private double value = -1;
  private String code = null;
  private String defaultValue = null;
  private Locale locale = null;


  /**
   *  Sets the value attribute of the CurrencyHandler object
   *
   *@param  tmp  The new value value
   */
  public void setValue(double tmp) {
    this.value = tmp;
  }


  /**
   *  Sets the code attribute of the CurrencyHandler object
   *
   *@param  tmp  The new code value
   */
  public void setCode(String tmp) {
    this.code = tmp;
  }


  /**
   *  Sets the default attribute of the CurrencyHandler object
   *
   *@param  tmp  The new default value
   */
  public void setDefault(String tmp) {
    this.defaultValue = tmp;
  }


  /**
   *  Sets the locale attribute of the CurrencyHandler object
   *
   *@param  tmp  The new locale value
   */
  public void setLocale(Locale tmp) {
    this.locale = tmp;
  }


  /**
   *  Description of the Method
   *
   *@return                   Description of the Return Value
   *@exception  JspException  Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {
      if (value > -1) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        if (code != null) {
          Currency currency = Currency.getInstance(code);
          formatter.setCurrency(currency);
        }
        this.pageContext.getOut().write(formatter.format(value));
      } else {
        //no date found, output default
        if (defaultValue != null) {
          this.pageContext.getOut().write(defaultValue);
        }
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return SKIP_BODY;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public int doEndTag() {
    return EVAL_PAGE;
  }

}

