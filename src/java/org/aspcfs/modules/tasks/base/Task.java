package com.darkhorseventures.cfsbase;

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
  private int estimatedLOE = -1;
  private int owner = -1;
  private int age = -1;
  private String notes = "";
  private String description = "";
  private boolean complete = false;
  private boolean enabled = false;
  private java.sql.Date dueDate = null;
  private java.sql.Timestamp modified = null;
  private java.sql.Timestamp entered = null;

  //other
  private HashMap dependencyList = new HashMap();
  private String ownerName = "";
  private int contactId = -1;
  private String contactName = "";
  private int ticketId = -1;



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
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();
    if (thisId == -1) {
      return;
    }
    this.setId(thisId);
    try {
      db.setAutoCommit(false);
      sql.append(
          "SELECT t.*,c.namelast as lastname,c.namefirst as firstname " +
          "FROM task t,contact c " +
          "WHERE (task_id = ? AND t.owner = c.contact_id) ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        buildRecord(rs);
        buildResources(db);
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
  private void setEntered(java.sql.Timestamp entered) {
    this.entered = entered;
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
    try {
      java.util.Date tmpDate = DateFormat.getDateInstance(3).parse(tmp);
      dueDate = new java.sql.Date(new java.util.Date().getTime());
      dueDate.setTime(tmpDate.getTime());
    } catch (Exception e) {
      dueDate = null;
    }
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
    if (Integer.parseInt(complete) == 1) {
      this.complete = true;
    } else {
      this.complete = false;
    }
  }


  /**
   *  Sets the estimatedLOE attribute of the Task object
   *
   *@param  estimatedLOE  The new estimatedLOE value
   */
  public void setEstimatedLOE(int estimatedLOE) {
    this.estimatedLOE = estimatedLOE;
  }


  /**
   *  Sets the estimatedLOE attribute of the Task object
   *
   *@param  estimatedLOE  The new estimatedLOE value
   */
  public void setEstimatedLOE(String estimatedLOE) {
    this.estimatedLOE = Integer.parseInt(estimatedLOE);
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
    System.out.println("Setting owner "+ owner);
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
   *  Sets the age attribute of the Task object
   *
   *@param  age  The new age value
   */
  public void setAge(int age) {
    this.age = age;
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
  public int getEstimatedLOE() {
    return estimatedLOE;
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
    String sql = "";
    try {
      db.setAutoCommit(false);
      if (this.getDescription().equals("")) {
        return false;
      }

      sql = "INSERT INTO task " +
          "(enteredby, priority, description, notes, sharing, complete, owner, duedate, estimatedloe) " +
          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      System.out.println("Task --> "+ pst.toString());
      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getPriority());
      pst.setString(++i, this.getDescription());
      pst.setString(++i, this.getNotes());
      pst.setInt(++i, this.getSharing());
      pst.setBoolean(++i, this.getComplete());
      pst.setInt(++i, this.getOwner());
      pst.setDate(++i, this.getDueDate());
      pst.setInt(++i, this.getEstimatedLOE());
      System.out.println("Task insert-->"+pst.toString()); 
      pst.execute();
      this.id = DatabaseUtils.getCurrVal(db, "task_task_id_seq");
      insertContacts(pst, db);
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
  public boolean update(Connection db, int thisId) throws SQLException {
    StringBuffer sql = new StringBuffer();
    if (this.getDescription().equals("")) {
      return false;
    }

    try {
      db.setAutoCommit(false);
      sql.append(
          "UPDATE task " +
          "SET enteredby = ?, priority = ?, description = ?, notes = ?, " +
          "sharing = ?, complete = ?, owner = ?, duedate = ?, estimatedloe = ?, " +
          "modified = CURRENT_TIMESTAMP " +
          "WHERE task_id = ? ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getPriority());
      pst.setString(++i, this.getDescription());
      pst.setString(++i, this.getNotes());
      pst.setInt(++i, this.getSharing());
      pst.setBoolean(++i, this.getComplete());
      pst.setInt(++i, this.getOwner());
      pst.setDate(++i, this.getDueDate());
      pst.setInt(++i, this.getEstimatedLOE());
      pst.setInt(++i, thisId);
      pst.execute();
      this.id = thisId;
      insertContacts(pst, db);
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
  public boolean insertContacts(PreparedStatement pst, Connection db) throws SQLException {

    String sql = "";
    if (contactId == -1) {
      return false;
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
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
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
    String sql = "";
    try {
      db.setAutoCommit(false);
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
      pst.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
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
    String sql = "";
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
    String sql = "";
    try {
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
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
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
    String sql = "";
    try {
      db.setAutoCommit(false);
      sql = "SELECT c.contact_id, c.namelast as ct_lastname, c.namefirst as ct_firstname " +
          "FROM tasklink_contact tl_ct, contact c " +
          "WHERE (task_id = ? AND tl_ct.contact_id = c.contact_id) ";

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        this.contactId = rs.getInt("contact_id");
        this.contactName = rs.getString("ct_lastname") + "," + rs.getString("ct_firstname");
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
      }
      pst.close();
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
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
    estimatedLOE = rs.getInt("estimatedloe");
    owner = rs.getInt("owner");
    ownerName = rs.getString("lastname") + "," + rs.getString("firstname");
    if (entered != null) {
      float ageCheck = ((System.currentTimeMillis() - entered.getTime()) / 86400000);
      age = java.lang.Math.round(ageCheck);
    }
  }

}

