//Copyright 2001 Dark Horse Ventures

package org.aspcfs.modules.communications.base;

import java.util.*;
import java.sql.*;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.contacts.base.Contact;
import java.text.*;

/**
 *  SearchCriteriaList contains the definitions for querying a ContactList. For
 *  example, "Contacts with a 23456 zip code" <p>
 *
 *  Contains a group of criteria groups, that each contain criteria elements. A
 *  group is a unique field that can have multiple criteria.
 *
 *@author     Wesley_S_Gillette
 *@created    November 9, 2001
 *@version    $Id: SearchCriteriaList.java,v 1.4 2001/12/11 22:21:17 mrajkowski
 *      Exp $
 */
public class SearchCriteriaList extends HashMap {

  protected HashMap errors = new HashMap();
  private int id = -1;
  private String groupName = "";
  private int contactSource = -1;
  private java.sql.Timestamp modified = null;
  private java.sql.Timestamp entered = null;
  private int modifiedBy = -1;
  private int enteredBy = -1;
  private int owner = -1;
  private String ownerIdRange = null;
  private String saveCriteria = "";
  private String htmlSelectIdName = null;
  private boolean onlyContactIds = true;
  public final static int SOURCE_MY_CONTACTS = 1;
  public final static int SOURCE_ALL_CONTACTS = 2;
  public final static int SOURCE_ALL_ACCOUNTS = 3;
  public final static int SOURCE_EMPLOYEES = 4;
  public final static int CONTACT_SOURCE_ELEMENTS = 4;


  /**
   *  Constructor for the SearchCriteriaList object
   *
   *@since    1.1
   */
  public SearchCriteriaList() { }


  /**
   *  Constructor for the SearchCriteriaList object
   *
   *@param  rs                data resultset from query
   *@exception  SQLException  SQL Exception
   *@since
   */
  public SearchCriteriaList(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the SearchCriteriaList object
   *
   *@param  db                db connection
   *@param  id                unique id of this SCL
   *@exception  SQLException  SQL Exception
   *@since
   */
  public SearchCriteriaList(Connection db, String id) throws SQLException {
    queryRecord(db, Integer.parseInt(id));
  }


  /**
   *  Constructor for the SearchCriteriaList object
   *
   *@param  db                db connection
   *@param  id                unique id of this SCL
   *@exception  SQLException  SQL Exception
   */
  public SearchCriteriaList(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Constructor for the SearchCriteriaList object, takes in a string of values
   *  delimited by a "^", each element is delimited by a "|", and stores the
   *  constructed group.
   *
   *@param  searchCriteriaText  delimited String to be parsed
   *@since                      1.1
   */
  public SearchCriteriaList(String searchCriteriaText) {
    SearchCriteriaGroup thisGroup = null;
    StringTokenizer st = new StringTokenizer(searchCriteriaText, "^");
    while (st.hasMoreTokens()) {
      String tmpCriteria = (String) st.nextToken();
      SearchCriteriaElement thisElement = new SearchCriteriaElement(tmpCriteria);
      Integer thisKey = new Integer(thisElement.getFieldId());

      if (thisKey.intValue() != Constants.CAMPAIGN_CONTACT_ID) {
        onlyContactIds = false;
      }

      if (this.containsKey(thisKey)) {
        thisGroup = (SearchCriteriaGroup) this.get(thisKey);
      } else {
        thisGroup = new SearchCriteriaGroup();
        thisGroup.getGroupField().setId(thisElement.getFieldId());
        this.put(thisKey, thisGroup);
      }
      thisGroup.add(thisElement);
    }
  }


  /**
   *  Sets the OnlyContactIds attribute of the SearchCriteriaList object
   *
   *@param  onlyContactIds  The new OnlyContactIds value
   */
  public void setOnlyContactIds(boolean onlyContactIds) {
    this.onlyContactIds = onlyContactIds;
  }


  /**
   *  Sets the Errors attribute of the SearchCriteriaList object
   *
   *@param  errors  The new Errors value
   *@since
   */
  public void setErrors(HashMap errors) {
    this.errors = errors;
  }


  /**
   *  Sets the Modified attribute of the SearchCriteriaList object
   *
   *@param  tmp  The new Modified value
   *@since
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the Entered attribute of the SearchCriteriaList object
   *
   *@param  tmp  The new Entered value
   *@since
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the ModifiedBy attribute of the SearchCriteriaList object
   *
   *@param  tmp  The new ModifiedBy value
   *@since
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the EnteredBy attribute of the SearchCriteriaList object
   *
   *@param  tmp  The new EnteredBy value
   *@since
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the SaveCriteria attribute of the SearchCriteriaList object
   *
   *@param  saveCriteria  The new SaveCriteria value
   *@since
   */
  public void setSaveCriteria(String saveCriteria) {
    this.saveCriteria = saveCriteria;
  }


  /**
   *  Sets the Owner attribute of the SearchCriteriaList object
   *
   *@param  tmp  The new Owner value
   *@since
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }


  /**
   *  Sets the HtmlSelectIdName attribute of the SearchCriteriaList object
   *
   *@param  htmlSelectIdName  The new HtmlSelectIdName value
   */
  public void setHtmlSelectIdName(String htmlSelectIdName) {
    this.htmlSelectIdName = htmlSelectIdName;
  }


  /**
   *  Sets the OwnerIdRange attribute of the SearchCriteriaList object
   *
   *@param  tmp  The new OwnerIdRange value
   *@since
   */
  public void setOwnerIdRange(String tmp) {
    this.ownerIdRange = tmp;
  }


  /**
   *  Sets the ModifiedBy attribute of the SearchCriteriaList object
   *
   *@param  tmp  The new ModifiedBy value
   *@since
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the EnteredBy attribute of the SearchCriteriaList object
   *
   *@param  tmp  The new EnteredBy value
   *@since
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
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
   *  Sets the Owner attribute of the SearchCriteriaList object
   *
   *@param  tmp  The new Owner value
   *@since
   */
  public void setOwner(String tmp) {
    this.owner = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Id attribute of the SearchCriteriaList object
   *
   *@param  tmp  The new Id value
   *@since
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the Id attribute of the SearchCriteriaList object
   *
   *@param  tmp  The new Id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the GroupName attribute of the SearchCriteriaList object
   *
   *@param  tmp  The new GroupName value
   *@since
   */
  public void setGroupName(String tmp) {
    this.groupName = tmp;
  }


  /**
   *  Sets the contactSource attribute of the SearchCriteriaList object
   *
   *@param  tmp  The new contactSource value
   */
  public void setContactSource(int tmp) {
    this.contactSource = tmp;
  }


  /**
   *  Sets the contactSource attribute of the SearchCriteriaList object
   *
   *@param  tmp  The new contactSource value
   */
  public void setContactSource(String tmp) {
    this.contactSource = Integer.parseInt(tmp);
  }


  /**
   *  Gets the OnlyContactIds attribute of the SearchCriteriaList object
   *
   *@return    The OnlyContactIds value
   */
  public boolean getOnlyContactIds() {
    return onlyContactIds;
  }


  /**
   *  Gets the HtmlSelectIdName attribute of the SearchCriteriaList object
   *
   *@return    The HtmlSelectIdName value
   */
  public String getHtmlSelectIdName() {
    return htmlSelectIdName;
  }


  /**
   *  Gets the Modified attribute of the SearchCriteriaList object
   *
   *@return    The Modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the Errors attribute of the SearchCriteriaList object
   *
   *@return    The Errors value
   *@since
   */
  public HashMap getErrors() {
    return errors;
  }


  /**
   *  Gets the SaveCriteria attribute of the SearchCriteriaList object
   *
   *@return    The SaveCriteria value
   *@since
   */
  public String getSaveCriteria() {
    return saveCriteria;
  }


  /**
   *  Gets the ModifiedBy attribute of the SearchCriteriaList object
   *
   *@return    The ModifiedBy value
   *@since
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the EnteredBy attribute of the SearchCriteriaList object
   *
   *@return    The EnteredBy value
   *@since
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the Entered attribute of the SearchCriteriaList object
   *
   *@return    The Entered value
   *@since
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the EnteredString attribute of the SearchCriteriaList object
   *
   *@return    The EnteredString value
   *@since
   */
  public String getEnteredString() {
    try {
      return DateFormat.getDateInstance(DateFormat.SHORT).format(entered);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   *  Gets the EnteredDateTimeString attribute of the SearchCriteriaList object
   *
   *@return    The EnteredDateTimeString value
   *@since
   */
  public String getEnteredDateTimeString() {
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(entered);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   *  Gets the ModifiedString attribute of the SearchCriteriaList object
   *
   *@return    The ModifiedString value
   *@since
   */
  public String getModifiedString() {
    try {
      return DateFormat.getDateInstance(DateFormat.SHORT).format(modified);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   *  Gets the ModifiedDateTimeString attribute of the SearchCriteriaList object
   *
   *@return    The ModifiedDateTimeString value
   *@since
   */
  public String getModifiedDateTimeString() {
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(modified);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   *  Gets the Owner attribute of the SearchCriteriaList object
   *
   *@return    The Owner value
   *@since
   */
  public int getOwner() {
    return owner;
  }


  /**
   *  Gets the Id attribute of the SearchCriteriaList object
   *
   *@return    The Id value
   *@since
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the GroupName attribute of the SearchCriteriaList object
   *
   *@return    The GroupName value
   *@since
   */
  public String getGroupName() {
    return groupName;
  }


  /**
   *  Gets the contactSource attribute of the SearchCriteriaList object
   *
   *@return    The contactSource value
   */
  public int getContactSource() {
    return contactSource;
  }


  /**
   *  Builds the HtmlSelect version of the SearchCriteriaList. The elements of
   *  the resulting HtmlSelect outline the criteria that is contained within
   *  this SearchCriteriaList
   *
   *@param  selectName  Desired name of the resulting HtmlSelect
   *@return             The HtmlSelect value
   *@since
   */
  public String getHtmlSelect(String selectName) {
    HtmlSelect selectList = new HtmlSelect();
    selectList.setSelectSize(10);

    if (this.getHtmlSelectIdName() != null) {
      selectList.setIdName(this.getHtmlSelectIdName());
    }

    LinkedHashMap criteriaList = getCriteriaTextArray();
    Iterator i = criteriaList.keySet().iterator();
    while (i.hasNext()) {
      String key = (String) i.next();
      selectList.addItem(key, (String) criteriaList.get(key));
    }
    return selectList.getHtml(selectName, -1);
  }


  /**
   *  Gets the criteriaTextArray attribute of the SearchCriteriaList object
   *
   *@return    The criteriaTextArray value
   */
  public LinkedHashMap getCriteriaTextArray() {
    String fromString = "";
    LinkedHashMap criteriaList = new LinkedHashMap();
    Iterator i = this.keySet().iterator();
    while (i.hasNext()) {
      SearchCriteriaGroup thisGroup = (SearchCriteriaGroup) this.get(i.next());
      Iterator j = thisGroup.iterator();
      String valueString = "";

      while (j.hasNext()) {
        SearchCriteriaElement thisElt = (SearchCriteriaElement) (j.next());
        String keyString = thisElt.getFieldIdAsString() + "*" + thisElt.getOperatorIdAsString() + "*" + thisElt.getText();

        if (thisElt.getSourceId() > -1) {
          keyString += "*" + thisElt.getSourceId();
        }

        //String keyString = thisElt.getFieldIdAsString() + "*" + thisElt.getOperatorIdAsString() + "*" + thisElt.getText();

        switch (thisElt.getSourceId()) {
            case SearchCriteriaList.SOURCE_MY_CONTACTS:
              fromString = " [My Contacts]";
              break;
            case SearchCriteriaList.SOURCE_ALL_CONTACTS:
              fromString = " [All Contacts]";
              break;
            case SearchCriteriaList.SOURCE_ALL_ACCOUNTS:
              fromString = " [Account Contacts]";
              break;
            case SearchCriteriaList.SOURCE_EMPLOYEES:
              fromString = " [Employees]";
              break;
            default:
              break;
        }

        if (thisGroup.getGroupField().getDescription().equals("Contact Type") && thisElt.getContactTypeName() != null) {
          valueString = thisGroup.getGroupField().getDescription() + " (" + thisElt.getOperatorDisplayText() + ") " + thisElt.getContactTypeName() + fromString;
        } else if (thisGroup.getGroupField().getDescription().equals("Account Type") && thisElt.getAccountTypeName() != null) {
          valueString = thisGroup.getGroupField().getDescription() + " (" + thisElt.getOperatorDisplayText() + ") " + thisElt.getAccountTypeName() + fromString;
        } else if (thisGroup.getGroupField().getDescription().equals("Contact ID") && thisElt.getContactNameLast() != null) {
          valueString = "Contact Name (" + thisElt.getOperatorDisplayText() + ") " + Contact.getNameLastFirst(thisElt.getContactNameLast(), thisElt.getContactNameFirst());
        } else {
          valueString = thisGroup.getGroupField().getDescription() + " (" + thisElt.getOperatorDisplayText() + ") " + thisElt.getText() + fromString;
        }

        criteriaList.put(keyString, valueString);
      }
    }
    return criteriaList;
  }


  /**
   *  Populate this object by querying its record in the database
   *
   *@param  db                db connection
   *@param  id                unique id
   *@exception  SQLException  SQL exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid ID specified.");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT scl.* " +
        "FROM saved_criterialist scl " +
        "WHERE scl.id > -1 " +
        "AND scl.id = ?");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    this.buildResources(db);
  }


  /**
   *  Build all the groups, elements, etc. associated with this SCL.
   *
   *@param  db                db connection
   *@exception  SQLException  SQL Exception
   *@since
   */
  public void buildResources(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT s.*, t.description as ctype, t2.description as atype, " +
        "c.namefirst as cnamefirst, c.namelast as cnamelast " +
        "FROM saved_criteriaelement s " +
        "LEFT JOIN lookup_contact_types t ON (s.value = t.code) " +
        "LEFT JOIN lookup_account_types t2 ON (s.value = t2.code) " +
        "LEFT JOIN contact c ON (s.value = c.contact_id) " +
        "WHERE s.id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      SearchCriteriaGroup thisGroup = null;
      SearchCriteriaElement thisElement = new SearchCriteriaElement(rs);
      Integer thisKey = new Integer(thisElement.getFieldId());
      if (this.containsKey(thisKey)) {
        thisGroup = (SearchCriteriaGroup) this.get(thisKey);
      } else {
        thisGroup = new SearchCriteriaGroup();
        thisGroup.getGroupField().setId(thisElement.getFieldId());
        this.put(thisKey, thisGroup);
      }
      thisGroup.add(thisElement);
    }
    rs.close();
    pst.close();
    buildRelatedResources(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildRelatedResources(Connection db) throws SQLException {
    //The groups
    Iterator i = this.keySet().iterator();
    while (i.hasNext()) {
      SearchCriteriaGroup thisGroup = (SearchCriteriaGroup) this.get(i.next());
      thisGroup.buildFieldData(db);
      //The fields
      Iterator j = thisGroup.iterator();
      while (j.hasNext()) {
        SearchCriteriaElement thisElt = (SearchCriteriaElement) (j.next());
        thisElt.buildOperatorData(db);
      }
    }
  }


  /**
   *  Delete all of this object's associated SearchCriteriaElements from the
   *  database
   *
   *@param  db                db connection
   *@param  listId            Description of the Parameter
   *@exception  SQLException  SQL Exception
   *@since
   */
  public void clearElements(int listId, Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM saved_criteriaelement where id = ? ");
    pst.setInt(1, listId);
    pst.execute();
    pst.close();
  }


  /**
   *  Insert this SCL into the database
   *
   *@param  db                db connection
   *@return                   true if successful, false otherwise
   *@exception  SQLException  SQL Exception
   *@since
   */
  public boolean insert(Connection db) throws SQLException {
    if (!isValid()) {
      return false;
    }
    StringBuffer sql = new StringBuffer();
    try {
      db.setAutoCommit(false);
      sql.append(
          "INSERT INTO saved_criterialist ( owner, name, contact_source, ");
      if (entered != null) {
        sql.append("entered, ");
      }
      if (modified != null) {
        sql.append("modified, ");
      }
      sql.append("enteredBy, modifiedBy ) ");
      sql.append("VALUES (?, ?, ?, ");
      if (entered != null) {
        sql.append("?, ");
      }
      if (modified != null) {
        sql.append("?, ");
      }
      sql.append("?, ?) ");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, this.getOwner());
      pst.setString(++i, this.getGroupName());
      DatabaseUtils.setInt(pst, ++i, this.getContactSource());
      if (entered != null) {
        pst.setTimestamp(++i, entered);
      }
      if (modified != null) {
        pst.setTimestamp(++i, modified);
      }
      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getModifiedBy());
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "saved_criterialist_id_seq");
      insertGroups(db);
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
   *  Update this object's record in the database
   *
   *@param  db                db connection
   *@return                   int, how many records were successfully updated
   *@exception  SQLException  SQL Exception
   *@since
   */
  public int update(Connection db) throws SQLException {
    int resultCount = -1;
    if (!isValid()) {
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
   *  Update this object's record in the database
   *
   *@param  db                db connection
   *@param  override          true to update no matter what the last modified
   *      date is, false to check whether or not someone has updated this record
   *      first
   *@return                   int, how many records were successfully updated
   *@exception  SQLException  SQL Exception
   *@since
   */
  public int update(Connection db, boolean override) throws SQLException {
    int resultCount = 0;
    if (!isValid()) {
      return -1;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE saved_criterialist SET ");
    if (override == false) {
      sql.append("modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }
    sql.append("name = ?, contact_source = ?, owner = ? " +
        "WHERE id = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, this.getGroupName());
    pst.setInt(++i, contactSource);
    pst.setInt(++i, this.getOwner());
    pst.setInt(++i, id);
    resultCount = pst.executeUpdate();
    pst.close();
    deleteGroups(db);
    insertGroups(db);
    return resultCount;
  }


  /**
   *  Check to see if there have been any errors associated with this object
   *
   *@return    true if has errors, false if not
   *@since
   */
  public boolean hasErrors() {
    return (errors.size() > 0);
  }


  /**
   *  Finds out what objects depend on this SCL (if any)
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    DependencyList dependencyList = new DependencyList();
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT COUNT(*) AS group_count " +
        "FROM campaign " +
        "WHERE status_id <> " + Campaign.FINISHED + " " +
        "AND campaign_id IN (SELECT campaign_id FROM campaign_list_groups WHERE group_id = ?)");
    pst.setInt(++i, this.getId());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      int groupcount = rs.getInt("group_count");
      if (groupcount != 0) {
        Dependency thisDependency = new Dependency();
        thisDependency.setName("Campaigns");
        thisDependency.setCount(groupcount);
        thisDependency.setCanDelete(true);
        dependencyList.add(thisDependency);
      }
    }
    rs.close();
    pst.close();
    return dependencyList;
  }


  /**
   *  Deletes the SCL from the database
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean delete(Connection db) throws SQLException {
    boolean commit = true;
    if (this.getId() == -1) {
      throw new SQLException("GroupID not specified.");
    }
    Statement st = null;
    ResultSet rs = null;
    try {
      commit = db.getAutoCommit();
      //Check to see if the group is being used by any unfinished campaigns
      //If so, the group can't be deleted
      int inactiveCount = 0;
      st = db.createStatement();
      rs = st.executeQuery(
          "SELECT COUNT(*) AS group_count " +
          "FROM campaign " +
          "WHERE status_id <> " + Campaign.FINISHED + " " +
          "AND campaign_id IN (SELECT campaign_id FROM campaign_list_groups WHERE group_id = " + this.getId() + ")");
      rs.next();
      inactiveCount = rs.getInt("group_count");
      rs.close();
      if (inactiveCount > 0) {
        st.close();
        errors.put("actionError", "Group could not be deleted because " +
            inactiveCount + " " +
            (inactiveCount == 1 ? "campaign is" : "campaigns are") +
            " being built that " +
            (inactiveCount == 1 ? "uses" : "use") +
            " this group.");
        return false;
      }
      //TODO: A group's criteria should be copied when a Campaign is executed for later review
      //The group is not in use... so delete it
      //Executed campaigns will want to know the group info, so if there are
      //executed campaigns, then hide the group... otherwise delete it
      int activeCount = 0;
      rs = st.executeQuery(
          "SELECT COUNT(*) AS group_count " +
          "FROM campaign " +
          "WHERE active = " + DatabaseUtils.getTrue(db) + " " +
          "AND campaign_id IN (SELECT campaign_id FROM campaign_list_groups WHERE group_id = " + this.getId() + ")");
      rs.next();
      activeCount = rs.getInt("group_count");
      rs.close();
      if (commit) {
        db.setAutoCommit(false);
      }
      if (activeCount > 0) {
        st.executeUpdate(
            "UPDATE saved_criterialist " +
            "SET enabled = " + DatabaseUtils.getFalse(db) + " " +
            "WHERE id = " + this.getId() + " " +
            "AND enabled = " + DatabaseUtils.getTrue(db));
      } else {
        st.executeUpdate("DELETE FROM saved_criteriaelement WHERE id = " + this.getId() + " ");
        st.executeUpdate("DELETE FROM campaign_list_groups WHERE group_id = " + this.getId());
        st.executeUpdate("DELETE FROM saved_criterialist WHERE id = " + this.getId());
      }
      st.close();
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
    }
    return true;
  }


  /**
   *  Checks whether or not this list is valid. A group name is required before
   *  an SCL can be inserted into the database
   *
   *@return    The Valid value
   *@since
   */
  protected boolean isValid() {
    if (groupName == null || groupName.equals("")) {
      errors.put("groupNameError", "Required field");
    }
    if (hasErrors()) {
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Inserts the groups that make up this SearchCriteriaList
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  protected boolean insertGroups(Connection db) throws SQLException {
    Iterator i = this.keySet().iterator();
    while (i.hasNext()) {
      SearchCriteriaGroup thisGroup = (SearchCriteriaGroup) this.get(i.next());
      Iterator j = thisGroup.iterator();
      while (j.hasNext()) {
        SearchCriteriaElement thisElt = (SearchCriteriaElement) (j.next());
        thisElt.insert(this.getId(), db);
      }
    }
    return true;
  }


  /**
   *  Deletes the groups that comprise this SearchCriteriaList
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  protected boolean deleteGroups(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM saved_criteriaelement " +
        "WHERE id = ?");
    pst.setInt(1, id);
    pst.execute();
    pst.close();
    return true;
  }


  /**
   *  Populates SearchCriteriaList object based on the ResultSet
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //saved_criterialist table
    this.setId(rs.getInt("id"));
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    owner = rs.getInt("owner");
    groupName = rs.getString("name");
    contactSource = DatabaseUtils.getInt(rs, "contact_source");
  }
}

