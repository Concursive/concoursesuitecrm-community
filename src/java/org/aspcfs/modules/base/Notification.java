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
package org.aspcfs.modules.base;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItemList;
import org.aspcfs.apps.workFlowManager.ComponentContext;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.communications.base.Campaign;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.SMTPMessage;
import org.aspcfs.utils.SSLMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 *  A logged message sent to a person using 1 of several transports: SMTP, Fax,
 *  Letter, IM
 *
 * @author mrajkowski
 * @version $Id$
 * @created October 15, 2001
 */
public class Notification extends Thread {

  public final static int EMAIL = 1;
  public final static int FAX = 2;
  public final static int LETTER = 3;
  public final static int EMAILFAX = 4;
  public final static int EMAILLETTER = 5;
  public final static int EMAILFAXLETTER = 6;
  public final static int IM = 7;
  public final static int SSL = 8;
  public final static int BROADCAST = 9;

  public final static String EMAIL_TEXT = "Email";
  public final static String FAX_TEXT = "Fax";
  public final static String LETTER_TEXT = "Letter";
  public final static String EMAILFAX_TEXT = "Email first, try Fax";
  public final static String EMAILLETTER_TEXT = "Email first, then Letter";
  public final static String EMAILFAXLETTER_TEXT = "Email first, try Fax, then Letter";
  public final static String IM_TEXT = "Instant Message";
  public final static String SSL_TEXT = "SSL Message";

  public final static String lf = System.getProperty("line.separator");

  String faxLogEntry = null;
  Contact contact = null;

  private int id = -1;
  private String databaseName = null;
  private String emailToNotify = null;
  private int userToNotify = -1;
  private int contactToNotify = -1;
  private String module = null;
  private int itemId = -1;
  private java.sql.Timestamp itemModified = null;
  private String from = null;
  private String subject = null;
  private int messageIdToSend = -1;
  private String messageToSend = null;
  private int type = -1;
  private String typeText = null;

  private String host = "127.0.0.1";
  private int port = -1;

  private String siteCode = null;
  private java.sql.Timestamp attempt = null;
  private int result = 0;
  private String status = "";
  private String errorMessage = null;

  private Connection connection = null;
  private Object context = null;

  private FileItemList fileAttachments = null;

  private long size = 0;

  private String bcc = null;
  private String cc = null;
  private int campaignType = -1;


  /**
   * Constructor for the Notification object
   */
  public Notification() { }


  /**
   * Constructor for the Notification object
   *
   * @param thisType Description of the Parameter
   */
  public Notification(int thisType) {
    this.setType(thisType);
  }


  /**
   * Constructor for the Notification object
   *
   * @param thisType Description of the Parameter
   * @param context  Description of the Parameter
   */
  public Notification(int thisType, ActionContext context) {
    this.setType(thisType);
    this.context = context;
  }


  /**
   * Sets the UserToNotify attribute of the Notification object
   *
   * @param tmp The new UserToNotify value
   */
  public void setUserToNotify(int tmp) {
    this.userToNotify = tmp;
  }


  /**
   * Sets the contactToNotify attribute of the Notification object
   *
   * @param tmp The new contactToNotify value
   */
  public void setContactToNotify(int tmp) {
    this.contactToNotify = tmp;
  }


  /**
   * Sets the emailToNotify attribute of the Notification object
   *
   * @param tmp The new emailToNotify value
   */
  public void setEmailToNotify(String tmp) {
    this.emailToNotify = tmp;
  }


  /**
   * Sets the databaseName attribute of the Notification object
   *
   * @param tmp The new databaseName value
   */
  public void setDatabaseName(String tmp) {
    this.databaseName = tmp;
  }


  /**
   * Sets the Module attribute of the Notification object
   *
   * @param tmp The new Module value
   */
  public void setModule(String tmp) {
    this.module = tmp;
  }


  /**
   * Sets the ItemId attribute of the Notification object
   *
   * @param tmp The new ItemId value
   */
  public void setItemId(int tmp) {
    this.itemId = tmp;
  }


  /**
   * Sets the ItemModified attribute of the Notification object
   *
   * @param tmp The new ItemModified value
   */
  public void setItemModified(java.sql.Timestamp tmp) {
    this.itemModified = tmp;
  }


  /**
   * Sets the messageIdToSend attribute of the Notification object
   *
   * @param tmp The new messageIdToSend value
   */
  public void setMessageIdToSend(int tmp) {
    this.messageIdToSend = tmp;
  }


  /**
   * Sets the MessageToSend attribute of the Notification object
   *
   * @param tmp The new MessageToSend value
   */
  public void setMessageToSend(String tmp) {
    this.messageToSend = tmp;
  }


  /**
   * Sets the type attribute of the Notification object
   *
   * @param tmp The new type value
   */
  public void setType(int tmp) {
    this.type = tmp;
    switch (this.type) {
      case EMAIL:
        this.setTypeText(EMAIL_TEXT);
        break;
      case FAX:
        this.setTypeText(FAX_TEXT);
        break;
      case LETTER:
        this.setTypeText(LETTER_TEXT);
        break;
      case EMAILFAX:
        this.setTypeText(EMAILFAX_TEXT);
        break;
      case EMAILLETTER:
        this.setTypeText(EMAILLETTER_TEXT);
        break;
      case EMAILFAXLETTER:
        this.setTypeText(EMAILFAXLETTER_TEXT);
        break;
      case IM:
        this.setTypeText(IM_TEXT);
        break;
      case SSL:
        this.setTypeText(SSL_TEXT);
        break;
      default:
        break;
    }
  }


  /**
   * Sets the Type attribute of the Notification object
   *
   * @param tmp The new Type value
   */
  public void setTypeText(String tmp) {
    this.typeText = tmp;
  }


  /**
   * Sets the Subject attribute of the Notification object
   *
   * @param tmp The new Subject value
   */
  public void setSubject(String tmp) {
    this.subject = tmp;
  }


  /**
   * Sets the from attribute of the Notification object
   *
   * @param tmp The new from value
   */
  public void setFrom(String tmp) {
    this.from = tmp;
  }


  /**
   * Sets the SiteCode attribute of the Notification object
   *
   * @param tmp The new SiteCode value
   */
  public void setSiteCode(String tmp) {
    this.siteCode = tmp;
  }


  /**
   * Sets the port attribute of the Notification object
   *
   * @param tmp The new port value
   */
  public void setPort(int tmp) {
    this.port = tmp;
  }


  /**
   * Sets the host attribute of the Notification object
   *
   * @param tmp The new host value
   */
  public void setHost(String tmp) {
    this.host = tmp;
  }


  /**
   * Sets the status attribute of the Notification object
   *
   * @param tmp The new status value
   */
  public void setStatus(String tmp) {
    this.status = tmp;
  }


  /**
   * Sets the fileAttachments attribute of the Notification object
   *
   * @param tmp The new fileAttachments value
   */
  public void setFileAttachments(FileItemList tmp) {
    this.fileAttachments = tmp;
  }


  /**
   * Sets the context attribute of the Notification object
   *
   * @param context The new context value
   */
  public void setContext(Object context) {
    this.context = context;
  }


  /**
   * Gets the Attempt attribute of the Notification object
   *
   * @return The Attempt value
   */
  public java.sql.Timestamp getAttempt() {
    return attempt;
  }


  /**
   * Gets the Result attribute of the Notification object
   *
   * @return The Result value
   */
  public int getResult() {
    return result;
  }


  /**
   * Gets the status attribute of the Notification object
   *
   * @return The status value
   */
  public String getStatus() {
    return status;
  }


  /**
   * Gets the type attribute of the Notification object
   *
   * @return The type value
   */
  public int getType() {
    return type;
  }


  /**
   * Gets the errorMessage attribute of the Notification object
   *
   * @return The errorMessage value
   */
  public String getErrorMessage() {
    return errorMessage;
  }


  /**
   * Gets the faxLogEntry attribute of the Notification object
   *
   * @return The faxLogEntry value
   */
  public String getFaxLogEntry() {
    return faxLogEntry;
  }


  /**
   * Gets the contact attribute of the Notification object
   *
   * @return The contact value
   */
  public Contact getContact() {
    return contact;
  }


  /**
   * Gets the fileAttachments attribute of the Notification object
   *
   * @return The fileAttachments value
   */
  public FileItemList getFileAttachments() {
    return fileAttachments;
  }


  /**
   * Gets the size attribute of the Notification object
   *
   * @return The size value
   */
  public long getSize() {
    return size;
  }


  /**
   *  Gets the bcc attribute of the Notification object
   *
   *@return    The bcc value
   */
  public String getBcc() {
    return bcc;
  }


  /**
   *  Sets the bcc attribute of the Notification object
   *
   *@param  tmp  The new bcc value
   */
  public void setBcc(String tmp) {
    this.bcc = tmp;
  }


  /**
   *  Gets the cc attribute of the Notification object
   *
   *@return    The cc value
   */
  public String getCc() {
    return cc;
  }


  /**
   *  Sets the cc attribute of the Notification object
   *
   *@param  tmp  The new cc value
   */
  public void setCc(String tmp) {
    this.cc = tmp;
  }


  /**
   *  Sets the campaignType attribute of the Notification object
   *
   *@param  tmp  The new campaignType value
   */
  public void setCampaignType(int tmp) {
    this.campaignType = tmp;
  }


  /**
   *  Sets the campaignType attribute of the Notification object
   *
   *@param  tmp  The new campaignType value
   */
  public void setCampaignType(String tmp) {
    this.campaignType = Integer.parseInt(tmp);
  }


  /**
   *  Gets the campaignType attribute of the Notification object
   *
   *@return    The campaignType value
   */
  public int getCampaignType() {
    return campaignType;
  }


  /**
   *  Gets the New attribute of the Notification object
   *
   *@param  db  Description of Parameter
   *@return     The New value
   */
  public boolean isNew(Connection db) {
    int resultCheck = -1;
    try {

      String sql =
          "SELECT * " +
          "FROM notification " +
          "WHERE notify_user = " + ((userToNotify > -1) ? userToNotify : contactToNotify) + " " +
          "AND " + DatabaseUtils.addQuotes(db, "module") + " = ? " +
          "AND item_id = " + itemId + " " +
          "AND item_modified = ? ";

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setString(++i, module);
      if (itemModified == null) {
        pst.setNull(++i, java.sql.Types.DATE);
      } else {
        pst.setTimestamp(++i, itemModified);
      }

      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        resultCheck = 1;
      } else {
        resultCheck = 0;
      }
      rs.close();
      pst.close();

    } catch (SQLException e) {
      errorMessage = e.toString();
      result += 1;
    }

    return (resultCheck == 0);
  }


  /**
   * Description of the Method
   *
   * @return Description of the Returned Value
   */
  public boolean hasErrors() {
    return (result > 0);
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   */
  public void insertNotification(Connection db) {
    try {
      id = DatabaseUtils.getNextSeq(db, "notification_notification_i_seq");
      String sql =
          "INSERT INTO notification " +
          "(" + (id > -1 ? "notification_id, " : "") + "notify_user, " + DatabaseUtils.addQuotes(db, "module") +
          ", item_id, item_modified, notify_type, subject, " + DatabaseUtils.addQuotes(db, "message") +
          ", result, errorMessage) " +
          "VALUES " +
          "(" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ?, ?, ?, ?) ";
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      if (id > -1) {
        pst.setInt(++i, id);
      }
      if (userToNotify > -1) {
        pst.setInt(++i, userToNotify);
      } else {
        pst.setInt(++i, contactToNotify);
      }
      pst.setString(++i, module);
      pst.setInt(++i, itemId);
      if (itemModified == null) {
        pst.setNull(++i, java.sql.Types.DATE);
      } else {
        pst.setTimestamp(++i, itemModified);
      }
      pst.setString(++i, typeText);
      pst.setString(++i, subject);
      pst.setString(++i, messageToSend);
      pst.setInt(++i, result);
      pst.setString(++i, errorMessage);
      pst.executeUpdate();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "notification_notification_i_seq", id);
    } catch (SQLException e) {
      errorMessage = e.toString();
      result += 1;
    }
  }


  /**
   * Description of the Method
   */
  public boolean notifyAddress() {
    if (type > -1) {
      try {
        if (type == EMAIL) {
          if (emailToNotify == null || "".equals(emailToNotify.trim())) {
            result = 2;
            return false;
          }
          System.out.println("Notification-> notifyAddress: " + emailToNotify);
          SMTPMessage mail = new SMTPMessage();
          mail.setHost(host);
          mail.setFrom(from);
          if (from != null && !from.equals("")) {
            mail.addReplyTo(from);
          }
          mail.setType("text/html");
          mail.addTo(emailToNotify);
          mail.setSubject(subject);
          mail.setBody(messageToSend);
          mail.setAttachments(fileAttachments);
          if (mail.send() == 2) {
            System.out.println(
                "Send error: " + mail.getErrorMsg() + "<br><br>");
            System.err.println(
                "ReportBuilder Error: Report could not be sent");
            System.err.println(mail.getErrorMsg());
          } else {
            //insertNotification(db);
          }
        } else if (type == IM) {

        } else if (type == FAX) {

        } else if (type == SSL) {

        }
      } catch (Exception e) {
        result = 2;
        errorMessage = e.toString();
        return false;
      }
    } else {
      System.out.println("Notification-> Type not set");
      return false;
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   */
  public void notifyUser(Connection db) {
    if (type > -1) {
      try {
        User thisUser = new User();
        thisUser.setBuildContact(true);
        thisUser.setBuildContactDetails(true);
        thisUser.buildRecord(db, userToNotify);
        if (type == EMAIL) {
          System.out.println(
              "Notification-> notifyUser: " + thisUser.getContact().getPrimaryEmailAddress());
          SMTPMessage mail = new SMTPMessage();
          mail.setHost(host);
          mail.setFrom(from);
          if (from != null && !from.equals("")) {
            mail.addReplyTo(from);
          }
          mail.setType("text/html");
          mail.addTo(thisUser.getContact().getPrimaryEmailAddress());
          mail.setSubject(subject);
          mail.setBody(messageToSend);
          mail.setAttachments(fileAttachments);
          if (mail.send() == 2) {
            System.out.println(
                "Send error: " + mail.getErrorMsg() + "<br><br>");
            System.err.println(
                "ReportBuilder Error: Report could not be sent");
            System.err.println(mail.getErrorMsg());
          } else {
            insertNotification(db);
          }
        } else if (type == IM) {

        } else if (type == FAX) {

        } else if (type == SSL) {

        }
      } catch (Exception e) {
        result = 2;
        errorMessage = e.toString();
      }
    } else {
      System.out.println("Notification-> Type not set");
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   */
  public void notifyContact(Connection db) {
    if (type > -1) {
      try {
        Contact thisContact = new Contact(db, String.valueOf(contactToNotify));
        //Determine the method...
        if (type == EMAILFAXLETTER) {
          if (("".equals(thisContact.getPrimaryEmailAddress())) &&
              thisContact.getPhoneNumber("Business Fax").equals("") &&
              thisContact.getPrimaryAddress() != null) {
            type = LETTER;
          } else {
            type = EMAILFAX;
          }
        }
        //2nd level
        if (type == EMAILFAX) {
          if (("".equals(thisContact.getPrimaryEmailAddress())) &&
              !thisContact.getPhoneNumber("Business Fax").equals("")) {
            type = FAX;
          } else {
            type = EMAIL;
          }
        } else if (type == EMAILLETTER) {
          if (("".equals(thisContact.getPrimaryEmailAddress())) &&
              thisContact.getPrimaryAddress() != null) {
            type = LETTER;
          } else {
            type = EMAIL;
          }
        }
        //Send it...
        if (type == EMAIL) {
          if ((campaignType == Campaign.GENERAL) && (thisContact.getNoEmail())){
            status = "Email opt out";
          } else{
            System.out.println(
                "Notification-> notifyContact: " + thisContact.getPrimaryEmailAddress());
            SMTPMessage mail = new SMTPMessage();
            mail.setHost(host);
            mail.setFrom(from);
            if (from != null && !from.equals("")) {
              mail.addReplyTo(from);
            }
            if (bcc != null && !"".equals(bcc)) {
              mail.setBcc(bcc);
            }
            if (cc != null && !"".equals(cc)) {
              mail.setCc(cc);
            }
            mail.setType("text/html");
            mail.addTo(thisContact.getPrimaryEmailAddress());
            mail.setSubject(subject);
            mail.setBody(messageToSend);
            mail.setAttachments(fileAttachments);
            int errorCode = mail.send();
            if (errorCode > 0) {
              status = "Email Error";
              System.out.println(
                  "Send error: " + mail.getErrorMsg() + "<br><br>");
              System.err.println(
                  "ReportBuilder Error: Report could not be sent");
              System.err.println(mail.getErrorMsg());
            } else {
              status = "Email Sent";
              insertNotification(db);
              size = subject.length() +
                  messageToSend.length() +
                  fileAttachments.getFileSize();
            }
          }
        } else if (type == IM) {
          if ((campaignType == Campaign.GENERAL) && (thisContact.getNoInstantMessage())){
            status = "IM opt out";
          } else{
            status = "IM Sent";
          }
        } else if (type == BROADCAST) {
          SMTPMessage mail = new SMTPMessage();
          mail.setHost(host);
          mail.setFrom(from);
          if (from != null && !from.equals("")) {
            mail.addReplyTo(from);
          }
          mail.setType("text/html");
          boolean canEmail = false;
          //sending to all email addresses
          if (!((campaignType == Campaign.GENERAL) && (thisContact.getNoEmail()))){
            canEmail = true;
            Iterator itr = thisContact.getEmailAddressList().iterator();
            if (itr.hasNext()) {
              while (itr.hasNext()) {
                EmailAddress tmpEmailAddress = (EmailAddress) itr.next();
                mail.addTo(tmpEmailAddress.getEmail());
              }
            }
          }
          boolean canPage = true;
          //sending to all text message addresses
          Iterator itr = thisContact.getTextMessageAddressList().iterator();
          if (itr.hasNext()) {
            while (itr.hasNext()) {
              TextMessageAddress tmpTextMessageAddress = (TextMessageAddress) itr.next();
              mail.addTo(tmpTextMessageAddress.getTextMessageAddress());
            }
          }
          mail.setSubject(subject);
          mail.setBody(messageToSend);
          mail.setAttachments(fileAttachments);
          int errorCode = mail.send();
          if (errorCode > 0) {
            status = "Email Error";
            System.out.println(
                "Send error: " + mail.getErrorMsg() + "<br><br>");
            System.err.println(
                "ReportBuilder Error: Report could not be sent");
            System.err.println(mail.getErrorMsg());
          } else {
            if (canEmail && canPage){
              status = "Broadcast Sent";
            } else {
              if (!canEmail){
                status = "Email Opt Out";
              } 
              if (!canPage){
                status = "Text Message Opt Out";
              }
              if ((!canEmail) && (!canPage)){
                status = "Email and Text Message Opt Out";
              }
            }
            insertNotification(db);
            size = subject.length() +
                messageToSend.length() +
                fileAttachments.getFileSize();
          }
        } else if (type == FAX) {
          String phoneNumber = thisContact.getPhoneNumber("Business Fax");
          System.out.println("Notification-> To: " + phoneNumber);
          if ((campaignType == Campaign.GENERAL) && (thisContact.getNoFax())){
            status = "Fax opt out";
          } else if (!phoneNumber.equals("") && phoneNumber.length() > 0) {
            phoneNumber = PhoneNumber.convertToNumber(phoneNumber);
            if (phoneNumber.startsWith("1")) {
              phoneNumber = phoneNumber.substring(1);
            }
            if (phoneNumber.startsWith("757")) {
              phoneNumber = phoneNumber.substring(3);
            } else {
              phoneNumber = "1" + phoneNumber;
            }
            System.out.println(
                "Notification-> Will send fax to: " + phoneNumber);
            faxLogEntry = databaseName + "|" + messageIdToSend + "|" + phoneNumber + "|" + thisContact.getId();
            status = "Fax Queued";
          }
        } else if (type == LETTER) {
          contact = thisContact;
          if (!((campaignType == Campaign.GENERAL) && (thisContact.getNoMail()))){
            status = "Added to Report";
          } else {
            status = "Mail opt out";
          }
        } else if (type == SSL) {

        }
      } catch (Exception e) {
        result = 2;
        errorMessage = e.toString();
        e.printStackTrace(System.out);
      }
    } else {
      System.out.println("Notification-> Type not set");
    }
  }


  /**
   * Description of the Method
   *
   * @throws Exception Description of the Exception
   */
  public void notifySystem() throws Exception {
    if (type == SSL) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Notification-> SENDING SSL NOTIFICATION");
      }
      SSLMessage thisMessage = new SSLMessage();
      thisMessage.setUrl(host);
      thisMessage.setPort(port);
      thisMessage.setMessage(messageToSend);
      if (context != null) {
        if (context instanceof ActionContext) {
          thisMessage.setKeystoreLocation(
              (String) ((ActionContext) context).getServletContext().getAttribute(
                  "ClientSSLKeystore"));
          thisMessage.setKeystorePassword(
              (String) ((ActionContext) context).getServletContext().getAttribute(
                  "ClientSSLKeystorePassword"));
        } else if (context instanceof ComponentContext) {
          thisMessage.setKeystoreLocation(
              (String) ((ComponentContext) context).getAttribute(
                  "ClientSSLKeystore"));
          thisMessage.setKeystorePassword(
              (String) ((ComponentContext) context).getAttribute(
                  "ClientSSLKeystorePassword"));
        }
      }
      result = thisMessage.send();
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Notification->SEND RESULT: " + result);
      }
    }
  }


  /**
   * Description of the Method
   */
  public void send() {
    this.start();
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   */
  public void send(Connection db) {
    this.connection = db;
    this.start();
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   */
  public void send(ActionContext context) {
    this.context = context;
    this.start();
  }


  /**
   * Main processing method for the Notification object
   */
  public void run() {
    try {
      if (userToNotify > -1) {
        this.notifyUser(connection);
      } else if (contactToNotify > -1) {
        this.notifyContact(connection);
      } else {
        this.notifySystem();
      }
    } catch (Exception ignore) {
      ignore.printStackTrace(System.out);
      result = 1;
    }
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public String toString() {

    StringBuffer text = new StringBuffer();
    text.append("==[ NOTIFICATION ]============================").append(lf);
    text.append("User to notify: ").append(userToNotify).append(lf);
    text.append("Module: ").append(module).append(lf);
    text.append("Item Id: ").append(itemId).append(lf);
    text.append("Item Modified: ").append(itemModified).append(lf);
    text.append("Subject: ").append(subject).append(lf);
    text.append("From: ").append(from).append(lf);
    text.append("Message: ").append(messageToSend).append(lf);
    text.append("Type: ").append(type).append(lf);
    text.append("==============================================");
    return text.toString();
  }
}

