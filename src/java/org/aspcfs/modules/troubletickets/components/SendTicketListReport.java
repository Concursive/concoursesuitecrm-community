//Copyright 2003 Dark Horse Ventures

package org.aspcfs.modules.troubletickets.components;

import org.aspcfs.controller.*;
import org.aspcfs.apps.workFlowManager.*;
import org.aspcfs.controller.objectHookManager.*;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.modules.base.Notification;
import org.aspcfs.modules.troubletickets.base.*;
import java.sql.*;
import org.aspcfs.modules.components.SendUserNotification;
import java.util.*;

public class SendTicketListReport extends ObjectHookComponent implements ComponentInterface {
  public final static String REPORT_TICKET_CONTENT = "report.ticket.content";
  public final static String EMAIL_TO = "notification.to";
  public final static String USER_TO = "notification.users.to";
  public final static String CONTACT_TO = "notification.contacts.to";
  public final static String DEPARTMENT_TO = "notification.departments.to";
  
  public String getDescription() {
    return "Sends a ticket report to specified users with the specified parameters";
  }

  public boolean execute(ComponentContext context) {
    if (!context.hasObjects()) {
      return false;
    }
    boolean result = false;
    Connection db = null;
    HashMap mailList = new HashMap();
    TicketList tickets = (TicketList) context.getObjects();
    Iterator i = tickets.iterator();
    try {
      db = this.getConnection(context);
      String emailTo = context.getParameter(EMAIL_TO);
      mailList.put(emailTo, new ArrayList());
      //Build message HashMap so that recipient will get a single email
      while (i.hasNext()) {
        Ticket thisTicket = (Ticket) i.next();
        System.out.println("Adding ticket: " + thisTicket.getId());
        //Determine who notification is for: variable user ids, contact ids, department ids
        String userTo = context.getParameter(USER_TO, thisTicket, null);
        String contactTo = context.getParameter(CONTACT_TO, thisTicket, null);
        String departmentTo = context.getParameter(DEPARTMENT_TO, thisTicket, null);
        //Determine the content of the message to send
        String thisMessage = context.getParameter(REPORT_TICKET_CONTENT, thisTicket, null);
        System.out.println(thisMessage);
        //Get a reference to the person, creating one if new
        ArrayList emailToList = (ArrayList) mailList.get(emailTo);
        emailToList.add(thisMessage);
        
        //Users: Lookup each user id
        
        //Contacts: Lookup each contact id
        
        //Departments: Get list of enabled users in the specified department, lookup each user id
        
        //Emails
        
        
        
        
      }
      
      //Process the HashMap and send emails
      Iterator toList = mailList.keySet().iterator();
      while (toList.hasNext()) {
        //Either
        //Set context parameters and execute component from cache
        //context.execute("org.aspcfs.modules.components.SendUserNotification");
        
        //Or
        //Create a new notification directly, configure, and execute
        StringBuffer messageDigest = new StringBuffer();
        String emailAddressTo = (String) toList.next();
        ArrayList emailToList = (ArrayList) mailList.get(emailAddressTo);
        Iterator messages = emailToList.iterator();
        while (messages.hasNext()) {
          messageDigest.append((String) messages.next());
        }
        Notification thisNotification = new Notification();
        thisNotification.setSubject(StringUtils.toHtml(context.getParameter(SendUserNotification.SUBJECT)));
        thisNotification.setFrom(StringUtils.toHtml(context.getParameter(SendUserNotification.FROM)));
        thisNotification.setType(Notification.EMAIL);
        thisNotification.setEmailToNotify(emailAddressTo);
        thisNotification.setMessageToSend(StringUtils.toHtml(context.getParameter(SendUserNotification.BODY) + messageDigest.toString()));
        String host = context.getParameter(SendUserNotification.HOST);
        if (host != null && !"".equals(host)) {
          thisNotification.setHost(host);
        }
        thisNotification.notifyAddress();
/*
        if int {
          thisNotification.setUserToNotify();
          thisNotification.notifyUser(db);
        } else {
          thisNotification.setTo();
          thisNotification.notify(db);
        }
*/
      }
      result = true;
    } catch (Exception e) {
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return result;
  }
}

