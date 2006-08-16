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
package org.aspcfs.modules.communications.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id: CommunicationsPreference.java,v 1.2 2004/08/31 12:48:26
 *          mrajkowski Exp $
 * @created August 6, 2004
 */
public class CommunicationsPreference extends GenericBean {

  //Fields specific to the table communication_preference
  private int id = -1;
  private int contactId = -1;
  private int typeId = -1;
  private int startDay = -1;
  private int endDay = -1;
  private int startTimeHour = -1;
  private int startTimeMinute = -1;
  private int endTimeHour = -1;
  private int endTimeMinute = -1;
  private int level = -1;
  private String timeZone = null;
  // record status
  private Timestamp entered = null;
  private int enteredBy = -1;
  private Timestamp modified = null;
  private int modifiedBy = -1;
  private boolean enabled = false;

  //Resources
  private String type = null;
  private String startDayName = null;
  private String endDayName = null;


  /**
   * Sets the id attribute of the CommunicationsPreference object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the CommunicationsPreference object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the contactId attribute of the CommunicationsPreference object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Sets the contactId attribute of the CommunicationsPreference object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   * Sets the startDay attribute of the CommunicationsPreference object
   *
   * @param tmp The new startDay value
   */
  public void setStartDay(int tmp) {
    this.startDay = tmp;
  }


  /**
   * Sets the startDay attribute of the CommunicationsPreference object
   *
   * @param tmp The new startDay value
   */
  public void setStartDay(String tmp) {
    this.startDay = Integer.parseInt(tmp);
  }


  /**
   * Sets the endDay attribute of the CommunicationsPreference object
   *
   * @param tmp The new endDay value
   */
  public void setEndDay(int tmp) {
    this.endDay = tmp;
  }


  /**
   * Sets the endDay attribute of the CommunicationsPreference object
   *
   * @param tmp The new endDay value
   */
  public void setEndDay(String tmp) {
    this.endDay = Integer.parseInt(tmp);
  }


  /**
   * Sets the startTimeHour attribute of the CommunicationsPreference object
   *
   * @param tmp The new startTimeHour value
   */
  public void setStartTimeHour(int tmp) {
    this.startTimeHour = tmp;
  }


  /**
   * Sets the startTimeHour attribute of the CommunicationsPreference object
   *
   * @param tmp The new startTimeHour value
   */
  public void setStartTimeHour(String tmp) {
    this.startTimeHour = Integer.parseInt(tmp);
  }


  /**
   * Sets the level attribute of the CommunicationsPreference object
   *
   * @param tmp The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   * Sets the level attribute of the CommunicationsPreference object
   *
   * @param tmp The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   * Sets the timeZone attribute of the CommunicationsPreference object
   *
   * @param tmp The new timeZone value
   */
  public void setTimeZone(String tmp) {
    this.timeZone = tmp;
  }


  /**
   * Gets the timeZone attribute of the CommunicationsPreference object
   *
   * @return The timeZone value
   */
  public String getTimeZone() {
    int counter = 0;
    String result = null;
    if (this.timeZone != null) {
      SimpleDateFormat formatter = new SimpleDateFormat();
      formatter.applyPattern("z");
      Timestamp timestamp = new Timestamp(
          Calendar.getInstance().getTimeInMillis());
      java.util.TimeZone tz = java.util.TimeZone.getTimeZone(this.timeZone);
      formatter.setTimeZone(tz);
      result = formatter.format(timestamp);
    }
    return result;
  }


  /**
   * Gets the level attribute of the CommunicationsPreference object
   *
   * @return The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   * Sets the startTimeHour attribute of the CommunicationsPreference object
   *
   * @param hour The new startTimeHour value
   * @param tmp  The new startTimeHour value
   */
  public void setStartTimeHour(int hour, int tmp) {
    Calendar calendar = Calendar.getInstance();
    if (tmp == Calendar.AM) {
      this.startTimeHour = hour;
    } else if (tmp == Calendar.PM) {
      this.startTimeHour = hour + 12;
    }
  }


  /**
   * Sets the startTimeHour attribute of the CommunicationsPreference object
   *
   * @param hourString The new startTimeHour value
   * @param tmpString  The new startTimeHour value
   */
  public void setStartTimeHour(String hourString, String tmpString) {
    int hour = Integer.parseInt(hourString);
    int tmp = Integer.parseInt(tmpString);
    if (tmp == Calendar.AM) {
      this.startTimeHour = hour;
    } else if (tmp == Calendar.PM) {
      this.startTimeHour = hour + 12;
    }
  }


  /**
   * Sets the startTimeMinute attribute of the CommunicationsPreference object
   *
   * @param tmp The new startTimeMinute value
   */
  public void setStartTimeMinute(int tmp) {
    this.startTimeMinute = tmp;
  }


  /**
   * Sets the startTimeMinute attribute of the CommunicationsPreference object
   *
   * @param tmp The new startTimeMinute value
   */
  public void setStartTimeMinute(String tmp) {
    this.startTimeMinute = Integer.parseInt(tmp);
  }


  /**
   * Sets the endTimeHour attribute of the CommunicationsPreference object
   *
   * @param tmp The new endTimeHour value
   */
  public void setEndTimeHour(int tmp) {
    this.endTimeHour = tmp;
  }


  /**
   * Sets the endTimeHour attribute of the CommunicationsPreference object
   *
   * @param tmp The new endTimeHour value
   */
  public void setEndTimeHour(String tmp) {
    this.endTimeHour = Integer.parseInt(tmp);
  }


  /**
   * Sets the endTimeHour attribute of the CommunicationsPreference object
   *
   * @param hour The new endTimeHour value
   * @param tmp  The new endTimeHour value
   */
  public void setEndTimeHour(int hour, int tmp) {
    Calendar calendar = Calendar.getInstance();
    if (tmp == Calendar.AM) {
      this.endTimeHour = hour;
    } else if (tmp == Calendar.PM) {
      this.endTimeHour = hour + 12;
    }
  }


  /**
   * Sets the endTimeHour attribute of the CommunicationsPreference object
   *
   * @param hourString The new endTimeHour value
   * @param tmpString  The new endTimeHour value
   */
  public void setEndTimeHour(String hourString, String tmpString) {
    int hour = Integer.parseInt(hourString);
    int tmp = Integer.parseInt(tmpString);
    if (tmp == Calendar.AM) {
      this.endTimeHour = hour;
    } else if (tmp == Calendar.PM) {
      this.endTimeHour = hour + 12;
    }
  }


  /**
   * Sets the endTimeMinute attribute of the CommunicationsPreference object
   *
   * @param tmp The new endTimeMinute value
   */
  public void setEndTimeMinute(int tmp) {
    this.endTimeMinute = tmp;
  }


  /**
   * Sets the endTimeMinute attribute of the CommunicationsPreference object
   *
   * @param tmp The new endTimeMinute value
   */
  public void setEndTimeMinute(String tmp) {
    this.endTimeMinute = Integer.parseInt(tmp);
  }


  /**
   * Sets the type attribute of the CommunicationsPreference object
   *
   * @param tmp The new type value
   */
  public void setType(String tmp) {
    this.type = tmp;
  }


  /**
   * Sets the startDayName attribute of the CommunicationsPreference object
   *
   * @param tmp The new startDayName value
   */
  public void setStartDayName(String tmp) {
    this.startDayName = tmp;
  }


  /**
   * Sets the endDayName attribute of the CommunicationsPreference object
   *
   * @param tmp The new endDayName value
   */
  public void setEndDayName(String tmp) {
    this.endDayName = tmp;
  }


  /**
   * Sets the entered attribute of the CommunicationsPreference object
   *
   * @param tmp The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the CommunicationsPreference object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the enteredBy attribute of the CommunicationsPreference object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the CommunicationsPreference object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modified attribute of the CommunicationsPreference object
   *
   * @param tmp The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the modified attribute of the CommunicationsPreference object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the CommunicationsPreference object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the CommunicationsPreference object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the enabled attribute of the CommunicationsPreference object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the CommunicationsPreference object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the typeId attribute of the CommunicationsPreference object
   *
   * @param tmp The new typeId value
   */
  public void setTypeId(int tmp) {
    this.typeId = tmp;
  }


  /**
   * Sets the typeId attribute of the CommunicationsPreference object
   *
   * @param tmp The new typeId value
   */
  public void setTypeId(String tmp) {
    this.typeId = Integer.parseInt(tmp);
  }


  /**
   * Gets the typeId attribute of the CommunicationsPreference object
   *
   * @return The typeId value
   */
  public int getTypeId() {
    return typeId;
  }


  /**
   * Gets the entered attribute of the CommunicationsPreference object
   *
   * @return The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the enteredBy attribute of the CommunicationsPreference object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modified attribute of the CommunicationsPreference object
   *
   * @return The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the modifiedBy attribute of the CommunicationsPreference object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the enabled attribute of the CommunicationsPreference object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the id attribute of the CommunicationsPreference object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the contactId attribute of the CommunicationsPreference object
   *
   * @return The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   * Gets the startDay attribute of the CommunicationsPreference object
   *
   * @return The startDay value
   */
  public int getStartDay() {
    return startDay;
  }


  /**
   * Gets the endDay attribute of the CommunicationsPreference object
   *
   * @return The endDay value
   */
  public int getEndDay() {
    return endDay;
  }


  /**
   * Gets the startTimeHour attribute of the CommunicationsPreference object
   *
   * @return The startTimeHour value
   */
  public int getStartTimeHour() {
    return startTimeHour;
  }


  /**
   * Gets the startTimeHour attribute of the CommunicationsPreference object
   *
   * @param str Description of the Parameter
   * @return The startTimeHour value
   */
  public String getStartTimeHour(String str) {
    String result = "";
    String minute = "" + startTimeMinute;
    if ((startTimeMinute / 10) % 10 == 0 && (startTimeMinute / 10) <= 0) {
      minute = "0" + minute;
    }
    if (startTimeHour < 13) {
      result = new String(startTimeHour + ":" + minute + " AM");
    } else if (startTimeHour == 12) {
      result = new String(startTimeHour + ":" + minute + " PM");
    } else if (startTimeHour == 24) {
      result = new String(startTimeHour + ":" + minute + " AM");
    } else {
      result = new String((startTimeHour - 12) + ":" + minute + " PM");
    }
    return result;
  }


  /**
   * Gets the startTimeMinute attribute of the CommunicationsPreference object
   *
   * @return The startTimeMinute value
   */
  public int getStartTimeMinute() {
    return startTimeMinute;
  }


  /**
   * Gets the endTimeHour attribute of the CommunicationsPreference object
   *
   * @return The endTimeHour value
   */
  public int getEndTimeHour() {
    return endTimeHour;
  }


  /**
   * Gets the endTimeHour attribute of the CommunicationsPreference object
   *
   * @param str Description of the Parameter
   * @return The endTimeHour value
   */
  public String getEndTimeHour(String str) {
    String result = "";
    String minute = "" + endTimeMinute;
    if ((endTimeMinute / 10) % 10 == 0 && (endTimeMinute / 10) <= 0) {
      minute = "0" + minute;
    }
    if (endTimeHour < 12) {
      result = new String(endTimeHour + ":0" + minute + " AM");
    } else if (endTimeHour == 12) {
      result = new String(endTimeHour + ":" + minute + " PM");
    } else if (endTimeHour == 24) {
      result = new String(endTimeHour + ":" + minute + " AM");
    } else {
      result = new String((endTimeHour - 12) + ":" + minute + " PM");
    }
    return result;
  }


  /**
   * Gets the endTimeMinute attribute of the CommunicationsPreference object
   *
   * @return The endTimeMinute value
   */
  public int getEndTimeMinute() {
    return endTimeMinute;
  }


  /**
   * Gets the type attribute of the CommunicationsPreference object
   *
   * @return The type value
   */
  public String getType() {
    return type;
  }


  /**
   * Gets the startDayName attribute of the CommunicationsPreference object
   *
   * @return The startDayName value
   */
  public String getStartDayName() {
    return startDayName;
  }


  /**
   * Gets the endDayName attribute of the CommunicationsPreference object
   *
   * @return The endDayName value
   */
  public String getEndDayName() {
    return endDayName;
  }


  /**
   * Constructor for the CommunicationsPreference object
   *
   * @throws SQLException Description of the Exception
   */
  public CommunicationsPreference() throws SQLException {
  }


  /**
   * Constructor for the CommunicationsPreference object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public CommunicationsPreference(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the CommunicationsPreference object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public CommunicationsPreference(Connection db, String id) throws SQLException {
    queryRecord(db, Integer.parseInt(id));
  }


  /**
   * Constructor for the CommunicationsPreference object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public CommunicationsPreference(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   * Description of the Method
   *
   * @param db           Description of the Parameter
   * @param preferenceId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int preferenceId) throws SQLException {
    if (preferenceId == -1) {
      throw new SQLException("Invalid Preference ID.");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT cp.* " +
        "FROM communication_preference cp " +
        "LEFT JOIN contact ct ON (cp.contact_id = ct.contact_id) " +
        "WHERE cp.preference_id = ? ");
    pst.setInt(1, preferenceId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Communications Preference not found.");
    }
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //communication_preference table
    this.setId(rs.getInt("preference_id"));
    this.setContactId(DatabaseUtils.getInt(rs, "contact_id"));
    this.setTypeId(DatabaseUtils.getInt(rs, "type_id"));
    this.setStartDay(DatabaseUtils.getInt(rs, "start_day"));
    this.setEndDay(DatabaseUtils.getInt(rs, "end_day"));
    this.setStartTimeHour(DatabaseUtils.getInt(rs, "start_time_hr"));
    this.setStartTimeMinute(DatabaseUtils.getInt(rs, "start_time_min"));
    this.setEndTimeHour(DatabaseUtils.getInt(rs, "end_time_hr"));
    this.setEndTimeMinute(DatabaseUtils.getInt(rs, "end_time_min"));
    this.setEntered(rs.getTimestamp("entered"));
    this.setModified(rs.getTimestamp("modified"));
    this.setEnteredBy(rs.getInt("enteredBy"));
    this.setModifiedBy(rs.getInt("modifiedBy"));
    this.setEnabled(rs.getBoolean("enabled"));
    this.setLevel(rs.getInt("level"));
    this.setTimeZone(rs.getString("time_zone"));
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(
        db, "communication_preference_preference_id_seq");
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO communication_preference " +
        "(" + (id > -1 ? "preference_id, " : "") + "contact_id, type_id, " + DatabaseUtils.addQuotes(db, "level") + ", time_zone ");
    if (startDay != -1) {
      sql.append(", start_day ");
    }
    if (endDay != -1) {
      sql.append(", end_day ");
    }
    if (startTimeHour != -1) {
      sql.append(", start_time_hr ");
    }
    if (startTimeMinute != -1) {
      sql.append(", start_time_min ");
    }
    if (endTimeHour != -1) {
      sql.append(", end_time_hr ");
    }
    if (endTimeMinute != -1) {
      sql.append(", end_time_min ");
    }
    if (entered != null) {
      sql.append(", entered ");
    }
    sql.append(", enteredby ");
    if (modified != null) {
      sql.append(", modified ");
    }
    sql.append(
        ", modifiedby , enabled ) VALUES " +
        "(" + (id > -1 ? "?, " : "") + "?, ?, ?, ? ");

    if (startDay != -1) {
      sql.append(", ? ");
    }
    if (endDay != -1) {
      sql.append(", ? ");
    }
    if (startTimeHour != -1) {
      sql.append(", ? ");
    }
    if (startTimeMinute != -1) {
      sql.append(", ? ");
    }
    if (endTimeHour != -1) {
      sql.append(", ? ");
    }
    if (endTimeMinute != -1) {
      sql.append(", ? ");
    }
    if (entered != null) {
      sql.append(", ? ");
    }
    sql.append(", ? ");
    if (modified != null) {
      sql.append(", ? ");
    }
    sql.append(", ?, ?)");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (id > -1) {
      pst.setInt(++i, id);
    }
    DatabaseUtils.setInt(pst, ++i, this.getContactId());
    pst.setInt(++i, this.getTypeId());
    DatabaseUtils.setInt(pst, ++i, this.getLevel());
    pst.setString(++i, this.timeZone);
    if (startDay != -1) {
      DatabaseUtils.setInt(pst, ++i, this.getStartDay());
    }
    if (endDay != -1) {
      DatabaseUtils.setInt(pst, ++i, this.getEndDay());
    }
    if (startTimeHour != -1) {
      DatabaseUtils.setInt(pst, ++i, this.getStartTimeHour());
    }
    if (startTimeMinute != -1) {
      DatabaseUtils.setInt(pst, ++i, this.getStartTimeMinute());
    }
    if (endTimeHour != -1) {
      DatabaseUtils.setInt(pst, ++i, this.getEndTimeHour());
    }
    if (endTimeMinute != -1) {
      DatabaseUtils.setInt(pst, ++i, this.getEndTimeMinute());
    }
    if (entered != null) {
      pst.setTimestamp(++i, this.getEntered());
    }
    pst.setInt(++i, this.getEnteredBy());
    if (modified != null) {
      pst.setTimestamp(++i, this.getModified());
    }
    pst.setInt(++i, this.getModifiedBy());
    pst.setBoolean(++i, this.getEnabled());
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(
        db, "communication_preference_preference_id_seq", id);
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM communication_preference WHERE preference_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   * Gets the dayOfWeek attribute of the CommunicationsPreference class
   *
   * @param day Description of the Parameter
   * @return The dayOfWeek value
   */
  public static String getDayOfWeek(int day) {
    String result = "";
    switch (day) {
      case Calendar.MONDAY:
        result = "Mon";
        break;
      case Calendar.TUESDAY:
        result = "Tue";
        break;
      case Calendar.WEDNESDAY:
        result = "Wed";
        break;
      case Calendar.THURSDAY:
        result = "Thu";
        break;
      case Calendar.FRIDAY:
        result = "Fri";
        break;
      case Calendar.SATURDAY:
        result = "Sat";
        break;
      case Calendar.SUNDAY:
        result = "Sun";
        break;
      default:
        result = "NONE";
        break;
    }
    return result;
  }


  /**
   * Gets the htmlSelect attribute of the CommunicationsPreference class
   *
   * @param selectName Description of the Parameter
   * @param defaultKey Description of the Parameter
   * @return The htmlSelect value
   */
  public static String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect currentList = new HtmlSelect();
    currentList.addItem(Calendar.MONDAY, "Monday");
    currentList.addItem(Calendar.TUESDAY, "Tuesday");
    currentList.addItem(Calendar.WEDNESDAY, "Wednesday");
    currentList.addItem(Calendar.THURSDAY, "Thursday");
    currentList.addItem(Calendar.FRIDAY, "Friday");
    currentList.addItem(Calendar.SATURDAY, "Saturday");
    currentList.addItem(Calendar.SUNDAY, "Sunday");
    return currentList.getHtml(selectName, defaultKey);
  }


  /**
   * Gets the htmlSelectLevel attribute of the CommunicationsPreference class
   *
   * @param selectName  Description of the Parameter
   * @param defaultKey  Description of the Parameter
   * @param lowestLevel Description of the Parameter
   * @return The htmlSelectLevel value
   */
  public static String getHtmlSelectLevel(String selectName, int defaultKey, int lowestLevel) {
    HtmlSelect currentList = new HtmlSelect();
    for (int i = 1; i <= lowestLevel; i++) {
      currentList.addItem(i, "preference " + i);
    }
    return currentList.getHtml(selectName, defaultKey);
  }
}

