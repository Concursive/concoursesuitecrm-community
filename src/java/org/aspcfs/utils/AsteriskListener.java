package org.aspcfs.utils;

import com.darkhorseventures.database.ConnectionPool;
import net.sf.asterisk.manager.ManagerConnection;
import net.sf.asterisk.manager.ManagerEventHandler;
import net.sf.asterisk.manager.event.DialEvent;
import net.sf.asterisk.manager.event.ManagerEvent;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.PhoneNumber;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactInstantMessageAddressList;
import org.aspcfs.modules.contacts.base.ContactPhoneNumber;
import org.aspcfs.modules.contacts.base.ContactPhoneNumberList;
import org.aspcfs.utils.formatter.PhoneNumberFormatter;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Presence;

import javax.servlet.ServletContext;
import java.sql.Connection;

/**
 * Description
 *
 * @author mrajkowski
 * @version $Id$
 * @created Nov 8, 2005
 */

public class AsteriskListener extends Thread implements ManagerEventHandler {

  private ManagerConnection managerConnection = null;
  private ServletContext servletContext = null;
  private SystemStatus systemStatus = null;
  private ApplicationPrefs applicationPrefs = null;
  private boolean shouldRun = true;

  public AsteriskListener() {
  }

  public void setManagerConnection(ManagerConnection managerConnection) {
    this.managerConnection = managerConnection;
  }

  public void setServletContext(ServletContext servletContext) {
    this.servletContext = servletContext;
  }

  public void setSystemStatus(SystemStatus systemStatus) {
    this.systemStatus = systemStatus;
  }

  public void setApplicationPrefs(ApplicationPrefs applicationPrefs) {
    this.applicationPrefs = applicationPrefs;
  }

  public void setShouldRun(boolean shouldRun) {
    this.shouldRun = shouldRun;
  }

  public void run() {
    System.out.println("AsteriskListener-> Registering events");
    // Ready to start listening
    managerConnection.addEventHandler(this);
    try {
      while (shouldRun) {
        Thread.sleep(1000);
      }
    } catch (Exception e) {
      System.out.println("AsteriskListener-> " + e.getMessage());
      shouldRun = false;
    }
    shouldRun = false;
    managerConnection.removeEventHandler(this);
    System.out.println("AsteriskListener-> Exiting");
  }

  public void handleEvent(ManagerEvent event) {
    if (!shouldRun) {
      return;
    }
    String eventClass = event.getClass().getName();
    // Inbound call
    if ("net.sf.asterisk.manager.event.DialEvent".equals(eventClass)) {
      if (!"true".equals(applicationPrefs.get("XMPP.ENABLED"))) {
        return;
      }
      DialEvent thisEvent = (DialEvent) event;
      String channel = thisEvent.getDestination();
      String callerId = thisEvent.getCallerId();
      String extension = "";
      if (channel == null || callerId == null) {
        return;
      }
      if (channel.length() > 4) {
        extension = channel.substring(4);
        if (extension.indexOf("-") > -1) {
          extension = extension.substring(0, extension.lastIndexOf("-"));
        }
      }

      if (extension.equals("")) {
        return;
      }

      if (System.getProperty("DEBUG") != null) {
        System.out.println("AsteriskListener-> " + eventClass);
        System.out.println("Caller Id: " + thisEvent.getCallerId());
        System.out.println("Caller Id Name: " + thisEvent.getCallerIdName());
        System.out.println("Channel: " + channel);
        System.out.println("Extension: " + extension);
      }

      // Get database connection
      ConnectionPool cp = (ConnectionPool) servletContext.getAttribute("ConnectionPool");
      Connection db = null;
      boolean connected = true;
      try {
        db = cp.getConnection(systemStatus.getConnectionElement());

        // Lookup the extension and user info
        ContactPhoneNumberList userPhoneList = new ContactPhoneNumberList();
        userPhoneList.setExtension(extension);
        userPhoneList.setUsersOnly(true);
        userPhoneList.buildList(db);
        if (userPhoneList.size() != 1) {
          return;
        }
        Contact user = new Contact(db, ((ContactPhoneNumber) userPhoneList.get(0)).getContactId());
        if (System.getProperty("DEBUG") != null) {
          System.out.println("AsteriskListener-> User found: " + user.getNameFirstLast());
        }
        ContactInstantMessageAddressList userIMList = new ContactInstantMessageAddressList();
        userIMList.setContactId(user.getId());
        userIMList.setServiceName("Jabber");
        userIMList.buildList(db);
        if (userIMList.size() == 0) {
          return;
        }
        String imAddress = userIMList.getPrimaryInstantMessageAddress();
        if (System.getProperty("DEBUG") != null) {
          System.out.println("AsteriskListener-> IM TO: " + imAddress);
        }
        // Lookup the caller id
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setNumber(callerId);
        PhoneNumberFormatter.format(phoneNumber);
        if (System.getProperty("DEBUG") != null) {
          System.out.println("AsteriskListener-> Looking up: " + phoneNumber.getNumber());
        }

        ContactPhoneNumberList phoneList = new ContactPhoneNumberList();
        phoneList.setNumber(phoneNumber.getNumber());
        phoneList.buildList(db);
        if (phoneList.size() == 0) {
          return;
        }
        Contact contact = new Contact(db, ((ContactPhoneNumber) phoneList.get(0)).getContactId());
        if (System.getProperty("DEBUG") != null) {
          System.out.println("AsteriskListener-> Contact: " + contact.getNameFirstLast());
        }

        // Send an XMPP message if extension and caller id found
        // Incoming Call:
        //   Caller Id:
        //   Account Name:
        //   Contact Name:
        //   View this account in Concourse Suite Community Edition
        //   View this contact in Concourse Suite Community Edition

        cp.free(db);
        connected = false;

        // Talk through Jabber
        XMPPConnection xmpp = XMPPManager.verifyConnection(systemStatus, applicationPrefs);
        if (xmpp == null) {
          if (System.getProperty("DEBUG") != null) {
            System.out.println("AsteriskListener-> No Connection");
          }
          return;
        }
        // See if user is online before sending message
        Roster roster = xmpp.getRoster();
        RosterEntry entry = roster.getEntry(imAddress);
        if (entry == null) {
          //Subscribe
          roster.createEntry(imAddress, String.valueOf(user.getId()), null);
        }
        Presence presence = roster.getPresence(imAddress);
        if (presence == null) {
          roster.createEntry(imAddress, String.valueOf(user.getId()), null);
        }
        presence = roster.getPresence(imAddress);
        if (presence == null) {
          // This is an indication the user is offline, but this appears to be broken
          // return
        }
        if (presence != null && presence.getType() != Presence.Type.AVAILABLE) {
          // User is not available so avoid the popup for now
          return;
        }
        // Construct the message
        StringBuffer message = new StringBuffer();
        message.append("Incoming call from... " + "\r\n");
        message.append("\r\n");
        message.append("Contact: " + contact.getNameFirstLast() + "\r\n");
        if (contact.getOrgName() != null && !"".equals(contact.getOrgName())) {
          message.append("Organization: " + contact.getOrgName() + "\r\n");
        }
        message.append("Phone Number: " + phoneNumber.getNumber() + "\r\n");
        if (contact.getOrgId() > 0) {
          message.append("\r\n");
          message.append("View this Account in Concourse Suite Community Edition" + "\r\n");
          message.append(systemStatus.getUrl() + "/Accounts.do?command=Details&orgId=" + contact.getOrgId() + "\r\n");
        }
        message.append("\r\n");
        message.append("View this Contact in Concourse Suite Community Edition" + "\r\n");
        message.append(systemStatus.getUrl() + "/ExternalContacts.do?command=ContactDetails&id=" + contact.getId() + "\r\n");
        // Send the message
        xmpp.createChat(imAddress).sendMessage(message.toString());
      } catch (Exception e) {
        System.out.println("AsteriskListener Exception: " + e.getMessage());
      } finally {
        if (connected) {
          cp.free(db);
        }
      }
    }
  }

}
