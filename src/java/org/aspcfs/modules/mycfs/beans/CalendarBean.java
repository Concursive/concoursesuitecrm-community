package org.aspcfs.modules.mycfs.beans;

import java.util.*;
import java.text.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.ActionContext;
import java.sql.*;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.mycfs.base.AlertType;

/**
 *  CalendarBean maintains all the users setting on his home page's calendar
 *  view like date,alerts etc so his view is maintained whenever he returns to
 *  home page in that session.
 *
 *@author     akhi_m
 *@created    September 9, 2002
 *@version    $Id$
 */
public class CalendarBean {

  private String calendarDetailsView = "all";
  private String calendarView = "";
  Calendar cal = Calendar.getInstance();
  private int primaryMonth = cal.get(Calendar.MONTH) + 1;
  private int monthSelected = cal.get(Calendar.MONTH) + 1;
  private int primaryYear = cal.get(Calendar.YEAR);
  private int yearSelected = cal.get(Calendar.YEAR);
  private int daySelected = -1;
  private int startMonthOfWeek = -1;
  private int startDayOfWeek = -1;
  private int selectedUserId = -1;
  private String selectedUserName = "";
  private boolean agendaView = true;
  private ArrayList alertTypes = new ArrayList();
  private TimeZone timeZone = null;



  /**
   *  Description of the Method
   */
  public void CalendarBean() { }


  /**
   *  Sets the view attribute of the CalendarBean object
   *
   *@param  calendarDetailsView  The new calendarDetailsView value
   */
  public void setCalendarDetailsView(String calendarDetailsView) {
    this.calendarDetailsView = calendarDetailsView;
  }


  /**
   *  Sets the daySelected attribute of the CalendarBean object
   *
   *@param  daySelected  The new daySelected value
   */
  public void setDaySelected(int daySelected) {
    this.daySelected = daySelected;
  }


  /**
   *  Sets the calendarView attribute of the CalendarBean object
   *
   *@param  calendarView  The new calendarView value
   */
  public void setCalendarView(String calendarView) {
    this.calendarView = calendarView;
  }


  /**
   *  Sets the selectedUserId attribute of the CalendarBean object
   *
   *@param  selectedUserId  The new selectedUserId value
   */
  public void setSelectedUserId(int selectedUserId) {
    this.selectedUserId = selectedUserId;
  }


  /**
   *  Sets the selectedUserName attribute of the CalendarBean object
   *
   *@param  selectedUserName  The new selectedUserName value
   */
  public void setSelectedUserName(String selectedUserName) {
    this.selectedUserName = selectedUserName;
  }


  /**
   *  Sets the primaryYear attribute of the CalendarBean object
   *
   *@param  primaryYear  The new primaryYear value
   */
  public void setPrimaryYear(int primaryYear) {
    this.primaryYear = primaryYear;
  }


  /**
   *  Sets the timeZone attribute of the CalendarBean object
   *
   *@param  timeZone  The new timeZone value
   */
  public void setTimeZone(TimeZone timeZone) {
    this.timeZone = timeZone;
  }


  /**
   *  Gets the timeZone attribute of the CalendarBean object
   *
   *@return    The timeZone value
   */
  public TimeZone getTimeZone() {
    return timeZone;
  }


  /**
   *  Gets the primaryYear attribute of the CalendarBean object
   *
   *@return    The primaryYear value
   */
  public int getPrimaryYear() {
    return primaryYear;
  }


  /**
   *  Sets the monthSelected attribute of the CalendarBean object
   *
   *@param  monthSelected  The new monthSelected value
   */
  public void setMonthSelected(int monthSelected) {
    this.monthSelected = monthSelected;
  }


  /**
   *  Sets the yearSelected attribute of the CalendarBean object
   *
   *@param  yearSelected  The new yearSelected value
   */
  public void setYearSelected(int yearSelected) {
    this.yearSelected = yearSelected;
  }



  /**
   *  Sets the startMonthOfWeek attribute of the CalendarBean object
   *
   *@param  startMonthOfWeek  The new startMonthOfWeek value
   */
  public void setStartMonthOfWeek(int startMonthOfWeek) {
    this.startMonthOfWeek = startMonthOfWeek;
  }


  /**
   *  Sets the startDayOfWeek attribute of the CalendarBean object
   *
   *@param  startDayOfWeek  The new startDayOfWeek value
   */
  public void setStartDayOfWeek(int startDayOfWeek) {
    this.startDayOfWeek = startDayOfWeek;
  }


  /**
   *  Sets the agendaView attribute of the CalendarBean object
   *
   *@param  agendaView  The new agendaView value
   */
  public void setAgendaView(boolean agendaView) {
    this.agendaView = agendaView;
  }


  /**
   *  Sets the selectedUserName attribute of the CalendarBean object
   *
   *@param  db                The new selectedUserName value
   *@exception  SQLException  Description of the Exception
   */
  public void setSelectedUserName(Connection db) throws SQLException {
    Exception errorMessage = null;
    try {
      int thisContactId = (new User(db, selectedUserId)).getContactId();
      Contact thisContact = new Contact(db, thisContactId);
      this.setSelectedUserName(thisContact.getNameLastFirst());
    } catch (SQLException e) {
      errorMessage = e;
      System.out.println(" *** ERROR *** \t CalendarBean -- > Error retrieving User Record");
    }
  }



  /**
   *  Gets the selectedUserName attribute of the CalendarBean object
   *
   *@return    The selectedUserName value
   */
  public String getSelectedUserName() {
    return selectedUserName;
  }



  /**
   *  Sets the alertTypes attribute of the CalendarBean object
   *
   *@param  alertTypes  The new alertTypes value
   */
  public void setAlertTypes(ArrayList alertTypes) {
    this.alertTypes = alertTypes;
  }


  /**
   *  Sets the primaryMonth attribute of the CalendarBean object
   *
   *@param  primaryMonth  The new primaryMonth value
   */
  public void setPrimaryMonth(int primaryMonth) {
    this.primaryMonth = primaryMonth;
  }


  /**
   *  Gets the primaryMonth attribute of the CalendarBean object
   *
   *@return    The primaryMonth value
   */
  public int getPrimaryMonth() {
    return primaryMonth;
  }


  /**
   *  Adds a feature to the AlertType attribute of the CalendarBean object
   *
   *@param  alert        The feature to be added to the AlertType attribute
   *@param  className    The feature to be added to the AlertType attribute
   *@param  displayName  The feature to be added to the AlertType attribute
   */
  public void addAlertType(String alert, String className, String displayName) {
    this.alertTypes.add(new AlertType(alert, className, displayName));
  }


  /**
   *  Gets the alertTypes attribute of the CalendarBean object
   *
   *@return    The alertTypes value
   */
  public ArrayList getAlertTypes() {
    return alertTypes;
  }



  /**
   *  Gets the agendaView attribute of the CalendarBean object
   *
   *@return    The agendaView value
   */
  public boolean isAgendaView() {
    return agendaView;
  }


  /**
   *  Gets the startMonthOfWeek attribute of the CalendarBean object
   *
   *@return    The startMonthOfWeek value
   */
  public int getStartMonthOfWeek() {
    return startMonthOfWeek;
  }


  /**
   *  Gets the startDayOfWeek attribute of the CalendarBean object
   *
   *@return    The startDayOfWeek value
   */
  public int getStartDayOfWeek() {
    return startDayOfWeek;
  }


  /**
   *  Gets the monthSelected attribute of the CalendarBean object
   *
   *@return    The monthSelected value
   */
  public int getMonthSelected() {
    return monthSelected;
  }


  /**
   *  Gets the yearSelected attribute of the CalendarBean object
   *
   *@return    The yearSelected value
   */
  public int getYearSelected() {
    return yearSelected;
  }


  /**
   *  Gets the calendarView attribute of the CalendarBean object
   *
   *@return    The calendarView value
   */
  public String getCalendarView() {
    return calendarView;
  }


  /**
   *  Gets the userId attribute of the CalendarBean object
   *
   *@return    The userId value
   */
  public int getSelectedUserId() {
    return selectedUserId;
  }


  /**
   *  Gets the daySelected attribute of the CalendarBean object
   *
   *@return    The daySelected value
   */
  public int getDaySelected() {
    return daySelected;
  }



  /**
   *  Gets the calendarDetailsView attribute of the CalendarBean object
   *
   *@return    The calendarDetailsView value
   */
  public String getCalendarDetailsView() {
    return calendarDetailsView;
  }


  /**
   *  Gets the startOfWeekDate attribute of the CalendarBean object
   *
   *@return    The startOfWeekDate value
   */
  public java.util.Date getStartOfWeekDate() {
    Calendar thisCal = cal.getInstance();
    thisCal.set(yearSelected, startMonthOfWeek - 1, startDayOfWeek);
    return thisCal.getTime();
  }


  /**
   *  Gets the endOfWeekDate attribute of the CalendarBean object
   *
   *@return    The endOfWeekDate value
   */
  public java.util.Date getEndOfWeekDate() {
    Calendar thisCal = cal.getInstance();
    thisCal.set(yearSelected, startMonthOfWeek - 1, startDayOfWeek);
    thisCal.add(java.util.Calendar.DATE, +6);
    return thisCal.getTime();
  }


  /**
   *  Description of the Method
   *
   *@param  context           Description of the Parameter
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void update(Connection db, ActionContext context) throws SQLException {
    HttpServletRequest request = context.getRequest();

    //set the timezone if it is different from the server timezone
    UserBean thisUser = (UserBean) request.getSession().getAttribute("User");
    String tZone = thisUser.getUserRecord().getTimeZone();
    
      if (tZone != null && (timeZone == null || (timeZone != null && !timeZone.hasSameRules(TimeZone.getTimeZone(tZone))))) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("CalendarBean -- > Setting timezone to " + tZone);
        }
        setTimeZone(TimeZone.getTimeZone(tZone));
        cal.setTimeZone(timeZone);
        if(timeZone == null){
          primaryMonth = cal.get(Calendar.MONTH) + 1;
          primaryYear = cal.get(Calendar.YEAR);
          monthSelected = cal.get(Calendar.MONTH) + 1;
          yearSelected = cal.get(Calendar.YEAR);
        }
      }

    if (request.getParameter("primaryMonth") != null) {
      setPrimaryMonth(Integer.parseInt(request.getParameter("primaryMonth")));
    }

    if (request.getParameter("primaryYear") != null) {
      setPrimaryYear(Integer.parseInt(request.getParameter("primaryYear")));
    }

    if (request.getParameter("alertsView") != null) {
      setCalendarDetailsView(request.getParameter("alertsView"));
    }

    if (request.getParameter("userId") != null) {
      setSelectedUserId(Integer.parseInt(request.getParameter("userId")));
      setSelectedUserName(db);
    }

    if (request.getParameter("day") != null) {
      setDaySelected(Integer.parseInt(request.getParameter("day")));
    }

    if (request.getParameter("month") != null) {
      monthSelected = Integer.parseInt(request.getParameter("month"));
    }

    if (request.getParameter("year") != null) {
      yearSelected = Integer.parseInt(request.getParameter("year"));
    }

    if (request.getParameter("startMonthOfWeek") != null) {
      startMonthOfWeek = Integer.parseInt(request.getParameter("startMonthOfWeek"));
    }

    if (request.getParameter("startDayOfWeek") != null) {
      startDayOfWeek = Integer.parseInt(request.getParameter("startDayOfWeek"));
    }

    if (this.getSelectedUserId() == -1) {
      this.setSelectedUserId(((UserBean) context.getSession().getAttribute("User")).getUserId());
      this.setSelectedUserName(db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  viewChanged  Description of the Parameter
   */
  public void resetParams(String viewChanged) {
    if (viewChanged.equalsIgnoreCase("month")) {
      daySelected = -1;
      startDayOfWeek = -1;
      startMonthOfWeek = -1;
    }
    if (viewChanged.equalsIgnoreCase("week")) {
      daySelected = -1;
      agendaView = false;
    }
    if (viewChanged.equalsIgnoreCase("day")) {
      startDayOfWeek = -1;
      startMonthOfWeek = -1;
      agendaView = false;
    }
    if (viewChanged.equalsIgnoreCase("agenda")) {
      startDayOfWeek = -1;
      startMonthOfWeek = -1;
      daySelected = -1;
    }
  }
}

