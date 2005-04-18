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
package org.aspcfs.modules.admin.base;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import java.util.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.base.SyncableList;
import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.database.ConnectionElement;
import org.aspcfs.controller.SystemStatus;
import java.util.Hashtable;

/**
 *  A list of User objects.
 *
 * @author     matt rajkowski
 * @created    September 19, 2001
 * @version    $Id: UserList.java,v 1.51.12.1 2004/11/19 21:36:20 mrajkowski Exp
 *      $
 */
public class UserList extends Vector implements SyncableList {

  public final static int TRUE = 1;
  public final static int FALSE = 0;
  //Sync variables
  public final static String tableName = "access";
  public final static String uniqueField = "user_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  private PagedListInfo pagedListInfo = null;
  private String emptyHtmlSelectRecord = null;
  //Query properties
  private int enteredBy = -1;
  private int roleId = -1;
  private int managerId = -1;
  private User managerUser = null;
  private boolean buildContact = false;
  private boolean buildContactDetails = false;
  private boolean buildHierarchy = false;
  private boolean buildEmployeeUsersOnly = false;
  private boolean buildAccountUsersOnly = false;
  private boolean topLevel = false;
  private int department = -1;
  private int enabled = -1;
  private String jsEvent = null;
  private int includeAliases = Constants.FALSE;
  private int hidden = Constants.UNDEFINED;
  private Timestamp expires = null;
  private int expired = Constants.UNDEFINED;
  //Revenue specific properties
  private boolean buildRevenueYTD = false;
  private int revenueYear = -1;
  private int revenueType = 0;
  private boolean buildGrossPipelineValue = false;

  private boolean includeMe = false;
  private String myValue = "";
  private int myId = -1;
  private String username = null;

  private boolean excludeDisabledIfUnselected = false;
  private boolean includeUsersWithRolesOnly = true;
  private boolean excludeExpiredIfUnselected = false;
  private java.sql.Timestamp enteredRangeStart = null;
  private java.sql.Timestamp enteredRangeEnd = null;

  private int roleType = -1;


  //0 for regular users and 1 for portal users

  /**
   *  Constructor for the UserList object
   *
   * @since    1.1
   */
  public UserList() { }


  /**
   *  Constructor for the UserList object
   *
   * @param  db                Description of Parameter
   * @param  doHierarchy       Description of Parameter
   * @param  parentUser        Description of Parameter
   * @exception  SQLException  Description of Exception
   * @since                    1.9
   */
  public UserList(Connection db, User parentUser, boolean doHierarchy) throws SQLException {
    this.managerId = parentUser.getId();
    this.managerUser = parentUser;
    this.buildHierarchy = doHierarchy;
    buildList(db);
  }


  /**
   *  Sets the lastAnchor attribute of the UserList object
   *
   * @param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the lastAnchor attribute of the UserList object
   *
   * @param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the nextAnchor attribute of the UserList object
   *
   * @param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the UserList object
   *
   * @param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the syncType attribute of the UserList object
   *
   * @param  tmp  The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   *  Sets the syncType attribute of the UserList object
   *
   * @param  tmp  The new syncType value
   */
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }


  /**
   *  Gets the buildRevenueYTD attribute of the UserList object
   *
   * @return    The buildRevenueYTD value
   */
  public boolean getBuildRevenueYTD() {
    return buildRevenueYTD;
  }


  /**
   *  Sets the buildRevenueYTD attribute of the UserList object
   *
   * @param  buildRevenueYTD  The new buildRevenueYTD value
   */
  public void setBuildRevenueYTD(boolean buildRevenueYTD) {
    this.buildRevenueYTD = buildRevenueYTD;
  }


  /**
   *  Gets the excludeDisabledIfUnselected attribute of the UserList object
   *
   * @return    The excludeDisabledIfUnselected value
   */
  public boolean getExcludeDisabledIfUnselected() {
    return excludeDisabledIfUnselected;
  }


  /**
   *  Gets the includeUsersWithRolesOnly attribute of the UserList object
   *
   * @return    The includeUsersWithRolesOnly value
   */
  public boolean getIncludeUsersWithRolesOnly() {
    return includeUsersWithRolesOnly;
  }



  /**
   *  Sets the excludeDisabledIfUnselected attribute of the UserList object
   *
   * @param  excludeDisabledIfUnselected  The new excludeDisabledIfUnselected
   *      value
   */
  public void setExcludeDisabledIfUnselected(boolean excludeDisabledIfUnselected) {
    this.excludeDisabledIfUnselected = excludeDisabledIfUnselected;
  }


  /**
   *  Sets the includeUsersWithRolesOnly attribute of the UserList object
   *
   * @param  tmp  The new includeUsersWithRolesOnly value
   */
  public void setIncludeUsersWithRolesOnly(boolean tmp) {
    this.includeUsersWithRolesOnly = tmp;
  }


  /**
   *  Sets the enteredRangeStart attribute of the UserList object
   *
   * @param  tmp  The new enteredRangeStart value
   */
  public void setEnteredRangeStart(java.sql.Timestamp tmp) {
    this.enteredRangeStart = tmp;
  }


  /**
   *  Sets the enteredRangeEnd attribute of the UserList object
   *
   * @param  tmp  The new enteredRangeEnd value
   */
  public void setEnteredRangeEnd(java.sql.Timestamp tmp) {
    this.enteredRangeEnd = tmp;
  }


  /**
   *  Sets the PagedListInfo attribute of the UserList object
   *
   * @param  tmp  The new PagedListInfo value
   * @since       1.4
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the roleType attribute of the UserList object
   *
   * @param  tmp  The new roleType value
   */
  public void setRoleType(int tmp) {
    this.roleType = tmp;
  }


  /**
   *  Sets the roleType attribute of the UserList object
   *
   * @param  tmp  The new roleType value
   */
  public void setRoleType(String tmp) {
    this.roleType = Integer.parseInt(tmp);
  }


  /**
   *  Gets the buildGrossPipelineValue attribute of the UserList object
   *
   * @return    The buildGrossPipelineValue value
   */
  public boolean getBuildGrossPipelineValue() {
    return buildGrossPipelineValue;
  }


  /**
   *  Sets the buildGrossPipelineValue attribute of the UserList object
   *
   * @param  buildGrossPipelineValue  The new buildGrossPipelineValue value
   */
  public void setBuildGrossPipelineValue(boolean buildGrossPipelineValue) {
    this.buildGrossPipelineValue = buildGrossPipelineValue;
  }


  /**
   *  Sets the EmptyHtmlSelectRecord attribute of the UserList object
   *
   * @param  tmp  The new EmptyHtmlSelectRecord value
   * @since       1.4
   */
  public void setEmptyHtmlSelectRecord(String tmp) {
    this.emptyHtmlSelectRecord = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the UserList object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the RoleId attribute of the UserList object
   *
   * @param  tmp  The new RoleId value
   * @since       1.8
   */
  public void setRoleId(int tmp) {
    this.roleId = tmp;
  }


  /**
   *  Sets the Department attribute of the UserList object
   *
   * @param  department  The new Department value
   */
  public void setDepartment(int department) {
    this.department = department;
  }


  /**
   *  Sets the Department attribute of the UserList object
   *
   * @param  department  The new Department value
   */
  public void setDepartment(String department) {
    this.department = Integer.parseInt(department);
  }


  /**
   *  Gets the revenueType attribute of the UserList object
   *
   * @return    The revenueType value
   */
  public int getRevenueType() {
    return revenueType;
  }


  /**
   *  Sets the revenueType attribute of the UserList object
   *
   * @param  revenueType  The new revenueType value
   */
  public void setRevenueType(int revenueType) {
    this.revenueType = revenueType;
  }


  /**
   *  Sets the username attribute of the UserList object
   *
   * @param  username  The new username value
   */
  public void setUsername(String username) {
    this.username = username;
  }


  /**
   *  Sets the JsEvent attribute of the UserList object
   *
   * @param  jsEvent  The new JsEvent value
   */
  public void setJsEvent(String jsEvent) {
    this.jsEvent = jsEvent;
  }


  /**
   *  Sets the Enabled attribute of the UserList object
   *
   * @param  tmp  The new Enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the IncludeMe attribute of the UserList object
   *
   * @param  tmp  The new IncludeMe value
   * @since       1.19
   */
  public void setIncludeMe(boolean tmp) {
    this.includeMe = tmp;
  }


  /**
   *  Gets the revenueYear attribute of the UserList object
   *
   * @return    The revenueYear value
   */
  public int getRevenueYear() {
    return revenueYear;
  }


  /**
   *  Sets the revenueYear attribute of the UserList object
   *
   * @param  revenueYear  The new revenueYear value
   */
  public void setRevenueYear(int revenueYear) {
    this.revenueYear = revenueYear;
  }


  /**
   *  Sets the MyValue attribute of the UserList object
   *
   * @param  tmp  The new MyValue value
   * @since       1.19
   */
  public void setMyValue(String tmp) {
    this.myValue = tmp;
  }


  /**
   *  Sets the MyId attribute of the UserList object
   *
   * @param  tmp  The new MyId value
   * @since       1.19
   */
  public void setMyId(int tmp) {
    this.myId = tmp;
  }


  /**
   *  Sets the includeAliases attribute of the UserList object
   *
   * @param  tmp  The new includeAliases value
   */
  public void setIncludeAliases(int tmp) {
    this.includeAliases = tmp;
  }


  /**
   *  Sets the ManagerId attribute of the UserList object
   *
   * @param  tmp  The new ManagerId value
   * @since       1.8
   */
  public void setManagerId(int tmp) {
    this.managerId = tmp;
  }


  /**
   *  Sets the TopLevel attribute of the UserList object
   *
   * @param  tmp  The new TopLevel value
   * @since       1.20
   */
  public void setTopLevel(boolean tmp) {
    this.topLevel = tmp;
  }



  /**
   *  Sets the BuildContact attribute of the UserList object
   *
   * @param  tmp  The new BuildContact value
   * @since       1.18
   */
  public void setBuildContact(boolean tmp) {
    this.buildContact = tmp;
  }


  /**
   *  Sets the buildContactDetails attribute of the UserList object
   *
   * @param  tmp  The new buildContactDetails value
   */
  public void setBuildContactDetails(boolean tmp) {
    this.buildContactDetails = tmp;
  }


  /**
   *  Sets the BuildHierarchy attribute of the UserList object
   *
   * @param  tmp  The new BuildHierarchy value
   * @since       1.17
   */
  public void setBuildHierarchy(boolean tmp) {
    this.buildHierarchy = tmp;
  }


  /**
   *  Sets the buildEmployeeUsersOnly attribute of the UserList object
   *
   * @param  tmp  The new buildEmployeeUsersOnly value
   */
  public void setBuildEmployeeUsersOnly(boolean tmp) {
    this.buildEmployeeUsersOnly = tmp;
  }


  /**
   *  Sets the buildEmployeeUsersOnly attribute of the UserList object
   *
   * @param  tmp  The new buildEmployeeUsersOnly value
   */
  public void setBuildEmployeeUsersOnly(String tmp) {
    this.buildEmployeeUsersOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the buildAccountUsersOnly attribute of the UserList object
   *
   * @param  tmp  The new buildAccountUsersOnly value
   */
  public void setBuildAccountUsersOnly(boolean tmp) {
    this.buildAccountUsersOnly = tmp;
  }


  /**
   *  Sets the buildAccountUsersOnly attribute of the UserList object
   *
   * @param  tmp  The new buildAccountUsersOnly value
   */
  public void setBuildAccountUsersOnly(String tmp) {
    this.buildAccountUsersOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the ManagerUser attribute of the UserList object
   *
   * @param  tmp  The new ManagerUser value
   */
  public void setManagerUser(User tmp) {
    this.managerUser = tmp;
  }


  /**
   *  Gets the tableName attribute of the UserList object
   *
   * @return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the uniqueField attribute of the UserList object
   *
   * @return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Gets the username attribute of the UserList object
   *
   * @return    The username value
   */
  public String getUsername() {
    return username;
  }


  /**
   *  Gets the includeAliases attribute of the UserList object
   *
   * @return    The includeAliases value
   */
  public int getIncludeAliases() {
    return includeAliases;
  }


  /**
   *  Gets the JsEvent attribute of the UserList object
   *
   * @return    The JsEvent value
   */
  public String getJsEvent() {
    return jsEvent;
  }


  /**
   *  Gets the Department attribute of the UserList object
   *
   * @return    The Department value
   */
  public int getDepartment() {
    return department;
  }


  /**
   *  Gets the IncludeMe attribute of the UserList object
   *
   * @return    The IncludeMe value
   * @since     1.19
   */
  public boolean getIncludeMe() {
    return includeMe;
  }


  /**
   *  Gets the MyValue attribute of the UserList object
   *
   * @return    The MyValue value
   * @since     1.19
   */
  public String getMyValue() {
    return myValue;
  }


  /**
   *  Gets the MyId attribute of the UserList object
   *
   * @return    The MyId value
   * @since     1.19
   */
  public int getMyId() {
    return myId;
  }


  /**
   *  Gets the ListSize attribute of the UserList object
   *
   * @return    The ListSize value
   * @since     1.9
   */
  public int getListSize() {
    return this.size();
  }


  /**
   *  Gets the HtmlSelect attribute of the UserList object
   *
   * @param  selectName  Description of Parameter
   * @return             The HtmlSelect value
   * @since              1.4
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   *  Gets the HtmlSelect attribute of the UserList object
   *
   * @param  selectName  Description of Parameter
   * @param  defaultKey  Description of Parameter
   * @return             The HtmlSelect value
   * @since              1.4
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect userListSelect = new HtmlSelect();
    userListSelect.setJsEvent(jsEvent);

    if (emptyHtmlSelectRecord != null) {
      userListSelect.addItem(-1, emptyHtmlSelectRecord);
    }
    if (includeMe == true) {
      userListSelect.addItem(myId, myValue);
    }
    Iterator i = this.iterator();
    while (i.hasNext()) {
      User thisUser = (User) i.next();
      String elementText = null;
      Timestamp currentTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
      elementText = thisUser.getContact().getValidName();
      if (!thisUser.getEnabled() || (thisUser.getExpires() != null && currentTime.after(thisUser.getExpires()))) {
        elementText += " *";
      }

      if (thisUser.getEnabled() || (!thisUser.getEnabled() && !excludeDisabledIfUnselected) || (excludeDisabledIfUnselected && thisUser.getId() == defaultKey)) {
        if (thisUser.getExpires() == null || (thisUser.getExpires() != null && currentTime.before(thisUser.getExpires())) || ((thisUser.getExpires() != null && currentTime.after(thisUser.getExpires())) && !excludeExpiredIfUnselected) || (excludeExpiredIfUnselected && thisUser.getId() == defaultKey)) {
          if (thisUser.getId() != 0) {
            userListSelect.addItem(
                thisUser.getId(),
                elementText);
          }
        }
      }
    }
    return userListSelect.getHtml(selectName, defaultKey);
  }



  /**
   *  Gets the htmlSelectObj attribute of the UserList object
   *
   * @param  selectName  Description of the Parameter
   * @param  defaultKey  Description of the Parameter
   * @return             The htmlSelectObj value
   */
  public HtmlSelect getHtmlSelectObj(String selectName, int defaultKey) {
    HtmlSelect userListSelect = new HtmlSelect();
    userListSelect.setJsEvent(jsEvent);
    if (emptyHtmlSelectRecord != null) {
      userListSelect.addItem(-1, emptyHtmlSelectRecord);
    }
    if (includeMe == true) {
      userListSelect.addItem(myId, myValue);
    }
    Iterator i = this.iterator();
    while (i.hasNext()) {
      User thisUser = (User) i.next();
      String elementText = null;
      Timestamp currentTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
      elementText = thisUser.getContact().getValidName();
      if (!thisUser.getEnabled() || (thisUser.getExpires() != null && currentTime.after(thisUser.getExpires()))) {
        elementText += " *";
      }
      if (thisUser.getEnabled() || (!thisUser.getEnabled() && !excludeDisabledIfUnselected) || (excludeDisabledIfUnselected && thisUser.getId() == defaultKey)) {
        userListSelect.addItem(thisUser.getId(), elementText);
      }
    }
    return userListSelect;
  }


  /**
   *  Gets the UserListIds attribute of the UserList object
   *
   * @param  toInclude  Description of Parameter
   * @return            The UserListIds value
   * @since             1.17
   */
  public String getUserListIds(int toInclude) {
    StringBuffer values = new StringBuffer();
    values.append(String.valueOf(toInclude));
    Iterator i = this.iterator();
    if (i.hasNext()) {
      values.append(", ");
    }
    while (i.hasNext()) {
      User thisUser = (User) i.next();
      values.append(String.valueOf(thisUser.getId()));
      if (i.hasNext()) {
        values.append(", ");
      }
    }
    return values.toString();
  }


  /**
   *  Gets the UserListIds attribute of the UserList object
   *
   * @return    The UserListIds value
   * @since     1.16
   */
  public String getUserListIds() {
    StringBuffer values = new StringBuffer();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      User thisUser = (User) i.next();
      values.append(String.valueOf(thisUser.getId()));
      if (i.hasNext()) {
        values.append(", ");
      }
    }
    return values.toString();
  }


  /**
   *  Gets the hidden attribute of the UserList object
   *
   * @return    The hidden value
   */
  public int getHidden() {
    return hidden;
  }


  /**
   *  Sets the hidden attribute of the UserList object
   *
   * @param  tmp  The new hidden value
   */
  public void setHidden(int tmp) {
    this.hidden = tmp;
  }


  /**
   *  Sets the hidden attribute of the UserList object
   *
   * @param  tmp  The new hidden value
   */
  public void setHidden(String tmp) {
    this.hidden = Integer.parseInt(tmp);
  }


  /**
   *  Gets the expires attribute of the UserList object
   *
   * @return    The expires value
   */
  public Timestamp getExpires() {
    return expires;
  }


  /**
   *  Sets the expires attribute of the UserList object
   *
   * @param  tmp  The new expires value
   */
  public void setExpires(Timestamp tmp) {
    this.expires = tmp;
  }


  /**
   *  Sets the expires attribute of the UserList object
   *
   * @param  tmp  The new expires value
   */
  public void setExpires(String tmp) {
    this.expires = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the expired attribute of the UserList object
   *
   * @return    The expired value
   */
  public int getExpired() {
    return expired;
  }


  /**
   *  Sets the expired attribute of the UserList object
   *
   * @param  tmp  The new expired value
   */
  public void setExpired(int tmp) {
    this.expired = tmp;
  }


  /**
   *  Sets the expired attribute of the UserList object
   *
   * @param  tmp  The new expired value
   */
  public void setExpired(String tmp) {
    this.expired = Integer.parseInt(tmp);
  }


  /**
   *  Gets the User attribute of the UserList object
   *
   * @param  childId  Description of Parameter
   * @return          The User value
   */
  public User getUser(int childId) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      User thisUser = (User) i.next();
      if (thisUser.getId() == childId) {
        return thisUser;
      }
      User childUser = thisUser.getChild(childId);
      if (childUser != null) {
        return childUser;
      }
    }
    return null;
  }


  /**
   *  Gets the TopUser attribute of the UserList object
   *
   * @param  userId  Description of Parameter
   * @return         The TopUser value
   */
  public User getTopUser(int userId) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      User thisUser = (User) i.next();
      if (thisUser.getId() == userId) {
        return thisUser;
      }
    }
    return null;
  }


  /**
   *  Gets the ManagerUser attribute of the UserList object
   *
   * @return    The ManagerUser value
   */
  public User getManagerUser() {
    return managerUser;
  }


  /**
   *  Gets the object attribute of the UserList object
   *
   * @param  rs                Description of Parameter
   * @return                   The object value
   * @exception  SQLException  Description of Exception
   */
  public User getObject(ResultSet rs) throws SQLException {
    User thisUser = new User(rs);
    return thisUser;
  }


  /**
   *  Gets the roleType attribute of the UserList object
   *
   * @return    The roleType value
   */
  public int getRoleType() {
    return roleType;
  }


  /**
   *  Gets the excludeExpiredIfUnselected attribute of the UserList object
   *
   * @return    The excludeExpiredIfUnselected value
   */
  public boolean getExcludeExpiredIfUnselected() {
    return excludeExpiredIfUnselected;
  }


  /**
   *  Sets the excludeExpiredIfUnselected attribute of the UserList object
   *
   * @param  tmp  The new excludeExpiredIfUnselected value
   */
  public void setExcludeExpiredIfUnselected(boolean tmp) {
    this.excludeExpiredIfUnselected = tmp;
  }


  /**
   *  Sets the excludeExpiredIfUnselected attribute of the UserList object
   *
   * @param  tmp  The new excludeExpiredIfUnselected value
   */
  public void setExcludeExpiredIfUnselected(String tmp) {
    this.excludeExpiredIfUnselected = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of Parameter
   * @exception  SQLException  Description of Exception
   */
  public void select(Connection db) throws SQLException {
    buildList(db);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  newOwner          Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int reassignElements(Connection db, int newOwner) throws SQLException {
    int total = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      User thisUser = (User) i.next();
      if (thisUser.reassign(db, newOwner)) {
        total++;
      }
    }
    return total;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  newOwner          Description of the Parameter
   * @param  userId            Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int reassignElements(Connection db, int newOwner, int userId) throws SQLException {
    int total = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      User thisUser = (User) i.next();
      thisUser.setModifiedBy(userId);
      if (thisUser.reassign(db, newOwner)) {
        total++;
      }
    }
    return total;
  }


  /**
   *  Generates the user list from the database
   *
   * @param  db                Description of Parameter
   * @exception  SQLException  Description of Exception
   * @since                    1.4
   */
  public void buildList(Connection db) throws SQLException {
    //A super query -- builds the user and contact data at same time
    PreparedStatement pst = null;
    ResultSet rs = queryList(db, pst);
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.isEndOfOffset(db)) {
        break;
      }
      User thisUser = new User(rs);
      if (thisUser.getContactId() > -1) {
        thisUser.setContact(new Contact(rs));
      }
      if (managerUser != null) {
        thisUser.setManagerId(managerUser.getId());
        thisUser.setManagerUser(managerUser);
      }
      this.add(thisUser);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
    buildResources(db);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildRevenueYTD(Connection db) throws SQLException {
    Iterator x = this.iterator();
    while (x.hasNext()) {
      User tempUser = (User) x.next();
      tempUser.buildRevenueYTD(db, this.getRevenueYear(), this.getRevenueType());
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildPipelineValues(Connection db) throws SQLException {
    Iterator x = this.iterator();
    while (x.hasNext()) {
      User tempUser = (User) x.next();
      tempUser.buildGrossPipelineValue(db);
    }
  }


  /**
   *  This method is required for synchronization, it allows for the resultset
   *  to be streamed with lower overhead
   *
   * @param  db                Description of Parameter
   * @param  pst               Description of Parameter
   * @return                   Description of the Returned Value
   * @exception  SQLException  Description of Exception
   */
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
    ResultSet rs = null;
    int items = -1;
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM access a " +
        "LEFT JOIN contact c ON (a.contact_id = c.contact_id) " +
        "LEFT JOIN role r ON (a.role_id = r.role_id) " +
        "WHERE a.user_id > -1 ");
    createFilter(sqlFilter);
    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() +
          sqlFilter.toString());
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
        pst = db.prepareStatement(
            sqlCount.toString() +
            sqlFilter.toString() +
            "AND lower(c.namelast) < ? ");
        items = prepareFilter(pst);
        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }
      //Determine column to sort by
      pagedListInfo.setDefaultSort("a.enabled DESC,c.namelast", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY a.enabled DESC,c.namelast ");
    }
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "a.username, a.password, a.role_id, a.last_login, a.manager_id, " +
        "a.last_ip, a.timezone, a.startofday as access_startofday, " +
        "a.endofday as access_endofday, a.expires, a.alias, " +
        "a.contact_id as contact_id_link, a.user_id as access_user_id, " +
        "a.enabled as access_enabled, a.assistant, " +
        "a.entered as access_entered, a.enteredby as access_enteredby, " +
        "a.modified as access_modified, a.modifiedby as access_modifiedby, " +
        "a.currency, a.language, a.webdav_password, a.hidden, " +
        "r.role, r.role_type, " +
        "m_usr.enabled as mgr_enabled, " +
        "c.*, d.description as departmentname, " +
        "o.name as org_name, o.enabled as orgenabled " +
        "FROM access a " +
        "LEFT JOIN contact c ON (a.contact_id = c.contact_id) " +
        "LEFT JOIN organization o ON (c.org_id = o.org_id) " +
        "LEFT JOIN lookup_department d ON (c.department = d.code) " +
        "LEFT JOIN access m_usr ON (a.manager_id = m_usr.user_id) " +
        "LEFT JOIN role r ON (a.role_id = r.role_id) " +
        "WHERE a.user_id > -1 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    return rs;
  }


  /**
   *  For each user, the contact information is retrieved
   *
   * @param  db                Description of Parameter
   * @exception  SQLException  Description of Exception
   * @since                    1.4
   */
  private void buildResources(Connection db) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      User thisUser = (User) i.next();
      thisUser.setBuildContact(buildContact);
      thisUser.setBuildContactDetails(buildContactDetails);
      thisUser.setBuildHierarchy(buildHierarchy);
      if (buildContact || buildHierarchy) {
        thisUser.buildResources(db);
      }
      if (buildRevenueYTD && revenueYear > -1) {
        thisUser.buildRevenueYTD(db, this.getRevenueYear(), this.getRevenueType());
      }
      if (buildGrossPipelineValue) {
        thisUser.buildGrossPipelineValue(db);
      }
    }
  }


  /**
   *  Limits the recods that are retrieved, works with prepareFilter
   *
   * @param  sqlFilter  Description of Parameter
   * @since             1.4
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (includeAliases == Constants.TRUE) {
      sqlFilter.append("AND a.alias > -1 ");
    }
    if (includeAliases == Constants.FALSE) {
      sqlFilter.append("AND a.alias = -1 ");
    }
    if (enteredBy > -1) {
      sqlFilter.append("AND a.enteredby = ? ");
    }
    if (roleId > -1) {
      sqlFilter.append("AND r.role_id = ? ");
    }
    if (managerId > -1) {
      sqlFilter.append("AND a.manager_id = ? ");
    }

    if (department > -1) {
      if (department == 0) {
        sqlFilter.append("AND c.department IS NULL ");
      } else {
        sqlFilter.append("AND c.department = ? ");
      }
    } else {
      if (topLevel) {
        sqlFilter.append("AND a.manager_id = -1 ");
      } else {
        sqlFilter.append("AND a.contact_id > -1 ");
      }
    }
    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND a.enabled = ? ");
    }
    if (username != null) {
      sqlFilter.append("AND a.username = ? ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND a.entered > ? ");
      }
      sqlFilter.append("AND a.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND a.modified > ? ");
      sqlFilter.append("AND a.entered < ? ");
      sqlFilter.append("AND a.modified < ? ");
    }
    if (enteredRangeStart != null) {
      sqlFilter.append("AND a.entered >= ? ");
    }
    if (enteredRangeEnd != null) {
      sqlFilter.append("AND a.entered <= ? ");
    }
    if (includeUsersWithRolesOnly) {
      sqlFilter.append("AND a.role_id > -1 AND a.role_id IS NOT NULL ");
    }
    if (roleType != -1) {
      sqlFilter.append("AND r.role_type = ? ");
    }
    if (buildEmployeeUsersOnly) {
      sqlFilter.append("AND c.org_id = 0 ");
    }
    if (buildAccountUsersOnly) {
      sqlFilter.append("AND c.org_id > 0 ");
    }
    if (hidden != Constants.UNDEFINED) {
      sqlFilter.append("AND a.hidden = ? ");
    }
    if (expired != Constants.UNDEFINED) {
      sqlFilter.append("AND a.expires " + (expired == Constants.TRUE ? "<" : ">") + " ? ");
    }
  }


  /**
   *  Limits the recods that are retrieved, works with createFilter
   *
   * @param  pst               Description of Parameter
   * @return                   Description of the Returned Value
   * @exception  SQLException  Description of Exception
   * @since                    1.4
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (enteredBy > -1) {
      pst.setInt(++i, enteredBy);
    }
    if (roleId > -1) {
      pst.setInt(++i, roleId);
    }
    if (managerId > -1) {
      pst.setInt(++i, managerId);
    }
    if (department > 0) {
      pst.setInt(++i, department);
    }
    if (enabled != Constants.UNDEFINED) {
      pst.setBoolean(++i, enabled == TRUE);
    }
    if (username != null) {
      pst.setString(++i, username);
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      pst.setTimestamp(++i, nextAnchor);
    }
    if (syncType == Constants.SYNC_UPDATES) {
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, nextAnchor);
    }
    if (enteredRangeStart != null) {
      pst.setTimestamp(++i, enteredRangeStart);
    }
    if (enteredRangeEnd != null) {
      pst.setTimestamp(++i, enteredRangeEnd);
    }
    if (roleType != -1) {
      pst.setInt(++i, roleType);
    }
    if (hidden != Constants.UNDEFINED) {
      pst.setBoolean(++i, hidden == Constants.TRUE);
    }
    if (expired != Constants.UNDEFINED) {
      pst.setTimestamp(++i, (expires != null) ? expires : new Timestamp(Calendar.getInstance().getTimeInMillis()));
    }
    return i;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int queryRecordCount(Connection db) throws SQLException {
    int recordCount = 0;
    StringBuffer sqlFilter = new StringBuffer();
    String sqlCount =
        "SELECT COUNT(*) AS recordcount " +
        "FROM access a " +
        "LEFT JOIN contact c ON (a.contact_id = c.contact_id), " +
        "role r " +
        "WHERE a.role_id = r.role_id ";
    createFilter(sqlFilter);
    PreparedStatement pst = db.prepareStatement(sqlCount + sqlFilter.toString());
    int items = prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      recordCount = DatabaseUtils.getInt(rs, "recordcount", 0);
    }
    rs.close();
    pst.close();
    return recordCount;
  }


  /**
   *  A convenient method to retrieve a specific contact from the cache
   *
   * @param  context  Description of the Parameter
   * @param  userId   Description of the Parameter
   * @return          Description of the Return Value
   */
  public final static Contact retrieveUserContact(ActionContext context, int userId) {
    ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute("ConnectionElement");
    if (ce == null) {
      return null;
    }
    SystemStatus systemStatus = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    if (systemStatus == null) {
      return null;
    }
    User thisUser = systemStatus.getUser(userId);
    if (thisUser != null) {
      Contact thisContact = thisUser.getContact();
      return thisContact;
    } else {
      return null;
    }
  }


  /**
   *  Description of the Method
   *
   * @param  inList       Description of the Parameter
   * @param  currentList  Description of the Parameter
   * @return              Description of the Return Value
   */
  public static UserList sortEnabledUsers(UserList inList, UserList currentList) {
    int counter = 0;
    Iterator iterator = inList.iterator();
    while (iterator.hasNext()) {
      User thisUser = (User) iterator.next();
      if (!thisUser.getHidden()) {
        if (thisUser.getEnabled()) {
          currentList.add(counter++, thisUser);
        } else {
          currentList.add(thisUser);
        }
      }
    }
    return currentList;
  }
}


