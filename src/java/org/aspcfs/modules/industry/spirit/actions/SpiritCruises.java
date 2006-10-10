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

package org.aspcfs.modules.industry.spirit.actions;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactAddress;
import org.aspcfs.modules.contacts.base.ContactAddressList;
import org.aspcfs.modules.industry.spirit.base.Maresa;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This class is used to update our Maresa DB with the client info
 *
 * @author Dan P. Marsh
 * @version $Id: ProcessCalculation.java,v 1.4 2003/04/04 21:54:47 mrajkowski
 *          Exp $
 * @created September 11, 2003
 */
public final class SpiritCruises extends CFSModule {

  /**
   * Execute this command to begin the processing, preferences must be set in
   * the system's preferences object
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public String executeCommandUpdateMaresa(ActionContext context) throws SQLException {
    //Declare the typical action objects

    ContactAddressList addressList = new ContactAddressList();
    Exception errorMessage = null;
    Connection db = null;
    HashMap errors = new HashMap();
    //Objects for connecting to the remote database using the connection pool
    ConnectionPool sqlDriver = null;
    ConnectionElement connectionElement = null;
    //ConnectionTimeout = 10;
    Connection prodDb = null;
    ConnectionElement ce = null;
    Connection conn = null;

    String maresaSite = null;
    // Test to get connected to the MAresa DB
    try {
      db = this.getConnection(context);
      String host = "128.1.1.26";
      // change,these won't work
      String port = "1521";
      String sid = "maresa";
      String s1 = "jdbc:oracle:thin:@" +
          host + ":" +
          port + ":" +
          sid;
      int thisUser = getUserId(context);
      int siteValue2 = -1;
      int thisSiteId = getUser(context, thisUser).getSiteId();

      String tableName = "lookup_site_id";
      String siteIdValue = context.getRequest().getParameter("siteId");

      int nationalSiteId = -1;
      if (siteIdValue != null) {
        nationalSiteId = Integer.parseInt(siteIdValue);
        siteValue2 = nationalSiteId;
      } else {
        siteValue2 = thisSiteId;
      }
      if (siteValue2 == -1) {
        context.getRequest().setAttribute("actionError", "Contact could not be Inserted into Maresa - MUST Choose a Site Id.");
        return ("MaresaError");
      }

      maresaSite = getMaresaId(db, siteValue2, tableName);
      String maresaLogin = "maresa_" + maresaSite;

      DriverManager.setLoginTimeout(1);
      Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
      conn = DatabaseUtils.getConnection(s1, maresaLogin, maresaLogin);

      int maresaClient = getMaresaClient(conn);
      Maresa insertMaresa = null;
      insertMaresa = new Maresa();
      String contactIdString = context.getRequest().getParameter("id");
      int contactId = -1;
      if (contactIdString == null) {
        contactId = Integer.parseInt((String) context.getRequest().getAttribute("id"));
      } else {
        contactId = Integer.parseInt(contactIdString);
      }
      int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));

      if (orgId < 1) {
        context.getRequest().setAttribute("actionError", "Contact Must Be An Account Contact to Update Maresa.");
        return ("MaresaError");
      }

      /*
       *  09.03.03
       *  Test to make sure not null value sare filled
       *  THe folowing fields cannot be null for maresa inseert.
       *  idpor = unique client number
       *  idtit = Mr. Mrs.  Dr. etc.
       *  Idint1 = Organization name
       *  idpos = zipcode
       *  idvil = City
       *  idpay = Country
       *  idco3 = segment
       *  idcsp = 001 or 002  I set it to 002????
       */
      Contact newContact = null;
      Organization newOrganization = null;
      newContact = new Contact(db, contactId);
      newOrganization = new Organization(db, orgId);

      if (newContact.getListSalutation() < 0) {
        context.getRequest().setAttribute("actionError", "An salutation is required to Update Maresa.");
        return ("MaresaError");
      }

      if (newOrganization.getSegmentId() < 0) {
        context.getRequest().setAttribute("actionError", "An salutation is required to Update Maresa.");
        return ("MaresaError");
      }

      if (newOrganization.getName() == null) {
        context.getRequest().setAttribute("actionError", "An account name is required to Update Maresa.");
        return ("MaresaError");
      }

      String zipCode = null;
      String city = null;
      boolean primaryAddressExists = false;
      addressList.setContactId(contactId);
      addressList.buildList(db);
      Iterator iaddress = addressList.iterator();
      int j = 0;
      while (iaddress.hasNext()) {
        ContactAddress thisAddress = (ContactAddress) iaddress.next();
        if (thisAddress.getPrimaryAddress()) {
          primaryAddressExists = true;
          zipCode = thisAddress.getZip();
          city = thisAddress.getCity();
        }
        ++j;
      }

      if (!primaryAddressExists) {
        context.getRequest().setAttribute("actionError", "Contact does not have a primary address.");
        return ("MaresaError");
      }

      if (zipCode == null || zipCode.trim().equals("")) {
        context.getRequest().setAttribute("actionError", "A zip code is required to Update Maresa.");
        return ("MaresaError");
      }

      if (city == null || city.trim().equals("")) {
        context.getRequest().setAttribute("actionError", "A city name is required to Update Maresa.");
        return ("MaresaError");
      }
      int segmentId = newOrganization.getSegmentId();

      if (orgId > 0) {
        if (thisSiteId > 0) {
          int test = getMaresaNumber(db, thisSiteId, orgId, contactId);
          if (test > 0) {
            if (System.getProperty("DEBUG") != null) {
            }
            insertMaresa.update(conn, db, test, contactId, orgId);
          } else {
            if (System.getProperty("DEBUG") != null) {
            }
            insertMaresa.insert(conn, db, maresaClient, contactId, orgId);
            insertMaresa.insertMaresaClient(db, maresaClient, contactId, orgId, thisSiteId);
          }
        } else {
          // nationalSiteId needs to be set to siteId chosen by national sales Rep (no site id for user

          int test2 = getMaresaNumber(db, nationalSiteId, orgId, contactId);
          if (test2 > 0) {
            if (System.getProperty("DEBUG") != null) {

            }
            insertMaresa.update(conn, db, test2, contactId, orgId);
          } else {
            if (System.getProperty("DEBUG") != null) {

            }
            insertMaresa.insert(conn, db, maresaClient, contactId, orgId);
            insertMaresa.insertMaresaClient(db, maresaClient, contactId, orgId, siteValue2);
          }

        }
      }
    } catch (SQLException e) {
      System.out.println("\n*** Java Stack Trace ***\n");
      e.printStackTrace();
      System.out.println("\n*** SQLException caught ***\n");
      context.getRequest().setAttribute("actionError", "Network Problem - Please call I.T.");
      while (e != null) {
        return ("MaresaError");
      }

    } catch (Exception e1) {
      //An error occurred, go to generic error message page
      context.getRequest().setAttribute("Error", e1);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
      if (conn != null && !conn.isClosed()) {
        conn.close();
      }
    }
    return ("UpdateOK");
  }


  /**
   * Gets the maresaId attribute of the SpiritCruises object
   *
   * @param db         Description of the Parameter
   * @param thisSiteId Description of the Parameter
   * @param tableName  Description of the Parameter
   * @return The maresaId value
   * @throws SQLException Description of the Exception
   */
  public String getMaresaId(Connection db, int thisSiteId, String tableName) throws SQLException {
    String maresaSite = null;
    ResultSet rs = null;
    int items = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT maresa_id " +
            "FROM " + tableName + " " +
            "WHERE code = ? ");
    pst.setInt(1, thisSiteId);

    rs = pst.executeQuery();

    while (rs.next()) {
      maresaSite = rs.getString("maresa_id");
    }
    rs.close();
    pst.close();
    return maresaSite;
  }


  /**
   * Gets the maresaClient attribute of the SpiritCruises object
   *
   * @param db Description of the Parameter
   * @return The maresaClient value
   * @throws SQLException Description of the Exception
   */
  public int getMaresaClient(Connection db) throws SQLException {
    int maresaClient = -1;
    ResultSet rs = null;
    int items = -1;

    PreparedStatement pst = db.prepareStatement(
        "SELECT numseq.NEXTVAL " +
            "FROM dual ");

    rs = pst.executeQuery();

    while (rs.next()) {
      maresaClient = rs.getInt(1);
    }
    rs.close();
    pst.close();
    return maresaClient;
  }


  /**
   * Gets the maresaNumber attribute of the SpiritCruises object
   *
   * @param db         Description of the Parameter
   * @param thisSiteId Description of the Parameter
   * @param orgId      Description of the Parameter
   * @param contactId  Description of the Parameter
   * @return The maresaNumber value
   * @throws SQLException Description of the Exception
   */
  public int getMaresaNumber(Connection db, int thisSiteId, int orgId, int contactId) throws SQLException {
    int maresaNumber = -1;
    ResultSet rs = null;
    int items = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT client_id " +
            "FROM MARESA_CLIENT " +
            "WHERE org_id  = ? " +
            "AND contact_id = ? AND site_id = ? ");
    pst.setInt(1, orgId);
    pst.setInt(2, contactId);
    pst.setInt(3, thisSiteId);

    rs = pst.executeQuery();

    while (rs.next()) {
      maresaNumber = rs.getInt("client_id");
    }
    rs.close();
    pst.close();
    return maresaNumber;
  }
}

