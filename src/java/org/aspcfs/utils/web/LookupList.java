//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.webutils;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.*;
import com.darkhorseventures.cfsbase.Constants;
import com.darkhorseventures.utils.DatabaseUtils;

/**
 *  A generic class that contains a list of LookupElement objects.
 *
 *@author     mrajkowski
 *@created    September 7, 2001
 *@version    $Id$
 *@version    $Id$
 */
public class LookupList extends HtmlSelect {
  public static String uniqueField = "code";
  public String tableName = null;

  protected String jsEvent = null;
  protected int selectSize = 1;
  protected boolean multiple = false;
  protected java.sql.Timestamp lastAnchor = null;
  protected java.sql.Timestamp nextAnchor = null;
  protected int syncType = Constants.NO_SYNC;
  protected boolean showDisabledFlag = true;
  protected PagedListInfo pagedListInfo = null;

  /**
   *  Constructor for the LookupList object. Generates an empty list, which is
   *  not very useful.
   *
   *@since    1.1
   */
  public LookupList() { }

  /**
   *  Builds a list of elements based on the database connection and the table
   *  name specified for the lookup. Only retrieves "enabled" items at this
   *  time.
   *
   *@param  db                Description of Parameter
   *@param  thisTable         Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public LookupList(Connection db, String thisTable) throws SQLException {
    tableName = thisTable;
    buildList(db);
  }

  /**
   *  Constructor for the LookupList object
   *
   *@param  vals              Description of Parameter
   *@param  names             Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public LookupList(String[] vals, String[] names) throws SQLException {

    for (int i = 0; i < vals.length; i++) {
      LookupElement thisElement = new LookupElement();
      thisElement.setDescription(names[i]);

      //as long as it is not a new entry

      if (!(vals[i].startsWith("*"))) {
        thisElement.setCode(Integer.parseInt(vals[i]));
      }

      thisElement.setLevel(i);
      this.add(thisElement);
    }
  }
  
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }
  public void setPagedListInfo(PagedListInfo pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
  }

  /**
   *  Constructor for the LookupList object
   *
   *@param  db                Description of Parameter
   *@param  table             Description of Parameter
   *@param  fieldId           Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public LookupList(Connection db, String table, int fieldId) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("LookupList-> " + table + ": " + fieldId);
    }
    Statement st = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT * " +
        "FROM " + table + " " +
        "WHERE field_id = " + fieldId + " " +
        "AND CURRENT_TIMESTAMP > start_date " +
        "AND (CURRENT_TIMESTAMP < end_date OR end_date IS NULL) " +
        "ORDER BY level, description ");

    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    while (rs.next()) {
      LookupElement thisElement = new LookupElement(rs);
      this.add(thisElement);
    }
    rs.close();
    st.close();
  }

  public void setShowDisabledFlag(boolean showDisabledFlag) {
    this.showDisabledFlag = showDisabledFlag;
  }
  public boolean getShowDisabledFlag() {
    return showDisabledFlag;
  }


  /**
   *  Sets the table attribute of the LookupList object
   *
   *@param  tmp  The new table value
   */
  public void setTable(String tmp) {
    this.tableName = tmp;
  }


  /**
   *  Sets the tableName attribute of the LookupList object
   *
   *@param  tmp  The new tableName value
   */
  public void setTableName(String tmp) {
    this.tableName = tmp;
  }


  /**
   *  Sets the lastAnchor attribute of the LookupList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the lastAnchor attribute of the LookupList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the nextAnchor attribute of the LookupList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the LookupList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the syncType attribute of the LookupList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   *  Sets the syncType attribute of the LookupList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Multiple attribute of the LookupList object
   *
   *@param  multiple  The new Multiple value
   *@since
   */
  public void setMultiple(boolean multiple) {
    this.multiple = multiple;
  }


  /**
   *  Sets the JsEvent attribute of the LookupList object
   *
   *@param  tmp  The new JsEvent value
   *@since
   */
  public void setJsEvent(String tmp) {
    this.jsEvent = tmp;
  }


  /**
   *  Sets the SelectSize attribute of the LookupList object
   *
   *@param  tmp  The new SelectSize value
   *@since
   */
  public void setSelectSize(int tmp) {
    this.selectSize = tmp;
  }
  
  public String getTableName() {
    return tableName;
  }
  
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Gets the table attribute of the LookupList object
   *
   *@return    The table value
   */
  public String getTable() {
    return tableName;
  }


  /**
   *  Gets the Multiple attribute of the LookupList object
   *
   *@return    The Multiple value
   *@since
   */
  public boolean getMultiple() {
    return multiple;
  }
  
  public String getHtmlSelectDefaultNone(String selectName) {
    HtmlSelect thisSelect = new HtmlSelect();
    thisSelect.addItem(-1, "-- None --");

    Iterator i = this.iterator();
    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();
      thisSelect.addItem(
          thisElement.getCode(),
          thisElement.getDescription());
    }

    return thisSelect.getHtml(selectName);
  }

  public int getEnabledElementCount() {
    int count = 0;

    Iterator i = this.iterator();
    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();
      if (thisElement.getEnabled()) {
        count++;
      }
    }
    return count;
  }
  
  /**
   *  Gets the HtmlSelect attribute of the ContactEmailTypeList object
   *
   *@param  selectName  Description of Parameter
   *@param  defaultKey  Description of Parameter
   *@return             The HtmlSelect value
   *@since              1.1
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect thisSelect = new HtmlSelect();
    thisSelect.setSelectSize(selectSize);
    thisSelect.setMultiple(multiple);
    thisSelect.setJsEvent(jsEvent);
    

    Iterator i = this.iterator();
    boolean keyFound = false;
    int lookupDefault = defaultKey;

    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();

      if (thisElement.getEnabled() == true || !showDisabledFlag) {
        thisSelect.addItem(thisElement.getCode(), thisElement.getDescription());
        if (thisElement.getDefaultItem()) {
          lookupDefault = thisElement.getCode();
        }
      } else if (thisElement.getCode() == defaultKey) {
          thisSelect.addItem(thisElement.getCode(), thisElement.getDescription());
      }

      if (thisElement.getCode() == defaultKey) {
        keyFound = true;
      }
    }

    if (keyFound) {
      return thisSelect.getHtml(selectName, defaultKey);
    } else {
      return thisSelect.getHtml(selectName, lookupDefault);
    }
  }
  

  /**
   *  Gets the HtmlSelect attribute of the ContactEmailTypeList object
   *
   *@param  selectName    Description of Parameter
   *@param  defaultValue  Description of Parameter
   *@return               The HtmlSelect value
   *@since                1.1
   */
  public String getHtmlSelect(String selectName, String defaultValue) {
    HtmlSelect thisSelect = new HtmlSelect();
    thisSelect.setSelectSize(selectSize);
    thisSelect.setJsEvent(jsEvent);
    Iterator i = this.iterator();

    boolean keyFound = false;
    String lookupDefault = null;

    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();

      if (thisElement.getEnabled() == true) {
        thisSelect.addItem(thisElement.getCode(), thisElement.getDescription());
      } else if (defaultValue.equals(thisElement.getDescription())) {
        keyFound = true;
        thisSelect.addItem(thisElement.getCode(), thisElement.getDescription());
      }

      if (defaultValue.equals(thisElement.getDescription())) {
        keyFound = true;
      }
      if (thisElement.getDefaultItem()) {
        lookupDefault = thisElement.getDescription();
      }
    }

    return thisSelect.getHtml(selectName, defaultValue);
  }


  /**
   *  Gets the htmlSelect attribute of the LookupList object
   *
   *@param  selectName  Description of Parameter
   *@param  ms          Description of Parameter
   *@return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName, LookupList ms) {
    HtmlSelect thisSelect = new HtmlSelect();
    thisSelect.setSelectSize(selectSize);
    thisSelect.setJsEvent(jsEvent);
    thisSelect.setMultiple(multiple);
    thisSelect.setMultipleSelects(ms);
    Iterator i = this.iterator();

    boolean keyFound = false;
    String lookupDefault = null;

    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();

      if (thisElement.getEnabled() == true) {
        thisSelect.addItem(thisElement.getCode(), thisElement.getDescription());
      }
      if (thisElement.getDefaultItem()) {
        lookupDefault = thisElement.getDescription();
      }
    }

    return thisSelect.getHtml(selectName);
  }


  /**
   *  Gets the selectedKey attribute of the LookupList object
   *
   *@return    The selectedKey value
   */
  public int getSelectedKey() {
    Iterator i = this.iterator();
    LookupElement keyFound = null;
    int x = 0;
    while (i.hasNext()) {
      ++x;
      LookupElement thisElement = (LookupElement) i.next();
      if (x == 1) {
        keyFound = thisElement;
      }
      try {
        if (thisElement.getCode() == Integer.parseInt(defaultKey)) {
          return thisElement.getCode();
        }
      } catch (Exception e) {
      }
      if (thisElement.getDefaultItem()) {
        keyFound = thisElement;
      }
    }
    if (keyFound != null) {
      return keyFound.getCode();
    } else {
      return -1;
    }
  }


  /**
   *  Gets the SelectedValue attribute of the LookupList object
   *
   *@param  selectedId  Description of Parameter
   *@return             The SelectedValue value
   *@since
   */
  public String getSelectedValue(int selectedId) {
    Iterator i = this.iterator();
    LookupElement keyFound = null;
    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();
      if (thisElement.getCode() == selectedId) {
        return thisElement.getDescription();
      }
      if (thisElement.getDefaultItem()) {
        keyFound = thisElement;
      }
    }
    if (keyFound != null) {
      return keyFound.getDescription();
    } else {
      return "";
    }
  }



  /**
   *  Gets the selectedValue attribute of the LookupList object
   *
   *@param  selectedId  Description of Parameter
   *@return             The selectedValue value
   */
  public String getSelectedValue(String selectedId) {
    return getSelectedValue(Integer.parseInt(selectedId));
  }


  /**
   *  Gets the object attribute of the LookupList object
   *
   *@param  rs                Description of Parameter
   *@return                   The object value
   *@exception  SQLException  Description of Exception
   */
  public LookupElement getObject(ResultSet rs) throws SQLException {
    LookupElement thisElement = new LookupElement(rs);
    return thisElement;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void select(Connection db) throws SQLException {
    buildList(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;
    
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlSelect = new StringBuffer();
    
    sqlCount.append(
      "SELECT COUNT(*) AS recordcount " +
      "FROM " + tableName + " " +
      "WHERE code > -1 ");
      
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
      pst.close();
      rs.close();
      
      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString() +
        "AND description < ? ");
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
      pagedListInfo.setDefaultSort("description ", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY level,description ");
    }  
    
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    
    sqlSelect.append(
        "* " +
        "FROM " + tableName + " " +
        "WHERE code > -1 ");
    
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
      LookupElement thisElement = new LookupElement(rs);
      this.add(thisElement);
    }
    
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  key  Description of Parameter
   *@return      Description of the Returned Value
   */
  public boolean containsKey(int key) {
    Iterator i = this.iterator();
    boolean keyFound = false;

    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();

      if (thisElement.getEnabled() == true && thisElement.getCode() == key) {
        keyFound = true;
      }
    }

    return keyFound;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   */
  public String valuesAsString() {
    Iterator i = this.iterator();
    String result = "";
    int count = 0;

    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();
      if (count > 0) {
        result += ", " + thisElement.getDescription();
      } else {
        result += thisElement.getDescription();
      }

      count++;
    }

    return result;
  }



  /**
   *  Description of the Method
   */
  public void printVals() {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();
      System.out.println("Level: " + thisElement.getLevel() + ", Desc: " + thisElement.getDescription() + ", Code: " + thisElement.getCode());
    }
  }


  /**
   *  Adds a feature to the Item attribute of the LookupList object
   *
   *@param  tmp1  The feature to be added to the Item attribute
   *@param  tmp2  The feature to be added to the Item attribute
   *@since
   */
  public void addItem(int tmp1, String tmp2) {
    LookupElement thisElement = new LookupElement();
    thisElement.setCode(tmp1);
    thisElement.setDescription(tmp2);
    if (this.size() > 0) {
      this.add(0, thisElement);
    } else {
      this.add(thisElement);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  tmp1  Description of Parameter
   *@param  tmp2  Description of Parameter
   */
  public void appendItem(int tmp1, String tmp2) {
    LookupElement thisElement = new LookupElement();
    thisElement.setCode(tmp1);
    thisElement.setDescription(tmp2);
    if (this.size() <= 0) {
      this.add(0, thisElement);
    } else {
      this.add(this.size(), thisElement);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND entered > ? ");
      }
      sqlFilter.append("AND entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND modified > ? ");
      sqlFilter.append("AND entered < ? ");
      sqlFilter.append("AND modified < ? ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
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
    return i;
  }

}

