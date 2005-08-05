package com.zeroio.webdav.context;

import com.zeroio.webdav.utils.ICalendar;
import org.apache.naming.resources.Resource;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.mycfs.beans.CalendarBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;
import java.util.TimeZone;

/**
 * Description of the Class
 *
 * @author ananth
 * @created December 1, 2004
 */
public class CalendarContext
    extends BaseWebdavContext implements ModuleContext {

  private User user = null;
  private CalendarBean calendarInfo = new CalendarBean();
  private boolean excludeDisabledIfUnselected = false;

  /**
   * Sets the calendarInfo attribute of the CalendarContext object
   *
   * @param tmp The new calendarInfo value
   */
  public void setCalendarInfo(CalendarBean tmp) {
    this.calendarInfo = tmp;
  }


  /**
   * Gets the calendarInfo attribute of the CalendarContext object
   *
   * @return The calendarInfo value
   */
  public CalendarBean getCalendarInfo() {
    return calendarInfo;
  }


  /**
   * Sets the user attribute of the CalendarContext object
   *
   * @param tmp The new user value
   */
  public void setUser(User tmp) {
    this.user = tmp;
  }


  /**
   * Gets the user attribute of the CalendarContext object
   *
   * @return The user value
   */
  public User getUser() {
    return user;
  }


  /**
   * Constructor for the CalendarContext object
   *
   * @param name Description of the Parameter
   */
  public CalendarContext(String name) {
    this.contextName = name;
  }


  /**
   * Description of the Method
   *
   * @param db              Description of the Parameter
   * @param userId          Description of the Parameter
   * @param thisSystem      Description of the Parameter
   * @param fileLibraryPath Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildResources(SystemStatus thisSystem, Connection db, int userId, String fileLibraryPath) throws SQLException {
    //fileLibraryPath is not needed by the CalendarContext. 
    bindings.clear();
    //Retrieve the user from user cache
    if (thisSystem != null) {
      user = thisSystem.getUser(userId);
      user.setLanguage(
          thisSystem.getApplicationPrefs().get("SYSTEM.LANGUAGE"));
      user.setCurrency(
          thisSystem.getApplicationPrefs().get("SYSTEM.CURRENCY"));
      populateBindings(db, thisSystem);
    }
  }


  /**
   * Description of the Method
   *
   * @param db         Description of the Parameter
   * @param thisSystem Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void populateBindings(Connection db, SystemStatus thisSystem) throws SQLException {
    UserList shortChildList = user.getShortChildList();
    UserList newUserList = user.getFullChildList(
        shortChildList, new UserList());
    newUserList = UserList.sortEnabledUsers(newUserList, new UserList());
    //Include the owner
    newUserList.add(user);
    Iterator i = newUserList.iterator();
    while (i.hasNext()) {
      User thisUser = (User) i.next();
      //Generate the file name
      Timestamp currentTime = new Timestamp(
          Calendar.getInstance().getTimeInMillis());
      String name = thisUser.getContact().getNameFirstLast();
      //Display disabled users
      if (!thisUser.getEnabled() || (thisUser.getExpires() != null && currentTime.after(
          thisUser.getExpires()))) {
        name += " *";
      }
      name += ".ics";
      //populate the calendar info
      //??
      if (thisUser.getEnabled() || (!thisUser.getEnabled() && !excludeDisabledIfUnselected)) {
        buildUserAlerts(thisSystem, thisUser);
        //populate the calendar for this user
        ICalendar ical = new ICalendar(
            db, thisUser, calendarInfo, user.getId());
        Resource resource = new Resource(ical.getBytes());
        bindings.put(name, resource);
        buildProperties(
            name, ical.getCreated(), ical.getCreated(), new Integer(
                ical.getBytes().length));
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param thisSystem Description of the Parameter
   * @param thisUser   Description of the Parameter
   */
  private void buildUserAlerts(SystemStatus thisSystem, User thisUser) {
    calendarInfo.setTimeZone(TimeZone.getTimeZone(thisUser.getTimeZone()));
    calendarInfo.getAlertTypes().clear();
    //Tasks
    if (hasPermission(thisSystem, thisUser.getId(), "myhomepage-tasks-view")) {
      calendarInfo.addAlertType(
          "Task", "org.aspcfs.modules.tasks.base.TaskListScheduledActions", thisSystem.getLabel(
              "calendar.Tasks"));
    }
    //Activities
    if (hasPermission(
        thisSystem, thisUser.getId(), "contacts-external_contacts-calls-view") || hasPermission(
            thisSystem, thisUser.getId(), "accounts-accounts-contacts-calls-view")) {
      calendarInfo.addAlertType(
          "Call", "org.aspcfs.modules.contacts.base.CallListScheduledActions", thisSystem.getLabel(
              "calendar.Activities"));
    }
    //Project Alerts
    if (hasPermission(thisSystem, thisUser.getId(), "projects-projects-view")) {
      calendarInfo.addAlertType(
          "Project", "com.zeroio.iteam.base.ProjectListScheduledActions", thisSystem.getLabel(
              "calendar.Projects"));
    }
    //Accounts Alerts
    if (hasPermission(thisSystem, thisUser.getId(), "accounts-accounts-view")) {
      calendarInfo.addAlertType(
          "Accounts", "org.aspcfs.modules.accounts.base.AccountsListScheduledActions", thisSystem.getLabel(
              "calendar.Accounts"));
    }
    //Opportunity Alerts
    if (hasPermission(
        thisSystem, thisUser.getId(), "contacts-external_contacts-opportunities-view") || hasPermission(
            thisSystem, thisUser.getId(), "pipeline-opportunities-view")) {
      calendarInfo.addAlertType(
          "Opportunity", "org.aspcfs.modules.pipeline.base.OpportunityListScheduledActions", thisSystem.getLabel(
              "calendar.Opportunities"));
    }
    //Help Desk Alerts
    if (hasPermission(thisSystem, thisUser.getId(), "products-view") || hasPermission(
        thisSystem, thisUser.getId(), "tickets-tickets-view")) {
      calendarInfo.addAlertType(
          "Ticket", "org.aspcfs.modules.troubletickets.base.TicketListScheduledActions", thisSystem.getLabel(
              "calendar.Tickets"));
    }
    //Project Tickets
    if (hasPermission(thisSystem, thisUser.getId(), "projects-projects-view")) {
      calendarInfo.addAlertType(
          "Project Ticket", "org.aspcfs.modules.troubletickets.base.ProjectTicketListScheduledActions", thisSystem.getLabel(
              "calendar.projectTickets"));
    }
  }
}

