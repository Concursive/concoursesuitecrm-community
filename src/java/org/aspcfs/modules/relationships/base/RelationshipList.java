//Copyright 2004 Dark Horse Ventures

package org.aspcfs.modules.relationships.base;

import java.sql.*;
import java.text.*;
import java.util.*;
import java.text.DateFormat;
import javax.servlet.http.HttpServletRequest;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.modules.base.Constants;

/**
 *  Builds a list of relationships
 *
 *      $
 *
 *@author     Mathur
 *@created    August 11, 2004
 *@version    $id:exp$
 */
public class RelationshipList extends LinkedHashMap {
  protected int typeId = -1;
  protected int categoryIdMapsFrom = -1;
  protected int objectIdMapsFrom = -1;
  protected boolean buildDualMappings = false;
  protected PagedListInfo pagedListInfo = null;



  /**
   *  Constructor for the RelationshipList object
   */
  public RelationshipList() { }


  /**
   *  Sets the typeId attribute of the RelationshipList object
   *
   *@param  tmp  The new typeId value
   */
  public void setTypeId(int tmp) {
    this.typeId = tmp;
  }


  /**
   *  Sets the typeId attribute of the RelationshipList object
   *
   *@param  tmp  The new typeId value
   */
  public void setTypeId(String tmp) {
    this.typeId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the categoryIdMapsFrom attribute of the RelationshipList object
   *
   *@param  tmp  The new categoryIdMapsFrom value
   */
  public void setCategoryIdMapsFrom(int tmp) {
    this.categoryIdMapsFrom = tmp;
  }


  /**
   *  Sets the categoryIdMapsFrom attribute of the RelationshipList object
   *
   *@param  tmp  The new categoryIdMapsFrom value
   */
  public void setCategoryIdMapsFrom(String tmp) {
    this.categoryIdMapsFrom = Integer.parseInt(tmp);
  }


  /**
   *  Sets the objectIdMapsFrom attribute of the RelationshipList object
   *
   *@param  tmp  The new objectIdMapsFrom value
   */
  public void setObjectIdMapsFrom(int tmp) {
    this.objectIdMapsFrom = tmp;
  }


  /**
   *  Sets the objectIdMapsFrom attribute of the RelationshipList object
   *
   *@param  tmp  The new objectIdMapsFrom value
   */
  public void setObjectIdMapsFrom(String tmp) {
    this.objectIdMapsFrom = Integer.parseInt(tmp);
  }


  /**
   *  Sets the buildDualMappings attribute of the RelationshipList object
   *
   *@param  tmp  The new buildDualMappings value
   */
  public void setBuildDualMappings(boolean tmp) {
    this.buildDualMappings = tmp;
  }


  /**
   *  Sets the buildDualMappings attribute of the RelationshipList object
   *
   *@param  tmp  The new buildDualMappings value
   */
  public void setBuildDualMappings(String tmp) {
    this.buildDualMappings = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the pagedListInfo attribute of the RelationshipList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Gets the typeId attribute of the RelationshipList object
   *
   *@return    The typeId value
   */
  public int getTypeId() {
    return typeId;
  }


  /**
   *  Gets the categoryIdMapsFrom attribute of the RelationshipList object
   *
   *@return    The categoryIdMapsFrom value
   */
  public int getCategoryIdMapsFrom() {
    return categoryIdMapsFrom;
  }


  /**
   *  Gets the objectIdMapsFrom attribute of the RelationshipList object
   *
   *@return    The objectIdMapsFrom value
   */
  public int getObjectIdMapsFrom() {
    return objectIdMapsFrom;
  }


  /**
   *  Gets the buildDualMappings attribute of the RelationshipList object
   *
   *@return    The buildDualMappings value
   */
  public boolean getBuildDualMappings() {
    return buildDualMappings;
  }


  /**
   *  Gets the pagedListInfo attribute of the RelationshipList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
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
    //Build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM relationship t " +
        "WHERE r.relationship_id > -1 ");
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
      rs.close();
      pst.close();
      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString() +
            sqlFilter.toString() +
            "AND rt.reciprocal_name_1 > ? ");
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
      pagedListInfo.setDefaultSort("r.type_id", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY r.type_id ");
    }
    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "r.relationship_id, r.type_id, r.object_id_maps_from, " +
        "r.category_id_maps_from, r.object_id_maps_to, r.category_id_maps_to, " + "r.entered, r.enteredby, r.modified, r.modifiedby, " + "rt.reciprocal_name_1, rt.reciprocal_name_2 " +
        "FROM relationship r " +
        "LEFT JOIN lookup_relationship_types rt ON (rt.type_id = r.type_id) " +
        "WHERE relationship_id > -1 ");
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
      Relationship thisRelationship = new Relationship(rs);
      this.add(thisRelationship);
    }
    rs.close();
    pst.close();

    //build mapped objects
    Iterator rt = this.keySet().iterator();
    while (rt.hasNext()) {
      String relType = (String) rt.next();
      ArrayList tmpList = (ArrayList) this.get(relType);
      Iterator j = tmpList.iterator();
      while (j.hasNext()) {
        Relationship thisRelationship = (Relationship) j.next();
        if (thisRelationship.getObjectIdMapsFrom() == this.objectIdMapsFrom) {
          thisRelationship.buildMappedObject(db, "to");
        } else {
          thisRelationship.buildMappedObject(db, "from");
        }
      }
    }

    //sort the list (NOTE: The mapped objects have to be built before sorting)
    Iterator i = this.keySet().iterator();
    while (i.hasNext()) {
      String relType = (String) i.next();
      ArrayList tmpList = (ArrayList) this.get(relType);
      Comparator comparator = new relationshipComparator();
      java.util.Collections.sort(tmpList, comparator);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (buildDualMappings) {
      sqlFilter.append("AND ((r.category_id_maps_from = ? AND r.object_id_maps_from = ?) OR (r.category_id_maps_to = ? AND r.object_id_maps_to = ?)) ");
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

    if (typeId != -1) {
      pst.setInt(++i, typeId);
    }

    if (buildDualMappings) {
      pst.setInt(++i, categoryIdMapsFrom);
      pst.setInt(++i, objectIdMapsFrom);
      pst.setInt(++i, categoryIdMapsFrom);
      pst.setInt(++i, objectIdMapsFrom);
    }
    return i;
  }


  /**
   *  Description of the Method
   *
   *@param  r  Description of the Parameter
   */
  public void add(Relationship r) {
    ArrayList tmpList = null;
    //make sure the type has a entry
    if (r.getObjectIdMapsFrom() == this.objectIdMapsFrom) {
      if (this.containsKey(r.getReciprocalName1())) {
        tmpList = (ArrayList) this.get(r.getReciprocalName1());
      } else {
        tmpList = new ArrayList();
      }
      tmpList.add(r);
      this.put(r.getReciprocalName1(), tmpList);
    } else {
      if (this.containsKey(r.getReciprocalName2())) {
        tmpList = (ArrayList) this.get(r.getReciprocalName2());
      } else {
        tmpList = new ArrayList();
      }
      tmpList.add(r);
      this.put(r.getReciprocalName2(), tmpList);
    }
  }


  /**
   *  Description of the Class
   *
   *@author     Mathur
   *@created    August 13, 2004
   *@version    $id:exp$
   */
  public class relationshipComparator implements Comparator {
    /**
     *  Description of the Method
     *
     *@param  left   Description of the Parameter
     *@param  right  Description of the Parameter
     *@return        Description of the Return Value
     */
    public int compare(Object left, Object right) {
      String a = new String(((Relationship) left).getMappedObjectLabel());
      String b = new String(((Relationship) right).getMappedObjectLabel());

      int compareResult = b.compareTo(a);
      return (compareResult);
    }
  }
}

