package org.aspcfs.modules.troubletickets.base;

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.modules.servicecontracts.base.*;
import org.aspcfs.modules.base.*;

/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    February 2, 2004
 *@version    $Id: TicketCSSTMMaintenance.java,v 1.1.2.3 2004/02/03 17:50:54
 *      kbhoopal Exp $
 */
public class TicketActivityLog extends GenericBean {

  private int id = -1;
  private int linkTicketId = -1;
  private String phoneResponseTime = null;
  private String engineerResponseTime = null;
  private java.sql.Timestamp alertDate = null;
  private boolean followUpRequired = false;
  private String followUpDescription = null;
  private TicketPerDayDescriptionList ticketPerDayDescriptionList = null;

  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private boolean enabled = true;

  private int totalTravelHours = -1;
  private int totalTravelMinutes = -1;
  private int totalLaborHours = -1;
  private int totalLaborMinutes = -1;
  private boolean travelTowardsServiceContract = true;
  private boolean laborTowardsServiceContract = true;

  private java.sql.Timestamp firstActivityDate = null;
  private java.sql.Timestamp lastActivityDate = null;

  private boolean override = false;

  private HttpServletRequest request = null;
  private int relatedContractId = -1;


  /**
   *  Constructor for the TicketCSSTMMaintenanc object
   */
  public TicketActivityLog() { }


  /**
   *  Constructor for the TicketCSSTMMaintenanc object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public TicketActivityLog(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the TicketCSSTMMaintenanc object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the TicketCSSTMMaintenanc object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the linkTicketId attribute of the TicketCSSTMMaintenanc object
   *
   *@param  tmp  The new linkTicketId value
   */
  public void setLinkTicketId(int tmp) {
    this.linkTicketId = tmp;
  }


  /**
   *  Sets the linkTicketId attribute of the TicketCSSTMMaintenanc object
   *
   *@param  tmp  The new linkTicketId value
   */
  public void setLinkTicketId(String tmp) {
    this.linkTicketId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the phoneResponseTime attribute of the TicketCSSTMMaintenanc object
   *
   *@param  tmp  The new phoneResponseTime value
   */
  public void setPhoneResponseTime(String tmp) {
    this.phoneResponseTime = tmp;
  }


  /**
   *  Sets the engineerResponseTime attribute of the TicketCSSTMMaintenanc
   *  object
   *
   *@param  tmp  The new engineerResponseTime value
   */
  public void setEngineerResponseTime(String tmp) {
    this.engineerResponseTime = tmp;
  }


  /**
   *  Sets the alertDate attribute of the TicketCSSTMMaintenanc object
   *
   *@param  tmp  The new alertDate value
   */
  public void setAlertDate(java.sql.Timestamp tmp) {
    this.alertDate = tmp;
  }


  /**
   *  Sets the alertDate attribute of the TicketCSSTMMaintenanc object
   *
   *@param  tmp  The new alertDate value
   */
  public void setAlertDate(String tmp) {
    java.sql.Timestamp tmpDate = null;
    tmpDate = DateUtils.parseTimestampString(tmp, "M/d/yy");
    if (tmpDate == null) {
      tmpDate = DateUtils.parseTimestampString(tmp, "M-d-yy");
    }

    this.alertDate = tmpDate;
  }


  /**
   *  Sets the followUpRequired attribute of the TicketCSSTMMaintenance object
   *
   *@param  tmp  The new followUpRequired value
   */
  public void setFollowUpRequired(String tmp) {
    this.followUpRequired = ("true".equalsIgnoreCase(tmp) || "on".equalsIgnoreCase(tmp));
  }


  /**
   *  Sets the followUpRequired attribute of the TicketCSSTMMaintenanc object
   *
   *@param  tmp  The new followUpRequired value
   */
  public void setFollowUpRequired(boolean tmp) {
    this.followUpRequired = tmp;
  }


  /**
   *  Sets the followUpDescription attribute of the TicketCSSTMMaintenanc object
   *
   *@param  tmp  The new followUpDescription value
   */
  public void setFollowUpDescription(String tmp) {
    this.followUpDescription = tmp;
  }


  /**
   *  Sets the perDayDescriptionList attribute of the TicketCSSTMMaintenanc
   *  object
   *
   *@param  tmp  The new perDayDescriptionList value
   */
  public void setTicketPerDayDescriptionList(TicketPerDayDescriptionList tmp) {
    this.ticketPerDayDescriptionList = tmp;
  }


  /**
   *  Sets the entered attribute of the TicketCSSTMMaintenanc object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the TicketCSSTMMaintenanc object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the TicketCSSTMMaintenanc object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the TicketCSSTMMaintenanc object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the TicketCSSTMMaintenanc object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the TicketCSSTMMaintenanc object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the TicketCSSTMMaintenanc object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the TicketCSSTMMaintenanc object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the TicketCSSTMMaintenanc object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the TicketCSSTMMaintenanc object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the totalTravelHours attribute of the TicketCSSTMMaintenance object
   *
   *@param  tmp  The new totalTravelHours value
   */
  public void setTotalTravelHours(int tmp) {
    this.totalTravelHours = tmp;
  }


  /**
   *  Sets the totalTravelHours attribute of the TicketCSSTMMaintenance object
   *
   *@param  tmp  The new totalTravelHours value
   */
  public void setTotalTravelHours(String tmp) {
    this.totalTravelHours = Integer.parseInt(tmp);
  }


  /**
   *  Sets the totalTravelMinutes attribute of the TicketCSSTMMaintenance object
   *
   *@param  tmp  The new totalTravelMinutes value
   */
  public void setTotalTravelMinutes(int tmp) {
    this.totalTravelMinutes = tmp;
  }


  /**
   *  Sets the totalTravelMinutes attribute of the TicketCSSTMMaintenance object
   *
   *@param  tmp  The new totalTravelMinutes value
   */
  public void setTotalTravelMinutes(String tmp) {
    this.totalTravelMinutes = Integer.parseInt(tmp);
  }


  /**
   *  Sets the totalLaborHours attribute of the TicketCSSTMMaintenance object
   *
   *@param  tmp  The new totalLaborHours value
   */
  public void setTotalLaborHours(int tmp) {
    this.totalLaborHours = tmp;
  }


  /**
   *  Sets the totalLaborHours attribute of the TicketCSSTMMaintenance object
   *
   *@param  tmp  The new totalLaborHours value
   */
  public void setTotalLaborHours(String tmp) {
    this.totalLaborHours = Integer.parseInt(tmp);
  }


  /**
   *  Sets the totalLaborMinutes attribute of the TicketCSSTMMaintenance object
   *
   *@param  tmp  The new totalLaborMinutes value
   */
  public void setTotalLaborMinutes(int tmp) {
    this.totalLaborMinutes = tmp;
  }


  /**
   *  Sets the totalLaborMinutes attribute of the TicketCSSTMMaintenance object
   *
   *@param  tmp  The new totalLaborMinutes value
   */
  public void setTotalLaborMinutes(String tmp) {
    this.totalLaborMinutes = Integer.parseInt(tmp);
  }


  /**
   *  Sets the travelTowardsServiceContract attribute of the
   *  TicketCSSTMMaintenance object
   *
   *@param  tmp  The new travelTowardsServiceContract value
   */
  public void setTravelTowardsServiceContract(boolean tmp) {
    this.travelTowardsServiceContract = tmp;
  }


  /**
   *  Sets the travelTowardsServiceContract attribute of the
   *  TicketCSSTMMaintenance object
   *
   *@param  tmp  The new travelTowardsServiceContract value
   */
  public void setTravelTowardsServiceContract(String tmp) {
    this.travelTowardsServiceContract = ("true".equalsIgnoreCase(tmp) || "on".equalsIgnoreCase(tmp));
  }


  /**
   *  Sets the laborTowardsServiceContract attribute of the
   *  TicketCSSTMMaintenance object
   *
   *@param  tmp  The new laborTowardsServiceContract value
   */
  public void setLaborTowardsServiceContract(boolean tmp) {
    this.laborTowardsServiceContract = tmp;
  }


  /**
   *  Sets the laborTowardsServiceContract attribute of the
   *  TicketCSSTMMaintenance object
   *
   *@param  tmp  The new laborTowardsServiceContract value
   */
  public void setLaborTowardsServiceContract(String tmp) {
    this.laborTowardsServiceContract = ("true".equalsIgnoreCase(tmp) || "on".equalsIgnoreCase(tmp));
  }


  /**
   *  Sets the override attribute of the TicketCSSTMMaintenance object
   *
   *@param  tmp  The new override value
   */
  public void setOverride(boolean tmp) {
    this.override = tmp;
  }


  /**
   *  Sets the override attribute of the TicketCSSTMMaintenance object
   *
   *@param  tmp  The new override value
   */
  public void setOverride(String tmp) {
    this.override = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the requestItems attribute of the TicketCSSTMMaintenanc object
   *
   *@param  request  The new requestItems value
   */
  public void setRequestItems(HttpServletRequest request) {
    ticketPerDayDescriptionList = new TicketPerDayDescriptionList(request);
    this.totalTravelHours = ticketPerDayDescriptionList.getTotalTravelHours();
    this.totalTravelMinutes = ticketPerDayDescriptionList.getTotalTravelMinutes();
    this.totalLaborHours = ticketPerDayDescriptionList.getTotalLaborHours();
    this.totalLaborMinutes = ticketPerDayDescriptionList.getTotalLaborMinutes();
  }


  /**
   *  Sets the request attribute of the TicketCSSTMMaintenance object
   *
   *@param  tmp  The new request value
   */
  public void setRequest(HttpServletRequest tmp) {
    this.request = tmp;
  }


  /**
   *  Sets the relatedContractId attribute of the TicketCSSTMMaintenance object
   *
   *@param  tmp  The new relatedContractId value
   */
  public void setRelatedContractId(int tmp) {
    this.relatedContractId = tmp;
  }


  /**
   *  Sets the relatedContractId attribute of the TicketCSSTMMaintenance object
   *
   *@param  tmp  The new relatedContractId value
   */
  public void setRelatedContractId(String tmp) {
    this.relatedContractId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the id attribute of the TicketCSSTMMaintenanc object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the linkTicketId attribute of the TicketCSSTMMaintenanc object
   *
   *@return    The linkTicketId value
   */
  public int getLinkTicketId() {
    return linkTicketId;
  }


  /**
   *  Gets the phoneResponseTime attribute of the TicketCSSTMMaintenanc object
   *
   *@return    The phoneResponseTime value
   */
  public String getPhoneResponseTime() {
    return phoneResponseTime;
  }


  /**
   *  Gets the engineerResponseTime attribute of the TicketCSSTMMaintenanc
   *  object
   *
   *@return    The engineerResponseTime value
   */
  public String getEngineerResponseTime() {
    return engineerResponseTime;
  }


  /**
   *  Gets the alertDate attribute of the TicketCSSTMMaintenanc object
   *
   *@return    The alertDate value
   */
  public java.sql.Timestamp getAlertDate() {
    return alertDate;
  }


  /**
   *  Gets the followUpRequired attribute of the TicketCSSTMMaintenanc object
   *
   *@return    The followUpRequired value
   */
  public boolean getFollowUpRequired() {
    return followUpRequired;
  }


  /**
   *  Gets the followUpDescription attribute of the TicketCSSTMMaintenanc object
   *
   *@return    The followUpDescription value
   */
  public String getFollowUpDescription() {
    return followUpDescription;
  }


  /**
   *  Gets the perDayDescriptionList attribute of the TicketCSSTMMaintenanc
   *  object
   *
   *@return    The perDayDescriptionList value
   */
  public TicketPerDayDescriptionList getTicketPerDayDescriptionList() {
    return ticketPerDayDescriptionList;
  }


  /**
   *  Gets the entered attribute of the TicketCSSTMMaintenanc object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredBy attribute of the TicketCSSTMMaintenanc object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modified attribute of the TicketCSSTMMaintenanc object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedBy attribute of the TicketCSSTMMaintenanc object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the enabled attribute of the TicketCSSTMMaintenanc object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the totalTravelHours attribute of the TicketCSSTMMaintenance object
   *
   *@return    The totalTravelHours value
   */
  public int getTotalTravelHours() {
    return totalTravelHours;
  }


  /**
   *  Gets the totalTravelMinutes attribute of the TicketCSSTMMaintenance object
   *
   *@return    The totalTravelMinutes value
   */
  public int getTotalTravelMinutes() {
    return totalTravelMinutes;
  }


  /**
   *  Gets the totalLaborHours attribute of the TicketCSSTMMaintenance object
   *
   *@return    The totalLaborHours value
   */
  public int getTotalLaborHours() {
    return totalLaborHours;
  }


  /**
   *  Gets the totalLaborMinutes attribute of the TicketCSSTMMaintenance object
   *
   *@return    The totalLaborMinutes value
   */
  public int getTotalLaborMinutes() {
    return totalLaborMinutes;
  }


  /**
   *  Gets the travelTowardsServiceContract attribute of the
   *  TicketCSSTMMaintenance object
   *
   *@return    The travelTowardsServiceContract value
   */
  public boolean getTravelTowardsServiceContract() {
    return travelTowardsServiceContract;
  }


  /**
   *  Gets the laborTowardsServiceContract attribute of the
   *  TicketCSSTMMaintenance object
   *
   *@return    The laborTowardsServiceContract value
   */
  public boolean getLaborTowardsServiceContract() {
    return laborTowardsServiceContract;
  }


  /**
   *  Gets the firstActivityDate attribute of the TicketCSSTMMaintenance object
   *
   *@return    The firstActivityDate value
   */
  public java.sql.Timestamp getFirstActivityDate() {
    return firstActivityDate;
  }


  /**
   *  Gets the lastActivityDate attribute of the TicketCSSTMMaintenance object
   *
   *@return    The lastActivityDate value
   */
  public java.sql.Timestamp getLastActivityDate() {
    return lastActivityDate;
  }


  /**
   *  Gets the override attribute of the TicketCSSTMMaintenance object
   *
   *@return    The override value
   */
  public boolean getOverride() {
    return override;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  tmpId             Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int tmpId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;

    pst = db.prepareStatement(
        "SELECT form_id, " +
        "link_ticket_id, " +
        "phone_response_time, " +
        "engineer_response_time, " +
        "follow_up_required, " +
        "follow_up_description, " +
        "alert_date,  " +
        "entered, " +
        "enteredby, " +
        "modified, " +
        "modifiedby, " +
        "enabled, " +
        "travel_towards_sc, " +
        "labor_towards_sc, " +
        "min(activity_date) as firstdate, " +
        "max(activity_date) as lastdate " +
        "FROM ticket_csstm_form " +
        "LEFT JOIN  ticket_activity_item on (link_form_id = form_id) " +
        "WHERE form_id = ? " +
        "GROUP BY form_id,link_ticket_id,phone_response_time,engineer_response_time, follow_up_required, follow_up_description, alert_date,  entered, enteredby, modified,modifiedby, enabled, travel_towards_sc, labor_towards_sc ");

    pst.setInt(1, tmpId);
    rs = pst.executeQuery();

    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();

    //retrieve perday work description for this activity
    buildPerDayDescriptionList(db);
    calculateTotalTravelTime();
    calculateTotalLaborTime();
  }


  /**
   *  Description of the Method
   */
  void calculateTotalTravelTime() {
    Iterator itr = ticketPerDayDescriptionList.iterator();
    totalTravelHours = 0;
    totalTravelMinutes = 0;
    while (itr.hasNext()) {
      TicketPerDayDescription thisDay = (TicketPerDayDescription) itr.next();
      totalTravelMinutes = totalTravelMinutes + thisDay.getTravelMinutes();
      totalTravelHours = totalTravelHours + thisDay.getTravelHours();
    }
    totalTravelHours = totalTravelHours + totalTravelMinutes / 60;
    totalTravelMinutes = totalTravelMinutes % 60;
  }


  /**
   *  Description of the Method
   */
  void calculateTotalLaborTime() {
    Iterator itr = ticketPerDayDescriptionList.iterator();
    totalLaborHours = 0;
    totalLaborMinutes = 0;
    while (itr.hasNext()) {
      TicketPerDayDescription thisDay = (TicketPerDayDescription) itr.next();
      totalLaborMinutes = totalLaborMinutes + thisDay.getLaborMinutes();
      totalLaborHours = totalLaborHours + thisDay.getLaborHours();
    }
    totalLaborHours = totalLaborHours + totalLaborMinutes / 60;
    totalLaborMinutes = totalLaborMinutes % 60;
  }


  /**
   *  Queries the activities performed during each day for this ticket form
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildPerDayDescriptionList(Connection db) throws SQLException {
    ticketPerDayDescriptionList = new TicketPerDayDescriptionList();
    ticketPerDayDescriptionList.queryList(db, this.id);
  }


  /**
   *  Deletes the activities performed during each day for this ticket form
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean deletePerDayDescriptionList(Connection db) throws SQLException {
    ticketPerDayDescriptionList = new TicketPerDayDescriptionList();
    return ticketPerDayDescriptionList.deleteList(db, this.id);
  }


  /**
   *  Gets the properties that are TimeZone sensitive
   *
   *@return    The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {

    ArrayList thisList = new ArrayList();
    thisList.add("alertDate");

    return thisList;
  }


  /**
   *  Gets the valid attribute of the TicketCSSTMMaintenance object
   *
   *@return                   The valid value
   *@exception  SQLException  Description of the Exception
   */
  protected boolean isValid() throws SQLException {
    errors.clear();
    if (hasErrors()) {
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                 Description of the Parameter
   *@return                    Description of the Return Value
   *@exception  SQLException   Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = -1;
    if (!isValid()) {
      return resultCount;
    }
    boolean doCommit = false;
    try {
      if ((doCommit = db.getAutoCommit()) == true) {
        db.setAutoCommit(false);
      }

      PreparedStatement pst = null;
      StringBuffer sql = new StringBuffer();
      sql.append(
          "UPDATE ticket_csstm_form SET " +
          "phone_response_time = ? , " +
          "engineer_response_time = ? , " +
          "follow_up_required = ? , " +
          "follow_up_description = ? , " +
          "alert_date = ? , " +
          "travel_towards_sc = ? , " +
          "labor_towards_sc = ? ");

      if (!override) {
        sql.append(" , modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , modifiedby = ? ");
      }
      sql.append("WHERE form_id = ? ");
      if (!override) {
        sql.append("AND modified = ? ");
      }

      pst = db.prepareStatement(sql.toString());
      int i = 0;
      pst.setString(++i, phoneResponseTime);
      pst.setString(++i, engineerResponseTime);
      pst.setBoolean(++i, followUpRequired);
      pst.setString(++i, followUpDescription);
      pst.setTimestamp(++i, alertDate);
      pst.setBoolean(++i, travelTowardsServiceContract);
      pst.setBoolean(++i, laborTowardsServiceContract);
      if (!override) {
        pst.setInt(++i, modifiedBy);
      }
      pst.setInt(++i, id);
      if (!override) {
        pst.setTimestamp(++i, modified);
      }
      resultCount = pst.executeUpdate();
      pst.close();
      if (resultCount == -1) {
        return resultCount;
      } else if (resultCount == 1) {
        // Save the description list (delete and then insert)
        TicketPerDayDescriptionList tmpPerDayDescriptionList = getTicketPerDayDescriptionList();
        tmpPerDayDescriptionList.deleteList(db, this.id);
        insertPerDayDescriptionList(db, tmpPerDayDescriptionList);
        //Update Remaining hours in the service contract
        if (request != null) {
          updateServiceContractHours(db);
        }
      }
      if (doCommit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace(System.out);
      if (doCommit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (doCommit) {
        db.setAutoCommit(true);
      }
    }
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public DependencyList processDependencies() {
    DependencyList dependencyList = new DependencyList();
    if (this.followUpRequired) {
      Calendar today = Calendar.getInstance();
      Timestamp timeStampToday = new Timestamp(today.getTimeInMillis());
      Dependency ticDependency = new Dependency();
      if (this.alertDate.compareTo(timeStampToday) > 0) {
        ticDependency.setName("Has Future Alert Date");
        ticDependency.setCount(1);
        ticDependency.setCanDelete(true);
        dependencyList.add(ticDependency);
      }
    }
    return dependencyList;
  }


  /**
   *  Description of the Method
   *
   *@param  db                 Description of the Parameter
   *@return                    Description of the Return Value
   *@exception  SQLException   Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {

    if (this.getId() == -1) {
      throw new SQLException("Note Id not specified.");
    }

    boolean doCommit = false;
    try {
      if ((doCommit = db.getAutoCommit()) == true) {
        db.setAutoCommit(false);
      }

      deletePerDayDescriptionList(db);

      PreparedStatement pst = null;
      pst = db.prepareStatement(
          " DELETE FROM ticket_csstm_form " +
          " WHERE form_id = ? ");

      pst.setInt(1, this.id);
      pst.execute();
      pst.close();

      //Reimburse the hours in the activity items to the service contract
      reimburseHoursAfterDelete(db);
      if (doCommit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace(System.out);
      if (doCommit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (doCommit) {
        db.setAutoCommit(true);
      }
    }

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insert(Connection db) throws SQLException {

    boolean doCommit = false;
    try {
      if ((doCommit = db.getAutoCommit()) == true) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = null;
      pst = db.prepareStatement(
          "INSERT INTO  ticket_csstm_form " +
          "(link_ticket_id, " +
          "phone_response_time, " +
          "engineer_response_time, " +
          "follow_up_required, " +
          "follow_up_description, " +
          "alert_date, " +
          "enteredby, " +
          "modifiedby, " +
          "travel_towards_sc, " +
          "labor_towards_sc) " +
          "VALUES (?,?,?,?,?,?,?,?,?,?)");

      int i = 0;
      pst.setInt(++i, linkTicketId);
      pst.setString(++i, phoneResponseTime);
      pst.setString(++i, engineerResponseTime);
      pst.setBoolean(++i, followUpRequired);
      pst.setString(++i, followUpDescription);
      pst.setTimestamp(++i, alertDate);
      pst.setInt(++i, enteredBy);
      pst.setInt(++i, modifiedBy);
      pst.setBoolean(++i, travelTowardsServiceContract);
      pst.setBoolean(++i, laborTowardsServiceContract);

      pst.execute();
      id = DatabaseUtils.getCurrVal(db, "ticket_csstm_form_form_id_seq");
      pst.close();

      // Save the description list
      TicketPerDayDescriptionList tmpPerDayDescriptionList = getTicketPerDayDescriptionList();
      insertPerDayDescriptionList(db, tmpPerDayDescriptionList);
      //Update Remaining hours in the service contract
      if (request != null) {
        updateServiceContractHours(db);
      }
      if (doCommit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace(System.out);
      if (doCommit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (doCommit) {
        db.setAutoCommit(true);
      }
    }
  }


  /**
   *  Inserts the per day activity item for the form
   *
   *@param  db                        Description of the Parameter
   *@param  tmpPerDayDescriptionList  Description of the Parameter
   *@exception  SQLException          Description of the Exception
   */
  public void insertPerDayDescriptionList(Connection db, TicketPerDayDescriptionList tmpPerDayDescriptionList) throws SQLException {
    Iterator itr = tmpPerDayDescriptionList.iterator();
    while (itr.hasNext()) {
      TicketPerDayDescription thisPerDayDescription = (TicketPerDayDescription) itr.next();
      thisPerDayDescription.setLinkFormId(this.id);
      thisPerDayDescription.insert(db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {

    id = rs.getInt("form_id");
    linkTicketId = rs.getInt("link_ticket_id");
    phoneResponseTime = rs.getString("phone_response_time");
    engineerResponseTime = rs.getString("engineer_response_time");
    followUpRequired = rs.getBoolean("follow_up_required");
    followUpDescription = rs.getString("follow_up_description");
    alertDate = rs.getTimestamp("alert_date");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    enabled = rs.getBoolean("enabled");
    travelTowardsServiceContract = rs.getBoolean("travel_towards_sc");
    laborTowardsServiceContract = rs.getBoolean("labor_towards_sc");

    firstActivityDate = rs.getTimestamp("firstdate");
    lastActivityDate = rs.getTimestamp("lastdate");
  }


  /**
   *  Updates the service contract hours in the service contract object and adds
   *  a record in th hours history if the activity is modified
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void updateServiceContractHours(Connection db) throws SQLException {

    if (relatedContractId == -1) {
      return;
    }

    String prevTravelTowardsServiceContract = (String) request.getParameter("prevTravelTowardsServiceContract");
    String prevLaborTowardsServiceContract = (String) request.getParameter("prevLaborTowardsServiceContract");
    String prevTravelMinutes = (String) request.getParameter("prevTotalTravelMinutes");
    String prevTravelHours = (String) request.getParameter("prevTotalTravelHours");
    String prevLaborMinutes = (String) request.getParameter("prevTotalLaborMinutes");
    String prevLaborHours = (String) request.getParameter("prevTotalLaborHours");
    int prevTotalHours = 0;
    int prevTotalMinutes = 0;
    double prevHoursUsed = 0;
    if (("on".equals(prevLaborTowardsServiceContract)) || ("true".equals(prevLaborTowardsServiceContract))) {
      prevTotalHours = prevTotalHours + ((prevLaborHours == null) ? 0 : Integer.parseInt(prevLaborHours));
      prevTotalMinutes = prevTotalMinutes + ((prevLaborMinutes == null) ? 0 : Integer.parseInt(prevLaborMinutes));
    }
    if (("on".equals(prevTravelTowardsServiceContract)) || ("true".equals(prevTravelTowardsServiceContract))) {
      prevTotalHours = prevTotalHours + ((prevTravelHours == null) ? 0 : Integer.parseInt(prevTravelHours));
      prevTotalMinutes = prevTotalMinutes + ((prevTravelMinutes == null) ? 0 : Integer.parseInt(prevTravelMinutes));
    }
    prevHoursUsed = prevTotalHours + ((prevTotalMinutes == 0) ? 0 : (prevTotalMinutes * 1.0) / 60);

    String travelTowardsServiceContract = (String) request.getParameter("travelTowardsServiceContract");
    String laborTowardsServiceContract = (String) request.getParameter("laborTowardsServiceContract");
    int travelMinutes = getTotalTravelMinutes();
    int travelHours = getTotalTravelHours();
    int laborMinutes = getTotalLaborMinutes();
    int laborHours = getTotalLaborHours();
    int totalHours = 0;
    int totalMinutes = 0;
    double hoursUsed = 0;
    if (("on".equals(laborTowardsServiceContract)) || ("true".equals(laborTowardsServiceContract))) {
      totalHours = totalHours + laborHours;
      totalMinutes = totalMinutes + laborMinutes;
    }
    if (("on".equals(travelTowardsServiceContract)) || ("true".equals(travelTowardsServiceContract))) {
      totalHours = totalHours + travelHours;
      totalMinutes = totalMinutes + travelMinutes;
    }
    hoursUsed = totalHours + ((totalMinutes == 0) ? 0 : (totalMinutes * 1.0) / 60);

    double netAdjustedHours = prevHoursUsed - hoursUsed;
    ServiceContract.updateHoursRemaining(db, this.relatedContractId, netAdjustedHours);

    ServiceContractHours sch = new ServiceContractHours();
    sch.setServiceContractId(this.relatedContractId);
    sch.setAdjustmentHours(netAdjustedHours);
    sch.setEnteredBy(getModifiedBy());
    sch.setModifiedBy(getModifiedBy());
    sch.setAdjustmentNotes("Adjusted due to an activity in ticket  #" + this.linkTicketId);
    sch.insert(db);
  }


  /**
   *  Reimburses the service contract hours when an activity is deleted and adds
   *  an entry in the hours change history
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void reimburseHoursAfterDelete(Connection db) throws SQLException {

    if (relatedContractId == -1) {
      return;
    }

    int totalHours = 0;
    int totalMinutes = 0;
    double hoursUsed = 0;
    if (this.laborTowardsServiceContract) {
      totalHours = totalHours + this.totalLaborHours;
      totalMinutes = totalMinutes + this.totalLaborMinutes;
    }
    if (this.travelTowardsServiceContract) {
      totalHours = totalHours + this.totalTravelHours;
      totalMinutes = totalMinutes + this.totalTravelMinutes;
    }
    hoursUsed = totalHours + ((totalMinutes == 0) ? 0 : (totalMinutes * 1.0) / 60);

    ServiceContract.updateHoursRemaining(db, this.relatedContractId, hoursUsed);

    ServiceContractHours sch = new ServiceContractHours();
    sch.setServiceContractId(this.relatedContractId);
    sch.setAdjustmentHours(hoursUsed);
    sch.setEnteredBy(getModifiedBy());
    sch.setModifiedBy(getModifiedBy());
    sch.setAdjustmentNotes("Reimbursement due to deletion of an activity in ticket  #" + this.linkTicketId);
    sch.insert(db);
  }
}

