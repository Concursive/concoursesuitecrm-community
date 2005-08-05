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

package org.aspcfs.ant.tasks;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.aspcfs.modules.service.base.Record;
import org.aspcfs.modules.service.base.TransactionStatus;
import org.aspcfs.modules.setup.beans.RegistrationBean;
import org.aspcfs.utils.*;
import org.w3c.dom.Element;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.security.Key;

/**
 * This Ant Task registers this application with the Centric CRM
 * registration server.
 *
 * @author matt rajkowski
 * @version $Id$
 * @created September 16, 2004
 */
public class RegisterTask extends Task {

  public final static String fs = System.getProperty("file.separator");
  private String organization = null;
  private String firstName = null;
  private String lastName = null;
  private String email = null;
  private String profile = null;
  private String webPath = null;


  /**
   * Sets the organization attribute of the RegisterTask object
   *
   * @param tmp The new organization value
   */
  public void setOrganization(String tmp) {
    this.organization = tmp;
  }


  /**
   * Sets the firstName attribute of the RegisterTask object
   *
   * @param tmp The new firstName value
   */
  public void setFirstName(String tmp) {
    this.firstName = tmp;
  }


  /**
   * Sets the lastName attribute of the RegisterTask object
   *
   * @param tmp The new lastName value
   */
  public void setLastName(String tmp) {
    this.lastName = tmp;
  }


  /**
   * Sets the email attribute of the RegisterTask object
   *
   * @param tmp The new email value
   */
  public void setEmail(String tmp) {
    this.email = tmp;
  }


  /**
   * Sets the profile attribute of the RegisterTask object
   *
   * @param tmp The new profile value
   */
  public void setProfile(String tmp) {
    this.profile = tmp;
  }


  /**
   * Sets the webPath attribute of the RegisterTask object
   *
   * @param tmp The new webPath value
   */
  public void setWebPath(String tmp) {
    this.webPath = tmp;
  }


  /**
   * This method is called by Ant when the upgradeDatabaseTask is used
   *
   * @throws BuildException Description of the Exception
   */
  public void execute() throws BuildException {
    try {
      //Create the key
      Key key = null;
      File thisFile = new File(webPath + "zlib.jar");
      if (!thisFile.exists()) {
        key = PrivateString.generateKeyFile(webPath + "zlib.jar");
      } else {
        key = PrivateString.loadKey(webPath + "zlib.jar");
      }
      //Send a registration to the server
      RegistrationBean bean = new RegistrationBean();
      bean.setNameFirst(firstName);
      bean.setNameLast(lastName);
      bean.setCompany(organization);
      bean.setEmail(email);
      bean.setProfile(profile);
      //Encode the key
      BASE64Encoder encoder = new BASE64Encoder();
      bean.setZlib(encoder.encode(ObjectUtils.toByteArray(key)));
      bean.setText(PrivateString.encrypt(key, "ENTERPRISE-3.1"));
      //Transmit
      String response = null;
      if (bean.getSsl()) {
        response = HTTPUtils.sendPacket(
            "https://registration.centriccrm.com/LicenseServer.do?command=SubmitRegistration&ent1source=ent1source", bean.toXmlString());
      } else {
        response = HTTPUtils.sendPacket(
            "http://registration.centriccrm.com/LicenseServer.do?command=SubmitRegistration&ent1source=ent1source", bean.toXmlString());
      }
      XMLUtils responseXML = new XMLUtils(response);
      Element responseNode = responseXML.getFirstChild("response");
      TransactionStatus thisStatus = new TransactionStatus(responseNode);
      if (thisStatus.getStatusCode() == 0) {
        System.out.println("Transmitting registration...");
        //Retrieve the license and install
        Record record = (Record) thisStatus.getRecordList().get(0);
        String text = (String) record.get("license");
        if (text != null) {
          StringUtils.saveText(webPath + "input.txt", text);
          System.out.println(
              "Registration sent.  A license was installed for Centric CRM.");
        }
      } else {
        System.out.println(
            "Registration failed... A license was not obtained from Dark Horse Ventures");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

