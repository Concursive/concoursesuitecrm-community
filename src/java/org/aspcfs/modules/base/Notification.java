package com.darkhorseventures.cfsbase;

import java.sql.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;

/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    October 15, 2001
 *@version    $Id$
 */
public class Notification extends Thread {

  public static final int EMAIL = 1;
  public static final int FAX = 2;
  public static final int LETTER = 3;
  public static final int EMAILFAX = 4;
  public static final int EMAILLETTER = 5;
  public static final int EMAILFAXLETTER = 6;
  public static final int IM = 7;
  public static final int SSL = 8;

  public static final String EMAIL_TEXT = "Email";
  public static final String FAX_TEXT = "Fax";
  public static final String LETTER_TEXT = "Letter";
  public static final String EMAILFAX_TEXT = "Email first, try Fax";
  public static final String EMAILLETTER_TEXT = "Email first, then Letter";
  public static final String EMAILFAXLETTER_TEXT = "Email first, try Fax, then Letter";
  public static final String IM_TEXT = "Instant Message";
  public static final String SSL_TEXT = "SSL Message";
  String faxLogEntry = null;
  Contact contact = null;

  private int id = -1;
  private String databaseName = null;
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
  private ActionContext context = null;

  /**
   *  Constructor for the Notification object
   *
   *@since
   */
  public Notification() { 
    if (System.getProperty("MailServer") != null) {
      host = (String)System.getProperty("MailServer");
    }
  }
  
  public Notification(int thisType) {
    this.setType(thisType);
  }
  
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
  
  public void setPort(int tmp) {
    this.port = tmp;
  }
  
  public void setHost(String tmp) {
    this.host = tmp;
  }


  /**
   *  Gets the Id attribute of the Notification object
   *
   *@return    The Id value
   *@since
   */
  public int getId() {
    return id;
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
        thisUser.buildRecord(db, userToNotify);
        if (type == EMAIL) {
          System.out.println("Notification-> To: " + thisUser.getContact().getEmailAddress("Business"));
          SMTPMessage mail = new SMTPMessage();
          mail.setHost(host);
          mail.setFrom(from + " <cfs-messenger@darkhorseventures.com>");
          if (from != null) {
            mail.addReplyTo(from);
          } else {
            mail.addReplyTo(siteCode + "@" + this.getHostName());
          }
          
          mail.setType("text/html");
          mail.addTo(thisUser.getContact().getEmailAddress("Business"));
          mail.setSubject(subject);
          mail.setBody(messageToSend);
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
        Contact thisContact = new Contact(db, "" + contactToNotify);
        //Determine the method...
        if (type == EMAILFAXLETTER) {
          if (thisContact.getEmailAddress("Business").equals("") &&
              thisContact.getPhoneNumber("Demo Fax").equals("") &&
              thisContact.getAddress("Business") != null) {
            type = LETTER;
          } else {
            type = EMAILFAX;
          }
        }
        //2nd level
        if (type == EMAILFAX) {
          if (thisContact.getEmailAddress("Business").equals("") &&
              !thisContact.getPhoneNumber("Demo Fax").equals("")) {
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
          if (from != null && !from.equals("")) {
            mail.setFrom(from);
          } else {
            mail.setFrom(siteCode + "@" + this.getHostName());
          }
          mail.setType("text/html");

          if (thisContact.getEmailAddress("Business").equals("")) {
            mail.addTo(thisContact.getEmailAddress("Personal"));
          } else {
            mail.addTo(thisContact.getEmailAddress("Business"));
          }
          mail.setSubject(subject);
          mail.setBody(messageToSend);
          int errorCode = mail.send();
          if (errorCode > 0) {
            status = "Email Error";
            System.out.println("Send error: " + mail.getErrorMsg() + "<br><br>");
            System.err.println("ReportBuilder Error: Report could not be sent");
            System.err.println(mail.getErrorMsg());
          } else {
            status = "Email Sent";
            insertNotification(db);
          }
        } else if (type == IM) {

          status = "IM Sent";
        } else if (type == FAX) {
          System.out.println("Notification-> To: " + thisContact.getPhoneNumber("Demo Fax"));
          String phoneNumber = thisContact.getPhoneNumber("Demo Fax");
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
            faxLogEntry = databaseName + "|" + messageIdToSend + "|" + phoneNumber;
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
      }
    } else {
      System.out.println("Notification-> Type not set");
    }
  }
  
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
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Notification-> Setting Keystore");
        }
        thisMessage.setKeystoreLocation((String)context.getServletContext().getAttribute("ClientSSLKeystore"));
        thisMessage.setKeystorePassword((String)context.getServletContext().getAttribute("ClientSSLKeystorePassword"));
      }
      result = thisMessage.send();
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Notification->SEND RESULT: " + result);
      }
    }
  }

  public void send()  {
    this.start();
  }
  
  public void send(Connection db)  {
    this.connection = db;
    this.start();
  }
  
  public void send(ActionContext context) {
    this.context = context;
    this.start();
  }
  
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
   *  Gets the HostName attribute of the Notification object
   *
   *@return    The HostName value
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

}

