package com.darkhorseventures.utils;

// JavaMail docs at http://developer.java.sun.com/developer/onlineTraining/JavaMail/contents.html

import java.util.Vector;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *  A wrapper around Sun's JavaMail API. Makes sending email a snap.
 *
 *@author     mrajkowski
 *@created    July 12, 2001
 *@version    $Id$
 */
public class SMTPMessage {

  private String host = "";
  private String from = "";
  private Vector to = new Vector();
  private Vector cc = new Vector();
  private String subject = "";
  private String body = "";
  private String type = "text";
  private String errorMsg = "";


  /**
   *  Constructor for the SMTPMessage object
   *
   *@since
   */
  public SMTPMessage() { }


  /**
   *  Sets the Host attribute of the SMTPMessage object
   *
   *@param  tmp  The new Host value
   *@since
   */
  public void setHost(String tmp) {
    host = tmp;
  }


  /**
   *  Sets the From attribute of the SMTPMessage object
   *
   *@param  tmp  The new From value
   *@since
   */
  public void setFrom(String tmp) {
    from = tmp;
  }


  /**
   *  Sets the To attribute of the SMTPMessage object
   *
   *@param  tmp  The new To value
   *@since
   */
  public void setTo(String tmp) {
    to.clear();
    addTo(tmp);
  }


  /**
   *  Sets the Cc attribute of the SMTPMessage object
   *
   *@param  tmp  The new Cc value
   *@since
   */
  public void setCc(String tmp) {
    cc.clear();
    addCc(tmp);
  }


  /**
   *  Sets the Subject attribute of the SMTPMessage object
   *
   *@param  tmp  The new Subject value
   *@since
   */
  public void setSubject(String tmp) {
    subject = tmp;
  }


  /**
   *  Sets the Body attribute of the SMTPMessage object
   *
   *@param  tmp  The new Body value
   *@since
   */
  public void setBody(String tmp) {
    body = tmp;
  }


  /**
   *  Sets the Type attribute of the SMTPMessage object. Use "text" for standard
   *  email. Use "text/html" for sending HTML messages.
   *
   *@param  tmp  The new Type value
   *@since
   */
  public void setType(String tmp) {
    type = tmp;
  }


  /**
   *  Gets the ErrorMsg attribute of the SMTPMessage object
   *
   *@return    The ErrorMsg value
   *@since
   */
  public String getErrorMsg() {
    return errorMsg;
  }


  /**
   *  Adds a feature to the To attribute of the SMTPMessage object
   *
   *@param  tmp  The feature to be added to the To attribute
   *@since
   */
  public void addTo(String tmp) {
    to.addElement(tmp);
  }


  /**
   *  Adds a feature to the Cc attribute of the SMTPMessage object
   *
   *@param  tmp  The feature to be added to the Cc attribute
   *@since
   */
  public void addCc(String tmp) {
    cc.addElement(tmp);
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   *@since
   */
  public int send() {
    //throws Exception {

    if (host == null || host.equals("")) errorMsg = "Host not specified";
    if (from == null || from.equals("")) errorMsg = "Repy to address not specified";
    if (to.size() == 0) errorMsg = "Recipients not specified";
      
    if (errorMsg != null && !errorMsg.equals("")) {
      return 1;
    }

    // Get system properties
    Properties props = System.getProperties();

    // Setup mail server
    if (!host.equals("")) {
      props.put("mail.smtp.host", host);
    }

    // Get session
    Session session = Session.getInstance(props, null);

    // Define message
    MimeMessage message = new MimeMessage(session);

    try {

      // Set the from address
      message.setFrom(new InternetAddress(from));

      // Set the to address(es)
      for (int i = 0; i < to.size(); i++) {
        message.addRecipient(Message.RecipientType.TO,
            new InternetAddress((String)to.get(i)));
      }

      // Set the cc address(es)
      for (int i = 0; i < cc.size(); i++) {
        message.addRecipient(Message.RecipientType.CC,
            new InternetAddress((String)cc.get(i)));
      }

      // Set the subject
      message.setSubject(subject);

      // Set the content
      if (!type.equals("text")) {
        message.setContent(body, type);
      } else {
        message.setText(body);
      }

      // Send message
      Transport.send(message);
      return 0;
    } catch (javax.mail.MessagingException me) {
      errorMsg = me.toString();
      return 2;
    }
  }
}

