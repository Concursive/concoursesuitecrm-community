//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import org.theseus.actions.*;
import java.sql.*;
import com.darkhorseventures.controller.*;
import com.darkhorseventures.utils.*;
import java.util.*;

/**
 *  Represents a user access record <p>
 *
 *  Password control is implemented with three fields: password is the database
 *  (stored) field, containing the encrypted string; password1 is the field that
 *  the user sees; password2 is the field used for verification.<p>
 *
 *  For an existing record, the password field is only included in the SQL
 *  command if the password1 field is left blank. Otherwise, it is compared to
 *  the password2 field (also filled in by the user), and if valid, is encrypted
 *  and included in the update.<p>
 *
 *  For a new record, the password 1 and 2 fields are required.
 *
 *@author     mrajkowski
 *@created    September 17, 2001
 *@version    $Id$
 */
public class User extends GenericBean {

  protected String errmsg = "";
  protected int id = -1;
  protected String username = null;
  protected String password = null;
  protected String password1 = null;
  protected String password2 = null;
  protected int contactId = -1;
  protected int roleId = -1;
  protected String role = null;
  protected int managerId = -1;
  protected String manager = null;
  protected User managerUser = null;
  protected String lastLogin = null;
  protected String entered = "";
  protected int enteredBy = -1;
  protected String modified = null;
  protected int modifiedBy = -1;
  protected boolean enabled = true;
  protected Contact contact = new Contact();
  protected Vector permissions = new Vector();
  protected UserList childUsers = null;

  protected boolean buildContact = false;
  protected boolean buildPermissions = false;
  protected boolean buildHierarchy = false;

  protected String previousUsername = null;

  protected boolean opportunityLock = false;
  protected GraphSummaryList gmr = new GraphSummaryList();
  protected GraphSummaryList ramr = new GraphSummaryList();
  protected GraphSummaryList cgmr = new GraphSummaryList();
  protected GraphSummaryList cramr = new GraphSummaryList();


  /**
   *  Constructor for the User object
   *
   *@since    1.1
   */
  public User() { }


  /**
   *  Constructor for the User object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public User(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the User object
   *
   *@param  db                Description of Parameter
   *@param  userId            Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public User(Connection db, String userId) throws SQLException {
    buildRecord(db, Integer.parseInt(userId));
    buildResources(db);
  }
  
  /**
   *  Constructor for the User object
   *
   *@param  db                Description of Parameter
   *@param  userId            Description of Parameter
   *@param  doHierarchy       Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.26
   */
  public User(Connection db, String userId, boolean doHierarchy) throws SQLException {
    this.buildHierarchy = doHierarchy;
    buildRecord(db, Integer.parseInt(userId));
    buildResources(db);
  }


  /**
   *  Constructor for the User object
   *
   *@param  db                Description of Parameter
   *@param  userId            Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.20
   */
  public User(Connection db, int userId) throws SQLException {
    buildRecord(db, userId);
    buildResources(db);
  }


  /**
   *  Constructor for the User object
   *
   *@param  db                Description of Parameter
   *@param  userId            Description of Parameter
   *@param  doHierarchy       Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.28
   */
  public User(Connection db, int userId, boolean doHierarchy) throws SQLException {
    this.buildHierarchy = doHierarchy;
    buildRecord(db, userId);
    buildResources(db);
  }


  /**
   *  Sets the Gmr attribute of the User object
   *
   *@param  tmp  The new Gmr value
   *@since       1.18
   */
  public void setGmr(GraphSummaryList tmp) {
    this.gmr = tmp;
  }


  /**
   *  Sets the Ramr attribute of the User object
   *
   *@param  tmp  The new Ramr value
   *@since       1.18
   */
  public void setRamr(GraphSummaryList tmp) {
    this.ramr = tmp;
  }

  public void setIsValid(boolean isValid, boolean dataAlso) {
    if (dataAlso == true) {
	    this.gmr.setIsValid(isValid);
	    this.ramr.setIsValid(isValid);
	    this.cgmr.setIsValid(isValid);
	    this.cramr.setIsValid(isValid);
    }
    
    if (isValid == false) {
    	this.gmr.setLastFileName("");
    	this.ramr.setLastFileName("");
    	this.cgmr.setLastFileName("");
    	this.cramr.setLastFileName("");   
    }
    
    if (managerUser != null) {
	managerUser.setIsValid(false, false);
    }
  }
  
  public boolean getIsValid() {
	if ( this.gmr.getIsValid() == true && this.ramr.getIsValid() == true && this.cgmr.getIsValid() == true && this.cramr.getIsValid() == true ) {
		return true;
	} else {
		return false;
	}
  }
  
  public void setGraphValues(String key, Float v1, Float v2, Float v3, Float v4) {
	this.getGmr().setValue(key, v1);	
	this.getRamr().setValue(key, v2);
	this.getCgmr().setValue(key, v3);
	this.getCramr().setValue(key, v4);
  }

  /**
   *  Sets the Cgmr attribute of the User object
   *
   *@param  tmp  The new Cgmr value
   *@since       1.18
   */
  public void setCgmr(GraphSummaryList tmp) {
    this.cgmr = tmp;
  }


  /**
   *  Sets the Cramr attribute of the User object
   *
   *@param  tmp  The new Cramr value
   *@since       1.18
   */
  public void setCramr(GraphSummaryList tmp) {
    this.cramr = tmp;
  }


  /**
   *  Sets the Id attribute of the User object
   *
   *@param  tmp  The new Id value
   *@since       1.1
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the Id attribute of the User object
   *
   *@param  tmp  The new Id value
   *@since       1.1
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Username attribute of the User object
   *
   *@param  tmp  The new Username value
   *@since       1.1
   */
  public void setUsername(String tmp) {
    this.username = tmp;
  }


  /**
   *  Sets the PreviousUsername attribute of the User object
   *
   *@param  tmp  The new PreviousUsername value
   *@since       1.28
   */
  public void setPreviousUsername(String tmp) {
    this.previousUsername = tmp;
  }


  /**
   *  Sets the Password attribute of the User object
   *
   *@param  tmp  The new Password value
   *@since       1.1
   */
  public void setPassword(String tmp) {
    this.password = tmp;
  }


  /**
   *  Sets the Password1 attribute of the User object
   *
   *@param  tmp  The new Password1 value
   *@since       1.1
   */
  public void setPassword1(String tmp) {
    this.password1 = tmp;
  }


  /**
   *  Sets the Password2 attribute of the User object
   *
   *@param  tmp  The new Password2 value
   *@since       1.1
   */
  public void setPassword2(String tmp) {
    this.password2 = tmp;
  }


  /**
   *  Sets the ContactId attribute of the User object
   *
   *@param  tmp  The new ContactId value
   *@since       1.1
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
    this.contact.setId(tmp);
  }


  /**
   *  Sets the ContactId attribute of the User object
   *
   *@param  tmp  The new ContactId value
   *@since       1.1
   */
  public void setContactId(String tmp) {
    if (tmp != null) {
      setContactId(Integer.parseInt(tmp));
    }
  }


  /**
   *  Sets the RoleId attribute of the User object
   *
   *@param  tmp  The new RoleId value
   *@since       1.1
   */
  public void setRoleId(int tmp) {
    this.roleId = tmp;
  }


  /**
   *  Sets the RoleId attribute of the User object
   *
   *@param  tmp  The new RoleId value
   *@since       1.1
   */
  public void setRoleId(String tmp) {
    if (tmp != null) {
      this.roleId = Integer.parseInt(tmp);
    }
  }


  /**
   *  Sets the Role attribute of the User object
   *
   *@param  tmp  The new Role value
   *@since       1.11
   */
  public void setRole(String tmp) {
    this.role = tmp;
  }


  /**
   *  Sets the managerId attribute of the User object
   *
   *@param  tmp  The new managerId value
   *@since       1.1
   */
  public void setManagerId(int tmp) {
    this.managerId = (tmp);
  }


  /**
   *  Sets the Manager attribute of the User object
   *
   *@param  tmp  The new Manager value
   *@since       1.11
   */
  public void setManager(String tmp) {
    this.manager = tmp;
  }


  /**
   *  Sets the managerId attribute of the User object
   *
   *@param  tmp  The new managerId value
   *@since       1.1
   */
  public void setManagerId(String tmp) {
    if (tmp != null) {
      this.managerId = Integer.parseInt(tmp);
    }
  }


  /**
   *  Sets the Entered attribute of the User object
   *
   *@param  tmp  The new Entered value
   *@since       1.1
   */
  public void setEntered(String tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the EnteredBy attribute of the User object
   *
   *@param  tmp  The new EnteredBy value
   *@since       1.1
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the EnteredBy attribute of the User object
   *
   *@param  tmp  The new EnteredBy value
   *@since       1.1
   */
  public void setEnteredBy(String tmp) {
    if (tmp != null) {
      this.enteredBy = Integer.parseInt(tmp);
    }
  }


  /**
   *  Sets the Modified attribute of the User object
   *
   *@param  tmp  The new Modified value
   *@since       1.1
   */
  public void setModified(String tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the ModifiedBy attribute of the User object
   *
   *@param  tmp  The new ModifiedBy value
   *@since       1.1
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the ModifiedBy attribute of the User object
   *
   *@param  tmp  The new ModifiedBy value
   *@since       1.1
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Enabled attribute of the User object
   *
   *@param  tmp  The new Enabled value
   *@since       1.1
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the Enabled attribute of the User object
   *
   *@param  tmp  The new Enabled value
   *@since       1.1
   */
  public void setEnabled(String tmp) {
    if (tmp.toLowerCase().equals("false")) {
      this.enabled = false;
    } else {
      this.enabled = true;
    }
  }


  /**
   *  Sets the BuildContact attribute of the User object
   *
   *@param  tmp  The new BuildContact value
   *@since       1.29
   */
  public void setBuildContact(boolean tmp) {
    this.buildContact = tmp;
  }


  /**
   *  Sets the BuildPermissions attribute of the User object
   *
   *@param  tmp  The new BuildPermissions value
   *@since       1.29
   */
  public void setBuildPermissions(boolean tmp) {
    this.buildPermissions = tmp;
  }


  /**
   *  Sets the BuildHierarchy attribute of the User object
   *
   *@param  tmp  The new BuildHierarchy value
   *@since       1.23
   */
  public void setBuildHierarchy(boolean tmp) {
    this.buildHierarchy = tmp;
  }


  /**
   *  Sets the Contact attribute of the User object
   *
   *@param  tmp  The new Contact value
   *@since       1.1
   */
  public void setContact(Contact tmp) {
    this.contact = tmp;
  }


  /**
   *  Sets the ManagerUser attribute of the User object
   *
   *@param  tmp  The new ManagerUser value
   *@since       1.17
   */
  public void setManagerUser(User tmp) {
    this.managerUser = tmp;
  }

  public void setChildUsers(UserList tmp) {
    this.childUsers = tmp;
  }

  /**
   *  Gets the Gmr attribute of the User object
   *
   *@return    The Gmr value
   *@since     1.18
   */
  public GraphSummaryList getGmr() {
    return gmr;
  }


  /**
   *  Gets the Ramr attribute of the User object
   *
   *@return    The Ramr value
   *@since     1.18
   */
  public GraphSummaryList getRamr() {
    return ramr;
  }


  /**
   *  Gets the Cgmr attribute of the User object
   *
   *@return    The Cgmr value
   *@since     1.18
   */
  public GraphSummaryList getCgmr() {
    return cgmr;
  }


  /**
   *  Gets the Cramr attribute of the User object
   *
   *@return    The Cramr value
   *@since     1.18
   */
  public GraphSummaryList getCramr() {
    return cramr;
  }



  /**
   *  Gets the Id attribute of the User object
   *
   *@return    The Id value
   *@since     1.1
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the Username attribute of the User object
   *
   *@return    The Username value
   *@since     1.1
   */
  public String getUsername() {
    return username;
  }


  /**
   *  Gets the PreviousUsername attribute of the User object
   *
   *@return    The PreviousUsername value
   *@since     1.28
   */
  public String getPreviousUsername() {
    return previousUsername;
  }


  /**
   *  Gets the Password attribute of the User object
   *
   *@return    The Password value
   *@since     1.1
   */
  public String getPassword() {
    return password;
  }


  /**
   *  Gets the Password1 attribute of the User object
   *
   *@return    The Password1 value
   *@since     1.1
   */
  public String getPassword1() {
    return password1;
  }


  /**
   *  Gets the Password2 attribute of the User object
   *
   *@return    The Password2 value
   *@since     1.1
   */
  public String getPassword2() {
    return password2;
  }


  /**
   *  Gets the ContactId attribute of the User object
   *
   *@return    The ContactId value
   *@since     1.1
   */
  public int getContactId() {
    return contactId;
  }


  /**
   *  Gets the RoleId attribute of the User object
   *
   *@return    The RoleId value
   *@since     1.1
   */
  public int getRoleId() {
    return roleId;
  }


  /**
   *  Gets the managerId attribute of the User object
   *
   *@return    The manager value
   *@since     1.1
   */
  public int getManagerId() {
    return managerId;
  }


  /**
   *  Gets the Manager attribute of the User object
   *
   *@return    The Manager value
   *@since     1.11
   */
  public String getManager() {
    return manager;
  }


  /**
   *  Gets the Role attribute of the User object
   *
   *@return    The Role value
   *@since     1.11
   */
  public String getRole() {
    return role;
  }


  /**
   *  Gets the Entered attribute of the User object
   *
   *@return    The Entered value
   *@since     1.1
   */
  public String getEntered() {
    return entered;
  }


  /**
   *  Gets the EnteredBy attribute of the User object
   *
   *@return    The EnteredBy value
   *@since     1.1
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the Modified attribute of the User object
   *
   *@return    The Modified value
   *@since     1.1
   */
  public String getModified() {
    return modified;
  }


  /**
   *  Gets the LastLogin attribute of the User object
   *
   *@return    The LastLogin value
   *@since     1.23
   */
  public String getLastLogin() {
    if (lastLogin != null && lastLogin.equals(entered)) {
      return ("None");
    } else {
      return lastLogin;
    }
  }


  /**
   *  Gets the FullChildList attribute of the User object
   *
   *@param  inList       Description of Parameter
   *@param  currentList  Description of Parameter
   *@return              The FullChildList value
   *@since               1.23
   */
  public UserList getFullChildList(UserList inList, UserList currentList) {
    if (inList != null) {
    
      Iterator j = inList.iterator();
  
      while (j.hasNext()) {
        User thisRec = (User)j.next();
        currentList.addElement(thisRec);
  
        UserList countList = thisRec.getShortChildList();
  
        //System.out.println("size of: " + countList.getListSize() + " ");
        if (countList != null && countList.getListSize() > 0) {
          currentList = thisRec.getFullChildList(countList, currentList);
        }
      }
    }

    return currentList;
  }


  /**
   *  Gets the ShortChildList attribute of the User object
   *
   *@return    The ShortChildList value
   *@since     1.20
   */
  public UserList getShortChildList() {
    return this.childUsers;
  }


  /**
   *  Gets the Child attribute of the User object by navigating through the
   *  children.
   *
   *@param  childId  Description of Parameter
   *@return          The Child value
   *@since           1.30
   */
  public User getChild(int childId) {
    UserList shortChildList = this.getShortChildList();
    if (shortChildList != null) {
      //System.out.println("User-> Child List Size: " + shortChildList.size());
      UserList fullChildList = this.getFullChildList(shortChildList, new UserList());
      Iterator i = fullChildList.iterator();
      while (i.hasNext()) {
        User childRecord = (User)i.next();
        if (childRecord.getId() == childId) {
          return childRecord;
        }
      }
    }
    return null;
  }


  /**
   *  Gets the ModifiedBy attribute of the User object
   *
   *@return    The ModifiedBy value
   *@since     1.1
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the Enabled attribute of the User object
   *
   *@return    The Enabled value
   *@since     1.1
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Geentuhe Contact attribute of the User object
   *
   *@return    The Contact value
   *@since     1.1
   */
  public Contact getContact() {
    return contact;
  }


  /**
   *  Gets the ManagerUser attribute of the User object
   *
   *@return    The ManagerUser value
   *@since     1.17
   */
  public User getManagerUser() {
    return managerUser;
  }
  

  /**
   *  Sets the opportunityLock to true, causing any other requests to this
   *  method to block until released.
   *
   *@since    1.19
   */
  public void doOpportunityLock() {
    while (opportunityLock == true) {
    }
    synchronized (this) {
      while (opportunityLock) {
      }
      this.opportunityLock = true;
    }
  }


  /**
   *  Sets the opportunityLock to false, allowing any waiting requests to try
   *  and lock it.
   *
   *@since    1.19
   */
  public void doOpportunityUnlock() {
    this.opportunityLock = false;
  }


  /**
   *  Inserts the current user record into the database
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public synchronized boolean insert(Connection db, ActionContext context) throws SQLException {

    if (!isValid(db, context)) {
      return false;
    }

    try {
      db.setAutoCommit(false);

      StringBuffer sql = new StringBuffer();
      sql.append(
        "INSERT INTO access " +
        "(username, password, contact_id, " +
        "manager_id, role_id, enteredby, modifiedby ) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?) ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setString(++i, getUsername());
      pst.setString(++i, encryptPassword(password1));
      pst.setInt(++i, contact.getId());
      pst.setInt(++i, getManagerId());
      pst.setInt(++i, getRoleId());
      pst.setInt(++i, getEnteredBy());
      pst.setInt(++i, getModifiedBy());
      pst.execute();
      pst.close();

      Statement st = db.createStatement();
      ResultSet rs = st.executeQuery("select currval('access_user_id_seq')");
      if (rs.next()) {
        this.setId(rs.getInt(1));
      }
      rs.close();
      st.close();
      
      st.executeUpdate("UPDATE contact SET user_id = " + id + " WHERE contact_id = " + contact.getId());
      
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    }
    db.setAutoCommit(true);
    return true;
  }


  /**
   *  Deletes the current user record
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID not specified.");
    }

    int resultCount = 0;
    Statement st = db.createStatement();
    resultCount = st.executeUpdate("DELETE FROM access WHERE user_id = " + this.getId());
    st.executeUpdate("UPDATE contact SET user_id = null WHERE user_id = " + this.getId());
    st.close();

    if (resultCount == 0) {
      errors.put("actionError", "User could not be deleted because it no longer exists.");
      return false;
    } else {
      return true;
    }
  }


  /**
   *  This method builds are extra data for a user... Permissions, Children
   *  Users, etc.
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.12
   */
  public void buildResources(Connection db) throws SQLException {
    if (buildContact || buildHierarchy) {
      if (this.getContactId() > -1) {
        contact = new Contact(db, "" + this.getContactId());
      }
    }
    if (buildPermissions) {
      buildPermissions(db);
    }
    if (buildHierarchy) {
      buildChildren(db);
    }
  }


  /**
   *  Updates the current user record
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public int update(Connection db, ActionContext context) throws SQLException {
    int i = -1;
    i = this.update(db, context, false);
    return i;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.18
   */
  public void updateLogin(Connection db) throws SQLException {
    if (this.id > -1) {
      Statement st = db.createStatement();
      st.execute("UPDATE access SET last_login = CURRENT_TIMESTAMP WHERE user_id = " + this.id);
      st.close();
    }
  }


  /**
   *  Description of the Method
   *
   *@param  thisPermission  Description of Parameter
   *@return                 Description of the Returned Value
   *@since                  1.18
   */
  public boolean hasPermission(String thisPermission) {
    if (permissions.contains(thisPermission)) {
      return true;
    } else {
      return false;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  userId            Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.10
   */
  public void buildRecord(Connection db, int userId) throws SQLException {
    Statement st = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT a.*, r.role, c.namelast, c.namefirst, " +
        "m.namefirst as mgr_namefirst, m.namelast as mgr_namelast " +
        "FROM access a " +
        "LEFT JOIN contact c ON (a.contact_id = c.contact_id) " +
        "LEFT JOIN contact m ON (a.manager_id = m.user_id), " +
        "role r " +
        "WHERE a.role_id = r.role_id " +
        "AND a.enabled = true " +
        "AND a.user_id = " + userId + " ");
    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      st.close();
      throw new SQLException("User record not found.");
    }
    rs.close();
    st.close();
    buildResources(db);
  }


  /**
   *  Gets the Valid attribute of the User object
   *
   *@param  db                Description of Parameter
   *@return                   The Valid value
   *@exception  SQLException  Description of Exception
   *@since                    1.12
   */
  protected boolean isValid(Connection db, ActionContext context) throws SQLException {
    isValidNoPass(db, context);

    if (contact == null || contact.getId() == -1) {
      errors.put("contactId", "User is not associated with a Contact");
    }

    if (password1 == null || password1.trim().equals("")) {
      errors.put("password1Error", "Password cannot be left blank");
    }

    if (!password1.equals(password2)) {
      errors.put("password2Error", "Verification password does not match the password");
    }

    if (hasErrors()) {
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Gets the ValidNoPass attribute of the User object
   *
   *@param  db                Description of Parameter
   *@return                   The ValidNoPass value
   *@exception  SQLException  Description of Exception
   *@since                    1.27
   */
  protected boolean isValidNoPass(Connection db, ActionContext context) throws SQLException {
    errors.clear();

    if (username == null || username.trim().equals("")) {
      errors.put("usernameError", "Username cannot be left blank");
    } else {
      if (isDuplicate(db)) {
        errors.put("usernameError", "Username is already in use");
      }
    }

    if (roleId < 0) {
      errors.put("roleError", "Role needs to be selected");
    }
    
    //No circular allowed -- check hierarchy context
    //Get id of the user being modified, see if the managerId is in their hierarchy
    if (managerId > 0 && id > -1) {
      if (managerId == id) {
        errors.put("managerIdError", "User cannot report to itself");
      } else {
        ConnectionElement ce = (ConnectionElement)context.getRequest().getSession().getAttribute("ConnectionElement");
        SystemStatus systemStatus = (SystemStatus)((Hashtable)context.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
        User updatedUser = systemStatus.getHierarchyList().getUser(id);
        User testChild = updatedUser.getChild(managerId);
        
        if (testChild != null) {
          errors.put("managerIdError", "Cannot create a circular hierarchy");
        }
      }
    }

    if (hasErrors()) {
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Populates the current user record from a ResultSet
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    this.setId(rs.getInt("user_id"));
    this.setUsername(rs.getString("username"));
    this.setPassword(rs.getString("password"));
    this.setContactId(rs.getInt("contact_id"));
    this.setRoleId(rs.getString("role_id"));
    this.setRole(rs.getString("role"));
    this.setManagerId(rs.getInt("manager_id"));

    String managerNameFirst = rs.getString("mgr_namefirst");
    String managerNameLast = rs.getString("mgr_namelast");
    if (managerNameFirst != null) {
      this.manager = managerNameLast + ", " + managerNameFirst;
    }

    java.sql.Timestamp tmpDateCreated = rs.getTimestamp("entered");
    if (tmpDateCreated != null) {
      entered = shortDateTimeFormat.format(tmpDateCreated);
    } else {
      entered = "";
    }

    enteredBy = rs.getInt("enteredby");

    java.sql.Timestamp tmpLastModified = rs.getTimestamp("modified");
    if (tmpLastModified != null) {
      modified = shortDateTimeFormat.format(tmpLastModified);
    } else {
      modified = "";
    }

    modifiedBy = rs.getInt("modifiedby");

    java.sql.Timestamp tmpLastLogin = rs.getTimestamp("last_login");
    if (tmpLastLogin != null) {
      lastLogin = shortDateTimeFormat.format(tmpLastLogin);
    } else {
      lastLogin = "";
    }

    enabled = rs.getBoolean("enabled");
  }


  /**
   *  Checks to see if the user is a duplicate in the database
   *
   *@param  db                Description of Parameter
   *@return                   The Duplicate value
   *@exception  SQLException  Description of Exception
   *@since                    1.11
   */
  private boolean isDuplicate(Connection db) throws SQLException {
    boolean duplicate = false;

    if (previousUsername != null && previousUsername.equals(username)) {
      return false;
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM access " +
        "WHERE lower(username) = lower(?) " +
        "AND enabled = true ");
    pst.setString(1, getUsername());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      duplicate = true;
    }
    rs.close();
    pst.close();
    return duplicate;
  }


  /**
   *  Updates this user's permissions from the database
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.28
   */
  private void buildPermissions(Connection db) throws SQLException {
    permissions.clear();
    UserPermissionList permissionList = new UserPermissionList(db, getRoleId());
    Iterator i = permissionList.iterator();
    while (i.hasNext()) {
      Permission thisPermission = (Permission)i.next();
      if (thisPermission.getAdd()) {
        permissions.addElement(thisPermission.getName() + "-add");
      }
      if (thisPermission.getView()) {
        permissions.addElement(thisPermission.getName() + "-view");
      }
      if (thisPermission.getEdit()) {
        permissions.addElement(thisPermission.getName() + "-edit");
      }
      if (thisPermission.getDelete()) {
        permissions.addElement(thisPermission.getName() + "-delete");
      }
    }
  }


  /**
   *  Updates the list of users that this user manages.
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.28
   */
  private void buildChildren(Connection db) throws SQLException {
    childUsers = new UserList(db, this, true);
  }


  /**
   *  Updates the current user record, this method is used internally
   *
   *@param  db                Description of Parameter
   *@param  override          Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  private int update(Connection db, ActionContext context, boolean override) throws SQLException {
    int resultCount = 0;

    if (!isValidNoPass(db, context)) {
      return -1;
    }

    if (this.getId() == -1) {
      throw new SQLException("User ID was not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();

    sql.append("UPDATE access ");
    sql.append("SET username = ?, manager_id = ?, role_id = ?  ");
    if (password1 != null) {
      sql.append("password = ?, ");
    }
    //sql.append("modified = CURRENT_TIMESTAMP, ");
    //sql.append("modifiedby = ? ");
    sql.append("WHERE user_id = ? ");
    //if (!override) {
    //	sql.append("AND modified = ? ");
    //}

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, username);
    //pst.setInt(++i, contact.getId());
    pst.setInt(++i, managerId);
    pst.setInt(++i, roleId);
    //pst.setBoolean(++i, getEnabled());
    if (password1 != null) {
      pst.setString(++i, encryptPassword(password1));
    }

    //pst.setInt(++i, getModifiedBy());

    pst.setInt(++i, getId());

    //if (!override) {
    //	pst.setTimestamp(++i, java.sql.Timestamp.valueOf(getModified()));
    //}

    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
  }


  /**
   *  Hashes a password... the resulting encrypted password cannot be decrypted
   *  since this is one-way.
   *
   *@param  tmp  Description of Parameter
   *@return      Description of the Returned Value
   *@since       1.1
   */
  private String encryptPassword(String tmp) {
    PasswordHash passwordHash = new PasswordHash();
    return passwordHash.encrypt(tmp);
  }
}

