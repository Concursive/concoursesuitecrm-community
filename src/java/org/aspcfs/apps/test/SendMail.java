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
package org.aspcfs.apps.test;

import org.aspcfs.utils.*;

/**
 *  Application to test sending mail
 *
 *@author     matt rajkowski
 *@created    January 28, 2003
 *@version    $Id$
 */
public class SendMail {

  /**
   *  Description of the Method
   *
   *@param  args  Description of the Parameter
   */
  public static void main(String args[]) {
    if (args[0] == null || "".equals(args[0])) {
      System.out.println("USAGE: app <email address>");
    }
    System.out.println("Running...");
    SMTPMessage mail = new SMTPMessage();
    mail.setHost("127.0.0.1");
    mail.setType("text/html");
    mail.addTo(args[0]);
    mail.setFrom("unknown@unkownaddress.com <" + args[0] + ">");
    mail.addReplyTo("unknown@unkownaddress.com");
    mail.setSubject("HELLO");
    mail.setBody("BODY");
    if (mail.send() == 2) {
      System.out.println(mail.getErrorMsg());
    }
    System.out.println("OK");
  }
}

