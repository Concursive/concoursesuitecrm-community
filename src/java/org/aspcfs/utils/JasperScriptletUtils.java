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

import org.aspcfs.modules.quotes.base.QuoteProduct;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.sql.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Map;

/**
 *  Description of the Class
 *
 *@author     ananth
 *@created    August 25, 2004
 *@version    $Id: JasperScriptletUtils.java,v 1.4.12.5 2005/01/21 19:33:32
 *      mrajkowski Exp $
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
    if (date == null) {
      return null;
    }
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
    if (date == null) {
      return null;
    }
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
    if (ts == null) {
      return null;
    }
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
    if (ts == null) {
      return null;
    }
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
    if (value == null) {
      return null;
    }
    
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
   *  Description of the Method
   *
   *@param  db                        Description of the Parameter
   *@param  id                        Description of the Parameter
   *@return                           Description of the Return Value
   *@exception  SQLException          Description of the Exception
   *@exception  JRScriptletException  Description of the Exception
   */
  public static boolean hasQuoteProductOptions(Connection db, int id) throws SQLException, JRScriptletException {
    boolean exists = false;
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(*) AS recordcount " +
        "FROM quote_product_options " +
        "WHERE item_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      if (rs.getInt("recordcount") > 0) {
        exists = true;
      }
    }
    rs.close();
    pst.close();
    return exists;
  }


  /**
   *  Gets the quoteProductTotalPrice attribute of the JasperScriptletUtils
   *  class
   *
   *@param  db                        Description of the Parameter
   *@param  id                        Description of the Parameter
   *@return                           The quoteProductTotalPrice value
   *@exception  SQLException          Description of the Exception
   *@exception  JRScriptletException  Description of the Exception
   */
  public String getFormattedQuoteProductTotalPrice(Connection db, int id) throws SQLException, JRScriptletException {
    QuoteProduct thisProduct = new QuoteProduct();
    thisProduct.setBuildProductOptions(true);
    thisProduct.queryRecord(db, id);
    return getLocaleFormat(new Double(thisProduct.getTotalPrice()));
  }


  /**
   *  Gets the quoteProductTotalPrice attribute of the JasperScriptletUtils
   *  object
   *
   *@param  db                        Description of the Parameter
   *@param  id                        Description of the Parameter
   *@return                           The quoteProductTotalPrice value
   *@exception  SQLException          Description of the Exception
   *@exception  JRScriptletException  Description of the Exception
   */
  public Double getQuoteProductTotalPrice(Connection db, int id) throws SQLException, JRScriptletException {
    QuoteProduct thisProduct = new QuoteProduct();
    thisProduct.setBuildProductOptions(true);
    thisProduct.queryRecord(db, id);
    return new Double(thisProduct.getTotalPrice());
  }


  /**
   *  Gets the paddedTicketId attribute of the JasperScriptletUtils object
   *
   *@param  ticketId  Description of the Parameter
   *@return           The paddedTicketId value
   */
  public String getPaddedTicketId(int ticketId) {
    String paddedId = String.valueOf(ticketId);
    while (paddedId.length() < 6) {
      paddedId = "0" + paddedId;
    }
    return paddedId;
  }


  /**
   *  Gets the paddedQuoteId attribute of the JasperScriptletUtils object
   *
   *@param  groupId  Description of the Parameter
   *@param  version  Description of the Parameter
   *@return          The paddedQuoteId value
   */
  public String getPaddedQuoteId(int groupId, String version) {
    String paddedId = String.valueOf(groupId);
    while (paddedId.length() < 6) {
      paddedId = "0" + paddedId;
    }
    return paddedId + "(" + version + ")";
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
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(*) AS recordcount " +
        "FROM ticket_csstm_form tcf, ticket_activity_item tai " +
        "WHERE tcf.form_id = tai.link_form_id " +
        "AND link_ticket_id = ? "
        );

    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      if (rs.getInt("recordcount") > 0) {
        exists = true;
      }
    }
    rs.close();
    pst.close();
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
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(*) AS recordcount " +
        "FROM ticket_sun_form tsf, trouble_asset_replacement tar " +
        "WHERE tar.link_form_id = tsf.form_id " +
        "AND tsf.link_ticket_id = ? "
        );

    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      if (rs.getInt("recordcount") > 0) {
        exists = true;
      }
    }
    rs.close();
    pst.close();
    return exists;
  }


  /**
   *  Gets the orgAddress attribute of the JasperScriptletUtils class
   *
   *@param  db                        Description of the Parameter
   *@param  orgId                     Description of the Parameter
   *@return                           The orgAddress value
   *@exception  SQLException          Description of the Exception
   *@exception  JRScriptletException  Description of the Exception
   */
  public static String getOrgAddress(Connection db, int orgId) throws SQLException, JRScriptletException {
    StringBuffer sb = new StringBuffer();
    PreparedStatement pst = db.prepareStatement(
        "SELECT addrline1, addrline2, addrline3, " +
        "city, state, country, postalcode " +
        "FROM organization_address addr " +
        "WHERE org_id = ? AND address_type = 1 ");
    pst.setInt(1, orgId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      String addrline1 = rs.getString("addrline1");
      String addrline2 = rs.getString("addrline2");
      String addrline3 = rs.getString("addrline3");
      String city = rs.getString("city");
      String state = rs.getString("state");
      String country = rs.getString("country");
      String postalcode = rs.getString("postalcode");

      if (addrline1 != null && !"".equals(addrline1.trim())) {
        sb.append(addrline1);
      }
      if (addrline2 != null && !"".equals(addrline2.trim())) {
        if (sb.length() > 0) {
          sb.append("\n" + addrline2);
        } else {
          sb.append(addrline2);
        }
      }
      if (addrline3 != null && !"".equals(addrline3.trim())) {
        if (sb.length() > 0) {
          sb.append("\n" + addrline3);
        } else {
          sb.append(addrline3);
        }
      }
      if (city != null && !"".equals(city.trim())) {
        if (sb.length() > 0) {
          sb.append("\n" + city);
        } else {
          sb.append(city);
        }
      }
      if (state != null && !"".equals(state.trim())) {
        if (city != null && !"".equals(city.trim())) {
          sb.append(", ");
        } else {
          sb.append("\n");
        }
        sb.append(state);
      }
      if (postalcode != null && !"".equals(postalcode.trim())) {
        sb.append(" " + postalcode);
      }
      if (country != null && !"".equals(country.trim())) {
        sb.append("\n" + country);
      }
    }

    pst = db.prepareStatement(
        "SELECT number as phonenum " +
        "FROM organization_phone " +
        "WHERE org_id = ? AND phone_type = 1 ");
    pst.setInt(1, orgId);
    rs = pst.executeQuery();
    if (rs.next()) {
      String phone = rs.getString("phonenum");
      if (phone != null && !"".equals(phone.trim())) {
        if (sb.length() > 0) {
          sb.append("\nPhone: " + phone);
        } else {
          sb.append("Phone: " + phone);
        }
      }
    }

    pst = db.prepareStatement(
        "SELECT number as faxnum " +
        "FROM organization_phone " +
        "WHERE org_id = ? AND phone_type = 2 ");
    pst.setInt(1, orgId);
    rs = pst.executeQuery();
    if (rs.next()) {
      String fax = rs.getString("faxnum");
      if (fax != null && !"".equals(fax.trim())) {
        if (sb.length() > 0) {
          sb.append("\nFax: " + fax);
        } else {
          sb.append("Fax: " + fax);
        }
      }
    }

    pst = db.prepareStatement(
        "SELECT url " +
        "FROM organization org " +
        "WHERE org_id = ? ");
    pst.setInt(1, orgId);
    rs = pst.executeQuery();
    if (rs.next()) {
      String url = rs.getString("url");
      if (url != null && !"".equals(url.trim())) {
        if (sb.length() > 0) {
          sb.append("\n" + url);
        } else {
          sb.append(url);
        }
      }
    }
    rs.close();
    pst.close();
    return sb.toString();
  }


  /**
   *  Gets the orgAddress attribute of the JasperScriptletUtils class
   *
   *@param  db                        Description of the Parameter
   *@param  quoteId                   Description of the Parameter
   *@return                           The orgAddress value
   *@exception  SQLException          Description of the Exception
   *@exception  JRScriptletException  Description of the Exception
   */
  public static String getQuoteContactAddress(Connection db, int quoteId) throws SQLException, JRScriptletException {
    StringBuffer sb = new StringBuffer();
    PreparedStatement pst = db.prepareStatement(
        "SELECT email_address, phone_number, " +
        "address, fax_number " +
        "FROM quote_entry qe " +
        "WHERE quote_id = ? ");
    pst.setInt(1, quoteId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      String email = rs.getString("email_address");
      String phone = rs.getString("phone_number");
      String address = rs.getString("address");
      String fax = rs.getString("fax_number");

      if (address != null && !"".equals(address.trim())) {
        sb.append(address);
      }
      if (phone != null && !"".equals(phone.trim())) {
        if (sb.length() > 0) {
          sb.append("\nPhone: " + phone);
        } else {
          sb.append("Phone: " + phone);
        }
      }
      if (fax != null && !"".equals(fax.trim())) {
        if (sb.length() > 0) {
          sb.append("\nFax: " + fax);
        } else {
          sb.append("Fax: " + fax);
        }
      }
      if (email != null && !"".equals(email.trim())) {
        if (sb.length() > 0) {
          sb.append("\nEmail: " + email);
        } else {
          sb.append("Email" + email);
        }
      }
    }
    rs.close();
    pst.close();
    return sb.toString();
  }


  /**
   *  Gets the label attribute of the JasperScriptletUtils class
   *
   *@param  localizationPrefs         Description of the Parameter
   *@param  labelName                 Description of the Parameter
   *@param  defaultLabel              Description of the Parameter
   *@return                           The label value
   *@exception  JRScriptletException  Description of the Exception
   */
  public static String getLabel(Map localizationPrefs, String labelName, String defaultLabel) throws JRScriptletException {
    // Only intrested in field labels and value tag
    String section = "system.fields.label";
    String tagName = "value";
    String val = null;
    if (localizationPrefs != null) {
      Map prefGroup = (Map) localizationPrefs.get(section);
      if (prefGroup != null) {
        Node param = (Node) prefGroup.get(labelName);
        if (param != null) {
          val = XMLUtils.getNodeText(XMLUtils.getFirstChild((Element) param, tagName));
        }
      }
    }
    return (val != null ? val : defaultLabel);
  }
}

