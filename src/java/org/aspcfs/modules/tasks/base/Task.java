package com.darkhorseventures.cfsbase;

import com.darkhorseventures.cfsbase.*;
import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.utils.DatabaseUtils;
import org.theseus.actions.*;

/**
 *  Description of the Class
 *
 *@author     akhi_m
 *@created    August 15, 2002
 *@version    $Id$
 *@version    $Id$
 */
public class Task extends GenericBean {

  //static variables
  public static int DONE = 1;

  //db variables
  private int id = -1;
  private int enteredBy = -1;
  private int priority = -1;
  private int reminderId = -1;
  private int sharing = -1;
  private int modifiedby = -1;
  private double estimatedLOE = -1;
  private int estimatedLOEType = -1;
  private int owner = -1;
  private int age = -1;
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
  private int ticketId = -1;
  private boolean hasLinks = false;
  private String contactName = null;
  private String ownerName = null;
  private HashMap dependencyList = new HashMap();
  private Contact contact = null;
  private Ticket ticket = null;
  
  private boolean hasEnabledOwnerAccount = true;
  private boolean hasEnabledLinkAccount = true;


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
        "t.duedate, t.notes, t.sharing, t.complete, t.estimatedloe, t.estimatedloetype, t.owner, t.completedate, t.modified, " +
        "c.namelast as lastname,c.namefirst as firstname " +
        "FROM task t,contact c " +
        "WHERE (task_id = ? AND t.owner = c.contact_id) ");
    int i = 0;
    pst.setInt(++i, thisId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
      buildResources(db);
    }
    rs.close();
    pst.close();
    if (thisId == -1) {
      throw new SQLException("Task ID not found");
    }
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

  public boolean getHasEnabledOwnerAccount() { return hasEnabledOwnerAccount; }
  public boolean getHasEnabledLinkAccount() { return hasEnabledLinkAccount; }
  public void setHasEnabledOwnerAccount(boolean tmp) { this.hasEnabledOwnerAccount = tmp; }
  public void setHasEnabledLinkAccount(boolean tmp) { this.hasEnabledLinkAccount = tmp; }

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
   *  Sets the ticketId attribute of the Task object
   *
   *@param  ticketId  The new ticketId value
   */
  public void setTicketId(int ticketId) {
    this.ticketId = ticketId;
  }


  /**
   *  Sets the ticketId attribute of the Task object
   *
   *@param  ticketId  The new ticketId value
   */
  public void setTicketId(String ticketId) {
    this.ticketId = Integer.parseInt(ticketId);
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
   *  Gets the modified attribute of the Task object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
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
   *  Gets the ticket attribute of the Task object
   *
   *@return    The ticket value
   */
  public Ticket getTicket() {
    return ticket;
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
   *  Gets the ticketId attribute of the Task object
   *
   *@return    The ticketId value
   */
  public int getTicketId() {
    return ticketId;
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
   *  Gets the ownerName attribute of the Task object
   *
   *@return    The ownerName value
   */
  public String getOwnerName() {
    return ownerName;
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

    if (!isValid()) {
      return false;
    }

    try {
      db.setAutoCommit(false);
      sql = "INSERT INTO task " +
          "(enteredby, priority, description, notes, sharing, owner, duedate, estimatedloe, " +
          (estimatedLOEType == -1 ? "" : "estimatedLOEType, ") +
          "complete, completedate) " +
          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, " +
          (estimatedLOEType == -1 ? "" : "?, ") +
          "?, ? ) ";

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getPriority());
      pst.setString(++i, this.getDescription());
      pst.setString(++i, this.getNotes());
      pst.setInt(++i, this.getSharing());
      pst.setInt(++i, this.getOwner());
      pst.setDate(++i, this.getDueDate());
      pst.setDouble(++i, this.getEstimatedLOE());
      if (this.getEstimatedLOEType() != -1) {
        pst.setInt(++i, this.getEstimatedLOEType());
      }
      pst.setBoolean(++i, this.getComplete());
      if (this.getComplete()) {
        pst.setTimestamp(++i, new Timestamp(System.currentTimeMillis()));
      } else {
        pst.setTimestamp(++i, null);
      }

      if (System.getProperty("DEBUG") != null) {
        System.out.println("Task Insert Query --> " + pst.toString());
      }
      pst.execute();
      this.id = DatabaseUtils.getCurrVal(db, "task_task_id_seq");
      if (this.getContactId() != -1) {
        insertContacts(pst, db);
      }
      pst.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
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
   *@param  thisId            Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int update(Connection db, int thisId) throws SQLException {
    String sql = "";
    ResultSet rs = null;
    PreparedStatement pst = null;
    int count = 0;

    if (!isValid()) {
      return -1;
    }

    try {
      db.setAutoCommit(false);
      Task previousTask = new Task(db, thisId);
      int i = 0;

      sql = "UPDATE task " +
          "SET enteredby = ?, priority = ?, description = ?, notes = ?, " +
          "sharing = ?, owner = ?, duedate = ?, estimatedloe = ?, " +
          (estimatedLOEType == -1 ? "" : "estimatedloetype = ?, ") +
          "modified = CURRENT_TIMESTAMP, complete = ?, completedate = ? " +
          "WHERE task_id = ? AND modified = ? ";

      i = 0;
      pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getPriority());
      pst.setString(++i, this.getDescription());
      pst.setString(++i, this.getNotes());
      pst.setInt(++i, this.getSharing());
      pst.setInt(++i, this.getOwner());
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
      pst.setInt(++i, thisId);
      pst.setTimestamp(++i, this.getModified());

      if (System.getProperty("DEBUG") != null) {
        System.out.println("Task -> Update Query is " + pst.toString());
      }

      count = pst.executeUpdate();
      this.id = thisId;
      if (this.getContactId() != -1) {
        insertContacts(pst, db);
      }
      pst.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return count;
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of the Parameter
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insertContacts(PreparedStatement pst, Connection db) throws SQLException {

    String sql = null;
    if (contactId == -1) {
      throw new SQLException("Contact ID incorrect");
    }
    if (this.getId() == -1) {
      throw new SQLException("Task ID not specified");
    }

    try {
      db.setAutoCommit(false);
      sql = "DELETE FROM tasklink_contact " +
          "WHERE task_id = ? ";

      int i = 0;
      pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getId());
      pst.execute();

      sql = "INSERT INTO tasklink_contact " +
          "(task_id, contact_id) " +
          "VALUES (?, ?) ";

      i = 0;
      pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getId());
      pst.setInt(++i, this.getContactId());
      pst.execute();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.commit();
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
  public HashMap processDependencies(Connection db) throws SQLException {
    ResultSet rs = null;
    String sql = null;
    try {
      sql = "SELECT count(*) as linkcount " +
          "FROM task,tasklink_contact " +
          "WHERE task.task_id = ? AND task.task_id = tasklink_contact.task_id ";

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        if (rs.getInt("linkcount") != 0) {
          dependencyList.put("Contacts", new Integer(rs.getInt("linkcount")));
        }
      }

      sql = "SELECT count(*) as linkcount " +
          "FROM task,tasklink_ticket " +
          "WHERE task.task_id = ? AND task.task_id = tasklink_ticket.task_id ";

      i = 0;
      pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        if (rs.getInt("linkcount") != 0) {
          dependencyList.put("Tickets", new Integer(rs.getInt("linkcount")));
        }
      }
      rs.close();
      pst.close();
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
    String sql = null;
    if (this.getId() == -1) {
      throw new SQLException("Task ID not specified");
    }

    try {
      db.setAutoCommit(false);
      PreparedStatement pst = null;

      //delete all relationships
      deleteRelationships(pst, db);

      sql = "DELETE from task " +
          "WHERE task_id = ? ";
      int i = 0;
      pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of the Parameter
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean deleteRelationships(PreparedStatement pst, Connection db) throws SQLException {
    String sql = null;
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      sql = "DELETE from tasklink_contact " +
          "WHERE task_id = ? ";
      int i = 0;
      pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getId());
      pst.execute();

      sql = "DELETE from tasklink_ticket " +
          "WHERE task_id = ? ";
      i = 0;
      pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getId());
      pst.execute();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
        db.setAutoCommit(true);
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
      sql = "SELECT c.contact_id, c.namelast as ct_lastname, c.namefirst as ct_firstname " +
          "FROM tasklink_contact tl_ct, contact c " +
          "WHERE (task_id = ? AND tl_ct.contact_id = c.contact_id) ";

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        this.contactId = rs.getInt("contact_id");
        this.contact = new Contact(db, new Integer(this.contactId).toString());
        this.contactName = rs.getString("ct_lastname") + ", " + rs.getString("ct_firstname");
        hasLinks = true;
      }
      sql = "SELECT ticket_id " +
          "FROM tasklink_ticket " +
          "WHERE task_id = ? ";

      i = 0;
      pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        this.ticketId = rs.getInt("ticket_id");
        this.ticket = new Ticket(db, this.ticketId);
      }
      rs.close();
      pst.close();
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
    owner = rs.getInt("owner");
    completeDate = rs.getTimestamp("completeDate");
    modified = rs.getTimestamp("modified");
    ownerName = Contact.getNameLastFirst(rs.getString("lastname"), rs.getString("firstname"));
    if (entered != null) {
      float ageCheck = ((System.currentTimeMillis() - entered.getTime()) / 86400000);
      age = java.lang.Math.round(ageCheck);
    }
  }


  /**
   *  Gets the valid attribute of the Task object
   *
   *@return                   The valid value
   *@exception  SQLException  Description of the Exception
   */
  protected boolean isValid() throws SQLException {
    errors.clear();

    if (this.getDescription() == null || this.getDescription().equals("")) {
      errors.put("descriptionError", "Task Description is required");
    }
    if (this.getOwner() == -1) {
      errors.put("ownerError", "Owner name is required");
    }
    if (hasErrors()) {
      return false;
    } else {
      return true;
    }
  }

}

