package org.aspcfs.modules.relationships.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.HtmlSelect;

/**
 *  Contains a list of relationship types built from the database
 *
 *@author     Mathur
 *@created    August 11, 2004
 *@version    $id:exp$
 */
public class RelationshipTypeList extends ArrayList {
  private int categoryIdMapsFrom = -1;
  private String jsEvent = "";
  private int defaultKey = -1;
  private int size = 1;
  private boolean showDisabled = true;
  protected PagedListInfo pagedListInfo = null;


  /**
   *Constructor for the RelationshipTypeList object
   */
  public RelationshipTypeList() { }


  /**
   *Constructor for the RelationshipTypeList object
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public RelationshipTypeList(Connection db) throws SQLException {
    buildList(db);
  }


  /**
   *  Sets the categoryIdMapsFrom attribute of the RelationshipTypeList object
   *
   *@param  tmp  The new categoryIdMapsFrom value
   */
  public void setCategoryIdMapsFrom(int tmp) {
    this.categoryIdMapsFrom = tmp;
  }


  /**
   *  Sets the categoryIdMapsFrom attribute of the RelationshipTypeList object
   *
   *@param  tmp  The new categoryIdMapsFrom value
   */
  public void setCategoryIdMapsFrom(String tmp) {
    this.categoryIdMapsFrom = Integer.parseInt(tmp);
  }


  /**
   *  Sets the jsEvent attribute of the RelationshipTypeList object
   *
   *@param  tmp  The new jsEvent value
   */
  public void setJsEvent(String tmp) {
    this.jsEvent = tmp;
  }


  /**
   *  Sets the defaultKey attribute of the RelationshipTypeList object
   *
   *@param  tmp  The new defaultKey value
   */
  public void setDefaultKey(int tmp) {
    this.defaultKey = tmp;
  }


  /**
   *  Sets the defaultKey attribute of the RelationshipTypeList object
   *
   *@param  tmp  The new defaultKey value
   */
  public void setDefaultKey(String tmp) {
    this.defaultKey = Integer.parseInt(tmp);
  }


  /**
   *  Sets the size attribute of the RelationshipTypeList object
   *
   *@param  tmp  The new size value
   */
  public void setSize(int tmp) {
    this.size = tmp;
  }


  /**
   *  Sets the size attribute of the RelationshipTypeList object
   *
   *@param  tmp  The new size value
   */
  public void setSize(String tmp) {
    this.size = Integer.parseInt(tmp);
  }


  /**
   *  Sets the showDisabled attribute of the RelationshipTypeList object
   *
   *@param  tmp  The new showDisabled value
   */
  public void setShowDisabled(boolean tmp) {
    this.showDisabled = tmp;
  }


  /**
   *  Sets the showDisabled attribute of the RelationshipTypeList object
   *
   *@param  tmp  The new showDisabled value
   */
  public void setShowDisabled(String tmp) {
    this.showDisabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the pagedListInfo attribute of the RelationshipTypeList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Gets the pagedListInfo attribute of the RelationshipTypeList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the categoryIdMapsFrom attribute of the RelationshipTypeList object
   *
   *@return    The categoryIdMapsFrom value
   */
  public int getCategoryIdMapsFrom() {
    return categoryIdMapsFrom;
  }


  /**
   *  Gets the jsEvent attribute of the RelationshipTypeList object
   *
   *@return    The jsEvent value
   */
  public String getJsEvent() {
    return jsEvent;
  }


  /**
   *  Gets the defaultKey attribute of the RelationshipTypeList object
   *
   *@return    The defaultKey value
   */
  public int getDefaultKey() {
    return defaultKey;
  }


  /**
   *  Gets the size attribute of the RelationshipTypeList object
   *
   *@return    The size value
   */
  public int getSize() {
    return size;
  }


  /**
   *  Gets the showDisabled attribute of the RelationshipTypeList object
   *
   *@return    The showDisabled value
   */
  public boolean getShowDisabled() {
    return showDisabled;
  }


  /**
   *  Gets the htmlSelect attribute of the RelationshipTypeList object
   *
   *@param  selectName  Description of the Parameter
   *@param  defaultKey  Description of the Parameter
   *@return             The htmlSelect value
   */
  public HtmlSelect getHtmlSelect() {
    HtmlSelect relationshipTypeSelect = new HtmlSelect();
    relationshipTypeSelect.setJsEvent(jsEvent);
    relationshipTypeSelect.setSelectSize(this.getSize());

    Iterator i = this.iterator();
    while (i.hasNext()) {
      RelationshipType thisRelationshipType = (RelationshipType) i.next();
      relationshipTypeSelect.addItem(thisRelationshipType.getTypeId(), thisRelationshipType.getReciprocalName1());
      relationshipTypeSelect.addItem(thisRelationshipType.getTypeId() + "_reciprocal", thisRelationshipType.getReciprocalName2());
    }
    return relationshipTypeSelect;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();

    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM lookup_relationship_types lrt " +
        "WHERE code > -1 ");

    createFilter(db, sqlFilter);

    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
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
        pst = db.prepareStatement(sqlCount.toString() +
            sqlFilter.toString() +
            "AND lrt.reciprocal_name_1 < ? ");
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
      pagedListInfo.setDefaultSort("lrt.type_id,category_id_maps_from", null);
    } else {
      sqlOrder.append("ORDER BY lrt.type_id,lrt.category_id_maps_from ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append("lrt.* FROM lookup_relationship_types lrt " +
        "WHERE lrt.type_id > -1 ");
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
      RelationshipType thisRelationshipType = new RelationshipType(rs);
      this.add(thisRelationshipType);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db         Description of the Parameter
   *@param  sqlFilter  Description of the Parameter
   */
  protected void createFilter(Connection db, StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (categoryIdMapsFrom != -1) {
      sqlFilter.append("AND lrt.category_id_maps_from = ? ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;

    if (categoryIdMapsFrom != -1) {
      pst.setInt(++i, categoryIdMapsFrom);
    }
    return i;
  }
}

