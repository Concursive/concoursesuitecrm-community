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
package org.aspcfs.utils;

import dori.jasper.engine.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import org.aspcfs.utils.DateUtils;

/**
 *  Description of the Class
 *
 *@author     ananth
 *@created    August 25, 2004
 *@version    $Id$
 */
public class JasperScriptletUtils extends JRDefaultScriptlet {

  /**
   *  Description of the Method
   *
   *@exception  JRScriptletException  Description of the Exception
   */
  public void beforeReportInit() throws JRScriptletException {
    //System.out.println("call beforeReportInit");
  }


  /**
   *  Description of the Method
   *
   *@exception  JRScriptletException  Description of the Exception
   */
  public void afterReportInit() throws JRScriptletException {
    //System.out.println("call afterReportInit");
  }


  /**
   *  Description of the Method
   *
   *@exception  JRScriptletException  Description of the Exception
   */
  public void beforePageInit() throws JRScriptletException {
    //System.out.println("call   beforePageInit : PAGE_NUMBER = " + this.getVariableValue("PAGE_NUMBER"));
  }


  /**
   *  Description of the Method
   *
   *@exception  JRScriptletException  Description of the Exception
   */
  public void afterPageInit() throws JRScriptletException {
    //System.out.println("call   afterPageInit  : PAGE_NUMBER = " + this.getVariableValue("PAGE_NUMBER"));
  }


  /**
   *  Description of the Method
   *
   *@exception  JRScriptletException  Description of the Exception
   */
  public void beforeColumnInit() throws JRScriptletException {
    //System.out.println("call     beforeColumnInit");
  }


  /**
   *  Description of the Method
   *
   *@exception  JRScriptletException  Description of the Exception
   */
  public void afterColumnInit() throws JRScriptletException {
    //System.out.println("call     afterColumnInit");
  }


  /**
   *  Description of the Method
   *
   *@param  groupName                 Description of the Parameter
   *@exception  JRScriptletException  Description of the Exception
   */
  public void beforeGroupInit(String groupName) throws JRScriptletException { }


  /**
   *  Description of the Method
   *
   *@param  groupName                 Description of the Parameter
   *@exception  JRScriptletException  Description of the Exception
   */
  public void afterGroupInit(String groupName) throws JRScriptletException {
    /*
     *  if (groupName.equals("CityGroup")) {
     *  //System.out.println("call afterGroupInit  : City = " + this.getFieldValue("City"));
     *  String allCities = (String)this.getVariableValue("AllCities");
     *  String city = (String)this.getFieldValue("City");
     *  StringBuffer sbuffer = new StringBuffer();
     *  if (allCities != null) {
     *  sbuffer.append(allCities);
     *  sbuffer.append(", ");
     *  }
     *  sbuffer.append(city);
     *  this.setVariableValue("AllCities", sbuffer.toString());
     *  }
     */
  }


  /**
   *  Description of the Method
   *
   *@exception  JRScriptletException  Description of the Exception
   */
  public void beforeDetailEval() throws JRScriptletException {
    //System.out.println("        detail");
  }


  /**
   *  Description of the Method
   *
   *@exception  JRScriptletException  Description of the Exception
   */
  public void afterDetailEval() throws JRScriptletException { }



  /**
   *  Gets the localeFormat attribute of the LocaleDateScriptlet class
   *
   *@param  date                      Description of the Parameter
   *@return                           The localeFormat value
   *@exception  JRScriptletException  Description of the Exception
   */
  public String getLocaleFormat(java.util.Date date) throws JRScriptletException {
    return (getLocaleFormat(new Timestamp(date.getTime())));
  }


  /**
   *  Gets the localeFormat attribute of the JasperScriptletUtils object
   *
   *@param  date                      Description of the Parameter
   *@param  pattern                   Description of the Parameter
   *@return                           The localeFormat value
   *@exception  JRScriptletException  Description of the Exception
   */
  public String getLocaleFormat(java.util.Date date, String pattern) throws JRScriptletException {
    return (getLocaleFormat(new Timestamp(date.getTime()), pattern));
  }


  /**
   *  Description of the Method
   *
   *@param  ts                        Description of the Parameter
   *@return                           Description of the Return Value
   *@exception  JRScriptletException  Description of the Exception
   */
  public String getLocaleFormat(java.sql.Timestamp ts) throws JRScriptletException {
    String language = (String) this.getParameterValue("language");
    String country = (String) this.getParameterValue("country");
    Locale locale = new Locale(language, country);

    SimpleDateFormat formatter =
        (SimpleDateFormat) SimpleDateFormat.getDateInstance(DateFormat.SHORT, locale);
    formatter.applyPattern(DateUtils.get4DigitYearDateFormat(formatter.toPattern()));
    return (formatter.format(ts));
  }


  /**
   *  Gets the localeFormat attribute of the JasperScriptletUtils object
   *
   *@param  ts                        Description of the Parameter
   *@param  pattern                   Description of the Parameter
   *@return                           The localeFormat value
   *@exception  JRScriptletException  Description of the Exception
   */
  public String getLocaleFormat(java.sql.Timestamp ts, String pattern) throws JRScriptletException {
    String language = (String) this.getParameterValue("language");
    String country = (String) this.getParameterValue("country");
    Locale locale = new Locale(language, country);

    SimpleDateFormat formatter =
        (SimpleDateFormat) SimpleDateFormat.getDateInstance(DateFormat.SHORT, locale);
    formatter.applyPattern(pattern);
    return (formatter.format(ts));
  }



  /**
   *  Gets the localeFormat attribute of the LocaleDateScriptlet object
   *
   *@param  value                     Description of the Parameter
   *@return                           The localeFormat value
   *@exception  JRScriptletException  Description of the Exception
   */
  public String getLocaleFormat(Double value) throws JRScriptletException {
    String language = (String) this.getParameterValue("language");
    String country = (String) this.getParameterValue("country");
    String code = (String) this.getParameterValue("currency");

    Locale locale = new Locale(language, country);
    NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
    if (code != null) {
      Currency currency = Currency.getInstance(code);
      nf.setCurrency(currency);
    }
    return (nf.format(value));
  }


  /**
   *  This method is used by the ticket_activity_log_subreport.xml which
   *  displays a subreport by first checking if activity log items exist
   *
   *@param  db                        Description of the Parameter
   *@param  id                        Description of the Parameter
   *@return                           Description of the Return Value
   *@exception  SQLException          Description of the Exception
   *@exception  JRScriptletException  Description of the Exception
   */
  public static boolean activityItemsExist(Connection db, int id) throws SQLException, JRScriptletException {
    boolean exists = false;
    System.out.println("ticketid : " + id);
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(*) AS recordcount " +
        "FROM ticket_csstm_form tcf, ticket_activity_item tai " +
        "WHERE tcf.form_id = tai.link_form_id " +
        "AND link_ticket_id = ? "
        );

    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      System.out.println("record count  : " + rs.getInt("recordcount"));
      if (rs.getInt("recordcount") > 0) {
        exists = true;
      }
    }
    return exists;
  }


  /**
   *  This method is used by the ticket_maintenance_log_subreport.xml which
   *  displays a subreport by first checking if maintenace log items exist
   *
   *@param  db                        Description of the Parameter
   *@param  id                        Description of the Parameter
   *@return                           Description of the Return Value
   *@exception  SQLException          Description of the Exception
   *@exception  JRScriptletException  Description of the Exception
   */
  public static boolean maintenanceItemsExist(Connection db, int id) throws SQLException, JRScriptletException {
    boolean exists = false;
    System.out.println("ticketid : " + id);
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(*) AS recordcount " +
        "FROM ticket_sun_form tsf, trouble_asset_replacement tar " +
        "WHERE tar.link_form_id = tsf.form_id " +
        "AND tsf.link_ticket_id = ? "
        );

    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      System.out.println("record count  : " + rs.getInt("recordcount"));
      if (rs.getInt("recordcount") > 0) {
        exists = true;
      }
    }
    return exists;
  }
}

