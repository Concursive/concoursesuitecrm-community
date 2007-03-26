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
package org.aspcfs.modules.relationships.base;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.modules.accounts.base.Organization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 *  Builds a list of relationships $
 *
 * @author     Mathur
 * @created    August 11, 2004
 * @version $Id$
 */
public class RelationshipList extends LinkedHashMap implements SyncableList{

  public final static String tableName = "relationship";
  public final static String uniqueField = "relationship_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  protected int typeId = -1;
  protected int categoryIdMapsFrom = -1;
  protected int objectIdMapsFrom = -1;
  protected int categoryIdMapsTo = -1;
  protected int objectIdMapsTo = -1;
  protected boolean buildDualMappings = false;
  protected PagedListInfo pagedListInfo = null;
  protected java.sql.Timestamp trashedDate = null;
  protected boolean includeOnlyTrashed = false;


  /**
   *  Constructor for the RelationshipList object
   */
  public RelationshipList() { }

  /**
   * Description of the Method
   *
   * @param rs
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public static Relationship getObject(ResultSet rs) throws SQLException {
    Relationship relationship = new Relationship(rs);
    return relationship;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#getTableName()
   */
  public String getTableName() {
    return tableName;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#getUniqueField()
   */
  public String getUniqueField() {
    return uniqueField;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setLastAnchor(java.sql.Timestamp)
   */
  public void setLastAnchor(Timestamp lastAnchor) {
    this.lastAnchor = lastAnchor;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setLastAnchor(java.lang.String)
   */
  public void setLastAnchor(String lastAnchor) {
    this.lastAnchor = java.sql.Timestamp.valueOf(lastAnchor);
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setNextAnchor(java.sql.Timestamp)
   */
  public void setNextAnchor(Timestamp nextAnchor) {
    this.nextAnchor = nextAnchor;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setNextAnchor(java.lang.String)
   */
  public void setNextAnchor(String nextAnchor) {
    this.nextAnchor = java.sql.Timestamp.valueOf(nextAnchor);
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setSyncType(int)
   */
  public void setSyncType(int syncType) {
    this.syncType = syncType;
  }
  
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }

  /**
   *  Sets the typeId attribute of the RelationshipList object
   *
   * @param  tmp  The new typeId value
   */
  public void setTypeId(int tmp) {
    this.typeId = tmp;
  }


  /**
   *  Sets the typeId attribute of the RelationshipList object
   *
   * @param  tmp  The new typeId value
   */
  public void setTypeId(String tmp) {
    this.typeId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the categoryIdMapsFrom attribute of the RelationshipList object
   *
   * @param  tmp  The new categoryIdMapsFrom value
   */
  public void setCategoryIdMapsFrom(int tmp) {
    this.categoryIdMapsFrom = tmp;
  }


  /**
   *  Sets the categoryIdMapsFrom attribute of the RelationshipList object
   *
   * @param  tmp  The new categoryIdMapsFrom value
   */
  public void setCategoryIdMapsFrom(String tmp) {
    this.categoryIdMapsFrom = Integer.parseInt(tmp);
  }


  /**
   *  Sets the objectIdMapsFrom attribute of the RelationshipList object
   *
   * @param  tmp  The new objectIdMapsFrom value
   */
  public void setObjectIdMapsFrom(int tmp) {
    this.objectIdMapsFrom = tmp;
  }


  /**
   *  Sets the objectIdMapsFrom attribute of the RelationshipList object
   *
   * @param  tmp  The new objectIdMapsFrom value
   */
  public void setObjectIdMapsFrom(String tmp) {
    this.objectIdMapsFrom = Integer.parseInt(tmp);
  }


  /**
   *  Sets the buildDualMappings attribute of the RelationshipList object
   *
   * @param  tmp  The new buildDualMappings value
   */
  public void setBuildDualMappings(boolean tmp) {
    this.buildDualMappings = tmp;
  }


  /**
   *  Sets the buildDualMappings attribute of the RelationshipList object
   *
   * @param  tmp  The new buildDualMappings value
   */
  public void setBuildDualMappings(String tmp) {
    this.buildDualMappings = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the pagedListInfo attribute of the RelationshipList object
   *
   * @param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Gets the typeId attribute of the RelationshipList object
   *
   * @return    The typeId value
   */
  public int getTypeId() {
    return typeId;
  }


  /**
   *  Gets the categoryIdMapsFrom attribute of the RelationshipList object
   *
   * @return    The categoryIdMapsFrom value
   */
  public int getCategoryIdMapsFrom() {
    return categoryIdMapsFrom;
  }


  /**
   *  Gets the objectIdMapsFrom attribute of the RelationshipList object
   *
   * @return    The objectIdMapsFrom value
   */
  public int getObjectIdMapsFrom() {
    return objectIdMapsFrom;
  }


  /**
   *  Gets the buildDualMappings attribute of the RelationshipList object
   *
   * @return    The buildDualMappings value
   */
  public boolean getBuildDualMappings() {
    return buildDualMappings;
  }


  /**
   *  Gets the pagedListInfo attribute of the RelationshipList object
   *
   * @return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the trashedDate attribute of the RelationshipList object
   *
   * @return    The trashedDate value
   */
  public java.sql.Timestamp getTrashedDate() {
    return trashedDate;
  }


  /**
   *  Sets the trashedDate attribute of the RelationshipList object
   *
   * @param  tmp  The new trashedDate value
   */
  public void setTrashedDate(java.sql.Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   *  Sets the trashedDate attribute of the RelationshipList object
   *
   * @param  tmp  The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the includeOnlyTrashed attribute of the RelationshipList object
   *
   * @return    The includeOnlyTrashed value
   */
  public boolean getIncludeOnlyTrashed() {
    return includeOnlyTrashed;
  }


  /**
   *  Sets the includeOnlyTrashed attribute of the RelationshipList object
   *
   * @param  tmp  The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(boolean tmp) {
    this.includeOnlyTrashed = tmp;
  }


  /**
   *  Sets the includeOnlyTrashed attribute of the RelationshipList object
   *
   * @param  tmp  The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(String tmp) {
    this.includeOnlyTrashed = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the categoryIdMapsTo attribute of the RelationshipList object
   *
   * @return    The categoryIdMapsTo value
   */
  public int getCategoryIdMapsTo() {
    return categoryIdMapsTo;
  }


  /**
   *  Sets the categoryIdMapsTo attribute of the RelationshipList object
   *
   * @param  tmp  The new categoryIdMapsTo value
   */
  public void setCategoryIdMapsTo(int tmp) {
    this.categoryIdMapsTo = tmp;
  }


  /**
   *  Sets the categoryIdMapsTo attribute of the RelationshipList object
   *
   * @param  tmp  The new categoryIdMapsTo value
   */
  public void setCategoryIdMapsTo(String tmp) {
    this.categoryIdMapsTo = Integer.parseInt(tmp);
  }


  /**
   *  Gets the objectIdMapsTo attribute of the RelationshipList object
   *
   * @return    The objectIdMapsTo value
   */
  public int getObjectIdMapsTo() {
    return objectIdMapsTo;
  }


  /**
   *  Sets the objectIdMapsTo attribute of the RelationshipList object
   *
   * @param  tmp  The new objectIdMapsTo value
   */
  public void setObjectIdMapsTo(int tmp) {
    this.objectIdMapsTo = tmp;
  }


  /**
   *  Sets the objectIdMapsTo attribute of the RelationshipList object
   *
   * @param  tmp  The new objectIdMapsTo value
   */
  public void setObjectIdMapsTo(String tmp) {
    this.objectIdMapsTo = Integer.parseInt(tmp);
  }

  /**
   * Description of the Method
   *
   * @param db
   * @param pst
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
    return queryList(db, pst, "", "");
  }
  
  /**
   * Description of the Method
   *
   * @param db
   * @param pst
   * @param sqlFilter
   * @param sqlOrder
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public ResultSet queryList(Connection db, PreparedStatement pst, String sqlFilter, String sqlOrder) throws SQLException {
    StringBuffer sqlSelect = new StringBuffer();

    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "r.relationship_id, r.type_id, r.object_id_maps_from, " +
        "r.category_id_maps_from, r.object_id_maps_to, r.category_id_maps_to, " +
        "r.entered, r.enteredby, r.modified, r.modifiedby, r.trashed_date, " +
        "rt.reciprocal_name_1, rt.reciprocal_name_2 " +
        "FROM " + tableName + " r " +
        "LEFT JOIN lookup_relationship_types rt ON (rt.type_id = r.type_id) " +
        "WHERE relationship_id > -1 ");
    if(sqlFilter == null || sqlFilter.length() == 0){
      StringBuffer buff = new StringBuffer();
      createFilter(buff);
      sqlFilter = buff.toString();
    }
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter + sqlOrder);
    prepareFilter(pst);

    return DatabaseUtils.executeQuery(db, pst, pagedListInfo);
  }

  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    //Build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM relationship r " +
        "LEFT JOIN lookup_relationship_types rt ON (rt.type_id = r.type_id) " +
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
        pst = db.prepareStatement(
            sqlCount.toString() +
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
    rs = queryList(db, pst, sqlFilter.toString(), sqlOrder.toString());
    while (rs.next()) {
      Relationship thisRelationship = new Relationship(rs);
      this.add(thisRelationship);
    }
    rs.close();
    if (pst!= null) {
      pst.close();
    }

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
   * @param  sqlFilter  Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (typeId != -1) {
      sqlFilter.append(" AND r.type_id = ? ");
    }

    if (buildDualMappings) {
      sqlFilter.append(
          "AND ((r.category_id_maps_from = ? AND r.object_id_maps_from = ?) OR " +
          "(r.category_id_maps_to = ? AND r.object_id_maps_to = ?)) ");
    } else {
      if (categoryIdMapsFrom != -1) {
        sqlFilter.append("AND r.category_id_maps_from = ? ");
      }
      if (objectIdMapsFrom != -1) {
        sqlFilter.append("AND r.object_id_maps_from = ? ");
      }
      if (categoryIdMapsTo != -1) {
        sqlFilter.append("AND r.category_id_maps_to = ? ");
      }
      if (objectIdMapsTo != -1) {
        sqlFilter.append("AND r.object_id_maps_to = ? ");
      }
    }
    if (includeOnlyTrashed) {
      sqlFilter.append("AND r.trashed_date IS NOT NULL ");
    } else if (trashedDate != null) {
      sqlFilter.append("AND r.trashed_date = ? ");
    } else {
      sqlFilter.append("AND r.trashed_date IS NULL ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND r.entered > ? ");
      }
      sqlFilter.append("AND r.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND r.modified > ? ");
      sqlFilter.append("AND r.entered < ? ");
      sqlFilter.append("AND r.modified < ? ");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  pst            Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
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
    } else {
      if (categoryIdMapsFrom != -1) {
        pst.setInt(++i, categoryIdMapsFrom);
      }
      if (objectIdMapsFrom != -1) {
        pst.setInt(++i, objectIdMapsFrom);
      }
      if (categoryIdMapsTo != -1) {
        pst.setInt(++i, categoryIdMapsTo);
      }
      if (objectIdMapsTo != -1) {
        pst.setInt(++i, objectIdMapsTo);
      }
    }
    if (includeOnlyTrashed) {
      // do nothing
    } else if (trashedDate != null) {
      pst.setTimestamp(++i, trashedDate);
    } else {
      // do nothing
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

    return i;
  }


  /**
   *  Description of the Method
   *
   * @param  r  Description of the Parameter
   */
  public void add(Relationship r) {
    ArrayList tmpList = null;
    //make sure the type has an entry
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
   * @author     Mathur
   * @created    August 13, 2004
   * @version $Id$
   */
  public class relationshipComparator implements Comparator {
    /**
     *  Description of the Method
     *
     * @param  left   Description of the Parameter
     * @param  right  Description of the Parameter
     * @return        Description of the Return Value
     */
    public int compare(Object left, Object right) {
      String a = new String(((Relationship) left).getMappedObjectLabel());
      String b = new String(((Relationship) right).getMappedObjectLabel());

      int compareResult = b.compareTo(a);
      return (compareResult);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    boolean result = false;
    int size = this.size();
    Iterator iterator = this.keySet().iterator();
    while (iterator.hasNext()) {
      String relType = (String) iterator.next();
      ArrayList tmpList = (ArrayList) this.get(relType);
      Iterator j = tmpList.iterator();
      while (j.hasNext()) {
        Relationship relationship = (Relationship) j.next();
        result = relationship.delete(db);
        j.remove();
      }
      iterator.remove();
      result = true;
    }
    return result;
  }


  /**
   *  Gets the numberOfRelationships attribute of the RelationshipList object
   *
   * @return    The numberOfRelationships value
   */
  public int getNumberOfRelationships() {
    int relationshipCounter = 0;
    Iterator iterator = (Iterator) this.keySet().iterator();
    while (iterator.hasNext()) {
      ArrayList tempList = (ArrayList) this.get((String) iterator.next());
      Iterator tempIter = (Iterator) tempList.iterator();
      while (tempIter.hasNext()) {
        Relationship rel = (Relationship) tempIter.next();
        ++relationshipCounter;
      }
    }
    return relationshipCounter;
  }


  /**
   *  Description of the Method
   *
   * @param  db              Description of the Parameter
   * @param  id              Description of the Parameter
   * @param  categoryTypeId  Description of the Parameter
   * @param  category        Description of the Parameter
   * @return                 Description of the Return Value
   */
  public int checkDuplicateRelationship(Connection db, int id, int categoryTypeId, int category) {
    int result = 0;
    Iterator iterator = (Iterator) this.keySet().iterator();
    while (iterator.hasNext()) {
      ArrayList tempList = (ArrayList) this.get((String) iterator.next());
      Iterator tempIter = (Iterator) tempList.iterator();
      while (tempIter.hasNext()) {
        Relationship rel = (Relationship) tempIter.next();
        if (rel.getObjectIdMapsTo() == id && rel.getTypeId() == categoryTypeId && rel.getCategoryIdMapsTo() == category) {
          ++result;
        }
      }
    }
    return result;
  }

  /**
   *  Description of the Method
   *
   * @param  db              Description of the Parameter
   * @param  id              Description of the Parameter
   * @param  categoryTypeId  Description of the Parameter
   * @param  category        Description of the Parameter
   * @return                 Description of the Return Value
   */
  public Relationship getDuplicateRelation(Connection db, int id, int categoryTypeId, int category) throws SQLException {
    Iterator iterator = (Iterator) this.keySet().iterator();
    while (iterator.hasNext()) {
    ArrayList tempList = (ArrayList) this.get((String) iterator.next());
      Iterator tempIter = (Iterator) tempList.iterator();
      while (tempIter.hasNext()) {
        Relationship rel = (Relationship) tempIter.next();
        if (rel.getObjectIdMapsTo() == id && rel.getTypeId() == categoryTypeId && rel.getCategoryIdMapsTo() == category) {
          return rel;
        }
      }
    }
    return null;
  }
  
  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  toTrash           Description of the Parameter
   * @param  tmpUserId         Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean updateStatus(Connection db, boolean toTrash, int tmpUserId) throws SQLException {
    Iterator iterator = (Iterator) this.keySet().iterator();
    while (iterator.hasNext()) {
      ArrayList tempList = (ArrayList) this.get((String) iterator.next());
      Iterator tempIter = (Iterator) tempList.iterator();
      while (tempIter.hasNext()) {
        Relationship rel = (Relationship) tempIter.next();
        rel.updateStatus(db, toTrash, tmpUserId);
      }
    }
    return true;
  }


  /**
   *  Gets the displayHtml attribute of the RelationshipList object
   *
   * @return    The displayHtml value
   */
  public String getDisplayHtml() {
    StringBuffer sb = new StringBuffer();
    Iterator j = (Iterator) this.keySet().iterator();
    while (j.hasNext()) {
      ArrayList tempList = (ArrayList) this.get((String) j.next());
      Iterator tempIter = (Iterator) tempList.iterator();
      while (tempIter.hasNext()) {
        Relationship rel = (Relationship) tempIter.next();
        String label = rel.getMappedObjectLabel();
        if (tempIter.hasNext()) {
          sb.append((label != null ? label : "") + "\r\n");
        } else {
          sb.append((label != null ? label : ""));
        }
      }
    }
    return sb.toString().trim();
  }


  /**
   *  Gets the mappedObjectIds attribute of the RelationshipList object
   *
   * @param  list  Description of the Parameter
   * @param  type  Description of the Parameter
   */
  public void getMappedObjectIds(ArrayList list, String type) {
    if (list == null) {
      list = new ArrayList();
    }
    Iterator j = (Iterator) this.keySet().iterator();
    while (j.hasNext()) {
      ArrayList tempList = (ArrayList) this.get((String) j.next());
      Iterator tempIter = (Iterator) tempList.iterator();
      while (tempIter.hasNext()) {
        Relationship rel = (Relationship) tempIter.next();
        if (type.endsWith("_reciprocal")) {
          list.add(String.valueOf(rel.getObjectIdMapsFrom()));
        } else {
          list.add(String.valueOf(rel.getObjectIdMapsTo()));
        }
      }
    }
  }


  /**
   *  Removes account relationships if the account does not have any one of the types  
   *  specified in accountTypes
   *
   * @param  accountTypes  Description of the Parameter
   */
  public void filterAccounts(ArrayList accountTypes) {
    if (this.getCategoryIdMapsFrom() == Constants.ACCOUNT_OBJECT) {
      Iterator rt = this.keySet().iterator();
      while (rt.hasNext()) {
        String relType = (String) rt.next();
        ArrayList tmpList = (ArrayList) this.get(relType);
        Iterator j = tmpList.iterator();
        while (j.hasNext()) {
          Relationship thisRelationship = (Relationship) j.next();
          Organization thisAccount = (Organization) thisRelationship.getMappedObject();
          boolean hasType = false;
          Iterator types = accountTypes.iterator();
          while (types.hasNext()) {
            String type = (String) types.next();  
            hasType = thisAccount.hasType(Integer.parseInt(type));
            if (hasType) {
              break;	
            }
          }
          if (!hasType) {
            j.remove();
          }
        }
      }
    }
  }
}

