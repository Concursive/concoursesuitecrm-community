package org.aspcfs.modules.troubletickets.jasperreports.scriptlets;

import dori.jasper.engine.*;
import java.sql.*;

/**
 *  Description of the Class
 *
 * @author     ananth
 * @created    June 10, 2004
 * @version    $Id$
 */
public class ActivityItemsExistScriptlet extends JRDefaultScriptlet {

  /**
   *  Description of the Method
   *
   * @exception  JRScriptletException  Description of the Exception
   */
  public void beforeReportInit() throws JRScriptletException {
    //System.out.println("call beforeReportInit");
  }


  /**
   *  Description of the Method
   *
   * @exception  JRScriptletException  Description of the Exception
   */
  public void afterReportInit() throws JRScriptletException {
    //System.out.println("call afterReportInit");
  }


  /**
   *  Description of the Method
   *
   * @exception  JRScriptletException  Description of the Exception
   */
  public void beforePageInit() throws JRScriptletException {
    //System.out.println("call   beforePageInit : PAGE_NUMBER = " + this.getVariableValue("PAGE_NUMBER"));
  }


  /**
   *  Description of the Method
   *
   * @exception  JRScriptletException  Description of the Exception
   */
  public void afterPageInit() throws JRScriptletException {
    //System.out.println("call   afterPageInit  : PAGE_NUMBER = " + this.getVariableValue("PAGE_NUMBER"));
  }


  /**
   *  Description of the Method
   *
   * @exception  JRScriptletException  Description of the Exception
   */
  public void beforeColumnInit() throws JRScriptletException {
    //System.out.println("call     beforeColumnInit");
  }


  /**
   *  Description of the Method
   *
   * @exception  JRScriptletException  Description of the Exception
   */
  public void afterColumnInit() throws JRScriptletException {
    //System.out.println("call     afterColumnInit");
  }


  /**
   *  Description of the Method
   *
   * @param  groupName                 Description of the Parameter
   * @exception  JRScriptletException  Description of the Exception
   */
  public void beforeGroupInit(String groupName) throws JRScriptletException { }


  /**
   *  Description of the Method
   *
   * @param  groupName                 Description of the Parameter
   * @exception  JRScriptletException  Description of the Exception
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
   * @exception  JRScriptletException  Description of the Exception
   */
  public void beforeDetailEval() throws JRScriptletException {
    //System.out.println("        detail");
  }


  /**
   *  Description of the Method
   *
   * @exception  JRScriptletException  Description of the Exception
   */
  public void afterDetailEval() throws JRScriptletException { }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  id             Description of the Parameter
   * @return                Description of the Return Value
   * @exception  Exception  Description of the Exception
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
      if (rs.getInt("recordcount") > 0 ) {
        exists = true;
      }
    }
    return exists;
  }
}

