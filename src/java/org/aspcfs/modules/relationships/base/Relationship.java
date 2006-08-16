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

import com.darkhorseventures.framework.beans.GenericBean;

import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationHistory;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.ContactHistory;
import org.aspcfs.modules.actionplans.base.ActionItemWork;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Represents a relationship
 *
 * @author     Mathur
 * @version    $id:exp$
 * @created    August 11, 2004
 */
public class Relationship extends GenericBean {
  private int id = -1;
  private int typeId = -1;
  private int objectIdMapsFrom = -1;
  private int categoryIdMapsFrom = -1;
  private int objectIdMapsTo = -1;
  private int categoryIdMapsTo = -1;
  private java.sql.Timestamp modified = null;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private int enabled = -1;
  private String reciprocalName1 = null;
  private String reciprocalName2 = null;
  private Object mappedObject = null;
  private java.sql.Timestamp trashedDate = null;


  /**
   *  Constructor for the Relationship object
   */
  public Relationship() { }


  /**
   *  Constructor for the Relationship object
   *
   * @param  db                Description of the Parameter
   * @param  thisId            Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public Relationship(Connection db, int thisId) throws SQLException {
    if (thisId == -1) {
      throw new SQLException("Relatioship Id not specified");
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT r.relationship_id, r.type_id, r.object_id_maps_from, r.category_id_maps_from, " +
        "r.object_id_maps_to, r.category_id_maps_to, r.entered, r.enteredby, " +
        "r.modified, r.modifiedby, r.trashed_date, rt.reciprocal_name_1, rt.reciprocal_name_2 " +
        "FROM relationship r " +
        "LEFT JOIN lookup_relationship_types rt ON (rt.type_id = r.type_id) " +
        "WHERE relationship_id = ? ");
    int i = 0;
    pst.setInt(++i, thisId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (thisId == -1) {
      throw new SQLException("Relationship ID not found");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public Relationship(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the Relationship object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the Relationship object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the typeId attribute of the Relationship object
   *
   * @param  tmp  The new typeId value
   */
  public void setTypeId(int tmp) {
    this.typeId = tmp;
  }


  /**
   *  Sets the type_id attribute of the Relationship object
   *
   * @param  tmp  The new type_id value
   */
  public void setTypeId(String tmp) {
    this.typeId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the categoryIdMapsFrom attribute of the Relationship object
   *
   * @param  tmp  The new categoryIdMapsFrom value
   */
  public void setCategoryIdMapsFrom(int tmp) {
    this.categoryIdMapsFrom = tmp;
  }


  /**
   *  Sets the categoryIdMapsFrom attribute of the Relationship object
   *
   * @param  tmp  The new categoryIdMapsFrom value
   */
  public void setCategoryIdMapsFrom(String tmp) {
    this.categoryIdMapsFrom = Integer.parseInt(tmp);
  }


  /**
   *  Sets the objectIdMapsTo attribute of the Relationship object
   *
   * @param  tmp  The new objectIdMapsTo value
   */
  public void setObjectIdMapsTo(int tmp) {
    this.objectIdMapsTo = tmp;
  }


  /**
   *  Sets the objectIdMapsTo attribute of the Relationship object
   *
   * @param  tmp  The new objectIdMapsTo value
   */
  public void setObjectIdMapsTo(String tmp) {
    this.objectIdMapsTo = Integer.parseInt(tmp);
  }


  /**
   *  Sets the categoryIdMapsTo attribute of the Relationship object
   *
   * @param  tmp  The new categoryIdMapsTo value
   */
  public void setCategoryIdMapsTo(int tmp) {
    this.categoryIdMapsTo = tmp;
  }


  /**
   *  Sets the categoryIdMapsTo attribute of the Relationship object
   *
   * @param  tmp  The new categoryIdMapsTo value
   */
  public void setCategoryIdMapsTo(String tmp) {
    this.categoryIdMapsTo = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the Relationship object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the Relationship object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the entered attribute of the Relationship object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the Relationship object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the Relationship object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the Relationship object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the Relationship object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the Relationship object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the Relationship object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the Relationship object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   *  Sets the mappedObject attribute of the Relationship object
   *
   * @param  tmp  The new mappedObject value
   */
  public void setMappedObject(Object tmp) {
    this.mappedObject = tmp;
  }


  /**
   *  Gets the mappedObject attribute of the Relationship object
   *
   * @return    The mappedObject value
   */
  public Object getMappedObject() {
    return mappedObject;
  }


  /**
   *  Gets the objectIdMapsFrom attribute of the Relationship object
   *
   * @return    The objectIdMapsFrom value
   */
  public int getObjectIdMapsFrom() {
    return objectIdMapsFrom;
  }


  /**
   *  Gets the categoryIdMapsFrom attribute of the Relationship object
   *
   * @return    The categoryIdMapsFrom value
   */
  public int getCategoryIdMapsFrom() {
    return categoryIdMapsFrom;
  }


  /**
   *  Gets the objectIdMapsTo attribute of the Relationship object
   *
   * @return    The objectIdMapsTo value
   */
  public int getObjectIdMapsTo() {
    return objectIdMapsTo;
  }


  /**
   *  Gets the categoryIdMapsTo attribute of the Relationship object
   *
   * @return    The categoryIdMapsTo value
   */
  public int getCategoryIdMapsTo() {
    return categoryIdMapsTo;
  }


  /**
   *  Gets the id attribute of the Relationship object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the type_id attribute of the Relationship object
   *
   * @return    The type_id value
   */
  public int getTypeId() {
    return typeId;
  }


  /**
   *  Sets the objectIdMapsFrom attribute of the Relationship object
   *
   * @param  tmp  The new objectIdMapsFrom value
   */
  public void setObjectIdMapsFrom(int tmp) {
    this.objectIdMapsFrom = tmp;
  }


  /**
   *  Sets the objectIdMapsFrom attribute of the Relationship object
   *
   * @param  tmp  The new objectIdMapsFrom value
   */
  public void setObjectIdMapsFrom(String tmp) {
    this.objectIdMapsFrom = Integer.parseInt(tmp);
  }


  /**
   *  Gets the modified attribute of the Relationship object
   *
   * @return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the entered attribute of the Relationship object
   *
   * @return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredBy attribute of the Relationship object
   *
   * @return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modifiedBy attribute of the Relationship object
   *
   * @return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the enabled attribute of the Relationship object
   *
   * @return    The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   *  Sets the reciprocalName1 attribute of the Relationship object
   *
   * @param  tmp  The new reciprocalName1 value
   */
  public void setReciprocalName1(String tmp) {
    this.reciprocalName1 = tmp;
  }


  /**
   *  Sets the reciprocalName2 attribute of the Relationship object
   *
   * @param  tmp  The new reciprocalName2 value
   */
  public void setReciprocalName2(String tmp) {
    this.reciprocalName2 = tmp;
  }


  /**
   *  Gets the reciprocalName1 attribute of the Relationship object
   *
   * @return    The reciprocalName1 value
   */
  public String getReciprocalName1() {
    return reciprocalName1;
  }


  /**
   *  Gets the reciprocalName2 attribute of the Relationship object
   *
   * @return    The reciprocalName2 value
   */
  public String getReciprocalName2() {
    return reciprocalName2;
  }


  /**
   *  Gets the trashedDate attribute of the Relationship object
   *
   * @return    The trashedDate value
   */
  public java.sql.Timestamp getTrashedDate() {
    return trashedDate;
  }


  /**
   *  Sets the trashedDate attribute of the Relationship object
   *
   * @param  tmp  The new trashedDate value
   */
  public void setTrashedDate(java.sql.Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   *  Sets the trashedDate attribute of the Relationship object
   *
   * @param  tmp  The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    try {
      db.setAutoCommit(false);
      StringBuffer sql = new StringBuffer();
      id = DatabaseUtils.getNextSeq(db, "relationship_relationship_id_seq");
      sql.append(
          "INSERT INTO relationship " +
          "(type_id, object_id_maps_from, category_id_maps_from, object_id_maps_to, category_id_maps_to, trashed_date, ");
      if (id > -1) {
        sql.append("relationship_id, ");
      }
      if (entered != null) {
        sql.append("entered, ");
      }
      if (modified != null) {
        sql.append("modified, ");
      }
      sql.append("enteredBy, modifiedBy ) ");
      sql.append("VALUES (?, ?, ?, ?, ?, ?, ");
      if (id > -1) {
        sql.append("?,");
      }
      if (entered != null) {
        sql.append("?, ");
      }
      if (modified != null) {
        sql.append("?, ");
      }
      sql.append("?, ?) ");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());

      pst.setInt(++i, this.getTypeId());
      pst.setInt(++i, this.getObjectIdMapsFrom());
      pst.setInt(++i, this.getCategoryIdMapsFrom());
      pst.setInt(++i, this.getObjectIdMapsTo());
      pst.setInt(++i, this.getCategoryIdMapsTo());
      DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
      if (id > -1) {
        pst.setInt(++i, id);
      }
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
      id = DatabaseUtils.getCurrVal(
          db, "relationship_relationship_id_seq", id);
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
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    String sql = null;
    PreparedStatement pst = null;
    int count = 0;
    if (id == -1) {
      throw new SQLException("Relationship ID not specified");
    }
    if (this.getId() == -1) {
      return -1;
    }
    try {
      db.setAutoCommit(false);
      sql = "UPDATE relationship " +
          "SET modifiedby = ?, object_id_maps_from = ?, category_id_maps_from = ?, " +
          "object_id_maps_to = ?, category_id_maps_to = ?, trashed_date = ?, " +
          "modified = CURRENT_TIMESTAMP " +
          "WHERE relationship_id = ? AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? ");
      int i = 0;
      pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getModifiedBy());
      pst.setInt(++i, this.getObjectIdMapsFrom());
      pst.setInt(++i, this.getCategoryIdMapsFrom());
      pst.setInt(++i, this.getObjectIdMapsTo());
      pst.setInt(++i, this.getCategoryIdMapsTo());
      DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
      pst.setInt(++i, id);
      if(this.getModified() != null){
        pst.setTimestamp(++i, this.getModified());
      }
      count = pst.executeUpdate();
      pst.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return count;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID was not specified");
    }

    // Delete the Account history
    ContactHistory.deleteObject(
        db, OrganizationHistory.RELATIONSHIP, this.getId());

    //Reset the step attachments, if any, with this relationship
    try {
      int linkModuleId = ActionPlan.getMapIdGivenConstantId(
            db, ActionPlan.ACTION_ITEM_WORK_RELATIONSHIP_OBJECT);
      ActionItemWork itemWork = new ActionItemWork(db, 
            linkModuleId, this.getId());
      itemWork.resetAttachment(db);
    } catch (SQLException e) {
    }
    
    
    int recordCount = 0;
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM relationship " +
        "WHERE relationship_id = ? ");
    pst.setInt(1, id);
    recordCount = pst.executeUpdate();
    pst.close();
    
    if (recordCount == 0) {
      return false;
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  direction      Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildMappedObject(Connection db, String direction) throws SQLException {
    //determine the mapping and mapped entities
    int categoryId = categoryIdMapsTo;
    if ("from".equals(direction)) {
      categoryId = categoryIdMapsFrom;
    }

    switch (categoryId) {
      case Constants.ACCOUNT_OBJECT:
        mappedObject = new Organization(
            db, "from".equals(direction) ? objectIdMapsFrom : objectIdMapsTo);
        break;
      default:
        break;
    }
  }


  /**
   *  Gets the mappedObjectLabel attribute of the Relationship object
   *
   * @return    The mappedObjectLabel value
   */
  public String getMappedObjectLabel() {
    if (mappedObject != null) {
      if (mappedObject instanceof Organization) {
        Organization thisOrg = (Organization) mappedObject;
        return thisOrg.getName();
      }
    }
    return null;
  }


  /**
   *  Description of the Method
   *
   * @param  rs             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("relationship_id");
    typeId = rs.getInt("type_id");
    objectIdMapsFrom = rs.getInt("object_id_maps_from");
    categoryIdMapsFrom = rs.getInt("category_id_maps_from");
    objectIdMapsTo = rs.getInt("object_id_maps_to");
    categoryIdMapsTo = rs.getInt("category_id_maps_to");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    trashedDate = rs.getTimestamp("trashed_date");

    //lookup
    reciprocalName1 = rs.getString("reciprocal_name_1");
    reciprocalName2 = rs.getString("reciprocal_name_2");
  }


  /*
   *  public void buildType(Connection db) throws SQLException {
   *  RelationshipTypeList typeList = new RelationshipTypeList();
   *  typeList.setTypeId(typeId);
   *  typeList.buildList(db);
   *  if (typeList.size() == 1) {
   *  type = (RelationshipType) typeList.get(0);
   *  }
   *  }
   */
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
    boolean commit = true;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      sql.append(
          "UPDATE relationship " +
          "SET trashed_date = ? , " +
          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , " +
          "modifiedby = ? " +
          "WHERE relationship_id = ? ");

      int i = 0;
      pst = db.prepareStatement(sql.toString());
      if (toTrash) {
        DatabaseUtils.setTimestamp(
            pst, ++i, new Timestamp(System.currentTimeMillis()));
      } else {
        DatabaseUtils.setTimestamp(pst, ++i, (Timestamp) null);
      }
      DatabaseUtils.setInt(pst, ++i, tmpUserId);
      pst.setInt(++i, this.getId());
      pst.executeUpdate();
      pst.close();

      //remove any links to this object from action step work
      if (this.getCategoryIdMapsFrom() == Constants.ACCOUNT_OBJECT && this.getCategoryIdMapsTo() == Constants.ACCOUNT_OBJECT) {
        i = 0;
        int relationshipLinkModuleId = ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACTION_ITEM_WORK_RELATIONSHIP_OBJECT);
        pst = db.prepareStatement(
            "UPDATE action_item_work " +
            "SET link_item_id = ? " +
            "WHERE link_module_id = ? " +
            "AND link_item_id = ? ");
        DatabaseUtils.setInt(pst, ++i, -1);
        pst.setInt(++i, relationshipLinkModuleId);
        pst.setInt(++i, this.getId());
        pst.executeUpdate();
        pst.close();
      }

      //Delete related organization history
      ContactHistory.deleteObject(
          db, OrganizationHistory.RELATIONSHIP, this.getId());

      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   *  Gets the present attribute of the Relationship object
   *
   * @param  finalSelection  Description of the Parameter
   * @param  type            Description of the Parameter
   * @return                 The present value
   */
  public boolean isPresent(ArrayList finalSelection, String type) {
    if (finalSelection != null) {
      Iterator j = finalSelection.iterator();
      while (j.hasNext()) {
        String val = (String) j.next();
        if (type.endsWith("_reciprocal")) {
          if (this.getObjectIdMapsFrom() == Integer.parseInt(val)) {
            return true;
          }
        } else {
          if (this.getObjectIdMapsTo() == Integer.parseInt(val)) {
            return true;
          }
        }
      }
    }
    return false;
  }
}

