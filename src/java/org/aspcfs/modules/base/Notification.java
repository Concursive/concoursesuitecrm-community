package org.aspcfs.modules.base;

import java.sql.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.*;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.apps.workFlowManager.*;
import org.aspcfs.utils.*;

/**
 *  A logged message sent to a person using 1 of several transports: SMTP, Fax,
 *  Letter, IM
 *
 *@author     mrajkowski
 *@created    October 15, 2001
 *@version    $Id$
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


  /**
   *  Constructor for the Notification object
   *
   *@since
   */
  public Notification() { }


  /**
   *  Constructor for the Notification object
   *
   *@param  thisType  Description of the Parameter
   */
  public Notification(int thisType) {
    this.setType(thisType);
  }


  /**
   *  Constructor for the Notification object
   *
   *@param  thisType  Description of the Parameter
   *@param  context   Description of the Parameter
   */
  public Notification(int thisType, ActionContext context) {
    this.setType(thisType);
    this.context = context;
  }


  /**
   *  Sets the UserToNotify attribute of the Notification object
   *
   *@param  tmp  The new UserToNotify value
   *@since
   */
  public void setUserToNotify(int tmp) {
    this.userToNotify = tmp;
  }


  /**
   *  Sets the contactToNotify attribute of the Notification object
   *
   *@param  tmp  The new contactToNotify value
   */
  public void setContactToNotify(int tmp) {
    this.contactToNotify = tmp;
  }

  public void setEmailToNotify(String tmp) { this.emailToNotify = tmp; }


  /**
   *  Sets the databaseName attribute of the Notification object
   *
   *@param  tmp  The new databaseName value
   */
  public void setDatabaseName(String tmp) {
    this.databaseName = tmp;
  }


  /**
   *  Sets the Module attribute of the Notification object
   *
   *@param  tmp  The new Module value
   *@since
   */
  public void setModule(String tmp) {
    this.module = tmp;
  }


  /**
   *  Sets the ItemId attribute of the Notification object
   *
   *@param  tmp  The new ItemId value
   *@since
   */
  public void setItemId(int tmp) {
    this.itemId = tmp;
  }


  /**
   *  Sets the ItemModified attribute of the Notification object
   *
   *@param  tmp  The new ItemModified value
   *@since
   */
  public void setItemModified(java.sql.Timestamp tmp) {
    this.itemModified = tmp;
  }


  /**
   *  Sets the messageIdToSend attribute of the Notification object
   *
   *@param  tmp  The new messageIdToSend value
   */
  public void setMessageIdToSend(int tmp) {
    this.messageIdToSend = tmp;
  }


  /**
   *  Sets the MessageToSend attribute of the Notification object
   *
   *@param  tmp  The new MessageToSend value
   *@since
   */
  public void setMessageToSend(String tmp) {
    this.messageToSend = tmp;
  }


  /**
   *  Sets the type attribute of the Notification object
   *
   *@param  tmp  The new type value
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
   *  Sets the Type attribute of the Notification object
   *
   *@param  tmp  The new Type value
   *@since
   */
  public void setTypeText(String tmp) {
    this.typeText = tmp;
  }


  /**
   *  Sets the Subject attribute of the Notification object
   *
   *@param  tmp  The new Subject value
   *@since
   */
  public void setSubject(String tmp) {
    this.subject = tmp;
  }


  /**
   *  Sets the from attribute of the Notification object
   *
   *@param  tmp  The new from value
   */
  public void setFrom(String tmp) {
    this.from = tmp;
  }


  /**
   *  Sets the SiteCode attribute of the Notification object
   *
   *@param  tmp  The new SiteCode value
   *@since
   */
  public void setSiteCode(String tmp) {
    this.siteCode = tmp;
  }


  /**
   *  Sets the port attribute of the Notification object
   *
   *@param  tmp  The new port value
   */
  public void setPort(int tmp) {
    this.port = tmp;
  }


  /**
   *  Sets the host attribute of the Notification object
   *
   *@param  tmp  The new host value
   */
  public void setHost(String tmp) {
    this.host = tmp;
  }


  /**
   *  Sets the fileAttachments attribute of the Notification object
   *
   *@param  tmp  The new fileAttachments value
   */
  public void setFileAttachments(FileItemList tmp) {
    this.fileAttachments = tmp;
  }


  /**
   *  Sets the context attribute of the Notification object
   *
   *@param  context  The new context value
   */
  public void setContext(Object context) {
    this.context = context;
  }


  /**
   *  Gets the Attempt attribute of the Notification object
   *
   *@return    The Attempt value
   *@since
   */
  public java.sql.Timestamp getAttempt() {
    return attempt;
  }


  /**
   *  Gets the Result attribute of the Notification object
   *
   *@return    The Result value
   *@since
   */
  public int getResult() {
    return result;
  }


  /**
   *  Gets the status attribute of the Notification object
   *
   *@return    The status value
   */
  public String getStatus() {
    return status;
  }


  /**
   *  Gets the type attribute of the Notification object
   *
   *@return    The type value
   */
  public int getType() {
    return type;
  }


  /**
   *  Gets the errorMessage attribute of the Notification object
   *
   *@return    The errorMessage value
   *@since
   */
  public String getErrorMessage() {
    return errorMessage;
  }


  /**
   *  Gets the faxLogEntry attribute of the Notification object
   *
   *@return    The faxLogEntry value
   */
  public String getFaxLogEntry() {
    return faxLogEntry;
  }


  /**
   *  Gets the contact attribute of the Notification object
   *
   *@return    The contact value
   */
  public Contact getContact() {
    return contact;
  }


  /**
   *  Gets the fileAttachments attribute of the Notification object
   *
   *@return    The fileAttachments value
   */
  public FileItemList getFileAttachments() {
    return fileAttachments;
  }


  /**
   *  Gets the size attribute of the Notification object
   *
   *@return    The size value
   */
  public long getSize() {
    return size;
  }


  /**
   *  Gets the New attribute of the Notification object
   *
   *@param  db  Description of Parameter
   *@return     The New value
   *@since
   */
  public boolean isNew(Connection db) {
    int resultCheck = -1;
    try {

      String sql =
          "SELECT * " +
          "FROM notification " +
          "WHERE notify_user = " + ((userToNotify > -1) ? userToNotify : contactToNotify) + " " +
          "AND module = ? " +
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
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   *@since
   */
  public boolean hasErrors() {
    return (result > 0);
  }


  /**
   *  Description of the Method
   *
   *@param  db  Description of Parameter
   *@since
   */
  public void insertNotification(Connection db) {
    try {
      String sql =
          "INSERT INTO notification " +
          "(notify_user, module, item_id, item_modified, notify_type, subject, message, result, errorMessage) " +
          "VALUES " +
          "(?, ?, ?, ?, ?, ?, ?, ?, ?) ";
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
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
    } catch (SQLException e) {
      errorMessage = e.toString();
      result += 1;
    }
  }
  
  
  public void notifyAddress() {
    if (type > -1) {
      try {
        if (type == EMAIL) {
          System.out.println("Notification-> To: " + emailToNotify);
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
            System.out.println("Send error: " + mail.getErrorMsg() + "<br><br>");
            System.err.println("ReportBuilder Error: Report could not be sent");
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
      }
    } else {
      System.out.println("Notification-> Type not set");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db  Description of Parameter
   *@since
   */
  public void notifyUser(Connection db) {
    if (type > -1) {
      try {
        User thisUser = new User();
        thisUser.setBuildContact(true);
        thisUser.setBuildContactDetails(true);
        thisUser.buildRecord(db, userToNotify);
        if (type == EMAIL) {
          System.out.println("Notification-> To: " + thisUser.getContact().getEmailAddress("Business"));
          SMTPMessage mail = new SMTPMessage();
          mail.setHost(host);
          mail.setFrom(from);
          if (from != null && !from.equals("")) {
            mail.addReplyTo(from);
          }
          mail.setType("text/html");
          mail.addTo(thisUser.getContact().getEmailAddress("Business"));
          mail.setSubject(subject);
          mail.setBody(messageToSend);
          mail.setAttachments(fileAttachments);
          if (mail.send() == 2) {
            System.out.println("Send error: " + mail.getErrorMsg() + "<br><br>");
            System.err.println("ReportBuilder Error: Report could not be sent");
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
   *  Description of the Method
   *
   *@param  db  Description of Parameter
   */
  public void notifyContact(Connection db) {
    if (type > -1) {
      try {
        Contact thisContact = new Contact(db, String.valueOf(contactToNotify));
        //Determine the method...
        if (type == EMAILFAXLETTER) {
          if (thisContact.getEmailAddress("Business").equals("") &&
              thisContact.getPhoneNumber("Business Fax").equals("") &&
              thisContact.getAddress("Business") != null) {
            type = LETTER;
          } else {
            type = EMAILFAX;
          }
        }
        //2nd level
        if (type == EMAILFAX) {
          if (thisContact.getEmailAddress("Business").equals("") &&
              !thisContact.getPhoneNumber("Business Fax").equals("")) {
            type = FAX;
          } else {
            type = EMAIL;
          }
        } else if (type == EMAILLETTER) {
          if (thisContact.getEmailAddress("Business").equals("") &&
              thisContact.getAddress("Business") != null) {
            type = LETTER;
          } else {
            type = EMAIL;
          }
        }
        //Send it...
        if (type == EMAIL) {
          System.out.println("Notification-> To: " + thisContact.getEmailAddress("Business"));
          SMTPMessage mail = new SMTPMessage();
          mail.setHost(host);
          mail.setFrom(from);
          if (from != null && !from.equals("")) {
            mail.addReplyTo(from);
          }
          mail.setType("text/html");
          if (thisContact.getEmailAddress("Business").equals("")) {
            mail.addTo(thisContact.getEmailAddress("Personal"));
          } else {
            mail.addTo(thisContact.getEmailAddress("Business"));
          }
          mail.setSubject(subject);
          mail.setBody(messageToSend);
          mail.setAttachments(fileAttachments);
          int errorCode = mail.send();
          if (errorCode > 0) {
            status = "Email Error";
            System.out.println("Send error: " + mail.getErrorMsg() + "<br><br>");
            System.err.println("ReportBuilder Error: Report could not be sent");
            System.err.println(mail.getErrorMsg());
          } else {
            status = "Email Sent";
            insertNotification(db);
            size = subject.length() +
                messageToSend.length() +
                fileAttachments.getFileSize();
          }
        } else if (type == IM) {

          status = "IM Sent";
        } else if (type == FAX) {
          String phoneNumber = thisContact.getPhoneNumber("Business Fax");
          System.out.println("Notification-> To: " + phoneNumber);
          if (!phoneNumber.equals("") && phoneNumber.length() > 0) {
            phoneNumber = PhoneNumber.convertToNumber(phoneNumber);
            if (phoneNumber.startsWith("1")) {
              phoneNumber = phoneNumber.substring(1);
            }
            if (phoneNumber.startsWith("757")) {
              phoneNumber = phoneNumber.substring(3);
            } else {
              phoneNumber = "1" + phoneNumber;
            }
            System.out.println("Notification-> Will send fax to: " + phoneNumber);
            faxLogEntry = databaseName + "|" + messageIdToSend + "|" + phoneNumber + "|" + thisContact.getId();
            status = "Fax Queued";
          }
        } else if (type == LETTER) {
          contact = thisContact;
          status = "Added to Report";
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
   *  Description of the Method
   *
   *@exception  Exception  Description of the Exception
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
          thisMessage.setKeystoreLocation((String) ((ActionContext) context).getServletContext().getAttribute("ClientSSLKeystore"));
          thisMessage.setKeystorePassword((String) ((ActionContext) context).getServletContext().getAttribute("ClientSSLKeystorePassword"));
        } else if (context instanceof ComponentContext) {
          thisMessage.setKeystoreLocation((String) ((ComponentContext) context).getAttribute("ClientSSLKeystore"));
          thisMessage.setKeystorePassword((String) ((ComponentContext) context).getAttribute("ClientSSLKeystorePassword"));
        }
      }
      result = thisMessage.send();
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Notification->SEND RESULT: " + result);
      }
    }
  }


  /**
   *  Description of the Method
   */
  public void send() {
    this.start();
  }


  /**
   *  Description of the Method
   *
   *@param  db  Description of the Parameter
   */
  public void send(Connection db) {
    this.connection = db;
    this.start();
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   */
  public void send(ActionContext context) {
    this.context = context;
    this.start();
  }


  /**
   *  Main processing method for the Notification object
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
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public String toString() {

    StringBuffer text = new StringBuffer();
    text.append("==[ NOTIFICATION ]============================" + lf);
    text.append("User to notify: " + userToNotify + lf);
    text.append("Module: " + module + lf);
    text.append("Item Id: " + itemId + lf);
    text.append("Item Modified: " + itemModified + lf);
    text.append("Subject: " + subject + lf);
    text.append("From: " + from + lf);
    text.append("Message: " + messageToSend + lf);
    text.append("Type: " + type + lf);
    text.append("==============================================");
    return text.toString();
  }
}

