package org.aspcfs.modules.accounts.base;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.accounts.base.*;

/**
 *  Description of the Class
 *
 *@author     Mathur
 *@created    January 13, 2003
 *@version    $Id$
 */
public class RevenueDetailTypeList extends Vector {
  private String jsEvent = "";
  private int defaultKey = -1;
  private int size = 1;
  private boolean multiple = false;


  /**
   *  Constructor for the RevenueDetailTypeList object
   */
  public RevenueDetailTypeList() { }


  /**
   *  Constructor for the RevenueDetailTypeList object
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public RevenueDetailTypeList(Connection db) throws SQLException {
    buildList(db);
  }


  /**
   *  Sets the size attribute of the RevenueDetailTypeList object
   *
   *@param  size  The new size value
   */
  public void setSize(int size) {
    this.size = size;
  }


  /**
   *  Sets the multiple attribute of the RevenueDetailTypeList object
   *
   *@param  multiple  The new multiple value
   */
  public void setMultiple(boolean multiple) {
    this.multiple = multiple;
  }


  /**
   *  Sets the jsEvent attribute of the RevenueDetailTypeList object
   *
   *@param  tmp  The new jsEvent value
   */
  public void setJsEvent(String tmp) {
    this.jsEvent = tmp;
  }


  /**
   *  Sets the defaultKey attribute of the RevenueDetailTypeList object
   *
   *@param  tmp  The new defaultKey value
   */
  public void setDefaultKey(int tmp) {
    this.defaultKey = tmp;
  }


  /**
   *  Sets the defaultKey attribute of the RevenueDetailTypeList object
   *
   *@param  tmp  The new defaultKey value
   */
  public void setDefaultKey(String tmp) {
    this.defaultKey = Integer.parseInt(tmp);
  }


  /**
   *  Gets the size attribute of the RevenueDetailTypeList object
   *
   *@return    The size value
   */
  public int getSize() {
    return size;
  }


  /**
   *  Gets the multiple attribute of the RevenueDetailTypeList object
   *
   *@return    The multiple value
   */
  public boolean getMultiple() {
    return multiple;
  }


  /**
   *  Gets the jsEvent attribute of the RevenueDetailTypeList object
   *
   *@return    The jsEvent value
   */
  public String getJsEvent() {
    return jsEvent;
  }


  /**
   *  Gets the defaultKey attribute of the RevenueDetailTypeList object
   *
   *@return    The defaultKey value
   */
  public int getDefaultKey() {
    return defaultKey;
  }


  /**
   *  Gets the htmlSelect attribute of the RevenueDetailTypeList object
   *
   *@param  selectName  Description of the Parameter
   *@return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, defaultKey);
  }


  /**
   *  Gets the htmlSelect attribute of the RevenueDetailTypeList object
   *
   *@param  selectName  Description of the Parameter
   *@param  defaultKey  Description of the Parameter
   *@return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    LookupList revenueTypeSelect = new LookupList();
    revenueTypeSelect = getLookupList(selectName, defaultKey);
    return revenueTypeSelect.getHtmlSelect(selectName, defaultKey);
  }


  /**
   *  Gets the lookupList attribute of the RevenueDetailTypeList object
   *
   *@param  selectName  Description of the Parameter
   *@param  defaultKey  Description of the Parameter
   *@return             The lookupList value
   */
  public LookupList getLookupList(String selectName, int defaultKey) {
    LookupList revenueTypeSelect = new LookupList();
    revenueTypeSelect.setJsEvent(jsEvent);
    revenueTypeSelect.setSelectSize(this.getSize());
    revenueTypeSelect.setMultiple(this.getMultiple());

    Iterator i = this.iterator();
    while (i.hasNext()) {
      RevenueDetailType thisRevenueType = (RevenueDetailType) i.next();

      if (thisRevenueType.getEnabled() == true) {
        revenueTypeSelect.appendItem(thisRevenueType.getId(), thisRevenueType.getDescription());
      } else if (thisRevenueType.getEnabled() == false && thisRevenueType.getId() == defaultKey) {
        revenueTypeSelect.appendItem(thisRevenueType.getId(), thisRevenueType.getDescription() + " (X)");
      }
    }

    return revenueTypeSelect;
  }


  /**
   *  Adds a feature to the Item attribute of the RevenueDetailTypeList object
   *
   *@param  key   The feature to be added to the Item attribute
   *@param  name  The feature to be added to the Item attribute
   */
  public void addItem(int key, String name) {
    RevenueDetailType thisRevenueType = new RevenueDetailType();
    thisRevenueType.setId(key);
    thisRevenueType.setDescription(name);
    this.add(0, thisRevenueType);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT * FROM lookup_revenuedetail_types WHERE code > -1 ");
    sql.append("ORDER BY level, description ");
    Statement st = db.createStatement();
    ResultSet rs = st.executeQuery(sql.toString());
    while (rs.next()) {
      RevenueDetailType thisRevenueType = new RevenueDetailType(rs);
      this.addElement(thisRevenueType);
    }
    rs.close();
    st.close();
  }

}

