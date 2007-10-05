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
import com.zeroio.iteam.base.FileItem;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

import java.sql.*;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    November 8, 2006
 * @version
 */
public class SyncPackage extends GenericBean {

  private final static long serialVersionUID = 53282556842580942L;

  //Sync Package Recipient
  public final static int SYNC_CLIENT = 1;
  public final static int SYNC_SERVER = 2;

  //Sync Package Type constants
  public final static int INIT = 1;
  public final static int UPDATE = 2;

  //Sync Package Status constants
  public final static int IDLE = 0;
  public final static int START = 1;
  public final static int COMPLETE = 2;
  public final static int DOWNLOADED = 3;

  private int id = -1;
  private int clientId = -1;
  private int type = -1;
  private int size = -1;
  private int statusId = -1;
  private int recipient = SyncPackage.SYNC_CLIENT;
  private Timestamp statusDate = null;
  private Timestamp lastAnchor = null;
  private Timestamp nextAnchor = null;
  private int packageFileId = -1;
  private Timestamp entered = null;

  //resources
  private boolean buildPackageData = false;
  private SyncPackageDataList dataItems = null;


  /**
   *  Gets the dataItems attribute of the SyncPackage object
   *
   * @return    The dataItems value
   */
  public SyncPackageDataList getDataItems() {
    return dataItems;
  }


  /**
   *  Sets the dataItems attribute of the SyncPackage object
   *
   * @param  tmp  The new dataItems value
   */
  public void setDataItems(SyncPackageDataList tmp) {
    this.dataItems = tmp;
  }


  /**
   *  Gets the buildPackageData attribute of the SyncPackage object
   *
   * @return    The buildPackageData value
   */
  public boolean getBuildPackageData() {
    return buildPackageData;
  }


  /**
   *  Sets the buildPackageData attribute of the SyncPackage object
   *
   * @param  tmp  The new buildPackageData value
   */
  public void setBuildPackageData(boolean tmp) {
    this.buildPackageData = tmp;
  }


  /**
   *  Sets the buildPackageData attribute of the SyncPackage object
   *
   * @param  tmp  The new buildPackageData value
   */
  public void setBuildPackageData(String tmp) {
    this.buildPackageData = DatabaseUtils.parseBoolean(tmp);
  }



  /**
   *  Gets the recipient attribute of the SyncPackage object
   *
   * @return    The recipient value
   */
  public int getRecipient() {
    return recipient;
  }


  /**
   *  Sets the recipient attribute of the SyncPackage object
   *
   * @param  tmp  The new recipient value
   */
  public void setRecipient(int tmp) {
    this.recipient = tmp;
  }


  /**
   *  Sets the recipient attribute of the SyncPackage object
   *
   * @param  tmp  The new recipient value
   */
  public void setRecipient(String tmp) {
    this.recipient = Integer.parseInt(tmp);
  }


  /**
   * @return    the clientId
   */
  public int getClientId() {
    return clientId;
  }


  /**
   * @param  clientId  the clientId to set
   */
  public void setClientId(int clientId) {
    this.clientId = clientId;
  }


  /**
   * @param  clientId  the clientId to set
   */
  public void setClientId(String clientId) {
    this.clientId = Integer.parseInt(clientId);
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
   * @return    the package_file_id
   */
  public int getPackageFileId() {
    return packageFileId;
  }


  /**
   * @param  packageFileId  The new packageFileId value
   */
  public void setPackageFileId(int packageFileId) {
    this.packageFileId = packageFileId;
  }


  /**
   * @param  packageFileId  The new packageFileId value
   */
  public void setPackageFileId(String packageFileId) {
    this.packageFileId = Integer.parseInt(packageFileId);
  }


  /**
   *  Gets the lastAnchor attribute of the SyncPackage object
   *
   * @return    The lastAnchor value
   */
  public Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   *  Sets the lastAnchor attribute of the SyncPackage object
   *
   * @param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the lastAnchor attribute of the SyncPackage object
   *
   * @param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the nextAnchor attribute of the SyncPackage object
   *
   * @return    The nextAnchor value
   */
  public Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   *  Sets the nextAnchor attribute of the SyncPackage object
   *
   * @param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the SyncPackage object
   *
   * @param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * @return    the size
   */
  public int getSize() {
    return size;
  }


  /**
   * @param  size  the size to set
   */
  public void setSize(int size) {
    this.size = size;
  }


  /**
   * @param  size  the size to set
   */
  public void setSize(String size) {
    this.size = Integer.parseInt(size);
  }


  /**
   * @return    the statusDate
   */
  public Timestamp getStatusDate() {
    return statusDate;
  }


  /**
   * @param  statusDate  the statusDate to set
   */
  public void setStatusDate(Timestamp statusDate) {
    this.statusDate = statusDate;
  }


  /**
   * @param  statusDate  the statusDate to set
   */
  public void setStatusDate(String statusDate) {
    this.statusDate = DateUtils.parseTimestampString(statusDate);
  }


  /**
   * @return    the statusId
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   * @param  statusId  the statusId to set
   */
  public void setStatusId(int statusId) {
    this.statusId = statusId;
  }


  /**
   * @param  statusId  the statusId to set
   */
  public void setStatusId(String statusId) {
    this.statusId = Integer.parseInt(statusId);
  }


  /**
   * @return    the type
   */
  public int getType() {
    return type;
  }


  /**
   * @param  type  the type to set
   */
  public void setType(int type) {
    this.type = type;
  }


  /**
   * @param  type  the type to set
   */
  public void setType(String type) {
    this.type = Integer.parseInt(type);
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
   *  Constructor for the SyncPackage object
   */
  public SyncPackage() { }


  /**
   *  Constructor for the SyncPackage object
   *
   * @param  rs
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException
   */
  public SyncPackage(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the SyncPackage object
   *
   * @param  db
   * @param  packageId         Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException
   */
  public SyncPackage(Connection db, String packageId) throws SQLException {
    queryRecord(db, Integer.parseInt(packageId));
  }


  /**
   *  Constructor for the SyncPackage object
   *
   * @param  db                Description of the Parameter
   * @param  packageId         Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public SyncPackage(Connection db, int packageId) throws SQLException {
    queryRecord(db, packageId);
  }


  /**
   *  Description of the Method
   *
   * @param  db
   * @param  packageId      Description of the Parameter
   * @throws  SQLException  Description of the Returned Value
   */
  public void queryRecord(Connection db, int packageId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append("SELECT package_id, client_id, "
         + DatabaseUtils.addQuotes(db, "type") + ", "
         + DatabaseUtils.addQuotes(db, "size") + ", "
         + "recipient, status_id, status_date, last_anchor, next_anchor, package_file_id, entered "
         + "FROM sync_package " + "WHERE package_id = ? ");

    pst = db.prepareStatement(sql.toString());
    pst.setInt(1, packageId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("SyncPackage record not found.");
    }

    if (buildPackageData) {
      dataItems = new SyncPackageDataList();
      dataItems.setPackageId(this.id);
      dataItems.buildList(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  rs                Description of the Returned Value
   * @exception  SQLException  Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("package_id");
    clientId = rs.getInt("client_id");
    type = rs.getInt("type");
    size = DatabaseUtils.getInt(rs, "size");
    recipient = rs.getInt("recipient");
    statusId = rs.getInt("status_id");
    statusDate = rs.getTimestamp("status_date");
    lastAnchor = rs.getTimestamp("last_anchor");
    nextAnchor = rs.getTimestamp("next_anchor");
    packageFileId = DatabaseUtils.getInt(rs, "package_file_id");
    entered = rs.getTimestamp("entered");
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
      id = DatabaseUtils.getNextSeq(db, "sync_package_package_id_seq");
      sql.append("INSERT INTO sync_package (");

      if (id > -1) {
        sql.append("package_id, ");
      }
      sql.append("client_id, " + DatabaseUtils.addQuotes(db, "type") + ", "
           + DatabaseUtils.addQuotes(db, "size") + ", "
           + "recipient, status_id, ");
      if (statusDate != null) {
        sql.append("status_date, ");
      }
      sql.append("entered, ");
      sql.append("last_anchor, next_anchor, package_file_id) ");
      sql.append("VALUES (");
      if (id > -1) {
        sql.append("?, ");
      }
      sql.append("?, ?, ?, ?, ?, ");
      if (statusDate != null) {
        sql.append("?, ");
      }
      if (entered != null) {
        sql.append("?, ");
      } else {
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      sql.append("?, ?, ?) ");

      PreparedStatement pst = db.prepareStatement(sql.toString());
      int i = 0;
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setInt(++i, this.getClientId());
      pst.setInt(++i, this.getType());
      DatabaseUtils.setInt(pst, ++i, this.getSize());
      pst.setInt(++i, this.getRecipient());
      pst.setInt(++i, this.getStatusId());
      if (statusDate != null) {
        pst.setTimestamp(++i, statusDate);
      }
      if (entered != null) {
        pst.setTimestamp(++i, entered);
      }
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      } else {
        pst.setNull(++i, java.sql.Types.DATE);
      }
      pst.setTimestamp(++i, nextAnchor);
      DatabaseUtils.setInt(pst, ++i, this.getPackageFileId());

      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "sync_package_package_id_seq", id);

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

    sql.append("UPDATE sync_package SET " + "client_id = ?, "
         + DatabaseUtils.addQuotes(db, "type") + " = ?, "
         + DatabaseUtils.addQuotes(db, "size") + " = ?, "
         + "status_id = ?, status_date = ?, package_file_id = ?, "
         + "entered = ? " + "WHERE package_id = ?");

    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    DatabaseUtils.setInt(pst, ++i, this.getClientId());
    DatabaseUtils.setInt(pst, ++i, this.getType());
    DatabaseUtils.setInt(pst, ++i, this.getSize());
    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
    DatabaseUtils.setTimestamp(pst, ++i, this.getStatusDate());
    DatabaseUtils.setInt(pst, ++i, this.getPackageFileId());
    DatabaseUtils.setTimestamp(pst, ++i, this.getEntered());
    DatabaseUtils.setInt(pst, ++i, id);

    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
  }


  /**
   *  Deletes the syncPackage's information
   *
   * @param  db
   * @param  packageDirPath  Description of the Parameter
   * @return
   * @throws  SQLException
   */
  public boolean delete(Connection db, String packageDirPath) throws SQLException {
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }

      SyncPackageDataList dataItems = new SyncPackageDataList();
      dataItems.setPackageId(this.id);
      dataItems.buildList(db);

      Iterator i = dataItems.iterator();
      while (i.hasNext()) {
        SyncPackageData dataItem = (SyncPackageData) i.next();
        dataItem.delete(db);
      }

      //Delete the package record
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM sync_package WHERE package_id = ? ");

      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      //Delete the Sync Package file in the file library
      FileItem thisFile = new FileItem(db, this.packageFileId);
      thisFile.delete(db, packageDirPath);

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
   *  Gets the clientSyncPakage attribute of the SyncPackage class
   *
   * @param  db                Description of the Parameter
   * @param  clientId          Description of the Parameter
   * @return                   The clientSyncPakage value
   * @exception  SQLException  Description of the Exception
   */
  public static SyncPackage getClientSyncPakage(Connection db, int clientId) throws SQLException {
    SyncPackageList syncPackageList = new SyncPackageList();
    syncPackageList.setClientId(clientId);
    syncPackageList.buildList(db);

    if (syncPackageList.size() > 0) {
      return (SyncPackage)
          syncPackageList.get(0);
    }
    return null;
  }


  /**
   *  Gets the ready attribute of the SyncPackage object
   *
   * @return    The ready value
   */
  public boolean isReady() {
    return (this.statusId == SyncPackage.COMPLETE);
  }
}

