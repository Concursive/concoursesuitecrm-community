package org.aspcfs.modules.communications.base;

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import com.zeroio.iteam.base.FileItemList;

/**
 *  Description of the Class
 *
 *@author     Wesley_S_Gillette
 *@created    November 16, 2001
 *@version    $Id$
 */
public class Campaign extends GenericBean {

  public final static int IDLE = 1;
  public final static int QUEUE = 2;
  public final static int STARTED = 3;
  public final static int FINISHED = 4;
  public final static int ERROR = 5;
  public final static int CANCELLED = 6;

  public final static String IDLE_TEXT = "Idle";
  public final static String QUEUE_TEXT = "In Queue";
  public final static String STARTED_TEXT = "Sending Messages";
  public final static String FINISHED_TEXT = "Messages Sent";
  public final static String ERROR_TEXT = "Error in Campaign";
  public final static String CANCELLED_TEXT = "Cancelled";

  private int id = -1;
  private String name = null;
  private String description = null;
  private int messageId = -1;
  private int groupId = -1;
  private int runId = -1;
  private int statusId = -1;
  private int owner = -1;
  private int modifiedBy = -1;
  private int enteredBy = -1;
  private java.sql.Date activeDate = null;
  private java.sql.Date inactiveDate = null;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private boolean enabled = true;
  private String status = null;
  private boolean active = false;
  private String replyTo = null;
  private String subject = null;
  private String message = null;
  private int sendMethodId = -1;

  private int files = 0;
  private String deliveryName = null;
  private ContactList contactList = null;
  private int recipientCount = -1;
  private int responseCount = -1;
  private int sentCount = -1;
  private String messageName = "";
  private int surveyId = -1;
  private String serverName = "";
  private String groupList = "";
  private int groupCount = -1;
  private int approvedBy = -1;
  private java.sql.Timestamp approvalDate = null;
  private LinkedHashMap groups = null;
  private java.sql.Timestamp lastResponse = null;


  /**
   *  Constructor for the Campaign object
   *
   *@since    1.1
   */
  public Campaign() { }


  /**
   *  Constructor for the Campaign object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public Campaign(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Gets the surveyId attribute of the Campaign object
   *
   *@return    The surveyId value
   */
  public int getSurveyId() {
    return surveyId;
  }


  /**
   *  Gets the activeSurveyId attribute of the Campaign object
   *
   *@return    The activeSurveyId value
   */
  public int getActiveSurveyId() {
    if (active) {
      return surveyId;
    } else {
      return -1;
    }
  }


  /**
   *  Sets the surveyId attribute of the Campaign object
   *
   *@param  surveyId  The new surveyId value
   */
  public void setSurveyId(int surveyId) {
    this.surveyId = surveyId;
  }


  /**
   *  Sets the surveyId attribute of the Campaign object
   *
   *@param  surveyId  The new surveyId value
   */
  public void setSurveyId(String surveyId) {
    this.surveyId = Integer.parseInt(surveyId);
  }


  /**
   *  Sets the groups attribute of the Campaign object
   *
   *@param  groups  The new groups value
   */
  public void setGroups(LinkedHashMap groups) {
    this.groups = groups;
  }


  /**
   *  Sets the responseCount attribute of the Campaign object
   *
   *@param  responseCount  The new responseCount value
   */
  public void setResponseCount(int responseCount) {
    this.responseCount = responseCount;
  }


  /**
   *  Sets the lastResponse attribute of the Campaign object
   *
   *@param  lastResponse  The new lastResponse value
   */
  public void setLastResponse(java.sql.Timestamp lastResponse) {
    this.lastResponse = lastResponse;
  }


  /**
   *  Gets the lastResponse attribute of the Campaign object
   *
   *@return    The lastResponse value
   */
  public java.sql.Timestamp getLastResponse() {
    return lastResponse;
  }


  /**
   *  Gets the lastResponseString attribute of the Campaign object
   *
   *@return    The lastResponseString value
   */
  public String getLastResponseString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(lastResponse);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the responseCount attribute of the Campaign object
   *
   *@return    The responseCount value
   */
  public int getResponseCount() {
    return responseCount;
  }


  /**
   *  Gets the groups attribute of the Campaign object
   *
   *@return    The groups value
   */
  public LinkedHashMap getGroups() {
    return groups;
  }


  /**
   *  Constructor for the Campaign object
   *
   *@param  db                Description of Parameter
   *@param  campaignId        Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public Campaign(Connection db, String campaignId) throws SQLException {
    queryRecord(db, Integer.parseInt(campaignId));
  }


  /**
   *  Constructor for the Campaign object
   *
   *@param  db                Description of the Parameter
   *@param  campaignId        Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public Campaign(Connection db, int campaignId) throws SQLException {
    queryRecord(db, campaignId);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  campaignId        Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int campaignId) throws SQLException {
    if (campaignId <= 0) {
      throw new SQLException("Invalid ID specified.");
    }
    PreparedStatement pst = null;
    ResultSet rs = null;

    String sql =
        "SELECT c.*, msg.name as messageName, dt.description as delivery " +
        "FROM campaign c " +
        "LEFT JOIN message msg ON (c.message_id = msg.id) " +
        "LEFT JOIN lookup_delivery_options dt ON (c.send_method_id = dt.code) " +
        "WHERE c.campaign_id = ? ";
    pst = db.prepareStatement(sql);
    pst.setInt(1, campaignId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Campaign record not found.");
    }
    buildRecipientCount(db);
    buildResponseCount(db);
    buildLastResponse(db);
    buildSurveyId(db);
    setGroupList(db);
    buildFileCount(db);
    buildGroups(db);
  }


  /**
   *  Sets the ContactList attribute of the Campaign object
   *
   *@param  contactList  The new ContactList value
   *@since               1.10
   */
  public void setContactList(ContactList contactList) {
    this.contactList = contactList;
  }


  /**
   *  Sets the RecipientCount attribute of the Campaign object
   *
   *@param  recipientCount  The new RecipientCount value
   *@since                  1.17
   */
  public void setRecipientCount(int recipientCount) {
    this.recipientCount = recipientCount;
  }


  /**
   *  Sets the RecipientCount attribute of the Campaign object
   *
   *@param  recipientCount  The new RecipientCount value
   *@since                  1.17
   */
  public void setRecipientCount(String recipientCount) {
    this.recipientCount = Integer.parseInt(recipientCount);
  }


  /**
   *  Sets the SentCount attribute of the Campaign object
   *
   *@param  tmp  The new SentCount value
   *@since       1.17
   */
  public void setSentCount(int tmp) {
    this.sentCount = tmp;
  }


  /**
   *  Sets the MessageName attribute of the Campaign object
   *
   *@param  messageName  The new MessageName value
   *@since               1.10
   */
  public void setMessageName(String messageName) {
    this.messageName = messageName;
  }


  /**
   *  Gets the serverName attribute of the Campaign object
   *
   *@return    The serverName value
   */
  public String getServerName() {
    return serverName;
  }


  /**
   *  Sets the serverName attribute of the Campaign object
   *
   *@param  serverName  The new serverName value
   */
  public void setServerName(String serverName) {
    this.serverName = serverName;
  }


  /**
   *  Sets the GroupList attribute of the Campaign object
   *
   *@param  groupList  The new GroupList value
   *@since             1.10
   */
  public void setGroupList(String groupList) {
    this.groupList = groupList;
  }


  /**
   *  Gets the approvedBy attribute of the Campaign object
   *
   *@return    The approvedBy value
   */
  public int getApprovedBy() {
    return approvedBy;
  }


  /**
   *  Sets the approvedBy attribute of the Campaign object
   *
   *@param  approvedBy  The new approvedBy value
   */
  public void setApprovedBy(int approvedBy) {
    this.approvedBy = approvedBy;
  }


  /**
   *  Sets the approvedBy attribute of the Campaign object
   *
   *@param  approvedBy  The new approvedBy value
   */
  public void setApprovedBy(String approvedBy) {
    this.approvedBy = Integer.parseInt(approvedBy);
  }


  /**
   *  Sets the id attribute of the Campaign object
   *
   *@param  tmp  The new id value
   *@since       1.1
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the Campaign object
   *
   *@param  tmp  The new id value
   *@since       1.1
   */
  public void setId(String tmp) {
    this.setId(Integer.parseInt(tmp));
  }


  /**
   *  Sets the Active attribute of the Campaign object
   *
   *@param  active  The new Active value
   *@since          1.1
   */
  public void setActive(boolean active) {
    this.active = active;
  }


  /**
   *  Sets the Active attribute of the Campaign object
   *
   *@param  tmp  The new Active value
   *@since       1.1
   */
  public void setActive(String tmp) {
    active = DatabaseUtils.parseBoolean(tmp);
    ;
  }


  /**
   *  Sets the replyTo attribute of the Campaign object
   *
   *@param  tmp  The new replyTo value
   */
  public void setReplyTo(String tmp) {
    this.replyTo = tmp;
  }


  /**
   *  Sets the subject attribute of the Campaign object
   *
   *@param  tmp  The new subject value
   */
  public void setSubject(String tmp) {
    this.subject = tmp;
  }


  /**
   *  Sets the message attribute of the Campaign object
   *
   *@param  tmp  The new message value
   */
  public void setMessage(String tmp) {
    this.message = tmp;
  }


  /**
   *  Sets the sendMethodId attribute of the Campaign object
   *
   *@param  tmp  The new sendMethodId value
   */
  public void setSendMethodId(int tmp) {
    this.sendMethodId = tmp;
  }


  /**
   *  Sets the sendMethodId attribute of the Campaign object
   *
   *@param  tmp  The new sendMethodId value
   */
  public void setSendMethodId(String tmp) {
    this.sendMethodId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the name attribute of the Campaign object
   *
   *@param  tmp  The new name value
   *@since       1.1
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the description attribute of the Campaign object
   *
   *@param  tmp  The new description value
   *@since       1.1
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the Status attribute of the Campaign object
   *
   *@param  status  The new Status value
   *@since          1.17
   */
  public void setStatus(String status) {
    this.status = status;
  }


  /**
   *  Sets the messageId attribute of the Campaign object
   *
   *@param  tmp  The new messageId value
   *@since       1.10
   */
  public void setMessageId(int tmp) {
    this.messageId = tmp;
  }


  /**
   *  Sets the MessageId attribute of the Campaign object
   *
   *@param  tmp  The new MessageId value
   *@since       1.10
   */
  public void setMessageId(String tmp) {
    this.messageId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the groupId attribute of the Campaign object
   *
   *@param  tmp  The new groupId value
   *@since       1.10
   */
  public void setGroupId(int tmp) {
    this.groupId = tmp;
  }


  /**
   *  Sets the groupId attribute of the Campaign object
   *
   *@param  tmp  The new groupId value
   */
  public void setGroupId(String tmp) {
    this.groupId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the runId attribute of the Campaign object
   *
   *@param  tmp  The new runId value
   *@since       1.10
   */
  public void setRunId(int tmp) {
    this.runId = tmp;
  }


  /**
   *  Sets the runId attribute of the Campaign object
   *
   *@param  tmp  The new runId value
   */
  public void setRunId(String tmp) {
    this.runId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the statusId attribute of the Campaign object
   *
   *@param  tmp  The new statusId value
   *@since       1.17
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;

    switch (statusId) {
        case IDLE:
          status = IDLE_TEXT;
          break;
        case QUEUE:
          status = QUEUE_TEXT;
          break;
        case STARTED:
          status = STARTED_TEXT;
          break;
        case ERROR:
          status = "Unspecified error";
          break;
        case FINISHED:
          status = FINISHED_TEXT;
          break;
        default:
          break;
    }
  }


  /**
   *  Sets the statusId attribute of the Campaign object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.setStatusId(Integer.parseInt(tmp));
  }


  /**
   *  Sets the owner attribute of the Campaign object
   *
   *@param  tmp  The new owner value
   *@since       1.10
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }


  /**
   *  Sets the owner attribute of the Campaign object
   *
   *@param  tmp  The new owner value
   */
  public void setOwner(String tmp) {
    this.owner = Integer.parseInt(tmp);
  }


  /**
   *  Sets the ActiveDate attribute of the Campaign object
   *
   *@param  tmp  The new ActiveDate value
   *@since       1.10
   */
  public void setActiveDate(java.sql.Date tmp) {
    this.activeDate = tmp;
  }


  /**
   *  Sets the entered attribute of the Ticket object
   *
   *@param  tmp  The new entered value
   */
  public void setActiveDate(String tmp) {
    this.activeDate = DateUtils.parseDateString(tmp);
  }


  /**
   *  Sets the inactiveDate attribute of the Campaign object
   *
   *@param  tmp  The new inactiveDate value
   */
  public void setInactiveDate(java.sql.Date tmp) {
    this.inactiveDate = tmp;
  }


  /**
   *  Sets the inactiveDate attribute of the Campaign object
   *
   *@param  tmp  The new inactiveDate value
   */
  public void setInactiveDate(String tmp) {
    this.inactiveDate = DateUtils.parseDateString(tmp);
  }


  /**
   *  Sets the Entered attribute of the Campaign object
   *
   *@param  tmp  The new Entered value
   *@since       1.17
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the Ticket object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the modified attribute of the Ticket object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the Campaign object
   *
   *@param  tmp  The new enteredBy value
   *@since       1.17
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the Campaign object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Modified attribute of the Campaign object
   *
   *@param  tmp  The new Modified value
   *@since       1.17
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the Campaign object
   *
   *@param  tmp  The new modifiedBy value
   *@since       1.17
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the Campaign object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the Campaign object
   *
   *@param  tmp  The new enabled value
   *@since       1.1
   */
  public void setEnabled(boolean tmp) {
    enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the Campaign object
   *
   *@param  tmp  The new enabled value
   *@since       1.1
   */
  public void setEnabled(String tmp) {
    enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the deliveryName attribute of the Campaign object
   *
   *@param  deliveryName  The new deliveryName value
   */
  public void setDeliveryName(String deliveryName) {
    this.deliveryName = deliveryName;
  }


  /**
   *  Sets the Groups attribute of the Campaign object
   *
   *@param  request  The new Groups value
   *@since           1.10
   */
  public void setGroups(HttpServletRequest request) {
    StringBuffer sb = new StringBuffer();

    int selectCount = 0;
    String item = null;
    while ((item = request.getParameter("select" + (++selectCount))) != null) {
      if ("on".equalsIgnoreCase(request.getParameter("select" + selectCount + "check"))) {
        if (sb.length() > 0) {
          sb.append("*");
        }
        sb.append(item);
      }
    }

    groupList = sb.toString();
  }


  /**
   *  Sets the GroupList attribute of the Campaign object
   *
   *@param  db                The new GroupList value
   *@exception  SQLException  Description of Exception
   *@since                    1.10
   */
  public void setGroupList(Connection db) throws SQLException {
    this.groupCount = 0;
    StringBuffer groups = new StringBuffer();
    boolean b = false;

    PreparedStatement pst = db.prepareStatement(
        "SELECT group_id " +
        "FROM campaign_list_groups " +
        "WHERE campaign_id = ? ");
    pst.setInt(1, this.getId());
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      ++groupCount;
      if (b) {
        groups.append(", ");
      } else {
        b = true;
      }
      groups.append(rs.getInt("group_id"));
    }
    this.setGroupList(groups.toString());
    rs.close();
    pst.close();
  }


  /**
   *  Gets the modified attribute of the Campaign object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the sendMethodId attribute of the Campaign object
   *
   *@return    The sendMethodId value
   */
  public int getSendMethodId() {
    return sendMethodId;
  }


  /**
   *  Gets the deliveryName attribute of the Campaign object
   *
   *@return    The deliveryName value
   */
  public String getDeliveryName() {
    return deliveryName;
  }


  /**
   *  Gets the RecipientCount attribute of the Campaign object
   *
   *@return    The RecipientCount value
   *@since     1.10
   */
  public int getRecipientCount() {
    return recipientCount;
  }


  /**
   *  Gets the SentCount attribute of the Campaign object
   *
   *@return    The SentCount value
   *@since     1.10
   */
  public int getSentCount() {
    return sentCount;
  }


  /**
   *  Gets the ContactList attribute of the Campaign object
   *
   *@return    The ContactList value
   *@since     1.10
   */
  public ContactList getContactList() {
    if (contactList == null) {
      contactList = new ContactList();
    }
    return contactList;
  }


  /**
   *  Gets the MessageName attribute of the Campaign object
   *
   *@return    The MessageName value
   *@since     1.10
   */
  public String getMessageName() {
    return messageName;
  }


  /**
   *  Gets the Active attribute of the Campaign object
   *
   *@return    The Active value
   *@since     1.10
   */
  public boolean getActive() {
    return active;
  }


  /**
   *  Gets the ActiveYesNo attribute of the Campaign object
   *
   *@return    The ActiveYesNo value
   *@since     1.10
   */
  public String getActiveYesNo() {
    if (active == true) {
      return ("Yes");
    } else {
      return ("No");
    }
  }


  /**
   *  Gets the Active attribute of the Campaign object
   *
   *@param  tmp  Description of Parameter
   *@return      The Active value
   *@since       1.10
   */
  public String getActive(String tmp) {
    if (active == true) {
      return tmp;
    } else {
      return "";
    }
  }


  /**
   *  Gets the ActiveDate attribute of the Campaign object
   *
   *@return    The ActiveDate value
   *@since     1.10
   */
  public java.sql.Date getActiveDate() {
    return activeDate;
  }


  /**
   *  Gets the ActiveDateString attribute of the Campaign object
   *
   *@return    The ActiveDateString value
   *@since     1.10
   */
  public String getActiveDateString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(activeDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the Status attribute of the Campaign object
   *
   *@return    The Status value
   *@since     1.17
   */
  public String getStatus() {
    return status;
  }


  /**
   *  Gets the GroupList attribute of the Campaign object
   *
   *@return    The GroupList value
   *@since     1.10
   */
  public String getGroupList() {
    return groupList;
  }


  /**
   *  Gets the GroupCount attribute of the Campaign object
   *
   *@return    The GroupCount value
   *@since     1.10
   */
  public int getGroupCount() {
    return groupCount;
  }



  /**
   *  Gets the id attribute of the Campaign object
   *
   *@return    The id value
   *@since     1.1
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the name attribute of the Campaign object
   *
   *@return    The name value
   *@since     1.1
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the description attribute of the Campaign object
   *
   *@return    The description value
   *@since     1.1
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the messageId attribute of the Campaign object
   *
   *@return    The messageId value
   *@since     1.1
   */
  public int getMessageId() {
    return messageId;
  }


  /**
   *  Gets the replyTo attribute of the Campaign object
   *
   *@return    The replyTo value
   */
  public String getReplyTo() {
    return replyTo;
  }


  /**
   *  Gets the message attribute of the Campaign object
   *
   *@return    The message value
   */
  public String getMessage() {
    return message;
  }


  /**
   *  Gets the subject attribute of the Campaign object
   *
   *@return    The subject value
   */
  public String getSubject() {
    return subject;
  }


  /**
   *  Gets the groupId attribute of the Campaign object
   *
   *@return    The groupId value
   *@since     1.1
   */
  public int getGroupId() {
    return groupId;
  }


  /**
   *  Gets the runId attribute of the Campaign object
   *
   *@return    The runId value
   *@since     1.1
   */
  public int getRunId() {
    return runId;
  }


  /**
   *  Gets the statusId attribute of the Campaign object
   *
   *@return    The statusId value
   *@since     1.17
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the owner attribute of the Campaign object
   *
   *@return    The owner value
   *@since     1.10
   */
  public int getOwner() {
    return owner;
  }


  /**
   *  Gets the enteredBy attribute of the Campaign object
   *
   *@return    The enteredBy value
   *@since     1.1
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modifiedBy attribute of the Campaign object
   *
   *@return    The modifiedBy value
   *@since     1.1
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the ModifiedString attribute of the Campaign object
   *
   *@return    The ModifiedString value
   *@since     1.17
   */
  public String getModifiedString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(modified);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the Entered attribute of the Campaign object
   *
   *@return    The Entered value
   *@since     1.17
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the EnteredString attribute of the Campaign object
   *
   *@return    The EnteredString value
   *@since     1.17
   */
  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the enabled attribute of the Campaign object
   *
   *@return    The enabled value
   *@since     1.1
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the Range attribute of the Campaign object
   *
   *@return    The Range value
   *@since     1.10
   */
  public String getRange() {
    StringTokenizer strt = new StringTokenizer(this.getGroupList(), "*");
    String range = "";
    boolean b = true;

    while (strt.hasMoreTokens()) {
      String tmpString = (String) strt.nextToken();

      if (b == true) {
        range = range + tmpString;
        b = false;
      } else {
        range = range + ", " + tmpString;
      }
    }

    return range;
  }


  /**
   *  Gets the MessageChecked attribute of the Campaign object
   *
   *@param  tmp  Description of Parameter
   *@return      The MessageChecked value
   *@since       1.17
   */
  public String getMessageChecked(int tmp) {
    if (messageId == tmp) {
      return "checked";
    } else {
      return "";
    }
  }


  /**
   *  Gets the GroupChecked attribute of the Campaign object
   *
   *@param  tmp  Description of Parameter
   *@return      The GroupChecked value
   *@since       1.17
   */
  public String getGroupChecked(int tmp) {
    if (groupList != null || groupList.length() > 0) {
      StringTokenizer st = new StringTokenizer(groupList, "*");

      while (st.hasMoreTokens()) {
        String tmpString = (String) st.nextToken();
        if (tmpString.equals(String.valueOf(tmp))) {
          return "checked";
        }
      }
    }
    return "";
  }


  /**
   *  Gets the ReadyToActivate attribute of the Campaign object
   *
   *@return    The ReadyToActivate value
   *@since     1.17
   */
  public boolean isReadyToActivate() {
    return (hasGroups() && hasMessage() && hasDetails());
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   *@since     1.17
   */
  public boolean hasGroups() {
    return (groupList != null && !groupList.equals(""));
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   *@since     1.17
   */
  public boolean hasMessage() {
    return (messageId > 0);
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public boolean hasSurvey() {
    return (surveyId > 0);
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   *@since     1.17
   */
  public boolean hasDetails() {
    return (activeDate != null);
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   *@since     1.17
   */
  public boolean hasRun() {
    return (statusId == FINISHED);
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   */
  public boolean hasFiles() {
    return (files > 0);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.10
   */
  public void buildRecipientCount(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(id) " +
        "FROM scheduled_recipient sr " +
        "WHERE campaign_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      this.setRecipientCount(rs.getInt(1));
    }
    rs.close();
    pst.close();
  }


  /**
   *  Builds the total number of people who responded to the survey Note :
   *  counts a person only once.
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildResponseCount(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(sr.contact_id) " +
        "FROM active_survey_responses sr, active_survey sa " +
        "WHERE campaign_id = ? AND (sr.active_survey_id = sa.active_survey_id) ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    int count = 0;
    while (rs.next()) {
      this.setResponseCount(rs.getInt(1));
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildLastResponse(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT max(sr.entered) as lastresponse " +
        "FROM active_survey_responses sr, active_survey sa " +
        "WHERE campaign_id = ? AND (sr.active_survey_id = sa.active_survey_id) ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    int count = 0;
    if (rs.next()) {
      this.setLastResponse(rs.getTimestamp("lastresponse"));
    }
    rs.close();
    pst.close();
  }


  /**
   *  Retrieves the file id for this campaign. A file will exist if a campaign
   *  gets executed and is configured to output a file. For example, when the
   *  "letter" option is selected, a .zip file gets created in which the user
   *  can download.
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void buildFileCount(Connection db) throws SQLException {
    files = FileItemList.retrieveRecordCount(db, Constants.COMMUNICATIONS, id);
  }


  /**
   *  Builds all groups associated with the campaign.
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildGroups(Connection db) throws SQLException {
    ArrayList criteriaList = null;
    groups = new LinkedHashMap();
    PreparedStatement pst = db.prepareStatement(
        "SELECT groupname, groupcriteria " +
        "FROM active_campaign_groups " +
        "WHERE campaign_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      String groupName = rs.getString("groupName");
      if (groups.containsKey(groupName)) {
        criteriaList = (ArrayList) groups.get(groupName);
      } else {
        criteriaList = new ArrayList();
        groups.put(groupName, criteriaList);
      }
      criteriaList.add(rs.getString("groupcriteria"));
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildSurveyId(Connection db) throws SQLException {
    if (active) {
      surveyId = ActiveSurvey.getId(db, this.id);
    } else {
      surveyId = Survey.getId(db, this.id);
    }
  }


  /**
   *  Gets the ContactList attribute of the Campaign object
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.10
   */
  public void insertContactList(Connection db) throws SQLException {
    Iterator j = contactList.iterator();
    while (j.hasNext()) {
      Contact thisContact = (Contact) j.next();
      Recipient thisRecipient = new Recipient();
      thisRecipient.setCampaignId(this.getId());
      thisRecipient.setContactId(thisContact.getId());
      thisRecipient.insert(db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  userId            Description of the Parameter
   *@param  userRangeId       Description of the Parameter
   *@exception  SQLException  Description of Exception
   */
  public void insertRecipients(Connection db, int userId, String userRangeId) throws SQLException {
    SearchCriteriaListList groupData = new SearchCriteriaListList();
    groupData.setCampaignId(this.id);
    groupData.buildList(db);
    Iterator i = groupData.iterator();
    while (i.hasNext()) {
      SearchCriteriaList thisGroup = (SearchCriteriaList) i.next();
      ContactList groupContacts = new ContactList();
      groupContacts.setScl(thisGroup, userId, userRangeId);
      groupContacts.setBuildDetails(false);
      groupContacts.setCheckExcludedFromCampaign(this.getId());
      groupContacts.buildList(db);

      if (System.getProperty("DEBUG") != null) {
        System.out.println("Campaign-> GroupContacts has " + groupContacts.size() + " items");
      }
      Iterator j = groupContacts.iterator();
      while (j.hasNext()) {
        Contact thisContact = (Contact) j.next();
        if (thisContact.excludedFromCampaign() == false) {
          Recipient thisRecipient = new Recipient();
          thisRecipient.setCampaignId(this.getId());
          thisRecipient.setContactId(thisContact.getId());
          thisRecipient.insert(db);
        }
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.5
   */
  public boolean insert(Connection db) throws SQLException {
    if (!isValid(db)) {
      return false;
    }

    StringBuffer sql = new StringBuffer();
    try {
      db.setAutoCommit(false);
      sql.append(
          "INSERT INTO campaign " +
          "(enteredby, modifiedby, name, message_id, " +
          "reply_addr, subject, message, send_method_id, " +
          "inactive_date, approval_date, ");
      if (entered != null) {
        sql.append("entered, ");
      }
      sql.append("approvedby ) ");
      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
      if (entered != null) {
        sql.append("?, ");
      }
      sql.append("?) ");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getModifiedBy());
      pst.setString(++i, this.getName());
      pst.setInt(++i, this.getMessageId());
      pst.setString(++i, replyTo);
      pst.setString(++i, subject);
      pst.setString(++i, message);
      pst.setInt(++i, sendMethodId);
      pst.setDate(++i, inactiveDate);
      pst.setTimestamp(++i, approvalDate);
      if (entered != null) {
        pst.setTimestamp(++i, entered);
      }
      if (approvedBy > -1) {
        pst.setInt(++i, this.getApprovedBy());
      } else {
        pst.setNull(++i, java.sql.Types.INTEGER);
      }
      pst.execute();
      pst.close();

      id = DatabaseUtils.getCurrVal(db, "campaign_campaign_id_seq");
      this.update(db, true);

      if (this.getGroupList() != null && !this.getGroupList().equals("")) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Campaign-> GroupList: " + this.getGroupList());
        }
        StringBuffer groupSql = new StringBuffer();
        StringTokenizer strt = new StringTokenizer(this.getGroupList(), "*");
        PreparedStatement pstx = null;

        while (strt.hasMoreTokens()) {
          String tmpString = (String) strt.nextToken();

          groupSql.append(
              "INSERT INTO campaign_list_groups " +
              "(campaign_id, group_id ) " +
              "VALUES (?, ?) ");

          int j = 0;
          pstx = db.prepareStatement(groupSql.toString());
          pstx.setInt(++j, this.getId());
          pstx.setInt(++j, Integer.parseInt(tmpString));
          pstx.execute();
          pstx.close();
        }
      }

      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.17
   */
  public boolean insertGroups(Connection db) throws SQLException {
    try {
      db.setAutoCommit(false);

      deleteAllGroups(db);

      if (this.getGroupList() != null && !this.getGroupList().equals("")) {
        StringTokenizer strt = new StringTokenizer(this.getGroupList(), "*");
        while (strt.hasMoreTokens()) {
          String tmpString = (String) strt.nextToken();

          PreparedStatement pstx = null;
          String groupSql =
              "INSERT INTO campaign_list_groups " +
              "(campaign_id, group_id ) " +
              "VALUES (?, ?) ";
          int j = 0;
          pstx = db.prepareStatement(groupSql);
          pstx.setInt(++j, this.getId());
          pstx.setInt(++j, Integer.parseInt(tmpString));
          pstx.execute();
          pstx.close();
        }
      }

      Statement st = db.createStatement();
      st.executeUpdate(
          "UPDATE campaign " +
          "SET modified = CURRENT_TIMESTAMP " +
          "WHERE campaign_id = " + id);
      //"WHERE id = " + id);
      st.close();

      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean deleteAllGroups(Connection db) throws SQLException {
    PreparedStatement pstx = null;
    String groupSql =
        "DELETE FROM campaign_list_groups " +
        "WHERE campaign_id = ? ";
    int j = 0;
    pstx = db.prepareStatement(groupSql);
    pstx.setInt(++j, this.getId());
    pstx.execute();
    pstx.close();
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.17
   */
  public boolean deleteGroups(Connection db) throws SQLException {
    try {
      db.setAutoCommit(false);

      if (this.getGroupList() != null && !this.getGroupList().equals("")) {
        StringTokenizer strt = new StringTokenizer(this.getGroupList(), "*");

        while (strt.hasMoreTokens()) {
          String tmpString = (String) strt.nextToken();

          PreparedStatement pstx = null;
          StringBuffer groupSql = new StringBuffer();
          groupSql.append(
              "DELETE FROM campaign_list_groups " +
              "WHERE campaign_id = ? " +
              "AND group_id = ? ");
          int j = 0;
          pstx = db.prepareStatement(groupSql.toString());
          pstx.setInt(++j, this.getId());
          pstx.setInt(++j, Integer.parseInt(tmpString));
          pstx.execute();
          pstx.close();
        }

        Statement st = db.createStatement();
        st.executeUpdate(
            "UPDATE campaign " +
            "SET modified = CURRENT_TIMESTAMP " +
            "WHERE campaign_id = " + id);
        //"WHERE id = " + id);
        st.close();
      }
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.5
   */
  public int update(Connection db) throws SQLException {
    int resultCount = -1;

    if (!isValid(db)) {
      return -1;
    }

    try {
      db.setAutoCommit(false);
      resultCount = this.update(db, false);
      db.commit();
    } catch (Exception e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.5
   */
  public boolean delete(Connection db) throws SQLException {
    PreparedStatement pst = null;
    SQLException message = null;
    try {
      db.setAutoCommit(false);
      pst = db.prepareStatement(
          "DELETE FROM campaign_list_groups WHERE campaign_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      
      pst = db.prepareStatement(
          "DELETE FROM scheduled_recipient WHERE campaign_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      
      pst = db.prepareStatement(
          "DELETE FROM campaign_run WHERE campaign_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      
      pst = db.prepareStatement(
          "DELETE FROM excluded_recipient WHERE campaign_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      
      //Delete any inactive survey links
      pst = db.prepareStatement(
          "DELETE FROM campaign_survey_link WHERE campaign_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      
      //Delete the attached survey
      int activeSurveyId = (ActiveSurvey.getId(db, id));
      if (activeSurveyId > -1) {
        ActiveSurvey thisSurvey = new ActiveSurvey(db, activeSurveyId);
        thisSurvey.delete(db);
      }

      pst = db.prepareStatement(
          "DELETE FROM campaign WHERE campaign_id = ? ");
      //"DELETE FROM campaign WHERE id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      message = e;
    } finally {
      db.setAutoCommit(true);
    }
    if (message != null) {
      throw new SQLException(message.getMessage());
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.17
   */
  public int cancel(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Campaign ID was not specified");
    }

    boolean commit = true;
    int resultCount = 0;
    PreparedStatement pst = null;

    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      pst = db.prepareStatement(
          "UPDATE campaign " +
          "SET status_id = ?, " +
          "status = ?, " +
          "active = ?, " +
          "modifiedby = ?, " +
          "modified = CURRENT_TIMESTAMP " +
          "WHERE campaign_id = ? " +
          "AND status_id IN (" + QUEUE + ", " + ERROR + ") ");
      int i = 0;
      pst.setInt(++i, CANCELLED);
      pst.setString(++i, CANCELLED_TEXT);
      pst.setBoolean(++i, false);
      pst.setInt(++i, modifiedBy);
      pst.setInt(++i, id);
      resultCount = pst.executeUpdate();

      if (resultCount == 1) {
        pst = db.prepareStatement(
            "DELETE FROM scheduled_recipient " +
            "WHERE campaign_id = ? " +
            "AND sent_date IS NULL ");
        pst.setInt(1, id);
        pst.execute();

        //Remove attached survey if campaign has one
        int activeSurveyId = ActiveSurvey.getId(db, id);
        if (activeSurveyId > -1) {
          ActiveSurvey activeSurvey = new ActiveSurvey(db, activeSurveyId);
          activeSurvey.delete(db);
        }
      }

      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.toString());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
      pst.close();
    }
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  userId            Description of the Parameter
   *@param  userRangeId       Description of the Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.17
   */
  public int activate(Connection db, int userId, String userRangeId) throws SQLException {
    int resultCount = 0;
    if (this.getId() == -1) {
      throw new SQLException("Campaign ID was not specified");
    }
    SQLException message = null;
    PreparedStatement pst = null;

    try {
      db.setAutoCommit(false);

      //See if the campaign is not already active
      pst = db.prepareStatement(
          "UPDATE campaign " +
          "SET status_id = ?, " +
          "status = ?, " +
          "modifiedby = ?, " +
          "modified = CURRENT_TIMESTAMP " +
          "WHERE campaign_id = ? " +
      //"WHERE id = ? " +
          "AND modified = ? " +
          "AND active = ? ");
      int i = 0;
      pst.setInt(++i, QUEUE);
      pst.setString(++i, QUEUE_TEXT);
      pst.setInt(++i, modifiedBy);
      pst.setInt(++i, id);
      pst.setTimestamp(++i, modified);
      pst.setBoolean(++i, false);
      resultCount = pst.executeUpdate();
      pst.close();

      //Activate the campaign...
      if (resultCount == 1) {
        active = true;
        //Lock in the recipients
        insertRecipients(db, userId, userRangeId);

        //Lock in the survey
        if (this.surveyId > -1) {
          Survey thisSurvey = new Survey(db, surveyId);
          ActiveSurvey activeSurvey = new ActiveSurvey(thisSurvey);
          activeSurvey.setEnteredBy(userId);
          activeSurvey.setModifiedBy(userId);
          activeSurvey.setCampaignId(id);
          activeSurvey.insert(db);
          this.surveyId = activeSurvey.getId();
        }

        //Lock in the message
        Message thisMessage = new Message(db, this.getMessageId());

        //Lock in the groups & criteria
        SearchCriteriaListList thisList = new SearchCriteriaListList();
        thisList.setCampaignId(id);
        thisList.buildList(db);
        lockGroupCriteria(thisList, db);

        //Replace tags
        Template template = new Template();
        if (this.surveyId > -1) {
          template.addParseElement("${survey_url}", "<a href=\"http://" + this.getServerName() + "/ProcessSurvey.do?id=${surveyId=" + this.getActiveSurveyId() + "}\">http://" + this.getServerName() + "/ProcessSurvey.do?id=${surveyId=" + this.getActiveSurveyId() + "}</a>");
          if (thisMessage.getMessageText().indexOf("${survey_url}") == -1) {
            template.setText(thisMessage.getMessageText() + "<br><br>You can take the survey at the following web-site: ${survey_url}");
          } else {
            template.setText(thisMessage.getMessageText());
          }
        } else {
          template.setText(thisMessage.getMessageText());
        }

        //Finalize the campaign activation
        pst = db.prepareStatement(
            "UPDATE campaign " +
            "SET active = ?, " +
            "reply_addr = ?, " +
            "subject = ?, " +
            "message = ?, " +
            "modifiedby = ?, " +
            "modified = CURRENT_TIMESTAMP " +
            "WHERE campaign_id = ? ");
        //"WHERE id = ? ");
        i = 0;
        pst.setBoolean(++i, true);
        pst.setString(++i, thisMessage.getReplyTo());
        pst.setString(++i, thisMessage.getMessageSubject());
        pst.setString(++i, template.getParsedText());
        pst.setInt(++i, modifiedBy);
        pst.setInt(++i, id);
        resultCount = pst.executeUpdate();
        pst.close();

        db.commit();
      }
    } catch (SQLException e) {
      message = e;
      db.rollback();
    } catch (Exception ee) {
      db.rollback();
      ee.printStackTrace(System.out);
    } finally {
      db.setAutoCommit(true);
    }

    if (message != null) {
      throw new SQLException(message.getMessage());
    }
    return resultCount;
  }


  /**
   *  Makes a copy of the groups associated with this Campaign.
   *
   *@param  groups            Description of the Parameter
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void lockGroupCriteria(SearchCriteriaListList groups, Connection db) throws SQLException {
    Iterator i = groups.iterator();
    while (i.hasNext()) {
      SearchCriteriaList thisList = (SearchCriteriaList) i.next();
      HashMap thisArray = thisList.getCriteriaTextArray();
      Iterator j = thisArray.keySet().iterator();
      while (j.hasNext()) {
        String criteria = (String) thisArray.get(j.next());
        int k = 0;
        PreparedStatement pst =
            db.prepareStatement("INSERT INTO active_campaign_groups " +
            "(campaign_id, groupname, groupcriteria )" +
            "VALUES (?, ?, ?) ");
        pst.setInt(++k, id);
        pst.setString(++k, thisList.getGroupName());
        pst.setString(++k, criteria);
        pst.execute();
        pst.close();
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.17
   */
  public int insertRun(Connection db) throws SQLException {
    if (!isValid(db)) {
      return -1;
    }
    CampaignRun thisRun = new CampaignRun();
    thisRun.setCampaignId(this.getId());
    thisRun.setTotalContacts(this.getRecipientCount());
    thisRun.setTotalSent(this.getSentCount());
    thisRun.insert(db);
    return thisRun.getId();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.17
   */
  public int updateDetails(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Campaign ID was not specified");
    }

    int resultCount = 0;

    PreparedStatement pst = null;
    pst = db.prepareStatement(
        "UPDATE campaign " +
        "SET name = ?, " +
        "description = ?, " +
        "modifiedby = ?, " +
        "modified = CURRENT_TIMESTAMP " +
        "WHERE campaign_id = " + id);
    //"WHERE id = " + id);
    int i = 0;
    pst.setString(++i, name);
    pst.setString(++i, description);
    pst.setInt(++i, modifiedBy);
    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.17
   */
  public int updateMessage(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Campaign ID was not specified");
    }

    int resultCount = 0;

    PreparedStatement pst = null;
    int i = 0;
    pst = db.prepareStatement(
        "UPDATE campaign " +
        "SET message_id = ?, " +
        "reply_addr = null, " +
        "subject = null, " +
        "message = null, " +
        "modifiedby = ?, " +
        "modified = CURRENT_TIMESTAMP " +
        "WHERE campaign_id = ? ");
    //"WHERE id = ? ");
    pst.setInt(++i, messageId);
    pst.setInt(++i, modifiedBy);
    pst.setInt(++i, id);
    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int updateSurvey(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Campaign ID was not specified");
    }

    int resultCount = 0;

    PreparedStatement pst = null;
    try {
      db.setAutoCommit(false);
      pst = db.prepareStatement(
          "UPDATE campaign " +
          "SET modifiedby = ?, " +
          "modified = CURRENT_TIMESTAMP " +
          "WHERE campaign_id = ? ");
      pst.setInt(1, modifiedBy);
      pst.setInt(2, id);
      resultCount = pst.executeUpdate();

      pst = db.prepareStatement("DELETE FROM campaign_survey_link WHERE campaign_id = ? ");
      pst.setInt(1, id);
      pst.execute();

      if (surveyId > -1) {
        pst = db.prepareStatement(
            "INSERT INTO campaign_survey_link " +
            "(campaign_id, survey_id) VALUES (?, ?) ");
        pst.setInt(1, id);
        pst.setInt(2, surveyId);
        pst.execute();
      }

      pst.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.17
   */
  public int updateSchedule(Connection db) throws SQLException {
    int resultCount = 0;

    if (this.getId() == -1) {
      throw new SQLException("Campaign ID was not specified");
    }

    PreparedStatement pst = null;
    int i = 0;
    pst = db.prepareStatement(
        "UPDATE campaign " +
        "SET message_id = ?, " +
        "active_date = ?, " +
        "send_method_id = ?, " +
        "modified = CURRENT_TIMESTAMP " +
        "WHERE campaign_id = " + id);
    //"WHERE id = " + id);
    pst.setInt(++i, messageId);
    pst.setDate(++i, activeDate);
    pst.setInt(++i, sendMethodId);
    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
  }


  /**
   *  Gets the valid attribute of the Message object
   *
   *@param  db                Description of Parameter
   *@return                   The valid value
   *@exception  SQLException  Description of Exception
   *@since                    1.15
   */
  protected boolean isValid(Connection db) throws SQLException {
    errors.clear();

    if (name == null || name.trim().equals("")) {
      errors.put("nameError", "Campaign name is required");
    }

    if (hasErrors()) {
      return false;
    } else {
      return true;
    }
  }



  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  override          Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.5
   */
  protected int update(Connection db, boolean override) throws SQLException {
    int resultCount = 0;

    if (this.getId() == -1) {
      throw new SQLException("Campaign ID was not specified");
    }

    if (status == null) {
      if (active) {
        this.setStatusId(QUEUE);
      } else {
        this.setStatusId(IDLE);
      }
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE campaign " +
        "SET description = ?, active_date = ?, " +
        "enabled = ?, ");
    if (override && modified != null) {
      sql.append("modified = ?, ");
    } else {
      sql.append("modified = CURRENT_TIMESTAMP, ");
    }
    sql.append("modifiedby = ?, " +
        "active = ?, status_id = ?, status = ?, message_id = ? " +
        "WHERE campaign_id = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, this.getDescription());
    if (activeDate == null) {
      pst.setNull(++i, java.sql.Types.DATE);
    } else {
      pst.setDate(++i, this.getActiveDate());
    }
    pst.setBoolean(++i, this.getEnabled());
    if (override && modified != null) {
      pst.setTimestamp(++i, modified);
    }
    pst.setInt(++i, this.getModifiedBy());
    pst.setBoolean(++i, this.getActive());
    pst.setInt(++i, this.getStatusId());
    pst.setString(++i, this.getStatus());
    pst.setInt(++i, this.getMessageId());
    pst.setInt(++i, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();

    //The original survey is no longer needed
    if (statusId == FINISHED) {
      Survey.removeLink(db, this.id);
    }

    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.5
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //campaign table
    this.setId(rs.getInt("campaign_id"));
    name = rs.getString("name");
    description = rs.getString("description");
    groupId = rs.getInt("list_id");
    messageId = rs.getInt("message_id");
    replyTo = rs.getString("reply_addr");
    subject = rs.getString("subject");
    message = rs.getString("message");
    statusId = rs.getInt("status_id");
    status = rs.getString("status");
    active = rs.getBoolean("active");
    activeDate = rs.getDate("active_date");
    sendMethodId = rs.getInt("send_method_id");
    inactiveDate = rs.getDate("inactive_date");
    approvalDate = rs.getTimestamp("approval_date");
    approvedBy = rs.getInt("approvedBy");
    if (rs.wasNull()) {
      approvedBy = -1;
    }
    enabled = rs.getBoolean("enabled");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");

    //message table
    messageName = rs.getString("messageName");

    //lookup_delivery_options table
    deliveryName = rs.getString("delivery");
  }


  /**
   *  This method is called when the notifier begins processing a campaign. If
   *  lockProcess returns a 1, then it was successful and this instance of the
   *  notifier can execute the campaign. If lockProcess is not successful then
   *  the notifier should skip this campaign because another instance may have
   *  processed this one already.
   *
   *@param  db                Description of the Parameter
   *@return                   1 if successfully locked
   *@exception  SQLException  Description of the Exception
   */
  public int lockProcess(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE campaign " +
        "SET status_id = ? " +
        "WHERE campaign_id = ? " +
        "AND status_id = ? ");
    pst.setInt(1, statusId);
    pst.setInt(2, id);
    pst.setInt(3, Campaign.QUEUE);
    int count = pst.executeUpdate();
    pst.close();
    return count;
  }


  /**
   *  Returns the user who entered a campaign, useful for checking the authority
   *  of a campaign record
   *
   *@param  db                Description of the Parameter
   *@param  campaignId        Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public final static int queryEnteredBy(Connection db, int campaignId) throws SQLException {
    int enteredBy = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT enteredby " +
        "FROM campaign " +
        "WHERE campaign_id = ? ");
    pst.setInt(1, campaignId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      enteredBy = rs.getInt("enteredby");
    }
    rs.close();
    pst.close();
    return enteredBy;
  }
}

