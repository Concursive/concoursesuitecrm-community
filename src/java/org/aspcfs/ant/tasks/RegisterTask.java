package org.aspcfs.ant.tasks;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import java.security.*;
import java.io.File;
import org.aspcfs.utils.*;
import sun.misc.*;
import org.aspcfs.modules.setup.beans.RegistrationBean;
import org.w3c.dom.*;
import org.aspcfs.modules.service.base.TransactionStatus;
import org.aspcfs.modules.service.base.Record;

/**
 *  This Ant Task registers this application with the Dark Horse CRM registration
 *  server.
 *
 *@author     matt rajkowski
 *@version    $Id$
 */
public class RegisterTask extends Task {

  public final static String fs = System.getProperty("file.separator");
  private String organization = null;
  private String firstName = null;
  private String lastName = null;
  private String email = null;
  private String profile = null;
  private String webPath = null;

  public void setOrganization(String tmp) { this.organization = tmp; }
  public void setFirstName(String tmp) { this.firstName = tmp; }
  public void setLastName(String tmp) { this.lastName = tmp; }
  public void setEmail(String tmp) { this.email = tmp; }
  public void setProfile(String tmp) { this.profile = tmp; }
  public void setWebPath(String tmp) { this.webPath = tmp; }


  /**
   *  This method is called by Ant when the upgradeDatabaseTask is used
   *
   *@exception  BuildException  Description of the Exception
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
      bean.setText(PrivateString.encrypt(key, "ENTERPRISE-2.7"));
      //Transmit
      String response = null;
      if (bean.getSsl()) {
        response = HTTPUtils.sendPacket("https://registration.darkhorsecrm.com/LicenseServer.do?command=SubmitRegistration&ent1source=ent1source", bean.toXmlString());
      } else {
        response = HTTPUtils.sendPacket("http://registration.darkhorsecrm.com/LicenseServer.do?command=SubmitRegistration&ent1source=ent1source", bean.toXmlString());
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
          System.out.println("Registration sent.  A license was installed for Dark Horse CRM.");
        }
      } else {
        System.out.println("Registration failed... A license was not obtained from Dark Horse Ventures");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

