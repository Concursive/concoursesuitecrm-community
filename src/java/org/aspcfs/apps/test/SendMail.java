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
      System.exit(2);
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
      System.exit(2);
    }
    System.out.println("OK");
    System.exit(0);
  }
}

