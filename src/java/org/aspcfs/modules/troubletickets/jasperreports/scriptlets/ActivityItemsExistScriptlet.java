/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.troubletickets.jasperreports.scriptlets;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author ananth
 * @version $Id: ActivityItemsExistScriptlet.java,v 1.2 2004/06/22 19:40:22
 *          mrajkowski Exp $
 * @created June 10, 2004
 */
public class ActivityItemsExistScriptlet extends JRDefaultScriptlet {

  /**
   * Description of the Method
   *
   * @throws JRScriptletException Description of the Exception
   */
  public void beforeReportInit() throws JRScriptletException {
    //System.out.println("call beforeReportInit");
  }


  /**
   * Description of the Method
   *
   * @throws JRScriptletException Description of the Exception
   */
  public void afterReportInit() throws JRScriptletException {
    //System.out.println("call afterReportInit");
  }


  /**
   * Description of the Method
   *
   * @throws JRScriptletException Description of the Exception
   */
  public void beforePageInit() throws JRScriptletException {
    //System.out.println("call   beforePageInit : PAGE_NUMBER = " + this.getVariableValue("PAGE_NUMBER"));
  }


  /**
   * Description of the Method
   *
   * @throws JRScriptletException Description of the Exception
   */
  public void afterPageInit() throws JRScriptletException {
    //System.out.println("call   afterPageInit  : PAGE_NUMBER = " + this.getVariableValue("PAGE_NUMBER"));
  }


  /**
   * Description of the Method
   *
   * @throws JRScriptletException Description of the Exception
   */
  public void beforeColumnInit() throws JRScriptletException {
    //System.out.println("call     beforeColumnInit");
  }


  /**
   * Description of the Method
   *
   * @throws JRScriptletException Description of the Exception
   */
  public void afterColumnInit() throws JRScriptletException {
    //System.out.println("call     afterColumnInit");
  }


  /**
   * Description of the Method
   *
   * @param groupName Description of the Parameter
   * @throws JRScriptletException Description of the Exception
   */
  public void beforeGroupInit(String groupName) throws JRScriptletException {
  }


  /**
   * Description of the Method
   *
   * @param groupName Description of the Parameter
   * @throws JRScriptletException Description of the Exception
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
   * Description of the Method
   *
   * @throws JRScriptletException Description of the Exception
   */
  public void beforeDetailEval() throws JRScriptletException {
    //System.out.println("        detail");
  }


  /**
   * Description of the Method
   *
   * @throws JRScriptletException Description of the Exception
   */
  public void afterDetailEval() throws JRScriptletException {
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException         Description of the Exception
   * @throws JRScriptletException Description of the Exception
   */
  public static boolean activityItemsExist(Connection db, int id) throws SQLException, JRScriptletException {
    boolean exists = false;
    System.out.println("ticketid : " + id);
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(*) AS recordcount " +
        "FROM ticket_csstm_form tcf, ticket_activity_item tai " +
        "WHERE tcf.form_id = tai.link_form_id " +
        "AND link_ticket_id = ? ");

    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      System.out.println("record count  : " + rs.getInt("recordcount"));
      if (rs.getInt("recordcount") > 0) {
        exists = true;
      }
    }
    rs.close();
    pst.close();
    return exists;
  }
}

