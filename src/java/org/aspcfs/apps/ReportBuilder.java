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
package org.aspcfs.apps;

import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.SMTPMessage;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id$
 * @created January 13, 2001
 */
public class ReportBuilder {

  protected java.util.Date start = new java.util.Date();
  protected StringBuffer output = new StringBuffer();
  protected String baseName = "";
  protected String dbUser = "";
  protected String dbPass = "";
  protected int totalRecords = 0;


  /**
   * Gets the iP attribute of the ReportBuilder object
   *
   * @return The iP value
   */
  protected String getIP() {
    String tmp = "";
    try {
      InetAddress localaddr = InetAddress.getLocalHost();
      tmp = localaddr.getHostAddress();
    } catch (UnknownHostException e) {
    }
    return tmp;
  }


  /**
   * Gets the hostName attribute of the ReportBuilder object
   *
   * @return The hostName value
   */
  protected String getHostName() {
    String tmp = "";
    try {
      InetAddress localaddr = InetAddress.getLocalHost();
      tmp = localaddr.getHostName();
    } catch (UnknownHostException e) {
    }
    if (tmp.indexOf(".") == -1) {
      tmp += ".darkhorseventures.com";
    }
    return tmp;
  }


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param flag   Description of the Parameter
   * @param output Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void sendReport(Connection db, String flag, StringBuffer output) throws SQLException {
    //Get list of recipients...

    UserList userList = new UserList();
    //need to set criteria here... otherwise sent to all!
    userList.setBuildContact(true);
    userList.setBuildContactDetails(true);
    userList.setRoleType(Constants.ROLETYPE_REGULAR);
    //only regular users
    userList.buildList(db);
    Iterator i = userList.iterator();
    StringBuffer log = new StringBuffer();

    while (i.hasNext()) {
      User thisUser = (User) i.next();
      if (thisUser.getContact().getEmailAddress("Work").indexOf("@") > 0) {
        //Send the report
        SMTPMessage mail = new SMTPMessage();
        mail.setHost("127.0.0.1");
        mail.setFrom(baseName + "@" + this.getHostName());
        mail.addTo(thisUser.getContact().getEmailAddress("Business"));
        mail.setSubject(
            "Report [1 day: " + totalRecords + " record" + ((totalRecords != 1) ? "s" : "") + "]");
        mail.setBody(output.toString());
        mail.setType("text/html");
        if (mail.send() == 2) {
          log.append("Send error: " + mail.getErrorMsg() + "<br><br>");
          System.err.println("ReportBuilder Error: Report could not be sent");
          System.err.println(mail.getErrorMsg());
        }
      }
    }

    //Send the log
    SMTPMessage mail = new SMTPMessage();
    mail.setHost("mail.darkhorseventures.com");
    mail.setFrom("notifier_error@" + this.getHostName());
    mail.addTo("mrajkowski@darkhorseventures.com");
    mail.setSubject(
        "Report [1 day: " + totalRecords + " record" + ((totalRecords != 1) ? "s" : "") + "]");
    mail.setBody(
        "Emails generated: " + userList.size() + "<br><br>" + log.toString());
    mail.setType("text/html");
    if (mail.send() == 2) {
      System.err.println("ReportBuilder Error: Report could not be sent");
      System.err.println(mail.getErrorMsg());
    }
  }


  /**
   * Description of the Method
   *
   * @param exc Description of the Parameter
   */
  protected void sendAdminReport(String exc) {
    SMTPMessage mail = new SMTPMessage();
    mail.setHost("mail.darkhorseventures.com");
    mail.setFrom("notifier_error@" + getHostName());
    mail.addTo("mrajkowski@darkhorseventures.com");
    mail.setSubject("Admin Alert");
    mail.setBody("<PRE>Any Errors Follow:<br>" + exc.toString() + "</PRE>");
    mail.setType("text/html");
    if (mail.send() == 2) {
      System.err.println("ReportBuilder Error: Mail could not be sent");
      System.err.println(mail.getErrorMsg());
    }
  }
}

