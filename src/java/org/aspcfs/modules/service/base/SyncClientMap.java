//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import org.theseus.actions.*;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;
import com.darkhorseventures.utils.ObjectUtils;
import com.darkhorseventures.utils.RecordList;
import java.util.Hashtable;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    June 3, 2002
 *@version    $Id$
 */
public class SyncClientMap {

  //private int id = -1;
  private int clientId = -1;
  private int tableId = -1;
  private int recordId = -1;
  private int clientUniqueId = -1;
  private boolean complete = false;
  private java.sql.Timestamp statusDate = null;


  /**
   *  Constructor for the SyncClientMap object
   */
  public SyncClientMap() { }


  /**
   *  Sets the clientId attribute of the SyncClientMap object
   *
   *@param  tmp  The new clientId value
   */
  public void setClientId(int tmp) {
    this.clientId = tmp;
  }


  /**
   *  Sets the tableId attribute of the SyncClientMap object
   *
   *@param  tmp  The new tableId value
   */
  public void setTableId(int tmp) {
    this.tableId = tmp;
  }


  /**
   *  Sets the recordId attribute of the SyncClientMap object
   *
   *@param  tmp  The new recordId value
   */
  public void setRecordId(int tmp) {
    this.recordId = tmp;
  }


  /**
   *  Sets the clientUniqueId attribute of the SyncClientMap object
   *
   *@param  tmp  The new clientUniqueId value
   */
  public void setClientUniqueId(String tmp) {
    this.clientUniqueId = Integer.parseInt(tmp);
  }
  
  public void setClientUniqueId(int tmp) {
    this.clientUniqueId = tmp;
  }


  /**
   *  Sets the complete attribute of the SyncClientMap object
   *
   *@param  tmp  The new complete value
   */
  public void setComplete(boolean tmp) {
    this.complete = tmp;
  }


  /**
   *  Sets the statusDate attribute of the SyncClientMap object
   *
   *@param  tmp  The new statusDate value
   */
  public void setStatusDate(java.sql.Timestamp tmp) {
    this.statusDate = tmp;
  }



  /**
   *  Gets the clientId attribute of the SyncClientMap object
   *
   *@return    The clientId value
   */
  public int getClientId() {
    return clientId;
  }


  /**
   *  Gets the tableId attribute of the SyncClientMap object
   *
   *@return    The tableId value
   */
  public int getTableId() {
    return tableId;
  }


  /**
   *  Gets the recordId attribute of the SyncClientMap object
   *
   *@return    The recordId value
   */
  public int getRecordId() {
    return recordId;
  }


  /**
   *  Gets the clientUniqueId attribute of the SyncClientMap object
   *
   *@return    The clientUniqueId value
   */
  public int getClientUniqueId() {
    return clientUniqueId;
  }


  /**
   *  Gets the complete attribute of the SyncClientMap object
   *
   *@return    The complete value
   */
  public boolean getComplete() {
    return complete;
  }


  /**
   *  Gets the statusDate attribute of the SyncClientMap object
   *
   *@return    The statusDate value
   */
  public java.sql.Timestamp getStatusDate() {
    return statusDate;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  timestamp         Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean insert(Connection db, String timestamp) throws SQLException {
    if (timestamp != null && !timestamp.trim().equals("")) {
      return insertMap(db, java.sql.Timestamp.valueOf(timestamp));
    } else {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("NULL TIMESTAMP-> " + getTableId() + " " + getRecordId());
      }
      return insertMap(db, null);
    }
  }
  
  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  timestamp         Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean insertMap(Connection db, java.sql.Timestamp timestamp) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO sync_map " +
        "(client_id, table_id, record_id, cuid, complete, status_date) " +
        "VALUES (?, ?, ?, ?, ?, ?) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, clientId);
    pst.setInt(++i, tableId);
    pst.setInt(++i, recordId);
    pst.setInt(++i, clientUniqueId);
    pst.setBoolean(++i, complete);
    if (timestamp != null) {
      pst.setTimestamp(++i, timestamp);
    } else {
      pst.setNull(++i, java.sql.Types.DATE);
    }
    pst.execute();
    pst.close();

    return true;
  }
  
  public boolean insertConflict(Connection db) throws SQLException {
    String sql = 
      "INSERT INTO sync_conflict_log " +
      "(client_id, table_id, record_id) " +
      "VALUES (?, ?, ?) ";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(++i, clientId);
    pst.setInt(++i, tableId);
    pst.setInt(++i, recordId);
    pst.execute();
    pst.close();

    return true;
  }
  
  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  referenceTable    Description of Parameter
   *@param  serverId          Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@deprecated
   */
  public int lookupClientId(Connection db, int referenceTable, String serverId) throws SQLException {
    int resultId = -1;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT cuid " +
        "FROM sync_map " +
        "WHERE client_id = ? " +
        "AND table_id = ? " +
        "AND record_id = ? ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, clientId);
    pst.setInt(++i, referenceTable);
    pst.setInt(++i, Integer.parseInt(serverId));
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      resultId = rs.getInt("cuid");
    }
    rs.close();
    pst.close();
    return resultId;
  }


  /**
   *  Description of the Method
   *
   *@param  clientManager   Description of Parameter
   *@param  referenceTable  Description of Parameter
   *@param  serverId        Description of Parameter
   *@return                 Description of the Returned Value
   */
  public int lookupClientId(SyncClientManager clientManager, int referenceTable, String serverId) {
    int resultId = -1;
    
    if (clientManager == null) {
      System.out.println("SyncClientMap-> clientManager is null (needs to be initialized first)");
    }
    
    if (clientManager.containsKey(new Integer(clientId)) && serverId != null) {
      Hashtable clientLookup = (Hashtable) clientManager.get(new Integer(clientId));
      if (clientLookup.containsKey(new Integer(referenceTable))) {
        Hashtable tableLookup = (Hashtable) clientLookup.get(new Integer(referenceTable));
        Integer serverNum = new Integer(serverId);
        if (tableLookup.containsKey(serverNum)) {
          Integer value = (Integer)tableLookup.get(serverNum);
          if (value != null) {
            resultId = ((Integer)value).intValue();
          } else {
            if (System.getProperty("DEBUG") != null) {
              System.out.println("SyncClientMap-> lookupClientId: Null value for table " + referenceTable + " record " + serverId);
            }
          }
        }
      }
    } else {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("SyncClientMap-> lookupClientId: Skipping search table " + referenceTable + " record " + serverId);
      }
    }
    return resultId;
  }


  /**
   *  Lookup the server's id, hitting the database
   *
   *@param  db                Description of Parameter
   *@param  referenceTable    Description of Parameter
   *@param  clientCuid        Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public int lookupServerId(Connection db, int referenceTable, String clientCuid) throws SQLException {
    int resultId = -1;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT record_id " +
        "FROM sync_map " +
        "WHERE client_id = ? " +
        "AND table_id = ? " +
        "AND cuid = ? ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, clientId);
    pst.setInt(++i, referenceTable);
    pst.setInt(++i, Integer.parseInt(clientCuid));
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      resultId = rs.getInt("record_id");
    }
    rs.close();
    pst.close();
    return resultId;
  }


  /**
   *  Searches for the server's id from the clientManager cache
   *
   *@param  clientManager   Description of Parameter
   *@param  referenceTable  Description of Parameter
   *@param  clientCuid      Description of Parameter
   *@return                 Description of the Returned Value
   */
  public int lookupServerId(SyncClientManager clientManager, int referenceTable, String clientCuid) {
    int resultId = -1;
    if (clientManager.containsKey(new Integer(clientId)) && clientCuid != null) {
      Hashtable clientLookup = (Hashtable) clientManager.get(new Integer(clientId));
      Hashtable tableLookup = (Hashtable) clientLookup.get(new Integer(referenceTable));

      if (tableLookup == null) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("SyncClientMap-> Referenced table is null: " + referenceTable);
        }
      }
      
      Iterator i = tableLookup.keySet().iterator();
      System.out.println("SyncClientMap-> Table count: " + tableLookup.size());
      while (i.hasNext()) {
        Integer serverNum = (Integer) i.next();
        if (((Integer) tableLookup.get(serverNum)).intValue() == Integer.parseInt(clientCuid)) {
          return serverNum.intValue();
        }
      }
    }
    return resultId;
  }
  
  public void buildStatusDate(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT status_date " +
        "FROM sync_map " +
        "WHERE client_id = ? " +
        "AND table_id = ? " +
        "AND record_id = ? " +
        "AND cuid = ? "); 
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, clientId);
    pst.setInt(++i, tableId);
    pst.setInt(++i, recordId);
    pst.setInt(++i, clientUniqueId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      statusDate = rs.getTimestamp("status_date");
      System.out.println("SyncClientMap-> Retrieved status date: " + statusDate);
    } else {
      System.out.println(this.toString());
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (clientId == -1 && tableId == -1 && recordId == -1) {
      throw new SQLException("ID was not specified");
    }

    System.out.println(this.toString());
    
    PreparedStatement pst = null;
    //Delete the record
    int recordCount = 0;
    pst = db.prepareStatement(
        "DELETE FROM sync_map " +
        "WHERE client_id = ? " +
        "AND table_id = ? " +
        "AND record_id = ? ");
    int i = 0;
    pst.setInt(++i, clientId);
    pst.setInt(++i, tableId);
    pst.setInt(++i, recordId);
    recordCount = pst.executeUpdate();
    pst.close();

    if (recordCount == 0) {
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public int updateStatus(Connection db) throws SQLException {
    if (clientId == -1 && tableId == -1 && recordId == -1) {
      throw new SQLException("Record ID was not specified");
    }

    int resultCount = 0;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE sync_map " +
        "SET complete = ? " +
        "WHERE client_id = ? " +
        "AND table_id = ? " +
        "AND record_id = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setBoolean(++i, complete);
    pst.setInt(++i, clientId);
    pst.setInt(++i, tableId);
    pst.setInt(++i, recordId);
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }

  public int updateStatusDate(Connection db, String timestamp) throws SQLException {
    if (timestamp != null && !timestamp.trim().equals("")) {
      return updateStatusDate(db, java.sql.Timestamp.valueOf(timestamp));
    } else {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("NULL TIMESTAMP-> " + getTableId() + " " + getRecordId());
      }
      return updateStatusDate(db, (java.sql.Timestamp)null);
    }
  }

  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  timestamp         Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public int updateStatusDate(Connection db, java.sql.Timestamp timestamp) throws SQLException {
    if (clientId == -1 && tableId == -1 && recordId == -1) {
      throw new SQLException("Record ID was not specified");
    }

    int resultCount = 0;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE sync_map " +
        "SET status_date = ? " +
        "WHERE client_id = ? " +
        "AND table_id = ? " +
        "AND record_id = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    if (timestamp != null) {
      pst.setTimestamp(++i, timestamp);
    } else {
      pst.setNull(++i, java.sql.Types.DATE);
    }
    pst.setInt(++i, clientId);
    pst.setInt(++i, tableId);
    pst.setInt(++i, recordId);
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  pst               Description of Parameter
   *@param  uniqueField       Description of Parameter
   *@param  tableName         Description of Parameter
   *@param  recordList        Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public ResultSet buildSyncDeletes(Connection db, PreparedStatement pst, String uniqueField, String tableName, RecordList recordList) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT cuid, record_id " +
        "FROM sync_map " +
        "WHERE client_id = ? " +
        "AND table_id = ? " +
        "AND record_id NOT IN (SELECT " + uniqueField + " FROM " + tableName + ") ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, clientId);
    pst.setInt(++i, tableId);
    ResultSet rs = pst.executeQuery();
    System.out.println(this.toString());
    return rs;
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //id = rs.getInt("map_id");
    clientId = rs.getInt("client_id");
    tableId = rs.getInt("table_id");
    recordId = rs.getInt("record_id");
    clientUniqueId = rs.getInt("cuid");
    complete = rs.getBoolean("complete");
    statusDate = rs.getTimestamp("status_date");
  }

  public String toString() {
    String lf = System.getProperty("line.separator");
    StringBuffer tmp = new StringBuffer();
    tmp.append("SyncClientMap=============" + lf);
    tmp.append(" clientId: " + clientId + lf);
    tmp.append(" tableId: " + tableId + lf);
    tmp.append(" recordId: " + recordId + lf);
    tmp.append(" cuid: " + clientUniqueId + lf);
    tmp.append(" statusDate: " + statusDate + lf);
    return tmp.toString();
  }
}

