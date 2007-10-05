/*
 *  Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.service.sync.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.service.base.SyncTable;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

import java.sql.*;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @version
 * @created    November 8, 2006
 */
public class SyncPackageData extends GenericBean {

  private final static long serialVersionUID = 1057867492598700371L;

  private int id = -1;
  private int packageId = -1;
  private int tableId = -1;
  private int action = -1;
  private int identityStart = -1;
  private int offset = -1;
  private int items = -1;
  private Timestamp lastAnchor = null;
  private Timestamp nextAnchor = null;
  private Timestamp entered = null;


  /**
   * @return    the action
   */
  public int getAction() {
    return action;
  }


  /**
   * @param  action  the action to set
   */
  public void setAction(int action) {
    this.action = action;
  }


  /**
   * @param  action  the action to set
   */
  public void setAction(String action) {
    this.action = Integer.parseInt(action);
  }


  /**
   * @return    the entered
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   * @param  entered  the entered to set
   */
  public void setEntered(Timestamp entered) {
    this.entered = entered;
  }


  /**
   * @param  entered  the entered to set
   */
  public void setEntered(String entered) {
    this.entered = DateUtils.parseTimestampString(entered);
  }


  /**
   * @return    the id
   */
  public int getId() {
    return id;
  }


  /**
   * @param  id  the id to set
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   * @param  id  the id to set
   */
  public void setId(String id) {
    this.id = Integer.parseInt(id);
  }


  /**
   * @return    the identityStart
   */
  public int getIdentityStart() {
    return identityStart;
  }


  /**
   * @param  identityStart  the identityStart to set
   */
  public void setIdentityStart(int identityStart) {
    this.identityStart = identityStart;
  }


  /**
   * @param  identityStart  the identityStart to set
   */
  public void setIdentityStart(String identityStart) {
    this.identityStart = Integer.parseInt(identityStart);
  }


  /**
   * @return    the items
   */
  public int getItems() {
    return items;
  }


  /**
   * @param  items  the items to set
   */
  public void setItems(int items) {
    this.items = items;
  }


  /**
   * @param  items  the items to set
   */
  public void setItems(String items) {
    this.items = Integer.parseInt(items);
  }


  /**
   * @return    the lastAnchor
   */
  public Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   * @param  lastAnchor  the lastAnchor to set
   */
  public void setLastAnchor(Timestamp lastAnchor) {
    this.lastAnchor = lastAnchor;
  }


  /**
   * @param  lastAnchor  the lastAnchor to set
   */
  public void setLastAnchor(String lastAnchor) {
    this.lastAnchor = DateUtils.parseTimestampString(lastAnchor);
  }


  /**
   * @return    the nextAnchor
   */
  public Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   * @param  nextAnchor  the nextAnchor to set
   */
  public void setNextAnchor(Timestamp nextAnchor) {
    this.nextAnchor = nextAnchor;
  }


  /**
   * @param  nextAnchor  the nextAnchor to set
   */
  public void setNextAnchor(String nextAnchor) {
    this.nextAnchor = DateUtils.parseTimestampString(nextAnchor);
  }


  /**
   * @return    the offset
   */
  public int getOffset() {
    return offset;
  }


  /**
   * @param  offset  the offset to set
   */
  public void setOffset(int offset) {
    this.offset = offset;
  }


  /**
   * @param  offset  the offset to set
   */
  public void setOffset(String offset) {
    this.offset = Integer.parseInt(offset);
  }


  /**
   * @return    the packageId
   */
  public int getPackageId() {
    return packageId;
  }


  /**
   * @param  packageId  the packageId to set
   */
  public void setPackageId(int packageId) {
    this.packageId = packageId;
  }


  /**
   * @param  packageId  the packageId to set
   */
  public void setPackageId(String packageId) {
    this.packageId = Integer.parseInt(packageId);
  }


  /**
   * @return    the tableId
   */
  public int getTableId() {
    return tableId;
  }


  /**
   * @param  tableId  the tableId to set
   */
  public void setTableId(int tableId) {
    this.tableId = tableId;
  }


  /**
   * @param  tableId  the tableId to set
   */
  public void setTableId(String tableId) {
    this.tableId = Integer.parseInt(tableId);
  }


  /**
   *  Constructor for the SyncPackageData object
   */
  public SyncPackageData() { }


  /**
   *  Constructor for the SyncPackageData object
   *
   * @param  syncPackage  Description of the Parameter
   * @param  syncTable    Description of the Parameter
   */
  public SyncPackageData(SyncPackage syncPackage, SyncTable syncTable) {
    this.packageId = syncPackage.getId();
    this.tableId = syncTable.getId();
    if (syncPackage.getType() == SyncPackage.INIT) {
      this.identityStart = 1;
    }
    //set offset and items parameters too.
  }


  /**
   *  Constructor for the SyncPackageData object
   *
   * @param  rs
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException
   */
  public SyncPackageData(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the SyncPackageData object
   *
   * @param  db
   * @param  dataId            Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException
   */
  public SyncPackageData(Connection db, String dataId) throws SQLException {
    queryRecord(db, Integer.parseInt(dataId));
  }


  /**
   *  Description of the Method
   *
   * @param  rs             Description of the Returned Value
   * @throws  SQLException
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("data_id");
    packageId = rs.getInt("package_id");
    tableId = rs.getInt("table_id");
    action = rs.getInt("action");
    identityStart = rs.getInt("identity_start");
    offset = DatabaseUtils.getInt(rs, "offset");
    items = DatabaseUtils.getInt(rs, "items");
    lastAnchor = rs.getTimestamp("last_anchor");
    nextAnchor = rs.getTimestamp("next_anchor");
    entered = rs.getTimestamp("entered");
  }


  /**
   *  Description of the Method
   *
   * @param  db
   * @param  dataId         Description of the Parameter
   * @throws  SQLException
   */
  private void queryRecord(Connection db, int dataId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append("SELECT package_id, table_id, "
         + DatabaseUtils.addQuotes(db, "action") + ", "
         + "identity_start, "
         + DatabaseUtils.addQuotes(db, "offset") + ", "
         + DatabaseUtils.addQuotes(db, "items") + ", "
         + "last_anchor, next_anchor, entered "
         + "FROM sync_package_data "
         + "WHERE data_id = ? ");

    pst = db.prepareStatement(sql.toString());
    pst.setInt(1, dataId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("SyncPackageData record not found.");
    }
  }


  /**
   *  Inserts this object into the database, and populates this Id. For
   *  maintenance, only the required fields are inserted, then an update is
   *  executed to finish the record.
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   * @since                 1.0
   */
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    boolean doCommit = false;
    try {
      if ((doCommit = db.getAutoCommit()) == true) {
        db.setAutoCommit(false);
      }
      id = DatabaseUtils.getNextSeq(db, "sync_package_data_data_id_seq");
      sql.append("INSERT INTO sync_package_data (");

      if (id > -1) {
        sql.append("data_id, ");
      }
      sql.append(" package_id, table_id, "
           + DatabaseUtils.addQuotes(db, "action") + ", "
           + "identity_start, "
           + DatabaseUtils.addQuotes(db, "offset") + ", "
           + DatabaseUtils.addQuotes(db, "items") + ", "
           + "last_anchor, ");
      sql.append("entered, ");
      sql.append("next_anchor ) ");
      sql.append("VALUES (");
      if (id > -1) {
        sql.append("?, ");
      }
      sql.append("?, ?, ?, ?, ?, ?, ?, ");
      if (entered != null) {
        sql.append("?, ");
      } else {
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      sql.append("?) ");

      PreparedStatement pst = db.prepareStatement(sql.toString());
      int i = 0;
      if (id > -1) {
        DatabaseUtils.setInt(pst, ++i, id);
      }
      pst.setInt(++i, packageId);
      pst.setInt(++i, tableId);
      pst.setInt(++i, action);
      pst.setInt(++i, identityStart);
      DatabaseUtils.setInt(pst, ++i, offset);
      DatabaseUtils.setInt(pst, ++i, items);
      pst.setTimestamp(++i, lastAnchor);
      if (entered != null) {
        pst.setTimestamp(++i, entered);
      }
      pst.setTimestamp(++i, nextAnchor);

      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "sync_package_data_data_id_seq", id);
      
      if (doCommit) {
        db.commit();
      }
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      if (doCommit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   *  Update the syncPackage's information
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   * @since                 1.0
   */
  public int update(Connection db) throws SQLException {
    int resultCount = -1;
    if (this.getId() < -1) {
      throw new SQLException("Contact ID was not specified");
    }

    StringBuffer sql = new StringBuffer();

    sql.append("UPDATE sync_package_data SET "
         + "package_id = ?, table_id= ?, "
         + DatabaseUtils.addQuotes(db, "action") + "= ?, "
         + "identity_start = ?, "
         + DatabaseUtils.addQuotes(db, "offset") + "= ?, "
         + DatabaseUtils.addQuotes(db, "items") + "= ?, "
         + "last_anchor = ?, next_anchor = ?, entered = ? "
         + "WHERE packet_id = ?");

    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    DatabaseUtils.setInt(pst, ++i, this.getPackageId());
    DatabaseUtils.setInt(pst, ++i, this.getTableId());
    DatabaseUtils.setInt(pst, ++i, this.getAction());
    DatabaseUtils.setInt(pst, ++i, this.getIdentityStart());
    DatabaseUtils.setInt(pst, ++i, this.getOffset());
    DatabaseUtils.setInt(pst, ++i, this.getItems());
    DatabaseUtils.setTimestamp(pst, ++i, this.getLastAnchor());
    DatabaseUtils.setTimestamp(pst, ++i, this.getNextAnchor());
    DatabaseUtils.setTimestamp(pst, ++i, this.getEntered());
    DatabaseUtils.setInt(pst, ++i, id);

    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
  }
  
  /** Deletes the syncPackage's information
   * @param db
   * @return
   * @throws SQLException
   */
  public boolean delete(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM sync_package_data WHERE data_id = ? ");
    
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();
    
    return true;
  }
}

