package com.darkhorseventures.cfsbase;

import java.sql.*;
import com.darkhorseventures.utils.*;
import java.net.*;

/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    October 15, 2001
 *@version    $Id$
 */
public class Notification {

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
  private String type = null;
  private String siteCode = null;
  private java.sql.Timestamp attempt = null;
  private int result = 0;
  private String status = "";
  private String errorMessage = null;
  String faxLogEntry = null;

  public final static String EMAIL = "Email";
  public final static String EMAILFAX = "Email first, try Fax";
  public final static String IM = "Instant Message";
  public final static String FAX = "Fax";


  /**
   *  Constructor for the Notification object
   *
   *@since
   */
  public Notification() { }


  /**
   *  Sets the UserToNotify attribute of the Notification object
   *
   *@param  tmp  The new UserToNotify value
   *@since
   */
  public void setUserToNotify(int tmp) {
    this.userToNotify = tmp;
  }
  
  public void setContactToNotify(int tmp) { this.contactToNotify = tmp; }

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
   *  Sets the Type attribute of the Notification object
   *
   *@param  tmp  The new Type value
   *@since
   */
  public void setType(String tmp) {
    this.type = tmp;
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

  public String getFaxLogEntry() {
    return faxLogEntry;
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
          "WHERE notify_user = " + ((userToNotify > -1)?userToNotify:contactToNotify) + " " +
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
      pst.setString(++i, type);
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
    if (type != null) {
      try {
        User thisUser = new User();
        thisUser.setBuildContact(true);
        thisUser.buildRecord(db, userToNotify);
        if (type.equals(EMAIL)) {
          System.out.println("Notification-> To: " + thisUser.getContact().getEmailAddress("Business"));
          SMTPMessage mail = new SMTPMessage();
          mail.setHost("127.0.0.1");
          if (from != null) {
            mail.setFrom(from);
          } else {
            mail.setFrom(siteCode + "@" + this.getHostName());
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
        } else if (type.equals(IM)) {


        } else if (type.equals(FAX)) {


        }
      } catch (Exception e) {
        result = 2;
        errorMessage = e.toString();
      }
    } else {
      System.out.println("Notification-> Type not set");
    }
  }
  
  public void notifyContact(Connection db) {
    if (type != null) {
      try {
        Contact thisContact = new Contact(db, "" + contactToNotify);
        if (type.equals(EMAILFAX)) {
          if (thisContact.getEmailAddress("Business").equals("") &&
              !thisContact.getPhoneNumber("Demo Fax").equals("")) {
            type = FAX;
          } else {
            type = EMAIL;
          }
        }
        if (type.equals(EMAIL)) {
          System.out.println("Notification-> To: " + thisContact.getEmailAddress("Business"));
          SMTPMessage mail = new SMTPMessage();
          mail.setHost("127.0.0.1");
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
        } else if (type.equals(IM)) {

          status = "IM Sent";
        } else if (type.equals(FAX)) {
					System.out.println("Notification-> To: " + thisContact.getPhoneNumber("Demo Fax"));
          String phoneNumber = thisContact.getPhoneNumber("Demo Fax");
          if (!phoneNumber.equals("") && phoneNumber.length() > 0) {
            if (phoneNumber.startsWith("757")) {
              phoneNumber = phoneNumber.substring(3);
            } else {
              phoneNumber = "1" + phoneNumber;
            }
            System.out.println("Notification-> Will send fax to: " + phoneNumber);
            faxLogEntry = databaseName + "|" + messageIdToSend + "|" + phoneNumber;
            status = "Fax Queued";
          }
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

