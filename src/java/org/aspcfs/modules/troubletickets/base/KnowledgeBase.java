/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.troubletickets.base;

import com.darkhorseventures.framework.beans.GenericBean;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    October 6, 2005
 * @version    $Id$
 */
public class KnowledgeBase extends GenericBean {

  private int id = -1;
  protected int categoryId = -1;
  protected String title = null;
  protected String description = null;
  protected int itemId = -1;
  protected java.sql.Timestamp entered = null;
  protected int enteredBy = -1;
  protected java.sql.Timestamp modified = null;
  protected int modifiedBy = -1;
  //related records
  FileItem item = null;
  boolean buildResources = false;


  /**
   *  Constructor for the KnowledgeBase object
   */
  public KnowledgeBase() { }


  /**
   *  Constructor for the KnowledgeBase object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public KnowledgeBase(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the KnowledgeBase object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public KnowledgeBase(Connection db, int id) throws SQLException {
    if (id <= -1) {
      throw new SQLException("Knowledge Base ID not specified");
    }
    queryRecord(db, id);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id <= -1) {
      throw new SQLException("Defect ID not specified");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT kb.* " +
        "FROM knowledge_base kb " +
        "LEFT JOIN ticket_category tc ON (kb.category_id = tc.id) " +
        "WHERE kb.kb_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
    if (buildResources) {
      buildResources(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildResources(Connection db) throws SQLException {
    if (this.getItemId() != -1) {
      item = new FileItem(db, itemId, this.getId(), Constants.DOCUMENTS_KNOWLEDGEBASE);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //ticket table
    this.setId(rs.getInt("kb_id"));
    categoryId = DatabaseUtils.getInt(rs, "category_id");
    title = rs.getString("title");
    description = rs.getString("description");
    itemId = DatabaseUtils.getInt(rs, "item_id");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
  }


  /**
   *  Description of the Method
   */
  public void printKb() {
    //ticket table
    System.out.println("KnowledgeBase::printDefect id is " + this.getId());
    System.out.println("KnowledgeBase::printDefect categoryId is " + categoryId);
    System.out.println("KnowledgeBase::printDefect title is " + this.getTitle());
    System.out.println("KnowledgeBase::printDefect description is " + this.getDescription());
    System.out.println("KnowledgeBase::printDefect itemId is " + itemId);
    System.out.println("KnowledgeBase::printDefect entered is " + (entered != null ? entered.toString() : "null"));
    System.out.println("KnowledgeBase::printDefect enteredBy is " + enteredBy);
    System.out.println("KnowledgeBase::printDefect modified is " + (modified != null ? modified.toString() : "null"));
    System.out.println("KnowledgeBase::printDefect modifiedBy is " + modifiedBy);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      id = DatabaseUtils.getNextSeq(db, "knowledge_base_kb_id_seq");
      sql.append(
          "INSERT INTO knowledge_base (" + (id > -1 ? "kb_id," : "") + " category_id, title, description, ");
      if (itemId != -1) {
        sql.append("item_id, ");
      }
      sql.append("entered, ");
      sql.append("enteredby, ");
      sql.append("modified, ");
      sql.append("modifiedby )");
      sql.append("VALUES (" + (id > -1 ? "?," : "") + " ?, ?, ?, ");
      if (itemId != -1) {
        sql.append("?, ");
      }
      if (entered != null) {
        sql.append("?, ");
      } else {
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      sql.append("?, ");
      if (modified != null) {
        sql.append("?, ");
      } else {
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      sql.append("? )");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      if (id > -1) {
        pst.setInt(++i, id);
      }
      DatabaseUtils.setInt(pst, ++i, this.getCategoryId());
      pst.setString(++i, this.getTitle());
      pst.setString(++i, this.getDescription());
      if (itemId != -1) {
        DatabaseUtils.setInt(pst, ++i, this.getItemId());
      }
      if (entered != null) {
        pst.setTimestamp(++i, this.getEntered());
      }
      pst.setInt(++i, this.getModifiedBy());
      if (modified != null) {
        pst.setTimestamp(++i, this.getModified());
      }
      pst.setInt(++i, this.getModifiedBy());
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "knowledge_base_kb_id_seq", id);
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
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  override          Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int update(Connection db, boolean override) throws SQLException {
    int resultCount = 0;
    if (this.getId() == -1) {
      return -1;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE knowledge_base " +
        " SET category_id = ?, title = ?, description = ?, ");
    if (this.getItemId() != -1) {
      sql.append(" item_id = ?, ");
    }
    sql.append(" modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
        " modifiedby = ? " +
        " WHERE kb_id = ? ");
    if (!override) {
      sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
    }
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getCategoryId());
    pst.setString(++i, this.getTitle());
    pst.setString(++i, this.getDescription());
    if (itemId != -1) {
      pst.setInt(++i, this.getItemId());
    }
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());
    if (!override && this.getModified() != null) {
      pst.setTimestamp(++i, this.getModified());
    }
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    return update(db, false);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Knowledge Base ID not specified");
    }
    DependencyList dependencyList = new DependencyList();
    //Check for documents
    Dependency docDependency = new Dependency();
    docDependency.setName("documents");
    docDependency.setCount(FileItemList.retrieveRecordCount(db, Constants.DOCUMENTS_KNOWLEDGEBASE, this.getId()));
    docDependency.setCanDelete(true);
    dependencyList.add(docDependency);

    return dependencyList;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  baseFilePath      Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db, String baseFilePath) throws SQLException {
    PreparedStatement pst = null;
    pst = db.prepareStatement("DELETE FROM knowledge_base WHERE kb_id = ? ");
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();

    //Delete any related documents
    FileItemList fileList = new FileItemList();
    fileList.setLinkModuleId(Constants.DOCUMENTS_KNOWLEDGEBASE);
    fileList.setLinkItemId(this.getId());
    fileList.buildList(db);
    fileList.delete(db, getFileLibraryPath(baseFilePath, "knowledgebase"));
    fileList = null;
    return true;
  }

/*Get and Set methods*/
  /**
   *  Gets the id attribute of the KnowledgeBase object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the KnowledgeBase object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the KnowledgeBase object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the categoryId attribute of the KnowledgeBase object
   *
   * @return    The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   *  Sets the categoryId attribute of the KnowledgeBase object
   *
   * @param  tmp  The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   *  Sets the categoryId attribute of the KnowledgeBase object
   *
   * @param  tmp  The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the title attribute of the KnowledgeBase object
   *
   * @return    The title value
   */
  public String getTitle() {
    return title;
  }


  /**
   *  Sets the title attribute of the KnowledgeBase object
   *
   * @param  tmp  The new title value
   */
  public void setTitle(String tmp) {
    this.title = tmp;
  }


  /**
   *  Gets the description attribute of the KnowledgeBase object
   *
   * @return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Sets the description attribute of the KnowledgeBase object
   *
   * @param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Gets the itemId attribute of the KnowledgeBase object
   *
   * @return    The itemId value
   */
  public int getItemId() {
    return itemId;
  }


  /**
   *  Sets the itemId attribute of the KnowledgeBase object
   *
   * @param  tmp  The new itemId value
   */
  public void setItemId(int tmp) {
    this.itemId = tmp;
  }


  /**
   *  Sets the itemId attribute of the KnowledgeBase object
   *
   * @param  tmp  The new itemId value
   */
  public void setItemId(String tmp) {
    this.itemId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the entered attribute of the KnowledgeBase object
   *
   * @return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Sets the entered attribute of the KnowledgeBase object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the KnowledgeBase object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the enteredBy attribute of the KnowledgeBase object
   *
   * @return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Sets the enteredBy attribute of the KnowledgeBase object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the KnowledgeBase object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the modified attribute of the KnowledgeBase object
   *
   * @return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Sets the modified attribute of the KnowledgeBase object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the KnowledgeBase object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the modifiedBy attribute of the KnowledgeBase object
   *
   * @return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Sets the modifiedBy attribute of the KnowledgeBase object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the KnowledgeBase object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the item attribute of the KnowledgeBase object
   *
   * @return    The item value
   */
  public FileItem getItem() {
    return item;
  }


  /**
   *  Sets the item attribute of the KnowledgeBase object
   *
   * @param  tmp  The new item value
   */
  public void setItem(FileItem tmp) {
    this.item = tmp;
  }


  /**
   *  Gets the buildResources attribute of the KnowledgeBase object
   *
   * @return    The buildResources value
   */
  public boolean getBuildResources() {
    return buildResources;
  }


  /**
   *  Sets the buildResources attribute of the KnowledgeBase object
   *
   * @param  tmp  The new buildResources value
   */
  public void setBuildResources(boolean tmp) {
    this.buildResources = tmp;
  }


  /**
   *  Sets the buildResources attribute of the KnowledgeBase object
   *
   * @param  tmp  The new buildResources value
   */
  public void setBuildResources(String tmp) {
    this.buildResources = DatabaseUtils.parseBoolean(tmp);
  }

}

