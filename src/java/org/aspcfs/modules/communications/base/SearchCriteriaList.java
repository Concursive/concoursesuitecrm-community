//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.*;
import java.sql.*;
import com.darkhorseventures.webutils.HtmlSelect;
import java.text.*;

/**
 *  Contains a group of criteria groups, that each contain criteria elements. A
 *  group is a unique field that can have multiple criteria.
 *
 *@author     Wesley_S_Gillette
 *@created    November 9, 2001
 *@version    $Id: SearchCriteriaList.java,v 1.4 2001/12/11 22:21:17 mrajkowski
 *      Exp $
 */
public class SearchCriteriaList extends Hashtable {

  private int id = -1;
  private String groupName = "";
  private java.sql.Timestamp modified = null;
  private java.sql.Timestamp entered = null;
  private int modifiedBy = -1;
  private int enteredBy = -1;
  private int owner = -1;
  private String ownerIdRange = null;
  private String saveCriteria = "";
  
  protected Hashtable errors = new Hashtable();


  /**
   *  Constructor for the SearchCriteriaList object
   *
   *@since    1.1
   */
  public SearchCriteriaList() { }


  /**
   *  Constructor for the SearchCriteriaList object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public SearchCriteriaList(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the SearchCriteriaList object
   *
   *@param  db                Description of Parameter
   *@param  id                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public SearchCriteriaList(Connection db, String id) throws SQLException {
    Statement st = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();

    //The list details
    sql.append(
        "SELECT scl.* " +
        "FROM saved_criterialist scl " +
        "WHERE scl.id > -1 ");
    if (id != null && !id.equals("")) {
      sql.append("AND scl.id = " + id + " ");
    } else {
      throw new SQLException("ID not specified.");
    }
    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      st.close();
      throw new SQLException("Saved Criteria record not found.");
    }
    rs.close();
    st.close();
    
    this.buildResources(db);
  }
    
  public void buildResources(Connection db) throws SQLException {
    Statement st = null;
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();

    //The elements
    sql.append(
        "SELECT s.* " +
        "FROM saved_criteriaelement s " +
        "WHERE s.id = " + id + " ");
    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    while (rs.next()) {
      SearchCriteriaGroup thisGroup = null;
      SearchCriteriaElement thisElement = new SearchCriteriaElement(rs);
      Integer thisKey = new Integer(thisElement.getFieldId());
      if (this.containsKey(thisKey)) {
        thisGroup = (SearchCriteriaGroup)this.get(thisKey);
      } else {
        thisGroup = new SearchCriteriaGroup();
        thisGroup.getGroupField().setId(thisElement.getFieldId());
        this.put(thisKey, thisGroup);
        System.out.println("SearchCriteriaList-> Added Group: " + thisElement.getFieldId());
      }
      thisGroup.addElement(thisElement);
      System.out.println("SearchCriteriaList-> Added Element: " + thisElement.getText());
    }
    rs.close();
    st.close();
    
    //The groups
    Iterator i = this.keySet().iterator();
    while (i.hasNext()) {
      SearchCriteriaGroup thisGroup = (SearchCriteriaGroup)this.get(i.next());
      thisGroup.buildFieldData(db);
      System.out.println("SearchCriteriaList-> Built Group: " + thisGroup.getGroupField().getFieldName());

      Iterator j = thisGroup.iterator();
      while (j.hasNext()) {
        SearchCriteriaElement thisElt = (SearchCriteriaElement)(j.next());
        thisElt.buildOperatorData(db);
      }
    }
  }


  /**
   *  Constructor for the SearchCriteriaList object, takes in a string of values
   *  delimited by a "^", each element is delimited by a "|", and stores the
   *  constructed group.
   *
   *@param  searchCriteriaText  Description of Parameter
   *@since                      1.1
   */
  public SearchCriteriaList(String searchCriteriaText) {
    SearchCriteriaGroup thisGroup = null;
    StringTokenizer st = new StringTokenizer(searchCriteriaText, "^");
    while (st.hasMoreTokens()) {
      String tmpCriteria = (String)st.nextToken();
      SearchCriteriaElement thisElement = new SearchCriteriaElement(tmpCriteria);
      Integer thisKey = new Integer(thisElement.getFieldId());
      if (this.containsKey(thisKey)) {
        thisGroup = (SearchCriteriaGroup)this.get(thisKey);
      } else {
        thisGroup = new SearchCriteriaGroup();
        thisGroup.getGroupField().setId(thisElement.getFieldId());
        this.put(thisKey, thisGroup);
      }
      thisGroup.addElement(thisElement);
    }
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
   *  Sets the Modified attribute of the SearchCriteriaList object
   *
   *@param  tmp  The new Modified value
   *@since
   */
  public void setModified(String tmp) {
    java.util.Date tmpDate = new java.util.Date();
    modified = new java.sql.Timestamp(tmpDate.getTime());
    modified = modified.valueOf(tmp);
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
  
  public void setOwnerIdRange(String tmp) { this.ownerIdRange = tmp; }

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
   *  Sets the GroupName attribute of the SearchCriteriaList object
   *
   *@param  tmp  The new GroupName value
   *@since
   */
  public void setGroupName(String tmp) {
    this.groupName = tmp;
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
   *  Gets the Modified attribute of the SearchCriteriaList object
   *
   *@return    The Modified value
   *@since
   */
  public String getModified() {
    return modified.toString();
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
   *  Description of the Method
   *
   *@param  listid            Description of Parameter
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public void clearElements(int listid, Connection db) throws SQLException {
    StringBuffer sqlDelete = new StringBuffer();

    try {
      db.setAutoCommit(false);

      sqlDelete.append(
          "DELETE FROM saved_criteriaelement where id = ? ");

      int i = 0;
      PreparedStatement pstDel = db.prepareStatement(sqlDelete.toString());
      pstDel.setInt(++i, listid);

      pstDel.execute();
      pstDel.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
  }


  public String getHtmlSelect(String selectName) {
    HtmlSelect selectList = new HtmlSelect();
    selectList.setSelectSize(10);

    Iterator i = this.keySet().iterator();
    while (i.hasNext()) {
      SearchCriteriaGroup thisGroup = (SearchCriteriaGroup)this.get(i.next());
      Iterator j = thisGroup.iterator();
      while (j.hasNext()) {
        SearchCriteriaElement thisElt = (SearchCriteriaElement)(j.next());
        String keyString = thisElt.getFieldIdAsString() + "*" + thisElt.getOperatorIdAsString() + "*" + thisElt.getText();
        String valueString = thisGroup.getGroupField().getDescription() + " (" + thisElt.getOperatorDisplayText() + ") " + thisElt.getText();
        selectList.addItem(keyString, valueString);
      }
    }
    return selectList.getHtml(selectName, -1);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public void insert(Connection db) throws SQLException {

    if (groupName.equals("")) {
      throw new SQLException("Contact Group must have a name.");
    }

    StringBuffer sql = new StringBuffer();
    try {
      db.setAutoCommit(false);
      sql.append(
          "INSERT INTO saved_criterialist ( owner, name, enteredby, modifiedby ) " +
          "VALUES ( ?, ?, ?, ? ) ");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());

      pst.setInt(++i, this.getOwner());
      pst.setString(++i, this.getGroupName());
      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getModifiedBy());
      pst.execute();
      pst.close();

      Statement st = db.createStatement();
      ResultSet rs = st.executeQuery("select currval('saved_criterialist_id_seq')");
      if (rs.next()) {
        this.setId(rs.getInt(1));
      }
      rs.close();
      st.close();
      
      insertGroups(db);

      db.commit();
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
  }
  
  protected boolean insertGroups(Connection db) throws SQLException {
    Iterator i = this.keySet().iterator();
    while (i.hasNext()) {
      SearchCriteriaGroup thisGroup = (SearchCriteriaGroup)this.get(i.next());
      Iterator j = thisGroup.iterator();
      while (j.hasNext()) {
        SearchCriteriaElement thisElt = (SearchCriteriaElement)(j.next());
        thisElt.insert(this.getId(), db);
      }
    }
    return true;
  }
  
  protected boolean deleteGroups(Connection db) throws SQLException {
    Statement st = db.createStatement();
    st.executeUpdate(
      "DELETE FROM saved_criteriaelement " +
      "WHERE id = " + id);
    st.close();
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
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
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    }

    db.setAutoCommit(true);
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  override          Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
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
        "UPDATE saved_criterialist " +
        "SET modified = CURRENT_TIMESTAMP, name = ?, owner = ? " +
        "WHERE id = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, this.getGroupName());
    pst.setInt(++i, this.getOwner());
    pst.setInt(++i, id);

    resultCount = pst.executeUpdate();
    pst.close();
    
    deleteGroups(db);
    insertGroups(db);
    
    return resultCount;
  }
  
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
  
  public boolean hasErrors() {
    return (errors.size() > 0);
  }



  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("The Group could not be found.");
    }

    Statement st = db.createStatement();
    try {
      db.setAutoCommit(false);
      st.executeUpdate("DELETE FROM saved_criteriaelement WHERE id = " + this.getId() + " ");
      st.executeUpdate("DELETE FROM saved_criterialist WHERE id = " + this.getId());
      db.commit();
    } catch (SQLException e) {
      db.rollback();
    } finally {
      db.setAutoCommit(true);
      st.close();
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    this.setId(rs.getInt("id"));
    groupName = rs.getString("name");
    owner = rs.getInt("owner");

    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");

    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
  }

}

