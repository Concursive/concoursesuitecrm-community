package org.aspcfs.modules.tasks.base;

import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import com.darkhorseventures.framework.beans.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.actionlist.base.*;

/**
 *  Description of the Class
 *
 *@author     akhi_m
 *@created    August 15, 2002
 *@version    $Id$
 */
public class Task extends GenericBean {

  //static variables
  public static int DONE = 1;
  public static int GENERAL = 1;

  //db variables
  private int id = -1;
  private int enteredBy = -1;
  private int priority = -1;
  private int reminderId = -1;
  private int sharing = -1;
  private int modifiedBy = -1;
  private double estimatedLOE = -1;
  private int estimatedLOEType = -1;
  private int owner = -1;
  private int ownerContactId = -1;
  private int categoryId = -1;
  private int age = -1;
  private int type = -1;
  private String notes = null;
  private String description = null;
  private boolean complete = false;
  private boolean enabled = false;
  private java.sql.Date dueDate = null;
  private java.sql.Timestamp modified = null;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp completeDate = null;

  //other
  private int contactId = -1;
  private boolean hasLinks = false;
  private String contactName = null;

  private Contact contact = null;
  private TaskLink linkDetails = new TaskLink();

  private boolean hasEnabledOwnerAccount = true;
  private boolean hasEnabledLinkAccount = true;

  //action list properties
  private int actionId = -1;


  /**
   *  Description of the Method
   */
  public Task() { }


  /**
   *  Constructor for the Task object
   *
   *@param  db                Description of the Parameter
   *@param  thisId            Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public Task(Connection db, int thisId) throws SQLException {
    if (thisId == -1) {
      throw new SQLException("Task ID not specified");
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT t.task_id, t.entered, t.enteredby, t.priority, t.description, " +
        "t.duedate, t.notes, t.sharing, t.complete, t.estimatedloe, " +
        "t.estimatedloetype, t.type, t.owner, t.completedate, t.modified, " +
        "t.modifiedby, t.category_id " +
        "FROM task t " +
        "WHERE task_id = ? ");
    int i = 0;
    pst.setInt(++i, thisId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (thisId == -1) {
      throw new SQLException("Task ID not found");
    }
    this.setOwnerContactId(db);
    buildResources(db);
  }



  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public Task(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }



  /**
   *  Sets the entered attribute of the Task object
   *
   *@param  entered  The new entered value
   */
  public void setEntered(java.sql.Timestamp entered) {
    this.entered = entered;
  }


  /**
   *  Sets the entered attribute of the Task object
   *
   *@param  entered  The new entered value
   */
  public void setEntered(String entered) {
    this.entered = DatabaseUtils.parseTimestamp(entered);
  }


  /**
   *  Sets the enteredBy attribute of the Task object
   *
   *@param  enteredBy  The new enteredBy value
   */
  public void setEnteredBy(int enteredBy) {
    this.enteredBy = enteredBy;
  }


  /**
   *  Sets the ownerContactId attribute of the Task object
   *
   *@param  ownerContactId  The new ownerContactId value
   */
  public void setOwnerContactId(int ownerContactId) {
    this.ownerContactId = ownerContactId;
  }


  /**
   *  Sets the ownerContactId attribute of the Task object
   *
   *@param  ownerContactId  The new ownerContactId value
   */
  public void setOwnerContactId(String ownerContactId) {
    this.ownerContactId = Integer.parseInt(ownerContactId);
  }


  /**
   *  Sets the ownerContactId attribute of the Task object
   *
   *@param  db                The new ownerContactId value
   *@exception  SQLException  Description of the Exception
   */
  public void setOwnerContactId(Connection db) throws SQLException {
    if (owner != -1 && ownerContactId == -1) {
      this.setOwnerContactId(User.getContactId(db, owner));
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean isOwnerValid(Connection db) throws SQLException {
    if (owner == -1 && ownerContactId != -1) {
      Contact thisContact = new Contact(db, ownerContactId);
      this.setOwner(thisContact.getUserId());
    }
    if (owner == -1) {
      return false;
    }
    return true;
  }


  /**
   *  Sets the type attribute of the Task object
   *
   *@param  type  The new type value
   */
  public void setType(int type) {
    this.type = type;

  }


  /**
   *  Sets the type attribute of the Task object
   *
   *@param  type  The new type value
   */
  public void setType(String type) {
    this.type = Integer.parseInt(type);
  }


  /**
   *  Gets the type attribute of the Task object
   *
   *@return    The type value
   */
  public int getType() {
    return type;
  }


  /**
   *  Gets the ownerContactId attribute of the Task object
   *
   *@return    The ownerContactId value
   */
  public int getOwnerContactId() {
    return ownerContactId;
  }


  /**
   *  Gets the hasEnabledOwnerAccount attribute of the Task object
   *
   *@return    The hasEnabledOwnerAccount value
   */
  public boolean getHasEnabledOwnerAccount() {
    return hasEnabledOwnerAccount;
  }


  /**
   *  Gets the hasEnabledLinkAccount attribute of the Task object
   *
   *@return    The hasEnabledLinkAccount value
   */
  public boolean getHasEnabledLinkAccount() {
    return hasEnabledLinkAccount;
  }


  /**
   *  Sets the hasEnabledOwnerAccount attribute of the Task object
   *
   *@param  tmp  The new hasEnabledOwnerAccount value
   */
  public void setHasEnabledOwnerAccount(boolean tmp) {
    this.hasEnabledOwnerAccount = tmp;
  }


  /**
   *  Sets the hasEnabledLinkAccount attribute of the Task object
   *
   *@param  tmp  The new hasEnabledLinkAccount value
   */
  public void setHasEnabledLinkAccount(boolean tmp) {
    this.hasEnabledLinkAccount = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the Task object
   *
   *@param  enteredBy  The new enteredBy value
   */
  public void setEnteredBy(String enteredBy) {
    this.enteredBy = Integer.parseInt(enteredBy);
  }


  /**
   *  Sets the description attribute of the Task object
   *
   *@param  description  The new description value
   */
  public void setDescription(String description) {
    this.description = description;
  }



  /**
   *  Sets the priority attribute of the Task object
   *
   *@param  priority  The new priority value
   */
  public void setPriority(int priority) {
    this.priority = priority;
  }


  /**
   *  Sets the priority attribute of the Task object
   *
   *@param  priority  The new priority value
   */
  public void setPriority(String priority) {
    this.priority = Integer.parseInt(priority);
  }


  /**
   *  Sets the dueDate attribute of the Task object
   *
   *@param  dueDate  The new dueDate value
   */
  public void setDueDate(java.sql.Date dueDate) {
    this.dueDate = dueDate;
  }



  /**
   *  Sets the dueDate attribute of the Task object
   *
   *@param  tmp  The new dueDate value
   */
  public void setDueDate(String tmp) {
    this.dueDate = DatabaseUtils.parseDate(tmp);
  }


  /**
   *  Sets the notes attribute of the Task object
   *
   *@param  notes  The new notes value
   */
  public void setNotes(String notes) {
    this.notes = notes;
  }


  /**
   *  Sets the sharing attribute of the Task object
   *
   *@param  sharing  The new sharing value
   */
  public void setSharing(int sharing) {
    this.sharing = sharing;
  }


  /**
   *  Sets the sharing attribute of the Task object sharing is set to 1 if
   *  bussiness else for personal set to 0
   *
   *@param  sharing  The new sharing value
   */
  public void setSharing(String sharing) {
    this.sharing = Integer.parseInt(sharing);
  }


  /**
   *  Sets the modifiedBy attribute of the Task object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the Task object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the complete attribute of the Task object
   *
   *@param  complete  The new complete value
   */
  public void setComplete(boolean complete) {
    this.complete = complete;
  }


  /**
   *  Sets the complete attribute of the Task object
   *
   *@param  complete  The new complete value
   */
  public void setComplete(String complete) {
    this.complete = DatabaseUtils.parseBoolean(complete);
  }


  /**
   *  Sets the estimatedLOE attribute of the Task object
   *
   *@param  estimatedLOE  The new estimatedLOE value
   */
  public void setEstimatedLOE(double estimatedLOE) {
    this.estimatedLOE = estimatedLOE;
  }


  /**
   *  Sets the estimatedLOE attribute of the Task object
   *
   *@param  estimatedLOE  The new estimatedLOE value
   */
  public void setEstimatedLOE(String estimatedLOE) {
    this.estimatedLOE = Double.parseDouble(estimatedLOE);
  }


  /**
   *  Sets the owner attribute of the Task object
   *
   *@param  owner  The new owner value
   */
  public void setOwner(int owner) {
    this.owner = owner;
  }


  /**
   *  Sets the categoryId attribute of the Task object
   *
   *@param  tmp  The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   *  Sets the categoryId attribute of the Task object
   *
   *@param  tmp  The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.setCategoryId(Integer.parseInt(tmp));
  }


  /**
   *  Sets the owner attribute of the Task object
   *
   *@param  owner  The new owner value
   */
  public void setOwner(String owner) {
    this.owner = Integer.parseInt(owner);
  }



  /**
   *  Sets the id attribute of the Task object
   *
   *@param  id  The new id value
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   *  Sets the id attribute of the Task object
   *
   *@param  id  The new id value
   */
  public void setId(String id) {
    this.id = Integer.parseInt(id);
  }


  /**
   *  Sets the age attribute of the Task object
   *
   *@param  age  The new age value
   */
  public void setAge(int age) {
    this.age = age;
  }


  /**
   *  Sets the age attribute of the Task object
   *
   *@param  age  The new age value
   */
  public void setAge(String age) {
    this.age = Integer.parseInt(age);
  }


  /**
   *  Sets the contactId attribute of the Task object
   *
   *@param  contactId  The new contactId value
   */
  public void setContactId(int contactId) {
    this.contactId = contactId;
  }


  /**
   *  Sets the contactId attribute of the Task object
   *
   *@param  contactId  The new contactId value
   */
  public void setContactId(String contactId) {
    this.contactId = Integer.parseInt(contactId);
  }


  /**
   *  Sets the contactName attribute of the Task object
   *
   *@param  contactName  The new contactName value
   */
  public void setContactName(String contactName) {
    this.contactName = contactName;
  }


  /**
   *  Sets the contact attribute of the Task object
   *
   *@param  contact_id  The new contact value
   */
  public void setContact(String contact_id) {
    this.contactId = Integer.parseInt(contact_id);
  }


  /**
   *  Sets the estimatedLOEType attribute of the Task object
   *
   *@param  estimatedLOEType  The new estimatedLOEType value
   */
  public void setEstimatedLOEType(int estimatedLOEType) {
    this.estimatedLOEType = estimatedLOEType;
  }


  /**
   *  Sets the estimatedLOEType attribute of the Task object
   *
   *@param  estimatedLOEType  The new estimatedLOEType value
   */
  public void setEstimatedLOEType(String estimatedLOEType) {
    this.estimatedLOEType = Integer.parseInt(estimatedLOEType);
  }


  /**
   *  Sets the linkDetails attribute of the Task object
   *
   *@param  linkDetails  The new linkDetails value
   */
  public void setLinkDetails(TaskLink linkDetails) {
    this.linkDetails = linkDetails;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildLinkDetails(Connection db) throws SQLException {
    if (linkDetails == null) {
      linkDetails = new TaskLink();
    }
    linkDetails.setType(this.getType());
    linkDetails.setTaskId(this.getId());
    linkDetails.build(db);
  }


  /**
   *  Gets the linkDetails attribute of the Task object
   *
   *@return    The linkDetails value
   */
  public TaskLink getLinkDetails() {
    return linkDetails;
  }


  /**
   *  Gets the estimatedLOEType attribute of the Task object
   *
   *@return    The estimatedLOEType value
   */
  public int getEstimatedLOEType() {
    return estimatedLOEType;
  }


  /**
   *  Sets the completeDate attribute of the Task object
   *
   *@param  completeDate  The new completeDate value
   */
  public void setCompleteDate(java.sql.Timestamp completeDate) {
    this.completeDate = completeDate;
  }


  /**
   *  Sets the modified attribute of the Task object
   *
   *@param  modified  The new modified value
   */
  public void setModified(java.sql.Timestamp modified) {
    this.modified = modified;
  }


  /**
   *  Sets the modified attribute of the Task object
   *
   *@param  modified  The new modified value
   */
  public void setModified(String modified) {
    this.modified = DatabaseUtils.parseTimestamp(modified);
  }


  /**
   *  Sets the actionId attribute of the Call object
   *
   *@param  actionId  The new actionId value
   */
  public void setActionId(int actionId) {
    this.actionId = actionId;
  }


  /**
   *  Sets the actionId attribute of the Call object
   *
   *@param  actionId  The new actionId value
   */
  public void setActionId(String actionId) {
    this.actionId = Integer.parseInt(actionId);
  }


  /**
   *  Gets the actionId attribute of the Task object
   *
   *@return    The actionId value
   */
  public int getActionId() {
    return actionId;
  }


  /**
   *  Gets the modified attribute of the Task object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedBy attribute of the Task object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the completeDate attribute of the Task object
   *
   *@return    The completeDate value
   */
  public java.sql.Timestamp getCompleteDate() {
    return completeDate;
  }


  /**
   *  Gets the completeDateString attribute of the Task object
   *
   *@return    The completeDateString value
   */
  public String getCompleteDateString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(completeDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the contact attribute of the Task object
   *
   *@return    The contact value
   */
  public Contact getContact() {
    return contact;
  }


  /**
   *  Gets the hasLinks attribute of the Task object
   *
   *@return    The hasLinks value
   */
  public boolean getHasLinks() {
    return hasLinks;
  }


  /**
   *  Gets the contactName attribute of the Task object
   *
   *@return    The contactName value
   */
  public String getContactName() {
    return contactName;
  }


  /**
   *  Gets the alertDateStringLongYear attribute of the Task object
   *
   *@return    The alertDateStringLongYear value
   */
  public String getAlertDateStringLongYear() {
    String tmp = "";
    try {
      SimpleDateFormat formatter = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.LONG);
      formatter.applyPattern("M/d/yyyy");
      return formatter.format(dueDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the alertDateStringLongYear attribute of the Task class
   *
   *@param  dueDate  Description of the Parameter
   *@return          The alertDateStringLongYear value
   */
  public static String getAlertDateStringLongYear(java.sql.Date dueDate) {
    String tmp = "";
    try {
      SimpleDateFormat formatter = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.LONG);
      formatter.applyPattern("M/d/yyyy");
      return formatter.format(dueDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the contactId attribute of the Task object
   *
   *@return    The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   *  Gets the age attribute of the Task object
   *
   *@return    The age value
   */
  public int getAge() {
    return age;
  }


  /**
   *  Gets the age attribute of the Task object
   *
   *@return    The age value
   */
  public String getAgeString() {
    return age + "d";
  }


  /**
   *  Gets the estimatedLOE attribute of the Task object
   *
   *@return    The estimatedLOE value
   */
  public double getEstimatedLOE() {
    return estimatedLOE;
  }


  /**
   *  Gets the estimatedLOEValue attribute of the Task object
   *
   *@return    The estimatedLOEValue value
   */
  public String getEstimatedLOEValue() {
    String toReturn = String.valueOf(estimatedLOE);
    if (toReturn.endsWith(".0")) {
      toReturn = (toReturn.substring(0, toReturn.length() - 2));
    }
    if ("0".equals(toReturn) || estimatedLOE < 0) {
      toReturn = "";
    }
    return toReturn;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void checkEnabledOwnerAccount(Connection db) throws SQLException {
    if (this.getOwner() == -1) {
      throw new SQLException("ID not specified for lookup.");
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM access " +
        "WHERE user_id = ? AND enabled = ? ");
    pst.setInt(1, this.getOwner());
    pst.setBoolean(2, true);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      this.setHasEnabledOwnerAccount(true);
    } else {
      this.setHasEnabledOwnerAccount(false);
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
  public void checkEnabledLinkAccount(Connection db) throws SQLException {
    if (this.getContactId() == -1) {
      throw new SQLException("ID not specified for lookup.");
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM access " +
        "WHERE user_id = ? AND enabled = ? ");
    pst.setInt(1, this.getContactId());
    pst.setBoolean(2, true);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      this.setHasEnabledLinkAccount(true);
    } else {
      this.setHasEnabledLinkAccount(false);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Gets the sharing attribute of the Task object
   *
   *@return    The sharing value
   */
  public int getSharing() {
    return sharing;
  }


  /**
   *  Gets the owner attribute of the Task object
   *
   *@return    The owner value
   */
  public int getOwner() {
    return owner;
  }


  /**
   *  Gets the categoryId attribute of the Task object
   *
   *@return    The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   *  Gets the complete attribute of the Task object
   *
   *@return    The complete value
   */
  public boolean getComplete() {
    return complete;
  }


  /**
   *  Gets the notes attribute of the Task object
   *
   *@return    The notes value
   */
  public String getNotes() {
    return notes;
  }


  /**
   *  Gets the reminderId attribute of the Task object
   *
   *@return    The reminderId value
   */
  public int getReminderId() {
    return reminderId;
  }


  /**
   *  Gets the dueDate attribute of the Task object
   *
   *@return    The dueDate value
   */
  public java.sql.Date getDueDate() {
    return dueDate;
  }



  /**
   *  Gets the dueDateString attribute of the Task object
   *
   *@return    The dueDateString value
   */
  public String getDueDateString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(dueDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the description attribute of the Task object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the priority attribute of the Task object
   *
   *@return    The priority value
   */
  public int getPriority() {
    return priority;
  }


  /**
   *  Gets the enteredBy attribute of the Task object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the entered attribute of the Task object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredString attribute of the Task object
   *
   *@return    The enteredString value
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
   *  Gets the id attribute of the Task object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    String sql = null;
    if (!isValid(db)) {
      return false;
    }
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      sql = "INSERT INTO task " +
          "(enteredby, modifiedby, priority, description, notes, sharing, owner, duedate, estimatedloe, " +
          (estimatedLOEType == -1 ? "" : "estimatedLOEType, ") +
          (type == -1 ? "" : "type, ") +
          "complete, completedate, category_id) " +
          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, " +
          (estimatedLOEType == -1 ? "" : "?, ") +
          (type == -1 ? "" : "?, ") +
          "?, ?, ? ) ";
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getModifiedBy());
      pst.setInt(++i, this.getPriority());
      pst.setString(++i, this.getDescription());
      pst.setString(++i, this.getNotes());
      pst.setInt(++i, this.getSharing());
      DatabaseUtils.setInt(pst, ++i, this.getOwner());
      pst.setDate(++i, this.getDueDate());
      pst.setDouble(++i, this.getEstimatedLOE());
      if (this.getEstimatedLOEType() != -1) {
        pst.setInt(++i, this.getEstimatedLOEType());
      }
      if (this.getType() != -1) {
        pst.setInt(++i, this.getType());
      }
      pst.setBoolean(++i, this.getComplete());
      if (this.getComplete()) {
        pst.setTimestamp(++i, new Timestamp(System.currentTimeMillis()));
      } else {
        pst.setTimestamp(++i, null);
      }
      DatabaseUtils.setInt(pst, ++i, categoryId);
      pst.execute();
      this.id = DatabaseUtils.getCurrVal(db, "task_task_id_seq");
      pst.close();
      if (this.getContactId() != -1) {
        insertContacts(db);
      }
      if (actionId > 0) {
        updateLog(db);
      }
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
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void updateLog(Connection db) throws SQLException {
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      ActionItemLog thisLog = new ActionItemLog();
      thisLog.setEnteredBy(this.getEnteredBy());
      thisLog.setModifiedBy(this.getModifiedBy());
      thisLog.setItemId(this.getActionId());
      thisLog.setLinkItemId(this.getId());
      thisLog.setType(Constants.TASK_OBJECT);
      thisLog.insert(db);
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    String sql = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    int count = 0;
    if (id == -1) {
      throw new SQLException("Task ID not specified");
    }
    if (!isValid(db)) {
      return -1;
    }
    try {
      db.setAutoCommit(false);
      Task previousTask = new Task(db, id);
      sql = "UPDATE task " +
          "SET modifiedby = ?, priority = ?, description = ?, notes = ?, " +
          "sharing = ?, owner = ?, duedate = ?, estimatedloe = ?, " +
          (estimatedLOEType == -1 ? "" : "estimatedloetype = ?, ") +
          "modified = CURRENT_TIMESTAMP, complete = ?, completedate = ?, category_id = ? " +
          "WHERE task_id = ? AND modified = ? ";
      int i = 0;
      pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getModifiedBy());
      pst.setInt(++i, this.getPriority());
      pst.setString(++i, this.getDescription());
      pst.setString(++i, this.getNotes());
      pst.setInt(++i, this.getSharing());
      DatabaseUtils.setInt(pst, ++i, this.getOwner());
      pst.setDate(++i, this.getDueDate());
      pst.setDouble(++i, this.getEstimatedLOE());
      if (this.getEstimatedLOEType() != -1) {
        pst.setInt(++i, this.getEstimatedLOEType());
      }
      pst.setBoolean(++i, this.getComplete());
      if (previousTask.getComplete() && this.getComplete()) {
        pst.setTimestamp(++i, previousTask.getCompleteDate());
      } else if (this.getComplete() && !previousTask.getComplete()) {
        pst.setTimestamp(++i, new Timestamp(System.currentTimeMillis()));
      } else {
        pst.setTimestamp(++i, null);
      }
      DatabaseUtils.setInt(pst, ++i, categoryId);
      pst.setInt(++i, id);
      pst.setTimestamp(++i, this.getModified());
      count = pst.executeUpdate();
      pst.close();
      if (this.getContactId() != -1) {
        insertContacts(db);
      }
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return count;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insertContacts(Connection db) throws SQLException {
    String sql = null;
    if (contactId == -1) {
      throw new SQLException("Contact ID incorrect");
    }
    if (this.getId() == -1) {
      throw new SQLException("Task ID not specified");
    }
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      sql = "DELETE FROM tasklink_contact " +
          "WHERE task_id = ? ";
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();
      sql = "INSERT INTO tasklink_contact " +
          "(task_id, contact_id) " +
          "VALUES (?, ?) ";
      i = 0;
      pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getId());
      pst.setInt(++i, this.getContactId());
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
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    ResultSet rs = null;
    String sql = null;
    DependencyList dependencyList = new DependencyList();
    try {
      sql = "SELECT count(*) as linkcount " +
          "FROM tasklink_contact " +
          "WHERE task_id = ? ";
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int linkcount = rs.getInt("linkcount");
        if (linkcount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("Contacts");
          thisDependency.setCount(linkcount);
          thisDependency.setCanDelete(true);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();

      sql = "SELECT count(*) as linkcount " +
          "FROM tasklink_ticket " +
          "WHERE task_id = ? ";
      i = 0;
      pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int linkcount = rs.getInt("linkcount");
        if (linkcount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("Tickets");
          thisDependency.setCount(linkcount);
          thisDependency.setCanDelete(true);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();

      sql = "SELECT count(*) as linkcount " +
          "FROM tasklink_project " +
          "WHERE task_id = ? ";
      i = 0;
      pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int linkcount = rs.getInt("linkcount");
        if (linkcount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("Projects");
          thisDependency.setCount(linkcount);
          thisDependency.setCanDelete(true);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();

      ActionList actionList = ActionItemLogList.isItemLinked(db, this.getId(), Constants.TASK_OBJECT);
      if (actionList != null) {
        Dependency thisDependency = new Dependency();
        thisDependency.setName(actionList.getDescription());
        thisDependency.setCount(1);
        thisDependency.setCanDelete(true);
        dependencyList.add(thisDependency);
      }
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
    return dependencyList;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Task ID not specified");
    }
    try {
      db.setAutoCommit(false);
      deleteRelationships(db);
      PreparedStatement pst = db.prepareStatement(
          "DELETE from task " +
          "WHERE task_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
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
  public boolean deleteRelationships(Connection db) throws SQLException {
    String sql = null;
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = db.prepareStatement(
          "DELETE from tasklink_contact " +
          "WHERE task_id = ? "
          );
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      pst = db.prepareStatement(
          "DELETE from tasklink_ticket " +
          "WHERE task_id = ? "
          );
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      pst = db.prepareStatement(
          "DELETE from tasklink_project " +
          "WHERE task_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      //delete any action list items associated
      ActionItemLog.deleteLink(db, this.getId(), Constants.TASK_OBJECT);

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
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildResources(Connection db) throws SQLException {
    ResultSet rs = null;
    String sql = null;
    if (this.getId() == -1) {
      throw new SQLException("Task ID not specified");
    }
    try {
      //build the linked contact info
      sql = "SELECT contact_id " +
          "FROM tasklink_contact tl_ct " +
          "WHERE task_id = ? ";
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        contactId = rs.getInt("contact_id");
        hasLinks = true;
      }
      rs.close();
      pst.close();

      if (contactId > 0) {
        contact = new Contact(db, contactId);
        contactName = contact.getNameLastFirst();
      }
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("task_id");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    priority = rs.getInt("priority");
    description = rs.getString("description");
    dueDate = rs.getDate("duedate");
    notes = rs.getString("notes");
    sharing = rs.getInt("sharing");
    complete = rs.getBoolean("complete");
    estimatedLOE = rs.getDouble("estimatedloe");
    estimatedLOEType = rs.getInt("estimatedloetype");
    if (rs.wasNull()) {
      estimatedLOEType = -1;
    }
    type = rs.getInt("type");
    owner = rs.getInt("owner");
    completeDate = rs.getTimestamp("completeDate");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    categoryId = DatabaseUtils.getInt(rs, "category_id");
    if (entered != null) {
      float ageCheck = ((System.currentTimeMillis() - entered.getTime()) / 86400000);
      age = java.lang.Math.round(ageCheck);
    }
  }


  /**
   *  Gets the valid attribute of the Task object
   *
   *@param  db                Description of the Parameter
   *@return                   The valid value
   *@exception  SQLException  Description of the Exception
   */
  protected boolean isValid(Connection db) throws SQLException {
    errors.clear();

    if (this.getDescription() == null || this.getDescription().equals("")) {
      errors.put("descriptionError", "Task Description is required");
    }
    if (this.getCategoryId() == -1 && !this.isOwnerValid(db)) {
      errors.put("ownerError", "Owner is required");
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
   *@param  db                Description of the Parameter
   *@param  projectId         Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insertProjectLink(Connection db, int projectId) throws SQLException {
    String sql = "INSERT INTO tasklink_project " +
        "(task_id, project_id) " +
        "VALUES (?, ?) ";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(++i, this.getId());
    pst.setInt(++i, projectId);
    pst.execute();
    pst.close();
  }
}

