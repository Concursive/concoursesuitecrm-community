package org.aspcfs.modules.communications.base;

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.utils.web.*;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    August 6, 2004
 * @version    $Id$
 */
public class CommunicationsPreferenceList extends ArrayList {

  //Fields specific to the table communication_preference
  private PagedListInfo pagedListInfo = null;
  private int id = -1;
  private int contactId = -1;
  private int typeId = -1;
  private int startDay = -1;
  private int endDay = -1;
  private int startTimeHour = -1;
  private int startTimeMinute = -1;
  private int endTimeHour = -1;
  private int endTimeMinute = -1;
  // record status
  private Timestamp entered = null;
  private int enteredBy = -1;
  private Timestamp modified = null;
  private int modifiedBy = -1;
  private int enabled = Constants.UNDEFINED;
  private int level = -1;
  private String timeZone = null;


  /**
   *  Sets the pagedListInfo attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the id attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the contactId attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   *  Sets the contactId attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the typeId attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new typeId value
   */
  public void setTypeId(int tmp) {
    this.typeId = tmp;
  }


  /**
   *  Sets the typeId attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new typeId value
   */
  public void setTypeId(String tmp) {
    this.typeId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the startDay attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new startDay value
   */
  public void setStartDay(int tmp) {
    this.startDay = tmp;
  }


  /**
   *  Sets the startDay attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new startDay value
   */
  public void setStartDay(String tmp) {
    this.startDay = Integer.parseInt(tmp);
  }


  /**
   *  Sets the endDay attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new endDay value
   */
  public void setEndDay(int tmp) {
    this.endDay = tmp;
  }


  /**
   *  Sets the endDay attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new endDay value
   */
  public void setEndDay(String tmp) {
    this.endDay = Integer.parseInt(tmp);
  }


  /**
   *  Sets the startTimeHour attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new startTimeHour value
   */
  public void setStartTimeHour(int tmp) {
    this.startTimeHour = tmp;
  }


  /**
   *  Sets the startTimeHour attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new startTimeHour value
   */
  public void setStartTimeHour(String tmp) {
    this.startTimeHour = Integer.parseInt(tmp);
  }


  /**
   *  Sets the startTimeMinute attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new startTimeMinute value
   */
  public void setStartTimeMinute(int tmp) {
    this.startTimeMinute = tmp;
  }


  /**
   *  Sets the startTimeMinute attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new startTimeMinute value
   */
  public void setStartTimeMinute(String tmp) {
    this.startTimeMinute = Integer.parseInt(tmp);
  }


  /**
   *  Sets the endTimeHour attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new endTimeHour value
   */
  public void setEndTimeHour(int tmp) {
    this.endTimeHour = tmp;
  }


  /**
   *  Sets the endTimeHour attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new endTimeHour value
   */
  public void setEndTimeHour(String tmp) {
    this.endTimeHour = Integer.parseInt(tmp);
  }


  /**
   *  Sets the endTimeMinute attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new endTimeMinute value
   */
  public void setEndTimeMinute(int tmp) {
    this.endTimeMinute = tmp;
  }


  /**
   *  Sets the endTimeMinute attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new endTimeMinute value
   */
  public void setEndTimeMinute(String tmp) {
    this.endTimeMinute = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   *  Sets the level attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   *  Sets the level attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   *  Sets the timeZone attribute of the CommunicationsPreferenceList object
   *
   * @param  tmp  The new timeZone value
   */
  public void setTimeZone(String tmp) {
    this.timeZone = tmp;
  }


  /**
   *  Gets the timeZone attribute of the CommunicationsPreferenceList object
   *
   * @return    The timeZone value
   */
  public String getTimeZone() {
    return timeZone;
  }


  /**
   *  Gets the level attribute of the CommunicationsPreferenceList object
   *
   * @return    The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   *  Gets the enabled attribute of the CommunicationsPreferenceList object
   *
   * @return    The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   *  Gets the pagedListInfo attribute of the CommunicationsPreferenceList object
   *
   * @return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the id attribute of the CommunicationsPreferenceList object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the contactId attribute of the CommunicationsPreferenceList object
   *
   * @return    The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   *  Gets the typeId attribute of the CommunicationsPreferenceList object
   *
   * @return    The typeId value
   */
  public int getTypeId() {
    return typeId;
  }


  /**
   *  Gets the startDay attribute of the CommunicationsPreferenceList object
   *
   * @return    The startDay value
   */
  public int getStartDay() {
    return startDay;
  }


  /**
   *  Gets the endDay attribute of the CommunicationsPreferenceList object
   *
   * @return    The endDay value
   */
  public int getEndDay() {
    return endDay;
  }


  /**
   *  Gets the startTimeHour attribute of the CommunicationsPreferenceList object
   *
   * @return    The startTimeHour value
   */
  public int getStartTimeHour() {
    return startTimeHour;
  }


  /**
   *  Gets the startTimeMinute attribute of the CommunicationsPreferenceList object
   *
   * @return    The startTimeMinute value
   */
  public int getStartTimeMinute() {
    return startTimeMinute;
  }


  /**
   *  Gets the endTimeHour attribute of the CommunicationsPreferenceList object
   *
   * @return    The endTimeHour value
   */
  public int getEndTimeHour() {
    return endTimeHour;
  }


  /**
   *  Gets the endTimeMinute attribute of the CommunicationsPreferenceList object
   *
   * @return    The endTimeMinute value
   */
  public int getEndTimeMinute() {
    return endTimeMinute;
  }


  /**
   *  Gets the entered attribute of the CommunicationsPreferenceList object
   *
   * @return    The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredBy attribute of the CommunicationsPreferenceList object
   *
   * @return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modified attribute of the CommunicationsPreferenceList object
   *
   * @return    The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedBy attribute of the CommunicationsPreferenceList object
   *
   * @return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *Constructor for the CommunicationsPreferenceList object
   */
  public CommunicationsPreferenceList() { }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    //Build a base SQL statement for counting records
    sqlCount.append(
        " SELECT COUNT(*) AS recordcount " +
        " FROM communication_preference cp " +
        " LEFT JOIN contact ct ON (cp.contact_id = ct.contact_id) " +
        " WHERE cp.preference_id > -1 ");

    createFilter(sqlFilter);
    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();
      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
        items = prepareFilter(pst);
        //pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }
      //Determine column to sort by
      pagedListInfo.setDefaultSort("cp.level", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY cp.level");
    }
    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        " cp.* " +
        " FROM communication_preference cp " +
        " LEFT JOIN contact ct ON (cp.contact_id = ct.contact_id) " +
        " WHERE cp.preference_id > -1 ");

    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    int count = 0;
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.getItemsPerPage() > 0 &&
          DatabaseUtils.getType(db) == DatabaseUtils.MSSQL &&
          count >= pagedListInfo.getItemsPerPage()) {
        break;
      }
      ++count;
      CommunicationsPreference thisPreference = new CommunicationsPreference(rs);
      this.add(thisPreference);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  sqlFilter  Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (contactId > -1) {
      sqlFilter.append("AND cp.contact_id = ? ");
    }
    if (typeId != -1) {
      sqlFilter.append("AND cp.type_id = ? ");
    }
    if (startDay != -1) {
      sqlFilter.append("AND cp.start_day = ? ");
    }
    if (endDay != -1) {
      sqlFilter.append("AND cp.end_day = ? ");
    }
    if (startTimeHour != -1) {
      sqlFilter.append("AND cp.start_time_hr = ? ");
    }
    if (startTimeMinute != -1) {
      sqlFilter.append("AND cp.start_time_min = ? ");
    }
    if (endTimeHour != -1) {
      sqlFilter.append("AND cp.end_time_hr = ? ");
    }
    if (endTimeMinute != -1) {
      sqlFilter.append("AND cp.end_time_min = ? ");
    }
    if (entered != null) {
      sqlFilter.append("AND cp.entered = ? ");
    }
    if (enteredBy != -1) {
      sqlFilter.append("AND cp.enteredby = ? ");
    }
    if (modified != null) {
      sqlFilter.append("AND cp.modified = ? ");
    }
    if (modifiedBy != -1) {
      sqlFilter.append("AND cp.modifiedby = ? ");
    }
    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND cp.enabled = ? ");
    }
    if (level != -1) {
      sqlFilter.append("AND cp.level = ? ");
    }
    if (timeZone != null) {
      sqlFilter.append("AND cp.time_zone = ? ");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  pst               Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (contactId > -1) {
      pst.setInt(++i, contactId);
    }
    if (typeId != -1) {
      pst.setInt(++i, this.getTypeId());
    }
    if (startDay != -1) {
      pst.setInt(++i, this.getStartDay());
    }
    if (endDay != -1) {
      pst.setInt(++i, this.getEndDay());
    }
    if (startTimeHour != -1) {
      pst.setInt(++i, this.getStartTimeHour());
    }
    if (startTimeMinute != -1) {
      pst.setInt(++i, this.getStartTimeMinute());
    }
    if (endTimeHour != -1) {
      pst.setInt(++i, this.getEndTimeHour());
    }
    if (endTimeMinute != -1) {
      pst.setInt(++i, this.getEndTimeMinute());
    }
    if (entered != null) {
      pst.setTimestamp(++i, this.getEntered());
    }
    if (enteredBy != -1) {
      pst.setInt(++i, this.getEnteredBy());
    }
    if (modified != null) {
      pst.setTimestamp(++i, this.getModified());
    }
    if (modifiedBy != -1) {
      pst.setInt(++i, this.getModifiedBy());
    }
    if (enabled == Constants.TRUE) {
      pst.setBoolean(++i, true);
    } else if (enabled == Constants.FALSE) {
      pst.setBoolean(++i, false);
    }
    if (level != -1) {
      pst.setInt(++i, this.getLevel());
    }
    if (timeZone != null) {
      pst.setString(++i, this.getTimeZone());
    }
    return i;
  }
}

