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
package org.aspcfs.modules.setup.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.login.base.AuthenticationItem;
import org.aspcfs.modules.service.base.Record;
import org.aspcfs.modules.service.base.RecordList;
import org.aspcfs.modules.service.base.TransactionStatus;
import org.aspcfs.modules.service.base.TransactionStatusList;
import org.aspcfs.modules.setup.base.Registration;
import org.aspcfs.modules.setup.base.RegistrationList;
import org.aspcfs.modules.setup.beans.Zlib;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.sql.Connection;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    September 16, 2004
 *@version    $Id$
 */
public class SetupServer extends CFSModule {

  /**
   *  A sample server action to receive the submitted registration form
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSubmitRegistration(ActionContext context) {
    try {
      boolean sendReg = false;
      //Receive the xml from the request
      XMLUtils xml = new XMLUtils(context.getRequest());
      //Create the object from the serialized xml
      Zlib license = new Zlib(xml);
      //Prepare the response XML
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = dbf.newDocumentBuilder();
      Document document = builder.newDocument();
      Element app = document.createElement("aspcfs");
      document.appendChild(app);
      //Prepare the status response, with possibly a new record
      TransactionStatusList statusList = new TransactionStatusList();
      TransactionStatus thisStatus = new TransactionStatus();
      if (license.isValid()) {
        //Store the license information in the database
        Connection db = null;
        try {
          //get a database connection using the vhost context info
          AuthenticationItem auth = new AuthenticationItem();
          db = auth.getConnection(context, false);
          if (db == null && System.getProperty("DEBUG") != null) {
            System.out.println("SetupServer-> FATAL: db IS NULL!!!");
          }
          //locate a previous registration to send back
          db.setAutoCommit(false);
          Registration previousRegistration = RegistrationList.locate(db, license.getEmail(), license.getProfile(), true);
          //If a previous registration matches, then set it to disabled and insert the new registration
          if (previousRegistration != null) {
            previousRegistration.setEnabled(false);
            previousRegistration.updateEnabled(db);
            license.setEdition(previousRegistration.getEdition());
            license.setText2(previousRegistration.getText2());
          } else {
            if (license.getText().startsWith("ENTERPRISE-")) {
              license.setEdition("Enterprise Edition");
              license.setText2(StringUtils.randomString(7, 7) + "-1");
            } else {
              license.setEdition("Free Edition (5-seat binary)");
              license.setText2(StringUtils.randomString(7, 7) + "5");
            }
          }
          //save the registration
          Registration registration = new Registration();
          registration.setKeyFile(license.getKeyText());
          registration.setNameFirst(license.getNameFirst());
          registration.setNameLast(license.getNameLast());
          registration.setCompany(license.getCompany());
          registration.setEmail(license.getEmail());
          registration.setProfile(license.getProfile());
          registration.setText(license.getText());
          registration.setOs(license.getOs());
          registration.setJava(license.getJava());
          registration.setWebserver(license.getWebserver());
          registration.setIp(context.getIpAddress());
          registration.setEdition(license.getEdition());
          registration.setText2(license.getText2());
          registration.insert(db);
          thisStatus.setStatusCode(0);
          thisStatus.setMessage("SUCCESS");
          db.commit();
          sendReg = true;
        } catch (Exception e) {
          thisStatus.setStatusCode(1);
          thisStatus.setMessage("FAILURE");
          db.rollback();
          e.printStackTrace(System.out);
        } finally {
          if (db != null) {
            db.setAutoCommit(true);
          }
          freeConnection(context, db);
        }
        if (sendReg) {
          if ("ent1source".equals(context.getRequest().getParameter("ent1source"))) {
            //Stream the license back
            RecordList recordList = new RecordList("license");
            Record record = new Record("processed");
            record.put("license", license.getCode());
            recordList.add(record);
            thisStatus.setRecordList(recordList);
          } else {
            //Send the license back as email
            license.setSystemStatus(this.getSystemStatus(context));
            license.sendEmailRegistration();
          }
        }
      } else {
        thisStatus.setStatusCode(1);
        thisStatus.setMessage("FAILURE");
      }
      statusList.add(thisStatus);
      statusList.appendResponse(document, app);
      context.getRequest().setAttribute("statusXML", XMLUtils.toString(document, "UTF-8"));
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return "SubmitProcessOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandRequestLicense(ActionContext context) {
    try {
      //Receive the xml from the request
      XMLUtils xml = new XMLUtils(context.getRequest());
      //Create the object from the serialized xml
      Zlib license = new Zlib(xml);
      //Prepare the response XML
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = dbf.newDocumentBuilder();
      Document document = builder.newDocument();
      Element app = document.createElement("aspcfs");
      document.appendChild(app);
      TransactionStatusList statusList = new TransactionStatusList();
      TransactionStatus thisStatus = new TransactionStatus();
      if (license.isValid()) {
        //Load the license information from the database
        Connection db = null;
        try {
          //get a database connection using the vhost context info
          AuthenticationItem auth = new AuthenticationItem();
          db = auth.getConnection(context, false);
          if (db == null && System.getProperty("DEBUG") != null) {
            System.out.println("SetupServer-> FATAL: db IS NULL!!!");
          }
          //locate a previous registration to send back
          Registration previousRegistration = RegistrationList.locate(db, license.getEmail(), license.getProfile(), true);
          //If not found send back error message
          if (previousRegistration == null) {
            thisStatus.setStatusCode(1);
            thisStatus.setMessage("FAILURE");
          }
          //If found then see if it is new
          if (previousRegistration != null) {
            thisStatus.setStatusCode(0);
            thisStatus.setMessage("SUCCESS");
            if (!previousRegistration.getText2().equals(license.getText2())) {
              //New license to send back...
              license.setNameFirst(previousRegistration.getNameFirst());
              license.setNameLast(previousRegistration.getNameLast());
              license.setCompany(previousRegistration.getCompany());
              license.setEdition(previousRegistration.getEdition());
              license.setText(previousRegistration.getText());
              license.setText2(previousRegistration.getText2());
              //Send the license back as a new status record
              RecordList recordList = new RecordList("license");
              Record record = new Record("update");
              record.put("license", license.getCode());
              recordList.add(record);
              thisStatus.setRecordList(recordList);
              //TODO: Log the transaction request
            }
          }
        } catch (Exception e) {
          thisStatus.setStatusCode(1);
          thisStatus.setMessage("FAILURE");
        } finally {
          freeConnection(context, db);
        }
      } else {
        thisStatus.setStatusCode(1);
        thisStatus.setMessage("FAILURE");
      }
      statusList.add(thisStatus);
      statusList.appendResponse(document, app);
      context.getRequest().setAttribute("statusXML", XMLUtils.toString(document, "UTF-8"));
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return "UpdateProcessOK";
  }
}

